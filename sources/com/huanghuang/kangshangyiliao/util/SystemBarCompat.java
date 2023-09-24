package com.huanghuang.kangshangyiliao.util;

import android.os.Build;
import android.view.View;
import android.view.Window;

public class SystemBarCompat {
    public static void tint(Window window, View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            window.clearFlags(201326592);
            window.getDecorView().setSystemUiVisibility(1792);
            view.setFitsSystemWindows(true);
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(0);
        } else if (Build.VERSION.SDK_INT >= 19) {
            window.getAttributes().flags |= 67108864;
            view.setFitsSystemWindows(true);
        }
    }
}
