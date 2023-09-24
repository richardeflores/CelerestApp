package com.huanghuang.kangshangyiliao.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseDialog;
import com.huanghuang.kangshangyiliao.util.ViewBind;

public class ClinicNameOperateDialog extends BaseDialog implements View.OnClickListener {
    private Context context;
    private OnOperateListener listener = new OnOperateListener() {
        public void onChange() {
        }

        public void onDelete() {
        }
    };

    public interface OnOperateListener {
        void onChange();

        void onDelete();
    }

    public ClinicNameOperateDialog(Context context2) {
        this.context = context2;
    }

    public void setOnOperateListener(OnOperateListener onOperateListener) {
        if (onOperateListener != null) {
            this.listener = onOperateListener;
        }
    }

    public void show() {
        this.dialog = new AlertDialog.Builder(this.context).create();
        this.dialog.setCancelable(true);
        this.dialog.show();
        Window window = this.dialog.getWindow();
        window.setContentView(C0418R.layout.widget_dialog_clinic_name_operate);
        window.setBackgroundDrawableResource(17170445);
        window.setGravity(80);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = -1;
        attributes.height = -2;
        window.setAttributes(attributes);
        ViewBind.bind(this, window.getDecorView());
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C0418R.C0420id.btChange) {
            dismiss();
            this.listener.onChange();
        } else if (id == C0418R.C0420id.btDelete) {
            dismiss();
            this.listener.onDelete();
        }
    }
}
