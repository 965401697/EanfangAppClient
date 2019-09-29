package net.eanfang.client.ui.activity.worksapce.monitor.device;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2019/9/29  15:35
 * @description  编辑汇报
 */

public class MonitorDeviceReportActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_device_report);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("编辑汇报");
        setLeftBack(true);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
