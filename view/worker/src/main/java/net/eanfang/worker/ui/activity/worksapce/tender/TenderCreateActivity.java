package net.eanfang.worker.ui.activity.worksapce.tender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;

import com.eanfang.base.BaseActivity;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2019/6/12
 * @description  用工发布
 */

public class TenderCreateActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tender_create);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
