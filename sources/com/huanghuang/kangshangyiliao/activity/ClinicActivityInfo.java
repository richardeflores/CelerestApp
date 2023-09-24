package com.huanghuang.kangshangyiliao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.dao.ClinicNameDao;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import com.huanghuang.kangshangyiliao.util.Cache;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.ViewBind;

public class ClinicActivityInfo extends BaseActivity implements View.OnClickListener {
    private Cache cache = Cache.getInstance();
    private ClinicName clinicName;
    private ClinicNameDao dao = new ClinicNameDao();
    @ViewBind.Bind(mo7926id = 2131230841)
    private EditText etClinicName;
    @ViewBind.Bind(mo7926id = 2131230842)
    private EditText etDoctorName;
    @ViewBind.Bind(mo7926id = 2131230847)
    private EditText etSex;
    private boolean isEdit;
    @ViewBind.Bind(mo7926id = 2131230898)
    private LinearLayout llSex;
    @ViewBind.Bind(mo7926id = 2131231068)
    private TextView tvTitle;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_clinic_info);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_clinic_info));
        ViewBind.bind((Activity) this);
        initData(getIntent().getStringExtra("currentClinicName"));
    }

    private void initData(String str) {
        this.tvTitle.setText(getString(C0418R.string.clinin_info));
        ClinicName query = new ClinicNameDao().query(str);
        this.etClinicName.setText(query.clinicName);
        this.etDoctorName.setText(query.doctorName);
        if (query.sex.equals("1")) {
            this.etSex.setText(getString(C0418R.string.male));
        } else {
            this.etSex.setText(getString(C0418R.string.female));
        }
        this.etClinicName.setEnabled(false);
        this.etDoctorName.setEnabled(false);
        this.etSex.setEnabled(false);
        this.llSex.setEnabled(false);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C0418R.C0420id.btConfirm || id == C0418R.C0420id.ivClose) {
            finish();
        }
    }
}
