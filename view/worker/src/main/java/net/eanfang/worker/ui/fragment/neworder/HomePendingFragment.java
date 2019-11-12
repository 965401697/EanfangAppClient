package net.eanfang.worker.ui.fragment.neworder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eanfang.base.BaseFragment;
import com.eanfang.biz.model.entity.OrderBean;

import net.eanfang.worker.databinding.FragmentPendingBinding;
import net.eanfang.worker.ui.adapter.neworder.HomeOrderAdapter;
import net.eanfang.worker.viewmodle.neworder.HomeOrderViewModle;

import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/10/29  16:02
 * @description 待处理
 */

public class HomePendingFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentPendingBinding fragmentPendingBinding;
    @Setter
    @Accessors(chain = true)
    private HomeOrderViewModle mHomeOrderViewModle;

    private HomeOrderAdapter homeOrderAdapter;

    public static HomePendingFragment getInstance(HomeOrderViewModle homeOrderViewModle) {
        return new HomePendingFragment().setMHomeOrderViewModle(homeOrderViewModle);
    }


    @Override
    protected ViewModel initViewModel() {
        return mHomeOrderViewModle;
    }

    private void getProgressData(List<OrderBean> orderList) {
        if (orderList != null && orderList.size() > 0) {
            fragmentPendingBinding.rvList.setVisibility(View.VISIBLE);
            fragmentPendingBinding.tvNoDatas.setVisibility(View.GONE);
            fragmentPendingBinding.swipreFresh.setRefreshing(false);
            homeOrderAdapter.setNewData(orderList);
        } else {
            fragmentPendingBinding.swipreFresh.setRefreshing(false);
            fragmentPendingBinding.rvList.setVisibility(View.GONE);
            fragmentPendingBinding.tvNoDatas.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        fragmentPendingBinding = FragmentPendingBinding.inflate(getLayoutInflater());
        mHomeOrderViewModle.doGetProgressData(0);
        mHomeOrderViewModle.getPendingMutableLiveData().observe(this, this::getProgressData);
        homeOrderAdapter = new HomeOrderAdapter();
        fragmentPendingBinding.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentPendingBinding.swipreFresh.setOnRefreshListener(this);
        homeOrderAdapter.bindToRecyclerView(fragmentPendingBinding.rvList);
        return fragmentPendingBinding.getRoot();
    }


    @Override
    public void onRefresh() {
        mHomeOrderViewModle.doGetProgressData(0);
    }
}
