package net.eanfang.client.ui.fragment.worktransfer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkTransferListBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CallUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.worktransfer.WorkTransferDetailActivity;
import net.eanfang.client.ui.adapter.worktransfer.WorkTransferAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;

/**
 * @author Guanluocang
 * @date on 2018/7/27  16:23
 * @decision 交接班列表
 */
public class WorkTransferFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private String mTitle;
    private int mType;

    private int page = 1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv_worktalk;
    public static final int REFRESH_LIST_CODE = 99;//刷新列表的request_Code
    private int mRefreshPosition = 0;//刷新的position
    private List<WorkTransferListBean.ListBean> workTalkBeanList = new ArrayList<>();
    private WorkTransferAdapter workTalkAdapter;
    private WorkTransferListBean.ListBean mDetailTaskBean;
    /**
     * 用户ID
     */
    private Long mUserId;

    private boolean mIsCreate = false;

    private QueryEntry mQueryEntry;


    public static WorkTransferFragment getInstance(String title, int type) {
        WorkTransferFragment workTransferFragment = new WorkTransferFragment();
        workTransferFragment.mTitle = title;
        workTransferFragment.mType = type;
        return workTransferFragment;

    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_work_transfer;
    }

    @Override
    protected void initData(Bundle arguments) {
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        getData();
    }


    /**
     * 获取数据
     */
    public void getData() {
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }

//        if (!Constant.ALL.equals(mTitle)) {
//            String status = GetConstDataUtils.getWorkTransfer().indexOf(getmTitle()) + "";
//            mQueryEntry.getEquals().put(Constant.STATUS, status);
//        }
        mQueryEntry.setPage(page);
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
                .execute(new EanfangCallback<WorkTransferListBean>(getActivity(), true, WorkTransferListBean.class, (bean) -> {
                            getActivity().runOnUiThread(() -> {
                                mQueryEntry = null;
                                if (bean.getList() != null) {
                                    workTalkBeanList = bean.getList();
                                    onDataReceived();
                                    swipeRefreshLayout.setRefreshing(false);
                                } else {
                                }
                            });
                        })
                );
    }

    @Override
    protected void initView() {
        // 我接收的
        if (mType == 2) {
            mIsCreate = false;
        } else if (mType == 1) {
            // 我创建的
            mIsCreate = true;
        }
        swipeRefreshLayout = findViewById(R.id.srl_worktransfer);
        swipeRefreshLayout.setOnRefreshListener(this);
        rv_worktalk = findViewById(R.id.rv_worktransfer);
        workTalkAdapter = new WorkTransferAdapter(mIsCreate);
        rv_worktalk.setLayoutManager(new LinearLayoutManager(getContext()));
        workTalkAdapter.bindToRecyclerView(rv_worktalk);
        workTalkAdapter.setOnLoadMoreListener(this, rv_worktalk);
        mUserId = EanfangApplication.get().getUser().getAccount().getDefaultUser().getUserId();
    }

    public void getTaskData(QueryEntry queryEntry) {
        this.mQueryEntry = queryEntry;
        page = 1;
        dataOption(TOP_REFRESH);
    }

    @Override
    public void onRefresh() {
        mQueryEntry = null;
        page = 1;
        dataOption(TOP_REFRESH);
    }

    @Override
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
//                    bundle.putSerializable("userId", workTalkAdapter.getData().get(position).getAssigneeUserId());
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
        if (createSuccess.equals("addTransferSuccess")) {
            mQueryEntry = null;
            page = 1;
            getData();
        }
    }

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
                page--;
                if (page <= 0) {
                    page = 1;
                }
                getData();
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                page++;
                getData();
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        dataOption(BOTTOM_REFRESH);
    }


    public void onDataReceived() {
        if (page == 1) {
            if (workTalkBeanList.size() == 0 || workTalkBeanList == null) {
                showToast("暂无数据");
                workTalkAdapter.getData().clear();
                workTalkAdapter.notifyDataSetChanged();
            } else {
                workTalkAdapter.getData().clear();
                workTalkAdapter.setNewData(workTalkBeanList);
                workTalkAdapter.disableLoadMoreIfNotFullPage();
                if (workTalkBeanList.size() < 10) {
                    workTalkAdapter.loadMoreEnd();
                }
            }
        } else {
            if (workTalkBeanList.size() == 0 || workTalkBeanList == null) {
                showToast("暂无更多数据");
                page = page - 1;
//                messageListAdapter.notifyDataSetChanged();
                workTalkAdapter.loadMoreEnd();
            } else {
                workTalkAdapter.addData(workTalkBeanList);
                workTalkAdapter.loadMoreComplete();
                if (workTalkBeanList.size() < 10) {
                    workTalkAdapter.loadMoreEnd();
                }
            }
        }
    }
}
