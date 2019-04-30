package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaintenanceDetailActivity extends BaseWorkerActivity {

    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private final String[] mTitles = {
            "订单详情", "订单状态"
    };
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_detail2);
        ButterKnife.bind(this);
        setTitle("维保详情");
        setLeftBack();


       long id = getIntent().getLongExtra("id", 0);
//        mOrderTime = getIntent().getStringExtra("orderTime");

        mFragments.add(MaintenanceOrderDetailFragment.newInstance(id));
        mFragments.add(MaintenanceOrderStatusFragment.newInstance(id));

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        tabLayout.setViewPager(viewPager, mTitles, this, mFragments);
        viewPager.setCurrentItem(0);

    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];

        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
