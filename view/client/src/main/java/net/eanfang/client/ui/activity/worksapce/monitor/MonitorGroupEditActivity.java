package net.eanfang.client.ui.activity.worksapce.monitor;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;

import net.eanfang.client.R;
/**
 * @author guanluocang
 * @data 2019/9/9  22:16
 * @description  设备分组 编辑等
 */

public class MonitorGroupEditActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_group_edit);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
