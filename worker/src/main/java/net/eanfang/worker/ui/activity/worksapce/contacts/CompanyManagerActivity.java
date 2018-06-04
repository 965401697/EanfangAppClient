package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/5/8  18:09
 * @decision 企业管理
 */
public class CompanyManagerActivity extends BaseActivity {
    @BindView(R.id.rl_prefectInfo)
    RelativeLayout rlPrefectInfo;
    @BindView(R.id.rl_auth)
    RelativeLayout rlAuth;
    private Long mOrgId;
    private String mOrgName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_manager);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("企业管理");
        mOrgId = getIntent().getLongExtra("orgid", 0);
        mOrgName = getIntent().getStringExtra("orgName");
    }

    @OnClick({R.id.rl_prefectInfo, R.id.rl_auth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_prefectInfo:
                Bundle bundle_prefect = new Bundle();
                bundle_prefect.putLong("orgid", mOrgId);
                bundle_prefect.putString("orgName", mOrgName);
                JumpItent.jump(CompanyManagerActivity.this, AuthCompanyActivity.class, bundle_prefect);
                break;
            case R.id.rl_auth:
                Bundle bundle_auth = new Bundle();
                bundle_auth.putLong("orgid", mOrgId);
                JumpItent.jump(CompanyManagerActivity.this, AuthSystemTypeActivity.class, bundle_auth);

                break;
        }
    }
}
