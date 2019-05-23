package com.eanfang.widget.controltool.piechart;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;

/**
 * created by wtt
 * at 2019/4/25
 * summary:
 */
public interface IPieChart<T>  {
    IPieChart pieChatView(PieChart pieChart);
    void setUsePercentValues(boolean enabled);
    Description getDescription();
    void setDrawEntryLabels(boolean enabled);
    void setEntryLabelColor(int color);
    void setNoDataText(String text);
    void setData(T data);
}
