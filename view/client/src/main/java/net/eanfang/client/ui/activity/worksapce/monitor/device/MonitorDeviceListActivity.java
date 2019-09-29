package net.eanfang.client.ui.activity.worksapce.monitor.device;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorDeviceListBinding;
/**
 * @author guanluocang
 * @data 2019/9/29  14:30
 * @description  设备详情 - 设备管理
 */

public class MonitorDeviceListActivity extends BaseActivity {

    private ActivityMonitorDeviceListBinding monitorDeviceListBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_device_list);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
