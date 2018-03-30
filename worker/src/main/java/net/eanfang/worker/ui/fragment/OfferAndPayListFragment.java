package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.PayOrderListBean;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CallUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.PayOrderDetailActivity;
import net.eanfang.worker.ui.adapter.PayOrderListAdapter;
import net.eanfang.worker.ui.interfaces.OnDataReceivedListener;

import java.util.List;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;


/**
 * Created by Mr.hou
 *
 * @on 2017/9/5  9:29
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class OfferAndPayListFragment extends BaseFragment implements
        OnDataReceivedListener, SwipyRefreshLayout.OnRefreshListener {
    RecyclerView rvList;
    SwipyRefreshLayout swiprefresh;
    private List<PayOrderListBean.ListBean> mDataList;
    private String mTitle;
    private String mType;
    private PayOrderListAdapter mAdapter;
    private Long id;
    private static int page = 1;

    public static OfferAndPayListFragment getInstance(String title, String type) {
        OfferAndPayListFragment sf = new OfferAndPayListFragment();
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
        return R.layout.fragment_work_report_list;
    }

    @Override
    protected void initData(Bundle arguments) {
        getData(1);
    }

    @Override
    protected void initView() {
        swiprefresh = (SwipyRefreshLayout) findViewById(R.id.swiprefresh);
        swiprefresh.setOnRefreshListener(this);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
    }

    @Override
    protected void setListener() {
    }

    private void initAdapter(List<PayOrderListBean.ListBean> mDataList) {
        mAdapter = new PayOrderListAdapter(R.layout.item_offer_pay, mDataList);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), PayOrderDetailActivity.class).putExtra("id", mDataList.get(position).getId()));
            }
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_do_first:
                    CallUtils.call(getActivity(), mDataList.get(position).getReporterPhone());
                    break;
                case R.id.tv_do_second:
                    agreeOffer(mDataList.get(position).getId());
                    break;
                default:
                    break;
            }
        });
        rvList.setAdapter(mAdapter);

    }

    private void agreeOffer(Long id) {
        EanfangHttp.get(NewApiService.QUOTE_ORDER_UPDATE)
                .params("id", id)
                .params("status", 1)
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, (bean) -> {
                    showToast("已同意");
                    findViewById(R.id.tv_do_second).setVisibility(View.GONE);
                }));
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
                getData(page);
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                page++;
                getData(page);
                break;
            default:
                break;
        }
    }

    /**
     * 获取工作任务列表
     */
    private void getData(int page) {
        int status = constDataUtils.getQuoteStatus().indexOf(getmTitle());

        QueryEntry queryEntry = new QueryEntry();
        if ("1".equals(mType)) {
            queryEntry.getEquals().put("createUserId", EanfangApplication.getApplication().getUserId() + "");
        } else if ("2".equals(mType)) {
            queryEntry.getEquals().put("assigneeCompanyId", EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyId() + "");
        }
        queryEntry.getEquals().put("status", status + "");

        queryEntry.setPage(page);
        queryEntry.setSize(5);

        EanfangHttp.post(NewApiService.QUOTE_ORDER_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<PayOrderListBean>(getActivity(), true, PayOrderListBean.class, (bean) -> {
                            getActivity().runOnUiThread(() -> {
                                onDataReceived();
                                initAdapter(bean.getList());
                            });
                        })
                );
    }


    @Override
    public void onDataReceived() {
//        initView();
        swiprefresh.setRefreshing(false);
    }
}
