package com.eanfang.ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.R;
import com.eanfang.biz.model.SecurityCompanyDetailsBean;
import com.eanfang.ui.adapter.GdNlAdapter;
import com.eanfang.ui.adapter.GdNlBAdapter;
import com.eanfang.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WQ
 */
public class GdNlFragment extends BaseFragment {
    private String mTitle;
private List<SecurityCompanyDetailsBean.AbilityListBean> abilityList=new ArrayList<>();
private List<SecurityCompanyDetailsBean.ToolListBean> toolList=new ArrayList<>();
    public GdNlFragment() {
    }


    public static GdNlFragment getInstance(String title) {
        GdNlFragment sf = new GdNlFragment();
        sf.mTitle = title;
        return sf;
    }

    public static Fragment getInstance(List<SecurityCompanyDetailsBean.AbilityListBean> abilityList, List<SecurityCompanyDetailsBean.ToolListBean> toolList) {
        GdNlFragment sf = new GdNlFragment();
        sf.abilityList = abilityList;
        sf.toolList = toolList;
        return sf;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_zz_xx;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        RecyclerView rvA = (RecyclerView) findViewById(R.id.rv_a);
        RecyclerView rvB = (RecyclerView) findViewById(R.id.rv_b);

        initRecyclerViewA(rvA, abilityList);
        initRecyclerViewB(rvB, toolList);
    }

    private void initRecyclerViewA(RecyclerView recyclerView, List<SecurityCompanyDetailsBean.AbilityListBean> stringList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        GdNlAdapter adapterA = new GdNlAdapter(true);
        adapterA.bindToRecyclerView(recyclerView);
        adapterA.setNewData(stringList);
    }

    private void initRecyclerViewB(RecyclerView recyclerView, List<SecurityCompanyDetailsBean.ToolListBean> stringList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        GdNlBAdapter adapterB = new GdNlBAdapter(true);
        adapterB.bindToRecyclerView(recyclerView);
        adapterB.setNewData(stringList);
    }

    @Override
    protected void setListener() {

    }
}
