package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.contacts.CreatTeamActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OwnDataHintActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_go)
    TextView tvGo;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_service)
    TextView tvService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_data_hint);
        ButterKnife.bind(this);
        setTitle("提交成功");
        setLeftBack();


        String info = getIntent().getStringExtra("info");
        String go = getIntent().getStringExtra("go");
        String desc = getIntent().getStringExtra("desc");
        String service = getIntent().getStringExtra("service");

        if (!TextUtils.isEmpty(info)) {
            tvInfo.setText(info);
            tvGo.setText(go);
            tvDesc.setText(desc);
            tvService.setText(service);
        }

        findViewById(R.id.tv_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OwnDataHintActivity.this, CreatTeamActivity.class));
                finishSelf();
            }
        });
    }
}
