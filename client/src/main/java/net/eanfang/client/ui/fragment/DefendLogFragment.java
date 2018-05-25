package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.DefendLogDetailBean;
import com.eanfang.model.OpenShopLogBean;
import com.eanfang.util.CallUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.DefendLogItemWriteAndDetailActivity;
import net.eanfang.client.ui.activity.worksapce.DefendLogWriteAndDetailActivity;
import net.eanfang.client.ui.adapter.OpenShopLogAdapter;

/**
 * Created by O u r on 2018/5/22.
 */

public class DefendLogFragment extends TemplateItemListFragment {

    private String mTitle;
    private int mType;
    private OpenShopLogAdapter mAdapter;
    private static int page = 1;

    public static DefendLogFragment getInstance(String title, int type) {
        DefendLogFragment f = new DefendLogFragment();
        f.mTitle = title;
        f.mType = type;
        return f;

    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    protected void initAdapter() {
        mAdapter = new OpenShopLogAdapter(R.layout.item_open_shop);

        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), DefendLogWriteAndDetailActivity.class).putExtra("id", ((OpenShopLogBean.ListBean) adapter.getData().get(position)).getId()));
                //刷新数据
                if (getmTitle().equals("未读日志")) {
                    updateStatus(((OpenShopLogBean.ListBean) adapter.getData().get(position)).getId());//更新数据
                    adapter.remove(position);
                } else if (getmTitle().equals("已读日志")) {

                } else {
                    updateStatus(((OpenShopLogBean.ListBean) adapter.getData().get(position)).getId());//更新数据
                    ((OpenShopLogBean.ListBean) adapter.getData().get(position)).setStatus(1);
                    adapter.notifyItemChanged(position);
                }
            }
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_do_first:
                    CallUtils.call(getActivity(), ((OpenShopLogBean.ListBean) adapter.getData().get(position)).getAssigneeUser().getAccountEntity().getMobile());
                    break;
                default:
                    break;
            }
        });

    }

    @Override
    protected void getData() {
        QueryEntry queryEntry = new QueryEntry();
        if (mType == 1) {
            queryEntry.getEquals().put("ownerUserId", EanfangApplication.getApplication().getUserId() + "");
        } else {
//            queryEntry.getEquals().put("assigneeUserId",  "980695066010497026");
            queryEntry.getEquals().put("assigneeUserId", EanfangApplication.getApplication().getUserId() + "");
        }
        if (getmTitle().equals("未读日志")) {
            queryEntry.getEquals().put(Constant.STATUS, "0");
        } else if (getmTitle().equals("已读日志")) {
            queryEntry.getEquals().put(Constant.STATUS, "1");
        }

        queryEntry.setSize(10);
        queryEntry.setPage(page);

        EanfangHttp.post(NewApiService.OA_DEFEND_LOG_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<OpenShopLogBean>(getActivity(), true, OpenShopLogBean.class) {

                    @Override
                    public void onSuccess(OpenShopLogBean bean) {

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

    private void updateStatus(String id) {
        EanfangHttp.post(NewApiService.OA__DEFEND_LOG_UPDATE)
                .params("protectionLogId", id)
                .execute(new EanfangCallback<JSONObject>(getActivity(), false, JSONObject.class) {
                });

    }
}
