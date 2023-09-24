package com.google.code.microlog4android.repository;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.Logger;

public interface LoggerRepository {
    boolean contains(String str);

    Logger getLogger(String str);

    Logger getRootLogger();

    int numberOfLeafNodes();

    void reset();

    void setLevel(String str, Level level);

    void shutdown();
}
