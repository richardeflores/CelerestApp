package com.huanghuang.kangshangyiliao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.bean.ImmuFluoResult;
import com.huanghuang.kangshangyiliao.dao.bean.Immunofluorescence;
import com.huanghuang.kangshangyiliao.util.Utils;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ImmunofluResultListAdapter extends BaseAdapter {
    private static final int TYPE_25OHVD3 = 9;
    private static final int TYPE_COV = 10;
    private static final int TYPE_COV11 = 11;
    private static final int TYPE_COV12 = 12;
    private static final int TYPE_COV13 = 13;
    private static final int TYPE_COV14 = 14;
    private static final int TYPE_COV15 = 15;
    private static final int TYPE_COV16 = 16;
    private static final int TYPE_COV17 = 17;
    private static final int TYPE_COV18 = 18;
    private static final int TYPE_COV19 = 19;
    private static final int TYPE_COV20 = 20;
    private static final int TYPE_COV21 = 21;
    private static final int TYPE_COV22 = 22;
    private static final int TYPE_COV23 = 23;
    private static final int TYPE_COV24 = 24;
    private static final int TYPE_COV25 = 25;
    private static final int TYPE_COV26 = 26;
    private static final int TYPE_COV27 = 27;
    private static final int TYPE_COV28 = 28;
    private static final int TYPE_CPR_HSCRP = 0;
    private static final int TYPE_CRP_SAA1 = 4;
    private static final int TYPE_HBALC = 8;
    private static final int TYPE_IL6 = 7;
    private static final int TYPE_MPCT = 6;
    private static final int TYPE_NTPROBNP = 5;
    private static final int TYPE_PCT = 2;
    private static final int TYPE_SAA = 1;
    private static final int TYPE_SAA_CRP = 3;
    private Context context;
    private List<Immunofluorescence> data;

    /* renamed from: df */
    DecimalFormat f71df;
    /* access modifiers changed from: private */
    public boolean[] flags = new boolean[getCount()];
    private boolean showCheckBox;

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public int getViewTypeCount() {
        return 29;
    }

    public ImmunofluResultListAdapter(Context context2, List<Immunofluorescence> list) {
        this.context = context2;
        this.data = list;
    }

    public void setData(List<Immunofluorescence> list) {
        this.data = list;
        this.flags = new boolean[getCount()];
    }

    public int getCount() {
        return this.data.size();
    }

    public int getItemViewType(int i) {
        int i2 = Utils.parseImmuFluoResult(Utils.toByteArray(this.data.get(i).data)).ReagentType;
        switch (i2) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
            case 8:
                return 7;
            case 9:
                return 8;
            case 10:
                return 9;
            case 11:
                return 10;
            default:
                switch (i2) {
                    case 21:
                        return 11;
                    case 22:
                        return 12;
                    case 23:
                        return 13;
                    case 24:
                        return 14;
                    case 25:
                        return 15;
                    case 26:
                        return 16;
                    case 27:
                        return 17;
                    case 28:
                        return 18;
                    case 29:
                        return 19;
                    case 30:
                        return 20;
                    case 31:
                        return 21;
                    case 32:
                        return 22;
                    case 33:
                        return 23;
                    case 34:
                        return 24;
                    case 35:
                        return 25;
                    case 36:
                        return 26;
                    case 37:
                        return 27;
                    case 38:
                        return 28;
                    default:
                        return 0;
                }
        }
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        View view2;
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = LayoutInflater.from(this.context).inflate(C0418R.layout.item_position_result, viewGroup, false);
            viewHolder.tvNickName = (TextView) view2.findViewById(C0418R.C0420id.tvNickName);
            viewHolder.tv_tag = (TextView) view2.findViewById(C0418R.C0420id.tv_tag);
            viewHolder.tvDate = (TextView) view2.findViewById(C0418R.C0420id.tvDate);
            viewHolder.tvHGB = (TextView) view2.findViewById(C0418R.C0420id.tvHGB);
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
                    ImmunofluResultListAdapter.this.flags[i] = !viewHolder.checkBox.isChecked();
                    viewHolder.checkBox.setChecked(ImmunofluResultListAdapter.this.flags[i]);
                }
            });
        } else {
            viewHolder.checkBox.setVisibility(8);
            viewHolder.checkBox.setChecked(false);
        }
        Immunofluorescence immunofluorescence = this.data.get(i);
        ImmuFluoResult parseImmuFluoResult = Utils.parseImmuFluoResult(Utils.toByteArray(immunofluorescence.data));
        viewHolder.tvNickName.setText(immunofluorescence.nickname);
        String str = parseImmuFluoResult.stringFrst0;
        int itemViewType = getItemViewType(i);
        String returnStringValue = returnStringValue(parseImmuFluoResult.Frst0, parseImmuFluoResult.decimalBits);
        switch (itemViewType) {
            case 0:
                setItemNumsCRP(parseImmuFluoResult, viewHolder.tv_tag);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
                setItemNumsSAA01(parseImmuFluoResult, viewHolder.tv_tag);
                break;
        }
        TextView textView = viewHolder.tvHGB;
        textView.setText(returnStringValue + "");
        viewHolder.tvDate.setText(immunofluorescence.getCreateDate());
        return view2;
    }

    private void setItemNumsCRP(ImmuFluoResult immuFluoResult, TextView textView) {
        textView.setText(immuFluoResult.itemNameOne + "/" + this.context.getString(C0418R.string.immuoflu_hscrp));
    }

    private void setItemNumsSAA01(ImmuFluoResult immuFluoResult, TextView textView) {
        if (immuFluoResult.itemnums == 1) {
            textView.setText(immuFluoResult.itemNameOne);
            PrintStream printStream = System.out;
            printStream.println("immuFluoResult.itemNameOne : " + immuFluoResult.itemNameOne);
        } else if (immuFluoResult.itemName != null) {
            textView.setText(immuFluoResult.itemName[0] + "/" + immuFluoResult.itemName[1]);
        }
    }

    private String returnStringValue(float f, int i) {
        if (i == 2) {
            this.f71df = new DecimalFormat("#0.00");
        } else if (i == 3) {
            this.f71df = new DecimalFormat("#0.000");
        } else if (i == 1) {
            this.f71df = new DecimalFormat("#0.0");
        } else {
            this.f71df = new DecimalFormat("#0");
        }
        return this.f71df.format((double) f);
    }

    static class ViewHolder {
        CheckBox checkBox;
        TextView tvDate;
        TextView tvHGB;
        TextView tvNickName;
        TextView tv_tag;

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
                arrayList.add(Integer.valueOf(this.data.get(i).f80id));
            }
            i++;
        }
    }

    public void checked(int i) {
        this.flags[i] = true;
    }
}
