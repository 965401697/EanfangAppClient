package com.eanfang.controltool.flycotablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;


import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

/**
 * created by wtt
 * at 2019/4/24
 * summary:
 */
public interface IFlycoTabLayout {
    /** 关联ViewPager,用于连适配器都不想自己实例化的情况 */
    void setViewPager(ViewPager vp, String[] titles, FragmentActivity fa, ArrayList<Fragment> fragments,
                      SlidingTabLayout slidingTabLayout);
    void setOnTabSelectListener(SlidingTabLayout slidingTabLayout,IOnTabSelectListener listener);
}
