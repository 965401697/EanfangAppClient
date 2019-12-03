package net.eanfang.worker.ui.fragment;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.ui.activity.worksapce.tender.WorkTenderDetailActivity;
import net.eanfang.worker.ui.adapter.worktender.WorkTenderAdapter;
import net.eanfang.worker.viewmodle.tender.TenderViewModle;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * aa
 */
public class HomeTenderFragment extends HomeTemplateItemListFragment {

    private WorkTenderAdapter workTenderAdapter;

    public int mPage = 1;
    @Setter
    @Accessors(chain = true)
    private TenderViewModle mTenderViewModle;

    public static HomeTenderFragment newInstance(TenderViewModle mTenderViewModle) {
        return new HomeTenderFragment().setMTenderViewModle(mTenderViewModle);
    }

    public void getTenderData(QueryEntry queryEntry) {
        mTenderViewModle.mNoticeQueryEntry = queryEntry;
//        mPage = 1;
//        getData();
    }

    @Override
    protected void initAdapter(BaseQuickAdapter baseQuickAdapter) {
        workTenderAdapter = new WorkTenderAdapter();
        super.initAdapter(workTenderAdapter);
        workTenderAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putLong("id", workTenderAdapter.getData().get(position).getId());
            JumpItent.jump(getActivity(), WorkTenderDetailActivity.class, bundle);
        });
    }


    @Override
    protected ViewModel initViewModel() {
        mTenderViewModle.getTenderLiveData().observe(getActivity(), this::getCommenData);
        return mTenderViewModle;
    }

    @Override
    protected void getData() {
        mTenderViewModle.doGetNoticeData(mTenderViewModle.mIntType, mPage);
    }


}
