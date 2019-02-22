package net.eanfang.worker.ui.activity.worksapce.security;

import android.os.Bundle;

import com.eanfang.ui.base.BaseActivity;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2019/2/15
 * @description 安防圈详情
 */

public class SecurityDetailActivity extends BaseActivity {

    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_detail);
        initView();
    }

    private void initView() {
        mId = getIntent().getStringExtra("id");
    }
}
