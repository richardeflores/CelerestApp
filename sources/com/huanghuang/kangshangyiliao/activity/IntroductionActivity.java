package com.huanghuang.kangshangyiliao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.ViewBind;

public class IntroductionActivity extends BaseActivity implements View.OnClickListener {
    private int[] images_en = {C0418R.C0419drawable.introduction_njjs_en, C0418R.C0419drawable.introduction_xyfx};
    private int[] images_zh = {C0418R.C0419drawable.introduction_njjs, C0418R.C0419drawable.introduction_xyfx};
    @ViewBind.Bind(mo7926id = 2131230979)
    private SubsamplingScaleImageView ssivIntroduction;
    private int[] titles = {C0418R.string.introduce_nyfxy, C0418R.string.introduce_xxbfxy};
    @ViewBind.Bind(mo7926id = 2131231068)
    private TextView tvTitle;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_introduction);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_introduction));
        ViewBind.bind((Activity) this);
        int intExtra = getIntent().getIntExtra("index", -1);
        String language = getResources().getConfiguration().locale.getLanguage();
        if (intExtra != -1) {
            this.tvTitle.setText(this.titles[intExtra]);
            if (language.equals("zh")) {
                this.ssivIntroduction.setImage(ImageSource.resource(this.images_zh[intExtra]));
            } else if (language.equals("en")) {
                this.ssivIntroduction.setImage(ImageSource.resource(this.images_en[intExtra]));
            } else if (language.equals("fr")) {
                this.ssivIntroduction.setImage(ImageSource.resource(this.images_en[intExtra]));
            } else if (language.equals("de")) {
                this.ssivIntroduction.setImage(ImageSource.resource(this.images_en[intExtra]));
            } else if (language.equals("it")) {
                this.ssivIntroduction.setImage(ImageSource.resource(this.images_en[intExtra]));
            } else if (language.equals("ja")) {
                this.ssivIntroduction.setImage(ImageSource.resource(this.images_en[intExtra]));
            } else if (language.equals("ko")) {
                this.ssivIntroduction.setImage(ImageSource.resource(this.images_en[intExtra]));
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == C0418R.C0420id.ivClose) {
            finish();
        }
    }
}
