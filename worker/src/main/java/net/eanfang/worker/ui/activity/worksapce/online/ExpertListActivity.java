package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.ExpertListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.ExpertsCertificationEntity;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpertListActivity extends BaseWorkerActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.tv_no_datas)
    TextView mTvNoData;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private BaseDataEntity mBrand;

    private int mPage = 1;
    private ExpertListAdapter mExpertListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_list);
        ButterKnife.bind(this);
        mBrand = (BaseDataEntity) getIntent().getSerializableExtra("brand");
        if (mBrand != null) {
            setTitle(mBrand.getDataName());
        } else {
            setTitle("专家在线");
        }
        setLeftBack();

        initViews();
    }

    private void initViews() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExpertListAdapter = new ExpertListAdapter();
        mExpertListAdapter.bindToRecyclerView(recyclerView);
        mExpertListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ExpertListActivity.this, AskExpertActivity.class);
                if (mBrand != null) {
                    intent.putExtra("brand", mBrand);
                    intent.putExtra("userId", ((ExpertsCertificationEntity) adapter.getData().get(position)).getUserId());
                }
                startActivity(intent);
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        getData();
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


    private void getData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(10);
        queryEntry.setPage(mPage);

        if (mBrand != null) {
            queryEntry.getEquals().put("dataCode", mBrand.getDataCode());
        }

        EanfangHttp.post(UserApi.EXPERT_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<ExpertListBean>(this, true, ExpertListBean.class) {
                    @Override
                    public void onSuccess(ExpertListBean bean) {

                        if (mPage == 1) {
                            mExpertListAdapter.getData().clear();
                            mExpertListAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
                            mExpertListAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                mExpertListAdapter.loadMoreEnd();
                            }

                            if (bean.getList().size() > 0) {
                                mTvNoData.setVisibility(View.GONE);
                            } else {
                                mTvNoData.setVisibility(View.VISIBLE);
                            }


                        } else {
                            mExpertListAdapter.addData(bean.getList());
                            mExpertListAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                mExpertListAdapter.loadMoreEnd();
                            }
                        }

                    }

                    @Override
                    public void onNoData(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mExpertListAdapter.loadMoreEnd();//没有数据了
                        if (mExpertListAdapter.getData().size() == 0) {
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

}
