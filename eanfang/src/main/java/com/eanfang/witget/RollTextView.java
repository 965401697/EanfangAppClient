package com.eanfang.witget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.eanfang.R;

import java.util.List;


/**
 * 文本上下滚动效果
 */

public class RollTextView extends ViewFlipper {
    private Context mContext;
    private boolean isSetAnimDuration = false;
    private int interval = 2000;//间隔时间
    private int animDuration = 500;//动画时间


    public RollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs, 0);
    }

    /**
     * 初始化
     */
    private void init(Context context, AttributeSet attrs, int i) {
        this.mContext = context;
        setFlipInterval(interval);
        Animation animIn = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_in);
        if (isSetAnimDuration) {
            animIn.setDuration(animDuration);
        }
        setInAnimation(animIn);
        Animation animOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_out);
        if (isSetAnimDuration) {
            animOut.setDuration(animDuration);
        }
        setOutAnimation(animOut);
    }

    /**
     * 设置循环滚动的View数组
     */
    public void setViews(final List<View> views) {
        if (views == null || views.size() == 0) {
            return;
        }
        for (View view : views) {
            ViewGroup p = (ViewGroup) view.getParent();
            if (p != null) {
                p.removeAllViewsInLayout();
            }
        }
        for (int i = 0; i < views.size(); i++) {
            final int position = i;
            //设置监听回调
            views.get(i).setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, views.get(position));
                }
            });
            addView(views.get(i));

        }
        startFlipping();
    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }
}
