package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkCheckListBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.ui.activity.worksapce.WorkCheckInfoActivity;
import net.eanfang.worker.ui.adapter.WorkCheckListAdapter;

import static android.app.Activity.RESULT_OK;

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
    public static final int REQUST_REFRESH_CODE = 101;

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
                if (!PermKit.get().getWorkInspectDetailPrem()) return;
                currentPosition = position;
                Intent intent = new Intent(getActivity(), WorkCheckInfoActivity.class);
                intent.putExtra("id", ((WorkCheckListBean.ListBean) adapter.getData().get(position)).getId());
                intent.putExtra("status", ((WorkCheckListBean.ListBean) adapter.getData().get(position)).getStatus());
                startActivityForResult(intent, REQUST_REFRESH_CODE);
            }
        });
    }

    @Override
    protected void getData() {
        QueryEntry queryEntry = new QueryEntry();
        if (!Constant.ALL.equals(mTitle)) {
            String status = GetConstDataUtils.getWorkInspectStatus().indexOf(getmTitle()) + "";
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
        EanfangHttp.post(NewApiService.GET_WORK_CHECK_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            mAdapter.remove(currentPosition);
        }
    }
}
