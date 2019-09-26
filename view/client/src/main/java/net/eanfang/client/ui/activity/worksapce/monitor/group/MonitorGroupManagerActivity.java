package net.eanfang.client.ui.activity.worksapce.monitor.group;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorGroupManagerBinding;
import net.eanfang.client.viewmodel.monitor.MonitorGroupManagerViewModle;

/**
 * @author guanluocang
 * @data 2019/9/9  18:46
 * @description 分组管理
 */

public class MonitorGroupManagerActivity extends BaseActivity {

    private ActivityMonitorGroupManagerBinding groupManagerBinding;
    private MonitorGroupManagerViewModle monitorGroupManagerViewModle;
    private String mChangeCompanyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        groupManagerBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_group_manager);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        monitorGroupManagerViewModle.doGetGroupList(mChangeCompanyId);
    }

    @Override
    protected void initView() {
        super.initView();
        setLeftBack(true);
        setTitle("分组管理");
        mChangeCompanyId = getIntent().getStringExtra("mChangeCompanyId");
        setRightClick(R.mipmap.add, (v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("mChangeCompanyId", getIntent().getStringExtra("mChangeCompanyId"));
            JumpItent.jump(this, MonitorGroupCreateActivity.class, bundle);
        });
    }

    @Override
    protected ViewModel initViewModel() {
        monitorGroupManagerViewModle = LViewModelProviders.of(this, MonitorGroupManagerViewModle.class);
        monitorGroupManagerViewModle.setGroupManagerBinding(groupManagerBinding);
        return monitorGroupManagerViewModle;
    }
}
