package com.huanghuang.kangshangyiliao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseActivity;
import com.huanghuang.kangshangyiliao.dao.NickNameDao;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.util.Cache;
import com.huanghuang.kangshangyiliao.util.Constants;
import com.huanghuang.kangshangyiliao.util.SystemBarCompat;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import com.huanghuang.kangshangyiliao.widget.NickNameListDialog;
import com.huanghuang.kangshangyiliao.widget.TipToast;
import com.zcw.togglebutton.ToggleButton;
import java.util.List;

@Deprecated
public class SetNickNameActivity extends BaseActivity implements View.OnClickListener {
    private Cache cache = Cache.getInstance();
    private NickNameDao dao;
    /* access modifiers changed from: private */
    @ViewBind.Bind(mo7926id = 2131230844)
    public EditText etNickName;
    @ViewBind.Bind(mo7926id = 2131230985)
    private ToggleButton tbManual;
    /* access modifiers changed from: private */
    @ViewBind.Bind(mo7926id = 2131231020)
    public TextView tvDefaultNickName;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0418R.layout.activity_set_nick_name);
        SystemBarCompat.tint(getWindow(), findViewById(C0418R.C0420id.activity_set_nick_name));
        ViewBind.bind((Activity) this);
        this.dao = new NickNameDao();
        this.tvDefaultNickName.setText(Constants.DEFAULT_NICK_NAME);
        this.tbManual.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            public void onToggle(boolean z) {
                if (z) {
                    SetNickNameActivity.this.etNickName.setEnabled(true);
                } else {
                    SetNickNameActivity.this.etNickName.setEnabled(false);
                }
            }
        });
    }

    public void onClick(View view) {
        String str;
        int id = view.getId();
        if (id == C0418R.C0420id.btConfirm) {
            if (this.tbManual.isToggleOn()) {
                str = this.etNickName.getText().toString().trim();
            } else {
                str = this.tvDefaultNickName.getText().toString().trim();
            }
            if (TextUtils.isEmpty(str)) {
                TipToast tipToast = new TipToast(this);
                tipToast.setImg(C0418R.C0419drawable.ico_tstb);
                tipToast.setText((int) C0418R.string.nick_name_can_not_be_empty);
                tipToast.show();
            } else if (this.dao.exists_name(str, -1)) {
                TipToast tipToast2 = new TipToast(this);
                tipToast2.setImg(C0418R.C0419drawable.ico_tstb);
                tipToast2.setText(str + getString(C0418R.string.exists));
                tipToast2.show();
            } else {
                NickName nickName = new NickName();
                nickName.nickName = str;
                nickName.createDate = Utils.getDate();
                this.dao.save(nickName);
                this.cache.save(Cache.NICK_NAME, str);
                setResult(1);
                finish();
            }
        } else if (id == C0418R.C0420id.ivClose) {
            setResult(0);
            finish();
        } else if (id == C0418R.C0420id.rlDefaultNickName) {
            final List<NickName> defaultNickName = Utils.getDefaultNickName();
            NickNameListDialog nickNameListDialog = new NickNameListDialog(this, defaultNickName);
            nickNameListDialog.setOnNickNameChoose(new NickNameListDialog.OnNickNameChoose() {
                public void onChoose(AdapterView<?> adapterView, View view, int i, long j) {
                    SetNickNameActivity.this.tvDefaultNickName.setText(((NickName) defaultNickName.get(i)).nickName);
                }
            });
            nickNameListDialog.show();
        }
    }

    public void onBackPressed() {
        setResult(0);
        finish();
    }
}
