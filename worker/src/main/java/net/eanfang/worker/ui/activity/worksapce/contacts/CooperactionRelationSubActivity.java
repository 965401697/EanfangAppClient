package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CooperactionRelationSubActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_desc)
    TextView tvDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperaction_relation_sub);
        ButterKnife.bind(this);
        setTitle("提交成功");
        setLeftBack();


        String name = getIntent().getStringExtra("companyName");
        String design = getIntent().getStringExtra("design");
        if (!TextUtils.isEmpty(name)) {
            tvCompanyName.setText(name);
        }

        if (!TextUtils.isEmpty(design)) {
            tvDesc.setText("你的完工申请已提交");
        }
    }
}
