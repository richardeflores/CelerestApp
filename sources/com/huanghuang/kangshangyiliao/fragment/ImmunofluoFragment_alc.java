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
import com.huanghuang.kangshangyiliao.bean.ImmuFluoResult;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import java.io.PrintStream;
import java.math.BigDecimal;

public class ImmunofluoFragment_alc extends BaseFragment {
    private View rootView;
    @ViewBind.Bind(mo7926id = 2131231001)
    private TextView tvAlc;
    @ViewBind.Bind(mo7926id = 2131231089)
    private TextView tv_range;
    private TextView[] tvs;

    private void initData(ImmuFluoResult immuFluoResult) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.include_immunoflu_alc, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        this.tvs = new TextView[]{this.tvAlc};
        return this.rootView;
    }

    public void setData(byte[] bArr) {
        clearResult();
        ImmuFluoResult parseImmuFluoResult = Utils.parseImmuFluoResult(bArr);
        String str = parseImmuFluoResult.stringFrst0;
        PrintStream printStream = System.out;
        printStream.println("immuFluoResultimmuFluoResult:stringFrst0____" + str);
        PrintStream printStream2 = System.out;
        printStream2.println("immuFluoResultimmuFluoResult:Frst0____" + parseImmuFluoResult.Frst0);
        PrintStream printStream3 = System.out;
        printStream3.println("immuFluoResultimmuFluoResult:time年____" + parseImmuFluoResult.time[0]);
        PrintStream printStream4 = System.out;
        printStream4.println("immuFluoResultimmuFluoResult:time月____" + parseImmuFluoResult.time[1]);
        PrintStream printStream5 = System.out;
        printStream5.println("immuFluoResultimmuFluoResult:time日____" + parseImmuFluoResult.time[2]);
        initData(parseImmuFluoResult);
    }

    private float returnFloat(float f, int i) {
        return new BigDecimal((double) f).setScale(i, 4).floatValue();
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

    private void judgeResult(String str) {
        Float valueOf = Float.valueOf(str);
        if (valueOf.floatValue() < 4.0f) {
            this.tvAlc.setText(getString(C0418R.string.alc_range_min));
        } else if (valueOf.floatValue() > 8.0f) {
            this.tvAlc.setText(getString(C0418R.string.alc_range_max));
        } else {
            this.tvAlc.setText(str);
        }
    }
}
