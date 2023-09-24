package com.huanghuang.kangshangyiliao.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.BaseFragment;
import com.huanghuang.kangshangyiliao.bean.BloodFatResult;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;

public class BloodFatFragment_xx extends BaseFragment {
    View rootView;
    @ViewBind.Bind(mo7926id = 2131231003)
    private TextView tvAlt;
    @ViewBind.Bind(mo7926id = 2131231033)
    private TextView tvHb;
    private TextView[] tvs;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.include_blood_donation_result, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        this.tvs = new TextView[]{this.tvAlt, this.tvHb};
        return this.rootView;
    }

    public void setData(byte[] bArr) {
        clearResult();
        BloodFatResult parseBloodFatData_sg = Utils.parseBloodFatData_sg(bArr);
        int i = 0;
        while (true) {
            TextView[] textViewArr = this.tvs;
            if (i >= textViewArr.length) {
                break;
            }
            TextView textView = textViewArr[i];
            textView.setText(((int) parseBloodFatData_sg.finalValues[i]) + "");
            i++;
        }
        int[] bloodFatAbnormal_xx = Utils.getBloodFatAbnormal_xx(parseBloodFatData_sg, "");
        for (int i2 = 0; i2 < this.tvs.length; i2++) {
            showJT(bloodFatAbnormal_xx[i2], i2);
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

    private void clearResult() {
        int i = 0;
        while (true) {
            TextView[] textViewArr = this.tvs;
            if (i < textViewArr.length) {
                textViewArr[i].setText("");
                int parseColor = Color.parseColor("#FFFFFF");
                if (i % 2 == 1) {
                    parseColor = Color.parseColor("#E5F6FF");
                }
                ((ViewGroup) this.tvs[i].getParent()).setBackgroundColor(parseColor);
                this.tvs[i].setCompoundDrawables((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                i++;
            } else {
                return;
            }
        }
    }
}
