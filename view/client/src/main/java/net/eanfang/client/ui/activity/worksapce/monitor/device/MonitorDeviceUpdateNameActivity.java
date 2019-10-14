package net.eanfang.client.ui.activity.worksapce.monitor.device;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.vo.MonitorUpdataVo;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorDeviceUpdateNameBinding;
import net.eanfang.client.viewmodel.device.MonitorDeviceEditNameViewModle;

/**
 * @author guanluocang
 * @data 2019/10/12  13:50
 * @description 修改设备名称
 */

public class MonitorDeviceUpdateNameActivity extends BaseActivity {


    private ActivityMonitorDeviceUpdateNameBinding monitorDeviceUpdateNameBinding;
    private MonitorDeviceEditNameViewModle monitorGroupEditNameViewModle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorDeviceUpdateNameBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_device_update_name);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("设备名称");
        setLeftBack(true);
        monitorDeviceUpdateNameBinding.etGroupName.setText(getIntent().getStringExtra("deviceName"));
        setRightClick("保存", null, (v) -> {
            monitorGroupEditNameViewModle.doSaveDeviceInfo(getIntent().getStringExtra("deviceId"));
        });
    }

    @Override
    protected ViewModel initViewModel() {
        monitorGroupEditNameViewModle = LViewModelProviders.of(this, MonitorDeviceEditNameViewModle.class);
        monitorGroupEditNameViewModle.setMonitorGroupEditNameBinding(monitorDeviceUpdateNameBinding);
        monitorGroupEditNameViewModle.getEditNameLiveData().observe(this, this::doEditSuccess);
        return monitorGroupEditNameViewModle;
    }

    private void doEditSuccess(MonitorUpdataVo monitorUpdataVo) {
        showToast("修改成功");
        Intent intent = new Intent();
        intent.putExtra("deviceName", monitorUpdataVo.getGroupName().get());
        setResult(RESULT_OK, intent);
        finish();
    }
}
