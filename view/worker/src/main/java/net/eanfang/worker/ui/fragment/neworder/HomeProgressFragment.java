package net.eanfang.worker.ui.fragment.neworder;

import androidx.lifecycle.ViewModel;

import net.eanfang.worker.ui.adapter.neworder.HomeOrderApdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;
import net.eanfang.worker.viewmodle.neworder.HomeOrderViewModle;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/10/29  16:02
 * @description 进行中订单
 */
public class HomeProgressFragment extends TemplateItemListFragment {
    @Setter
    @Accessors(chain = true)
    private HomeOrderViewModle mHomeOrderViewModle;
    private HomeOrderApdapter homeOrderApdapter;
    private List<String> mProgressList = new ArrayList<>();

    public static HomeProgressFragment getInstance(HomeOrderViewModle homeOrderViewModle) {
        return new HomeProgressFragment().setMHomeOrderViewModle(homeOrderViewModle);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    protected void getData() {
        homeOrderApdapter = new HomeOrderApdapter();
        mProgressList.add("1");
        mProgressList.add("1");
        mProgressList.add("1");
        mProgressList.add("1");
        mProgressList.add("1");
        homeOrderApdapter.setNewData(mProgressList);
    }
}
