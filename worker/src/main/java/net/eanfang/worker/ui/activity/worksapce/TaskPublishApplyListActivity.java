package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.ApplyTaskListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.ApplyListAdapter;
import net.eanfang.worker.ui.widget.TaskPubApplyListDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/18  14:33
 * @email houzhongzhou@yeah.net
 * @desc 发包申请列表
 */

public class TaskPublishApplyListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_datas)
    TextView mTvNoData;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int mPage = 1;

    private ApplyListAdapter mAdapter;
    private Long shopTaskPublishId;
    private int mCurrentPositon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("申请列表");
        setLeftBack();
        shopTaskPublishId = getIntent().getLongExtra("shopTaskPublishId", 0);


        mSwipeRefreshLayout.setOnRefreshListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ApplyListAdapter();
        mAdapter.bindToRecyclerView(rvList);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                new TaskPubApplyListDetailView(TaskPublishApplyListActivity.this, true, ((ApplyTaskListBean.ListBean) adapter.getData().get(position)).getId()).show();

            }
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mCurrentPositon = position;
            ApplyTaskListBean.ListBean bean = (ApplyTaskListBean.ListBean) adapter.getData().get(position);
            switch (view.getId()) {
                case R.id.tv_select:
                    ignoreOrSelect(bean.getShopTaskPublishId(),
                            bean.getId(), 1,
                            bean.getCreateCompanyId(),
                            bean.getCreateOrgCode(),
                            bean.getCreateTopCompanyId(),
                            bean.getCreateUserId(),
                            bean.getProjectQuote());
                    break;
                case R.id.tv_ignore:
                    ignoreOrSelect(bean.getShopTaskPublishId(),
                            bean.getId(), 2,
                            bean.getCreateCompanyId(),
                            bean.getCreateOrgCode(),
                            bean.getCreateTopCompanyId(),
                            bean.getCreateUserId(),
                            bean.getProjectQuote());
                    break;
                default:
                    break;
            }
        });

        getData();
    }

    private void getData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setPage(mPage);
        queryEntry.setSize(10);
        queryEntry.getEquals().put("shopTaskPublishId", shopTaskPublishId + "");
        EanfangHttp.post(NewApiService.TASK_APPLY_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<ApplyTaskListBean>(this, true, ApplyTaskListBean.class) {
                    @Override
                    public void onSuccess(ApplyTaskListBean bean) {

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


    private void ignoreOrSelect(Long shopTaskPublishId, Long id, int status, Long createCompanyId,
                                String createOrgCode, Long createTopCompanyId, Long createUserId, int projectQuote) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("shopTaskPublishId", shopTaskPublishId);
        jsonObject.put("id", id);
        jsonObject.put("status", status);
        jsonObject.put("createCompanyId", createCompanyId);
        jsonObject.put("createOrgCode", createOrgCode);
        jsonObject.put("createTopCompanyId", createTopCompanyId);
        jsonObject.put("createUserId", createUserId);
        jsonObject.put("projectQuote", projectQuote);

        String json = JSONObject.toJSONString(jsonObject);
        EanfangHttp.post(NewApiService.TASK_APPLY_LIST_UPDATE)
                .upJson(json)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    if (status == 1) {
                        showToast("中标");
                        setResult(RESULT_OK);
                        finishSelf();
                    } else {
                        showToast("忽略");
                        mAdapter.remove(mCurrentPositon);
                    }
                }));
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        refresh();
    }

    public void refresh() {
        mPage = 1;//下拉永远第一页
        getData();
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mPage++;
        getData();
    }
}
