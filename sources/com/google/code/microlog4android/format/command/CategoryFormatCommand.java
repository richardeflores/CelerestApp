package com.google.code.microlog4android.format.command;

import android.util.Log;
import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.format.command.util.StringUtil;
import java.io.PrintStream;

public class CategoryFormatCommand implements FormatCommandInterface {
    public static final int DEFAULT_PRECISION_SPECIFIER = 1;
    public static final int FULL_CLASS_NAME_SPECIFIER = -1;
    private static final String TAG = "Microlog.CategoryFormatCommand";
    private int precisionSpecifier = 1;

    public String execute(String str, String str2, long j, Level level, Object obj, Throwable th) {
        if (str2 == null) {
            return "";
        }
        int i = this.precisionSpecifier;
        return i == -1 ? str2 : StringUtil.extractPartialClassName(str2, i);
    }

    public void init(String str) {
        try {
            this.precisionSpecifier = Integer.parseInt(str);
            PrintStream printStream = System.out;
            printStream.println("Precision specifier for %c is " + this.precisionSpecifier);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Failed to parse the specifier for the %c pattern " + e);
        }
    }

    public int getPrecisionSpecifier() {
        return this.precisionSpecifier;
    }

    public void setPrecisionSpecifier(int i) {
        this.precisionSpecifier = i;
    }
}
