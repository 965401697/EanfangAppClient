package net.eanfang.worker.ui.fragment.neworder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eanfang.base.BaseFragment;
import com.eanfang.biz.model.entity.OrderBean;

import net.eanfang.worker.databinding.FragmentProgressBinding;
import net.eanfang.worker.ui.adapter.neworder.HomeOrderAdapter;
import net.eanfang.worker.viewmodle.neworder.HomeOrderViewModle;

import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/10/29  16:02
 * @description 进行中订单
 */
public class HomeProgressFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Setter
    @Accessors(chain = true)
    private HomeOrderViewModle mHomeOrderViewModle;
    private HomeOrderAdapter homeOrderAdapter;


    private FragmentProgressBinding fragmentProgressBinding;

    public static HomeProgressFragment getInstance(HomeOrderViewModle homeOrderViewModle) {
        return new HomeProgressFragment().setMHomeOrderViewModle(homeOrderViewModle);
    }

    @Override
    protected ViewModel initViewModel() {
        return mHomeOrderViewModle;
    }

    private void getProgressData(List<OrderBean> orderList) {
        if (orderList != null && orderList.size() > 0) {
            fragmentProgressBinding.rvList.setVisibility(View.VISIBLE);
            fragmentProgressBinding.tvNoDatas.setVisibility(View.GONE);
            fragmentProgressBinding.swipreFresh.setRefreshing(false);
            homeOrderAdapter.setNewData(orderList);
        } else {
            fragmentProgressBinding.swipreFresh.setRefreshing(false);
            fragmentProgressBinding.rvList.setVisibility(View.GONE);
            fragmentProgressBinding.tvNoDatas.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        fragmentProgressBinding = FragmentProgressBinding.inflate(getLayoutInflater());
        mHomeOrderViewModle.doGetProgressData(1);
        mHomeOrderViewModle.getProgressMutableLiveData().observe(this, this::getProgressData);
        homeOrderAdapter = new HomeOrderAdapter();
        fragmentProgressBinding.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentProgressBinding.swipreFresh.setOnRefreshListener(this);
        homeOrderAdapter.bindToRecyclerView(fragmentProgressBinding.rvList);
        return fragmentProgressBinding.getRoot();
    }


    @Override
    public void onRefresh() {
        mHomeOrderViewModle.doGetProgressData(1);
    }
}
