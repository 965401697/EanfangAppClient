package net.eanfang.worker.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by Administrator on 2016/12/1.
 * 自定义viewpager,添加是否能滑动方法
 */

public class MyViewpager extends ViewPager {
    private boolean scrollble = true;

    public MyViewpager(Context context) {
        super(context);
    }

    public MyViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!scrollble) {
            return true;
        }
        return super.onTouchEvent(ev);
    }


    public boolean isScrollble() {
        return scrollble;
    }
    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }
}
