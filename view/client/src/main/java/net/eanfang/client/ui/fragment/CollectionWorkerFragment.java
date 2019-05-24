package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.CollectionWorkerListBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.adapter.CollectionWorkerListAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;


/**
 * Created by Administrator on 2017/6/22.
 */

public class CollectionWorkerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerView mRecyclerView;
    private List<CollectionWorkerListBean.ListBean> mDataList = new ArrayList<>();
    private TextView mTvNoData;
    private CollectionWorkerListAdapter evaluateAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static CollectionWorkerFragment getInstance() {
        CollectionWorkerFragment sf = new CollectionWorkerFragment();
//        sf.id = id;
        return sf;
    }

    private int page = 1;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {

        mRecyclerView = findViewById(R.id.rv_list);
        mTvNoData = findViewById(R.id.tv_nodata);
        mSwipeRefreshLayout = findViewById(R.id.swipre_fresh);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        evaluateAdapter = new CollectionWorkerListAdapter();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        evaluateAdapter.setOnLoadMoreListener(this, mRecyclerView);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        getData();
    }

    @Override
    protected void setListener() {

    }

    private void getData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("ownerId", ClientApplication.get().getUserId() + "");
        queryEntry.setSize(10);
        queryEntry.setPage(page);
        EanfangHttp.post(RepairApi.GET_COLLECT_List)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<CollectionWorkerListBean>(getActivity(), true, CollectionWorkerListBean.class, (bean) -> {
                    mDataList = bean.getList();
                    initAdapter();
                    onDataReceived();
                    mSwipeRefreshLayout.setRefreshing(false);
                }));
    }

    private void initAdapter() {

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        dataOption(TOP_REFRESH);
    }


    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        dataOption(BOTTOM_REFRESH);
    }

    public void onDataReceived() {
        if (page == 1) {
            if (mDataList.size() == 0 || mDataList == null) {
                showToast("暂无数据");
                evaluateAdapter.getData().clear();
                evaluateAdapter.notifyDataSetChanged();
            } else {
                evaluateAdapter.getData().clear();
                evaluateAdapter.setNewData(mDataList);
                if (mDataList.size() < 10) {
                    evaluateAdapter.loadMoreEnd();
                }
            }
        } else {
            if (mDataList.size() == 0 || mDataList == null) {
                showToast("暂无更多数据");
                page = page - 1;
                evaluateAdapter.loadMoreEnd();
            } else {
                evaluateAdapter.addData(mDataList);
                evaluateAdapter.loadMoreComplete();
                if (mDataList.size() < 10) {
                    evaluateAdapter.loadMoreEnd();
                }
            }
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


}
