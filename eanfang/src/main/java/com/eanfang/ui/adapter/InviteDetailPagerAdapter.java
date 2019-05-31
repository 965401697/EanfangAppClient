package com.eanfang.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.eanfang.ui.fragment.InviteDetailFragment;

import java.util.List;

/**
 * @author liangkailun
 * Date ：2019/5/13
 * Describe :
 */
public class InviteDetailPagerAdapter extends FragmentPagerAdapter {


    private final List<InviteDetailFragment> mFragments;

    public InviteDetailPagerAdapter(FragmentManager fm, List<InviteDetailFragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //没有找到child要求重新加载
        return POSITION_NONE;
    }
}
