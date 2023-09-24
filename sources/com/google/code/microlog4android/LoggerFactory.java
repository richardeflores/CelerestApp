package com.google.code.microlog4android;

import com.google.code.microlog4android.factory.DefaultRepositoryFactory;
import com.google.code.microlog4android.repository.LoggerRepository;

public class LoggerFactory {
    private static final LoggerRepository loggerRepository = DefaultRepositoryFactory.getDefaultLoggerRepository();

    public static Logger getLogger() {
        return loggerRepository.getRootLogger();
    }

    public static Logger getLogger(String str) {
        if (str != null) {
            return loggerRepository.getLogger(str);
        }
        throw new IllegalArgumentException("The Logger name must not be null.");
    }

    public static Logger getLogger(Class<?> cls) {
        if (cls != null) {
            return getLogger(cls.getName());
        }
        throw new IllegalArgumentException("The clazz must not be null.");
    }

    public static void shutdown() {
        loggerRepository.shutdown();
    }

    public static LoggerRepository getLoggerRepository() {
        return loggerRepository;
    }
}
