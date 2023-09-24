package com.google.code.microlog4android.format;

import com.google.code.microlog4android.Level;

public interface Formatter {
    String format(String str, String str2, long j, Level level, Object obj, Throwable th);

    String[] getPropertyNames();

    void setProperty(String str, String str2);
}
