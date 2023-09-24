package com.huanghuang.kangshangyiliao.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import com.google.code.microlog4android.format.SimpleFormatter;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseDialog;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import com.huanghuang.kangshangyiliao.view.wheel.LoopView;
import java.util.ArrayList;
import java.util.Calendar;

public class ChoseDateDialog extends BaseDialog implements View.OnClickListener {
    private Context context;
    @ViewBind.Bind(mo7926id = 2131230908)
    private LoopView lvDay;
    @ViewBind.Bind(mo7926id = 2131230910)
    private LoopView lvMonth;
    @ViewBind.Bind(mo7926id = 2131230912)
    private LoopView lvYear;
    private OnOperateListener operate = new OnOperateListener() {
        public void onConfirm(String str) {
        }
    };

    public interface OnOperateListener {
        void onConfirm(String str);
    }

    public ChoseDateDialog(Context context2) {
        this.context = context2;
    }

    public void setOnOperateListener(OnOperateListener onOperateListener) {
        if (onOperateListener != null) {
            this.operate = onOperateListener;
        }
    }

    public void show() {
        String str;
        String str2;
        this.dialog = new AlertDialog.Builder(this.context).create();
        this.dialog.setCancelable(false);
        this.dialog.show();
        Window window = this.dialog.getWindow();
        window.setContentView(C0418R.layout.widget_dialog_chose_date);
        window.setBackgroundDrawableResource(17170445);
        ViewBind.bind(this, window.getDecorView());
        Calendar instance = Calendar.getInstance();
        int i = instance.get(1);
        ArrayList arrayList = new ArrayList();
        int i2 = 75;
        for (int i3 = 1900; i3 <= 2050; i3++) {
            if (i == i3) {
                i2 = i3 - 1900;
            }
            arrayList.add("" + i3);
        }
        this.lvYear.setArrayList(arrayList);
        this.lvYear.setCurrentItem(i2);
        int i4 = instance.get(2) + 1;
        ArrayList arrayList2 = new ArrayList();
        int i5 = 6;
        int i6 = 6;
        for (int i7 = 1; i7 <= 12; i7++) {
            if (i4 == i7) {
                i6 = i7 - 1;
            }
            if (i7 < 10) {
                str2 = "" + "0";
            } else {
                str2 = "";
            }
            arrayList2.add(str2 + i7);
        }
        this.lvMonth.setArrayList(arrayList2);
        this.lvMonth.setCurrentItem(i6);
        int i8 = instance.get(5);
        ArrayList arrayList3 = new ArrayList();
        for (int i9 = 1; i9 <= 31; i9++) {
            if (i8 == i9) {
                i5 = i9 - 1;
            }
            if (i9 < 10) {
                str = "" + "0";
            } else {
                str = "";
            }
            arrayList3.add(str + i9);
        }
        this.lvDay.setArrayList(arrayList3);
        this.lvDay.setCurrentItem(i5);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C0418R.C0420id.btCancel) {
            dismiss();
        } else if (id == C0418R.C0420id.btConfirm) {
            dismiss();
            this.operate.onConfirm(this.lvYear.getCurrentItemValue() + SimpleFormatter.DEFAULT_DELIMITER + this.lvMonth.getCurrentItemValue() + SimpleFormatter.DEFAULT_DELIMITER + this.lvDay.getCurrentItemValue());
        }
    }
}
