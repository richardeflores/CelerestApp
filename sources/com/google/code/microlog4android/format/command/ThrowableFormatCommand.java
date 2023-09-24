package com.google.code.microlog4android.format.command;

import com.google.code.microlog4android.Level;

public class ThrowableFormatCommand implements FormatCommandInterface {
    public void init(String str) {
    }

    public String execute(String str, String str2, long j, Level level, Object obj, Throwable th) {
        StringBuilder sb = new StringBuilder();
        if (th != null) {
            sb.append(th.toString());
            String property = System.getProperty("line.separator");
            StackTraceElement[] stackTrace = th.getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                sb.append(property);
                sb.append("\tat ");
                sb.append(stackTraceElement.toString());
            }
        }
        return sb.toString();
    }
}
