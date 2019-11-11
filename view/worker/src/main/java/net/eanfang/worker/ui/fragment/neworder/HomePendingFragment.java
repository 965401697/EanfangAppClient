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
import net.eanfang.worker.ui.adapter.neworder.HomeOrderApdapter;
import net.eanfang.worker.viewmodle.neworder.HomeOrderViewModle;

import java.util.ArrayList;
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

    private HomeOrderApdapter homeOrderApdapter;
    private List<String> mProgressList = new ArrayList<>();

    public static HomePendingFragment getInstance(HomeOrderViewModle homeOrderViewModle) {
        return new HomePendingFragment().setMHomeOrderViewModle(homeOrderViewModle);
    }


    @Override
    protected ViewModel initViewModel() {
        mHomeOrderViewModle.doGetProgressData(0);
        mHomeOrderViewModle.getPendingMutableLiveData().observe(this, this::getProgressData);
        return mHomeOrderViewModle;
    }

    private void getProgressData(List<OrderBean> orderList) {
        if (orderList != null && orderList.size() > 0) {
            fragmentPendingBinding.rvList.setVisibility(View.VISIBLE);
            fragmentPendingBinding.tvNoDatas.setVisibility(View.GONE);
            fragmentPendingBinding.swipreFresh.setRefreshing(false);
            homeOrderApdapter.setNewData(orderList);
        } else {
            fragmentPendingBinding.swipreFresh.setRefreshing(false);
            fragmentPendingBinding.rvList.setVisibility(View.GONE);
            fragmentPendingBinding.tvNoDatas.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        fragmentPendingBinding = FragmentPendingBinding.inflate(getLayoutInflater());
        homeOrderApdapter = new HomeOrderApdapter();
        fragmentPendingBinding.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentPendingBinding.swipreFresh.setOnRefreshListener(this);
        homeOrderApdapter.bindToRecyclerView(fragmentPendingBinding.rvList);
        return fragmentPendingBinding.getRoot();
    }


    @Override
    public void onRefresh() {
        mHomeOrderViewModle.doGetProgressData(0);
    }
}
