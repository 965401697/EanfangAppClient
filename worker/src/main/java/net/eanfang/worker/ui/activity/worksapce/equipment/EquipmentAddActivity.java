package net.eanfang.worker.ui.activity.worksapce.equipment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.EquipmentBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EquipmentAddActivity extends BaseWorkerActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_datas)
    TextView mTvNoData;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private int mPage = 1;
    private EquipmentListAdapter mAdapter;
    private Bundle mBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_search);
        ButterKnife.bind(this);
        mBundle = getIntent().getExtras();
        setTitle(Config.get().getBusinessNameByCode((String) mBundle.get("businessOneCode"), 3));
        setLeftBack();
        initView();
        mPage = 1;


    }


    private void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));


        mAdapter = new EquipmentListAdapter(R.layout.item_equipment_list);
        mAdapter.bindToRecyclerView(rvList);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (mBundle != null) {
                    Intent intent = new Intent();
                    intent.putExtra("bean", mAdapter.getData().get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                }
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
        queryEntry.getEquals().put("businessThreeCode", (String) mBundle.get("businessOneCode"));
        queryEntry.getEquals().put("ownerCompanyId", (String) mBundle.get("clientCompanyUid"));
        EanfangHttp.post(NewApiService.DEVICE_LIST_ADD)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<EquipmentBean>(this, true, EquipmentBean.class) {
                    @Override
                    public void onSuccess(EquipmentBean bean) {

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

}
