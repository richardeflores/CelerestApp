package com.huanghuang.kangshangyiliao.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.widget.ChoseDateDialog;

public class CommonDialog extends Dialog {
    /* access modifiers changed from: private */
    public String age;
    /* access modifiers changed from: private */
    public String birthday;
    private Button btn_cancel;
    private Button btn_sure;
    Context context;
    /* access modifiers changed from: private */
    public EditText etBirthday;
    private EditText et_age;
    private EditText et_id;
    private EditText et_name;
    /* access modifiers changed from: private */

    /* renamed from: id */
    public String f117id;
    /* access modifiers changed from: private */
    public String name;
    public OnClickBottomListener onClickBottomListener;
    private RadioButton rb_man;
    private RadioButton rb_women;

    /* renamed from: rg */
    private RadioGroup f118rg;
    /* access modifiers changed from: private */
    public String sex;
    private TextView title;

    public interface OnClickBottomListener {
        void onNegtiveClick();

        void onPositiveClick(String str, String str2, String str3, String str4, String str5);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C0418R.layout.dialog_layout);
        setCanceledOnTouchOutside(false);
        initView();
        initListener();
    }

    /* access modifiers changed from: private */
    public boolean initData() {
        this.f117id = this.et_id.getText().toString().trim();
        this.name = this.et_name.getText().toString().trim();
        this.age = this.et_age.getText().toString().trim();
        this.birthday = this.etBirthday.getText().toString().trim();
        if (TextUtils.isEmpty(this.et_id.getText().toString().trim())) {
            Context context2 = this.context;
            Toast.makeText(context2, context2.getString(C0418R.string.commdialog_toast_id), 1).show();
            return false;
        } else if (Integer.parseInt(this.f117id) > 9999) {
            Context context3 = this.context;
            Toast.makeText(context3, context3.getString(C0418R.string.commdialog_toast_id_9999), 1).show();
            return false;
        } else if (TextUtils.isEmpty(this.et_name.getText().toString().trim())) {
            Context context4 = this.context;
            Toast.makeText(context4, context4.getString(C0418R.string.commdialog_toast_name), 1).show();
            return false;
        } else if (getStrLength(this.et_name.getText().toString().trim()) > 8) {
            Context context5 = this.context;
            Toast.makeText(context5, context5.getString(C0418R.string.commdialog_toast_name_length), 1).show();
            return false;
        } else if (TextUtils.isEmpty(this.et_age.getText().toString().trim())) {
            Context context6 = this.context;
            Toast.makeText(context6, context6.getString(C0418R.string.commdialog_toast_name_age), 1).show();
            return false;
        } else if (TextUtils.isEmpty(this.birthday)) {
            Context context7 = this.context;
            Toast.makeText(context7, context7.getString(C0418R.string.commdialog_toast_name_birth), 1).show();
            return false;
        } else if (!this.rb_man.isChecked() && !this.rb_women.isChecked()) {
            Context context8 = this.context;
            Toast.makeText(context8, context8.getString(C0418R.string.commdialog_toast_name_sex), 1).show();
            return false;
        } else if (this.rb_man.isChecked()) {
            this.sex = "0";
            return true;
        } else {
            this.sex = "1";
            return true;
        }
    }

    private void initListener() {
        this.btn_sure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (CommonDialog.this.onClickBottomListener != null && CommonDialog.this.initData()) {
                    CommonDialog.this.onClickBottomListener.onPositiveClick(CommonDialog.this.f117id, CommonDialog.this.name, CommonDialog.this.age, CommonDialog.this.sex, CommonDialog.this.birthday);
                }
            }
        });
        this.btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (CommonDialog.this.onClickBottomListener != null) {
                    CommonDialog.this.onClickBottomListener.onNegtiveClick();
                }
            }
        });
        this.f118rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case C0418R.C0420id.rb_man:
                        String unused = CommonDialog.this.sex = "0";
                        return;
                    case C0418R.C0420id.rb_women:
                        String unused2 = CommonDialog.this.sex = "1";
                        return;
                    default:
                        return;
                }
            }
        });
    }

    private void initView() {
        this.et_id = (EditText) findViewById(C0418R.C0420id.et_id);
        this.et_name = (EditText) findViewById(C0418R.C0420id.et_name);
        this.et_age = (EditText) findViewById(C0418R.C0420id.et_age);
        this.title = (TextView) findViewById(C0418R.C0420id.title);
        this.f118rg = (RadioGroup) findViewById(C0418R.C0420id.f55rg);
        this.rb_man = (RadioButton) findViewById(C0418R.C0420id.rb_man);
        this.rb_women = (RadioButton) findViewById(C0418R.C0420id.rb_women);
        this.btn_cancel = (Button) findViewById(C0418R.C0420id.btn_cancel);
        this.btn_sure = (Button) findViewById(C0418R.C0420id.btn_sure);
        this.btn_sure.setText(this.context.getString(C0418R.string.confirm));
        this.btn_cancel.setText(this.context.getString(C0418R.string.cancel));
        this.title.setText(this.context.getString(C0418R.string.input_patient_info));
        this.etBirthday = (EditText) findViewById(C0418R.C0420id.etBirthday);
        findViewById(C0418R.C0420id.llBirthday).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ChoseDateDialog choseDateDialog = new ChoseDateDialog(CommonDialog.this.context);
                choseDateDialog.setOnOperateListener(new ChoseDateDialog.OnOperateListener() {
                    public void onConfirm(String str) {
                        CommonDialog.this.etBirthday.setText(str);
                    }
                });
                choseDateDialog.show();
            }
        });
        this.etBirthday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ChoseDateDialog choseDateDialog = new ChoseDateDialog(CommonDialog.this.context);
                choseDateDialog.setOnOperateListener(new ChoseDateDialog.OnOperateListener() {
                    public void onConfirm(String str) {
                        CommonDialog.this.etBirthday.setText(str);
                    }
                });
                choseDateDialog.show();
            }
        });
    }

    public CommonDialog(Context context2) {
        super(context2);
        this.context = context2;
    }

    public CommonDialog(Context context2, int i) {
        super(context2, i);
    }

    protected CommonDialog(Context context2, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context2, z, onCancelListener);
    }

    public CommonDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener2) {
        this.onClickBottomListener = onClickBottomListener2;
        return this;
    }

    public int getStrLength(String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            int codePointAt = Character.codePointAt(str, i2);
            i = (codePointAt < 0 || codePointAt > 255) ? i + 2 : i + 1;
        }
        return i;
    }
}
