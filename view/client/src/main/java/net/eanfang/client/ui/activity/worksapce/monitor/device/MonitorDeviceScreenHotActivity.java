package net.eanfang.client.ui.activity.worksapce.monitor.device;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2019/9/29  17:09
 * @description  截图
 */

public class MonitorDeviceScreenHotActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_device_screen_hot);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("视频截图");
        setLeftBack(true);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
