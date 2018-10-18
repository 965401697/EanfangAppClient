package net.eanfang.worker.ui.activity.my.certification;

import android.os.Bundle;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.ButterKnife;

/**
 * 技能资质的区域
 */
public class SkillAreaActivity extends BaseWorkerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_area);
        ButterKnife.bind(this);
        setTitle("技能资质");
        setLeftBack();
    }
}
