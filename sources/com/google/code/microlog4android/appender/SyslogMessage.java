package com.google.code.microlog4android.appender;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SyslogMessage {
    public static final int DEFAULT_MESSAGE_BUFFER_SIZE = 128;
    public static final int DEFAULT_SYSLOG_PORT = 514;
    public static final String DEFAULT_SYSLOG_TAG = "microlog";
    public static final byte FACILITY_KERNAL_MESSAGE = 0;
    public static final byte FACILITY_LOCAL_USE_0 = 16;
    public static final byte FACILITY_LOCAL_USE_1 = 17;
    public static final byte FACILITY_LOCAL_USE_2 = 18;
    public static final byte FACILITY_LOCAL_USE_3 = 19;
    public static final byte FACILITY_LOCAL_USE_4 = 20;
    public static final byte FACILITY_LOCAL_USE_5 = 21;
    public static final byte FACILITY_LOCAL_USE_6 = 22;
    public static final byte FACILITY_LOCAL_USE_7 = 23;
    public static final byte FACILITY_LOG_ALERT = 14;
    public static final byte FACILITY_LOG_AUDIT = 13;
    public static final byte FACILITY_MAIL_SYSTEM = 2;
    public static final byte FACILITY_SECURITY_MESSAGE = 4;
    public static final byte FACILITY_SYSTEM_DAEMONS = 3;
    public static final byte FACILITY_USER_LEVEL_MESSAGE = 1;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final byte SEVERITY_ALERT = 1;
    public static final byte SEVERITY_CRITICAL = 2;
    public static final byte SEVERITY_DEBUG = 7;
    public static final byte SEVERITY_EMERGENCY = 0;
    public static final byte SEVERITY_ERROR = 3;
    public static final byte SEVERITY_INFORMATIONAL = 6;
    public static final byte SEVERITY_NOTICE = 5;
    public static final byte SEVERITY_WARNING = 4;
    public static final int TEN = 10;
    private final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    private byte facility = 1;
    private boolean header;
    private String hostname;
    private final StringBuffer messageStringBuffer = new StringBuffer(128);
    private byte severity = 7;
    String tag;

    public String createMessageData(String str) {
        StringBuffer stringBuffer = this.messageStringBuffer;
        stringBuffer.delete(0, stringBuffer.length());
        this.messageStringBuffer.append('<');
        this.messageStringBuffer.append((this.facility * 8) + this.severity);
        this.messageStringBuffer.append('>');
        if (this.header) {
            this.calendar.setTime(new Date(System.currentTimeMillis()));
            this.messageStringBuffer.append(MONTHS[this.calendar.get(2)]);
            this.messageStringBuffer.append(' ');
            int i = this.calendar.get(5);
            if (i < 10) {
                this.messageStringBuffer.append('0');
            }
            this.messageStringBuffer.append(i);
            this.messageStringBuffer.append(' ');
            int i2 = this.calendar.get(11);
            if (i2 < 10) {
                this.messageStringBuffer.append('0');
            }
            this.messageStringBuffer.append(i2);
            this.messageStringBuffer.append(':');
            int i3 = this.calendar.get(12);
            if (i3 < 10) {
                this.messageStringBuffer.append('0');
            }
            this.messageStringBuffer.append(i3);
            this.messageStringBuffer.append(':');
            int i4 = this.calendar.get(13);
            if (i4 < 10) {
                this.messageStringBuffer.append('0');
            }
            this.messageStringBuffer.append(i4);
            this.messageStringBuffer.append(' ');
            this.messageStringBuffer.append(this.hostname);
        }
        this.messageStringBuffer.append(' ');
        this.messageStringBuffer.append(this.tag);
        this.messageStringBuffer.append(": ");
        this.messageStringBuffer.append(str);
        return this.messageStringBuffer.toString();
    }

    public void setFacility(byte b) {
        if (b < 0 || b > 23) {
            throw new IllegalArgumentException("Not a valid facility.");
        }
        this.facility = b;
    }

    public byte getFacility() {
        return this.facility;
    }

    public void setSeverity(byte b) throws IllegalArgumentException {
        if (b < 0 || b > 7) {
            throw new IllegalArgumentException("Not a valid severity.");
        }
        this.severity = b;
    }

    public void setHeader(boolean z) {
        this.header = z;
    }

    public void setHostname(String str) throws IllegalArgumentException {
        if (str != null) {
            this.hostname = str;
            return;
        }
        throw new IllegalArgumentException("The hostname must not be null.");
    }

    public String getHostname() {
        return this.hostname;
    }

    public void setTag(String str) throws IllegalArgumentException {
        if (str == null || (str != null && (str.length() < 1 || str.length() > 32))) {
            throw new IllegalArgumentException("The tag must not be null, the length between 1..32");
        }
        this.tag = str;
    }
}
