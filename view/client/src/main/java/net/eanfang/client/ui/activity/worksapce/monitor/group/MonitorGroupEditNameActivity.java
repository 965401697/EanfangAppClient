package net.eanfang.client.ui.activity.worksapce.monitor.group;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.vo.MonitorUpdataVo;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorGroupEditNameBinding;
import net.eanfang.client.viewmodel.monitor.MonitorGroupEditNameViewModle;

/**
 * @author guanluocang
 * @data 2019/9/9  22:16
 * @description 设备分组 编辑等
 */

public class MonitorGroupEditNameActivity extends BaseActivity {

    private ActivityMonitorGroupEditNameBinding monitorGroupEditNameBinding;
    private MonitorGroupEditNameViewModle monitorGroupEditNameViewModle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorGroupEditNameBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_group_edit_name);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("分组名称");
        monitorGroupEditNameBinding.etGroupName.setText(getIntent().getStringExtra("groupName"));
        setRightClick("保存", null, (v) -> {
            monitorGroupEditNameViewModle.doSaveGroupInfo(getIntent().getLongExtra("groupId", 0));
        });
    }

    @Override
    protected ViewModel initViewModel() {
        monitorGroupEditNameViewModle = LViewModelProviders.of(this, MonitorGroupEditNameViewModle.class);
        monitorGroupEditNameViewModle.setMonitorGroupEditNameBinding(monitorGroupEditNameBinding);
        monitorGroupEditNameViewModle.getEditNameLiveData().observe(this, this::doEditSuccess);
        return monitorGroupEditNameViewModle;
    }

    private void doEditSuccess(MonitorUpdataVo monitorUpdataVo) {
        showToast("修改成功");
        Intent intent = new Intent();
        intent.putExtra("groupName", monitorUpdataVo.getGroupName().get());
        setResult(RESULT_OK, intent);
        finish();
    }
}
