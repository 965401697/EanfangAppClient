package net.eanfang.worker.ui.fragment.tender;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.base.BaseActivity;
import com.eanfang.base.BaseFragment;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.tender.TenderCommitActivity;
import net.eanfang.worker.ui.activity.worksapce.tender.TenderFindDetailActivity;
import net.eanfang.worker.ui.adapter.worktender.WorkTenderFindAdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;
import net.eanfang.worker.viewmodle.tender.TenderViewModle;

import lombok.Setter;
import lombok.experimental.Accessors;

import static com.eanfang.base.network.event.BaseActionEvent.EMPTY_DATA;

/**
 * @author guanluocang
 * @data 2019/5/31
 * @description 招标用工大厅  用工找活
 */

public class WorkTenderFindFragment extends TemplateItemListFragment {

    @Setter
    @Accessors(chain = true)
    private TenderViewModle mTenderViewModle;

    private WorkTenderFindAdapter workTenderFindAdapter;

    public static WorkTenderFindFragment getInstance(TenderViewModle tenderViewModle) {
        return new WorkTenderFindFragment().setMTenderViewModle(tenderViewModle);
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

    @Override
    public void onRefresh() {
        mTenderViewModle.mFindQueryEntry = null;
        super.onRefresh();
    }

    public void getTenderData(QueryEntry queryEntry) {
        mTenderViewModle.mFindQueryEntry = queryEntry;
        mPage = 1;
        getData();
    }


    @Override
    protected void getData() {
        mTenderViewModle.doGetFindData(mPage);
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


}
