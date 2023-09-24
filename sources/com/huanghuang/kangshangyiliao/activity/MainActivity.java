package com.huanghuang.kangshangyiliao.activity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.p000v4.app.Fragment;
import android.support.p000v4.app.FragmentManager;
import android.support.p000v4.app.FragmentTransaction;
import android.support.p000v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.fragment.FragmentFactory;
import com.huanghuang.kangshangyiliao.util.BloodFatManager;
import com.huanghuang.kangshangyiliao.util.DoubleClickExitHelper;
import com.huanghuang.kangshangyiliao.util.ImmunofluorescenceManager;
import com.huanghuang.kangshangyiliao.util.POCTManager;
import com.huanghuang.kangshangyiliao.util.Parameter;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.UrinalysisManager;

public class MainActivity extends BaseActivity implements Parameter {
    /* access modifiers changed from: private */
    public Fragment currFragment;
    private DoubleClickExitHelper doubleClickHelper;
    private LocalBroadcastManager lbm;
    private RadioGroup radioGroup;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r1, android.content.Intent r2) {
            /*
                r0 = this;
                java.lang.String r1 = r2.getAction()
                int r2 = r1.hashCode()
                switch(r2) {
                    case -1522631667: goto L_0x0052;
                    case -767539241: goto L_0x0048;
                    case -606267464: goto L_0x003e;
                    case -530064037: goto L_0x0034;
                    case 155122889: goto L_0x002a;
                    case 255270522: goto L_0x0020;
                    case 1085444827: goto L_0x0016;
                    case 1636638796: goto L_0x000c;
                    default: goto L_0x000b;
                }
            L_0x000b:
                goto L_0x005c
            L_0x000c:
                java.lang.String r2 = "poct_refresh"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x005c
                r1 = 3
                goto L_0x005d
            L_0x0016:
                java.lang.String r2 = "refresh"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x005c
                r1 = 2
                goto L_0x005d
            L_0x0020:
                java.lang.String r2 = "urinalysis_query"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x005c
                r1 = 0
                goto L_0x005d
            L_0x002a:
                java.lang.String r2 = "bloodFat_notify"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x005c
                r1 = 6
                goto L_0x005d
            L_0x0034:
                java.lang.String r2 = "bloodFat_refresh"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x005c
                r1 = 5
                goto L_0x005d
            L_0x003e:
                java.lang.String r2 = "poct_notify"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x005c
                r1 = 4
                goto L_0x005d
            L_0x0048:
                java.lang.String r2 = "urinalysis_notify"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x005c
                r1 = 1
                goto L_0x005d
            L_0x0052:
                java.lang.String r2 = "immunofluorescence_notify"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x005c
                r1 = 7
                goto L_0x005d
            L_0x005c:
                r1 = -1
            L_0x005d:
                switch(r1) {
                    case 0: goto L_0x0061;
                    case 1: goto L_0x0061;
                    case 2: goto L_0x0061;
                    case 3: goto L_0x0061;
                    case 4: goto L_0x0061;
                    case 5: goto L_0x0061;
                    case 6: goto L_0x0061;
                    case 7: goto L_0x0061;
                    default: goto L_0x0060;
                }
            L_0x0060:
                goto L_0x0076
            L_0x0061:
                com.huanghuang.kangshangyiliao.activity.MainActivity r1 = com.huanghuang.kangshangyiliao.activity.MainActivity.this
                android.support.v4.app.Fragment r1 = r1.currFragment
                boolean r1 = r1 instanceof com.huanghuang.kangshangyiliao.fragment.AnalyzeResultFragment
                if (r1 == 0) goto L_0x0076
                com.huanghuang.kangshangyiliao.activity.MainActivity r1 = com.huanghuang.kangshangyiliao.activity.MainActivity.this
                android.support.v4.app.Fragment r1 = r1.currFragment
                com.huanghuang.kangshangyiliao.fragment.AnalyzeResultFragment r1 = (com.huanghuang.kangshangyiliao.fragment.AnalyzeResultFragment) r1
                r1.refresh()
            L_0x0076:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.activity.MainActivity.C04502.onReceive(android.content.Context, android.content.Intent):void");
        }
    };

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_main);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.rl_content));
        initView();
        initListener();
        this.lbm = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UrinalysisManager.NOTIFY);
        intentFilter.addAction(UrinalysisManager.QUERY);
        intentFilter.addAction(UrinalysisResultDetailActivity.REFRESH);
        intentFilter.addAction(POCTManager.NOTIFY);
        intentFilter.addAction(BloodFatManager.NOTIFY);
        intentFilter.addAction(ImmunofluorescenceManager.NOTIFY);
        this.lbm.registerReceiver(this.receiver, intentFilter);
        this.doubleClickHelper = new DoubleClickExitHelper(this);
        this.doubleClickHelper.setOnExitListener(new DoubleClickExitHelper.OnExitListener() {
            public void onExit() {
                MainActivity.this.finish();
            }
        });
    }

    private void initView() {
        this.radioGroup = (RadioGroup) findViewById(C0418R.C0420id.RadioGroup_Report);
    }

    private void initListener() {
        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                FragmentManager supportFragmentManager = MainActivity.this.getSupportFragmentManager();
                FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
                if (MainActivity.this.currFragment != null) {
                    beginTransaction.hide(MainActivity.this.currFragment);
                }
                MainActivity mainActivity = MainActivity.this;
                Fragment unused = mainActivity.currFragment = supportFragmentManager.findFragmentByTag(i + "");
                if (MainActivity.this.currFragment == null) {
                    Fragment unused2 = MainActivity.this.currFragment = FragmentFactory.getMainByIndex(i);
                    Fragment access$000 = MainActivity.this.currFragment;
                    beginTransaction.add(C0418R.C0420id.FrameLayout_Content, access$000, i + "");
                } else {
                    beginTransaction.show(MainActivity.this.currFragment);
                }
                beginTransaction.commit();
            }
        });
        ((RadioButton) this.radioGroup.findViewById(C0418R.C0420id.rbAnalyze)).setChecked(true);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.lbm.unregisterReceiver(this.receiver);
        UrinalysisManager instance = UrinalysisManager.getInstance();
        POCTManager instance2 = POCTManager.getInstance();
        if (instance.isConnect()) {
            instance.disconnect();
        }
        if (instance2.isConnect()) {
            instance2.disconnect();
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            return this.doubleClickHelper.onKeyDown(i, keyEvent);
        }
        return super.onKeyDown(i, keyEvent);
    }
}
