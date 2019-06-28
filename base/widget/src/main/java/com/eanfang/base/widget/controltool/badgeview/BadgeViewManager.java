package com.eanfang.base.widget.controltool.badgeview;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import q.rorbin.badgeview.QBadgeView;

/**
 * created by wtt
 * at 2019/4/25
 * summary:
 */
public class BadgeViewManager implements IBadgeView {
    private View targetView;
    private int badgeNum;
    private int color = 0xFFFF0000;
    private float padding = 2;
    private boolean isDpValue = true;
    private int gravity = Gravity.END | Gravity.TOP;
    private float offsetX = 0;
    private float offsetY = 3;
    private boolean isDpValueGravityOffset = true;
    private float size = 11;
    private boolean isSpValue = true;

    @Override
    public IBadgeView bindTarget(View targetView) {
        this.targetView = targetView;
        return this;
    }

    @Override
    public IBadgeView setBadgeNumber(int badgeNum) {
        this.badgeNum = badgeNum;
        return this;
    }

    @Override
    public IBadgeView setBadgeBackgroundColor(int color) {
        this.color = color;
        return this;
    }

    @Override
    public IBadgeView setBadgePadding(float padding, boolean isDpValue) {
        this.padding = padding;
        this.isDpValue = isDpValue;
        return this;
    }

    @Override
    public IBadgeView setBadgeGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    @Override
    public IBadgeView setGravityOffset(float offsetX, float offsetY, boolean isDpValue) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.isDpValueGravityOffset = isDpValue;
        return this;
    }

    @Override
    public IBadgeView setBadgeTextSize(float size, boolean isSpValue) {
        this.size = size;
        this.isSpValue = isSpValue;
        return this;
    }

    @Override
    public QBadgeView create(Context context) {
        QBadgeView qBadgeView = new QBadgeView(context);
        qBadgeView.bindTarget(targetView)
                .setBadgeNumber(badgeNum)
                .setBadgeBackgroundColor(color)
                .setBadgePadding(padding, isDpValue)
                .setBadgeGravity(gravity)
                .setGravityOffset(offsetX, offsetY, isDpValueGravityOffset)
                .setBadgeTextSize(size, isSpValue)
                .setShowShadow(false);
        return qBadgeView;
    }
}
