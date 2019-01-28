package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
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

    @OnClick({R.id.tv_search,R.id.rl_free_ask, R.id.rl_find_expert})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                Intent i = new Intent(ExpertOnlineActivity.this, CommentFaultSearchActivity.class);
                startActivity(i);
                break;
            case R.id.rl_free_ask:
                Intent intent = new Intent(ExpertOnlineActivity.this, FreeAskActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_find_expert:
                Intent in = new Intent(ExpertOnlineActivity.this, ExpertListActivity.class);
                startActivity(in);
                break;
        }
    }
}
