package com.eanfang.controltool.calendarview;

import android.support.annotation.Nullable;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;

/**
 * created by wtt
 * at 2019/4/26
 * summary:
 */
public interface ICalendarView {
    ICalendarView findView(MaterialCalendarView materialCalendarView);
    void setSelectedDate(@Nullable LocalDate date);
    void setOnDateChangedListener(OnDateSelectedListener listener);
}
