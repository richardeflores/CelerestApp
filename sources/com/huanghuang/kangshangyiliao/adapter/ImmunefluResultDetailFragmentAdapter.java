package com.huanghuang.kangshangyiliao.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import com.huanghuang.kangshangyiliao.dao.ImmunofluorescenceDao;
import com.huanghuang.kangshangyiliao.dao.bean.Immunofluorescence;
import com.huanghuang.kangshangyiliao.fragment.ImmunofluoResultDetailFragment_crp_new;
import com.huanghuang.kangshangyiliao.util.Utils;
import java.util.List;

public class ImmunefluResultDetailFragmentAdapter extends FragmentStatePagerAdapter {
    private Activity activity;
    private Fragment currentFragment;
    private ImmunofluorescenceDao dao = new ImmunofluorescenceDao();
    List<Immunofluorescence> data = this.dao.queryAll();

    public int getItemPosition(Object obj) {
        return -2;
    }

    public ImmunefluResultDetailFragmentAdapter(FragmentManager fragmentManager, Activity activity2) {
        super(fragmentManager);
        this.activity = activity2;
    }

    public Fragment getItem(int i) {
        Utils.toByteArray(this.data.get(i).data);
        ImmunofluoResultDetailFragment_crp_new immunofluoResultDetailFragment_crp_new = new ImmunofluoResultDetailFragment_crp_new();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", this.data.get(i));
        immunofluoResultDetailFragment_crp_new.setArguments(bundle);
        return immunofluoResultDetailFragment_crp_new;
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
        this.dao.delete(this.data.get(i).f80id);
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
