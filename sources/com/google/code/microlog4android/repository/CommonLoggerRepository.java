package com.google.code.microlog4android.repository;

import com.google.code.microlog4android.Level;

public interface CommonLoggerRepository {
    Level getEffectiveLevel(String str);
}
