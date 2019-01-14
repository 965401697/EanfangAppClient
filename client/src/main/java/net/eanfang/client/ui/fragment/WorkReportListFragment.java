package net.eanfang.client.ui.fragment;

import android.content.Intent;
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

import net.eanfang.client.ui.activity.worksapce.oa.workreport.WorkReportDetailActivity;
import net.eanfang.client.ui.adapter.WorkReportListAdapter;

import org.greenrobot.eventbus.Subscribe;


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
    private QueryEntry mQueryEntry;
    public static final int DETAILL_REQUEST_CODE = 22;//点击详情的返回刷新的code
    private WorkReportListBean.ListBean mDetailBean;
    private int mPosition;

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
        mAdapter = new WorkReportListAdapter(mType);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (!PermKit.get().getWorkReportDetailPrem()) return;

                if (((WorkReportListBean.ListBean) adapter.getData().get(position)).getNewOrder() == EanfangConst.WORK_TASK_STATUS_READ) {
//                if (((WorkReportListBean.ListBean) adapter.getData().get(position)).getStatus() == EanfangConst.WORK_TASK_STATUS_UNREAD && mType == 1) {
                    getFirstLookData(((WorkReportListBean.ListBean) adapter.getData().get(position)).getId());
                    mDetailBean = ((WorkReportListBean.ListBean) adapter.getData().get(position));
                    mPosition = position;
                }

//                new WorkReportInfoView((Activity) view.getContext(), true, ((WorkReportListBean.ListBean) adapter.getData().get(position)).getId(), false).show();


                Intent intent = new Intent(getActivity(), WorkReportDetailActivity.class);
                intent.putExtra("id", ((WorkReportListBean.ListBean) adapter.getData().get(position)).getId());
                intent.putExtra("type", GetConstDataUtils.getWorkReportTypeList().get((((WorkReportListBean.ListBean) adapter.getData().get(position)).getType())));
                intent.putExtra("name", ((WorkReportListBean.ListBean) adapter.getData().get(position)).getCreateUser().getAccountEntity().getRealName());
                getActivity().startActivityForResult(intent, DETAILL_REQUEST_CODE);
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
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }
//        if (!Constant.ALL.equals(mTitle)) {
//            String status = GetConstDataUtils.getWorkReportStatus().indexOf(getmTitle()) + "";
//            queryEntry.getEquals().put(Constant.STATUS, status);
//        }
        if (Constant.COMPANY_DATA_CODE == mType) {
            mQueryEntry.getEquals().put(Constant.CREATE_COMPANY_ID, EanfangApplication.getApplication().getCompanyId() + "");
        } else if (Constant.CREATE_DATA_CODE == mType) {
            mQueryEntry.getEquals().put(Constant.CREATE_USER_ID, EanfangApplication.getApplication().getUserId() + "");
        } else if (Constant.ASSIGNEE_DATA_CODE == mType) {
            mQueryEntry.getEquals().put(Constant.ASSIGNEE_USER_ID, EanfangApplication.getApplication().getUserId() + "");
        }
        mQueryEntry.setPage(mPage);
        mQueryEntry.setSize(10);

        EanfangHttp.post(NewApiService.GET_WORK_REPORT_LIST)
                .upJson(JsonUtils.obj2String(mQueryEntry))
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
                                //释放对象
                                mQueryEntry = null;
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

    public void getReportData(QueryEntry queryEntry) {
        this.mQueryEntry = queryEntry;
        mPage = 1;
        getData();
    }


    @Override
    public void onRefresh() {
        mQueryEntry = null;
        mPage = 1;
        getData();
    }

    /**
     * 刷新已读未读的状态
     */
    public void refreshStatus() {
        if (mDetailBean != null) {
            mDetailBean.setNewOrder(0);
            mAdapter.notifyItemChanged(mPosition);
        }
    }

    @Subscribe
    public void onEvent(String createSuccess) {
        if (createSuccess.equals("addReportSuccess")) {
            mQueryEntry = null;
            mPage = 1;
            getData();
        }
    }
}
