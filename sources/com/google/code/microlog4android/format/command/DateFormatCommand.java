package com.google.code.microlog4android.format.command;

import android.util.Log;
import com.google.code.microlog4android.Level;
import java.util.Calendar;
import java.util.Date;

public class DateFormatCommand implements FormatCommandInterface {
    public static final int ABSOLUTE_FORMAT = 1;
    public static final String ABSOLUTE_FORMAT_STRING = "ABSOLUTE";
    public static final int DATE_FORMAT = 2;
    public static final String DATE_FORMAT_STRING = "DATE";
    public static final int ISO_8601_FORMAT = 3;
    public static final String ISO_8601_FORMAT_STRING = "ISO8601";
    static final String[] MONTH_ARRAY = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    private static final String TAG = "Microlog.DateFormatCommand";
    public static int USER_FORMAT;
    private final Calendar calendar = Calendar.getInstance();
    int format = 1;

    public String execute(String str, String str2, long j, Level level, Object obj, Throwable th) {
        long currentTimeMillis = System.currentTimeMillis();
        int i = this.format;
        if (i == 1) {
            return toAbsoluteFormat(currentTimeMillis);
        }
        if (i == 2) {
            return toDateFormat(currentTimeMillis);
        }
        if (i == 3) {
            return toISO8601Format(currentTimeMillis);
        }
        Log.e(TAG, "Unrecognized format, using default format.");
        return toAbsoluteFormat(System.currentTimeMillis());
    }

    public void init(String str) {
        if (str.equals(ABSOLUTE_FORMAT_STRING)) {
            this.format = 1;
        } else if (str.equals(DATE_FORMAT_STRING)) {
            this.format = 2;
        } else if (str.equals(ISO_8601_FORMAT_STRING)) {
            this.format = 3;
        }
    }

    /* access modifiers changed from: package-private */
    public String toAbsoluteFormat(long j) {
        this.calendar.setTime(new Date(j));
        long j2 = (long) this.calendar.get(11);
        StringBuffer stringBuffer = new StringBuffer();
        if (j2 < 10) {
            stringBuffer.append('0');
        }
        stringBuffer.append(j2);
        stringBuffer.append(':');
        long j3 = (long) this.calendar.get(12);
        if (j3 < 10) {
            stringBuffer.append('0');
        }
        stringBuffer.append(j3);
        stringBuffer.append(':');
        long j4 = (long) this.calendar.get(13);
        if (j4 < 10) {
            stringBuffer.append('0');
        }
        stringBuffer.append(j4);
        stringBuffer.append(',');
        long j5 = (long) this.calendar.get(14);
        if (j5 < 10) {
            stringBuffer.append('0');
        }
        stringBuffer.append(j5);
        return stringBuffer.toString();
    }

    /* access modifiers changed from: package-private */
    public String toDateFormat(long j) {
        this.calendar.setTime(new Date(j));
        StringBuffer stringBuffer = new StringBuffer();
        int i = this.calendar.get(5);
        if (i < 10) {
            stringBuffer.append('0');
        }
        stringBuffer.append(i);
        stringBuffer.append(' ');
        stringBuffer.append(MONTH_ARRAY[this.calendar.get(2)]);
        stringBuffer.append(' ');
        stringBuffer.append(this.calendar.get(1));
        stringBuffer.append(' ');
        stringBuffer.append(toAbsoluteFormat(j));
        return stringBuffer.toString();
    }

    /* access modifiers changed from: package-private */
    public String toISO8601Format(long j) {
        this.calendar.setTime(new Date(j));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.calendar.get(1));
        stringBuffer.append('-');
        int i = this.calendar.get(2) + 1;
        if (i < 10) {
            stringBuffer.append('0');
        }
        stringBuffer.append(i);
        stringBuffer.append('-');
        int i2 = this.calendar.get(5);
        if (i2 < 10) {
            stringBuffer.append('0');
        }
        stringBuffer.append(i2);
        stringBuffer.append(' ');
        stringBuffer.append(toAbsoluteFormat(j));
        return stringBuffer.toString();
    }
}
