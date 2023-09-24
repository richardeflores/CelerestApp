package com.huanghuang.kangshangyiliao.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.util.Cache;
import com.huanghuang.kangshangyiliao.util.Constants;
import com.huanghuang.kangshangyiliao.util.Utils;
import java.util.ArrayList;
import java.util.List;

public class NickNameDao extends BaseDao {
    public NickNameDao() {
        super("nickName");
    }

    public void save(NickName nickName) {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nickName", nickName.nickName);
        contentValues.put("createDate", nickName.createDate);
        contentValues.put("sex", nickName.sex);
        contentValues.put("birthday", nickName.birthday);
        contentValues.put("phoneNumber", nickName.phoneNumber);
        contentValues.put("height", nickName.height);
        contentValues.put("weight", nickName.weight);
        writableDatabase.insert(this.table, (String) null, contentValues);
        writableDatabase.close();
    }

    public void update(NickName nickName) {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nickName", nickName.nickName);
        contentValues.put("createDate", nickName.createDate);
        contentValues.put("sex", nickName.sex);
        contentValues.put("birthday", nickName.birthday);
        contentValues.put("phoneNumber", nickName.phoneNumber);
        contentValues.put("height", nickName.height);
        contentValues.put("weight", nickName.weight);
        String str = this.table;
        writableDatabase.update(str, contentValues, "id=" + nickName.f81id, new String[0]);
        writableDatabase.close();
    }

    public List<NickName> queryAll() {
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

    public NickName query(String str) {
        SQLiteDatabase readableDatabase = this.sqLite.getReadableDatabase();
        Cursor query = readableDatabase.query(this.table, (String[]) null, "nickname=?", new String[]{str}, (String) null, (String) null, (String) null);
        NickName bean = query.moveToNext() ? getBean(query) : null;
        query.close();
        readableDatabase.close();
        return bean;
    }

    public NickName queryById(String str) {
        SQLiteDatabase readableDatabase = this.sqLite.getReadableDatabase();
        Cursor query = readableDatabase.query(this.table, (String[]) null, "id=?", new String[]{str}, (String) null, (String) null, (String) null);
        NickName bean = query.moveToNext() ? getBean(query) : null;
        query.close();
        readableDatabase.close();
        return bean;
    }

    public boolean exists_name(String str, int i) {
        String str2;
        SQLiteDatabase readableDatabase = this.sqLite.getReadableDatabase();
        if (i != -1) {
            str2 = "nickname=?" + " and id !=" + i;
        } else {
            str2 = "nickname=?";
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
        NickName nickName = new NickName();
        nickName.nickName = Constants.DEFAULT_NICK_NAME;
        nickName.createDate = Utils.getDate();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nickName", nickName.nickName);
        contentValues.put("createDate", nickName.createDate);
        writableDatabase.insert("nickName", (String) null, contentValues);
        writableDatabase.close();
        Cache.getInstance().save(Cache.NICK_NAME, nickName.nickName);
    }

    public void delete(String str) {
        SQLiteDatabase writableDatabase = this.sqLite.getWritableDatabase();
        writableDatabase.delete(this.table, "nickName=?", new String[]{str});
        writableDatabase.close();
    }

    private NickName getBean(Cursor cursor) {
        NickName nickName = new NickName();
        nickName.f81id = cursor.getInt(0);
        nickName.nickName = cursor.getString(1);
        nickName.createDate = cursor.getString(2);
        nickName.sex = cursor.getString(3);
        nickName.birthday = cursor.getString(4);
        nickName.phoneNumber = cursor.getString(5);
        nickName.height = cursor.getString(6);
        nickName.weight = cursor.getString(7);
        return nickName;
    }
}
