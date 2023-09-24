package com.huanghuang.kangshangyiliao.util;

import android.os.Environment;
import java.io.File;

public class PathUtils {
    private static String appRootName = "KangShang";
    private static String crashPathName = "crash";
    private static String sdRoot = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static String getAppRoot() {
        File file = new File(sdRoot, appRootName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static String getCrashPath() {
        File file = new File(getAppRoot(), crashPathName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static String getShareImagePath() {
        File file = new File(getAppRoot(), "share");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static String getDBPath() {
        File file = new File(getAppRoot(), "db");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
}
