package net.eanfang.worker.ui.activity.worksapce.datastatistics;

import android.support.v7.app.AppCompatActivity;
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
}
