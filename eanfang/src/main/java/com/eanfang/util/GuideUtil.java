/*
 * Copyright (c) 2016. Huangrui All rights reserved.
 */

package com.eanfang.util;

import android.animation.ArgbEvaluator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;

import com.eanfang.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


/**
 * Created by YOLO on 2016/6/27.
 */
public class GuideUtil {
    static OnCallback mOnCallback;
    final float PARALLAX_COEFFICIENT = 1.2f;
    final float DISTANCE_COEFFICIENT = 0.5f;
    Activity mContext;
    TextSwitcher mTextSwitcher;
    ViewPager mPager;

    SparseArray<int[]> mLayoutViewIdsMap = new SparseArray<>();
    FragmentAdapter mAdapter;

    public void init(Activity activity, ViewGroup parent, int[] guides, OnCallback onCallback) {
        this.mContext = activity;

        mOnCallback = onCallback;
        View view = LayoutInflater.from(mContext).inflate(R.layout.j_view_pager, null);
        parent.addView(view);
        mPager = view.findViewById(R.id.pager);
        mAdapter = new FragmentAdapter(((FragmentActivity) activity).getSupportFragmentManager());
        int index = 0;
        for (int i : guides) {
            index++;
            MyFragment fragment = MyFragment.newInstance(index == guides.length, i);
            mAdapter.addItem(fragment);
            mLayoutViewIdsMap.put(fragment.getRootViewId(), fragment.getChildViewIds());
        }
        mPager.setAdapter(mAdapter);

        mPager.setPageTransformer(true, new ParallaxTransformer(PARALLAX_COEFFICIENT, DISTANCE_COEFFICIENT));
        mPager.setOnPageChangeListener(new GuidePageChangeListener());

    }

    public interface OnCallback {
        void goLogin();
    }

    public static class MyFragment extends Fragment {
        boolean last;
        int resource;

        public static MyFragment newInstance(boolean last, int i) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("last", last);
            bundle.putInt("resource", i);
            MyFragment instance = new MyFragment();
            instance.setArguments(bundle);
            return instance;
        }

        public int getRootViewId() {
            return R.id.guide;
        }

        public int[] getChildViewIds() {
            return new int[]{R.id.button1};
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.j_view_guide, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            last = getArguments().getBoolean("last");
            resource = getArguments().getInt("resource");
            getView().setBackgroundResource(resource);

            if (last) {
                view.findViewById(R.id.button1).setVisibility(View.VISIBLE);
                view.findViewById(R.id.button1).setOnClickListener((v) -> {
                    if (mOnCallback != null) {
                        mOnCallback.goLogin();
                    }
                });

            }
        }
    }

    class ParallaxTransformer implements ViewPager.PageTransformer {

        float parallaxCoefficient;
        float distanceCoefficient;

        public ParallaxTransformer(float parallaxCoefficient, float distanceCoefficient) {
            this.parallaxCoefficient = parallaxCoefficient;
            this.distanceCoefficient = distanceCoefficient;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void transformPage(View page, float position) {
            float scrollXOffset = page.getWidth() * parallaxCoefficient;

            ViewGroup pageViewWrapper = (ViewGroup) page;
            @SuppressWarnings("SuspiciousMethodCalls")
            int[] layer = mLayoutViewIdsMap.get(pageViewWrapper.getChildAt(0).getId());
            if (layer == null) {
                return;
            }
            for (int id : layer) {
                View view = page.findViewById(id);
                if (view != null) {
                    view.setTranslationX(scrollXOffset * position);
                }
                scrollXOffset *= distanceCoefficient;
            }
        }
    }

    public class FragmentAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment fragment) {
            fragments.add(fragment);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }

    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        ArgbEvaluator mColorEvaluator;

        int mPageWidth, mTotalScrollWidth;

        int mGuideStartBackgroundColor, mGuideEndBackgroundColor;

        String[] mGuideTips;

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public GuidePageChangeListener() {
            mColorEvaluator = new ArgbEvaluator();

            mPageWidth = mContext.getWindowManager().getDefaultDisplay().getWidth();
            mTotalScrollWidth = mPageWidth * mAdapter.getCount();
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            float ratio = (mPageWidth * position + positionOffsetPixels) / (float) mTotalScrollWidth;
            Integer color = (Integer) mColorEvaluator.evaluate(ratio, mGuideStartBackgroundColor, mGuideEndBackgroundColor);
            mPager.setBackgroundColor(color);
        }

        @Override
        public void onPageSelected(int position) {
            if (mGuideTips != null && mGuideTips.length > position) {
                mTextSwitcher.setText(mGuideTips[position]);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

}
