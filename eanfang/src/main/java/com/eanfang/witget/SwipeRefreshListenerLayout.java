package com.eanfang.witget;

import android.content.Context;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.AttributeSet;
import android.view.View;

/**
 * Created by O u r on 2018/10/13.
 */

public class SwipeRefreshListenerLayout extends SwipeRefreshLayout {
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
        } else {
            return ViewCompat.canScrollVertically(target, -1);
        }
    }
}

