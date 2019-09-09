package net.eanfang.client.ui.activity.worksapce.monitor;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2019/9/9  10:21
 * @description 实时监控 列表
 */

public class MonitorListActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_list);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
