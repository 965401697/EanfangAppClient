package net.eanfang.worker.ui.fragment.neworder;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.DesignOrderListBean;
import com.eanfang.biz.model.entity.OrderBean;
import com.eanfang.util.PermKit;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.design.DesignOrderDetailActivity;
import net.eanfang.worker.ui.adapter.neworder.HomeOrderAdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;
import net.eanfang.worker.viewmodle.neworder.HistoryOrderViewModle;

/**
 * @author guanluocang
 * @data 2019/10/30  9:38
 * @description 历史订单 报修
 */

public class HistoryDesignFragment extends TemplateItemListFragment {

    public String mType;

    private HomeOrderAdapter homeOrderAdapter;

    private HistoryOrderViewModle historyOrderViewModle;

    public static HistoryDesignFragment getInstance(String type, HistoryOrderViewModle mHistoryOrderViewModle) {
        HistoryDesignFragment homeDesignFragment = new HistoryDesignFragment();
        homeDesignFragment.mType = type;
        homeDesignFragment.historyOrderViewModle = mHistoryOrderViewModle;
        return homeDesignFragment;
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
        homeOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!PermKit.get().getDesignDetailPrem()) {
                    return;
                }
                startActivity(new Intent(getActivity(), DesignOrderDetailActivity.class).putExtra("id", String.valueOf(((OrderBean) adapter.getData().get(position)).getId())));
            }
        });
        homeOrderAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_detail:
                    if (!PermKit.get().getDesignDetailPrem()) {
                        return;
                    }
                    DesignOrderListBean.ListBean bean = (DesignOrderListBean.ListBean) adapter.getData().get(position);
                    startActivity(new Intent(getActivity(), DesignOrderDetailActivity.class).putExtra("id", String.valueOf(((DesignOrderListBean.ListBean) adapter.getData().get(position)).getId())));
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    protected ViewModel initViewModel() {
//        historyOrderViewModle = LViewModelProviders.of(getActivity(), HistoryOrderViewModle.class);
        historyOrderViewModle.getHistoryDesignMutableLiveData().observe(this, this::getCommenData);
        return historyOrderViewModle;
    }


}
