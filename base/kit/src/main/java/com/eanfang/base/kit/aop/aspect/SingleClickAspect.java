package com.eanfang.base.kit.aop.aspect;

import android.util.Log;
import android.view.View;

import com.eanfang.base.kit.R;
import com.eanfang.base.network.config.HttpConfig;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Calendar;

/**
 * 防止View被连续点击,间隔时间600ms
 *
 * @author jornl
 * @date 2019-07-04
 */
@Aspect
public class SingleClickAspect {
    private static final String TAG = "SingleClickAspect";
    private static final int MIN_CLICK_DELAY_TIME = 600;
    private static final int TIME_TAG = R.id.click_time;

    /**
     * //方法切入点
     */
    @Pointcut("execution(@com.eanfang.base.kit.aop.annotation.SingleClick * *(..))")
    public void methodAnnotated() {

    }

    /**
     * 检测点击事件
     *
     * @param joinPoint joinPoint
     * @throws
     */
    @Around("methodAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = ((View) arg);
            }
        }
        if (view != null) {
            Object tag = view.getTag(TIME_TAG);
            long lastClickTime = (tag != null) ? (long) tag : 0;
            if (HttpConfig.get().isDebug()) {
                Log.d(TAG, "lastClickTime:" + lastClickTime);
            }
            long currentTime = Calendar.getInstance().getTimeInMillis();
            //过滤掉600毫秒内的连续点击
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                view.setTag(TIME_TAG, currentTime);
                if (HttpConfig.get().isDebug()) {
                    Log.d(TAG, "currentTime:" + currentTime);
                }
                //执行原方法
                joinPoint.proceed();
            }
        }
    }
}
