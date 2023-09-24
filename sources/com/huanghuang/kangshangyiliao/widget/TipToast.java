package com.huanghuang.kangshangyiliao.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.app.utils.AppUtils;

public class TipToast {
    private Context context;
    private int imgId;
    private String text;

    public TipToast(Context context2) {
        this.context = context2;
    }

    public void show() {
        View inflate = LayoutInflater.from(this.context).inflate(C0418R.layout.widget_toast_tip, (ViewGroup) null);
        if (this.imgId > 0) {
            ((ImageView) inflate.findViewById(C0418R.C0420id.ivTip)).setImageResource(this.imgId);
        }
        if (!TextUtils.isEmpty(this.text)) {
            ((TextView) inflate.findViewById(C0418R.C0420id.tvTip)).setText(this.text);
        }
        Toast toast = new Toast(this.context);
        toast.setGravity(17, 0, 0);
        toast.setDuration(0);
        toast.setView(inflate);
        toast.show();
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
