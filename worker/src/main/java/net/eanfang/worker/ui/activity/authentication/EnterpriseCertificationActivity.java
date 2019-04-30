package net.eanfang.worker.ui.activity.authentication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify.AuthQualifyFirstActivity;
import net.eanfang.worker.ui.widget.WQLeftRightClickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author WQ
 */
public class EnterpriseCertificationActivity extends BaseActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @BindView(R.id.gs_rz_wq_tv)
    WQLeftRightClickTextView gsRzWqTv;
    @BindView(R.id.fw_rz_wq_tv)
    WQLeftRightClickTextView fwRzWqTv;
    @BindView(R.id.zz_ry_wq_tv)
    WQLeftRightClickTextView zzRyWqTv;
    @BindView(R.id.gd_nl_wq_tv)
    WQLeftRightClickTextView gdNlWqTv;
    private Long mOrgId;
    private int status = 0;
    private String mOrgName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_certification);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setLeftBack();
        setTitle("企业认证");
    }

    private void initData() {
        mOrgId = getIntent().getLongExtra("mOrgId", 0);
        status = getIntent().getIntExtra("status", 0);
        mOrgName = getIntent().getStringExtra("orgName");


    }

    @OnClick({R.id.gs_rz_wq_tv, R.id.fw_rz_wq_tv, R.id.zz_ry_wq_tv, R.id.gd_nl_wq_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gs_rz_wq_tv:
                Intent intent = new Intent(this, BusinessCertificationActivity.class);
                intent.putExtra("mOrgId", mOrgId);
                intent.putExtra("status", status);
                startActivity(intent);
                break;
            case R.id.fw_rz_wq_tv:
                Bundle bundle_prefect = new Bundle();
                bundle_prefect.putLong("orgid", mOrgId);
                if (!PermKit.get().getWorkerCompanyVerifyPerm()) {
                    return;
                } else {
                    JumpItent.jump(this, AuthQualifyFirstActivity.class, bundle_prefect);

                }
                break;
            case R.id.zz_ry_wq_tv:
                Intent intentb = new Intent(this, QualificationsAndHonoursActivity.class);
                intentb.putExtra("orgid", mOrgId);
                intentb.putExtra("isAuth", status);
                startActivity(intentb);
                break;
            case R.id.gd_nl_wq_tv:
                Intent intentc = new Intent(this, MoreCapabilityActivity.class);
                intentc.putExtra("orgid", mOrgId);
                intentc.putExtra("isAuth", status);
                startActivity(intentc);
                break;
            default:
        }
    }
}
