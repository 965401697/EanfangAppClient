package net.eanfang.worker.ui.fragment.neworder;

import androidx.lifecycle.ViewModel;

import net.eanfang.worker.ui.fragment.TemplateItemListFragment;

/**
 * @author guanluocang
 * @data 2019/10/30  9:38
 * @description  历史订单 报修
 */

public class HistoryRepairFragment extends TemplateItemListFragment {

    public static HomePendingFragment getInstance() {
//        return new HomePendingFragment().setMHomeOrderViewModle(homeOrderViewModle);
        return new HomePendingFragment();
    }

    @Override
    protected void getData() {

    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
