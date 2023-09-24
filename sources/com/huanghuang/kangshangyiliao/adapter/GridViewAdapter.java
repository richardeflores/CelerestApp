package com.huanghuang.kangshangyiliao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.bean.GridView_Icon_add;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<GridView_Icon_add> data;

    public long getItemId(int i) {
        return (long) i;
    }

    public GridViewAdapter(Context context2, List<GridView_Icon_add> list) {
        this.context = context2;
        this.data = list;
    }

    public int getCount() {
        return this.data.size();
    }

    public Object getItem(int i) {
        return this.data.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(C0418R.layout.gridview_layout, (ViewGroup) null);
            viewHolder = new ViewHolder();
            viewHolder.f70iv = (ImageView) view.findViewById(C0418R.C0420id.grImage);
            viewHolder.text = (TextView) view.findViewById(C0418R.C0420id.grText);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.f70iv.setImageResource(Integer.valueOf(this.data.get(i).getiId()).intValue());
        viewHolder.text.setText(this.data.get(i).getiName());
        return view;
    }

    static class ViewHolder {

        /* renamed from: iv */
        ImageView f70iv;
        TextView text;

        ViewHolder() {
        }
    }
}
