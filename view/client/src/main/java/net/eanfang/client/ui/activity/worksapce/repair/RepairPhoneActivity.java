package net.eanfang.client.ui.activity.worksapce.repair;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2019/8/12  18:47
 * @description 电话报修
 */

public class RepairPhoneActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_phone);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
