package com.google.code.microlog4android.config;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.appender.Appender;
import com.google.code.microlog4android.appender.FileAppender;
import com.google.code.microlog4android.format.Formatter;
import com.google.code.microlog4android.format.PatternFormatter;
import com.google.code.microlog4android.repository.DefaultLoggerRepository;
import com.google.code.microlog4android.repository.LoggerRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

public class PropertyConfigurator {
    public static final String[] APPENDER_ALIASES = {"LogCatAppender", "FileAppender"};
    public static final String[] APPENDER_CLASS_NAMES = {"com.google.code.microlog4android.appender.LogCatAppender", "com.google.code.microlog4android.appender.FileAppender"};
    public static final String APPENDER_PREFIX_KEY = "microlog.appender";
    public static String DEFAULT_PROPERTIES_FILENAME = "microlog.properties";
    public static final String FILE_APPENDER_APPEND_KEY = "microlog.appender.FileAppender.Append";
    public static final String FILE_APPENDER_FILE_NAME_KEY = "microlog.appender.FileAppender.File";
    public static final String[] FORMATTER_ALIASES = {"SimpleFormatter", "PatternFormatter"};
    public static final String[] FORMATTER_CLASS_NAMES = {"com.google.code.microlog4android.format.SimpleFormatter", "com.google.code.microlog4android.format.PatternFormatter"};
    public static final String FORMATTER_PREFIX_KEY = "microlog.formatter";
    public static final String LOGGER_PREFIX_KEY = "microlog.logger";
    public static final String LOG_LEVEL_PREFIX_KEY = "microlog.level";
    public static final String MICROLOG_PREFIX = "microlog";
    public static final String PATTERN_LAYOUT_PREFIX_KEY = "microlog.formatter.PatternFormatter.pattern";
    public static final String ROOT_LOGGER_KEY = "microlog.rootLogger";
    private static final String TAG = "Microlog.PropertyConfiguration";
    public static final String TAG_PREFIX_KEY = "microlog.tag";
    private static final HashMap<String, String> appenderAliases = new HashMap<>(43);
    private static final HashMap<String, String> formatterAliases = new HashMap<>(21);
    private Context context;
    private LoggerRepository loggerRepository;

    private PropertyConfigurator(Context context2) {
        int i = 0;
        int i2 = 0;
        while (true) {
            String[] strArr = APPENDER_ALIASES;
            if (i2 >= strArr.length) {
                break;
            }
            appenderAliases.put(strArr[i2], APPENDER_CLASS_NAMES[i2]);
            i2++;
        }
        while (true) {
            String[] strArr2 = FORMATTER_ALIASES;
            if (i >= strArr2.length) {
                this.context = context2;
                this.loggerRepository = DefaultLoggerRepository.INSTANCE;
                return;
            }
            formatterAliases.put(strArr2[i], FORMATTER_CLASS_NAMES[i]);
            i++;
        }
    }

    public static PropertyConfigurator getConfigurator(Context context2) {
        if (context2 != null) {
            return new PropertyConfigurator(context2);
        }
        throw new IllegalArgumentException("The context must not be null");
    }

    public void configure() {
        configure(DEFAULT_PROPERTIES_FILENAME);
    }

    public void configure(String str) {
        try {
            startConfiguration(loadProperties(this.context.getResources().getAssets().open(str)));
        } catch (IOException e) {
            Log.e(TAG, "Failed to open the microlog properties file. Hint: the file should be in the /assets directory " + str + " " + e);
        }
    }

    public void configure(int i) {
        try {
            startConfiguration(loadProperties(this.context.getResources().openRawResource(i)));
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Did not find the microlog properties resource. Hint: this should be in the /res/raw directory " + e);
        } catch (IOException e2) {
            Log.e(TAG, "Failed to read the microlog properties resource." + e2);
        }
    }

    private Properties loadProperties(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    private void startConfiguration(Properties properties) {
        if (properties.containsKey(ROOT_LOGGER_KEY)) {
            Log.i(TAG, "Modern configuration not yet supported");
            return;
        }
        Log.i(TAG, "Configure using the simple style (aka classic style)");
        configureSimpleStyle(properties);
    }

    private void configureSimpleStyle(Properties properties) {
        setLevel(properties);
        setAppenders(parseAppenderString(properties.getProperty(APPENDER_PREFIX_KEY, "LogCatAppender")), properties);
        setFormatter(properties);
    }

    private void setLevel(Properties properties) {
        Level stringToLevel = stringToLevel((String) properties.get(LOG_LEVEL_PREFIX_KEY));
        if (stringToLevel != null) {
            this.loggerRepository.getRootLogger().setLevel(stringToLevel);
            Log.i(TAG, "Root level: " + this.loggerRepository.getRootLogger().getLevel());
        }
    }

    private List<String> parseAppenderString(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ";,");
        ArrayList arrayList = new ArrayList();
        while (stringTokenizer.hasMoreElements()) {
            arrayList.add((String) stringTokenizer.nextElement());
        }
        return arrayList;
    }

    private void setAppenders(List<String> list, Properties properties) {
        for (String addAppender : list) {
            addAppender(addAppender, properties);
        }
    }

    private void addAppender(String str, Properties properties) {
        Logger rootLogger = this.loggerRepository.getRootLogger();
        String str2 = appenderAliases.get(str);
        if (str2 != null) {
            str = str2;
        }
        try {
            Appender appender = (Appender) Class.forName(str).newInstance();
            if (appender != null) {
                if (appender instanceof FileAppender) {
                    setPropertiesForFileAppender(appender, properties);
                }
                Log.i(TAG, "Adding appender " + appender.getClass().getName());
                rootLogger.addAppender(appender);
            }
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Failed to find appender class: " + e);
        } catch (IllegalAccessException e2) {
            Log.e(TAG, "No access to appender class: " + e2);
        } catch (InstantiationException e3) {
            Log.e(TAG, "Failed to instantiate appender class: " + e3);
        } catch (ClassCastException e4) {
            Log.e(TAG, "Specified appender class does not implement the Appender interface: " + e4);
        }
    }

    private void setPropertiesForFileAppender(Appender appender, Properties properties) {
        FileAppender fileAppender = (FileAppender) appender;
        fileAppender.setFileName(properties.getProperty(FILE_APPENDER_FILE_NAME_KEY, FileAppender.DEFAULT_FILENAME));
        fileAppender.setAppend(Boolean.parseBoolean(properties.getProperty(FILE_APPENDER_APPEND_KEY, "true")));
    }

    private void setFormatter(Properties properties) {
        String property = properties.getProperty(FORMATTER_PREFIX_KEY, "PatternFormatter");
        String str = property != null ? formatterAliases.get(property) : null;
        if (str != null) {
            property = str;
        }
        try {
            Formatter formatter = (Formatter) Class.forName(property).newInstance();
            if (formatter instanceof PatternFormatter) {
                ((PatternFormatter) formatter).setPattern(properties.getProperty(PATTERN_LAYOUT_PREFIX_KEY, PatternFormatter.DEFAULT_CONVERSION_PATTERN));
            }
            if (formatter != null) {
                Logger rootLogger = this.loggerRepository.getRootLogger();
                int numberOfAppenders = rootLogger.getNumberOfAppenders();
                for (int i = 0; i < numberOfAppenders; i++) {
                    rootLogger.getAppender(i).setFormatter(formatter);
                }
            }
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Failed to find Formatter class: " + e);
        } catch (InstantiationException e2) {
            Log.e(TAG, "Failed to instantiate formtter: " + e2);
        } catch (IllegalAccessException e3) {
            Log.e(TAG, "No access to formatter class: " + e3);
        } catch (ClassCastException e4) {
            Log.e(TAG, "Specified formatter class does not implement the Formatter interface: " + e4);
        }
    }

    private Level stringToLevel(String str) {
        return Level.valueOf(str);
    }
}
