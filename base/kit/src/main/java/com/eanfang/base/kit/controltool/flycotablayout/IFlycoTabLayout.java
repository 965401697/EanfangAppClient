package com.eanfang.base.kit.controltool.flycotablayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

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
