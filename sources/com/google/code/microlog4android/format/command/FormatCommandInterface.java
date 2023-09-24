package com.google.code.microlog4android.format.command;

import com.google.code.microlog4android.Level;

public interface FormatCommandInterface {
    String execute(String str, String str2, long j, Level level, Object obj, Throwable th);

    void init(String str);
}
