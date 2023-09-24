package com.huanghuang.kangshangyiliao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.bean.ReportDetailsBean;
import java.util.ArrayList;
import java.util.List;

public class ReportBriefAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ReportDetailsBean> list = new ArrayList();

    public long getItemId(int i) {
        return (long) i;
    }

    public ReportBriefAdapter(Context context2, List<ReportDetailsBean> list2) {
        this.context = context2;
        this.inflater = (LayoutInflater) context2.getSystemService("layout_inflater");
        this.list = list2;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int i) {
        return this.list.get(i);
    }

    public class ViewHolder {
        public ViewHolder() {
        }
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        return view == null ? this.inflater.inflate(C0418R.layout.listview_report_brief_item, (ViewGroup) null) : view;
    }
}
