package net.eanfang.worker.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.base.BaseFragment;
import com.eanfang.biz.model.PageBean;

import net.eanfang.worker.databinding.FragmentHomeTemplateItemListBinding;

/**
 * Created by O u r on 2018/5/3.
 */

public abstract class HomeTemplateItemListFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {
    public RecyclerView mRecyclerView;
    public TextView mTvNoData;

    public int mPage = 1;

    public FragmentHomeTemplateItemListBinding templateItemListBinding;

    private BaseQuickAdapter baseQuickAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        templateItemListBinding = FragmentHomeTemplateItemListBinding.inflate(getLayoutInflater());

        mRecyclerView = templateItemListBinding.rvList;
        mTvNoData = templateItemListBinding.tvNoDatas;
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initAdapter(null);
        return templateItemListBinding.getRoot();
    }

    @Override
    protected void onLazyLoad() {
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
//        baseQuickAdapter.setOnLoadMoreListener(this, mRecyclerView);
        baseQuickAdapter.disableLoadMoreIfNotFullPage();
    }


    protected abstract void getData();

    protected void getCommenData(PageBean pageBean) {
        if (mPage == 1) {
            if (pageBean == null) {
                return;
            }
            baseQuickAdapter.getData().clear();
            baseQuickAdapter.setNewData(pageBean.getList());
            baseQuickAdapter.notifyDataSetChanged();
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
