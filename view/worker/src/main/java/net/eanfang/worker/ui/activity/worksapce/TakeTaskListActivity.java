package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.MineTaskListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.adapter.TakeTaskAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by MrHou
 *
 * @on 2018/1/18  16:03
 * @email houzhongzhou@yeah.net
 * @desc 我要接包
 */

public class TakeTaskListActivity extends BaseWorkerActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_datas)
    TextView mTvNoData;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int mPage = 1;
    private TakeTaskAdapter mAdapter;
    private final int REQEST_TAKE_CODE = 101;
    private int mCurrentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_list);
        ButterKnife.bind(this);
        setTitle("我的找活");
        setRightTitle("查看");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermKit.get().getBidListPrem()) {
                    return;
                }
                startActivity(new Intent(TakeTaskListActivity.this, MineTakePublishListReceiveParentActivity.class));
            }
        });
        setLeftBack();
        initView();

    }

    private void initView() {

        mSwipeRefreshLayout.setOnRefreshListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new TakeTaskAdapter();
        mAdapter.bindToRecyclerView(rvList);
        mAdapter.setOnLoadMoreListener(this);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                new TaskPublishDetailView(TakeTaskListActivity.this, true, (MineTaskListBean.ListBean) mAdapter.getData().get(position), true).show();
                mCurrentPosition = position;
                Intent intent = new Intent(TakeTaskListActivity.this, TaskPublishDetailActivitty.class);
                intent.putExtra("bean", mAdapter.getData().get(position));
                intent.putExtra("isTakePackpage", true);
                startActivityForResult(intent, REQEST_TAKE_CODE);
            }
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mCurrentPosition = position;
            switch (view.getId()) {
                case R.id.tv_select:
                    Intent intent = new Intent(TakeTaskListActivity.this, TaskPublishDetailActivitty.class);
                    intent.putExtra("bean", mAdapter.getData().get(position));
                    intent.putExtra("isTakePackpage", true);
                    startActivityForResult(intent, REQEST_TAKE_CODE);
//                    new TaskPublishDetailView(TakeTaskListActivity.this, true, (MineTaskListBean.ListBean) mAdapter.getData().get(position), true).show();
                    break;
                default:
                    break;
            }
        });

        getData();
    }

    private void getData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("createCompanyId", WorkerApplication.get().getCompanyId() + "");
        queryEntry.getEquals().put("assigneeCompanyId", WorkerApplication.get().getCompanyId() + "");
        queryEntry.setPage(mPage);
        queryEntry.setSize(10);

        EanfangHttp.post(NewApiService.ACCEPT_TASK_PUBLISH_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<MineTaskListBean>(this, true, MineTaskListBean.class) {
                    @Override
                    public void onSuccess(MineTaskListBean bean) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && requestCode == REQEST_TAKE_CODE) {
            mAdapter.remove(mCurrentPosition);
        }

    }

}
