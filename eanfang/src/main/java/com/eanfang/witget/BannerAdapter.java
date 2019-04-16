package com.eanfang.witget;

import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/03/23  09:07
 * @email houzhongzhou@yeah.net
 * @desc 轮播控件adapter
 */
public class BannerAdapter extends PagerAdapter {

    private List<View> viewList;
    private int size;
    private final int cacheCount = 3;

    public BannerAdapter(List<View> viewList) {
        this.viewList = viewList;
        size = viewList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (viewList.size() > cacheCount){
            container.removeView(viewList.get(position%size));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup parent = (ViewGroup) viewList.get(position%size).getParent();
        if (parent != null) {
            parent.removeView(viewList.get(position%size));
        }
        container.addView(viewList.get(position%size));
        return viewList.get(position%size);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
