package net.eanfang.client.ui.activity.worksapce.setting;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.listener.MultiClickListener;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2018/8/23
 * @description 更换手机号
 */
public class ChangePhoneActivity extends BaseActivity {

    //手机号
    @BindView(R.id.et_phone)
    EditText etPhone;
    //倒计时
    @BindView(R.id.tv_verify)
    TextView tvVerify;
    //获取验证码
    @BindView(R.id.ll_yanzheng)
    LinearLayout llYanzheng;
    //验证码
    @BindView(R.id.et_yanzheng)
    EditText etYanzheng;
    //提交
    @BindView(R.id.rl_confirm)
    RelativeLayout rlConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setLeftBack();
        setTitle("修改手机号");
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

        if (StringUtils.isEmpty(mMobile)) {
            showToast("请输入手机号");
            return false;
        }
        if (StringUtils.isEmpty(mVerify)) {
            showToast("请输入验证码");
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
