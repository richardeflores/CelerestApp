package com.huanghuang.kangshangyiliao.dayiji;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BluetoothPrintService extends Service {

    /* renamed from: D */
    protected static final boolean f86D = true;
    public static final String DEVICE_NAME = "device_name";
    public static final int DISCOVERY_DEFAULT = -1;
    public static final String DISCOVERY_END = "end";
    public static final int DISCOVERY_HAVING = 1;
    public static final int DISCOVERY_NOTHING = 0;
    public static final String DISCOVERY_RESULT = "result";
    public static final String INTENT_ADDRESS = "address";
    public static final String INTENT_OP = "operate";
    public static final String INTENT_PRINT = "print";
    protected static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    protected static final String NAME = "BluetoothPrint";
    public static final int OP_CANCEL_DISCOVERY = 4;
    public static final int OP_CONNECT = 0;
    public static final int OP_DISCOVERY = 3;
    public static final int OP_LISTEN = 1;
    public static final int OP_PRINT = 2;
    public static final String SERVICE_ACTION = "BluetoothPrintService";
    public static final int STATE_CONNECTED = 4;
    public static final int STATE_CONNECTING = 3;
    public static final int STATE_LISTEN = 2;
    public static final int STATE_NONE = 1;
    public static final String STATE_STATUS = "state";
    protected static final String TAG = "BluetoothPrintService";
    public static final String TOAST = "toast";
    protected AcceptThread mAcceptThread;
    protected final BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    protected ConnectThread mConnectThread;
    protected ConnectedThread mConnectedThread;
    protected List<String> mDeviceList;
    protected final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Intent intent2 = new Intent();
            if ("android.bluetooth.device.action.FOUND".equals(action)) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                String str = bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress();
                if (!BluetoothPrintService.this.mDeviceList.contains(str)) {
                    BluetoothPrintService.this.mDeviceList.add(str);
                    intent2.putExtra(BluetoothPrintService.DISCOVERY_RESULT, str);
                    BluetoothPrintService.this.sendServiceBroadcast(intent2);
                }
            } else if (!"android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action)) {
            } else {
                if (BluetoothPrintService.this.mDeviceList.isEmpty()) {
                    intent2.putExtra(BluetoothPrintService.DISCOVERY_END, 0);
                    BluetoothPrintService.this.sendServiceBroadcast(intent2);
                    return;
                }
                intent2.putExtra(BluetoothPrintService.DISCOVERY_END, 1);
                BluetoothPrintService.this.sendServiceBroadcast(intent2);
            }
        }
    };
    protected int mState;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        Log.i("BluetoothPrintService", "onCreate");
        super.onCreate();
        this.mState = 1;
        RegisterReceiverBluetooth();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        Bundle extras;
        Intent intent2 = new Intent();
        if (!(intent == null || (extras = intent.getExtras()) == null)) {
            int i3 = extras.getInt(INTENT_OP);
            if (i3 == 0) {
                connect(intent.getExtras().getString(INTENT_ADDRESS));
            } else if (i3 == 1) {
                intent2.putExtra(STATE_STATUS, this.mState);
                sendServiceBroadcast(intent2);
            } else if (i3 == 2) {
                int i4 = this.mState;
                if (i4 != 4) {
                    intent2.putExtra(STATE_STATUS, i4);
                    Bundle bundle = new Bundle();
                    bundle.putString(TOAST, "Is not connected to the device！");
                    intent2.putExtras(bundle);
                    sendServiceBroadcast(intent2);
                } else {
                    write(intent.getByteArrayExtra(INTENT_PRINT));
                }
            } else if (i3 == 3) {
                doDiscovery();
            } else if (i3 == 4 && this.mAdapter.isDiscovering()) {
                this.mAdapter.cancelDiscovery();
            }
        }
        return super.onStartCommand(intent, i, i2);
    }

    public void onDestroy() {
        Log.i("BluetoothPrintService", "onDestroy");
        super.onDestroy();
        stop();
    }

    /* access modifiers changed from: protected */
    public synchronized void setState(int i) {
        Log.d("BluetoothPrintService", "setState() " + this.mState + " -> " + i);
        this.mState = i;
        Intent intent = new Intent();
        intent.putExtra(STATE_STATUS, this.mState);
        sendServiceBroadcast(intent);
    }

    /* access modifiers changed from: protected */
    public synchronized void setState(int i, Intent intent) {
        Log.d("BluetoothPrintService", "setState() " + this.mState + " -> " + i);
        this.mState = i;
        sendServiceBroadcast(intent);
    }

    public synchronized int getState() {
        return this.mState;
    }

    /* access modifiers changed from: protected */
    public synchronized void sendServiceBroadcast(Intent intent) {
        intent.setAction("BluetoothPrintService");
        sendBroadcast(intent);
    }

    /* access modifiers changed from: protected */
    public void doDiscovery() {
        Log.d("BluetoothPrintService", "doDiscovery()");
        if (this.mAdapter.isDiscovering()) {
            this.mAdapter.cancelDiscovery();
        }
        this.mDeviceList = new ArrayList();
        this.mAdapter.startDiscovery();
    }

    /* access modifiers changed from: protected */
    public void RegisterReceiverBluetooth() {
        registerReceiver(this.mReceiver, new IntentFilter("android.bluetooth.device.action.FOUND"));
        registerReceiver(this.mReceiver, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED"));
    }

    public synchronized void start() {
        Log.d("BluetoothPrintService", "start");
        if (this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }
        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }
        if (this.mAcceptThread == null) {
            this.mAcceptThread = new AcceptThread();
            this.mAcceptThread.start();
        }
        setState(2);
    }

    public synchronized void connect(String str) {
        Log.d("BluetoothPrintService", "connect to: " + str);
        BluetoothDevice remoteDevice = this.mAdapter.getRemoteDevice(str);
        if (this.mState == 3 && this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }
        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }
        this.mConnectThread = new ConnectThread(remoteDevice);
        this.mConnectThread.start();
        setState(3);
        saveAddress(str);
    }

    private void saveAddress(String str) {
        SharedPreferences.Editor edit = getApplicationContext().getSharedPreferences("name", 0).edit();
        edit.putString("Printer", str);
        edit.apply();
    }

    public synchronized void connected(BluetoothSocket bluetoothSocket, BluetoothDevice bluetoothDevice) {
        Log.d("BluetoothPrintService", "connected");
        if (this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }
        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }
        if (this.mAcceptThread != null) {
            this.mAcceptThread.cancel();
            this.mAcceptThread = null;
        }
        this.mConnectedThread = new ConnectedThread(bluetoothSocket);
        this.mConnectedThread.start();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(DEVICE_NAME, bluetoothDevice.getName());
        intent.putExtras(bundle);
        sendServiceBroadcast(intent);
        setState(4);
    }

    public synchronized void stop() {
        Log.d("BluetoothPrintService", "stop");
        if (this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }
        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }
        if (this.mAcceptThread != null) {
            this.mAcceptThread.cancel();
            this.mAcceptThread = null;
        }
        if (this.mAdapter.isDiscovering()) {
            this.mAdapter.cancelDiscovery();
        }
        unregisterReceiver(this.mReceiver);
        setState(1);
    }

    public void write(byte[] bArr) {
        synchronized (this) {
            if (this.mState == 4) {
                ConnectedThread connectedThread = this.mConnectedThread;
                connectedThread.write(bArr);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void connectionFailed() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(TOAST, "fail to connect!");
        intent.putExtras(bundle);
        setState(2, intent);
    }

    protected class AcceptThread extends Thread {
        protected final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket bluetoothServerSocket;
            try {
                bluetoothServerSocket = BluetoothPrintService.this.mAdapter.listenUsingRfcommWithServiceRecord(BluetoothPrintService.NAME, BluetoothPrintService.MY_UUID);
            } catch (IOException e) {
                Log.e("BluetoothPrintService", "listen() failed", e);
                bluetoothServerSocket = null;
            }
            this.mmServerSocket = bluetoothServerSocket;
        }

        public void run() {
            Log.d("BluetoothPrintService", "BEGIN mAcceptThread" + this);
            setName("AcceptThread");
            while (BluetoothPrintService.this.mState != 4) {
                try {
                    BluetoothSocket accept = this.mmServerSocket.accept();
                    if (accept != null) {
                        synchronized (BluetoothPrintService.this) {
                            int i = BluetoothPrintService.this.mState;
                            if (i != 1) {
                                if (i == 2 || i == 3) {
                                    BluetoothPrintService.this.connected(accept, accept.getRemoteDevice());
                                } else if (i != 4) {
                                }
                            }
                            try {
                                accept.close();
                            } catch (IOException e) {
                                Log.e("BluetoothPrintService", "Can not close the socket！", e);
                            }
                        }
                    }
                } catch (IOException e2) {
                    Log.e("BluetoothPrintService", "accept() failed", e2);
                }
            }
            Log.i("BluetoothPrintService", "END mAcceptThread");
        }

        public void cancel() {
            Log.d("BluetoothPrintService", "cancel " + this);
            try {
                this.mmServerSocket.close();
            } catch (IOException e) {
                Log.e("BluetoothPrintService", "close() of server failed", e);
            }
        }
    }

    protected class ConnectThread extends Thread {
        protected final BluetoothDevice mmDevice;
        protected final BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice bluetoothDevice) {
            BluetoothSocket bluetoothSocket;
            this.mmDevice = bluetoothDevice;
            try {
                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(BluetoothPrintService.MY_UUID);
            } catch (IOException e) {
                Log.e("BluetoothPrintService", "An exception occurs！", e);
                bluetoothSocket = null;
            }
            this.mmSocket = bluetoothSocket;
        }

        public void run() {
            Log.i("BluetoothPrintService", "BEGIN mConnectThread");
            setName("ConnectThread");
            BluetoothPrintService.this.mAdapter.cancelDiscovery();
            try {
                this.mmSocket.connect();
                synchronized (BluetoothPrintService.this) {
                    BluetoothPrintService.this.mConnectThread = null;
                }
                BluetoothPrintService.this.connected(this.mmSocket, this.mmDevice);
            } catch (IOException unused) {
                BluetoothPrintService.this.connectionFailed();
                try {
                    this.mmSocket.close();
                } catch (IOException e) {
                    Log.e("BluetoothPrintService", "Can not close the socket before the connection fails！", e);
                }
                BluetoothPrintService.this.start();
            }
        }

        public void cancel() {
            try {
                this.mmSocket.close();
            } catch (IOException e) {
                Log.e("BluetoothPrintService", "Close the socket failure！", e);
            }
        }
    }

    protected class ConnectedThread extends Thread {
        protected final OutputStream mmOutStream;
        protected final BluetoothSocket mmSocket;

        public ConnectedThread(BluetoothSocket bluetoothSocket) {
            OutputStream outputStream;
            Log.d("BluetoothPrintService", "create ConnectedThread");
            this.mmSocket = bluetoothSocket;
            try {
                outputStream = bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                Log.e("BluetoothPrintService", "Temporary socket is not established！", e);
                outputStream = null;
            }
            this.mmOutStream = outputStream;
        }

        public void run() {
            Log.i("BluetoothPrintService", "BEGIN mConnectedThread");
        }

        public void write(byte[] bArr) {
            boolean z;
            int length = bArr.length;
            int i = 0;
            int i2 = 512;
            while (true) {
                if (i + i2 > length) {
                    i2 = length - i;
                    z = BluetoothPrintService.f86D;
                } else {
                    z = false;
                }
                try {
                    this.mmOutStream.write(bArr, i, i2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException unused) {
                }
                if (!z) {
                    i += i2;
                } else {
                    return;
                }
            }
        }

        public void cancel() {
            try {
                this.mmSocket.close();
            } catch (IOException e) {
                Log.e("BluetoothPrintService", "close() of connect socket failed", e);
            }
        }
    }
}
