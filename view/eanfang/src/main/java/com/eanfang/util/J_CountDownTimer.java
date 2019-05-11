/*
 * Copyright (c) 2016. YOLO  All rights reserved.
 *
 */

package com.eanfang.util.message;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public abstract class J_CountDownTimer {
    private static final int MSG = 1;
    /***
     * The interval in millis that the user receives callbacks
     */
    private final long mCountdownInterval;
    boolean isFinish = true;
    /***
     * Millis since epoch when alarm should stop.
     */
    private long mMillisInFuture;
    private long mStopTimeInFuture;
    // handles counting down
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            synchronized (J_CountDownTimer.this) {
                final long millisLeft = mStopTimeInFuture
                        - SystemClock.elapsedRealtime();
                if (millisLeft <= 0) {
                    onFinish();
                } else if (millisLeft < mCountdownInterval) {
                    // no tick, just delay until done
                    mHandler.sendMessageDelayed(mHandler
                            .obtainMessage(MSG), millisLeft);
                } else {
                    long lastTickStart = SystemClock
                            .elapsedRealtime();
                    onTick(millisLeft);
                    // take into account user's onTick taking time to execute
                    long delay = lastTickStart
                            + mCountdownInterval
                            - SystemClock
                            .elapsedRealtime();
                    // special case: user's onTick took more than interval to
                    // complete, skip to next interval
                    while (delay < 0) {
                        delay += mCountdownInterval;
                    }
                    mHandler.sendMessageDelayed(mHandler
                            .obtainMessage(MSG), delay);
                }
            }
            return false;
        }
    });

    /***
     * @param millisInFuture
     *            The number of millis in the future from the call to
     *            {@link #start()} until the countdown is done and
     *            {@link #onFinish()} is called.
     * @param countDownInterval
     *            The interval along the way to receive {@link #onTick(long)}
     *            callbacks.
     */
    public J_CountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    public void setMillisInFuture(long millisInFuture) {
        mMillisInFuture = millisInFuture;
    }

    /***
     * Cancel the countdown.
     */
    public final void cancel() {
        mHandler.removeMessages(MSG);
    }

    public final void reset() {
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
    }

    /***
     * Start the countdown.
     */
    public synchronized final J_CountDownTimer start() {
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        isFinish = false;
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    /***
     * Callback fired on regular interval.
     *
     * @param millisUntilFinished
     *            The amount of time until finished.
     */
    public abstract void onTick(long millisUntilFinished);

    /***
     * Callback fired when the time is up.
     */
    public abstract void onFinish();
}
