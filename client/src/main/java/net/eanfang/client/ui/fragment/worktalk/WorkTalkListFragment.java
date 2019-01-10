package net.eanfang.client.ui.fragment.worktalk;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkTalkListBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CallUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.worktalk.WorkTalkDetailActivity;
import net.eanfang.client.ui.adapter.WorkTalkAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;

/**
 * @author Guanluocang
 * @date on 2018/7/11  19:08
 * @decision 面谈员工列表
 */
public class WorkTalkListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private String mTitle;
    private int mType;

    private int page = 1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv_worktalk;
    private TextView tvNoData;
    private List<WorkTalkListBean.ListBean> workTalkBeanList = new ArrayList<>();
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

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_work_talk_list;
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
//            String status = GetConstDataUtils.getWorkTalkStatus().indexOf(getmTitle()) + "";
//            mQueryEntry.getEquals().put(Constant.STATUS, status);
//        }
        // 我创建的
        if (mType == 1) {
            mQueryEntry.getEquals().put("ownerUserId", mUserId + "");
            // 我接收的
        } else if (mType == 2) {
//            queryEntry.getEquals().put("assigneeUserId",  "980695066010497026");
            mQueryEntry.getEquals().put("assigneeUserId", mUserId + "");
        }
        mQueryEntry.setPage(page);
        mQueryEntry.setSize(10);

        EanfangHttp.post(NewApiService.WORK_TALK)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<WorkTalkListBean>(getActivity(), true, WorkTalkListBean.class, (bean) -> {
//                            getActivity().runOnUiThread(() -> {
                            if (bean.getList() != null) {
                                tvNoData.setVisibility(View.GONE);
                                rv_worktalk.setVisibility(View.VISIBLE);
                                workTalkBeanList = bean.getList();
                                onDataReceived();
                                swipeRefreshLayout.setRefreshing(false);
                            } else {
                                tvNoData.setVisibility(View.VISIBLE);
                                rv_worktalk.setVisibility(View.GONE);
                            }
//                            });
                        })
                );
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
    protected void initView() {

        // 我接收的
        if (mType == 2) {
            isCreate = false;
        } else if (mType == 1) {
            // 我创建的
            isCreate = true;
        }

        swipeRefreshLayout = findViewById(R.id.srl_worktalk);
        swipeRefreshLayout.setOnRefreshListener(this);
        rv_worktalk = findViewById(R.id.rv_worktalk);
        workTalkAdapter = new WorkTalkAdapter(isCreate);
        rv_worktalk.setLayoutManager(new LinearLayoutManager(getContext()));
        workTalkAdapter.bindToRecyclerView(rv_worktalk);
        tvNoData = findViewById(R.id.tv_no_data);
        workTalkAdapter.setOnLoadMoreListener(this, rv_worktalk);
        mUserId = EanfangApplication.get().getUser().getAccount().getDefaultUser().getUserId();
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
            page = 1;
            getData();
        }
    }

    public void onDataReceived() {
        if (page == 1) {
            if (workTalkBeanList.size() == 0 || workTalkBeanList == null) {
                showToast("暂无数据");
                workTalkAdapter.getData().clear();
                workTalkAdapter.notifyDataSetChanged();
                mQueryEntry = null;
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
