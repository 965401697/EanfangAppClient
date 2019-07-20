package com.eanfang.util;

import java.util.Calendar;
import java.util.Date;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;

/**
 * 日期工具类
 * DateUtil中有些方法 不兼容 安卓 24 以下,需使用本工具类替换
 * <p>
 * 1.DateUtil.offset ${@link DateUtil offset}
 * <p>
 * 2.DateUtil.dayOfWeek
 * <p>
 * DateUtil.year() 、 DateUtil.month()  、 DateUtil.dayOfMonth()  、 DateUtil.hour()  、 DateUtil.minute()
 *
 * @author jornl
 * @date 2019.07.17
 */
public class DateKit {
    public DateTime date;

    private DateKit(Date date) {
        this.date = DateTime.of(date);
    }

    public static DateKit get(String date) {
        return new DateKit(DateUtil.parse(date));
    }

    public static DateKit get(Date date) {
        return new DateKit(date);
    }

    public static DateKit get() {
        return new DateKit(DateUtil.date());
    }

    /**
     * 偏移指定日期
     *
     * @param datePart 偏移单位
     * @param offset   偏移量，正数向未来偏移，负数向历史偏移
     * @return Date
     */
    public DateKit offset(DateField datePart, int offset) {
        Calendar calendar = getCalendar();
        calendar.add(datePart.getValue(), offset);
        date = DateTime.of(calendar.getTime());
        return this;
    }

    /**
     * 获取 指定日期的 是星期几
     *
     * @return weekName
     */
    public String weekName() {
        Calendar calendar = getCalendar();
        Week week = Week.of(calendar.get(DateField.DAY_OF_WEEK.getValue()));
        if (week != null) {
            return week.toChinese();
        }
        return "";
    }

    /**
     * 哪一年
     *
     * @return int
     */
    public int year() {
        return getCalendar().get(DateField.YEAR.getValue());
    }

    /**
     * 年的 哪一月
     *
     * @return int
     */
    public int month() {
        return getCalendar().get(DateField.MONTH.getValue());
    }

    /**
     * 月的 哪一天
     *
     * @return int
     */
    public int day() {
        return getCalendar().get(DateField.DAY_OF_MONTH.getValue());
    }

    /**
     * 天的 哪个小时
     *
     * @return int
     */
    public int hour() {
        return getCalendar().get(DateField.HOUR_OF_DAY.getValue());
    }

    /**
     * 小时的 哪个分钟
     *
     * @return int
     */
    public int minute() {
        return getCalendar().get(DateField.MINUTE.getValue());
    }


    /**
     * getCalendar
     *
     * @return Calendar
     */
    public Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
