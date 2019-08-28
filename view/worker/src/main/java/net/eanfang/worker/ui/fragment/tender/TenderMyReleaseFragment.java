package net.eanfang.worker.ui.fragment.tender;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.util.CallUtils;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.tender.TenderCreateActivity;
import net.eanfang.worker.ui.activity.worksapce.tender.TenderOfferDetailActivity;
import net.eanfang.worker.ui.adapter.worktender.WorkTenderReleaseAdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;
import net.eanfang.worker.viewmodle.tender.TenderPersonControlViewModle;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/6/22
 * @description 我发布的
 */

public class TenderMyReleaseFragment extends TemplateItemListFragment {
    @Setter
    @Accessors(chain = true)
    private TenderPersonControlViewModle mTenderPersonControlViewModle;

    private WorkTenderReleaseAdapter workTenderReleaseAdapter;

    public static TenderMyReleaseFragment newInstance(TenderPersonControlViewModle tenderPersonControlViewModle) {
        return new TenderMyReleaseFragment().setMTenderPersonControlViewModle(tenderPersonControlViewModle);
    }

    @Override
    protected void initAdapter(BaseQuickAdapter baseQuickAdapter) {
        workTenderReleaseAdapter = new WorkTenderReleaseAdapter();
        super.initAdapter(workTenderReleaseAdapter);
        setListener();
    }

    @Override
    protected ViewModel initViewModel() {
        mTenderPersonControlViewModle.getMyReleaseTenderData().observe(this, this::getCommenData);
        mTenderPersonControlViewModle.getMyCloseReleaseTenderData().observe(this, this::getCloseData);
        return mTenderPersonControlViewModle;
    }

    @Override
    protected void getData() {
        mTenderPersonControlViewModle.getReleaseData(mPage);
    }

    @Override
    public void onRefresh() {
        mTenderPersonControlViewModle.mReleaseQueryEntry = null;
        super.onRefresh();
    }

    public void getTenderData(QueryEntry queryEntry) {
        mTenderPersonControlViewModle.mReleaseQueryEntry = queryEntry;
        mPage = 1;
        getData();
    }

    private void getCloseData(TaskPublishEntity taskPublishEntity) {
        mTenderPersonControlViewModle.mReleaseQueryEntry = null;
        mPage = 1;
        getData();
    }

    private void setListener() {
        workTenderReleaseAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            if (workTenderReleaseAdapter.getData().get(position).getPublishStatus() == 1) {
                bundle.putBoolean("offing", true);
            }
            bundle.putSerializable("tenderDetail", workTenderReleaseAdapter.getData().get(position));
            JumpItent.jump(getActivity(), TenderOfferDetailActivity.class, bundle);
        });
        workTenderReleaseAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                //再次发布
                case R.id.tv_release:
                    Bundle bundle_release = new Bundle();
                    bundle_release.putSerializable("agaginRelase", workTenderReleaseAdapter.getData().get(position));
                    JumpItent.jump(getActivity(), TenderCreateActivity.class, bundle_release);
                    break;
                // 关闭
                case R.id.tv_close:
                    mTenderPersonControlViewModle.doCloseMyReleaseTender(workTenderReleaseAdapter.getData().get(position));
                    break;
                // 评标
                case R.id.tv_offer:
                    Bundle bundle_offer = new Bundle();
                    bundle_offer.putBoolean("offing", true);
                    bundle_offer.putSerializable("tenderDetail", workTenderReleaseAdapter.getData().get(position));
                    JumpItent.jump(getActivity(), TenderOfferDetailActivity.class, bundle_offer);
                    break;
                // 联系中标人
                case R.id.tv_contact:
                    CallUtils.call(getActivity(), workTenderReleaseAdapter.getData().get(position).getApplyContactsPhone());
                    break;
                default:
                    break;
            }
        });
    }


}
