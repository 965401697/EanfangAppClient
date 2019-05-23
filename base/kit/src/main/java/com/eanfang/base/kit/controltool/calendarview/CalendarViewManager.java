package com.eanfang.base.kit.controltool.calendarview;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import java.util.Date;

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
    public void setSelectedDate(Date date) {
        materialCalendarView.setSelectedDate(date);
    }

    @Override
    public void setOnDateChangedListener(OnDateSelectedListener listener) {
        materialCalendarView.setOnDateChangedListener(listener);
    }
}
