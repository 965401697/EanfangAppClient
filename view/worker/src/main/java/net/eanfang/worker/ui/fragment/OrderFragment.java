package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.eanfang.base.kit.V;
import com.eanfang.biz.model.entity.OrderBean;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.CallUtils;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.databinding.FragmentOrderBinding;
import net.eanfang.worker.ui.activity.order.HomeOrderHistoryActivity;
import net.eanfang.worker.ui.activity.worksapce.EvaluateClientActivity;
import net.eanfang.worker.ui.activity.worksapce.SignInActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.RepairCtrlActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.SolveModeActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.finishwork.FillRepairInfoActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.finishwork.PhoneSolveRepairInfoActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.seefaultdetail.PsTroubleDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.seefaultdetail.TroubleDetailActivity;
import net.eanfang.worker.ui.adapter.neworder.HomeOrderAdapter;
import net.eanfang.worker.ui.fragment.neworder.HomePendingFragment;
import net.eanfang.worker.ui.fragment.neworder.HomeProgressFragment;
import net.eanfang.worker.ui.widget.FillAppointmentInfoRebookView;
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

    private View root;

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
        if (orderBinding.getRoot() != null) {
            ViewGroup p = (ViewGroup) orderBinding.getRoot().getParent();
            if (p != null) {
                p.removeAllViewsInLayout();
            }
        }
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
            orderBinding.vpOrderList.setCurrentItem(0);
        });
        // 进行中
        orderBinding.tvHaveIn.setOnClickListener((v) -> {
            orderBinding.tvPending.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_new_order_unselect));
            orderBinding.tvHaveIn.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
            orderBinding.viewPending.setVisibility(View.GONE);
            orderBinding.viewHaveIn.setVisibility(View.VISIBLE);
            orderBinding.vpOrderList.setCurrentItem(1);

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
        homeOrderAdapter.setOnItemClickListener(((adapter, view, position) -> {
            OrderBean item = homeOrderAdapter.getData().get(position);
            switchCase(item, view);
        }));


    }

    private void switchCase(OrderBean item, View view) {
        Intent intent;
        //获取：订单状态( 0:待支付，1:待回电，2:待上门，3:待完工，4:待确认，5:订单完成)
        switch (item.getStatus()) {
            case 0:
                switch (view.getId()) {
                    case R.id.tv_do_second:
                        //只有当前登陆人为订单负责人才可以操作
                        //联系客户
                        CallUtils.call(getActivity(), item.getContactPhone());
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                switch (view.getId()) {
                    case R.id.tv_do_second:
                        // 解决方式
                        //马上回电
                        Bundle bundle = new Bundle();
                        bundle.putLong("orderId", item.getId());
                        JumpItent.jump(getActivity(), SolveModeActivity.class, bundle, RepairCtrlActivity.REFREST_ITEM);
                        //给客户联系人打电话
                        CallUtils.call(getActivity(), V.v(() -> item.getContactPhone()));
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                // 待上门 签到
                switch (view.getId()) {

                    case R.id.tv_do_first:
                        //只有当前登陆人为订单负责人才可以操作
                        Bundle bundle = new Bundle();
                        bundle.putLong("itemId", item.getId());
                        bundle.putBoolean("isReAppoint", true);
                        JumpItent.jump(getActivity(), FillAppointmentInfoRebookView.class, bundle);
                        break;
                    case R.id.tv_do_second:
                        //只有当前登陆人为订单负责人才可以操作
                        intent = new Intent(getActivity(), SignInActivity.class);
                        intent.putExtra("orderId", item.getId());
                        intent.putExtra("latitude", item.getLatitude());
                        intent.putExtra("longitude", item.getLongitude());
                        getActivity().startActivityForResult(intent, RepairCtrlActivity.REFREST_ITEM);
                        break;
                    default:
                        break;
                }
                break;


            case 3:
                switch (view.getId()) {
                    case R.id.tv_do_first:
                        //给客户联系人打电话
                        //联系客户
                        CallUtils.call(getActivity(), V.v(() -> item.getContactPhone()));
                        break;
                    case R.id.tv_do_second:
                        //只有当前登陆人为订单负责人才可以操作
                        // 是否 电话解决 0：未解决，1：已解决
                        //完工
                        if (item.getIsPhoneSolve() == 0) {
                            intent = new Intent(getActivity(), FillRepairInfoActivity.class);
                        } else {
                            intent = new Intent(getActivity(), PhoneSolveRepairInfoActivity.class);
                        }
                        intent.putExtra("orderId", item.getId());
                        intent.putExtra("workerUserId", item.getAssigneeUserId());
//                        if (item.getOwnerOrg() != null) {
//                            intent.putExtra("companyName", item.getOwnerOrg().getBelongCompany().getOrgName());
//                        } else {
//                            intent.putExtra("companyName", "个人");
//                        }

                        intent.putExtra("phoneSolve", item.getIsPhoneSolve());
                        intent.putExtra("companyUid", item.getAssigneeCompanyId());
                        intent.putExtra("clientCompanyUid", item.getOwnerCompanyId());
                        getActivity().startActivityForResult(intent, RepairCtrlActivity.REFREST_ITEM);
                        break;
                    default:
                        break;
                }
                break;
            //待确认
            case 4:
                switch (view.getId()) {
                    case R.id.tv_do_first:
                        CallUtils.call(getActivity(), V.v(() -> item.getAssigneeUser().getAccountEntity().getMobile()));
                        break;
                    case R.id.tv_do_second:
                        doJumpTroubleDetail(item.getId(), item.getIsPhoneSolve());
                        break;
                    default:
                        break;
                }
                break;
            //待评价
            case 5:
                switch (view.getId()) {

                    case R.id.tv_do_first:
                        //查看故障处理
                        doJumpTroubleDetail(item.getId(), item.getIsPhoneSolve());
                        break;
                    case R.id.tv_do_second:
//                        if (!item.getAssigneeUserId().equals(WorkerApplication.get().getUserId())) {
//                            showToast("当前订单负责人可以操作");
//                            return;
//                        }
                        //评价客户
                        startActivity(new Intent(getActivity(), EvaluateClientActivity.class).putExtra("flag", 0)
                                .putExtra("ordernum", item.getOrderNum())
                                .putExtra("ownerId", item.getOwnerUserId())
                                .putExtra("orderId", item.getId())
                                .putExtra("avatar", item.getAssigneeUser().getAccountEntity().getAvatar()));
                        break;
                    default:
                        break;
                }
                break;

            case 6:
                CallUtils.call(getActivity(), item.getOwnerUser().getAccountEntity().getMobile());
                break;
            default:
                break;

        }
    }

    private void doJumpTroubleDetail(Long busRepairOrderId, Integer isPhoneSolve) {
        Bundle bundle = new Bundle();
        bundle.putLong("busRepairOrderId", busRepairOrderId);
        bundle.putBoolean("isVisible", false);
        if (isPhoneSolve == 0) {
            // 电话未解决
            JumpItent.jump(getActivity(), TroubleDetailActivity.class, bundle);
        } else {
            // 电话解决
            JumpItent.jump(getActivity(), PsTroubleDetailActivity.class, bundle);
        }
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
