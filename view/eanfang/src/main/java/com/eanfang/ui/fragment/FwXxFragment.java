package com.eanfang.ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.eanfang.R;
import com.eanfang.ui.adapter.FwXxAdapter;
import com.eanfang.ui.base.BaseFragment;

import java.util.List;

/**
 * @author WQ
 */
public class FwXxFragment extends BaseFragment {
    private String mTitle;
    private List<String> bizList, areaList,list;

    public FwXxFragment() {
    }


    public static FwXxFragment getInstance(String title) {
        FwXxFragment sf = new FwXxFragment();
        sf.mTitle = title;
        return sf;
    }

    public static Fragment getInstance(List<String> list, List<String> bizList, List<String> areaList) {
        FwXxFragment sf = new FwXxFragment();
        sf.bizList = bizList;
        sf.areaList = areaList;
        sf.list = list;
        return sf;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_fw_xx;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        RecyclerView rvA = findViewById(R.id.rv_a);
        RecyclerView rvB = findViewById(R.id.rv_b);
        RecyclerView rvC = findViewById(R.id.rv_c);

        initRecyclerView(rvA,bizList);
        initRecyclerView(rvB,list);
        initRecyclerView(rvC,areaList);
    }
    private void initRecyclerView(RecyclerView recyclerView,List<String> stringList) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        FwXxAdapter  adapterA = new FwXxAdapter(true);
        adapterA.bindToRecyclerView(recyclerView);
        adapterA.setNewData(stringList);
    }
    @Override
    protected void setListener() {

    }
}
