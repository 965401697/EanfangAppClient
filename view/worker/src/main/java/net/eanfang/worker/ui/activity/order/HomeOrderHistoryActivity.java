package net.eanfang.worker.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivityHomeOrderHistoryBinding;
import net.eanfang.worker.ui.activity.worksapce.tender.FilterTenderActivity;
import net.eanfang.worker.ui.fragment.neworder.HistoryDesignFragment;
import net.eanfang.worker.ui.fragment.neworder.HistoryInstallFragment;
import net.eanfang.worker.ui.fragment.neworder.HistoryMaintenanceFragment;
import net.eanfang.worker.ui.fragment.neworder.HistoryRepairFragment;
import net.eanfang.worker.ui.fragment.neworder.HistoryTaskApplyFragment;
import net.eanfang.worker.viewmodle.neworder.HistoryOrderViewModle;

/**
 * @author guanluocang
 * @data 2019/10/29  18:17
 * @description 历史订单
 */

public class HomeOrderHistoryActivity extends BaseActivity {

    private ActivityHomeOrderHistoryBinding homeOrderHistoryBinding;

    private String[] mTitles = {"报修", "报装", "设计", "维保", "用工"};
    private MyPagerAdapter mAdapter;


    private final int FILTRATE_TYPE_CODE = 1001;
    private HistoryOrderViewModle historyOrderViewModle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        homeOrderHistoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_order_history);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setLeftBack(true);
        setTitle("历史订单");

        setRightClick("筛选", (v) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("type", 0);
            bundle.putString("order","orderHistory");
            JumpItent.jump(HomeOrderHistoryActivity.this, FilterTenderActivity.class, bundle, FILTRATE_TYPE_CODE);
        });
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mTitles);

        mAdapter.getFragments().add(HistoryRepairFragment.getInstance("repair", historyOrderViewModle));
        mAdapter.getFragments().add(HistoryInstallFragment.getInstance("install", historyOrderViewModle));
        mAdapter.getFragments().add(HistoryDesignFragment.getInstance("design", historyOrderViewModle));
        mAdapter.getFragments().add(HistoryMaintenanceFragment.getInstance("maintain", historyOrderViewModle));
        mAdapter.getFragments().add(HistoryTaskApplyFragment.getInstance("tender", historyOrderViewModle));
        homeOrderHistoryBinding.tlPerosonalList.setViewPager(homeOrderHistoryBinding.vpOrderList, mTitles, this, mAdapter.getFragments());
        homeOrderHistoryBinding.vpOrderList.setAdapter(mAdapter);
        homeOrderHistoryBinding.vpOrderList.setCurrentItem(0);
    }

    @Override
    protected ViewModel initViewModel() {
        historyOrderViewModle = LViewModelProviders.of(this, HistoryOrderViewModle.class);
        return historyOrderViewModle;
    }

    /**
     * 筛选回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int currentTab = homeOrderHistoryBinding.vpOrderList.getCurrentItem();
        if (data == null) {
            return;
        }
        if (resultCode == RESULT_OK && requestCode == FILTRATE_TYPE_CODE) {
            QueryEntry queryEntry = (QueryEntry) data.getSerializableExtra("query");
            if (queryEntry != null) {
                if (currentTab == 0) {
                    ((HistoryRepairFragment) mAdapter.getFragments().get(currentTab)).getTenderData(queryEntry);
                } else if (currentTab == 1) {
                    ((HistoryInstallFragment) mAdapter.getFragments().get(currentTab)).getTenderData(queryEntry);
                } else if (currentTab == 2) {
                    ((HistoryDesignFragment) mAdapter.getFragments().get(currentTab)).getTenderData(queryEntry);
                } else if (currentTab == 3) {
                    ((HistoryMaintenanceFragment) mAdapter.getFragments().get(currentTab)).getTenderData(queryEntry);
                } else if (currentTab == 4) {
                    ((HistoryTaskApplyFragment) mAdapter.getFragments().get(currentTab)).getTenderData(queryEntry);
                }
            }
        }
    }
}
