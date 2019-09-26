package net.eanfang.client.ui.activity.worksapce.monitor.group;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.vo.MonitorDeleteVo;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorGroupEditDeviceBinding;
import net.eanfang.client.viewmodel.monitor.MonitorGroupEditDeviceViewModle;

/**
 * @author guanluocang
 * @data 2019/9/24  19:13
 * @description 分组编辑设备
 */

public class MonitorGroupEditDeviceActivity extends BaseActivity {

    private ActivityMonitorGroupEditDeviceBinding monitorGroupEditDeviceBinding;
    private MonitorGroupEditDeviceViewModle monitorGroupEditDeviceViewModle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorGroupEditDeviceBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_group_edit_device);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("编辑分组设备");
        setLeftBack(true);
        setRightClick("保存", (v) -> {
            monitorGroupEditDeviceViewModle.doSaveDelete();
        });
        monitorGroupEditDeviceViewModle.initAdapter();
        monitorGroupEditDeviceViewModle.mConfigId = getIntent().getStringExtra("configId");
        monitorGroupEditDeviceViewModle.mGroupId = getIntent().getStringExtra("groupId");
        monitorGroupEditDeviceViewModle.mCompanyId = getIntent().getStringExtra("mCompanyId");

        /**
         * 添加设备
         * */
        monitorGroupEditDeviceBinding.tvCreate.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("mCompanyId", getIntent().getStringExtra("mCompanyId"));
            bundle.putString("mGroupId", getIntent().getStringExtra("groupId"));
            JumpItent.jump(this, MonitorGroupAddDeviceActivity.class, bundle);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        monitorGroupEditDeviceViewModle.doGetRightDeviceList(1);
    }

    @Override
    protected ViewModel initViewModel() {
        monitorGroupEditDeviceViewModle = LViewModelProviders.of(this, MonitorGroupEditDeviceViewModle.class);
        monitorGroupEditDeviceViewModle.setMonitorGroupEditDeviceBinding(monitorGroupEditDeviceBinding);
        monitorGroupEditDeviceViewModle.getMonitorDeleteVoMutableLiveData().observe(this, this::deleteSuccess);
        return monitorGroupEditDeviceViewModle;
    }

    private void deleteSuccess(MonitorDeleteVo monitorDeleteVo) {
        showToast("删除成功");
        finish();
    }
}
