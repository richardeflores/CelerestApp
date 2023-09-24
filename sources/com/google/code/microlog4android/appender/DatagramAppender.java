package com.google.code.microlog4android.appender;

import android.util.Log;
import com.google.code.microlog4android.Level;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DatagramAppender extends AbstractAppender {
    public static final String DEFAULT_HOST = "127.0.0.1";
    private static final String TAG = "Microlog.DatagramAppender";
    private InetAddress address;
    private DatagramPacket datagramPacket;
    private DatagramSocket datagramSocket;
    private String host = DEFAULT_HOST;
    private int port;

    public void clear() {
    }

    public long getLogSize() {
        return -1;
    }

    public void open() throws IOException {
        this.datagramSocket = new DatagramSocket();
        this.address = InetAddress.getByName(this.host);
        this.datagramPacket = new DatagramPacket(new byte[0], 0, this.address, this.port);
        this.logOpen = true;
    }

    public void doLog(String str, String str2, long j, Level level, Object obj, Throwable th) {
        if (this.logOpen && this.formatter != null) {
            sendMessage(this.formatter.format(str, str2, j, level, obj, th));
        }
    }

    public void sendMessage(String str) {
        this.datagramPacket.setData(str.getBytes());
        try {
            this.datagramSocket.send(this.datagramPacket);
        } catch (IOException e) {
            Log.e(TAG, "Failed to send datagram log " + e);
        }
    }

    public void close() throws IOException {
        DatagramSocket datagramSocket2 = this.datagramSocket;
        if (datagramSocket2 != null) {
            datagramSocket2.close();
        }
        this.logOpen = false;
    }

    public void setPort(int i) {
        this.port = i;
    }
}
