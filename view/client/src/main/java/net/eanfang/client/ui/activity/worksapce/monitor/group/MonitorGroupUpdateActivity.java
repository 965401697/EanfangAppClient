package net.eanfang.client.ui.activity.worksapce.monitor.group;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorGroupUpdateBinding;

/**
 * @author guanluocang
 * @data 2019/9/17  18:00
 * @description 分组管理 分组设置 修改上级分组
 */

public class MonitorGroupUpdateActivity extends BaseActivity {

    private ActivityMonitorGroupUpdateBinding monitorGroupUpdateBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorGroupUpdateBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_group_update);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("上级分组");
        setLeftBack(true);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }


}
