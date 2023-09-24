package com.google.code.microlog4android.format;

import android.util.Log;
import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.format.command.CategoryFormatCommand;
import com.google.code.microlog4android.format.command.ClientIdFormatCommand;
import com.google.code.microlog4android.format.command.DateFormatCommand;
import com.google.code.microlog4android.format.command.FormatCommandInterface;
import com.google.code.microlog4android.format.command.MessageFormatCommand;
import com.google.code.microlog4android.format.command.NoFormatCommand;
import com.google.code.microlog4android.format.command.PriorityFormatCommand;
import com.google.code.microlog4android.format.command.ThreadFormatCommand;
import com.google.code.microlog4android.format.command.ThrowableFormatCommand;
import com.google.code.microlog4android.format.command.TimeFormatCommand;
import java.util.Vector;

public class PatternFormatter implements Formatter {
    public static final char CATEGORY_CONVERSION_CHAR = 'c';
    public static final char CLIENT_ID_CONVERSION_CHAR = 'i';
    public static final char DATE_CONVERSION_CHAR = 'd';
    public static final String DEFAULT_CONVERSION_PATTERN = "%r %c{1} [%P] %m %T";
    public static final char MESSAGE_CONVERSION_CHAR = 'm';
    public static final String PATTERN_PROPERTY = "pattern";
    public static final char PERCENT_CONVERSION_CHAR = '%';
    public static final char PRIORITY_CONVERSION_CHAR = 'P';
    private static final String[] PROPERTY_NAMES = {PATTERN_PROPERTY};
    public static final char RELATIVE_TIME_CONVERSION_CHAR = 'r';
    private static final String TAG = "Microlog.PatternFormatter";
    public static final char THREAD_CONVERSION_CHAR = 't';
    public static final char THROWABLE_CONVERSION_CHAR = 'T';
    private FormatCommandInterface[] commandArray;
    private String pattern = DEFAULT_CONVERSION_PATTERN;
    private boolean patternParsed = false;

    public String format(String str, String str2, long j, Level level, Object obj, Throwable th) {
        String str3;
        if (!this.patternParsed && (str3 = this.pattern) != null) {
            parsePattern(str3);
        }
        StringBuffer stringBuffer = new StringBuffer(64);
        FormatCommandInterface[] formatCommandInterfaceArr = this.commandArray;
        if (formatCommandInterfaceArr != null) {
            int length = formatCommandInterfaceArr.length;
            for (int i = 0; i < length; i++) {
                FormatCommandInterface formatCommandInterface = this.commandArray[i];
                if (formatCommandInterface != null) {
                    stringBuffer.append(formatCommandInterface.execute(str, str2, j, level, obj, th));
                }
            }
        }
        return stringBuffer.toString();
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setPattern(String str) throws IllegalArgumentException {
        if (str != null) {
            this.pattern = str;
            parsePattern(this.pattern);
            return;
        }
        throw new IllegalArgumentException("The pattern must not be null.");
    }

    private void parsePattern(String str) {
        String str2;
        int length = str.length();
        Vector vector = new Vector(20);
        int i = 0;
        while (i < length) {
            if (str.charAt(i) == '%') {
                int i2 = i + 1;
                char charAt = str.charAt(i2);
                if (charAt == '%') {
                    NoFormatCommand noFormatCommand = new NoFormatCommand();
                    noFormatCommand.init("%");
                    vector.addElement(noFormatCommand);
                } else if (charAt == 'P') {
                    vector.addElement(new PriorityFormatCommand());
                } else if (charAt == 'T') {
                    vector.addElement(new ThrowableFormatCommand());
                } else if (charAt == 'i') {
                    vector.addElement(new ClientIdFormatCommand());
                } else if (charAt == 'm') {
                    vector.addElement(new MessageFormatCommand());
                } else if (charAt == 'r') {
                    vector.addElement(new TimeFormatCommand());
                } else if (charAt == 't') {
                    vector.addElement(new ThreadFormatCommand());
                } else if (charAt == 'c') {
                    CategoryFormatCommand categoryFormatCommand = new CategoryFormatCommand();
                    String extraxtSpecifier = extraxtSpecifier(str, i2);
                    int length2 = extraxtSpecifier.length();
                    if (length2 > 0) {
                        categoryFormatCommand.init(extraxtSpecifier);
                        i2 = i2 + length2 + 2;
                    }
                    vector.addElement(categoryFormatCommand);
                } else if (charAt != 'd') {
                    Log.e(TAG, "Unrecognized conversion character " + charAt);
                } else {
                    DateFormatCommand dateFormatCommand = new DateFormatCommand();
                    String extraxtSpecifier2 = extraxtSpecifier(str, i2);
                    int length3 = extraxtSpecifier2.length();
                    if (length3 > 0) {
                        dateFormatCommand.init(extraxtSpecifier2);
                        i2 = i2 + length3 + 2;
                    }
                    vector.addElement(dateFormatCommand);
                }
                i = i2 + 1;
            } else {
                int indexOf = str.indexOf("%", i);
                if (indexOf != -1) {
                    str2 = str.substring(i, indexOf);
                } else {
                    str2 = str.substring(i, length);
                }
                NoFormatCommand noFormatCommand2 = new NoFormatCommand();
                noFormatCommand2.init(str2);
                vector.addElement(noFormatCommand2);
                i += str2.length();
            }
        }
        this.commandArray = new FormatCommandInterface[vector.size()];
        vector.copyInto(this.commandArray);
        this.patternParsed = true;
    }

    /* access modifiers changed from: package-private */
    public String extraxtSpecifier(String str, int i) {
        int indexOf = str.indexOf(123, i);
        int indexOf2 = str.indexOf(125, i);
        return (indexOf <= 0 || indexOf2 <= indexOf) ? "" : str.substring(indexOf + 1, indexOf2);
    }

    public String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }

    public void setProperty(String str, String str2) {
        if (str.equals(PATTERN_PROPERTY)) {
            setPattern(str2);
        }
    }
}
