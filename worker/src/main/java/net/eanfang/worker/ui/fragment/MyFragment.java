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
import com.eanfang.model.AuthStatusBean;
import com.eanfang.model.LoginBean;
import com.eanfang.ui.activity.QrCodeShowActivity;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.eanfang.witget.PersonalQRCodeDialog;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.my.EvaluateActivity;
import net.eanfang.worker.ui.activity.my.PersonInfoActivity;
import net.eanfang.worker.ui.activity.my.SettingActivity;
import net.eanfang.worker.ui.activity.my.specialist.SpecialistAuthListActivity;
import net.eanfang.worker.ui.activity.my.certification.NewAuthListActivity;
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
    private TextView tv_user_name, tvVerfiy, tvExpertVerfiy;
    private RelativeLayout rlWorkerVerfity, rlExpertVerfity;

    private SimpleDraweeView iv_header;
    // 二维码头像
    private SimpleDraweeView mIvPersonalQRCode;
    // Dialog
    private PersonalQRCodeDialog personalQRCodeDialog;
    private RadioButton rbWorking, rbFree, rbStop;
    private RadioGroup rbWorkStatus;

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
        EanfangHttp.post(UserApi.POST_WORKER_AUTH_STATUS)
                .params("accId", EanfangApplication.getApplication().getAccId())
                .execute(new EanfangCallback<AuthStatusBean>(getActivity(), true, AuthStatusBean.class, (bean) -> {
                    setOnClick(bean.getVerify());
                }));
    }

    private void setOnClick(int status) {

        //status 0草稿1认证中2认证通过3认证拒绝
        if (status == 0) {
            tvVerfiy.setText("技师未认证，待认证");
        } else if (status == 1) {
            tvVerfiy.setText("认证中");
        } else if (status == 2) {
            tvVerfiy.setText("已认证");
        } else if (status == 3) {
            tvVerfiy.setText("认证失败，请重新认证");
        }
        rlWorkerVerfity.setOnClickListener((v) -> {
            doWorkAuth();
        });
        rlExpertVerfity.setOnClickListener((v) -> {
            JumpItent.jump(getActivity(), SpecialistAuthListActivity.class);
        });
    }

    @Override
    protected void initView() {
        rlWorkerVerfity = findViewById(R.id.rl_worker_verfity);
        tvVerfiy = (TextView) findViewById(R.id.tv_verfity_status);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        iv_header = (SimpleDraweeView) findViewById(R.id.iv_user_header);
        mIvPersonalQRCode = findViewById(R.id.iv_personalQRCode);
        rlExpertVerfity = (RelativeLayout) findViewById(R.id.rl_expert_verfity);
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