package net.eanfang.client.ui.activity.worksapce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eanfang.ui.base.BaseActivity;

import net.eanfang.client.R;

/**
 * @author Guanluocang
 * @date on 2018/4/24  10:59
 * @decision 数据统计
 */
public class DataStatisticsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_statistics);
        initView();
    }

    /**
     * @date on 2018/4/24  10:59
     * @decision 初始化视图
     */
    private void initView() {
        setTitle("数据统计");
        setLeftBack();
    }
}
