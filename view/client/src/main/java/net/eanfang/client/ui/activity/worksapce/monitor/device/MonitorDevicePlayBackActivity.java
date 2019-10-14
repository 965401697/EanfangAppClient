package net.eanfang.client.ui.activity.worksapce.monitor.device;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorDevicePlayBackBinding;

/**
 * @author guanluocang
 * @data 2019/9/29  17:18
 * @description 会放
 */

public class MonitorDevicePlayBackActivity extends BaseActivity {

    private ActivityMonitorDevicePlayBackBinding monitorDevicePlayBackBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorDevicePlayBackBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_device_play_back);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("视频回访");
        setLeftBack(true);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
