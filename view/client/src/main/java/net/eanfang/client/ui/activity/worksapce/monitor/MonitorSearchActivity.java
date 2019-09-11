package net.eanfang.client.ui.activity.worksapce.monitor;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorSearchBinding;

/**
 * @author guanluocang
 * @data 2019/9/9  17:35
 * @description 搜索 实时监控
 */

public class MonitorSearchActivity extends BaseActivity {

    private ActivityMonitorSearchBinding monitorSearchBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorSearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_search);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("搜索");
        setLeftBack(true);
    }


    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
