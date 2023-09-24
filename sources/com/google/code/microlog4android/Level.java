package com.google.code.microlog4android;

public enum Level {
    FATAL(16),
    ERROR(8),
    WARN(4),
    INFO(2),
    DEBUG(1),
    TRACE(0),
    OFF(-1);
    
    public static final int DEBUG_INT = 1;
    public static final int ERROR_INT = 8;
    public static final int FATAL_INT = 16;
    public static final int INFO_INT = 2;
    public static final int OFF_INT = -1;
    public static final int TRACE_INT = 0;
    public static final int WARN_INT = 4;
    private final int levelValue;

    private Level(int i) {
        this.levelValue = i;
    }

    public int toInt() {
        return this.levelValue;
    }

    public String toString() {
        return name();
    }
}
