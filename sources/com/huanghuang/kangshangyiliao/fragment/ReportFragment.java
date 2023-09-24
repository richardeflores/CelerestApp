package com.huanghuang.kangshangyiliao.fragment;

import android.os.Bundle;
import android.support.p000v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.huanghuang.kangshangyiliao.C0418R;
import com.huanghuang.kangshangyiliao.adapter.ReportBriefAdapter;
import com.huanghuang.kangshangyiliao.bean.ReportDetailsBean;
import java.util.ArrayList;

@Deprecated
public class ReportFragment extends Fragment {
    private ListView listView_report;
    private ReportBriefAdapter reportBriefAdapter;
    private View rootView;

    private void initListener() {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            this.rootView = layoutInflater.inflate(C0418R.layout.fragment_report, (ViewGroup) null);
        }
        initView();
        ViewGroup viewGroup2 = (ViewGroup) this.rootView.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
        initListener();
        return this.rootView;
    }

    private void initView() {
        this.listView_report = (ListView) this.rootView.findViewById(C0418R.C0420id.ListView_Report);
        ArrayList arrayList = new ArrayList();
        ReportDetailsBean reportDetailsBean = new ReportDetailsBean();
        arrayList.add(reportDetailsBean);
        arrayList.add(reportDetailsBean);
        arrayList.add(reportDetailsBean);
        arrayList.add(reportDetailsBean);
        this.reportBriefAdapter = new ReportBriefAdapter(getActivity(), arrayList);
        this.listView_report.setAdapter(this.reportBriefAdapter);
    }
}
