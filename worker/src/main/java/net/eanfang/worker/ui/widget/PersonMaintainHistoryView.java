package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.MainHistoryBean;
import com.eanfang.model.WorkspaceInstallBean;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.MaintenanceHistoryDetailActivity;
import net.eanfang.worker.ui.adapter.MainAdapter;
import net.eanfang.worker.ui.interfaces.OnDataReceivedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;

/**
 * Created by MrHou
 *
 * @on 2017/11/28  16:49
 * @email houzhongzhou@yeah.net
 * @desc 统一都成acitivity  废弃
 */

public class PersonMaintainHistoryView extends BaseDialog implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    //public class PersonMaintainHistoryView extends BaseDialog implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_datas)
    TextView mTvNoData;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.swipre_fresh)
    SwipyRefreshLayout swiprefresh;
    @BindView(R.id.iv_left)
    ImageView ivLeft;


    private int mPage = 1;
    private Activity mContext;
    private MainAdapter mAdapter;
    private Long id;
    private int type;

    public PersonMaintainHistoryView(Activity context, boolean isfull, Long id, int type) {
        super(context, isfull);
        this.mContext = context;
        this.id = id;
        this.type = type;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_list);
        ButterKnife.bind(this);
        initAdapter();
        initView();

        mPage = 1;
    }

    private void initView() {
        tvTitle.setText("历史记录");
        mSwipeRefreshLayout.setOnRefreshListener(this);
        ivLeft.setOnClickListener(v -> dismiss());
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        queryHistory();
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
        queryHistory();
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mPage++;
        queryHistory();
    }


    private void queryHistory() {
        QueryEntry queryEntry = new QueryEntry();
        if (type == 0) {
            queryEntry.getEquals().put("createUserId", id + "");
        } else {
            queryEntry.getEquals().put("createCompanyId", id + "");
        }
        queryEntry.setSize(10);
        queryEntry.setPage(mPage);
        EanfangHttp.post(NewApiService.QUERY_HISTORY_RECORD_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<MainHistoryBean>(mContext, true, MainHistoryBean.class) {
                    @Override
                    public void onSuccess(MainHistoryBean bean) {

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

    private void initAdapter() {

        mAdapter = new MainAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_select:
                    Intent intent = new Intent(mContext, MaintenanceHistoryDetailActivity.class);
                    intent.putExtra("maintianId", mAdapter.getData().get(position).getId());
                    mContext.startActivity(intent);
                    break;
                default:
                    break;
            }
        });
    }
}
