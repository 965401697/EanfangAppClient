package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.my.PersonInfoActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginHintActivity extends BaseWorkerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_hint);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("完善资料");
        setLeftBack();
    }

    @OnClick(R.id.tv_go)
    public void onViewClicked() {
        startAnimActivity(new Intent(this, PersonInfoActivity.class));
        finishSelf();
    }
}
