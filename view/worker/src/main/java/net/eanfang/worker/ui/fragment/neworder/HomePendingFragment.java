package net.eanfang.worker.ui.fragment.neworder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eanfang.base.BaseFragment;
import com.eanfang.base.kit.V;
import com.eanfang.biz.model.entity.OrderBean;
import com.eanfang.config.Constant;
import com.eanfang.util.CallUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.FragmentPendingBinding;
import net.eanfang.worker.ui.activity.worksapce.EvaluateClientActivity;
import net.eanfang.worker.ui.activity.worksapce.OrderDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.SignInActivity;
import net.eanfang.worker.ui.activity.worksapce.design.DesignOrderDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.maintenance.MaintenanceDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.RepairCtrlActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.SolveModeActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.finishwork.FillRepairInfoActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.finishwork.PhoneSolveRepairInfoActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.seefaultdetail.PsTroubleDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.seefaultdetail.TroubleDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.tender.TenderFindDetailActivity;
import net.eanfang.worker.ui.adapter.neworder.HomeOrderAdapter;
import net.eanfang.worker.ui.widget.FillAppointmentInfoRebookView;
import net.eanfang.worker.ui.widget.InstallCtrlItemView;
import net.eanfang.worker.viewmodle.neworder.HomeOrderViewModle;

import java.util.List;

import cn.hutool.core.date.DateUtil;
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
        homeOrderAdapter = new HomeOrderAdapter(false);
        fragmentPendingBinding.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentPendingBinding.swipreFresh.setOnRefreshListener(this);
        homeOrderAdapter.bindToRecyclerView(fragmentPendingBinding.rvList);

        homeOrderAdapter.setOnItemClickListener(((adapter, view, position) -> {
            if (homeOrderAdapter.getData().get(position).getType() == Constant.OrderType.REPAIR.ordinal()) {
                if (!PermKit.get().getRepairDetailPerm()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra("id", ((OrderBean) adapter.getData().get(position)).getId());
                intent.putExtra("staus", ((OrderBean) adapter.getData().get(position)).getStatus());
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("orderTime", DateUtil.date(((OrderBean) adapter.getData().get(position)).getCreateTime()).toString());
                startActivity(intent);
            } else if (homeOrderAdapter.getData().get(position).getType() == Constant.OrderType.INSTALL.ordinal()) {
                if (!PermKit.get().getInstallDetailPrem()) {
                    return;
                }
                new InstallCtrlItemView(getActivity(), true, ((OrderBean) adapter.getData().get(position)).getId()).show();
            } else if (homeOrderAdapter.getData().get(position).getType() == Constant.OrderType.DESIGN.ordinal()) {
                if (!PermKit.get().getDesignDetailPrem()) {
                    return;
                }
                startActivity(new Intent(getActivity(), DesignOrderDetailActivity.class).putExtra("id", String.valueOf(((OrderBean) adapter.getData().get(position)).getId())));

            } else if (homeOrderAdapter.getData().get(position).getType() == Constant.OrderType.MAINTAIN.ordinal()) {
                if (!PermKit.get().getMaintenanceDetailPrem()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), MaintenanceDetailActivity.class);
                intent.putExtra("id", homeOrderAdapter.getData().get(position).getId());
                startActivity(intent);
            } else if (homeOrderAdapter.getData().get(position).getType() == Constant.OrderType.PUBLISH.ordinal()) {
                Bundle bundle = new Bundle();
                bundle.putLong("tendFindId", homeOrderAdapter.getData().get(position).getId());
                JumpItent.jump(getActivity(), TenderFindDetailActivity.class, bundle);
            }

        }));
        homeOrderAdapter.setOnItemChildClickListener(((adapter, view, position) ->{
            OrderBean item = homeOrderAdapter.getData().get(position);
            switchCase(item, view);
        } ));

        return fragmentPendingBinding.getRoot();
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
                        CallUtils.call(getActivity(), V.v(() -> item.getAssigneeUserPhone()));
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
                                .putExtra("ordernum", item.getNum())
                                .putExtra("ownerId", item.getOwnerUserId())
                                .putExtra("orderId", item.getId()));
//                                .putExtra("avatar", item.getAssigneeUser().getAccountEntity().getAvatar()));
                        break;
                    default:
                        break;
                }
                break;

            case 6:
                CallUtils.call(getActivity(), item.getContactPhone());
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
        mHomeOrderViewModle.doGetProgressData(0);
    }
}
