package com.eanfang.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author zhang.qijia
 * @create 2017-05-27 10:47
 **/
public class GetDateUtils {
    /**
     * 返回上一天的0点信息
     *
     * @param date
     * @return 2014-3-3 00:00:00
     */
    public static Date lastDayStartDate(Date date) {

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        if ((gc.get(Calendar.HOUR_OF_DAY) + 1 == Calendar.SUNDAY) && (gc.get(Calendar.MINUTE) == Calendar.SUNDAY) && (gc.get(Calendar.SECOND) == Calendar.SUNDAY)) {
            return new Date(date.getTime() - (24 * 60 * 60 * 1000));
        } else {
            return new Date(date.getTime() - gc.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000
                    - gc.get(Calendar.MINUTE) * 60 * 1000 - gc.get(Calendar.SECOND) * 1000 - 24 * 60 * 60 * 1000);
        }

    }

    /**
     * 返回上一天的23:59:59信息
     *
     * @param date
     * @return 2014-3-3 00:00:00
     */
    public static Date lastDayEndDate(Date date) {

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        int day = gc.get(Calendar.DAY_OF_MONTH) - 1;
        gc.set(Calendar.DAY_OF_MONTH, day);
        gc.set(Calendar.HOUR_OF_DAY, 23);
        gc.set(Calendar.MINUTE, 59);
        gc.set(Calendar.SECOND, 59);
        return gc.getTime();
    }

    /**
     * 返回上个月第一天的0点信息
     *
     * @param date
     * @return 2014-3-3 00:00:00
     */
    public static Date lastMonthStart(Date date) {

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        int month = gc.get(Calendar.MONTH) - 1;
        gc.set(Calendar.DAY_OF_MONTH, 1);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MONTH, month);
        return gc.getTime();
    }

    /**
     * 返回上个月最后一天的23:59:59点信息
     *
     * @param date
     * @return 2014-3-3 00:00:00
     */
    public static Date lastMonthEnd(Date date) {

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, -1);
        return gc.getTime();
    }

    /**
     * 返回本月第一天的0点信息
     *
     * @param date
     * @return 2014-3-3 00:00:00
     */
    public static Date thisMonthStart(Date date) {

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        return gc.getTime();
    }

    /**
     * 返回本月最后一天的23:59:59点信息
     *
     * @param date
     * @return 2014-3-3 00:00:00
     */
    public static Date thisMonthEnd(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        int month = gc.get(Calendar.MONTH) + 1;
        gc.set(Calendar.MONTH, month);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, -1);
        return gc.getTime();
    }

    /**
     * 返回本年第一天的0点信息
     *
     * @param date
     * @return 2014-3-3 00:00:00
     */
    public static Date getYearStart(Date date) {

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.set(Calendar.MONTH, 0);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        return gc.getTime();
    }

    /**
     * 返回本年末一天的24点信息
     *
     * @param date
     * @return 2014-3-3 00:00:00
     */
    public static Date getYearEnd(Date date) {

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.set(Calendar.MONTH, 11);
        gc.set(Calendar.DAY_OF_MONTH, 31);
        gc.set(Calendar.HOUR_OF_DAY, 23);
        gc.set(Calendar.MINUTE, 59);
        gc.set(Calendar.SECOND, 59);
        return gc.getTime();
    }

    /**
     * 将指定日期 增加 指定个月
     *
     * @param date  要增加的日期
     * @param month 要增加几个月
     * @return
     */
    public static Date addMonth(Date date, int month) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(GregorianCalendar.MONTH, month);
        return gc.getTime();
    }

    /**
     * 将指定日期 增加 指定个年
     *
     * @param date 要增加的日期
     * @param year 要增加几个年
     * @return
     */
    public static Date addYear(Date date, int year) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(GregorianCalendar.YEAR, year);
        return gc.getTime();
    }

    /**
     * 获得两个时间差
     *
     * @param fir
     * @param sec
     * @param type
     * @return
     */
    public static long getTimeDiff(Date fir, Date sec, String type) {
        // d1 = df.parse(fir);
        // Date d2 = df.parse(sec);
        // 这样得到的差值是微秒级别
        long diff = fir.getTime() - sec.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);

        if ("day".equals(type)) {
            return days;
        } else if ("hours".equals(type)) {
            return hours;
        } else if ("minutes".equals(type)) {
            return minutes;
        }
        return 0;
    }

    /**
     * 根据指定格式转换时间
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateToFormatString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 将日期转换成 （yyyy-MM-dd）
     *
     * @param date
     * @return
     */
    public static String dateToDateString(Date date) {
        return dateToFormatString(date, "yyyy-MM-dd");
    }

    /**
     * 将日期转换成 （yyyy-MM-dd hh:mm:ss）
     *
     * @param date
     * @return
     */
    public static String dateToDateTimeString(Date date) {
        return dateToFormatString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getDateNow() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取指定时间
     *
     * @param time
     * @return
     */
    public static Date getDate(Long time) {
        return new Date(time);
    }

    public static Date getDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(date);
        } catch (ParseException e) {
        }
        return null;
    }

    public static Date getDate(String year, String month, String day, String hour, String minute, String second) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 转化你字符串为Date
     *
     * @param dateStr       格式："2015-11-20 13:33:25"
     * @param calendarField 格式：Calendar.Month Calendar.dayOfMonth等
     * @return int 无效：-1
     */
    public static int getDateProperty(String dateStr, int calendarField) {
        if (null == dateStr || calendarField < 0)
            return -1;
        Date date = getDate(dateStr);
        if (null == date)
            return -1;
        int result = -1;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            result = calendar.get(calendarField);
        } catch (Exception e) {

        }
        return result;
    }


    /**
     * 获取某个时间的天数据
     *
     * @param dateStr 格式："2015-11-20 13:33:25"
     * @return int 有效数据：1-31, 无效：-1
     */
    public static int getDay(String dateStr) {
        return getDateProperty(dateStr, Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取某个时间的月数据
     *
     * @param dateStr 格式："2015-11-20 13:33:25"
     * @return int 有效数据：0-11, 无效：-1
     */
    public static int getMonth(String dateStr) {
        return getDateProperty(dateStr, Calendar.MONTH);
    }

    /**
     * 获取某个时间的年数据
     *
     * @param dateStr 格式："2015-11-20 13:33:25"
     * @return int 有效数据：0-11, 无效：-1
     */
    public static int getYear(String dateStr) {
        return getDateProperty(dateStr, Calendar.YEAR);
    }

    /**
     * 获取某个时间的小时数据
     *
     * @param dateStr 格式："2015-11-20 13:33:25"
     * @return int 有效数据：0-23, 无效：-1
     */
    public static int getHour(String dateStr) {
        return getDateProperty(dateStr, Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取某个时间的分钟数据
     *
     * @param dateStr 格式："2015-11-20 13:33:25"
     * @return int 有效数据：0-59, 无效：-1
     */
    public static int getMinute(String dateStr) {
        return getDateProperty(dateStr, Calendar.MINUTE);
    }


    /**
     * 转换时间字符串为秒值
     *
     * @param dateStr "yyyy-MM-dd HH:mm:ss"
     * @return Right, The seconds since Jan. 1, 1970; Error, 0;
     */
    public static long convertDateToSecond(String dateStr) {
        Date date = getDate(dateStr);
        if (date == null)
            return 0;
        return date.getTime() / 1000;
    }

    /**
     * 日期转为星期
     * add on 2018/3/15
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


}
