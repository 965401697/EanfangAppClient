package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.content.Intent;
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
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.CooperationEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.CooperationRelationAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CooperationRelationActivity extends BaseWorkerActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_datas)
    TextView mTvNoData;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private int mPage = 1;
    private CooperationRelationAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_relation);
        ButterKnife.bind(this);

        initView();
        mPage = 1;
        startTransaction(true);
    }


    private void initView() {
        setTitle("合作业务");
        setLeftBack();
        setRightImageResId(R.mipmap.ic_news_add);
        setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CooperationRelationActivity.this, CooperationSearchClientActivity.class);
                startActivity(intent);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));


        mAdapter = new CooperationRelationAdapter(R.layout.item_cooperation_relation);
        mAdapter.bindToRecyclerView(rvList);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(CooperationRelationActivity.this, CooperationRelationDetailActivity.class);
                intent.putExtra("bean", (CooperationEntity) adapter.getData().get(position));
                startActivity(intent);
            }
        });

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
        queryEntry.setSize(5);
        queryEntry.setPage(mPage);

        queryEntry.getEquals().put("ownerOrgId", String.valueOf(EanfangApplication.getApplication().getCompanyId()));

        EanfangHttp.post(NewApiService.GET_COOPERATION_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<CooperationEntity>(this, true, CooperationEntity.class, true, (list) -> {


                    if (mPage == 1) {
                        mAdapter.getData().clear();
                        mAdapter.setNewData(list);
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.loadMoreComplete();
                        if (list.size() < 10) {
                            mAdapter.loadMoreEnd();
                        }

                        if (list.size() > 0) {
                            mTvNoData.setVisibility(View.GONE);
                        } else {
                            mTvNoData.setVisibility(View.VISIBLE);
                        }


                    } else {
                        mAdapter.addData(list);
                        mAdapter.loadMoreComplete();
                        if (list.size() < 10) {
                            mAdapter.loadMoreEnd();
                        }
                    }


//                    @Override
//                    public void onNoData(String message) {
//                        mSwipeRefreshLayout.setRefreshing(false);
//                        mAdapter.loadMoreEnd();//没有数据了
//                        if (mAdapter.getData().size() == 0) {
//                            mTvNoData.setVisibility(View.VISIBLE);
//                        } else {
//                            mTvNoData.setVisibility(View.GONE);
//                        }
//
//                    }
//
//                    @Override
//                    public void onCommitAgain() {
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
                }));
    }

}
