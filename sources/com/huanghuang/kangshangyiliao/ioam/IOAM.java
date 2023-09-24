package com.huanghuang.kangshangyiliao.ioam;

import android.os.Handler;

public class IOAM {
    public static <T> void toMain(Main<T> main) {
        toMain(main, (Object) null);
    }

    public static <T> void toMain(final Main<T> main, final T t) {
        new Handler().post(new Runnable() {
            public void run() {
                main.onMain(t);
            }
        });
    }
}
