package net.eanfang.worker.ui.activity.authentication;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.BusinessManagementData;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.PermKit;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify.AuthQualifyFirstActivity;
import net.eanfang.worker.ui.widget.WQLeftRightClickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.eanfang.apiservice.NewApiService.BUSINESS_MANAGEMENT;

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
    private int bizCertify = 0;
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
        SpannableString spannableString = new SpannableString("工商认证（必填）");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3F3F")), 4, spannableString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        gsRzWqTv.setText(spannableString);
        SpannableString spannableStringb = new SpannableString("服务认证（必填）");
        spannableStringb.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringb.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3F3F")), 4, spannableString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        fwRzWqTv.setText(spannableStringb);
    }

    private void initData() {
        mOrgId = getIntent().getLongExtra("mOrgId", 0);
        mOrgName = getIntent().getStringExtra("orgName");
        EanfangHttp.post(BUSINESS_MANAGEMENT).params("orgId", mOrgId).execute(new EanfangCallback<>(this, true, BusinessManagementData.DataBean.class, this::setData));

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    private void setData(BusinessManagementData.DataBean data) {
        bizCertify = data.getBizCertify();
        status = data.getStatus();
    }

    @OnClick({R.id.gs_rz_wq_tv, R.id.fw_rz_wq_tv, R.id.zz_ry_wq_tv, R.id.gd_nl_wq_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gs_rz_wq_tv:
                Intent intent = new Intent(this, BusinessCertificationActivity.class);
                intent.putExtra("mOrgId", mOrgId);
                intent.putExtra("status", status);
                intent.putExtra("bizCertify", bizCertify);
                startActivity(intent);
                break;
            case R.id.fw_rz_wq_tv:
                if (!PermKit.get().getWorkerCompanyVerifyPerm()) {
                    return;
                } else {
                    if (bizCertify == 1) {
                        showToast("请先进行工商认证");
                    } else {
                        Intent intenta = new Intent(this, AuthQualifyFirstActivity.class);
                        intenta.putExtra("orgid", mOrgId);
                        intenta.putExtra("status", status);
                        startActivity(intenta);
                    }
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
