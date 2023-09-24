package com.huanghuang.kangshangyiliao.bean;

public class GridView_Icon_add {
    private int iId;
    private String iName;

    public GridView_Icon_add() {
    }

    public GridView_Icon_add(int i, String str) {
        this.iId = i;
        this.iName = str;
    }

    public int getiId() {
        return this.iId;
    }

    public String getiName() {
        return this.iName;
    }

    public void setiId(int i) {
        this.iId = i;
    }

    public void setiName(String str) {
        this.iName = str;
    }
}
