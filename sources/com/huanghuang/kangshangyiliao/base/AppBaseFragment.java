package com.huanghuang.kangshangyiliao.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

public class AppBaseFragment extends Fragment {
    protected Context context;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.context = getActivity();
    }
}
