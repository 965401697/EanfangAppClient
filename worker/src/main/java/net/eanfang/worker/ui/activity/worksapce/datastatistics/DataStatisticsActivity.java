package net.eanfang.worker.ui.activity.worksapce.datastatistics;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.witget.DataSelectPopWindow;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/4/24  10:59
 * @decision 数据统计
 */
public class DataStatisticsActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.tv_dataSelectType)
    TextView tvDataSelectType;
    @BindView(R.id.rb_dataTimeToday)
    RadioButton rbDataTimeToday;
    @BindView(R.id.rb_dataTimeMonth)
    RadioButton rbDataTimeMonth;
    @BindView(R.id.rg_dataTiem)
    RadioGroup rgDataTiem;
    // 故障现象比
    @BindView(R.id.pc_fault)
    PieChart pcFault;
    // 设备完好率
    @BindView(R.id.pc_intact)
    PieChart pcIntact;
    // 类型选择下拉Pop
    private DataSelectPopWindow dataSelectPopWindow;
    private List<String> mDataType = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_statistics);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    /**
     * @date on 2018/4/24  10:59
     * @decision 初始化视图
     */
    private void initView() {
        setTitle("数据统计");
        setLeftBack();
    }

    /**
     * @date on 2018/4/26  11:44
     * @decision 初始化数据
     */
    private void initData() {
        /**
         * 设置pieChart图表的描述
         * */
        initMyPieChart(pcFault);
        initMyPieChart(pcIntact);

        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(20, "优秀啊岁"));
        entries.add(new PieEntry(50, "是的"));
        entries.add(new PieEntry(10, "测萨等"));
        entries.add(new PieEntry(20, "是"));
        //模拟数据
        ArrayList<PieEntry> entries_intact = new ArrayList<PieEntry>();
        entries_intact.add(new PieEntry(20, "擦拭"));
        entries_intact.add(new PieEntry(20, "测试是"));
        entries_intact.add(new PieEntry(20, "及格"));
        entries_intact.add(new PieEntry(10, "哈哈搜索我"));
        entries_intact.add(new PieEntry(10, "你好"));
        entries_intact.add(new PieEntry(20, "谁的撒旦法"));

        //设置数据
        setFaultData(entries);
        setIntactData(entries_intact);

        mDataType.add("防盗报警");
        mDataType.add("电视监控");
        mDataType.add("可视对讲");
        mDataType.add("公共广播");
        mDataType.add("停车场");
    }

    private void initListener() {
        rgDataTiem.setOnCheckedChangeListener(this);
    }

    @OnClick(R.id.tv_dataSelectType)
    public void onViewClicked() {
        dataSelectPopWindow = new DataSelectPopWindow(this, mDataType, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvDataSelectType.setText(mDataType.get(i).toString());
                dataSelectPopWindow.dismiss();
            }
        });
        dataSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dataSelectPopWindow.backgroundAlpha(1.0f);
            }
        });
        dataSelectPopWindow.showAtLocation(findViewById(R.id.ll_datastatistics), Gravity.BOTTOM, 0, 0);
    }

    /**
     * Radiogroup
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_dataTimeToday:
                break;
            case R.id.rb_dataTimeMonth:
                break;
        }
    }

    //设置数据
    private void setFaultData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "电视监控");
        //设置个饼状图之间的距离
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

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
        pcFault.setData(data);

        // 撤销所有的亮点
        pcFault.highlightValues(null);

        pcFault.invalidate();

    }

    //设置数据
    private void setIntactData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "电视监控");
        //设置个饼状图之间的距离
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
        final int[] MY_COLORS = {Color.rgb(192, 0, 0), Color.rgb(255, 0, 0), Color.rgb(255, 192, 0),
                Color.rgb(127, 127, 127), Color.rgb(146, 208, 80), Color.rgb(0, 176, 80), Color.rgb(79, 129, 189)};
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : MY_COLORS) colors.add(c);
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
        data.setValueTextColor(R.color.roll_content);
        data.setValueTextSize(15f);
        pcIntact.setData(data);
        // 撤销所有的亮点
        pcIntact.highlightValues(null);

        pcIntact.invalidate();

    }

    public void initPieChart(PieChart pieChart) {
        /**
         * 是否使用百分比
         */
        pieChart.setUsePercentValues(true);
        /**
         * 设置pieChart图表的描述
         * */
        pieChart.getDescription().setEnabled(false);

        /**
         * 是否显示圆环中间的洞
         * */
        pieChart.setDrawHoleEnabled(true);
        /**
         * 是否显示洞中间文本
         */
        pieChart.setDrawCenterText(false);

        /**
         * 圆环距离屏幕上下上下左右的距离
         */
        pcFault.setExtraOffsets(5, 10, 5, 5);
//        pieChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
        // 阻力 摩擦系数
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        /**
         * 设置中间洞的颜色
         */
        pieChart.setHoleColor(Color.RED);
        /**
         * 设置圆环透明度及半径
         */
        pieChart.setTransparentCircleColor(Color.GREEN);
        /**
         * 设置PieChart内部透明圆与内部圆间距(31f-28f)透明度[0~255]数值越小越透明
         * */
        pieChart.setTransparentCircleAlpha(10);
        /**
         * 设置PieChart内部透明圆的半径
         * */
        pieChart.setTransparentCircleRadius(61f);
        /**
         * 设置圆环中间洞的半径
         */
        pieChart.setHoleRadius(68f);
        /**
         * 是否显示洞中间文本
         */
        pieChart.setDrawCenterText(false);
        /**
         *触摸是否可以旋转以及松手后旋转的度数
         */
        pieChart.setRotationEnabled(true);
        /**
         * 设置piecahrt图表点击Item高亮是否可用
         * */
        pieChart.setHighlightPerTapEnabled(true);
        /**
         * 设置pieChart图表起始角度
         * */
        pieChart.setRotationAngle(0);

        //默认动画
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = pieChart.getLegend();
        l.setEnabled(true);
        /**
         * 设置图例实体之间延X轴的间距（setOrientation = HORIZONTAL有效）
         * */
        l.setXEntrySpace(10f);
        /**
         * 设置图例实体之间延Y轴的间距（setOrientation = VERTICAL 有效）
         * */
        l.setYEntrySpace(2f);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //设置比例块换行...
        l.setWordWrapEnabled(true);
        l.setDrawInside(false);

    }

    public void initMyPieChart(PieChart pieChart) {
        /**
         * 是否使用百分比
         */
        pieChart.setUsePercentValues(true);
        /**
         * 设置pieChart图表的描述
         * */
        pieChart.getDescription().setEnabled(false);
    }

}
