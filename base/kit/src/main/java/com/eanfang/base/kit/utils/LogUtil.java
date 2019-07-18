package com.eanfang.base.kit.utils;

import android.util.Log;

import com.eanfang.base.network.config.HttpConfig;

/**
 * 日志工具
 *
 * @author jornl
 * @date 2019年7月15日
 */
public class LogUtil {

    public static void w(String tag, Object msg) { // 警告信息
        log(tag, msg.toString(), 'w');
    }

    public static void e(String tag, Object msg) { // 错误信息
        log(tag, msg.toString(), 'e');
    }

    public static void d(String tag, Object msg) {// 调试信息
        log(tag, msg.toString(), 'd');
    }

    public static void i(String tag, Object msg) {//
        log(tag, msg.toString(), 'i');
    }

    public static void v(String tag, Object msg) {
        log(tag, msg.toString(), 'v');
    }

    public static void w(String tag, String text) {
        log(tag, text, 'w');
    }

    /**
     * Android原生log
     * 根据tmsg和等级，输出日志
     *
     * @param tag   tag
     * @param msg   msg
     * @param level level
     */
    public static void log(String tag, String msg, char level) {
        // 日志文件总开关
        if (HttpConfig.get().isDebug()) {
            if ('e' == level) { // 输出错误信息
                Log.e(tag, msg);
            } else if ('w' == level) {
                Log.w(tag, msg);
            } else if ('d' == level) {
                Log.d(tag, msg);
            } else if ('i' == level) {
                Log.i(tag, msg);
            } else {
                Log.v(tag, msg);
            }
        }
    }
}
