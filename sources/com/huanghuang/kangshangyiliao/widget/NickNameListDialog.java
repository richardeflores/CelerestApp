package com.huanghuang.kangshangyiliao.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.adapter.NickNameListAdapter;
import com.huanghuang.kangshangyiliao.base.BaseDialog;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import java.util.List;

public class NickNameListDialog extends BaseDialog implements View.OnClickListener {
    private Context context;
    private List<NickName> data;
    @ViewBind.Bind(mo7926id = 2131230911)
    private ListView lvNickName;
    /* access modifiers changed from: private */
    public OnNickNameChoose nickNameChoose = new OnNickNameChoose() {
        public void onChoose(AdapterView<?> adapterView, View view, int i, long j) {
        }
    };
    private String text;
    @ViewBind.Bind(mo7926id = 2131231068)
    private TextView tvTitle;

    public interface OnNickNameChoose {
        void onChoose(AdapterView<?> adapterView, View view, int i, long j);
    }

    public NickNameListDialog(Context context2, List<NickName> list) {
        this.context = context2;
        this.data = list;
        this.dialog = new AlertDialog.Builder(context2).create();
        this.dialog.setCancelable(false);
    }

    public void setText(String str) {
        this.text = str;
    }

    public void show() {
        this.dialog.show();
        Window window = this.dialog.getWindow();
        window.setContentView(C0418R.layout.widget_dialog_nick_name_list);
        window.setBackgroundDrawableResource(17170445);
        ViewBind.bind(this, window.getDecorView());
        this.lvNickName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                NickNameListDialog.this.dismiss();
                NickNameListDialog.this.nickNameChoose.onChoose(adapterView, view, i, j);
            }
        });
        this.lvNickName.setAdapter(new NickNameListAdapter(this.context, this.data));
        if (!TextUtils.isEmpty(this.text)) {
            this.tvTitle.setText(this.text);
        }
    }

    public void onClick(View view) {
        if (view.getId() == C0418R.C0420id.btClose) {
            dismiss();
        }
    }

    public void setOnNickNameChoose(OnNickNameChoose onNickNameChoose) {
        if (onNickNameChoose != null) {
            this.nickNameChoose = onNickNameChoose;
        }
    }
}
