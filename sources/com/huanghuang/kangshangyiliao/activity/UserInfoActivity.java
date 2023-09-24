package com.huanghuang.kangshangyiliao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.dao.ClinicNameDao;
import com.huanghuang.kangshangyiliao.dao.DoctorAndPatientDao;
import com.huanghuang.kangshangyiliao.dao.NickNameDao;
import com.huanghuang.kangshangyiliao.dao.bean.DoctorAndPatientBean;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.util.Cache;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import com.huanghuang.kangshangyiliao.widget.ChoseDateDialog;
import com.huanghuang.kangshangyiliao.widget.ChoseSexDialog;
import com.huanghuang.kangshangyiliao.widget.NickNameListDialog;
import com.huanghuang.kangshangyiliao.widget.NickNameOperateDialog;
import com.huanghuang.kangshangyiliao.widget.TipToast;
import java.io.PrintStream;
import java.util.List;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private boolean booleanExtraEnable;
    private Cache cache = Cache.getInstance();
    private ClinicNameDao clinicNameDao = new ClinicNameDao();
    private String currentClinicName;
    /* access modifiers changed from: private */
    public NickNameDao dao = new NickNameDao();
    private DoctorAndPatientDao doctorAndPatientDao = new DoctorAndPatientDao();
    /* access modifiers changed from: private */
    @ViewBind.Bind(mo7926id = 2131230840)
    public EditText etBirthday;
    @ViewBind.Bind(mo7926id = 2131230843)
    private EditText etHeight;
    @ViewBind.Bind(mo7926id = 2131230845)
    private EditText etPhoneNumber;
    /* access modifiers changed from: private */
    @ViewBind.Bind(mo7926id = 2131230847)
    public EditText etSex;
    @ViewBind.Bind(mo7926id = 2131230848)
    private EditText etUserName;
    @ViewBind.Bind(mo7926id = 2131230849)
    private EditText etWeight;
    private boolean isEdit;
    private NickName nickName;
    @ViewBind.Bind(mo7926id = 2131231068)
    private TextView tvTitle;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_user_info);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_user_info));
        ViewBind.bind((Activity) this);
        this.nickName = (NickName) getIntent().getSerializableExtra("userInfo");
        this.currentClinicName = getIntent().getStringExtra("currentClinicName");
        this.booleanExtraEnable = getIntent().getBooleanExtra("enable", true);
        PrintStream printStream = System.out;
        printStream.println("nickName___enable::" + this.booleanExtraEnable);
        setEnable(this.booleanExtraEnable);
        if (!this.booleanExtraEnable) {
            initMethod();
            PrintStream printStream2 = System.out;
            printStream2.println("initMethod:: " + this.booleanExtraEnable);
        }
    }

    public void setEnable(boolean z) {
        if (z) {
            this.tvTitle.setText(C0418R.string.create_user);
        } else {
            this.tvTitle.setText(C0418R.string.edit_user);
        }
        this.etUserName.setEnabled(z);
        this.etSex.setEnabled(z);
        this.etBirthday.setEnabled(z);
        this.etPhoneNumber.setEnabled(z);
        this.etHeight.setEnabled(z);
        this.etWeight.setEnabled(z);
    }

    private void initMethod() {
        String str;
        List<DoctorAndPatientBean> queryAll = this.doctorAndPatientDao.queryAll();
        if (queryAll == null || queryAll.size() == 0) {
            setEnable(true);
            return;
        }
        for (int i = 0; i < queryAll.size(); i++) {
            if (queryAll.get(i).clinicName != null) {
                if (queryAll.get(i).clinicName.equals(this.currentClinicName)) {
                    this.isEdit = true;
                    this.etUserName.setText(queryAll.get(i).nickName);
                    this.etUserName.setSelection(queryAll.get(i).nickName.length());
                    if ("1".equals(queryAll.get(queryAll.size() - 1).sex)) {
                        str = getString(C0418R.string.male);
                    } else {
                        str = "2".equals(queryAll.get(queryAll.size() - 1).sex) ? getString(C0418R.string.female) : "";
                    }
                    this.etSex.setText(str);
                    this.etBirthday.setText(queryAll.get(i).birthday);
                    this.etPhoneNumber.setText(queryAll.get(i).phoneNumber);
                    this.etHeight.setText(queryAll.get(i).height);
                    this.etWeight.setText(queryAll.get(i).weight);
                    setEnable(false);
                    changCurrentNickName(queryAll.get(i).f79id);
                } else if (TextUtils.isEmpty(this.etUserName.getText().toString()) && TextUtils.isEmpty(this.etSex.getText().toString()) && TextUtils.isEmpty(this.etBirthday.getText().toString())) {
                    setEnable(true);
                }
            }
        }
    }

    private void changCurrentNickName(int i) {
        this.nickName.nickName = this.etUserName.getText().toString().trim();
        this.nickName.createDate = Utils.getDate();
        this.nickName.sex = getSex();
        this.nickName.birthday = this.etBirthday.getText().toString().trim();
        this.nickName.phoneNumber = this.etPhoneNumber.getText().toString().trim();
        this.nickName.height = this.etHeight.getText().toString().trim();
        this.nickName.weight = this.etWeight.getText().toString().trim();
        NickName nickName2 = this.nickName;
        nickName2.f81id = i;
        if (this.isEdit) {
            this.dao.update(nickName2);
        } else {
            this.dao.save(nickName2);
        }
        Utils.saveUserToCache(this.dao.query(this.etUserName.getText().toString().trim()));
    }

    private void initUserInfo() {
        String str;
        this.tvTitle.setText(C0418R.string.edit_user);
        this.etUserName.setText(this.nickName.nickName);
        this.etUserName.setSelection(this.nickName.nickName.length());
        if ("1".equals(this.nickName.sex)) {
            str = getString(C0418R.string.male);
        } else {
            str = "2".equals(this.nickName.sex) ? getString(C0418R.string.female) : "";
        }
        this.etSex.setText(str);
        this.etBirthday.setText(this.nickName.birthday);
        this.etPhoneNumber.setText(this.nickName.phoneNumber);
        this.etHeight.setText(this.nickName.height);
        this.etWeight.setText(this.nickName.weight);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case C0418R.C0420id.btConfirm:
                if (this.booleanExtraEnable) {
                    confirmData();
                    return;
                }
                setResult(0);
                finish();
                return;
            case C0418R.C0420id.etBirthday:
            case C0418R.C0420id.llBirthday:
                ChoseDateDialog choseDateDialog = new ChoseDateDialog(this);
                choseDateDialog.setOnOperateListener(new ChoseDateDialog.OnOperateListener() {
                    public void onConfirm(String str) {
                        UserInfoActivity.this.etBirthday.setText(str);
                    }
                });
                choseDateDialog.show();
                return;
            case C0418R.C0420id.etSex:
            case C0418R.C0420id.llSex:
                ChoseSexDialog choseSexDialog = new ChoseSexDialog(this);
                choseSexDialog.setSex(getSex());
                choseSexDialog.setOnOperateListener(new ChoseSexDialog.OnOperateListener() {
                    public void onConfirm(int i) {
                        UserInfoActivity.this.etSex.setText(i == 2 ? C0418R.string.female : C0418R.string.male);
                    }
                });
                choseSexDialog.show();
                return;
            case C0418R.C0420id.ivClose:
                setResult(0);
                finish();
                return;
            default:
                return;
        }
    }

    private void confirmData() {
        String trim = this.etUserName.getText().toString().trim();
        String trim2 = this.etSex.getText().toString().trim();
        String trim3 = this.etBirthday.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            TipToast tipToast = new TipToast(this);
            tipToast.setImg(C0418R.C0419drawable.ico_tstb);
            tipToast.setText((int) C0418R.string.nick_name_can_not_be_empty);
            tipToast.show();
        } else if (TextUtils.isEmpty(trim2)) {
            TipToast tipToast2 = new TipToast(this);
            tipToast2.setImg(C0418R.C0419drawable.ico_tstb);
            tipToast2.setText((int) C0418R.string.nick_sex_can_not_be_empty);
            tipToast2.show();
        } else if (TextUtils.isEmpty(trim3)) {
            TipToast tipToast3 = new TipToast(this);
            tipToast3.setImg(C0418R.C0419drawable.ico_tstb);
            tipToast3.setText((int) C0418R.string.nick_birthday_can_not_be_empty);
            tipToast3.show();
        } else {
            NickName nickName2 = this.nickName;
            int i = nickName2 != null ? nickName2.f81id : -1;
            if (this.dao.exists_name(trim, i)) {
                select_name();
                return;
            }
            if (this.nickName == null) {
                this.nickName = new NickName();
            }
            NickName nickName3 = this.nickName;
            nickName3.nickName = trim;
            nickName3.createDate = Utils.getDate();
            this.nickName.sex = getSex();
            this.nickName.birthday = this.etBirthday.getText().toString().trim();
            this.nickName.phoneNumber = this.etPhoneNumber.getText().toString().trim();
            this.nickName.height = this.etHeight.getText().toString().trim();
            this.nickName.weight = this.etWeight.getText().toString().trim();
            if (this.isEdit) {
                this.dao.update(this.nickName);
            } else {
                this.dao.save(this.nickName);
            }
            Utils.saveUserToCache(this.dao.query(trim));
            recordName(trim, Utils.getDate(), getSex(), trim3, this.etPhoneNumber.getText().toString().trim(), this.etHeight.getText().toString().trim(), this.etWeight.getText().toString().trim(), i);
            setResult(1);
            finish();
        }
    }

    private void recordName(String str, String str2, String str3, String str4, String str5, String str6, String str7, int i) {
        String str8 = this.currentClinicName;
        if (str8 != null && !TextUtils.isEmpty(str8)) {
            DoctorAndPatientBean doctorAndPatientBean = new DoctorAndPatientBean();
            doctorAndPatientBean.f79id = i;
            doctorAndPatientBean.clinicName = this.currentClinicName;
            doctorAndPatientBean.nickName = str;
            doctorAndPatientBean.createDate = str2;
            doctorAndPatientBean.sex = str3;
            doctorAndPatientBean.birthday = str4;
            doctorAndPatientBean.phoneNumber = str5;
            doctorAndPatientBean.height = str6;
            doctorAndPatientBean.weight = str7;
            this.doctorAndPatientDao.save(doctorAndPatientBean);
        }
    }

    private void select_name() {
        NickNameOperateDialog nickNameOperateDialog = new NickNameOperateDialog(this);
        nickNameOperateDialog.setOnOperateListener(new NickNameOperateDialog.OnOperateListener() {
            public void onDelete() {
            }

            public void onChange() {
                final List<NickName> queryAll = UserInfoActivity.this.dao.queryAll();
                NickNameListDialog nickNameListDialog = new NickNameListDialog(UserInfoActivity.this, queryAll);
                nickNameListDialog.setText(UserInfoActivity.this.getString(C0418R.string.select_person_measure));
                nickNameListDialog.setOnNickNameChoose(new NickNameListDialog.OnNickNameChoose() {
                    public void onChoose(AdapterView<?> adapterView, View view, int i, long j) {
                        Utils.saveUserToCache((NickName) queryAll.get(i));
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent();
                        intent.putExtras(bundle);
                        UserInfoActivity.this.setResult(-1, intent);
                        UserInfoActivity.this.finish();
                    }
                });
                nickNameListDialog.show();
            }
        });
        nickNameOperateDialog.show();
    }

    public void onBackPressed() {
        setResult(0);
        finish();
    }

    private String getSex() {
        if (getString(C0418R.string.male).equals(this.etSex.getText().toString().trim())) {
            return "1";
        }
        return getString(C0418R.string.female).equals(this.etSex.getText().toString().trim()) ? "2" : "";
    }
}
