package com.eanfang.base.kit.controltool.timeline;

import android.graphics.drawable.Drawable;

import com.github.vipulasri.timelineview.TimelineView;

/**
 * created by wtt
 * at 2019/4/25
 * summary:
 */
public class TimeLineManager implements ITimeLine {
    @Override
    public void setMarker(TimelineView timelineView, Drawable marker) {
        timelineView.setMarker(marker);
    }
}
