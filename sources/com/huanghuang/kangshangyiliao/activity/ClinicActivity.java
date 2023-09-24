package com.huanghuang.kangshangyiliao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.dao.ClinicNameDao;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import com.huanghuang.kangshangyiliao.util.Cache;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import com.huanghuang.kangshangyiliao.widget.ChoseSexDialog;
import com.huanghuang.kangshangyiliao.widget.TipToast;

public class ClinicActivity extends BaseActivity implements View.OnClickListener {
    private Cache cache = Cache.getInstance();
    private ClinicName clinicName;
    private ClinicNameDao dao = new ClinicNameDao();
    @ViewBind.Bind(mo7926id = 2131230841)
    private EditText etClinicName;
    @ViewBind.Bind(mo7926id = 2131230842)
    private EditText etDoctorName;
    /* access modifiers changed from: private */
    @ViewBind.Bind(mo7926id = 2131230847)
    public EditText etSex;
    private boolean isEdit;
    @ViewBind.Bind(mo7926id = 2131231068)
    private TextView tvTitle;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_clinic_info);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_clinic_info));
        ViewBind.bind((Activity) this);
        this.clinicName = (ClinicName) getIntent().getSerializableExtra(Cache.CLINIC_INFO);
        if (this.clinicName != null) {
            this.isEdit = true;
            this.etClinicName.setEnabled(false);
            this.etDoctorName.setEnabled(false);
            this.etSex.setEnabled(false);
            initClinicInfo();
        }
    }

    private void initClinicInfo() {
        String str;
        this.tvTitle.setText(C0418R.string.edit_user);
        this.etClinicName.setText(this.clinicName.clinicName);
        this.etClinicName.setSelection(this.clinicName.clinicName.length());
        this.etDoctorName.setText(this.clinicName.doctorName);
        if ("1".equals(this.clinicName.sex)) {
            str = getString(C0418R.string.male);
        } else {
            str = "2".equals(this.clinicName.sex) ? getString(C0418R.string.female) : "";
        }
        this.etSex.setText(str);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case C0418R.C0420id.btConfirm:
                String trim = this.etClinicName.getText().toString().trim();
                String trim2 = this.etDoctorName.getText().toString().trim();
                String trim3 = this.etSex.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    TipToast tipToast = new TipToast(this);
                    tipToast.setImg(C0418R.C0419drawable.ico_tstb);
                    tipToast.setText((int) C0418R.string.nick_name_can_not_be_empty);
                    tipToast.show();
                    return;
                } else if (TextUtils.isEmpty(trim2)) {
                    TipToast tipToast2 = new TipToast(this);
                    tipToast2.setImg(C0418R.C0419drawable.ico_tstb);
                    tipToast2.setText((int) C0418R.string.nick_birthday_can_not_be_empty);
                    tipToast2.show();
                    return;
                } else if (TextUtils.isEmpty(trim3)) {
                    TipToast tipToast3 = new TipToast(this);
                    tipToast3.setImg(C0418R.C0419drawable.ico_tstb);
                    tipToast3.setText((int) C0418R.string.nick_sex_can_not_be_empty);
                    tipToast3.show();
                    return;
                } else {
                    int i = -1;
                    ClinicName clinicName2 = this.clinicName;
                    if (clinicName2 != null) {
                        i = clinicName2.f77id;
                    }
                    if (this.dao.exists(trim, i)) {
                        TipToast tipToast4 = new TipToast(this);
                        tipToast4.setImg(C0418R.C0419drawable.ico_tstb);
                        tipToast4.setText(trim + getString(C0418R.string.exists));
                        tipToast4.show();
                        return;
                    }
                    if (this.clinicName == null) {
                        this.clinicName = new ClinicName();
                    }
                    ClinicName clinicName3 = this.clinicName;
                    clinicName3.clinicName = trim;
                    clinicName3.createDate = Utils.getDate();
                    this.clinicName.doctorName = this.etDoctorName.getText().toString().trim();
                    this.clinicName.sex = getSex();
                    if (this.isEdit) {
                        this.dao.update(this.clinicName);
                    } else {
                        this.dao.save(this.clinicName);
                    }
                    Utils.saveClinicToCache(this.dao.query(trim));
                    setResult(1);
                    finish();
                    return;
                }
            case C0418R.C0420id.etSex:
            case C0418R.C0420id.llSex:
                this.clinicName = (ClinicName) getIntent().getSerializableExtra(Cache.CLINIC_INFO);
                if (this.clinicName == null) {
                    ChoseSexDialog choseSexDialog = new ChoseSexDialog(this);
                    choseSexDialog.setSex(getSex());
                    choseSexDialog.setOnOperateListener(new ChoseSexDialog.OnOperateListener() {
                        public void onConfirm(int i) {
                            ClinicActivity.this.etSex.setText(i == 2 ? C0418R.string.female : C0418R.string.male);
                        }
                    });
                    choseSexDialog.show();
                    return;
                }
                return;
            case C0418R.C0420id.ivClose:
                setResult(0);
                finish();
                return;
            default:
                return;
        }
    }

    private String getSex() {
        if (getString(C0418R.string.male).equals(this.etSex.getText().toString().trim())) {
            return "1";
        }
        return getString(C0418R.string.female).equals(this.etSex.getText().toString().trim()) ? "2" : "";
    }
}
