package net.eanfang.client.ui.fragment;

import android.app.Activity;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Constant;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkReportListBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.ui.adapter.WorkReportListAdapter;
import net.eanfang.client.ui.widget.WorkReportInfoView;


/**
 * Created by Mr.hou
 *
 * @on 2017/9/5  9:29
 * @email houzhongzhou@yeah.net
 * @desc
 */


public class WorkReportListFragment extends TemplateItemListFragment {


    private String mTitle;
    private int mType;
    private WorkReportListAdapter mAdapter;

    public static WorkReportListFragment getInstance(String title, int type) {
        WorkReportListFragment sf = new WorkReportListFragment();
        sf.mTitle = title;
        sf.mType = type;
        return sf;

    }

    public String getmTitle() {
        return mTitle;
    }


    @Override
    protected void initAdapter() {
        mAdapter = new WorkReportListAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (!PermKit.get().getWorkReportDetailPrem()) return;

                if (((WorkReportListBean.ListBean) adapter.getData().get(position)).getStatus() == EanfangConst.WORK_TASK_STATUS_UNREAD) {
                    getFirstLookData(((WorkReportListBean.ListBean) adapter.getData().get(position)).getId());
                }

                new WorkReportInfoView((Activity) view.getContext(), true, ((WorkReportListBean.ListBean) adapter.getData().get(position)).getId(), false).show();

            }
        });
    }


    /**
     * 首次阅读，更新状态
     */
    private void getFirstLookData(long id) {
        EanfangHttp.get(NewApiService.WORK_REPORT_FIRST_READ)
                .params("id", id)
                .execute(new EanfangCallback(getActivity(), true, JSONObject.class, (bean) -> {

                }));
    }

    @Override
    protected void getData() {

        QueryEntry queryEntry = new QueryEntry();
        if (!Constant.ALL.equals(mTitle)) {
            String status = GetConstDataUtils.getWorkReportStatus().indexOf(getmTitle()) + "";
            queryEntry.getEquals().put(Constant.STATUS, status);
        }
        if (Constant.COMPANY_DATA_CODE == mType) {
            queryEntry.getEquals().put(Constant.CREATE_COMPANY_ID, EanfangApplication.getApplication().getCompanyId() + "");
        } else if (Constant.CREATE_DATA_CODE == mType) {
            queryEntry.getEquals().put(Constant.CREATE_USER_ID, EanfangApplication.getApplication().getUserId() + "");
        } else if (Constant.ASSIGNEE_DATA_CODE == mType) {
            queryEntry.getEquals().put(Constant.ASSIGNEE_USER_ID, EanfangApplication.getApplication().getUserId() + "");
        }
        queryEntry.setPage(mPage);
        queryEntry.setSize(10);

        EanfangHttp.post(NewApiService.GET_WORK_REPORT_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkReportListBean>(getActivity(), true, WorkReportListBean.class) {

                    @Override
                    public void onSuccess(WorkReportListBean bean) {

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
