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
import com.huanghuang.kangshangyiliao.dao.bean.Immunofluorescence;
import com.huanghuang.kangshangyiliao.util.Utils;
import java.util.ArrayList;
import java.util.List;

public class ImmunofluorescenceResultListAdapter extends BaseAdapter {
    private Context context;
    private List<Immunofluorescence> data;
    /* access modifiers changed from: private */
    public boolean[] flags;
    private boolean showCheckBox;

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public ImmunofluorescenceResultListAdapter(Context context2, List<Immunofluorescence> list) {
        this.context = context2;
        this.data = list;
        this.flags = new boolean[list.size()];
    }

    public void setData(List<Immunofluorescence> list) {
        this.data = list;
        this.flags = new boolean[list.size()];
    }

    public int getCount() {
        return this.data.size();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        final int i2 = i;
        View inflate = view == null ? LayoutInflater.from(this.context).inflate(C0418R.layout.item_position_result, viewGroup, false) : view;
        TextView textView = (TextView) inflate.findViewById(C0418R.C0420id.tvNickName);
        TextView textView2 = (TextView) inflate.findViewById(C0418R.C0420id.tvDate);
        TextView textView3 = (TextView) inflate.findViewById(C0418R.C0420id.tvHGB);
        TextView textView4 = (TextView) inflate.findViewById(C0418R.C0420id.tvHCT);
        final CheckBox checkBox = (CheckBox) inflate.findViewById(C0418R.C0420id.cbItem);
        if (this.showCheckBox) {
            checkBox.setChecked(this.flags[i2]);
            checkBox.setVisibility(0);
            inflate.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ImmunofluorescenceResultListAdapter.this.flags[i2] = !checkBox.isChecked();
                    checkBox.setChecked(ImmunofluorescenceResultListAdapter.this.flags[i2]);
                }
            });
        } else {
            checkBox.setVisibility(8);
            checkBox.setChecked(false);
        }
        ProteinResult parseProteinData = Utils.parseProteinData(Utils.toByteArray(this.data.get(i2).data));
        textView.setText("HGB:" + parseProteinData.values[0] + "g/dL");
        StringBuilder sb = new StringBuilder();
        sb.append((int) (parseProteinData.values[1] * 100.0f));
        sb.append("％");
        textView3.setText(sb.toString());
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
        textView2.setText("20" + ((int) parseProteinData.time[0]) + SimpleFormatter.DEFAULT_DELIMITER + valueOf + SimpleFormatter.DEFAULT_DELIMITER + valueOf2 + " " + valueOf3 + ":" + valueOf4 + ":" + valueOf5);
        return inflate;
    }

    public void show(boolean z) {
        this.showCheckBox = z;
        notifyDataSetChanged();
    }

    public List<Integer> getDeleteIds() {
        ArrayList arrayList = new ArrayList();
        int i = 1;
        while (true) {
            boolean[] zArr = this.flags;
            if (i >= zArr.length) {
                return arrayList;
            }
            if (zArr[i]) {
                arrayList.add(Integer.valueOf(this.data.get(i).f80id));
            }
            i++;
        }
    }

    public void checked(int i) {
        this.flags[i] = true;
    }
}
