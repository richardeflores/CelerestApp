package com.huanghuang.kangshangyiliao.base;

import com.google.gson.Gson;

public class BaseBean {
    public String toString() {
        return new Gson().toJson((Object) this);
    }
}
