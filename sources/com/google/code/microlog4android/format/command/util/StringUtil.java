package com.google.code.microlog4android.format.command.util;

public enum StringUtil {
    ;

    public static String extractPartialClassName(String str, int i) {
        int lastIndexOf = str.lastIndexOf(46);
        int i2 = lastIndexOf != -1 ? 1 : 0;
        while (i2 < i && lastIndexOf > -1) {
            lastIndexOf = str.lastIndexOf(46, lastIndexOf - 1);
            if (lastIndexOf != -1) {
                i2++;
            }
        }
        return (lastIndexOf <= -1 || i2 > i) ? str : str.substring(lastIndexOf + 1);
    }
}
