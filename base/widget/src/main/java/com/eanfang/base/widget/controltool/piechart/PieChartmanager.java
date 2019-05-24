package com.eanfang.base.widget.controltool.piechart;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;

/**
 * created by wtt
 * at 2019/4/25
 * summary:
 */
public class PieChartmanager implements IPieChart {
    private PieChart pieChart;

    @Override
    public IPieChart pieChatView(PieChart pieChart) {
        this.pieChart = pieChart;
        return this;
    }

    @Override
    public void setUsePercentValues(boolean enabled) {
        pieChart.setUsePercentValues(enabled);
    }

    @Override
    public Description getDescription() {
        return pieChart.getDescription();
    }

    @Override
    public void setDrawEntryLabels(boolean enabled) {
        pieChart.setDrawEntryLabels(enabled);
    }

    @Override
    public void setEntryLabelColor(int color) {
        pieChart.setEntryLabelColor(color);
    }

    @Override
    public void setNoDataText(String text) {
        pieChart.setNoDataText(text);
    }

    @Override
    public void setData(Object data) {
        pieChart.setData((PieData) data);
    }
}
