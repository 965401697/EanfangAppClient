package net.eanfang.client.ui.activity.worksapce.contacts;

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
import com.eanfang.biz.model.BusinessManagementData;
import com.eanfang.ui.base.BaseActivity;

import net.eanfang.client.R;
import net.eanfang.client.ui.fragment.ContactsFragment;
import net.eanfang.client.ui.widget.WQLeftRightClickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.eanfang.apiservice.NewApiService.BUSINESS_MANAGEMENT;

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
    private Long mOrgId;
    private int status = 0;
    private String mOrgName = "";
    private int bizCertify = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_certification);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("企业认证");
        SpannableString spannableString = new SpannableString("工商认证（必填）");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3F3F")), 4, spannableString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        gsRzWqTv.setText(spannableString);
        initData();
        ContactsFragment.isRefresh = true;
    }

    private void initData() {
        mOrgId = getIntent().getLongExtra("mOrgId", 0);
        status = getIntent().getIntExtra("status", 0);
        mOrgName = getIntent().getStringExtra("orgName");

        EanfangHttp.post(BUSINESS_MANAGEMENT).params("orgId", mOrgId).execute(new EanfangCallback<>(this, true, BusinessManagementData.DataBean.class, this::setData));

    }
    private void setData(BusinessManagementData.DataBean data) {
        bizCertify = data.getBizCertify();
        status = data.getStatus();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }
    @OnClick({R.id.gs_rz_wq_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gs_rz_wq_tv:
                Intent intent = new Intent(this, BusinessCertificationActivity.class);
                intent.putExtra("mOrgId", mOrgId);
                intent.putExtra("status", status);
                intent.putExtra("bizCertify", bizCertify);
                startActivity(intent);
                break;
            default:
        }
    }
}