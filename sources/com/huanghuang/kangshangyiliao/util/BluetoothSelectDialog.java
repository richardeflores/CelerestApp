package com.huanghuang.kangshangyiliao.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.huanghuang.kangshangyiliao.C0418R;

public class BluetoothSelectDialog {
    private Button button_allow;
    private Button button_refuse;
    /* access modifiers changed from: private */
    public Dialog dialog;
    private Context mContext;
    /* access modifiers changed from: private */
    public OnAllowListener onAllowListener;

    public interface OnAllowListener {
        void click();
    }

    public BluetoothSelectDialog(Context context) {
        this.mContext = context;
    }

    public void ShowDialog() {
        this.dialog = new Dialog(this.mContext);
        this.dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
            }
        });
        this.dialog.requestWindowFeature(1);
        Window window = this.dialog.getWindow();
        window.setGravity(80);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = -1;
        attributes.height = -2;
        window.setAttributes(attributes);
        window.setBackgroundDrawableResource(17170443);
        View inflate = LayoutInflater.from(this.mContext).inflate(C0418R.layout.duoqu_webview_img_select_dialog, (ViewGroup) null);
        this.button_refuse = (Button) inflate.findViewById(C0418R.C0420id.Button_BlueTooth_Refuse);
        this.button_allow = (Button) inflate.findViewById(C0418R.C0420id.Button_BlueTooth_allow);
        this.button_allow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BluetoothSelectDialog.this.onAllowListener.click();
                BluetoothSelectDialog.this.dialog.dismiss();
            }
        });
        this.button_refuse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BluetoothSelectDialog.this.dialog.dismiss();
            }
        });
        this.dialog.setContentView(inflate);
        this.dialog.show();
    }

    public void setOnAllowListener(OnAllowListener onAllowListener2) {
        this.onAllowListener = onAllowListener2;
    }
}
