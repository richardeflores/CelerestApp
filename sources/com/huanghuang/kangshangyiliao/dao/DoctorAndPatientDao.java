package com.huanghuang.kangshangyiliao.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.huanghuang.kangshangyiliao.dao.bean.DoctorAndPatientBean;
import java.util.ArrayList;
import java.util.List;

public class DoctorAndPatientDao extends BaseDao {
    public DoctorAndPatientDao() {
        super("doctorpatient");
    }

    public void save(DoctorAndPatientBean doctorAndPatientBean) {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("clinicName", doctorAndPatientBean.clinicName);
        contentValues.put("nickName", doctorAndPatientBean.nickName);
        contentValues.put("createDate", doctorAndPatientBean.createDate);
        contentValues.put("sex", doctorAndPatientBean.sex);
        contentValues.put("birthday", doctorAndPatientBean.birthday);
        contentValues.put("phoneNumber", doctorAndPatientBean.phoneNumber);
        contentValues.put("height", doctorAndPatientBean.height);
        contentValues.put("weight", doctorAndPatientBean.weight);
        writableDatabase.insert(this.table, (String) null, contentValues);
        writableDatabase.close();
    }

    public void update(DoctorAndPatientBean doctorAndPatientBean) {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("clinicName", doctorAndPatientBean.clinicName);
        contentValues.put("nickName", doctorAndPatientBean.nickName);
        contentValues.put("createDate", doctorAndPatientBean.createDate);
        contentValues.put("sex", doctorAndPatientBean.sex);
        contentValues.put("birthday", doctorAndPatientBean.birthday);
        contentValues.put("phoneNumber", doctorAndPatientBean.phoneNumber);
        contentValues.put("height", doctorAndPatientBean.height);
        contentValues.put("weight", doctorAndPatientBean.weight);
        String str = this.table;
        writableDatabase.update(str, contentValues, "id=" + doctorAndPatientBean.f79id, new String[0]);
        writableDatabase.close();
    }

    public List<DoctorAndPatientBean> queryAll() {
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

    public DoctorAndPatientBean query(String str) {
        SQLiteDatabase readableDatabase = this.sqLite.getReadableDatabase();
        Cursor query = readableDatabase.query(this.table, (String[]) null, "clinicName=?", new String[]{str}, (String) null, (String) null, (String) null);
        DoctorAndPatientBean bean = query.moveToNext() ? getBean(query) : null;
        query.close();
        readableDatabase.close();
        return bean;
    }

    public DoctorAndPatientBean queryById(String str) {
        SQLiteDatabase readableDatabase = this.sqLite.getReadableDatabase();
        Cursor query = readableDatabase.query(this.table, (String[]) null, "id=?", new String[]{str}, (String) null, (String) null, (String) null);
        DoctorAndPatientBean bean = query.moveToNext() ? getBean(query) : null;
        query.close();
        readableDatabase.close();
        return bean;
    }

    public void delete(String str) {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        writableDatabase.delete(this.table, "nickName=?", new String[]{str});
        writableDatabase.close();
    }

    private DoctorAndPatientBean getBean(Cursor cursor) {
        DoctorAndPatientBean doctorAndPatientBean = new DoctorAndPatientBean();
        doctorAndPatientBean.f79id = cursor.getInt(0);
        doctorAndPatientBean.clinicName = cursor.getString(1);
        doctorAndPatientBean.nickName = cursor.getString(2);
        doctorAndPatientBean.createDate = cursor.getString(3);
        doctorAndPatientBean.sex = cursor.getString(4);
        doctorAndPatientBean.birthday = cursor.getString(5);
        doctorAndPatientBean.phoneNumber = cursor.getString(6);
        doctorAndPatientBean.height = cursor.getString(7);
        doctorAndPatientBean.weight = cursor.getString(8);
        return doctorAndPatientBean;
    }
}
