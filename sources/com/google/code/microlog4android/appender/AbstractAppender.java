package com.google.code.microlog4android.appender;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.format.Formatter;
import com.google.code.microlog4android.format.SimpleFormatter;
import java.io.IOException;

public abstract class AbstractAppender implements Appender {
    protected Formatter formatter = new SimpleFormatter();
    protected boolean logOpen;

    public abstract void clear();

    public abstract void close() throws IOException;

    public abstract void doLog(String str, String str2, long j, Level level, Object obj, Throwable th);

    public abstract void open() throws IOException;

    public final void setFormatter(Formatter formatter2) throws IllegalArgumentException {
        if (formatter2 != null) {
            this.formatter = formatter2;
            return;
        }
        throw new IllegalArgumentException("The formatter must not be null.");
    }

    public final Formatter getFormatter() {
        return this.formatter;
    }

    public boolean isLogOpen() {
        return this.logOpen;
    }
}
