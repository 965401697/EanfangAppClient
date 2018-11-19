package com.eanfang.witget;

import android.view.Gravity;
import android.view.View;

import com.eanfang.application.EanfangApplication;

import q.rorbin.badgeview.QBadgeView;

/**
 * @author guanluocang
 * @data 2018/10/30
 * @description
 */

public class SetQBadgeView {

    private QBadgeView qBadgeViewSys;

    private View mView;

    private SetQBadgeView() {
        qBadgeViewSys = new QBadgeView(EanfangApplication.get().getApplicationContext());
    }

    public static SetQBadgeView getSingleton() {
        return SetQBadgeView.SingletonHolder.singleton;
    }

    public static class SingletonHolder {
        private static final SetQBadgeView singleton = new SetQBadgeView();
    }


    public void setBadgeView(View weidgt, int num) {
        this.mView = weidgt;
        qBadgeViewSys.bindTarget(mView)
                .setBadgeNumber(num)
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setShowShadow(false)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(11, true);
    }


}
