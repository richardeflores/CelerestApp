package com.huanghuang.kangshangyiliao.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.adapter.BluetoothDeviceListAdapter;
import com.huanghuang.kangshangyiliao.bean.BluetoothDeviceInfo;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import java.util.List;

public class BluetoothDeviceListDialog implements View.OnClickListener {
    private Context context;
    private List<BluetoothDeviceInfo> data;
    /* access modifiers changed from: private */
    public OnDeviceChoose deviceChoose = new OnDeviceChoose() {
        public void onChoose(AdapterView<?> adapterView, View view, int i, long j) {
        }
    };
    private AlertDialog dialog;
    @ViewBind.Bind(mo7926id = 2131230909)
    private ListView lvDevice;

    public interface OnDeviceChoose {
        void onChoose(AdapterView<?> adapterView, View view, int i, long j);
    }

    public BluetoothDeviceListDialog(Context context2, List<BluetoothDeviceInfo> list) {
        this.context = context2;
        this.data = list;
        this.dialog = new AlertDialog.Builder(context2).create();
        this.dialog.setCancelable(false);
    }

    public void show() {
        this.dialog.show();
        Window window = this.dialog.getWindow();
        window.setContentView(C0418R.layout.widget_dialog_bluetooth_device_list);
        window.setBackgroundDrawableResource(17170445);
        ViewBind.bind(this, window.getDecorView());
        this.lvDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                BluetoothDeviceListDialog.this.dismiss();
                BluetoothDeviceListDialog.this.deviceChoose.onChoose(adapterView, view, i, j);
            }
        });
        this.lvDevice.setAdapter(new BluetoothDeviceListAdapter(this.context, this.data));
    }

    public void dismiss() {
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public void onClick(View view) {
        if (view.getId() == C0418R.C0420id.btClose) {
            dismiss();
        }
    }

    public void setOnDeviceChoose(OnDeviceChoose onDeviceChoose) {
        if (onDeviceChoose != null) {
            this.deviceChoose = onDeviceChoose;
        }
    }
}
