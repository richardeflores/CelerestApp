package com.huanghuang.kangshangyiliao.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;
import com.huanghuang.kangshangyiliao.C0418R;

public class DoubleClickExitHelper {
    /* access modifiers changed from: private */
    public boolean isOnKeyBacking;
    private final Activity mActivity;
    /* access modifiers changed from: private */
    public Toast mBackToast;
    private Handler mHandler;
    private Runnable onBackTimeRunnable = new Runnable() {
        public void run() {
            boolean unused = DoubleClickExitHelper.this.isOnKeyBacking = false;
            if (DoubleClickExitHelper.this.mBackToast != null) {
                DoubleClickExitHelper.this.mBackToast.cancel();
            }
        }
    };
    private OnExitListener onExitListener;

    public interface OnExitListener {
        void onExit();
    }

    public DoubleClickExitHelper(Activity activity) {
        this.mActivity = activity;
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        if (this.isOnKeyBacking) {
            this.mHandler.removeCallbacks(this.onBackTimeRunnable);
            Toast toast = this.mBackToast;
            if (toast != null) {
                toast.cancel();
            }
            OnExitListener onExitListener2 = this.onExitListener;
            if (onExitListener2 != null) {
                onExitListener2.onExit();
            }
            return true;
        }
        this.isOnKeyBacking = true;
        if (this.mBackToast == null) {
            Activity activity = this.mActivity;
            this.mBackToast = Toast.makeText(activity, activity.getResources().getString(C0418R.string.exit_double_click), 0);
        }
        this.mBackToast.show();
        this.mHandler.postDelayed(this.onBackTimeRunnable, 2000);
        return true;
    }

    public void setOnExitListener(OnExitListener onExitListener2) {
        this.onExitListener = onExitListener2;
    }
}
