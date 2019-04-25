package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.ui.base.BaseFragment;

import net.eanfang.client.R;


/**
 * Created by O u r on 2018/5/3.
 */

public abstract class TemplateItemListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    public RecyclerView mRecyclerView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public TextView mTvNoData;
//    public BaseQuickAdapter mAdapter;

    public int mPage = 1;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_template_item_list;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSwipeRefreshLayout = findViewById(R.id.swipre_fresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mTvNoData = findViewById(R.id.tv_no_datas);
        initAdapter();
    }


    @Override
    protected void setListener() {

    }

    @Override
    protected void onLazyLoad() {
        mPage = 1;
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

//    public void setAdapter(BaseQuickAdapter adapter) {
//        this.mAdapter = adapter;
//    }


    protected abstract void initAdapter();


    protected abstract void getData();
}
