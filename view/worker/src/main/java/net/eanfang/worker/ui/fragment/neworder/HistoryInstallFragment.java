package net.eanfang.worker.ui.fragment.neworder;

import androidx.lifecycle.ViewModel;

import com.chad.library.adapter.base.BaseQuickAdapter;

import net.eanfang.worker.ui.adapter.neworder.HomeOrderApdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;
import net.eanfang.worker.viewmodle.neworder.HistoryOrderViewModle;

/**
 * @author guanluocang
 * @data 2019/10/30  9:38
 * @description 历史订单 报装
 */

public class HistoryInstallFragment extends TemplateItemListFragment {

    public String mType;
    private HomeOrderApdapter homeOrderApdapter;

    private HistoryOrderViewModle historyOrderViewModle;

    public static HistoryInstallFragment getInstance(String type, HistoryOrderViewModle mHistoryOrderViewModle) {
        HistoryInstallFragment homeInstallFragment = new HistoryInstallFragment();
        homeInstallFragment.mType = type;
        homeInstallFragment.historyOrderViewModle = mHistoryOrderViewModle;
        return homeInstallFragment;
    }

    @Override
    protected void initAdapter(BaseQuickAdapter baseQuickAdapter) {
        homeOrderApdapter = new HomeOrderApdapter();
        super.initAdapter(homeOrderApdapter);
    }

    @Override
    public void onRefresh() {
        historyOrderViewModle.mQueryEntry = null;
        super.onRefresh();
    }

    @Override
    protected ViewModel initViewModel() {
//        historyOrderViewModle = LViewModelProviders.of(getActivity(), HistoryOrderViewModle.class);
        historyOrderViewModle.getHistoryInstallMutableLiveData().observe(this, this::getCommenData);
        return historyOrderViewModle;
    }


    @Override
    protected void getData() {
        historyOrderViewModle.doGetHistroryOrder(mType, mPage);
    }
}
