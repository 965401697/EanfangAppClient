package com.eanfang.base.widget.controltool.badgeview;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import q.rorbin.badgeview.QBadgeView;

public class MyBadgeView extends QBadgeView {
    private View targetView;
    private int badgeNum;
    private int color = 0xFFFF0000;
    private float padding = 2;
    private boolean isDpValue = true;
    private int gravity = Gravity.END | Gravity.TOP;
    private float offsetX = 5;
    private float offsetY = 3;
    private boolean isDpValueGravityOffset = true;
    private float size = 11;
    private boolean isSpValue = true;
    private static MyBadgeView myBadgeView;
    public MyBadgeView(Context context) {
        super(context);

    }
    public static MyBadgeView get(Context context){
        myBadgeView=new MyBadgeView(context);
        return myBadgeView;
    }

    public MyBadgeView setTargetView(View targetView) {
        this.targetView = targetView;
        return this;
    }

    public MyBadgeView setBadgeNum(int badgeNum) {
        this.badgeNum = badgeNum;
        return this;
    }

    public MyBadgeView setColor(int color) {
        this.color = color;
        return this;
    }

    public MyBadgeView setPadding(float padding) {
        this.padding = padding;
        return this;
    }

    public MyBadgeView setDpValue(boolean dpValue) {
        isDpValue = dpValue;
        return this;
    }

    public MyBadgeView setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public MyBadgeView setOffset(float offsetX,float offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        return this;
    }

    public MyBadgeView setDpValueGravityOffset(boolean dpValueGravityOffset) {
        isDpValueGravityOffset = dpValueGravityOffset;
        return this;
    }

    public MyBadgeView setSize(float size) {
        this.size = size;
        return this;
    }

    public MyBadgeView setSpValue(boolean spValue) {
        isSpValue = spValue;
        return this;
    }

    public void badge() {
                bindTarget(targetView);
                setBadgeNumber(badgeNum);
                setBadgeBackgroundColor(color);
                setBadgePadding(padding, isDpValue);
                setBadgeGravity(gravity);
                setGravityOffset(offsetX, offsetY, isDpValueGravityOffset);
                setBadgeTextSize(size, isSpValue);
                setShowShadow(false);
    }
}
