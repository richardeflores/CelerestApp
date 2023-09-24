package com.huanghuang.kangshangyiliao.base;

import android.app.AlertDialog;

public abstract class BaseDialog {
    protected AlertDialog dialog;

    public abstract void show();

    public void dismiss() {
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }
}
