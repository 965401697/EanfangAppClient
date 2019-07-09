package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.my.PersonInfoActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginHintActivity extends BaseClientActivity {


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
