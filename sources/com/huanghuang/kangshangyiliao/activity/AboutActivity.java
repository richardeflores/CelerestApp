package com.huanghuang.kangshangyiliao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.ViewBind;

public class AboutActivity extends BaseActivity implements View.OnClickListener {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_about);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_about));
        ViewBind.bind((Activity) this);
    }

    public void onClick(View view) {
        if (view.getId() == C0418R.C0420id.ivClose) {
            finish();
        }
    }
}
