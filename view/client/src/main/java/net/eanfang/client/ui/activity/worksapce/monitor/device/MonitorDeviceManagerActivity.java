package net.eanfang.client.ui.activity.worksapce.monitor.device;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorDeviceManagerBinding;

/**
 * @author guanluocang
 * @data 2019/9/11  16:15
 * @description 编辑分组 添加新设备
 */

public class MonitorDeviceManagerActivity extends BaseActivity {

    private ActivityMonitorDeviceManagerBinding monitorDeviceManagerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorDeviceManagerBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_device_manager);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
