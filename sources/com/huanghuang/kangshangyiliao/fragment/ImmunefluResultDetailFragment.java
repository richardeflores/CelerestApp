package com.huanghuang.kangshangyiliao.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.AppBaseFragment;
import com.huanghuang.kangshangyiliao.bean.ImmuFluoResult;
import com.huanghuang.kangshangyiliao.dao.bean.Immunofluorescence;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import com.huanghuang.kangshangyiliao.util.Age;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;

public class ImmunefluResultDetailFragment extends AppBaseFragment {
    private Age age = new Age();
    private String age_phase;
    private Immunofluorescence bloodFat;
    private byte[] data;
    private NickName nickName = Utils.getUserInfo();
    private View rootView;
    @ViewBind.Bind(mo7926id = 2131231013)
    private TextView tvCRP;
    @ViewBind.Bind(mo7926id = 2131231019)
    private TextView tvDate;
    @ViewBind.Bind(mo7926id = 2131231051)
    private TextView tvNickName;
    @ViewBind.Bind(mo7926id = 2131231053)
    private TextView tvPCT;
    @ViewBind.Bind(mo7926id = 2131231059)
    private TextView tvSAA;
    private TextView[] tvs;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.bloodFat = (Immunofluorescence) getArguments().getSerializable("data");
        Immunofluorescence immunofluorescence = this.bloodFat;
        if (immunofluorescence != null) {
            this.data = Utils.toByteArray(immunofluorescence.data);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.fragment_immuneflu_result_detail, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        this.tvs = new TextView[]{this.tvCRP, this.tvSAA, this.tvPCT};
        showResult();
        return this.rootView;
    }

    private void showResult() {
        if (Utils.getUserInfo() != null) {
            this.tvNickName.setText(this.bloodFat.nickname);
        }
        this.tvDate.setText(this.bloodFat.getCreateDate());
        ImmuFluoResult parseImmuFluoResult = Utils.parseImmuFluoResult(this.data);
        String str = parseImmuFluoResult.stringFrst0;
        int[] immuFlorAbnormal = Utils.getImmuFlorAbnormal(parseImmuFluoResult);
        if (str.equals(".00")) {
            TextView textView = this.tvs[0];
            textView.setText(Utils.float2Point(parseImmuFluoResult.Frst0) + "");
        } else {
            this.tvs[0].setText(str);
        }
        TextView textView2 = this.tvs[1];
        textView2.setText(Utils.float2Point(parseImmuFluoResult.Frst1) + "");
        TextView textView3 = this.tvs[2];
        textView3.setText(Utils.float2Point(parseImmuFluoResult.Frst2) + "");
        int i = 0;
        while (true) {
            TextView[] textViewArr = this.tvs;
            if (i < textViewArr.length) {
                if (immuFlorAbnormal[i] != 0) {
                    ((ViewGroup) textViewArr[i].getParent()).setBackgroundColor(Utils.getAbnormalBackgroundColor());
                }
                if (immuFlorAbnormal[i] == -1) {
                    Drawable drawable = getResources().getDrawable(C0418R.C0419drawable.ico_xjt);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, drawable, (Drawable) null);
                } else if (immuFlorAbnormal[i] == 1) {
                    Drawable drawable2 = getResources().getDrawable(C0418R.C0419drawable.ico_sjt);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                    this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, drawable2, (Drawable) null);
                }
                i++;
            } else {
                return;
            }
        }
    }
}
