package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.PayOrderListBean;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.PayLogEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.pay.NewPayActivity;
import net.eanfang.client.ui.activity.worksapce.PayOrderDetailActivity;
import net.eanfang.client.ui.adapter.PayOrderListAdapter;

import java.util.List;


/**
 * Created by Mr.hou
 *
 * @on 2017/9/5  9:29
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PersonOfferAndPayListFragment extends TemplateItemListFragment {

    private String mTitle;
    private PayOrderListAdapter mAdapter;

    public static PersonOfferAndPayListFragment getInstance(String title) {
        PersonOfferAndPayListFragment sf = new PersonOfferAndPayListFragment();
        sf.mTitle = title;

        return sf;

    }

    public String getmTitle() {
        return mTitle;
    }


    @Override
    public void initAdapter() {

        mAdapter = new PayOrderListAdapter(R.layout.item_offer_pay);


        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), PayOrderDetailActivity.class).putExtra("id", mAdapter.getData().get(position).getId()));
            }
        });


        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_do_first:
                    CallUtils.call(getActivity(), mAdapter.getData().get(position).getOfferer().getAccountEntity().getMobile());
                    break;
                case R.id.tv_do_second:
                    payment(mAdapter.getData(), position);
                    break;
                default:
                    break;
            }
        });

    }

    /**
     * 支付
     */
    private void payment(List<PayOrderListBean.ListBean> mDataList, int pos) {

        PayLogEntity payLogEntity = new PayLogEntity();
        payLogEntity.setOrderId(mDataList.get(pos).getId());
        payLogEntity.setOrderNum(mDataList.get(pos).getOrderNum());
        payLogEntity.setOrderType(Constant.OrderType.QUOTE.ordinal());
        payLogEntity.setAssigneeUserId(mDataList.get(pos).getOwnerUserId());
        payLogEntity.setAssigneeOrgCode(mDataList.get(pos).getOwnerOrgCode());
        payLogEntity.setAssigneeTopCompanyId(mDataList.get(pos).getOwnerTopCompanyId());
        // 原始价格
        payLogEntity.setOriginPrice(mDataList.get(pos).getTotalCost());
        //实际支付价格
        payLogEntity.setPayPrice(mDataList.get(pos).getTotalCost());
        // 优惠价格
        payLogEntity.setReducedPrice(0);

//        Intent intent = new Intent(getActivity(), PayActivity.class);
        Intent intent = new Intent(getActivity(), NewPayActivity.class);
        intent.putExtra("payLogEntity", payLogEntity);
        startActivity(intent);
//        finishSelf();

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    /**
     * 获取工作任务列表
     */
    @Override
    protected void getData() {
        int status = GetConstDataUtils.getQuoteStatus().indexOf(getmTitle());

        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("assigneeUserId", ClientApplication.get().getUserId() + "");
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
}
