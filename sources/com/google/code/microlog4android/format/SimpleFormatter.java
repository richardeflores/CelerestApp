package com.google.code.microlog4android.format;

import com.google.code.microlog4android.Level;

public final class SimpleFormatter implements Formatter {
    public static final String DEFAULT_DELIMITER = "-";
    private static final int INITIAL_BUFFER_SIZE = 256;
    StringBuffer buffer = new StringBuffer(256);
    private String delimiter = DEFAULT_DELIMITER;

    public String[] getPropertyNames() {
        return null;
    }

    public void setProperty(String str, String str2) {
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimeter(String str) {
        this.delimiter = str;
    }

    public String format(String str, String str2, long j, Level level, Object obj, Throwable th) {
        if (this.buffer.length() > 0) {
            StringBuffer stringBuffer = this.buffer;
            stringBuffer.delete(0, stringBuffer.length());
        }
        if (str != null) {
            this.buffer.append(str);
            this.buffer.append(' ');
        }
        this.buffer.append(j);
        this.buffer.append(':');
        if (level != null) {
            this.buffer.append('[');
            this.buffer.append(level);
            this.buffer.append(']');
        }
        if (obj != null) {
            this.buffer.append(this.delimiter);
            this.buffer.append(obj);
        }
        if (th != null) {
            this.buffer.append(this.delimiter);
            this.buffer.append(th);
        }
        return this.buffer.toString();
    }
}
