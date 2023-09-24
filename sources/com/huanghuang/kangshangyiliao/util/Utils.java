package com.huanghuang.kangshangyiliao.util;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.p000v4.view.InputDeviceCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.ScrollView;
import com.google.code.microlog4android.appender.SyslogMessage;
import com.google.code.microlog4android.format.SimpleFormatter;
import com.google.gson.Gson;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.bean.BloodFatResult;
import com.huanghuang.kangshangyiliao.bean.ImmuFluoResult;
import com.huanghuang.kangshangyiliao.bean.POCTResult;
import com.huanghuang.kangshangyiliao.bean.ProteinResult;
import com.huanghuang.kangshangyiliao.bean.UrinalysisResult;
import com.huanghuang.kangshangyiliao.dao.NickNameDao;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.p006db.SQLite;
import com.yzq.zxinglibrary.BuildConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {
    private static String byte2Binary(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(addZero(Integer.toBinaryString(b & 255), 8));
        }
        return sb.toString();
    }

    public static String addZero(String str, int i) {
        int length = i - str.length();
        if (length == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < length; i2++) {
            sb.append("0");
        }
        sb.append(str);
        return sb.toString();
    }

    public static String byteArray2HexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append("0123456789abcdef".charAt((b >> 4) & 15));
            sb.append("0123456789abcdef".charAt(b & 15));
        }
        return sb.toString();
    }

    public static byte[] toByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    public static UrinalysisResult parseUrinalysisData(byte[] bArr) {
        UrinalysisResult urinalysisResult = new UrinalysisResult();
        if (bArr != null && bArr.length >= 7) {
            byte[] bArr2 = new byte[(bArr.length - 7)];
            System.arraycopy(bArr, 6, bArr2, 0, bArr2.length);
            String byte2Binary = byte2Binary(bArr2);
            urinalysisResult.f75sn = addZero(Integer.valueOf(byte2Binary.substring(6, 16), 2) + "", 4);
            String substring = byte2Binary.substring(21, 32);
            for (int i = 0; i < substring.length(); i++) {
                if (substring.charAt(i) == '0') {
                    urinalysisResult.effectiveTp[i] = true;
                } else if (substring.charAt(i) == '1') {
                    urinalysisResult.effectiveTp[i] = false;
                }
            }
            urinalysisResult.day = Integer.valueOf(byte2Binary.substring(32, 37), 2).intValue();
            urinalysisResult.month = Integer.valueOf(byte2Binary.substring(37, 41), 2).intValue();
            urinalysisResult.year = Integer.valueOf(byte2Binary.substring(41, 48), 2).intValue();
            urinalysisResult.data[0] = Integer.valueOf(byte2Binary.substring(50, 53), 2).intValue();
            urinalysisResult.minute = Integer.valueOf(byte2Binary.substring(53, 59), 2).intValue();
            urinalysisResult.hour = Integer.valueOf(byte2Binary.substring(59, 64), 2).intValue();
            urinalysisResult.data[1] = Integer.valueOf(byte2Binary.substring(65, 68), 2).intValue();
            urinalysisResult.data[2] = Integer.valueOf(byte2Binary.substring(68, 71), 2).intValue();
            urinalysisResult.data[3] = Integer.valueOf(byte2Binary.substring(71, 74), 2).intValue();
            urinalysisResult.data[4] = Integer.valueOf(byte2Binary.substring(74, 77), 2).intValue();
            urinalysisResult.data[5] = Integer.valueOf(byte2Binary.substring(77, 80), 2).intValue();
            urinalysisResult.data[6] = Integer.valueOf(byte2Binary.substring(81, 84), 2).intValue();
            urinalysisResult.data[7] = Integer.valueOf(byte2Binary.substring(84, 87), 2).intValue();
            urinalysisResult.data[8] = Integer.valueOf(byte2Binary.substring(87, 90), 2).intValue();
            urinalysisResult.data[9] = Integer.valueOf(byte2Binary.substring(90, 93), 2).intValue();
            urinalysisResult.data[10] = Integer.valueOf(byte2Binary.substring(93, 96), 2).intValue();
            if (bArr.length > 19) {
                urinalysisResult.f74pf = Integer.valueOf(byte2Binary.substring(96, 97), 2).intValue();
                urinalysisResult.data[11] = Integer.valueOf(byte2Binary.substring(103, 106), 2).intValue();
                urinalysisResult.data[12] = Integer.valueOf(byte2Binary.substring(106, 109), 2).intValue();
                urinalysisResult.data[13] = Integer.valueOf(byte2Binary.substring(109, 112), 2).intValue();
            }
        }
        return urinalysisResult;
    }

    public static String[] getRealValueText(UrinalysisResult urinalysisResult) {
        String[] strArr = new String[14];
        int i = 0;
        strArr[0] = getText1(urinalysisResult.data[0]);
        strArr[1] = getText1(urinalysisResult.data[1]);
        if (urinalysisResult.data[5] == 0) {
            strArr[2] = SimpleFormatter.DEFAULT_DELIMITER;
        } else {
            strArr[2] = "+";
        }
        strArr[3] = getText1(urinalysisResult.data[9]);
        strArr[4] = getText2(urinalysisResult.data[4]);
        strArr[5] = getText2(urinalysisResult.data[8]);
        strArr[6] = getText1(urinalysisResult.data[3]);
        strArr[7] = getText1(urinalysisResult.data[7]);
        String[] strArr2 = {"5.0", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5"};
        int i2 = urinalysisResult.data[2];
        if (i2 >= 0 && i2 <= 6) {
            i = i2;
        }
        strArr[8] = "" + strArr2[i];
        strArr[9] = getText1(urinalysisResult.data[6]);
        strArr[10] = float3Point((((float) urinalysisResult.data[10]) * 0.005f) + 1.0f);
        strArr[11] = getText2(urinalysisResult.data[12]);
        strArr[12] = getText2(urinalysisResult.data[13]);
        strArr[13] = getText2(urinalysisResult.data[11]);
        return strArr;
    }

    public static String getText1(int i) {
        if (i == 0) {
            return SimpleFormatter.DEFAULT_DELIMITER;
        }
        if (i == 1) {
            return "+-";
        }
        return "+" + (i - 1);
    }

    public static String getText2(int i) {
        if (i == 0) {
            return SimpleFormatter.DEFAULT_DELIMITER;
        }
        return "+" + i;
    }

    public static void delayTask(Runnable runnable, long j) {
        new Handler().postDelayed(runnable, j);
    }

    public static String getDate() {
        return getDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String getDate(String str) {
        return getDate(new Date(), str);
    }

    public static String getDate(Date date, String str) {
        try {
            return new SimpleDateFormat(str).format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static byte getUrinalysisFrameType(byte[] bArr) {
        if (bArr.length < 6 || bArr[0] != -109 || bArr[1] != -114 || bArr[3] != 0) {
            return 0;
        }
        if (bArr[4] == 8 || bArr[4] == 16) {
            return bArr[5];
        }
        return 0;
    }

    public static byte getPOCTFrameType(byte[] bArr) {
        if (bArr.length >= 6 && bArr[0] == -91 && bArr[1] == 85 && bArr[3] == 0 && bArr[4] == 32) {
            return bArr[5];
        }
        return 0;
    }

    public static byte getProteinFrameType(byte[] bArr) {
        if (bArr.length >= 6 && bArr[0] == -91 && bArr[1] == 85 && bArr[3] == 0 && bArr[4] == 48) {
            return bArr[5];
        }
        return 0;
    }

    public static byte getBloodFatFrameType(byte[] bArr) {
        if (bArr.length >= 6 && bArr[0] == -91 && bArr[1] == 85 && bArr[3] == 0 && bArr[4] == 96) {
            return bArr[5];
        }
        return 0;
    }

    public static byte getImmunofluorescenceFrameType(byte[] bArr) {
        if (bArr.length >= 6 && bArr[0] == -91 && bArr[1] == 85 && bArr[3] == 0 && bArr[4] == -112) {
            return bArr[5];
        }
        return 0;
    }

    public static List<NickName> getDefaultNickName() {
        ArrayList arrayList = new ArrayList();
        NickName nickName = new NickName();
        nickName.nickName = Constants.DEFAULT_NICK_NAME;
        nickName.createDate = getDate();
        arrayList.add(nickName);
        NickName nickName2 = new NickName();
        nickName2.nickName = "健康365";
        nickName2.createDate = getDate();
        arrayList.add(nickName2);
        return arrayList;
    }

    public static byte[] getUrinalysisTimeFrame() {
        byte[] bArr = {-109, -114, 9, 0, 8, 2, SyslogMessage.FACILITY_LOCAL_USE_1, 8, 25, SyslogMessage.FACILITY_LOG_ALERT, 41, 124};
        byte[] dateValue = getDateValue();
        byte b = 0;
        for (int i = 0; i < dateValue.length; i++) {
            bArr[i + 6] = dateValue[i];
        }
        for (int i2 = 2; i2 < bArr.length - 1; i2++) {
            b = (byte) (b + bArr[i2]);
        }
        bArr[bArr.length - 1] = b;
        return bArr;
    }

    public static byte[] getPOCTTimeFrame() {
        byte[] bArr = {-91, 85, 10, 0, 32, 2, SyslogMessage.FACILITY_LOCAL_USE_1, 9, 12, SyslogMessage.FACILITY_LOCAL_USE_0, SyslogMessage.FACILITY_LOCAL_USE_3, 64, -75};
        byte[] dateValue = getDateValue();
        byte b = 0;
        for (int i = 0; i < dateValue.length; i++) {
            bArr[i + 6] = dateValue[i];
        }
        for (int i2 = 2; i2 < bArr.length - 1; i2++) {
            b = (byte) (b + bArr[i2]);
        }
        bArr[bArr.length - 1] = b;
        return bArr;
    }

    public static byte[] getProteinTimeFrame() {
        byte[] bArr = {-91, 85, 10, 0, 48, 2, SyslogMessage.FACILITY_LOCAL_USE_1, 9, 12, SyslogMessage.FACILITY_LOCAL_USE_0, SyslogMessage.FACILITY_LOCAL_USE_3, 48, -75};
        byte[] dateValue = getDateValue();
        byte b = 0;
        for (int i = 0; i < dateValue.length; i++) {
            bArr[i + 6] = dateValue[i];
        }
        for (int i2 = 2; i2 < bArr.length - 1; i2++) {
            b = (byte) (b + bArr[i2]);
        }
        bArr[bArr.length - 1] = b;
        return bArr;
    }

    public static byte[] getBloodFatTimeFrame() {
        byte[] bArr = {-91, 85, 11, 0, 96, 2, SyslogMessage.FACILITY_LOCAL_USE_1, 9, 12, SyslogMessage.FACILITY_LOCAL_USE_0, SyslogMessage.FACILITY_LOCAL_USE_3, 64, 0, -10};
        byte[] dateValue_week = getDateValue_week();
        byte b = 0;
        for (int i = 0; i < dateValue_week.length; i++) {
            bArr[i + 6] = dateValue_week[i];
        }
        for (int i2 = 2; i2 < bArr.length - 1; i2++) {
            b = (byte) (b + bArr[i2]);
        }
        bArr[bArr.length - 1] = b;
        return bArr;
    }

    public static byte[] getImmufluoTimeFrame() {
        byte[] bArr = {-91, 85, 11, 0, -112, 2, SyslogMessage.FACILITY_LOCAL_USE_4, 3, 3, SyslogMessage.FACILITY_LOG_ALERT, 33, 6, 0, -20};
        byte[] dateValue_week = getDateValue_week();
        byte b = 0;
        for (int i = 0; i < dateValue_week.length; i++) {
            bArr[i + 6] = dateValue_week[i];
        }
        for (int i2 = 2; i2 < bArr.length - 1; i2++) {
            b = (byte) (b + bArr[i2]);
        }
        bArr[bArr.length - 1] = b;
        return bArr;
    }

    public static byte[] getChecksum(byte[] bArr) {
        byte b = 0;
        for (int i = 2; i < bArr.length - 1; i++) {
            b = (byte) (b + bArr[i]);
        }
        bArr[bArr.length - 1] = b;
        return bArr;
    }

    public static String bytesIntByteString(int i) {
        String str;
        int i2 = i / 16;
        int i3 = i % 16;
        String str2 = "f";
        if (i2 <= 9) {
            str = String.valueOf(i2);
        } else {
            str = i2 == 10 ? "a" : i2 == 11 ? "b" : i2 == 12 ? "c" : i2 == 13 ? "d" : i2 == 14 ? "e" : i2 == 15 ? str2 : "";
        }
        if (i3 <= 9) {
            str2 = String.valueOf(i3);
        } else if (i3 == 10) {
            str2 = "a";
        } else if (i3 == 11) {
            str2 = "b";
        } else if (i3 == 12) {
            str2 = "c";
        } else if (i3 == 13) {
            str2 = "d";
        } else if (i3 == 14) {
            str2 = "e";
        } else if (i3 != 15) {
            str2 = "";
        }
        return str + str2 + "00";
    }

    public static byte[] getObserved(int i) {
        byte[] bArr = {-91, 85, 8, 0, 48, 38, 1, 0, 0, 0, 0};
        bArr[9] = toByteArray(bytesIntByteString(i))[0];
        return getChecksum(bArr);
    }

    public static byte[] getDateValue() {
        byte[] bArr = {0, 0, 0, 0, 0, 0};
        Calendar instance = Calendar.getInstance();
        bArr[0] = (byte) (instance.get(1) - 2000);
        bArr[1] = (byte) (instance.get(2) + 1);
        bArr[2] = (byte) instance.get(5);
        bArr[3] = (byte) instance.get(11);
        bArr[4] = (byte) instance.get(12);
        bArr[5] = (byte) instance.get(13);
        return bArr;
    }

    public static byte[] getDateValue_week() {
        byte[] bArr = {0, 0, 0, 0, 0, 0, 0};
        Calendar instance = Calendar.getInstance();
        bArr[0] = (byte) (instance.get(1) - 2000);
        bArr[1] = (byte) (instance.get(2) + 1);
        bArr[2] = (byte) instance.get(5);
        bArr[3] = (byte) instance.get(11);
        bArr[4] = (byte) instance.get(12);
        bArr[5] = (byte) instance.get(13);
        switch (instance.get(7)) {
            case 1:
                bArr[6] = 7;
                break;
            case 2:
                bArr[6] = 1;
                break;
            case 3:
                bArr[6] = 2;
                break;
            case 4:
                bArr[6] = 3;
                break;
            case 5:
                bArr[6] = 4;
                break;
            case 6:
                bArr[6] = 5;
                break;
            case 7:
                bArr[6] = 6;
                break;
        }
        return bArr;
    }

    public static POCTResult parsePOCTData(byte[] bArr) {
        byte[] bArr2 = bArr;
        POCTResult pOCTResult = new POCTResult();
        if (bArr2 != null && bArr2.length >= 7) {
            byte[] bArr3 = new byte[(bArr2.length - 7)];
            System.arraycopy(bArr2, 6, bArr3, 0, bArr3.length);
            String byte2Binary = byte2Binary(bArr3);
            try {
                pOCTResult.values[0] = Integer.parseInt(byte2Binary.substring(16, 32), 2);
                pOCTResult.values[1] = Integer.parseInt(byte2Binary.substring(32, 48), 2);
                pOCTResult.values[2] = Integer.parseInt(byte2Binary.substring(48, 64), 2);
                pOCTResult.values[3] = Integer.parseInt(byte2Binary.substring(64, 80), 2);
                pOCTResult.values[4] = Integer.parseInt(byte2Binary.substring(80, 96), 2);
                pOCTResult.values[5] = Integer.parseInt(byte2Binary.substring(96, 112), 2);
                pOCTResult.values[6] = Integer.parseInt(byte2Binary.substring(112, 128), 2);
                pOCTResult.values[7] = Integer.parseInt(byte2Binary.substring(128, 144), 2);
                pOCTResult.values[8] = Integer.parseInt(byte2Binary.substring(144, 160), 2);
                pOCTResult.values[9] = Integer.parseInt(byte2Binary.substring(160, 176), 2);
                pOCTResult.values[10] = Integer.parseInt(byte2Binary.substring(176, 192), 2);
                pOCTResult.finalValues[0] = ((float) pOCTResult.values[0]) / 100.0f;
                pOCTResult.finalValues[1] = ((float) pOCTResult.values[1]) / 100.0f;
                pOCTResult.finalValues[2] = ((float) pOCTResult.values[2]) / 100.0f;
                pOCTResult.finalValues[3] = ((float) pOCTResult.values[3]) / 100.0f;
                pOCTResult.finalValues[4] = ((float) pOCTResult.values[4]) / 100.0f;
                pOCTResult.finalValues[5] = ((float) pOCTResult.values[5]) / 100.0f;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (pOCTResult.values[1] != 0) {
                pOCTResult.finalValues[6] = ((((float) pOCTResult.values[1]) * 1.0f) / ((float) pOCTResult.values[0])) * 100.0f;
            }
            if (pOCTResult.values[2] != 0) {
                pOCTResult.finalValues[7] = ((((float) pOCTResult.values[2]) * 1.0f) / ((float) pOCTResult.values[0])) * 100.0f;
            }
            if (pOCTResult.values[3] != 0) {
                pOCTResult.finalValues[8] = ((((float) pOCTResult.values[3]) * 1.0f) / ((float) pOCTResult.values[0])) * 100.0f;
            }
            if (pOCTResult.values[4] != 0) {
                pOCTResult.finalValues[9] = ((((float) pOCTResult.values[4]) * 1.0f) / ((float) pOCTResult.values[0])) * 100.0f;
            }
            if (pOCTResult.values[5] != 0) {
                pOCTResult.finalValues[10] = ((((float) pOCTResult.values[5]) * 1.0f) / ((float) pOCTResult.values[0])) * 100.0f;
            }
        }
        return pOCTResult;
    }

    public static String[] getPOCTRealValueText(POCTResult pOCTResult) {
        String[] strArr = new String[11];
        for (int i = 0; i < strArr.length; i++) {
            if (i <= 3) {
                try {
                    strArr[i] = floatPoint(pOCTResult.finalValues[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                strArr[i] = calculateProfit((double) pOCTResult.finalValues[i]);
            }
        }
        return strArr;
    }

    public static ProteinResult parseProteinData(byte[] bArr) {
        ProteinResult proteinResult = new ProteinResult();
        if (bArr != null && bArr.length >= 7) {
            byte[] bArr2 = new byte[(bArr.length - 7)];
            System.arraycopy(bArr, 6, bArr2, 0, bArr2.length);
            String byte2Binary = byte2Binary(bArr2);
            try {
                proteinResult.values[0] = (float) Integer.parseInt(byte2Binary.substring(48, 56), 2);
                proteinResult.values[1] = (float) Integer.parseInt(byte2Binary.substring(56, 64), 2);
                proteinResult.time[0] = (float) Integer.parseInt(byte2Binary.substring(25, 32), 2);
                proteinResult.time[1] = (float) Integer.parseInt(byte2Binary.substring(21, 25), 2);
                proteinResult.time[2] = (float) Integer.parseInt(byte2Binary.substring(16, 21), 2);
                proteinResult.time[3] = (float) Integer.parseInt(byte2Binary.substring(43, 48), 2);
                proteinResult.time[4] = (float) Integer.parseInt(byte2Binary.substring(37, 43), 2);
                proteinResult.time[5] = (float) Integer.parseInt(byte2Binary.substring(32, 37), 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return proteinResult;
    }

    public static BloodFatResult parseBloodFatData_dx(byte[] bArr) {
        byte[] bArr2 = bArr;
        BloodFatResult bloodFatResult = new BloodFatResult();
        if (bArr2 != null && bArr2.length >= 47) {
            byte[] bArr3 = new byte[(bArr2.length - 7)];
            System.arraycopy(bArr2, 6, bArr3, 0, bArr3.length);
            String byte2Binary = byte2Binary(bArr3);
            try {
                bloodFatResult.f72sn = Integer.parseInt(byte2Binary.substring(0, 16), 2);
                bloodFatResult.time[0] = (float) Integer.parseInt(byte2Binary.substring(25, 32), 2);
                bloodFatResult.time[1] = (float) Integer.parseInt(byte2Binary.substring(21, 25), 2);
                bloodFatResult.time[2] = (float) Integer.parseInt(byte2Binary.substring(16, 21), 2);
                bloodFatResult.time[3] = (float) Integer.parseInt(byte2Binary.substring(43, 48), 2);
                bloodFatResult.time[4] = (float) Integer.parseInt(byte2Binary.substring(37, 43), 2);
                bloodFatResult.time[5] = (float) Integer.parseInt(byte2Binary.substring(32, 37), 2);
                bloodFatResult.ReagentType = Integer.parseInt(byte2Binary.substring(48, 64), 2);
                bloodFatResult.values[0] = Integer.parseInt(byte2Binary.substring(64, 80), 2);
                bloodFatResult.values[1] = Integer.parseInt(byte2Binary.substring(80, 96), 2);
                bloodFatResult.values[2] = Integer.parseInt(byte2Binary.substring(96, 112), 2);
                bloodFatResult.values[3] = Integer.parseInt(byte2Binary.substring(112, 128), 2);
                bloodFatResult.values[4] = Integer.parseInt(byte2Binary.substring(128, 144), 2);
                bloodFatResult.values[5] = Integer.parseInt(byte2Binary.substring(144, 160), 2);
                bloodFatResult.values[6] = Integer.parseInt(byte2Binary.substring(160, 176), 2);
                bloodFatResult.values[7] = Integer.parseInt(byte2Binary.substring(176, 192), 2);
                bloodFatResult.finalValues[0] = ((float) Integer.parseInt(byte2Binary.substring(64, 80), 2)) * 0.1f;
                bloodFatResult.finalValues[1] = ((float) Integer.parseInt(byte2Binary.substring(80, 96), 2)) * 0.1f;
                bloodFatResult.finalValues[2] = ((float) Integer.parseInt(byte2Binary.substring(96, 112), 2)) * 0.01f;
                bloodFatResult.finalValues[3] = ((float) Integer.parseInt(byte2Binary.substring(112, 128), 2)) * 0.1f;
                bloodFatResult.finalValues[4] = ((float) Integer.parseInt(byte2Binary.substring(128, 144), 2)) * 0.01f;
                bloodFatResult.finalValues[5] = ((float) Integer.parseInt(byte2Binary.substring(144, 160), 2)) * 0.01f;
                bloodFatResult.finalValues[6] = ((float) Integer.parseInt(byte2Binary.substring(160, 176), 2)) * 0.01f;
                bloodFatResult.finalValues[7] = ((float) Integer.parseInt(byte2Binary.substring(176, 192), 2)) * 0.01f;
                bloodFatResult.finalValues_change_unit[0] = bloodFatResult.finalValues[0] * 18.0f;
                bloodFatResult.finalValues_change_unit[1] = (bloodFatResult.finalValues[1] * 17.0f) / 1000.0f;
                bloodFatResult.finalValues_change_unit[2] = (bloodFatResult.finalValues[2] * 3867.0f) / 100.0f;
                bloodFatResult.units = Integer.parseInt(byte2Binary.substring(192, 208), 2);
                PrintStream printStream = System.out;
                printStream.println(" bloodFatResult.units: dx" + bloodFatResult.units);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bloodFatResult;
    }

    public static BloodFatResult parseBloodFatData_lf(byte[] bArr) {
        byte[] bArr2 = bArr;
        BloodFatResult bloodFatResult = new BloodFatResult();
        if (bArr2 != null && bArr2.length >= 47) {
            byte[] bArr3 = new byte[(bArr2.length - 7)];
            System.arraycopy(bArr2, 6, bArr3, 0, bArr3.length);
            String byte2Binary = byte2Binary(bArr3);
            try {
                bloodFatResult.f72sn = Integer.parseInt(byte2Binary.substring(0, 16), 2);
                bloodFatResult.time[0] = (float) Integer.parseInt(byte2Binary.substring(25, 32), 2);
                bloodFatResult.time[1] = (float) Integer.parseInt(byte2Binary.substring(21, 25), 2);
                bloodFatResult.time[2] = (float) Integer.parseInt(byte2Binary.substring(16, 21), 2);
                bloodFatResult.time[3] = (float) Integer.parseInt(byte2Binary.substring(43, 48), 2);
                bloodFatResult.time[4] = (float) Integer.parseInt(byte2Binary.substring(37, 43), 2);
                bloodFatResult.time[5] = (float) Integer.parseInt(byte2Binary.substring(32, 37), 2);
                bloodFatResult.ReagentType = Integer.parseInt(byte2Binary.substring(48, 64), 2);
                bloodFatResult.values[0] = Integer.parseInt(byte2Binary.substring(64, 80), 2);
                bloodFatResult.values[1] = Integer.parseInt(byte2Binary.substring(80, 96), 2);
                bloodFatResult.values[2] = Integer.parseInt(byte2Binary.substring(96, 112), 2);
                bloodFatResult.values[3] = Integer.parseInt(byte2Binary.substring(112, 128), 2);
                bloodFatResult.values[4] = Integer.parseInt(byte2Binary.substring(128, 144), 2);
                bloodFatResult.values[5] = Integer.parseInt(byte2Binary.substring(144, 160), 2);
                bloodFatResult.values[6] = Integer.parseInt(byte2Binary.substring(160, 176), 2);
                bloodFatResult.values[7] = Integer.parseInt(byte2Binary.substring(176, 192), 2);
                bloodFatResult.finalValues[0] = ((float) Integer.parseInt(byte2Binary.substring(64, 80), 2)) * 0.1f;
                bloodFatResult.finalValues[1] = ((float) Integer.parseInt(byte2Binary.substring(80, 96), 2)) * 0.1f;
                bloodFatResult.finalValues[2] = ((float) Integer.parseInt(byte2Binary.substring(96, 112), 2)) * 0.1f;
                bloodFatResult.finalValues[3] = ((float) Integer.parseInt(byte2Binary.substring(112, 128), 2)) * 0.1f;
                bloodFatResult.finalValues[4] = ((float) Integer.parseInt(byte2Binary.substring(128, 144), 2)) * 0.01f;
                bloodFatResult.finalValues[5] = ((float) Integer.parseInt(byte2Binary.substring(144, 160), 2)) * 0.01f;
                bloodFatResult.finalValues[6] = ((float) Integer.parseInt(byte2Binary.substring(160, 176), 2)) * 0.01f;
                bloodFatResult.finalValues[7] = ((float) Integer.parseInt(byte2Binary.substring(176, 192), 2)) * 0.01f;
                bloodFatResult.units = Integer.parseInt(byte2Binary.substring(192, 208), 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bloodFatResult;
    }

    public static BloodFatResult parseBloodFatData_sg(byte[] bArr) {
        byte[] bArr2 = bArr;
        BloodFatResult bloodFatResult = new BloodFatResult();
        if (bArr2 != null && bArr2.length >= 47) {
            byte[] bArr3 = new byte[(bArr2.length - 7)];
            System.arraycopy(bArr2, 6, bArr3, 0, bArr3.length);
            String byte2Binary = byte2Binary(bArr3);
            try {
                bloodFatResult.f72sn = Integer.parseInt(byte2Binary.substring(0, 16), 2);
                bloodFatResult.time[0] = (float) Integer.parseInt(byte2Binary.substring(25, 32), 2);
                bloodFatResult.time[1] = (float) Integer.parseInt(byte2Binary.substring(21, 25), 2);
                bloodFatResult.time[2] = (float) Integer.parseInt(byte2Binary.substring(16, 21), 2);
                bloodFatResult.time[3] = (float) Integer.parseInt(byte2Binary.substring(43, 48), 2);
                bloodFatResult.time[4] = (float) Integer.parseInt(byte2Binary.substring(37, 43), 2);
                bloodFatResult.time[5] = (float) Integer.parseInt(byte2Binary.substring(32, 37), 2);
                bloodFatResult.ReagentType = Integer.parseInt(byte2Binary.substring(48, 64), 2);
                bloodFatResult.values[0] = Integer.parseInt(byte2Binary.substring(64, 80), 2);
                bloodFatResult.values[1] = Integer.parseInt(byte2Binary.substring(80, 96), 2);
                bloodFatResult.values[2] = Integer.parseInt(byte2Binary.substring(96, 112), 2);
                bloodFatResult.values[3] = Integer.parseInt(byte2Binary.substring(112, 128), 2);
                bloodFatResult.values[4] = Integer.parseInt(byte2Binary.substring(128, 144), 2);
                bloodFatResult.values[5] = Integer.parseInt(byte2Binary.substring(144, 160), 2);
                bloodFatResult.values[6] = Integer.parseInt(byte2Binary.substring(160, 176), 2);
                bloodFatResult.values[7] = Integer.parseInt(byte2Binary.substring(176, 192), 2);
                bloodFatResult.finalValues[0] = ((float) Integer.parseInt(byte2Binary.substring(64, 80), 2)) * 0.1f;
                bloodFatResult.finalValues[1] = ((float) Integer.parseInt(byte2Binary.substring(80, 96), 2)) * 0.1f;
                bloodFatResult.finalValues[2] = ((float) Integer.parseInt(byte2Binary.substring(96, 112), 2)) * 0.01f;
                bloodFatResult.finalValues[3] = ((float) Integer.parseInt(byte2Binary.substring(112, 128), 2)) * 0.01f;
                bloodFatResult.finalValues[4] = ((float) Integer.parseInt(byte2Binary.substring(128, 144), 2)) * 0.01f;
                bloodFatResult.finalValues[5] = ((float) Integer.parseInt(byte2Binary.substring(144, 160), 2)) * 0.01f;
                bloodFatResult.finalValues[6] = ((float) Integer.parseInt(byte2Binary.substring(160, 176), 2)) * 0.01f;
                bloodFatResult.finalValues[7] = ((float) Integer.parseInt(byte2Binary.substring(176, 192), 2)) * 0.01f;
                bloodFatResult.units = Integer.parseInt(byte2Binary.substring(192, 208), 2);
                bloodFatResult.finalValues_change_unit[0] = (bloodFatResult.finalValues[0] * 11.0f) / 1000.0f;
                bloodFatResult.finalValues_change_unit[1] = (bloodFatResult.finalValues[1] * 17.0f) / 1000.0f;
                PrintStream printStream = System.out;
                printStream.println("bloodFatResult.finalValues[2]: " + bloodFatResult.finalValues[2]);
                PrintStream printStream2 = System.out;
                printStream2.println("bloodFatResult.units: " + bloodFatResult.units);
                String valueOf = String.valueOf(bloodFatResult.finalValues[2]);
                if (valueOf.contains(".")) {
                    if (valueOf.split("\\.")[1].toCharArray().length >= 2) {
                        bloodFatResult.finalValues_change_unit[2] = (Float.parseFloat(floatPointNoRound(bloodFatResult.finalValues[2])) * 601.0f) / 100.0f;
                    } else {
                        float[] fArr = bloodFatResult.finalValues_change_unit;
                        double d = (double) bloodFatResult.values[2];
                        Double.isNaN(d);
                        fArr[2] = (float) (((d * 601.0d) / 100.0d) / 100.0d);
                    }
                }
                PrintStream printStream3 = System.out;
                printStream3.println("bloodFatResult.finalValues_change_unit[2]: " + bloodFatResult.finalValues_change_unit[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bloodFatResult;
    }

    public static BloodFatResult parseBloodFatData(byte[] bArr) {
        byte[] bArr2 = bArr;
        BloodFatResult bloodFatResult = new BloodFatResult();
        if (bArr2 != null && bArr2.length >= 47) {
            byte[] bArr3 = new byte[(bArr2.length - 7)];
            System.arraycopy(bArr2, 6, bArr3, 0, bArr3.length);
            String byte2Binary = byte2Binary(bArr3);
            try {
                bloodFatResult.f72sn = Integer.parseInt(byte2Binary.substring(0, 16), 2);
                bloodFatResult.time[0] = (float) Integer.parseInt(byte2Binary.substring(25, 32), 2);
                bloodFatResult.time[1] = (float) Integer.parseInt(byte2Binary.substring(21, 25), 2);
                bloodFatResult.time[2] = (float) Integer.parseInt(byte2Binary.substring(16, 21), 2);
                bloodFatResult.time[3] = (float) Integer.parseInt(byte2Binary.substring(43, 48), 2);
                bloodFatResult.time[4] = (float) Integer.parseInt(byte2Binary.substring(37, 43), 2);
                bloodFatResult.time[5] = (float) Integer.parseInt(byte2Binary.substring(32, 37), 2);
                bloodFatResult.ReagentType = Integer.parseInt(byte2Binary.substring(48, 64), 2);
                bloodFatResult.values[0] = Integer.parseInt(byte2Binary.substring(64, 80), 2);
                bloodFatResult.values[1] = Integer.parseInt(byte2Binary.substring(80, 96), 2);
                bloodFatResult.values[2] = Integer.parseInt(byte2Binary.substring(96, 112), 2);
                bloodFatResult.values[3] = Integer.parseInt(byte2Binary.substring(112, 128), 2);
                bloodFatResult.values[4] = Integer.parseInt(byte2Binary.substring(128, 144), 2);
                bloodFatResult.values[5] = Integer.parseInt(byte2Binary.substring(144, 160), 2);
                bloodFatResult.values[6] = Integer.parseInt(byte2Binary.substring(160, 176), 2);
                bloodFatResult.values[7] = Integer.parseInt(byte2Binary.substring(176, 192), 2);
                bloodFatResult.finalValues[0] = ((float) Integer.parseInt(byte2Binary.substring(64, 80), 2)) * 0.1f;
                bloodFatResult.finalValues[1] = ((float) Integer.parseInt(byte2Binary.substring(80, 96), 2)) * 0.01f;
                bloodFatResult.finalValues[2] = ((float) Integer.parseInt(byte2Binary.substring(96, 112), 2)) * 0.01f;
                bloodFatResult.finalValues[3] = ((float) Integer.parseInt(byte2Binary.substring(112, 128), 2)) * 0.01f;
                bloodFatResult.finalValues[4] = ((float) Integer.parseInt(byte2Binary.substring(128, 144), 2)) * 0.01f;
                bloodFatResult.finalValues[5] = ((float) Integer.parseInt(byte2Binary.substring(144, 160), 2)) * 0.01f;
                bloodFatResult.finalValues[6] = ((float) Integer.parseInt(byte2Binary.substring(160, 176), 2)) * 0.01f;
                bloodFatResult.finalValues[7] = ((float) Integer.parseInt(byte2Binary.substring(176, 192), 2)) * 0.01f;
                bloodFatResult.uSampleType = Integer.parseInt(byte2Binary.substring(248, 256), 2);
                bloodFatResult.units = Integer.parseInt(byte2Binary.substring(192, 208), 2);
                PrintStream printStream = System.out;
                printStream.println(" bloodFatResult.units: " + bloodFatResult.units);
                PrintStream printStream2 = System.out;
                printStream2.println("uSampleType:" + bloodFatResult.uSampleType);
                PrintStream printStream3 = System.out;
                printStream3.println("values[0]:" + bloodFatResult.values[0]);
                PrintStream printStream4 = System.out;
                printStream4.println("values[1]:" + bloodFatResult.values[1]);
                PrintStream printStream5 = System.out;
                printStream5.println("values[2]:" + bloodFatResult.values[2]);
                PrintStream printStream6 = System.out;
                printStream6.println("values[3]:" + bloodFatResult.values[3]);
                PrintStream printStream7 = System.out;
                printStream7.println("values[4]:" + bloodFatResult.values[4]);
                PrintStream printStream8 = System.out;
                printStream8.println("finalValues[0]:" + bloodFatResult.finalValues[0]);
                PrintStream printStream9 = System.out;
                printStream9.println("finalValues[1]:" + bloodFatResult.finalValues[1]);
                PrintStream printStream10 = System.out;
                printStream10.println("finalValues[2]:" + bloodFatResult.finalValues[2]);
                PrintStream printStream11 = System.out;
                printStream11.println("finalValues[3]:" + bloodFatResult.finalValues[3]);
                PrintStream printStream12 = System.out;
                printStream12.println("finalValues[4]:" + bloodFatResult.finalValues[4]);
                bloodFatResult.finalValues_change_unit[0] = bloodFatResult.finalValues[0] * 18.0f;
                bloodFatResult.finalValues_change_unit[1] = (bloodFatResult.finalValues[1] * 8857.0f) / 100.0f;
                bloodFatResult.finalValues_change_unit[2] = (bloodFatResult.finalValues[2] * 3867.0f) / 100.0f;
                bloodFatResult.finalValues_change_unit[3] = (bloodFatResult.finalValues[3] * 3867.0f) / 100.0f;
                bloodFatResult.finalValues_change_unit[4] = (bloodFatResult.finalValues[4] * 3867.0f) / 100.0f;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bloodFatResult;
    }

    public static String[] getBloodFatRealValueText(BloodFatResult bloodFatResult) {
        String[] strArr = new String[5];
        for (int i = 0; i < strArr.length; i++) {
            if (i == 0) {
                try {
                    strArr[i] = floatPointNoRound(bloodFatResult.finalValues[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                strArr[i] = float2PointRound(bloodFatResult.finalValues[i]);
            }
        }
        return strArr;
    }

    public static String[] getBloodFatRealValueText02(BloodFatResult bloodFatResult, int i) {
        String[] strArr = new String[5];
        int i2 = 0;
        if (i == 0) {
            while (i2 < strArr.length) {
                if (i2 == 0) {
                    try {
                        strArr[i2] = floatPointNoRound(bloodFatResult.finalValues[i2]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    strArr[i2] = float2PointRound(bloodFatResult.finalValues[i2]);
                }
                i2++;
            }
        } else {
            while (i2 < strArr.length) {
                if (i2 == 0) {
                    try {
                        strArr[i2] = floatPointNoRound(bloodFatResult.finalValues_change_unit[i2]);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    strArr[i2] = calculateProfit((double) bloodFatResult.finalValues_change_unit[i2]);
                }
                i2++;
            }
        }
        return strArr;
    }

    public static String swap(String str, int i, int i2) {
        StringBuilder sb = new StringBuilder(str);
        char charAt = sb.charAt(i);
        sb.setCharAt(i, sb.charAt(i2));
        sb.setCharAt(i2, charAt);
        return sb.toString();
    }

    public static ImmuFluoResult parseImmuFluoResult(byte[] bArr) {
        ImmuFluoResult immuFluoResult = new ImmuFluoResult();
        if (bArr != null && bArr.length >= 71) {
            String byteArray2HexString = byteArray2HexString(Arrays.copyOfRange(bArr, 6, bArr.length));
            String swap = swap(swap(swap(swap(swap(swap(byteArray2HexString, 0, 2), 1, 3), 44, 46), 45, 47), 48, 50), 49, 51);
            PrintStream printStream = System.out;
            printStream.println("array2HexString转换前  " + byteArray2HexString);
            PrintStream printStream2 = System.out;
            printStream2.println("array2HexString转换成swap6后  " + swap);
            String byte2Binary = byte2Binary(toByteArray(swap));
            try {
                immuFluoResult.f73sn = Integer.parseInt(byte2Binary.substring(0, 16), 2);
                immuFluoResult.year = Integer.parseInt(byte2Binary.substring(185, 192), 2) + 2000;
                immuFluoResult.mon = Integer.parseInt(byte2Binary.substring(181, 185), 2);
                immuFluoResult.day = Integer.parseInt(byte2Binary.substring(176, 181), 2);
                immuFluoResult.hour = Integer.parseInt(byte2Binary.substring(203, 208), 2);
                immuFluoResult.minute = Integer.parseInt(byte2Binary.substring(197, 203), 2);
                immuFluoResult.second = Integer.parseInt(byte2Binary.substring(192, 197), 2) * 2;
                immuFluoResult.ReagentType = Integer.parseInt(byte2Binary.substring(208, 216), 2);
                immuFluoResult.itemnums = Integer.parseInt(byte2Binary.substring(216, BuildConfig.VERSION_CODE), 2);
                immuFluoResult.decimalBits = Integer.parseInt(byte2Binary.substring(InputDeviceCompat.SOURCE_GAMEPAD, 1032), 2);
                immuFluoResult.unitNum0 = Integer.parseInt(byte2Binary.substring(768, 776), 2);
                immuFluoResult.unitNum1 = Integer.parseInt(byte2Binary.substring(776, 784), 2);
                immuFluoResult.unitNum2 = Integer.parseInt(byte2Binary.substring(784, 792), 2);
                immuFluoResult.reagentLotNum = Integer.parseInt(byte2Binary.substring(1034, 1040), 2);
                String convertHexToString = convertHexToString(swap.substring(56, 120), immuFluoResult.itemnums);
                if (convertHexToString.contains(",")) {
                    immuFluoResult.itemName = convertHexToString.split(",");
                } else {
                    immuFluoResult.itemNameOne = convertHexToString;
                }
                PrintStream printStream3 = System.out;
                printStream3.println(" immuFluoResult.convertHexToString  : " + convertHexToString);
                PrintStream printStream4 = System.out;
                printStream4.println("unitNum0:" + immuFluoResult.unitNum0);
                PrintStream printStream5 = System.out;
                printStream5.println("unitNum1:" + immuFluoResult.unitNum1);
                PrintStream printStream6 = System.out;
                printStream6.println("unitNum2:" + immuFluoResult.unitNum2);
                PrintStream printStream7 = System.out;
                printStream7.println(" immuFluoResult.decimalBits:   " + immuFluoResult.decimalBits);
                getFrst(immuFluoResult, byte2Binary);
                getRefL(immuFluoResult, byte2Binary);
                getRefH(immuFluoResult, byte2Binary);
                getReagent(immuFluoResult, byte2Binary);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return immuFluoResult;
    }

    private static void getReagent(ImmuFluoResult immuFluoResult, String str) {
        immuFluoResult.rn_year = Integer.parseInt(str.substring(800, 808), 2);
        immuFluoResult.rn_mon = Integer.parseInt(str.substring(808, 816), 2);
        immuFluoResult.rn_day = Integer.parseInt(str.substring(816, 824), 2);
        immuFluoResult.rn_serial = Integer.parseInt(str.substring(824, 832), 2);
    }

    private static void getFrst(ImmuFluoResult immuFluoResult, String str) {
        String substring = str.substring(480, 488);
        String substring2 = str.substring(488, 496);
        String substring3 = str.substring(496, 504);
        String substring4 = str.substring(504, 512);
        String str2 = substring4 + substring3 + substring2 + substring;
        immuFluoResult.stringFrst0 = binaryToDecimal(str2);
        immuFluoResult.Frst0 = Float.intBitsToFloat(Integer.parseInt(str2, 2));
        System.out.println("str0:::  " + substring);
        System.out.println("str1:::  " + substring2);
        System.out.println("str2:::  " + substring3);
        System.out.println("str3:::  " + substring4);
        System.out.println("strall:::  " + str2);
        String substring5 = str.substring(512, 520);
        String substring6 = str.substring(520, 528);
        String substring7 = str.substring(528, 536);
        String str3 = str.substring(536, 544) + substring7 + substring6 + substring5;
        immuFluoResult.stringFrst1 = binaryToDecimal(str3);
        immuFluoResult.Frst1 = Float.intBitsToFloat(Integer.parseInt(str3, 2));
        String substring8 = str.substring(544, 552);
        String substring9 = str.substring(552, 560);
        String substring10 = str.substring(560, 568);
        String str4 = str.substring(568, 576) + substring10 + substring9 + substring8;
        immuFluoResult.stringFrst2 = binaryToDecimal(str4);
        immuFluoResult.Frst2 = Float.intBitsToFloat(Integer.parseInt(str4, 2));
    }

    private static void getRefH(ImmuFluoResult immuFluoResult, String str) {
        String substring = str.substring(672, 680);
        String substring2 = str.substring(680, 688);
        String substring3 = str.substring(688, 696);
        String str2 = str.substring(696, 704) + substring3 + substring2 + substring;
        immuFluoResult.stringRefH0 = binaryToDecimal(str2);
        immuFluoResult.RefH0 = Float.intBitsToFloat(Integer.parseInt(str2, 2));
        String substring4 = str.substring(704, 712);
        String substring5 = str.substring(712, 720);
        String substring6 = str.substring(720, 728);
        String str3 = str.substring(728, 736) + substring6 + substring5 + substring4;
        immuFluoResult.stringRefH1 = binaryToDecimal(str3);
        immuFluoResult.RefH1 = Float.intBitsToFloat(Integer.parseInt(str3, 2));
        String substring7 = str.substring(736, 744);
        String substring8 = str.substring(744, 752);
        String substring9 = str.substring(752, 760);
        immuFluoResult.RefH2 = Float.intBitsToFloat(Integer.parseInt(str.substring(760, 768) + substring9 + substring8 + substring7, 2));
        PrintStream printStream = System.out;
        StringBuilder sb = new StringBuilder();
        sb.append(" immuFluoResult.RefH0:  ");
        sb.append(immuFluoResult.RefH0);
        printStream.println(sb.toString());
        System.out.println(" immuFluoResult.RefH1:  " + immuFluoResult.RefH1);
        System.out.println(" immuFluoResult.RefH2:  " + immuFluoResult.RefH2);
    }

    private static void getRefL(ImmuFluoResult immuFluoResult, String str) {
        String substring = str.substring(576, 584);
        String substring2 = str.substring(584, 592);
        String substring3 = str.substring(592, 600);
        String str2 = str.substring(600, 608) + substring3 + substring2 + substring;
        immuFluoResult.stringRefL0 = binaryToDecimal(str2);
        immuFluoResult.RefL0 = Float.intBitsToFloat(Integer.parseInt(str2, 2));
        String substring4 = str.substring(608, 616);
        String substring5 = str.substring(616, 624);
        String substring6 = str.substring(624, 632);
        String str3 = str.substring(632, 640) + substring6 + substring5 + substring4;
        immuFluoResult.stringRefL1 = binaryToDecimal(str3);
        immuFluoResult.RefL1 = Float.intBitsToFloat(Integer.parseInt(str3, 2));
        String substring7 = str.substring(640, 648);
        String substring8 = str.substring(648, 656);
        String substring9 = str.substring(656, 664);
        String str4 = str.substring(664, 672) + substring9 + substring8 + substring7;
        immuFluoResult.stringRefL2 = binaryToDecimal(str4);
        immuFluoResult.RefL2 = Float.intBitsToFloat(Integer.parseInt(str4, 2));
        System.out.println(" immuFluoResult.RefL0:  " + immuFluoResult.RefL0);
        System.out.println(" immuFluoResult.RefL1:  " + immuFluoResult.RefL1);
        System.out.println(" immuFluoResult.RefL2:  " + immuFluoResult.RefL2);
    }

    public static String[] getProteinRealValueText(ProteinResult proteinResult) {
        String[] strArr = new String[2];
        for (int i = 0; i < strArr.length; i++) {
            try {
                strArr[i] = float2Point(proteinResult.values[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strArr;
    }

    public static String floatPoint(float f) {
        float f2 = f * 100.0f;
        float f3 = f2 % 10.0f;
        if (f3 == 5.0f) {
            return String.valueOf(new BigDecimal((double) (((float) (((int) (f2 + 10.0f)) - ((int) f3))) / 100.0f)).setScale(1, 4).floatValue());
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.0");
        BigDecimal bigDecimal = new BigDecimal((double) f);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(bigDecimal.doubleValue());
    }

    public static String floatPointNoRound(float f) {
        float f2 = f * 100.0f;
        DecimalFormat decimalFormat = new DecimalFormat("#0.0");
        BigDecimal bigDecimal = new BigDecimal((double) (((float) (((int) f2) - ((int) (f2 % 10.0f)))) / 100.0f));
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(bigDecimal.doubleValue());
    }

    public static String float2Point(float f) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        BigDecimal bigDecimal = new BigDecimal((double) f);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(bigDecimal.doubleValue());
    }

    public static String float2PointRound(float f) {
        float f2 = f * 1000.0f;
        float f3 = f2 % 10.0f;
        if (f3 == 5.0f) {
            return String.valueOf(new BigDecimal((double) (((float) (((int) (f2 + 10.0f)) - ((int) f3))) / 1000.0f)).setScale(1, 4).floatValue());
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        BigDecimal bigDecimal = new BigDecimal((double) f);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(bigDecimal.doubleValue());
    }

    public static String float3Point(float f) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.000");
        BigDecimal bigDecimal = new BigDecimal((double) f);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(bigDecimal.doubleValue());
    }

    public static String float4Point(float f) {
        DecimalFormat decimalFormat = new DecimalFormat("#00%");
        BigDecimal bigDecimal = new BigDecimal((double) f);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(bigDecimal.doubleValue());
    }

    public static String calculateProfit(double d) {
        String format = new DecimalFormat("#.0000").format(d);
        if (".".equals(format.substring(0, 1))) {
            format = "0" + format;
        }
        return format.substring(0, firstIndexOf(format, ".") + 3);
    }

    public static int firstIndexOf(String str, String str2) {
        int i = 0;
        while (i < str.length() - str2.length()) {
            int i2 = 0;
            while (i2 < str2.length() && str.charAt(i + i2) == str2.charAt(i2)) {
                i2++;
            }
            if (i2 == str2.length()) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static void closeBluetooth() {
        BluetoothAdapter adapter = ((BluetoothManager) AppBase.getInstance().getContext().getSystemService("bluetooth")).getAdapter();
        if (adapter.isEnabled()) {
            adapter.disable();
        }
    }

    public static void share(Activity activity, Fragment fragment) {
        String activityShot = activityShot(activity, fragment);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(activityShot)));
        intent.setType("image/*");
        activity.startActivity(Intent.createChooser(intent, activity.getString(C0418R.string.selection_platform)));
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x00db A[SYNTHETIC, Splitter:B:18:0x00db] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00e5 A[SYNTHETIC, Splitter:B:24:0x00e5] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00f4 A[SYNTHETIC, Splitter:B:31:0x00f4] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:21:0x00e0=Splitter:B:21:0x00e0, B:15:0x00d6=Splitter:B:15:0x00d6} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String activityShot(android.app.Activity r10, android.app.Fragment r11) {
        /*
            android.view.Window r0 = r10.getWindow()
            android.view.View r0 = r0.getDecorView()
            r1 = 1
            r0.setDrawingCacheEnabled(r1)
            r0.buildDrawingCache()
            android.graphics.Rect r1 = new android.graphics.Rect
            r1.<init>()
            r0.getWindowVisibleDisplayFrame(r1)
            int r2 = r1.top
            android.view.WindowManager r10 = r10.getWindowManager()
            android.util.DisplayMetrics r2 = new android.util.DisplayMetrics
            r2.<init>()
            android.view.Display r10 = r10.getDefaultDisplay()
            r10.getMetrics(r2)
            int r10 = r2.widthPixels
            int r2 = r2.heightPixels
            android.graphics.Bitmap r2 = r0.getDrawingCache()
            android.view.View r11 = r11.getView()
            r3 = 2131230983(0x7f080107, float:1.8078034E38)
            android.view.View r11 = r11.findViewById(r3)
            android.widget.ScrollView r11 = (android.widget.ScrollView) r11
            android.graphics.Bitmap r3 = shotScrollView(r11)
            int r4 = r0.getHeight()
            int r5 = r11.getHeight()
            int r4 = r4 - r5
            int r5 = r3.getHeight()
            int r4 = r4 + r5
            android.graphics.Bitmap$Config r5 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r5 = android.graphics.Bitmap.createBitmap(r10, r4, r5)
            android.graphics.Canvas r6 = new android.graphics.Canvas
            r6.<init>(r5)
            int r7 = r0.getHeight()
            int r1 = r1.bottom
            int r7 = r7 - r1
            int r1 = r0.getHeight()
            int r11 = r11.getHeight()
            int r1 = r1 - r11
            int r1 = r1 - r7
            android.graphics.Rect r11 = new android.graphics.Rect
            r7 = 0
            r11.<init>(r7, r7, r10, r1)
            android.graphics.Rect r8 = new android.graphics.Rect
            r8.<init>(r7, r7, r10, r1)
            r9 = 0
            r6.drawBitmap(r2, r11, r8, r9)
            android.graphics.Rect r11 = new android.graphics.Rect
            int r2 = r3.getHeight()
            r11.<init>(r7, r7, r10, r2)
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>(r7, r1, r10, r4)
            r6.drawBitmap(r3, r11, r2, r9)
            r0.destroyDrawingCache()
            r0.setDrawingCacheEnabled(r7)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = ".Share"
            r10.append(r11)
            java.lang.String r11 = "_yyyyMMdd_HHmmss"
            java.lang.String r11 = getDate(r11)
            r10.append(r11)
            java.lang.String r11 = ".png"
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            java.io.File r11 = new java.io.File
            java.lang.String r0 = com.huanghuang.kangshangyiliao.util.PathUtils.getShareImagePath()
            r11.<init>(r0, r10)
            java.io.FileOutputStream r10 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x00df, IOException -> 0x00d5 }
            r10.<init>(r11)     // Catch:{ FileNotFoundException -> 0x00df, IOException -> 0x00d5 }
            android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ FileNotFoundException -> 0x00d0, IOException -> 0x00cd, all -> 0x00ca }
            r1 = 100
            r5.compress(r0, r1, r10)     // Catch:{ FileNotFoundException -> 0x00d0, IOException -> 0x00cd, all -> 0x00ca }
            r10.flush()     // Catch:{ FileNotFoundException -> 0x00d0, IOException -> 0x00cd, all -> 0x00ca }
            r10.close()     // Catch:{ IOException -> 0x00e9 }
            goto L_0x00ed
        L_0x00ca:
            r11 = move-exception
            r9 = r10
            goto L_0x00f2
        L_0x00cd:
            r0 = move-exception
            r9 = r10
            goto L_0x00d6
        L_0x00d0:
            r0 = move-exception
            r9 = r10
            goto L_0x00e0
        L_0x00d3:
            r11 = move-exception
            goto L_0x00f2
        L_0x00d5:
            r0 = move-exception
        L_0x00d6:
            r0.printStackTrace()     // Catch:{ all -> 0x00d3 }
            if (r9 == 0) goto L_0x00ed
            r9.close()     // Catch:{ IOException -> 0x00e9 }
            goto L_0x00ed
        L_0x00df:
            r0 = move-exception
        L_0x00e0:
            r0.printStackTrace()     // Catch:{ all -> 0x00d3 }
            if (r9 == 0) goto L_0x00ed
            r9.close()     // Catch:{ IOException -> 0x00e9 }
            goto L_0x00ed
        L_0x00e9:
            r10 = move-exception
            r10.printStackTrace()
        L_0x00ed:
            java.lang.String r10 = r11.getAbsolutePath()
            return r10
        L_0x00f2:
            if (r9 == 0) goto L_0x00fc
            r9.close()     // Catch:{ IOException -> 0x00f8 }
            goto L_0x00fc
        L_0x00f8:
            r10 = move-exception
            r10.printStackTrace()
        L_0x00fc:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.util.Utils.activityShot(android.app.Activity, android.app.Fragment):java.lang.String");
    }

    public static Bitmap shotScrollView(ScrollView scrollView) {
        int i = 0;
        for (int i2 = 0; i2 < scrollView.getChildCount(); i2++) {
            i += scrollView.getChildAt(i2).getHeight();
            scrollView.getChildAt(i2).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        Bitmap createBitmap = Bitmap.createBitmap(scrollView.getWidth(), i, Bitmap.Config.RGB_565);
        scrollView.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public static void copyDBToSDcrad() {
        String format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        copyFile("data/data/com.xiangzhi.kangshangyiliao/databases/" + SQLite.name, new File(PathUtils.getDBPath(), format + "_" + SQLite.name).getAbsolutePath());
    }

    public static void copyFile(String str, String str2) {
        try {
            File file = new File(str);
            File file2 = new File(str2);
            if (!file2.exists()) {
                file2.createNewFile();
            }
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(str);
                FileOutputStream fileOutputStream = new FileOutputStream(str2);
                byte[] bArr = new byte[1444];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read != -1) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        fileInputStream.close();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    public static int[] getPOCTAbnormal(POCTResult pOCTResult, String str) {
        int[] iArr = new int[11];
        float[] fArr = pOCTResult.finalValues;
        if ("0".equals(str)) {
            if_and_if(iArr, fArr, new float[]{15.0f, 1.0f, 0.12f, 1.5f, 0.05f, 0.0f, 20.0f, 3.0f, 50.0f, 0.5f, 0.0f}, new float[]{20.0f, 3.7f, 0.8f, 7.0f, 0.5f, 0.1f, 40.0f, 8.0f, 70.0f, 5.0f, 1.0f});
        } else if ("1".equals(str)) {
            if_and_if(iArr, fArr, new float[]{11.0f, 1.0f, 0.12f, 1.5f, 0.05f, 0.0f, 20.0f, 3.0f, 50.0f, 0.5f, 0.0f}, new float[]{12.0f, 3.7f, 0.8f, 7.0f, 0.5f, 0.1f, 40.0f, 8.0f, 70.0f, 5.0f, 1.0f});
        } else if ("2".equals(str)) {
            if_and_if(iArr, fArr, new float[]{8.0f, 1.0f, 0.12f, 1.5f, 0.05f, 0.0f, 20.0f, 3.0f, 50.0f, 0.5f, 0.0f}, new float[]{10.0f, 3.7f, 0.8f, 7.0f, 0.5f, 0.1f, 40.0f, 8.0f, 70.0f, 5.0f, 1.0f});
        } else {
            if_and_if(iArr, fArr, new float[]{3.5f, 1.0f, 0.12f, 1.5f, 0.05f, 0.0f, 20.0f, 3.0f, 50.0f, 0.5f, 0.0f}, new float[]{9.5f, 3.7f, 0.8f, 7.0f, 0.5f, 0.1f, 40.0f, 8.0f, 70.0f, 5.0f, 1.0f});
        }
        return iArr;
    }

    public static int[] getProteinAbnormal_MAN(ProteinResult proteinResult, String str) {
        int[] iArr = new int[2];
        float[] fArr = proteinResult.values;
        if ("0".equals(str)) {
            if_and_if(iArr, fArr, new float[]{180.0f, 0.4f}, new float[]{190.0f, 0.5f});
        } else if ("1".equals(str)) {
            if_and_if(iArr, fArr, new float[]{110.0f, 0.4f}, new float[]{120.0f, 0.5f});
        } else if ("2".equals(str)) {
            if_and_if(iArr, fArr, new float[]{120.0f, 0.4f}, new float[]{140.0f, 0.5f});
        } else {
            if_and_if(iArr, fArr, new float[]{130.0f, 0.4f}, new float[]{175.0f, 0.5f});
        }
        return iArr;
    }

    public static int[] getProteinAbnormal_WOMAN(ProteinResult proteinResult, String str) {
        int[] iArr = new int[2];
        float[] fArr = proteinResult.values;
        if ("0".equals(str)) {
            if_and_if(iArr, fArr, new float[]{180.0f, 0.35f}, new float[]{190.0f, 0.45f});
        } else if ("1".equals(str)) {
            if_and_if(iArr, fArr, new float[]{110.0f, 0.35f}, new float[]{120.0f, 0.45f});
        } else if ("2".equals(str)) {
            if_and_if(iArr, fArr, new float[]{120.0f, 0.35f}, new float[]{140.0f, 0.45f});
        } else {
            if_and_if(iArr, fArr, new float[]{115.0f, 0.35f}, new float[]{150.0f, 0.45f});
        }
        return iArr;
    }

    private static void if_and_if(int[] iArr, float[] fArr, float[] fArr2, float[] fArr3) {
        for (int i = 0; i < iArr.length; i++) {
            if (fArr[i] < fArr2[i]) {
                iArr[i] = -1;
            } else if (fArr[i] > fArr3[i]) {
                iArr[i] = 1;
            } else {
                iArr[i] = 0;
            }
        }
    }

    public static int[] getBloodFatAbnormal(BloodFatResult bloodFatResult, String str) {
        int[] iArr = new int[5];
        float[] fArr = bloodFatResult.finalValues;
        float[] fArr2 = {fArr[3], fArr[1], fArr[2], fArr[4], fArr[0]};
        if ("0".equals(str)) {
            if_and_if(iArr, fArr2, new float[]{3.12f, 0.44f, 1.0f, 0.0f, 3.9f}, new float[]{5.18f, 1.7f, 1.9f, 3.1f, 6.1f});
        } else if ("1".equals(str)) {
            if_and_if(iArr, fArr2, new float[]{3.12f, 0.44f, 1.0f, 0.0f, 3.9f}, new float[]{5.18f, 1.7f, 1.9f, 3.1f, 6.1f});
        } else if ("2".equals(str)) {
            if_and_if(iArr, fArr2, new float[]{3.12f, 0.44f, 1.0f, 0.0f, 3.9f}, new float[]{5.18f, 1.7f, 1.9f, 3.1f, 6.1f});
        } else {
            if_and_if(iArr, fArr2, new float[]{3.12f, 0.44f, 1.0f, 0.0f, 3.9f}, new float[]{5.18f, 1.7f, 1.9f, 3.1f, 6.1f});
        }
        return iArr;
    }

    public static int[] getBloodFatAbnormal02(BloodFatResult bloodFatResult, String str) {
        int[] iArr = new int[5];
        float[] fArr = bloodFatResult.finalValues_change_unit;
        float[] fArr2 = {fArr[3], fArr[1], fArr[2], fArr[4], fArr[0]};
        if ("0".equals(str)) {
            if_and_if(iArr, fArr2, new float[]{120.65f, 38.97f, 38.67f, 0.0f, 70.2f}, new float[]{200.31f, 150.56f, 73.47f, 119.87f, 109.8f});
        } else if ("1".equals(str)) {
            if_and_if(iArr, fArr2, new float[]{120.65f, 38.97f, 38.67f, 0.0f, 70.2f}, new float[]{200.31f, 150.56f, 73.47f, 119.87f, 109.8f});
        } else if ("2".equals(str)) {
            if_and_if(iArr, fArr2, new float[]{120.65f, 38.97f, 38.67f, 0.0f, 70.2f}, new float[]{200.31f, 150.56f, 73.47f, 119.87f, 109.8f});
        } else {
            if_and_if(iArr, fArr2, new float[]{120.65f, 38.97f, 38.67f, 0.0f, 70.2f}, new float[]{200.31f, 150.56f, 73.47f, 119.87f, 109.8f});
        }
        return iArr;
    }

    public static int[] getBloodFatAbnormal_xx(BloodFatResult bloodFatResult, String str) {
        int[] iArr = new int[2];
        if_and_if(iArr, bloodFatResult.finalValues, new float[]{7.0f, 120.0f}, new float[]{50.0f, 170.0f});
        return iArr;
    }

    public static int[] getBloodFatAbnormal_lf(BloodFatResult bloodFatResult, String str) {
        int[] iArr = new int[4];
        if_and_if(iArr, bloodFatResult.finalValues, new float[]{38.0f, 7.0f, 13.0f, 40.0f}, new float[]{126.0f, 50.0f, 40.0f, 55.0f});
        return iArr;
    }

    public static int[] getBloodFatAbnormal_lf_three(BloodFatResult bloodFatResult, String str) {
        int[] iArr = new int[3];
        if_and_if(iArr, bloodFatResult.finalValues, new float[]{7.0f, 13.0f, 40.0f}, new float[]{50.0f, 40.0f, 55.0f});
        return iArr;
    }

    public static int[] getBloodFatAbnormal_kf(BloodFatResult bloodFatResult, String str) {
        int[] iArr = new int[3];
        float[] fArr = bloodFatResult.finalValues;
        if_and_if(iArr, new float[]{fArr[1], fArr[0], fArr[2]}, new float[]{155.0f, 41.0f, 2.6f}, new float[]{428.0f, 111.0f, 9.5f});
        return iArr;
    }

    public static int[] getBloodFatAbnormal_kf02(BloodFatResult bloodFatResult, String str) {
        int[] iArr = new int[3];
        float[] fArr = bloodFatResult.finalValues_change_unit;
        if_and_if(iArr, new float[]{fArr[1], fArr[0], fArr[2]}, new float[]{2.6f, 0.4f, 15.6f}, new float[]{7.3f, 1.2f, 57.1f});
        return iArr;
    }

    public static int[] getBloodFatAbnormal_dx(BloodFatResult bloodFatResult, String str) {
        int[] iArr = new int[3];
        float[] fArr = bloodFatResult.finalValues;
        if_and_if(iArr, new float[]{fArr[2], fArr[1], fArr[0]}, new float[]{3.12f, 155.0f, 3.9f}, new float[]{5.18f, 428.0f, 6.1f});
        return iArr;
    }

    public static int[] getBloodFatAbnormal_dx02(BloodFatResult bloodFatResult) {
        int[] iArr = new int[3];
        float[] fArr = bloodFatResult.finalValues_change_unit;
        if_and_if(iArr, new float[]{fArr[2], fArr[1], fArr[0]}, new float[]{120.65f, 2.6f, 70.2f}, new float[]{200.31f, 7.3f, 109.8f});
        return iArr;
    }

    public static int[] getImmuFlorAbnormal(ImmuFluoResult immuFluoResult) {
        int[] iArr = new int[3];
        float f = immuFluoResult.Frst0;
        float f2 = immuFluoResult.Frst1;
        float f3 = immuFluoResult.Frst2;
        if (f < 0.0f) {
            iArr[0] = -1;
        } else if (f > 5.0f) {
            iArr[0] = 1;
        } else {
            iArr[0] = 0;
        }
        if (f2 < 0.0f) {
            iArr[1] = -1;
        } else if (f2 > 10.0f) {
            iArr[1] = 1;
        } else {
            iArr[1] = 0;
        }
        if (f3 < 0.0f) {
            iArr[2] = -1;
        } else if (f3 > 0.5f) {
            iArr[2] = 1;
        } else {
            iArr[2] = 0;
        }
        return iArr;
    }

    public static int getAbnormalTextColor() {
        return Color.parseColor("#FF0000");
    }

    public static int getAbnormalBackgroundColor() {
        return Color.parseColor("#FCE3E7");
    }

    public static int[] getUrinalysisAbnormal(UrinalysisResult urinalysisResult) {
        int[] iArr = new int[14];
        String[] realValueText = getRealValueText(urinalysisResult);
        for (int i = 0; i < realValueText.length; i++) {
            if (realValueText[i].contains("+")) {
                iArr[i] = 2;
            }
        }
        double parseFloat = (double) Float.parseFloat(realValueText[8]);
        if (parseFloat < 5.5d) {
            iArr[8] = -1;
        } else if (parseFloat > 8.5d) {
            iArr[8] = 1;
        }
        double parseFloat2 = (double) Float.parseFloat(realValueText[10]);
        if (parseFloat2 < 1.009d) {
            iArr[10] = -1;
        } else if (parseFloat2 > 1.025d) {
            iArr[10] = 1;
        }
        return iArr;
    }

    public static String getNickName() {
        NickName userInfo = getUserInfo();
        return userInfo != null ? userInfo.nickName : "";
    }

    public static String getUserId() {
        NickName query = new NickNameDao().query(getNickName());
        return query.f81id + "";
    }

    public static void saveUserToCache(NickName nickName) {
        Cache instance = Cache.getInstance();
        if (nickName == null) {
            instance.save(Cache.USER_INFO, "");
        } else {
            instance.save(Cache.USER_INFO, Base64.encodeToString(nickName.toString().getBytes(), 0));
        }
    }

    public static NickName getUserInfo() {
        String read = Cache.getInstance().read(Cache.USER_INFO);
        if (TextUtils.isEmpty(read)) {
            return null;
        }
        try {
            return (NickName) new Gson().fromJson(new String(Base64.decode(read, 0), StandardCharsets.UTF_8), NickName.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getClinicName() {
        ClinicName clinicInfo = getClinicInfo();
        return clinicInfo != null ? clinicInfo.clinicName : "";
    }

    public static void saveClinicToCache(ClinicName clinicName) {
        Cache instance = Cache.getInstance();
        if (clinicName == null) {
            instance.save(Cache.CLINIC_INFO, "");
        } else {
            instance.save(Cache.CLINIC_INFO, Base64.encodeToString(clinicName.toString().getBytes(), 0));
        }
    }

    public static ClinicName getClinicInfo() {
        String read = Cache.getInstance().read(Cache.CLINIC_INFO);
        if (TextUtils.isEmpty(read)) {
            return null;
        }
        try {
            return (ClinicName) new Gson().fromJson(new String(Base64.decode(read, 0), StandardCharsets.UTF_8), ClinicName.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String binaryToDecimal(String str) {
        String str2 = str.substring(0, 1).equals("1") ? SimpleFormatter.DEFAULT_DELIMITER : "";
        int i = 9;
        int intValue = Integer.valueOf(str.substring(1, 9), 2).intValue() - 127;
        str.substring(9, 32);
        double d = 1.0d;
        while (i < 31) {
            int i2 = i + 1;
            String substring = str.substring(i, i2);
            if (substring.equals("1")) {
                d += Math.pow(2.0d, (double) (8 - i));
            } else {
                substring.equals("0");
            }
            i = i2;
        }
        double pow = d * Math.pow(2.0d, (double) intValue);
        if ((1000.0d * pow) % 10.0d >= 5.0d) {
            pow += 0.01d;
        }
        double d2 = (double) ((int) (pow * 100.0d));
        Double.isNaN(d2);
        return str2 + new DecimalFormat(".00").format(d2 * 0.01d);
    }

    public static String bytes2HexString(byte[] bArr) {
        String str = "";
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            str = str + hexString.toUpperCase();
        }
        return str;
    }

    public static byte[] setPrintTitle(String str) {
        byte[] bArr = {-91, 85, 8, 0, 32, 5};
        byte[] bArr2 = new byte[0];
        try {
            bArr2 = str.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        bArr[2] = toByteArray(bytesIntByteString(bArr2.length + 4))[0];
        Log.d("title_data.length", "............." + bArr2.length);
        Log.d("title.length()", "............." + str.length());
        byte[] bArr3 = new byte[(bArr.length + bArr2.length + 1)];
        for (int i = 0; i < bArr3.length; i++) {
            if (i < bArr.length) {
                bArr3[i] = bArr[i];
            } else if (i < bArr2.length + bArr.length) {
                bArr3[i] = bArr2[i - bArr.length];
            } else {
                bArr3[i] = 0;
            }
        }
        byte[] checksum = getChecksum(bArr3);
        Log.d("zdc set printer name", "............." + bytes2HexString(checksum));
        return checksum;
    }

    public static byte[] setPatientInfomation(String str, String str2, String str3, String str4) {
        byte[] bArr = {-91, 85, 8, 0, 32, 6};
        byte[] bArr2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        try {
            bArr2 = str2.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int parseInt = Integer.parseInt(str);
        byte[] bArr3 = {(byte) (parseInt & 255), (byte) ((parseInt >> 8) & 255)};
        byte[] bArr4 = {(byte) (Integer.parseInt(str3) & 255)};
        byte[] bArr5 = {(byte) (Integer.parseInt(str4) & 255)};
        bArr[2] = toByteArray(bytesIntByteString(36))[0];
        byte[] bArr6 = new byte[39];
        for (int i = 0; i < bArr6.length; i++) {
            if (i < bArr.length) {
                bArr6[i] = bArr[i];
            } else if (i >= bArr.length + 2) {
                if (i < bArr.length + 2 + 2) {
                    bArr6[i] = bArr3[i - (bArr.length + 2)];
                } else if (i < bArr.length + 2 + 2 + bArr2.length) {
                    bArr6[i] = bArr2[i - ((bArr.length + 2) + 2)];
                } else if (i >= bArr.length + 2 + 2 + 16) {
                    if (i < bArr.length + 2 + 2 + 16 + 1) {
                        bArr6[i] = bArr4[i - (((bArr.length + 2) + 2) + 16)];
                    } else if (i < bArr.length + 2 + 2 + 16 + 1 + 1) {
                        bArr6[i] = bArr5[i - ((((bArr.length + 2) + 2) + 16) + 1)];
                    } else if (i >= bArr.length + 2 + 2 + 16 + 1 + 1 + 10) {
                        bArr6[i] = 0;
                    }
                }
            }
        }
        byte[] checksum = getChecksum(bArr6);
        Log.d("zdc ", "set patientInfo........" + bytes2HexString(checksum));
        return checksum;
    }

    public static byte[] setBloodFatPrintTitle(String str) {
        byte[] bArr = {-91, 85, 8, 0, 96, SyslogMessage.FACILITY_LOCAL_USE_1};
        byte[] bArr2 = new byte[0];
        try {
            bArr2 = str.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        bArr[2] = toByteArray(bytesIntByteString(bArr2.length + 4))[0];
        Log.d("title_data.length", "............." + bArr2.length);
        Log.d("title.length()", "............." + str.length());
        byte[] bArr3 = new byte[(bArr.length + bArr2.length + 1)];
        for (int i = 0; i < bArr3.length; i++) {
            if (i < bArr.length) {
                bArr3[i] = bArr[i];
            } else if (i < bArr2.length + bArr.length) {
                bArr3[i] = bArr2[i - bArr.length];
            } else {
                bArr3[i] = 0;
            }
        }
        byte[] checksum = getChecksum(bArr3);
        Log.d("zdc set printer name", "............." + bytes2HexString(checksum));
        return checksum;
    }

    public static byte[] setBloodFatPatientInfomation(String str, String str2, String str3, String str4) {
        byte[] bArr = {-91, 85, 8, 0, 96, SyslogMessage.FACILITY_LOCAL_USE_2};
        byte[] bArr2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        try {
            bArr2 = str2.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int parseInt = Integer.parseInt(str);
        byte[] bArr3 = {(byte) (parseInt & 255), (byte) ((parseInt >> 8) & 255)};
        byte[] bArr4 = {(byte) (Integer.parseInt(str3) & 255)};
        byte[] bArr5 = {(byte) (Integer.parseInt(str4) & 255)};
        bArr[2] = toByteArray(bytesIntByteString(36))[0];
        byte[] bArr6 = new byte[39];
        for (int i = 0; i < bArr6.length; i++) {
            if (i < bArr.length) {
                bArr6[i] = bArr[i];
            } else if (i >= bArr.length + 2) {
                if (i < bArr.length + 2 + 2) {
                    bArr6[i] = bArr3[i - (bArr.length + 2)];
                } else if (i < bArr.length + 2 + 2 + bArr2.length) {
                    bArr6[i] = bArr2[i - ((bArr.length + 2) + 2)];
                } else if (i >= bArr.length + 2 + 2 + 16) {
                    if (i < bArr.length + 2 + 2 + 16 + 1) {
                        bArr6[i] = bArr4[i - (((bArr.length + 2) + 2) + 16)];
                    } else if (i < bArr.length + 2 + 2 + 16 + 1 + 1) {
                        bArr6[i] = bArr5[i - ((((bArr.length + 2) + 2) + 16) + 1)];
                    } else if (i >= bArr.length + 2 + 2 + 16 + 1 + 1 + 10) {
                        bArr6[i] = 0;
                    }
                }
            }
        }
        byte[] checksum = getChecksum(bArr6);
        Log.d("zdc ", "set patientInfo........" + bytes2HexString(checksum));
        return checksum;
    }

    public static byte[] setImmunoFluoPrintTitle(String str) {
        byte[] bArr = {-91, 85, 8, 0, -112, SyslogMessage.FACILITY_LOCAL_USE_1};
        byte[] bArr2 = new byte[0];
        try {
            bArr2 = str.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        bArr[2] = toByteArray(bytesIntByteString(bArr2.length + 4))[0];
        Log.d("title_data.length", "............." + bArr2.length);
        Log.d("title.length()", "............." + str.length());
        byte[] bArr3 = new byte[(bArr.length + bArr2.length + 1)];
        for (int i = 0; i < bArr3.length; i++) {
            if (i < bArr.length) {
                bArr3[i] = bArr[i];
            } else if (i < bArr2.length + bArr.length) {
                bArr3[i] = bArr2[i - bArr.length];
            } else {
                bArr3[i] = 0;
            }
        }
        byte[] checksum = getChecksum(bArr3);
        Log.d("zdc set printer name", "............." + bytes2HexString(checksum));
        return checksum;
    }

    public static byte[] setImmunoFluoPatientInfomation(String str, String str2, String str3, String str4) {
        byte[] bArr = {-91, 85, 8, 0, -112, SyslogMessage.FACILITY_LOCAL_USE_2};
        byte[] bArr2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        try {
            bArr2 = str2.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int parseInt = Integer.parseInt(str);
        byte[] bArr3 = {(byte) (parseInt & 255), (byte) ((parseInt >> 8) & 255)};
        byte[] bArr4 = {(byte) (Integer.parseInt(str3) & 255)};
        byte[] bArr5 = {(byte) (Integer.parseInt(str4) & 255)};
        bArr[2] = toByteArray(bytesIntByteString(36))[0];
        byte[] bArr6 = new byte[39];
        for (int i = 0; i < bArr6.length; i++) {
            if (i < bArr.length) {
                bArr6[i] = bArr[i];
            } else if (i >= bArr.length + 2) {
                if (i < bArr.length + 2 + 2) {
                    bArr6[i] = bArr3[i - (bArr.length + 2)];
                } else if (i < bArr.length + 2 + 2 + bArr2.length) {
                    bArr6[i] = bArr2[i - ((bArr.length + 2) + 2)];
                } else if (i >= bArr.length + 2 + 2 + 16) {
                    if (i < bArr.length + 2 + 2 + 16 + 1) {
                        bArr6[i] = bArr4[i - (((bArr.length + 2) + 2) + 16)];
                    } else if (i < bArr.length + 2 + 2 + 16 + 1 + 1) {
                        bArr6[i] = bArr5[i - ((((bArr.length + 2) + 2) + 16) + 1)];
                    } else if (i >= bArr.length + 2 + 2 + 16 + 1 + 1 + 10) {
                        bArr6[i] = 0;
                    }
                }
            }
        }
        byte[] checksum = getChecksum(bArr6);
        Log.d("zdc ", "set patientInfo........" + bytes2HexString(checksum));
        return checksum;
    }

    public static String convertHexToString(String str, int i) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        if (i == 1) {
            cycleOne(str, sb, sb2);
        } else if (i == 2 || i == 3) {
            cycleOne(str, sb, sb2);
        }
        return sb.toString();
    }

    private static void cycleOne(String str, StringBuilder sb, StringBuilder sb2) {
        int i = 0;
        while (i < str.length() - 1) {
            int i2 = i + 2;
            String substring = str.substring(i, i2);
            if (!substring.equalsIgnoreCase("00")) {
                int parseInt = Integer.parseInt(substring, 16);
                sb.append((char) parseInt);
                sb2.append(parseInt);
                i = i2;
            } else {
                return;
            }
        }
    }

    private static void cycleTwo(String str, StringBuilder sb, StringBuilder sb2) {
        int i = 0;
        int i2 = 0;
        while (i < str.length() - 1) {
            int i3 = i + 2;
            String substring = str.substring(i, i3);
            if (substring.equalsIgnoreCase("00")) {
                i2++;
                PrintStream printStream = System.out;
                printStream.println("numCount:" + i2);
                if (i2 == 2) {
                    return;
                }
            } else {
                int parseInt = Integer.parseInt(substring, 16);
                sb.append((char) parseInt);
                sb2.append(parseInt);
            }
            i = i3;
        }
    }

    private static void cycleThree(String str, StringBuilder sb, StringBuilder sb2) {
        int i = 0;
        int i2 = 0;
        while (i < str.length() - 1) {
            int i3 = i + 2;
            String substring = str.substring(i, i3);
            if (!substring.equalsIgnoreCase("00")) {
                int parseInt = Integer.parseInt(substring, 16);
                sb.append((char) parseInt);
                sb2.append(parseInt);
            } else if (i2 != 3) {
                i2++;
            } else {
                return;
            }
            i = i3;
        }
    }
}
