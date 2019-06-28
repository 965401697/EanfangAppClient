package com.eanfang.sdk;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eanfang.R;
import com.eanfang.base.network.config.HttpConfig;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

public class MySlidingTabLayout extends SlidingTabLayout {
    private ArrayList<Fragment> fragments;
    private String[] titles;
    private Context context;
    public MySlidingTabLayout(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public MySlidingTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public MySlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    public void init(){
        setBackgroundColor(context.getResources().getColor(R.color.color_white));
        setDividerColor(context.getResources().getColor(R.color.color_service_deiveder));
        setDividerWidth(1f);
        switch (HttpConfig.get().getApp()) {
            case 0:
                setIndicatorColor(context.getResources().getColor(R.color.colorPrimaryC));
                setTextSelectColor(context.getResources().getColor(R.color.colorPrimaryC));
                break;
            case 1:
                setIndicatorColor(context.getResources().getColor(R.color.colorPrimaryW));
                setTextSelectColor(context.getResources().getColor(R.color.colorPrimaryW));
                break;
        }
        setIndicatorHeight(3f);
        setTabPadding(1f);
        setTabSpaceEqual(true);
        setTextUnselectColor(Color.parseColor("#84889A"));
        setTextsize(14f);
        setUnderlineColor(Color.parseColor("#1A000000"));
        setUnderlineHeight(1f);

    }
    public void setViewPager(ViewPager viewPager, String[] titles, FragmentActivity fragmentActivity, ArrayList<Fragment> fragments){
        this.fragments=fragments;
        this.titles=titles;
        MyPagerAdapter   mAdapter = new MyPagerAdapter(fragmentActivity.getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        // 设置不可滑动
//        viewPager.setScanScroll(false);
        super.setViewPager(viewPager, titles, fragmentActivity, fragments);
        viewPager.setCurrentItem(0);
        setCurrentTab(0);
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }
}
