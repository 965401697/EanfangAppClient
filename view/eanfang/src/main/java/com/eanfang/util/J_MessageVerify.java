/*
 * Copyright (c) 2016. Huangrui All rights reserved.
 */

package com.eanfang.util;

import android.os.Handler;

/**
 * 短信验证
 *
 * @author HR
 *         <p>
 *         2013-12-23 上午11:14:19
 */
public class J_MessageVerify {
    public static final int FINISH = 2;
    public static final int ONTICK = 1;
    public static final int CODE = 0;
    static J_MessageVerify verify;
    Handler handler;
    // 时间长度

    long seconds;
    // 倒计时

    MyCount count;
    // 匹配字符串

    String patter;
    // 验证码长度

    int len;

    public J_MessageVerify() {
    }

    public static J_MessageVerify get() {
        if (verify == null) {
            verify = new J_MessageVerify();
        }
        return verify;
    }

    /**
     * @param seconds 单位秒
     */
    public void init(long seconds) {
        this.seconds = seconds > 0 ? seconds : 30;
        count = new MyCount(seconds * 1000, 1000);
    }

    @Deprecated
    public void init(Handler handler, long seconds) {
        this.seconds = seconds > 0 ? seconds : 30;
        this.handler = handler;
        count = new MyCount(seconds * 1000, 1000);
        count.setHandler(handler);
    }

    public void start() {
        if (count == null) {
            throw new NullPointerException("J_MessageVerify should init first");
        }
        count.reset();
        count.start();
    }

    public void setHandler(Handler handler) {
        if (count == null) {
            throw new NullPointerException("J_MessageVerify should init first");
        }
        this.handler = handler;
        count.setHandler(handler);
    }

    /**
     * 当前页面关闭,后台倒计时
     */
    public void onDismiss() {
        count.setHandler(null);
        count.cancel();
    }

    /**
     * 销毁
     */
    public void onDestory() {
        if (count != null) {
            count.cancel();
        }
        count = null;
    }

    /* 定义一个倒计时的内部类 */
    public class MyCount extends J_CountDownTimer {
        Handler handler;

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void setHandler(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void onFinish() {
            if (handler != null) {
                handler.sendEmptyMessage(FINISH);
            }

            isFinish = true;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (handler != null) {
                handler
                        .sendMessage(handler.obtainMessage(ONTICK, String
                                .valueOf(millisUntilFinished)));
            }
        }
    }
}
