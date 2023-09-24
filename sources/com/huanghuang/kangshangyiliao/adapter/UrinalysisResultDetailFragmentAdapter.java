package com.huanghuang.kangshangyiliao.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import com.huanghuang.kangshangyiliao.dao.UrinalysisDao;
import com.huanghuang.kangshangyiliao.dao.bean.Urinalysis;
import com.huanghuang.kangshangyiliao.fragment.UrinalysisResultDetailFragment;
import java.util.List;

public class UrinalysisResultDetailFragmentAdapter extends FragmentStatePagerAdapter {
    private Activity activity;
    private Fragment currentFragment;
    private UrinalysisDao dao = new UrinalysisDao();
    List<Urinalysis> data = this.dao.queryAll();

    public int getItemPosition(Object obj) {
        return -2;
    }

    public UrinalysisResultDetailFragmentAdapter(FragmentManager fragmentManager, Activity activity2) {
        super(fragmentManager);
        this.activity = activity2;
    }

    public Fragment getItem(int i) {
        UrinalysisResultDetailFragment urinalysisResultDetailFragment = new UrinalysisResultDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", this.data.get(i));
        urinalysisResultDetailFragment.setArguments(bundle);
        return urinalysisResultDetailFragment;
    }

    public int getCount() {
        return this.data.size();
    }

    public void refresh() {
        this.data = this.dao.queryAll();
        if (getCount() == 0) {
            this.activity.finish();
        }
        notifyDataSetChanged();
    }

    public void delete(int i) {
        this.dao.delete(this.data.get(i).f85id);
        refresh();
    }

    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        this.currentFragment = (Fragment) obj;
        super.setPrimaryItem(viewGroup, i, obj);
    }

    public Fragment getCurrentFragment() {
        return this.currentFragment;
    }
}
