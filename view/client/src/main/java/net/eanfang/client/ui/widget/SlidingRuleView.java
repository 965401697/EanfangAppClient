package net.eanfang.client.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import androidx.core.content.ContextCompat;

import net.eanfang.client.R;

import java.text.DecimalFormat;

/**
 * @author guanluocang
 * @data 2019/9/27
 * @description 实时监控设备详情 时间轴
 */
public class SlidingRuleView extends View {

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 自定义view的高度
     */
    private int mViewHeight;

    /**
     * 滑动实例
     */
    private Scroller mScroller;
    /**
     * 界面可滚动的左右边界
     */
    private float mLeftOrder;
    private float mRightOrder;
    /**
     * 手指最开始触摸屏幕的X坐标
     */
    private float mXDown;
    /**
     * 上次触发移动事件的坐标
     */
    private float mLastMoveX;
    /**
     * 当前手指移动结束后停下来的X坐标
     */
    private float mCurrentMoveX;
    /**
     * 长刻度线长度
     */
    private float longDegreeLine;
    private float shortDegreeLine;
    /**
     * 刻度线的颜色
     */
    private int timeLineDegreeColor;
    /**
     * 刻度顶部线
     */
    private float topDegreeLine;
    /**
     * 刻度的间隔
     */
    private float lineDegreeSpace;
    /**
     * 刻度大数目
     */
    private int lineCount;

    /**
     * 最小移动距离
     */
    private int mTouchMinDistance;
    /**
     * 左右边界距离
     */
    private float ruleLeftSpacing;
    private float ruleRightSpacing;
    /**
     * 数字颜色
     */
    private int numberColor;
    /**
     * 数字文字大小
     */
    private float numberSize;
    /**
     * 绿色指针粗细
     */
    private float greenPointWidth;
    /**
     * 绿色指针颜色
     */
    private int greenPointColor;

    /**
     * 绿色指针X坐标
     */
    private int greenPointX;
    /**
     * 监控手势速度类
     */
    private VelocityTracker mVelocityTracker;
    //惯性最大最小速度
    protected int mMaximumVelocity, mMinimumVelocity;
    /**
     * 格式化数字
     */
    private DecimalFormat decimalFormat;
    /**
     * 获取到的值
     */
    private String mValue;

    private DoGetValueListener doGetValueListener = null;
    private boolean isFirst = true;

    public SlidingRuleView(Context context) {
        this(context, null);
    }

    public SlidingRuleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingRuleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化参数
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingRuleView);

        // 刻度线的颜色
        timeLineDegreeColor = typedArray.getColor(R.styleable.SlidingRuleView_lineDegreeColor, ContextCompat.getColor(getContext(), R.color.color_monitor_text_back));
        //顶部的直线距离View顶部距离
//        topDegreeLine = typedArray.getDimension(R.styleable.SlidingRuleView_topDegreeLine, dp2px(getContext(), 45));
        //刻度间隔
        lineDegreeSpace = typedArray.getDimension(R.styleable.SlidingRuleView_lineDegreeSpace, dp2px(getContext(), 10));
        //刻度大数目 默认30
        lineCount = typedArray.getInt(R.styleable.SlidingRuleView_lineCount, 12);
        //长的刻度线条长度
        longDegreeLine = typedArray.getDimension(R.styleable.SlidingRuleView_longDegreeLine, dp2px(getContext(), 25));
        //短刻度值的长度
        shortDegreeLine = typedArray.getDimension(R.styleable.SlidingRuleView_shortDegreeLine, dp2px(getContext(), 5));
        //左右边界的刻度线 间隔
        ruleLeftSpacing = typedArray.getDimension(R.styleable.SlidingRuleView_ruleLeftSpacing, dp2px(getContext(), 10));
        ruleRightSpacing = typedArray.getDimension(R.styleable.SlidingRuleView_ruleRightSpacing, dp2px(getContext(), 10));
        //数字颜色
        numberColor = typedArray.getColor(R.styleable.SlidingRuleView_numberColor, ContextCompat.getColor(getContext(), R.color.color_monitor_text));
        //数字大小
        numberSize = typedArray.getDimension(R.styleable.SlidingRuleView_numberSize, dp2px(getContext(), 15));
        //绿色指针粗细
        greenPointWidth = typedArray.getDimension(R.styleable.SlidingRuleView_greenPointWidth, dp2px(getContext(), 4));
        //绿色指针颜色
        greenPointColor = typedArray.getColor(R.styleable.SlidingRuleView_greenPointColor, 0xFF4FBA75);
        //当前刻度的颜色
//        currentNumberColor = typedArray.getColor(R.styleable.SlidingRuleView_currentNumberColor, 0xFF4FBA75);
        //当前刻度的大小
//        currentNumberSize = typedArray.getDimension(R.styleable.SlidingRuleView_currentNumberSize, dp2px(getContext(), 30));
        init(context);
        typedArray.recycle();
    }


    private void init(Context context) {
        // 抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 创建滑动实例
        mScroller = new Scroller(context);

//        //第一步，获取Android常量距离对象，这个类有UI中所使用到的标准常量，像超时，尺寸，距离
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
//        //获取最小移动距离
        mTouchMinDistance = viewConfiguration.getScaledTouchSlop();
        //增加左边界距离
        mLeftOrder = ruleLeftSpacing;
        //确定刻顶部度长线右边界 格数 * 之间的间隔 * 大数目（间隔）之间是有10小间隔的
        mRightOrder = lineDegreeSpace * lineCount * 5 + ruleLeftSpacing + ruleRightSpacing;
        //添加速度追踪器
        mVelocityTracker = VelocityTracker.obtain();
        //获取最大速度
        mMaximumVelocity = ViewConfiguration.get(context)
                .getScaledMaximumFlingVelocity();
        //获取最小速度
        mMinimumVelocity = ViewConfiguration.get(context)
                .getScaledMinimumFlingVelocity();

        //数字小数点一位
        decimalFormat = new DecimalFormat("0.0");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // MeasureSpec值由specMode和specSize共同组成，onMeasure两个参数的作用根据specMode的不同，有所区别。
        // 当specMode为EXACTLY时 子视图的大小会根据specSize的大小来设置，对于布局参数中的match_parent或者精确大小值
        //  当specMode为AT_MOST时，这两个参数只表示了子视图当前可以使用的最大空间大小 而子视图的实际大小不一定是specSize。
        //  所以我们自定义View时，重写onMeasure方法主要是在AT_MOST模式时，为子视图设置一个默认的大小，对应布局参数wrap_content

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, dp2px(getContext(), 60));
        } else {
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }
        // 获取View的高度 方便后面绘制计算一些坐标
        mViewHeight = getMeasuredHeight();
        //绿色指针的x坐标
        greenPointX = getMeasuredWidth() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //确定顶部长线的左端
        float x = mLeftOrder;
        //确定顶部长线
//        float y = topDegreeLine;
        //设置画笔颜色
        mPaint.setColor(timeLineDegreeColor);
        //设置刻度线宽度
        mPaint.setStrokeWidth(3);
//        canvas.drawLine(x, y, mRightOrder, y, mPaint);

        //循环绘制长刻度
        for (int i = 0; i <= lineCount * 5; i++) {
            //画长刻度
            if (i % 5 == 0) {
                mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_monitor_text));
                mPaint.setStrokeWidth(6);
                canvas.drawLine(x, 0, x, 0 + longDegreeLine, mPaint);
                //画刻度值
                String number = i + "分";
                //得到文字宽度
                float textWidth = mPaint.measureText(number);
                //绘制颜色
                mPaint.setColor(numberColor);
                //绘制文字大小
                mPaint.setTextSize(numberSize);
                mPaint.setStrokeWidth(1);
                canvas.drawText(number, x - textWidth / 2, longDegreeLine + dp2px(getContext(), 15), mPaint);
            } else {
                //画短刻度
                mPaint.setColor(timeLineDegreeColor);
                mPaint.setStrokeWidth(3);
                canvas.drawLine(x, 0, x, shortDegreeLine, mPaint);
            }
            x += lineDegreeSpace;
        }
        //画指针
        mPaint.setColor(greenPointColor);
        mPaint.setStrokeWidth(greenPointWidth);
        canvas.drawLine(greenPointX + getScrollX(), 0, greenPointX + getScrollX(), longDegreeLine + dp2px(getContext(), 3),
                mPaint);
        mValue = decimalFormat.format((greenPointX + getScrollX() - mLeftOrder) / lineDegreeSpace);
        if (doGetValueListener != null) {
            doGetValueListener.doGetValue(mValue);
        }
        if (isFirst) {
            scrollBy(0, 0);
            isFirst = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录初始触摸屏幕下的坐标
                mXDown = event.getRawX();
                mLastMoveX = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mCurrentMoveX = event.getRawX();
                // 本次滑动的距离
                //本次的滑动距离
                int scrolledX = (int) (mLastMoveX - mCurrentMoveX);
                //左右边界中 自由滑动
                scrollBy(scrolledX, 0);
                //当停止滑动时，现在的滑动已经变成上次滑动
                mLastMoveX = mCurrentMoveX;
                break;
            case MotionEvent.ACTION_UP:
                //处理松手后的Fling 获取当前事件的速率，1毫秒运动了多少个像素的速率，1000表示一秒
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                //获取横向速率
                int velocityX = (int) mVelocityTracker.getXVelocity();
                //滑动速度大于最小速度 就滑动
                if (Math.abs(velocityX) > mMinimumVelocity) {
                    fling(-velocityX);
                }
                moveRecently();
                break;
            case MotionEvent.ACTION_CANCEL:
//                if (mVelocityTracker != null) {
//                    mVelocityTracker.recycle();
//                    mVelocityTracker = null;
//                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 当用户手指快速划过屏幕，手指快速离开屏幕时，系统会判定用户执行一个Fling手势，视图会快速滚动，并且在手指离开屏幕之后也会滚动一定时间。
     */
    private void fling(int vX) {
        mScroller.fling(getScrollX(), 0, vX, 0, (int) (-mRightOrder), (int) mRightOrder, 0, 0);
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }

    private void moveRecently() {
        float distance = (greenPointX + getScrollX() - mLeftOrder) % lineDegreeSpace;
        //指针的位置在小刻度中间位置往后（右）
        if (distance >= lineDegreeSpace / 2) {
            scrollBy((int) (lineDegreeSpace - distance), 0);
        } else {
            scrollBy((int) (-distance), 0);
        }
    }

    /**
     * 在fling或者startScroll方法，调用invalidate方法后执行的函数，并在里面增加刻度边界的检测，完成平滑移动
     */
    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //这是最后mScroller的最后一次滑动 进行刻度边界检测
            if (!mScroller.computeScrollOffset()) {
                moveRecently();
            }
        }

    }

    @Override
    public void scrollTo(int x, int y) {
        //左边界检测
        if (x < mLeftOrder - getWidth() / 2) {
            x = (int) (-getWidth() / 2 + mLeftOrder);
        }
        //有边界检测
        if (x + getWidth() / 2 > mRightOrder) {
            x = (int) (mRightOrder - getWidth() / 2 - ruleRightSpacing);
        }
        if (x != getScrollX()) {

            super.scrollTo(x, y);
        }

    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    public void setOnScrollListener(DoGetValueListener listener) {
        doGetValueListener = listener;
    }

    public interface DoGetValueListener {
        void doGetValue(String mValue);
    }
}
