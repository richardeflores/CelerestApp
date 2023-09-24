package com.huanghuang.kangshangyiliao.ioam;

public interface IOAndMain<T> {
    T onIo();

    void onMain(T t);
}
