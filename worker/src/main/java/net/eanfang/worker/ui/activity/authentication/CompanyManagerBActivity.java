package net.eanfang.worker.ui.activity.authentication;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.BusinessManagementData;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.contacts.baseinfo.AuthCompanyFirstBActivity;
import net.eanfang.worker.ui.activity.worksapce.setting.UpdatePasswordActivity;
import net.eanfang.worker.ui.fragment.ContactsFragment;
import net.eanfang.worker.ui.widget.DissloveTeamDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.eanfang.apiservice.NewApiService.BUSINESS_MANAGEMENT;

/**
 * @author WQ
 */
public class CompanyManagerBActivity extends BaseActivity implements DissloveTeamDialog.OnForgetPasswordListener, DissloveTeamDialog.OnConfirmListener {

    @BindView(R.id.gs_xq_tv)
    TextView gsXqTv;
    @BindView(R.id.show_more_tv)
    TextView showMoreTv;
    @BindView(R.id.rl_admin_set)
    RelativeLayout rlAdminSet;
    @BindView(R.id.rl_creat_section)
    RelativeLayout rlCreatSection;
    @BindView(R.id.rl_add_staff)
    RelativeLayout rlAddStaff;
    @BindView(R.id.rl_permission)
    RelativeLayout rlPermission;
    @BindView(R.id.ll_cooperation_relation)
    LinearLayout llCooperationRelation;
    @BindView(R.id.gg_iv)
    ImageView ggIv;
    @BindView(R.id.gs_log_sdv)
    SimpleDraweeView gsLogSdv;
    @BindView(R.id.gs_name_tv)
    TextView gsNameTv;
    @BindView(R.id.iv_verify)
    ImageView ivVerify;
    @BindView(R.id.tv_auth_status)
    TextView tvAuthStatus;
    @BindView(R.id.gs_xq_iv)
    ImageView gsXqIv;
    @BindView(R.id.tv_des)
    TextView tvDes;
    private Boolean flag = false;
    private Long mOrgId;
    private int status = 0;
    private int bizCertify = 0;
    private String mOrgName = "";

    /**
     * 解散团队
     */
    private DissloveTeamDialog dissloveTeamDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_manager_b);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("企业管理");
        if (String.valueOf(EanfangApplication.get().getUserId()).equals(getIntent().getStringExtra("adminUserId"))) {
            setRightTitle("解散团队");
            setRightTitleOnClickListener(v -> doDisslove());
        } else {
            setRightTitle("");
        }
        initData();
    }

    private void doDisslove() {
        dissloveTeamDialog = new DissloveTeamDialog(CompanyManagerBActivity.this, this, this);
        dissloveTeamDialog.show();
    }


    @OnClick({R.id.show_more_tv, R.id.tv_des, R.id.rl_admin_set, R.id.rl_creat_section, R.id.rl_add_staff, R.id.rl_permission, R.id.ll_cooperation_relation, R.id.gg_iv, R.id.gs_xq_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_more_tv:
                showMoreTv();
                break;
            case R.id.tv_des:
                Intent intent = new Intent(this, EnterpriseCertificationActivity.class);
                intent.putExtra("mOrgId", mOrgId);
                startActivity(intent);
                finish();
                break;
            case R.id.rl_admin_set:
                break;
            case R.id.rl_creat_section:
                break;
            case R.id.rl_add_staff:
                break;
            case R.id.rl_permission:
                break;
            case R.id.ll_cooperation_relation:
                break;
            case R.id.gg_iv:
                break;
            case R.id.gs_xq_iv:
                if (status == 1 | status == 2) {
                    startActivity(new Intent(this, CompanyPagesActivity.class).putExtra("mOrgId", mOrgId).putExtra("status", status));
                } else {
                    startActivity(new Intent(this, AuthCompanyFirstBActivity.class).putExtra("orgName", mOrgName).putExtra("orgid", mOrgId));
                }
                finish();
                break;
            default:
        }
    }

    public void showMoreTv() {
        gsXqTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        if (flag) {
            gsXqTv.setEllipsize(TextUtils.TruncateAt.END);
            gsXqTv.setLines(3);
            showMoreTv.setText("[更多]");
        } else {
            gsXqTv.setMovementMethod(ScrollingMovementMethod.getInstance());
            gsXqTv.setEllipsize(null);
            gsXqTv.setSingleLine(false);
            showMoreTv.setText("收起");
        }
        flag = !flag;
    }

    private void initData() {
        mOrgId = getIntent().getLongExtra("orgid", 0);
        mOrgName = getIntent().getStringExtra("orgName");
        EanfangHttp.post(BUSINESS_MANAGEMENT).params("orgId", mOrgId).execute(new EanfangCallback<>(this, true, BusinessManagementData.DataBean.class, this::setData));
    }

    private void setData(BusinessManagementData.DataBean data) {
        Log.d("BUSINESS_MANAGEMENT", "setData: " + data.toString());
        gsNameTv.setText(data.getName());
        gsLogSdv.setImageURI(com.eanfang.BuildConfig.OSS_SERVER + Uri.parse(data.getLogoPic()));
        if (data.getIntro() == null) {
            SpannableString spannableString = new SpannableString("公司简介: ");
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#006BFF")), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            gsXqTv.setText(spannableString);
        } else {
            SpannableString spannableString = new SpannableString("公司简介: " + data.getIntro());
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#006BFF")), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 5, spannableString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            gsXqTv.setText(spannableString);
            Log.d("gsXqTv.getLineCount()", "setData: " + gsXqTv.getLineCount());
            gsXqTv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Log.d("getLineCount: ", "行数" + gsXqTv.getLineCount());
                    if (gsXqTv.getLineCount() < 4) {
                        showMoreTv.setVisibility(View.GONE);
                        flag = true;
                    } else {
                        showMoreTv.setVisibility(View.VISIBLE);
                        flag = false;
                        gsXqTv.setEllipsize(TextUtils.TruncateAt.END);
                        gsXqTv.setLines(3);
                    }
                    gsXqTv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });

        }
        status = data.getStatus();
        bizCertify = data.getBizCertify();
        switch (status) {
            case 1:
                tvAuthStatus.setText("认证中");
                tvDes.setText("修改认证");
                break;
            case 2:
                ivVerify.setImageResource(R.mipmap.ic_contact_auth);
                tvAuthStatus.setText("已认证");
                tvDes.setText("查看认证");
                break;
            case 3:
                tvAuthStatus.setText("认证拒绝");
                tvDes.setText("修改认证");
                break;
            case 4:
                break;
            default:
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (gsXqTv.getLineCount() < 4) {
                showMoreTv.setVisibility(View.GONE);
                flag = true;
            } else {
                showMoreTv.setVisibility(View.VISIBLE);
                flag = false;
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dissloveTeamDialog != null) {
            dissloveTeamDialog.dismiss();
        }
    }

    /**
     * 忘记密码
     */
    @Override
    public void doForget() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("disslove", true);
        JumpItent.jump(CompanyManagerBActivity.this, UpdatePasswordActivity.class, bundle);
    }

    /**
     * 确认解散团队
     */
    @Override
    public void doConfirm() {
        ContactsFragment.isDisslove = true;
        finishSelf();
    }
}
