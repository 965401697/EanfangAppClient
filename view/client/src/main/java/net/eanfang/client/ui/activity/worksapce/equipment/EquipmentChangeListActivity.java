package net.eanfang.client.ui.activity.worksapce.equipment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.CustDeviceChangeLogEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EquipmentChangeListActivity extends BaseClientActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_datas)
    TextView mTvNoData;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private int mPage = 1;
    private EquipmentChangeListAdapter mAdapter;

    private String deviceNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_equipment_change);
        ButterKnife.bind(this);
        setTitle("变更记录");
        setLeftBack();
        deviceNo = getIntent().getStringExtra("deviceNo");

        initView();


    }


    private void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));


        mAdapter = new EquipmentChangeListAdapter(R.layout.item_equipment_change_list);
        mAdapter.bindToRecyclerView(rvList);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(EquipmentChangeListActivity.this, EquipmentChangeDetailActivity.class);
                intent.putExtra("id", ((CustDeviceChangeLogEntity) adapter.getData().get(position)).getId());
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
        queryEntry.getEquals().put("deviceNo", deviceNo);
        EanfangHttp.post(NewApiService.DEVICE_CHANGE_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<CustDeviceChangeLogEntity>(this, true, CustDeviceChangeLogEntity.class, true, (list) -> {
//                    @Override
//                    public void onSuccess (FaultListsBean bean){

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

//                    }

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
