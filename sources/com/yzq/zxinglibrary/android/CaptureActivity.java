package com.yzq.zxinglibrary.android;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.app.AppCompatActivity;
import android.support.p003v7.app.AppCompatDelegate;
import android.support.p003v7.widget.AppCompatImageView;
import android.support.p003v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.Result;
import com.yzq.zxinglibrary.C0598R;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.camera.CameraManager;
import com.yzq.zxinglibrary.common.Constant;
import com.yzq.zxinglibrary.decode.DecodeImgCallback;
import com.yzq.zxinglibrary.decode.DecodeImgThread;
import com.yzq.zxinglibrary.decode.ImageUtil;
import com.yzq.zxinglibrary.view.ViewfinderView;
import java.io.IOException;

public class CaptureActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {
    private static final String TAG = CaptureActivity.class.getSimpleName();
    private LinearLayoutCompat albumLayout;
    private AppCompatImageView backIv;
    private BeepManager beepManager;
    private LinearLayoutCompat bottomLayout;
    private CameraManager cameraManager;
    public ZxingConfig config;
    private AppCompatImageView flashLightIv;
    private LinearLayoutCompat flashLightLayout;
    private TextView flashLightTv;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private SurfaceView previewView;
    private SurfaceHolder surfaceHolder;
    private ViewfinderView viewfinderView;

    public void surfaceChanged(SurfaceHolder surfaceHolder2, int i, int i2, int i3) {
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public ViewfinderView getViewfinderView() {
        return this.viewfinderView;
    }

    public Handler getHandler() {
        return this.handler;
    }

    public CameraManager getCameraManager() {
        return this.cameraManager;
    }

    public void drawViewfinder() {
        this.viewfinderView.drawViewfinder();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        window.addFlags(128);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ViewCompat.MEASURED_STATE_MASK);
        }
        try {
            this.config = (ZxingConfig) getIntent().getExtras().get(Constant.INTENT_ZXING_CONFIG);
        } catch (Exception e) {
            Log.i("config", e.toString());
        }
        if (this.config == null) {
            this.config = new ZxingConfig();
        }
        setContentView(C0598R.layout.activity_capture);
        initView();
        this.hasSurface = false;
        this.inactivityTimer = new InactivityTimer(this);
        this.beepManager = new BeepManager(this);
        this.beepManager.setPlayBeep(this.config.isPlayBeep());
        this.beepManager.setVibrate(this.config.isShake());
    }

    private void initView() {
        this.previewView = (SurfaceView) findViewById(C0598R.C0600id.preview_view);
        this.previewView.setOnClickListener(this);
        this.viewfinderView = (ViewfinderView) findViewById(C0598R.C0600id.viewfinder_view);
        this.viewfinderView.setZxingConfig(this.config);
        this.backIv = (AppCompatImageView) findViewById(C0598R.C0600id.backIv);
        this.backIv.setOnClickListener(this);
        this.flashLightIv = (AppCompatImageView) findViewById(C0598R.C0600id.flashLightIv);
        this.flashLightTv = (TextView) findViewById(C0598R.C0600id.flashLightTv);
        this.flashLightLayout = (LinearLayoutCompat) findViewById(C0598R.C0600id.flashLightLayout);
        this.flashLightLayout.setOnClickListener(this);
        this.albumLayout = (LinearLayoutCompat) findViewById(C0598R.C0600id.albumLayout);
        this.albumLayout.setOnClickListener(this);
        this.bottomLayout = (LinearLayoutCompat) findViewById(C0598R.C0600id.bottomLayout);
        switchVisibility(this.bottomLayout, this.config.isShowbottomLayout());
        switchVisibility(this.flashLightLayout, this.config.isShowFlashLight());
        switchVisibility(this.albumLayout, this.config.isShowAlbum());
        if (isSupportCameraLedFlash(getPackageManager())) {
            this.flashLightLayout.setVisibility(0);
        } else {
            this.flashLightLayout.setVisibility(8);
        }
    }

    public static boolean isSupportCameraLedFlash(PackageManager packageManager) {
        FeatureInfo[] systemAvailableFeatures;
        if (!(packageManager == null || (systemAvailableFeatures = packageManager.getSystemAvailableFeatures()) == null)) {
            for (FeatureInfo featureInfo : systemAvailableFeatures) {
                if (featureInfo != null && "android.hardware.camera.flash".equals(featureInfo.name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void switchFlashImg(int i) {
        if (i == 8) {
            this.flashLightIv.setImageResource(C0598R.C0599drawable.ic_open);
            this.flashLightTv.setText("关闭闪光灯");
            return;
        }
        this.flashLightIv.setImageResource(C0598R.C0599drawable.ic_close);
        this.flashLightTv.setText("打开闪光灯");
    }

    public void handleDecode(Result result) {
        this.inactivityTimer.onActivity();
        this.beepManager.playBeepSoundAndVibrate();
        Intent intent = getIntent();
        intent.putExtra(Constant.CODED_CONTENT, result.getText());
        setResult(-1, intent);
        finish();
    }

    private void switchVisibility(View view, boolean z) {
        if (z) {
            view.setVisibility(0);
        } else {
            view.setVisibility(8);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.cameraManager = new CameraManager(getApplication(), this.config);
        this.viewfinderView.setCameraManager(this.cameraManager);
        this.handler = null;
        this.surfaceHolder = this.previewView.getHolder();
        if (this.hasSurface) {
            initCamera(this.surfaceHolder);
        } else {
            this.surfaceHolder.addCallback(this);
        }
        this.beepManager.updatePrefs();
        this.inactivityTimer.onResume();
    }

    private void initCamera(SurfaceHolder surfaceHolder2) {
        if (surfaceHolder2 == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        } else if (!this.cameraManager.isOpen()) {
            try {
                this.cameraManager.openDriver(surfaceHolder2);
                if (this.handler == null) {
                    this.handler = new CaptureActivityHandler(this, this.cameraManager);
                }
            } catch (IOException e) {
                Log.w(TAG, e);
                displayFrameworkBugMessageAndExit();
            } catch (RuntimeException e2) {
                Log.w(TAG, "Unexpected error initializing camera", e2);
                displayFrameworkBugMessageAndExit();
            }
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("扫一扫");
        builder.setMessage(getString(C0598R.string.msg_camera_framework_bug));
        builder.setPositiveButton(C0598R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        Log.i("CaptureActivity", "onPause");
        CaptureActivityHandler captureActivityHandler = this.handler;
        if (captureActivityHandler != null) {
            captureActivityHandler.quitSynchronously();
            this.handler = null;
        }
        this.inactivityTimer.onPause();
        this.beepManager.close();
        this.cameraManager.closeDriver();
        if (!this.hasSurface) {
            this.surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.inactivityTimer.shutdown();
        super.onDestroy();
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder2) {
        if (!this.hasSurface) {
            this.hasSurface = true;
            initCamera(surfaceHolder2);
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder2) {
        this.hasSurface = false;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C0598R.C0600id.flashLightLayout) {
            this.cameraManager.switchFlashLight(this.handler);
        } else if (id == C0598R.C0600id.albumLayout) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.PICK");
            intent.setType("image/*");
            startActivityForResult(intent, 10);
        } else if (id == C0598R.C0600id.backIv) {
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 10 && i2 == -1) {
            new DecodeImgThread(ImageUtil.getImageAbsolutePath(this, intent.getData()), new DecodeImgCallback() {
                public void onImageDecodeSuccess(Result result) {
                    CaptureActivity.this.handleDecode(result);
                }

                public void onImageDecodeFailed() {
                    Toast.makeText(CaptureActivity.this, "抱歉，解析失败,换个图片试试.", 0).show();
                }
            }).run();
        }
    }
}
