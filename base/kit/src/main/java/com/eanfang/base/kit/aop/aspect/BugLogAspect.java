package com.eanfang.base.kit.aop.aspect;

import android.view.View;

import com.alibaba.fastjson.JSON;
import com.eanfang.base.network.config.HttpConfig;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import cn.hutool.core.date.DateUtil;

/**
 * 防止View被连续点击,间隔时间600ms
 *
 * @author jornl
 * @date 2019-07-04
 */
@Aspect
public class BugLogAspect {
    private static final String TAG = "BugLogAspect";

    /**
     * 方法切入点
     */
    @Pointcut("execution(@com.eanfang.base.kit.aop.annotation.BugLog * *(..))")
    public void methodAnnotated() {
    }

    /**
     * 性能调试方法
     *
     * @param joinPoint joinPoint
     * @throws
     */
    @Around(value = "methodAnnotated()", argNames = "joinPoint")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!HttpConfig.get().isDebug()) {
            joinPoint.proceed();
            return;
        }
        int timer = 0;
//        if (bugLog != null) {
//            timer = Integer.parseInt(bugLog.ignoreTimer());
//        }
        String methodName = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();
        Object[] paramArr = joinPoint.getArgs();
        String returnType = joinPoint.getSignature().toString().split(" ")[0];
        String returnValue = "";
        String errorMsg = "";
        long begin = 0, end = 0;
        try {
            begin = DateUtil.date().getTime();
            Object obj = joinPoint.proceed();
            if (obj != null) {
                returnValue = JSON.toJSONString(obj);
            }
            end = DateUtil.date().getTime();
        } catch (Throwable e) {
            errorMsg = e.getMessage();
        }
        StringBuilder paramStr = new StringBuilder();
        if (paramArr != null) {
            for (int i = 0; i < paramArr.length; i++) {
                if (paramArr[i] instanceof View) {
                    paramStr.append("┣参数").append(i + 1).append("：").append(paramArr[i].toString()).append("\n");
                } else {
                    paramStr.append("┣参数").append(i + 1).append("：").append(JSON.toJSONString(paramArr[i])).append("\n");
                }
            }
        }
        if ((end - begin) > timer) {
            String log = String.format("\n┏━━━ 执行方法:%s\n" +
                            "%s" +
                            "┣返回值：%s (%s)\n" +
                            "┣异常信息：%s\n" +
                            "┣开始时间：%s\n" +
                            "┣方法耗时：%s ms\n" +
                            "┗━━━━\n",
                    methodName, paramStr.toString(), returnValue, returnType, errorMsg, DateUtil.date(begin).toString(), (end - begin));
            System.err.println(log);
        }
    }
}
