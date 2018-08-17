package net.eanfang.client.ui.fragment;

import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkTaskListBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.ui.adapter.WorkTaskListAdapter;
import net.eanfang.client.ui.widget.WorkTaskInfoView;


/**
 * Created by MrHou
 *
 * @on 2017/11/22  17:37
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkTaskListFragment extends TemplateItemListFragment {


    private String mTitle;
    private String mType;
    private WorkTaskListAdapter mAdapter;

    public static WorkTaskListFragment getInstance(String title, int type) {
        WorkTaskListFragment sf = new WorkTaskListFragment();
        sf.mTitle = title;
        sf.mType = String.valueOf(type);
        return sf;

    }

    public String getmTitle() {
        return mTitle;
    }


    @Override
    protected void initAdapter() {

        mAdapter = new WorkTaskListAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (!PermKit.get().getWorkTaskDetailPrem()) return;

                WorkTaskListBean.ListBean bean = (WorkTaskListBean.ListBean) adapter.getData().get(position);

                if (bean.getStatus() == (EanfangConst.WORK_TASK_STATUS_UNREAD)) {
                    getFirstLookData(bean.getId());
                }
                new WorkTaskInfoView(getActivity(), true, bean.getId(), false).show();
            }
        });
    }

    @Override
    protected void getData() {
        String status = "";
        if (!mTitle.equals("全部")) {
            status = GetConstDataUtils.getWorkTaskStatus().indexOf(getmTitle()) + "";
        }

        QueryEntry queryEntry = new QueryEntry();
        if ("0".equals(mType)) {
            queryEntry.getEquals().put("createCompanyId", EanfangApplication.getApplication().getCompanyId() + "");
        } else if ("1".equals(mType)) {
            queryEntry.getEquals().put("createUserId", EanfangApplication.getApplication().getUserId() + "");
        } else if ("2".equals(mType)) {
            queryEntry.getEquals().put("assigneeUserId", EanfangApplication.getApplication().getUserId() + "");
        }
        if (!mTitle.equals("全部")) {
            queryEntry.getEquals().put("status", status);
        }

        queryEntry.setPage(mPage);
        queryEntry.setSize(10);


        EanfangHttp.post(NewApiService.GET_WORK_TASK_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkTaskListBean>(getActivity(), true, WorkTaskListBean.class) {

                    @Override
                    public void onSuccess(WorkTaskListBean bean) {

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


    /**
     * 首次阅读，更新状态
     */
    private void getFirstLookData(Long id) {
        EanfangHttp.get(NewApiService.WORK_TASK_FIRST_READ)
                .params("id", id)
                .execute(new EanfangCallback(getActivity(), true, JSONObject.class, (bean) -> {

                }));
    }
}
