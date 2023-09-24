package com.huanghuang.kangshangyiliao.adapter;

import android.os.Bundle;
import android.support.p000v4.app.Fragment;
import android.support.p000v4.app.FragmentManager;
import android.support.p000v4.app.FragmentStatePagerAdapter;
import com.huanghuang.kangshangyiliao.fragment.BloodFatFragment_bf;
import com.huanghuang.kangshangyiliao.fragment.BloodFatFragment_dx;
import com.huanghuang.kangshangyiliao.fragment.BloodFatFragment_kf;
import com.huanghuang.kangshangyiliao.fragment.BloodFatFragment_lf_three;
import com.huanghuang.kangshangyiliao.fragment.BloodFatFragment_xx;
import java.util.ArrayList;
import java.util.List;

public class ProteinFragmentAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> list = new ArrayList();

    public int getCount() {
        return 5;
    }

    public ProteinFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.list.add(new BloodFatFragment_bf());
        this.list.add(new BloodFatFragment_lf_three());
        this.list.add(new BloodFatFragment_kf());
        this.list.add(new BloodFatFragment_dx());
        this.list.add(new BloodFatFragment_xx());
    }

    public Fragment getItem(int i) {
        Fragment fragment = this.list.get(i);
        fragment.setArguments(new Bundle());
        return fragment;
    }
}
