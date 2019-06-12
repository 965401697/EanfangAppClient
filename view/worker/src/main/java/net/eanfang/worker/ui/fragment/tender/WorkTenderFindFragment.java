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
import com.eanfang.base.BaseFragment;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.worktender.WorkTenderFindAdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;
import net.eanfang.worker.viewmodle.tender.TenderViewModle;

import lombok.Setter;
import lombok.experimental.Accessors;

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
    protected ViewModel initViewModel() {
        mTenderViewModle.getTenderLiveData().observe(this, this::getCommenData);
        return mTenderViewModle;
    }

    @Override
    protected void initAdapter(BaseQuickAdapter baseQuickAdapter) {
        workTenderFindAdapter = new WorkTenderFindAdapter();
        super.initAdapter(workTenderFindAdapter);
    }

    @Override
    protected void getData() {
        mTenderViewModle.doGetFindData(mPage);
    }

}
