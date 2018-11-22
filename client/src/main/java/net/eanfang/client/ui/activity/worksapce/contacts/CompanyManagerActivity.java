package net.eanfang.client.ui.activity.worksapce.contacts;

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

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.setting.UpdatePasswordActivity;
import net.eanfang.client.ui.fragment.ContactsFragment;
import net.eanfang.client.ui.widget.DissloveTeamDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
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
    //    @BindView(R.id.rl_auth)
//    RelativeLayout rlAuth;
    private Long mOrgId;

    private String mOrgName = "";

    //认证中显示标示
    private String isAuth = "";

    private String adminUserId = "";

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

    @OnClick({R.id.rl_prefectInfo, R.id.rl_admin_set, R.id.rl_creat_section, R.id.rl_add_staff, R.id.rl_permission,
            R.id.ll_cooperation_relation, R.id.tv_againAuth, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 完善资料
            case R.id.rl_prefectInfo:
                if (PermKit.get().getCompanyVerifyPerm()) {
                    Bundle bundle_prefect = new Bundle();
                    bundle_prefect.putLong("orgid", mOrgId);
                    bundle_prefect.putString("orgName", mOrgName);
                    bundle_prefect.putString("assign", "prefect");
                    if ("2".equals(isAuth) || "1".equals(isAuth)) {//已认证  进行查看 资料
                        JumpItent.jump(CompanyManagerActivity.this, AuthCompanyDataActivity.class, bundle_prefect);
                    } else {
                        JumpItent.jump(CompanyManagerActivity.this, AuthCompanyFirstActivity.class, bundle_prefect);
                    }
                }
                break;
//            case R.id.rl_auth:
//                Bundle bundle_auth = new Bundle();
//                bundle_auth.putLong("orgid", mOrgId);
//                bundle_auth.putString("orgName", mOrgName);
//                bundle_auth.putString("assign", "auth");
//                JumpItent.jump(CompanyManagerActivity.this, AuthCompanyDataActivity.class, bundle_auth);
//
//                break;
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
        new TrueFalseDialog(this, "系统提示", "是否撤销认证并保存信息", () -> {
            EanfangHttp.post(NewApiService.COMPANY_ENTERPRISE_AUTH_REVOKE + mOrgId)
                    .execute(new EanfangCallback<JSONPObject>(this, true, JSONPObject.class, bean -> {
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
