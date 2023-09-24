package com.huanghuang.kangshangyiliao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.dao.bean.ClinicName;
import java.util.List;

public class ClinicNameListAdapter extends BaseAdapter {
    private Context context;
    private List<ClinicName> data;

    public long getItemId(int i) {
        return 0;
    }

    public ClinicNameListAdapter(Context context2, List<ClinicName> list) {
        this.context = context2;
        this.data = list;
    }

    public int getCount() {
        return this.data.size();
    }

    public ClinicName getItem(int i) {
        return this.data.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(C0418R.layout.item_nick_name, viewGroup, false);
        }
        ((TextView) view.findViewById(C0418R.C0420id.tvNickName)).setText(this.data.get(i).clinicName);
        return view;
    }
}
