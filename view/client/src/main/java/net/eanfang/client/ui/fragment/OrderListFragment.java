package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.RepairedOrderBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.pay.NewPayActivity;
import net.eanfang.client.ui.activity.worksapce.EvaluateWorkerActivity;
import net.eanfang.client.ui.activity.worksapce.OrderDetailActivity;
import net.eanfang.client.ui.activity.worksapce.TroubleDetalilListActivity;
import net.eanfang.client.ui.activity.worksapce.repair.RepairCtrlActivity;
import net.eanfang.client.ui.adapter.RepairedManageOrderAdapter;
import net.eanfang.client.ui.interfaces.OnDataReceivedListener;

import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;


/**
 * 工作台已报修
 */
public class OrderListFragment extends BaseFragment implements
        OnDataReceivedListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    private static int page = 1;
    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            if (PermKit.get().getRepairDetailPerm()) {
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra(Constant.ID, ((RepairOrderEntity) adapter.getData().get(position)).getId());
                intent.putExtra("title", mTitle);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                //刷新已读未读
                ((RepairOrderEntity) adapter.getData().get(position)).setNewOrder(0);
                adapter.notifyItemChanged(position);
                intent.putExtra("orderTime", GetDateUtils.dateToDateTimeString(((RepairOrderEntity) adapter.getData().get(position)).getCreateTime()));
                startActivity(intent);
            }
        }
    };
    private String mTitle;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private RepairedManageOrderAdapter adapter;
    private String status;

    private Long mUseId = ClientApplication.get().getUserId();

    public static OrderListFragment getInstance(String title) {
        OrderListFragment sf = new OrderListFragment();
        sf.mTitle = title;
        page = 1;
        return sf;

    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_workspace_order_list;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout = findViewById(R.id.swiprefresh);
        refreshLayout.setOnRefreshListener(this);

        initAdapter();
    }

    private void initAdapter() {
        adapter = new RepairedManageOrderAdapter();
        adapter.bindToRecyclerView(mRecyclerView);
        adapter.setOnLoadMoreListener(this);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            RepairOrderEntity item = adapter.getData().get(position);
            switchCase(item, view);
        });
        mRecyclerView.addOnItemTouchListener(onItemClickListener);
    }

    private void switchCase(RepairOrderEntity item, View view) {
        // TODO: 2018/1/5 待处理
        switch (item.getStatus()) {
            case 0:
                //待付款
                switch (view.getId()) {
                    case R.id.tv_do_second:
                        payment(item);
                        break;
                    default:
                        break;
                }
                break;
            //待回电
            case 1:
                switch (view.getId()) {
                    case R.id.tv_do_second:
                        if (item.getAssigneeUser() != null) {
                            CallUtils.call(getActivity(), item.getAssigneeUser().getAccountEntity().getMobile());
                        } else {
                            showToast("请等待分配技师");
                        }

                        break;
                    default:
                        break;
                }
                break;
            //待上门
            case 2:
                switch (view.getId()) {
                    case R.id.tv_do_second:
                        CallUtils.call(getActivity(), item.getAssigneeUser().getAccountEntity().getMobile());
                        break;
                    default:
                        break;
                }
                break;
            //客户端 待完工  联系技师
            case 3:
                switch (view.getId()) {
                    case R.id.tv_do_second:
                        CallUtils.call(getActivity(), item.getAssigneeUser().getAccountEntity().getMobile());
                        break;
                    default:
                        break;
                }
                break;
            //待确认
            case 4:
                switch (view.getId()) {
                    case R.id.tv_do_first:
                        CallUtils.call(getActivity(), item.getOwnerUser().getAccountEntity().getMobile());
                        break;
                    case R.id.tv_do_second:
                        new TroubleDetalilListActivity(getActivity(), true, item.getId(), item.getIsPhoneSolve(), "待确认", false).show();
                        break;
                    case R.id.tv_finish:
                        new TroubleDetalilListActivity(getActivity(), true, item.getId(), item.getIsPhoneSolve(), "完成", false).show();
                        break;
                    default:
                        break;
                }
                break;
            //完成
            case 5:
                switch (view.getId()) {

                    case R.id.tv_do_first:
                        //  完工报告
//                        if (!item.getOwnerUserId().equals(ClientApplication.get().getUserId())) {
//                            showToast("当前订单负责人可以操作");
//                            return;
//                        }
                        //if (doCompare(item.getOwnerUserId(), mUseId)) {
                        new TroubleDetalilListActivity(getActivity(), true, item.getId(), item.getIsPhoneSolve(), "完成", false).show();
                        //}
                        break;
                    case R.id.tv_do_second:
                        // 评价技师
                        if (!item.getOwnerUserId().equals(ClientApplication.get().getUserId())) {
                            showToast("当前订单负责人可以操作");
                            return;
                        }
                        startActivity(new Intent(getActivity(), EvaluateWorkerActivity.class)
                                .putExtra("ordernum", item.getOrderNum())
                                .putExtra("workerUid", item.getAssigneeUserId())
                                .putExtra("orderId", item.getId())
                                .putExtra("avatar", item.getAssigneeUser().getAccountEntity().getAvatar()));
                        break;
                    default:
                        break;
                }
                break;

            case 6:
                CallUtils.call(Objects.requireNonNull(getActivity()), item.getOwnerUser().getAccountEntity().getMobile());
                break;
            default:
                break;

        }
    }

    /**
     * 支付
     *
     * @param orderEntity
     */
    private void payment(RepairOrderEntity orderEntity) {

//        PayLogEntity payLogEntity = new PayLogEntity();
//        payLogEntity.setOrderId(orderEntity.getId());
//        payLogEntity.setOrderNum(orderEntity.getOrderNum());
//        payLogEntity.setOrderType(Constant.OrderType.REPAIR.ordinal());
//        payLogEntity.setAssigneeUserId(orderEntity.getOwnerUserId());
//        payLogEntity.setAssigneeOrgCode(orderEntity.getOwnerOrgCode());
//        payLogEntity.setAssigneeTopCompanyId(orderEntity.getOwnerTopCompanyId());
//
//        payLogEntity.setOriginPrice(orderEntity.getPayLogEntity().getOriginPrice());// 原始价格
//        payLogEntity.setPayPrice(orderEntity.getPayLogEntity().getPayPrice());//实际支付价格
//        优惠价格
//        payLogEntity.setReducedPrice(0);


        Intent intent = new Intent(getActivity(), NewPayActivity.class);
        intent.putExtra("payLogEntity", orderEntity.getPayLogEntity());
        startActivity(intent);
    }

    @Override
    protected void setListener() {

    }


    protected void getData() {
        QueryEntry queryEntry = new QueryEntry();
        if (!"全部".equals(getTitle())) {
            status = GetConstDataUtils.getRepairStatus().indexOf(getTitle()) + "";
            queryEntry.getEquals().put(Constant.STATUS, status);
        }
        queryEntry.setSize(10);
        queryEntry.setPage(page);

        EanfangHttp.post(RepairApi.GET_REPAIR_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<RepairedOrderBean>(getActivity(), true, RepairedOrderBean.class)
//                                , (bean) -> {
//                            ((RepairCtrlActivity) getActivity()).setBean(bean);
//                            getActivity().runOnUiThread(this::onDataReceived);
//    })
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


//                        ((RepairCtrlActivity) getActivity()).setBean(bean);
//                        getActivity().runOnUiThread(() -> onDataReceived());
                    }

                    @Override
                    public void onCommitAgain() {
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(String message) {
                        //重新加载 页面
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

                    }
                });
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    protected void onLazyLoad() {
//        new CommonRequestProtocol("/workerorderlist", 100004, this).execute();
//        ((RepairedManageActivity) getActivity()).initData();
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (status != null) {
            int index = ((RepairCtrlActivity) getActivity()).tabLayout_2.getCurrentTab();
            if (status.equals(String.valueOf(index))) {
                page = 1;
                getData();
            }
        }

    }

    @Override
    public void onDataReceived() {
//        initView();
//        initAdapter();
//        refreshLayout.setRefreshing(false);
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

    /**
     * 刷新
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
}