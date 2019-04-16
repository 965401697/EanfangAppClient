package com.eanfang.witget.recycleview;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/6/7$  11:46$
 */
public class NestRecyclerView extends RecyclerView {

    public NestRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayoutManager(new LinearLayoutManager(context));
        setNestedScrollingEnabled(false);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int newHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthSpec, newHeightSpec);
    }
}
