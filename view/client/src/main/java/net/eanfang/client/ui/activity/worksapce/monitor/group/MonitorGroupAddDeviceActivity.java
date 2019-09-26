package net.eanfang.client.ui.activity.worksapce.monitor.group;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.vo.MonitorDeleteVo;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorGroupAddDeviceBinding;
import net.eanfang.client.viewmodel.monitor.MonitorGroupAddDeviceViewModle;

import cn.hutool.core.util.StrUtil;

/**
 * @author guanluocang
 * @data 2019/9/24  19:13
 * @description 分组添加设备
 */

public class MonitorGroupAddDeviceActivity extends BaseActivity {

    private ActivityMonitorGroupAddDeviceBinding monitorGroupAddDeviceBinding;
    private MonitorGroupAddDeviceViewModle monitorGroupAddDeviceViewModle;

    private static final int MSG_SEARCH = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorGroupAddDeviceBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_group_add_device);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setLeftBack(true);
        setTitle("添加设备");
        setRightClick("保存", (v) -> {
            monitorGroupAddDeviceViewModle.doAddDevice();
        });
        monitorGroupAddDeviceViewModle.initAdapter();
        monitorGroupAddDeviceViewModle.mCompanyId = getIntent().getStringExtra("mCompanyId");
        monitorGroupAddDeviceViewModle.mGroupId = getIntent().getStringExtra("mGroupId");
        monitorGroupAddDeviceViewModle.doGetDeviceList(1, "");

        monitorGroupAddDeviceBinding.etSerach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //文字变动 ， 有未发出的搜索请求，应取消
                if (mHandler.hasMessages(MSG_SEARCH)) {
                    mHandler.removeMessages(MSG_SEARCH);
                }
                //否则延迟500ms开始搜索
                mHandler.sendEmptyMessageDelayed(MSG_SEARCH, 1500);
            }
        });
    }

    @Override
    protected ViewModel initViewModel() {
        monitorGroupAddDeviceViewModle = LViewModelProviders.of(this, MonitorGroupAddDeviceViewModle.class);
        monitorGroupAddDeviceViewModle.setMonitorGroupAddDeviceBinding(monitorGroupAddDeviceBinding);
        monitorGroupAddDeviceViewModle.getMonitorDeleteVoMutableLiveData().observe(this,this::addDeviceSuccess);
        return monitorGroupAddDeviceViewModle;
    }

    private void addDeviceSuccess(MonitorDeleteVo monitorDeleteVo) {
        finish();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String message = monitorGroupAddDeviceBinding.etSerach.getText().toString().trim();
            monitorGroupAddDeviceViewModle.mDeviceName = message;
            if (!StrUtil.isEmpty(message)) {
                monitorGroupAddDeviceViewModle.doGetDeviceList(1, message);
            }
        }
    };
}
