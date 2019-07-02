package net.eanfang.worker.ui.fragment.tender;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.util.CallUtils;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.tender.TenderBidDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.tender.TenderCommitActivity;
import net.eanfang.worker.ui.adapter.worktender.WorkTenderBidAdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;
import net.eanfang.worker.viewmodle.tender.TenderPersonControlViewModle;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/6/22
 * @description 我投标的
 */

public class TenderMyBidFragment extends TemplateItemListFragment {


    @Setter
    @Accessors(chain = true)
    private TenderPersonControlViewModle mTenderPersonControlViewModle;

    private WorkTenderBidAdapter workTenderBidAdapter;

    public static TenderMyBidFragment newInstance(TenderPersonControlViewModle tenderPersonControlViewModle) {
        return new TenderMyBidFragment().setMTenderPersonControlViewModle(tenderPersonControlViewModle);
    }

    @Override
    protected void initAdapter(BaseQuickAdapter baseQuickAdapter) {
        workTenderBidAdapter = new WorkTenderBidAdapter();
        super.initAdapter(workTenderBidAdapter);
        setListener();
    }

    @Override
    protected ViewModel initViewModel() {
        mTenderPersonControlViewModle.getMyBidTenderLiveData().observe(this, this::getCommenData);
        return mTenderPersonControlViewModle;
    }

    private void setListener() {
        workTenderBidAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("bidId", workTenderBidAdapter.getData().get(position).getId());
            JumpItent.jump(getActivity(), TenderBidDetailActivity.class, bundle);
        });
        workTenderBidAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_agagin:
                    Bundle bundle_offer = new Bundle();
                    bundle_offer.putSerializable("bidDetail", workTenderBidAdapter.getData().get(position));
                    JumpItent.jump(getActivity(), TenderCommitActivity.class, bundle_offer);
                    break;
                case R.id.tv_contact:
                    CallUtils.call(getActivity(), workTenderBidAdapter.getData().get(position).getApplyConstactsPhone());
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    protected void getData() {
        mTenderPersonControlViewModle.getBidData(mPage);
    }

    @Override
    public void onRefresh() {
        mTenderPersonControlViewModle.mBidQueryEntry = null;
        super.onRefresh();
    }

    public void getTenderData(QueryEntry queryEntry) {
        mTenderPersonControlViewModle.mBidQueryEntry = queryEntry;
        mPage = 1;
        getData();
    }


}
