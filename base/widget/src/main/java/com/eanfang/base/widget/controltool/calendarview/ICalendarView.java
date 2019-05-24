package com.eanfang.base.widget.controltool.calendarview;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import java.util.Date;

/**
 * created by wtt
 * at 2019/4/26
 * summary:
 */
public interface ICalendarView {
    ICalendarView findView(MaterialCalendarView materialCalendarView);
    void setSelectedDate(Date date);
    void setOnDateChangedListener(OnDateSelectedListener listener);
}
