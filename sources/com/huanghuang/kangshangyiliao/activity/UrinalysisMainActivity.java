package com.huanghuang.kangshangyiliao.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.p000v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.zxing.common.StringUtils;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.base.BaseManager;
import com.huanghuang.kangshangyiliao.bean.UrinalysisResult;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.dao.bean.Urinalysis;
import com.huanghuang.kangshangyiliao.dayiji.BluetoothPrintService;
import com.huanghuang.kangshangyiliao.util.Session;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.UrinalysisManager;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import com.huanghuang.kangshangyiliao.widget.AskCreateNickNameDialog;
import com.huanghuang.kangshangyiliao.widget.AskDialog;
import com.huanghuang.kangshangyiliao.widget.LoadingDialog;
import com.huanghuang.kangshangyiliao.widget.TipDialog;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UrinalysisMainActivity extends BaseActivity implements View.OnClickListener {
    public static String EXTRA_DEVICE_ADDRESS_a = "device_address";
    private String Printer_ID;
    private AppBase appBase = AppBase.getInstance();
    private BaseManager[] baseManager;
    private BroadcastReceiver bluetoothStateListener = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1)) {
                case 10:
                    UrinalysisMainActivity.this.onDeviceStateOff();
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public byte[] data;
    /* access modifiers changed from: private */
    public boolean isCheckDeviceType = false;
    private LocalBroadcastManager lbm;
    /* access modifiers changed from: private */
    public LoadingDialog loadingDialog;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r3, android.content.Intent r4) {
            /*
                r2 = this;
                java.lang.String r3 = r4.getAction()
                int r0 = r3.hashCode()
                r1 = 0
                switch(r0) {
                    case -1779137561: goto L_0x0049;
                    case -1275842528: goto L_0x003f;
                    case -767539241: goto L_0x0035;
                    case 255270522: goto L_0x002b;
                    case 1599827238: goto L_0x0021;
                    case 2086157437: goto L_0x0017;
                    case 2086522587: goto L_0x000d;
                    default: goto L_0x000c;
                }
            L_0x000c:
                goto L_0x0053
            L_0x000d:
                java.lang.String r0 = "urinalysis_time"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0053
                r3 = 3
                goto L_0x0054
            L_0x0017:
                java.lang.String r0 = "urinalysis_hand"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0053
                r3 = 2
                goto L_0x0054
            L_0x0021:
                java.lang.String r0 = "urinalysis_device_state_off"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0053
                r3 = 6
                goto L_0x0054
            L_0x002b:
                java.lang.String r0 = "urinalysis_query"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0053
                r3 = 1
                goto L_0x0054
            L_0x0035:
                java.lang.String r0 = "urinalysis_notify"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0053
                r3 = 0
                goto L_0x0054
            L_0x003f:
                java.lang.String r0 = "device_available"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0053
                r3 = 4
                goto L_0x0054
            L_0x0049:
                java.lang.String r0 = "device_unavailable"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0053
                r3 = 5
                goto L_0x0054
            L_0x0053:
                r3 = -1
            L_0x0054:
                switch(r3) {
                    case 0: goto L_0x00f3;
                    case 1: goto L_0x00f3;
                    case 2: goto L_0x00c3;
                    case 3: goto L_0x00b2;
                    case 4: goto L_0x008b;
                    case 5: goto L_0x0060;
                    case 6: goto L_0x0059;
                    default: goto L_0x0057;
                }
            L_0x0057:
                goto L_0x011c
            L_0x0059:
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r3 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                r3.onDeviceStateOff()
                goto L_0x011c
            L_0x0060:
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r3 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x0071
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r3 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.dismiss()
            L_0x0071:
                com.huanghuang.kangshangyiliao.widget.TipDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipDialog
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r4 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                r3.<init>(r4)
                r4 = 2131558528(0x7f0d0080, float:1.8742374E38)
                r3.setText((int) r4)
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity$1$1 r4 = new com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity$1$1
                r4.<init>()
                r3.setOnOperateListener(r4)
                r3.show()
                goto L_0x011c
            L_0x008b:
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r3 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x009c
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r3 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.dismiss()
            L_0x009c:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r4 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                r3.<init>(r4)
                r4 = 2131558523(0x7f0d007b, float:1.8742364E38)
                r3.setText((int) r4)
                r3.show()
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r3 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                r3.checkDeviceType()
                goto L_0x011c
            L_0x00b2:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r4 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                r3.<init>(r4)
                r4 = 2131558760(0x7f0d0168, float:1.8742845E38)
                r3.setText((int) r4)
                r3.show()
                goto L_0x011c
            L_0x00c3:
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r3 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                boolean r3 = r3.isCheckDeviceType
                if (r3 == 0) goto L_0x00e2
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r3 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                boolean unused = r3.isCheckDeviceType = r1
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r3 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x00e1
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r3 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.dismiss()
            L_0x00e1:
                return
            L_0x00e2:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r4 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                r3.<init>(r4)
                r4 = 2131558548(0x7f0d0094, float:1.8742415E38)
                r3.setText((int) r4)
                r3.show()
                goto L_0x011c
            L_0x00f3:
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r3 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                java.lang.String r0 = "urinalysis"
                java.io.Serializable r4 = r4.getSerializableExtra(r0)
                com.huanghuang.kangshangyiliao.dao.bean.Urinalysis r4 = (com.huanghuang.kangshangyiliao.dao.bean.Urinalysis) r4
                com.huanghuang.kangshangyiliao.dao.bean.Urinalysis unused = r3.urinalysis = r4
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r3 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                com.huanghuang.kangshangyiliao.dao.bean.Urinalysis r3 = r3.urinalysis
                if (r3 == 0) goto L_0x011c
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r3 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                com.huanghuang.kangshangyiliao.dao.bean.Urinalysis r4 = r3.urinalysis
                java.lang.String r4 = r4.data
                byte[] r4 = com.huanghuang.kangshangyiliao.util.Utils.toByteArray(r4)
                byte[] unused = r3.data = r4
                com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity r3 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.this
                r3.showResult()
            L_0x011c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.C04831.onReceive(android.content.Context, android.content.Intent):void");
        }
    };
    private Session session = Session.getInstance();
    @ViewBind.Bind(mo7926id = 2131231007)
    private TextView tvBIL;
    @ViewBind.Bind(mo7926id = 2131231008)
    private TextView tvBLD;
    @ViewBind.Bind(mo7926id = 2131231010)
    private TextView tvCA;
    @ViewBind.Bind(mo7926id = 2131231012)
    private TextView tvCRE;
    @ViewBind.Bind(mo7926id = 2131231026)
    private TextView tvGLU;
    @ViewBind.Bind(mo7926id = 2131231036)
    private TextView tvKET;
    @ViewBind.Bind(mo7926id = 2131231039)
    private TextView tvLEU;
    @ViewBind.Bind(mo7926id = 2131231044)
    private TextView tvMA;
    @ViewBind.Bind(mo7926id = 2131231050)
    private TextView tvNIT;
    @ViewBind.Bind(mo7926id = 2131231056)
    private TextView tvPH;
    @ViewBind.Bind(mo7926id = 2131231058)
    private TextView tvPRO;
    @ViewBind.Bind(mo7926id = 2131231062)
    private TextView tvSG;
    @ViewBind.Bind(mo7926id = 2131231068)
    private TextView tvTitle;
    @ViewBind.Bind(mo7926id = 2131231070)
    private TextView tvUBG;
    @ViewBind.Bind(mo7926id = 2131231073)
    private TextView tvVC;
    private TextView[] tvs;
    /* access modifiers changed from: private */
    public Urinalysis urinalysis;
    /* access modifiers changed from: private */
    public UrinalysisManager urinalysisManager = UrinalysisManager.getInstance();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_urinalysis_operate);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_urinalysis_operate));
        ViewBind.bind((Activity) this);
        this.tvs = new TextView[]{this.tvLEU, this.tvBLD, this.tvNIT, this.tvKET, this.tvUBG, this.tvBIL, this.tvPRO, this.tvGLU, this.tvPH, this.tvVC, this.tvSG, this.tvMA, this.tvCA, this.tvCRE};
        this.lbm = LocalBroadcastManager.getInstance(this.appBase.getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UrinalysisManager.NOTIFY);
        intentFilter.addAction(UrinalysisManager.HAND);
        intentFilter.addAction(UrinalysisManager.QUERY);
        intentFilter.addAction(UrinalysisManager.TIME);
        intentFilter.addAction(UrinalysisManager.DEVICE_AVAILABLE);
        intentFilter.addAction("device_unavailable");
        intentFilter.addAction(UrinalysisManager.DEVICE_STATE_OFF);
        this.lbm.registerReceiver(this.receiver, intentFilter);
        registerReceiver(this.bluetoothStateListener, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        if (getIntent().getBooleanExtra("isNeedCheckDevice", false)) {
            this.loadingDialog = new LoadingDialog(this);
            this.loadingDialog.setLoadingText(getString(C0418R.string.check_device));
            this.loadingDialog.show();
        } else {
            checkDeviceType();
        }
        clearResult();
        this.Printer_ID = getDeviceData(this, "Printer");
    }

    /* access modifiers changed from: private */
    public void checkDeviceType() {
        this.isCheckDeviceType = true;
        this.loadingDialog = new LoadingDialog(this);
        this.loadingDialog.setLoadingText(getString(C0418R.string.check_device_type));
        this.loadingDialog.show();
        this.urinalysisManager.postHand();
        Utils.delayTask(new Runnable() {
            public void run() {
                if (UrinalysisMainActivity.this.isCheckDeviceType) {
                    if (UrinalysisMainActivity.this.loadingDialog != null) {
                        UrinalysisMainActivity.this.loadingDialog.dismiss();
                    }
                    TipDialog tipDialog = new TipDialog(UrinalysisMainActivity.this);
                    tipDialog.setText((int) C0418R.string.device_type_mismatch);
                    tipDialog.setOnOperateListener(new TipDialog.OnOperateListener() {
                        public void onConfirm() {
                            UrinalysisMainActivity.this.getSharedPreferences("name", 0).edit().putString("Urinalysis", "").apply();
                            UrinalysisMainActivity.this.urinalysisManager.disconnect();
                            UrinalysisMainActivity.this.finish();
                        }
                    });
                    tipDialog.show();
                }
            }
        }, 5000);
    }

    private void clearResult() {
        TextView[] textViewArr = {this.tvLEU, this.tvNIT, this.tvUBG, this.tvPRO, this.tvPH, this.tvBLD, this.tvSG, this.tvKET, this.tvBIL, this.tvGLU, this.tvVC, this.tvCA, this.tvMA, this.tvCRE};
        for (int i = 0; i < textViewArr.length; i++) {
            textViewArr[i].setText("");
            int parseColor = Color.parseColor("#FFFFFF");
            if (i % 2 == 1) {
                parseColor = Color.parseColor("#E5F6FF");
            }
            ((ViewGroup) textViewArr[i].getParent()).setBackgroundColor(parseColor);
            textViewArr[i].setCompoundDrawables((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        }
    }

    /* access modifiers changed from: private */
    public void showResult() {
        clearResult();
        UrinalysisResult parseUrinalysisData = Utils.parseUrinalysisData(this.data);
        PrintStream printStream = System.out;
        printStream.println("data:::  " + Utils.byteArray2HexString(this.data));
        String[] realValueText = Utils.getRealValueText(parseUrinalysisData);
        int[] urinalysisAbnormal = Utils.getUrinalysisAbnormal(parseUrinalysisData);
        for (int i = 0; i < realValueText.length; i++) {
            this.tvs[i].setText(realValueText[i]);
            if (urinalysisAbnormal[i] != 0) {
                ((ViewGroup) this.tvs[i].getParent()).setBackgroundColor(Utils.getAbnormalBackgroundColor());
            }
            if (i == 8 || i == 10) {
                if (urinalysisAbnormal[i] == -1) {
                    Drawable drawable = getResources().getDrawable(C0418R.C0419drawable.ico_xjt);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, drawable, (Drawable) null);
                } else if (urinalysisAbnormal[i] == 1) {
                    Drawable drawable2 = getResources().getDrawable(C0418R.C0419drawable.ico_sjt);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                    this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, drawable2, (Drawable) null);
                }
            }
        }
        if (this.data.length == 19) {
            ((ViewGroup) this.tvCA.getParent()).setVisibility(8);
            ((ViewGroup) this.tvMA.getParent()).setVisibility(8);
            ((ViewGroup) this.tvCRE.getParent()).setVisibility(8);
        } else {
            ((ViewGroup) this.tvCA.getParent()).setVisibility(0);
            ((ViewGroup) this.tvMA.getParent()).setVisibility(0);
            ((ViewGroup) this.tvCRE.getParent()).setVisibility(0);
        }
        if (!this.Printer_ID.equals("")) {
            initBluetooth();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case C0418R.C0420id.btHand:
                this.urinalysisManager.postHand();
                return;
            case C0418R.C0420id.btQuery:
                this.urinalysisManager.query();
                return;
            case C0418R.C0420id.btSwitch_Urinalysis:
                showNormalDialog();
                return;
            case C0418R.C0420id.btTimeSynchronization:
                this.urinalysisManager.setTime();
                return;
            case C0418R.C0420id.ivClose:
                finish();
                return;
            default:
                return;
        }
    }

    private void showNormalDialog() {
        AskDialog askDialog = new AskDialog(this);
        askDialog.setText((int) C0418R.string.delete_urinalysis);
        askDialog.setOnOperateListener(new AskCreateNickNameDialog.OnOperateListener() {
            public void onConfirm() {
                UrinalysisMainActivity.this.getSharedPreferences("name", 0).edit().putString("Urinalysis", "").apply();
                UrinalysisMainActivity.this.urinalysisManager.disconnect();
                UrinalysisMainActivity.this.finish();
            }
        });
        askDialog.show();
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
                    UrinalysisMainActivity.this.finish();
                }
            });
            tipDialog.show();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.lbm.unregisterReceiver(this.receiver);
        BroadcastReceiver broadcastReceiver = this.bluetoothStateListener;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    public void initBluetooth() {
        UrinalysisResult parseUrinalysisData = Utils.parseUrinalysisData(this.data);
        String[] realValueText = Utils.getRealValueText(parseUrinalysisData);
        int[] urinalysisAbnormal = Utils.getUrinalysisAbnormal(parseUrinalysisData);
        final String valueOf = String.valueOf(this.tvs[0].getText());
        String valueOf2 = String.valueOf(this.tvs[1].getText());
        final String valueOf3 = String.valueOf(this.tvs[2].getText());
        final String valueOf4 = String.valueOf(this.tvs[3].getText());
        final String valueOf5 = String.valueOf(this.tvs[4].getText());
        final String valueOf6 = String.valueOf(this.tvs[5].getText());
        final String valueOf7 = String.valueOf(this.tvs[6].getText());
        final String valueOf8 = String.valueOf(this.tvs[7].getText());
        final String valueOf9 = String.valueOf(this.tvs[8].getText());
        final String valueOf10 = String.valueOf(this.tvs[9].getText());
        String valueOf11 = String.valueOf(this.tvs[10].getText());
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
                str7 = getResources().getString(C0418R.string.male);
            } else if (str7.equals("2")) {
                str7 = getResources().getString(C0418R.string.female);
            }
        }
        final String str8 = str7;
        final String format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date(System.currentTimeMillis()));
        connectDevice(this.Printer_ID);
        new Intent().putExtra(EXTRA_DEVICE_ADDRESS_a, this.Printer_ID);
        C04906 r32 = r0;
        final String str9 = str;
        Handler handler = new Handler();
        final String str10 = valueOf2;
        final String str11 = valueOf11;
        final String str12 = str2;
        C04906 r0 = new Runnable(this) {
            final /* synthetic */ UrinalysisMainActivity this$0;

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

    public static String getDeviceData(Context context, String str) {
        return context.getSharedPreferences("name", 0).getString(str, "").replaceAll(" ", "");
    }
}
