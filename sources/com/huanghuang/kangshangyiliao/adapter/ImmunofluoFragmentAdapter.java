package com.huanghuang.kangshangyiliao.adapter;

import android.os.Bundle;
import android.support.p000v4.app.Fragment;
import android.support.p000v4.app.FragmentManager;
import android.support.p000v4.app.FragmentStatePagerAdapter;
import com.huanghuang.kangshangyiliao.fragment.ImmunofluoFragment_crp;
import java.util.ArrayList;
import java.util.List;

public class ImmunofluoFragmentAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> list = new ArrayList();

    public int getCount() {
        return 1;
    }

    public ImmunofluoFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.list.add(new ImmunofluoFragment_crp());
    }

    public Fragment getItem(int i) {
        Fragment fragment = this.list.get(i);
        fragment.setArguments(new Bundle());
        return fragment;
    }
}
