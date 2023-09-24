package com.huanghuang.kangshangyiliao.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.AppBaseFragment;
import com.huanghuang.kangshangyiliao.bean.ProteinResult;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.dao.bean.Protein;
import com.huanghuang.kangshangyiliao.util.Age;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;

public class ProteinResultDetailFragment extends AppBaseFragment {
    private String age_phase;
    private byte[] data;
    @ViewBind.Bind(mo7926id = 2131231028)
    private TextView getTvHCT_Scope;
    @ViewBind.Bind(mo7926id = 2131231031)
    private TextView getTvHGB_Scope;
    private NickName nickName = Utils.getUserInfo();
    private Protein protein;
    private View rootView;
    @ViewBind.Bind(mo7926id = 2131231019)
    private TextView tvDate;
    @ViewBind.Bind(mo7926id = 2131231027)
    private TextView tvHCT;
    @ViewBind.Bind(mo7926id = 2131231030)
    private TextView tvHGB;
    @ViewBind.Bind(mo7926id = 2131231051)
    private TextView tvNickName;
    private TextView[] tvs;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.protein = (Protein) getArguments().getSerializable("data");
        Protein protein2 = this.protein;
        if (protein2 != null) {
            this.data = Utils.toByteArray(protein2.data);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.fragment_protein_result_detail, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        this.tvs = new TextView[]{this.tvHGB, this.tvHCT};
        initNumber();
        return this.rootView;
    }

    private void initNumber() {
        if (this.nickName.birthday != null) {
            this.age_phase = Age.age_phase(this.nickName.birthday);
            if ("0".equals(this.age_phase)) {
                this.getTvHGB_Scope.setText(C0418R.string.hgb_newborn_reference_range);
            } else if ("1".equals(this.age_phase)) {
                this.getTvHGB_Scope.setText(C0418R.string.hgb_baby_reference_range);
            } else if ("2".equals(this.age_phase)) {
                this.getTvHGB_Scope.setText(C0418R.string.hgb_children_reference_range);
            } else if ("1".equals(this.nickName.sex)) {
                this.getTvHGB_Scope.setText(C0418R.string.hgb_man_reference_range);
            } else if ("2".equals(this.nickName.sex)) {
                this.getTvHGB_Scope.setText(C0418R.string.hgb_woman_reference_range);
            }
        }
        if ("1".equals(this.nickName.sex)) {
            this.getTvHCT_Scope.setText(C0418R.string.hct_man_reference_range);
        } else if ("2".equals(this.nickName.sex)) {
            this.getTvHCT_Scope.setText(C0418R.string.hct_woman_reference_range);
        }
        showResult();
    }

    private void showResult() {
        if (Utils.getUserInfo() != null) {
            this.tvNickName.setText(this.protein.nickname);
        }
        this.tvDate.setText(this.protein.getCreateDate());
        ProteinResult parseProteinData = Utils.parseProteinData(this.data);
        String[] proteinRealValueText = Utils.getProteinRealValueText(parseProteinData);
        if ("1".equals(this.nickName.sex)) {
            prompt(proteinRealValueText, Utils.getProteinAbnormal_MAN(parseProteinData, this.age_phase));
        } else if ("2".equals(this.nickName.sex)) {
            prompt(proteinRealValueText, Utils.getProteinAbnormal_WOMAN(parseProteinData, this.age_phase));
        }
    }

    private void prompt(String[] strArr, int[] iArr) {
        for (int i = 0; i < strArr.length; i++) {
            if (i == 0) {
                this.tvs[i].setText(Utils.floatPointNoRound(Float.parseFloat(strArr[i]) / 10.0f));
            } else {
                this.tvs[i].setText(strArr[i]);
            }
            if (iArr[i] != 0) {
                ((ViewGroup) this.tvs[i].getParent()).setBackgroundColor(Utils.getAbnormalBackgroundColor());
            }
            if (iArr[i] == -1) {
                Drawable drawable = getResources().getDrawable(C0418R.C0419drawable.ico_xjt);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, drawable, (Drawable) null);
            } else if (iArr[i] == 1) {
                Drawable drawable2 = getResources().getDrawable(C0418R.C0419drawable.ico_sjt);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, drawable2, (Drawable) null);
            }
        }
    }
}
