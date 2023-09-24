package com.huanghuang.kangshangyiliao.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import com.huanghuang.kangshangyiliao.dao.BloodFatDao;
import com.huanghuang.kangshangyiliao.dao.bean.BloodFat;
import com.huanghuang.kangshangyiliao.fragment.BloodFatResultDetailFragment;
import com.huanghuang.kangshangyiliao.fragment.BloodFatResultDetailFragment_dx;
import com.huanghuang.kangshangyiliao.fragment.BloodFatResultDetailFragment_kf;
import com.huanghuang.kangshangyiliao.fragment.BloodFatResultDetailFragment_lf;
import com.huanghuang.kangshangyiliao.fragment.BloodFatResultDetailFragment_lf_three;
import com.huanghuang.kangshangyiliao.fragment.BloodFatResultDetailFragment_xx;
import com.huanghuang.kangshangyiliao.util.Utils;
import java.util.List;

public class BloodFatResultDetailFragmentAdapter extends FragmentStatePagerAdapter {
    private Activity activity;
    private Fragment currentFragment;
    private BloodFatDao dao = new BloodFatDao();
    List<BloodFat> data = this.dao.queryAll();

    public int getItemPosition(Object obj) {
        return -2;
    }

    public BloodFatResultDetailFragmentAdapter(FragmentManager fragmentManager, Activity activity2) {
        super(fragmentManager);
        this.activity = activity2;
    }

    public Fragment getItem(int i) {
        int i2 = Utils.parseBloodFatData(Utils.toByteArray(this.data.get(i).data)).ReagentType;
        if (i2 == 1) {
            BloodFatResultDetailFragment bloodFatResultDetailFragment = new BloodFatResultDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", this.data.get(i));
            bloodFatResultDetailFragment.setArguments(bundle);
            return bloodFatResultDetailFragment;
        } else if (i2 == 3) {
            BloodFatResultDetailFragment_kf bloodFatResultDetailFragment_kf = new BloodFatResultDetailFragment_kf();
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("data", this.data.get(i));
            bloodFatResultDetailFragment_kf.setArguments(bundle2);
            return bloodFatResultDetailFragment_kf;
        } else if (i2 == 9) {
            BloodFatResultDetailFragment_lf_three bloodFatResultDetailFragment_lf_three = new BloodFatResultDetailFragment_lf_three();
            Bundle bundle3 = new Bundle();
            bundle3.putSerializable("data", this.data.get(i));
            bloodFatResultDetailFragment_lf_three.setArguments(bundle3);
            return bloodFatResultDetailFragment_lf_three;
        } else if (i2 == 5) {
            BloodFatResultDetailFragment_lf bloodFatResultDetailFragment_lf = new BloodFatResultDetailFragment_lf();
            Bundle bundle4 = new Bundle();
            bundle4.putSerializable("data", this.data.get(i));
            bloodFatResultDetailFragment_lf.setArguments(bundle4);
            return bloodFatResultDetailFragment_lf;
        } else if (i2 == 6) {
            BloodFatResultDetailFragment_xx bloodFatResultDetailFragment_xx = new BloodFatResultDetailFragment_xx();
            Bundle bundle5 = new Bundle();
            bundle5.putSerializable("data", this.data.get(i));
            bloodFatResultDetailFragment_xx.setArguments(bundle5);
            return bloodFatResultDetailFragment_xx;
        } else if (i2 != 7) {
            BloodFatResultDetailFragment bloodFatResultDetailFragment2 = new BloodFatResultDetailFragment();
            Bundle bundle6 = new Bundle();
            bundle6.putSerializable("data", this.data.get(i));
            bloodFatResultDetailFragment2.setArguments(bundle6);
            return bloodFatResultDetailFragment2;
        } else {
            BloodFatResultDetailFragment_dx bloodFatResultDetailFragment_dx = new BloodFatResultDetailFragment_dx();
            Bundle bundle7 = new Bundle();
            bundle7.putSerializable("data", this.data.get(i));
            bloodFatResultDetailFragment_dx.setArguments(bundle7);
            return bloodFatResultDetailFragment_dx;
        }
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
        this.dao.delete(this.data.get(i).f76id);
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
