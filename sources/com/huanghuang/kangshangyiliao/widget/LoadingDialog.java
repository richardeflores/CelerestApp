package com.huanghuang.kangshangyiliao.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.app.utils.AppUtils;

public class LoadingDialog {
    private AlertDialog dialog;
    public boolean interceptBack = false;
    public boolean interceptHome = false;
    private String text;

    public LoadingDialog(Context context) {
        this.dialog = new AlertDialog.Builder(context).create();
        this.dialog.setCancelable(false);
    }

    public void show() {
        this.dialog.show();
        boolean z = this.interceptHome;
        Window window = this.dialog.getWindow();
        window.setDimAmount(0.0f);
        window.setContentView(C0418R.layout.widget_dialog_loading);
        window.setBackgroundDrawableResource(17170445);
        TextView textView = (TextView) this.dialog.findViewById(C0418R.C0420id.tvLoading);
        if (!TextUtils.isEmpty(this.text)) {
            textView.setText(this.text);
        }
        this.dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == 4 && keyEvent.getRepeatCount() == 0) {
                    if (LoadingDialog.this.interceptBack) {
                        return true;
                    }
                    LoadingDialog.this.dismiss();
                }
                if (i != 3 || !LoadingDialog.this.interceptHome) {
                    return false;
                }
                return true;
            }
        });
    }

    public void setLoadingText(String str) {
        this.text = str;
    }

    public void setLoadingText(int i) {
        this.text = AppUtils.getString(i);
    }

    public void dismiss() {
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public void setMissing() {
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.dialog.dismiss();
        }
    }
}
