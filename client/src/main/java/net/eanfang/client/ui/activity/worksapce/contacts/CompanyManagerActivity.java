package net.eanfang.client.ui.activity.worksapce.contacts;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONPObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.AuthCompanyBaseInfoBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.setting.UpdatePasswordActivity;
import net.eanfang.client.ui.fragment.ContactsFragment;
import net.eanfang.client.ui.widget.DissloveTeamDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang 客户端企业管理
 * @date on 2018/5/7  11:06
 * @decision
 */
public class CompanyManagerActivity extends BaseActivity implements DissloveTeamDialog.OnForgetPasswordListener, DissloveTeamDialog.OnConfirmListener {

    @BindView(R.id.rl_prefectInfo)
    RelativeLayout rlPrefectInfo;
    @BindView(R.id.rl_is_auth)
    RelativeLayout rlIsAuth;
    // 重新认证
    @BindView(R.id.tv_againAuth)
    TextView tvAgainAuth;
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
    @BindView(R.id.gs_xq_tv)
    TextView gsXqTv;
    @BindView(R.id.show_more_tv)
    TextView showMoreTv;
    @BindView(R.id.tv_des)
    TextView tvDes;
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
    //    @BindView(R.id.rl_auth)
//    RelativeLayout rlAuth;
    private Long mOrgId;

    private String mOrgName = "";

    //认证中显示标示
    private String isAuth = "";
    private int status = 0;

    private String adminUserId = "";
    private AuthCompanyBaseInfoBean byNetBean;

    /**
     * 解散团队
     */
    private DissloveTeamDialog dissloveTeamDialog;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comapany_manager);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        setLeftBack();
        setTitle("企业管理");
        mOrgId = getIntent().getLongExtra("orgid", 0);
        mOrgName = getIntent().getStringExtra("orgName");
        isAuth = getIntent().getStringExtra("isAuth");
        adminUserId = getIntent().getStringExtra("adminUserId");
        if (String.valueOf(EanfangApplication.get().getUserId()).equals(adminUserId)) {
            setRightTitle("解散团队");
        } else {
            setRightTitle("");
            setRightTitleOnClickListener(v -> {
                return;
            });
        }
        /**
         *  0 未认证，待认证
         *  1认证中
         *  2已认证
         *  3认证失败，请重新认证
         * */
        if ("1".equals(isAuth)) {
            rlIsAuth.setVisibility(View.VISIBLE);
        } else {
            rlIsAuth.setVisibility(View.GONE);
        }
        if ("2".equals(isAuth)) {
            tvAgainAuth.setVisibility(View.VISIBLE);
        } else {
            tvAgainAuth.setVisibility(View.GONE);
        }
        initData();
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_INFO + mOrgId)
                .execute(new EanfangCallback<AuthCompanyBaseInfoBean>(this, true, AuthCompanyBaseInfoBean.class, (beans) -> {
                    byNetBean = beans;
                    setData();
                }));
    }

    private void setData() {
        Log.d("BUSINESS_MANAGEMENT", "setData: " + byNetBean.toString());
        gsNameTv.setText(byNetBean.getName());
        gsLogSdv.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(byNetBean.getLogoPic()));
        if (byNetBean.getIntro() == null) {
            SpannableString spannableString = new SpannableString("公司简介: ");
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#006BFF")), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            gsXqTv.setText(spannableString);
        } else {
            SpannableString spannableString = new SpannableString("公司简介: " + byNetBean.getIntro());
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#006BFF")), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 5, spannableString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            gsXqTv.setText(spannableString);
        }
        status = byNetBean.getStatus();
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
                flag = false;
            }
        }
    }

    private Boolean flag = false;

    public void showMoreTv() {
        gsXqTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        if (flag) {
            gsXqTv.setEllipsize(TextUtils.TruncateAt.END);
            gsXqTv.setLines(3);
            showMoreTv.setText("更多");
        } else {
            gsXqTv.setMovementMethod(ScrollingMovementMethod.getInstance());
            gsXqTv.setEllipsize(null);
            gsXqTv.setSingleLine(false);
            showMoreTv.setText("收起");
        }
        flag = !flag;
    }

    @OnClick({R.id.tv_des, R.id.show_more_tv, R.id.gs_xq_iv, R.id.rl_admin_set, R.id.rl_creat_section, R.id.rl_add_staff, R.id.rl_permission, R.id.ll_cooperation_relation, R.id.tv_againAuth, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_des:
                Intent intent = new Intent(this, EnterpriseCertificationActivity.class);
                intent.putExtra("mOrgId", mOrgId);
                intent.putExtra("status", status);
                intent.putExtra("orgName", mOrgName);
                startActivity(intent);
                finish();
                break;
            case R.id.show_more_tv:
                showMoreTv();
                break;
            // 完善资料
            case R.id.gs_xq_iv:
                Bundle bundle_prefect = new Bundle();
                bundle_prefect.putLong("orgid", mOrgId);
                bundle_prefect.putString("orgName", mOrgName);
                bundle_prefect.putString("assign", "prefect");
                if ("2".equals(isAuth) || "1".equals(isAuth)) {//已认证  进行查看 资料
                    if (!PermKit.get().getCompanyDetailPerm()) {
                        return;
                    }
                    JumpItent.jump(CompanyManagerActivity.this, AuthCompanyDataActivity.class, bundle_prefect);
                } else {
                    if (!PermKit.get().getCompanyVerifyPerm()) {
                        return;
                    }
                    JumpItent.jump(CompanyManagerActivity.this, AuthCompanyFirstActivity.class, bundle_prefect);
                }
                break;
            // 管理员转让
            case R.id.rl_admin_set:
                if (String.valueOf(EanfangApplication.get().getUserId()).equals(adminUserId)) {
                    JumpItent.jump(CompanyManagerActivity.this, AdministratorSetActivity.class);
                } else {
                    ToastUtil.get().showToast(this, "您不是当前公司的管理员");
                }
                break;
            case R.id.rl_creat_section:
                if (PermKit.get().getCompanyDepartmentCreatPerm()) {
                    JumpItent.jump(CompanyManagerActivity.this, CreatSectionActivity.class);
                }

                break;
            // 添加员工
            case R.id.rl_add_staff:
                if (PermKit.get().getCompanyStaffCreatPerm()) {
                    JumpItent.jump(CompanyManagerActivity.this, SearchStaffActivity.class);
                }

                break;
            case R.id.rl_permission:
                if (PermKit.get().getCompanyStaffAssignrolePerm()) {
                    JumpItent.jump(CompanyManagerActivity.this, PermissionManagerActivity.class);
                }
                break;
            // 合作关系
            case R.id.ll_cooperation_relation:
                if (PermKit.get().getCooperationListAllPerm()) {
                    JumpItent.jump(CompanyManagerActivity.this, CooperationRelationActivity.class);
                }
                break;
            case R.id.tv_againAuth:
                doUndoVerify();
                break;
            case R.id.tv_right:
                doDisslove();
                break;
            default:
                break;

        }

    }

    /**
     * 解散团队
     */
    private void doDisslove() {
        dissloveTeamDialog = new DissloveTeamDialog(CompanyManagerActivity.this, this, this);
        dissloveTeamDialog.show();
    }

    /**
     * 进行撤销认证操作
     */
    public void doUndoVerify() {
        if (!PermKit.get().getCompanyBackPerm()) {
            return;
        }
        new TrueFalseDialog(this, "系统提示", "是否撤销认证并保存信息", () -> {
            EanfangHttp.post(NewApiService.COMPANY_ENTERPRISE_AUTH_REVOKE + mOrgId).execute(new EanfangCallback<JSONPObject>(this, true, JSONPObject.class, bean -> {
                showToast("撤销成功");
                isAuth = "0";
                tvAgainAuth.setVisibility(View.GONE);
            }));
        }).showDialog();
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
        JumpItent.jump(CompanyManagerActivity.this, UpdatePasswordActivity.class, bundle);
    }

    /**
     * 确认解散团队
     */
    @Override
    public void doConfirm() {
        ContactsFragment.isDisslove = true;
        finishSelf();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//MAIN代表主线程
    public void receiveMessage(String message) {//该方法名可更改，不影响任何东西。
        if (message.equals("customerIsAuthing")) {
            isAuth = "1";
        }
    }
}
