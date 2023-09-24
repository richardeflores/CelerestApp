package com.huanghuang.kangshangyiliao.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.AppBaseFragment;
import com.huanghuang.kangshangyiliao.bean.POCTResult;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.dao.bean.POCT;
import com.huanghuang.kangshangyiliao.util.Age;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;

public class POCTResultDetailFragment extends AppBaseFragment {
    private Age age = new Age();
    private String age_phase;
    private byte[] data;
    private NickName nickName = Utils.getUserInfo();
    private POCT poct;
    private View rootView;
    @ViewBind.Bind(mo7926id = 2131231005)
    private TextView tvBAS;
    @ViewBind.Bind(mo7926id = 2131231006)
    private TextView tvBASP;
    @ViewBind.Bind(mo7926id = 2131231019)
    private TextView tvDate;
    @ViewBind.Bind(mo7926id = 2131231023)
    private TextView tvEOS;
    @ViewBind.Bind(mo7926id = 2131231024)
    private TextView tvEOSP;
    @ViewBind.Bind(mo7926id = 2131231040)
    private TextView tvLYMP;
    @ViewBind.Bind(mo7926id = 2131231041)
    private TextView tvLYMPP;
    @ViewBind.Bind(mo7926id = 2131231045)
    private TextView tvMON;
    @ViewBind.Bind(mo7926id = 2131231046)
    private TextView tvMONP;
    @ViewBind.Bind(mo7926id = 2131231048)
    private TextView tvNEUI;
    @ViewBind.Bind(mo7926id = 2131231049)
    private TextView tvNEUTP;
    @ViewBind.Bind(mo7926id = 2131231051)
    private TextView tvNickName;
    @ViewBind.Bind(mo7926id = 2131231074)
    private TextView tvWBC;
    @ViewBind.Bind(mo7926id = 2131231075)
    private TextView tvWBC2;
    @ViewBind.Bind(mo7926id = 2131231076)
    private TextView tvWBC_Scope;
    @ViewBind.Bind(mo7926id = 2131231077)
    private TextView tvWBC_unit;
    private TextView[] tvs;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.poct = (POCT) getArguments().getSerializable("data");
        POCT poct2 = this.poct;
        if (poct2 != null) {
            this.data = Utils.toByteArray(poct2.data);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.fragment_poct_result_detail, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        this.tvs = new TextView[]{this.tvWBC, this.tvLYMP, this.tvMON, this.tvNEUI, this.tvEOS, this.tvBAS, this.tvLYMPP, this.tvMONP, this.tvNEUTP, this.tvEOSP, this.tvBASP};
        if (this.nickName.birthday != null) {
            this.age_phase = Age.age_phase(this.nickName.birthday);
            if ("0".equals(this.age_phase)) {
                this.tvWBC_unit.setText(C0418R.string.wbc_unit);
                this.tvWBC_Scope.setText(C0418R.string.wbc_newborn_reference_range);
            } else if ("1".equals(this.age_phase)) {
                this.tvWBC_unit.setText(C0418R.string.wbc_unit);
                this.tvWBC_Scope.setText(C0418R.string.wbc_baby_reference_range);
            } else if ("2".equals(this.age_phase)) {
                this.tvWBC_unit.setText(C0418R.string.wbc_unit);
                this.tvWBC_Scope.setText(C0418R.string.wbc_children_reference_range);
            } else {
                this.tvWBC_unit.setText(C0418R.string.wbc_man_unit);
                this.tvWBC_Scope.setText(C0418R.string.wbc_reference_range);
            }
        }
        showResult();
        return this.rootView;
    }

    private void showResult() {
        if (Utils.getUserInfo() != null) {
            this.tvNickName.setText(this.poct.nickname);
        }
        this.tvDate.setText(this.poct.getCreateDate());
        POCTResult parsePOCTData = Utils.parsePOCTData(this.data);
        String[] pOCTRealValueText = Utils.getPOCTRealValueText(parsePOCTData);
        int[] pOCTAbnormal = Utils.getPOCTAbnormal(parsePOCTData, this.age_phase);
        this.tvWBC2.setText(pOCTRealValueText[0]);
        for (int i = 0; i < pOCTRealValueText.length; i++) {
            this.tvs[i].setText(pOCTRealValueText[i]);
            if (pOCTAbnormal[i] != 0) {
                ((ViewGroup) this.tvs[i].getParent()).setBackgroundColor(Utils.getAbnormalBackgroundColor());
            }
            if (pOCTAbnormal[i] == -1) {
                Drawable drawable = getResources().getDrawable(C0418R.C0419drawable.ico_xjt);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, drawable, (Drawable) null);
            } else if (pOCTAbnormal[i] == 1) {
                Drawable drawable2 = getResources().getDrawable(C0418R.C0419drawable.ico_sjt);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, drawable2, (Drawable) null);
            }
        }
    }
}
