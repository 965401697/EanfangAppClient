package net.eanfang.worker.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.base.BaseFragment;
import com.eanfang.biz.model.PageBean;

import net.eanfang.worker.databinding.FragmentTemplateItemListBinding;

/**
 * Created by O u r on 2018/5/3.
 */

public abstract class TemplateItemListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    public RecyclerView mRecyclerView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public TextView mTvNoData;

    public int mPage = 1;

    private FragmentTemplateItemListBinding templateItemListBinding;

    private BaseQuickAdapter baseQuickAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        templateItemListBinding = FragmentTemplateItemListBinding.inflate(getLayoutInflater());

        mRecyclerView = templateItemListBinding.rvList;
        mTvNoData = templateItemListBinding.tvNoDatas;
        mSwipeRefreshLayout = templateItemListBinding.swipreFresh;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initAdapter(null);
        return templateItemListBinding.getRoot();
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
        //下拉永远第一页
//        mTenderViewModle.mQueryEntry = null;
        mPage = 1;
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

    protected void initAdapter() {
    }

    protected void initAdapter(BaseQuickAdapter baseQuickAdapter) {
        this.baseQuickAdapter = baseQuickAdapter;
        if (baseQuickAdapter == null) {
            initAdapter();
            return;
        }
        baseQuickAdapter.bindToRecyclerView(mRecyclerView);
        baseQuickAdapter.setOnLoadMoreListener(this, mRecyclerView);
    }


    protected abstract void getData();

    protected void getCommenData(PageBean pageBean) {
        if (mPage == 1) {
            if (pageBean == null || pageBean.getList().size() <= 0) {
                mRecyclerView.setVisibility(View.GONE);
                mTvNoData.setVisibility(View.VISIBLE);
                return;
            }
            baseQuickAdapter.getData().clear();
            baseQuickAdapter.setNewData(pageBean.getList());
            mSwipeRefreshLayout.setRefreshing(false);
            baseQuickAdapter.loadMoreComplete();
            if (pageBean.getList().size() < 10) {
                baseQuickAdapter.loadMoreEnd();
                //释放对象
//                mTenderViewModle.mQueryEntry = null;
            }

            if (pageBean.getList().size() > 0) {
                mTvNoData.setVisibility(View.GONE);
            } else {
                mTvNoData.setVisibility(View.VISIBLE);
            }

        } else {
            baseQuickAdapter.addData(pageBean.getList());
            baseQuickAdapter.loadMoreComplete();
            if (pageBean.getList().size() < 10) {
                baseQuickAdapter.loadMoreEnd();
            }
        }
    }

}
