package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.RepairedOrderBean;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.EvaluateWorkerActivity;
import net.eanfang.client.ui.activity.worksapce.OrderDetailActivity;
import net.eanfang.client.ui.activity.worksapce.RepairCtrlActivity;
import net.eanfang.client.ui.activity.worksapce.TroubleDetalilListActivity;
import net.eanfang.client.ui.adapter.RepairedManageOrderAdapter;
import net.eanfang.client.ui.interfaces.OnDataReceivedListener;

import java.util.ArrayList;
import java.util.List;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;


/**
 * 工作台已报修
 */
public class OrderListFragment extends BaseFragment implements
        OnDataReceivedListener, SwipyRefreshLayout.OnRefreshListener {


    private static int page = 1;
    private String mTitle;
    private View v;
    private String Msg;
    private SwipyRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private List<RepairOrderEntity> mDataList;
    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
            intent.putExtra(Constant.ID, mDataList.get(position).getId());
            startActivity(intent);
        }
    };

    public static OrderListFragment getInstance(String title) {
        OrderListFragment sf = new OrderListFragment();
        sf.mTitle = title;
        page = 1;
        return sf;

    }

    private void initAdapter() {
        if (getActivity() == null) {
            return;
        }
        if (!(getActivity() instanceof RepairCtrlActivity)) {
            return;
        }
        if (((RepairCtrlActivity) getActivity()).getBean() == null) {
            return;
        }
        mDataList = ((RepairCtrlActivity) getActivity()).getBean().getList();
        RepairedManageOrderAdapter adapter = new RepairedManageOrderAdapter(mDataList);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            RepairOrderEntity item = mDataList.get(position);
            switchCase(item, view);
        });
        mRecyclerView.addOnItemTouchListener(onItemClickListener);
        if (mDataList.size() > 0) {
            mRecyclerView.setAdapter(adapter);
            findViewById(R.id.tv_no_datas).setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            findViewById(R.id.tv_no_datas).setVisibility(View.VISIBLE);
        }

        adapter.notifyDataSetChanged();
    }

    private void switchCase(RepairOrderEntity item, View view) {
        // TODO: 2018/1/5 待处理
        switch (item.getStatus()) {
            case 0:
                //待付款
                switch (view.getId()) {
                    case R.id.tv_do_second:
                        if (!item.getOwnerUserId().equals(EanfangApplication.get().getUserId())) {
                            showToast("当前订单负责人可以操作");
                            return;
                        }
//                        startActivity(new Intent(getActivity(), PayActivity.class)
//                                .putExtra("ordernum", item.getOrderNum())
//                                .putExtra("orderType", "报修"));
                        break;
                    default:
                        break;
                }
                break;
            //待回电
            case 1:
                switch (view.getId()) {
                    case R.id.tv_do_second:
                        CallUtils.call(getActivity(), item.getAssigneeUser().getAccountEntity().getMobile());
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
                        if (!item.getOwnerUserId().equals(EanfangApplication.get().getUserId())) {
                            showToast("当前订单负责人可以操作");
                            return;
                        }
                        new TroubleDetalilListActivity(getActivity(), true, item.getId(), item.getIsPhoneSolve(), "待确认").show();
                        break;
                    default:
                        break;
                }
                break;
            //完成
            case 5:
                switch (view.getId()) {

                    case R.id.tv_do_first:
                        if (!item.getOwnerUserId().equals(EanfangApplication.get().getUserId())) {
                            showToast("当前订单负责人可以操作");
                            return;
                        }
                        new TroubleDetalilListActivity(getActivity(), true, item.getId(), item.getIsPhoneSolve(), "完成").show();

                        break;
                    case R.id.tv_do_second:
                        if (!item.getOwnerUserId().equals(EanfangApplication.get().getUserId())) {
                            showToast("当前订单负责人可以操作");
                            return;
                        }
                        startActivity(new Intent(getActivity(), EvaluateWorkerActivity.class)
                                .putExtra("flag", 0)
                                .putExtra("ordernum", item.getOrderNum())
                                .putExtra("workerUid", item.getAssigneeUserId())
                                .putExtra("orderId", item.getId())
                        );
                        break;
                    default:
                        break;
                }
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

    protected void getData() {
        QueryEntry queryEntry = new QueryEntry();
        if (!Constant.ALL.equals(getTitle())) {
            String status = constDataUtils.getRepairStatus().indexOf(getTitle()) + "";
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
                        ((RepairCtrlActivity) getActivity()).setBean(bean);
                        getActivity().runOnUiThread(() -> onDataReceived());
                    }

                    @Override
                    public void onNoData(String message) {
                        page--;
                        getActivity().runOnUiThread(() -> {
                            //如果是第一页 没有数据了 则清空 bean
                            if (page < 1) {
                                RepairedOrderBean bean = new RepairedOrderBean();
                                bean.setList(new ArrayList<>());
                                ((RepairCtrlActivity) getActivity()).setBean(bean);
                            } else {
                                showToast("已经到底了");
                            }
                            onDataReceived();
                        });
                    }

                    @Override
                    public void onError(String message) {
                        //重新加载 页面
                    }
                });

        initAdapter();

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
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onLazyLoad() {
//        new CommonRequestProtocol("/workerorderlist", 100004, this).execute();
//        ((RepairedManageActivity) getActivity()).initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();

    }

    @Override
    public void onDataReceived() {
        initView();
        initAdapter();
        refreshLayout.setRefreshing(false);
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh(int index) {
        dataOption(TOP_REFRESH);

    }

    @Override
    public void onLoad(int index) {
        dataOption(BOTTOM_REFRESH);
    }

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
//                mDataList.clear();
                page--;
                if (page <= 0) {
                    page = 1;
                }
                getData();
                // ((RepairedManageActivity) getActivity()).initData(page);
                //initAdapter();
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                page++;
                getData();
                // ((RepairedManageActivity) getActivity()).initData(page);
                //initAdapter();
                break;
            default:
                break;
        }
    }
}