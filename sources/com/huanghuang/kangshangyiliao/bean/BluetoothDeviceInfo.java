package com.huanghuang.kangshangyiliao.bean;

import android.bluetooth.BluetoothDevice;
import java.io.Serializable;

public class BluetoothDeviceInfo implements Serializable {
    private static final long serialVersionUID = 4678981812128816308L;
    public BluetoothDevice device;
    public int rssi;

    public BluetoothDeviceInfo() {
    }

    public BluetoothDeviceInfo(BluetoothDevice bluetoothDevice, int i) {
        this.device = bluetoothDevice;
        this.rssi = i;
    }
}
