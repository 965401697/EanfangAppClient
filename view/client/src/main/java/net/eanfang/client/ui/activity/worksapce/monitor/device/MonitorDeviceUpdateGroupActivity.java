package net.eanfang.client.ui.activity.worksapce.monitor.device;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.vo.MonitorUpdataVo;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorDeviceUpdateGroupBinding;
import net.eanfang.client.viewmodel.monitor.MonitorDeviceUpdateGroupViewModle;

/**
 * @author guanluocang
 * @data 2019/10/12  15:41
 * @description 设备修改上级分组
 */

public class MonitorDeviceUpdateGroupActivity extends BaseActivity {

    private ActivityMonitorDeviceUpdateGroupBinding monitorDeviceUpdateGroupBinding;

    private MonitorDeviceUpdateGroupViewModle monitorDeviceUpdateGroupViewModle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorDeviceUpdateGroupBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_device_update_group);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("上级分组");
        setLeftBack(true);

        monitorDeviceUpdateGroupViewModle.mDeviceId = getIntent().getStringExtra("deviceId");
        monitorDeviceUpdateGroupViewModle.doGetGroupList(getIntent().getStringExtra("companyId"));
        setRightClick("保存", (v) -> {
            monitorDeviceUpdateGroupViewModle.doSaveTopGroupInfo(monitorDeviceUpdateGroupViewModle.mTopGroupId);
        });
    }

    @Override
    protected ViewModel initViewModel() {
        monitorDeviceUpdateGroupViewModle = LViewModelProviders.of(this, MonitorDeviceUpdateGroupViewModle.class);
        monitorDeviceUpdateGroupViewModle.setMonitorDeviceUpdateGroupBinding(monitorDeviceUpdateGroupBinding);
        monitorDeviceUpdateGroupViewModle.getEditTopGroupLiveData().observe(this, this::updateTopGroupSuccess);
        return monitorDeviceUpdateGroupViewModle;
    }

    private void updateTopGroupSuccess(MonitorUpdataVo monitorUpdataVo) {
        showToast("修改成功");
        Intent intent = new Intent();
        intent.putExtra("groupName", monitorDeviceUpdateGroupViewModle.mTopGroupName);
        setResult(RESULT_OK, intent);
        finish();
    }
}
