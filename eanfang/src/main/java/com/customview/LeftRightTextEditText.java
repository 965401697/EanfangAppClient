package com.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;


/**
 * @author WQ 左右显示文字的EditText
 */
public class LeftRightTextEditText extends android.support.v7.widget.AppCompatEditText {
    private String leftText;
    private String rightText;
    private int leftPadding;
    private int rightPadding;

    public LeftRightTextEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLeftText(String leftString) {
        leftText = leftString;
        leftPadding = getPaddingLeft();
        int left = (int) getPaint().measureText(leftText) + leftPadding;
        setPadding(left, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        invalidate();
    }

    public void setRightText(String rightString) {
        rightText = rightString;
        rightPadding = getPaddingRight();
        int right = (int) getPaint().measureText(rightText) + rightPadding;
        setPadding(getPaddingLeft(), getPaddingTop(), right, getPaddingBottom());
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(leftText)) {
            canvas.drawText(leftText, leftPadding, getBaseline(), getPaint());
        }
        if (!TextUtils.isEmpty(rightText)) {
            canvas.drawText(rightText, getWidth() - (getPaint().measureText(rightText) + rightPadding), getBaseline(), getPaint());
        }
    }

}