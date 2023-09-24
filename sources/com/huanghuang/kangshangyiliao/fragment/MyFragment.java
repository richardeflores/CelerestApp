package com.huanghuang.kangshangyiliao.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.p000v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.activity.AboutActivity;
import com.huanghuang.kangshangyiliao.activity.AboutAppActivity;
import com.huanghuang.kangshangyiliao.activity.ClinicActivity;
import com.huanghuang.kangshangyiliao.activity.ClinicActivityInfo;
import com.huanghuang.kangshangyiliao.activity.IntroductionActivity;
import com.huanghuang.kangshangyiliao.activity.UserInfoActivity;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.base.BaseFragment;
import com.huanghuang.kangshangyiliao.dao.ClinicNameDao;
import com.huanghuang.kangshangyiliao.dao.DoctorAndPatientDao;
import com.huanghuang.kangshangyiliao.dao.NickNameDao;
import com.huanghuang.kangshangyiliao.dao.POCTDao;
import com.huanghuang.kangshangyiliao.dao.ProteinDao;
import com.huanghuang.kangshangyiliao.dao.UrinalysisDao;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import com.huanghuang.kangshangyiliao.dao.bean.DoctorAndPatientBean;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.util.POCTManager;
import com.huanghuang.kangshangyiliao.util.Session;
import com.huanghuang.kangshangyiliao.util.UrinalysisManager;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import com.huanghuang.kangshangyiliao.widget.AskCreateNickNameDialog;
import com.huanghuang.kangshangyiliao.widget.AskDialog;
import com.huanghuang.kangshangyiliao.widget.ClinicNameListDialog;
import com.huanghuang.kangshangyiliao.widget.ClinicNameOperateDialog;
import java.util.List;

public class MyFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CREATE_NICK_NAME = 1001;
    private AppBase appBase = AppBase.getInstance();
    /* access modifiers changed from: private */
    public ClinicNameDao clinic_dao;
    private NickNameDao dao = new NickNameDao();
    private DoctorAndPatientDao doctorAndPatientDao = new DoctorAndPatientDao();
    private LocalBroadcastManager lbm;
    /* access modifiers changed from: private */
    public POCTDao poctDao = new POCTDao();
    /* access modifiers changed from: private */
    public ProteinDao proteinDao = new ProteinDao();
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r1, android.content.Intent r2) {
            /*
                r0 = this;
                java.lang.String r1 = r2.getAction()
                int r2 = r1.hashCode()
                switch(r2) {
                    case -1836855365: goto L_0x005d;
                    case -767539241: goto L_0x0053;
                    case -606267464: goto L_0x0049;
                    case 155122889: goto L_0x003e;
                    case 414754657: goto L_0x0034;
                    case 630737538: goto L_0x002a;
                    case 837216314: goto L_0x0020;
                    case 1694932361: goto L_0x0016;
                    case 2089049811: goto L_0x000c;
                    default: goto L_0x000b;
                }
            L_0x000b:
                goto L_0x0067
            L_0x000c:
                java.lang.String r2 = "bloodFat_connect_fail"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x0067
                r1 = 5
                goto L_0x0068
            L_0x0016:
                java.lang.String r2 = "bloodFat_connected"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x0067
                r1 = 4
                goto L_0x0068
            L_0x0020:
                java.lang.String r2 = "poct_connected"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x0067
                r1 = 2
                goto L_0x0068
            L_0x002a:
                java.lang.String r2 = "poct_connect_fail"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x0067
                r1 = 3
                goto L_0x0068
            L_0x0034:
                java.lang.String r2 = "urinalysis_connect_fail"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x0067
                r1 = 1
                goto L_0x0068
            L_0x003e:
                java.lang.String r2 = "bloodFat_notify"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x0067
                r1 = 8
                goto L_0x0068
            L_0x0049:
                java.lang.String r2 = "poct_notify"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x0067
                r1 = 6
                goto L_0x0068
            L_0x0053:
                java.lang.String r2 = "urinalysis_notify"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x0067
                r1 = 7
                goto L_0x0068
            L_0x005d:
                java.lang.String r2 = "urinalysis_connected"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x0067
                r1 = 0
                goto L_0x0068
            L_0x0067:
                r1 = -1
            L_0x0068:
                switch(r1) {
                    case 0: goto L_0x007a;
                    case 1: goto L_0x007a;
                    case 2: goto L_0x007a;
                    case 3: goto L_0x007a;
                    case 4: goto L_0x007a;
                    case 5: goto L_0x007a;
                    case 6: goto L_0x006c;
                    case 7: goto L_0x006c;
                    case 8: goto L_0x006c;
                    default: goto L_0x006b;
                }
            L_0x006b:
                goto L_0x007f
            L_0x006c:
                com.huanghuang.kangshangyiliao.fragment.MyFragment r1 = com.huanghuang.kangshangyiliao.fragment.MyFragment.this
                android.widget.TextView r1 = r1.tvClinicName
                java.lang.String r2 = com.huanghuang.kangshangyiliao.util.Utils.getClinicName()
                r1.setText(r2)
                goto L_0x007f
            L_0x007a:
                com.huanghuang.kangshangyiliao.fragment.MyFragment r1 = com.huanghuang.kangshangyiliao.fragment.MyFragment.this
                r1.showDeviceName()
            L_0x007f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.fragment.MyFragment.C05411.onReceive(android.content.Context, android.content.Intent):void");
        }
    };
    private View rootView;
    private Session session = Session.getInstance();
    /* access modifiers changed from: private */
    @ViewBind.Bind(mo7926id = 2131231016)
    public TextView tvClinicName;
    @ViewBind.Bind(mo7926id = 2131231019)
    private TextView tvDate;
    @ViewBind.Bind(mo7926id = 2131231057)
    private TextView tvPOCTDeviceName;
    @ViewBind.Bind(mo7926id = 2131231072)
    private TextView tvUrinalysDeviceName;
    /* access modifiers changed from: private */
    public UrinalysisDao urinalysisDao = new UrinalysisDao();

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.clinic_dao = new ClinicNameDao();
        this.lbm = LocalBroadcastManager.getInstance(this.appBase.getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UrinalysisManager.CONNECTED);
        intentFilter.addAction(UrinalysisManager.CONNECTED_FAIL);
        intentFilter.addAction(UrinalysisManager.DISCONNECTED);
        intentFilter.addAction(POCTManager.CONNECTED);
        intentFilter.addAction(POCTManager.CONNECTED_FAIL);
        intentFilter.addAction(POCTManager.DISCONNECTED);
        intentFilter.addAction(POCTManager.NOTIFY);
        intentFilter.addAction(UrinalysisManager.NOTIFY);
        this.lbm.registerReceiver(this.receiver, intentFilter);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.fragment_my, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        this.tvClinicName.setText(Utils.getClinicName());
        this.tvDate.setText(Utils.getDate("yyyy年MM月dd日"));
        onHiddenChanged(false);
        return this.rootView;
    }

    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (!z) {
            showDeviceName();
        }
    }

    /* access modifiers changed from: private */
    public void showDeviceName() {
        String pOCTCurrentDevice = this.session.getPOCTCurrentDevice();
        String urinalysCurrentDevice = this.session.getUrinalysCurrentDevice();
        if (TextUtils.isEmpty(pOCTCurrentDevice)) {
            this.tvPOCTDeviceName.setText(C0418R.string.not_connected);
        } else {
            this.tvPOCTDeviceName.setText(pOCTCurrentDevice);
        }
        if (TextUtils.isEmpty(urinalysCurrentDevice)) {
            this.tvUrinalysDeviceName.setText(C0418R.string.not_connected);
        } else {
            this.tvUrinalysDeviceName.setText(urinalysCurrentDevice);
        }
        if (this.clinic_dao.queryAll().size() == 0) {
            this.tvClinicName.setText("");
            Utils.saveClinicToCache((ClinicName) null);
            ClinicName clinicInfo = Utils.getClinicInfo();
            if (clinicInfo != null) {
                String str = clinicInfo.clinicName;
                this.urinalysisDao.delete(str);
                this.poctDao.delete(str);
                this.proteinDao.delete(str);
                this.clinic_dao.delete(str);
                return;
            }
            return;
        }
        this.tvClinicName.setText(Utils.getClinicName());
    }

    public void onClick(View view) {
        String language = getResources().getConfiguration().locale.getLanguage();
        switch (view.getId()) {
            case C0418R.C0420id.ImageView_My_TouXiang:
                String charSequence = this.tvClinicName.getText().toString();
                if (TextUtils.isEmpty(charSequence)) {
                    startActivityForResult(new Intent(this.context, ClinicActivity.class), 1001);
                    return;
                }
                Intent intent = new Intent(this.context, ClinicActivityInfo.class);
                intent.putExtra("currentClinicName", charSequence);
                startActivityForResult(intent, 1001);
                return;
            case C0418R.C0420id.ivNickNameOperate:
                ClinicNameOperateDialog clinicNameOperateDialog = new ClinicNameOperateDialog(this.context);
                clinicNameOperateDialog.setOnOperateListener(new ClinicNameOperateDialog.OnOperateListener() {
                    public void onChange() {
                        final List<ClinicName> queryAll = MyFragment.this.clinic_dao.queryAll();
                        ClinicNameListDialog clinicNameListDialog = new ClinicNameListDialog(MyFragment.this.context, queryAll);
                        clinicNameListDialog.setText(MyFragment.this.getString(C0418R.string.change_user));
                        clinicNameListDialog.setOnClinicNameChoose(new ClinicNameListDialog.OnClinicNameChoose() {
                            public void onChoose(AdapterView<?> adapterView, View view, int i, long j) {
                                ClinicName clinicName = (ClinicName) queryAll.get(i);
                                MyFragment.this.tvClinicName.setText(clinicName.clinicName);
                                Utils.saveClinicToCache(clinicName);
                                MyFragment.this.initMethod(clinicName.clinicName);
                            }
                        });
                        clinicNameListDialog.show();
                    }

                    public void onDelete() {
                        final ClinicName clinicInfo = Utils.getClinicInfo();
                        if (clinicInfo != null) {
                            AskDialog askDialog = new AskDialog(MyFragment.this.context);
                            askDialog.setText((int) C0418R.string.delete_user_tip);
                            askDialog.setOnOperateListener(new AskCreateNickNameDialog.OnOperateListener() {
                                public void onConfirm() {
                                    String str = clinicInfo.clinicName;
                                    MyFragment.this.urinalysisDao.delete(str);
                                    MyFragment.this.poctDao.delete(str);
                                    MyFragment.this.proteinDao.delete(str);
                                    MyFragment.this.clinic_dao.delete(str);
                                    List<ClinicName> queryAll = MyFragment.this.clinic_dao.queryAll();
                                    if (queryAll.size() > 0) {
                                        Utils.saveClinicToCache(queryAll.get(0));
                                        MyFragment.this.tvClinicName.setText(queryAll.get(0).clinicName);
                                        return;
                                    }
                                    Utils.saveUserToCache((NickName) null);
                                    MyFragment.this.tvClinicName.setText("");
                                }
                            });
                            askDialog.show();
                        }
                    }
                });
                clinicNameOperateDialog.show();
                return;
            case C0418R.C0420id.llCreateClinicName:
                startActivityForResult(new Intent(this.context, ClinicActivity.class), 1001);
                return;
            case C0418R.C0420id.llCreateNickName:
                String charSequence2 = this.tvClinicName.getText().toString();
                if (TextUtils.isEmpty(charSequence2)) {
                    Context context = getContext();
                    Toast.makeText(context, "" + getString(C0418R.string.create_clinic), 0).show();
                    return;
                }
                Intent intent2 = new Intent(this.context, UserInfoActivity.class);
                if (!TextUtils.isEmpty(charSequence2)) {
                    intent2.putExtra("currentClinicName", charSequence2);
                    intent2.putExtra("enable", true);
                }
                startActivityForResult(intent2, 1001);
                return;
            case C0418R.C0420id.llNYFX:
                Intent intent3 = new Intent(this.context, IntroductionActivity.class);
                intent3.putExtra("index", 0);
                startActivity(intent3);
                return;
            case C0418R.C0420id.llXYFX:
                if (language.equals("zh")) {
                    Intent intent4 = new Intent(this.context, IntroductionActivity.class);
                    intent4.putExtra("index", 1);
                    startActivity(intent4);
                    return;
                } else if (!language.equals("en") && !language.equals("fr") && !language.equals("de") && !language.equals("it") && !language.equals("ja")) {
                    boolean equals = language.equals("ko");
                    return;
                } else {
                    return;
                }
            case C0418R.C0420id.rlAboutAPP:
                startActivity(new Intent(this.context, AboutAppActivity.class));
                return;
            case C0418R.C0420id.rlAboutUs:
                startActivity(new Intent(this.context, AboutActivity.class));
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void initMethod(String str) {
        List<DoctorAndPatientBean> queryAll = this.doctorAndPatientDao.queryAll();
        if (queryAll != null && queryAll.size() != 0) {
            for (int i = 0; i < queryAll.size(); i++) {
                if (queryAll.get(i).clinicName.equals(str)) {
                    Utils.saveUserToCache(this.dao.query(queryAll.get(i).nickName));
                }
            }
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1001 && i2 == 1) {
            this.tvClinicName.setText(Utils.getClinicName());
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.lbm.unregisterReceiver(this.receiver);
    }
}
