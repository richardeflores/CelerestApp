package com.huanghuang.kangshangyiliao.dayiji;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.zxing.common.StringUtils;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import java.io.IOException;

public class BluetoothPrint extends BaseActivity implements View.OnClickListener {
    private static int DIALOG_EXIT;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Bundle extras;
            if (BluetoothPrintService.SERVICE_ACTION.equals(intent.getAction()) && (extras = intent.getExtras()) != null) {
                DeviceBean instance = DeviceBean.getInstance();
                String string = extras.getString(BluetoothPrintService.DEVICE_NAME);
                if (string != null) {
                    instance.setConnectState(C0418R.string.msg_info_003);
                    instance.setDeviceNm(string);
                    Toast.makeText(BluetoothPrint.this.getApplicationContext(), context.getString(C0418R.string.msg_info_004), 0).show();
                }
                int i = extras.getInt(BluetoothPrintService.STATE_STATUS);
                if (i == 1 || i == 2) {
                    instance.setConnectState(C0418R.string.msg_info_001);
                } else if (i == 3) {
                    instance.setConnectState(C0418R.string.msg_info_002);
                }
                BluetoothPrint.this.setTitle(instance.getDeviceState(context));
            }
        }
    };

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_printer);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_printer));
        ViewBind.bind((Activity) this);
        if (!EnviromentUtil.isEmulator()) {
            registerReceiver(this.mReceiver, new IntentFilter(BluetoothPrintService.SERVICE_ACTION));
            isSupportBluetooth();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case C0418R.C0420id.btPrinter:
                startActivityForResult(new Intent(this, PrinterActivity.class), 1);
                return;
            case C0418R.C0420id.btPrinter_sta:
                sendPrintFile("     我今天要打印超级打印机      \n     天苍苍野茫茫     \n     风吹草低见牛羊     \n\n\n");
                return;
            case C0418R.C0420id.ivClose:
                finish();
                return;
            default:
                return;
        }
    }

    public void sendPrintFile(String str) {
        try {
            byte[] bytes = str.getBytes(StringUtils.GB2312);
            Intent intent = new Intent(this, BluetoothPrintService.class);
            Bundle bundle = new Bundle();
            bundle.putInt(BluetoothPrintService.INTENT_OP, 2);
            bundle.putByteArray(BluetoothPrintService.INTENT_PRINT, bytes);
            intent.putExtras(bundle);
            startService(intent);
        } catch (IOException unused) {
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        if (!EnviromentUtil.isEmulator()) {
            EnviromentUtil.openBluetooth(this);
        }
    }

    public synchronized void onResume() {
        super.onResume();
    }

    public synchronized void onPause() {
        super.onPause();
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
        EnviromentUtil.finishApp();
        if (!EnviromentUtil.isEmulator()) {
            stopService(new Intent(this, BluetoothPrintService.class));
            unregisterReceiver(this.mReceiver);
        }
    }

    public void onBackPressed() {
        showDialog(DIALOG_EXIT);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if ((i == 2 || i == 3) && i2 != -1) {
            Toast.makeText(this, C0418R.string.bt_not_enabled_leaving, 0).show();
            finish();
        }
    }

    private void isSupportBluetooth() {
        if (!EnviromentUtil.isBluetoothHardwareOk()) {
            Toast.makeText(this, C0418R.string.msg_err_001, 1).show();
            finish();
        }
    }
}
