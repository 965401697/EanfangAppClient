package net.eanfang.client.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreatTeamStatusHintActivity extends BaseClientActivity {

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
        setContentView(R.layout.activity_creat_team_status_hint);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("提交成功");
        setLeftBack();

        findViewById(R.id.tv_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CreatTeamStatusHintActivity.this, AuthCompanyFirstActivity.class).
                        putExtra("orgName", getIntent().getStringExtra("orgName")).putExtra("orgid", getIntent().getLongExtra("orgid", 0)));
                finishSelf();
            }
        });
    }
}
