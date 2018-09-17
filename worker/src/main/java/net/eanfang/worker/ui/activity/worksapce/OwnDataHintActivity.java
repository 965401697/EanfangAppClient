package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.contacts.CreatTeamActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;


public class OwnDataHintActivity extends BaseWorkerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_data_hint);
        setTitle("提交成功");
        setLeftBack();

        findViewById(R.id.tv_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OwnDataHintActivity.this, CreatTeamActivity.class));
                finishSelf();
            }
        });
    }
}
