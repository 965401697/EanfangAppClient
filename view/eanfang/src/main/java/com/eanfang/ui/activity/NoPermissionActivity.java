package com.eanfang.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.R;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;

public class NoPermissionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_no_permission);
        setTitle("无权访问");
        setLeftBack();

        ((TextView) findViewById(R.id.tv_info)).setText(getIntent().getStringExtra("info"));

        String type = EanfangHttp.getHttp().getCommonHeaders().get("Request-From");
        if (!type.equals("CLIENT")) {
            findViewById(R.id.ll_footer).setBackgroundColor(Color.parseColor("#F7FAFF"));
            ((ImageView) findViewById(R.id.iv_logo)).setImageDrawable(getResources().getDrawable(R.drawable.worker_logo));
            ((TextView) findViewById(R.id.tv_desc)).setText("安防监控施工过程管理神器");
        }
        findViewById(R.id.tv_know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
