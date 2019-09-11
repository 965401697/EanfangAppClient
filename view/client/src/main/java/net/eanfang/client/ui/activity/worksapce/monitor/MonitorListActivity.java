package net.eanfang.client.ui.activity.worksapce.monitor;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorListBinding;

/**
 * @author guanluocang
 * @data 2019/9/9  10:21
 * @description 实时监控 列表
 */

public class MonitorListActivity extends BaseActivity {

    private ActivityMonitorListBinding monitorListBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorListBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_list);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("实时监控");
        setLeftBack(true);
        setRightClick(R.mipmap.ic_search_white,(v)->{
            
        });
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
