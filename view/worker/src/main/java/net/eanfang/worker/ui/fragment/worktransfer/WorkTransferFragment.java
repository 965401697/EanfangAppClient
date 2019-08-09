package net.eanfang.worker.ui.fragment.worktransfer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.WorkTransferListBean;
import com.eanfang.util.CallUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.biz.model.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.worktransfer.WorkTransferDetailActivity;
import net.eanfang.worker.ui.adapter.worktransfer.WorkTransferAdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Guanluocang
 * @date on 2018/7/27  16:23
 * @decision 交接班列表
 */
public class WorkTransferFragment extends TemplateItemListFragment {

    private String mTitle;
    private int mType;

    public static final int REFRESH_LIST_CODE = 99;//刷新列表的request_Code
    private int mRefreshPosition = 0;//刷新的position
    private List<WorkTransferListBean.ListBean> workTalkBeanList = new ArrayList<>();
    private WorkTransferAdapter workTalkAdapter;
    private WorkTransferListBean.ListBean mDetailTaskBean;
    /**
     * 用户ID
     */
    private Long mUserId;

    private QueryEntry mQueryEntry;

    private boolean mIsCreate = false;

    public static WorkTransferFragment getInstance(String title, int type) {
        WorkTransferFragment workTransferFragment = new WorkTransferFragment();
        workTransferFragment.mTitle = title;
        workTransferFragment.mType = type;
        return workTransferFragment;

    }

    public String getmTitle() {
        return mTitle;
    }

    /**
     * 获取数据
     */
    @Override
    protected void getData() {
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }

        mQueryEntry.setPage(mPage);
        mQueryEntry.setSize(10);
        // 我接收的
        if (mType == 2) {
            mQueryEntry.getEquals().put("assigneeUserId", mUserId + "");
            mIsCreate = false;
        } else if (mType == 1) {
            // 我创建的
            mQueryEntry.getEquals().put("ownerUserId", mUserId + "");
            mIsCreate = true;
        }

        EanfangHttp.post(NewApiService.WORK_TRANSFER_LIST)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<WorkTransferListBean>(getActivity(), true, WorkTransferListBean.class) {
                    @Override
                    public void onSuccess(WorkTransferListBean bean) {
                        if (mPage == 1) {
                            workTalkAdapter.getData().clear();
                            workTalkAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
                            workTalkAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                workTalkAdapter.loadMoreEnd();
                                //释放对象
                                mQueryEntry = null;
                            }

                            if (bean.getList().size() > 0) {
                                mTvNoData.setVisibility(View.GONE);
                            } else {
                                mTvNoData.setVisibility(View.VISIBLE);
                            }


                        } else {
                            workTalkAdapter.addData(bean.getList());
                            workTalkAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                workTalkAdapter.loadMoreEnd();
                            }
                        }

                    }

                    @Override
                    public void onNoData(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        workTalkAdapter.loadMoreEnd();//没有数据了
                        if (workTalkAdapter.getData().size() == 0) {
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
    protected void initAdapter() {
        // 我接收的
        if (mType == 2) {
            mIsCreate = false;
        } else if (mType == 1) {
            // 我创建的
            mIsCreate = true;
        }
        workTalkAdapter = new WorkTransferAdapter(mIsCreate);
        workTalkAdapter.bindToRecyclerView(mRecyclerView);
        workTalkAdapter.setOnLoadMoreListener(this, mRecyclerView);

        mUserId = WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getUserId();
        setListener();
    }


    protected void setListener() {
        workTalkAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            WorkTransferListBean.ListBean workTalkBean = workTalkAdapter.getData().get(position);
            switch (view.getId()) {
                // 查看详情
                case R.id.tv_seedetail:
                    if (!PermKit.get().getExchangeDetailPrem()) {
                        return;
                    }
                    mRefreshPosition = position;
                    mDetailTaskBean = (WorkTransferListBean.ListBean) adapter.getData().get(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("itemId", workTalkAdapter.getData().get(position).getId());
                    bundle.putSerializable("userId", workTalkAdapter.getData().get(position).getAssigneeUserId());
                    bundle.putInt("status", workTalkAdapter.getData().get(position).getStatus());
                    JumpItent.jump(getActivity(), WorkTransferDetailActivity.class, bundle, REFRESH_LIST_CODE);
                    break;
                //联系汇报人
                case R.id.tv_contact:
                    // 我接收的
                    if (mType == 2) {
                        CallUtils.call(getActivity(), workTalkBean.getOwnerUserEntity().getAccountEntity().getMobile());
                    } else if (mType == 1) {//我创建的
                        CallUtils.call(getActivity(), workTalkBean.getAssigneeUserEntity().getAccountEntity().getMobile());
                    } else {
                        if (workTalkAdapter.getData().get(position).getAssigneeUserId().equals(mUserId + "")) {
                            CallUtils.call(getActivity(), workTalkBean.getOwnerUserEntity().getAccountEntity().getMobile());
                        } else {
                            CallUtils.call(getActivity(), workTalkBean.getAssigneeUserEntity().getAccountEntity().getMobile());
                        }
                    }
                    break;
                default:
                    break;
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REFRESH_LIST_CODE && resultCode == getActivity().RESULT_OK) {
            workTalkAdapter.remove(mRefreshPosition);
            workTalkAdapter.notifyDataSetChanged();
            if (workTalkAdapter.getData().size() == 0) {
                showToast("暂无数据");
            }
        }
    }

    /**
     * 刷新已读未读的状态
     */
    public void refreshStatus() {
        if (mDetailTaskBean != null) {
            mDetailTaskBean.setNewOrder(0);
            workTalkAdapter.notifyItemChanged(mRefreshPosition);
        }
    }

    @Subscribe
    public void onEvent(String createSuccess) {
        if (createSuccess.equals("addWorkTransferSuccess")) {
            mQueryEntry = null;
            mPage = 1;
            getData();
        }
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

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
