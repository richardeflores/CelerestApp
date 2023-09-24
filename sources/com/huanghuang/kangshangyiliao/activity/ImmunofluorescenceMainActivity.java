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
import android.util.Log;
import android.view.LayoutInflater;
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
import com.huanghuang.kangshangyiliao.adapter.ImmunofluoFragmentAdapter;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.bean.ImmuFluoResult;
import com.huanghuang.kangshangyiliao.dao.DoctorAndPatientDao;
import com.huanghuang.kangshangyiliao.dao.NickNameDao;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import com.huanghuang.kangshangyiliao.dao.bean.DoctorAndPatientBean;
import com.huanghuang.kangshangyiliao.dao.bean.Immunofluorescence;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.dao.bean.Printer;
import com.huanghuang.kangshangyiliao.dayiji.BluetoothPrintService;
import com.huanghuang.kangshangyiliao.fragment.ImmunofluoFragment_crp;
import com.huanghuang.kangshangyiliao.util.ImmunofluorescenceManager;
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
import java.io.PrintStream;

public class ImmunofluorescenceMainActivity extends BaseActivity implements View.OnClickListener {
    public static String EXTRA_DEVICE_ADDRESS_a = "device_address";
    private static final int REQUEST_CREATE_NICK_NAME = 1001;
    private String Printer_ID;
    private AlertDialog alert = null;
    private AppBase appBase = AppBase.getInstance();
    private BroadcastReceiver bluetoothStateListener = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1)) {
                case 10:
                    ImmunofluorescenceMainActivity.this.onDeviceStateOff();
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    @ViewBind.Bind(mo7926id = 2131230799)
    public Button btMeasurement;
    /* access modifiers changed from: private */
    @ViewBind.Bind(mo7926id = 2131230800)
    public Button btMeasurement02;
    private AlertDialog.Builder builder = null;
    private ClinicName clinicName = new ClinicName();
    String currentClinicName;
    private NickNameDao dao = new NickNameDao();
    /* access modifiers changed from: private */
    public byte[] data;
    private DoctorAndPatientDao doctorAndPatientDao = new DoctorAndPatientDao();
    private ImmunofluoFragmentAdapter fragmentAdapter;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    /* access modifiers changed from: private */
    public Immunofluorescence immunofluorescence;
    /* access modifiers changed from: private */
    public ImmunofluorescenceManager immunofluorescenceManager = ImmunofluorescenceManager.getInstance();
    /* access modifiers changed from: private */
    public boolean isCheckDeviceType = false;
    /* access modifiers changed from: private */
    public boolean isUnderway = false;
    private LocalBroadcastManager lbm;
    /* access modifiers changed from: private */
    public LoadingDialog loadingDialog;
    private NickName nickName = new NickName();
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
                    case -1522631667: goto L_0x0049;
                    case -1163786701: goto L_0x003f;
                    case -1163421551: goto L_0x0035;
                    case -379169316: goto L_0x002b;
                    case 932704315: goto L_0x0021;
                    case 972484580: goto L_0x0017;
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
                java.lang.String r0 = "immunofluorescence_device_available"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x005d
                r3 = 5
                goto L_0x005e
            L_0x0021:
                java.lang.String r0 = "set_title"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x005d
                r3 = 3
                goto L_0x005e
            L_0x002b:
                java.lang.String r0 = "immunofluorescence_device_state_off"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x005d
                r3 = 7
                goto L_0x005e
            L_0x0035:
                java.lang.String r0 = "immunofluorescence_time"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x005d
                r3 = 2
                goto L_0x005e
            L_0x003f:
                java.lang.String r0 = "immunofluorescence_hand"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x005d
                r3 = 1
                goto L_0x005e
            L_0x0049:
                java.lang.String r0 = "immunofluorescence_notify"
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
                    case 0: goto L_0x0129;
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
                goto L_0x01ab
            L_0x0063:
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                r3.onDeviceStateOff()
                goto L_0x01ab
            L_0x006a:
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x007b
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.dismiss()
            L_0x007b:
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                boolean unused = r3.isUnderway = r1
                com.huanghuang.kangshangyiliao.widget.TipDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipDialog
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r4 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                r3.<init>(r4)
                r4 = 2131558528(0x7f0d0080, float:1.8742374E38)
                r3.setText((int) r4)
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity$2$2 r4 = new com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity$2$2
                r4.<init>()
                r3.setOnOperateListener(r4)
                r3.show()
                goto L_0x01ab
            L_0x009a:
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x00ab
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.dismiss()
            L_0x00ab:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r4 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                r3.<init>(r4)
                r4 = 2131558523(0x7f0d007b, float:1.8742364E38)
                r3.setText((int) r4)
                r3.show()
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                r3.checkDeviceType()
                goto L_0x01ab
            L_0x00c2:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r4 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                r3.<init>(r4)
                r4 = 2131558738(0x7f0d0152, float:1.87428E38)
                r3.setText((int) r4)
                r3.show()
                goto L_0x01ab
            L_0x00d4:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r4 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                r3.<init>(r4)
                r4 = 2131558739(0x7f0d0153, float:1.8742802E38)
                r3.setText((int) r4)
                r3.show()
                goto L_0x01ab
            L_0x00e6:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r4 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                r3.<init>(r4)
                r4 = 2131558760(0x7f0d0168, float:1.8742845E38)
                r3.setText((int) r4)
                r3.show()
                goto L_0x01ab
            L_0x00f8:
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                boolean r3 = r3.isCheckDeviceType
                if (r3 == 0) goto L_0x0117
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                boolean unused = r3.isCheckDeviceType = r1
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x0116
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.dismiss()
            L_0x0116:
                return
            L_0x0117:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r4 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                r3.<init>(r4)
                r4 = 2131558548(0x7f0d0094, float:1.8742415E38)
                r3.setText((int) r4)
                r3.show()
                goto L_0x01ab
            L_0x0129:
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                boolean unused = r3.isUnderway = r1
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                android.os.Handler r3 = r3.handler
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r0 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                java.lang.Runnable r0 = r0.runnable
                r3.removeCallbacks(r0)
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x014e
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.dismiss()
            L_0x014e:
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                java.lang.String r0 = "immunofluorescence"
                java.io.Serializable r4 = r4.getSerializableExtra(r0)
                com.huanghuang.kangshangyiliao.dao.bean.Immunofluorescence r4 = (com.huanghuang.kangshangyiliao.dao.bean.Immunofluorescence) r4
                com.huanghuang.kangshangyiliao.dao.bean.Immunofluorescence unused = r3.immunofluorescence = r4
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                com.huanghuang.kangshangyiliao.dao.bean.Immunofluorescence r3 = r3.immunofluorescence
                if (r3 == 0) goto L_0x0191
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                com.huanghuang.kangshangyiliao.dao.bean.Immunofluorescence r4 = r3.immunofluorescence
                java.lang.String r4 = r4.data
                byte[] r4 = com.huanghuang.kangshangyiliao.util.Utils.toByteArray(r4)
                byte[] unused = r3.data = r4
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x0183
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.setMissing()
            L_0x0183:
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                r3.judgeCurrentItem()
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                byte[] r4 = r3.data
                r3.judgeFragment(r4)
            L_0x0191:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r4 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                r3.<init>(r4)
                r4 = 2131558658(0x7f0d0102, float:1.8742638E38)
                r3.setText((int) r4)
                r3.show()
                com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity$2$1 r3 = new com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity$2$1
                r3.<init>()
                r0 = 1000(0x3e8, double:4.94E-321)
                com.huanghuang.kangshangyiliao.util.Utils.delayTask(r3, r0)
            L_0x01ab:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.C04382.onReceive(android.content.Context, android.content.Intent):void");
        }
    };
    /* access modifiers changed from: private */
    public Runnable runnable = new Runnable() {
        public void run() {
            if (ImmunofluorescenceMainActivity.this.isUnderway) {
                if (ImmunofluorescenceMainActivity.this.loadingDialog != null) {
                    ImmunofluorescenceMainActivity.this.loadingDialog.dismiss();
                }
                TipToastDialog tipToastDialog = new TipToastDialog(ImmunofluorescenceMainActivity.this);
                tipToastDialog.setImg(C0418R.C0419drawable.ico_tstb);
                tipToastDialog.setText((int) C0418R.string.measurement_timeout);
                tipToastDialog.show();
                ImmunofluorescenceMainActivity.this.btMeasurement.setEnabled(true);
                ImmunofluorescenceMainActivity.this.btMeasurement02.setEnabled(true);
            }
        }
    };
    @ViewBind.Bind(mo7926id = 2131231013)
    private TextView tvCRP;
    @ViewBind.Bind(mo7926id = 2131231053)
    private TextView tvPCT;
    @ViewBind.Bind(mo7926id = 2131231059)
    private TextView tvSAA;
    @ViewBind.Bind(mo7926id = 2131231068)
    private TextView tvTitle;
    @ViewBind.Bind(mo7926id = 2131231076)
    private TextView tvWBC_Scope;
    @ViewBind.Bind(mo7926id = 2131231077)
    private TextView tvWBC_unit;
    @ViewBind.Bind(mo7926id = 2131231079)
    private TextView tv_device_name;
    private TextView[] tvs;
    @ViewBind.Bind(mo7926id = 2131231110)
    private ViewPager viewpager;

    public void ininBluetooth() {
    }

    /* access modifiers changed from: private */
    public void judgeCurrentItem() {
        ImmuFluoResult parseImmuFluoResult = Utils.parseImmuFluoResult(this.data);
        PrintStream printStream = System.out;
        printStream.println("immuFluoResult:: ReagentType  " + parseImmuFluoResult.ReagentType);
        PrintStream printStream2 = System.out;
        printStream2.println("immuFluoResult :: decimalBits:::  " + parseImmuFluoResult.decimalBits);
        PrintStream printStream3 = System.out;
        printStream3.println("immuFluoResult::snsnsn   " + parseImmuFluoResult.f73sn);
        this.viewpager.getCurrentItem();
        this.viewpager.setCurrentItem(0);
    }

    /* access modifiers changed from: private */
    public void judgeFragment(byte[] bArr) {
        int currentItem = this.viewpager.getCurrentItem();
        if (currentItem == 0) {
            ((ImmunofluoFragment_crp) this.fragmentAdapter.getItem(currentItem)).setData(bArr);
        } else if (currentItem != 1 && currentItem != 2 && currentItem != 3) {
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_immunofluorescence_operate);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_immunofluorescence_operate));
        ViewBind.bind((Activity) this);
        this.tvs = new TextView[]{this.tvCRP, this.tvSAA, this.tvPCT};
        this.lbm = LocalBroadcastManager.getInstance(this.appBase.getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ImmunofluorescenceManager.NOTIFY);
        intentFilter.addAction(ImmunofluorescenceManager.HAND);
        intentFilter.addAction(ImmunofluorescenceManager.TIME);
        intentFilter.addAction("set_title");
        intentFilter.addAction("set_patient_info");
        intentFilter.addAction(ImmunofluorescenceManager.DEVICE_AVAILABLE);
        intentFilter.addAction("device_unavailable");
        intentFilter.addAction(ImmunofluorescenceManager.DEVICE_STATE_OFF);
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
        this.Printer_ID = getDeviceData(this, "Printer");
        this.currentClinicName = getIntent().getStringExtra("currentClinicName");
    }

    /* access modifiers changed from: private */
    public void checkDeviceType() {
        this.isCheckDeviceType = true;
        this.loadingDialog = new LoadingDialog(this);
        this.loadingDialog.setLoadingText(getString(C0418R.string.check_device_type));
        this.loadingDialog.show();
        this.immunofluorescenceManager.postHand();
        Utils.delayTask(new Runnable() {
            public void run() {
                if (ImmunofluorescenceMainActivity.this.isCheckDeviceType) {
                    ImmunofluorescenceMainActivity.this.dismissDialog();
                    TipDialog tipDialog = new TipDialog(ImmunofluorescenceMainActivity.this);
                    tipDialog.setText((int) C0418R.string.device_type_mismatch);
                    tipDialog.setOnOperateListener(new TipDialog.OnOperateListener() {
                        public void onConfirm() {
                            ImmunofluorescenceMainActivity.this.getSharedPreferences("name", 0).edit().putString("Immunofluorescence", "").apply();
                            ImmunofluorescenceMainActivity.this.immunofluorescenceManager.disconnect();
                            ImmunofluorescenceMainActivity.this.finish();
                        }
                    });
                    tipDialog.show();
                }
            }
        }, 5000);
    }

    private void initViewPager() {
        this.fragmentAdapter = new ImmunofluoFragmentAdapter(getSupportFragmentManager());
        this.viewpager.setAdapter(this.fragmentAdapter);
        this.viewpager.setCurrentItem(0);
        TextView textView = this.tv_device_name;
        textView.setText(getString(C0418R.string.current_device_name) + " " + Session.getInstance().getImmunofluorescenceCurrentDevice());
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

    private void showResult() {
        clearResult();
        PrintStream printStream = System.out;
        printStream.println("datadata::" + Utils.byteArray2HexString(this.data));
        ImmuFluoResult parseImmuFluoResult = Utils.parseImmuFluoResult(this.data);
        String str = parseImmuFluoResult.stringFrst0;
        float f = parseImmuFluoResult.Frst1;
        float f2 = parseImmuFluoResult.Frst2;
        int[] immuFlorAbnormal = Utils.getImmuFlorAbnormal(parseImmuFluoResult);
        if (str.equals(".00")) {
            TextView textView = this.tvs[0];
            textView.setText(Utils.float2Point(parseImmuFluoResult.Frst0) + "");
        } else {
            this.tvs[0].setText(str);
        }
        TextView textView2 = this.tvs[1];
        textView2.setText(Utils.float2Point(parseImmuFluoResult.Frst1) + "");
        TextView textView3 = this.tvs[2];
        textView3.setText(Utils.float2Point(parseImmuFluoResult.Frst2) + "");
        int i = 0;
        while (true) {
            TextView[] textViewArr = this.tvs;
            if (i < textViewArr.length) {
                if (immuFlorAbnormal[i] != 0) {
                    ((ViewGroup) textViewArr[i].getParent()).setBackgroundColor(Utils.getAbnormalBackgroundColor());
                }
                if (immuFlorAbnormal[i] == -1) {
                    Drawable drawable = getResources().getDrawable(C0418R.C0419drawable.ico_xjt);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, drawable, (Drawable) null);
                } else if (immuFlorAbnormal[i] == 1) {
                    Drawable drawable2 = getResources().getDrawable(C0418R.C0419drawable.ico_sjt);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                    this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, drawable2, (Drawable) null);
                }
                i++;
            } else {
                return;
            }
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C0418R.C0420id.btSwitch_Immunofluorescence) {
            showNormalDialog();
        } else if (id == C0418R.C0420id.btTimeSynchronization) {
            this.immunofluorescenceManager.setTime();
        } else if (id != C0418R.C0420id.ivClose) {
            switch (id) {
                case C0418R.C0420id.btHand:
                    this.immunofluorescenceManager.postHand();
                    return;
                case C0418R.C0420id.btImmuFluorSetPatientInfo:
                    initDialog();
                    return;
                case C0418R.C0420id.btImmuFluorSetPrintTitle:
                    printTitleDialog();
                    return;
                case C0418R.C0420id.btMeasurement:
                    this.immunofluorescenceManager.measurement();
                    this.loadingDialog = new LoadingDialog(this);
                    this.loadingDialog.setLoadingText(getString(C0418R.string.being_measured));
                    LoadingDialog loadingDialog2 = this.loadingDialog;
                    loadingDialog2.interceptHome = true;
                    loadingDialog2.interceptBack = true;
                    loadingDialog2.show();
                    this.btMeasurement.setEnabled(false);
                    this.isUnderway = true;
                    this.handler.postDelayed(this.runnable, 30000);
                    return;
                case C0418R.C0420id.btMeasurement02:
                    this.immunofluorescenceManager.measurement02();
                    this.loadingDialog = new LoadingDialog(this);
                    this.loadingDialog.setLoadingText(getString(C0418R.string.being_measured));
                    LoadingDialog loadingDialog3 = this.loadingDialog;
                    loadingDialog3.interceptHome = true;
                    loadingDialog3.interceptBack = true;
                    loadingDialog3.show();
                    this.btMeasurement02.setEnabled(false);
                    this.isUnderway = true;
                    this.handler.postDelayed(this.runnable, 300000);
                    return;
                default:
                    return;
            }
        } else {
            finish();
        }
    }

    private void printTitleDialog() {
        final EditText editText = new EditText(this);
        editText.setFocusable(true);
        editText.setMaxEms(15);
        this.builder = new AlertDialog.Builder(this);
        AlertDialog.Builder builder2 = this.builder;
        this.alert = builder2.setTitle("" + getResources().getString(C0418R.string.input_print_title)).setView(editText).setPositiveButton(getString(C0418R.string.record_save_dialog_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                String obj = editText.getText().toString();
                Log.d("zdc", obj);
                if (obj.equals("")) {
                    ImmunofluorescenceMainActivity immunofluorescenceMainActivity = ImmunofluorescenceMainActivity.this;
                    Toast.makeText(immunofluorescenceMainActivity, immunofluorescenceMainActivity.getResources().getString(C0418R.string.print_title_not_empty), 1).show();
                } else if (ImmunofluorescenceMainActivity.this.getStrLength(obj) >= 31) {
                    ImmunofluorescenceMainActivity immunofluorescenceMainActivity2 = ImmunofluorescenceMainActivity.this;
                    Toast.makeText(immunofluorescenceMainActivity2, immunofluorescenceMainActivity2.getResources().getString(C0418R.string.print_title_not_length), 1).show();
                } else {
                    ImmunofluorescenceMainActivity.this.immunofluorescenceManager.setTitle(obj);
                }
            }
        }).setNegativeButton(getString(C0418R.string.record_save_dialog_cancel), (DialogInterface.OnClickListener) null).create();
        this.alert.show();
    }

    private void initDialog() {
        final CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
            public void onPositiveClick(String str, String str2, String str3, String str4, String str5) {
                ImmunofluorescenceMainActivity.this.immunofluorescenceManager.setPatientInfo(str, str2, str3, str4);
                ImmunofluorescenceMainActivity.this.saveSettingData(str2, str4, str5);
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
                    com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
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
                    com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                    java.lang.String r5 = "姓名不能为空"
                    android.widget.Toast r3 = android.widget.Toast.makeText(r3, r5, r4)
                    r3.show()
                L_0x006a:
                    r3 = 1
                    goto L_0x0082
                L_0x006c:
                    com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r5 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                    int r5 = r5.getStrLength(r9)
                    r6 = 8
                    if (r5 <= r6) goto L_0x0082
                    com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                    java.lang.String r5 = "姓名长度不得超过8个字符或4个中文汉字"
                    android.widget.Toast r3 = android.widget.Toast.makeText(r3, r5, r4)
                    r3.show()
                    goto L_0x006a
                L_0x0082:
                    boolean r5 = r0.equals(r2)
                    if (r5 == 0) goto L_0x0095
                    com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
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
                    com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                    java.lang.String r5 = "年龄长度不得超过999"
                    android.widget.Toast r3 = android.widget.Toast.makeText(r3, r5, r4)
                    r3.show()
                    goto L_0x0093
                L_0x00a8:
                    boolean r2 = r1.equals(r2)
                    if (r2 == 0) goto L_0x00ba
                    com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r2 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
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
                    com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r2 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                    java.lang.String r3 = "性别只能为0或1"
                    android.widget.Toast r2 = android.widget.Toast.makeText(r2, r3, r4)
                    r2.show()
                    goto L_0x00d7
                L_0x00d6:
                    r4 = r3
                L_0x00d7:
                    if (r4 != 0) goto L_0x00e2
                    com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity r2 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.this
                    com.huanghuang.kangshangyiliao.util.ImmunofluorescenceManager r2 = r2.immunofluorescenceManager
                    r2.setPatientInfo(r8, r9, r0, r1)
                L_0x00e2:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.C04467.onClick(android.content.DialogInterface, int):void");
            }
        }).setNegativeButton(getString(C0418R.string.record_save_dialog_cancel), (DialogInterface.OnClickListener) null).create();
        this.alert.show();
    }

    private void showNormalDialog() {
        AskDialog askDialog = new AskDialog(this);
        askDialog.setText((int) C0418R.string.delete_immunofluorescence);
        askDialog.setOnOperateListener(new AskCreateNickNameDialog.OnOperateListener() {
            public void onConfirm() {
                ImmunofluorescenceMainActivity.this.getSharedPreferences("name", 0).edit().putString("Immunofluorescence", "").apply();
                ImmunofluorescenceMainActivity.this.immunofluorescenceManager.disconnect();
                ImmunofluorescenceMainActivity.this.finish();
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
                    ImmunofluorescenceMainActivity.this.finish();
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

    public void showPrintInfoDialog() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setView(LayoutInflater.from(getBaseContext()).inflate(C0418R.layout.dialog_setpatientinfo, (ViewGroup) null, false));
        builder2.show();
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
