package com.yzq.zxinglibrary.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.yzq.zxinglibrary.C0598R;
import com.yzq.zxinglibrary.android.CaptureActivityHandler;
import com.yzq.zxinglibrary.bean.ZxingConfig;

public final class CameraManager {
    private static final String TAG = CameraManager.class.getSimpleName();
    private static CameraManager cameraManager;
    private AutoFocusManager autoFocusManager;
    private Camera camera;
    private ZxingConfig config;
    private final CameraConfigurationManager configManager;
    private final Context context;
    private Rect framingRect;
    private Rect framingRectInPreview;
    private boolean initialized;
    private final PreviewCallback previewCallback;
    private boolean previewing;
    private int requestedCameraId = -1;
    private int requestedFramingRectHeight;
    private int requestedFramingRectWidth;

    public CameraManager(Context context2, ZxingConfig zxingConfig) {
        this.context = context2;
        this.configManager = new CameraConfigurationManager(context2);
        this.previewCallback = new PreviewCallback(this.configManager);
        this.config = zxingConfig;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0054 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x0083 */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0073  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void openDriver(android.view.SurfaceHolder r5) throws java.io.IOException {
        /*
            r4 = this;
            monitor-enter(r4)
            android.hardware.Camera r0 = r4.camera     // Catch:{ all -> 0x008c }
            if (r0 != 0) goto L_0x001f
            int r0 = r4.requestedCameraId     // Catch:{ all -> 0x008c }
            if (r0 < 0) goto L_0x0010
            int r0 = r4.requestedCameraId     // Catch:{ all -> 0x008c }
            android.hardware.Camera r0 = com.yzq.zxinglibrary.camera.OpenCameraInterface.open(r0)     // Catch:{ all -> 0x008c }
            goto L_0x0014
        L_0x0010:
            android.hardware.Camera r0 = com.yzq.zxinglibrary.camera.OpenCameraInterface.open()     // Catch:{ all -> 0x008c }
        L_0x0014:
            if (r0 == 0) goto L_0x0019
            r4.camera = r0     // Catch:{ all -> 0x008c }
            goto L_0x001f
        L_0x0019:
            java.io.IOException r5 = new java.io.IOException     // Catch:{ all -> 0x008c }
            r5.<init>()     // Catch:{ all -> 0x008c }
            throw r5     // Catch:{ all -> 0x008c }
        L_0x001f:
            r0.setPreviewDisplay(r5)     // Catch:{ all -> 0x008c }
            boolean r5 = r4.initialized     // Catch:{ all -> 0x008c }
            if (r5 != 0) goto L_0x0042
            r5 = 1
            r4.initialized = r5     // Catch:{ all -> 0x008c }
            com.yzq.zxinglibrary.camera.CameraConfigurationManager r5 = r4.configManager     // Catch:{ all -> 0x008c }
            r5.initFromCameraParameters(r0)     // Catch:{ all -> 0x008c }
            int r5 = r4.requestedFramingRectWidth     // Catch:{ all -> 0x008c }
            if (r5 <= 0) goto L_0x0042
            int r5 = r4.requestedFramingRectHeight     // Catch:{ all -> 0x008c }
            if (r5 <= 0) goto L_0x0042
            int r5 = r4.requestedFramingRectWidth     // Catch:{ all -> 0x008c }
            int r1 = r4.requestedFramingRectHeight     // Catch:{ all -> 0x008c }
            r4.setManualFramingRect(r5, r1)     // Catch:{ all -> 0x008c }
            r5 = 0
            r4.requestedFramingRectWidth = r5     // Catch:{ all -> 0x008c }
            r4.requestedFramingRectHeight = r5     // Catch:{ all -> 0x008c }
        L_0x0042:
            android.hardware.Camera$Parameters r5 = r0.getParameters()     // Catch:{ all -> 0x008c }
            if (r5 != 0) goto L_0x004a
            r5 = 0
            goto L_0x004e
        L_0x004a:
            java.lang.String r5 = r5.flatten()     // Catch:{ all -> 0x008c }
        L_0x004e:
            com.yzq.zxinglibrary.camera.CameraConfigurationManager r1 = r4.configManager     // Catch:{ RuntimeException -> 0x0054 }
            r1.setDesiredCameraParameters(r0)     // Catch:{ RuntimeException -> 0x0054 }
            goto L_0x008a
        L_0x0054:
            java.lang.String r1 = TAG     // Catch:{ all -> 0x008c }
            java.lang.String r2 = "Camera rejected parameters. Setting only minimal safe-mode parameters"
            android.util.Log.w(r1, r2)     // Catch:{ all -> 0x008c }
            java.lang.String r1 = TAG     // Catch:{ all -> 0x008c }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x008c }
            r2.<init>()     // Catch:{ all -> 0x008c }
            java.lang.String r3 = "Resetting to saved camera params: "
            r2.append(r3)     // Catch:{ all -> 0x008c }
            r2.append(r5)     // Catch:{ all -> 0x008c }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x008c }
            android.util.Log.i(r1, r2)     // Catch:{ all -> 0x008c }
            if (r5 == 0) goto L_0x008a
            android.hardware.Camera$Parameters r1 = r0.getParameters()     // Catch:{ all -> 0x008c }
            r1.unflatten(r5)     // Catch:{ all -> 0x008c }
            r0.setParameters(r1)     // Catch:{ RuntimeException -> 0x0083 }
            com.yzq.zxinglibrary.camera.CameraConfigurationManager r5 = r4.configManager     // Catch:{ RuntimeException -> 0x0083 }
            r5.setDesiredCameraParameters(r0)     // Catch:{ RuntimeException -> 0x0083 }
            goto L_0x008a
        L_0x0083:
            java.lang.String r5 = TAG     // Catch:{ all -> 0x008c }
            java.lang.String r0 = "Camera rejected even safe-mode parameters! No configuration"
            android.util.Log.w(r5, r0)     // Catch:{ all -> 0x008c }
        L_0x008a:
            monitor-exit(r4)
            return
        L_0x008c:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yzq.zxinglibrary.camera.CameraManager.openDriver(android.view.SurfaceHolder):void");
    }

    public synchronized boolean isOpen() {
        return this.camera != null;
    }

    public synchronized void closeDriver() {
        if (this.camera != null) {
            this.camera.release();
            this.camera = null;
            this.framingRect = null;
            this.framingRectInPreview = null;
        }
    }

    public void switchFlashLight(CaptureActivityHandler captureActivityHandler) {
        Camera.Parameters parameters = this.camera.getParameters();
        Message message = new Message();
        if (parameters.getFlashMode().equals("torch")) {
            parameters.setFlashMode("off");
            message.what = 9;
        } else {
            parameters.setFlashMode("torch");
            message.what = 8;
        }
        this.camera.setParameters(parameters);
        captureActivityHandler.sendMessage(message);
    }

    public synchronized void startPreview() {
        Camera camera2 = this.camera;
        if (camera2 != null && !this.previewing) {
            camera2.startPreview();
            this.previewing = true;
            this.autoFocusManager = new AutoFocusManager(this.camera);
        }
    }

    public synchronized void stopPreview() {
        if (this.autoFocusManager != null) {
            this.autoFocusManager.stop();
            this.autoFocusManager = null;
        }
        if (this.camera != null && this.previewing) {
            this.camera.stopPreview();
            this.previewCallback.setHandler((Handler) null, 0);
            this.previewing = false;
        }
    }

    public synchronized void requestPreviewFrame(Handler handler, int i) {
        Camera camera2 = this.camera;
        if (camera2 != null && this.previewing) {
            this.previewCallback.setHandler(handler, i);
            camera2.setOneShotPreviewCallback(this.previewCallback);
        }
    }

    public synchronized Rect getFramingRect() {
        if (this.framingRect == null) {
            if (this.camera == null) {
                return null;
            }
            Point screenResolution = this.configManager.getScreenResolution();
            if (screenResolution == null) {
                return null;
            }
            double d = (double) screenResolution.x;
            Double.isNaN(d);
            int i = (int) (d * 0.6d);
            int i2 = (screenResolution.x - i) / 2;
            int i3 = (screenResolution.y - i) / 5;
            this.framingRect = new Rect(i2, i3, i2 + i, i + i3);
            String str = TAG;
            Log.d(str, "Calculated framing rect: " + this.framingRect);
        }
        return this.framingRect;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0054, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized android.graphics.Rect getFramingRectInPreview() {
        /*
            r5 = this;
            monitor-enter(r5)
            android.graphics.Rect r0 = r5.framingRectInPreview     // Catch:{ all -> 0x0059 }
            if (r0 != 0) goto L_0x0055
            android.graphics.Rect r0 = r5.getFramingRect()     // Catch:{ all -> 0x0059 }
            r1 = 0
            if (r0 != 0) goto L_0x000e
            monitor-exit(r5)
            return r1
        L_0x000e:
            android.graphics.Rect r2 = new android.graphics.Rect     // Catch:{ all -> 0x0059 }
            r2.<init>(r0)     // Catch:{ all -> 0x0059 }
            com.yzq.zxinglibrary.camera.CameraConfigurationManager r0 = r5.configManager     // Catch:{ all -> 0x0059 }
            android.graphics.Point r0 = r0.getCameraResolution()     // Catch:{ all -> 0x0059 }
            com.yzq.zxinglibrary.camera.CameraConfigurationManager r3 = r5.configManager     // Catch:{ all -> 0x0059 }
            android.graphics.Point r3 = r3.getScreenResolution()     // Catch:{ all -> 0x0059 }
            if (r0 == 0) goto L_0x0053
            if (r3 != 0) goto L_0x0024
            goto L_0x0053
        L_0x0024:
            int r1 = r2.left     // Catch:{ all -> 0x0059 }
            int r4 = r0.y     // Catch:{ all -> 0x0059 }
            int r1 = r1 * r4
            int r4 = r3.x     // Catch:{ all -> 0x0059 }
            int r1 = r1 / r4
            r2.left = r1     // Catch:{ all -> 0x0059 }
            int r1 = r2.right     // Catch:{ all -> 0x0059 }
            int r4 = r0.y     // Catch:{ all -> 0x0059 }
            int r1 = r1 * r4
            int r4 = r3.x     // Catch:{ all -> 0x0059 }
            int r1 = r1 / r4
            r2.right = r1     // Catch:{ all -> 0x0059 }
            int r1 = r2.top     // Catch:{ all -> 0x0059 }
            int r4 = r0.x     // Catch:{ all -> 0x0059 }
            int r1 = r1 * r4
            int r4 = r3.y     // Catch:{ all -> 0x0059 }
            int r1 = r1 / r4
            r2.top = r1     // Catch:{ all -> 0x0059 }
            int r1 = r2.bottom     // Catch:{ all -> 0x0059 }
            int r0 = r0.x     // Catch:{ all -> 0x0059 }
            int r1 = r1 * r0
            int r0 = r3.y     // Catch:{ all -> 0x0059 }
            int r1 = r1 / r0
            r2.bottom = r1     // Catch:{ all -> 0x0059 }
            r5.framingRectInPreview = r2     // Catch:{ all -> 0x0059 }
            goto L_0x0055
        L_0x0053:
            monitor-exit(r5)
            return r1
        L_0x0055:
            android.graphics.Rect r0 = r5.framingRectInPreview     // Catch:{ all -> 0x0059 }
            monitor-exit(r5)
            return r0
        L_0x0059:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yzq.zxinglibrary.camera.CameraManager.getFramingRectInPreview():android.graphics.Rect");
    }

    public synchronized void setManualCameraId(int i) {
        this.requestedCameraId = i;
    }

    public synchronized void setManualFramingRect(int i, int i2) {
        if (this.initialized) {
            Point screenResolution = this.configManager.getScreenResolution();
            if (i > screenResolution.x) {
                i = screenResolution.x;
            }
            if (i2 > screenResolution.y) {
                i2 = screenResolution.y;
            }
            int i3 = (screenResolution.x - i) / 2;
            int i4 = (screenResolution.y - i2) / 2;
            this.framingRect = new Rect(i3, i4, i + i3, i2 + i4);
            String str = TAG;
            Log.d(str, "Calculated manual framing rect: " + this.framingRect);
            this.framingRectInPreview = null;
        } else {
            this.requestedFramingRectWidth = i;
            this.requestedFramingRectHeight = i2;
        }
    }

    public PlanarYUVLuminanceSource buildLuminanceSource(byte[] bArr, int i, int i2) {
        Rect framingRectInPreview2 = getFramingRectInPreview();
        if (framingRectInPreview2 == null) {
            return null;
        }
        if (this.config == null) {
            this.config = new ZxingConfig();
        }
        if (this.config.isFullScreenScan()) {
            return new PlanarYUVLuminanceSource(bArr, i, i2, 0, 0, i, i2, false);
        }
        return new PlanarYUVLuminanceSource(bArr, i, i2, framingRectInPreview2.left, framingRectInPreview2.top + this.context.getResources().getDimensionPixelSize(C0598R.dimen.toolBarHeight), framingRectInPreview2.width(), framingRectInPreview2.height(), false);
    }

    public static CameraManager get() {
        return cameraManager;
    }
}
