package net.eanfang.worker.ui.activity.worksapce.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.sys.activity.LoginActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.CleanMessageUtil;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * @author guanluocang
 * @data 2018/8/23
 * @description 修改密码
 */
public class UpdatePasswordActivity extends BaseActivity {

    //手机号
    @BindView(R.id.tv_phone)
    TextView tvPhone;
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
    @BindView(R.id.iv_show)
    ImageView ivShow;
    @BindView(R.id.iv_confirm_show)
    ImageView ivConfirmShow;
    //手机号
    private String mMobile = "";
    //验证码
    private String mVerify = "";
    //新密码
    private String mPassword = "";
    private boolean isHidden = true;
    // 确认密码
    private String mConfirmPassword = "";
    private boolean isConfirmHidden = true;
    /**
     * 解散团队
     */
    private boolean mDisslove = false;

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
        tvPhone.setText(WorkerApplication.get().getLoginBean().getAccount().getMobile());
        mDisslove = getIntent().getBooleanExtra("disslove", false);
    }

    private void initListener() {
        rlConfirm.setOnClickListener(new MultiClickListener(this, this::isCheckInfo, this::doSubmit));
        llYanzheng.setOnClickListener(v -> {
            mMobile = tvPhone.getText().toString().trim();
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
                .execute(new EanfangCallback<String>(UpdatePasswordActivity.this, true, String.class, (bean) -> {
                    showToast(R.string.hint_success_verify);
                }));
    }


    public void doSubmit() {
        EanfangHttp.post(UserApi.UPDATA_PASSWORD)
                .params("mobile", mMobile)
                .params("verifycode", mVerify)
                .params("newKey1", mPassword)//新密码
                .params("newKey2", mConfirmPassword)//确认密码
                .execute(new EanfangCallback<JSONObject>(UpdatePasswordActivity.this, true, JSONObject.class, (bean) -> {
                    showToast("修改成功");
                    if (!mDisslove) {
                        signout();
                    } else {
                        finishSelf();
                    }
                }));
    }

    private void signout() {
        EanfangHttp.get(UserApi.APP_LOGOUT)
                .execute(new EanfangCallback<com.alibaba.fastjson.JSONObject>(this, true, com.alibaba.fastjson.JSONObject.class, (bean) -> {
                    RongIM.getInstance().logout();//退出融云
                    CleanMessageUtil.clearAllCache(WorkerApplication.get());
//                    SharePreferenceUtil.get().clear();
                    startActivity(new Intent(UpdatePasswordActivity.this, LoginActivity.class));
                    finishSelf();
                }));
    }

    public boolean isCheckInfo() {
        mMobile = tvPhone.getText().toString().trim();
        mVerify = etYanzheng.getText().toString().trim();
        mPassword = etNewPassword.getText().toString().trim();
        mConfirmPassword = etConifrmPassword.getText().toString().trim();

        if (StringUtils.isEmpty(mMobile)) {
            showToast("请输入手机号");
            return false;
        }
        //电话号码是否符合格式
        if (!StringUtils.isMobileString(mMobile)) {
            showToast("请输入正确手机号");
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
        if (mPassword.length() < 6 || mPassword.length() > 18) {
            showToast("密码长度须为6-18位");
            return false;
        }
        if (!StringUtils.isPwdLegal(mPassword)) {
            showToast("密码至少包含一个字母和数字");
            return false;
        }
        if (StringUtils.isEmpty(mConfirmPassword)) {
            showToast("确认密码不能为空");
            return false;
        }
        if (!mPassword.equals(mConfirmPassword)) {
            showToast("密码输入不一致");
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @OnClick({R.id.iv_show, R.id.iv_confirm_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_show:
                if (isHidden) {
                    etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivShow.setImageResource(R.mipmap.ic_password_look);
                } else {
                    etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivShow.setImageResource(R.mipmap.ic_password_hide);
                }
                isHidden = !isHidden;
                etNewPassword.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etNewPassword.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.iv_confirm_show:
                if (isConfirmHidden) {
                    etConifrmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivConfirmShow.setImageResource(R.mipmap.ic_password_look);
                } else {
                    etConifrmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivConfirmShow.setImageResource(R.mipmap.ic_password_hide);
                }
                isConfirmHidden = !isConfirmHidden;
                etConifrmPassword.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence_confirm = etConifrmPassword.getText();
                if (charSequence_confirm instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence_confirm;
                    Selection.setSelection(spanText, charSequence_confirm.length());
                }
                break;
            default:
                break;
        }
    }
}
