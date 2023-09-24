package com.google.code.microlog4android.format.command;

import com.google.code.microlog4android.Level;

public class PriorityFormatCommand implements FormatCommandInterface {
    public void init(String str) {
    }

    public String execute(String str, String str2, long j, Level level, Object obj, Throwable th) {
        return level != null ? level.toString() : "";
    }
}
