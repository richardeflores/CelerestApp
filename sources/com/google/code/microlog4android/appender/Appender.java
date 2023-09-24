package com.google.code.microlog4android.appender;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.format.Formatter;
import java.io.IOException;

public interface Appender {
    public static final int SIZE_UNDEFINED = -1;

    void clear();

    void close() throws IOException;

    void doLog(String str, String str2, long j, Level level, Object obj, Throwable th);

    Formatter getFormatter();

    long getLogSize();

    boolean isLogOpen();

    void open() throws IOException;

    void setFormatter(Formatter formatter);
}
