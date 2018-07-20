package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaintenanceListActivity extends BaseWorkerActivity {

    @BindView(R.id.tl_2)
    SlidingTabLayout tl2;
    @BindView(R.id.vp)
    ViewPager vp;

    private String[] mTitles = {"待预约", "待上门", "维修中", "待验收", "订单完成"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_list);
        ButterKnife.bind(this);

        int type = getIntent().getIntExtra("type", 0);

        for (String title : mTitles) {
            mFragments.add(MaintenanceListFragment.getInstance(type, title));
        }

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        vp.setCurrentItem(0);
        tl2.setViewPager(vp, mTitles, this, mFragments);

        setTitle("维保订单");
        setLeftBack();
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
