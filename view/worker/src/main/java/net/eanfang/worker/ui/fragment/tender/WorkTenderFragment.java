package net.eanfang.worker.ui.fragment.tender;


import androidx.lifecycle.ViewModel;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;

import net.eanfang.worker.ui.adapter.worktender.WorkTenderAdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;
import net.eanfang.worker.viewmodle.tender.TenderViewModle;

import org.greenrobot.eventbus.Subscribe;

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
    }

    @Override
    protected ViewModel initViewModel() {
        mTenderViewModle.getTenderLiveData().observe(this, this::getCommenData);
        return mTenderViewModle;
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

    @Subscribe
    public void onEvent(String createSuccess) {
        if (createSuccess.equals("addTenderSuccess")) {
            mTenderViewModle.mNoticeQueryEntry = null;
            mPage = 1;
            getData();
        }
    }
}
