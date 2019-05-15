package net.eanfang.worker.ui.fragment.worktalk;

import android.os.Bundle;
import android.view.View;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.WorkTalkListBean;
import com.eanfang.util.CallUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.worktalk.WorkTalkDetailActivity;
import net.eanfang.worker.ui.adapter.WorkTalkAdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;

import org.greenrobot.eventbus.Subscribe;

/**
 * @author Guanluocang
 * @date on 2018/7/11  19:08
 * @decision 面谈员工列表
 */
public class WorkTalkListFragment extends TemplateItemListFragment {

    private String mTitle;
    private int mType;

    private WorkTalkAdapter workTalkAdapter;

    public static final int DETAIL_TASK_REQUSET_COOD = 9;

    private WorkTalkListBean.ListBean mDetailTaskBean;
    private int mPosition;
    /**
     * 用户ID
     */
    private Long mUserId;

    private boolean isCreate = false;


    private QueryEntry mQueryEntry;

    public static WorkTalkListFragment getInstance(String title, int type) {
        WorkTalkListFragment workTalkListFragment = new WorkTalkListFragment();
        workTalkListFragment.mTitle = title;
        workTalkListFragment.mType = type;
        return workTalkListFragment;

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
        mUserId = EanfangApplication.get().getUser().getAccount().getDefaultUser().getUserId();
        // 我创建的
        if (mType == 1) {
            mQueryEntry.getEquals().put("ownerUserId", mUserId + "");
            // 我接收的
        } else if (mType == 2) {
            mQueryEntry.getEquals().put("assigneeUserId", mUserId + "");
        }
        mQueryEntry.setPage(mPage);
        mQueryEntry.setSize(10);

        EanfangHttp.post(NewApiService.WORK_TALK)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<WorkTalkListBean>(getActivity(), true, WorkTalkListBean.class) {
                    @Override
                    public void onSuccess(WorkTalkListBean bean) {
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
    protected void setListener() {
        workTalkAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            WorkTalkListBean.ListBean workTalkBean = workTalkAdapter.getData().get(position);
            switch (view.getId()) {
                // 查看详情
                case R.id.tv_seedetail:
                    if (!PermKit.get().getFaceToWorkerDetailPrem()) {
                        return;
                    }
                    mDetailTaskBean = ((WorkTalkListBean.ListBean) adapter.getData().get(position));
                    mPosition = position;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("itemId", workTalkAdapter.getData().get(position).getId());
                    JumpItent.jump(getActivity(), WorkTalkDetailActivity.class, bundle, DETAIL_TASK_REQUSET_COOD);
                    break;
                //联系汇报人
                case R.id.tv_contact:
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
            workTalkAdapter.notifyItemChanged(mPosition);
        }
    }

    @Subscribe
    public void onEvent(String createSuccess) {
        if (createSuccess.equals("addTalkSuccess")) {
            mQueryEntry = null;
            mPage = 1;
            getData();
        }
    }

    @Override
    protected void initAdapter() {
        // 我接收的
        if (mType == 2) {
            isCreate = false;
        } else if (mType == 1) {
            // 我创建的
            isCreate = true;
        }
        workTalkAdapter = new WorkTalkAdapter(isCreate);
        workTalkAdapter.bindToRecyclerView(mRecyclerView);
        workTalkAdapter.setOnLoadMoreListener(this, mRecyclerView);
    }

}
