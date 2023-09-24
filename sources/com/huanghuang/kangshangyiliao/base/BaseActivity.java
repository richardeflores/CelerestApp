package com.huanghuang.kangshangyiliao.base;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.p000v4.content.LocalBroadcastManager;
import android.support.p003v7.app.AppCompatActivity;
import com.google.zxing.common.StringUtils;
import com.huanghuang.kangshangyiliao.bean.POCTResult;
import com.huanghuang.kangshangyiliao.bean.UrinalysisResult;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.dayiji.BluetoothPrintService;
import com.huanghuang.kangshangyiliao.util.Age;
import com.huanghuang.kangshangyiliao.util.BloodFatManager;
import com.huanghuang.kangshangyiliao.util.ImmunofluorescenceManager;
import com.huanghuang.kangshangyiliao.util.POCTManager;
import com.huanghuang.kangshangyiliao.util.ProteinManager;
import com.huanghuang.kangshangyiliao.util.UrinalysisManager;
import com.huanghuang.kangshangyiliao.util.Utils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseActivity extends AppCompatActivity {
    public static String EXTRA_DEVICE_ADDRESS_a = "device_address";
    public static String EXTRA_DEVICE_ADDRESS_aa = "device_address";
    private Age age = new Age();
    private String age_phase;
    private byte[] data;
    private LocalBroadcastManager lbm;
    private NickName nickName = Utils.getUserInfo();
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r8, android.content.Intent r9) {
            /*
                r7 = this;
                java.lang.String r0 = r9.getAction()
                int r1 = r0.hashCode()
                r2 = 0
                switch(r1) {
                    case -1836855365: goto L_0x00be;
                    case -1801921244: goto L_0x00b4;
                    case -1522631667: goto L_0x00a9;
                    case -864649907: goto L_0x009f;
                    case -767539241: goto L_0x0094;
                    case -622348460: goto L_0x0089;
                    case -606267464: goto L_0x007f;
                    case -545089593: goto L_0x0074;
                    case -379169316: goto L_0x0069;
                    case -21357672: goto L_0x005e;
                    case 155122889: goto L_0x0052;
                    case 206361477: goto L_0x0046;
                    case 243378117: goto L_0x003b;
                    case 255270522: goto L_0x0030;
                    case 837216314: goto L_0x0025;
                    case 1599827238: goto L_0x0019;
                    case 1694932361: goto L_0x000e;
                    default: goto L_0x000c;
                }
            L_0x000c:
                goto L_0x00c8
            L_0x000e:
                java.lang.String r1 = "bloodFat_connected"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 3
                goto L_0x00c9
            L_0x0019:
                java.lang.String r1 = "urinalysis_device_state_off"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 12
                goto L_0x00c9
            L_0x0025:
                java.lang.String r1 = "poct_connected"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 1
                goto L_0x00c9
            L_0x0030:
                java.lang.String r1 = "urinalysis_query"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 6
                goto L_0x00c9
            L_0x003b:
                java.lang.String r1 = "immunofluorescence_connected"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 4
                goto L_0x00c9
            L_0x0046:
                java.lang.String r1 = "protein_notify"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 8
                goto L_0x00c9
            L_0x0052:
                java.lang.String r1 = "bloodFat_notify"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 10
                goto L_0x00c9
            L_0x005e:
                java.lang.String r1 = "bloodFat_device_state_off"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 15
                goto L_0x00c9
            L_0x0069:
                java.lang.String r1 = "immunofluorescence_device_state_off"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 16
                goto L_0x00c9
            L_0x0074:
                java.lang.String r1 = "poct_device_state_off"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 13
                goto L_0x00c9
            L_0x007f:
                java.lang.String r1 = "poct_notify"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 7
                goto L_0x00c9
            L_0x0089:
                java.lang.String r1 = "protein_device_state_off"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 14
                goto L_0x00c9
            L_0x0094:
                java.lang.String r1 = "urinalysis_notify"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 9
                goto L_0x00c9
            L_0x009f:
                java.lang.String r1 = "protein_connected"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 2
                goto L_0x00c9
            L_0x00a9:
                java.lang.String r1 = "immunofluorescence_notify"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 11
                goto L_0x00c9
            L_0x00b4:
                java.lang.String r1 = "printer_connected"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 5
                goto L_0x00c9
            L_0x00be:
                java.lang.String r1 = "urinalysis_connected"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x00c8
                r0 = 0
                goto L_0x00c9
            L_0x00c8:
                r0 = -1
            L_0x00c9:
                r1 = 2131558778(0x7f0d017a, float:1.8742881E38)
                r3 = 2131558658(0x7f0d0102, float:1.8742638E38)
                java.lang.String r4 = "name"
                r5 = 2131558493(0x7f0d005d, float:1.8742303E38)
                java.lang.String r6 = "device"
                switch(r0) {
                    case 0: goto L_0x0301;
                    case 1: goto L_0x02b9;
                    case 2: goto L_0x0270;
                    case 3: goto L_0x0227;
                    case 4: goto L_0x01de;
                    case 5: goto L_0x01cc;
                    case 6: goto L_0x01cc;
                    case 7: goto L_0x019d;
                    case 8: goto L_0x016e;
                    case 9: goto L_0x014b;
                    case 10: goto L_0x0121;
                    case 11: goto L_0x00f7;
                    case 12: goto L_0x00db;
                    case 13: goto L_0x00db;
                    case 14: goto L_0x00db;
                    case 15: goto L_0x00db;
                    case 16: goto L_0x00db;
                    default: goto L_0x00d9;
                }
            L_0x00d9:
                goto L_0x0348
            L_0x00db:
                com.huanghuang.kangshangyiliao.base.BaseActivity r8 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                boolean r9 = r8 instanceof com.huanghuang.kangshangyiliao.activity.POCTMainActivity
                if (r9 != 0) goto L_0x00f6
                boolean r9 = r8 instanceof com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity
                if (r9 == 0) goto L_0x00e6
                goto L_0x00f6
            L_0x00e6:
                r9 = 2131558525(0x7f0d007d, float:1.8742368E38)
                java.lang.String r9 = r8.getString(r9)
                android.widget.Toast r8 = android.widget.Toast.makeText(r8, r9, r2)
                r8.show()
                goto L_0x0348
            L_0x00f6:
                return
            L_0x00f7:
                com.huanghuang.kangshangyiliao.base.BaseActivity r8 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                boolean r8 = r8 instanceof com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity
                if (r8 == 0) goto L_0x00fe
                return
            L_0x00fe:
                com.huanghuang.kangshangyiliao.dao.bean.NickName r8 = com.huanghuang.kangshangyiliao.util.Utils.getUserInfo()
                if (r8 != 0) goto L_0x0112
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r8 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r9 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r8.<init>(r9)
                r8.setText((int) r1)
                r8.show()
                return
            L_0x0112:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r8 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r9 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r8.<init>(r9)
                r8.setText((int) r3)
                r8.show()
                goto L_0x0348
            L_0x0121:
                com.huanghuang.kangshangyiliao.base.BaseActivity r8 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                boolean r8 = r8 instanceof com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity
                if (r8 == 0) goto L_0x0128
                return
            L_0x0128:
                com.huanghuang.kangshangyiliao.dao.bean.NickName r8 = com.huanghuang.kangshangyiliao.util.Utils.getUserInfo()
                if (r8 != 0) goto L_0x013c
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r8 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r9 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r8.<init>(r9)
                r8.setText((int) r1)
                r8.show()
                return
            L_0x013c:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r8 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r9 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r8.<init>(r9)
                r8.setText((int) r3)
                r8.show()
                goto L_0x0348
            L_0x014b:
                com.huanghuang.kangshangyiliao.dao.bean.NickName r8 = com.huanghuang.kangshangyiliao.util.Utils.getUserInfo()
                if (r8 != 0) goto L_0x015f
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r8 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r9 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r8.<init>(r9)
                r8.setText((int) r1)
                r8.show()
                return
            L_0x015f:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r8 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r9 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r8.<init>(r9)
                r8.setText((int) r3)
                r8.show()
                goto L_0x0348
            L_0x016e:
                com.huanghuang.kangshangyiliao.base.BaseActivity r8 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                boolean r8 = r8 instanceof com.huanghuang.kangshangyiliao.activity.ProteinMainActivity
                if (r8 == 0) goto L_0x0175
                return
            L_0x0175:
                com.huanghuang.kangshangyiliao.dao.bean.NickName r8 = com.huanghuang.kangshangyiliao.util.Utils.getUserInfo()
                if (r8 != 0) goto L_0x0189
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r8 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r9 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r8.<init>(r9)
                r8.setText((int) r1)
                r8.show()
                return
            L_0x0189:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r8 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r9 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r8.<init>(r9)
                r8.setText((int) r3)
                r8.show()
                com.huanghuang.kangshangyiliao.base.BaseActivity r8 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r8.ininBluetooth_Protein()
                goto L_0x0348
            L_0x019d:
                com.huanghuang.kangshangyiliao.base.BaseActivity r8 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                boolean r8 = r8 instanceof com.huanghuang.kangshangyiliao.activity.POCTMainActivity
                if (r8 == 0) goto L_0x01a4
                return
            L_0x01a4:
                com.huanghuang.kangshangyiliao.dao.bean.NickName r8 = com.huanghuang.kangshangyiliao.util.Utils.getUserInfo()
                if (r8 != 0) goto L_0x01b8
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r8 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r9 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r8.<init>(r9)
                r8.setText((int) r1)
                r8.show()
                return
            L_0x01b8:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r8 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r9 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r8.<init>(r9)
                r8.setText((int) r3)
                r8.show()
                com.huanghuang.kangshangyiliao.base.BaseActivity r8 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r8.ininBluetooth_POCT()
                goto L_0x0348
            L_0x01cc:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r8 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r9 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r8.<init>(r9)
                r9 = 2131558714(0x7f0d013a, float:1.8742752E38)
                r8.setText((int) r9)
                r8.show()
                goto L_0x0348
            L_0x01de:
                android.os.Parcelable r0 = r9.getParcelableExtra(r6)
                android.bluetooth.BluetoothDevice r0 = (android.bluetooth.BluetoothDevice) r0
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r1 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r3 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r1.<init>(r3)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r0 = r0.getName()
                r3.append(r0)
                com.huanghuang.kangshangyiliao.base.BaseActivity r0 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                java.lang.String r0 = r0.getString(r5)
                r3.append(r0)
                java.lang.String r0 = r3.toString()
                r1.setText((java.lang.String) r0)
                r1.show()
                android.content.SharedPreferences r8 = r8.getSharedPreferences(r4, r2)
                android.os.Parcelable r9 = r9.getParcelableExtra(r6)
                android.bluetooth.BluetoothDevice r9 = (android.bluetooth.BluetoothDevice) r9
                java.lang.String r9 = r9.getAddress()
                android.content.SharedPreferences$Editor r8 = r8.edit()
                java.lang.String r0 = "Immunofluorescence"
                android.content.SharedPreferences$Editor r8 = r8.putString(r0, r9)
                r8.apply()
                goto L_0x0348
            L_0x0227:
                android.os.Parcelable r0 = r9.getParcelableExtra(r6)
                android.bluetooth.BluetoothDevice r0 = (android.bluetooth.BluetoothDevice) r0
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r1 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r3 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r1.<init>(r3)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r0 = r0.getName()
                r3.append(r0)
                com.huanghuang.kangshangyiliao.base.BaseActivity r0 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                java.lang.String r0 = r0.getString(r5)
                r3.append(r0)
                java.lang.String r0 = r3.toString()
                r1.setText((java.lang.String) r0)
                r1.show()
                android.content.SharedPreferences r8 = r8.getSharedPreferences(r4, r2)
                android.os.Parcelable r9 = r9.getParcelableExtra(r6)
                android.bluetooth.BluetoothDevice r9 = (android.bluetooth.BluetoothDevice) r9
                java.lang.String r9 = r9.getAddress()
                android.content.SharedPreferences$Editor r8 = r8.edit()
                java.lang.String r0 = "Blood_Fat"
                android.content.SharedPreferences$Editor r8 = r8.putString(r0, r9)
                r8.apply()
                goto L_0x0348
            L_0x0270:
                android.os.Parcelable r0 = r9.getParcelableExtra(r6)
                android.bluetooth.BluetoothDevice r0 = (android.bluetooth.BluetoothDevice) r0
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r1 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r3 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r1.<init>(r3)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r0 = r0.getName()
                r3.append(r0)
                com.huanghuang.kangshangyiliao.base.BaseActivity r0 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                java.lang.String r0 = r0.getString(r5)
                r3.append(r0)
                java.lang.String r0 = r3.toString()
                r1.setText((java.lang.String) r0)
                r1.show()
                android.content.SharedPreferences r8 = r8.getSharedPreferences(r4, r2)
                android.os.Parcelable r9 = r9.getParcelableExtra(r6)
                android.bluetooth.BluetoothDevice r9 = (android.bluetooth.BluetoothDevice) r9
                java.lang.String r9 = r9.getAddress()
                android.content.SharedPreferences$Editor r8 = r8.edit()
                java.lang.String r0 = "Protein"
                android.content.SharedPreferences$Editor r8 = r8.putString(r0, r9)
                r8.apply()
                goto L_0x0348
            L_0x02b9:
                android.os.Parcelable r0 = r9.getParcelableExtra(r6)
                android.bluetooth.BluetoothDevice r0 = (android.bluetooth.BluetoothDevice) r0
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r1 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r3 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r1.<init>(r3)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r0 = r0.getName()
                r3.append(r0)
                com.huanghuang.kangshangyiliao.base.BaseActivity r0 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                java.lang.String r0 = r0.getString(r5)
                r3.append(r0)
                java.lang.String r0 = r3.toString()
                r1.setText((java.lang.String) r0)
                r1.show()
                android.content.SharedPreferences r8 = r8.getSharedPreferences(r4, r2)
                android.os.Parcelable r9 = r9.getParcelableExtra(r6)
                android.bluetooth.BluetoothDevice r9 = (android.bluetooth.BluetoothDevice) r9
                java.lang.String r9 = r9.getAddress()
                android.content.SharedPreferences$Editor r8 = r8.edit()
                java.lang.String r0 = "POCT"
                android.content.SharedPreferences$Editor r8 = r8.putString(r0, r9)
                r8.apply()
                goto L_0x0348
            L_0x0301:
                android.os.Parcelable r0 = r9.getParcelableExtra(r6)
                android.bluetooth.BluetoothDevice r0 = (android.bluetooth.BluetoothDevice) r0
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r1 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.base.BaseActivity r3 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                r1.<init>(r3)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r0 = r0.getName()
                r3.append(r0)
                com.huanghuang.kangshangyiliao.base.BaseActivity r0 = com.huanghuang.kangshangyiliao.base.BaseActivity.this
                java.lang.String r0 = r0.getString(r5)
                r3.append(r0)
                java.lang.String r0 = r3.toString()
                r1.setText((java.lang.String) r0)
                r1.show()
                android.content.SharedPreferences r8 = r8.getSharedPreferences(r4, r2)
                android.os.Parcelable r9 = r9.getParcelableExtra(r6)
                android.bluetooth.BluetoothDevice r9 = (android.bluetooth.BluetoothDevice) r9
                java.lang.String r9 = r9.getAddress()
                android.content.SharedPreferences$Editor r8 = r8.edit()
                java.lang.String r0 = "Urinalysis"
                android.content.SharedPreferences$Editor r8 = r8.putString(r0, r9)
                r8.apply()
            L_0x0348:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.base.BaseActivity.C05031.onReceive(android.content.Context, android.content.Intent):void");
        }
    };

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.lbm = LocalBroadcastManager.getInstance(this);
        getWindow().addFlags(128);
    }

    private void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UrinalysisManager.CONNECTED);
        intentFilter.addAction(UrinalysisManager.CONNECTED_FAIL);
        intentFilter.addAction(UrinalysisManager.DISCONNECTED);
        intentFilter.addAction(UrinalysisManager.HAND);
        intentFilter.addAction(UrinalysisManager.TIME);
        intentFilter.addAction(UrinalysisManager.QUERY);
        intentFilter.addAction(UrinalysisManager.NOTIFY);
        intentFilter.addAction(UrinalysisManager.DEVICE_STATE_OFF);
        intentFilter.addAction(POCTManager.CONNECTED);
        intentFilter.addAction(POCTManager.NOTIFY);
        intentFilter.addAction(POCTManager.CONNECTED_FAIL);
        intentFilter.addAction(POCTManager.DEVICE_STATE_OFF);
        intentFilter.addAction(ProteinManager.CONNECTED);
        intentFilter.addAction(ProteinManager.NOTIFY);
        intentFilter.addAction(ProteinManager.CONNECTED_FAIL);
        intentFilter.addAction(ProteinManager.DEVICE_STATE_OFF);
        intentFilter.addAction(BloodFatManager.CONNECTED);
        intentFilter.addAction(BloodFatManager.NOTIFY);
        intentFilter.addAction(BloodFatManager.CONNECTED_FAIL);
        intentFilter.addAction(BloodFatManager.DEVICE_STATE_OFF);
        intentFilter.addAction(ImmunofluorescenceManager.CONNECTED);
        intentFilter.addAction(ImmunofluorescenceManager.NOTIFY);
        intentFilter.addAction(ImmunofluorescenceManager.CONNECTED_FAIL);
        intentFilter.addAction(ImmunofluorescenceManager.DEVICE_STATE_OFF);
        this.lbm.registerReceiver(this.receiver, intentFilter);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        register();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.lbm.unregisterReceiver(this.receiver);
    }

    public void ininBluetooth_POCT() {
        String str;
        String str2;
        POCTResult parsePOCTData = Utils.parsePOCTData(this.data);
        String[] pOCTRealValueText = Utils.getPOCTRealValueText(parsePOCTData);
        int[] pOCTAbnormal = Utils.getPOCTAbnormal(parsePOCTData, this.age_phase);
        final String valueOf = String.valueOf(pOCTRealValueText[0]);
        final String valueOf2 = String.valueOf(pOCTRealValueText[1]);
        final String valueOf3 = String.valueOf(pOCTRealValueText[2]);
        final String valueOf4 = String.valueOf(pOCTRealValueText[3]);
        final String valueOf5 = String.valueOf(pOCTRealValueText[4]);
        final String valueOf6 = String.valueOf(pOCTRealValueText[5]);
        final String valueOf7 = String.valueOf(pOCTRealValueText[6]);
        final String valueOf8 = String.valueOf(pOCTRealValueText[7]);
        final String valueOf9 = String.valueOf(pOCTRealValueText[8]);
        final String valueOf10 = String.valueOf(pOCTRealValueText[9]);
        final String valueOf11 = String.valueOf(pOCTRealValueText[10]);
        String[] strArr = {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "};
        for (int i = 0; i < pOCTRealValueText.length; i++) {
            if (pOCTAbnormal[i] == -1) {
                strArr[i] = "↓";
            } else if (pOCTAbnormal[i] == 1) {
                strArr[i] = "↑";
            } else {
                strArr[i] = "  ";
            }
        }
        String str3 = strArr[0];
        String str4 = strArr[1];
        String str5 = strArr[2];
        String str6 = strArr[3];
        String str7 = strArr[4];
        String str8 = strArr[5];
        String str9 = strArr[6];
        final String str10 = strArr[7];
        String str11 = strArr[8];
        String str12 = strArr[9];
        String str13 = strArr[10];
        ClinicName clinicInfo = Utils.getClinicInfo();
        NickName userInfo = Utils.getUserInfo();
        final String str14 = clinicInfo.clinicName;
        final String str15 = clinicInfo.doctorName;
        final String str16 = userInfo.nickName;
        final String str17 = userInfo.birthday;
        String str18 = userInfo.sex;
        if (str18 != null) {
            if (str18.equals("1")) {
                str18 = "男";
            } else if (str18.equals("2")) {
                str18 = "女";
            }
        }
        String str19 = str18;
        String str20 = "10 ^ 9 / L";
        if (this.nickName.birthday == null) {
            str20 = "";
            str = str20;
        } else {
            this.age_phase = Age.age_phase(this.nickName.birthday);
            if ("0".equals(this.age_phase)) {
                str2 = "新生儿:15～20";
            } else if ("1".equals(this.age_phase)) {
                str2 = "婴儿:11～12";
            } else if ("2".equals(this.age_phase)) {
                str2 = "儿童:8～10";
            } else {
                str20 = "10 ^ 12 / L";
                str = "成年人:3.5～9.5";
            }
            str = str2;
        }
        final String format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date(System.currentTimeMillis()));
        connectDevice("DC:0D:30:43:2E:FB");
        new Intent().putExtra(EXTRA_DEVICE_ADDRESS_aa, "DC:0D:30:43:2E:FB");
        C05042 r42 = r0;
        Handler handler = new Handler();
        final String str21 = str19;
        final String str22 = str3;
        final String str23 = str4;
        final String str24 = str20;
        final String str25 = str;
        final String str26 = str5;
        final String str27 = str6;
        final String str28 = str7;
        final String str29 = str8;
        final String str30 = str9;
        final String str31 = str11;
        final String str32 = str12;
        final String str33 = str13;
        C05042 r0 = new Runnable(this) {
            final /* synthetic */ BaseActivity this$0;

            {
                this.this$0 = r3;
            }

            public void run() {
                StringBuilder sb = new StringBuilder();
                String str = " MOU%  " + valueOf8 + str10 + "   %     3.00～8.00";
                sb.append(" NEU%  ");
                sb.append(valueOf9);
                sb.append(str31);
                sb.append("   %     50.0～70.0");
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder();
                String str2 = sb2;
                sb3.append(" EOS%  ");
                sb3.append(valueOf10);
                sb3.append(str32);
                sb3.append("   %     0.50～5.00");
                String sb4 = sb3.toString();
                StringBuilder sb5 = new StringBuilder();
                sb5.append(" BAS%  ");
                sb5.append(valueOf11);
                sb5.append(str33);
                sb5.append("   %     0.00～1.00");
                this.this$0.sendPrintFile("       白细胞分析检测报告       " + "\n" + ("        " + str14 + "诊所        ") + "\n" + ("        主治医生：" + str15 + "        ") + "\n" + ("姓名：" + str16) + "\n" + ("出生日期：" + str17) + "\n" + ("性别：" + str21) + "\n" + ("测量时间：\n" + format) + "\n\n" + " 项目  结果    单位     参考范围" + "\n" + ("*白细胞 " + valueOf + str22 + " 10^9/L 3.50～9.50") + "\n" + (" LYM   " + valueOf2 + str23 + " " + str24 + "  " + str25) + "\n" + (" MOU   " + valueOf3 + str26 + " 10^9/L  0.12～0.80") + "\n" + (" NEU   " + valueOf4 + str27 + " 10^9/L  1.50～7.00") + "\n" + (" EOS   " + valueOf5 + str28 + " 10^9/L  0.05～0.50") + "\n" + (" BAS   " + valueOf6 + str29 + " 10^9/L  0.00～0.10") + "\n" + (" LYM%  " + valueOf7 + str30 + "   %     20.0～40.0") + "\n" + str + "\n" + str2 + "\n" + sb4 + "\n" + sb5.toString() + "\n\n" + "以上结果仅对本次测量负责。" + "\n\n\n\n\n");
            }
        };
        handler.postDelayed(r42, 1000);
    }

    public void ininBluetooth_Urinalysis() {
        UrinalysisResult parseUrinalysisData = Utils.parseUrinalysisData(this.data);
        String[] realValueText = Utils.getRealValueText(parseUrinalysisData);
        int[] urinalysisAbnormal = Utils.getUrinalysisAbnormal(parseUrinalysisData);
        final String valueOf = String.valueOf(realValueText[0]);
        String valueOf2 = String.valueOf(realValueText[1]);
        final String valueOf3 = String.valueOf(realValueText[2]);
        final String valueOf4 = String.valueOf(realValueText[3]);
        final String valueOf5 = String.valueOf(realValueText[4]);
        final String valueOf6 = String.valueOf(realValueText[5]);
        final String valueOf7 = String.valueOf(realValueText[6]);
        final String valueOf8 = String.valueOf(realValueText[7]);
        final String valueOf9 = String.valueOf(realValueText[8]);
        final String valueOf10 = String.valueOf(realValueText[9]);
        String valueOf11 = String.valueOf(realValueText[10]);
        String[] strArr = {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "};
        for (int i = 0; i < realValueText.length; i++) {
            if (i == 8 || i == 10) {
                if (urinalysisAbnormal[i] == -1) {
                    strArr[i] = "↓";
                } else if (urinalysisAbnormal[i] == 1) {
                    strArr[i] = "↑";
                } else {
                    strArr[i] = "  ";
                }
            }
        }
        String str = strArr[8];
        String str2 = strArr[10];
        ClinicName clinicInfo = Utils.getClinicInfo();
        NickName userInfo = Utils.getUserInfo();
        final String str3 = clinicInfo.clinicName;
        final String str4 = clinicInfo.doctorName;
        final String str5 = userInfo.nickName;
        final String str6 = userInfo.birthday;
        String str7 = userInfo.sex;
        if (str7 != null) {
            if (str7.equals("1")) {
                str7 = "男";
            } else if (str7.equals("2")) {
                str7 = "女";
            }
        }
        final String str8 = str7;
        final String format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date(System.currentTimeMillis()));
        connectDevice("DC:0D:30:43:2E:FB");
        new Intent().putExtra(EXTRA_DEVICE_ADDRESS_a, "DC:0D:30:43:2E:FB");
        C05053 r32 = r0;
        final String str9 = str;
        Handler handler = new Handler();
        final String str10 = valueOf2;
        final String str11 = valueOf11;
        final String str12 = str2;
        C05053 r0 = new Runnable(this) {
            final /* synthetic */ BaseActivity this$0;

            {
                this.this$0 = r3;
            }

            public void run() {
                StringBuilder sb = new StringBuilder();
                String str = " SG   " + str11 + str12 + "     1.010～1.025";
                sb.append(" BIL    ");
                sb.append(valueOf6);
                sb.append("   μ mol/L  阴性(-)");
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder();
                String str2 = sb2;
                sb3.append(" GLU    ");
                sb3.append(valueOf8);
                sb3.append("   mmol/L    阴性(-)");
                String sb4 = sb3.toString();
                StringBuilder sb5 = new StringBuilder();
                sb5.append(" VC     ");
                sb5.append(valueOf10);
                sb5.append("   mmol/L    阴性(-)");
                this.this$0.sendPrintFile("        尿液检测报告            " + "\n" + ("        " + str3 + "诊所        ") + "\n" + ("        主治医生：" + str4 + "        ") + "\n" + ("姓名：" + str5) + "\n" + ("出生日期：" + str6) + "\n" + ("性别：" + str8) + "\n" + ("测量时间：\n" + format) + "\n\n" + " 项目  结果   单位     参考范围" + "\n" + ("*LUE    " + valueOf + "   Cell/μL  阴性(-)") + "\n" + (" NIT    " + valueOf3 + "             阴性(-)") + "\n" + (" UBG    " + valueOf5 + "   μmol/L   阴性(-)") + "\n" + (" PRO    " + valueOf7 + "     g/L     阴性(-)") + "\n" + (" pH    " + valueOf9 + str9 + "         5.5～8.5") + "\n" + (" BLD    " + str10 + "   Cell/μL  阴性(-)") + "\n" + str + "\n" + (" KET    " + valueOf4 + "   mmol/L    阴性(-)") + "\n" + str2 + "\n" + sb4 + "\n" + sb5.toString() + "\n\n" + "以上结果仅对本次测量负责。" + "\n\n\n\n\n");
            }
        };
        handler.postDelayed(r32, 1000);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00ee  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void ininBluetooth_Protein() {
        /*
            r17 = this;
            r14 = r17
            com.huanghuang.kangshangyiliao.dao.bean.NickName r0 = r14.nickName
            java.lang.String r0 = r0.birthday
            java.lang.String r1 = ""
            java.lang.String r2 = "2"
            java.lang.String r3 = "1"
            if (r0 != 0) goto L_0x000f
            goto L_0x0059
        L_0x000f:
            com.huanghuang.kangshangyiliao.dao.bean.NickName r0 = r14.nickName
            java.lang.String r0 = r0.birthday
            java.lang.String r0 = com.huanghuang.kangshangyiliao.util.Age.age_phase(r0)
            r14.age_phase = r0
            java.lang.String r0 = r14.age_phase
            java.lang.String r4 = "0"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0028
            java.lang.String r0 = "新生儿:180～190"
            r10 = r1
            r1 = r0
            goto L_0x005a
        L_0x0028:
            java.lang.String r0 = r14.age_phase
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0034
            java.lang.String r0 = "婴儿:110～120"
        L_0x0032:
            r10 = r0
            goto L_0x005a
        L_0x0034:
            java.lang.String r0 = r14.age_phase
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x003f
            java.lang.String r0 = "儿童:120～140"
            goto L_0x0032
        L_0x003f:
            com.huanghuang.kangshangyiliao.dao.bean.NickName r0 = r14.nickName
            java.lang.String r0 = r0.sex
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x004c
            java.lang.String r0 = "男:130～175"
            goto L_0x0032
        L_0x004c:
            com.huanghuang.kangshangyiliao.dao.bean.NickName r0 = r14.nickName
            java.lang.String r0 = r0.sex
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0059
            java.lang.String r0 = "女:115～150"
            goto L_0x0032
        L_0x0059:
            r10 = r1
        L_0x005a:
            com.huanghuang.kangshangyiliao.dao.bean.NickName r0 = r14.nickName
            java.lang.String r0 = r0.sex
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0068
            java.lang.String r0 = "男:40％～50％"
        L_0x0066:
            r13 = r0
            goto L_0x0076
        L_0x0068:
            com.huanghuang.kangshangyiliao.dao.bean.NickName r0 = r14.nickName
            java.lang.String r0 = r0.sex
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0075
            java.lang.String r0 = "女:35％～45％"
            goto L_0x0066
        L_0x0075:
            r13 = r1
        L_0x0076:
            byte[] r0 = r14.data
            com.huanghuang.kangshangyiliao.bean.ProteinResult r0 = com.huanghuang.kangshangyiliao.util.Utils.parseProteinData(r0)
            java.lang.String[] r1 = com.huanghuang.kangshangyiliao.util.Utils.getProteinRealValueText(r0)
            r4 = 2
            int[] r4 = new int[r4]
            com.huanghuang.kangshangyiliao.dao.bean.NickName r5 = r14.nickName
            java.lang.String r5 = r5.sex
            boolean r5 = r3.equals(r5)
            if (r5 == 0) goto L_0x0094
            java.lang.String r4 = r14.age_phase
            int[] r4 = com.huanghuang.kangshangyiliao.util.Utils.getProteinAbnormal_MAN(r0, r4)
            goto L_0x00a4
        L_0x0094:
            com.huanghuang.kangshangyiliao.dao.bean.NickName r5 = r14.nickName
            java.lang.String r5 = r5.sex
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x00a4
            java.lang.String r4 = r14.age_phase
            int[] r4 = com.huanghuang.kangshangyiliao.util.Utils.getProteinAbnormal_WOMAN(r0, r4)
        L_0x00a4:
            r0 = 0
            r5 = r1[r0]
            java.lang.String r8 = java.lang.String.valueOf(r5)
            r5 = 1
            r6 = r1[r5]
            java.lang.String r11 = java.lang.String.valueOf(r6)
            java.lang.String r6 = " "
            java.lang.String[] r6 = new java.lang.String[]{r6, r6}
            r7 = 0
        L_0x00b9:
            int r9 = r1.length
            if (r7 >= r9) goto L_0x00d6
            r9 = r4[r7]
            r12 = -1
            if (r9 != r12) goto L_0x00c6
            java.lang.String r9 = "↓"
            r6[r7] = r9
            goto L_0x00d3
        L_0x00c6:
            r9 = r4[r7]
            if (r9 != r5) goto L_0x00cf
            java.lang.String r9 = "↑"
            r6[r7] = r9
            goto L_0x00d3
        L_0x00cf:
            java.lang.String r9 = "  "
            r6[r7] = r9
        L_0x00d3:
            int r7 = r7 + 1
            goto L_0x00b9
        L_0x00d6:
            r9 = r6[r0]
            r12 = r6[r5]
            com.huanghuang.kangshangyiliao.dao.bean.ClinicName r0 = com.huanghuang.kangshangyiliao.util.Utils.getClinicInfo()
            com.huanghuang.kangshangyiliao.dao.bean.NickName r1 = com.huanghuang.kangshangyiliao.util.Utils.getUserInfo()
            java.lang.String r4 = r0.clinicName
            java.lang.String r5 = r0.doctorName
            java.lang.String r6 = r1.nickName
            java.lang.String r7 = r1.birthday
            java.lang.String r0 = r1.sex
            if (r0 == 0) goto L_0x00ff
            boolean r1 = r0.equals(r3)
            if (r1 == 0) goto L_0x00f7
            java.lang.String r0 = "男"
            goto L_0x00ff
        L_0x00f7:
            boolean r1 = r0.equals(r2)
            if (r1 == 0) goto L_0x00ff
            java.lang.String r0 = "女"
        L_0x00ff:
            r15 = r0
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.lang.String r1 = "yyyy年MM月dd日 HH:mm:ss"
            r0.<init>(r1)
            java.util.Date r1 = new java.util.Date
            long r2 = java.lang.System.currentTimeMillis()
            r1.<init>(r2)
            java.lang.String r16 = r0.format(r1)
            java.lang.String r0 = "DC:0D:30:43:2E:FB"
            r14.connectDevice(r0)
            android.content.Intent r1 = new android.content.Intent
            r1.<init>()
            java.lang.String r2 = EXTRA_DEVICE_ADDRESS_a
            r1.putExtra(r2, r0)
            android.os.Handler r3 = new android.os.Handler
            r3.<init>()
            com.huanghuang.kangshangyiliao.base.BaseActivity$4 r2 = new com.huanghuang.kangshangyiliao.base.BaseActivity$4
            r0 = r2
            r1 = r17
            r14 = r2
            r2 = r4
            r4 = r3
            r3 = r5
            r5 = r4
            r4 = r6
            r6 = r5
            r5 = r7
            r7 = r6
            r6 = r15
            r15 = r7
            r7 = r16
            r0.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            r0 = 1000(0x3e8, double:4.94E-321)
            r15.postDelayed(r14, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.base.BaseActivity.ininBluetooth_Protein():void");
    }

    public void connectDevice(String str) {
        Intent intent = new Intent(this, BluetoothPrintService.class);
        Bundle bundle = new Bundle();
        bundle.putInt(BluetoothPrintService.INTENT_OP, 0);
        bundle.putString(BluetoothPrintService.INTENT_ADDRESS, str);
        intent.putExtras(bundle);
        startService(intent);
    }

    public void sendPrintFile(String str) {
        try {
            byte[] bytes = str.getBytes(StringUtils.GB2312);
            Intent intent = new Intent(this, BluetoothPrintService.class);
            Bundle bundle = new Bundle();
            bundle.putInt(BluetoothPrintService.INTENT_OP, 2);
            bundle.putByteArray(BluetoothPrintService.INTENT_PRINT, bytes);
            intent.putExtras(bundle);
            startService(intent);
        } catch (IOException unused) {
        }
    }
}
