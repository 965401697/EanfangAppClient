package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.biz.model.SpecialistAuthStatusBean;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.activity.InviteFriendActivity;
import com.eanfang.ui.activity.QrCodeShowActivity;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.my.EvaluateActivity;
import net.eanfang.worker.ui.activity.my.PersonInfoActivity;
import net.eanfang.worker.ui.activity.my.SettingActivity;
import net.eanfang.worker.ui.activity.my.certification.NewAuthListActivity;
import net.eanfang.worker.ui.activity.my.specialist.SpecialistAuthListActivity;
import net.eanfang.worker.ui.activity.my.specialist.SpecialistSkillInfoDetailActivity;
import net.eanfang.worker.ui.activity.my.specialist.SpecialistSkillTypeActivity;
import net.eanfang.worker.ui.activity.techniciancertification.TechnicianCertificationActivity;
import net.eanfang.worker.ui.widget.InviteView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:08
 * @email houzhongzhou@yeah.net
 * @desc 我的
 */

public class MyFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.iv_user_header)
    CircleImageView ivHeader;
    // 二维码头像
    @BindView(R.id.iv_personalQRCode)
    ImageView ivPersonalQRCode;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.rb_free)
    RadioButton rbFree;
    @BindView(R.id.rb_stop)
    RadioButton rbStop;
    @BindView(R.id.rb_working)
    RadioButton rbWorking;
    @BindView(R.id.rg_workStauts)
    RadioGroup rgWorkStauts;
    @BindView(R.id.rl_worker_verfity)
    RelativeLayout rlWorkerVerfity;
    @BindView(R.id.tv_verfity_status)
    TextView tvVerfiy;
    @BindView(R.id.tv_expert_verfity_status)
    TextView tvExpertVerfiy;
    @BindView(R.id.rl_expert_verfity)
    RelativeLayout rlExpertVerfity;
    @BindView(R.id.tv_verfity_status_b)
    TextView tvVerfiyb;
    @BindView(R.id.tv_expert_verfity_status_b)
    TextView tvExpertVerfiyb;
    @BindView(R.id.rl_worker_verfity_b)
    RelativeLayout rlWorkerVerfityB;
    @BindView(R.id.rl_expert_verfity_b)
    RelativeLayout rlExpertVerfityB;
    private int verify = -1;
    private SpecialistAuthStatusBean mAuthStatusBean;
    private int e = 0;
    private int t = 0;
    private int workerStatus;

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
        EanfangHttp.post(UserApi.POST_WORKER_EXPERT_AUTH_STATUS)
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, (bean) -> {
                    //专家
                    e = (int) bean.get("expert");
                    //技师
                    t = (int) bean.get("techWorker");
                    setOnClick(e, t);
                }));
        // 获取认证状态
        EanfangHttp.post(UserApi.GET_EXPERT_CERTIFICATION_STATUS).params("accId", WorkerApplication.get().getAccId()).execute(new EanfangCallback<SpecialistAuthStatusBean>(getActivity(), true, SpecialistAuthStatusBean.class, (bean) -> {
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
            BaseApplication.get().getAccount().setRealVerify(1);
        } else if (t == 1) {
            tvVerfiy.setText("认证中");
            tvVerfiyb.setText("认证中");
            BaseApplication.get().getAccount().setRealVerify(1);
        } else if (t == 2) {
            tvVerfiy.setText("已认证");
            tvVerfiyb.setText("已认证");
            BaseApplication.get().getAccount().setRealVerify(0);
        } else if (t == 3) {
            tvVerfiy.setText("认证失败，请重新认证");
            tvVerfiyb.setText("认证失败，请重新认证");
            BaseApplication.get().getAccount().setRealVerify(1);
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
        headViewSize(ivHeader, (int) getResources().getDimension(com.eanfang.R.dimen.dimen_155));
        workerStatus = WorkerApplication.get().getLoginBean().getWorkerStatus();
        rgWorkStauts.setOnCheckedChangeListener(this);
    }

    @Override
    protected void setListener() {
        // 二维码头像
        ivPersonalQRCode.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("qrcodeTitle", WorkerApplication.get().getLoginBean().getAccount().getRealName());
            bundle.putString("qrcodeAddress", WorkerApplication.get().getLoginBean().getAccount().getQrCode());
            bundle.putString("qrcodeMessage", "personal");
            JumpItent.jump(getActivity(), QrCodeShowActivity.class, bundle);
//            personalQRCodeDialog = new PersonalQRCodeDialog(getActivity(), WorkerApplication.get().getLoginBean().getAccount().getQrCode());
//            personalQRCodeDialog.show();
        });

    }

    /**
     * 更改技师工作状态
     */
    private void setWorkStatus(int status) {
        EanfangHttp.post(UserApi.GET_WORKER_CHANGE)
                .params("accId", WorkerApplication.get().getAccId())
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
        LoginBean user = WorkerApplication.get().getLoginBean();
        if (!StringUtils.isEmpty(user.getAccount().getNickName())) {
            tvUserName.setText(user.getAccount().getNickName());
        }

        if (!StringUtils.isEmpty(user.getAccount().getAvatar())) {
            GlideUtil.intoImageView(getActivity(), Uri.parse(BuildConfig.OSS_SERVER + user.getAccount().getAvatar()), ivHeader);
        }
        if (workerStatus == 0) {
            rbFree.setChecked(true);
            rbStop.setChecked(false);
            rbWorking.setChecked(false);
        } else if (workerStatus == 2) {
            rbFree.setChecked(false);
            rbStop.setChecked(true);
            rbWorking.setChecked(false);
        } else if (workerStatus == 1) {
            rbFree.setChecked(false);
            rbStop.setChecked(false);
            rbWorking.setChecked(true);
        }
    }

    // 判断是否认证
    private void doWorkAuth() {
        // 技师未认证，提示完善个人资料

        String realName = WorkerApplication.get().getLoginBean().getAccount().getRealName();
        if (StringUtils.isEmpty(realName) || "待提供".equals(realName)) {
            showToast("请先完善个人资料");
        } else {
            JumpItent.jump(getActivity(), NewAuthListActivity.class);
//            JumpItent.jump(getActivity(), AuthListActivity.class);
        }
    }

    private void doExpertWorkAuth() {
        // 技师未认证，提示完善个人资料
        String realName = WorkerApplication.get().getLoginBean().getAccount().getRealName();
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
                workerStatus = 0;
                doChangeWorkStatus("空闲状态");
                break;
            case R.id.rb_stop:
                workerStatus = 2;
                doChangeWorkStatus("停止接单");
                break;
            case R.id.rb_working:
                workerStatus = 1;
                doChangeWorkStatus("工作中");
                break;
            default:
                break;
        }
    }

    public void doChangeWorkStatus(String status) {
        setWorkStatus(Config.get().getConstBean().getData().getShopConstant().get(Constant.WORK_STATUS).indexOf(status));
//        PrefUtils.setString("status", status);
    }

    @OnClick({R.id.iv_setting, R.id.iv_user_header, R.id.rl_worker_verfity, R.id.rl_expert_verfity, R.id.rl_ivite, R.id.iv_personalQRCode,
            R.id.rl_evaluate, R.id.rl_worker_verfity_b, R.id.rl_expert_verfity_b, R.id.img_invite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_setting:
                //设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.iv_user_header:
                PersonInfoActivity.jumpToActivity(getActivity());
                break;
            case R.id.rl_worker_verfity:
                doWorkAuth();
                break;
            case R.id.rl_expert_verfity:
                JumpItent.jump(getActivity(), SpecialistAuthListActivity.class);
                break;
            case R.id.rl_ivite:
                //邀请
                InviteView inviteView = new InviteView(getActivity(), true);
                inviteView.show();
                break;
            case R.id.iv_personalQRCode:
                Bundle bundle = new Bundle();
                bundle.putString("qrcodeTitle", WorkerApplication.get().getLoginBean().getAccount().getRealName());
                bundle.putString("qrcodeAddress", WorkerApplication.get().getLoginBean().getAccount().getQrCode());
                bundle.putString("qrcodeMessage", "personal");
                JumpItent.jump(getActivity(), QrCodeShowActivity.class, bundle);
                break;
            case R.id.rl_evaluate:
                //评价
                startActivity(new Intent(getActivity(), EvaluateActivity.class));
                break;
            case R.id.rl_worker_verfity_b:
                doWorkAuthB();
                break;
            case R.id.rl_expert_verfity_b:
                doExpertB();
                break;
            //邀请活动入口
            case R.id.img_invite:
                //闪退  先注释掉
                startActivity(new Intent(getActivity(), InviteFriendActivity.class));
                break;
            default:
                break;
        }
    }

    private void doExpertB() {
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
    }


    // 判断是否认证
    private void doWorkAuthB() {
        // 技师未认证，提示完善个人资料
        String realName = WorkerApplication.get().getLoginBean().getAccount().getRealName();
        if (StringUtils.isEmpty(realName) || "待提供".equals(realName)) {
            showToast("请先完善个人资料");
        } else {
            JumpItent.jump(getActivity(), TechnicianCertificationActivity.class);
        }
    }

}