package com.huanghuang.kangshangyiliao.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseDialog;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import com.huanghuang.kangshangyiliao.widget.AskCreateNickNameDialog;

public class AskDialog extends BaseDialog implements View.OnClickListener {
    private Context context;
    private AskCreateNickNameDialog.OnOperateListener operate = new AskCreateNickNameDialog.OnOperateListener() {
        public void onConfirm() {
        }
    };
    private String text;
    @ViewBind.Bind(mo7926id = 2131231047)
    private TextView tvMsg;

    public interface OnOperateListener {
        void onConfirm();
    }

    public AskDialog(Context context2) {
        this.context = context2;
    }

    public void setText(String str) {
        this.text = str;
    }

    public void setText(int i) {
        this.text = this.context.getString(i);
    }

    public void setOnOperateListener(AskCreateNickNameDialog.OnOperateListener onOperateListener) {
        if (onOperateListener != null) {
            this.operate = onOperateListener;
        }
    }

    public void show() {
        this.dialog = new AlertDialog.Builder(this.context).create();
        this.dialog.setCancelable(false);
        this.dialog.show();
        Window window = this.dialog.getWindow();
        window.setContentView(C0418R.layout.widget_ask_dialog);
        window.setBackgroundDrawableResource(17170445);
        window.setGravity(80);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = -1;
        attributes.height = -2;
        window.setAttributes(attributes);
        ViewBind.bind(this, window.getDecorView());
        if (!TextUtils.isEmpty(this.text)) {
            this.tvMsg.setText(this.text);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C0418R.C0420id.btCancel) {
            dismiss();
        } else if (id == C0418R.C0420id.btConfirm) {
            dismiss();
            this.operate.onConfirm();
        }
    }
}
