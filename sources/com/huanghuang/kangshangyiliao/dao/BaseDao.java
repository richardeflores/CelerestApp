package com.huanghuang.kangshangyiliao.dao;

import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.p006db.SQLite;

public class BaseDao {
    protected AppBase appBase = AppBase.getInstance();
    protected SQLite sqLite;
    protected final String table;

    public BaseDao(String str) {
        this.table = str;
        this.sqLite = new SQLite(this.appBase.getContext());
    }
}
