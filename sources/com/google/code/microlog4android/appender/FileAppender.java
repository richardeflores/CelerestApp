package com.google.code.microlog4android.appender;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import com.google.code.microlog4android.Level;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class FileAppender extends AbstractAppender {
    public static final String DEFAULT_FILENAME = "microlog.txt";
    private static final String TAG = "Microlog.FileAppender";
    private boolean append = false;
    private String fileName = DEFAULT_FILENAME;
    Context mContext = null;
    private File mSdCardLogFile = null;
    private PrintWriter writer;

    public long getLogSize() {
        return -1;
    }

    public FileAppender(Context context) {
        this.mContext = context;
    }

    public FileAppender() {
    }

    public synchronized void open() throws IOException {
        File logFile = getLogFile();
        this.logOpen = false;
        if (logFile != null) {
            if (!logFile.exists() && !logFile.createNewFile()) {
                Log.e(TAG, "Unable to create new log file");
            }
            this.writer = new PrintWriter(new FileOutputStream(logFile, this.append));
            this.logOpen = true;
        }
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public synchronized void close() throws IOException {
        Log.i(TAG, "Closing the FileAppender");
        if (this.writer != null) {
            this.writer.close();
        }
    }

    public synchronized void doLog(String str, String str2, long j, Level level, Object obj, Throwable th) {
        synchronized (this) {
            if (this.logOpen && this.formatter != null && this.writer != null) {
                this.writer.println(this.formatter.format(str, str2, j, level, obj, th));
                this.writer.flush();
                if (th != null) {
                    th.printStackTrace();
                }
            } else if (this.formatter == null) {
                Log.e(TAG, "Please set a formatter.");
            }
        }
    }

    public void setFileName(String str) {
        if (str != null) {
            this.fileName = str;
        }
    }

    public void setAppend(boolean z) {
        this.append = z;
    }

    /* access modifiers changed from: protected */
    public synchronized File getExternalStorageDirectory() {
        File externalStorageDirectory;
        externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (new Integer(Build.VERSION.SDK).intValue() >= 8 && this.mContext != null) {
            try {
                externalStorageDirectory = (File) Context.class.getMethod("getExternalFilesDir", new Class[]{String.class}).invoke(this.mContext, new Object[1]);
            } catch (Throwable th) {
                Log.e(TAG, "Could not execute method getExternalFilesDir() on sdk >=8", th);
            }
        }
        if (externalStorageDirectory != null && !externalStorageDirectory.exists() && !externalStorageDirectory.mkdirs()) {
            externalStorageDirectory = null;
            Log.e(TAG, "mkdirs failed on externalStorageDirectory " + null);
        }
        return externalStorageDirectory;
    }

    public synchronized File getLogFile() {
        File externalStorageDirectory;
        if (this.mSdCardLogFile == null) {
            if (Environment.getExternalStorageState().equals("mounted") && (externalStorageDirectory = getExternalStorageDirectory()) != null) {
                this.mSdCardLogFile = new File(externalStorageDirectory, this.fileName);
            }
            if (this.mSdCardLogFile == null) {
                Log.e(TAG, "Unable to open log file from external storage");
            }
        }
        return this.mSdCardLogFile;
    }
}
