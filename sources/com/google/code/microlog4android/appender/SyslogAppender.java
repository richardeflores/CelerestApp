package com.google.code.microlog4android.appender;

import com.google.code.microlog4android.Level;

public class SyslogAppender extends DatagramAppender {
    private SyslogMessage syslogMessage = new SyslogMessage();

    public SyslogAppender() {
        super.setPort(SyslogMessage.DEFAULT_SYSLOG_PORT);
        this.syslogMessage.setTag("microlog");
        this.syslogMessage.setFacility((byte) 1);
        this.syslogMessage.setSeverity((byte) 7);
    }

    public void doLog(String str, String str2, long j, Level level, Object obj, Throwable th) {
        if (this.logOpen && this.formatter != null) {
            sendMessage(this.syslogMessage.createMessageData(this.formatter.format(str, str2, j, level, obj, th)));
        }
    }

    public void setFacility(byte b) {
        this.syslogMessage.setFacility(b);
    }

    public void setSeverity(byte b) throws IllegalArgumentException {
        this.syslogMessage.setSeverity(b);
    }

    public void setHeader(boolean z) {
        this.syslogMessage.setHeader(z);
    }

    public void setHostname(String str) throws IllegalArgumentException {
        this.syslogMessage.setHostname(str);
    }

    public void setTag(String str) throws IllegalArgumentException {
        this.syslogMessage.setTag(str);
    }
}
