package com.huanghuang.kangshangyiliao.fragment;

import android.support.p000v4.app.Fragment;
import com.huanghuang.kangshangyiliao.C0418R;

public class FragmentFactory {
    public static Fragment getMainByIndex(int i) {
        switch (i) {
            case C0418R.C0420id.RadioButton_My:
                return new MyFragment();
            case C0418R.C0420id.RadioButton_Report:
                return new AnalyzeResultFragment();
            case C0418R.C0420id.rbAnalyze:
                return new AnalyzeFragment();
            default:
                return null;
        }
    }
}
