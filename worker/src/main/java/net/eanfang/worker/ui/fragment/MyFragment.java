package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.LoginBean;
import com.eanfang.model.SpecialistAuthStatusBean;
import com.eanfang.ui.activity.ExpertDetailsActivity;
import com.eanfang.ui.activity.QrCodeShowActivity;
import com.eanfang.ui.activity.SecurityCompanyDetailsActivity;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.eanfang.witget.PersonalQRCodeDialog;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.my.EvaluateActivity;
import net.eanfang.worker.ui.activity.my.PersonInfoActivity;
import net.eanfang.worker.ui.activity.my.SettingActivity;
import net.eanfang.worker.ui.activity.my.certification.NewAuthListActivity;
import net.eanfang.worker.ui.activity.my.specialist.SpecialistAuthListActivity;
import net.eanfang.worker.ui.activity.my.specialist.SpecialistSkillInfoDetailActivity;
import net.eanfang.worker.ui.activity.my.specialist.SpecialistSkillTypeActivity;
import net.eanfang.worker.ui.activity.techniciancertification.TechnicianCertificationActivity;
import net.eanfang.worker.ui.widget.InviteView;
import net.eanfang.worker.util.PrefUtils;


/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:08
 * @email houzhongzhou@yeah.net
 * @desc 我的
 */

public class MyFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private TextView tv_user_name, tvVerfiy, tvVerfiyb, tvExpertVerfiyb, tvExpertVerfiy;
    private RelativeLayout rlWorkerVerfity, rlWorkerVerfityB, rlExpertVerfity, rlExpertVerfityB;

    private SimpleDraweeView iv_header;
    // 二维码头像
    private SimpleDraweeView mIvPersonalQRCode;
    // Dialog
    private PersonalQRCodeDialog personalQRCodeDialog;
    private RadioButton rbWorking, rbFree, rbStop;
    private RadioGroup rbWorkStatus;
    private int verify = -1;
    private SpecialistAuthStatusBean mAuthStatusBean;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_config;
    }

    @Override
    protected void initData(Bundle arguments) {
        getWorkInfo();
    }


    private void getWorkInfo() {
        // 获取认证状态
        EanfangHttp.post(UserApi.POST_WORKER_EXPERT_AUTH_STATUS).execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, (bean) -> {
            int e = (int) bean.get("expert");//专家
            int t = (int) bean.get("techWorker");//技师
            setOnClick(e, t);
        }));

        // 获取认证状态
        EanfangHttp.post(UserApi.GET_EXPERT_CERTIFICATION_STATUS).params("accId", EanfangApplication.getApplication().getAccId()).execute(new EanfangCallback<SpecialistAuthStatusBean>(getActivity(), true, SpecialistAuthStatusBean.class, (bean) -> {
            verify = bean.verify;
//                    if (verify == 2) {
//                        setRightTitle("重新认证");
//                    }
            mAuthStatusBean = bean;
        }));
    }

    private void setOnClick(int e, int t) {
        if (t == 0) {
            tvVerfiy.setText("技师未认证，待认证");
            tvVerfiyb.setText("技师未认证，待认证");
        } else if (t == 1) {
            tvVerfiy.setText("认证中");
            tvVerfiyb.setText("认证中");
        } else if (t == 2) {
            tvVerfiy.setText("已认证");
            tvVerfiyb.setText("已认证");
        } else if (t == 3) {

            tvVerfiy.setText("认证失败，请重新认证");
            tvVerfiyb.setText("认证失败，请重新认证");
        }
        //status 0草稿1认证中2认证通过3认证拒绝
        if (e == 0) {
            tvExpertVerfiy.setText("专家未认证，待认证");
            tvExpertVerfiyb.setText("专家未认证，待认证");
        } else if (e == 1) {
            tvExpertVerfiy.setText("认证中");
            tvExpertVerfiyb.setText("认证中");
        } else if (e == 2) {
            tvExpertVerfiy.setText("已认证");
            tvExpertVerfiyb.setText("已认证");
        } else if (e == 3) {
            tvExpertVerfiy.setText("认证失败，请重新认证");
            tvExpertVerfiyb.setText("认证失败，请重新认证");
        }
        rlWorkerVerfity.setOnClickListener((v) -> {
            doWorkAuth();
        });
        rlWorkerVerfityB.setOnClickListener((v) -> {
            doWorkAuthB();
        });

        rlExpertVerfity.setOnClickListener((v) -> {
            JumpItent.jump(getActivity(), SpecialistAuthListActivity.class);
        });
        rlExpertVerfityB.setOnClickListener((v) -> {

            if (t == 2) {
                if (e == 1 || e == 2) {
                    //如果是认证中  和 认证完成 跳到详情界面 不可修改
                    JumpItent.jump(getActivity(), SpecialistSkillInfoDetailActivity.class);
                } else {
                    if (mAuthStatusBean == null) {
                        //多次访问状态的接口 造成mAuthStatusBean == null
                        ToastUtil.get().showToast(getActivity(), "请稍后操作");
                        return;
                    }
                    startActivity(new Intent(getActivity(), SpecialistSkillTypeActivity.class).putExtra("status", mAuthStatusBean.qual));
                }
            } else {
                ToastUtil.get().showToast(getActivity(), "请先进行技师认证");
            }
        });

    }

    @Override
    protected void initView() {
        rlWorkerVerfity = findViewById(R.id.rl_worker_verfity);
        rlWorkerVerfityB = findViewById(R.id.rl_worker_verfity_b);
        findViewById(R.id.rl_worker_verfity_c).setOnClickListener(view -> startActivity(new Intent(getContext(), SecurityCompanyDetailsActivity.class)));
        findViewById(R.id.rl_worker_verfity_d).setOnClickListener(view -> startActivity(new Intent(getContext(), ExpertDetailsActivity.class)));
        tvVerfiy = (TextView) findViewById(R.id.tv_verfity_status);
        tvVerfiyb = (TextView) findViewById(R.id.tv_verfity_status_b);
        tvExpertVerfiy = (TextView) findViewById(R.id.tv_expert_verfity_status);
        tvExpertVerfiyb = (TextView) findViewById(R.id.tv_expert_verfity_status_b);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        iv_header = (SimpleDraweeView) findViewById(R.id.iv_user_header);
        mIvPersonalQRCode = findViewById(R.id.iv_personalQRCode);
        rlExpertVerfity = (RelativeLayout) findViewById(R.id.rl_expert_verfity);
        rlExpertVerfityB = (RelativeLayout) findViewById(R.id.rl_expert_verfity_b);
        rbFree = findViewById(R.id.rb_free);
        rbStop = findViewById(R.id.rb_stop);
        rbWorking = findViewById(R.id.rb_working);
        rbWorking = findViewById(R.id.rb_working);
        rbWorkStatus = findViewById(R.id.rg_workStauts);
        findViewById(R.id.iv_user_header).setOnClickListener((v) -> {
            PersonInfoActivity.jumpToActivity(getActivity());
        });

        //评价
        findViewById(R.id.rl_evaluate).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), EvaluateActivity.class));
        });
        //邀请
        findViewById(R.id.rl_ivite).setOnClickListener((v) -> {
            InviteView inviteView = new InviteView(getActivity(), true);
            inviteView.show();
        });
        //设置
        findViewById(R.id.iv_setting).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), SettingActivity.class));
        });

        rbWorkStatus.setOnCheckedChangeListener(this);
    }

    @Override
    protected void setListener() {
        // 二维码头像
        mIvPersonalQRCode.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("qrcodeTitle", EanfangApplication.get().getUser().getAccount().getRealName());
            bundle.putString("qrcodeAddress", EanfangApplication.get().getUser().getAccount().getQrCode());
            bundle.putString("qrcodeMessage", "personal");
            JumpItent.jump(getActivity(), QrCodeShowActivity.class, bundle);
//            personalQRCodeDialog = new PersonalQRCodeDialog(getActivity(), EanfangApplication.get().getUser().getAccount().getQrCode());
//            personalQRCodeDialog.show();
        });

    }

    /**
     * 更改技师工作状态
     */
    private void setWorkStatus(int status) {
        EanfangHttp.post(UserApi.GET_WORKER_CHANGE)
                .params("accId", EanfangApplication.getApplication().getAccId())
                .params("status", status)
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, (bean) -> {
                }));
    }

    @Override
    public void onResume() {
        super.onResume();
        initDatas();
        getWorkInfo();
    }

    public void initDatas() {
        LoginBean user = EanfangApplication.getApplication().getUser();
        if (!StringUtils.isEmpty(user.getAccount().getNickName())) {
            tv_user_name.setText(user.getAccount().getNickName());
        }

        if (!StringUtils.isEmpty(user.getAccount().getAvatar())) {
            iv_header.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + user.getAccount().getAvatar()));
        }
        /**
         * 获取技师工作状态
         * */
        String mStatus = PrefUtils.getString("status", "");
        if (mStatus.equals("空闲状态")) {
            rbFree.setChecked(true);
            rbStop.setChecked(false);
            rbWorking.setChecked(false);
        } else if (mStatus.equals("停止接单")) {
            rbFree.setChecked(false);
            rbStop.setChecked(true);
            rbWorking.setChecked(false);
        } else if (mStatus.equals("工作中")) {
            rbFree.setChecked(false);
            rbStop.setChecked(false);
            rbWorking.setChecked(true);
        }
    }

    // 判断是否认证
    private void doWorkAuth() {
        // 技师未认证，提示完善个人资料

        String realName = EanfangApplication.get().getUser().getAccount().getRealName();
        if (StringUtils.isEmpty(realName) || "待提供".equals(realName)) {
            showToast("请先完善个人资料");
        } else {
            JumpItent.jump(getActivity(), NewAuthListActivity.class);
//            JumpItent.jump(getActivity(), AuthListActivity.class);
        }
    }

    // 判断是否认证
    private void doWorkAuthB() {
        // 技师未认证，提示完善个人资料
        String realName = EanfangApplication.get().getUser().getAccount().getRealName();
        if (StringUtils.isEmpty(realName) || "待提供".equals(realName)) {
            showToast("请先完善个人资料");
        } else {
            JumpItent.jump(getActivity(), TechnicianCertificationActivity.class);
        }
    }

    private void doExpertWorkAuth() {
        // 技师未认证，提示完善个人资料

        String realName = EanfangApplication.get().getUser().getAccount().getRealName();
        if (StringUtils.isEmpty(realName) || "待提供".equals(realName)) {
            showToast("请先完善个人资料");
        } else {
            JumpItent.jump(getActivity(), SpecialistAuthListActivity.class);
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_free:
                doChangeWorkStatus("空闲状态");
                break;
            case R.id.rb_stop:
                doChangeWorkStatus("停止接单");
                break;
            case R.id.rb_working:
                doChangeWorkStatus("工作中");
                break;
            default:
                break;
        }
    }

    public void doChangeWorkStatus(String status) {
        setWorkStatus(Config.get().getConstBean().getData().getShopConstant().get(Constant.WORK_STATUS).indexOf(status));
        PrefUtils.setString("status", status);
    }
}
