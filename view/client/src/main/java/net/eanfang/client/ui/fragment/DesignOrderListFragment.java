package net.eanfang.client.ui.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.DesignOrderListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PermKit;
import com.eanfang.biz.model.QueryEntry;

import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.worksapce.DesignOrderListActivity;
import net.eanfang.client.ui.adapter.DesignOrderAdapter;
import net.eanfang.client.ui.widget.LookDesignOrderInfoView;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  20:01
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class DesignOrderListFragment extends TemplateItemListFragment {

    private String mTitle;
    private int mType;
    private DesignOrderAdapter mAdapter;


    public static DesignOrderListFragment getInstance(String title, int type) {
        DesignOrderListFragment sf = new DesignOrderListFragment();
        sf.mTitle = title;
        sf.mType = type;
        return sf;
    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    protected void initAdapter() {

        mAdapter = new DesignOrderAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (PermKit.get().getDesignDetailPrem()) {
                    //刷新已读未读
                    ((DesignOrderListBean.ListBean) adapter.getData().get(position)).setNewOrder(0);
                    adapter.notifyItemChanged(position);
                    new LookDesignOrderInfoView(getActivity(), true, ((DesignOrderListBean.ListBean) adapter.getData().get(position)).getId()).show();
                }
            }
        });

    }

    /**
     * 获取工作任务列表
     */
    @Override
    protected void getData() {
        String status = "";
        if (!mTitle.equals("全部")) {
            status = ((DesignOrderListActivity) getActivity()).allmTitles.indexOf(this.getmTitle()) + "";
        }
        QueryEntry queryEntry = new QueryEntry();
        if ("0".equals(mType)) {
            queryEntry.getEquals().put("createCompanyId", ClientApplication.get().getCompanyId() + "");
        } else if ("1".equals(mType)) {
            queryEntry.getEquals().put("createUserId", ClientApplication.get().getUserId() + "");
        }
        if (!mTitle.equals("全部")) {
            queryEntry.getEquals().put("status", status);
        }
        queryEntry.setPage(mPage);
        queryEntry.setSize(10);

        EanfangHttp.post(NewApiService.GET_WORK_DESIGN_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<DesignOrderListBean>(getActivity(), true, DesignOrderListBean.class) {

                    @Override
                    public void onSuccess(DesignOrderListBean bean) {

                        if (mPage == 1) {
                            mAdapter.getData().clear();
                            mAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
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
                            if (bean.getList().size() < 10) {
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

