package com.huanghuang.kangshangyiliao.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.huanghuang.kangshangyiliao.app.base.AppBase;

public class Cache {
    public static final String CLINIC_INFO = "clinicinfo";
    public static final String DEVICE_INFO = "device";
    public static final String NICK_NAME = "nickname";
    public static final String Printer_INFO = "printerinfo";
    public static final String USER_INFO = "userinfo";
    private int MODE;
    private Context mCtx;
    private String name;

    /* renamed from: sp */
    private SharedPreferences f90sp;

    private static class Loader {
        /* access modifiers changed from: private */
        public static final Cache INSTANCE = new Cache();

        private Loader() {
        }
    }

    private Cache() {
        this.name = "assay";
        this.MODE = 0;
        this.mCtx = AppBase.getInstance().getContext();
        this.f90sp = this.mCtx.getSharedPreferences(this.name, this.MODE);
    }

    public static Cache getInstance() {
        return Loader.INSTANCE;
    }

    public void save(String str, String str2) {
        SharedPreferences.Editor edit = this.f90sp.edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public void save(String str, int i) {
        SharedPreferences.Editor edit = this.f90sp.edit();
        edit.putInt(str, i);
        edit.commit();
    }

    public void save(String str, long j) {
        SharedPreferences.Editor edit = this.f90sp.edit();
        edit.putLong(str, j);
        edit.commit();
    }

    public void save(String str, float f) {
        SharedPreferences.Editor edit = this.f90sp.edit();
        edit.putFloat(str, f);
        edit.commit();
    }

    public void save(String str, boolean z) {
        SharedPreferences.Editor edit = this.f90sp.edit();
        edit.putBoolean(str, z);
        edit.commit();
    }

    public String read(String str, String str2) {
        return this.f90sp.getString(str, str2);
    }

    public String read(String str) {
        return read(str, "");
    }

    public int read(String str, int i) {
        return this.f90sp.getInt(str, i);
    }

    public int readInt(String str) {
        return read(str, 0);
    }

    public long read(String str, long j) {
        return this.f90sp.getLong(str, j);
    }

    public long readLong(String str) {
        return read(str, 0);
    }

    public float read(String str, float f) {
        return this.f90sp.getFloat(str, f);
    }

    public float readFloat(String str) {
        return read(str, 0.0f);
    }

    public boolean read(String str, boolean z) {
        return this.f90sp.getBoolean(str, z);
    }

    public boolean readBoolean(String str) {
        return read(str, false);
    }
}
