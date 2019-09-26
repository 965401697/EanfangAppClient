package net.eanfang.client.ui.activity.worksapce.monitor.device;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorDeviceDetailBinding;

/**
 * @author guanluocang
 * @data 2019/9/11  16:27
 * @description 摄像机详情页
 */

public class MonitorDeviceDetailActivity extends BaseActivity {

    private ActivityMonitorDeviceDetailBinding monitorDeviceDetailBinding;

    private String mDeviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorDeviceDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_device_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setLeftBack(true);
        mDeviceName = getIntent().getStringExtra("mDeviceName");
        setTitle(mDeviceName);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
