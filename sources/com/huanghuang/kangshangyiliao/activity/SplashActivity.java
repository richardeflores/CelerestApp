package com.huanghuang.kangshangyiliao.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.p000v4.app.ActivityCompat;
import android.support.p000v4.content.ContextCompat;
import android.widget.FrameLayout;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.Utils;

public class SplashActivity extends BaseActivity {
    private static final int PERMISSIONS_REQUEST = 1001;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView((int) C0418R.layout.activity_splash);
        SystemBarCompat.tint(getWindow(), (FrameLayout) findViewById(C0418R.C0420id.fl_content));
        checkPermission();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            goMain();
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            goMain();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1001);
        }
    }

    private void goMain() {
        Utils.delayTask(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.addFlags(536870912);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 1500);
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1001) {
            if (iArr.length == 3 && iArr[0] == 0 && iArr[1] == 0 && iArr[2] == 0) {
                goMain();
            } else {
                checkPermission();
            }
        }
    }
}
