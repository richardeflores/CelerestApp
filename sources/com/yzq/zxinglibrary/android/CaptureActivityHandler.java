package com.yzq.zxinglibrary.android;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.google.zxing.Result;
import com.yzq.zxinglibrary.camera.CameraManager;
import com.yzq.zxinglibrary.decode.DecodeThread;
import com.yzq.zxinglibrary.view.ViewfinderResultPointCallback;

public final class CaptureActivityHandler extends Handler {
    private static final String TAG = CaptureActivityHandler.class.getSimpleName();
    private final CaptureActivity activity;
    private final CameraManager cameraManager;
    private final DecodeThread decodeThread;
    private State state = State.SUCCESS;

    private enum State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    public CaptureActivityHandler(CaptureActivity captureActivity, CameraManager cameraManager2) {
        this.activity = captureActivity;
        this.decodeThread = new DecodeThread(captureActivity, new ViewfinderResultPointCallback(captureActivity.getViewfinderView()));
        this.decodeThread.start();
        this.cameraManager = cameraManager2;
        cameraManager2.startPreview();
        restartPreviewAndDecode();
    }

    public void handleMessage(Message message) {
        int i = message.what;
        if (i == 2) {
            this.state = State.PREVIEW;
            this.cameraManager.requestPreviewFrame(this.decodeThread.getHandler(), 1);
        } else if (i != 3) {
            switch (i) {
                case 6:
                    restartPreviewAndDecode();
                    return;
                case 7:
                    this.activity.setResult(-1, (Intent) message.obj);
                    this.activity.finish();
                    return;
                case 8:
                    this.activity.switchFlashImg(8);
                    return;
                case 9:
                    this.activity.switchFlashImg(9);
                    return;
                default:
                    return;
            }
        } else {
            this.state = State.SUCCESS;
            this.activity.handleDecode((Result) message.obj);
        }
    }

    public void quitSynchronously() {
        this.state = State.DONE;
        this.cameraManager.stopPreview();
        Message.obtain(this.decodeThread.getHandler(), 5).sendToTarget();
        try {
            this.decodeThread.join(500);
        } catch (InterruptedException unused) {
        }
        removeMessages(3);
        removeMessages(2);
    }

    public void restartPreviewAndDecode() {
        if (this.state == State.SUCCESS) {
            this.state = State.PREVIEW;
            this.cameraManager.requestPreviewFrame(this.decodeThread.getHandler(), 1);
            this.activity.drawViewfinder();
        }
    }
}
