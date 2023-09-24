package com.huanghuang.kangshangyiliao.dayiji;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;

public class EnviromentUtil {
    public static boolean isBluetoothHardwareOk() {
        return BluetoothAdapter.getDefaultAdapter() != null;
    }

    public static boolean isBluetoothOpen() {
        return isBluetoothHardwareOk() && BluetoothAdapter.getDefaultAdapter().isEnabled();
    }

    public static void openBluetooth(Activity activity) {
        if (!isBluetoothOpen()) {
            activity.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 2);
        }
    }

    public static boolean isEmulator() {
        return Build.MODEL.equals("sdk") || Build.MODEL.equals("google_sdk");
    }

    public static void finishApp() {
        DeviceBean.getInstance().clear();
        SettingBean.getInstance().clear();
    }
}
