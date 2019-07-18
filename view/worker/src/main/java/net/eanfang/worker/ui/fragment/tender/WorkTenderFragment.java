package net.eanfang.worker.ui.fragment.tender;


import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.ui.activity.worksapce.tender.WorkTenderDetailActivity;
import net.eanfang.worker.ui.adapter.worktender.WorkTenderAdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;
import net.eanfang.worker.viewmodle.tender.TenderViewModle;

import lombok.Setter;
import lombok.experimental.Accessors;

public class WorkTenderFragment extends TemplateItemListFragment {

    private WorkTenderAdapter workTenderAdapter;

    @Setter
    @Accessors(chain = true)
    private TenderViewModle mTenderViewModle;

    public static WorkTenderFragment getInstance(TenderViewModle tenderViewModle) {
        return new WorkTenderFragment().setMTenderViewModle(tenderViewModle);
    }

    @Override
    protected void initAdapter(BaseQuickAdapter baseQuickAdapter) {
        workTenderAdapter = new WorkTenderAdapter();
        super.initAdapter(workTenderAdapter);
        setListener();
    }


    @Override
    protected ViewModel initViewModel() {
        mTenderViewModle.getTenderLiveData().observe(this, this::getCommenData);
        return mTenderViewModle;
    }

    @Override
    public void onRefresh() {
        mTenderViewModle.mNoticeQueryEntry = null;
        super.onRefresh();
    }

    @Override
    protected void getData() {
        mTenderViewModle.doGetNoticeData(mTenderViewModle.mIntType, mPage);
    }

    public void getTenderData(QueryEntry queryEntry) {
        mTenderViewModle.mNoticeQueryEntry = queryEntry;
        mPage = 1;
        getData();
    }

    private void setListener() {
        workTenderAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putLong("id", workTenderAdapter.getData().get(position).getId());
            JumpItent.jump(getActivity(), WorkTenderDetailActivity.class, bundle);
        });
    }

}
