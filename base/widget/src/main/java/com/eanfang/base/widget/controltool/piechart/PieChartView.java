package com.eanfang.base.widget.controltool.piechart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.eanfang.base.widget.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class PieChartView extends PieChart {
    public static final int[] LIBERTY_COLORS_FiVE = {
            Color.rgb(123, 119, 249), Color.rgb(255, 159, 0),
            Color.rgb(72, 205, 210), Color.rgb(117, 226, 228),
            Color.rgb(166, 98, 247)
    };
    public static final int[] LIBERTY_TWO_COLORS_MORE = {
            Color.rgb(255, 202, 115), Color.rgb(0, 206, 161),
            Color.rgb(255, 98, 0), Color.rgb(255, 211, 0),
            Color.rgb(130, 104, 234),
    };

    public PieChartView(Context context) {
        super(context);
        init();
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        super.init();
        /**
         * 是否使用百分比
         */
        setUsePercentValues(true);
        /**
         * 设置pieChart图表的描述
         * */
       getDescription().setEnabled(false);
        /**
         * 是否显示图标上文字
         * */
        setDrawEntryLabels(true);
        setEntryLabelColor(Color.BLACK);
    }

    public void setData(List<PieEntry> list) {
        if (list != null && list.size() > 0) {
            if (list.size() <= 5) {
                setPieChartData(list, true);
            } else {
                setPieChartData(list, false);
            }
        }

    }

    //设置数据
    private void setPieChartData(List<PieEntry> list, boolean isFive) {
        clear();
        PieDataSet dataSet = new PieDataSet(list, "");
        //设置个饼状图之间的距离
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        List<Integer> colors = new ArrayList<Integer>();
        if (isFive) {
            for (int c : LIBERTY_COLORS_FiVE) {
                colors.add(c);
            }
        } else {
            for (int c : LIBERTY_COLORS_FiVE) {
                colors.add(c);
            }
            for (int c : LIBERTY_TWO_COLORS_MORE) {
                colors.add(c);
            }
        }
        dataSet.setColors(colors);

//        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        /**
         * 设置是否显示数据实体 显示百分比
         * */
        data.setDrawValues(true);
        /**
         * 设置所有DataSet内数据实体（百分比）的文本字体格式
         * */
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(R.color.roll_content);
        setData(data);

        // 撤销所有的亮点
        highlightValues(null);

        notifyDataSetChanged();
        invalidate();

    }
}
