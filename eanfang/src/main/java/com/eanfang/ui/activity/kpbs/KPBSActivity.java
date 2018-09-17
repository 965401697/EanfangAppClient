package com.eanfang.ui.activity.kpbs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.ui.base.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KPBSActivity extends BaseActivity {

    @BindView(R2.id.tl_kpbs)
    SlidingTabLayout tlKpbs;
    @BindView(R2.id.vp_kpbs)
    ViewPager vpKpbs;
    private String[] mTitles = {"储存天数", "储存容量", "录像码率"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kpbs);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
        setTitle("录像计算");
        setLeftBack();
    }

    private void initView() {


        mFragments.add(KPBSDayFragment.getInstance("储存天数", 0));
        mFragments.add(KPBSDayFragment.getInstance("储存容量", 1));
        mFragments.add(KPBSDayFragment.getInstance("录像码率", 2));


        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpKpbs.setAdapter(mAdapter);
        tlKpbs.setViewPager(vpKpbs, mTitles, this, mFragments);
        vpKpbs.setCurrentItem(0);
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
