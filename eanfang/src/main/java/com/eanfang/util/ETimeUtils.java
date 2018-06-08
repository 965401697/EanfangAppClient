package com.eanfang.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/6/5$  13:57$
 */
public class ETimeUtils {
    /**
     * 获取年月日 时分秒
     */
    public static String getTimeByYearMonthDayHourMinSec(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 获取年月日
     */
    public static String getTimeByYearMonthDay(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 获取时分秒
     */
    public static String getTimeByHourMinSec(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }
}
