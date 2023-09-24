package com.huanghuang.kangshangyiliao.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.p000v4.content.LocalBroadcastManager;
import android.support.p000v4.view.ViewPager;
import android.view.View;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.adapter.POCTResultDetailFragmentAdapter;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.util.POCTManager;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import com.huanghuang.kangshangyiliao.widget.AskCreateNickNameDialog;
import com.huanghuang.kangshangyiliao.widget.AskDialog;

public class POCTResultDetailActivity extends BaseActivity implements View.OnClickListener {
    public static final String REFRESH = "poct_refresh";
    /* access modifiers changed from: private */
    public POCTResultDetailFragmentAdapter adapter;
    private AppBase appBase = AppBase.getInstance();
    private LocalBroadcastManager lbm;
    private int position;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.hashCode() == -606267464) {
                boolean equals = action.equals(POCTManager.NOTIFY);
            }
        }
    };
    /* access modifiers changed from: private */
    @ViewBind.Bind(mo7926id = 2131231112)
    public ViewPager vpPOCTResultDetail;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_poct_result_detail);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_poct_result_detail));
        ViewBind.bind((Activity) this);
        this.position = getIntent().getIntExtra("position", 0);
        this.adapter = new POCTResultDetailFragmentAdapter(getFragmentManager(), this);
        this.vpPOCTResultDetail.setAdapter(this.adapter);
        this.vpPOCTResultDetail.setCurrentItem(this.position);
        this.lbm = LocalBroadcastManager.getInstance(this.appBase.getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(POCTManager.NOTIFY);
        this.lbm.registerReceiver(this.receiver, intentFilter);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case C0418R.C0420id.ivClose:
                finish();
                return;
            case C0418R.C0420id.ivDelete:
                AskDialog askDialog = new AskDialog(this);
                askDialog.setText((int) C0418R.string.confirm_delete);
                askDialog.setOnOperateListener(new AskCreateNickNameDialog.OnOperateListener() {
                    public void onConfirm() {
                        POCTResultDetailActivity.this.adapter.delete(POCTResultDetailActivity.this.vpPOCTResultDetail.getCurrentItem());
                        Intent intent = new Intent();
                        intent.setAction(POCTResultDetailActivity.REFRESH);
                        POCTResultDetailActivity.this.sendBroadcast(intent);
                        POCTResultDetailActivity.this.setResult(-1, intent);
                        POCTResultDetailActivity.this.finish();
                    }
                });
                askDialog.show();
                return;
            case C0418R.C0420id.ivShare:
                Utils.share(this, this.adapter.getCurrentFragment());
                return;
            default:
                return;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.lbm.unregisterReceiver(this.receiver);
    }
}
