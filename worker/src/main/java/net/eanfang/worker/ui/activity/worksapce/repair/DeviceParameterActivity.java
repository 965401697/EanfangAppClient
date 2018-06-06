package net.eanfang.worker.ui.activity.worksapce.repair;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eanfang.ui.base.BaseActivity;

import net.eanfang.worker.R;

/**
 * @author Guanluocang
 * @date on 2018/6/5  18:37
 * @decision 设备参数
 */
public class DeviceParameterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_parameter);
        initView();
    }

    private void initView() {
        setTitle("设备参数");
        setLeftBack();
    }
}
