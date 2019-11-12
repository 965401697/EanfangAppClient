package net.eanfang.worker.ui.fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.eanfang.base.BaseFragment;
import com.eanfang.biz.model.entity.OrderBean;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.databinding.FragmentOrderBinding;
import net.eanfang.worker.ui.activity.order.HomeOrderHistoryActivity;
import net.eanfang.worker.ui.adapter.neworder.HomeOrderAdapter;
import net.eanfang.worker.ui.fragment.neworder.HomePendingFragment;
import net.eanfang.worker.ui.fragment.neworder.HomeProgressFragment;
import net.eanfang.worker.viewmodle.neworder.HomeOrderViewModle;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import q.rorbin.badgeview.QBadgeView;

/**
 * @author guanluocang
 * @data 2019/10/25  15:07
 * @description 订单页面
 */

public class OrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentOrderBinding orderBinding;

    private String[] mTitles = {"待处理", "进行中"};
    private MyPagerAdapter mAdapter;
    private HomeOrderViewModle homeOrderViewModle;


    private HomeOrderAdapter homeOrderAdapter;

    private QBadgeView qBadgeViewReport = new QBadgeView(WorkerApplication.get().getApplicationContext());


    @Override
    protected ViewModel initViewModel() {
        orderBinding = FragmentOrderBinding.inflate(getLayoutInflater());
        homeOrderViewModle = LViewModelProviders.of(getActivity(), HomeOrderViewModle.class);
        homeOrderViewModle.setFragmentOrderBinding(orderBinding);
        return homeOrderViewModle;
    }

    private void getProgressData(List<OrderBean> orderList) {
        if (orderList != null && orderList.size() > 0) {
            qBadgeViewReport.bindTarget(orderBinding.tvOrder)
                    .setBadgeBackgroundColor(0xFFFF0000)
                    .setBadgePadding(5, true)
                    .setBadgeGravity(Gravity.END | Gravity.TOP)
                    .setGravityOffset(0, 1, true)
                    .setBadgeTextSize(10, true)
                    .setBadgeNumber(orderList.size());
            orderBinding.swipreFresh.setRefreshing(false);
            homeOrderAdapter.setNewData(orderList);
        } else {
            orderBinding.swipreFresh.setRefreshing(false);
            orderBinding.rvList.setVisibility(View.GONE);
            orderBinding.tvNoDatas.setVisibility(View.VISIBLE);
        }

    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        if (WorkerApplication.get().getUserId().equals(WorkerApplication.get().getCompanyEntity().getOrgUnitEntity().getAdminUserId())) {
            // 管理员
            orderBinding.llAdmin.setVisibility(View.VISIBLE);
            orderBinding.swipreFresh.setVisibility(View.GONE);

            mAdapter = new MyPagerAdapter(getChildFragmentManager(), mTitles);
            mAdapter.getFragments().add(HomePendingFragment.getInstance(homeOrderViewModle));
            mAdapter.getFragments().add(HomeProgressFragment.getInstance(homeOrderViewModle));
            orderBinding.vpOrderList.setAdapter(mAdapter);
            orderBinding.vpOrderList.setCurrentItem(0);
        } else {
            // 技师
            orderBinding.llAdmin.setVisibility(View.GONE);
            orderBinding.swipreFresh.setVisibility(View.VISIBLE);

            homeOrderAdapter = new HomeOrderAdapter();
            orderBinding.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
            orderBinding.swipreFresh.setOnRefreshListener(this);
            homeOrderAdapter.bindToRecyclerView(orderBinding.rvList);
            homeOrderViewModle.doGetProgressData(1);
            homeOrderViewModle.getProgressMutableLiveData().observe(this, this::getProgressData);
        }

        initListener();
        return orderBinding.getRoot();
    }

    private void initListener() {
        // 待处理
        orderBinding.tvPending.setOnClickListener((v) -> {
            orderBinding.tvPending.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
            orderBinding.tvHaveIn.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_new_order_unselect));
            orderBinding.viewPending.setVisibility(View.VISIBLE);
            orderBinding.viewHaveIn.setVisibility(View.GONE);
        });
        // 进行中
        orderBinding.tvHaveIn.setOnClickListener((v) -> {
            orderBinding.tvPending.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_new_order_unselect));
            orderBinding.tvHaveIn.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
            orderBinding.viewPending.setVisibility(View.GONE);
            orderBinding.viewHaveIn.setVisibility(View.VISIBLE);

        });
        orderBinding.ivOrderHestory.setOnClickListener((v) -> {
            JumpItent.jump(getActivity(), HomeOrderHistoryActivity.class);
        });

        orderBinding.vpOrderList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        orderBinding.tvPending.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                        orderBinding.tvHaveIn.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_new_order_unselect));
                        orderBinding.viewPending.setVisibility(View.VISIBLE);
                        orderBinding.viewHaveIn.setVisibility(View.GONE);
                        break;
                    case 1:
                        orderBinding.tvPending.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_new_order_unselect));
                        orderBinding.tvHaveIn.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                        orderBinding.viewPending.setVisibility(View.GONE);
                        orderBinding.viewHaveIn.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onRefresh() {
        homeOrderViewModle.doGetProgressData(1);
    }

    /**
     * viewpager Adapter
     */
    protected class MyPagerAdapter extends FragmentPagerAdapter {
        @Getter
        private String[] titles;
        @Getter
        private ArrayList<Fragment> fragments;

        /**
         * 普通，主页使用
         */
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public MyPagerAdapter(FragmentManager fm, String[] mTitles) {
            super(fm);
            fragments = new ArrayList<>();
            this.titles = mTitles;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }

}
