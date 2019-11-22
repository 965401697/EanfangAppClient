package net.eanfang.worker.ui.fragment.neworder;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.ui.activity.worksapce.tender.TenderFindDetailActivity;
import net.eanfang.worker.ui.adapter.neworder.HomeOrderAdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;
import net.eanfang.worker.viewmodle.neworder.HistoryOrderViewModle;

/**
 * @author guanluocang
 * @data 2019/10/30  9:38
 * @description 用工
 */

public class HistoryTaskApplyFragment extends TemplateItemListFragment {

    public String mType;

    private HomeOrderAdapter homeOrderAdapter;

    private HistoryOrderViewModle historyOrderViewModle;

    public static HistoryTaskApplyFragment getInstance(String type, HistoryOrderViewModle mHistoryOrderViewModle) {
        HistoryTaskApplyFragment homeTaskApplyFragment = new HistoryTaskApplyFragment();
        homeTaskApplyFragment.mType = type;
        homeTaskApplyFragment.historyOrderViewModle = mHistoryOrderViewModle;
        return homeTaskApplyFragment;
    }

    @Override
    public void onRefresh() {
        historyOrderViewModle.mQueryEntry = null;
        super.onRefresh();
    }

    @Override
    protected void getData() {
        historyOrderViewModle.doGetHistroryOrder(mType, mPage);
    }

    @Override
    protected void initAdapter(BaseQuickAdapter baseQuickAdapter) {
        homeOrderAdapter = new HomeOrderAdapter(true);
        super.initAdapter(homeOrderAdapter);
        homeOrderAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putLong("tendFindId", homeOrderAdapter.getData().get(position).getId());
            JumpItent.jump(getActivity(), TenderFindDetailActivity.class, bundle);
        });
    }


    @Override
    protected ViewModel initViewModel() {
//        historyOrderViewModle = LViewModelProviders.of(getActivity(), HistoryOrderViewModle.class);
        historyOrderViewModle.getHistoryTaskApplyMutableLiveData().observe(this, this::getCommenData);
        return historyOrderViewModle;
    }


}
