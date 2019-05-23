package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.PayOrderListBean;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.worksapce.PayOrderDetailActivity;
import net.eanfang.client.ui.adapter.PayOrderListAdapter;


/**
 * Created by O u r on 2018/5/3.
 */

public class OfferAndPayListFragment1 extends TemplateItemListFragment {

    private PayOrderListAdapter mAdapter;
    private String mTitle;
    private String mType;

    public static OfferAndPayListFragment1 getInstance(String title, String type) {
        OfferAndPayListFragment1 f = new OfferAndPayListFragment1();
        f.mTitle = title;
        f.mType = type;
        return f;

    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    protected void initAdapter() {
        mAdapter = new PayOrderListAdapter(R.layout.item_offer_pay);

        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!PermKit.get().getQuoteDetailPrem()) {
                    return;
                }
                startActivity(new Intent(getActivity(), PayOrderDetailActivity.class).putExtra("id", ((PayOrderListBean.ListBean) adapter.getData().get(position)).getId()));
            }
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_do_first:
                    CallUtils.call(getActivity(), ((PayOrderListBean.ListBean) adapter.getData().get(position)).getReporterPhone());
                    break;
                case R.id.tv_do_second:
                    if (!PermKit.get().getQuoteAgreePrem()) {
                        return;
                    }
                    agreeOffer(((PayOrderListBean.ListBean) adapter.getData().get(position)).getId());
                    break;
                default:
                    break;
            }
        });
    }


    /**
     * 获取工作任务列表
     */
    @Override
    protected void getData() {
        int status = GetConstDataUtils.getQuoteStatus().indexOf(getmTitle());

        QueryEntry queryEntry = new QueryEntry();
        if ("1".equals(mType)) {
            queryEntry.getEquals().put("createUserId", ClientApplication.get().getUserId() + "");
        } else if ("2".equals(mType)) {
            queryEntry.getEquals().put("assigneeUserId", ClientApplication.get().getUserId() + "");
        }
        queryEntry.getEquals().put("status", status + "");

        queryEntry.setPage(mPage);
        queryEntry.setSize(5);

        EanfangHttp.post(NewApiService.QUOTE_ORDER_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<PayOrderListBean>(getActivity(), true, PayOrderListBean.class) {


                    @Override
                    public void onSuccess(PayOrderListBean bean) {


                        if (mPage == 1) {
                            mAdapter.getData().clear();
                            mAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                            if (bean.getList().size() < 5) {
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
                            if (bean.getList().size() < 5) {
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


    private void agreeOffer(Long id) {
        EanfangHttp.get(NewApiService.QUOTE_ORDER_UPDATE)
                .params("id", id)
                .params("status", 1)
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, (bean) -> {
                    showToast("已同意");
                    findViewById(R.id.tv_do_second).setVisibility(View.GONE);
                }));
    }
}
