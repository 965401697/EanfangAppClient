package net.eanfang.worker.ui.fragment.tender;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.base.BaseApplication;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.tender.TenderBidDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.tender.TenderOfferDetailActivity;
import net.eanfang.worker.ui.adapter.worktender.WorkTenderBidAdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;
import net.eanfang.worker.viewmodle.tender.TenderPersonControlViewModle;
import net.eanfang.worker.viewmodle.tender.TenderViewModle;

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
