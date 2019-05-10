package net.eanfang.worker.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import net.eanfang.worker.R;


/**
 * Created by WQ on 2019/4/8.
 *
 * @描述 ${TODO TextView 左右图标点击事件}
 *
 */

@SuppressLint("AppCompatCustomView")
public class WQLeftRightClickTextView extends TextView {
    private DrawableLeftListener mLeftListener;
    private DrawableRightListener mRightListener;
    private int imageWidth;
    private int imageHeight;

    private Drawable leftImage;
    private Drawable topImage;
    private Drawable rightImage;
    private Drawable bottomImage;
    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;

    public WQLeftRightClickTextView(Context context) {
        this(context, null);
    }

    public WQLeftRightClickTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WQLeftRightClickTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WQLeftRightClickTextView, 0, 0);
        int countNum = ta.getIndexCount();
        for (int i = 0; i < countNum; i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.WQLeftRightClickTextView_tvLeftImage) {
                leftImage = ta.getDrawable(attr);
            } else if (attr == R.styleable.WQLeftRightClickTextView_tvTopImage) {
                topImage = ta.getDrawable(attr);
            } else if (attr == R.styleable.WQLeftRightClickTextView_tvRightImage) {
                rightImage = ta.getDrawable(attr);
            } else if (attr == R.styleable.WQLeftRightClickTextView_tvBottomImage) {
                bottomImage = ta.getDrawable(attr);
            } else if (attr == R.styleable.WQLeftRightClickTextView_tvImageWidth) {
                imageWidth = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.WQLeftRightClickTextView_tvImageHeight) {
                imageHeight = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
            }
        }
        ta.recycle();
        setCompoundDrawablesWithIntrinsicBounds(leftImage, topImage, rightImage, bottomImage);
    }

    @SuppressLint("NewApi")
    public WQLeftRightClickTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context,attrs,defStyleAttr,defStyleRes);
    }

    public void setDrawableLeftListener(DrawableLeftListener listener) {
        this.mLeftListener = listener;
    }

    public void setDrawableRightListener(DrawableRightListener listener) {
        this.mRightListener = listener;
    }

    public interface DrawableLeftListener {
        public void onDrawableLeftClick(View view);
    }

    public interface DrawableRightListener {
        public void onDrawableRightClick(View view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (mRightListener != null) {
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())) {
                        mRightListener.onDrawableRightClick(this);
                        return true;
                    }
                }

                if (mLeftListener != null) {
                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                    if (drawableLeft != null && event.getRawX() <= (getLeft() + drawableLeft.getBounds().width())) {
                        mLeftListener.onDrawableLeftClick(this);
                        return true;
                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {

        if (left != null) {
            left.setBounds(0, 0, imageWidth, imageHeight);
        }

        if (top != null) {
            top.setBounds(0, 0, imageWidth, imageHeight);
        }

        if (right != null) {
            right.setBounds(0, 0, imageWidth/2, imageHeight);
        }

        if (bottom != null) {
            bottom.setBounds(0, 0, imageWidth, imageHeight);
        }

        setCompoundDrawables(left, top, right, bottom);
    }
}
