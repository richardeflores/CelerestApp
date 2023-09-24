package com.google.code.microlog4android.repository;

public enum LoggerNamesUtil {
    ;
    
    private static final String EMPTY = "";

    public static String[] getLoggerNameComponents(String str) {
        return str.split("\\.");
    }

    public static String getClassName(String[] strArr) {
        return strArr.length > 0 ? strArr[strArr.length - 1] : "";
    }
}
