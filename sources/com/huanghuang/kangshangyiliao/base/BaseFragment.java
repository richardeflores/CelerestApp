package com.huanghuang.kangshangyiliao.base;

import android.content.Context;
import android.os.Bundle;
import android.support.p000v4.app.Fragment;
import com.huanghuang.kangshangyiliao.util.Parameter;

public class BaseFragment extends Fragment implements Parameter {
    protected Context context;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.context = getActivity();
    }
}
