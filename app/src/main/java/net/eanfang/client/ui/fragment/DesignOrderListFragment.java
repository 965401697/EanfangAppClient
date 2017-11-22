package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.okgo.model.HttpParams;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.DesignOrderAdapter;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.interfaces.OnDataReceivedListener;
import net.eanfang.client.ui.model.DesignOrderListBean;
import net.eanfang.client.ui.activity.worksapce.DesignOrderListActivity;
import net.eanfang.client.ui.widget.LookDesignOrderInfoView;

import java.util.ArrayList;
import java.util.List;

import static net.eanfang.client.config.EanfangConst.BOTTOM_REFRESH;
import static net.eanfang.client.config.EanfangConst.TOP_REFRESH;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  20:01
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class DesignOrderListFragment extends BaseFragment implements OnDataReceivedListener {

    TextView tvNoDatas;

    RecyclerView rvList;

    SwipyRefreshLayout swiprefresh;

    private List<DesignOrderListBean.AllBean> mDataList;
    private String mTitle;
    private String mType;
    private DesignOrderAdapter mAdapter;

    private static int page = 1;

    public String getmTitle() {
        return mTitle;
    }

    public static DesignOrderListFragment getInstance(String title, String type) {
        DesignOrderListFragment sf = new DesignOrderListFragment();
        sf.mTitle = title;
        page = 1;
        sf.mType = type;
        return sf;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_detail_order_list;
    }

    @Override
    protected void initView() {
        tvNoDatas = findViewById(R.id.tv_no_datas);
        rvList = findViewById(R.id.rv_list);
        swiprefresh = findViewById(R.id.swiprefresh);
    }

    @Override
    protected void initData(Bundle arguments) {
    }


    @Override
    protected void setListener() {
        swiprefresh.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(int index) {
                dataOption(TOP_REFRESH);
            }

            @Override
            public void onLoad(int index) {
                dataOption(BOTTOM_REFRESH);
            }
        });

        rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new LookDesignOrderInfoView(getActivity(), true, mDataList.get(position).getId()).show();
            }
        });

    }

    private void initAdapter() {
        if (getActivity() == null) {
            return;
        }
        if (!(getActivity() instanceof DesignOrderListActivity)) {
            return;
        }
        if (((DesignOrderListActivity) getActivity()).getDesignOrderListBean() == null) {
            return;
        }
        mDataList = ((DesignOrderListActivity) getActivity()).getDesignOrderListBean().getAll();
        mAdapter = new DesignOrderAdapter(mDataList);

        if (mDataList.size() > 0) {
            rvList.setAdapter(mAdapter);
            tvNoDatas.setVisibility(View.GONE);
        } else {
            tvNoDatas.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
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
        }
    }


    @Override
    public void onDataReceived() {
        initView();
        initAdapter();
        swiprefresh.setRefreshing(false);
    }

    /**
     * 获取工作任务列表
     */
    private void getData() {
        String status = "";
        if (!mTitle.equals("全部")) {
            status = ((DesignOrderListActivity) getActivity()).allmTitles.indexOf(this.getmTitle()) + "";
        }

        HttpParams params = new HttpParams();
        params.put("page", page);
        params.put("rows", 10);
        params.put("type", mType);
        params.put("status", status);

        doHttp(params);
    }

    private void doHttp(HttpParams params) {
        EanfangHttp.get(ApiService.GET_DESIGN_ORDER_LIST)
                .tag(this)
                .params(params)
                .execute(new EanfangCallback<DesignOrderListBean>(getActivity(), true) {
                    @Override
                    public void onSuccess(DesignOrderListBean bean) {
                        ((DesignOrderListActivity) getActivity()).setDesignOrderListBean(bean);
                        getActivity().runOnUiThread(() -> {
                            onDataReceived();
                        });
                    }

                    @Override
                    public void onError(String message) {
                    }

                    @Override
                    public void onNoData(String message) {
                        swiprefresh.setRefreshing(false);
                        page--;
                        getActivity().runOnUiThread(() -> {
                            //如果是第一页 没有数据了 则清空 bean
                            if (page < 1) {
                                DesignOrderListBean bean = new DesignOrderListBean();
                                bean.setAll(new ArrayList<DesignOrderListBean.AllBean>());
                                ((DesignOrderListActivity) getActivity()).setDesignOrderListBean(bean);
                            } else {
                                showToast("已经到底了");
                            }
                            onDataReceived();
                        });
                    }
                });
    }
}

