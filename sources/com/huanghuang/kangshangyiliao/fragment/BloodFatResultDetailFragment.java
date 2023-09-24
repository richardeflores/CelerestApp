package com.huanghuang.kangshangyiliao.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.AppBaseFragment;
import com.huanghuang.kangshangyiliao.bean.BloodFatResult;
import com.huanghuang.kangshangyiliao.dao.bean.BloodFat;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.util.Age;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;

public class BloodFatResultDetailFragment extends AppBaseFragment {
    private Age age = new Age();
    private String age_phase;
    private BloodFat bloodFat;
    private byte[] data;
    private NickName nickName = Utils.getUserInfo();
    private View rootView;
    @ViewBind.Bind(mo7926id = 2131230999)
    private TextView tvAI_Scope;
    @ViewBind.Bind(mo7926id = 2131231019)
    private TextView tvDate;
    @ViewBind.Bind(mo7926id = 2131231026)
    private TextView tvGLU;
    @ViewBind.Bind(mo7926id = 2131231032)
    private TextView tvHLDL_C_Scope;
    @ViewBind.Bind(mo7926id = 2131231037)
    private TextView tvLDL_C;
    @ViewBind.Bind(mo7926id = 2131231038)
    private TextView tvLDL_C_Scope;
    @ViewBind.Bind(mo7926id = 2131231051)
    private TextView tvNickName;
    @ViewBind.Bind(mo7926id = 2131231063)
    private TextView tvTC;
    @ViewBind.Bind(mo7926id = 2131231064)
    private TextView tvTC_Scope;
    @ViewBind.Bind(mo7926id = 2131231065)
    private TextView tvTG;
    @ViewBind.Bind(mo7926id = 2131231066)
    private TextView tvTG_Scope;
    @ViewBind.Bind(mo7926id = 2131231029)
    private TextView tvVLDL_C;
    @ViewBind.Bind(mo7926id = 2131231096)
    private TextView tv_unit_01;
    @ViewBind.Bind(mo7926id = 2131231097)
    private TextView tv_unit_02;
    @ViewBind.Bind(mo7926id = 2131231098)
    private TextView tv_unit_03;
    @ViewBind.Bind(mo7926id = 2131231099)
    private TextView tv_unit_04;
    @ViewBind.Bind(mo7926id = 2131231100)
    private TextView tv_unit_05;
    private TextView[] tvs;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.bloodFat = (BloodFat) getArguments().getSerializable("data");
        BloodFat bloodFat2 = this.bloodFat;
        if (bloodFat2 != null) {
            this.data = Utils.toByteArray(bloodFat2.data);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.fragment_blood_fat_result_detail, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        this.tvs = new TextView[]{this.tvTC, this.tvTG, this.tvVLDL_C, this.tvLDL_C, this.tvGLU};
        showResult();
        return this.rootView;
    }

    private void showResult() {
        String[] strArr;
        int[] iArr;
        if (Utils.getUserInfo() != null) {
            this.tvNickName.setText(this.bloodFat.nickname);
        }
        this.tvDate.setText(this.bloodFat.getCreateDate());
        BloodFatResult parseBloodFatData = Utils.parseBloodFatData(this.data);
        int i = parseBloodFatData.units;
        if (i == 1) {
            strArr = Utils.getBloodFatRealValueText02(parseBloodFatData, 1);
            setUnit();
        } else {
            strArr = Utils.getBloodFatRealValueText(parseBloodFatData);
            setUnitDefault();
        }
        if (i == 1) {
            iArr = Utils.getBloodFatAbnormal02(parseBloodFatData, this.age_phase);
        } else {
            iArr = Utils.getBloodFatAbnormal(parseBloodFatData, this.age_phase);
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            showJT(iArr[i2], i2);
        }
        for (int i3 = 0; i3 < strArr.length; i3++) {
            if (i3 == 0) {
                this.tvs[i3].setText(strArr[3]);
            } else if (i3 == 1) {
                this.tvs[i3].setText(strArr[1]);
            } else if (i3 == 2) {
                this.tvs[i3].setText(strArr[2]);
            } else if (i3 == 3) {
                this.tvs[i3].setText(strArr[4]);
            } else if (i3 == 4) {
                this.tvs[i3].setText(strArr[0]);
            }
        }
    }

    private void showJT(int i, int i2) {
        if (i != 0) {
            ((ViewGroup) this.tvs[i2].getParent()).setBackgroundColor(Utils.getAbnormalBackgroundColor());
        }
        if (i == -1) {
            Drawable drawable = getResources().getDrawable(C0418R.C0419drawable.ico_xjt);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.tvs[i2].setCompoundDrawables((Drawable) null, (Drawable) null, drawable, (Drawable) null);
        } else if (i == 1) {
            Drawable drawable2 = getResources().getDrawable(C0418R.C0419drawable.ico_sjt);
            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            this.tvs[i2].setCompoundDrawables((Drawable) null, (Drawable) null, drawable2, (Drawable) null);
        }
    }

    public void setUnit() {
        this.tv_unit_01.setText(getString(C0418R.string.bloodfat_uit));
        this.tv_unit_02.setText(getString(C0418R.string.bloodfat_uit));
        this.tv_unit_03.setText(getString(C0418R.string.bloodfat_uit));
        this.tv_unit_04.setText(getString(C0418R.string.bloodfat_uit));
        this.tv_unit_05.setText(getString(C0418R.string.bloodfat_uit));
        this.tvTC_Scope.setText(getString(C0418R.string.tc_reference_range_02));
        this.tvTG_Scope.setText(getString(C0418R.string.tg_reference_range_02));
        this.tvHLDL_C_Scope.setText(getString(C0418R.string.hdl_c_reference_range_02));
        this.tvLDL_C_Scope.setText(getString(C0418R.string.ldl_c_reference_range_02));
        this.tvAI_Scope.setText(getString(C0418R.string.glu_reference_range_02));
    }

    public void setUnitDefault() {
        this.tv_unit_01.setText(getString(C0418R.string.mmol));
        this.tv_unit_02.setText(getString(C0418R.string.mmol));
        this.tv_unit_03.setText(getString(C0418R.string.mmol));
        this.tv_unit_04.setText(getString(C0418R.string.mmol));
        this.tv_unit_05.setText(getString(C0418R.string.mmol));
        this.tvTC_Scope.setText(getString(C0418R.string.tc_reference_range));
        this.tvTG_Scope.setText(getString(C0418R.string.tg_reference_range));
        this.tvHLDL_C_Scope.setText(getString(C0418R.string.hdl_c_reference_range));
        this.tvLDL_C_Scope.setText(getString(C0418R.string.ldl_c_reference_range));
        this.tvAI_Scope.setText(getString(C0418R.string.glu_reference_range));
    }
}
