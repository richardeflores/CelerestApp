package com.google.code.microlog4android.format.command;

import com.google.code.microlog4android.Level;

public class NoFormatCommand implements FormatCommandInterface {
    private String preFormatString = "";

    public void init(String str) {
        this.preFormatString = str;
    }

    public String execute(String str, String str2, long j, Level level, Object obj, Throwable th) {
        return this.preFormatString;
    }
}
