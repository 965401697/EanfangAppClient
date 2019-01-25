package net.eanfang.worker.ui.activity.worksapce.online;

import android.os.Bundle;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.ButterKnife;

public class ExpertAnswerActivity extends BaseWorkerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_answer);
        ButterKnife.bind(this);
        setTitle("专家回复");
        setLeftBack();
    }
}
