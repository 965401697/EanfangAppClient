package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
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

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.EvaluateClientActivity;
import net.eanfang.worker.ui.activity.worksapce.FillRepairInfoActivity;
import net.eanfang.worker.ui.activity.worksapce.OrderDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.PhoneSolveRepairInfoActivity;
import net.eanfang.worker.ui.activity.worksapce.RepairCtrlActivity;
import net.eanfang.worker.ui.activity.worksapce.SignInActivity;
import net.eanfang.worker.ui.activity.worksapce.TroubleDetalilListActivity;
import net.eanfang.worker.ui.adapter.RepairedManageOrderAdapter;
import net.eanfang.worker.ui.interfaces.OnDataReceivedListener;
import net.eanfang.worker.ui.widget.FillAppointmentInfoRebookView;
import net.eanfang.worker.ui.widget.FillAppointmentInfoView;

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
        Intent intent;
        switch (item.getStatus()) {
            case 0:
                switch (view.getId()) {
                    case R.id.tv_do_second:
                        //只有当前登陆人为订单负责人才可以操作
                        CallUtils.call(getActivity(), item.getOwnerUser().getAccountEntity().getMobile());

                        break;
                    default:
                        break;
                }
                break;
            case 1:
                switch (view.getId()) {

                    case R.id.tv_do_second:
                        new FillAppointmentInfoView(getActivity(), true, item.getId()).show();
                        //给客户联系人打电话
                        CallUtils.call(getActivity(), item.getOwnerUser().getAccountEntity().getMobile());

                        break;
                    default:
                        break;
                }
                break;
            case 2:
                switch (view.getId()) {
                    case R.id.tv_do_first:
                        //只有当前登陆人为订单负责人才可以操作
                        new FillAppointmentInfoRebookView(getActivity(), true, item.getId(), true).show();
                        break;
                    case R.id.tv_do_second:
                        //只有当前登陆人为订单负责人才可以操作
                        intent = new Intent(getActivity(), SignInActivity.class);
                        intent.putExtra("orderId", item.getId());
                        intent.putExtra("latitude", item.getLatitude());
                        intent.putExtra("longitude", item.getLongitude());
                        startActivity(intent);

                        break;
                    default:
                        break;
                }
                break;

            case 3:
                switch (view.getId()) {
                    case R.id.tv_do_second:

                        //只有当前登陆人为订单负责人才可以操作
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
                        startActivity(intent);

                        break;
                    default:
                        break;
                }
                break;
            //待确认
            case 4:
                switch (view.getId()) {
                    case R.id.tv_do_first:
                        CallUtils.call(getActivity(), item.getAssigneeUser().getAccountEntity().getMobile());

                        break;
                    case R.id.tv_do_second:
                        new TroubleDetalilListActivity(getActivity(), true, item.getId(), item.getIsPhoneSolve(), "待确认").show();
                        break;
                    default:
                        break;
                }
                break;
            //待评价
            case 5:
                switch (view.getId()) {

                    case R.id.tv_do_first:
                        new TroubleDetalilListActivity(getActivity(), true, item.getId(), item.getIsPhoneSolve(), "完成").show();
//
                        break;
                    case R.id.tv_do_second:
                        startActivity(new Intent(getActivity(), EvaluateClientActivity.class).putExtra("flag", 0)
                                .putExtra("ordernum", item.getOrderNum())
                                .putExtra("ownerId", item.getOwnerUserId())
                                .putExtra("orderId", item.getId()));
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
        String status = null;
        if (!"全部".equals(getTitle())) {
            status = GetConstDataUtils.getRepairStatus().indexOf(getTitle()) + "";
            queryEntry.getEquals().put("status", status);
        }
        queryEntry.setSize(10);
        queryEntry.setPage(1);

        EanfangHttp.post(RepairApi.GET_REPAIR_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<RepairedOrderBean>(getActivity(), true, RepairedOrderBean.class, (bean) -> {
                            ((RepairCtrlActivity) getActivity()).setBean(bean);
                            getActivity().runOnUiThread(this::onDataReceived);
                        })
//                {
//                    @Override
//                    public void onSuccess(final RepairedOrderBean bean) {
//                        ((RepairCtrlActivity) getActivity()).setBean(bean);
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                onDataReceived();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onNoData(String message) {
//                        page--;
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                //如果是第一页 没有数据了 则清空 bean
//                                if (page < 1) {
//                                    RepairedOrderBean bean = new RepairedOrderBean();
//                                    bean.setAll(new ArrayList<RepairedOrderBean.AllBean>());
//                                    ((RepairCtrlActivity) getActivity()).setBean(bean);
//                                } else {
//                                    showToast("已经到底了");
//                                }
//                                onDataReceived();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                        //重新加载 页面
//
//                    }
//                }
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
    }

    @Override
    protected void setListener() {

    }


    @Override
    protected void onLazyLoad() {

    }


    @Override
    public void onResume() {
        super.onResume();
        //getData();

    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
            intent.putExtra("id", mDataList.get(position).getId());
            startActivity(intent);
        }
    };


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
                page--;
                if (page <= 0) {
                    page = 1;
                }
                getData();
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                page++;
                getData();
                break;
            default:
                break;
        }
    }
}