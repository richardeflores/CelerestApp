package com.huanghuang.kangshangyiliao.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.p000v4.content.LocalBroadcastManager;
import android.support.p000v4.view.PointerIconCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity;
import com.huanghuang.kangshangyiliao.activity.ClinicActivity;
import com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity;
import com.huanghuang.kangshangyiliao.activity.POCTMainActivity;
import com.huanghuang.kangshangyiliao.activity.ProteinMainActivity;
import com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity;
import com.huanghuang.kangshangyiliao.activity.UserInfoActivity;
import com.huanghuang.kangshangyiliao.adapter.BluetoothDeviceListAdapter;
import com.huanghuang.kangshangyiliao.adapter.GridViewAdapter;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.base.BaseFragment;
import com.huanghuang.kangshangyiliao.base.BaseManager;
import com.huanghuang.kangshangyiliao.bean.BluetoothDeviceInfo;
import com.huanghuang.kangshangyiliao.bean.GridView_Icon_add;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.dayiji.PrinterActivity;
import com.huanghuang.kangshangyiliao.ioam.IOAM;
import com.huanghuang.kangshangyiliao.ioam.Main;
import com.huanghuang.kangshangyiliao.util.BloodFatManager;
import com.huanghuang.kangshangyiliao.util.BluetoothSelectDialog;
import com.huanghuang.kangshangyiliao.util.Cache;
import com.huanghuang.kangshangyiliao.util.Constants;
import com.huanghuang.kangshangyiliao.util.ImmunofluorescenceManager;
import com.huanghuang.kangshangyiliao.util.POCTManager;
import com.huanghuang.kangshangyiliao.util.PrinterManager;
import com.huanghuang.kangshangyiliao.util.ProteinManager;
import com.huanghuang.kangshangyiliao.util.Session;
import com.huanghuang.kangshangyiliao.util.UrinalysisManager;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import com.huanghuang.kangshangyiliao.widget.AskCreateNickNameDialog;
import com.huanghuang.kangshangyiliao.widget.BluetoothDeviceListDialog;
import com.huanghuang.kangshangyiliao.widget.LoadingDialog;
import com.huanghuang.kangshangyiliao.widget.TipDialog;
import com.huanghuang.kangshangyiliao.widget.TipToast;
import com.huanghuang.kangshangyiliao.widget.TipToastDialog;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyzeFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final int REQUEST_CREATE_NICK_NAME = 1001;
    private static final int REQUEST_ENABLE = 1001;
    private BaseAdapter GridViewAdapter;
    private List<GridView_Icon_add> GridViewDate;
    private AppBase appBase = AppBase.getInstance();
    private BaseManager[] baseManager;
    private BloodFatManager bloodFatManager = BloodFatManager.getInstance();
    private BluetoothLeScanner bluetoothLeAdvertiser;
    private BluetoothManager bluetoothManager;
    private Cache cache = Cache.getInstance();
    /* access modifiers changed from: private */
    public int currentChoose;
    /* access modifiers changed from: private */
    public Map<String, BluetoothDeviceInfo> devices = new HashMap();
    @ViewBind.Bind(mo7926id = 2131230864)
    private GridView gridView;
    private ImmunofluorescenceManager immunofluorescenceManager = ImmunofluorescenceManager.getInstance();
    /* access modifiers changed from: private */
    public boolean isScan = false;
    private LocalBroadcastManager lbm;
    BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
            boolean unused = AnalyzeFragment.this.isScan = true;
            AnalyzeFragment.this.devices.put(bluetoothDevice.getAddress(), new BluetoothDeviceInfo(bluetoothDevice, i));
        }
    };
    /* access modifiers changed from: private */
    public LoadingDialog loadingDialog;
    /* access modifiers changed from: private */
    public BluetoothAdapter mAdapter;
    /* access modifiers changed from: private */
    public BluetoothAdapter mAdaptera_device = BluetoothAdapter.getDefaultAdapter();
    private POCTManager poctManager = POCTManager.getInstance();
    private PrinterManager printerManager = PrinterManager.getInstance();
    private ProteinManager proteinManager = ProteinManager.getInstance();
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r6, android.content.Intent r7) {
            /*
                r5 = this;
                java.lang.String r7 = r7.getAction()
                int r0 = r7.hashCode()
                r1 = 1
                switch(r0) {
                    case -1871890289: goto L_0x0082;
                    case -1836855365: goto L_0x0078;
                    case -1801921244: goto L_0x006d;
                    case -864649907: goto L_0x0063;
                    case 243378117: goto L_0x0058;
                    case 414754657: goto L_0x004e;
                    case 567302935: goto L_0x0043;
                    case 630737538: goto L_0x0039;
                    case 837216314: goto L_0x002f;
                    case 1694932361: goto L_0x0025;
                    case 1755067736: goto L_0x0019;
                    case 2089049811: goto L_0x000e;
                    default: goto L_0x000c;
                }
            L_0x000c:
                goto L_0x008c
            L_0x000e:
                java.lang.String r0 = "bloodFat_connect_fail"
                boolean r7 = r7.equals(r0)
                if (r7 == 0) goto L_0x008c
                r7 = 7
                goto L_0x008d
            L_0x0019:
                java.lang.String r0 = "printer_connect_fail"
                boolean r7 = r7.equals(r0)
                if (r7 == 0) goto L_0x008c
                r7 = 11
                goto L_0x008d
            L_0x0025:
                java.lang.String r0 = "bloodFat_connected"
                boolean r7 = r7.equals(r0)
                if (r7 == 0) goto L_0x008c
                r7 = 6
                goto L_0x008d
            L_0x002f:
                java.lang.String r0 = "poct_connected"
                boolean r7 = r7.equals(r0)
                if (r7 == 0) goto L_0x008c
                r7 = 2
                goto L_0x008d
            L_0x0039:
                java.lang.String r0 = "poct_connect_fail"
                boolean r7 = r7.equals(r0)
                if (r7 == 0) goto L_0x008c
                r7 = 3
                goto L_0x008d
            L_0x0043:
                java.lang.String r0 = "immunofluorescence_connect_fail"
                boolean r7 = r7.equals(r0)
                if (r7 == 0) goto L_0x008c
                r7 = 9
                goto L_0x008d
            L_0x004e:
                java.lang.String r0 = "urinalysis_connect_fail"
                boolean r7 = r7.equals(r0)
                if (r7 == 0) goto L_0x008c
                r7 = 1
                goto L_0x008d
            L_0x0058:
                java.lang.String r0 = "immunofluorescence_connected"
                boolean r7 = r7.equals(r0)
                if (r7 == 0) goto L_0x008c
                r7 = 8
                goto L_0x008d
            L_0x0063:
                java.lang.String r0 = "protein_connected"
                boolean r7 = r7.equals(r0)
                if (r7 == 0) goto L_0x008c
                r7 = 4
                goto L_0x008d
            L_0x006d:
                java.lang.String r0 = "printer_connected"
                boolean r7 = r7.equals(r0)
                if (r7 == 0) goto L_0x008c
                r7 = 10
                goto L_0x008d
            L_0x0078:
                java.lang.String r0 = "urinalysis_connected"
                boolean r7 = r7.equals(r0)
                if (r7 == 0) goto L_0x008c
                r7 = 0
                goto L_0x008d
            L_0x0082:
                java.lang.String r0 = "protein_connect_fail"
                boolean r7 = r7.equals(r0)
                if (r7 == 0) goto L_0x008c
                r7 = 5
                goto L_0x008d
            L_0x008c:
                r7 = -1
            L_0x008d:
                java.lang.String r0 = "currentClinicName"
                java.lang.String r2 = "isNeedCheckDevice"
                switch(r7) {
                    case 0: goto L_0x0179;
                    case 1: goto L_0x0173;
                    case 2: goto L_0x0141;
                    case 3: goto L_0x013b;
                    case 4: goto L_0x011a;
                    case 5: goto L_0x0113;
                    case 6: goto L_0x00e0;
                    case 7: goto L_0x00d9;
                    case 8: goto L_0x00b7;
                    case 9: goto L_0x00b0;
                    case 10: goto L_0x009d;
                    case 11: goto L_0x0096;
                    default: goto L_0x0094;
                }
            L_0x0094:
                goto L_0x0199
            L_0x0096:
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r6 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                r6.connectedFail()
                goto L_0x0199
            L_0x009d:
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r6 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r6 = r6.loadingDialog
                if (r6 == 0) goto L_0x0199
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r6 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r6 = r6.loadingDialog
                r6.dismiss()
                goto L_0x0199
            L_0x00b0:
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r6 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                r6.connectedFail()
                goto L_0x0199
            L_0x00b7:
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r7 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r7 = r7.loadingDialog
                if (r7 == 0) goto L_0x00c8
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r7 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r7 = r7.loadingDialog
                r7.dismiss()
            L_0x00c8:
                android.content.Intent r7 = new android.content.Intent
                java.lang.Class<com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity> r0 = com.huanghuang.kangshangyiliao.activity.ImmunofluorescenceMainActivity.class
                r7.<init>(r6, r0)
                r7.putExtra(r2, r1)
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r6 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                r6.startActivity(r7)
                goto L_0x0199
            L_0x00d9:
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r6 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                r6.connectedFail()
                goto L_0x0199
            L_0x00e0:
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r7 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r7 = r7.loadingDialog
                if (r7 == 0) goto L_0x00f1
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r7 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r7 = r7.loadingDialog
                r7.dismiss()
            L_0x00f1:
                com.huanghuang.kangshangyiliao.dao.bean.ClinicName r7 = com.huanghuang.kangshangyiliao.util.Utils.getClinicInfo()
                android.content.Intent r3 = new android.content.Intent
                java.lang.Class<com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity> r4 = com.huanghuang.kangshangyiliao.activity.BloodFatMainActivity.class
                r3.<init>(r6, r4)
                r3.putExtra(r2, r1)
                java.lang.String r6 = r7.clinicName
                boolean r6 = android.text.TextUtils.isEmpty(r6)
                if (r6 != 0) goto L_0x010c
                java.lang.String r6 = r7.clinicName
                r3.putExtra(r0, r6)
            L_0x010c:
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r6 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                r6.startActivity(r3)
                goto L_0x0199
            L_0x0113:
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r6 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                r6.connectedFail()
                goto L_0x0199
            L_0x011a:
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r7 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r7 = r7.loadingDialog
                if (r7 == 0) goto L_0x012b
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r7 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r7 = r7.loadingDialog
                r7.dismiss()
            L_0x012b:
                android.content.Intent r7 = new android.content.Intent
                java.lang.Class<com.huanghuang.kangshangyiliao.activity.ProteinMainActivity> r0 = com.huanghuang.kangshangyiliao.activity.ProteinMainActivity.class
                r7.<init>(r6, r0)
                r7.putExtra(r2, r1)
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r6 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                r6.startActivity(r7)
                goto L_0x0199
            L_0x013b:
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r6 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                r6.connectedFail()
                goto L_0x0199
            L_0x0141:
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r7 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r7 = r7.loadingDialog
                if (r7 == 0) goto L_0x0152
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r7 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r7 = r7.loadingDialog
                r7.dismiss()
            L_0x0152:
                com.huanghuang.kangshangyiliao.dao.bean.ClinicName r7 = com.huanghuang.kangshangyiliao.util.Utils.getClinicInfo()
                android.content.Intent r3 = new android.content.Intent
                java.lang.Class<com.huanghuang.kangshangyiliao.activity.POCTMainActivity> r4 = com.huanghuang.kangshangyiliao.activity.POCTMainActivity.class
                r3.<init>(r6, r4)
                r3.putExtra(r2, r1)
                java.lang.String r6 = r7.clinicName
                boolean r6 = android.text.TextUtils.isEmpty(r6)
                if (r6 != 0) goto L_0x016d
                java.lang.String r6 = r7.clinicName
                r3.putExtra(r0, r6)
            L_0x016d:
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r6 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                r6.startActivity(r3)
                goto L_0x0199
            L_0x0173:
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r6 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                r6.connectedFail()
                goto L_0x0199
            L_0x0179:
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r7 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r7 = r7.loadingDialog
                if (r7 == 0) goto L_0x018a
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r7 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                com.huanghuang.kangshangyiliao.widget.LoadingDialog r7 = r7.loadingDialog
                r7.dismiss()
            L_0x018a:
                android.content.Intent r7 = new android.content.Intent
                java.lang.Class<com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity> r0 = com.huanghuang.kangshangyiliao.activity.UrinalysisMainActivity.class
                r7.<init>(r6, r0)
                r7.putExtra(r2, r1)
                com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment r6 = com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.this
                r6.startActivity(r7)
            L_0x0199:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.fragment.AnalyzeFragment.C05121.onReceive(android.content.Context, android.content.Intent):void");
        }
    };
    private View rootView;
    ScanCallback scanCallback = new ScanCallback() {
        public void onScanResult(int i, ScanResult scanResult) {
            super.onScanResult(i, scanResult);
            boolean unused = AnalyzeFragment.this.isScan = true;
            AnalyzeFragment.this.devices.put(scanResult.getDevice().getAddress(), new BluetoothDeviceInfo(scanResult.getDevice(), i));
        }
    };
    private Session session = Session.getInstance();
    private UrinalysisManager urinalysisManager = UrinalysisManager.getInstance();

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.bluetoothManager = (BluetoothManager) this.appBase.getContext().getSystemService("bluetooth");
        this.mAdapter = this.bluetoothManager.getAdapter();
        this.lbm = LocalBroadcastManager.getInstance(this.appBase.getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UrinalysisManager.CONNECTED);
        intentFilter.addAction(UrinalysisManager.CONNECTED_FAIL);
        intentFilter.addAction(UrinalysisManager.DISCONNECTED);
        intentFilter.addAction(POCTManager.CONNECTED);
        intentFilter.addAction(POCTManager.CONNECTED_FAIL);
        intentFilter.addAction(POCTManager.DISCONNECTED);
        intentFilter.addAction(ProteinManager.CONNECTED);
        intentFilter.addAction(ProteinManager.CONNECTED_FAIL);
        intentFilter.addAction(ProteinManager.DISCONNECTED);
        intentFilter.addAction(BloodFatManager.CONNECTED);
        intentFilter.addAction(BloodFatManager.CONNECTED_FAIL);
        intentFilter.addAction(BloodFatManager.DISCONNECTED);
        intentFilter.addAction(ImmunofluorescenceManager.CONNECTED);
        intentFilter.addAction(ImmunofluorescenceManager.CONNECTED_FAIL);
        intentFilter.addAction(ImmunofluorescenceManager.DISCONNECTED);
        this.lbm.registerReceiver(this.receiver, intentFilter);
        this.baseManager = new BaseManager[]{this.urinalysisManager, this.poctManager, this.proteinManager, this.bloodFatManager, this.immunofluorescenceManager, this.printerManager};
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.fragment_analyze, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        initGridView(this.rootView);
        return this.rootView;
    }

    private void initGridView(View view) {
        this.GridViewDate = new ArrayList();
        this.GridViewDate.add(new GridView_Icon_add(C0418R.C0419drawable.ico_surveyors, getString(C0418R.string.ico_detector_info)));
        this.GridViewDate.add(new GridView_Icon_add(C0418R.C0419drawable.ico_routine_urine, getString(C0418R.string.ico_ncg)));
        this.GridViewDate.add(new GridView_Icon_add(C0418R.C0419drawable.ico_blood_cell_analysis, getString(C0418R.string.ico_wbc)));
        this.GridViewDate.add(new GridView_Icon_add(C0418R.C0419drawable.ico_specific_protein, getString(C0418R.string.ico_hgb)));
        this.GridViewDate.add(new GridView_Icon_add(C0418R.C0419drawable.ico_bloodfat, getString(C0418R.string.ico_bf)));
        this.GridViewDate.add(new GridView_Icon_add(C0418R.C0419drawable.ico_immunofluorescence, getString(C0418R.string.ico_if)));
        this.GridViewDate.add(new GridView_Icon_add(C0418R.C0419drawable.ico_printer, getString(C0418R.string.ico_bt)));
        this.GridViewAdapter = new GridViewAdapter(getActivity(), this.GridViewDate);
        this.gridView.setAdapter(this.GridViewAdapter);
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long j) {
                view.clearAnimation();
                Animation loadAnimation = AnimationUtils.loadAnimation(AnalyzeFragment.this.getContext(), C0418R.anim.btn_main_item);
                loadAnimation.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        switch (i) {
                            case 0:
                                ClinicName clinicInfo = Utils.getClinicInfo();
                                if (clinicInfo == null) {
                                    AskCreateNickNameDialog askCreateNickNameDialog = new AskCreateNickNameDialog(AnalyzeFragment.this.context);
                                    askCreateNickNameDialog.setOnOperateListener(new AskCreateNickNameDialog.OnOperateListener() {
                                        public void onConfirm() {
                                            AnalyzeFragment.this.startActivityForResult(new Intent(AnalyzeFragment.this.context, ClinicActivity.class), PointerIconCompat.TYPE_CONTEXT_MENU);
                                        }
                                    });
                                    askCreateNickNameDialog.show();
                                    return;
                                }
                                Intent intent = new Intent(AnalyzeFragment.this.context, UserInfoActivity.class);
                                if (!TextUtils.isEmpty(clinicInfo.clinicName)) {
                                    intent.putExtra("currentClinicName", clinicInfo.clinicName);
                                    intent.putExtra("enable", true);
                                }
                                AnalyzeFragment.this.startActivityForResult(intent, PointerIconCompat.TYPE_CONTEXT_MENU);
                                return;
                            case 1:
                                AnalyzeFragment.this.trunActivity(3);
                                return;
                            case 2:
                                AnalyzeFragment.this.trunActivity(5);
                                return;
                            case 3:
                                AnalyzeFragment.this.trunActivity(2);
                                return;
                            case 4:
                                AnalyzeFragment.this.trunActivity(6);
                                return;
                            case 5:
                                AnalyzeFragment.this.trunActivity(4);
                                return;
                            case 6:
                                AnalyzeFragment.this.startActivityForResult(new Intent(AnalyzeFragment.this.context, PrinterActivity.class), 1);
                                return;
                            default:
                                return;
                        }
                    }
                });
                view.startAnimation(loadAnimation);
            }
        });
    }

    /* access modifiers changed from: private */
    public void connectedFail() {
        LoadingDialog loadingDialog2 = this.loadingDialog;
        if (loadingDialog2 != null) {
            loadingDialog2.dismiss();
        }
        TipToastDialog tipToastDialog = new TipToastDialog(this.context);
        tipToastDialog.setImg(C0418R.C0419drawable.ico_tstb);
        tipToastDialog.setText(getString(C0418R.string.connect_fail));
        tipToastDialog.show();
    }

    /* access modifiers changed from: private */
    public void trunActivity(int i) {
        this.currentChoose = i;
        ClinicName clinicInfo = Utils.getClinicInfo();
        NickName userInfo = Utils.getUserInfo();
        if (clinicInfo == null) {
            AskCreateNickNameDialog askCreateNickNameDialog = new AskCreateNickNameDialog(this.context);
            askCreateNickNameDialog.setOnOperateListener(new AskCreateNickNameDialog.OnOperateListener() {
                public void onConfirm() {
                    AnalyzeFragment.this.startActivityForResult(new Intent(AnalyzeFragment.this.context, ClinicActivity.class), PointerIconCompat.TYPE_CONTEXT_MENU);
                }
            });
            askCreateNickNameDialog.show();
        } else if (userInfo == null) {
            TipToastDialog tipToastDialog = new TipToastDialog(this.context);
            tipToastDialog.setText((int) C0418R.string.detection_personnel_information);
            tipToastDialog.show();
        } else {
            showAskOpenBluetooth();
        }
    }

    private void showAskOpenBluetooth() {
        String pOCTCurrentDevice = Session.getInstance().getPOCTCurrentDevice();
        String urinalysCurrentDevice = Session.getInstance().getUrinalysCurrentDevice();
        String proteinCurrentDevice = Session.getInstance().getProteinCurrentDevice();
        String printerDevice = Session.getInstance().getPrinterDevice();
        String bloodFat_Device = Session.getInstance().getBloodFat_Device();
        String immunofluorescenceCurrentDevice = Session.getInstance().getImmunofluorescenceCurrentDevice();
        if (this.mAdapter.isEnabled()) {
            int i = this.currentChoose;
            if (i == 3) {
                if (TextUtils.isEmpty(urinalysCurrentDevice)) {
                    scanDevice_AutomaticConnection();
                }
                if (this.urinalysisManager.isConnect()) {
                    startActivity(new Intent(this.context, UrinalysisMainActivity.class));
                }
            } else if (i == 5) {
                if (TextUtils.isEmpty(pOCTCurrentDevice)) {
                    scanDevice_AutomaticConnection();
                }
                if (this.poctManager.isConnect()) {
                    ClinicName clinicInfo = Utils.getClinicInfo();
                    Intent intent = new Intent(this.context, POCTMainActivity.class);
                    if (!TextUtils.isEmpty(clinicInfo.clinicName)) {
                        intent.putExtra("currentClinicName", clinicInfo.clinicName);
                    }
                    startActivity(intent);
                }
            } else if (i == 2) {
                if (TextUtils.isEmpty(proteinCurrentDevice)) {
                    scanDevice_AutomaticConnection();
                }
                if (this.proteinManager.isConnect()) {
                    startActivity(new Intent(this.context, ProteinMainActivity.class));
                }
            } else if (i == 6) {
                if (TextUtils.isEmpty(bloodFat_Device)) {
                    scanDevice_AutomaticConnection();
                }
                if (this.bloodFatManager.isConnect()) {
                    ClinicName clinicInfo2 = Utils.getClinicInfo();
                    Intent intent2 = new Intent(this.context, BloodFatMainActivity.class);
                    if (!TextUtils.isEmpty(clinicInfo2.clinicName)) {
                        intent2.putExtra("currentClinicName", clinicInfo2.clinicName);
                    }
                    startActivity(intent2);
                }
            } else if (i == 4) {
                if (TextUtils.isEmpty(immunofluorescenceCurrentDevice)) {
                    scanDevice_AutomaticConnection();
                }
                if (this.immunofluorescenceManager.isConnect()) {
                    startActivity(new Intent(this.context, ImmunofluorescenceMainActivity.class));
                }
            } else if (i == 7) {
                if (TextUtils.isEmpty(printerDevice)) {
                    scanDevice_AutomaticConnection();
                }
                if (this.printerManager.isConnect()) {
                    Toast.makeText(this.context, getResources().getString(C0418R.string.printer_has_link), 0).show();
                }
            }
        } else {
            BluetoothSelectDialog bluetoothSelectDialog = new BluetoothSelectDialog(this.context);
            bluetoothSelectDialog.setOnAllowListener(new BluetoothSelectDialog.OnAllowListener() {
                public void click() {
                    if (!AnalyzeFragment.this.mAdapter.isEnabled()) {
                        AnalyzeFragment.this.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), PointerIconCompat.TYPE_CONTEXT_MENU);
                        return;
                    }
                    AnalyzeFragment.this.scanDevice();
                }
            });
            bluetoothSelectDialog.ShowDialog();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1001) {
            if (i2 == -1) {
                scanDevice();
            }
        } else if (i == 1001 && i2 == 1) {
            showAskOpenBluetooth();
        }
    }

    /* access modifiers changed from: private */
    public void scanDevice() {
        this.loadingDialog = new LoadingDialog(getActivity());
        this.loadingDialog.setLoadingText((int) C0418R.string.search_device);
        this.loadingDialog.show();
        this.devices.clear();
        differenceScan();
    }

    private void differenceScan() {
        if (Build.VERSION.SDK_INT <= 23) {
            this.mAdapter.startLeScan(this.leScanCallback);
            Utils.delayTask(new Runnable() {
                public void run() {
                    AnalyzeFragment.this.stopLeScan();
                    AnalyzeFragment.this.showScanResult();
                }
            }, 3000);
            return;
        }
        this.bluetoothLeAdvertiser = this.mAdapter.getBluetoothLeScanner();
        this.bluetoothLeAdvertiser.startScan(this.scanCallback);
        Utils.delayTask(new Runnable() {
            public void run() {
                AnalyzeFragment.this.stopLeScan2();
                AnalyzeFragment.this.showScanResult();
            }
        }, 3000);
    }

    /* access modifiers changed from: private */
    public void showScanResult() {
        IOAM.toMain(new Main<String>() {
            public void onMain(String str) {
                if (AnalyzeFragment.this.loadingDialog != null) {
                    AnalyzeFragment.this.loadingDialog.dismiss();
                }
                if (AnalyzeFragment.this.devices.size() <= 0) {
                    TipToast tipToast = new TipToast(AnalyzeFragment.this.context);
                    tipToast.setImg(C0418R.C0419drawable.ico_tstb);
                    tipToast.setText(AnalyzeFragment.this.getResources().getString(C0418R.string.not_found_device));
                    tipToast.show();
                    return;
                }
                BluetoothDeviceListDialog bluetoothDeviceListDialog = new BluetoothDeviceListDialog(AnalyzeFragment.this.context, new ArrayList(AnalyzeFragment.this.devices.values()));
                bluetoothDeviceListDialog.setOnDeviceChoose(new BluetoothDeviceListDialog.OnDeviceChoose() {
                    public void onChoose(AdapterView<?> adapterView, View view, int i, long j) {
                        AnalyzeFragment.this.judgePosition(i, (BluetoothDeviceListAdapter) adapterView.getAdapter());
                    }
                });
                bluetoothDeviceListDialog.show();
            }
        });
    }

    /* access modifiers changed from: private */
    public void judgePosition(int i, BluetoothDeviceListAdapter bluetoothDeviceListAdapter) {
        String name = bluetoothDeviceListAdapter.getItem(i).device.getName();
        PrintStream printStream = System.out;
        printStream.println("deviceNamedeviceName::" + name);
        int i2 = this.currentChoose;
        if (i2 != 2) {
            if (i2 != 3) {
                if (i2 != 4) {
                    if (i2 != 5) {
                        if (i2 == 6) {
                            if (name.toLowerCase().contains(Constants.DBString.toLowerCase())) {
                                connect(bluetoothDeviceListAdapter.getItem(i).device);
                            } else {
                                dialogDismiss();
                            }
                        }
                    } else if (name.toLowerCase().contains(Constants.WBCString.toLowerCase()) || name.toLowerCase().contains(Constants.WBCString2.toLowerCase())) {
                        connect(bluetoothDeviceListAdapter.getItem(i).device);
                    } else {
                        dialogDismiss();
                    }
                } else if (name.toLowerCase().contains(Constants.IFString.toLowerCase())) {
                    connect(bluetoothDeviceListAdapter.getItem(i).device);
                } else {
                    dialogDismiss();
                }
            } else if (name.toLowerCase().contains(Constants.UrinalysisString1.toLowerCase()) || name.toLowerCase().contains(Constants.UrinalysisString2.toLowerCase()) || name.toLowerCase().contains(Constants.UrinalysisString3.toLowerCase()) || name.toLowerCase().contains(Constants.UrinalysisString4.toLowerCase())) {
                connect(bluetoothDeviceListAdapter.getItem(i).device);
            } else {
                dialogDismiss();
            }
        } else if (name.toLowerCase().contains(Constants.HBString.toLowerCase())) {
            connect(bluetoothDeviceListAdapter.getItem(i).device);
        } else {
            dialogDismiss();
        }
    }

    private void dialogDismiss() {
        final TipDialog tipDialog = new TipDialog(this.context);
        tipDialog.setText((int) C0418R.string.device_type_mismatch2);
        tipDialog.setOnOperateListener(new TipDialog.OnOperateListener() {
            public void onConfirm() {
                tipDialog.dismiss();
            }
        });
        tipDialog.show();
    }

    /* access modifiers changed from: private */
    public void connect(BluetoothDevice bluetoothDevice) {
        this.loadingDialog = new LoadingDialog(this.context);
        this.loadingDialog.setLoadingText((int) C0418R.string.connecting);
        this.loadingDialog.show();
        int i = this.currentChoose;
        if (i == 3) {
            if (this.urinalysisManager.isConnect()) {
                startActivity(new Intent(this.context, UrinalysisMainActivity.class));
                return;
            }
            this.urinalysisManager.setDevice(bluetoothDevice);
            this.urinalysisManager.connect();
        } else if (i == 5) {
            if (this.poctManager.isConnect()) {
                ClinicName clinicInfo = Utils.getClinicInfo();
                Intent intent = new Intent(this.context, POCTMainActivity.class);
                if (!TextUtils.isEmpty(clinicInfo.clinicName)) {
                    intent.putExtra("currentClinicName", clinicInfo.clinicName);
                }
                startActivity(intent);
                return;
            }
            this.poctManager.setDevice(bluetoothDevice);
            this.poctManager.connect();
        } else if (i == 2) {
            if (this.proteinManager.isConnect()) {
                startActivity(new Intent(this.context, ProteinMainActivity.class));
                return;
            }
            this.proteinManager.setDevice(bluetoothDevice);
            this.proteinManager.connect();
        } else if (i == 6) {
            if (this.bloodFatManager.isConnect()) {
                ClinicName clinicInfo2 = Utils.getClinicInfo();
                Intent intent2 = new Intent(this.context, BloodFatMainActivity.class);
                if (!TextUtils.isEmpty(clinicInfo2.clinicName)) {
                    intent2.putExtra("currentClinicName", clinicInfo2.clinicName);
                }
                startActivity(intent2);
                return;
            }
            this.bloodFatManager.setDevice(bluetoothDevice);
            this.bloodFatManager.connect();
        } else if (i == 4) {
            if (this.immunofluorescenceManager.isConnect()) {
                startActivity(new Intent(this.context, ImmunofluorescenceMainActivity.class));
                return;
            }
            this.immunofluorescenceManager.setDevice(bluetoothDevice);
            this.immunofluorescenceManager.connect();
        } else if (i == 7 && !this.printerManager.isConnect()) {
            this.printerManager.setDevice(bluetoothDevice);
            this.printerManager.connect();
        }
    }

    /* access modifiers changed from: private */
    public void stopLeScan() {
        if (this.isScan) {
            this.mAdapter.stopLeScan(this.leScanCallback);
        }
    }

    /* access modifiers changed from: private */
    public void stopLeScan2() {
        if (this.isScan) {
            this.bluetoothLeAdvertiser.stopScan(this.scanCallback);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.lbm.unregisterReceiver(this.receiver);
    }

    public static String getDeviceData(Context context, String str) {
        return context.getSharedPreferences("name", 0).getString(str, "").replaceAll(" ", "");
    }

    private void scanDevice_AutomaticConnection() {
        this.loadingDialog = new LoadingDialog(getActivity());
        this.loadingDialog.setLoadingText((int) C0418R.string.search_device);
        this.loadingDialog.show();
        this.devices.clear();
        if (Build.VERSION.SDK_INT <= 23) {
            this.mAdapter.startLeScan(this.leScanCallback);
            Utils.delayTask(new Runnable() {
                public void run() {
                    AnalyzeFragment.this.stopLeScan();
                    AnalyzeFragment.this.showScanResult_AutomaticConnection();
                }
            }, 3000);
            return;
        }
        this.bluetoothLeAdvertiser = this.mAdapter.getBluetoothLeScanner();
        this.bluetoothLeAdvertiser.startScan(this.scanCallback);
        Utils.delayTask(new Runnable() {
            public void run() {
                AnalyzeFragment.this.stopLeScan2();
                AnalyzeFragment.this.showScanResult_AutomaticConnection();
            }
        }, 3000);
    }

    /* access modifiers changed from: private */
    public void showScanResult_AutomaticConnection() {
        IOAM.toMain(new Main<String>() {
            public void onMain(String str) {
                if (AnalyzeFragment.this.loadingDialog != null) {
                    AnalyzeFragment.this.loadingDialog.dismiss();
                }
                if (AnalyzeFragment.this.devices.size() <= 0) {
                    TipToast tipToast = new TipToast(AnalyzeFragment.this.context);
                    tipToast.setImg(C0418R.C0419drawable.ico_tstb);
                    tipToast.setText(AnalyzeFragment.this.getResources().getString(C0418R.string.no_found_device));
                    tipToast.show();
                    return;
                }
                ArrayList arrayList = new ArrayList(AnalyzeFragment.this.devices.values());
                int i = 0;
                if (AnalyzeFragment.this.currentChoose == 3) {
                    String deviceData = AnalyzeFragment.getDeviceData(AnalyzeFragment.this.context, "Urinalysis");
                    if (deviceData.equals("")) {
                        AnalyzeFragment.this.scanDevice();
                        return;
                    }
                    boolean z = false;
                    while (i < arrayList.size()) {
                        if (deviceData.equals(String.valueOf(((BluetoothDeviceInfo) arrayList.get(i)).device))) {
                            z = true;
                        }
                        i++;
                    }
                    if (z) {
                        AnalyzeFragment analyzeFragment = AnalyzeFragment.this;
                        analyzeFragment.connect(analyzeFragment.mAdaptera_device.getRemoteDevice(deviceData));
                        return;
                    }
                    AnalyzeFragment.this.scanDevice();
                } else if (AnalyzeFragment.this.currentChoose == 5) {
                    String deviceData2 = AnalyzeFragment.getDeviceData(AnalyzeFragment.this.context, "POCT");
                    if (deviceData2.equals("")) {
                        AnalyzeFragment.this.scanDevice();
                        return;
                    }
                    boolean z2 = false;
                    while (i < arrayList.size()) {
                        if (deviceData2.equals(String.valueOf(((BluetoothDeviceInfo) arrayList.get(i)).device))) {
                            z2 = true;
                        }
                        i++;
                    }
                    if (z2) {
                        AnalyzeFragment analyzeFragment2 = AnalyzeFragment.this;
                        analyzeFragment2.connect(analyzeFragment2.mAdaptera_device.getRemoteDevice(deviceData2));
                        return;
                    }
                    AnalyzeFragment.this.scanDevice();
                } else if (AnalyzeFragment.this.currentChoose == 2) {
                    String deviceData3 = AnalyzeFragment.getDeviceData(AnalyzeFragment.this.context, "Protein");
                    if (deviceData3.equals("")) {
                        AnalyzeFragment.this.scanDevice();
                        return;
                    }
                    boolean z3 = false;
                    while (i < arrayList.size()) {
                        if (deviceData3.equals(String.valueOf(((BluetoothDeviceInfo) arrayList.get(i)).device))) {
                            z3 = true;
                        }
                        i++;
                    }
                    if (z3) {
                        AnalyzeFragment analyzeFragment3 = AnalyzeFragment.this;
                        analyzeFragment3.connect(analyzeFragment3.mAdaptera_device.getRemoteDevice(deviceData3));
                        return;
                    }
                    AnalyzeFragment.this.scanDevice();
                } else if (AnalyzeFragment.this.currentChoose == 6) {
                    String deviceData4 = AnalyzeFragment.getDeviceData(AnalyzeFragment.this.context, "Blood_Fat");
                    if (deviceData4.equals("")) {
                        AnalyzeFragment.this.scanDevice();
                        return;
                    }
                    boolean z4 = false;
                    while (i < arrayList.size()) {
                        if (deviceData4.equals(String.valueOf(((BluetoothDeviceInfo) arrayList.get(i)).device))) {
                            z4 = true;
                        }
                        i++;
                    }
                    if (z4) {
                        PrintStream printStream = System.out;
                        printStream.println("mAdaptera_device.getRemoteDevice(Blood_Fat_ID)____  " + deviceData4);
                        AnalyzeFragment analyzeFragment4 = AnalyzeFragment.this;
                        analyzeFragment4.connect(analyzeFragment4.mAdaptera_device.getRemoteDevice(deviceData4));
                        return;
                    }
                    AnalyzeFragment.this.scanDevice();
                } else if (AnalyzeFragment.this.currentChoose == 4) {
                    String deviceData5 = AnalyzeFragment.getDeviceData(AnalyzeFragment.this.context, "Immunofluorescence");
                    if (deviceData5.equals("")) {
                        AnalyzeFragment.this.scanDevice();
                        return;
                    }
                    boolean z5 = false;
                    while (i < arrayList.size()) {
                        if (deviceData5.equals(String.valueOf(((BluetoothDeviceInfo) arrayList.get(i)).device))) {
                            z5 = true;
                        }
                        i++;
                    }
                    if (z5) {
                        AnalyzeFragment analyzeFragment5 = AnalyzeFragment.this;
                        analyzeFragment5.connect(analyzeFragment5.mAdaptera_device.getRemoteDevice(deviceData5));
                        return;
                    }
                    AnalyzeFragment.this.scanDevice();
                } else if (AnalyzeFragment.this.currentChoose == 7) {
                    String deviceData6 = AnalyzeFragment.getDeviceData(AnalyzeFragment.this.context, "Printer");
                    if (deviceData6.equals("")) {
                        AnalyzeFragment.this.scanDevice();
                        return;
                    }
                    boolean z6 = false;
                    while (i < arrayList.size()) {
                        if (deviceData6.equals(String.valueOf(((BluetoothDeviceInfo) arrayList.get(i)).device))) {
                            z6 = true;
                        }
                        i++;
                    }
                    if (z6) {
                        AnalyzeFragment analyzeFragment6 = AnalyzeFragment.this;
                        analyzeFragment6.connect(analyzeFragment6.mAdaptera_device.getRemoteDevice(deviceData6));
                        return;
                    }
                    AnalyzeFragment.this.scanDevice();
                }
            }
        });
    }
}
