package com.yzq.zxinglibrary.decode;

import android.os.Handler;
import android.os.Looper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPointCallback;
import com.yzq.zxinglibrary.android.CaptureActivity;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

public final class DecodeThread extends Thread {
    private final CaptureActivity activity;
    private final Vector<BarcodeFormat> decodeFormats = new Vector<>();
    private Handler handler;
    private final CountDownLatch handlerInitLatch = new CountDownLatch(1);
    private final Hashtable<DecodeHintType, Object> hints = new Hashtable<>();

    public DecodeThread(CaptureActivity captureActivity, ResultPointCallback resultPointCallback) {
        this.activity = captureActivity;
        if (captureActivity.config.isDecodeBarCode()) {
            this.decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
        }
        this.decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
        this.decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
        this.hints.put(DecodeHintType.POSSIBLE_FORMATS, this.decodeFormats);
        this.hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        this.hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, resultPointCallback);
    }

    public Handler getHandler() {
        try {
            this.handlerInitLatch.await();
        } catch (InterruptedException unused) {
        }
        return this.handler;
    }

    public void run() {
        Looper.prepare();
        this.handler = new DecodeHandler(this.activity, this.hints);
        this.handlerInitLatch.countDown();
        Looper.loop();
    }
}
