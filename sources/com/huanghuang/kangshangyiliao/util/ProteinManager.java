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
import com.huanghuang.kangshangyiliao.dao.ProteinDao;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import com.huanghuang.kangshangyiliao.dao.bean.Protein;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ProteinManager implements BaseManager {
    public static final String CONNECTED = "protein_connected";
    public static final String CONNECTED_FAIL = "protein_connect_fail";
    public static final String DEVICE_AVAILABLE = "protein_device_available";
    public static final String DEVICE_STATE_OFF = "protein_device_state_off";
    public static final String DEVICE_UNAVAILABLE = "device_unavailable";
    public static final String DISCONNECTED = "protein_disconnected";
    public static final String HAND = "protein_hand";
    public static final String NOTIFY = "protein_notify";
    public static final String OBSERVEED = "Observed_time";
    public static final String POWER = "power";
    public static final String TIME = "protein_time";
    private AppBase appBase;
    /* access modifiers changed from: private */
    public byte[] back;
    private Context context;
    private ProteinDao dao;
    /* access modifiers changed from: private */
    public BluetoothDevice device;
    private String deviceAdd;
    /* access modifiers changed from: private */
    public byte[] front;
    private BluetoothGatt gatt;
    /* access modifiers changed from: private */
    public boolean isCheckDevice;
    /* access modifiers changed from: private */
    public boolean isConnect;
    private LocalBroadcastManager lbm;
    private final byte[] proteinHandValue;
    private final byte[] proteinMeasurementValue;
    /* access modifiers changed from: private */
    public byte[] resultByte;
    /* access modifiers changed from: private */
    public byte[] resultByte_a;

    /* renamed from: sb */
    private StringBuilder f93sb;
    /* access modifiers changed from: private */
    public Session session;

    private static class Loader {
        /* access modifiers changed from: private */
        public static final ProteinManager INSTANCE = new ProteinManager();

        private Loader() {
        }
    }

    public static ProteinManager getInstance() {
        return Loader.INSTANCE;
    }

    private ProteinManager() {
        this.proteinHandValue = new byte[]{-91, 85, 8, 0, 48, 1, 67, 79, 78, 84, 109};
        this.proteinMeasurementValue = new byte[]{-91, 85, 8, 0, 48, 33, 1, 0, 0, 0, 90};
        this.appBase = AppBase.getInstance();
        this.isConnect = false;
        this.session = Session.getInstance();
        this.isCheckDevice = false;
        this.context = this.appBase.getContext();
        this.dao = new ProteinDao();
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
                    Intent intent = new Intent(ProteinManager.CONNECTED);
                    intent.putExtra(Cache.DEVICE_INFO, ProteinManager.this.device);
                    ProteinManager.this.sendBroadcast(intent);
                    boolean unused = ProteinManager.this.isConnect = true;
                    bluetoothGatt.discoverServices();
                    ProteinManager.this.session.setProteinCurrentDevice(ProteinManager.this.device.getName());
                    ProteinManager.this.session.setDeviceType(2);
                } else if (i2 != 0) {
                } else {
                    if (i == 133) {
                        ProteinManager.this.sendBroadcast(new Intent(ProteinManager.CONNECTED_FAIL));
                        boolean unused2 = ProteinManager.this.isConnect = false;
                        ProteinManager.this.session.setProteinCurrentDevice((String) null);
                        ProteinManager.this.session.setDeviceType(-1);
                        return;
                    }
                    bluetoothGatt.close();
                    boolean unused3 = ProteinManager.this.isConnect = false;
                    ProteinManager.this.session.setProteinCurrentDevice((String) null);
                    ProteinManager.this.session.setDeviceType(-1);
                    ProteinManager.this.sendBroadcast(new Intent(ProteinManager.DEVICE_STATE_OFF));
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
                byte proteinFrameType = Utils.getProteinFrameType(value);
                PrintStream printStream = System.out;
                printStream.println("valuevalue5800:___ " + Utils.byteArray2HexString(value));
                if (value.length == 11) {
                    byte[] unused = ProteinManager.this.back = value;
                    ProteinManager proteinManager = ProteinManager.this;
                    byte[] unused2 = proteinManager.resultByte = new byte[(proteinManager.front.length + ProteinManager.this.back.length)];
                    System.arraycopy(ProteinManager.this.front, 0, ProteinManager.this.resultByte, 0, ProteinManager.this.front.length);
                    System.arraycopy(ProteinManager.this.back, 0, ProteinManager.this.resultByte, ProteinManager.this.front.length, ProteinManager.this.back.length);
                    if (ProteinManager.this.resultByte[2] == 16) {
                        ProteinManager.this.onReceiveResult();
                    }
                }
                if (proteinFrameType == 1) {
                    ProteinManager.this.sendBroadcast(new Intent(ProteinManager.HAND));
                } else if (proteinFrameType == 2) {
                    ProteinManager.this.sendBroadcast(new Intent(ProteinManager.TIME));
                } else if (proteinFrameType == 34) {
                    ProteinManager.this.sendBroadcast(new Intent(ProteinManager.POWER));
                } else if (proteinFrameType == 38) {
                    ProteinManager.this.sendBroadcast(new Intent(ProteinManager.OBSERVEED));
                } else if (proteinFrameType != 33) {
                    if (proteinFrameType == 36) {
                        byte[] unused3 = ProteinManager.this.resultByte_a = Arrays.copyOf(value, value[2] + 3);
                        byte[] unused4 = ProteinManager.this.front = value;
                        if (value[2] == 16) {
                            ProteinManager.this.onReceiveResult();
                        }
                    } else if (ProteinManager.this.resultByte_a != null) {
                        ProteinManager.this.onReceiveResult();
                    }
                }
            }

            public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
                super.onServicesDiscovered(bluetoothGatt, i);
                if (i == 0) {
                    ProteinManager.this.setNotification(bluetoothGatt);
                }
            }
        });
        this.gatt.connect();
    }

    /* access modifiers changed from: private */
    public void onReceiveResult() {
        String byteArray2HexString = Utils.byteArray2HexString(this.resultByte);
        Protein protein = new Protein();
        protein.deviceName = this.device.getName();
        protein.deviceAdd = this.deviceAdd;
        protein.data = byteArray2HexString;
        protein.nickname = Utils.getNickName();
        protein.createDate = Utils.getDate();
        Intent intent = new Intent();
        if (this.resultByte[5] == 36) {
            intent.setAction(NOTIFY);
            intent.putExtra(ProteinDao.table, protein);
            ClinicName clinicInfo = Utils.getClinicInfo();
            if (clinicInfo != null) {
                protein.clinicName = clinicInfo.f77id + "";
                this.dao.save(protein);
            }
        }
        sendBroadcast(intent);
        this.resultByte = null;
    }

    /* access modifiers changed from: private */
    public void setNotification(BluetoothGatt bluetoothGatt) {
        List<BluetoothGattDescriptor> descriptors;
        new Timer().schedule(new TimerTask() {
            public void run() {
                if (ProteinManager.this.isCheckDevice) {
                    ProteinManager.this.sendBroadcast(new Intent("device_unavailable"));
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

    public void measurement() {
        this.f93sb = new StringBuilder();
        writeData(this.gatt, this.proteinMeasurementValue);
    }

    public void postHand() {
        writeData(this.gatt, this.proteinHandValue);
    }

    public static String hex(int i) {
        return String.format("%04x", new Object[]{Integer.valueOf(i)});
    }

    public void postPowerData(String str) {
        int parseFloat = (int) (Float.parseFloat(str) * 10.0f);
        String hexString = Integer.toHexString(parseFloat & 255);
        String hex = hex(parseFloat);
        PrintStream printStream = System.out;
        printStream.println("hex: hex  " + hex);
        byte b = 0;
        String substring = hex.substring(0, 2);
        String substring2 = hex.substring(2);
        int parseInt = Integer.parseInt(substring, 16);
        int parseInt2 = Integer.parseInt(substring2, 16);
        PrintStream printStream2 = System.out;
        printStream2.println("substring1:" + substring);
        PrintStream printStream3 = System.out;
        printStream3.println("substring2:" + substring2);
        PrintStream printStream4 = System.out;
        printStream4.println("substring1: byte" + ((byte) parseInt));
        byte[] bArr = substring.equalsIgnoreCase("00") ? new byte[]{-91, 85, 8, 0, 48, 34, 2, 37, 0, (byte) (parseInt2 & 255)} : new byte[]{-91, 85, 8, 0, 48, 34, 2, 37, (byte) (parseInt & 255), (byte) (parseInt2 & 255)};
        PrintStream printStream5 = System.out;
        printStream5.println("checkSum: sixteen  " + hexString);
        for (int i = 2; i < bArr.length; i++) {
            b = (byte) (b + bArr[i]);
            PrintStream printStream6 = System.out;
            printStream6.println("bytes1 checkSum--- " + b);
        }
        byte[] insertElement = insertElement(bArr, b, bArr.length);
        PrintStream printStream7 = System.out;
        printStream7.println("proteinMeasurementValue22:  " + Arrays.toString(insertElement));
        writeData(this.gatt, insertElement);
    }

    private static byte[] insertElement(byte[] bArr, byte b, int i) {
        int length = bArr.length;
        byte[] bArr2 = new byte[(length + 1)];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        bArr2[i] = b;
        System.arraycopy(bArr, i, bArr2, i + 1, length - i);
        return bArr2;
    }

    /* access modifiers changed from: private */
    public void sendBroadcast(Intent intent) {
        this.lbm.sendBroadcast(intent);
    }

    public boolean isConnect() {
        return this.isConnect;
    }

    public void setTime() {
        byte[] proteinTimeFrame = Utils.getProteinTimeFrame();
        PrintStream printStream = System.out;
        printStream.println("valuevalue58:发出去的___" + Utils.byteArray2HexString(proteinTimeFrame));
        writeData(this.gatt, proteinTimeFrame);
    }

    public void setSend(String str) {
        writeData(this.gatt, Utils.getObserved(Integer.parseInt(str)));
    }
}
