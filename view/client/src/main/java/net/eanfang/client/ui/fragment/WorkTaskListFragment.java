package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.WorkTaskListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.ui.activity.worksapce.oa.task.TaskDetailActivity;
import net.eanfang.client.ui.adapter.WorkTaskListAdapter;

import org.greenrobot.eventbus.Subscribe;


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

    public static final int DETAIL_TASK_REQUSET_COOD = 9;
    private QueryEntry mQueryEntry;
    private WorkTaskListBean.ListBean mDetailTaskBean;
    private int mPosition;

    public static WorkTaskListFragment getInstance(String title, String type) {
        WorkTaskListFragment sf = new WorkTaskListFragment();
        sf.mTitle = title;
        sf.mType = type;
        return sf;

    }

    public String getmTitle() {
        return mTitle;
    }


    @Override
    protected void initAdapter() {

        mAdapter = new WorkTaskListAdapter(Integer.parseInt(mType));
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


                if (!PermKit.get().getWorkTaskDetailPrem()) {
                    return;
                }

                WorkTaskListBean.ListBean bean = (WorkTaskListBean.ListBean) adapter.getData().get(position);

                if (bean.getNewOrder() == (EanfangConst.WORK_TASK_STATUS_READ)) {
//                    getFirstLookData(bean.getId());
                    mDetailTaskBean = ((WorkTaskListBean.ListBean) adapter.getData().get(position));
                    mPosition = position;
                }
//                new WorkTaskInfoView(getActivity(), true, bean.getId(), false).show();
//                Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
//                intent.putExtra("id", ((WorkTaskListBean.ListBean) adapter.getData().get(position)).getId());
//                intent.putExtra("name", ((WorkTaskListBean.ListBean) adapter.getData().get(position)).getCreateUser().getAccountEntity().getRealName());
                Bundle bundle = new Bundle();
                bundle.putString("name", ((WorkTaskListBean.ListBean) adapter.getData().get(position)).getCreateUser().getAccountEntity().getRealName());
                bundle.putLong("id", ((WorkTaskListBean.ListBean) adapter.getData().get(position)).getId());
                JumpItent.jump(getActivity(), TaskDetailActivity.class, bundle, DETAIL_TASK_REQUSET_COOD);
            }
        });
    }

    @Override
    protected void getData() {
//        String status = "";
//        if (!mTitle.equals("全部")) {
//            status = GetConstDataUtils.getWorkTaskStatus().indexOf(getmTitle()) + "";
//        }
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }

        if ("0".equals(mType)) {
            mQueryEntry.getEquals().put("createCompanyId", EanfangApplication.getApplication().getCompanyId() + "");
        } else if ("1".equals(mType)) {
            mQueryEntry.getEquals().put("createUserId", EanfangApplication.getApplication().getUserId() + "");
        } else if ("2".equals(mType)) {
            mQueryEntry.getEquals().put("assigneeUserId", EanfangApplication.getApplication().getUserId() + "");
        }
//        if (!mTitle.equals("全部")) {
//            mQueryEntry.getEquals().put("status", status);
//        }

        mQueryEntry.setPage(mPage);
        mQueryEntry.setSize(10);


        EanfangHttp.post(NewApiService.GET_WORK_TASK_LIST)
                .upJson(JsonUtils.obj2String(mQueryEntry))
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
                                //释放以前对象
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

    public void getTaskData(QueryEntry queryEntry) {
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
        if (mDetailTaskBean != null) {
            mDetailTaskBean.setNewOrder(0);
            mAdapter.notifyItemChanged(mPosition);
        }
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

    @Subscribe
    public void onEvent(String createSuccess) {
        if (createSuccess.equals("addTaskSuccess")) {
            mQueryEntry = null;
            mPage = 1;
            getData();
        }
    }

}
