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
import android.util.Log;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.base.BaseManager;
import com.huanghuang.kangshangyiliao.dao.POCTDao;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import com.huanghuang.kangshangyiliao.dao.bean.POCT;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class POCTManager implements BaseManager {
    public static final String CONNECTED = "poct_connected";
    public static final String CONNECTED_FAIL = "poct_connect_fail";
    public static final String DEVICE_AVAILABLE = "poct_device_available";
    public static final String DEVICE_STATE_OFF = "poct_device_state_off";
    public static final String DEVICE_UNAVAILABLE = "device_unavailable";
    public static final String DISCONNECTED = "poct_disconnected";
    public static final String HAND = "poct_hand";
    public static final String NOTIFY = "poct_notify";
    public static final String SET_PATIENT_INFO = "set_patient_info";
    public static final String SET_TITLE = "set_title";
    public static final String TIME = "poct_time";
    private AppBase appBase;
    private Context context;
    /* access modifiers changed from: private */
    public POCTDao dao;
    /* access modifiers changed from: private */
    public BluetoothDevice device;
    /* access modifiers changed from: private */
    public String deviceAdd;
    /* access modifiers changed from: private */
    public BluetoothGatt gatt;
    /* access modifiers changed from: private */
    public boolean isCheckDevice;
    /* access modifiers changed from: private */
    public boolean isConnect;
    private LocalBroadcastManager lbm;
    private final byte[] poctHandValue;
    private final byte[] poctMeasurementValue;
    /* access modifiers changed from: private */
    public byte[] resultByte;
    /* access modifiers changed from: private */

    /* renamed from: sb */
    public StringBuilder f92sb;
    /* access modifiers changed from: private */
    public Session session;

    private static class Loader {
        /* access modifiers changed from: private */
        public static final POCTManager INSTANCE = new POCTManager();

        private Loader() {
        }
    }

    public static POCTManager getInstance() {
        return Loader.INSTANCE;
    }

    private POCTManager() {
        this.poctHandValue = new byte[]{-91, 85, 8, 0, 32, 1, 67, 79, 78, 84, 93};
        this.poctMeasurementValue = new byte[]{-91, 85, 8, 0, 32, 33, 1, 0, 0, 0, 74};
        this.appBase = AppBase.getInstance();
        this.isConnect = false;
        this.session = Session.getInstance();
        this.isCheckDevice = false;
        this.context = this.appBase.getContext();
        this.dao = new POCTDao();
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
                    Intent intent = new Intent(POCTManager.CONNECTED);
                    intent.putExtra(Cache.DEVICE_INFO, POCTManager.this.device);
                    POCTManager.this.sendBroadcast(intent);
                    boolean unused = POCTManager.this.isConnect = true;
                    bluetoothGatt.discoverServices();
                    POCTManager.this.session.setPOCTCurrentDevice(POCTManager.this.device.getName());
                    POCTManager.this.session.setDeviceType(1);
                } else if (i2 != 0) {
                } else {
                    if (i == 133) {
                        POCTManager.this.sendBroadcast(new Intent(POCTManager.CONNECTED_FAIL));
                        boolean unused2 = POCTManager.this.isConnect = false;
                        POCTManager.this.session.setPOCTCurrentDevice((String) null);
                        POCTManager.this.session.setDeviceType(-1);
                        return;
                    }
                    bluetoothGatt.close();
                    boolean unused3 = POCTManager.this.isConnect = false;
                    POCTManager.this.session.setPOCTCurrentDevice((String) null);
                    POCTManager.this.session.setDeviceType(-1);
                    POCTManager.this.sendBroadcast(new Intent(POCTManager.DEVICE_STATE_OFF));
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
                byte pOCTFrameType = Utils.getPOCTFrameType(value);
                PrintStream printStream = System.out;
                printStream.println("POCTvaluevalue::" + Utils.byteArray2HexString(value));
                if (pOCTFrameType == 1) {
                    POCTManager.this.sendBroadcast(new Intent(POCTManager.HAND));
                } else if (pOCTFrameType == 2) {
                    POCTManager.this.sendBroadcast(new Intent(POCTManager.TIME));
                } else if (pOCTFrameType == 5) {
                    POCTManager.this.sendBroadcast(new Intent("set_title"));
                } else if (pOCTFrameType == 6) {
                    POCTManager.this.sendBroadcast(new Intent("set_patient_info"));
                } else if (pOCTFrameType != 33) {
                    if (pOCTFrameType == 36) {
                        byte[] unused = POCTManager.this.resultByte = Arrays.copyOf(value, value[2] + 3);
                        StringBuilder unused2 = POCTManager.this.f92sb = new StringBuilder();
                        POCTManager.this.f92sb.append(Utils.byteArray2HexString(value));
                    } else if (POCTManager.this.f92sb != null) {
                        POCTManager.this.f92sb.append(Utils.byteArray2HexString(value));
                        if (POCTManager.this.f92sb.toString().endsWith("a55508002001434f4e545d")) {
                            StringBuilder unused3 = POCTManager.this.f92sb = new StringBuilder();
                            POCTManager.this.sendBroadcast(new Intent(POCTManager.HAND));
                        } else if (!POCTManager.this.f92sb.toString().startsWith("a55544002024")) {
                            StringBuilder unused4 = POCTManager.this.f92sb = new StringBuilder();
                        } else if (value.length < 20) {
                            String sb = POCTManager.this.f92sb.toString();
                            ClinicName clinicInfo = Utils.getClinicInfo();
                            POCT poct = new POCT();
                            poct.deviceName = POCTManager.this.device.getName();
                            poct.deviceAdd = POCTManager.this.deviceAdd;
                            poct.createDate = Utils.getDate();
                            poct.data = sb;
                            poct.nickname = Utils.getNickName();
                            if (clinicInfo != null) {
                                poct.clinicName = clinicInfo.f77id + "";
                                POCTManager.this.dao.save(poct);
                            }
                            Intent intent = new Intent();
                            intent.setAction(POCTManager.NOTIFY);
                            intent.putExtra(POCTDao.table, poct);
                            POCTManager.this.sendBroadcast(intent);
                            StringBuilder unused5 = POCTManager.this.f92sb = null;
                        }
                    }
                }
            }

            public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
                super.onServicesDiscovered(bluetoothGatt, i);
                if (i == 0) {
                    POCTManager.this.setNotification(bluetoothGatt);
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
                if (POCTManager.this.isCheckDevice) {
                    POCTManager.this.sendBroadcast(new Intent("device_unavailable"));
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

    /* access modifiers changed from: private */
    public void writeData(BluetoothGatt bluetoothGatt, byte[] bArr) {
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
        this.f92sb = new StringBuilder();
        writeData(this.gatt, this.poctMeasurementValue);
    }

    public void postHand() {
        writeData(this.gatt, this.poctHandValue);
    }

    /* access modifiers changed from: private */
    public void sendBroadcast(Intent intent) {
        this.lbm.sendBroadcast(intent);
    }

    public boolean isConnect() {
        return this.isConnect;
    }

    public void setTime() {
        writeData(this.gatt, Utils.getPOCTTimeFrame());
    }

    public void setTitle(String str) {
        byte[] printTitle = Utils.setPrintTitle(str);
        int i = 1;
        int[] iArr = {printTitle.length};
        int i2 = 20;
        if (iArr[0] <= 20) {
            writeData(this.gatt, printTitle);
            return;
        }
        int[] iArr2 = {0};
        int[] iArr3 = {0};
        byte[] bArr = new byte[20];
        while (iArr[0] >= i2) {
            if (iArr3[0] == 0) {
                iArr3[0] = i;
                for (int i3 = 0; i3 < i2; i3++) {
                    bArr[i3] = printTitle[(iArr2[0] * 20) + i3];
                }
                final byte[] bArr2 = bArr;
                C05653 r11 = r0;
                final int[] iArr4 = iArr;
                final int[] iArr5 = iArr2;
                final int[] iArr6 = iArr3;
                C05653 r0 = new TimerTask() {
                    public void run() {
                        POCTManager pOCTManager = POCTManager.this;
                        pOCTManager.writeData(pOCTManager.gatt, bArr2);
                        Log.d("zdc", "-------------send------------- ");
                        int[] iArr = iArr4;
                        iArr[0] = iArr[0] - 20;
                        int[] iArr2 = iArr5;
                        iArr2[0] = iArr2[0] + 1;
                        iArr6[0] = 0;
                    }
                };
                new Timer().schedule(r11, 500);
                Log.d("zdc", "----start timer");
                iArr = iArr;
                i = 1;
                i2 = 20;
            }
        }
        int[] iArr7 = iArr;
        if (iArr7[0] > 0) {
            final byte[] bArr3 = new byte[iArr7[0]];
            if (iArr3[0] == 0) {
                iArr3[0] = 1;
                for (int i4 = 0; i4 < iArr7[0]; i4++) {
                    bArr3[i4] = printTitle[(iArr2[0] * 20) + i4];
                }
                final int[] iArr8 = iArr7;
                final int[] iArr9 = iArr2;
                final int[] iArr10 = iArr3;
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        POCTManager pOCTManager = POCTManager.this;
                        pOCTManager.writeData(pOCTManager.gatt, bArr3);
                        Log.d("zdc", "-------------send------------- ");
                        int[] iArr = iArr8;
                        iArr[0] = iArr[0] - 20;
                        int[] iArr2 = iArr9;
                        iArr2[0] = iArr2[0] + 1;
                        iArr10[0] = 0;
                    }
                }, 500);
                Log.d("zdc", "----start timer2");
            }
        }
    }

    public void setPatientInfo(String str, String str2, String str3, String str4) {
        byte[] patientInfomation = Utils.setPatientInfomation(str, str2, str3, str4);
        int i = 1;
        int[] iArr = {patientInfomation.length};
        int i2 = 20;
        if (iArr[0] <= 20) {
            writeData(this.gatt, patientInfomation);
            return;
        }
        int[] iArr2 = {0};
        int[] iArr3 = {0};
        byte[] bArr = new byte[20];
        while (iArr[0] >= i2) {
            if (iArr3[0] == 0) {
                iArr3[0] = i;
                for (int i3 = 0; i3 < i2; i3++) {
                    bArr[i3] = patientInfomation[(iArr2[0] * 20) + i3];
                }
                final byte[] bArr2 = bArr;
                C05675 r11 = r0;
                final int[] iArr4 = iArr;
                final int[] iArr5 = iArr2;
                final int[] iArr6 = iArr3;
                C05675 r0 = new TimerTask() {
                    public void run() {
                        POCTManager pOCTManager = POCTManager.this;
                        pOCTManager.writeData(pOCTManager.gatt, bArr2);
                        Log.d("zdc", "-------------send------------- ");
                        int[] iArr = iArr4;
                        iArr[0] = iArr[0] - 20;
                        int[] iArr2 = iArr5;
                        iArr2[0] = iArr2[0] + 1;
                        iArr6[0] = 0;
                    }
                };
                new Timer().schedule(r11, 500);
                Log.d("zdc", "----start timer");
                iArr = iArr;
                i = 1;
                i2 = 20;
            }
        }
        int[] iArr7 = iArr;
        if (iArr7[0] > 0) {
            final byte[] bArr3 = new byte[iArr7[0]];
            if (iArr3[0] == 0) {
                iArr3[0] = 1;
                for (int i4 = 0; i4 < iArr7[0]; i4++) {
                    bArr3[i4] = patientInfomation[(iArr2[0] * 20) + i4];
                }
                final int[] iArr8 = iArr7;
                final int[] iArr9 = iArr2;
                final int[] iArr10 = iArr3;
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        POCTManager pOCTManager = POCTManager.this;
                        pOCTManager.writeData(pOCTManager.gatt, bArr3);
                        Log.d("zdc", "-------------send------------- ");
                        int[] iArr = iArr8;
                        iArr[0] = iArr[0] - 20;
                        int[] iArr2 = iArr9;
                        iArr2[0] = iArr2[0] + 1;
                        iArr10[0] = 0;
                    }
                }, 500);
                Log.d("zdc", "----start timer2");
            }
        }
    }
}
