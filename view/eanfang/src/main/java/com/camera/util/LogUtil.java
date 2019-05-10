package com.camera.util;

import android.util.Log;

import com.camera.CameraApplication;
import com.eanfang.R;


/**
 * Created by Mr.hou
 *
 * @on 2017/9/26  15:12
 * @email houzhongzhou@yeah.net
 * @desc 日志管理
 */
public class LogUtil {
    private static final String TAG = CameraApplication.getCommonLibContext().getResources().getString(R.string.app_name);
    public static Boolean isDebug = CameraApplication.DEBUG; // 日志文件总开关

    public static void w(String tag, Object msg) { // 警告信息
        log(msg.toString(), 'w');
    }

    public static void e(String tag, Object msg) { // 错误信息
        log(msg.toString(), 'e');
    }

    public static void d(String tag, Object msg) {// 调试信息
        log(msg.toString(), 'd');
    }

    public static void i(String tag, Object msg) {//
        log(msg.toString(), 'i');
    }

    public static void v(String tag, Object msg) {
        log(msg.toString(), 'v');
    }

    public static void w(String tag, String text) {
        log(text, 'w');
    }


    /**
     * Android原生log
     * 根据tmsg和等级，输出日志
     *
     * @param msg
     * @param level
     * @return void
     */
    public static void log(String msg, char level) {
        if (isDebug) {
            if ('e' == level) { // 输出错误信息
                Log.e(TAG, msg);
            } else if ('w' == level) {
                Log.w(TAG, msg);
            } else if ('d' == level) {
                Log.d(TAG, msg);
            } else if ('i' == level) {
                Log.i(TAG, msg);
            } else {
                Log.v(TAG, msg);
            }
        }
    }

    /**
     * Android原生log
     *
     * @param msg
     */
    public static void androidLog(String msg) {
        if (isDebug) {
            Log.i(TAG, msg);
        }
    }

}
