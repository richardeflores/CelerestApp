package com.huanghuang.kangshangyiliao.app.utils;

import com.huanghuang.kangshangyiliao.app.base.AppBase;

public class AppUtils {
    private static final AppBase appBase = AppBase.getInstance();

    public static String getString(int i) {
        return appBase.getContext().getString(i);
    }
}
