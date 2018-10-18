package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.AuthStatusBean;
import com.eanfang.model.Message;
import com.eanfang.model.WorkerInfoBean;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.widget.CommitVerfiyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewAuthListActivity extends BaseWorkerActivity {

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
    private WorkerInfoBean workerInfoBean;

    //提交认证 弹框
    private CommitVerfiyView verfiyView;

    private int verify = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_auth_list);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        setTitle("技师认证");
        setLeftBack();
    }

    private void initData() {
        // 获取认证状态
        EanfangHttp.post(UserApi.POST_WORKER_AUTH_STATUS)
                .params("accId", EanfangApplication.getApplication().getAccId())
                .execute(new EanfangCallback<AuthStatusBean>(this, true, AuthStatusBean.class, (bean) -> {
                    verify = bean.getVerify();
                    doChange(bean.getBase(), bean.getBiz(), bean.getService(), bean.getArea(), bean.getVerify());
                }));
        // 获取技师信息
        EanfangHttp.get(UserApi.GET_WORKER_INFO)
                .execute(new EanfangCallback<WorkerInfoBean>(NewAuthListActivity.this, true, WorkerInfoBean.class, (bean) -> {
                    workerInfoBean = bean;
                }));
    }

    @OnClick({R.id.rl_base_info, R.id.rl_skill, R.id.rl_own, R.id.rl_certificate, R.id.tv_confim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 实名认证
            case R.id.rl_base_info:
                Bundle bundle = new Bundle();
                bundle.putSerializable("workerInfoBean", workerInfoBean);
                bundle.putInt("status", verify);
                JumpItent.jump(this, CertificationActivity.class, bundle);
                break;
            //技能资质
            case R.id.rl_skill:
                startActivity(new Intent(this, SkillTypeActivity.class).putExtra("status", verify));
                break;
            // 个人经历
            case R.id.rl_own:
                startActivity(new Intent(this, OwmHistoryActivity.class).putExtra("status", verify));
                break;
            //荣誉证书
            case R.id.rl_certificate:
                startActivity(new Intent(this, CertificateListActivity.class).putExtra("status", verify));
                break;
            case R.id.tv_confim:
                doVerify();
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
        if (baseStatus > 0 && serviceStatus > 0 && bizStatus > 0 && areaStatus > 0 && (verify != 1 && verify != 2)) {
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
        EanfangHttp.post(UserApi.POST_TECH_WORKER_SEND_VERIFY)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    verfiyView.dismiss();
                    doJumpConfirm();
                }));
    }

    // 认证提交完毕 提示页面
    public void doJumpConfirm() {
        Intent intent = new Intent(NewAuthListActivity.this, StateChangeActivity.class);
        Bundle bundle = new Bundle();
        Message message = new Message();
        message.setTitle("认证提交成功");
        message.setMsgTitle("您的技师认证资料已经提交成功");
        message.setMsgContent("我们会在72小时内进行审核");
        message.setMsgHelp("如需修改认证资料");
        message.setShowOkBtn(true);
        message.setShowLogo(true);
        message.setTip("");
        bundle.putSerializable("message", message);
        intent.putExtras(bundle);
        startActivity(intent);
        finishSelf();
    }
}
