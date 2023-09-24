package com.huanghuang.kangshangyiliao.util;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.p000v4.content.LocalBroadcastManager;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.base.BaseManager;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PrinterManager implements BaseManager {
    public static final String CONNECTED = "printer_connected";
    public static final String CONNECTED_FAIL = "printer_connect_fail";
    public static final String DEVICE_AVAILABLE = "printer_device_available";
    public static final String DEVICE_STATE_OFF = "printer_device_state_off";
    public static final String DEVICE_UNAVAILABLE = "device_unavailable";
    public static final String DISCONNECTED = "printer_disconnected";
    public static final String NOTIFY = "printer_notify";
    private AppBase appBase;
    private Context context;
    /* access modifiers changed from: private */
    public BluetoothDevice device;
    private BluetoothGatt gatt;
    /* access modifiers changed from: private */
    public boolean isCheckDevice;
    /* access modifiers changed from: private */
    public boolean isConnect;
    private LocalBroadcastManager lbm;
    /* access modifiers changed from: private */
    public Session session;

    private static class Loader {
        /* access modifiers changed from: private */
        public static final PrinterManager INSTANCE = new PrinterManager();

        private Loader() {
        }
    }

    public static PrinterManager getInstance() {
        return Loader.INSTANCE;
    }

    private PrinterManager() {
        this.appBase = AppBase.getInstance();
        this.isConnect = false;
        this.session = Session.getInstance();
        this.isCheckDevice = false;
        this.context = this.appBase.getContext();
        this.lbm = LocalBroadcastManager.getInstance(this.appBase.getContext());
    }

    public void setDevice(BluetoothDevice bluetoothDevice) {
        this.device = bluetoothDevice;
    }

    public void connect() {
        this.gatt = this.device.connectGatt(this.context, false, new BluetoothGattCallback() {
            public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
                super.onConnectionStateChange(bluetoothGatt, i, i2);
                if (i2 == 2) {
                    Intent intent = new Intent(PrinterManager.CONNECTED);
                    intent.putExtra(Cache.DEVICE_INFO, PrinterManager.this.device);
                    PrinterManager.this.sendBroadcast(intent);
                    boolean unused = PrinterManager.this.isConnect = true;
                    bluetoothGatt.discoverServices();
                    PrinterManager.this.session.setPrinterDevice(PrinterManager.this.device.getName());
                    PrinterManager.this.session.setDeviceType(5);
                } else if (i2 != 0) {
                } else {
                    if (i == 133) {
                        PrinterManager.this.sendBroadcast(new Intent(PrinterManager.CONNECTED_FAIL));
                        boolean unused2 = PrinterManager.this.isConnect = false;
                        PrinterManager.this.session.setPrinterDevice((String) null);
                        PrinterManager.this.session.setDeviceType(-1);
                        return;
                    }
                    bluetoothGatt.close();
                    boolean unused3 = PrinterManager.this.isConnect = false;
                    PrinterManager.this.session.setPrinterDevice((String) null);
                    PrinterManager.this.session.setDeviceType(-1);
                    PrinterManager.this.sendBroadcast(new Intent(PrinterManager.DEVICE_STATE_OFF));
                }
            }

            public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
                super.onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, i);
            }

            public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
                super.onCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic, i);
            }

            public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                super.onCharacteristicChanged(bluetoothGatt, bluetoothGattCharacteristic);
            }

            public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
                super.onServicesDiscovered(bluetoothGatt, i);
                if (i == 0) {
                    PrinterManager.this.setNotification(bluetoothGatt);
                }
            }
        });
        this.gatt.connect();
    }

    /* access modifiers changed from: private */
    public void setNotification(BluetoothGatt bluetoothGatt) {
        List<BluetoothGattDescriptor> descriptors;
        new Timer().schedule(new TimerTask() {
            public void run() {
                if (PrinterManager.this.isCheckDevice) {
                    PrinterManager.this.sendBroadcast(new Intent("device_unavailable"));
                }
            }
        }, 10000);
        this.isCheckDevice = true;
        BluetoothGattService service = bluetoothGatt.getService(Constants.MY_A_UUID);
        if (service == null) {
            SystemClock.sleep(500);
            if (this.isCheckDevice) {
                this.isCheckDevice = false;
                sendBroadcast(new Intent("device_unavailable"));
                return;
            }
            return;
        }
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(Constants.MY_A_UUID);
        if (characteristic == null) {
            SystemClock.sleep(500);
            if (this.isCheckDevice) {
                this.isCheckDevice = false;
                sendBroadcast(new Intent("device_unavailable"));
                return;
            }
            return;
        }
        if (bluetoothGatt.setCharacteristicNotification(characteristic, true) && (descriptors = characteristic.getDescriptors()) != null && descriptors.size() > 0) {
            for (BluetoothGattDescriptor next : descriptors) {
                next.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                bluetoothGatt.writeDescriptor(next);
            }
        }
        bluetoothGatt.readCharacteristic(characteristic);
        this.isCheckDevice = false;
        SystemClock.sleep(500);
        sendBroadcast(new Intent(DEVICE_AVAILABLE));
    }

    public void disconnect() {
        BluetoothGatt bluetoothGatt = this.gatt;
        if (bluetoothGatt != null) {
            this.isConnect = false;
            bluetoothGatt.disconnect();
        }
    }

    /* access modifiers changed from: private */
    public void sendBroadcast(Intent intent) {
        this.lbm.sendBroadcast(intent);
    }

    public boolean isConnect() {
        return this.isConnect;
    }
}
