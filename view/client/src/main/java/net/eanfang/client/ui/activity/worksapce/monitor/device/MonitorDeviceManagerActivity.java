package net.eanfang.client.ui.activity.worksapce.monitor.device;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.BuildConfig;
import com.eanfang.base.BaseActivity;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorDeviceManagerBinding;

/**
 * @author guanluocang
 * @data 2019/9/11  16:15
 * @description 编辑分组 添加新设备
 */

public class MonitorDeviceManagerActivity extends BaseActivity {

    private ActivityMonitorDeviceManagerBinding monitorDeviceManagerBinding;

    private static final int REQUEST_UPDATE_NAME = 1001;
    private static final int REQUEST_UPDATE_GROUP = 1002;

    private boolean isChangeName = false;
    private String mDeviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorDeviceManagerBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_device_manager);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setLeftBack((v) -> {
            if (isChangeName) {
                Intent intent = new Intent();
                intent.putExtra("deviceName", mDeviceName);
                setResult(RESULT_OK, intent);
            }
            finish();
        });
        setTitle("设备管理");
        // 截图
        GlideUtil.intoImageView(MonitorDeviceManagerActivity.this, Uri.parse(BuildConfig.OSS_SERVER + getIntent().getStringExtra("imagePath")), monitorDeviceManagerBinding.ivThumbnail);
        // 设备名称
        monitorDeviceManagerBinding.tvDevicesName.setText(getIntent().getStringExtra("deviceName"));
        // 设备分组
        monitorDeviceManagerBinding.tvDeviceGroup.setText(getIntent().getStringExtra("deviceGroupName"));
        // 序列号
        monitorDeviceManagerBinding.tvDeviceSerial.setText(getIntent().getStringExtra("deviceSerial"));

        monitorDeviceManagerBinding.rlEditDeviceName.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("deviceName", getIntent().getStringExtra("deviceName"));
            bundle.putString("deviceId", getIntent().getStringExtra("deviceId"));
            JumpItent.jump(this, MonitorDeviceUpdateNameActivity.class, bundle, REQUEST_UPDATE_NAME);
        });
        monitorDeviceManagerBinding.rlDeviceGroup.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("deviceId", getIntent().getStringExtra("deviceId"));
            bundle.putLong("groupId", getIntent().getLongExtra("groupId", 0));
            bundle.putString("companyId", getIntent().getStringExtra("companyId"));
            JumpItent.jump(this, MonitorDeviceUpdateGroupActivity.class,bundle, REQUEST_UPDATE_GROUP);
        });
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    public void onBackPressed() {
        if (isChangeName) {
            Intent intent = new Intent();
            intent.putExtra("deviceName", mDeviceName);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_UPDATE_NAME:
                isChangeName = true;
                mDeviceName = data.getStringExtra("deviceName");
                monitorDeviceManagerBinding.tvDevicesName.setText(mDeviceName);
                break;
            case REQUEST_UPDATE_GROUP:
                monitorDeviceManagerBinding.tvDeviceGroup.setText(data.getStringExtra("groupName"));
                break;
            default:
                break;
        }
    }
}
