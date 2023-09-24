package com.huanghuang.kangshangyiliao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.p000v4.content.LocalBroadcastManager;
import android.support.p000v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.adapter.ProteinFragmentAdapter;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.bean.BloodFatResult;
import com.huanghuang.kangshangyiliao.dao.DoctorAndPatientDao;
import com.huanghuang.kangshangyiliao.dao.NickNameDao;
import com.huanghuang.kangshangyiliao.dao.bean.BloodFat;
import com.huanghuang.kangshangyiliao.dao.bean.DoctorAndPatientBean;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.fragment.BloodFatFragment_bf;
import com.huanghuang.kangshangyiliao.fragment.BloodFatFragment_dx;
import com.huanghuang.kangshangyiliao.fragment.BloodFatFragment_kf;
import com.huanghuang.kangshangyiliao.fragment.BloodFatFragment_lf_three;
import com.huanghuang.kangshangyiliao.fragment.BloodFatFragment_xx;
import com.huanghuang.kangshangyiliao.util.Age;
import com.huanghuang.kangshangyiliao.util.BloodFatManager;
import com.huanghuang.kangshangyiliao.util.Session;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import com.huanghuang.kangshangyiliao.widget.AskCreateNickNameDialog;
import com.huanghuang.kangshangyiliao.widget.AskDialog;
import com.huanghuang.kangshangyiliao.widget.CommonDialog;
import com.huanghuang.kangshangyiliao.widget.LoadingDialog;
import com.huanghuang.kangshangyiliao.widget.TipDialog;
import com.huanghuang.kangshangyiliao.widget.TipToastDialog;
import java.io.PrintStream;

public class BloodFatMainActivity extends BaseActivity implements View.OnClickListener {
    public static String unitNumber = "";
    private Age age = new Age();
    private String age_phase;
    private AlertDialog alert = null;
    private AppBase appBase = AppBase.getInstance();
    /* access modifiers changed from: private */
    public BloodFat bloodFat;
    /* access modifiers changed from: private */
    public BloodFatManager bloodFatManager = BloodFatManager.getInstance();
    private BroadcastReceiver bluetoothStateListener = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1)) {
                case 10:
                    BloodFatMainActivity.this.onDeviceStateOff();
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    @ViewBind.Bind(mo7926id = 2131230799)
    public Button btMeasurement;
    @ViewBind.Bind(mo7926id = 2131230814)
    private Button bt_set_unit;
    private AlertDialog.Builder builder = null;
    private String currentClinicName;
    private NickNameDao dao = new NickNameDao();
    /* access modifiers changed from: private */
    public byte[] data;
    private DoctorAndPatientDao doctorAndPatientDao = new DoctorAndPatientDao();
    @ViewBind.Bind(mo7926id = 2131230846)
    private EditText etSend;
    private ProteinFragmentAdapter fragmentAdapter;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    /* access modifiers changed from: private */
    public boolean isCheckDeviceType = false;
    /* access modifiers changed from: private */
    public boolean isUnderway = false;
    private LocalBroadcastManager lbm;
    @ViewBind.Bind(mo7926id = 2131230897)
    private LinearLayout llSend;
    /* access modifiers changed from: private */
    public LoadingDialog loadingDialog;
    private AskDialog mSwitchDialog;
    private NickName nickName = Utils.getUserInfo();
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r9, android.content.Intent r10) {
            /*
                r8 = this;
                java.lang.String r9 = r10.getAction()
                int r0 = r9.hashCode()
                r1 = 1
                r2 = 0
                switch(r0) {
                    case -1779137561: goto L_0x0077;
                    case -168118629: goto L_0x006c;
                    case -21357672: goto L_0x0062;
                    case 155122889: goto L_0x0058;
                    case 516879891: goto L_0x004d;
                    case 822314479: goto L_0x0043;
                    case 822679629: goto L_0x0039;
                    case 932704315: goto L_0x002f;
                    case 1330296224: goto L_0x0025;
                    case 1861375666: goto L_0x001a;
                    case 1863428709: goto L_0x000f;
                    default: goto L_0x000d;
                }
            L_0x000d:
                goto L_0x0081
            L_0x000f:
                java.lang.String r0 = "set_patient_info"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0081
                r9 = 4
                goto L_0x0082
            L_0x001a:
                java.lang.String r0 = "par_Flash"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0081
                r9 = 10
                goto L_0x0082
            L_0x0025:
                java.lang.String r0 = "bloodFat_device_available"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0081
                r9 = 5
                goto L_0x0082
            L_0x002f:
                java.lang.String r0 = "set_title"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0081
                r9 = 3
                goto L_0x0082
            L_0x0039:
                java.lang.String r0 = "bloodFat_time"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0081
                r9 = 2
                goto L_0x0082
            L_0x0043:
                java.lang.String r0 = "bloodFat_hand"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0081
                r9 = 1
                goto L_0x0082
            L_0x004d:
                java.lang.String r0 = "setting_unit"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0081
                r9 = 8
                goto L_0x0082
            L_0x0058:
                java.lang.String r0 = "bloodFat_notify"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0081
                r9 = 0
                goto L_0x0082
            L_0x0062:
                java.lang.String r0 = "bloodFat_device_state_off"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0081
                r9 = 7
                goto L_0x0082
            L_0x006c:
                java.lang.String r0 = "query_unit"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0081
                r9 = 9
                goto L_0x0082
            L_0x0077:
                java.lang.String r0 = "device_unavailable"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0081
                r9 = 6
                goto L_0x0082
            L_0x0081:
                r9 = -1
            L_0x0082:
                java.lang.String r0 = "1"
                java.lang.String r3 = "0"
                java.lang.String r4 = "01"
                r5 = 20
                r6 = 18
                java.lang.String r7 = "hexString"
                switch(r9) {
                    case 0: goto L_0x025b;
                    case 1: goto L_0x021b;
                    case 2: goto L_0x01fa;
                    case 3: goto L_0x01d9;
                    case 4: goto L_0x01b8;
                    case 5: goto L_0x0171;
                    case 6: goto L_0x012c;
                    case 7: goto L_0x0125;
                    case 8: goto L_0x00f7;
                    case 9: goto L_0x00a5;
                    case 10: goto L_0x0093;
                    default: goto L_0x0091;
                }
            L_0x0091:
                goto L_0x02db
            L_0x0093:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                r10 = 2131558741(0x7f0d0155, float:1.8742806E38)
                java.lang.String r10 = r9.getString(r10)
                android.widget.Toast r9 = android.widget.Toast.makeText(r9, r10, r2)
                r9.show()
                goto L_0x02db
            L_0x00a5:
                java.lang.String r9 = r10.getStringExtra(r7)
                java.lang.String r10 = r9.substring(r6, r5)
                com.huanghuang.kangshangyiliao.event.MessageEvent r5 = new com.huanghuang.kangshangyiliao.event.MessageEvent
                r5.<init>(r10)
                org.greenrobot.eventbus.EventBus r6 = org.greenrobot.eventbus.EventBus.getDefault()
                r6.post(r5)
                java.io.PrintStream r5 = java.lang.System.out
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "BloodFatManager.QUERY_UNIT: "
                r6.append(r7)
                r6.append(r9)
                java.lang.String r9 = r6.toString()
                r5.println(r9)
                java.io.PrintStream r9 = java.lang.System.out
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "BloodFatManager.substring: "
                r5.append(r6)
                r5.append(r10)
                java.lang.String r5 = r5.toString()
                r9.println(r5)
                boolean r9 = r10.equals(r4)
                if (r9 == 0) goto L_0x00f1
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.unitNumber = r0
                com.huanghuang.kangshangyiliao.base.KangShangApplication.defaultNumber = r1
                goto L_0x02db
            L_0x00f1:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.unitNumber = r3
                com.huanghuang.kangshangyiliao.base.KangShangApplication.defaultNumber = r2
                goto L_0x02db
            L_0x00f7:
                java.lang.String r9 = r10.getStringExtra(r7)
                java.lang.String r9 = r9.substring(r6, r5)
                com.huanghuang.kangshangyiliao.event.MessageEvent r10 = new com.huanghuang.kangshangyiliao.event.MessageEvent
                r10.<init>(r9)
                org.greenrobot.eventbus.EventBus r5 = org.greenrobot.eventbus.EventBus.getDefault()
                r5.post(r10)
                boolean r9 = r9.equals(r4)
                if (r9 == 0) goto L_0x0116
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.unitNumber = r0
                com.huanghuang.kangshangyiliao.base.KangShangApplication.defaultNumber = r1
                goto L_0x011a
            L_0x0116:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.unitNumber = r3
                com.huanghuang.kangshangyiliao.base.KangShangApplication.defaultNumber = r2
            L_0x011a:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.util.BloodFatManager r9 = r9.bloodFatManager
                r9.postFlash()
                goto L_0x02db
            L_0x0125:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                r9.onDeviceStateOff()
                goto L_0x02db
            L_0x012c:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r9 = r9.loadingDialog
                if (r9 == 0) goto L_0x013d
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r9 = r9.loadingDialog
                r9.dismiss()
            L_0x013d:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                boolean unused = r9.isUnderway = r2
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipDialog r10 = new com.huanghuang.kangshangyiliao.widget.TipDialog
                r10.<init>(r9)
                com.huanghuang.kangshangyiliao.widget.TipDialog unused = r9.toast06 = r10
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipDialog r9 = r9.toast06
                r10 = 2131558528(0x7f0d0080, float:1.8742374E38)
                r9.setText((int) r10)
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipDialog r9 = r9.toast06
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity$2$3 r10 = new com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity$2$3
                r10.<init>()
                r9.setOnOperateListener(r10)
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipDialog r9 = r9.toast06
                r9.show()
                goto L_0x02db
            L_0x0171:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r9 = r9.loadingDialog
                if (r9 == 0) goto L_0x0182
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r9 = r9.loadingDialog
                r9.dismiss()
            L_0x0182:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r10 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                r10.<init>(r9)
                com.huanghuang.kangshangyiliao.widget.TipToastDialog unused = r9.toast05 = r10
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r9 = r9.toast05
                r10 = 2131558523(0x7f0d007b, float:1.8742364E38)
                r9.setText((int) r10)
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r9 = r9.toast05
                r9.show()
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                r9.checkDeviceType()
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                android.os.Handler r9 = r9.handler
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity$2$2 r10 = new com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity$2$2
                r10.<init>()
                r0 = 200(0xc8, double:9.9E-322)
                r9.postDelayed(r10, r0)
                goto L_0x02db
            L_0x01b8:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r10 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                r10.<init>(r9)
                com.huanghuang.kangshangyiliao.widget.TipToastDialog unused = r9.toast05 = r10
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r9 = r9.toast05
                r10 = 2131558738(0x7f0d0152, float:1.87428E38)
                r9.setText((int) r10)
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r9 = r9.toast05
                r9.show()
                goto L_0x02db
            L_0x01d9:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r10 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                r10.<init>(r9)
                com.huanghuang.kangshangyiliao.widget.TipToastDialog unused = r9.toast04 = r10
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r9 = r9.toast04
                r10 = 2131558739(0x7f0d0153, float:1.8742802E38)
                r9.setText((int) r10)
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r9 = r9.toast04
                r9.show()
                goto L_0x02db
            L_0x01fa:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r10 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                r10.<init>(r9)
                com.huanghuang.kangshangyiliao.widget.TipToastDialog unused = r9.toast03 = r10
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r9 = r9.toast03
                r10 = 2131558760(0x7f0d0168, float:1.8742845E38)
                r9.setText((int) r10)
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r9 = r9.toast03
                r9.show()
                goto L_0x02db
            L_0x021b:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                boolean r9 = r9.isCheckDeviceType
                if (r9 == 0) goto L_0x023a
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                boolean unused = r9.isCheckDeviceType = r2
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r9 = r9.loadingDialog
                if (r9 == 0) goto L_0x0239
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r9 = r9.loadingDialog
                r9.dismiss()
            L_0x0239:
                return
            L_0x023a:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r10 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                r10.<init>(r9)
                com.huanghuang.kangshangyiliao.widget.TipToastDialog unused = r9.toast02 = r10
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r9 = r9.toast02
                r10 = 2131558548(0x7f0d0094, float:1.8742415E38)
                r9.setText((int) r10)
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r9 = r9.toast02
                r9.show()
                goto L_0x02db
            L_0x025b:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                boolean unused = r9.isUnderway = r2
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                android.os.Handler r9 = r9.handler
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r0 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                java.lang.Runnable r0 = r0.runnable
                r9.removeCallbacks(r0)
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r9 = r9.loadingDialog
                if (r9 == 0) goto L_0x0280
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r9 = r9.loadingDialog
                r9.dismiss()
            L_0x0280:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                java.lang.String r0 = "bloodFat"
                java.io.Serializable r10 = r10.getSerializableExtra(r0)
                com.huanghuang.kangshangyiliao.dao.bean.BloodFat r10 = (com.huanghuang.kangshangyiliao.dao.bean.BloodFat) r10
                com.huanghuang.kangshangyiliao.dao.bean.BloodFat unused = r9.bloodFat = r10
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.dao.bean.BloodFat r9 = r9.bloodFat
                if (r9 == 0) goto L_0x02b2
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.dao.bean.BloodFat r10 = r9.bloodFat
                java.lang.String r10 = r10.data
                byte[] r10 = com.huanghuang.kangshangyiliao.util.Utils.toByteArray(r10)
                byte[] unused = r9.data = r10
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                r9.judgeCurrentItem()
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                byte[] r10 = r9.data
                r9.judgeFragment(r10)
            L_0x02b2:
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r10 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                r10.<init>(r9)
                com.huanghuang.kangshangyiliao.widget.TipToastDialog unused = r9.toast01 = r10
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r9 = r9.toast01
                r10 = 2131558658(0x7f0d0102, float:1.8742638E38)
                r9.setText((int) r10)
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity r9 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.this
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r9 = r9.toast01
                r9.show()
                com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity$2$1 r9 = new com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity$2$1
                r9.<init>()
                r0 = 1000(0x3e8, double:4.94E-321)
                com.huanghuang.kangshangyiliao.util.Utils.delayTask(r9, r0)
            L_0x02db:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.C04222.onReceive(android.content.Context, android.content.Intent):void");
        }
    };
    /* access modifiers changed from: private */
    public Runnable runnable = new Runnable() {
        public void run() {
            if (BloodFatMainActivity.this.isUnderway) {
                if (BloodFatMainActivity.this.loadingDialog != null) {
                    BloodFatMainActivity.this.loadingDialog.dismiss();
                }
                TipToastDialog tipToastDialog = new TipToastDialog(BloodFatMainActivity.this);
                tipToastDialog.setImg(C0418R.C0419drawable.ico_tstb);
                tipToastDialog.setText((int) C0418R.string.measurement_timeout);
                tipToastDialog.show();
                BloodFatMainActivity.this.btMeasurement.setEnabled(true);
            }
        }
    };
    /* access modifiers changed from: private */
    public TipToastDialog toast01;
    /* access modifiers changed from: private */
    public TipToastDialog toast02;
    /* access modifiers changed from: private */
    public TipToastDialog toast03;
    /* access modifiers changed from: private */
    public TipToastDialog toast04;
    /* access modifiers changed from: private */
    public TipToastDialog toast05;
    /* access modifiers changed from: private */
    public TipDialog toast06;
    @ViewBind.Bind(mo7926id = 2131231079)
    private TextView tv_device_name;
    private TextView[] tvs;
    @ViewBind.Bind(mo7926id = 2131231110)
    private ViewPager viewpager;

    /* access modifiers changed from: private */
    public void judgeCurrentItem() {
        BloodFatResult parseBloodFatData = Utils.parseBloodFatData(this.data);
        PrintStream printStream = System.out;
        printStream.println("bloodFatResult::   " + parseBloodFatData.ReagentType);
        int currentItem = this.viewpager.getCurrentItem();
        int i = parseBloodFatData.ReagentType;
        if (i != 1) {
            if (i != 3) {
                if (i != 9) {
                    if (i != 6) {
                        if (i == 7 && currentItem != 3) {
                            this.viewpager.setCurrentItem(3);
                        }
                    } else if (currentItem != 4) {
                        this.viewpager.setCurrentItem(4);
                    }
                } else if (currentItem != 1) {
                    this.viewpager.setCurrentItem(1);
                }
            } else if (currentItem != 2) {
                this.viewpager.setCurrentItem(2);
            }
        } else if (currentItem != 0) {
            this.viewpager.setCurrentItem(0);
        }
    }

    /* access modifiers changed from: private */
    public void judgeFragment(byte[] bArr) {
        int currentItem = this.viewpager.getCurrentItem();
        if (currentItem == 0) {
            ((BloodFatFragment_bf) this.fragmentAdapter.getItem(currentItem)).setData(bArr);
        } else if (currentItem == 1) {
            ((BloodFatFragment_lf_three) this.fragmentAdapter.getItem(currentItem)).setData(bArr);
        } else if (currentItem == 2) {
            ((BloodFatFragment_kf) this.fragmentAdapter.getItem(currentItem)).setData(bArr);
        } else if (currentItem == 3) {
            ((BloodFatFragment_dx) this.fragmentAdapter.getItem(currentItem)).setData(bArr);
        } else if (currentItem == 4) {
            ((BloodFatFragment_xx) this.fragmentAdapter.getItem(currentItem)).setData(bArr);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_blood_fat_operate);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_blood_fat_operate));
        ViewBind.bind((Activity) this);
        this.lbm = LocalBroadcastManager.getInstance(this.appBase.getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BloodFatManager.NOTIFY);
        intentFilter.addAction(BloodFatManager.HAND);
        intentFilter.addAction(BloodFatManager.TIME);
        intentFilter.addAction("set_title");
        intentFilter.addAction("set_patient_info");
        intentFilter.addAction(BloodFatManager.DEVICE_AVAILABLE);
        intentFilter.addAction("device_unavailable");
        intentFilter.addAction(BloodFatManager.DEVICE_STATE_OFF);
        intentFilter.addAction(BloodFatManager.SETING_UNIT);
        intentFilter.addAction(BloodFatManager.QUERY_UNIT);
        intentFilter.addAction(BloodFatManager.PAR_FLASH);
        this.lbm.registerReceiver(this.receiver, intentFilter);
        registerReceiver(this.bluetoothStateListener, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        if (getIntent().getBooleanExtra("isNeedCheckDevice", false)) {
            this.loadingDialog = new LoadingDialog(this);
            this.loadingDialog.setLoadingText(getString(C0418R.string.check_device));
            this.loadingDialog.show();
        } else {
            checkDeviceType();
        }
        initViewPager();
        this.currentClinicName = getIntent().getStringExtra("currentClinicName");
    }

    public String getDeviceData(Context context, String str) {
        return context.getSharedPreferences("name", 0).getString(str, "").replaceAll(" ", "");
    }

    private void initViewPager() {
        this.fragmentAdapter = new ProteinFragmentAdapter(getSupportFragmentManager());
        this.viewpager.setAdapter(this.fragmentAdapter);
        this.viewpager.setCurrentItem(0);
        this.viewpager.setOffscreenPageLimit(4);
        TextView textView = this.tv_device_name;
        textView.setText(getString(C0418R.string.current_device_name) + " " + Session.getInstance().getBloodFat_Device());
    }

    /* access modifiers changed from: private */
    public void checkDeviceType() {
        this.isCheckDeviceType = true;
        this.loadingDialog = new LoadingDialog(this);
        this.loadingDialog.setLoadingText(getString(C0418R.string.check_device_type));
        this.loadingDialog.show();
        this.bloodFatManager.postHand();
        Utils.delayTask(new Runnable() {
            public void run() {
                if (BloodFatMainActivity.this.isCheckDeviceType) {
                    BloodFatMainActivity.this.dismissDialog();
                    TipDialog tipDialog = new TipDialog(BloodFatMainActivity.this);
                    tipDialog.setText((int) C0418R.string.device_type_mismatch);
                    tipDialog.setOnOperateListener(new TipDialog.OnOperateListener() {
                        public void onConfirm() {
                            BloodFatMainActivity.this.getSharedPreferences("name", 0).edit().putString("Blood_Fat", "").apply();
                            BloodFatMainActivity.this.bloodFatManager.disconnect();
                            BloodFatMainActivity.this.finish();
                        }
                    });
                    tipDialog.show();
                }
            }
        }, 5000);
    }

    private void clearResult() {
        int i = 0;
        while (true) {
            TextView[] textViewArr = this.tvs;
            if (i < textViewArr.length) {
                textViewArr[i].setText("");
                int parseColor = Color.parseColor("#FFFFFF");
                if (i % 2 == 1) {
                    parseColor = Color.parseColor("#E5F6FF");
                }
                ((ViewGroup) this.tvs[i].getParent()).setBackgroundColor(parseColor);
                this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                i++;
            } else {
                return;
            }
        }
    }

    private void showResult() {
        clearResult();
        BloodFatResult parseBloodFatData = Utils.parseBloodFatData(this.data);
        String[] bloodFatRealValueText = Utils.getBloodFatRealValueText(parseBloodFatData);
        int[] bloodFatAbnormal = Utils.getBloodFatAbnormal(parseBloodFatData, this.age_phase);
        for (int i = 0; i < bloodFatRealValueText.length; i++) {
            showJT(bloodFatAbnormal[i], i);
        }
        for (int i2 = 0; i2 < bloodFatRealValueText.length; i2++) {
            if (i2 == 0) {
                this.tvs[i2].setText(bloodFatRealValueText[3]);
            } else if (i2 == 1) {
                this.tvs[i2].setText(bloodFatRealValueText[1]);
            } else if (i2 == 2) {
                this.tvs[i2].setText(bloodFatRealValueText[2]);
            } else if (i2 == 3) {
                this.tvs[i2].setText(bloodFatRealValueText[4]);
            } else if (i2 == 4) {
                this.tvs[i2].setText(bloodFatRealValueText[0]);
            }
        }
    }

    private void showJT(int i, int i2) {
        if (i != 0) {
            ((ViewGroup) this.tvs[i2].getParent()).setBackgroundColor(Utils.getAbnormalBackgroundColor());
        }
        if (i == -1) {
            Drawable drawable = getResources().getDrawable(C0418R.C0419drawable.ico_xjt);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.tvs[i2].setCompoundDrawables((Drawable) null, (Drawable) null, drawable, (Drawable) null);
        } else if (i == 1) {
            Drawable drawable2 = getResources().getDrawable(C0418R.C0419drawable.ico_sjt);
            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            this.tvs[i2].setCompoundDrawables((Drawable) null, (Drawable) null, drawable2, (Drawable) null);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case C0418R.C0420id.btBloodFatSetPatientInfo:
                initDialog();
                return;
            case C0418R.C0420id.btBloodFatSetPrintTitle:
                final EditText editText = new EditText(this);
                editText.setFocusable(true);
                this.builder = new AlertDialog.Builder(this);
                AlertDialog.Builder builder2 = this.builder;
                this.alert = builder2.setTitle("" + getResources().getString(C0418R.string.input_print_title)).setView(editText).setPositiveButton(getString(C0418R.string.record_save_dialog_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String obj = editText.getText().toString();
                        if (obj.equals("")) {
                            BloodFatMainActivity bloodFatMainActivity = BloodFatMainActivity.this;
                            Toast.makeText(bloodFatMainActivity, bloodFatMainActivity.getResources().getString(C0418R.string.print_title_not_empty), 1).show();
                        } else if (BloodFatMainActivity.this.getStrLength(obj) >= 31) {
                            BloodFatMainActivity bloodFatMainActivity2 = BloodFatMainActivity.this;
                            Toast.makeText(bloodFatMainActivity2, bloodFatMainActivity2.getResources().getString(C0418R.string.print_title_not_length), 1).show();
                        } else {
                            BloodFatMainActivity.this.bloodFatManager.setTitle(obj);
                        }
                    }
                }).setNegativeButton(getString(C0418R.string.record_save_dialog_cancel), (DialogInterface.OnClickListener) null).create();
                this.alert.show();
                return;
            case C0418R.C0420id.btHand:
                this.bloodFatManager.postHand();
                return;
            case C0418R.C0420id.btMeasurement:
                this.bloodFatManager.measurement();
                this.loadingDialog = new LoadingDialog(this);
                this.loadingDialog.setLoadingText(getString(C0418R.string.being_measured));
                LoadingDialog loadingDialog2 = this.loadingDialog;
                loadingDialog2.interceptHome = true;
                loadingDialog2.interceptBack = true;
                loadingDialog2.show();
                this.btMeasurement.setEnabled(false);
                this.isUnderway = true;
                this.handler.postDelayed(this.runnable, 300000);
                return;
            case C0418R.C0420id.btSwitch_BloodFat:
                showNormalDialog();
                return;
            case C0418R.C0420id.btTimeSynchronization:
                this.bloodFatManager.setTime();
                return;
            case C0418R.C0420id.bt_set_unit:
                this.bloodFatManager.postSetting02();
                return;
            case C0418R.C0420id.bt_set_unit02:
                this.bloodFatManager.postSetting01();
                return;
            case C0418R.C0420id.ivClose:
                finish();
                return;
            default:
                return;
        }
    }

    private void initProtenDIalog() {
        final EditText editText = new EditText(this);
        final EditText editText2 = new EditText(this);
        final EditText editText3 = new EditText(this);
        final EditText editText4 = new EditText(this);
        editText.setFocusable(true);
        editText.setHint("请输入id号，不超过30000");
        editText2.setFocusable(true);
        editText2.setHint("请输入姓名,不超过4个中文字");
        editText3.setFocusable(true);
        editText3.setHint("请输入年龄，如：37");
        editText3.setInputType(2);
        editText4.setFocusable(true);
        editText4.setHint("请输入性别，如：0-代表男,1-代表女");
        editText3.setInputType(2);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        linearLayout.addView(editText);
        linearLayout.addView(editText2);
        linearLayout.addView(editText3);
        linearLayout.addView(editText4);
        this.builder = new AlertDialog.Builder(this);
        this.alert = this.builder.setTitle("请输入病人信息：").setView(linearLayout).setPositiveButton(getString(C0418R.string.record_save_dialog_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                String obj = editText.getText().toString();
                String obj2 = editText2.getText().toString();
                String obj3 = editText3.getText().toString();
                String obj4 = editText4.getText().toString();
                if (TextUtils.isEmpty(obj)) {
                    Toast.makeText(BloodFatMainActivity.this, "ID不能为空", 1).show();
                } else if (TextUtils.isEmpty(obj2)) {
                    Toast.makeText(BloodFatMainActivity.this, "姓名不能为空", 1).show();
                } else if (BloodFatMainActivity.this.getStrLength(obj2) > 8) {
                    Toast.makeText(BloodFatMainActivity.this, "姓名长度不得超过8个字符或4个中文汉字", 1).show();
                } else if (TextUtils.isEmpty(obj3)) {
                    Toast.makeText(BloodFatMainActivity.this, "年龄不能为空", 1).show();
                } else if (obj3.length() >= 4) {
                    Toast.makeText(BloodFatMainActivity.this, "年龄长度不得超过999", 1).show();
                } else if (TextUtils.isEmpty(obj4)) {
                    Toast.makeText(BloodFatMainActivity.this, "性别不能为空", 1).show();
                } else if (obj4.equals("0") || obj4.equals("1")) {
                    BloodFatMainActivity.this.bloodFatManager.setPatientInfo(obj, obj2, obj3, obj4);
                } else {
                    Toast.makeText(BloodFatMainActivity.this, "性别只能为0或1", 1).show();
                }
            }
        }).setNegativeButton(getString(C0418R.string.record_save_dialog_cancel), (DialogInterface.OnClickListener) null).create();
        this.alert.show();
    }

    private void initDialog() {
        final CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
            public void onPositiveClick(String str, String str2, String str3, String str4, String str5) {
                BloodFatMainActivity.this.bloodFatManager.setPatientInfo(str, str2, str3, str4);
                BloodFatMainActivity.this.saveSettingData(str2, str4, str5);
                commonDialog.dismiss();
            }

            public void onNegtiveClick() {
                commonDialog.dismiss();
            }
        }).show();
        WindowManager.LayoutParams attributes = commonDialog.getWindow().getAttributes();
        attributes.width = getWindowManager().getDefaultDisplay().getWidth();
        commonDialog.getWindow().setAttributes(attributes);
    }

    /* access modifiers changed from: private */
    public void saveSettingData(String str, String str2, String str3) {
        String str4 = Integer.parseInt(str2) == 0 ? "1" : "2";
        if (this.nickName == null) {
            this.nickName = new NickName();
        }
        NickName nickName2 = this.nickName;
        nickName2.nickName = str;
        nickName2.birthday = str3;
        nickName2.createDate = Utils.getDate();
        NickName nickName3 = this.nickName;
        nickName3.clinicName = this.currentClinicName;
        this.dao.save(nickName3);
        Utils.saveUserToCache(this.dao.query(str));
        recordName(str, Utils.getDate(), str4, str3, "", "", "", -1);
    }

    private void showNormalDialog() {
        this.mSwitchDialog = new AskDialog(this);
        this.mSwitchDialog.setText((int) C0418R.string.delete_BloodFat);
        this.mSwitchDialog.setOnOperateListener(new AskCreateNickNameDialog.OnOperateListener() {
            public void onConfirm() {
                BloodFatMainActivity.this.getSharedPreferences("name", 0).edit().putString("Blood_Fat", "").apply();
                BloodFatMainActivity.this.bloodFatManager.disconnect();
                BloodFatMainActivity.this.finish();
            }
        });
        this.mSwitchDialog.show();
    }

    /* access modifiers changed from: private */
    public void onDeviceStateOff() {
        if (!isFinishing()) {
            LoadingDialog loadingDialog2 = this.loadingDialog;
            if (loadingDialog2 != null) {
                loadingDialog2.dismiss();
            }
            TipDialog tipDialog = new TipDialog(this);
            tipDialog.setText((int) C0418R.string.device_state_off);
            tipDialog.setOnOperateListener(new TipDialog.OnOperateListener() {
                public void onConfirm() {
                    BloodFatMainActivity.this.finish();
                }
            });
            tipDialog.show();
        }
    }

    /* access modifiers changed from: private */
    public void dismissDialog() {
        LoadingDialog loadingDialog2 = this.loadingDialog;
        if (loadingDialog2 != null) {
            loadingDialog2.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        dialogDismissSetting();
        super.onDestroy();
        this.lbm.unregisterReceiver(this.receiver);
        BroadcastReceiver broadcastReceiver = this.bluetoothStateListener;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        this.handler.removeCallbacks(this.runnable);
        dismissDialog();
    }

    private void dialogDismissSetting() {
        AskDialog askDialog = this.mSwitchDialog;
        if (askDialog != null) {
            askDialog.dismiss();
        }
        TipToastDialog tipToastDialog = this.toast01;
        if (tipToastDialog != null) {
            tipToastDialog.dismiss();
        }
        TipToastDialog tipToastDialog2 = this.toast02;
        if (tipToastDialog2 != null) {
            tipToastDialog2.dismiss();
        }
        TipToastDialog tipToastDialog3 = this.toast03;
        if (tipToastDialog3 != null) {
            tipToastDialog3.dismiss();
        }
        TipToastDialog tipToastDialog4 = this.toast04;
        if (tipToastDialog4 != null) {
            tipToastDialog4.dismiss();
        }
        TipToastDialog tipToastDialog5 = this.toast05;
        if (tipToastDialog5 != null) {
            tipToastDialog5.dismiss();
        }
        TipDialog tipDialog = this.toast06;
        if (tipDialog != null) {
            tipDialog.dismiss();
        }
    }

    public int getStrLength(String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            int codePointAt = Character.codePointAt(str, i2);
            i = (codePointAt < 0 || codePointAt > 255) ? i + 2 : i + 1;
        }
        return i;
    }

    private void recordName(String str, String str2, String str3, String str4, String str5, String str6, String str7, int i) {
        String str8 = this.currentClinicName;
        if (str8 != null && !TextUtils.isEmpty(str8)) {
            DoctorAndPatientBean doctorAndPatientBean = new DoctorAndPatientBean();
            doctorAndPatientBean.f79id = i;
            doctorAndPatientBean.clinicName = this.currentClinicName;
            doctorAndPatientBean.nickName = str;
            doctorAndPatientBean.createDate = str2;
            doctorAndPatientBean.sex = str3;
            doctorAndPatientBean.birthday = str4;
            doctorAndPatientBean.phoneNumber = str5;
            doctorAndPatientBean.height = str6;
            doctorAndPatientBean.weight = str7;
            this.doctorAndPatientDao.save(doctorAndPatientBean);
        }
    }
}
