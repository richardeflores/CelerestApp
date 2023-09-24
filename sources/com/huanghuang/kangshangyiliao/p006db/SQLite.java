package com.huanghuang.kangshangyiliao.p006db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* renamed from: com.huanghuang.kangshangyiliao.db.SQLite */
public class SQLite extends SQLiteOpenHelper {
    public static String name = "assay.db";
    private static int version = 1;

    public SQLite(Context context) {
        super(context, name, (SQLiteDatabase.CursorFactory) null, version);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table nickName (id integer primary key autoincrement,nickName varchar(50),createDate varchar(30),sex varchar(30),birthday varchar(30),phoneNumber varchar(30),height varchar(30),weight varchar(30) )");
        sQLiteDatabase.execSQL("create table doctorpatient (id integer primary key autoincrement,clinicName varchar(50) , nickName varchar(50),createDate varchar(30),sex varchar(30),birthday varchar(30),phoneNumber varchar(30),height varchar(30),weight varchar(30)  )");
        sQLiteDatabase.execSQL("create table clinicName(id integer primary key autoincrement,clinicName varchar(80),doctorName varchar(80),createDate varchar(30),sex varchar(30))");
        sQLiteDatabase.execSQL("create table deviceID(id integer primary key autoincrement,UrinalysisID varchar(80),POCTID varchar(80),ProteinID varchar(80),BloodFatID varchar(80),PrinterID varchar(80))");
        sQLiteDatabase.execSQL("create table urinalysis (id integer primary key autoincrement,deviceName varchar(80),deviceAdd varchar(30),clinicname varchar(50),nickname varchar(50),data varchar(50))");
        sQLiteDatabase.execSQL("create table poct (id integer primary key autoincrement,deviceName varchar(80),deviceAdd varchar(30),clinicname varchar(50),nickname varchar(50),data varchar(80),createDate varchar(30))");
        sQLiteDatabase.execSQL("create table protein (id integer primary key autoincrement,deviceName varchar(80),deviceAdd varchar(30),clinicname varchar(50),nickname varchar(50),data varchar(80),createDate varchar(30))");
        sQLiteDatabase.execSQL("create table bloodFat (id integer primary key autoincrement,deviceName varchar(80),deviceAdd varchar(30),clinicname varchar(50),nickname varchar(50),data varchar(80),createDate varchar(30),unitType varchar(30))");
        sQLiteDatabase.execSQL("create table immunofluorescence (id integer primary key autoincrement,deviceName varchar(80),deviceAdd varchar(30),clinicname varchar(50),nickname varchar(50),data varchar(80),createDate varchar(30))");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("create table nickName (id integer primary key autoincrement,nickName varchar(50),createDate varchar(30),sex varchar(30),birthday varchar(30),phoneNumber varchar(30),height varchar(30),weight varchar(30)  )");
        sQLiteDatabase.execSQL("create table doctorpatient (id integer primary key autoincrement,clinicName varchar(50) , nickName varchar(50),createDate varchar(30),sex varchar(30),birthday varchar(30),phoneNumber varchar(30),height varchar(30),weight varchar(30)  )");
        sQLiteDatabase.execSQL("create table clinicName(id integer primary key autoincrement,clinicName varchar(80),doctorName varchar(80),createDate varchar(30),sex varchar(30))");
        sQLiteDatabase.execSQL("create table urinalysis (id integer primary key autoincrement,deviceName varchar(80),deviceAdd varchar(30),clinicname varchar(50),nickname varchar(50),data varchar(50))");
        sQLiteDatabase.execSQL("create table poct (id integer primary key autoincrement,deviceName varchar(80),deviceAdd varchar(30),clinicname varchar(50),nickname varchar(50),data varchar(80),createDate varchar(30))");
        sQLiteDatabase.execSQL("create table protein (id integer primary key autoincrement,deviceName varchar(80),deviceAdd varchar(30),clinicname varchar(50),nickname varchar(50),data varchar(80),createDate varchar(30))");
        sQLiteDatabase.execSQL("create table poct (id integer primary key autoincrement,deviceName varchar(80),deviceAdd varchar(30),clinicname varchar(50),nickname varchar(50),data varchar(80),createDate varchar(30),unitType varchar(30))");
        sQLiteDatabase.execSQL("create table immunofluorescence (id integer primary key autoincrement,deviceName varchar(80),deviceAdd varchar(30),clinicname varchar(50),nickname varchar(50),data varchar(80),createDate varchar(30))");
    }
}
