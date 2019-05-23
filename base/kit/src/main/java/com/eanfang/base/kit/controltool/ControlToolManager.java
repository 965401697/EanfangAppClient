package com.eanfang.base.kit.controltool;

import com.eanfang.base.kit.controltool.badgeview.BadgeViewManager;
import com.eanfang.base.kit.controltool.badgeview.IBadgeView;
import com.eanfang.base.kit.controltool.calendarview.CalendarViewManager;
import com.eanfang.base.kit.controltool.calendarview.ICalendarView;
import com.eanfang.base.kit.controltool.flowlayout.FlowlayoutManager;
import com.eanfang.base.kit.controltool.flowlayout.IFlowLayout;
import com.eanfang.base.kit.controltool.flycotablayout.FlycoTablayoutManager;
import com.eanfang.base.kit.controltool.flycotablayout.IFlycoTabLayout;
import com.eanfang.base.kit.controltool.materialratingbar.IMaterialRatingBar;
import com.eanfang.base.kit.controltool.materialratingbar.MateriaIRatingBarManager;
import com.eanfang.base.kit.controltool.numberprogress.INumberProgressBar;
import com.eanfang.base.kit.controltool.numberprogress.NumProgressManager;
import com.eanfang.base.kit.controltool.piechart.IPieChart;
import com.eanfang.base.kit.controltool.piechart.PieChartmanager;
import com.eanfang.base.kit.controltool.timeline.ITimeLine;
import com.eanfang.base.kit.controltool.timeline.TimeLineManager;

/**
 * created by wtt
 * at 2019/4/24
 * summary:
 */
public class ControlToolManager {
    private static IFlycoTabLayout IFLYCOTABLAYOUT;
    private static ITimeLine ITIMELINE;
    private static IMaterialRatingBar IMATERIALRATINGBAR;
    private static INumberProgressBar INUMBERPROGRESSBAR;
    private static IFlowLayout IFLOWLAYOUT;
    private static IBadgeView IBADGEVIEW;
    private static IPieChart IPIECHART;
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

    public static IFlowLayout flowLayout() {
        if (IFLOWLAYOUT == null) {
            IFLOWLAYOUT = new FlowlayoutManager();
        }
        return IFLOWLAYOUT;
    }

    public static IBadgeView badgeView() {
        if (IBADGEVIEW == null) {
            IBADGEVIEW = new BadgeViewManager();
        }
        return IBADGEVIEW;
    }

    public static IPieChart pieChart() {
        if (IPIECHART == null) {
            IPIECHART = new PieChartmanager();
        }
        return IPIECHART;
    }

    public static ICalendarView calendarV() {
        if (ICALENDARVIEW == null) {
            ICALENDARVIEW = new CalendarViewManager();
        }
        return ICALENDARVIEW;
    }
}
