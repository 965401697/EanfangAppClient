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
public class CalendarViewManager implements ICalendarView {
    private MaterialCalendarView materialCalendarView;

    @Override
    public ICalendarView findView(MaterialCalendarView materialCalendarView) {
        this.materialCalendarView = materialCalendarView;
        return this;
    }

    @Override
    public void setSelectedDate(@Nullable LocalDate date) {
        materialCalendarView.setSelectedDate(date);
    }

    @Override
    public void setOnDateChangedListener(OnDateSelectedListener listener) {
        materialCalendarView.setOnDateChangedListener(listener);
    }
}
