package net.eanfang.worker.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.WorkCheckListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.oa.check.DealWithFirstActivity;
import net.eanfang.worker.ui.adapter.WorkCheckListAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  13:46
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkCheckListFragment extends TemplateItemListFragment {


    private String mTitle;
    private int mType;
    private WorkCheckListAdapter mAdapter;
    private int currentPosition;
    public static final int REQUST_REFRESH_CODE = 1010;

    private QueryEntry mQueryEntry;
    private WorkCheckListBean.ListBean mDetailTaskBean;


    public static WorkCheckListFragment getInstance(String title, int type) {
        WorkCheckListFragment sf = new WorkCheckListFragment();
        sf.mTitle = title;
        sf.mType = type;
        return sf;

    }

    public String getmTitle() {
        return mTitle;
    }


    @Override
    protected void initAdapter() {
        mAdapter = new WorkCheckListAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (!PermKit.get().getWorkInspectDetailPrem()) {
                    return;
                }

                currentPosition = position;
                mDetailTaskBean = (WorkCheckListBean.ListBean) adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("status", ((WorkCheckListBean.ListBean) adapter.getData().get(position)).getStatus());
                bundle.putLong("id", ((WorkCheckListBean.ListBean) adapter.getData().get(position)).getId());
                JumpItent.jump(getActivity(), DealWithFirstActivity.class, bundle, REQUST_REFRESH_CODE);
            }
        });
    }

    @Override
    protected void getData() {
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }

//        if (!Constant.ALL.equals(mTitle)) {
//            String status = GetConstDataUtils.getWorkInspectStatus().indexOf(getmTitle()) + "";
//            mQueryEntry.getEquals().put(Constant.STATUS, status);
//        }
        if (Constant.COMPANY_DATA_CODE == mType) {// 全部 本公司的
            mQueryEntry.getEquals().put(Constant.CREATE_COMPANY_ID, WorkerApplication.get().getCompanyId() + "");
        } else if (Constant.CREATE_DATA_CODE == mType) {// 我创建的
            mQueryEntry.getEquals().put(Constant.CREATE_USER_ID, WorkerApplication.get().getUserId() + "");
        } else if (Constant.ASSIGNEE_DATA_CODE == mType) {// 我负责的
            mQueryEntry.getEquals().put(Constant.ASSIGNEE_USER_ID,WorkerApplication.get().getUserId() + "");
        }
        mQueryEntry.setPage(mPage);
        mQueryEntry.setSize(10);
        EanfangHttp.post(NewApiService.GET_WORK_CHECK_LIST)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<WorkCheckListBean>(getActivity(), true, WorkCheckListBean.class) {


                    @Override
                    public void onSuccess(WorkCheckListBean bean) {

                        if (mPage == 1) {
                            mAdapter.getData().clear();
                            mAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                mAdapter.loadMoreEnd();
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
            mAdapter.notifyItemChanged(currentPosition);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)//MAIN代表主线程
    public void receiveMessage(String message) {//该方法名可更改，不影响任何东西。
        if (message.equals("addDealWithInfoSuccess")) {
            mAdapter.remove(currentPosition);
            if (mAdapter.getData().size() <= 0) {
                mTvNoData.setVisibility(View.VISIBLE);
            } else {
                mTvNoData.setVisibility(View.GONE);
            }
        }
    }

    @Subscribe
    public void onEvent(String createSuccess) {
        if (createSuccess.equals("addCheckSuccess")) {
            mQueryEntry = null;
            mPage = 1;
            getData();
        }
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
