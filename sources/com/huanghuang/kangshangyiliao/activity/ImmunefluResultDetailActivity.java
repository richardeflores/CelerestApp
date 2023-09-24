package com.huanghuang.kangshangyiliao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.p000v4.content.LocalBroadcastManager;
import android.support.p000v4.view.ViewPager;
import android.view.View;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.adapter.ImmunefluResultDetailFragmentAdapter;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import com.huanghuang.kangshangyiliao.widget.AskCreateNickNameDialog;
import com.huanghuang.kangshangyiliao.widget.AskDialog;

public class ImmunefluResultDetailActivity extends BaseActivity implements View.OnClickListener {
    public static final String REFRESH = "Immuneflu_refresh";
    /* access modifiers changed from: private */
    public ImmunefluResultDetailFragmentAdapter adapter;
    private AppBase appBase = AppBase.getInstance();
    /* access modifiers changed from: private */
    public LocalBroadcastManager lbm;
    private int position;
    /* access modifiers changed from: private */
    @ViewBind.Bind(mo7926id = 2131231111)
    public ViewPager vpBloodFatResultDetail;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_blood_fat_result_detail);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_blood_fat_result_detail));
        ViewBind.bind((Activity) this);
        this.position = getIntent().getIntExtra("position", 0);
        this.adapter = new ImmunefluResultDetailFragmentAdapter(getFragmentManager(), this);
        this.vpBloodFatResultDetail.setAdapter(this.adapter);
        this.vpBloodFatResultDetail.setCurrentItem(this.position);
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
                        ImmunefluResultDetailActivity.this.adapter.delete(ImmunefluResultDetailActivity.this.vpBloodFatResultDetail.getCurrentItem());
                        Intent intent = new Intent();
                        intent.setAction(ImmunefluResultDetailActivity.REFRESH);
                        ImmunefluResultDetailActivity.this.lbm.sendBroadcast(intent);
                        ImmunefluResultDetailActivity.this.setResult(-1, intent);
                        ImmunefluResultDetailActivity.this.finish();
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
    }
}
