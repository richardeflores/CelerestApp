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
import com.huanghuang.kangshangyiliao.event.MessageEvent;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BloodFatFragment_kf extends BaseFragment {
    byte[] currentData;
    View rootView;
    @ViewBind.Bind(mo7926id = 2131231011)
    private TextView tvCR;
    @ViewBind.Bind(mo7926id = 2131231032)
    private TextView tvHLDL_C_Scope;
    @ViewBind.Bind(mo7926id = 2131231064)
    private TextView tvTC_Scope;
    @ViewBind.Bind(mo7926id = 2131231066)
    private TextView tvTG_Scope;
    @ViewBind.Bind(mo7926id = 2131231069)
    private TextView tvUA;
    @ViewBind.Bind(mo7926id = 2131231071)
    private TextView tvUrea;
    @ViewBind.Bind(mo7926id = 2131231096)
    private TextView tv_unit_01;
    @ViewBind.Bind(mo7926id = 2131231097)
    private TextView tv_unit_02;
    @ViewBind.Bind(mo7926id = 2131231098)
    private TextView tv_unit_03;
    private TextView[] tvs;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EventBus.getDefault().register(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.include_kidney_function_result, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        this.tvs = new TextView[]{this.tvUA, this.tvCR, this.tvUrea};
        return this.rootView;
    }

    public void setData(byte[] bArr) {
        this.currentData = bArr;
        setPageData(bArr);
    }

    private void setPageData(byte[] bArr) {
        int[] iArr;
        clearResult();
        BloodFatResult parseBloodFatData_sg = Utils.parseBloodFatData_sg(bArr);
        int i = parseBloodFatData_sg.units;
        if (i == 0) {
            TextView textView = this.tvCR;
            textView.setText("" + ((int) parseBloodFatData_sg.finalValues[0]));
            TextView textView2 = this.tvUA;
            textView2.setText("" + ((int) parseBloodFatData_sg.finalValues[1]));
            this.tvUrea.setText(Utils.floatPointNoRound(parseBloodFatData_sg.finalValues[2]));
            setUnitDefault();
        } else {
            this.tvCR.setText(Utils.floatPointNoRound(parseBloodFatData_sg.finalValues_change_unit[0]));
            this.tvUA.setText(Utils.floatPointNoRound(parseBloodFatData_sg.finalValues_change_unit[1]));
            this.tvUrea.setText(Utils.floatPointNoRound(parseBloodFatData_sg.finalValues_change_unit[2]));
            setUnit();
        }
        if (i == 0) {
            iArr = Utils.getBloodFatAbnormal_kf(parseBloodFatData_sg, "");
        } else {
            iArr = Utils.getBloodFatAbnormal_kf02(parseBloodFatData_sg, "");
        }
        for (int i2 = 0; i2 < this.tvs.length; i2++) {
            showJT(iArr[i2], i2);
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
        System.out.println("onMessageEvent: kf");
        if (messageEvent.getMessage().equals("01")) {
            setUnit();
        } else {
            setUnitDefault();
        }
    }

    public void setUnitDefault() {
        this.tv_unit_01.setText(getString(C0418R.string.umol));
        this.tv_unit_02.setText(getString(C0418R.string.umol));
        this.tv_unit_03.setText(getString(C0418R.string.mmol));
        this.tvTC_Scope.setText(getString(C0418R.string.kf_ua));
        this.tvTG_Scope.setText(getString(C0418R.string.kf_cr));
        this.tvHLDL_C_Scope.setText(getString(C0418R.string.kf_urea));
        String str = (String) this.tvUA.getText();
    }

    public void setUnit() {
        this.tv_unit_01.setText(getString(C0418R.string.bloodfat_uit));
        this.tv_unit_02.setText(getString(C0418R.string.bloodfat_uit));
        this.tv_unit_03.setText(getString(C0418R.string.bloodfat_uit));
        this.tvTC_Scope.setText(getString(C0418R.string.kf_ua02));
        this.tvTG_Scope.setText(getString(C0418R.string.kf_cr02));
        this.tvHLDL_C_Scope.setText(getString(C0418R.string.kf_urea02));
        String str = (String) this.tvUA.getText();
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
