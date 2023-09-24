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
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;

public class BloodFatResultDetailFragment_lf extends AppBaseFragment {
    private BloodFat bloodFat;
    private byte[] data;
    private View rootView;
    @ViewBind.Bind(mo7926id = 2131231000)
    private TextView tvAlb;
    @ViewBind.Bind(mo7926id = 2131231002)
    private TextView tvAlp;
    @ViewBind.Bind(mo7926id = 2131231003)
    private TextView tvAlt;
    @ViewBind.Bind(mo7926id = 2131231004)
    private TextView tvAst;
    @ViewBind.Bind(mo7926id = 2131231019)
    private TextView tvDate;
    @ViewBind.Bind(mo7926id = 2131231051)
    private TextView tvNickName;
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
            this.rootView = layoutInflater.inflate(C0418R.layout.fragment_blood_fat_result_detail_lf, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        this.tvs = new TextView[]{this.tvAlp, this.tvAlt, this.tvAst, this.tvAlb};
        showResult();
        return this.rootView;
    }

    private void showResult() {
        if (Utils.getUserInfo() != null) {
            this.tvNickName.setText(this.bloodFat.nickname);
        }
        this.tvDate.setText(this.bloodFat.getCreateDate());
        BloodFatResult parseBloodFatData_lf = Utils.parseBloodFatData_lf(this.data);
        int i = 0;
        while (true) {
            TextView[] textViewArr = this.tvs;
            if (i >= textViewArr.length) {
                break;
            }
            if (i == 3) {
                textViewArr[i].setText(Utils.floatPointNoRound(parseBloodFatData_lf.finalValues[i]));
            } else {
                TextView textView = textViewArr[i];
                textView.setText(((int) parseBloodFatData_lf.finalValues[i]) + "");
            }
            i++;
        }
        int[] bloodFatAbnormal_lf = Utils.getBloodFatAbnormal_lf(parseBloodFatData_lf, "");
        for (int i2 = 0; i2 < this.tvs.length; i2++) {
            showJT(bloodFatAbnormal_lf[i2], i2);
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
}
