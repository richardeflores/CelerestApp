package com.huanghuang.kangshangyiliao.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import com.huanghuang.kangshangyiliao.util.Cache;
import com.huanghuang.kangshangyiliao.util.Constants;
import com.huanghuang.kangshangyiliao.util.Utils;
import java.util.ArrayList;
import java.util.List;

public class ClinicNameDao extends BaseDao {
    public ClinicNameDao() {
        super("clinicName");
    }

    public void save(ClinicName clinicName) {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("clinicName", clinicName.clinicName);
        contentValues.put("doctorName", clinicName.doctorName);
        contentValues.put("createDate", clinicName.createDate);
        contentValues.put("sex", clinicName.sex);
        writableDatabase.insert(this.table, (String) null, contentValues);
        writableDatabase.close();
    }

    public void update(ClinicName clinicName) {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("clinicName", clinicName.clinicName);
        contentValues.put("doctorName", clinicName.doctorName);
        contentValues.put("createDate", clinicName.createDate);
        contentValues.put("sex", clinicName.sex);
        String str = this.table;
        writableDatabase.update(str, contentValues, "id=" + clinicName.f77id, new String[0]);
        writableDatabase.close();
    }

    public List<ClinicName> queryAll() {
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

    public ClinicName query(String str) {
        SQLiteDatabase readableDatabase = this.sqLite.getReadableDatabase();
        Cursor query = readableDatabase.query(this.table, (String[]) null, "clinicName=?", new String[]{str}, (String) null, (String) null, (String) null);
        ClinicName bean = query.moveToNext() ? getBean(query) : null;
        query.close();
        readableDatabase.close();
        return bean;
    }

    public ClinicName queryById(String str) {
        SQLiteDatabase readableDatabase = this.sqLite.getReadableDatabase();
        Cursor query = readableDatabase.query(this.table, (String[]) null, "id=?", new String[]{str}, (String) null, (String) null, (String) null);
        ClinicName bean = query.moveToNext() ? getBean(query) : null;
        query.close();
        readableDatabase.close();
        return bean;
    }

    public boolean exists(String str, int i) {
        String str2;
        SQLiteDatabase readableDatabase = this.sqLite.getReadableDatabase();
        if (i != -1) {
            str2 = "clinicName=?" + " and id !=" + i;
        } else {
            str2 = "clinicName=?";
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

    public void inertDefaultData() {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        ClinicName clinicName = new ClinicName();
        clinicName.clinicName = Constants.DEFAULT_NICK_NAME;
        clinicName.createDate = Utils.getDate();
        ContentValues contentValues = new ContentValues();
        contentValues.put("clinicName", clinicName.clinicName);
        contentValues.put("createDate", clinicName.createDate);
        writableDatabase.insert("clinicName", (String) null, contentValues);
        writableDatabase.close();
        Cache.getInstance().save(Cache.CLINIC_INFO, clinicName.clinicName);
    }

    public void delete(String str) {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        writableDatabase.delete(this.table, "clinicName=?", new String[]{str});
        writableDatabase.close();
    }

    private ClinicName getBean(Cursor cursor) {
        ClinicName clinicName = new ClinicName();
        clinicName.f77id = cursor.getInt(0);
        clinicName.clinicName = cursor.getString(1);
        clinicName.doctorName = cursor.getString(2);
        clinicName.createDate = cursor.getString(3);
        clinicName.sex = cursor.getString(4);
        return clinicName;
    }
}
