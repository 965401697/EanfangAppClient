package com.eanfang.listener;


import android.app.Activity;
import android.view.View;

import com.eanfang.util.ToastUtil;

/**
 * Created by jornl on 2017/9/23.
 * 限制两次点击事件间隔在 30秒以上
 */

public class MultiClickListener implements View.OnClickListener {
    // 两次点击按钮之间的点击间隔不能少于30*1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 30 * 1000;
    private long lastClickTime = 0;

    private Activity activity;
    private Before before;
    private MultiClick multiClick;

    /**
     * 判断30秒内是否重复点击
     *
     * @param activity   当前activity
     * @param multiClick 验证通过执行方法
     */
    public MultiClickListener(Activity activity, MultiClick multiClick) {
        this.activity = activity;
        this.multiClick = multiClick;
    }

    /**
     * 判断30秒内是否重复点击
     *
     * @param activity   当前activity
     * @param before     执行验证前方法
     * @param multiClick 验证通过执行方法
     */
    public MultiClickListener(Activity activity, Before before, MultiClick multiClick) {
        this.activity = activity;
        this.before = before;
        this.multiClick = multiClick;
    }

    @Override
    public final void onClick(View v) {
        if (before != null && !before.onBefore()) {
            return;
        }
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = curClickTime;
            multiClick.onMultiClick();
            return;
        }
        //如果重复点击
        ToastUtil.get().showToast(activity, "请勿重复提交，30秒后重试");
    }


    public interface Before {
        boolean onBefore();
    }

    public interface MultiClick {
        void onMultiClick();
    }

}
