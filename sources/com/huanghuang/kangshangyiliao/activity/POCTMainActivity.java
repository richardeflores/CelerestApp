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
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.common.StringUtils;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.bean.POCTResult;
import com.huanghuang.kangshangyiliao.dao.DoctorAndPatientDao;
import com.huanghuang.kangshangyiliao.dao.NickNameDao;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import com.huanghuang.kangshangyiliao.dao.bean.DoctorAndPatientBean;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.dao.bean.POCT;
import com.huanghuang.kangshangyiliao.dao.bean.Printer;
import com.huanghuang.kangshangyiliao.dayiji.BluetoothPrintService;
import com.huanghuang.kangshangyiliao.util.Age;
import com.huanghuang.kangshangyiliao.util.POCTManager;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class POCTMainActivity extends BaseActivity implements View.OnClickListener {
    public static String EXTRA_DEVICE_ADDRESS_a = "device_address";
    private static final int REQUEST_CREATE_NICK_NAME = 1001;
    private String Printer_ID;
    private Age age = new Age();
    private String age_phase;
    private AlertDialog alert = null;
    private AppBase appBase = AppBase.getInstance();
    private BroadcastReceiver bluetoothStateListener = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1)) {
                case 10:
                    POCTMainActivity.this.onDeviceStateOff();
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    @ViewBind.Bind(mo7926id = 2131230799)
    public Button btMeasurement;
    private AlertDialog.Builder builder = null;
    private ClinicName clinicName = new ClinicName();
    private String currentClinicName;
    private NickNameDao dao = new NickNameDao();
    /* access modifiers changed from: private */
    public byte[] data;
    private DoctorAndPatientDao doctorAndPatientDao = new DoctorAndPatientDao();
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    /* access modifiers changed from: private */
    public boolean isCheckDeviceType = false;
    /* access modifiers changed from: private */
    public boolean isUnderway = false;
    private LocalBroadcastManager lbm;
    /* access modifiers changed from: private */
    public LoadingDialog loadingDialog;
    private NickName nickName = new NickName();
    /* access modifiers changed from: private */
    public POCT poct;
    /* access modifiers changed from: private */
    public POCTManager poctManager = POCTManager.getInstance();
    private Printer printer = new Printer();
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
                    case -1779137561: goto L_0x0053;
                    case -606267464: goto L_0x0049;
                    case -545089593: goto L_0x003f;
                    case 687444126: goto L_0x0035;
                    case 687809276: goto L_0x002b;
                    case 806564303: goto L_0x0021;
                    case 932704315: goto L_0x0017;
                    case 1863428709: goto L_0x000d;
                    default: goto L_0x000c;
                }
            L_0x000c:
                goto L_0x005d
            L_0x000d:
                java.lang.String r0 = "set_patient_info"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x005d
                r3 = 4
                goto L_0x005e
            L_0x0017:
                java.lang.String r0 = "set_title"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x005d
                r3 = 3
                goto L_0x005e
            L_0x0021:
                java.lang.String r0 = "poct_device_available"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x005d
                r3 = 5
                goto L_0x005e
            L_0x002b:
                java.lang.String r0 = "poct_time"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x005d
                r3 = 2
                goto L_0x005e
            L_0x0035:
                java.lang.String r0 = "poct_hand"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x005d
                r3 = 1
                goto L_0x005e
            L_0x003f:
                java.lang.String r0 = "poct_device_state_off"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x005d
                r3 = 7
                goto L_0x005e
            L_0x0049:
                java.lang.String r0 = "poct_notify"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x005d
                r3 = 0
                goto L_0x005e
            L_0x0053:
                java.lang.String r0 = "device_unavailable"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x005d
                r3 = 6
                goto L_0x005e
            L_0x005d:
                r3 = -1
            L_0x005e:
                switch(r3) {
                    case 0: goto L_0x0128;
                    case 1: goto L_0x00f8;
                    case 2: goto L_0x00e6;
                    case 3: goto L_0x00d4;
                    case 4: goto L_0x00c2;
                    case 5: goto L_0x009a;
                    case 6: goto L_0x006a;
                    case 7: goto L_0x0063;
                    default: goto L_0x0061;
                }
            L_0x0061:
                goto L_0x0190
            L_0x0063:
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                r3.onDeviceStateOff()
                goto L_0x0190
            L_0x006a:
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x007b
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.dismiss()
            L_0x007b:
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                boolean unused = r3.isUnderway = r1
                com.huanghuang.kangshangyiliao.widget.TipDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipDialog
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r4 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                r3.<init>(r4)
                r4 = 2131558528(0x7f0d0080, float:1.8742374E38)
                r3.setText((int) r4)
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity$2$2 r4 = new com.huanghuang.kangshangyiliao.activity.POCTMainActivity$2$2
                r4.<init>()
                r3.setOnOperateListener(r4)
                r3.show()
                goto L_0x0190
            L_0x009a:
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x00ab
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.dismiss()
            L_0x00ab:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r4 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                r3.<init>(r4)
                r4 = 2131558523(0x7f0d007b, float:1.8742364E38)
                r3.setText((int) r4)
                r3.show()
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                r3.checkDeviceType()
                goto L_0x0190
            L_0x00c2:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r4 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                r3.<init>(r4)
                r4 = 2131558738(0x7f0d0152, float:1.87428E38)
                r3.setText((int) r4)
                r3.show()
                goto L_0x0190
            L_0x00d4:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r4 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                r3.<init>(r4)
                r4 = 2131558739(0x7f0d0153, float:1.8742802E38)
                r3.setText((int) r4)
                r3.show()
                goto L_0x0190
            L_0x00e6:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r4 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                r3.<init>(r4)
                r4 = 2131558760(0x7f0d0168, float:1.8742845E38)
                r3.setText((int) r4)
                r3.show()
                goto L_0x0190
            L_0x00f8:
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                boolean r3 = r3.isCheckDeviceType
                if (r3 == 0) goto L_0x0117
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                boolean unused = r3.isCheckDeviceType = r1
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x0116
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.dismiss()
            L_0x0116:
                return
            L_0x0117:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r4 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                r3.<init>(r4)
                r4 = 2131558548(0x7f0d0094, float:1.8742415E38)
                r3.setText((int) r4)
                r3.show()
                goto L_0x0190
            L_0x0128:
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                boolean unused = r3.isUnderway = r1
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                android.os.Handler r3 = r3.handler
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r0 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                java.lang.Runnable r0 = r0.runnable
                r3.removeCallbacks(r0)
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x014d
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.dismiss()
            L_0x014d:
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                java.lang.String r0 = "poct"
                java.io.Serializable r4 = r4.getSerializableExtra(r0)
                com.huanghuang.kangshangyiliao.dao.bean.POCT r4 = (com.huanghuang.kangshangyiliao.dao.bean.POCT) r4
                com.huanghuang.kangshangyiliao.dao.bean.POCT unused = r3.poct = r4
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                com.huanghuang.kangshangyiliao.dao.bean.POCT r3 = r3.poct
                if (r3 == 0) goto L_0x0176
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                com.huanghuang.kangshangyiliao.dao.bean.POCT r4 = r3.poct
                java.lang.String r4 = r4.data
                byte[] r4 = com.huanghuang.kangshangyiliao.util.Utils.toByteArray(r4)
                byte[] unused = r3.data = r4
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                r3.showResult()
            L_0x0176:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity r4 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                r3.<init>(r4)
                r4 = 2131558658(0x7f0d0102, float:1.8742638E38)
                r3.setText((int) r4)
                r3.show()
                com.huanghuang.kangshangyiliao.activity.POCTMainActivity$2$1 r3 = new com.huanghuang.kangshangyiliao.activity.POCTMainActivity$2$1
                r3.<init>()
                r0 = 1000(0x3e8, double:4.94E-321)
                com.huanghuang.kangshangyiliao.util.Utils.delayTask(r3, r0)
            L_0x0190:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.activity.POCTMainActivity.C04542.onReceive(android.content.Context, android.content.Intent):void");
        }
    };
    /* access modifiers changed from: private */
    public Runnable runnable = new Runnable() {
        public void run() {
            if (POCTMainActivity.this.isUnderway) {
                if (POCTMainActivity.this.loadingDialog != null) {
                    POCTMainActivity.this.loadingDialog.dismiss();
                }
                TipToastDialog tipToastDialog = new TipToastDialog(POCTMainActivity.this);
                tipToastDialog.setImg(C0418R.C0419drawable.ico_tstb);
                tipToastDialog.setText((int) C0418R.string.measurement_timeout);
                tipToastDialog.show();
                POCTMainActivity.this.btMeasurement.setEnabled(true);
            }
        }
    };
    private Session session = Session.getInstance();
    @ViewBind.Bind(mo7926id = 2131231005)
    private TextView tvBAS;
    @ViewBind.Bind(mo7926id = 2131231006)
    private TextView tvBASP;
    @ViewBind.Bind(mo7926id = 2131231023)
    private TextView tvEOS;
    @ViewBind.Bind(mo7926id = 2131231024)
    private TextView tvEOSP;
    @ViewBind.Bind(mo7926id = 2131231040)
    private TextView tvLYMP;
    @ViewBind.Bind(mo7926id = 2131231041)
    private TextView tvLYMPP;
    @ViewBind.Bind(mo7926id = 2131231045)
    private TextView tvMON;
    @ViewBind.Bind(mo7926id = 2131231046)
    private TextView tvMONP;
    @ViewBind.Bind(mo7926id = 2131231048)
    private TextView tvNEUI;
    @ViewBind.Bind(mo7926id = 2131231049)
    private TextView tvNEUTP;
    @ViewBind.Bind(mo7926id = 2131231068)
    private TextView tvTitle;
    @ViewBind.Bind(mo7926id = 2131231074)
    private TextView tvWBC;
    @ViewBind.Bind(mo7926id = 2131231076)
    private TextView tvWBC_Scope;
    @ViewBind.Bind(mo7926id = 2131231077)
    private TextView tvWBC_unit;
    @ViewBind.Bind(mo7926id = 2131231079)
    private TextView tv_device_name;
    private TextView[] tvs;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_poct_operate);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_poct_operate));
        ViewBind.bind((Activity) this);
        if (this.nickName.birthday != null) {
            this.age_phase = Age.age_phase(this.nickName.birthday);
            if ("0".equals(this.age_phase)) {
                this.tvWBC_unit.setText(C0418R.string.wbc_unit);
                this.tvWBC_Scope.setText(C0418R.string.wbc_newborn_reference_range);
            } else if ("1".equals(this.age_phase)) {
                this.tvWBC_unit.setText(C0418R.string.wbc_unit);
                this.tvWBC_Scope.setText(C0418R.string.wbc_baby_reference_range);
            } else if ("2".equals(this.age_phase)) {
                this.tvWBC_unit.setText(C0418R.string.wbc_unit);
                this.tvWBC_Scope.setText(C0418R.string.wbc_children_reference_range);
            } else {
                this.tvWBC_unit.setText(C0418R.string.wbc_man_unit);
                this.tvWBC_Scope.setText(C0418R.string.wbc_reference_range);
            }
        }
        this.tvs = new TextView[]{this.tvWBC, this.tvLYMP, this.tvMON, this.tvNEUI, this.tvEOS, this.tvBAS, this.tvLYMPP, this.tvMONP, this.tvNEUTP, this.tvEOSP, this.tvBASP};
        this.lbm = LocalBroadcastManager.getInstance(this.appBase.getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(POCTManager.NOTIFY);
        intentFilter.addAction(POCTManager.HAND);
        intentFilter.addAction(POCTManager.TIME);
        intentFilter.addAction("set_title");
        intentFilter.addAction("set_patient_info");
        intentFilter.addAction(POCTManager.DEVICE_AVAILABLE);
        intentFilter.addAction("device_unavailable");
        intentFilter.addAction(POCTManager.DEVICE_STATE_OFF);
        this.lbm.registerReceiver(this.receiver, intentFilter);
        registerReceiver(this.bluetoothStateListener, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        if (getIntent().getBooleanExtra("isNeedCheckDevice", false)) {
            this.loadingDialog = new LoadingDialog(this);
            this.loadingDialog.setLoadingText(getString(C0418R.string.check_device));
            this.loadingDialog.show();
        } else {
            checkDeviceType();
        }
        this.Printer_ID = getDeviceData(this, "Printer");
        this.currentClinicName = getIntent().getStringExtra("currentClinicName");
        TextView textView = this.tv_device_name;
        textView.setText(getString(C0418R.string.current_device_name) + " " + Session.getInstance().getPOCTCurrentDevice());
    }

    /* access modifiers changed from: private */
    public void checkDeviceType() {
        this.isCheckDeviceType = true;
        this.loadingDialog = new LoadingDialog(this);
        this.loadingDialog.setLoadingText(getString(C0418R.string.check_device_type));
        this.loadingDialog.show();
        this.poctManager.postHand();
        Utils.delayTask(new Runnable() {
            public void run() {
                if (POCTMainActivity.this.isCheckDeviceType) {
                    TipToastDialog tipToastDialog = new TipToastDialog(POCTMainActivity.this);
                    tipToastDialog.setText(POCTMainActivity.this.getResources().getString(C0418R.string.first_hand_fail));
                    tipToastDialog.show();
                    POCTMainActivity.this.poctManager.postHand();
                    if (POCTMainActivity.this.isCheckDeviceType) {
                        POCTMainActivity.this.dismissDialog();
                        TipDialog tipDialog = new TipDialog(POCTMainActivity.this);
                        tipDialog.setText((int) C0418R.string.device_type_mismatch);
                        tipDialog.setOnOperateListener(new TipDialog.OnOperateListener() {
                            public void onConfirm() {
                                POCTMainActivity.this.getSharedPreferences("name", 0).edit().putString("POCT", "").apply();
                                POCTMainActivity.this.poctManager.disconnect();
                                POCTMainActivity.this.finish();
                            }
                        });
                        tipDialog.show();
                    }
                }
            }
        }, 5000);
    }

    public void clearResult() {
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

    /* access modifiers changed from: private */
    public void showResult() {
        clearResult();
        POCTResult parsePOCTData = Utils.parsePOCTData(this.data);
        String[] pOCTRealValueText = Utils.getPOCTRealValueText(parsePOCTData);
        int[] pOCTAbnormal = Utils.getPOCTAbnormal(parsePOCTData, this.age_phase);
        for (int i = 0; i < pOCTRealValueText.length; i++) {
            this.tvs[i].setText(pOCTRealValueText[i]);
            if (pOCTAbnormal[i] != 0) {
                ((ViewGroup) this.tvs[i].getParent()).setBackgroundColor(Utils.getAbnormalBackgroundColor());
            }
            if (pOCTAbnormal[i] == -1) {
                Drawable drawable = getResources().getDrawable(C0418R.C0419drawable.ico_xjt);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, drawable, (Drawable) null);
            } else if (pOCTAbnormal[i] == 1) {
                Drawable drawable2 = getResources().getDrawable(C0418R.C0419drawable.ico_sjt);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, drawable2, (Drawable) null);
            }
        }
        if (!this.Printer_ID.equals("")) {
            ininBluetooth();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case C0418R.C0420id.btHand:
                this.poctManager.postHand();
                return;
            case C0418R.C0420id.btMeasurement:
                clearResult();
                this.poctManager.measurement();
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
            case C0418R.C0420id.btSetPatientInfo:
                initDialog();
                return;
            case C0418R.C0420id.btSetPrintTitle:
                setPatientInFoDialog();
                return;
            case C0418R.C0420id.btSwitch_Poct:
                showNormalDialog();
                return;
            case C0418R.C0420id.btTimeSynchronization:
                this.poctManager.setTime();
                return;
            case C0418R.C0420id.ivClose:
                finish();
                return;
            default:
                return;
        }
    }

    private void initDialog() {
        final CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
            public void onPositiveClick(String str, String str2, String str3, String str4, String str5) {
                POCTMainActivity.this.poctManager.setPatientInfo(str, str2, str3, str4);
                POCTMainActivity.this.saveSettingData(str2, str4, str5);
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

    private void initPoctDailog() {
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
            /* JADX WARNING: Removed duplicated region for block: B:13:0x0088  */
            /* JADX WARNING: Removed duplicated region for block: B:15:0x0095  */
            /* JADX WARNING: Removed duplicated region for block: B:20:0x00ae  */
            /* JADX WARNING: Removed duplicated region for block: B:21:0x00ba  */
            /* JADX WARNING: Removed duplicated region for block: B:28:0x00d9  */
            /* JADX WARNING: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onClick(android.content.DialogInterface r8, int r9) {
                /*
                    r7 = this;
                    android.widget.EditText r8 = r2
                    android.text.Editable r8 = r8.getText()
                    java.lang.String r8 = r8.toString()
                    java.lang.String r9 = "zdc-inputId"
                    android.util.Log.d(r9, r8)
                    android.widget.EditText r9 = r3
                    android.text.Editable r9 = r9.getText()
                    java.lang.String r9 = r9.toString()
                    java.lang.String r0 = "zdc-inputName"
                    android.util.Log.d(r0, r9)
                    android.widget.EditText r0 = r4
                    android.text.Editable r0 = r0.getText()
                    java.lang.String r0 = r0.toString()
                    java.lang.Integer.parseInt(r0)
                    java.lang.String r1 = "zdc-inputAge"
                    android.util.Log.d(r1, r0)
                    android.widget.EditText r1 = r5
                    android.text.Editable r1 = r1.getText()
                    java.lang.String r1 = r1.toString()
                    java.lang.Integer.parseInt(r0)
                    java.lang.String r2 = "zdc-inputSex"
                    android.util.Log.d(r2, r1)
                    java.lang.String r2 = ""
                    boolean r3 = r8.equals(r2)
                    r4 = 1
                    if (r3 == 0) goto L_0x0058
                    com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                    java.lang.String r5 = "ID不能为空"
                    android.widget.Toast r3 = android.widget.Toast.makeText(r3, r5, r4)
                    r3.show()
                    r3 = 1
                    goto L_0x0059
                L_0x0058:
                    r3 = 0
                L_0x0059:
                    boolean r5 = r9.equals(r2)
                    if (r5 == 0) goto L_0x006c
                    com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                    java.lang.String r5 = "姓名不能为空"
                    android.widget.Toast r3 = android.widget.Toast.makeText(r3, r5, r4)
                    r3.show()
                L_0x006a:
                    r3 = 1
                    goto L_0x0082
                L_0x006c:
                    com.huanghuang.kangshangyiliao.activity.POCTMainActivity r5 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                    int r5 = r5.getStrLength(r9)
                    r6 = 8
                    if (r5 <= r6) goto L_0x0082
                    com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                    java.lang.String r5 = "姓名长度不得超过8个字符或4个中文汉字"
                    android.widget.Toast r3 = android.widget.Toast.makeText(r3, r5, r4)
                    r3.show()
                    goto L_0x006a
                L_0x0082:
                    boolean r5 = r0.equals(r2)
                    if (r5 == 0) goto L_0x0095
                    com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                    java.lang.String r5 = "年龄不能为空"
                    android.widget.Toast r3 = android.widget.Toast.makeText(r3, r5, r4)
                    r3.show()
                L_0x0093:
                    r3 = 1
                    goto L_0x00a8
                L_0x0095:
                    int r5 = r0.length()
                    r6 = 4
                    if (r5 < r6) goto L_0x00a8
                    com.huanghuang.kangshangyiliao.activity.POCTMainActivity r3 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                    java.lang.String r5 = "年龄长度不得超过999"
                    android.widget.Toast r3 = android.widget.Toast.makeText(r3, r5, r4)
                    r3.show()
                    goto L_0x0093
                L_0x00a8:
                    boolean r2 = r1.equals(r2)
                    if (r2 == 0) goto L_0x00ba
                    com.huanghuang.kangshangyiliao.activity.POCTMainActivity r2 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                    java.lang.String r3 = "性别不能为空"
                    android.widget.Toast r2 = android.widget.Toast.makeText(r2, r3, r4)
                    r2.show()
                    goto L_0x00d7
                L_0x00ba:
                    java.lang.String r2 = "0"
                    boolean r2 = r1.equals(r2)
                    if (r2 != 0) goto L_0x00d6
                    java.lang.String r2 = "1"
                    boolean r2 = r1.equals(r2)
                    if (r2 != 0) goto L_0x00d6
                    com.huanghuang.kangshangyiliao.activity.POCTMainActivity r2 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                    java.lang.String r3 = "性别只能为0或1"
                    android.widget.Toast r2 = android.widget.Toast.makeText(r2, r3, r4)
                    r2.show()
                    goto L_0x00d7
                L_0x00d6:
                    r4 = r3
                L_0x00d7:
                    if (r4 != 0) goto L_0x00e2
                    com.huanghuang.kangshangyiliao.activity.POCTMainActivity r2 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.this
                    com.huanghuang.kangshangyiliao.util.POCTManager r2 = r2.poctManager
                    r2.setPatientInfo(r8, r9, r0, r1)
                L_0x00e2:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.activity.POCTMainActivity.C04616.onClick(android.content.DialogInterface, int):void");
            }
        }).setNegativeButton(getString(C0418R.string.record_save_dialog_cancel), (DialogInterface.OnClickListener) null).create();
        this.alert.show();
    }

    private void setPatientInFoDialog() {
        final EditText editText = new EditText(this);
        editText.setFocusable(true);
        this.builder = new AlertDialog.Builder(this);
        this.alert = this.builder.setTitle(getResources().getString(C0418R.string.input_print_title)).setView(editText).setPositiveButton(getString(C0418R.string.record_save_dialog_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                String obj = editText.getText().toString();
                if (obj.equals("")) {
                    POCTMainActivity pOCTMainActivity = POCTMainActivity.this;
                    Toast.makeText(pOCTMainActivity, pOCTMainActivity.getResources().getString(C0418R.string.print_title_not_empty), 1).show();
                } else if (POCTMainActivity.this.getStrLength(obj) >= 31) {
                    POCTMainActivity pOCTMainActivity2 = POCTMainActivity.this;
                    Toast.makeText(pOCTMainActivity2, pOCTMainActivity2.getResources().getString(C0418R.string.print_title_not_length), 1).show();
                } else {
                    POCTMainActivity.this.poctManager.setTitle(obj);
                }
            }
        }).setNegativeButton(getString(C0418R.string.record_save_dialog_cancel), (DialogInterface.OnClickListener) null).create();
        this.alert.show();
    }

    private void showNormalDialog() {
        AskDialog askDialog = new AskDialog(this);
        askDialog.setText((int) C0418R.string.delete_poct);
        askDialog.setOnOperateListener(new AskCreateNickNameDialog.OnOperateListener() {
            public void onConfirm() {
                POCTMainActivity.this.getSharedPreferences("name", 0).edit().putString("POCT", "").apply();
                POCTMainActivity.this.poctManager.disconnect();
                POCTMainActivity.this.finish();
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
                    POCTMainActivity.this.finish();
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
        super.onDestroy();
        this.lbm.unregisterReceiver(this.receiver);
        BroadcastReceiver broadcastReceiver = this.bluetoothStateListener;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        this.handler.removeCallbacks(this.runnable);
    }

    public void ininBluetooth() {
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
        String str = strArr[0];
        String str2 = strArr[1];
        String str3 = strArr[2];
        String str4 = strArr[3];
        String str5 = strArr[4];
        String str6 = strArr[5];
        String str7 = strArr[6];
        String str8 = strArr[7];
        String str9 = strArr[8];
        String str10 = strArr[9];
        String str11 = strArr[10];
        String str12 = this.printer.PrinterName;
        ClinicName clinicInfo = Utils.getClinicInfo();
        NickName userInfo = Utils.getUserInfo();
        final String str13 = clinicInfo.clinicName;
        String str14 = clinicInfo.doctorName;
        final String str15 = userInfo.nickName;
        String str16 = userInfo.birthday;
        String str17 = userInfo.sex;
        if (str17 != null) {
            if (str17.equals("1")) {
                str17 = "男";
            } else if (str17.equals("2")) {
                str17 = "女";
            }
        }
        String str18 = str17;
        if (this.nickName.birthday != null) {
            this.age_phase = Age.age_phase(this.nickName.birthday);
            if (!"0".equals(this.age_phase) && !"1".equals(this.age_phase)) {
                boolean equals = "2".equals(this.age_phase);
            }
        }
        final String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        connectDevice(this.Printer_ID);
        new Intent().putExtra(EXTRA_DEVICE_ADDRESS_a, this.Printer_ID);
        C045310 r38 = r0;
        Handler handler2 = new Handler();
        final String str19 = str18;
        final String str20 = str;
        final String str21 = str2;
        final String str22 = str3;
        final String str23 = str4;
        final String str24 = str5;
        final String str25 = str6;
        final String str26 = str7;
        final String str27 = str8;
        final String str28 = str9;
        final String str29 = str10;
        final String str30 = str11;
        C045310 r0 = new Runnable(this) {
            final /* synthetic */ POCTMainActivity this$0;

            {
                this.this$0 = r3;
            }

            public void run() {
                String str;
                String str2;
                String str3;
                String str4;
                String str5 = "诊    所：" + str13;
                String str6 = "姓    名：" + str15;
                String str7 = "性    别：" + str19;
                String str8 = "测量时间：" + format;
                String str9 = " WBC   " + valueOf + str20 + "  10^9/L  3.5～9.5";
                String str10 = " LYM*  " + valueOf2 + str21 + "  10^9/L  1.1～3.2";
                String str11 = " MON*  " + valueOf3 + str22 + "  10^9/L  0.1～0.6";
                String str12 = " NEU*  " + valueOf4 + str23 + "  10^9/L  1.8～6.3";
                String str13 = " EOS*  " + valueOf5 + str24 + " 10^9/L  0.02～0.52";
                String str14 = " BAS*  " + valueOf6 + str25 + " 10^9/L  0～0.06";
                String str15 = "";
                if (valueOf7.length() == 3) {
                    str = " LYM%* " + valueOf7 + " " + str26 + "   %     20.0～50.0";
                } else if (valueOf7.length() == 4) {
                    str = " LYM%* " + valueOf7 + str26 + "   %     20.0～50.0";
                } else {
                    str = str15;
                }
                String str16 = str;
                if (valueOf8.length() == 3) {
                    str2 = " MON%* " + valueOf8 + " " + str27 + "   %     3.0～10.0";
                } else if (valueOf7.length() == 4) {
                    str2 = " MON%* " + valueOf8 + str27 + "   %     3.0～10.0";
                } else {
                    str2 = str15;
                }
                String str17 = str2;
                if (valueOf9.length() == 3) {
                    str3 = " NEU%* " + valueOf9 + " " + str28 + "   %     40.0～75.0";
                } else if (valueOf7.length() == 4) {
                    str3 = " NEU%* " + valueOf9 + str28 + "   %     40.0～75.0";
                } else {
                    str3 = str15;
                }
                String str18 = str3;
                if (valueOf10.length() == 3) {
                    str4 = " EOS%* " + valueOf10 + " " + str29 + "   %     0.4～8.0";
                } else if (valueOf7.length() == 4) {
                    str4 = " EOS%* " + valueOf10 + str29 + "   %     0.4～8.0";
                } else {
                    str4 = str15;
                }
                if (valueOf11.length() == 3) {
                    str15 = " BAS%* " + valueOf11 + " " + str30 + "   %     0～1.0";
                } else if (valueOf7.length() == 4) {
                    str15 = " BAS%* " + valueOf11 + str30 + "   %     0～1.0";
                }
                this.this$0.sendPrintFile("       白细胞分析仪报告单       " + "\n\n" + str5 + "\n" + str6 + "\n" + str7 + "\n" + "年    龄：25" + "\n" + "病人ID  ：15" + "\n" + "样本编号：00053" + "\n" + str8 + "\n\n" + " 项目  结果   单位    参考范围" + "\n" + str9 + "\n" + str10 + "\n" + str11 + "\n" + str12 + "\n" + str13 + "\n" + str14 + "\n" + str16 + "\n" + str17 + "\n" + str18 + "\n" + str4 + "\n" + str15 + "\n\n" + "以上结果仅对本次测量负责。" + "\n\n" + "审核：_______________" + "\n\n\n\n");
            }
        };
        handler2.postDelayed(r38, 1000);
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

    public int getStrLength(String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            int codePointAt = Character.codePointAt(str, i2);
            i = (codePointAt < 0 || codePointAt > 255) ? i + 2 : i + 1;
        }
        return i;
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
