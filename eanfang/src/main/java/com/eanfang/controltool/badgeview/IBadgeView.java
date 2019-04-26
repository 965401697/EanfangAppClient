package com.eanfang.controltool.badgeview;

import android.content.Context;
import android.view.View;

import q.rorbin.badgeview.QBadgeView;


/**
 * created by wtt
 * at 2019/4/25
 * summary:
 */
public interface IBadgeView {
    IBadgeView bindTarget(final View targetView);
    IBadgeView setBadgeNumber(int badgeNum);
    IBadgeView setBadgeBackgroundColor(int color);
    IBadgeView setBadgePadding(float padding, boolean isDpValue);
    IBadgeView setBadgeGravity(int gravity);
    IBadgeView setGravityOffset(float offsetX, float offsetY, boolean isDpValue);
    IBadgeView setBadgeTextSize(float size, boolean isSpValue);
    void create(Context context);
}
