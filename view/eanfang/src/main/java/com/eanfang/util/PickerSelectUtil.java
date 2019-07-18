package com.eanfang.util;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.picker.common.util.ConvertUtils;
import com.picker.common.util.ScreenUtils;
import com.picker.wheelpicker.picker.DatePicker;
import com.picker.wheelpicker.picker.DateTimePicker;
import com.picker.wheelpicker.picker.LinkagePicker;
import com.picker.wheelpicker.picker.OptionPicker;
import com.picker.wheelpicker.widget.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.hutool.core.date.DateUtil;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/28  10:59
 * @email houzhongzhou@yeah.net
 * @desc 选择器工具类
 */

public class PickerSelectUtil {

    /**
     * @param context
     * @param gravity   显示位置
     * @param ratio     页面宽度
     * @param textsize  字体大小
     * @param textcolor 字体颜色
     * @param items     数据 集合
     */
    public static void singleTextPicker(Activity context, int gravity, float ratio,
                                        int textsize, int textcolor, String title, List<String> items,
                                        OnOptionPickListener listener) {
        OptionPicker picker = new OptionPicker(context, items);
        picker.setCanceledOnTouchOutside(true);
        picker.setGravity(gravity);
        picker.setDividerRatio(ratio);
        picker.setSelectedIndex(0);
        picker.setCycleDisable(true);
        picker.setTextSize(textsize);
        picker.setTextColor(textcolor);
        picker.setTitleText(title);
        picker.setHeight(ScreenUtils.heightPixels(context) / 3);//整体高度
        picker.setLineSpaceMultiplier(3.0F);//每个item的高度
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                listener.picker(index, item);
            }
        });
        picker.show();
    }

    /**
     * @param context
     * @param items   数据集合
     */
    public static void singleTextPicker(Activity context, String title, List<String> items,
                                        OnOptionPickListener listener) {
        singleTextPicker(context, Gravity.BOTTOM, WheelView.DividerConfig.FILL, 14, Color.BLACK, title, items, listener);
    }


    /**
     * @param context
     * @param tv      控件
     * @param items   数据集合
     */
    public static void singleTextPicker(Activity context, String title, final TextView tv, List<String> items) {
        singleTextPicker(context, Gravity.BOTTOM, WheelView.DividerConfig.FILL, 14, Color.BLACK, title, items, (index, item) -> {
            tv.setText(item);
        });
    }

    public static void singleTextPicker(Activity context, String title, final TextView tv, List<String> items, OnOptionPickListener listener) {
        singleTextPicker(context, Gravity.BOTTOM, WheelView.DividerConfig.FILL, 14, Color.BLACK, title, items, listener);
    }

    /**
     * 三级联动
     *
     * @param activity
     * @param onlyTwo
     * @param gravity
     * @param ratio
     * @param textsize
     * @param textcolor
     * @param fir
     * @param sed
     * @param trd
     * @param listener
     */
    public static void linkagePicker(Activity activity, boolean onlyTwo, int gravity, float ratio,
                                     int textsize, int textcolor, String title, List<String> fir, List<List<String>> sed, List<List<List<String>>> trd, OnLinkageThreePickListener listener) {
        LinkagePicker.Provider provider = new LinkagePicker.DataProvider() {
            @Override
            public boolean isOnlyTwo() {
                return onlyTwo;
            }

            @NonNull
            @Override
            public List<String> provideFirstData() {
                return fir;
            }

            @NonNull
            @Override
            public List<String> provideSecondData(int firstIndex) {
                return sed.get(firstIndex);
            }

            @Nullable
            @Override
            public List<String> provideThirdData(int firstIndex, int secondIndex) {
                if (onlyTwo) {
                    return new ArrayList<>();
                }
                return sed.get(secondIndex);
            }
        };
        LinkagePicker picker = new LinkagePicker(activity, provider);
        picker.setCanceledOnTouchOutside(true);
        picker.setGravity(gravity);
        picker.setDividerRatio(ratio);
        picker.setSelectedIndex(0, 0);
        picker.setCycleDisable(true);
        picker.setTextSize(textsize);
        picker.setTextColor(textcolor);
        picker.setTitleText(title);
        picker.setOnStringPickListener(new LinkagePicker.OnStringPickListener() {
            @Override
            public void onPicked(String first, String second, String third) {
                listener.picker(first, second, third);
            }
        });
        picker.show();


    }

    public static void linkagePicker(Activity activity, String title, List<String> fir, List<List<String>> sed, List<List<List<String>>> trd, OnLinkageThreePickListener listener) {
        linkagePicker(activity, false, Gravity.BOTTOM, WheelView.DividerConfig.FILL, 14, Color.BLACK, title, fir, sed, trd, listener);
    }

    public static void linkagePicker(Activity activity, String title, List<String> fir, List<List<String>> sed, OnLinkageTwoPickListener listener) {
        linkagePicker(activity, true, Gravity.BOTTOM, WheelView.DividerConfig.FILL, 14, Color.BLACK, title, fir, sed, null, ((first, second, three) -> listener.picker(first, second)));
    }


    /**
     * 年月日时分
     */
    public static void onYearMonthDayTimePicker(Activity context, String title, Calendar start, Calendar end, OnOptionDateTimePickListener listener) {
        DateTimePicker picker = new DateTimePicker(context, DateTimePicker.HOUR_24);
        int startYear = start.get(Calendar.YEAR);
        int startMonth = start.get(Calendar.MONTH) + 1;
        int startDay = start.get(Calendar.DATE);
        int startHour = start.get(Calendar.HOUR_OF_DAY);
        int startMinute = start.get(Calendar.MINUTE);
        int startSecond = start.get(Calendar.SECOND);
        picker.setDateRangeStart(startYear, startMonth, startDay);//设置范围：开始的年月日

        int endYear = end.get(Calendar.YEAR);
        int endMonth = end.get(Calendar.MONTH) + 1;
        int endDay = end.get(Calendar.DATE);
        int endHour = end.get(Calendar.HOUR_OF_DAY);
        int endMinute = end.get(Calendar.MINUTE);
        int endSecond = end.get(Calendar.SECOND);
        picker.setDateRangeEnd(endYear, endMonth, endDay);//设置范围：结束的年月日
        picker.setTimeRangeStart(startHour, startMinute);//设置范围：开始的时分
        picker.setTimeRangeEnd(endHour, endMinute);//设置范围：结束的时分
        picker.setLabelTextColor(0xFFFF0000);
        picker.setDividerColor(0xFFFF0000);
        picker.setOnDateTimePickListener((DateTimePicker.OnYearMonthDayTimePickListener) (year1, month1, day1, hour1, minute1) -> {
            listener.picker(year1, month1, day1, hour1, minute1, startSecond + "");
        });
        picker.show();
    }


    public static void onYearMonthDayTimePicker(Activity context, String title, final TextView textView, Calendar start) {
        Calendar end = Calendar.getInstance();
        end.set(2099, 11, 31, 23, 59, 59);
        onYearMonthDayTimePicker(context, title, start, end, (year1, month1, day1, hour1, minute1, startSecond) -> {
            String time = DateUtil.parse(year1 + "-" + month1 + "-" + day1 + " " + hour1 + "-" + minute1 + "-" + startSecond).toString();
            textView.setText(time);
        });
    }

    public static void onYearMonthDayTimePicker(Activity context, String title, final TextView textView) {
        Calendar start = Calendar.getInstance();

        Calendar end = Calendar.getInstance();
        end.set(2099, 11, 31, 23, 59, 59);
        onYearMonthDayTimePicker(context, title, start, end, (year1, month1, day1, hour1, minute1, startSecond) -> {
            String time = DateUtil.parse(year1 + "-" + month1 + "-" + day1 + " " + hour1 + "-" + minute1 + "-" + startSecond).toString();
            textView.setText(time);
        });
    }

    /**
     * 年月日
     */
    public static void onYearMonthDayPicker(Activity context, String title, Calendar start, Calendar end, OnOptionDatePickListener listener) {
        DatePicker picker = new DatePicker(context);
        int startYear = start.get(Calendar.YEAR);
        int startMonth = start.get(Calendar.MONTH) + 1;
        int startDay = start.get(Calendar.DATE);
        picker.setRangeStart(startYear, startMonth, startDay);//设置范围：开始的年月日

        int endYear = end.get(Calendar.YEAR);
        int endMonth = end.get(Calendar.MONTH) + 1;
        int endDay = end.get(Calendar.DATE);
        picker.setRangeEnd(endYear, endMonth, endDay);//设置范围：结束的年月日
        picker.setLabelTextColor(0xFFFF0000);
        picker.setDividerColor(0xFFFF0000);
        picker.setOnDatePickListener((DatePicker.OnYearMonthDayPickListener) (year, month, day) -> {
            listener.picker(year, month, day);
        });
        picker.show();
    }


    public static void onYearMonthDayPicker(Activity context, String title, final TextView textView, Calendar start) {
        Calendar end = Calendar.getInstance();
        end.set(2099, 11, 31);
        onYearMonthDayPicker(context, title, start, end, (year1, month1, day1) -> {
            String time = DateUtil.parseTime(year1 + "-" + month1 + "-" + day1).toDateStr();
            textView.setText(time);
        });
    }

    public static void onYearMonthDayPicker(Activity context, String title, final TextView textView) {
        Calendar start = Calendar.getInstance();

        Calendar end = Calendar.getInstance();
        end.set(2099, 11, 31);
        onYearMonthDayPicker(context, title, start, end, (year1, month1, day1) -> {
            String time = DateUtil.parseTime(year1 + "-" + month1 + "-" + day1).toDateStr();
            textView.setText(time);
        });
    }


    /**
     * 年月日，开始时间为今天
     */
    public static void onUpYearMonthDayPicker(Activity context, TextView tv, DatePicker.OnYearMonthDayPickListener listener) {
        final DatePicker picker = new DatePicker(context);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(context, 10));
        picker.setRangeEnd(2099, 12, 31);
        picker.setRangeStart(year, month, day);
        picker.setSelectedItem(year, month, day);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(listener);
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    /**
     * 年月日,结束时间为今天
     */
    public static void onDownYearMonthDayPicker(Activity context, TextView tv, DatePicker.OnYearMonthDayPickListener listener) {
        final DatePicker picker = new DatePicker(context);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(context, 10));
        picker.setRangeEnd(year, month, day);
        picker.setRangeStart(1960, 8, 29);
        picker.setSelectedItem(year, month, day);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(listener);
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    /**
     * 年月日,结束时间为今天
     */
    public static void onDownYearMonthDayPicker(Activity context, TextView tv) {
        final DatePicker picker = new DatePicker(context);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(context, 10));
        picker.setRangeEnd(year, month, day);
        picker.setRangeStart(1960, 8, 29);
        picker.setSelectedItem(year, month, day);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tv.setText(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    /**
     * 年月日，开始时间为今天
     */
    public static void onUpYearMonthDayPicker(Activity context, TextView tv) {
        final DatePicker picker = new DatePicker(context);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(context, 10));
        picker.setRangeEnd(2099, 12, 31);
        picker.setRangeStart(year, month, day);
        picker.setSelectedItem(year, month, day);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tv.setText(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }


    public interface OnOptionPickListener {
        void picker(int index, String item);
    }

    public interface OnOptionDateTimePickListener {
        void picker(String year, String month, String day, String hour, String minutes, String seconds);
    }

    public interface OnOptionDatePickListener {
        void picker(String year, String month, String day);
    }

    public interface OnLinkageTwoPickListener {
        void picker(String first, String second);
    }

    public interface OnLinkageThreePickListener {
        void picker(String first, String second, String three);
    }
}
