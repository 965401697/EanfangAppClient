package net.eanfang.client.ui.activity.worksapce.datastatistics;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.datastatistics.DataStatisticsBean;
import com.eanfang.model.datastatistics.DataStatisticsCompany;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.eanfang.witget.DataSelectPopWindow;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.datastatistics.DataStatisticsCompanyAdapter;
import net.eanfang.client.ui.adapter.datastatistics.DataStatisticsDeviceAdapter;
import net.eanfang.client.ui.adapter.datastatistics.DataStatisticsReapirAdapter;
import net.eanfang.client.ui.widget.DataStatisticsCompanyListView;

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

    // 数据分析选择系统类别：全部，电子监控
    @BindView(R.id.tv_dataSelectType)
    TextView tvDataSelectType;
    // 本月 昨日
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
    //昨日报修
    @BindView(R.id.rv_repair_class_one)
    RecyclerView rvRepairClassOne;
    //五家公司
    @BindView(R.id.rv_five_company)
    RecyclerView rvFiveCompany;
    // 设备情况
    @BindView(R.id.rv_device)
    RecyclerView rvDevice;
    // 总公司名称
    @BindView(R.id.tv_companyName)
    TextView tvCompanyName;
    //子公司名称
    @BindView(R.id.tv_childCompanyName)
    TextView tvChildCompanyName;

    @BindView(R.id.tv_select_company_name)
    TextView tvSelectCompanyName;

    // 切换公司
    @BindView(R.id.rl_change_company)
    RelativeLayout rlChangeCompany;


    // 设备完好率
    @BindView(R.id.ll_intact)
    LinearLayout llIntact;
    // 故障类型
    @BindView(R.id.ll_fault)
    LinearLayout llFault;

    // 昨日报修 暂无数据
    @BindView(R.id.tv_repair_noresult)
    TextView tvRepairNoresult;
    // 五家公司 暂无数据
    @BindView(R.id.tv_five_noresult)
    TextView tvFiveNoresult;
    // 设备情况 暂无数据
    @BindView(R.id.tv_device_noresult)
    TextView tvDeviceNoresult;
    // 昨日报修 暂无数据
    @BindView(R.id.tv_pie_noresult)
    TextView tvPieNoresult;

    // 类型选择下拉Pop
    private DataSelectPopWindow dataSelectPopWindow;

    private DataStatisticsReapirAdapter dataStatisticsReapirAdapter;
    private DataStatisticsCompanyAdapter dataStatisticsCompanyAdapter;
    private DataStatisticsDeviceAdapter dataStatisticsDeviceAdapter;
    // 类型
    private List<BaseDataEntity> mDataType = new ArrayList();

    // 时间  值 1是昨日，2是本月 默认是昨日
    private String mData = "1";

    //报修公司
    private List<DataStatisticsBean.RepairBean> repairBeanList = new ArrayList<>();
    // 报修五家单位
    private List<DataStatisticsBean.FiveBean> fiveBeanList = new ArrayList<>();
    // 设备情况
    private List<DataStatisticsBean.DeviceBean> deviceBeanList = new ArrayList<>();
    // 故障类型
    private List<DataStatisticsBean.BussinessBean> bussinessBeanList = new ArrayList<>();
    private ArrayList<PieEntry> bussinessEntryList = new ArrayList<>();
    // 设备完好率
    private List<DataStatisticsBean.FailureBean> failureBeanList = new ArrayList<>();
    private ArrayList<PieEntry> failureEntryList = new ArrayList<>();

    //当前公司ID
    private Long mOrgId;
    // 当前公司名称
    private String mOrgName = "";
    // 业务类型
    private String mBussiness = "";

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

    List<DataStatisticsCompany> companyEntityBeanList = new ArrayList();

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
        setTitle("报修数据统计");
        setLeftBack();
        mOrgId = EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgId();
        mOrgName = EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName();
        /**
         * 设置pieChart图表的描述
         * */
        initMyPieChart(pcFault);
        initMyPieChart(pcIntact);
        // 报修
        dataStatisticsReapirAdapter = new DataStatisticsReapirAdapter(DataStatisticsActivity.this);
        rvRepairClassOne.setLayoutManager(new LinearLayoutManager(this));
        dataStatisticsReapirAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        dataStatisticsReapirAdapter.bindToRecyclerView(rvRepairClassOne);
        rvRepairClassOne.setNestedScrollingEnabled(false);
        // 五家公司
        dataStatisticsCompanyAdapter = new DataStatisticsCompanyAdapter();
        rvFiveCompany.setLayoutManager(new LinearLayoutManager(this));
        dataStatisticsCompanyAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        dataStatisticsCompanyAdapter.bindToRecyclerView(rvFiveCompany);
        rvFiveCompany.setNestedScrollingEnabled(false);
        // 设备情况
        dataStatisticsDeviceAdapter = new DataStatisticsDeviceAdapter();
        rvDevice.setLayoutManager(new LinearLayoutManager(this));
        dataStatisticsDeviceAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        dataStatisticsDeviceAdapter.bindToRecyclerView(rvDevice);
        rvDevice.setNestedScrollingEnabled(false);

    }

    /**
     * @date on 2018/4/26  11:44
     * @decision 初始化数据
     */
    private void initData() {
        tvCompanyName.setText(mOrgName);
        tvSelectCompanyName.setText(mOrgName);
        // 获取统计数据
        doGetData("");
        // 获取公司
        doGetComapnyData(mOrgId + "");
        BaseDataEntity baseDataEntity = new BaseDataEntity();
        baseDataEntity.setDataName("全部");
        baseDataEntity.setDataCode("");
        mDataType.add(baseDataEntity);
        mDataType.addAll(Config.get().getBusinessList(1));

    }

    /**
     * 获取公司
     */
    private void doGetComapnyData(String orgId) {
        QueryEntry queryEntry = new QueryEntry();
//        queryEntry.getEquals().put("topCompanyId", orgId + "");
//        queryEntry.getEquals().put("companyId", orgId + "");
        EanfangHttp.post(NewApiService.REPAIR_DATA_COMPANGY)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<DataStatisticsCompany>(this, false, DataStatisticsCompany.class, true, bean -> {
                    companyEntityBeanList = bean;
                    if (companyEntityBeanList.size() - 1 > 0) {
                        tvChildCompanyName.setText(companyEntityBeanList.size() + "");
                    } else {
                        tvChildCompanyName.setText("0");
                    }
                }));
    }

    private void initListener() {
        rgDataTiem.setOnCheckedChangeListener(this);
        rlChangeCompany.setOnClickListener((View v) -> {
            new DataStatisticsCompanyListView(DataStatisticsActivity.this, mOrgId + "", (mCompanyName, mCompanyId) -> {
                tvSelectCompanyName.setText(mCompanyName);
                doGetData("");
            }).show();
            doGetComapnyData(mOrgId + "");
        });
    }

    public void doGetData(String businessCode) {
        QueryEntry queryEntry = new QueryEntry();
        if (!StringUtils.isEmpty(businessCode)) {
            queryEntry.getEquals().put("bussinessCode", businessCode);
        }
        queryEntry.getEquals().put("shopCompanyId", mOrgId + "");
        queryEntry.getEquals().put("date", mData);
        EanfangHttp.post(NewApiService.REPAIR_DATA_STATISTICE)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<DataStatisticsBean>(this, true, DataStatisticsBean.class, bean -> {
                    setData(bean);
                }));

    }

    /**
     * 填充数据
     */
    private void setData(DataStatisticsBean bean) {

        // 昨日报修
        if (bean.getRepair().size() > 0) {
            repairBeanList.clear();
            tvRepairNoresult.setVisibility(View.GONE);
            repairBeanList = bean.getRepair();
            dataStatisticsReapirAdapter.setNewData(repairBeanList);
        } else {
            tvRepairNoresult.setVisibility(View.VISIBLE);
        }
        // 五家公司
        if (bean.getFive().size() > 0) {
            fiveBeanList.clear();
            tvFiveNoresult.setVisibility(View.GONE);
            fiveBeanList = bean.getFive();
            dataStatisticsCompanyAdapter.setNewData(fiveBeanList);
        } else {
            tvFiveNoresult.setVisibility(View.VISIBLE);
        }
        // 设备情况
        if (bean.getDevice().size() > 0) {
            tvDeviceNoresult.setVisibility(View.GONE);
            deviceBeanList.clear();
            deviceBeanList = bean.getDevice();
            dataStatisticsDeviceAdapter.setNewData(deviceBeanList);
        } else {
            tvDeviceNoresult.setVisibility(View.VISIBLE);
        }
        // 饼状图
        if (bean.getBussiness().size() > 0) {
            bussinessBeanList = bean.getBussiness();
            //设置数据
            bussinessEntryList.clear();
            for (int i = 0; i < bussinessBeanList.size(); i++) {
                if (bussinessBeanList.get(i).getCount() != 0) {
                    bussinessEntryList.add(new PieEntry(bussinessBeanList.get(i).getCount(), bussinessBeanList.get(i).getTypeStr()));
                }
            }
            if (bussinessEntryList.size() <= 5) {
                setFaultData(bussinessEntryList, true);
            } else {
                setFaultData(bussinessEntryList, false);
            }


        }
        if (bean.getFailure().size() > 0) {
            failureBeanList = bean.getFailure();
            //设置数据
            failureEntryList.clear();
            for (int i = 0; i < failureBeanList.size(); i++) {
                if (failureBeanList.get(i).getCount() != 0) {
                    failureEntryList.add(new PieEntry(failureBeanList.get(i).getCount(), failureBeanList.get(i).getTypeStr()));
                }
            }
            if (failureEntryList.size() <= 5) {
                setIntactData(failureEntryList, true);
            } else {
                setIntactData(failureEntryList, false);
            }

        }
        if (bussinessEntryList.size() <= 0 && failureEntryList.size() <= 0) {
            tvPieNoresult.setVisibility(View.VISIBLE);
            llIntact.setVisibility(View.GONE);
            llFault.setVisibility(View.GONE);
        } else {
            tvPieNoresult.setVisibility(View.GONE);
            llIntact.setVisibility(View.VISIBLE);
            llFault.setVisibility(View.VISIBLE);
        }


    }

    @OnClick(R.id.tv_dataSelectType)
    public void onViewClicked() {
        dataSelectPopWindow = new DataSelectPopWindow(this, mDataType, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvDataSelectType.setText(mDataType.get(i).getDataName());
                mBussiness = mDataType.get(i).getDataCode();
                doGetData(mDataType.get(i).getDataCode());
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
                mData = "1";
                // 获取统计数据
                doGetData(mBussiness);
                break;
            case R.id.rb_dataTimeMonth:
                mData = "2";
                // 获取统计数据
                doGetData(mBussiness);
                break;
        }
    }

    //设置数据
    private void setFaultData(ArrayList<PieEntry> entries, boolean isFive) {
        pcFault.clear();
        PieDataSet dataSet = new PieDataSet(entries, "");
        //设置个饼状图之间的距离
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        if (isFive) {
            for (int c : LIBERTY_COLORS_FiVE)
                colors.add(c);
        } else {
            for (int c : LIBERTY_COLORS_FiVE)
                colors.add(c);
            for (int c : LIBERTY_TWO_COLORS_MORE)
                colors.add(c);
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
        pcFault.setData(data);

        // 撤销所有的亮点
        pcFault.highlightValues(null);

        pcFault.notifyDataSetChanged();
        pcFault.invalidate();

    }

    //设置数据
    private void setIntactData(ArrayList<PieEntry> entries, boolean isFive) {
        pcIntact.clear();
        PieDataSet dataSet = new PieDataSet(entries, "");
        //设置个饼状图之间的距离
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        if (isFive) {
            for (int c : LIBERTY_TWO_COLORS_MORE)
                colors.add(c);
        } else {
            for (int c : LIBERTY_COLORS_FiVE)
                colors.add(c);
            for (int c : LIBERTY_TWO_COLORS_MORE)
                colors.add(c);
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
        data.setValueTextColor(R.color.roll_content);
        data.setValueTextSize(15f);
        pcIntact.setData(data);
        // 撤销所有的亮点
        pcIntact.highlightValues(null);

        pcIntact.notifyDataSetChanged();
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
        /**
         * 是否显示图标上文字
         * */
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setNoDataText("暂无数据");
    }

}
