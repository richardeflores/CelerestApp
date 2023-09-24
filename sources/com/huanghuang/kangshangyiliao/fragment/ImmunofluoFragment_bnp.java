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
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;

public class ImmunofluoFragment_bnp extends BaseFragment {
    private View rootView;
    @ViewBind.Bind(mo7926id = 2131231009)
    private TextView tvBnp;
    private TextView[] tvs;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.include_immunoflu_bnp, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        this.tvs = new TextView[]{this.tvBnp};
        return this.rootView;
    }

    public void setData(byte[] bArr) {
        clearResult();
        judgeResult(Utils.parseImmuFluoResult(bArr).stringFrst0);
    }

    private void judgeResult(String str) {
        Float valueOf = Float.valueOf(str);
        if (valueOf.floatValue() < 10.0f) {
            this.tvBnp.setText(getString(C0418R.string.bnp_range_min));
        } else if (valueOf.floatValue() > 35000.0f) {
            this.tvBnp.setText(getString(C0418R.string.bnp_range_max));
        } else if (valueOf.floatValue() >= 10.0f && valueOf.floatValue() <= 35000.0f) {
            this.tvBnp.setText(str);
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
