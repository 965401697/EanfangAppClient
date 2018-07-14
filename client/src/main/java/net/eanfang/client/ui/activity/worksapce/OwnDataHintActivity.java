package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.contacts.CreatTeamActivity;
import net.eanfang.client.ui.base.BaseClientActivity;


public class OwnDataHintActivity extends BaseClientActivity {

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
