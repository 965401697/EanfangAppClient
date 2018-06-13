package net.eanfang.client.ui.activity.worksapce.contacts;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CooperactionRelationSubActivity extends BaseClientActivity {

    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperaction_relation_sub);
        ButterKnife.bind(this);
        setTitle("提交成功");
        setLeftBack();


        String name = getIntent().getStringExtra("companyName");
        if (!TextUtils.isEmpty(name)) {
            tvCompanyName.setText(name);
        }
    }
}
