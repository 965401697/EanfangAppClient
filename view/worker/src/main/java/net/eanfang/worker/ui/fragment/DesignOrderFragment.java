package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.DesignOrderListBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PermKit;
import com.eanfang.biz.model.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.design.DesignOrderDetailActivity;
import net.eanfang.worker.ui.adapter.DesignOrderAdapter;

/**
 * Created by O u r on 2018/6/25.
 */

public class DesignOrderFragment extends TemplateItemListFragment {

    private DesignOrderAdapter mAdapter;
    private String mTitle;
    private String mType;

    public static DesignOrderFragment getInstance(String title, String type) {
        DesignOrderFragment f = new DesignOrderFragment();
        f.mTitle = title;
        f.mType = type;
        return f;

    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    protected void initAdapter() {
        mAdapter = new DesignOrderAdapter(R.layout.item_design_order);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(!PermKit.get().getDesignDetailPrem()) {
                    return;
                }
                startActivity(new Intent(getActivity(), DesignOrderDetailActivity.class).putExtra("id", String.valueOf(((DesignOrderListBean.ListBean) adapter.getData().get(position)).getId())));
            }
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_detail:
                    if(!PermKit.get().getDesignDetailPrem()) {
                        return;
                    }
                    DesignOrderListBean.ListBean bean = (DesignOrderListBean.ListBean) adapter.getData().get(position);
                    startActivity(new Intent(getActivity(), DesignOrderDetailActivity.class).putExtra("id", String.valueOf(((DesignOrderListBean.ListBean) adapter.getData().get(position)).getId())));
                    break;
                default:
                    break;
            }
        });
    }


    /**
     * 获取设计订单列表
     */
    @Override
    protected void getData() {
        int status = GetConstDataUtils.getDesignStatus().indexOf(getmTitle());

        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("assigneeUserId", WorkerApplication.get().getUserId() + "");

        queryEntry.getEquals().put("status", status + "");

        queryEntry.setPage(mPage);
        queryEntry.setSize(5);

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

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
