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
import com.huanghuang.kangshangyiliao.base.KangShangApplication;
import com.huanghuang.kangshangyiliao.bean.BloodFatResult;
import com.huanghuang.kangshangyiliao.event.MessageEvent;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import java.io.PrintStream;
import java.util.Arrays;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BloodFatFragment_bf extends BaseFragment {
    String age_phase;
    byte[] currentData;
    private View rootView;
    @ViewBind.Bind(mo7926id = 2131230999)
    private TextView tvAI_Scope;
    @ViewBind.Bind(mo7926id = 2131231026)
    private TextView tvGLU;
    @ViewBind.Bind(mo7926id = 2131231029)
    private TextView tvHDL_C;
    @ViewBind.Bind(mo7926id = 2131231032)
    private TextView tvHLDL_C_Scope;
    @ViewBind.Bind(mo7926id = 2131231037)
    private TextView tvLDL_C;
    @ViewBind.Bind(mo7926id = 2131231038)
    private TextView tvLDL_C_Scope;
    @ViewBind.Bind(mo7926id = 2131231063)
    private TextView tvTC;
    @ViewBind.Bind(mo7926id = 2131231064)
    private TextView tvTC_Scope;
    @ViewBind.Bind(mo7926id = 2131231065)
    private TextView tvTG;
    @ViewBind.Bind(mo7926id = 2131231066)
    private TextView tvTG_Scope;
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
        getArguments();
        EventBus.getDefault().register(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.include_blood_fat_result, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        this.tvs = new TextView[]{this.tvTC, this.tvTG, this.tvHDL_C, this.tvLDL_C, this.tvGLU};
        if (KangShangApplication.defaultNumber == 0) {
            setUnitDefault();
        } else {
            setUnit();
        }
        return this.rootView;
    }

    public void setData(byte[] bArr) {
        this.currentData = bArr;
        setPageData(bArr);
        PrintStream printStream = System.out;
        printStream.println("currentData: " + Arrays.toString(this.currentData));
    }

    private void setPageData(byte[] bArr) {
        int[] iArr;
        clearResult();
        BloodFatResult parseBloodFatData = Utils.parseBloodFatData(bArr);
        int i = parseBloodFatData.units;
        PrintStream printStream = System.out;
        printStream.println("unitsunits:" + i);
        String[] bloodFatRealValueText02 = Utils.getBloodFatRealValueText02(parseBloodFatData, i);
        if (i == 0) {
            iArr = Utils.getBloodFatAbnormal(parseBloodFatData, this.age_phase);
            setUnitDefault();
        } else {
            setUnit();
            iArr = Utils.getBloodFatAbnormal02(parseBloodFatData, this.age_phase);
        }
        for (int i2 = 0; i2 < bloodFatRealValueText02.length; i2++) {
            showJT(iArr[i2], i2);
        }
        for (int i3 = 0; i3 < bloodFatRealValueText02.length; i3++) {
            if (i3 == 0) {
                this.tvs[i3].setText(bloodFatRealValueText02[3]);
            } else if (i3 == 1) {
                this.tvs[i3].setText(bloodFatRealValueText02[1]);
            } else if (i3 == 2) {
                this.tvs[i3].setText(bloodFatRealValueText02[2]);
            } else if (i3 == 3) {
                this.tvs[i3].setText(bloodFatRealValueText02[4]);
            } else if (i3 == 4) {
                this.tvs[i3].setText(bloodFatRealValueText02[0]);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        System.out.println("onMessageEvent: bf");
        if (messageEvent.getMessage().equals("01")) {
            setUnit();
        } else {
            setUnitDefault();
        }
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
        String str = (String) this.tvTC.getText();
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
        String str = (String) this.tvTC.getText();
    }

    public String getUnit() {
        return (String) this.tv_unit_01.getText();
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
