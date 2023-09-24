package com.huanghuang.kangshangyiliao.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import com.huanghuang.kangshangyiliao.dao.bean.Protein;
import com.huanghuang.kangshangyiliao.p006db.SQLite;
import com.huanghuang.kangshangyiliao.util.Cache;
import com.huanghuang.kangshangyiliao.util.Utils;
import java.util.ArrayList;
import java.util.List;

public class ProteinDao {
    public static final String table = "protein";
    private AppBase appBase = AppBase.getInstance();
    private SQLite sqLite = new SQLite(this.appBase.getContext());

    public void save(Protein protein) {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("deviceName", protein.deviceName);
        contentValues.put("deviceAdd", protein.deviceAdd);
        contentValues.put("clinicname", protein.clinicName);
        contentValues.put(Cache.NICK_NAME, protein.nickname);
        contentValues.put("data", protein.data);
        contentValues.put("createDate", protein.createDate);
        writableDatabase.insert(table, (String) null, contentValues);
        writableDatabase.close();
    }

    public List<Protein> queryAll() {
        String str;
        ClinicName clinicInfo = Utils.getClinicInfo();
        if (clinicInfo != null) {
            str = clinicInfo.f77id + "";
        } else {
            str = "-1";
        }
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase readableDatabase = this.sqLite.getReadableDatabase();
        Cursor query = readableDatabase.query(table, (String[]) null, "clinicname=?", new String[]{str}, (String) null, (String) null, "id desc");
        while (query.moveToNext()) {
            Protein protein = new Protein();
            protein.f84id = query.getInt(0);
            protein.deviceName = query.getString(1);
            protein.deviceAdd = query.getString(2);
            protein.clinicName = query.getString(3);
            protein.nickname = query.getString(4);
            protein.data = query.getString(5);
            protein.createDate = query.getString(6);
            arrayList.add(protein);
        }
        query.close();
        readableDatabase.close();
        return arrayList;
    }

    public boolean exists(String str) {
        SQLiteDatabase readableDatabase = this.sqLite.getReadableDatabase();
        boolean z = true;
        Cursor query = readableDatabase.query(table, (String[]) null, "data=?", new String[]{str}, (String) null, (String) null, (String) null);
        if (query.getCount() <= 0) {
            z = false;
        }
        query.close();
        readableDatabase.close();
        return z;
    }

    public void clear() {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        writableDatabase.delete(table, (String) null, (String[]) null);
        writableDatabase.close();
    }

    public void delete(int i) {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        writableDatabase.delete(table, "id=?", new String[]{i + ""});
        writableDatabase.close();
    }

    public void delete(String str) {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        writableDatabase.delete(table, "clinicname=?", new String[]{str});
        writableDatabase.close();
    }

    public void delete(List<Integer> list) {
        StringBuilder sb = new StringBuilder("id in(");
        for (Integer num : list) {
            sb.append(num + ",");
        }
        if (list.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(")");
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        writableDatabase.delete(table, sb.toString(), new String[0]);
        writableDatabase.close();
    }
}
