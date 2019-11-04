package net.eanfang.worker.ui.activity.order;

import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.viewpager.widget.ViewPager;

import com.eanfang.base.BaseActivity;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivityHomeOrderHistoryBinding;
import net.eanfang.worker.ui.fragment.neworder.HistoryInstallFragment;
import net.eanfang.worker.ui.fragment.neworder.HistoryRepairFragment;

/**
 * @author guanluocang
 * @data 2019/10/29  18:17
 * @description 历史订单
 */

public class HomeOrderHistoryActivity extends BaseActivity {

    private ActivityHomeOrderHistoryBinding homeOrderHistoryBinding;

    private String[] mTitles = {"待处理", "进行中"};
    private MyPagerAdapter mAdapter;

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

        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mTitles);
        mAdapter.getFragments().add(HistoryRepairFragment.getInstance());
        mAdapter.getFragments().add(HistoryInstallFragment.getInstance());
        homeOrderHistoryBinding.vpOrderList.setAdapter(mAdapter);
        homeOrderHistoryBinding.vpOrderList.setCurrentItem(0);

        initListener();
    }

    private void initListener() {
        homeOrderHistoryBinding.vpOrderList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        homeOrderHistoryBinding.tvPending.setTextColor(ContextCompat.getColor(HomeOrderHistoryActivity.this, R.color.color_white));
                        homeOrderHistoryBinding.tvHaveIn.setTextColor(ContextCompat.getColor(HomeOrderHistoryActivity.this, R.color.color_new_order_unselect));
                        homeOrderHistoryBinding.viewPending.setVisibility(View.VISIBLE);
                        homeOrderHistoryBinding.viewHaveIn.setVisibility(View.GONE);
                        break;
                    case 1:
                        homeOrderHistoryBinding.tvPending.setTextColor(ContextCompat.getColor(HomeOrderHistoryActivity.this, R.color.color_new_order_unselect));
                        homeOrderHistoryBinding.tvHaveIn.setTextColor(ContextCompat.getColor(HomeOrderHistoryActivity.this, R.color.color_white));
                        homeOrderHistoryBinding.viewPending.setVisibility(View.GONE);
                        homeOrderHistoryBinding.viewHaveIn.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
