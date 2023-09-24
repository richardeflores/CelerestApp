package com.huanghuang.kangshangyiliao.activity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.p000v4.app.Fragment;
import android.support.p000v4.app.FragmentManager;
import android.support.p000v4.app.FragmentTransaction;
import android.support.p000v4.content.LocalBroadcastManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.fragment.FragmentFactory;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.UrinalysisManager;

@Deprecated
public class ReportActivity extends BaseActivity {
    /* access modifiers changed from: private */
    public Fragment currFragment;
    private LocalBroadcastManager lbm;
    private RadioGroup radioGroup;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        /* JADX WARNING: Removed duplicated region for block: B:12:0x002b A[ADDED_TO_REGION] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r3, android.content.Intent r4) {
            /*
                r2 = this;
                java.lang.String r3 = r4.getAction()
                int r4 = r3.hashCode()
                r0 = -767539241(0xffffffffd24047d7, float:-2.06459748E11)
                r1 = 1
                if (r4 == r0) goto L_0x001e
                r0 = 255270522(0xf371e7a, float:9.028466E-30)
                if (r4 == r0) goto L_0x0014
                goto L_0x0028
            L_0x0014:
                java.lang.String r4 = "urinalysis_query"
                boolean r3 = r3.equals(r4)
                if (r3 == 0) goto L_0x0028
                r3 = 0
                goto L_0x0029
            L_0x001e:
                java.lang.String r4 = "urinalysis_notify"
                boolean r3 = r3.equals(r4)
                if (r3 == 0) goto L_0x0028
                r3 = 1
                goto L_0x0029
            L_0x0028:
                r3 = -1
            L_0x0029:
                if (r3 == 0) goto L_0x002e
                if (r3 == r1) goto L_0x002e
                goto L_0x0043
            L_0x002e:
                com.huanghuang.kangshangyiliao.activity.ReportActivity r3 = com.huanghuang.kangshangyiliao.activity.ReportActivity.this
                android.support.v4.app.Fragment r3 = r3.currFragment
                boolean r3 = r3 instanceof com.huanghuang.kangshangyiliao.fragment.AnalyzeResultFragment
                if (r3 == 0) goto L_0x0043
                com.huanghuang.kangshangyiliao.activity.ReportActivity r3 = com.huanghuang.kangshangyiliao.activity.ReportActivity.this
                android.support.v4.app.Fragment r3 = r3.currFragment
                com.huanghuang.kangshangyiliao.fragment.AnalyzeResultFragment r3 = (com.huanghuang.kangshangyiliao.fragment.AnalyzeResultFragment) r3
                r3.refresh()
            L_0x0043:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.activity.ReportActivity.C04781.onReceive(android.content.Context, android.content.Intent):void");
        }
    };

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_report);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_report));
        initView();
        initListener();
        this.lbm = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UrinalysisManager.NOTIFY);
        intentFilter.addAction(UrinalysisManager.QUERY);
        this.lbm.registerReceiver(this.receiver, intentFilter);
    }

    private void initView() {
        this.radioGroup = (RadioGroup) findViewById(C0418R.C0420id.RadioGroup_Report);
    }

    private void initListener() {
        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                FragmentManager supportFragmentManager = ReportActivity.this.getSupportFragmentManager();
                FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
                if (ReportActivity.this.currFragment != null) {
                    beginTransaction.hide(ReportActivity.this.currFragment);
                }
                ReportActivity reportActivity = ReportActivity.this;
                Fragment unused = reportActivity.currFragment = supportFragmentManager.findFragmentByTag(i + "");
                if (ReportActivity.this.currFragment == null) {
                    Fragment unused2 = ReportActivity.this.currFragment = FragmentFactory.getMainByIndex(i);
                    Fragment access$000 = ReportActivity.this.currFragment;
                    beginTransaction.add(C0418R.C0420id.FrameLayout_Content, access$000, i + "");
                } else {
                    beginTransaction.show(ReportActivity.this.currFragment);
                }
                beginTransaction.commit();
            }
        });
        ((RadioButton) this.radioGroup.findViewById(C0418R.C0420id.RadioButton_Report)).setChecked(true);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.lbm.unregisterReceiver(this.receiver);
    }
}
