package com.huanghuang.kangshangyiliao.dayiji;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.dao.bean.Printer;
import java.util.ArrayList;
import java.util.Set;

public class PrinterActivity extends Activity {
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private BluetoothAdapter mBtAdapter;
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            Intent intent = new Intent();
            intent.setClass(PrinterActivity.this, BluetoothPrintService.class);
            Bundle bundle = new Bundle();
            bundle.putInt(BluetoothPrintService.INTENT_OP, 4);
            intent.putExtras(bundle);
            PrinterActivity.this.startService(intent);
            String charSequence = ((TextView) view).getText().toString();
            if (!charSequence.equals(PrinterActivity.this.getResources().getText(C0418R.string.none_founda))) {
                String substring = charSequence.substring(charSequence.length() - 17);
                PrinterActivity.this.printer.PrinterName = substring;
                PrinterActivity.this.connectDevice(substring);
                Intent intent2 = new Intent();
                intent2.putExtra(PrinterActivity.EXTRA_DEVICE_ADDRESS, substring);
                PrinterActivity printerActivity = PrinterActivity.this;
                Toast.makeText(printerActivity, printerActivity.getString(C0418R.string.connect_success), 0).show();
                PrinterActivity.this.setResult(-1, intent2);
                PrinterActivity.this.finish();
            }
        }
    };
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mNewDevicesArrayAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (BluetoothPrintService.SERVICE_ACTION.equals(intent.getAction())) {
                String stringExtra = intent.getStringExtra(BluetoothPrintService.DISCOVERY_RESULT);
                int intExtra = intent.getIntExtra(BluetoothPrintService.DISCOVERY_END, -1);
                if (!(stringExtra == null || stringExtra == "")) {
                    PrinterActivity.this.mNewDevicesArrayAdapter.add(stringExtra);
                }
                if (intExtra != -1) {
                    PrinterActivity.this.setProgressBarIndeterminateVisibility(false);
                    PrinterActivity.this.setTitle(C0418R.string.select_devicea);
                    if (intExtra != 1) {
                        PrinterActivity.this.mNewDevicesArrayAdapter.add((String) PrinterActivity.this.getResources().getText(C0418R.string.none_founda));
                    }
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public Printer printer = new Printer();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(5);
        setContentView(C0418R.layout.device_list);
        setResult(0);
        findViewById(C0418R.C0420id.button_scan).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PrinterActivity.this.doDiscovery();
                view.setVisibility(0);
            }
        });
        this.mPairedDevicesArrayAdapter = new ArrayAdapter<>(this, C0418R.layout.device_name);
        this.mNewDevicesArrayAdapter = new ArrayAdapter<>(this, C0418R.layout.device_name);
        ListView listView = (ListView) findViewById(C0418R.C0420id.paired_devices);
        listView.setAdapter(this.mPairedDevicesArrayAdapter);
        listView.setOnItemClickListener(this.mDeviceClickListener);
        ListView listView2 = (ListView) findViewById(C0418R.C0420id.new_devices);
        listView2.setAdapter(this.mNewDevicesArrayAdapter);
        listView2.setOnItemClickListener(this.mDeviceClickListener);
        registerReceiver(this.mReceiver, new IntentFilter(BluetoothPrintService.SERVICE_ACTION));
        this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> bondedDevices = this.mBtAdapter.getBondedDevices();
        if (bondedDevices.size() > 0) {
            ArrayList arrayList = new ArrayList(100);
            findViewById(C0418R.C0420id.title_paired_devices).setVisibility(0);
            for (BluetoothDevice next : bondedDevices) {
                if (!arrayList.contains(next.getAddress())) {
                    arrayList.add(next.getAddress());
                    ArrayAdapter<String> arrayAdapter = this.mPairedDevicesArrayAdapter;
                    arrayAdapter.add(next.getName() + "\n" + next.getAddress());
                }
            }
            return;
        }
        this.mPairedDevicesArrayAdapter.add(getResources().getText(C0418R.string.none_paired).toString());
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, BluetoothPrintService.class);
        Bundle bundle = new Bundle();
        bundle.putInt(BluetoothPrintService.INTENT_OP, 4);
        intent.putExtras(bundle);
        startService(intent);
        unregisterReceiver(this.mReceiver);
    }

    /* access modifiers changed from: private */
    public void doDiscovery() {
        setProgressBarIndeterminateVisibility(true);
        setTitle(C0418R.string.scanning);
        findViewById(C0418R.C0420id.title_new_devices).setVisibility(0);
        Intent intent = new Intent(this, BluetoothPrintService.class);
        Bundle bundle = new Bundle();
        bundle.putInt(BluetoothPrintService.INTENT_OP, 3);
        intent.putExtras(bundle);
        startService(intent);
    }

    /* access modifiers changed from: private */
    public void connectDevice(String str) {
        Intent intent = new Intent(this, BluetoothPrintService.class);
        Bundle bundle = new Bundle();
        bundle.putInt(BluetoothPrintService.INTENT_OP, 0);
        bundle.putString(BluetoothPrintService.INTENT_ADDRESS, str);
        intent.putExtras(bundle);
        startService(intent);
    }
}
