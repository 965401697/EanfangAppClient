package net.eanfang.worker.ui.activity.worksapce.online;

import android.os.Bundle;
import android.view.View;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 专家在线
 */
public class ExpertOnlineActivity extends BaseWorkerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_online);
        ButterKnife.bind(this);
        setTitle("专家在线");
        setLeftBack();
    }

    @OnClick({R.id.rl_free_ask, R.id.rl_find_expert})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_free_ask:
                break;
            case R.id.rl_find_expert:
                break;
        }
    }
}
