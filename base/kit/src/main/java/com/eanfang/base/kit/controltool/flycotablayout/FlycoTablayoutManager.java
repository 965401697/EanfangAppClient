package com.eanfang.base.kit.controltool.flycotablayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

/**
 * created by wtt
 * at 2019/4/24
 * summary:
 */
public class FlycoTablayoutManager implements IFlycoTabLayout {
    private IOnTabSelectListener iOnTabSelectListener;

    public IOnTabSelectListener getiOnTabSelectListener() {
        return iOnTabSelectListener;
    }

    public void setiOnTabSelectListener(IOnTabSelectListener iOnTabSelectListener) {
        this.iOnTabSelectListener = iOnTabSelectListener;
    }

    @Override
    public void setViewPager(ViewPager vp, String[] titles, FragmentActivity fa, ArrayList<Fragment> fragments,
                             SlidingTabLayout slidingTabLayout) {
        slidingTabLayout.setViewPager(vp, titles, fa, fragments);
    }

    @Override
    public void setOnTabSelectListener(SlidingTabLayout slidingTabLayout, IOnTabSelectListener listener) {
        setiOnTabSelectListener(listener);
        slidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(getiOnTabSelectListener()!=null)
                getiOnTabSelectListener().onTabSelect(position);
            }

            @Override
            public void onTabReselect(int position) {
                getiOnTabSelectListener().onTabReselect(position);
            }
        });

    }
}
