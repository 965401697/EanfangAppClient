package net.eanfang.client.ui.activity.worksapce.monitor;

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
import net.eanfang.client.databinding.ActivityMonitorSearchBinding;
import net.eanfang.client.viewmodel.monitor.MonitorSearchViewModle;

import cn.hutool.core.util.StrUtil;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/9/9  17:35
 * @description 搜索 实时监控
 */

public class MonitorSearchActivity extends BaseActivity {

    private ActivityMonitorSearchBinding monitorSearchBinding;

    private static final int MSG_SEARCH = 100;
    @Setter
    @Accessors(chain = true)
    private MonitorSearchViewModle monitorViewModle;


    private String mChangeCompanyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorSearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_search);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("搜索");
        setLeftBack(true);
        mChangeCompanyId = getIntent().getStringExtra("mChangeCompanyId");
        monitorViewModle.mChangeCompanyId = mChangeCompanyId;
        monitorSearchBinding.etSerach.addTextChangedListener(new TextWatcher() {
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
        monitorViewModle = LViewModelProviders.of(this, MonitorSearchViewModle.class);
        monitorViewModle.setMonitorSearchBinding(monitorSearchBinding);
        monitorViewModle.initAdapter();
        return null;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String message = monitorSearchBinding.etSerach.getText().toString().trim();
            if (!StrUtil.isEmpty(message)) {
                monitorViewModle.doSearchDevice(message);
            }
        }
    };
}
