package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.DesignOrderListBean;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.DesignOrderListActivity;
import net.eanfang.client.ui.adapter.DesignOrderAdapter;
import net.eanfang.client.ui.interfaces.OnDataReceivedListener;
import net.eanfang.client.ui.widget.LookDesignOrderInfoView;

import java.util.List;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  20:01
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class DesignOrderListFragment extends BaseFragment implements OnDataReceivedListener {

    private static int page = 1;
    TextView tvNoDatas;
    RecyclerView rvList;
    SwipyRefreshLayout swiprefresh;
    private List<DesignOrderListBean.ListBean> mDataList;
    private String mTitle;
    private int mType;
    private DesignOrderAdapter mAdapter;

    public static DesignOrderListFragment getInstance(String title, int type) {
        DesignOrderListFragment sf = new DesignOrderListFragment();
        sf.mTitle = title;
        page = 1;
        sf.mType = type;
        return sf;
    }

    public String getmTitle() {
        return mTitle;
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
        mDataList = ((DesignOrderListActivity) getActivity()).getDesignOrderListBean().getList();
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
            default:
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
        QueryEntry queryEntry = new QueryEntry();
        if ("0".equals(mType)) {
            queryEntry.getEquals().put("createCompanyId", EanfangApplication.getApplication().getCompanyId() + "");
        } else if ("1".equals(mType)) {
            queryEntry.getEquals().put("createUserId", EanfangApplication.getApplication().getUserId() + "");
        }
        if (!mTitle.equals("全部")) {
            queryEntry.getEquals().put("status", status);
        }
        queryEntry.setPage(page);
        queryEntry.setSize(5);

        EanfangHttp.post(NewApiService.GET_WORK_DESIGN_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<DesignOrderListBean>(getActivity(), true, DesignOrderListBean.class, (bean) -> {
                            ((DesignOrderListActivity) getActivity()).setDesignOrderListBean(bean);
                            getActivity().runOnUiThread(() -> {
                                onDataReceived();
                            });
                        })
//                {
//                    @Override
//                    public void onSuccess(DesignOrderListBean bean) {
//                        ((DesignOrderListActivity) getActivity()).setDesignOrderListBean(bean);
//                        getActivity().runOnUiThread(() -> {
//                            onDataReceived();
//                        });
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                    }
//
//                    @Override
//                    public void onNoData(String message) {
//                        swiprefresh.setRefreshing(false);
//                        page--;
//                        getActivity().runOnUiThread(() -> {
//                            //如果是第一页 没有数据了 则清空 bean
//                            if (page < 1) {
//                                DesignOrderListBean bean = new DesignOrderListBean();
//                                bean.setList(new ArrayList<DesignOrderListBean.ListBean>());
//                                ((DesignOrderListActivity) getActivity()).setDesignOrderListBean(bean);
//                            } else {
//                                showToast("已经到底了");
//                            }
//                            onDataReceived();
//                        });
//                    }
//                }
                );
    }


}

