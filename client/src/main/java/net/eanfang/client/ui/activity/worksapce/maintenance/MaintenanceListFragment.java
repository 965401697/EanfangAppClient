package net.eanfang.client.ui.activity.worksapce.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.device.MaintenanceOrderListBean;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.V;
import com.yaf.base.entity.ShopMaintenanceOrderEntity;


import net.eanfang.client.R;
import net.eanfang.client.ui.fragment.TemplateItemListFragment;

import java.util.ArrayList;

/**
 * Created by O u r on 2018/7/16.
 */

public class MaintenanceListFragment extends TemplateItemListFragment {

    private int mStatus;
    private String mTitle;
    private MaintenanceListAdapter mAdapter;
    private int currentPosition;


    public static MaintenanceListFragment getInstance(int status, String title) {
        MaintenanceListFragment sf = new MaintenanceListFragment();
        sf.mStatus = status;
        sf.mTitle = title;
        return sf;

    }

    @Override
    protected void initAdapter() {
        mAdapter = new MaintenanceListAdapter(R.layout.item_maintenance_list);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(getActivity(), MaintenanceDetailActivity.class);
                intent.putExtra("id", mAdapter.getData().get(position).getId());
                startActivity(intent);

            }
        });


        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ShopMaintenanceOrderEntity shopMaintenanceOrderEntity = (ShopMaintenanceOrderEntity) adapter.getData().get(position);
                currentPosition = position;
                switchCase(shopMaintenanceOrderEntity, view);
            }
        });
    }

    @Override
    protected void getData() {

        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(10);
        queryEntry.setPage(mPage);
        if (mStatus == 1) {
            queryEntry.getEquals().put("assigneeUserId", String.valueOf(EanfangApplication.getApplication().getUserId()));
        } else if (mStatus == 2) {
            queryEntry.getEquals().put("assigneeOrgCode", EanfangApplication.get().getOrgCode());
        } else if (mStatus == 3) {
            queryEntry.getEquals().put("assigneeCompanyId", String.valueOf(EanfangApplication.get().getCompanyId()));
        }

        int status = GetConstDataUtils.getMaintainStatusList().indexOf(mTitle);
        queryEntry.getEquals().put("status", String.valueOf(status));
//        queryEntry.getEquals().put("status", String.valueOf(status));

        EanfangHttp.post(NewApiService.MAINTENANCE_GET_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<MaintenanceOrderListBean>(getActivity(), true, MaintenanceOrderListBean.class) {
                    @Override
                    public void onSuccess(MaintenanceOrderListBean bean) {

                        if (mPage == 1) {
                            mAdapter.getData().clear();
                            mAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                mAdapter.loadMoreEnd();
                            }

                            if (bean.getList().size() > 0) {
                                mTvNoData.setVisibility(View.GONE);
                            } else {
                                mTvNoData.setVisibility(View.VISIBLE);
                            }


                        } else {
                            mAdapter.addData(bean.getList());
                            mAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                mAdapter.loadMoreEnd();
                            }
                        }

                    }

                    @Override
                    public void onNoData(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.loadMoreEnd();//没有数据了
                        if (mAdapter.getData().size() == 0) {
                            mTvNoData.setVisibility(View.VISIBLE);
                        } else {
                            mTvNoData.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onCommitAgain() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private void switchCase(ShopMaintenanceOrderEntity item, View view) {
        Intent intent;
        //获取：1:待预约，2:待上门，3:维修中，4:待验收，5:订单完成)
        switch (item.getStatus()) {
            case 1:
//                        if (!item.getAssigneeUserId().equals(EanfangApplication.get().getUserId())) {
//                            showToast("当前订单负责人可以操作");
//                            return;
//                        }
                // 解决方式
                Bundle bundle = new Bundle();
                bundle.putLong("orderId", item.getId());
                bundle.putInt("type", 1);
                JumpItent.jump(getActivity(), MaintenanceAppointTimeActivity.class, bundle);
                //给客户联系人打电话
                CallUtils.call(getActivity(), V.v(() -> item.getOwnerUserEntity().getAccountEntity().getMobile()));
                break;
            case 2:
                switch (view.getId()) {

                    case R.id.tv_do_first:
                        //只有当前登陆人为订单负责人才可以操作
//                        if (!item.getAssigneeUserId().equals(EanfangApplication.get().getUserId())) {
//                            showToast("当前订单负责人可以操作");
//                            return;
//                        }
                        Bundle b = new Bundle();
                        b.putLong("orderId", item.getId());
                        JumpItent.jump(getActivity(), MaintenanceAppointTimeActivity.class, b);
                        break;
                    case R.id.tv_do_second:
                        //只有当前登陆人为订单负责人才可以操作
//                        if (!item.getAssigneeUserId().equals(EanfangApplication.get().getUserId())) {
//                            showToast("当前订单负责人可以操作");
//                            return;
//                        }
//                        intent = new Intent(getActivity(), SignInActivity.class);
//                        intent.putExtra("orderId", item.getId());
//                        intent.putExtra("latitude", item.getLatitude());
//                        intent.putExtra("longitude", item.getLongitude());
//                        intent.putExtra("isFromType", 1);
//                        startActivity(intent);
                        break;
                }
                break;
            case 3:// 待上门 签到
                switch (view.getId()) {
                    case R.id.tv_do_first:
                        //给客户联系人打电话
                        CallUtils.call(getActivity(), V.v(() -> item.getOwnerUserEntity().getAccountEntity().getMobile()));
                        break;
                    case R.id.tv_do_second:
//                        if (!item.getAssigneeUserId().equals(EanfangApplication.get().getUserId())) {
//                            showToast("当前订单负责人可以操作");
//                            return;
//                        }
                        //只有当前登陆人为订单负责人才可以操作
                        intent = new Intent(getActivity(), MaintenanceHandleActivity.class);
                        intent.putExtra("orderId", item.getId());
                        intent.putExtra("list", (ArrayList) item.getExamDeviceEntityList());
//                        intent.putExtra("companyName", item.getOwnerOrg().getBelongCompany().getOrgName());
//                        intent.putExtra("companyUid", item.getAssigneeOrg().getCompanyId());
//                        intent.putExtra("clientCompanyUid", item.getOwnerCompanyId());
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                break;
            case 4:
            case 5:
                switch (view.getId()) {

                    case R.id.tv_do_second:
//                        if (!item.getAssigneeUserId().equals(EanfangApplication.get().getUserId())) {
//                            showToast("当前订单负责人可以操作");
//                            return;
//                        }
                        //只有当前登陆人为订单负责人才可以操作
                        intent = new Intent(getActivity(), MaintenanceHandleShowActivity.class);
                        intent.putExtra("orderId", item.getId());
//                        intent.putExtra("companyName", item.getOwnerOrg().getBelongCompany().getOrgName());
//                        intent.putExtra("companyUid", item.getAssigneeOrg().getCompanyId());
//                        intent.putExtra("clientCompanyUid", item.getOwnerCompanyId());
                        startActivity(intent);

                        break;
                    default:
                        break;
                }
                //待确认
                break;


            default:
                break;

        }
    }

    public MaintenanceListAdapter getmAdapter() {
        return mAdapter;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}
