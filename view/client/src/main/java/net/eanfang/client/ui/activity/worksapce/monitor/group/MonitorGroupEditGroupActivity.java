package net.eanfang.client.ui.activity.worksapce.monitor.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorGroupEditGroupBinding;
import net.eanfang.client.viewmodel.monitor.MonitorGroupEditViewModle;

import org.json.JSONObject;

/**
 * @author guanluocang
 * @data 2019/9/17  19:16
 * @description 分组管理 分组设置 修改上级分组
 */

public class MonitorGroupEditGroupActivity extends BaseActivity {

    private ActivityMonitorGroupEditGroupBinding monitorGroupEditGroupBinding;

    private MonitorGroupEditViewModle monitorGroupEditViewModle;

    private static final int REQUEST_UPDATENAME = 1001;
    private static final int REQUEST_SELECT_TOP = 1002;

    /**
     * 是否有二级分组
     */
    private boolean isHaveSubGroup = false;
    /**
     * 一级下面是否有设备
     */
    private boolean isHaveDeviceList = false;
    /**
     * 是否是二级
     */
    private boolean isSecond = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorGroupEditGroupBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_group_edit_group);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("分组设置");
        setLeftBack(true);

        monitorGroupEditGroupBinding.tvGroupName.setText(getIntent().getStringExtra("groupName"));
        monitorGroupEditGroupBinding.tvTopGroupName.setText(getIntent().getStringExtra("topGroupName"));
        monitorGroupEditGroupBinding.tvDeviceCount.setText(getIntent().getIntExtra("deviceCount", 0) + "个设备");
        monitorGroupEditViewModle.mGroupId = String.valueOf(getIntent().getLongExtra("groupId", 0));
        isHaveSubGroup = getIntent().getBooleanExtra("isHaveSubGroup", false);
        isHaveDeviceList = getIntent().getBooleanExtra("isHaveDeviceList", false);
        isSecond = getIntent().getBooleanExtra("isSecond", false);

        /**
         * 分组名称
         * */
        monitorGroupEditGroupBinding.rlGroup.setOnClickListener((v) -> {
            Bundle bundleGroupName = new Bundle();
            bundleGroupName.putString("groupName", getIntent().getStringExtra("groupName"));
            bundleGroupName.putLong("groupId", getIntent().getLongExtra("groupId", 0));
            JumpItent.jump(this, MonitorGroupEditNameActivity.class, bundleGroupName, REQUEST_UPDATENAME);
        });

        /**
         *上级分组
         * */
        monitorGroupEditGroupBinding.rlTopGroup.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("mCompanyId", String.valueOf(getIntent().getStringExtra("mCompanyId")));
            bundle.putBoolean("mEdit", true);
            bundle.putLong("groupId", getIntent().getLongExtra("groupId", 0));
            JumpItent.jump(this, MonitorGroupSelectTopActivity.class, bundle, REQUEST_SELECT_TOP);
        });
        /**
         * 分组设备
         * */
        monitorGroupEditGroupBinding.rlDevice.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("groupId", String.valueOf(getIntent().getLongExtra("groupId", 0)));
            bundle.putString("configId", String.valueOf(getIntent().getLongExtra("configId", 0)));
            bundle.putString("mCompanyId", getIntent().getStringExtra("mCompanyId"));
            JumpItent.jump(this, MonitorGroupEditDeviceActivity.class, bundle);
        });

        /**
         * 没有子分组 没有设备
         * */
        if (!isHaveSubGroup && !isHaveDeviceList) {
            monitorGroupEditGroupBinding.rlTopGroup.setVisibility(View.GONE);
            monitorGroupEditGroupBinding.rlDevice.setVisibility(View.GONE);
        } else if (!isHaveSubGroup && isHaveDeviceList) {
            monitorGroupEditGroupBinding.rlTopGroup.setVisibility(View.GONE);
        } else if (isHaveSubGroup) {
            monitorGroupEditGroupBinding.rlTopGroup.setVisibility(View.GONE);
            monitorGroupEditGroupBinding.rlDevice.setClickable(false);
            monitorGroupEditGroupBinding.rlDevice.setFocusable(false);
        }
        if (isSecond) {
            monitorGroupEditGroupBinding.rlTopGroup.setVisibility(View.VISIBLE);
            monitorGroupEditGroupBinding.rlDevice.setVisibility(View.VISIBLE);
            monitorGroupEditGroupBinding.rlDevice.setClickable(true);
            monitorGroupEditGroupBinding.rlDevice.setFocusable(true);
        }
    }

    @Override
    protected ViewModel initViewModel() {
        monitorGroupEditViewModle = LViewModelProviders.of(this, MonitorGroupEditViewModle.class);
        monitorGroupEditGroupBinding.setViewModle(monitorGroupEditViewModle);
        monitorGroupEditViewModle.setMonitorGroupEditGroupBinding(monitorGroupEditGroupBinding);
        monitorGroupEditViewModle.getEditNameLiveData().observe(this, this::doDeleteSucess);
        return null;
    }

    private void doDeleteSucess(JSONObject jsonObject) {
        showToast("删除成功");
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_UPDATENAME:
                monitorGroupEditGroupBinding.tvGroupName.setText(data.getStringExtra("groupName"));
                break;
            case REQUEST_SELECT_TOP:
                monitorGroupEditGroupBinding.tvTopGroupName.setText(data.getStringExtra("groupName"));
                break;
            default:
                break;
        }
    }


}
