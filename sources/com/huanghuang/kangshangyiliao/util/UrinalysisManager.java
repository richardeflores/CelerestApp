package com.huanghuang.kangshangyiliao.util;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.p000v4.content.LocalBroadcastManager;
import com.google.code.microlog4android.appender.SyslogMessage;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.base.BaseManager;
import com.huanghuang.kangshangyiliao.dao.UrinalysisDao;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import com.huanghuang.kangshangyiliao.dao.bean.Urinalysis;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class UrinalysisManager implements BaseManager {
    public static final String CONNECTED = "urinalysis_connected";
    public static final String CONNECTED_FAIL = "urinalysis_connect_fail";
    public static final String DEVICE_AVAILABLE = "device_available";
    public static final String DEVICE_STATE_OFF = "urinalysis_device_state_off";
    public static final String DEVICE_UNAVAILABLE = "device_unavailable";
    public static final String DISCONNECTED = "urinalysis_disconnected";
    public static final String HAND = "urinalysis_hand";
    public static final String NOTIFY = "urinalysis_notify";
    public static final String QUERY = "urinalysis_query";
    public static final String TIME = "urinalysis_time";
    private AppBase appBase;
    private Cache cache;
    private Context context;
    private UrinalysisDao dao;
    /* access modifiers changed from: private */
    public BluetoothDevice device;
    private String deviceAdd;
    private BluetoothGatt gatt;
    /* access modifiers changed from: private */
    public boolean isCheckDevice;
    /* access modifiers changed from: private */
    public boolean isConnect;
    private boolean isQuery;
    private LocalBroadcastManager lbm;
    /* access modifiers changed from: private */
    public byte[] resultByte;
    /* access modifiers changed from: private */
    public Session session;
    private final byte[] urineHandValue;
    private final byte[] urineResultQueryValue;

    private static class Loader {
        /* access modifiers changed from: private */
        public static final UrinalysisManager INSTANCE = new UrinalysisManager();

        private Loader() {
        }
    }

    public static UrinalysisManager getInstance() {
        return Loader.INSTANCE;
    }

    private UrinalysisManager() {
        this.urineHandValue = new byte[]{-109, -114, 8, 0, 8, 1, 67, 79, 78, 84, 69};
        this.urineResultQueryValue = new byte[]{-109, -114, 4, 0, 8, 4, SyslogMessage.FACILITY_LOCAL_USE_0};
        this.appBase = AppBase.getInstance();
        this.cache = Cache.getInstance();
        this.isConnect = false;
        this.session = Session.getInstance();
        this.isCheckDevice = false;
        this.context = this.appBase.getContext();
        this.dao = new UrinalysisDao();
        this.lbm = LocalBroadcastManager.getInstance(this.appBase.getContext());
    }

    public void setDevice(BluetoothDevice bluetoothDevice) {
        this.device = bluetoothDevice;
        this.deviceAdd = bluetoothDevice.getAddress();
    }

    public void connect() {
        this.gatt = this.device.connectGatt(this.context, false, new BluetoothGattCallback() {
            public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
                super.onConnectionStateChange(bluetoothGatt, i, i2);
                if (i2 == 2) {
                    Intent intent = new Intent(UrinalysisManager.CONNECTED);
                    intent.putExtra(Cache.DEVICE_INFO, UrinalysisManager.this.device);
                    UrinalysisManager.this.sendBroadcast(intent);
                    boolean unused = UrinalysisManager.this.isConnect = true;
                    bluetoothGatt.discoverServices();
                    UrinalysisManager.this.session.setUrinalysCurrentDevice(UrinalysisManager.this.device.getName());
                    UrinalysisManager.this.session.setDeviceType(0);
                } else if (i2 != 0) {
                } else {
                    if (i == 133) {
                        UrinalysisManager.this.sendBroadcast(new Intent(UrinalysisManager.CONNECTED_FAIL));
                        boolean unused2 = UrinalysisManager.this.isConnect = false;
                        UrinalysisManager.this.session.setUrinalysCurrentDevice((String) null);
                        UrinalysisManager.this.session.setDeviceType(-1);
                        return;
                    }
                    bluetoothGatt.close();
                    boolean unused3 = UrinalysisManager.this.isConnect = false;
                    UrinalysisManager.this.session.setUrinalysCurrentDevice((String) null);
                    UrinalysisManager.this.session.setDeviceType(-1);
                    UrinalysisManager.this.sendBroadcast(new Intent(UrinalysisManager.DEVICE_STATE_OFF));
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
                byte[] value = bluetoothGattCharacteristic.getValue();
                byte urinalysisFrameType = Utils.getUrinalysisFrameType(value);
                PrintStream printStream = System.out;
                printStream.println("valuevalue:" + Arrays.toString(value));
                PrintStream printStream2 = System.out;
                printStream2.println("data:::  " + Utils.byteArray2HexString(value));
                if (urinalysisFrameType == 1) {
                    UrinalysisManager.this.sendBroadcast(new Intent(UrinalysisManager.HAND));
                } else if (urinalysisFrameType == 2) {
                    UrinalysisManager.this.sendBroadcast(new Intent(UrinalysisManager.TIME));
                } else if (urinalysisFrameType == 4 || urinalysisFrameType == 36) {
                    byte[] unused = UrinalysisManager.this.resultByte = Arrays.copyOf(value, value[2] + 3);
                    PrintStream printStream3 = System.out;
                    printStream3.println("resultByteresultByte" + Arrays.toString(UrinalysisManager.this.resultByte));
                    if (value[2] == 16) {
                        UrinalysisManager.this.onReceiveResult();
                    }
                } else if (UrinalysisManager.this.resultByte != null) {
                    System.arraycopy(value, 0, UrinalysisManager.this.resultByte, UrinalysisManager.this.resultByte.length - value.length, value.length);
                    UrinalysisManager.this.onReceiveResult();
                }
            }

            public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
                super.onServicesDiscovered(bluetoothGatt, i);
                if (i == 0) {
                    UrinalysisManager.this.setNotification(bluetoothGatt);
                }
            }
        });
        this.gatt.connect();
    }

    /* access modifiers changed from: private */
    public void onReceiveResult() {
        String byteArray2HexString = Utils.byteArray2HexString(this.resultByte);
        Urinalysis urinalysis = new Urinalysis();
        urinalysis.deviceName = this.device.getName();
        urinalysis.deviceAdd = this.deviceAdd;
        urinalysis.data = byteArray2HexString;
        urinalysis.nickname = Utils.getNickName();
        Intent intent = new Intent();
        byte[] bArr = this.resultByte;
        if (bArr[5] == 4 || this.isQuery) {
            if (this.isQuery) {
                intent.setAction(QUERY);
                intent.putExtra(UrinalysisDao.table, urinalysis);
            } else {
                intent.setAction(NOTIFY);
                intent.putExtra(UrinalysisDao.table, urinalysis);
                ClinicName clinicInfo = Utils.getClinicInfo();
                if (clinicInfo != null) {
                    urinalysis.clinicName = clinicInfo.f77id + "";
                    this.dao.save(urinalysis);
                }
            }
            this.isQuery = false;
        } else if (bArr[5] == 36) {
            intent.setAction(NOTIFY);
            this.dao.save(urinalysis);
        }
        sendBroadcast(intent);
        this.resultByte = null;
    }

    /* access modifiers changed from: private */
    public void setNotification(BluetoothGatt bluetoothGatt) {
        new Timer().schedule(new TimerTask() {
            public void run() {
                if (UrinalysisManager.this.isCheckDevice) {
                    UrinalysisManager.this.sendBroadcast(new Intent("device_unavailable"));
                }
            }
        }, 10000);
        this.isCheckDevice = true;
        BluetoothGattService service = bluetoothGatt.getService(Constants.U_NS);
        if (service == null) {
            SystemClock.sleep(500);
            if (this.isCheckDevice) {
                this.isCheckDevice = false;
                sendBroadcast(new Intent("device_unavailable"));
                return;
            }
            return;
        }
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(Constants.U_NC);
        if (characteristic == null) {
            SystemClock.sleep(500);
            if (this.isCheckDevice) {
                this.isCheckDevice = false;
                sendBroadcast(new Intent("device_unavailable"));
                return;
            }
            return;
        }
        this.isCheckDevice = false;
        bluetoothGatt.setCharacteristicNotification(characteristic, true);
        bluetoothGatt.readCharacteristic(characteristic);
        SystemClock.sleep(500);
        sendBroadcast(new Intent(DEVICE_AVAILABLE));
    }

    private void writeData(BluetoothGatt bluetoothGatt, byte[] bArr) {
        BluetoothGattService service = bluetoothGatt.getService(Constants.U_WS);
        if (service == null) {
            sendBroadcast(new Intent("device_unavailable"));
            return;
        }
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(Constants.U_WC);
        if (characteristic == null) {
            sendBroadcast(new Intent("device_unavailable"));
            return;
        }
        characteristic.setValue(bArr);
        bluetoothGatt.writeCharacteristic(characteristic);
    }

    public void disconnect() {
        BluetoothGatt bluetoothGatt = this.gatt;
        if (bluetoothGatt != null) {
            this.isConnect = false;
            bluetoothGatt.disconnect();
        }
    }

    public void query() {
        this.isQuery = true;
        writeData(this.gatt, this.urineResultQueryValue);
    }

    public void postHand() {
        writeData(this.gatt, this.urineHandValue);
    }

    /* access modifiers changed from: private */
    public void sendBroadcast(Intent intent) {
        this.lbm.sendBroadcast(intent);
    }

    public boolean isConnect() {
        return this.isConnect;
    }

    public void setTime() {
        writeData(this.gatt, Utils.getUrinalysisTimeFrame());
    }
}
