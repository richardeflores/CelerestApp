package com.huanghuang.kangshangyiliao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.dao.bean.POCT;
import com.huanghuang.kangshangyiliao.util.Utils;
import java.util.ArrayList;
import java.util.List;

public class POCTResultListAdapter extends BaseAdapter {
    private Context context;
    private List<POCT> data;
    /* access modifiers changed from: private */
    public boolean[] flags = new boolean[getCount()];
    private boolean showCheckBox;

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public POCTResultListAdapter(Context context2, List<POCT> list) {
        this.context = context2;
        this.data = list;
    }

    public void setData(List<POCT> list) {
        this.data = list;
        this.flags = new boolean[getCount()];
    }

    public int getCount() {
        return this.data.size();
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        View view2;
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = LayoutInflater.from(this.context).inflate(C0418R.layout.item_poct_result, viewGroup, false);
            viewHolder.tvNickName = (TextView) view2.findViewById(C0418R.C0420id.tvNickName);
            viewHolder.tvDate = (TextView) view2.findViewById(C0418R.C0420id.tvDate);
            viewHolder.tvWBC = (TextView) view2.findViewById(C0418R.C0420id.tvWBC);
            viewHolder.checkBox = (CheckBox) view2.findViewById(C0418R.C0420id.cbItem);
            view2.setTag(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (this.showCheckBox) {
            viewHolder.checkBox.setChecked(this.flags[i]);
            viewHolder.checkBox.setVisibility(0);
            view2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    POCTResultListAdapter.this.flags[i] = !viewHolder.checkBox.isChecked();
                    viewHolder.checkBox.setChecked(POCTResultListAdapter.this.flags[i]);
                }
            });
        } else {
            viewHolder.checkBox.setVisibility(8);
            viewHolder.checkBox.setChecked(false);
        }
        POCT poct = this.data.get(i);
        viewHolder.tvNickName.setText(poct.nickname);
        viewHolder.tvDate.setText(poct.getCreateDate());
        viewHolder.tvWBC.setText(Utils.floatPoint(Utils.parsePOCTData(Utils.toByteArray(poct.data)).finalValues[0]));
        return view2;
    }

    class ViewHolder {
        CheckBox checkBox;
        TextView tvDate;
        TextView tvNickName;
        TextView tvWBC;

        ViewHolder() {
        }
    }

    public void show(boolean z) {
        this.showCheckBox = z;
        notifyDataSetChanged();
    }

    public boolean isShowCheckBox() {
        return this.showCheckBox;
    }

    public List<Integer> getDeleteIds() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            boolean[] zArr = this.flags;
            if (i >= zArr.length) {
                return arrayList;
            }
            if (zArr[i]) {
                arrayList.add(Integer.valueOf(this.data.get(i).f82id));
            }
            i++;
        }
    }

    public void checked(int i) {
        this.flags[i] = true;
    }
}
