package com.eanfang.witget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by O u r on 2018/10/13.
 */

public class SwipeRefreshListenerLayout extends android.support.v4.widget.SwipeRefreshLayout {
    public SwipeRefreshListenerLayout(Context context) {
        super(context);
    }

    public SwipeRefreshListenerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        View target = getChildAt(0);
        if (target instanceof RecyclerView) {
            final RecyclerView recyclerView = (RecyclerView) target;
            return recyclerView.getChildCount() > 0
                    && (recyclerView.getChildAt(0).getVisibility() == View.VISIBLE || recyclerView.getChildAt(0)
                    .getTop() < recyclerView.getPaddingTop());
        } else
            return ViewCompat.canScrollVertically(target, -1);
    }
}

