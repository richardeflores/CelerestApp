package com.huanghuang.kangshangyiliao.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseDialog;
import com.huanghuang.kangshangyiliao.util.ViewBind;

public class ChoseSexDialog extends BaseDialog implements View.OnClickListener {
    private Context context;
    private OnOperateListener operate = new OnOperateListener() {
        public void onConfirm(int i) {
        }
    };
    @ViewBind.Bind(mo7926id = 2131230944)
    private RadioGroup rgSex;
    private String sex;

    public interface OnOperateListener {
        void onConfirm(int i);
    }

    public ChoseSexDialog(Context context2) {
        this.context = context2;
    }

    public void setOnOperateListener(OnOperateListener onOperateListener) {
        if (onOperateListener != null) {
            this.operate = onOperateListener;
        }
    }

    public void setSex(String str) {
        this.sex = str;
    }

    public void show() {
        this.dialog = new AlertDialog.Builder(this.context).create();
        this.dialog.setCancelable(false);
        this.dialog.show();
        Window window = this.dialog.getWindow();
        window.setContentView(C0418R.layout.widget_dialog_chose_sex);
        window.setBackgroundDrawableResource(17170445);
        ViewBind.bind(this, window.getDecorView());
        if ("2".equals(this.sex)) {
            ((RadioButton) this.rgSex.findViewById(C0418R.C0420id.rbFemale)).setChecked(true);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C0418R.C0420id.btCancel) {
            dismiss();
        } else if (id == C0418R.C0420id.btConfirm) {
            dismiss();
            int i = 1;
            if (this.rgSex.getCheckedRadioButtonId() == C0418R.C0420id.rbFemale) {
                i = 2;
            }
            this.operate.onConfirm(i);
        }
    }
}
