package com.huanghuang.kangshangyiliao.bean;

import com.google.code.microlog4android.format.SimpleFormatter;
import com.huanghuang.kangshangyiliao.util.Utils;

public class UrinalysisResult {
    public int[] data = new int[14];
    public int day;
    public boolean[] effectiveTp = new boolean[11];
    public int hour;
    public int minute;
    public int month;

    /* renamed from: pf */
    public int f74pf;

    /* renamed from: sn */
    public String f75sn;
    public int year;

    public String getDate() {
        return "20" + this.year + SimpleFormatter.DEFAULT_DELIMITER + getDateString(this.month) + SimpleFormatter.DEFAULT_DELIMITER + getDateString(this.day) + " " + getDateString(this.hour) + ":" + getDateString(this.minute);
    }

    private String getDateString(int i) {
        return Utils.addZero(i + "", 2);
    }
}
