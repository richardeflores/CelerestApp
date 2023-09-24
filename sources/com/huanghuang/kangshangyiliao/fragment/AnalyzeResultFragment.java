package com.huanghuang.kangshangyiliao.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.p000v4.content.LocalBroadcastManager;
import android.support.p003v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.activity.BloodFatResultDetailActivity;
import com.huanghuang.kangshangyiliao.activity.ImmunefluResultDetailActivity;
import com.huanghuang.kangshangyiliao.activity.POCTResultDetailActivity;
import com.huanghuang.kangshangyiliao.activity.ProteinResultDetailActivity;
import com.huanghuang.kangshangyiliao.activity.UrinalysisResultDetailActivity;
import com.huanghuang.kangshangyiliao.adapter.BloodFatResultListAdapter;
import com.huanghuang.kangshangyiliao.adapter.ImmunofluResultListAdapter;
import com.huanghuang.kangshangyiliao.adapter.POCTResultListAdapter;
import com.huanghuang.kangshangyiliao.adapter.ProteinResultListAdapter;
import com.huanghuang.kangshangyiliao.adapter.UrinalysisResultListAdapter;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.base.BaseFragment;
import com.huanghuang.kangshangyiliao.dao.BloodFatDao;
import com.huanghuang.kangshangyiliao.dao.ImmunofluorescenceDao;
import com.huanghuang.kangshangyiliao.dao.POCTDao;
import com.huanghuang.kangshangyiliao.dao.ProteinDao;
import com.huanghuang.kangshangyiliao.dao.UrinalysisDao;
import com.huanghuang.kangshangyiliao.dao.bean.BloodFat;
import com.huanghuang.kangshangyiliao.dao.bean.Immunofluorescence;
import com.huanghuang.kangshangyiliao.dao.bean.POCT;
import com.huanghuang.kangshangyiliao.dao.bean.Protein;
import com.huanghuang.kangshangyiliao.dao.bean.Urinalysis;
import com.huanghuang.kangshangyiliao.util.Session;
import com.huanghuang.kangshangyiliao.util.ViewBind;
import com.huanghuang.kangshangyiliao.view.wheel.TrendChartView;
import java.io.Serializable;
import java.util.List;

public class AnalyzeResultFragment extends BaseFragment implements View.OnClickListener {
    int REQUEST_CODE__POCT = 101;
    private AppBase appBase = AppBase.getInstance();
    private BloodFatDao bloodFatDao;
    /* access modifiers changed from: private */
    @ViewBind.Bind(mo7926id = 2131230832)
    public CardView cvDelete;
    private ImmunofluorescenceDao immunofluorescenceDao;
    /* access modifiers changed from: private */
    public int index = 0;
    /* access modifiers changed from: private */
    public boolean isChangeAnalyzeTypePopShow = false;
    private LocalBroadcastManager lbm;
    @ViewBind.Bind(mo7926id = 2131230899)
    private LinearLayout llTrendChart;
    @ViewBind.Bind(mo7926id = 2131230907)
    private ListView lvAnalyzeResult;
    private POCTDao poctDao;
    @ViewBind.Bind(mo7926id = 2131230927)
    private TrendChartView pocttrendChartView;
    private ProteinDao proteinDao;
    @ViewBind.Bind(mo7926id = 2131230950)
    private RelativeLayout rlLow;
    private View rootView;
    private Session session = Session.getInstance();
    private String[] titles;
    @ViewBind.Bind(mo7926id = 2131231034)
    private TextView tvHigh;
    @ViewBind.Bind(mo7926id = 2131231043)
    private TextView tvLow;
    @ViewBind.Bind(mo7926id = 2131231052)
    private TextView tvNormal;
    @ViewBind.Bind(mo7926id = 2131231068)
    private TextView tvTitle;
    private UrinalysisDao urinalysisDao;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.fragment_analyze_result_list, (ViewGroup) null);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        ViewBind.bind(this, this.rootView);
        init();
        onHiddenChanged(false);
        return this.rootView;
    }

    private void init() {
        this.urinalysisDao = new UrinalysisDao();
        this.poctDao = new POCTDao();
        this.proteinDao = new ProteinDao();
        this.bloodFatDao = new BloodFatDao();
        this.immunofluorescenceDao = new ImmunofluorescenceDao();
        this.titles = new String[]{getString(C0418R.string.urinalysis_report), getString(C0418R.string.poct_report), getString(C0418R.string.protein_report), getString(C0418R.string.blood_fat_report), getString(C0418R.string.immunofluorescence_report)};
    }

    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (!z) {
            refresh();
        }
    }

    private void showUrinalysisList() {
        this.session.setViewType(0);
        this.rlLow.setVisibility(8);
        this.tvNormal.setText(C0418R.string.trend_leu_chart_normal);
        this.tvHigh.setText(C0418R.string.trend_leu_chart_high);
        this.pocttrendChartView.setPostInvalidate();
        this.pocttrendChartView.requestLayout();
        final List<Urinalysis> queryAll = this.urinalysisDao.queryAll();
        final UrinalysisResultListAdapter urinalysisResultListAdapter = new UrinalysisResultListAdapter(getActivity(), queryAll);
        if (queryAll.size() != 0) {
            this.llTrendChart.setVisibility(0);
        } else if (queryAll.size() == 0) {
            this.llTrendChart.setVisibility(8);
        }
        this.lvAnalyzeResult.setAdapter(urinalysisResultListAdapter);
        this.lvAnalyzeResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (!urinalysisResultListAdapter.isShowCheckBox()) {
                    Intent intent = new Intent(AnalyzeResultFragment.this.getActivity(), UrinalysisResultDetailActivity.class);
                    intent.putExtra("position", i);
                    intent.putExtra("data", (Serializable) queryAll.get(i));
                    AnalyzeResultFragment analyzeResultFragment = AnalyzeResultFragment.this;
                    analyzeResultFragment.startActivityForResult(intent, analyzeResultFragment.REQUEST_CODE__POCT);
                }
            }
        });
        this.lvAnalyzeResult.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                urinalysisResultListAdapter.checked(i);
                urinalysisResultListAdapter.show(true);
                AnalyzeResultFragment.this.cvDelete.setVisibility(0);
                return true;
            }
        });
    }

    private void showPOCTList() {
        this.session.setViewType(1);
        this.rlLow.setVisibility(0);
        this.tvNormal.setText(C0418R.string.trend_wbc_chart_normal);
        this.tvHigh.setText(C0418R.string.trend_wbc_chart_high);
        this.tvLow.setText(C0418R.string.trend_wbc_chart_low);
        this.pocttrendChartView.setPostInvalidate();
        this.pocttrendChartView.requestLayout();
        final List<POCT> queryAll = this.poctDao.queryAll();
        final POCTResultListAdapter pOCTResultListAdapter = new POCTResultListAdapter(this.context, queryAll);
        if (queryAll.size() != 0) {
            this.llTrendChart.setVisibility(0);
        } else if (queryAll.size() == 0) {
            this.llTrendChart.setVisibility(8);
        }
        this.lvAnalyzeResult.setAdapter(pOCTResultListAdapter);
        this.lvAnalyzeResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (!pOCTResultListAdapter.isShowCheckBox()) {
                    Intent intent = new Intent(AnalyzeResultFragment.this.getActivity(), POCTResultDetailActivity.class);
                    intent.putExtra("position", i);
                    intent.putExtra("data", (Serializable) queryAll.get(i));
                    AnalyzeResultFragment analyzeResultFragment = AnalyzeResultFragment.this;
                    analyzeResultFragment.startActivityForResult(intent, analyzeResultFragment.REQUEST_CODE__POCT);
                }
            }
        });
        this.lvAnalyzeResult.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                pOCTResultListAdapter.checked(i);
                pOCTResultListAdapter.show(true);
                AnalyzeResultFragment.this.cvDelete.setVisibility(0);
                return true;
            }
        });
    }

    private void showProteinList() {
        this.session.setViewType(2);
        this.rlLow.setVisibility(8);
        this.tvNormal.setText(C0418R.string.trend_wbc_chart_normal);
        this.tvHigh.setText(C0418R.string.trend_wbc_chart_high);
        this.tvLow.setText(C0418R.string.trend_wbc_chart_low);
        this.pocttrendChartView.setPostInvalidate();
        this.pocttrendChartView.requestLayout();
        final List<Protein> queryAll = this.proteinDao.queryAll();
        final ProteinResultListAdapter proteinResultListAdapter = new ProteinResultListAdapter(getActivity(), queryAll);
        this.lvAnalyzeResult.setAdapter(proteinResultListAdapter);
        this.lvAnalyzeResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (!proteinResultListAdapter.isShowCheckBox()) {
                    Intent intent = new Intent(AnalyzeResultFragment.this.getActivity(), ProteinResultDetailActivity.class);
                    intent.putExtra("position", i);
                    intent.putExtra("data", (Serializable) queryAll.get(i));
                    AnalyzeResultFragment analyzeResultFragment = AnalyzeResultFragment.this;
                    analyzeResultFragment.startActivityForResult(intent, analyzeResultFragment.REQUEST_CODE__POCT);
                }
            }
        });
        this.lvAnalyzeResult.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                proteinResultListAdapter.checked(i);
                proteinResultListAdapter.show(true);
                AnalyzeResultFragment.this.cvDelete.setVisibility(0);
                return true;
            }
        });
    }

    private void showBloodFatList() {
        this.session.setViewType(3);
        this.rlLow.setVisibility(8);
        this.pocttrendChartView.setPostInvalidate();
        this.pocttrendChartView.requestLayout();
        this.tvNormal.setText(C0418R.string.trend_wbc_chart_normal);
        this.tvHigh.setText(C0418R.string.trend_wbc_chart_high);
        this.tvLow.setText(C0418R.string.trend_wbc_chart_low);
        final List<BloodFat> queryAll = this.bloodFatDao.queryAll();
        final BloodFatResultListAdapter bloodFatResultListAdapter = new BloodFatResultListAdapter(getActivity(), queryAll);
        this.lvAnalyzeResult.setAdapter(bloodFatResultListAdapter);
        this.lvAnalyzeResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (!bloodFatResultListAdapter.isShowCheckBox()) {
                    Intent intent = new Intent(AnalyzeResultFragment.this.getActivity(), BloodFatResultDetailActivity.class);
                    intent.putExtra("position", i);
                    intent.putExtra("data", (Serializable) queryAll.get(i));
                    AnalyzeResultFragment analyzeResultFragment = AnalyzeResultFragment.this;
                    analyzeResultFragment.startActivityForResult(intent, analyzeResultFragment.REQUEST_CODE__POCT);
                }
            }
        });
        this.lvAnalyzeResult.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                bloodFatResultListAdapter.checked(i);
                bloodFatResultListAdapter.show(true);
                AnalyzeResultFragment.this.cvDelete.setVisibility(0);
                return true;
            }
        });
    }

    private void showImmunofluorescenceList() {
        this.session.setViewType(4);
        this.rlLow.setVisibility(8);
        this.tvNormal.setText(C0418R.string.trend_wbc_chart_normal);
        this.tvHigh.setText(C0418R.string.trend_wbc_chart_high);
        this.tvLow.setText(C0418R.string.trend_wbc_chart_low);
        this.pocttrendChartView.setPostInvalidate();
        this.pocttrendChartView.requestLayout();
        final List<Immunofluorescence> queryAll = this.immunofluorescenceDao.queryAll();
        final ImmunofluResultListAdapter immunofluResultListAdapter = new ImmunofluResultListAdapter(getActivity(), queryAll);
        this.lvAnalyzeResult.setAdapter(immunofluResultListAdapter);
        this.lvAnalyzeResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (!immunofluResultListAdapter.isShowCheckBox()) {
                    Intent intent = new Intent(AnalyzeResultFragment.this.getActivity(), ImmunefluResultDetailActivity.class);
                    intent.putExtra("position", i);
                    intent.putExtra("data", (Serializable) queryAll.get(i));
                    AnalyzeResultFragment analyzeResultFragment = AnalyzeResultFragment.this;
                    analyzeResultFragment.startActivityForResult(intent, analyzeResultFragment.REQUEST_CODE__POCT);
                }
            }
        });
        this.lvAnalyzeResult.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                immunofluResultListAdapter.checked(i);
                immunofluResultListAdapter.show(true);
                AnalyzeResultFragment.this.cvDelete.setVisibility(0);
                return true;
            }
        });
    }

    public void refresh() {
        CardView cardView = this.cvDelete;
        if (cardView != null && cardView.getVisibility() == 0) {
            this.cvDelete.setVisibility(8);
        }
        this.tvTitle.setText(this.titles[this.index]);
        int i = this.index;
        if (i == 0) {
            this.llTrendChart.setVisibility(8);
            showUrinalysisList();
        } else if (i == 1) {
            this.llTrendChart.setVisibility(8);
            showPOCTList();
        } else if (i == 2) {
            this.llTrendChart.setVisibility(8);
            showProteinList();
        } else if (i == 3) {
            this.llTrendChart.setVisibility(8);
            showBloodFatList();
        } else if (i == 4) {
            this.llTrendChart.setVisibility(8);
            showImmunofluorescenceList();
        }
    }

    private void showPop(View view, final View.OnClickListener onClickListener) {
        final PopupWindow popupWindow = new PopupWindow(this.context);
        View inflate = LayoutInflater.from(this.context).inflate(C0418R.layout.pop_analyze_result_operate, (ViewGroup) null);
        popupWindow.setContentView(inflate);
        inflate.findViewById(C0418R.C0420id.tvDeleteResult).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                popupWindow.dismiss();
                onClickListener.onClick(view);
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(view, 220, -60);
    }

    private void showChangeAnalyzeTypePop(View view) {
        final PopupWindow popupWindow = new PopupWindow(this.context);
        View inflate = LayoutInflater.from(this.context).inflate(C0418R.layout.pop_change_analyze_result_type, (ViewGroup) null);
        popupWindow.setContentView(inflate);
        popupWindow.setWidth(-2);
        popupWindow.setHeight(-2);
        ((RadioButton) ((RadioGroup) inflate.findViewById(C0418R.C0420id.rgAnalyzeType)).getChildAt(this.index)).setChecked(true);
        C053112 r2 = new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case C0418R.C0420id.rbBlood_Fat:
                        popupWindow.dismiss();
                        int unused = AnalyzeResultFragment.this.index = 3;
                        AnalyzeResultFragment.this.refresh();
                        return;
                    case C0418R.C0420id.rbMYYG:
                        popupWindow.dismiss();
                        int unused2 = AnalyzeResultFragment.this.index = 4;
                        AnalyzeResultFragment.this.refresh();
                        return;
                    case C0418R.C0420id.rbNJ:
                        popupWindow.dismiss();
                        int unused3 = AnalyzeResultFragment.this.index = 0;
                        AnalyzeResultFragment.this.refresh();
                        return;
                    case C0418R.C0420id.rbPOCT:
                        popupWindow.dismiss();
                        int unused4 = AnalyzeResultFragment.this.index = 1;
                        AnalyzeResultFragment.this.refresh();
                        return;
                    case C0418R.C0420id.rbTDDB:
                        popupWindow.dismiss();
                        int unused5 = AnalyzeResultFragment.this.index = 2;
                        AnalyzeResultFragment.this.refresh();
                        return;
                    default:
                        return;
                }
            }
        };
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                boolean unused = AnalyzeResultFragment.this.isChangeAnalyzeTypePopShow = false;
            }
        });
        inflate.findViewById(C0418R.C0420id.rbNJ).setOnClickListener(r2);
        inflate.findViewById(C0418R.C0420id.rbPOCT).setOnClickListener(r2);
        inflate.findViewById(C0418R.C0420id.rbTDDB).setOnClickListener(r2);
        inflate.findViewById(C0418R.C0420id.rbBlood_Fat).setOnClickListener(r2);
        inflate.findViewById(C0418R.C0420id.rbMYYG).setOnClickListener(r2);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.showAsDropDown(view, 200, 30);
        this.isChangeAnalyzeTypePopShow = true;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id != C0418R.C0420id.ivChangeAnalyzeResultType) {
            if (id == C0418R.C0420id.tvDeleteResult) {
                delete();
            } else if (id == C0418R.C0420id.tvExit) {
                this.cvDelete.setVisibility(8);
                refresh();
            }
        } else if (this.isChangeAnalyzeTypePopShow) {
            this.isChangeAnalyzeTypePopShow = false;
        } else {
            showChangeAnalyzeTypePop(view);
        }
    }

    private void delete() {
        this.cvDelete.setVisibility(8);
        int i = this.index;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i == 4 && (this.lvAnalyzeResult.getAdapter() instanceof ImmunofluResultListAdapter)) {
                            this.immunofluorescenceDao.delete(((ImmunofluResultListAdapter) this.lvAnalyzeResult.getAdapter()).getDeleteIds());
                            showImmunofluorescenceList();
                        }
                    } else if (this.lvAnalyzeResult.getAdapter() instanceof BloodFatResultListAdapter) {
                        this.bloodFatDao.delete(((BloodFatResultListAdapter) this.lvAnalyzeResult.getAdapter()).getDeleteIds());
                        showBloodFatList();
                    }
                } else if (this.lvAnalyzeResult.getAdapter() instanceof ProteinResultListAdapter) {
                    this.proteinDao.delete(((ProteinResultListAdapter) this.lvAnalyzeResult.getAdapter()).getDeleteIds());
                    showProteinList();
                }
            } else if (this.lvAnalyzeResult.getAdapter() instanceof POCTResultListAdapter) {
                this.poctDao.delete(((POCTResultListAdapter) this.lvAnalyzeResult.getAdapter()).getDeleteIds());
                showPOCTList();
            }
        } else if (this.lvAnalyzeResult.getAdapter() instanceof UrinalysisResultListAdapter) {
            this.urinalysisDao.delete(((UrinalysisResultListAdapter) this.lvAnalyzeResult.getAdapter()).getDeleteIds());
            showUrinalysisList();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == this.REQUEST_CODE__POCT) {
            char c = 65535;
            if (i2 == -1 && intent != null) {
                String action = intent.getAction();
                switch (action.hashCode()) {
                    case -530064037:
                        if (action.equals(BloodFatResultDetailActivity.REFRESH)) {
                            c = 3;
                            break;
                        }
                        break;
                    case 6036104:
                        if (action.equals(ImmunefluResultDetailActivity.REFRESH)) {
                            c = 4;
                            break;
                        }
                        break;
                    case 61314559:
                        if (action.equals(ProteinResultDetailActivity.REFRESH)) {
                            c = 2;
                            break;
                        }
                        break;
                    case 1085444827:
                        if (action.equals(UrinalysisResultDetailActivity.REFRESH)) {
                            c = 1;
                            break;
                        }
                        break;
                    case 1636638796:
                        if (action.equals(POCTResultDetailActivity.REFRESH)) {
                            c = 0;
                            break;
                        }
                        break;
                }
                if (c == 0 || c == 1 || c == 2 || c == 3 || c == 4) {
                    refresh();
                }
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
