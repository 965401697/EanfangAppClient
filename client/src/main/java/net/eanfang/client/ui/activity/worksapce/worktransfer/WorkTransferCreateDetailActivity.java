package net.eanfang.client.ui.activity.worksapce.worktransfer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eanfang.ui.base.BaseActivity;

import net.eanfang.client.R;

/**
 * @author Guanluocang
 * @date on 2018/7/27  15:45
 * @decision 创建交接班详情
 */
public class WorkTransferCreateDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_transfer_create_detail);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("交接班");
    }
}
