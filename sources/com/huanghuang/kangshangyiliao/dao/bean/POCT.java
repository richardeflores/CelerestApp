package com.huanghuang.kangshangyiliao.dao.bean;

import android.text.TextUtils;
import java.io.Serializable;

public class POCT implements Serializable {
    private static final long serialVersionUID = 1;
    public String clinicName;
    public String createDate;
    public String data;
    public String deviceAdd;
    public String deviceName;

    /* renamed from: id */
    public int f82id;
    public String nickname;

    public String getCreateDate() {
        return TextUtils.isEmpty(this.createDate) ? "" : this.createDate;
    }
}
