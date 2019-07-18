package com.eanfang.util;

import java.util.Calendar;
import java.util.Date;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

/**
 * 日期工具类
 * DateUtil中有些方法 不兼容 安卓 24 以下,需使用本工具类替换
 * <p>
 * 1.DateUtil.offset ${@link DateUtil offset}
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

    /**
     * 偏移指定日期
     *
     * @param datePart 偏移单位
     * @param offset   偏移量，正数向未来偏移，负数向历史偏移
     * @return Date
     */
    public DateKit offset(DateField datePart, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(datePart.getValue(), offset);
        date = DateTime.of(calendar.getTime());
        return this;
    }

}
