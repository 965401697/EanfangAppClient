package net.eanfang.worker.ui.fragment.neworder;

import androidx.lifecycle.ViewModel;

import net.eanfang.worker.ui.fragment.TemplateItemListFragment;

/**
 * @author guanluocang
 * @data 2019/10/30  9:38
 * @description 历史订单 报装
 */

public class HistoryInstallFragment extends TemplateItemListFragment {

    public static HistoryInstallFragment getInstance() {
//        return new HomePendingFragment().setMHomeOrderViewModle(homeOrderViewModle);
        return new HistoryInstallFragment();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    protected void getData() {

    }
}
