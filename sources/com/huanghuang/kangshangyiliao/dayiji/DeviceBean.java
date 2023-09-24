package com.huanghuang.kangshangyiliao.dayiji;

import android.content.Context;
import com.huanghuang.kangshangyiliao.C0418R;

public class DeviceBean {
    private static DeviceBean instance = new DeviceBean();
    private int connectState;
    private String deviceAddress;
    private String deviceNm;

    private DeviceBean() {
    }

    public static DeviceBean getInstance() {
        return instance;
    }

    public String getDeviceNm() {
        return this.deviceNm;
    }

    public void setDeviceNm(String str) {
        this.deviceNm = str;
    }

    public String getDeviceAddress() {
        return this.deviceAddress;
    }

    public void setDeviceAddress(String str) {
        this.deviceAddress = str;
    }

    public int getConnectState() {
        return this.connectState;
    }

    public void setConnectState(int i) {
        this.connectState = i;
    }

    public String getDeviceState(Context context) {
        String string = context.getString(C0418R.string.msg_info_title);
        int i = this.connectState;
        return String.format(string, new Object[]{i != 0 ? context.getString(i) : ""});
    }

    public void clear() {
        this.deviceNm = null;
        this.deviceAddress = null;
        this.connectState = 0;
    }
}
