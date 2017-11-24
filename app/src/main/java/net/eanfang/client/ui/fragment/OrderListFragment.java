package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.util.CallUtils;

import net.eanfang.client.R;
import net.eanfang.client.config.EanfangConst;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.activity.pay.PayActivity;
import net.eanfang.client.ui.activity.worksapce.EvaluateWorkerActivity;
import net.eanfang.client.ui.activity.worksapce.OrderDetailActivity;
import net.eanfang.client.ui.activity.worksapce.PsTroubleDetailActivity;
import net.eanfang.client.ui.activity.worksapce.RepairCtrlActivity;
import net.eanfang.client.ui.activity.worksapce.TroubleDetailActivity;
import net.eanfang.client.ui.adapter.RepairedManageOrderAdapter;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.interfaces.OnDataReceivedListener;
import net.eanfang.client.ui.model.RepairedOrderBean;
import net.eanfang.client.util.GetConstDataUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static net.eanfang.client.config.EanfangConst.BOTTOM_REFRESH;
import static net.eanfang.client.config.EanfangConst.TOP_REFRESH;


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
    private List<RepairedOrderBean.AllBean> mDataList;

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
        mDataList = ((RepairCtrlActivity) getActivity()).getBean().getAll();
        RepairedManageOrderAdapter adapter = new RepairedManageOrderAdapter(mDataList);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            RepairedOrderBean.AllBean item = mDataList.get(position);
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

    private void switchCase(RepairedOrderBean.AllBean item, View view) {
        switch (item.getStatus()) {
            case 0:
                switch (view.getId()) {
                    case R.id.tv_do_second:
                        startActivity(new Intent(getActivity(), PayActivity.class)
                                .putExtra("ordernum", item.getOrdernum())
                                .putExtra("doorfee", item.getDoorfee() + "")
                                .putExtra("orderType", "报修"));
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                switch (view.getId()) {
                    case R.id.tv_do_second:
                        CallUtils.call(getActivity(), item.getWorkerPhone());
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                switch (view.getId()) {
                    case R.id.tv_do_second:
                        CallUtils.call(getActivity(), item.getWorkerPhone());
                        break;
                    default:
                        break;
                }
                break;

            case 3:
                switch (view.getId()) {
                    case R.id.tv_do_second:
                        //客户端 待完工  联系技师
                        CallUtils.call(getActivity(), item.getWorkerPhone());
                        break;
                    default:
                        break;
                }
                break;
            //待确认
            case 4:
                switch (view.getId()) {
                    case R.id.tv_do_first:
                        CallUtils.call(getActivity(), item.getClientphone());
                        break;
                    case R.id.tv_do_second:
                        Intent intent;
                        if (EanfangConst.EANFANG_FALSE_STR.equals(item.getPhonesolve())) {
                            intent = new Intent(getActivity(), TroubleDetailActivity.class);
                        } else {
                            intent = new Intent(getActivity(), PsTroubleDetailActivity.class);
                        }
                        intent.putExtra("orderId", item.getId());
                        intent.putExtra("status", "待确认");
                        intent.putExtra("phoneSolve", item.getPhonesolve());
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                break;
            //待评价
            case 5:
                switch (view.getId()) {

                    case R.id.tv_do_first:
                        Intent intent;
                        if (EanfangConst.EANFANG_FALSE_STR.equals(item.getPhonesolve())) {
                            intent = new Intent(getActivity(), TroubleDetailActivity.class);
                        } else {
                            intent = new Intent(getActivity(), PsTroubleDetailActivity.class);
                        }
                        intent.putExtra("orderId", item.getId());
                        intent.putExtra("status", "待评价");
                        intent.putExtra("phoneSolve", item.getPhonesolve());
                        startActivity(intent);
                        break;
                    case R.id.tv_do_second:
                        startActivity(new Intent(getActivity(), EvaluateWorkerActivity.class)
                                .putExtra("flag", 0)
                                .putExtra("ordernum", item.getOrdernum())
                        );
                        break;
                    default:
                        break;
                }
                break;
            //已完成
            case 6:
                switch (view.getId()) {
                    case R.id.tv_do_second:
                        Intent intent;
                        if (EanfangConst.EANFANG_FALSE_STR.equals(item.getPhonesolve())) {
                            intent = new Intent(getActivity(), TroubleDetailActivity.class);
                        } else {
                            intent = new Intent(getActivity(), PsTroubleDetailActivity.class);
                        }
                        intent.putExtra("orderId", item.getId());
                        intent.putExtra("status", "已完成");
                        intent.putExtra("phoneSolve", item.getPhonesolve());
                        startActivity(intent);
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

        //2017年8月3日 lin
        JSONObject jsonObject = new JSONObject();
        try {
            if (!getTitle().equals("全部")) {
                String status = GetConstDataUtils.getRepairStatusByStr(getTitle());
                jsonObject.put("status", status);
            }
            if (!jsonObject.has("status")) {
                jsonObject.put("status", "");
            }
            jsonObject.put("page", page);
            jsonObject.put("rows", 10);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EanfangHttp.post(ApiService.REPAIR_LIST)
                .params("json", jsonObject.toString())
                .execute(new EanfangCallback<RepairedOrderBean>(((RepairCtrlActivity) getActivity()), true) {
                    @Override
                    public void onSuccess(final RepairedOrderBean bean) {
                        ((RepairCtrlActivity) getActivity()).setBean(bean);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onDataReceived();
                            }
                        });
                    }

                    @Override
                    public void onNoData(String message) {
                        page--;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //如果是第一页 没有数据了 则清空 bean
                                if (page < 1) {
                                    RepairedOrderBean bean = new RepairedOrderBean();
                                    bean.setAll(new ArrayList<RepairedOrderBean.AllBean>());
                                    ((RepairCtrlActivity) getActivity()).setBean(bean);
                                } else {
                                    showToast("已经到底了");
                                }
                                onDataReceived();
                            }
                        });
                    }

                    @Override
                    public void onError(String message) {
                        //重新加载 页面

                    }
                });

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
        //getData();

    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
            intent.putExtra("id", mDataList.get(position).getId());
            intent.putExtra("ordernum", mDataList.get(position).getOrdernum());
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
        }
    }
}