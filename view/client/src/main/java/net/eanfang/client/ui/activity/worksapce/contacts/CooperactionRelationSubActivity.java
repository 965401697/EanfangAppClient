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
    @BindView(R.id.tv_status)
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cooperaction_relation_sub);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("提交成功");
        setLeftBack();


        String name = getIntent().getStringExtra("companyName");
        String status = getIntent().getStringExtra("status");
        if (!TextUtils.isEmpty(name)) {
            tvCompanyName.setText(name);
        }
        if (!TextUtils.isEmpty(status)) {
            tvStatus.setText("您已经解绑成功");
        }
    }
}
