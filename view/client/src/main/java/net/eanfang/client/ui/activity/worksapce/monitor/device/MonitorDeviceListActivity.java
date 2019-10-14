package net.eanfang.client.ui.activity.worksapce.monitor.device;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorDeviceListBinding;
import net.eanfang.client.viewmodel.device.MonitorDeviceListViewModle;

import cn.hutool.core.util.StrUtil;

/**
 * @author guanluocang
 * @data 2019/9/29  14:30
 * @description 设备详情 - 设备管理
 */

public class MonitorDeviceListActivity extends BaseActivity {

    private static final int MSG_SEARCH = 100;
    private ActivityMonitorDeviceListBinding monitorDeviceListBinding;
    protected MonitorDeviceListViewModle monitorDeviceListViewModle;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        monitorDeviceListBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_device_list);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setLeftBack(true);
        setTitle("设备列表");
        monitorDeviceListViewModle.mChangeCompanyId = getIntent().getStringExtra("mChangeCompanyId");
        monitorDeviceListViewModle.mGroupId = getIntent().getLongExtra("mLeftGroupId", 0);

        monitorDeviceListBinding.etSerach.addTextChangedListener(new TextWatcher() {
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
        monitorDeviceListViewModle = LViewModelProviders.of(this, MonitorDeviceListViewModle.class);
        monitorDeviceListViewModle.setMonitorDeviceListBinding(monitorDeviceListBinding);
        monitorDeviceListViewModle.initAdapter();
        return monitorDeviceListViewModle;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String message = monitorDeviceListBinding.etSerach.getText().toString().trim();
            if (!StrUtil.isEmpty(message)) {
                monitorDeviceListViewModle.doSearchDevice(message);
            }
        }
    };
}
