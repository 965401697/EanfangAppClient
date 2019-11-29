package net.eanfang.worker.ui.fragment;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.tender.TenderCommitActivity;
import net.eanfang.worker.ui.activity.worksapce.tender.TenderFindDetailActivity;
import net.eanfang.worker.ui.adapter.worktender.WorkTenderFindAdapter;
import net.eanfang.worker.viewmodle.tender.TenderViewModle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import lombok.Setter;
import lombok.experimental.Accessors;

public class HomeFindFragment extends HomeTemplateItemListFragment {

    @Setter
    @Accessors(chain = true)
    private TenderViewModle mTenderViewModle;

    private WorkTenderFindAdapter workTenderFindAdapter;

    public static HomeFindFragment getInstance(TenderViewModle mTenderViewModle) {
        return new HomeFindFragment().setMTenderViewModle(mTenderViewModle);
    }

    @Override
    protected void initAdapter(BaseQuickAdapter baseQuickAdapter) {
        workTenderFindAdapter = new WorkTenderFindAdapter();
        super.initAdapter(workTenderFindAdapter);
        setLinstener();
    }

    @Override
    protected ViewModel initViewModel() {
        mTenderViewModle.getTenderFindLiveData().observe(this, this::getCommenData);
        return mTenderViewModle;
    }


    public void getTenderData(QueryEntry queryEntry) {
        mTenderViewModle.mFindQueryEntry = queryEntry;
        mPage = 1;
        getData();
    }

    @Override
    protected void getData() {
        mTenderViewModle.doGetFindData(mPage);
        /**
         * 添加完成后回来
         *    mTenderViewModle.mNoticeQueryEntry = null;
         *             mPage = 1;
         *             getData();
         * */
    }


    private void setLinstener() {
        workTenderFindAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putLong("tendFindId", workTenderFindAdapter.getData().get(position).getId());
            JumpItent.jump(getActivity(), TenderFindDetailActivity.class, bundle);
        });
        workTenderFindAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_offer:
                    Bundle bundle = new Bundle();
                    bundle.putLong("publishId", workTenderFindAdapter.getData().get(position).getId());
                    JumpItent.jump(getActivity(), TenderCommitActivity.class, bundle);
                    break;
                default:
                    break;
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCreateTender(String status) {
        if (status.equals("tenderCreateSuccess")) {
            mTenderViewModle.mFindQueryEntry = null;
            mPage = 1;
            getData();
        }
    }

}
