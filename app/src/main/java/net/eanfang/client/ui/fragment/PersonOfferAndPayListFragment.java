package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.util.CallUtils;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.config.Config;
import net.eanfang.client.config.Constant;
import net.eanfang.client.network.apiservice.NewApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.activity.worksapce.PayOrderDetailActivity;
import net.eanfang.client.ui.activity.worksapce.PersonOfferAndPayOrderActivity;
import net.eanfang.client.ui.adapter.PayOrderListAdapter;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.interfaces.OnDataReceivedListener;
import net.eanfang.client.ui.model.PayOrderListBean;
import net.eanfang.client.util.JsonUtils;
import net.eanfang.client.util.QueryEntry;

import java.util.List;

import static net.eanfang.client.config.EanfangConst.BOTTOM_REFRESH;
import static net.eanfang.client.config.EanfangConst.TOP_REFRESH;


/**
 * Created by Mr.hou
 *
 * @on 2017/9/5  9:29
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PersonOfferAndPayListFragment extends BaseFragment implements
        OnDataReceivedListener, SwipyRefreshLayout.OnRefreshListener {
    TextView tvNoDatas;
    RecyclerView rvList;
    SwipyRefreshLayout swiprefresh;
    private List<PayOrderListBean.ListBean> mDataList;
    private String mTitle;
    private PayOrderListAdapter mAdapter;
    private static int page = 1;

    public static PersonOfferAndPayListFragment getInstance(String title) {
        PersonOfferAndPayListFragment sf = new PersonOfferAndPayListFragment();
        sf.mTitle = title;
        page = 1;
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

    }

    @Override
    protected void initView() {
        tvNoDatas = (TextView) findViewById(R.id.tv_no_datas);
        swiprefresh = (SwipyRefreshLayout) findViewById(R.id.swiprefresh);
        swiprefresh.setOnRefreshListener(this);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
    }

    @Override
    protected void setListener() {
    }

    private void initAdapter() {
        mDataList = ((PersonOfferAndPayOrderActivity) getActivity()).getWorkReportListBean().getList();
        mAdapter = new PayOrderListAdapter(R.layout.item_offer_pay, mDataList);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), PayOrderDetailActivity.class).putExtra("id", mDataList.get(position).getId()));
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_do_first:
                        CallUtils.call(getActivity(), mDataList.get(position).getOfferer().getAccountEntity().getMobile());
                        break;
                    case R.id.tv_do_second:
                        showToast("等待开通");
                        break;
                    default:
                        break;
                }
            }
        });
        if (mDataList.size() > 0) {
            rvList.setAdapter(mAdapter);
            tvNoDatas.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
        } else {
            tvNoDatas.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
        getData();
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

    /**
     * 获取工作任务列表
     */
    private void getData() {
        int status = Config.getConfig().getConstBean().getQuoteOrderConstant().get(Constant.CUST_STATUS).indexOf(getmTitle());

        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("assigneeUserId", EanfangApplication.getApplication().getUserId() + "");
        queryEntry.getEquals().put("status", status + "");

        queryEntry.setPage(page);
        queryEntry.setSize(5);

        EanfangHttp.post(NewApiService.QUOTE_ORDER_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<PayOrderListBean>(getActivity(), true, PayOrderListBean.class, (bean) -> {
                            getActivity().runOnUiThread(() -> {
                                ((PersonOfferAndPayOrderActivity) getActivity()).setWorkReportListBean(bean);
                                onDataReceived();
                            });
                        })
                );
    }


    @Override
    public void onDataReceived() {
        initView();
        initAdapter();
        swiprefresh.setRefreshing(false);
    }
}
