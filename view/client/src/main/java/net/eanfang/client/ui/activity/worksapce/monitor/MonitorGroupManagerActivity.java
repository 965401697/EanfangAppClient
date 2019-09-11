package net.eanfang.client.ui.activity.worksapce.monitor;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorGroupManagerBinding;
import net.eanfang.client.viewmodel.MonitorViewModle;

/**
 * @author guanluocang
 * @data 2019/9/9  18:46
 * @description 分组管理
 */

public class MonitorGroupManagerActivity extends BaseActivity {

    private ActivityMonitorGroupManagerBinding groupManagerBinding;
    private MonitorViewModle monitorViewModle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        groupManagerBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_group_manager);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setLeftBack(true);
        setTitle("分组管理");
        setRightClick(R.mipmap.add,(v)->{

        });
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
