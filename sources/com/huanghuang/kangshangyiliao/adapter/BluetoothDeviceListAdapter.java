package com.huanghuang.kangshangyiliao.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.bean.BluetoothDeviceInfo;
import java.util.List;

public class BluetoothDeviceListAdapter extends BaseAdapter {
    private Context context;
    private List<BluetoothDeviceInfo> data;

    public long getItemId(int i) {
        return 0;
    }

    public BluetoothDeviceListAdapter(Context context2, List<BluetoothDeviceInfo> list) {
        this.context = context2;
        this.data = list;
    }

    public int getCount() {
        return this.data.size();
    }

    public BluetoothDeviceInfo getItem(int i) {
        return this.data.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(C0418R.layout.item_bluetooth_device, viewGroup, false);
        }
        TextView textView = (TextView) view.findViewById(C0418R.C0420id.tvDeviceName);
        BluetoothDevice bluetoothDevice = this.data.get(i).device;
        if ((bluetoothDevice.getName() != null ? (char) 1 : 2) != 1) {
            view.setVisibility(8);
            view.setLayoutParams(new AbsListView.LayoutParams(1, 1));
        } else {
            view.setVisibility(0);
            textView.setText(bluetoothDevice.getName());
            view.setLayoutParams(new AbsListView.LayoutParams(2500, 250));
        }
        return view;
    }
}
