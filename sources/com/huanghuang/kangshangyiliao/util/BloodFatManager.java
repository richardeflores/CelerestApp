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
import com.huanghuang.kangshangyiliao.bean.BloodFatResult;
import com.huanghuang.kangshangyiliao.dao.BloodFatDao;
import com.huanghuang.kangshangyiliao.dao.bean.BloodFat;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class BloodFatManager implements BaseManager {
    public static final String CONNECTED = "bloodFat_connected";
    public static final String CONNECTED_FAIL = "bloodFat_connect_fail";
    public static final String DEVICE_AVAILABLE = "bloodFat_device_available";
    public static final String DEVICE_STATE_OFF = "bloodFat_device_state_off";
    public static final String DEVICE_UNAVAILABLE = "device_unavailable";
    public static final String DISCONNECTED = "bloodFat_disconnected";
    public static final String HAND = "bloodFat_hand";
    public static final String NOTIFY = "bloodFat_notify";
    public static final String PAR_FLASH = "par_Flash";
    public static final String QUERY_UNIT = "query_unit";
    public static final String SETING_UNIT = "setting_unit";
    public static final String SET_PATIENT_INFO = "set_patient_info";
    public static final String SET_TITLE = "set_title";
    public static final String TIME = "bloodFat_time";
    private AppBase appBase;
    private final byte[] bloodFatHandValue;
    private final byte[] bloodFatMeasurementValue;
    private final byte[] bloodFatQuerying;
    private final byte[] bloodFatSetting;
    private final byte[] bloodFatSetting01;
    private final byte[] bloodFatSetting02;
    private Context context;
    /* access modifiers changed from: private */
    public BloodFatDao dao;
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
    /* access modifiers changed from: private */
    public byte[] resultByte;
    /* access modifiers changed from: private */

    /* renamed from: sb */
    public StringBuilder f89sb;
    /* access modifiers changed from: private */
    public Session session;

    private static class Loader {
        /* access modifiers changed from: private */
        public static final BloodFatManager INSTANCE = new BloodFatManager();

        private Loader() {
        }
    }

    public static BloodFatManager getInstance() {
        return Loader.INSTANCE;
    }

    private BloodFatManager() {
        this.bloodFatHandValue = new byte[]{-91, 85, 8, 0, 96, 1, 67, 79, 78, 84, -99};
        this.bloodFatMeasurementValue = new byte[]{-91, 85, 8, 0, 96, 33, 3, 0, 0, 1, -115};
        this.bloodFatSetting01 = new byte[]{-91, 85, 8, 0, 96, 34, 0, 47, 0, 1, -70};
        this.bloodFatSetting02 = new byte[]{-91, 85, 8, 0, 96, 34, 0, 47, 0, 0, -71};
        this.bloodFatQuerying = new byte[]{-91, 85, 8, 0, 96, 35, 0, 47, 0, 0, -70};
        this.appBase = AppBase.getInstance();
        this.isConnect = false;
        this.session = Session.getInstance();
        this.isCheckDevice = false;
        this.bloodFatSetting = new byte[]{-91, 85, 8, 0, 96, -1, 0, 0, 0, 0, 103};
        this.context = this.appBase.getContext();
        this.dao = new BloodFatDao();
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
                    Intent intent = new Intent(BloodFatManager.CONNECTED);
                    intent.putExtra(Cache.DEVICE_INFO, BloodFatManager.this.device);
                    BloodFatManager.this.sendBroadcast(intent);
                    boolean unused = BloodFatManager.this.isConnect = true;
                    bluetoothGatt.discoverServices();
                    BloodFatManager.this.session.setBloodFat_Device(BloodFatManager.this.device.getName());
                    BloodFatManager.this.session.setDeviceType(3);
                } else if (i2 != 0) {
                } else {
                    if (i == 133) {
                        BloodFatManager.this.sendBroadcast(new Intent(BloodFatManager.CONNECTED_FAIL));
                        boolean unused2 = BloodFatManager.this.isConnect = false;
                        BloodFatManager.this.session.setBloodFat_Device((String) null);
                        BloodFatManager.this.session.setDeviceType(-1);
                        return;
                    }
                    bluetoothGatt.close();
                    boolean unused3 = BloodFatManager.this.isConnect = false;
                    BloodFatManager.this.session.setBloodFat_Device((String) null);
                    BloodFatManager.this.session.setDeviceType(-1);
                    BloodFatManager.this.sendBroadcast(new Intent(BloodFatManager.DEVICE_STATE_OFF));
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
                byte bloodFatFrameType = Utils.getBloodFatFrameType(value);
                String byteArray2HexString = Utils.byteArray2HexString(value);
                PrintStream printStream = System.out;
                printStream.println("应答转化16进制后数据byteArray2HexString:" + byteArray2HexString);
                BloodFatResult parseBloodFatData = Utils.parseBloodFatData(value);
                if (bloodFatFrameType == 1) {
                    BloodFatManager.this.sendBroadcast(new Intent(BloodFatManager.HAND));
                } else if (bloodFatFrameType == 2) {
                    BloodFatManager.this.sendBroadcast(new Intent(BloodFatManager.TIME));
                } else if (bloodFatFrameType == 18) {
                    BloodFatManager.this.sendBroadcast(new Intent("set_patient_info"));
                } else if (bloodFatFrameType == 17) {
                    BloodFatManager.this.sendBroadcast(new Intent("set_title"));
                } else if (bloodFatFrameType == -1) {
                    Intent intent = new Intent();
                    intent.setAction(BloodFatManager.PAR_FLASH);
                    BloodFatManager.this.sendBroadcast(intent);
                } else if (bloodFatFrameType != 33) {
                    if (bloodFatFrameType == 34) {
                        String byteArray2HexString2 = Utils.byteArray2HexString(value);
                        Intent intent2 = new Intent(BloodFatManager.SETING_UNIT);
                        intent2.putExtra("hexString", byteArray2HexString2);
                        BloodFatManager.this.sendBroadcast(intent2);
                    } else if (bloodFatFrameType == 35) {
                        String byteArray2HexString3 = Utils.byteArray2HexString(value);
                        Intent intent3 = new Intent(BloodFatManager.QUERY_UNIT);
                        intent3.putExtra("hexString", byteArray2HexString3);
                        BloodFatManager.this.sendBroadcast(intent3);
                    } else if (bloodFatFrameType == 36) {
                        byte[] unused = BloodFatManager.this.resultByte = Arrays.copyOf(value, value[2] + 3);
                        StringBuilder unused2 = BloodFatManager.this.f89sb = new StringBuilder();
                        BloodFatManager.this.f89sb.append(Utils.byteArray2HexString(value));
                    } else if (BloodFatManager.this.f89sb != null) {
                        BloodFatManager.this.f89sb.append(Utils.byteArray2HexString(value));
                        if (BloodFatManager.this.f89sb.toString().endsWith("a55508006001434f4e549d")) {
                            StringBuilder unused3 = BloodFatManager.this.f89sb = new StringBuilder();
                            BloodFatManager.this.sendBroadcast(new Intent(BloodFatManager.HAND));
                        } else if (!BloodFatManager.this.f89sb.toString().startsWith("a5552c006024")) {
                            StringBuilder unused4 = BloodFatManager.this.f89sb = new StringBuilder();
                        } else if (value.length < 20) {
                            String sb = BloodFatManager.this.f89sb.toString();
                            ClinicName clinicInfo = Utils.getClinicInfo();
                            BloodFat bloodFat = new BloodFat();
                            bloodFat.deviceName = BloodFatManager.this.device.getName();
                            bloodFat.deviceAdd = BloodFatManager.this.deviceAdd;
                            bloodFat.createDate = Utils.getDate();
                            bloodFat.data = sb;
                            bloodFat.nickname = Utils.getNickName();
                            if (clinicInfo != null) {
                                bloodFat.clinicName = clinicInfo.f77id + "";
                                bloodFat.unitType = parseBloodFatData.units + "";
                                BloodFatManager.this.dao.save(bloodFat);
                            }
                            Intent intent4 = new Intent();
                            intent4.setAction(BloodFatManager.NOTIFY);
                            intent4.putExtra(BloodFatDao.table, bloodFat);
                            BloodFatManager.this.sendBroadcast(intent4);
                            StringBuilder unused5 = BloodFatManager.this.f89sb = null;
                        }
                    }
                }
            }

            public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
                super.onServicesDiscovered(bluetoothGatt, i);
                if (i == 0) {
                    BloodFatManager.this.setNotification(bluetoothGatt);
                }
            }
        });
        this.gatt.connect();
    }

    /* access modifiers changed from: private */
    public void setNotification(BluetoothGatt bluetoothGatt) {
        new Timer().schedule(new TimerTask() {
            public void run() {
                if (BloodFatManager.this.isCheckDevice) {
                    BloodFatManager.this.sendBroadcast(new Intent("device_unavailable"));
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
        boolean characteristicNotification = bluetoothGatt.setCharacteristicNotification(characteristic, true);
        if (characteristicNotification) {
            List<BluetoothGattDescriptor> descriptors = characteristic.getDescriptors();
            if (descriptors != null && descriptors.size() > 0) {
                for (BluetoothGattDescriptor next : descriptors) {
                    next.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    bluetoothGatt.writeDescriptor(next);
                }
            }
            PrintStream printStream = System.out;
            printStream.println("gatt.setCharacteristicNotification::" + characteristicNotification);
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
        this.f89sb = new StringBuilder();
        writeData(this.gatt, this.bloodFatMeasurementValue);
    }

    public void postHand() {
        writeData(this.gatt, this.bloodFatHandValue);
    }

    public void postSetting01() {
        writeData(this.gatt, this.bloodFatSetting01);
    }

    public void postSetting02() {
        writeData(this.gatt, this.bloodFatSetting02);
    }

    public void postQuerying() {
        writeData(this.gatt, this.bloodFatQuerying);
    }

    public void postFlash() {
        writeData(this.gatt, this.bloodFatSetting);
    }

    public boolean isConnect() {
        return this.isConnect;
    }

    /* access modifiers changed from: private */
    public void sendBroadcast(Intent intent) {
        this.lbm.sendBroadcast(intent);
    }

    public void setTime() {
        byte[] bloodFatTimeFrame = Utils.getBloodFatTimeFrame();
        writeData(this.gatt, bloodFatTimeFrame);
        PrintStream printStream = System.out;
        printStream.println("setTime: " + Utils.byteArray2HexString(bloodFatTimeFrame));
    }

    public void setTitle(String str) {
        byte[] bloodFatPrintTitle = Utils.setBloodFatPrintTitle(str);
        int i = 1;
        int[] iArr = {bloodFatPrintTitle.length};
        int i2 = 20;
        if (iArr[0] <= 20) {
            writeData(this.gatt, bloodFatPrintTitle);
            return;
        }
        int[] iArr2 = {0};
        int[] iArr3 = {0};
        byte[] bArr = new byte[20];
        while (iArr[0] >= i2) {
            if (iArr3[0] == 0) {
                iArr3[0] = i;
                for (int i3 = 0; i3 < i2; i3++) {
                    bArr[i3] = bloodFatPrintTitle[(iArr2[0] * 20) + i3];
                }
                final byte[] bArr2 = bArr;
                C05483 r11 = r0;
                final int[] iArr4 = iArr;
                final int[] iArr5 = iArr2;
                final int[] iArr6 = iArr3;
                C05483 r0 = new TimerTask() {
                    public void run() {
                        BloodFatManager bloodFatManager = BloodFatManager.this;
                        bloodFatManager.writeData(bloodFatManager.gatt, bArr2);
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
                    bArr3[i4] = bloodFatPrintTitle[(iArr2[0] * 20) + i4];
                }
                final int[] iArr8 = iArr7;
                final int[] iArr9 = iArr2;
                final int[] iArr10 = iArr3;
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        BloodFatManager bloodFatManager = BloodFatManager.this;
                        bloodFatManager.writeData(bloodFatManager.gatt, bArr3);
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
        byte[] bloodFatPatientInfomation = Utils.setBloodFatPatientInfomation(str, str2, str3, str4);
        if (new int[]{bloodFatPatientInfomation.length}[0] <= 20) {
            writeData(this.gatt, bloodFatPatientInfomation);
            return;
        }
        Queue<byte[]> splitByte = splitByte(bloodFatPatientInfomation, 20);
        final byte[] poll = splitByte.poll();
        final byte[] poll2 = splitByte.poll();
        if (poll != null) {
            new Timer().schedule(new TimerTask() {
                public void run() {
                    BloodFatManager bloodFatManager = BloodFatManager.this;
                    bloodFatManager.writeData(bloodFatManager.gatt, poll);
                }
            }, 500);
        }
        if (poll2 != null) {
            new Timer().schedule(new TimerTask() {
                public void run() {
                    BloodFatManager bloodFatManager = BloodFatManager.this;
                    bloodFatManager.writeData(bloodFatManager.gatt, poll2);
                }
            }, 1000);
        }
    }

    private static Queue<byte[]> splitByte(byte[] bArr, int i) {
        int i2;
        byte[] bArr2;
        LinkedList linkedList = new LinkedList();
        if (bArr.length % i == 0) {
            i2 = bArr.length / i;
        } else {
            i2 = Math.round((float) ((bArr.length / i) + 1));
        }
        if (i2 > 0) {
            for (int i3 = 0; i3 < i2; i3++) {
                if (i2 == 1 || i3 == i2 - 1) {
                    int length = bArr.length % i == 0 ? i : bArr.length % i;
                    byte[] bArr3 = new byte[length];
                    System.arraycopy(bArr, i3 * i, bArr3, 0, length);
                    bArr2 = bArr3;
                } else {
                    bArr2 = new byte[i];
                    System.arraycopy(bArr, i3 * i, bArr2, 0, i);
                }
                linkedList.offer(bArr2);
            }
        }
        return linkedList;
    }
}
