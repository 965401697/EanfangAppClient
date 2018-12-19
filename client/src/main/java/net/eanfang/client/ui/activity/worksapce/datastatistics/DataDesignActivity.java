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
import com.eanfang.model.datastatistics.DataDesignBean;
import com.eanfang.model.datastatistics.DataStatisticsCompany;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.eanfang.witget.DataSelectPopWindow;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.datastatistics.DataStatisticsDesignAdapter;
import net.eanfang.client.ui.adapter.datastatistics.DataStatisticsDesignCompanyAdapter;
import net.eanfang.client.ui.widget.DataStatisticsCompanyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2018/9/19
 * @description 数据统计 - 设计
 */

public class DataDesignActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

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
    //昨日报修
    @BindView(R.id.rv_repair_class_one)
    RecyclerView rvRepairClassOne;
    //五家公司
    @BindView(R.id.rv_five_company)
    RecyclerView rvFiveCompany;
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

    // 故障类型
    @BindView(R.id.ll_fault)
    LinearLayout llFault;

    // 昨日报修 暂无数据
    @BindView(R.id.tv_repair_noresult)
    TextView tvRepairNoresult;
    // 五家公司 暂无数据
    @BindView(R.id.tv_five_noresult)
    TextView tvFiveNoresult;
    // 昨日报修 暂无数据
    @BindView(R.id.tv_pie_noresult)
    TextView tvPieNoresult;

    // 类型选择下拉Pop
    private DataSelectPopWindow dataSelectPopWindow;

    // 昨日报修
    private DataStatisticsDesignAdapter dataStatisticsReapirAdapter;
    // 五家公司
    private DataStatisticsDesignCompanyAdapter dataStatisticsCompanyAdapter;
    // 类型
    private List<BaseDataEntity> mDataType = new ArrayList();

    // 时间  值 1是昨日，2是本月 默认是昨日
    private String mData = "1";

    //报修公司
    private List<DataDesignBean.DesignBean> installBeanList = new ArrayList<>();
    // 报修五家单位
    private List<DataDesignBean.FiveBean> fiveBeanList = new ArrayList<>();
    // 故障类型
    private List<DataDesignBean.BussinessBean> bussinessBeanList = new ArrayList<>();
    private ArrayList<PieEntry> bussinessEntryList = new ArrayList<>();

    //当前登录人公司ID
    private Long mMyOrgId;
    //切换公司ID
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

    private List<DataStatisticsCompany> companyEntityBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_design);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        setTitle("设计数据统计");
        setLeftBack();
        mMyOrgId = EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgId();
        mOrgName = EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName();
        mOrgId = mMyOrgId;
        /**
         * 设置pieChart图表的描述
         * */
        initMyPieChart(pcFault);
        // 报修
        dataStatisticsReapirAdapter = new DataStatisticsDesignAdapter(DataDesignActivity.this);
        rvRepairClassOne.setLayoutManager(new LinearLayoutManager(this));
        dataStatisticsReapirAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        dataStatisticsReapirAdapter.bindToRecyclerView(rvRepairClassOne);
        rvRepairClassOne.setNestedScrollingEnabled(false);
        // 五家公司
        dataStatisticsCompanyAdapter = new DataStatisticsDesignCompanyAdapter();
        rvFiveCompany.setLayoutManager(new LinearLayoutManager(this));
        dataStatisticsCompanyAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        dataStatisticsCompanyAdapter.bindToRecyclerView(rvFiveCompany);
        rvFiveCompany.setNestedScrollingEnabled(false);
    }

    private void initData() {
        tvCompanyName.setText(mOrgName);
        tvSelectCompanyName.setText(mOrgName);
        // 获取统计数据
        doGetData("", mMyOrgId);
        // 获取公司
        doGetComapnyData(mMyOrgId + "");
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
            new DataStatisticsCompanyListView(DataDesignActivity.this, mMyOrgId + "", mOrgName, (mCompanyName, mCompanyId) -> {
                tvSelectCompanyName.setText(mCompanyName);
                mOrgId = mCompanyId;
                doGetData("", mOrgId);
            }).show();
        });
    }

    /**
     * 获取数据
     */
    public void doGetData(String businessCode, Long compangId) {
        QueryEntry queryEntry = new QueryEntry();
        if (!StringUtils.isEmpty(businessCode)) {
            queryEntry.getEquals().put("businessOneCode", businessCode);
        }
        queryEntry.getEquals().put("shopCompanyId", compangId + "");
        queryEntry.getEquals().put("date", mData);
        EanfangHttp.post(NewApiService.DESIGN_DATA_STATISTICE)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<DataDesignBean>(this, true, DataDesignBean.class, bean -> {
                    setData(bean);
                }));

    }

    /**
     * 填充数据
     */
    private void setData(DataDesignBean bean) {

        // 昨日设计
        if (bean.getDesign().size() > 0) {
            installBeanList.clear();
            tvRepairNoresult.setVisibility(View.GONE);
            installBeanList = bean.getDesign();
            rvRepairClassOne.setVisibility(View.VISIBLE);
            dataStatisticsReapirAdapter.setNewData(installBeanList);
        } else {
            rvRepairClassOne.setVisibility(View.GONE);
            tvRepairNoresult.setVisibility(View.VISIBLE);
        }
        // 五家公司
        if (bean.getFive().size() > 0) {
            fiveBeanList.clear();
            tvFiveNoresult.setVisibility(View.GONE);
            fiveBeanList = bean.getFive();
            rvFiveCompany.setVisibility(View.VISIBLE);
            dataStatisticsCompanyAdapter.setNewData(fiveBeanList);
        } else {
            rvFiveCompany.setVisibility(View.GONE);
            tvFiveNoresult.setVisibility(View.VISIBLE);
        }
        // 饼状图
        if (bean.getBussiness().size() > 0) {
            bussinessBeanList = bean.getBussiness();
            //设置数据
            bussinessEntryList.clear();
            for (int i = 0; i < bussinessBeanList.size(); i++) {
                if (bussinessBeanList.get(i).getNum() != 0) {
                    bussinessEntryList.add(new PieEntry(bussinessBeanList.get(i).getNum(), bussinessBeanList.get(i).getTypeStr()));
                }
            }
            if (bussinessEntryList.size() <= 5) {
                setFaultData(bussinessEntryList, true);
            } else {
                setFaultData(bussinessEntryList, false);
            }


        }
        if (bussinessEntryList.size() <= 0) {
            tvPieNoresult.setVisibility(View.VISIBLE);
            llFault.setVisibility(View.GONE);
        } else {
            tvPieNoresult.setVisibility(View.GONE);
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
                doGetData(mDataType.get(i).getDataCode(), mOrgId);
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
                doGetData(mBussiness, mOrgId);
                break;
            case R.id.rb_dataTimeMonth:
                mData = "2";
                // 获取统计数据
                doGetData(mBussiness, mOrgId);
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
        pcFault.setData(data);

        // 撤销所有的亮点
        pcFault.highlightValues(null);

        pcFault.notifyDataSetChanged();
        pcFault.invalidate();

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
    }
}
