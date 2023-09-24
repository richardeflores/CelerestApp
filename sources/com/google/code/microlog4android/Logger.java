package com.google.code.microlog4android;

import android.util.Log;
import com.google.code.microlog4android.appender.Appender;
import com.google.code.microlog4android.factory.DefaultAppenderFactory;
import com.google.code.microlog4android.repository.CommonLoggerRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Logger {
    public static final String DEFAULT_CLIENT_ID = "Microlog";
    public static final Level DEFAULT_LOG_LEVEL = Level.DEBUG;
    private static final String TAG = "Microlog.Logger";
    private static final List<Appender> appenderList = new ArrayList(4);
    private static boolean firstLogEvent = true;
    private static final StopWatch stopWatch = new StopWatch();
    private String clientID = DEFAULT_CLIENT_ID;
    private CommonLoggerRepository commonLoggerRepository = null;
    private Level level;
    private String name;

    public Logger(String str) {
        this.name = str;
    }

    public Logger(String str, CommonLoggerRepository commonLoggerRepository2) {
        this.name = str;
        this.commonLoggerRepository = commonLoggerRepository2;
    }

    public synchronized void setCommonRepository(CommonLoggerRepository commonLoggerRepository2) {
        this.commonLoggerRepository = commonLoggerRepository2;
    }

    public Level getLevel() {
        return this.level;
    }

    public void setLevel(Level level2) throws IllegalArgumentException {
        if (level2 != null) {
            this.level = level2;
            return;
        }
        throw new IllegalArgumentException("The level must not be null.");
    }

    public Level getEffectiveLevel() {
        Level level2 = this.level;
        if (level2 != null || this.name.equals("")) {
            return level2;
        }
        CommonLoggerRepository commonLoggerRepository2 = this.commonLoggerRepository;
        if (commonLoggerRepository2 != null) {
            return commonLoggerRepository2.getEffectiveLevel(this.name);
        }
        throw new IllegalStateException("CommonLoggerRepository has not been set");
    }

    public String getClientID() {
        return this.clientID;
    }

    public void setClientID(String str) {
        this.clientID = str;
    }

    public String getName() {
        return this.name;
    }

    public void addAppender(Appender appender) throws IllegalArgumentException {
        if (appender == null) {
            throw new IllegalArgumentException("Appender not allowed to be null");
        } else if (!appenderList.contains(appender)) {
            appenderList.add(appender);
        }
    }

    public void removeAppender(Appender appender) throws IllegalArgumentException {
        if (appender != null) {
            if (appender.isLogOpen()) {
                try {
                    appender.close();
                } catch (IOException e) {
                    Log.e(TAG, "Failed to close appender. " + e);
                }
            }
            appenderList.remove(appender);
            return;
        }
        throw new IllegalArgumentException("The appender must not be null.");
    }

    public void removeAllAppenders() {
        for (Appender next : appenderList) {
            if (next.isLogOpen()) {
                try {
                    next.close();
                } catch (IOException e) {
                    Log.e(TAG, "Failed to close appender. " + e);
                }
            }
        }
        appenderList.clear();
    }

    public int getNumberOfAppenders() {
        return appenderList.size();
    }

    public Appender getAppender(int i) {
        return appenderList.get(i);
    }

    public void log(Level level2, Object obj) throws IllegalArgumentException {
        log(level2, obj, (Throwable) null);
    }

    public void log(Level level2, Object obj, Throwable th) throws IllegalArgumentException {
        if (level2 == null) {
            throw new IllegalArgumentException("The level must not be null.");
        } else if (getEffectiveLevel().toInt() <= level2.toInt() && level2.toInt() > -1) {
            if (firstLogEvent) {
                addDefaultAppender();
                try {
                    open();
                } catch (IOException e) {
                    Log.e(TAG, "Failed to open the log. " + e);
                }
                stopWatch.start();
                firstLogEvent = false;
            }
            for (Appender doLog : appenderList) {
                doLog.doLog(this.clientID, this.name, stopWatch.getCurrentTime(), level2, obj, th);
            }
        }
    }

    private void addDefaultAppender() {
        if (appenderList.size() == 0) {
            Log.w(TAG, "Warning! No appender is set, using LogCatAppender with PatternFormatter");
            addAppender(DefaultAppenderFactory.createDefaultAppender());
        }
    }

    public boolean isTraceEnabled() {
        return getEffectiveLevel().toInt() <= 0;
    }

    public void trace(Object obj) {
        log(Level.TRACE, obj, (Throwable) null);
    }

    public void trace(Object obj, Throwable th) {
        log(Level.TRACE, obj, th);
    }

    public boolean isDebugEnabled() {
        return getEffectiveLevel().toInt() <= 1;
    }

    public void debug(Object obj) {
        log(Level.DEBUG, obj, (Throwable) null);
    }

    public void debug(Object obj, Throwable th) {
        log(Level.DEBUG, obj, th);
    }

    public boolean isInfoEnabled() {
        return getEffectiveLevel().toInt() <= 2;
    }

    public void info(Object obj) {
        log(Level.INFO, obj, (Throwable) null);
    }

    public void info(Object obj, Throwable th) {
        log(Level.INFO, obj, th);
    }

    public void warn(Object obj) {
        log(Level.WARN, obj, (Throwable) null);
    }

    public void warn(Object obj, Throwable th) {
        log(Level.WARN, obj, th);
    }

    public void error(Object obj) {
        log(Level.ERROR, obj, (Throwable) null);
    }

    public void error(Object obj, Throwable th) {
        log(Level.ERROR, obj, th);
    }

    public void fatal(Object obj) {
        log(Level.FATAL, obj, (Throwable) null);
    }

    public void fatal(Object obj, Throwable th) {
        log(Level.FATAL, obj, th);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append('[');
        for (Appender append : appenderList) {
            stringBuffer.append(append);
            stringBuffer.append(';');
        }
        stringBuffer.append(']');
        return stringBuffer.toString();
    }

    public synchronized void resetLogger() {
        appenderList.clear();
        stopWatch.stop();
        stopWatch.reset();
        firstLogEvent = true;
    }

    /* access modifiers changed from: package-private */
    public void open() throws IOException {
        for (Appender open : appenderList) {
            open.open();
        }
    }

    public void close() throws IOException {
        for (Appender close : appenderList) {
            close.close();
        }
        stopWatch.stop();
        firstLogEvent = true;
    }
}
