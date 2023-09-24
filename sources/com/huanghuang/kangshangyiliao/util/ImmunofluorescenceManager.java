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
import com.google.code.microlog4android.appender.SyslogMessage;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.base.BaseManager;
import com.huanghuang.kangshangyiliao.dao.ImmunofluorescenceDao;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import com.huanghuang.kangshangyiliao.dao.bean.Immunofluorescence;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class ImmunofluorescenceManager implements BaseManager {
    public static final String CONNECTED = "immunofluorescence_connected";
    public static final String CONNECTED_FAIL = "immunofluorescence_connect_fail";
    public static final String DEVICE_AVAILABLE = "immunofluorescence_device_available";
    public static final String DEVICE_STATE_OFF = "immunofluorescence_device_state_off";
    public static final String DEVICE_UNAVAILABLE = "device_unavailable";
    public static final String DISCONNECTED = "immunofluorescence_disconnected";
    public static final String HAND = "immunofluorescence_hand";
    public static final String NOTIFY = "immunofluorescence_notify";
    public static final String SET_PATIENT_INFO = "set_patient_info";
    public static final String SET_TITLE = "set_title";
    public static final String TIME = "immunofluorescence_time";
    private AppBase appBase;
    private Context context;
    /* access modifiers changed from: private */
    public ImmunofluorescenceDao dao;
    /* access modifiers changed from: private */
    public BluetoothDevice device;
    /* access modifiers changed from: private */
    public String deviceAdd;
    /* access modifiers changed from: private */
    public BluetoothGatt gatt;
    private final byte[] immunofluorescenceHandValue;
    private final byte[] immunofluorescenceMeasurementValue;
    private final byte[] immunofluorescenceMeasurementValue_base;
    private final byte[] immunofluorescenceOpen;
    /* access modifiers changed from: private */
    public boolean isCheckDevice;
    /* access modifiers changed from: private */
    public boolean isConnect;
    private LocalBroadcastManager lbm;
    /* access modifiers changed from: private */
    public byte[] resultByte;
    /* access modifiers changed from: private */

    /* renamed from: sb */
    public StringBuilder f91sb;
    /* access modifiers changed from: private */
    public Session session;

    private static class Loader {
        /* access modifiers changed from: private */
        public static final ImmunofluorescenceManager INSTANCE = new ImmunofluorescenceManager();

        private Loader() {
        }
    }

    public static ImmunofluorescenceManager getInstance() {
        return Loader.INSTANCE;
    }

    private ImmunofluorescenceManager() {
        this.immunofluorescenceHandValue = new byte[]{-91, 85, 8, 0, -112, 1, 67, 79, 78, 84, -51};
        this.immunofluorescenceMeasurementValue = new byte[]{-91, 85, 8, 0, -112, 33, 3, 0, 0, 1, -67};
        this.immunofluorescenceMeasurementValue_base = new byte[]{-91, 85, 8, 0, -112, 33, 1, 0, 0, 0, -70};
        this.immunofluorescenceOpen = new byte[]{-91, 85, 8, 0, -112, 33, 9, SyslogMessage.FACILITY_LOCAL_USE_0, 0, 1, -45};
        this.appBase = AppBase.getInstance();
        this.isConnect = false;
        this.session = Session.getInstance();
        this.isCheckDevice = false;
        this.context = this.appBase.getContext();
        this.dao = new ImmunofluorescenceDao();
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
                    Intent intent = new Intent(ImmunofluorescenceManager.CONNECTED);
                    intent.putExtra(Cache.DEVICE_INFO, ImmunofluorescenceManager.this.device);
                    ImmunofluorescenceManager.this.sendBroadcast(intent);
                    boolean unused = ImmunofluorescenceManager.this.isConnect = true;
                    bluetoothGatt.discoverServices();
                    ImmunofluorescenceManager.this.session.setImmunofluorescenceCurrentDevice(ImmunofluorescenceManager.this.device.getName());
                    ImmunofluorescenceManager.this.session.setDeviceType(4);
                } else if (i2 != 0) {
                } else {
                    if (i == 133) {
                        ImmunofluorescenceManager.this.sendBroadcast(new Intent(ImmunofluorescenceManager.CONNECTED_FAIL));
                        boolean unused2 = ImmunofluorescenceManager.this.isConnect = false;
                        ImmunofluorescenceManager.this.session.setImmunofluorescenceCurrentDevice((String) null);
                        ImmunofluorescenceManager.this.session.setDeviceType(-1);
                        return;
                    }
                    bluetoothGatt.close();
                    boolean unused3 = ImmunofluorescenceManager.this.isConnect = false;
                    ImmunofluorescenceManager.this.session.setImmunofluorescenceCurrentDevice((String) null);
                    ImmunofluorescenceManager.this.session.setDeviceType(-1);
                    ImmunofluorescenceManager.this.sendBroadcast(new Intent(ImmunofluorescenceManager.DEVICE_STATE_OFF));
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
                byte immunofluorescenceFrameType = Utils.getImmunofluorescenceFrameType(value);
                PrintStream printStream = System.out;
                printStream.println("valuevalue:" + Arrays.toString(value));
                PrintStream printStream2 = System.out;
                printStream2.println("typetype::" + immunofluorescenceFrameType);
                PrintStream printStream3 = System.out;
                printStream3.println("valuevalue::" + Utils.byteArray2HexString(value));
                if (immunofluorescenceFrameType == 1) {
                    ImmunofluorescenceManager.this.sendBroadcast(new Intent(ImmunofluorescenceManager.HAND));
                } else if (immunofluorescenceFrameType == 2) {
                    ImmunofluorescenceManager.this.sendBroadcast(new Intent(ImmunofluorescenceManager.TIME));
                } else if (immunofluorescenceFrameType == 16 || immunofluorescenceFrameType == 18) {
                    ImmunofluorescenceManager.this.sendBroadcast(new Intent("set_patient_info"));
                } else if (immunofluorescenceFrameType == 17) {
                    ImmunofluorescenceManager.this.sendBroadcast(new Intent("set_title"));
                } else if (immunofluorescenceFrameType != 33) {
                    if (immunofluorescenceFrameType == 36) {
                        byte[] unused = ImmunofluorescenceManager.this.resultByte = Arrays.copyOf(value, 143);
                        StringBuilder unused2 = ImmunofluorescenceManager.this.f91sb = new StringBuilder();
                        ImmunofluorescenceManager.this.f91sb.append(Utils.byteArray2HexString(value));
                        if (!ImmunofluorescenceManager.this.f91sb.toString().startsWith("a5558c009024")) {
                            StringBuilder unused3 = ImmunofluorescenceManager.this.f91sb = new StringBuilder();
                        } else if (value.length < 20) {
                            String sb = ImmunofluorescenceManager.this.f91sb.toString();
                            ClinicName clinicInfo = Utils.getClinicInfo();
                            Immunofluorescence immunofluorescence = new Immunofluorescence();
                            immunofluorescence.deviceName = ImmunofluorescenceManager.this.device.getName();
                            immunofluorescence.deviceAdd = ImmunofluorescenceManager.this.deviceAdd;
                            immunofluorescence.nickname = Utils.getUserId();
                            immunofluorescence.createDate = Utils.getDate();
                            immunofluorescence.data = sb;
                            immunofluorescence.nickname = Utils.getNickName();
                            if (clinicInfo != null) {
                                immunofluorescence.clinicName = clinicInfo.f77id + "";
                                ImmunofluorescenceManager.this.dao.save(immunofluorescence);
                            }
                            Intent intent = new Intent();
                            intent.setAction(ImmunofluorescenceManager.NOTIFY);
                            intent.putExtra(ImmunofluorescenceDao.table, immunofluorescence);
                            ImmunofluorescenceManager.this.sendBroadcast(intent);
                            StringBuilder unused4 = ImmunofluorescenceManager.this.f91sb = null;
                        }
                    } else if (ImmunofluorescenceManager.this.f91sb != null) {
                        ImmunofluorescenceManager.this.f91sb.append(Utils.byteArray2HexString(value));
                        PrintStream printStream4 = System.out;
                        printStream4.println("sbsbsb::" + ImmunofluorescenceManager.this.f91sb.toString());
                        if (ImmunofluorescenceManager.this.f91sb.toString().endsWith("a55508009001434f4e54cd")) {
                            StringBuilder unused5 = ImmunofluorescenceManager.this.f91sb = new StringBuilder();
                            ImmunofluorescenceManager.this.sendBroadcast(new Intent(ImmunofluorescenceManager.HAND));
                        } else if (!ImmunofluorescenceManager.this.f91sb.toString().startsWith("a5558c009024")) {
                            StringBuilder unused6 = ImmunofluorescenceManager.this.f91sb = new StringBuilder();
                        } else {
                            PrintStream printStream5 = System.out;
                            printStream5.println("resultByteresultByte结束::::" + Arrays.toString(ImmunofluorescenceManager.this.resultByte));
                            if (value.length < 20 && ImmunofluorescenceManager.this.f91sb.toString().length() >= 258) {
                                PrintStream printStream6 = System.out;
                                printStream6.println("value.length<20进入了:::::" + value.length);
                                String sb2 = ImmunofluorescenceManager.this.f91sb.toString();
                                PrintStream printStream7 = System.out;
                                printStream7.println("value.length<20进入了data::" + sb2);
                                PrintStream printStream8 = System.out;
                                printStream8.println("value.length<20进入了data::的长度" + sb2.length());
                                ClinicName clinicInfo2 = Utils.getClinicInfo();
                                Immunofluorescence immunofluorescence2 = new Immunofluorescence();
                                immunofluorescence2.deviceName = ImmunofluorescenceManager.this.device.getName();
                                immunofluorescence2.deviceAdd = ImmunofluorescenceManager.this.deviceAdd;
                                immunofluorescence2.nickname = Utils.getUserId();
                                immunofluorescence2.createDate = Utils.getDate();
                                immunofluorescence2.data = sb2;
                                immunofluorescence2.nickname = Utils.getNickName();
                                if (clinicInfo2 != null) {
                                    immunofluorescence2.clinicName = clinicInfo2.f77id + "";
                                    ImmunofluorescenceManager.this.dao.save(immunofluorescence2);
                                }
                                Intent intent2 = new Intent();
                                intent2.setAction(ImmunofluorescenceManager.NOTIFY);
                                intent2.putExtra(ImmunofluorescenceDao.table, immunofluorescence2);
                                ImmunofluorescenceManager.this.sendBroadcast(intent2);
                                StringBuilder unused7 = ImmunofluorescenceManager.this.f91sb = null;
                            }
                        }
                    }
                }
            }

            public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
                super.onServicesDiscovered(bluetoothGatt, i);
                if (i == 0) {
                    ImmunofluorescenceManager.this.setNotification(bluetoothGatt);
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
                if (ImmunofluorescenceManager.this.isCheckDevice) {
                    ImmunofluorescenceManager.this.sendBroadcast(new Intent("device_unavailable"));
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
        this.f91sb = new StringBuilder();
        writeData(this.gatt, this.immunofluorescenceMeasurementValue);
    }

    public void measurement02() {
        this.f91sb = new StringBuilder();
        writeData(this.gatt, this.immunofluorescenceMeasurementValue_base);
    }

    public void postHand() {
        writeData(this.gatt, this.immunofluorescenceHandValue);
    }

    public boolean isConnect() {
        return this.isConnect;
    }

    /* access modifiers changed from: private */
    public void sendBroadcast(Intent intent) {
        this.lbm.sendBroadcast(intent);
    }

    public void setTime() {
        writeData(this.gatt, Utils.getImmufluoTimeFrame());
    }

    public void setTitle(String str) {
        byte[] immunoFluoPrintTitle = Utils.setImmunoFluoPrintTitle(str);
        int i = 1;
        int[] iArr = {immunoFluoPrintTitle.length};
        int i2 = 20;
        if (iArr[0] <= 20) {
            writeData(this.gatt, immunoFluoPrintTitle);
            return;
        }
        final int[] iArr2 = {0};
        final int[] iArr3 = {0};
        byte[] bArr = new byte[20];
        while (iArr[0] >= i2) {
            if (iArr3[0] == 0) {
                iArr3[0] = i;
                for (int i3 = 0; i3 < i2; i3++) {
                    bArr[i3] = immunoFluoPrintTitle[(iArr2[0] * 20) + i3];
                }
                final byte[] bArr2 = bArr;
                C05593 r11 = r0;
                final int[] iArr4 = iArr;
                final int[] iArr5 = iArr2;
                final int[] iArr6 = iArr3;
                C05593 r0 = new TimerTask() {
                    public void run() {
                        ImmunofluorescenceManager immunofluorescenceManager = ImmunofluorescenceManager.this;
                        immunofluorescenceManager.writeData(immunofluorescenceManager.gatt, bArr2);
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
                    bArr3[i4] = immunoFluoPrintTitle[(iArr2[0] * 20) + i4];
                }
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        ImmunofluorescenceManager immunofluorescenceManager = ImmunofluorescenceManager.this;
                        immunofluorescenceManager.writeData(immunofluorescenceManager.gatt, bArr3);
                        Log.d("zdc", "-------------send------------- ");
                        int[] iArr = iArr2;
                        iArr[0] = iArr[0] + 1;
                        iArr3[0] = 0;
                    }
                }, 500);
                Log.d("zdc", "----start timer2");
            }
        }
    }

    public void setPatientInfo(String str, String str2, String str3, String str4) {
        byte[] immunoFluoPatientInfomation = Utils.setImmunoFluoPatientInfomation(str, str2, str3, str4);
        if (new int[]{immunoFluoPatientInfomation.length}[0] <= 20) {
            writeData(this.gatt, immunoFluoPatientInfomation);
            return;
        }
        Queue<byte[]> splitByte = splitByte(immunoFluoPatientInfomation, 20);
        final byte[] poll = splitByte.poll();
        final byte[] poll2 = splitByte.poll();
        if (poll != null) {
            new Timer().schedule(new TimerTask() {
                public void run() {
                    ImmunofluorescenceManager immunofluorescenceManager = ImmunofluorescenceManager.this;
                    immunofluorescenceManager.writeData(immunofluorescenceManager.gatt, poll);
                }
            }, 500);
        }
        if (poll2 != null) {
            new Timer().schedule(new TimerTask() {
                public void run() {
                    ImmunofluorescenceManager immunofluorescenceManager = ImmunofluorescenceManager.this;
                    immunofluorescenceManager.writeData(immunofluorescenceManager.gatt, poll2);
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
