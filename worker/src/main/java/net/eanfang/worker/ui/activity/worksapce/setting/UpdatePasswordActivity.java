package net.eanfang.worker.ui.activity.worksapce.setting;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.listener.MultiClickListener;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2018/8/23
 * @description 修改密码
 */
public class UpdatePasswordActivity extends BaseActivity {

    //手机号
    @BindView(R.id.et_phone)
    EditText etPhone;
    //获取验证码
    @BindView(R.id.ll_yanzheng)
    LinearLayout llYanzheng;
    //验证码
    @BindView(R.id.et_yanzheng)
    EditText etYanzheng;
    //新密码
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    //确认密码
    @BindView(R.id.et_conifrm_password)
    EditText etConifrmPassword;
    //提交
    @BindView(R.id.rl_confirm)
    RelativeLayout rlConfirm;
    //倒计时
    @BindView(R.id.tv_verify)
    TextView tvVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_password);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setLeftBack();
        setTitle("修改密码");
    }

    private void initListener() {
        rlConfirm.setOnClickListener(new MultiClickListener(this, this::isCheckInfo, this::doSubmit));
        llYanzheng.setOnClickListener(v -> {
            if (StringUtils.isEmpty(etPhone.getText().toString().trim())) {
                showToast("请输入手机号");
                return;
            }
            doGetVerifyCode();
            v.setEnabled(false);
            timer.start();
        });
    }

    //验证码倒计时
    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvVerify.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            tvVerify.setEnabled(true);
            tvVerify.setText("获取验证码");
        }
    };

    /**
     * 获取验证码
     */

    private void doGetVerifyCode() {

    }


    public void doSubmit() {

    }

    public boolean isCheckInfo() {
        String mMobile = etPhone.getText().toString().trim();
        String mVerify = etYanzheng.getText().toString().trim();
        String mPassword = etNewPassword.getText().toString().trim();
        String mConfirmPassword = etConifrmPassword.getText().toString().trim();

        if (StringUtils.isEmpty(mMobile)) {
            showToast("请输入手机号");
            return false;
        }
        if (StringUtils.isEmpty(mVerify)) {
            showToast("请输入验证码");
            return false;
        }
        if (StringUtils.isEmpty(mPassword)) {
            showToast("请输入密码");
            return false;
        }
        if (StringUtils.isEmpty(mConfirmPassword)) {
            showToast("确认密码不能为空");
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
