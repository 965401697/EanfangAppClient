package net.eanfang.worker.ui.fragment.neworder;

import androidx.lifecycle.ViewModel;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.bean.WorkspaceInstallBean;
import com.eanfang.util.PermKit;

import net.eanfang.worker.ui.adapter.neworder.HomeOrderAdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;
import net.eanfang.worker.ui.widget.InstallCtrlItemView;
import net.eanfang.worker.viewmodle.neworder.HistoryOrderViewModle;

/**
 * @author guanluocang
 * @data 2019/10/30  9:38
 * @description 历史订单 报装
 */

public class HistoryInstallFragment extends TemplateItemListFragment {

    public String mType;
    private HomeOrderAdapter homeOrderAdapter;

    private HistoryOrderViewModle historyOrderViewModle;

    public static HistoryInstallFragment getInstance(String type, HistoryOrderViewModle mHistoryOrderViewModle) {
        HistoryInstallFragment homeInstallFragment = new HistoryInstallFragment();
        homeInstallFragment.mType = type;
        homeInstallFragment.historyOrderViewModle = mHistoryOrderViewModle;
        return homeInstallFragment;
    }
    public void getTenderData(QueryEntry queryEntry) {
    }
    @Override
    protected void initAdapter(BaseQuickAdapter baseQuickAdapter) {
        homeOrderAdapter = new HomeOrderAdapter(true);
        super.initAdapter(homeOrderAdapter);
        homeOrderAdapter.setOnItemClickListener(((adapter, view, position) -> {
            if (!PermKit.get().getInstallDetailPrem()) {
                return;
            }
            new InstallCtrlItemView(getActivity(), true, ((WorkspaceInstallBean.ListBean) adapter.getData().get(position)).getId()).show();

        }));

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
