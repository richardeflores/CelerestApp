package com.yzq.zxinglibrary.decode;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.yzq.zxinglibrary.android.CaptureActivity;
import java.util.Map;

public final class DecodeHandler extends Handler {
    private static final String TAG = DecodeHandler.class.getSimpleName();
    private final CaptureActivity activity;
    private final MultiFormatReader multiFormatReader = new MultiFormatReader();
    private boolean running = true;

    DecodeHandler(CaptureActivity captureActivity, Map<DecodeHintType, Object> map) {
        this.multiFormatReader.setHints(map);
        this.activity = captureActivity;
    }

    public void handleMessage(Message message) {
        if (this.running) {
            int i = message.what;
            if (i == 1) {
                decode((byte[]) message.obj, message.arg1, message.arg2);
            } else if (i == 5) {
                this.running = false;
                Looper.myLooper().quit();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x005f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void decode(byte[] r7, int r8, int r9) {
        /*
            r6 = this;
            int r0 = r7.length
            byte[] r0 = new byte[r0]
            r1 = 0
            r2 = 0
        L_0x0005:
            if (r2 >= r9) goto L_0x001d
            r3 = 0
        L_0x0008:
            if (r3 >= r8) goto L_0x001a
            int r4 = r3 * r9
            int r4 = r4 + r9
            int r4 = r4 - r2
            int r4 = r4 + -1
            int r5 = r2 * r8
            int r5 = r5 + r3
            byte r5 = r7[r5]
            r0[r4] = r5
            int r3 = r3 + 1
            goto L_0x0008
        L_0x001a:
            int r2 = r2 + 1
            goto L_0x0005
        L_0x001d:
            com.yzq.zxinglibrary.android.CaptureActivity r7 = r6.activity
            com.yzq.zxinglibrary.camera.CameraManager r7 = r7.getCameraManager()
            com.google.zxing.PlanarYUVLuminanceSource r7 = r7.buildLuminanceSource(r0, r9, r8)
            if (r7 == 0) goto L_0x004b
            com.google.zxing.BinaryBitmap r8 = new com.google.zxing.BinaryBitmap
            com.google.zxing.common.HybridBinarizer r9 = new com.google.zxing.common.HybridBinarizer
            r9.<init>(r7)
            r8.<init>(r9)
            com.google.zxing.MultiFormatReader r7 = r6.multiFormatReader     // Catch:{ ReaderException -> 0x0046, all -> 0x003f }
            com.google.zxing.Result r7 = r7.decodeWithState(r8)     // Catch:{ ReaderException -> 0x0046, all -> 0x003f }
            com.google.zxing.MultiFormatReader r8 = r6.multiFormatReader
            r8.reset()
            goto L_0x004c
        L_0x003f:
            r7 = move-exception
            com.google.zxing.MultiFormatReader r8 = r6.multiFormatReader
            r8.reset()
            throw r7
        L_0x0046:
            com.google.zxing.MultiFormatReader r7 = r6.multiFormatReader
            r7.reset()
        L_0x004b:
            r7 = 0
        L_0x004c:
            com.yzq.zxinglibrary.android.CaptureActivity r8 = r6.activity
            android.os.Handler r8 = r8.getHandler()
            if (r7 == 0) goto L_0x005f
            if (r8 == 0) goto L_0x0069
            r9 = 3
            android.os.Message r7 = android.os.Message.obtain(r8, r9, r7)
            r7.sendToTarget()
            goto L_0x0069
        L_0x005f:
            if (r8 == 0) goto L_0x0069
            r7 = 2
            android.os.Message r7 = android.os.Message.obtain(r8, r7)
            r7.sendToTarget()
        L_0x0069:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yzq.zxinglibrary.decode.DecodeHandler.decode(byte[], int, int):void");
    }
}
