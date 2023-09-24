package com.huanghuang.kangshangyiliao.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.ViewBind;

public class AboutAppActivity extends BaseActivity implements View.OnClickListener {
    private String Zxing_URL = "http://115.28.38.110:9030/ivd/checkout_1.0.1.2.apk";
    @ViewBind.Bind(mo7926id = 2131230883)
    private ImageView ivZxing;
    @ViewBind.Bind(mo7926id = 2131231018)
    private TextView tvCurrentVersion;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_about_app);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_about_app));
        ViewBind.bind((Activity) this);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            TextView textView = this.tvCurrentVersion;
            textView.setText(getString(C0418R.string.current_version) + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        if (view.getId() == C0418R.C0420id.ivClose) {
            finish();
        }
    }
}
