package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.base.kit.V;
import com.eanfang.biz.model.QueryEntry;
import com.eanfang.biz.model.bean.RepairedOrderBean;
import com.eanfang.biz.model.entity.RepairOrderEntity;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.EvaluateClientActivity;
import net.eanfang.worker.ui.activity.worksapce.OrderDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.SignInActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.RepairCtrlActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.SolveModeActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.finishwork.FillRepairInfoActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.finishwork.PhoneSolveRepairInfoActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.seefaultdetail.PsTroubleDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.seefaultdetail.TroubleDetailActivity;
import net.eanfang.worker.ui.adapter.RepairedManageOrderAdapter;
import net.eanfang.worker.ui.widget.FillAppointmentInfoRebookView;

import cn.hutool.core.date.DateUtil;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;


/**
 * 工作台已报修
 */
public class OrderListFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    private static int page = 1;
    private String mTitle;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private RepairedManageOrderAdapter adapter;
    private String status = null;
    private int currentPosition;

    private Long mUseId = WorkerApplication.get().getUserId();

    public static OrderListFragment getInstance(String title) {
        OrderListFragment sf = new OrderListFragment();
        sf.mTitle = title;
        page = 1;
        return sf;

    }

    private void initAdapter() {
//        if (getActivity() == null) {
//            return;
//        }
//        if (!(getActivity() instanceof RepairCtrlActivity)) {
//            return;
//        }
//        if (((RepairCtrlActivity) getActivity()).getBean() == null) {
//            return;
//        }

        adapter = new RepairedManageOrderAdapter();
        adapter.bindToRecyclerView(mRecyclerView);
        adapter.setOnLoadMoreListener(this);


        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            RepairOrderEntity item = adapter.getData().get(position);
            switchCase(item, view);
            currentPosition = position;
        });

        mRecyclerView.addOnItemTouchListener(onItemClickListener);


//        if (mDataList.size() > 0) {
//            mRecyclerView.setAdapter(adapter);
//            findViewById(R.id.tv_no_datas).setVisibility(View.GONE);
//            adapter.notifyDataSetChanged();
//        } else {
//            findViewById(R.id.tv_no_datas).setVisibility(View.VISIBLE);
//        }
//
//        adapter.notifyDataSetChanged();
    }

    private void switchCase(RepairOrderEntity item, View view) {
        Intent intent;
        Log.i("POLpp", item + "");
        //获取：订单状态( 0:待支付，1:待回电，2:待上门，3:待完工，4:待确认，5:订单完成)
        switch (item.getStatus()) {
            case 0:
                switch (view.getId()) {
                    case R.id.tv_do_second:

                        //只有当前登陆人为订单负责人才可以操作
                        //联系客户
                        CallUtils.call(getActivity(), item.getOwnerUser().getAccountEntity().getMobile());
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
                        CallUtils.call(getActivity(), V.v(() -> item.getRepairContactPhone()));
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
                        CallUtils.call(getActivity(), V.v(() -> item.getOwnerUser().getAccountEntity().getMobile()));
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
                        intent.putExtra("workerUserId", item.getAssigneeUser().getUserId());
                        intent.putExtra("companyName", item.getOwnerOrg().getBelongCompany().getOrgName());
                        intent.putExtra("phoneSolve", item.getIsPhoneSolve());
                        intent.putExtra("companyUid", item.getAssigneeOrg().getCompanyId());
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


    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_workspace_order_list;
    }

    @Override
    protected void initData(Bundle arguments) {

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

    protected void getData() {

        QueryEntry queryEntry = new QueryEntry();

        if (!"全部".equals(getTitle())) {
            status = GetConstDataUtils.getRepairStatus().indexOf(getTitle()) + "";
            queryEntry.getEquals().put("status", status);
        }
        queryEntry.setSize(10);
        queryEntry.setPage(page);

        EanfangHttp.post(RepairApi.GET_REPAIR_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<RepairedOrderBean>(getActivity(), true, RepairedOrderBean.class)
//                                , (bean) -> {
//                            ((RepairCtrlActivity) getActivity()).setBean(bean);
//                            getActivity().runOnUiThread(this::onDataReceived);
//                        })
                         {
                             @Override
                             public void onSuccess(final RepairedOrderBean bean) {
                                 if (page == 1) {
                                     adapter.getData().clear();
                                     adapter.setNewData(bean.getList());
                                     refreshLayout.setRefreshing(false);
                                     adapter.loadMoreComplete();
                                     if (bean.getList().size() < 10) {
                                         adapter.loadMoreEnd();
                                     }

                                     if (bean.getList().size() > 0) {
                                         findViewById(R.id.tv_no_datas).setVisibility(View.GONE);
                                     } else {
                                         findViewById(R.id.tv_no_datas).setVisibility(View.VISIBLE);
                                     }


                                 } else {
                                     adapter.addData(bean.getList());
                                     adapter.loadMoreComplete();
                                     if (bean.getList().size() < 10) {
                                         adapter.loadMoreEnd();
                                     }
                                 }

                             }

                             @Override
                             public void onNoData(String message) {
                                 refreshLayout.setRefreshing(false);
                                 adapter.loadMoreEnd();//没有数据了
                                 if (adapter.getData().size() == 0) {
                                     findViewById(R.id.tv_no_datas).setVisibility(View.VISIBLE);
                                 } else {
                                     findViewById(R.id.tv_no_datas).setVisibility(View.GONE);
                                 }
                                 adapter.notifyDataSetChanged();

//                                 page--;
//                                 getActivity().runOnUiThread(() -> {
//                                     //如果是第一页 没有数据了 则清空 bean
//                                     if (page < 1) {
//                                         RepairedOrderBean bean = new RepairedOrderBean();
//                                         bean.setList(new ArrayList<>());
//                                         ((RepairCtrlActivity) getActivity()).setBean(bean);
//                                     } else {
//                                         showToast("已经到底了");
//                                     }
//                                     onDataReceived();
//                                 });
                             }


                             @Override
                             public void onCommitAgain() {
                                 refreshLayout.setRefreshing(false);
                             }

                             @Override
                             public void onError(String message) {
                                 //重新加载 页面

                             }
                         }
                );
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout = findViewById(R.id.swiprefresh);
        refreshLayout.setOnRefreshListener(this);

        initAdapter();
    }

    @Override
    protected void setListener() {

    }


    @Override
    protected void onLazyLoad() {
        //Log.e("zzw", "onLazyLoad");
        page = 1;
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (status != null) {
            int index = ((RepairCtrlActivity) getActivity()).tabLayout_2.getCurrentTab() + 1;
            if (status.equals(String.valueOf(index))) {
                //Log.e("zzw", "onResume == " + status);
                page = 1;
                getData();
            }
        }
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            if (!PermKit.get().getRepairDetailPerm()) {
                return;
            }
            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
            intent.putExtra("id", ((RepairOrderEntity) adapter.getData().get(position)).getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            //刷新已读未读
            ((RepairOrderEntity) adapter.getData().get(position)).setNewOrder(0);
            adapter.notifyItemChanged(position);
            intent.putExtra("orderTime", DateUtil.date(((RepairOrderEntity) adapter.getData().get(position)).getCreateTime()).toString());
            startActivity(intent);
        }
    };

    /**
     * 下拉刷新
     */
    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
//                page--;
//                if (page <= 0) {
//                    page = 1;
//                }
                //下拉永远第一页
                page = 1;
                getData();

                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
//                page++;
//                getData();
                break;
            default:
                break;
        }
    }

    /**
     * 上拉加载更多
     */

    @Override
    public void onLoadMoreRequested() {
        //上拉加载更多
        page++;
        getData();
    }

    @Override
    public void onRefresh() {
        dataOption(TOP_REFRESH);
    }

    public RepairedManageOrderAdapter getAdapter() {
        return adapter;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

}