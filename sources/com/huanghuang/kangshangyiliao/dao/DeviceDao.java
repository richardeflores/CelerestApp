package com.huanghuang.kangshangyiliao.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.huanghuang.kangshangyiliao.dao.bean.DeviceID;
import java.util.ArrayList;
import java.util.List;

public class DeviceDao extends BaseDao {
    public DeviceDao() {
        super("deviceID");
    }

    public void save(DeviceID deviceID) {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UrinalysisID", deviceID.UrinalysisID);
        contentValues.put("POCTID", deviceID.POCTID);
        contentValues.put("ProteinID", deviceID.ProteinID);
        contentValues.put("BloodFatID", deviceID.BloodFatID);
        contentValues.put("PrinterID", deviceID.PrinterID);
        writableDatabase.insert(this.table, (String) null, contentValues);
        writableDatabase.close();
    }

    public void update(DeviceID deviceID) {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UrinalysisID", deviceID.UrinalysisID);
        contentValues.put("POCTID", deviceID.POCTID);
        contentValues.put("ProteinID", deviceID.ProteinID);
        contentValues.put("BloodFatID", deviceID.BloodFatID);
        contentValues.put("PrinterID", deviceID.PrinterID);
        String str = this.table;
        writableDatabase.update(str, contentValues, "id=" + deviceID.f78id, new String[0]);
        writableDatabase.close();
    }

    public List<DeviceID> queryAll() {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase readableDatabase = this.sqLite.getReadableDatabase();
        Cursor query = readableDatabase.query(this.table, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, "id asc");
        while (query.moveToNext()) {
            arrayList.add(getBean(query));
        }
        query.close();
        readableDatabase.close();
        return arrayList;
    }

    public DeviceID query(String str) {
        SQLiteDatabase readableDatabase = this.sqLite.getReadableDatabase();
        Cursor query = readableDatabase.query(this.table, (String[]) null, "deviceID=?", new String[]{str}, (String) null, (String) null, (String) null);
        DeviceID bean = query.moveToNext() ? getBean(query) : null;
        query.close();
        readableDatabase.close();
        return bean;
    }

    public DeviceID queryById(String str) {
        SQLiteDatabase readableDatabase = this.sqLite.getReadableDatabase();
        Cursor query = readableDatabase.query(this.table, (String[]) null, "id=?", new String[]{str}, (String) null, (String) null, (String) null);
        DeviceID bean = query.moveToNext() ? getBean(query) : null;
        query.close();
        readableDatabase.close();
        return bean;
    }

    public boolean exists(String str, int i) {
        String str2;
        SQLiteDatabase readableDatabase = this.sqLite.getReadableDatabase();
        if (i != -1) {
            str2 = "deviceID=?" + " and id !=" + i;
        } else {
            str2 = "deviceID=?";
        }
        boolean z = true;
        Cursor query = readableDatabase.query(this.table, (String[]) null, str2, new String[]{str}, (String) null, (String) null, (String) null);
        if (query.getCount() <= 0) {
            z = false;
        }
        query.close();
        readableDatabase.close();
        return z;
    }

    public void delete(String str) {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        writableDatabase.delete(this.table, "clinicName=?", new String[]{str});
        writableDatabase.close();
    }

    private DeviceID getBean(Cursor cursor) {
        DeviceID deviceID = new DeviceID();
        deviceID.f78id = cursor.getInt(0);
        deviceID.UrinalysisID = cursor.getString(1);
        deviceID.POCTID = cursor.getString(2);
        deviceID.ProteinID = cursor.getString(3);
        deviceID.BloodFatID = cursor.getString(4);
        deviceID.PrinterID = cursor.getString(4);
        return deviceID;
    }
}
