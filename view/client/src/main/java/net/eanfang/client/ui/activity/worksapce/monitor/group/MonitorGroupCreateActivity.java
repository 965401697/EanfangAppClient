package net.eanfang.client.ui.activity.worksapce.monitor.group;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.vo.MonitorUpdataVo;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorGroupCreateBinding;
import net.eanfang.client.viewmodel.monitor.MonitorGroupCreatViewModle;

/**
 * @author guanluocang
 * @data 2019/9/17  17:17
 * @description 分组管理 创建分组
 */

public class MonitorGroupCreateActivity extends BaseActivity {

    private ActivityMonitorGroupCreateBinding monitorGroupCreateBinding;
    private MonitorGroupCreatViewModle monitorGroupCreatViewModle;

    private static final int REQUEST_SELECT_TOP = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorGroupCreateBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_group_create);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setLeftBack(true);
        setTitle("创建分组");

        monitorGroupCreateBinding.rlTopGroupName.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("mCompanyId", getIntent().getStringExtra("mChangeCompanyId"));
            JumpItent.jump(this, MonitorGroupSelectTopActivity.class, bundle, REQUEST_SELECT_TOP);
        });
    }

    @Override
    protected ViewModel initViewModel() {
        monitorGroupCreatViewModle = LViewModelProviders.of(this, MonitorGroupCreatViewModle.class);
        monitorGroupCreateBinding.setViewModle(monitorGroupCreatViewModle);
        monitorGroupCreatViewModle.setMonitorGroupCreateBinding(monitorGroupCreateBinding);
        monitorGroupCreatViewModle.getMonitorUpdataVoMutableLiveData().observe(this, this::doCreateSuccess);
        return monitorGroupCreatViewModle;
    }

    private void doCreateSuccess(MonitorUpdataVo monitorUpdataVo) {
        showToast("创建成功");
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == REQUEST_SELECT_TOP) {
            monitorGroupCreateBinding.tvGroupTopName.setText(data.getStringExtra("groupName"));
            monitorGroupCreatViewModle.mTopGroupName = String.valueOf(data.getStringExtra("groupName"));
            monitorGroupCreatViewModle.mTopGroupId = String.valueOf(data.getLongExtra("groupId", 0));
        }
    }

}
