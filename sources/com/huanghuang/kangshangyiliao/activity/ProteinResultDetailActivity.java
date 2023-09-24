package com.huanghuang.kangshangyiliao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.p000v4.content.LocalBroadcastManager;
import android.support.p000v4.view.ViewPager;
import android.view.View;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.adapter.ProteinResultDetailFragmentAdapter;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import com.huanghuang.kangshangyiliao.widget.AskCreateNickNameDialog;
import com.huanghuang.kangshangyiliao.widget.AskDialog;

public class ProteinResultDetailActivity extends BaseActivity implements View.OnClickListener {
    public static final String REFRESH = "Protein_refresh";
    /* access modifiers changed from: private */
    public ProteinResultDetailFragmentAdapter adapter;
    private AppBase appBase = AppBase.getInstance();
    /* access modifiers changed from: private */
    public LocalBroadcastManager lbm;
    private int position;
    /* access modifiers changed from: private */
    @ViewBind.Bind(mo7926id = 2131231112)
    public ViewPager vpPOCTResultDetail;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_proteinresultdetail);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_proteinresultdetail));
        ViewBind.bind((Activity) this);
        this.position = getIntent().getIntExtra("position", 0);
        this.adapter = new ProteinResultDetailFragmentAdapter(getFragmentManager(), this);
        this.vpPOCTResultDetail.setAdapter(this.adapter);
        this.vpPOCTResultDetail.setCurrentItem(this.position);
        this.lbm = LocalBroadcastManager.getInstance(this.appBase.getContext());
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
                        ProteinResultDetailActivity.this.adapter.delete(ProteinResultDetailActivity.this.vpPOCTResultDetail.getCurrentItem());
                        Intent intent = new Intent();
                        intent.setAction(ProteinResultDetailActivity.REFRESH);
                        ProteinResultDetailActivity.this.lbm.sendBroadcast(intent);
                        ProteinResultDetailActivity.this.setResult(-1, intent);
                        ProteinResultDetailActivity.this.finish();
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
}
