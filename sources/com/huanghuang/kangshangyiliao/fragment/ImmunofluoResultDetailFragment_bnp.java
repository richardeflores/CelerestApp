package com.huanghuang.kangshangyiliao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.AppBaseFragment;
import com.huanghuang.kangshangyiliao.dao.bean.Immunofluorescence;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;

public class ImmunofluoResultDetailFragment_bnp extends AppBaseFragment {
    private byte[] data;
    private Immunofluorescence immuFluoResult;
    private View rootView;
    @ViewBind.Bind(mo7926id = 2131231009)
    private TextView tvBnp;
    @ViewBind.Bind(mo7926id = 2131231019)
    private TextView tvDate;
    @ViewBind.Bind(mo7926id = 2131231051)
    private TextView tvNickName;
    private TextView[] tvs;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.immuFluoResult = (Immunofluorescence) getArguments().getSerializable("data");
        Immunofluorescence immunofluorescence = this.immuFluoResult;
        if (immunofluorescence != null) {
            this.data = Utils.toByteArray(immunofluorescence.data);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.fragment_immunoflu_reuslt_detail_bnp, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        this.tvs = new TextView[]{this.tvBnp};
        judgeResult();
        return this.rootView;
    }

    private void judgeResult() {
        if (Utils.getUserInfo() != null) {
            this.tvNickName.setText(this.immuFluoResult.nickname);
        }
        this.tvDate.setText(this.immuFluoResult.getCreateDate());
        String str = Utils.parseImmuFluoResult(this.data).stringFrst0;
        Float valueOf = Float.valueOf(str);
        if (valueOf.floatValue() < 10.0f) {
            this.tvBnp.setText(getString(C0418R.string.bnp_range_min));
        } else if (valueOf.floatValue() > 35000.0f) {
            this.tvBnp.setText(getString(C0418R.string.bnp_range_max));
        } else if (valueOf.floatValue() >= 10.0f && valueOf.floatValue() <= 35000.0f) {
            this.tvBnp.setText(str);
        }
    }
}
