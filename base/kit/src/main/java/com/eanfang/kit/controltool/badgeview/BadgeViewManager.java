package com.eanfang.kit.controltool.badgeview;

import android.content.Context;
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
    private int color;
    private float padding;
    private boolean isDpValue;
    private int gravity;
    private float offsetX;
    private float offsetY;
    private boolean isDpValueGravityOffset;
    private float size;
    private boolean isSpValue;
    @Override
    public IBadgeView bindTarget(View targetView) {
        this.targetView=targetView;
        return this;
    }

    @Override
    public IBadgeView setBadgeNumber(int badgeNum) {
        this.badgeNum=badgeNum;
        return this;
    }

    @Override
    public IBadgeView setBadgeBackgroundColor(int color) {
        this.color=color;
        return this;
    }

    @Override
    public IBadgeView setBadgePadding(float padding, boolean isDpValue) {
        this.padding=padding;
        this.isDpValue=isDpValue;
        return this;
    }

    @Override
    public IBadgeView setBadgeGravity(int gravity) {
        this.gravity=gravity;
        return this;
    }

    @Override
    public IBadgeView setGravityOffset(float offsetX, float offsetY, boolean isDpValue) {
        this.offsetX=offsetX;
        this.offsetY=offsetY;
        this.isDpValueGravityOffset=isDpValue;
        return this;
    }

    @Override
    public IBadgeView setBadgeTextSize(float size, boolean isSpValue) {
        this.size=size;
        this.isSpValue=isSpValue;
        return this;
    }

    @Override
    public void create(Context context) {
        QBadgeView qBadgeView=new QBadgeView(context);
        qBadgeView.bindTarget(targetView)
                .setBadgeNumber(badgeNum)
                .setBadgeBackgroundColor(color)
                .setBadgePadding(padding, isDpValue)
                .setBadgeGravity(gravity)
                .setGravityOffset(offsetX, offsetY, isDpValueGravityOffset)
                .setBadgeTextSize(size, isSpValue);
    }
}
