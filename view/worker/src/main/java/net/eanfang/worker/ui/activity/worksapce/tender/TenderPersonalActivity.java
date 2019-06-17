package net.eanfang.worker.ui.activity.worksapce.tender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;

import com.eanfang.base.BaseActivity;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2019/6/13
 * @description 招标用工大厅 个人中心
 */

public class TenderPersonalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tender_personal);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
