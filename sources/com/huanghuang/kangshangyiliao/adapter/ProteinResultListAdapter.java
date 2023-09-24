package com.huanghuang.kangshangyiliao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.google.code.microlog4android.format.SimpleFormatter;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.bean.ProteinResult;
import com.huanghuang.kangshangyiliao.dao.bean.Protein;
import com.huanghuang.kangshangyiliao.util.Utils;
import java.util.ArrayList;
import java.util.List;

public class ProteinResultListAdapter extends BaseAdapter {
    private Context context;
    private List<Protein> data;
    /* access modifiers changed from: private */
    public boolean[] flags;
    private boolean showCheckBox;

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public ProteinResultListAdapter(Context context2, List<Protein> list) {
        this.context = context2;
        this.data = list;
        this.flags = new boolean[list.size()];
    }

    public void setData(List<Protein> list) {
        this.data = list;
        this.flags = new boolean[list.size()];
    }

    public int getCount() {
        return this.data.size();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        final ViewHolder viewHolder;
        final int i2 = i;
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = LayoutInflater.from(this.context).inflate(C0418R.layout.item_position_result, viewGroup, false);
            viewHolder.tvNickName = (TextView) view2.findViewById(C0418R.C0420id.tvNickName);
            viewHolder.tvDate = (TextView) view2.findViewById(C0418R.C0420id.tvDate);
            viewHolder.tvHGB = (TextView) view2.findViewById(C0418R.C0420id.tvHGB);
            viewHolder.checkBox = (CheckBox) view2.findViewById(C0418R.C0420id.cbItem);
            view2.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
            view2 = view;
        }
        if (this.showCheckBox) {
            viewHolder.checkBox.setChecked(this.flags[i2]);
            viewHolder.checkBox.setVisibility(0);
            view2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ProteinResultListAdapter.this.flags[i2] = !viewHolder.checkBox.isChecked();
                    viewHolder.checkBox.setChecked(ProteinResultListAdapter.this.flags[i2]);
                }
            });
        } else {
            viewHolder.checkBox.setVisibility(8);
            viewHolder.checkBox.setChecked(false);
        }
        Protein protein = this.data.get(i2);
        ProteinResult parseProteinData = Utils.parseProteinData(Utils.toByteArray(protein.data));
        String floatPointNoRound = Utils.floatPointNoRound(parseProteinData.values[0] / 10.0f);
        viewHolder.tvNickName.setText("HGB:" + floatPointNoRound + "g/dL");
        viewHolder.tvHGB.setText(((int) (parseProteinData.values[1] * 100.0f)) + "％");
        int i3 = (int) parseProteinData.time[1];
        int i4 = (int) parseProteinData.time[2];
        int i5 = (int) parseProteinData.time[3];
        int i6 = (int) parseProteinData.time[4];
        int i7 = (int) parseProteinData.time[5];
        String valueOf = String.valueOf(i3);
        String valueOf2 = String.valueOf(i4);
        String valueOf3 = String.valueOf(i5);
        String valueOf4 = String.valueOf(i6);
        String valueOf5 = String.valueOf(i7);
        if (i3 < 10) {
            valueOf = "0" + i3;
        }
        if (i4 < 10) {
            valueOf2 = "0" + i4;
        }
        if (i5 < 10) {
            valueOf3 = "0" + i5;
        }
        if (i6 < 10) {
            valueOf4 = "0" + i6;
        }
        if (i7 < 10) {
            valueOf5 = "0" + i7;
        }
        "20" + ((int) parseProteinData.time[0]) + SimpleFormatter.DEFAULT_DELIMITER + valueOf + SimpleFormatter.DEFAULT_DELIMITER + valueOf2 + " " + valueOf3 + ":" + valueOf4 + ":" + valueOf5;
        viewHolder.tvDate.setText(protein.createDate);
        return view2;
    }

    class ViewHolder {
        CheckBox checkBox;
        TextView tvDate;
        TextView tvHGB;
        TextView tvNickName;

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
                arrayList.add(Integer.valueOf(this.data.get(i).f84id));
            }
            i++;
        }
    }

    public void checked(int i) {
        this.flags[i] = true;
    }
}
