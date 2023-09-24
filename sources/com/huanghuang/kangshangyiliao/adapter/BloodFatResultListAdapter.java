package com.huanghuang.kangshangyiliao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.bean.BloodFatResult;
import com.huanghuang.kangshangyiliao.dao.bean.BloodFat;
import com.huanghuang.kangshangyiliao.util.Utils;
import java.util.ArrayList;
import java.util.List;

public class BloodFatResultListAdapter extends BaseAdapter {
    private static final int TYPE_BF = 0;
    private static final int TYPE_DX = 1;
    private static final int TYPE_KF = 2;
    private static final int TYPE_LF = 3;
    private static final int TYPE_LF_THREE = 5;
    private static final int TYPE_XX = 4;
    private Context context;
    private List<BloodFat> data;
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
        return 6;
    }

    public BloodFatResultListAdapter(Context context2, List<BloodFat> list) {
        this.context = context2;
        this.data = list;
    }

    public void setData(List<BloodFat> list) {
        this.data = list;
        this.flags = new boolean[getCount()];
    }

    public int getCount() {
        return this.data.size();
    }

    public int getItemViewType(int i) {
        int i2 = Utils.parseBloodFatData(Utils.toByteArray(this.data.get(i).data)).ReagentType;
        if (i2 == 1) {
            return 0;
        }
        if (i2 == 3) {
            return 2;
        }
        if (i2 == 9) {
            return 5;
        }
        if (i2 == 5) {
            return 3;
        }
        if (i2 != 6) {
            return i2 != 7 ? 0 : 1;
        }
        return 4;
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
            viewHolder.tv_name = (TextView) view2.findViewById(C0418R.C0420id.tv_name);
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
                    BloodFatResultListAdapter.this.flags[i] = !viewHolder.checkBox.isChecked();
                    viewHolder.checkBox.setChecked(BloodFatResultListAdapter.this.flags[i]);
                }
            });
        } else {
            viewHolder.checkBox.setVisibility(8);
            viewHolder.checkBox.setChecked(false);
        }
        BloodFat bloodFat = this.data.get(i);
        viewHolder.tvNickName.setText(bloodFat.nickname);
        viewHolder.tvDate.setText(bloodFat.getCreateDate());
        byte[] byteArray = Utils.toByteArray(bloodFat.data);
        int i2 = Utils.parseBloodFatData(byteArray).units;
        int itemViewType = getItemViewType(i);
        if (itemViewType != 0) {
            if (itemViewType != 1) {
                if (itemViewType != 2) {
                    if (itemViewType == 3) {
                        BloodFatResult parseBloodFatData_lf = Utils.parseBloodFatData_lf(byteArray);
                        viewHolder.tv_name.setText("ALP:");
                        TextView textView = viewHolder.tvWBC;
                        textView.setText(((int) parseBloodFatData_lf.finalValues[0]) + "");
                    } else if (itemViewType == 4) {
                        BloodFatResult parseBloodFatData_sg = Utils.parseBloodFatData_sg(byteArray);
                        viewHolder.tv_name.setText("ALT:");
                        TextView textView2 = viewHolder.tvWBC;
                        textView2.setText(((int) parseBloodFatData_sg.finalValues[0]) + "");
                    } else if (itemViewType == 5) {
                        BloodFatResult parseBloodFatData_lf2 = Utils.parseBloodFatData_lf(byteArray);
                        viewHolder.tv_name.setText("ALT:");
                        TextView textView3 = viewHolder.tvWBC;
                        textView3.setText(((int) parseBloodFatData_lf2.finalValues[0]) + "");
                    }
                } else if (i2 == 1) {
                    BloodFatResult parseBloodFatData_sg2 = Utils.parseBloodFatData_sg(byteArray);
                    viewHolder.tv_name.setText("UA:");
                    viewHolder.tvWBC.setText(Utils.floatPointNoRound(parseBloodFatData_sg2.finalValues_change_unit[1]));
                } else {
                    BloodFatResult parseBloodFatData_sg3 = Utils.parseBloodFatData_sg(byteArray);
                    viewHolder.tv_name.setText("UA:");
                    TextView textView4 = viewHolder.tvWBC;
                    textView4.setText("" + ((int) parseBloodFatData_sg3.finalValues[1]));
                }
            } else if (i2 == 1) {
                BloodFatResult parseBloodFatData_dx = Utils.parseBloodFatData_dx(byteArray);
                viewHolder.tv_name.setText("TC:");
                viewHolder.tvWBC.setText(Utils.float2PointRound(parseBloodFatData_dx.finalValues_change_unit[2]));
            } else {
                BloodFatResult parseBloodFatData_dx2 = Utils.parseBloodFatData_dx(byteArray);
                viewHolder.tv_name.setText("TC:");
                viewHolder.tvWBC.setText(Utils.float2PointRound(parseBloodFatData_dx2.finalValues[2]));
            }
        } else if (i2 == 1) {
            String[] bloodFatRealValueText02 = Utils.getBloodFatRealValueText02(Utils.parseBloodFatData(byteArray), 1);
            viewHolder.tv_name.setText("TC:");
            viewHolder.tvWBC.setText(bloodFatRealValueText02[3]);
        } else {
            String[] bloodFatRealValueText = Utils.getBloodFatRealValueText(Utils.parseBloodFatData(byteArray));
            viewHolder.tv_name.setText("TC:");
            viewHolder.tvWBC.setText(bloodFatRealValueText[3]);
        }
        return view2;
    }

    class ViewHolder {
        CheckBox checkBox;
        TextView tvDate;
        TextView tvNickName;
        TextView tvWBC;
        TextView tv_name;

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
                arrayList.add(Integer.valueOf(this.data.get(i).f76id));
            }
            i++;
        }
    }

    public void checked(int i) {
        this.flags[i] = true;
    }
}
