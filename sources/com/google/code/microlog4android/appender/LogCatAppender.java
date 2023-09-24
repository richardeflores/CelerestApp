package com.google.code.microlog4android.appender;

import android.util.Log;
import com.google.code.microlog4android.Level;
import java.io.IOException;

public class LogCatAppender extends AbstractAppender {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$google$code$microlog4android$Level;

    public void clear() {
    }

    public long getLogSize() {
        return -1;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(17:3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|20) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0027 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0030 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0039 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0042 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0015 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x001e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ int[] $SWITCH_TABLE$com$google$code$microlog4android$Level() {
        /*
            int[] r0 = $SWITCH_TABLE$com$google$code$microlog4android$Level
            if (r0 == 0) goto L_0x0005
            return r0
        L_0x0005:
            com.google.code.microlog4android.Level[] r0 = com.google.code.microlog4android.Level.values()
            int r0 = r0.length
            int[] r0 = new int[r0]
            com.google.code.microlog4android.Level r1 = com.google.code.microlog4android.Level.DEBUG     // Catch:{ NoSuchFieldError -> 0x0015 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0015 }
            r2 = 5
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0015 }
        L_0x0015:
            com.google.code.microlog4android.Level r1 = com.google.code.microlog4android.Level.ERROR     // Catch:{ NoSuchFieldError -> 0x001e }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001e }
            r2 = 2
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001e }
        L_0x001e:
            com.google.code.microlog4android.Level r1 = com.google.code.microlog4android.Level.FATAL     // Catch:{ NoSuchFieldError -> 0x0027 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0027 }
            r2 = 1
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0027 }
        L_0x0027:
            com.google.code.microlog4android.Level r1 = com.google.code.microlog4android.Level.INFO     // Catch:{ NoSuchFieldError -> 0x0030 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0030 }
            r2 = 4
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0030 }
        L_0x0030:
            com.google.code.microlog4android.Level r1 = com.google.code.microlog4android.Level.OFF     // Catch:{ NoSuchFieldError -> 0x0039 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0039 }
            r2 = 7
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0039 }
        L_0x0039:
            com.google.code.microlog4android.Level r1 = com.google.code.microlog4android.Level.TRACE     // Catch:{ NoSuchFieldError -> 0x0042 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0042 }
            r2 = 6
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0042 }
        L_0x0042:
            com.google.code.microlog4android.Level r1 = com.google.code.microlog4android.Level.WARN     // Catch:{ NoSuchFieldError -> 0x004b }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
            r2 = 3
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004b }
        L_0x004b:
            $SWITCH_TABLE$com$google$code$microlog4android$Level = r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.code.microlog4android.appender.LogCatAppender.$SWITCH_TABLE$com$google$code$microlog4android$Level():int[]");
    }

    public void doLog(String str, String str2, long j, Level level, Object obj, Throwable th) {
        String str3 = str;
        if (this.logOpen && this.formatter != null) {
            switch ($SWITCH_TABLE$com$google$code$microlog4android$Level()[level.ordinal()]) {
                case 1:
                case 2:
                    Log.e(str, this.formatter.format(str, str2, j, level, obj, th));
                    return;
                case 3:
                    Log.w(str, this.formatter.format(str, str2, j, level, obj, th));
                    return;
                case 4:
                    Log.i(str, this.formatter.format(str, str2, j, level, obj, th));
                    return;
                case 5:
                case 6:
                    Log.d(str, this.formatter.format(str, str2, j, level, obj, th));
                    return;
                default:
                    return;
            }
        }
    }

    public void open() throws IOException {
        this.logOpen = true;
    }

    public void close() throws IOException {
        this.logOpen = false;
    }
}
