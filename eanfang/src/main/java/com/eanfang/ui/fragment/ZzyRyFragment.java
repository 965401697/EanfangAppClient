package com.eanfang.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eanfang.R;
import com.eanfang.model.SecurityCompanyDetailsBean;
import com.eanfang.ui.adapter.ZzyRyAdapter;
import com.eanfang.ui.adapter.ZzyRyBAdapter;
import com.eanfang.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WQ
 */
public class ZzyRyFragment extends BaseFragment {
    private String mTitle;
    private List<SecurityCompanyDetailsBean.AptitudeListBean> aptitudeList = new ArrayList<>();
    private List<SecurityCompanyDetailsBean.GloryListBean> gloryList = new ArrayList<>();

    public ZzyRyFragment() {
    }


    public static ZzyRyFragment getInstance(String title) {
        ZzyRyFragment sf = new ZzyRyFragment();
        sf.mTitle = title;
        return sf;
    }

    public static Fragment getInstance(List<SecurityCompanyDetailsBean.AptitudeListBean> aptitudeList, List<SecurityCompanyDetailsBean.GloryListBean> gloryList) {
        ZzyRyFragment sf = new ZzyRyFragment();
        sf.aptitudeList = aptitudeList;
        sf.gloryList = gloryList;
        return sf;

    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_nl_xx;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        RecyclerView rvA = (RecyclerView) findViewById(R.id.rv_a);
        RecyclerView rvB = (RecyclerView) findViewById(R.id.rv_b);

        initRecyclerViewA(rvA, aptitudeList);
        initRecyclerViewB(rvB, gloryList);
    }

    private void initRecyclerViewA(RecyclerView recyclerView, List<SecurityCompanyDetailsBean.AptitudeListBean> stringList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        ZzyRyAdapter adapterA = new ZzyRyAdapter(true);
        adapterA.bindToRecyclerView(recyclerView);
        adapterA.setNewData(stringList);
    }

    private void initRecyclerViewB(RecyclerView recyclerView, List<SecurityCompanyDetailsBean.GloryListBean> stringList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        ZzyRyBAdapter adapterB = new ZzyRyBAdapter(true);
        adapterB.bindToRecyclerView(recyclerView);
        adapterB.setNewData(stringList);
    }

    @Override
    protected void setListener() {

    }
}
