package net.eanfang.client.ui.activity.worksapce.monitor.group;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.vo.MonitorUpdataVo;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorGroupSelectTopBinding;
import net.eanfang.client.viewmodel.monitor.MonitorGroupSelectTopViewModle;

/**
 * @author guanluocang
 * @data 2019/9/25  11:36
 * @description 选择上级分组
 */

public class MonitorGroupSelectTopActivity extends BaseActivity {

    private ActivityMonitorGroupSelectTopBinding monitorGroupSelectTopBinding;

    private MonitorGroupSelectTopViewModle monitorGroupSelectTopViewModle;

    private boolean mEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorGroupSelectTopBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_group_select_top);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("上级分组");
        setLeftBack(true);

        mEdit = getIntent().getBooleanExtra("mEdit", false);
        monitorGroupSelectTopBinding.tvCompanyName.setText(getIntent().getStringExtra("mChangeCompanyName"));
        setRightClick("保存", (v) -> {
            if (mEdit) {
                monitorGroupSelectTopViewModle.doSaveTopGroupInfo(getIntent().getLongExtra("groupId", 0));
            } else {
                Intent intent = new Intent();
                intent.putExtra("groupId", monitorGroupSelectTopViewModle.mTopGroupId);
                intent.putExtra("groupName", monitorGroupSelectTopViewModle.mTopGroupName);
                setResult(RESULT_OK, intent);
                finish();
            }

        });
        monitorGroupSelectTopViewModle.doGetGroupList(getIntent().getStringExtra("mCompanyId"));
    }

    @Override
    protected ViewModel initViewModel() {
        monitorGroupSelectTopViewModle = LViewModelProviders.of(this, MonitorGroupSelectTopViewModle.class);
        monitorGroupSelectTopViewModle.setMonitorGroupSelectTopBinding(monitorGroupSelectTopBinding);
        monitorGroupSelectTopViewModle.getEditTopGroupLiveData().observe(this, this::updateTopGroupSuccess);
        return monitorGroupSelectTopViewModle;
    }

    private void updateTopGroupSuccess(MonitorUpdataVo monitorUpdataVo) {
        showToast("修改成功");
        Intent intent = new Intent();
        intent.putExtra("groupId", monitorGroupSelectTopViewModle.mTopGroupId);
        intent.putExtra("groupName", monitorGroupSelectTopViewModle.mTopGroupName);
        setResult(RESULT_OK, intent);
        finish();
    }
}
