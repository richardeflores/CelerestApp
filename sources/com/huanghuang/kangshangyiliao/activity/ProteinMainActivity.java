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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.zxing.common.StringUtils;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.bean.ProteinResult;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.dao.bean.Protein;
import com.huanghuang.kangshangyiliao.dayiji.BluetoothPrintService;
import com.huanghuang.kangshangyiliao.util.Age;
import com.huanghuang.kangshangyiliao.util.ProteinManager;
import com.huanghuang.kangshangyiliao.util.Session;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import com.huanghuang.kangshangyiliao.widget.AskCreateNickNameDialog;
import com.huanghuang.kangshangyiliao.widget.AskDialog;
import com.huanghuang.kangshangyiliao.widget.LoadingDialog;
import com.huanghuang.kangshangyiliao.widget.TipDialog;
import com.huanghuang.kangshangyiliao.widget.TipToastDialog;
import java.io.IOException;

public class ProteinMainActivity extends BaseActivity implements View.OnClickListener {
    public static String EXTRA_DEVICE_ADDRESS_a = "device_address";
    private String Printer_ID;
    private Age age = new Age();
    private String age_phase;
    private AppBase appBase = AppBase.getInstance();
    private BroadcastReceiver bluetoothStateListener = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1)) {
                case 10:
                    ProteinMainActivity.this.onDeviceStateOff();
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
    public byte[] data;
    @ViewBind.Bind(mo7926id = 2131230846)
    private EditText etSend;
    @ViewBind.Bind(mo7926id = 2131231028)
    private TextView getTvHCT_Scope;
    @ViewBind.Bind(mo7926id = 2131231031)
    private TextView getTvHGB_Scope;
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
    private NickName nickName;
    /* access modifiers changed from: private */
    public Protein protein;
    /* access modifiers changed from: private */
    public ProteinManager proteinManager = ProteinManager.getInstance();
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
                    case -622348460: goto L_0x003f;
                    case 206361477: goto L_0x0035;
                    case 729305436: goto L_0x002b;
                    case 902814635: goto L_0x0021;
                    case 903179785: goto L_0x0017;
                    case 1196778948: goto L_0x000d;
                    default: goto L_0x000c;
                }
            L_0x000c:
                goto L_0x0053
            L_0x000d:
                java.lang.String r0 = "Observed_time"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0053
                r3 = 3
                goto L_0x0054
            L_0x0017:
                java.lang.String r0 = "protein_time"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0053
                r3 = 2
                goto L_0x0054
            L_0x0021:
                java.lang.String r0 = "protein_hand"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0053
                r3 = 1
                goto L_0x0054
            L_0x002b:
                java.lang.String r0 = "protein_device_available"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0053
                r3 = 4
                goto L_0x0054
            L_0x0035:
                java.lang.String r0 = "protein_notify"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0053
                r3 = 0
                goto L_0x0054
            L_0x003f:
                java.lang.String r0 = "protein_device_state_off"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0053
                r3 = 6
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
                    case 0: goto L_0x010c;
                    case 1: goto L_0x00dc;
                    case 2: goto L_0x00ca;
                    case 3: goto L_0x00b8;
                    case 4: goto L_0x0090;
                    case 5: goto L_0x0060;
                    case 6: goto L_0x0059;
                    default: goto L_0x0057;
                }
            L_0x0057:
                goto L_0x0174
            L_0x0059:
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                r3.onDeviceStateOff()
                goto L_0x0174
            L_0x0060:
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x0071
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.dismiss()
            L_0x0071:
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                boolean unused = r3.isUnderway = r1
                com.huanghuang.kangshangyiliao.widget.TipDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipDialog
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r4 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                r3.<init>(r4)
                r4 = 2131558528(0x7f0d0080, float:1.8742374E38)
                r3.setText((int) r4)
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity$2$2 r4 = new com.huanghuang.kangshangyiliao.activity.ProteinMainActivity$2$2
                r4.<init>()
                r3.setOnOperateListener(r4)
                r3.show()
                goto L_0x0174
            L_0x0090:
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x00a1
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.dismiss()
            L_0x00a1:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r4 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                r3.<init>(r4)
                r4 = 2131558523(0x7f0d007b, float:1.8742364E38)
                r3.setText((int) r4)
                r3.show()
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                r3.checkDeviceType()
                goto L_0x0174
            L_0x00b8:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r4 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                r3.<init>(r4)
                r4 = 2131558696(0x7f0d0128, float:1.8742715E38)
                r3.setText((int) r4)
                r3.show()
                goto L_0x0174
            L_0x00ca:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r4 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                r3.<init>(r4)
                r4 = 2131558760(0x7f0d0168, float:1.8742845E38)
                r3.setText((int) r4)
                r3.show()
                goto L_0x0174
            L_0x00dc:
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                boolean r3 = r3.isCheckDeviceType
                if (r3 == 0) goto L_0x00fb
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                boolean unused = r3.isCheckDeviceType = r1
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x00fa
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.dismiss()
            L_0x00fa:
                return
            L_0x00fb:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r4 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                r3.<init>(r4)
                r4 = 2131558548(0x7f0d0094, float:1.8742415E38)
                r3.setText((int) r4)
                r3.show()
                goto L_0x0174
            L_0x010c:
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                boolean unused = r3.isUnderway = r1
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                android.os.Handler r3 = r3.handler
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r0 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                java.lang.Runnable r0 = r0.runnable
                r3.removeCallbacks(r0)
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                if (r3 == 0) goto L_0x0131
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r3 = r3.loadingDialog
                r3.dismiss()
            L_0x0131:
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                java.lang.String r0 = "protein"
                java.io.Serializable r4 = r4.getSerializableExtra(r0)
                com.huanghuang.kangshangyiliao.dao.bean.Protein r4 = (com.huanghuang.kangshangyiliao.dao.bean.Protein) r4
                com.huanghuang.kangshangyiliao.dao.bean.Protein unused = r3.protein = r4
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                com.huanghuang.kangshangyiliao.dao.bean.Protein r3 = r3.protein
                if (r3 == 0) goto L_0x015a
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                com.huanghuang.kangshangyiliao.dao.bean.Protein r4 = r3.protein
                java.lang.String r4 = r4.data
                byte[] r4 = com.huanghuang.kangshangyiliao.util.Utils.toByteArray(r4)
                byte[] unused = r3.data = r4
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r3 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                r3.showResult()
            L_0x015a:
                com.huanghuang.kangshangyiliao.widget.TipToastDialog r3 = new com.huanghuang.kangshangyiliao.widget.TipToastDialog
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity r4 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.this
                r3.<init>(r4)
                r4 = 2131558658(0x7f0d0102, float:1.8742638E38)
                r3.setText((int) r4)
                r3.show()
                com.huanghuang.kangshangyiliao.activity.ProteinMainActivity$2$1 r3 = new com.huanghuang.kangshangyiliao.activity.ProteinMainActivity$2$1
                r3.<init>()
                r0 = 1000(0x3e8, double:4.94E-321)
                com.huanghuang.kangshangyiliao.util.Utils.delayTask(r3, r0)
            L_0x0174:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.C04682.onReceive(android.content.Context, android.content.Intent):void");
        }
    };
    /* access modifiers changed from: private */
    public Runnable runnable = new Runnable() {
        public void run() {
            if (ProteinMainActivity.this.isUnderway) {
                if (ProteinMainActivity.this.loadingDialog != null) {
                    ProteinMainActivity.this.loadingDialog.dismiss();
                }
                TipToastDialog tipToastDialog = new TipToastDialog(ProteinMainActivity.this);
                tipToastDialog.setImg(C0418R.C0419drawable.ico_tstb);
                tipToastDialog.setText((int) C0418R.string.measurement_timeout);
                tipToastDialog.show();
            }
        }
    };
    @ViewBind.Bind(mo7926id = 2131231027)
    private TextView tvHCT;
    @ViewBind.Bind(mo7926id = 2131231030)
    private TextView tvHGB;
    @ViewBind.Bind(mo7926id = 2131231068)
    private TextView tvTitle;
    @ViewBind.Bind(mo7926id = 2131231079)
    private TextView tv_device_name;
    private TextView[] tvs;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_protein_operate);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_protein_operate));
        ViewBind.bind((Activity) this);
        this.nickName = Utils.getUserInfo();
        if (this.nickName.birthday != null) {
            this.age_phase = Age.age_phase(this.nickName.birthday);
            if ("0".equals(this.age_phase)) {
                this.getTvHGB_Scope.setText(C0418R.string.hgb_newborn_reference_range);
            } else if ("1".equals(this.age_phase)) {
                this.getTvHGB_Scope.setText(C0418R.string.hgb_baby_reference_range);
            } else if ("2".equals(this.age_phase)) {
                this.getTvHGB_Scope.setText(C0418R.string.hgb_children_reference_range);
            } else if ("1".equals(this.nickName.sex)) {
                this.getTvHGB_Scope.setText(C0418R.string.hgb_man_reference_range);
            } else if ("2".equals(this.nickName.sex)) {
                this.getTvHGB_Scope.setText(C0418R.string.hgb_woman_reference_range);
            }
        }
        if ("1".equals(this.nickName.sex)) {
            this.getTvHCT_Scope.setText(C0418R.string.hct_man_reference_range);
        } else if ("2".equals(this.nickName.sex)) {
            this.getTvHCT_Scope.setText(C0418R.string.hct_woman_reference_range);
        }
        this.tvs = new TextView[]{this.tvHGB, this.tvHCT};
        this.lbm = LocalBroadcastManager.getInstance(this.appBase.getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ProteinManager.NOTIFY);
        intentFilter.addAction(ProteinManager.HAND);
        intentFilter.addAction(ProteinManager.TIME);
        intentFilter.addAction(ProteinManager.OBSERVEED);
        intentFilter.addAction(ProteinManager.DEVICE_AVAILABLE);
        intentFilter.addAction("device_unavailable");
        intentFilter.addAction(ProteinManager.DEVICE_STATE_OFF);
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
        TextView textView = this.tv_device_name;
        textView.setText(getString(C0418R.string.current_device_name) + " " + Session.getInstance().getProteinCurrentDevice());
    }

    /* access modifiers changed from: private */
    public void checkDeviceType() {
        this.isCheckDeviceType = true;
        this.loadingDialog = new LoadingDialog(this);
        this.loadingDialog.setLoadingText(getString(C0418R.string.check_device_type));
        this.loadingDialog.show();
        this.proteinManager.postHand();
        Utils.delayTask(new Runnable() {
            public void run() {
                if (ProteinMainActivity.this.isCheckDeviceType) {
                    ProteinMainActivity.this.dismissDialog();
                    TipDialog tipDialog = new TipDialog(ProteinMainActivity.this);
                    tipDialog.setText((int) C0418R.string.device_type_mismatch);
                    tipDialog.setOnOperateListener(new TipDialog.OnOperateListener() {
                        public void onConfirm() {
                            ProteinMainActivity.this.getSharedPreferences("name", 0).edit().putString("Protein", "").apply();
                            ProteinMainActivity.this.proteinManager.disconnect();
                            ProteinMainActivity.this.finish();
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

    /* access modifiers changed from: private */
    public void showResult() {
        clearResult();
        ProteinResult parseProteinData = Utils.parseProteinData(this.data);
        String[] proteinRealValueText = Utils.getProteinRealValueText(parseProteinData);
        if ("1".equals(this.nickName.sex)) {
            prompt(proteinRealValueText, Utils.getProteinAbnormal_MAN(parseProteinData, this.age_phase));
        } else if ("2".equals(this.nickName.sex)) {
            prompt(proteinRealValueText, Utils.getProteinAbnormal_WOMAN(parseProteinData, this.age_phase));
        } else {
            prompt(proteinRealValueText, Utils.getProteinAbnormal_MAN(parseProteinData, this.age_phase));
        }
    }

    private void prompt(String[] strArr, int[] iArr) {
        for (int i = 0; i < strArr.length; i++) {
            if (i == 0) {
                this.tvs[i].setText(Utils.floatPointNoRound(Float.parseFloat(strArr[i]) / 10.0f));
            } else {
                this.tvs[i].setText(strArr[i]);
            }
            if (iArr[i] != 0) {
                ((ViewGroup) this.tvs[i].getParent()).setBackgroundColor(Utils.getAbnormalBackgroundColor());
            }
            if (iArr[i] == -1) {
                Drawable drawable = getResources().getDrawable(C0418R.C0419drawable.ico_xjt);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, drawable, (Drawable) null);
            } else if (iArr[i] == 1) {
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
                this.proteinManager.postHand();
                return;
            case C0418R.C0420id.btMeasurement:
                clearResult();
                this.proteinManager.measurement();
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
            case C0418R.C0420id.btSwitch_Protein:
                showNormalDialog();
                return;
            case C0418R.C0420id.btTimeSynchronization:
                this.proteinManager.setTime();
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
        askDialog.setText((int) C0418R.string.delete_protein);
        askDialog.setOnOperateListener(new AskCreateNickNameDialog.OnOperateListener() {
            public void onConfirm() {
                ProteinMainActivity.this.getSharedPreferences("name", 0).edit().putString("Protein", "").apply();
                ProteinMainActivity.this.proteinManager.disconnect();
                ProteinMainActivity.this.finish();
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
                    ProteinMainActivity.this.finish();
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

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00ee  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void ininBluetooth() {
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
            java.lang.String r0 = r14.Printer_ID
            r14.connectDevice(r0)
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            java.lang.String r1 = EXTRA_DEVICE_ADDRESS_a
            java.lang.String r2 = r14.Printer_ID
            r0.putExtra(r1, r2)
            android.os.Handler r3 = new android.os.Handler
            r3.<init>()
            com.huanghuang.kangshangyiliao.activity.ProteinMainActivity$7 r2 = new com.huanghuang.kangshangyiliao.activity.ProteinMainActivity$7
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
        throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.ininBluetooth():void");
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
