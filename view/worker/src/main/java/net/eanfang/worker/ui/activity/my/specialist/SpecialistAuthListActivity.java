package net.eanfang.worker.ui.activity.my.specialist;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.Message;
import com.eanfang.biz.model.bean.SpecialistAuthStatusBean;
import com.eanfang.util.JumpItent;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.widget.CommitVerfiyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 已废弃
 *
 * @author jornl
 * @date 2019-07-17 14:41:56
 */
@Deprecated
public class SpecialistAuthListActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_base_info)
    TextView tvBaseInfo;
    @BindView(R.id.tv_skill)
    TextView tvSkill;
    @BindView(R.id.tv_own)
    TextView tvOwn;
    @BindView(R.id.tv_certificate)
    TextView tvCertificate;
    @BindView(R.id.tv_confim)
    TextView tvConfim;
    @BindView(R.id.rl_is_auth)
    RelativeLayout rlIsAuth;


    //提交认证 弹框
    private CommitVerfiyView verfiyView;

    private int verify = -1;
    private SpecialistAuthStatusBean mAuthStatusBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_specialist_auth_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);


        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 重新认证
                 */
                if (verify == 1) {
                    ToastUtil.get().showToast(SpecialistAuthListActivity.this, "信息认证中，不能撤销");
                    return;
                } else if (verify == 0) {
                    ToastUtil.get().showToast(SpecialistAuthListActivity.this, "认证信息还没有提交，不能撤销");
                    return;
                }
                new TrueFalseDialog(SpecialistAuthListActivity.this, "系统提示", "是否撤销认证并保存信息", () -> {
                    EanfangHttp.post(NewApiService.EXPERT_AUTH_REVOKE + WorkerApplication.get().getAccId())
                            .execute(new EanfangCallback<JSONPObject>(SpecialistAuthListActivity.this, true, JSONPObject.class, bean -> {
                                tvConfim.setVisibility(View.VISIBLE);
                                verify = 0;//撤销认证  状态没有时时的刷新 减少请求 本地改变状态
                                setRightTitle("");//重置状态
                            }));
                }).showDialog();
            }
        });

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        setTitle("专家认证");
        setLeftBack();
    }

    private void initData() {
        // 获取认证状态
        EanfangHttp.post(UserApi.GET_EXPERT_CERTIFICATION_STATUS)
                .params("accId", WorkerApplication.get().getAccId())
                .execute(new EanfangCallback<SpecialistAuthStatusBean>(this, true, SpecialistAuthStatusBean.class, (bean) -> {
                    verify = bean.verify;
                    //只有认证完成了 才显示重新认证
                    if (verify == 2) {
                        setRightTitle("重新认证");
                    }
                    mAuthStatusBean = bean;
                    doChange(bean.base, bean.qual, bean.job, bean.honor, bean.verify);
                }));
    }

    @OnClick({R.id.rl_base_info, R.id.rl_skill, R.id.rl_own, R.id.rl_certificate, R.id.tv_confim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 实名认证
            case R.id.rl_base_info:
                if (verify == 1 || verify == 2) {//如果是认证中  和 认证完成 跳到详情界面 不可修改
                    JumpItent.jump(this, SpecialistCertificationInfoActivity.class);
                } else {
                    if (mAuthStatusBean == null) {

                        ToastUtil.get().showToast(this, "请稍后操作");

                        return;
                    }
                    startActivity(new Intent(this, SpecialistCertificationActivity.class).putExtra("status", mAuthStatusBean.base));
                }
                break;
            //技能资质
            case R.id.rl_skill:
                if (mAuthStatusBean.base == 0) {
                    ToastUtil.get().showToast(this, "请先进行实名认证");
                    return;
                }
                if (verify == 1 || verify == 2) {//如果是认证中  和 认证完成 跳到详情界面 不可修改
                    JumpItent.jump(this, SpecialistSkillInfoDetailActivity.class);
                } else {

                    if (mAuthStatusBean == null) {
                        //多次访问状态的接口 造成mAuthStatusBean == null
                        ToastUtil.get().showToast(this, "请稍后操作");

                        return;
                    }

                    startActivity(new Intent(this, SpecialistSkillTypeActivity.class).putExtra("status", mAuthStatusBean.qual));
                }
                break;
            // 个人经历
            case R.id.rl_own:

                startActivity(new Intent(this, SpecialistOwmHistoryActivity.class));

                break;
            //荣誉证书
            case R.id.rl_certificate:

                JumpItent.jump(this, SpecialistCertificateListActivity.class);
                break;
            case R.id.tv_confim:
                doVerify();
                break;
            default:
                break;
        }
    }

    private void doChange(int baseStatus, int serviceStatus, int bizStatus, int areaStatus, int verify) {
        // 正在认证中 给予提示
        if (verify == 1) {
            rlIsAuth.setVisibility(View.VISIBLE);
        } else {
            rlIsAuth.setVisibility(View.GONE);
        }
        // 实名认证
        if (baseStatus > 0) {
            tvBaseInfo.setText("已完善");
            tvBaseInfo.setTextColor(ContextCompat.getColor(this, R.color.color_bottom));
        } else {
            tvBaseInfo.setText("待完善");
            tvBaseInfo.setTextColor(ContextCompat.getColor(this, R.color.color_auth_list_unfinish));
        }
        //技能资质
        if (serviceStatus > 0) {
            tvSkill.setText("已完善");
            tvSkill.setTextColor(ContextCompat.getColor(this, R.color.color_bottom));
        } else {
            tvSkill.setText("待完善");
            tvSkill.setTextColor(ContextCompat.getColor(this, R.color.color_auth_list_unfinish));
        }
        //个人经历
        if (bizStatus > 0) {
            tvOwn.setText("已完善");
            tvOwn.setTextColor(ContextCompat.getColor(this, R.color.color_bottom));
        } else {
            tvOwn.setText("待完善");
            tvOwn.setTextColor(ContextCompat.getColor(this, R.color.color_auth_list_unfinish));
        }
        //荣誉证书
        if (areaStatus > 0) {
            tvCertificate.setText("已完善");
            tvCertificate.setTextColor(ContextCompat.getColor(this, R.color.color_bottom));
        } else {
            tvCertificate.setText("待完善");
            tvCertificate.setTextColor(ContextCompat.getColor(this, R.color.color_auth_list_unfinish));
        }
        // 当认证都大于0
        if (baseStatus > 0 && serviceStatus > 0 && (verify != 1 && verify != 2)) {
            tvConfim.setVisibility(View.VISIBLE);
        } else {
            tvConfim.setVisibility(View.GONE);
        }
    }

    /**
     * 是否提交认证
     */
    private void doVerify() {
        verfiyView = new CommitVerfiyView(this, view -> commitVerfiy(verfiyView));
        verfiyView.show();
    }

    // 提交认证
    private void commitVerfiy(CommitVerfiyView verfiyView) {
        EanfangHttp.post(UserApi.POST_EXPERT_SEND_VERIFY)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    verfiyView.dismiss();
                    doJumpConfirm();
                }));
    }

    // 认证提交完毕 提示页面
    public void doJumpConfirm() {
        Bundle bundle = new Bundle();
        Message message = new Message();
        message.setMsgContent("认证提交成功");
        message.setMsgHelp("如需修改认证资料");
        message.setTip("确定");
        bundle.putSerializable("message", message);
        JumpItent.jump(SpecialistAuthListActivity.this, StateChangeActivity.class, bundle);
        finishSelf();
    }
}
