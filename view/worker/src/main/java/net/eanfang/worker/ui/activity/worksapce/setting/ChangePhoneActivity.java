package net.eanfang.worker.ui.activity.worksapce.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.sys.activity.LoginActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.CleanMessageUtil;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;

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

    //手机号
    private String mMobile = "";
    //验证码
    private String mVerify = "";

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

            mMobile = etPhone.getText().toString().trim();
            if (StringUtils.isEmpty(mMobile)) {
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
            llYanzheng.setEnabled(true);
            tvVerify.setText("获取验证码");
        }
    };

    /**
     * 获取验证码
     */
    private void doGetVerifyCode() {
        EanfangHttp.post(UserApi.GET_VERIFY_CODE)
                .params("mobile", mMobile)
                .execute(new EanfangCallback<String>(ChangePhoneActivity.this, false, String.class, (bean) -> {
                    showToast(R.string.hint_success_verify);
                }));
    }

    /**
     * 提交
     */
    public void doSubmit() {
        EanfangHttp.post(UserApi.UPDATA_MOBILE + "/" + mMobile + "/" + mVerify)
                .execute(new EanfangCallback<JSONObject>(ChangePhoneActivity.this, true, JSONObject.class, bean -> {
                    showToast("修改成功，请重新登录");
                    signout();
                }));
    }

    private void signout() {
        EanfangHttp.get(UserApi.APP_LOGOUT)
                .execute(new EanfangCallback<com.alibaba.fastjson.JSONObject>(this, true, com.alibaba.fastjson.JSONObject.class, (bean) -> {
                    RongIM.getInstance().logout();//退出融云
                    CleanMessageUtil.clearAllCache(EanfangApplication.get());
//                    SharePreferenceUtil.get().clear();
                    startActivity(new Intent(ChangePhoneActivity.this, LoginActivity.class));
                    finishSelf();
                }));
    }


    public boolean isCheckInfo() {
        mMobile = etPhone.getText().toString().trim();
        mVerify = etYanzheng.getText().toString().trim();

        if (StringUtils.isEmpty(mMobile)) {
            showToast("请输入手机号");
            return false;
        }
        if (StringUtils.isEmpty(mVerify)) {
            showToast("请输入验证码");
            return false;
        }
        //电话号码是否符合格式
        if (!StringUtils.isMobileString(mMobile)) {
            showToast("请输入正确手机号");
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
