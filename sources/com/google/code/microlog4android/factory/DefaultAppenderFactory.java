package com.google.code.microlog4android.factory;

import com.google.code.microlog4android.appender.Appender;
import com.google.code.microlog4android.appender.LogCatAppender;
import com.google.code.microlog4android.format.PatternFormatter;

public enum DefaultAppenderFactory {
    ;

    public static Appender createDefaultAppender() {
        LogCatAppender logCatAppender = new LogCatAppender();
        logCatAppender.setFormatter(new PatternFormatter());
        return logCatAppender;
    }
}
