package com.huanghuang.kangshangyiliao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.code.microlog4android.format.SimpleFormatter;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.base.AppBaseFragment;
import com.huanghuang.kangshangyiliao.bean.ImmuFluoResult;
import com.huanghuang.kangshangyiliao.dao.bean.Immunofluorescence;
import com.huanghuang.kangshangyiliao.util.Utils;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import java.text.DecimalFormat;

public class ImmunofluoResultDetailFragment_crp_new extends AppBaseFragment {
    private byte[] data;

    /* renamed from: df */
    DecimalFormat f88df;
    private Immunofluorescence immuFluoResult;
    @ViewBind.Bind(mo7926id = 2131230904)
    private LinearLayout ll_one;
    @ViewBind.Bind(mo7926id = 2131230905)
    private LinearLayout ll_three;
    @ViewBind.Bind(mo7926id = 2131230906)
    private LinearLayout ll_two;
    private View rootView;
    @ViewBind.Bind(mo7926id = 2131231019)
    private TextView tvDate;
    @ViewBind.Bind(mo7926id = 2131231051)
    private TextView tvNickName;
    @ViewBind.Bind(mo7926id = 2131231081)
    private TextView tv_item_one;
    @ViewBind.Bind(mo7926id = 2131231082)
    private TextView tv_item_three;
    @ViewBind.Bind(mo7926id = 2131231083)
    private TextView tv_item_two;
    @ViewBind.Bind(mo7926id = 2131231084)
    private TextView tv_measure_time;
    @ViewBind.Bind(mo7926id = 2131231086)
    private TextView tv_name_one;
    @ViewBind.Bind(mo7926id = 2131231087)
    private TextView tv_name_three;
    @ViewBind.Bind(mo7926id = 2131231088)
    private TextView tv_name_two;
    @ViewBind.Bind(mo7926id = 2131231090)
    private TextView tv_range_one;
    @ViewBind.Bind(mo7926id = 2131231091)
    private TextView tv_range_three;
    @ViewBind.Bind(mo7926id = 2131231092)
    private TextView tv_range_two;
    @ViewBind.Bind(mo7926id = 2131231093)
    private TextView tv_reagent;
    @ViewBind.Bind(mo7926id = 2131231101)
    private TextView tv_unit_one;
    @ViewBind.Bind(mo7926id = 2131231102)
    private TextView tv_unit_three;
    @ViewBind.Bind(mo7926id = 2131231103)
    private TextView tv_unit_two;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.immuFluoResult = (Immunofluorescence) getArguments().getSerializable("data");
        Immunofluorescence immunofluorescence = this.immuFluoResult;
        if (immunofluorescence != null) {
            this.data = Utils.toByteArray(immunofluorescence.data);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.fragment_immunoflu_reuslt_detail_crp, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        judgeResult();
        return this.rootView;
    }

    public void judgeResult() {
        if (Utils.getUserInfo() != null) {
            this.tvNickName.setText(this.immuFluoResult.nickname);
        }
        this.tvDate.setText(this.immuFluoResult.getCreateDate());
        ImmuFluoResult parseImmuFluoResult = Utils.parseImmuFluoResult(this.data);
        StringBuilder sb = new StringBuilder();
        sb.append(parseImmuFluoResult.year + SimpleFormatter.DEFAULT_DELIMITER + String.format("%02d", new Object[]{Integer.valueOf(parseImmuFluoResult.mon)}) + SimpleFormatter.DEFAULT_DELIMITER + String.format("%02d", new Object[]{Integer.valueOf(parseImmuFluoResult.day)}));
        sb.append(" ");
        sb.append(String.format("%02d", new Object[]{Integer.valueOf(parseImmuFluoResult.hour)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(parseImmuFluoResult.minute)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(parseImmuFluoResult.second)}));
        this.tv_measure_time.setText(sb.toString());
        initData(parseImmuFluoResult);
    }

    private void initData(ImmuFluoResult immuFluoResult2) {
        judgeItemShowCount(immuFluoResult2);
        judgeItemShow(immuFluoResult2);
    }

    private void judgeItemShow(ImmuFluoResult immuFluoResult2) {
        ShowCrpAndHsCrp(immuFluoResult2);
    }

    private void ShowCrpAndHsCrp(ImmuFluoResult immuFluoResult2) {
        setItemName(immuFluoResult2, this.tv_name_one, this.tv_name_two, this.tv_name_three);
        setUnit(this.tv_unit_one, immuFluoResult2.unitNum0);
        setUnit(this.tv_unit_two, immuFluoResult2.unitNum1);
        setUnit(this.tv_unit_three, immuFluoResult2.unitNum2);
        String returnStringValue = returnStringValue(immuFluoResult2.Frst0, immuFluoResult2.decimalBits);
        String returnStringValue2 = returnStringValue(immuFluoResult2.Frst1, immuFluoResult2.decimalBits);
        String returnStringValue3 = returnStringValue(immuFluoResult2.Frst2, immuFluoResult2.decimalBits);
        if (immuFluoResult2.ReagentType != 11) {
            this.tv_item_one.setText(returnStringValue);
        } else if (immuFluoResult2.Frst0 > 99.0f) {
            this.tv_item_one.setText(">99");
        } else {
            this.tv_item_one.setText(returnStringValue);
        }
        this.tv_item_two.setText(returnStringValue2);
        if (immuFluoResult2.ReagentType == 1) {
            this.tv_item_two.setText(returnStringValue);
        } else {
            this.tv_item_two.setText(returnStringValue2);
        }
        this.tv_item_three.setText(returnStringValue3);
        String returnStringValue4 = returnStringValue(immuFluoResult2.RefL0, immuFluoResult2.decimalBits);
        String returnStringValue5 = returnStringValue(immuFluoResult2.RefH0, immuFluoResult2.decimalBits);
        TextView textView = this.tv_range_one;
        textView.setText(returnStringValue4 + SimpleFormatter.DEFAULT_DELIMITER + returnStringValue5);
        if (immuFluoResult2.ReagentType == 1) {
            this.tv_range_two.setText("<3.00");
        } else {
            String returnStringValue6 = returnStringValue(immuFluoResult2.RefL1, immuFluoResult2.decimalBits);
            String returnStringValue7 = returnStringValue(immuFluoResult2.RefH1, immuFluoResult2.decimalBits);
            TextView textView2 = this.tv_range_two;
            textView2.setText(returnStringValue6 + SimpleFormatter.DEFAULT_DELIMITER + returnStringValue7);
        }
        String returnStringValue8 = returnStringValue(immuFluoResult2.RefL2, immuFluoResult2.decimalBits);
        String returnStringValue9 = returnStringValue(immuFluoResult2.RefH2, immuFluoResult2.decimalBits);
        TextView textView3 = this.tv_range_three;
        textView3.setText(returnStringValue8 + SimpleFormatter.DEFAULT_DELIMITER + returnStringValue9);
        String format = String.format("%02d", new Object[]{Integer.valueOf(immuFluoResult2.rn_mon)});
        String format2 = String.format("%02d", new Object[]{Integer.valueOf(immuFluoResult2.rn_day)});
        String format3 = String.format("%02d", new Object[]{Integer.valueOf(immuFluoResult2.rn_serial)});
        String format4 = String.format("%02d", new Object[]{Integer.valueOf(immuFluoResult2.reagentLotNum)});
        if (immuFluoResult2.ReagentType > 11) {
            TextView textView4 = this.tv_reagent;
            textView4.setText(getString(C0418R.string.immuoflu_item_name_reagent_d00) + format4 + immuFluoResult2.rn_year + format + format2 + format3);
            return;
        }
        TextView textView5 = this.tv_reagent;
        textView5.setText(getString(C0418R.string.immuoflu_item_name_reagent_s00) + format4 + immuFluoResult2.rn_year + format + format2 + format3);
    }

    private void setItemName(ImmuFluoResult immuFluoResult2, TextView textView, TextView textView2, TextView textView3) {
        int i = immuFluoResult2.ReagentType;
        switch (i) {
            case 1:
                setItemNumsCRP(immuFluoResult2, textView, textView2, textView3);
                return;
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
                break;
            default:
                switch (i) {
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                        break;
                    default:
                        switch (i) {
                            case 35:
                            case 36:
                            case 37:
                            case 38:
                                break;
                            default:
                                return;
                        }
                }
        }
        setItemNumsSAA01(immuFluoResult2, textView, textView2, textView3);
    }

    private void setItemNumsCRP(ImmuFluoResult immuFluoResult2, TextView textView, TextView textView2, TextView textView3) {
        textView.setText(immuFluoResult2.itemNameOne);
        textView2.setText(getString(C0418R.string.immuoflu_hscrp));
        textView3.setText(getString(C0418R.string.immuoflu_item_name_placeholder));
    }

    private void setItemNumsSAA01(ImmuFluoResult immuFluoResult2, TextView textView, TextView textView2, TextView textView3) {
        if (immuFluoResult2.itemnums == 1) {
            textView.setText(immuFluoResult2.itemNameOne);
        } else {
            textView.setText(immuFluoResult2.itemName[0]);
            textView2.setText(immuFluoResult2.itemName[1]);
        }
        textView3.setText(getString(C0418R.string.immuoflu_item_name_placeholder));
    }

    private void setUnit(TextView textView, int i) {
        switch (i) {
            case 0:
                textView.setText(getString(C0418R.string.immuoflu_unit_00));
                return;
            case 1:
                textView.setText(getString(C0418R.string.immuoflu_unit_01));
                return;
            case 2:
                textView.setText(getString(C0418R.string.immuoflu_unit_02));
                return;
            case 3:
                textView.setText(getString(C0418R.string.immuoflu_unit_03));
                return;
            case 4:
                textView.setText(getString(C0418R.string.immuoflu_unit_04));
                return;
            case 5:
                textView.setText(getString(C0418R.string.immuoflu_unit_05));
                return;
            case 6:
                textView.setText(getString(C0418R.string.immuoflu_unit_06));
                return;
            case 7:
                textView.setText(getString(C0418R.string.immuoflu_unit_07));
                return;
            case 8:
                textView.setText(getString(C0418R.string.immuoflu_unit_08));
                return;
            case 9:
                textView.setText(getString(C0418R.string.immuoflu_unit_09));
                return;
            case 10:
                textView.setText(getString(C0418R.string.immuoflu_unit_10));
                return;
            case 11:
                textView.setText(getString(C0418R.string.immuoflu_unit_11));
                return;
            case 12:
                textView.setText(getString(C0418R.string.immuoflu_unit_12));
                return;
            case 13:
                textView.setText(getString(C0418R.string.immuoflu_unit_13));
                return;
            case 14:
                textView.setText(getString(C0418R.string.immuoflu_unit_14));
                return;
            case 15:
                textView.setText(getString(C0418R.string.immuoflu_unit_15));
                return;
            case 16:
                textView.setText(getString(C0418R.string.immuoflu_unit_16));
                return;
            default:
                return;
        }
    }

    private void judgeItemShowCount(ImmuFluoResult immuFluoResult2) {
        int i = immuFluoResult2.itemnums;
        if (i == 1) {
            this.ll_one.setVisibility(0);
            if (immuFluoResult2.ReagentType == 1) {
                this.ll_two.setVisibility(0);
            } else {
                this.ll_two.setVisibility(4);
            }
            this.ll_three.setVisibility(4);
        } else if (i == 2) {
            this.ll_one.setVisibility(0);
            this.ll_two.setVisibility(0);
            this.ll_three.setVisibility(4);
        } else if (i == 3) {
            this.ll_one.setVisibility(0);
            this.ll_two.setVisibility(0);
            this.ll_three.setVisibility(0);
        }
    }

    private String returnStringValue(float f, int i) {
        if (i == 2) {
            this.f88df = new DecimalFormat("#0.00");
        } else if (i == 3) {
            this.f88df = new DecimalFormat("#0.000");
        } else if (i == 1) {
            this.f88df = new DecimalFormat("#0.0");
        } else {
            this.f88df = new DecimalFormat("#0");
        }
        return this.f88df.format((double) f);
    }
}
