package com.eanfang.base.widget.controltool;

import android.content.Context;
import com.eanfang.base.widget.controltool.badgeview.MyBadgeView;
import com.eanfang.base.widget.controltool.calendarview.CalendarViewManager;
import com.eanfang.base.widget.controltool.calendarview.ICalendarView;
import com.eanfang.base.widget.controltool.flycotablayout.FlycoTablayoutManager;
import com.eanfang.base.widget.controltool.flycotablayout.IFlycoTabLayout;
import com.eanfang.base.widget.controltool.materialratingbar.IMaterialRatingBar;
import com.eanfang.base.widget.controltool.materialratingbar.MateriaIRatingBarManager;
import com.eanfang.base.widget.controltool.numberprogress.INumberProgressBar;
import com.eanfang.base.widget.controltool.numberprogress.NumProgressManager;
import com.eanfang.base.widget.controltool.timeline.ITimeLine;
import com.eanfang.base.widget.controltool.timeline.TimeLineManager;

/**
 * created by wtt
 * at 2019/4/24
 * summary:
 */
public class ControlToolView {
    private static IFlycoTabLayout IFLYCOTABLAYOUT;
    private static ITimeLine ITIMELINE;
    private static IMaterialRatingBar IMATERIALRATINGBAR;
    private static INumberProgressBar INUMBERPROGRESSBAR;
    private static MyBadgeView IBADGEVIEW;
    private static ICalendarView ICALENDARVIEW;

    public static IFlycoTabLayout flycoTabLayoutManager() {
        if (IFLYCOTABLAYOUT == null) {
            IFLYCOTABLAYOUT = new FlycoTablayoutManager();
        }
        return IFLYCOTABLAYOUT;
    }

    public static ITimeLine timeLinemanager() {
        if (ITIMELINE == null) {
            ITIMELINE = new TimeLineManager();
        }
        return ITIMELINE;
    }

    public static IMaterialRatingBar materialRatingBarManager() {
        if (IMATERIALRATINGBAR == null) {
            IMATERIALRATINGBAR = new MateriaIRatingBarManager();
        }
        return IMATERIALRATINGBAR;
    }

    public static INumberProgressBar numberProgressBar() {
        if (INUMBERPROGRESSBAR == null) {
            INUMBERPROGRESSBAR = new NumProgressManager();
        }
        return INUMBERPROGRESSBAR;
    }

    public static MyBadgeView getBadge(Context context) {
        IBADGEVIEW =MyBadgeView.get(context);
        return IBADGEVIEW;
    }

    public static ICalendarView calendarV() {
        if (ICALENDARVIEW == null) {
            ICALENDARVIEW = new CalendarViewManager();
        }
        return ICALENDARVIEW;
    }
}
