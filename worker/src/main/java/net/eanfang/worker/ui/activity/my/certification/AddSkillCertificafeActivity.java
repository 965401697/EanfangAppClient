package net.eanfang.worker.ui.activity.my.certification;

import android.os.Bundle;

import com.eanfang.ui.base.BaseActivityWithTakePhoto;

import net.eanfang.worker.R;

import butterknife.ButterKnife;

public class AddSkillCertificafeActivity extends BaseActivityWithTakePhoto {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_skill_certificafe);
        ButterKnife.bind(this);
        setTitle("技能资质");
        setLeftBack();
    }
}
