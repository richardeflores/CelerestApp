package com.huanghuang.kangshangyiliao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.dao.bean.NickName;
import java.util.List;

public class NickNameListAdapter extends BaseAdapter {
    private Context context;
    private List<NickName> data;

    public long getItemId(int i) {
        return 0;
    }

    public NickNameListAdapter(Context context2, List<NickName> list) {
        this.context = context2;
        this.data = list;
    }

    public int getCount() {
        return this.data.size();
    }

    public NickName getItem(int i) {
        return this.data.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(C0418R.layout.item_nick_name, viewGroup, false);
        }
        ((TextView) view.findViewById(C0418R.C0420id.tvNickName)).setText(this.data.get(i).nickName);
        return view;
    }
}
