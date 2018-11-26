package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONPObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.my.certification.CertificateListActivity;
import net.eanfang.worker.ui.activity.worksapce.contacts.baseinfo.AuthCompanyDataActivity;
import net.eanfang.worker.ui.activity.worksapce.contacts.baseinfo.AuthCompanyFirstActivity;
import net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify.AuthQualifyFirstActivity;
import net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify.QualifyDataActivity;
import net.eanfang.worker.ui.activity.worksapce.setting.UpdatePasswordActivity;
import net.eanfang.worker.ui.fragment.ContactsFragment;
import net.eanfang.worker.ui.widget.DissloveTeamDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/5/8  18:09
 * @decision 企业管理
 */
public class CompanyManagerActivity extends BaseActivity implements DissloveTeamDialog.OnForgetPasswordListener, DissloveTeamDialog.OnConfirmListener {
    @BindView(R.id.rl_prefectInfo)
    RelativeLayout rlPrefectInfo;
    @BindView(R.id.rl_auth)
    RelativeLayout rlAuth;
    @BindView(R.id.rl_is_auth)
    RelativeLayout rlIsAuth;
    // 重新认证
    @BindView(R.id.tv_againAuth)
    TextView tvAgainAuth;
    // 荣誉证书
    @BindView(R.id.rl_honorcertificate)
    RelativeLayout rlHonorcertificate;
    private Long mOrgId;
    private String mOrgName = "";
    //认证中显示标示
    private String isAuth = "";
    private String adminUserId;
    /**
     * 解散团队
     */
    private DissloveTeamDialog dissloveTeamDialog;

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
        setRightTitle("解散团队");
        mOrgId = getIntent().getLongExtra("orgid", 0);
        mOrgName = getIntent().getStringExtra("orgName");
        isAuth = getIntent().getStringExtra("isAuth");
        adminUserId = getIntent().getStringExtra("adminUserId");
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
    }

    @OnClick({R.id.rl_prefectInfo, R.id.rl_auth, R.id.rl_admin_set, R.id.rl_creat_section, R.id.rl_add_staff,
            R.id.rl_permission, R.id.ll_cooperation_relation, R.id.tv_againAuth, R.id.rl_honorcertificate, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 完善资料
            case R.id.rl_prefectInfo:
                if (!PermKit.get().getWorkerCompanyVerifyPerm()) return;
                Bundle bundle_prefect = new Bundle();
                bundle_prefect.putLong("orgid", mOrgId);
                bundle_prefect.putString("orgName", mOrgName);
                bundle_prefect.putString("assign", "prefect");
                if ("2".equals(isAuth) || "1".equals(isAuth)) {//已认证  进行查看 资料
                    JumpItent.jump(CompanyManagerActivity.this, AuthCompanyDataActivity.class, bundle_prefect);
                } else {
                    JumpItent.jump(CompanyManagerActivity.this, AuthCompanyFirstActivity.class, bundle_prefect);
                }
                break;
            // 资质认证
            case R.id.rl_auth:
                if (!PermKit.get().getWorkerCompanyVerifyPerm()) return;
                Bundle bundle_auth = new Bundle();
                bundle_auth.putLong("orgid", mOrgId);
                bundle_auth.putString("isAuth", isAuth);
                if ("2".equals(isAuth) || "1".equals(isAuth)) {//已认证  进行查看资质认证
                    JumpItent.jump(CompanyManagerActivity.this, QualifyDataActivity.class, bundle_auth);
                } else {
                    JumpItent.jump(CompanyManagerActivity.this, AuthQualifyFirstActivity.class, bundle_auth);
                }

                break;
            // 荣誉证书
            case R.id.rl_honorcertificate:
                Bundle bundle = new Bundle();
                bundle.putString("isAuth", isAuth);
                bundle.putLong("orgid", mOrgId);
                bundle.putString("role", "company");
                JumpItent.jump(CompanyManagerActivity.this, CertificateListActivity.class, bundle);

                break;
            case R.id.rl_admin_set:
                if (String.valueOf(EanfangApplication.get().getUserId()).equals(adminUserId)) {
                    JumpItent.jump(CompanyManagerActivity.this, AdministratorSetActivity.class);
                } else {
                    ToastUtil.get().showToast(this, "您不是当前公司的管理员");
                }
                break;
            case R.id.rl_creat_section:
                if (!PermKit.get().getCompanyDepartmentCreatPerm()) return;
                JumpItent.jump(CompanyManagerActivity.this, CreatSectionActivity.class);

                break;
            case R.id.rl_add_staff:
                if (!PermKit.get().getCompanyStaffCreatPerm()) return;
                JumpItent.jump(CompanyManagerActivity.this, SearchStaffActivity.class);

                break;
            case R.id.rl_permission:
                if (!PermKit.get().getCompanyStaffAssignrolePerm()) return;
                JumpItent.jump(CompanyManagerActivity.this, PermissionManagerActivity.class);
                break;
            case R.id.ll_cooperation_relation:
                if (!PermKit.get().getCooperationListAllPerm()) return;
                JumpItent.jump(CompanyManagerActivity.this, CooperationRelationActivity.class);
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
        new TrueFalseDialog(this, "系统提示", "是否撤销认证并保存信息", () -> {
            EanfangHttp.post(NewApiService.COMPANY_SECURITY_AUTH_REVOKE + mOrgId).
                    execute(new EanfangCallback<JSONPObject>(this, true, JSONPObject.class, bean -> {
                        showToast("撤销成功");
                        tvAgainAuth.setVisibility(View.GONE);
                        isAuth = "0";
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

}
