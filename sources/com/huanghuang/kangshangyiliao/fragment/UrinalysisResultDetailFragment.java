package com.huanghuang.kangshangyiliao.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.AppBaseFragment;
import com.huanghuang.kangshangyiliao.bean.UrinalysisResult;
import com.huanghuang.kangshangyiliao.dao.bean.Urinalysis;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;

public class UrinalysisResultDetailFragment extends AppBaseFragment {
    private byte[] data;
    private View rootView;
    @ViewBind.Bind(mo7926id = 2131231007)
    private TextView tvBIL;
    @ViewBind.Bind(mo7926id = 2131231008)
    private TextView tvBLD;
    @ViewBind.Bind(mo7926id = 2131231010)
    private TextView tvCA;
    @ViewBind.Bind(mo7926id = 2131231012)
    private TextView tvCRE;
    @ViewBind.Bind(mo7926id = 2131231019)
    private TextView tvDate;
    @ViewBind.Bind(mo7926id = 2131231026)
    private TextView tvGLU;
    @ViewBind.Bind(mo7926id = 2131231036)
    private TextView tvKET;
    @ViewBind.Bind(mo7926id = 2131231039)
    private TextView tvLEU;
    @ViewBind.Bind(mo7926id = 2131231044)
    private TextView tvMA;
    @ViewBind.Bind(mo7926id = 2131231050)
    private TextView tvNIT;
    @ViewBind.Bind(mo7926id = 2131231051)
    private TextView tvNickName;
    @ViewBind.Bind(mo7926id = 2131231056)
    private TextView tvPH;
    @ViewBind.Bind(mo7926id = 2131231058)
    private TextView tvPRO;
    @ViewBind.Bind(mo7926id = 2131231062)
    private TextView tvSG;
    @ViewBind.Bind(mo7926id = 2131231070)
    private TextView tvUBG;
    @ViewBind.Bind(mo7926id = 2131231073)
    private TextView tvVC;
    private TextView[] tvs;
    private Urinalysis urinalysis;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.urinalysis = (Urinalysis) getArguments().getSerializable("data");
        Urinalysis urinalysis2 = this.urinalysis;
        if (urinalysis2 != null) {
            this.data = Utils.toByteArray(urinalysis2.data);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.fragment_urinalysis_result_detail, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        this.tvs = new TextView[]{this.tvLEU, this.tvBLD, this.tvNIT, this.tvKET, this.tvUBG, this.tvBIL, this.tvPRO, this.tvGLU, this.tvPH, this.tvVC, this.tvSG, this.tvMA, this.tvCA, this.tvCRE};
        showResult();
        return this.rootView;
    }

    private void showResult() {
        UrinalysisResult parseUrinalysisData = Utils.parseUrinalysisData(this.data);
        if (Utils.getUserInfo() != null) {
            this.tvNickName.setText(this.urinalysis.nickname);
        }
        this.tvDate.setText(parseUrinalysisData.getDate());
        String[] realValueText = Utils.getRealValueText(parseUrinalysisData);
        int[] urinalysisAbnormal = Utils.getUrinalysisAbnormal(parseUrinalysisData);
        for (int i = 0; i < realValueText.length; i++) {
            this.tvs[i].setText(realValueText[i]);
            if (urinalysisAbnormal[i] != 0) {
                ((ViewGroup) this.tvs[i].getParent()).setBackgroundColor(Utils.getAbnormalBackgroundColor());
            }
            if (i == 8 || i == 10) {
                if (urinalysisAbnormal[i] == -1) {
                    Drawable drawable = getResources().getDrawable(C0418R.C0419drawable.ico_xjt);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, drawable, (Drawable) null);
                } else if (urinalysisAbnormal[i] == 1) {
                    Drawable drawable2 = getResources().getDrawable(C0418R.C0419drawable.ico_sjt);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                    this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, drawable2, (Drawable) null);
                }
            }
        }
        if (this.data.length == 19) {
            ((ViewGroup) this.tvCA.getParent()).setVisibility(8);
            ((ViewGroup) this.tvMA.getParent()).setVisibility(8);
            ((ViewGroup) this.tvCRE.getParent()).setVisibility(8);
            return;
        }
        ((ViewGroup) this.tvCA.getParent()).setVisibility(0);
        ((ViewGroup) this.tvMA.getParent()).setVisibility(0);
        ((ViewGroup) this.tvCRE.getParent()).setVisibility(0);
    }
}
