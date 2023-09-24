package com.huanghuang.kangshangyiliao.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.app.utils.AppUtils;
import com.huanghuang.kangshangyiliao.base.BaseDialog;

public class TipToastDialog extends BaseDialog {
    Context context;
    private int imgId;
    private String text;

    public TipToastDialog(Context context2) {
        this.dialog = new AlertDialog.Builder(context2).create();
        this.dialog.setCancelable(false);
    }

    public void show() {
        this.dialog.show();
        Window window = this.dialog.getWindow();
        window.setDimAmount(0.0f);
        window.setContentView(C0418R.layout.widget_toast_tip);
        window.setBackgroundDrawableResource(17170445);
        if (this.imgId > 0) {
            ((ImageView) window.findViewById(C0418R.C0420id.ivTip)).setImageResource(this.imgId);
        }
        if (!TextUtils.isEmpty(this.text)) {
            ((TextView) window.findViewById(C0418R.C0420id.tvTip)).setText(this.text);
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                TipToastDialog.this.dismiss();
            }
        }, 500);
    }

    public void setText(String str) {
        this.text = str;
    }

    public void setText(int i) {
        this.text = AppUtils.getString(i);
    }

    public void setImg(int i) {
        this.imgId = i;
    }
}
