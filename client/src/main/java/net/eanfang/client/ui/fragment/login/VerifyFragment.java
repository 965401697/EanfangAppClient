package net.eanfang.client.ui.fragment.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.LoginBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.MainActivity;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Guanluocang
 * @date on 2018/8/13  11:49
 * @decision 验证码输入
 */
public class VerifyFragment extends BaseFragment {

    private String legalText;
    private EditText et_phone;
    private EditText et_yanzheng;
    private TextView tv_yanzheng;
    private AppCompatCheckBox cb;
    private TextView read;
    private Button btn_login;

    public static VerifyFragment getInstance() {
        VerifyFragment verifyFragment = new VerifyFragment();
        return verifyFragment;
    }
    protected int setLayoutResouceId() {
        return R.layout.fragment_verify;
    }

    @Override
    protected void initData(Bundle arguments) {
    }

    @Override
    protected void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_yanzheng = (EditText) findViewById(R.id.et_yanzheng);
        tv_yanzheng = (TextView) findViewById(R.id.tv_yanzheng);
        cb = (AppCompatCheckBox) findViewById(R.id.cb);
        read = (TextView) findViewById(R.id.tv_read_agreement);
        btn_login = (Button) findViewById(R.id.btn_login);

        try {
            InputStream is = null;
            is = getResources().openRawResource(R.raw.legal);
            int size = 0;
            size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            legalText = new String(buffer, "UTF-8");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        EanfangHttp.setClient();
    }

    @Override
    protected void setListener() {
        btn_login.setOnClickListener(v -> {

            String userPhone = et_phone.getText().toString().trim();
            String userAulth = et_yanzheng.getText().toString().trim();
            if (StringUtils.isEmpty(userPhone)) {
                showToast("手机号不能为空");
                return;
            }

            if (StringUtils.isEmpty(userAulth)) {
                showToast("验证码不能为空");
                return;
            }
            if (!cb.isChecked()) {
                showToast("同意易安防会员章程和协议后才可以登陆使用");
                return;
            }
            setVerfiyLogin(userPhone, userAulth);
        });

        tv_yanzheng.setOnClickListener(v -> {
            if (StringUtils.isEmpty(et_phone.getText().toString().trim())) {
                showToast("手机号不能为空");
                return;
            }
            //电话号码是否符合格式
            if (!StringUtils.isMobileString(et_phone.getText().toString().trim())) {
                showToast("请输入正确手机号");
                return;
            }
            getVerificationCode(et_phone.getText().toString().trim());
            v.setEnabled(false);
            timer.start();
        });
        read.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("法律声明");
            builder.setMessage(legalText);
            // 同意
            builder.setPositiveButton("我同意", (dialog1, which) -> {
                dialog1.dismiss();
                cb.setChecked(true);
            });
            // 稍后更新
            builder.setNegativeButton("我不同意", (dialog1, which) -> {
                dialog1.dismiss();
                cb.setChecked(false);
            });
            Dialog noticeDialog = builder.create();
            noticeDialog.show();
        });
    }

    //验证码倒计时
    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tv_yanzheng.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            tv_yanzheng.setEnabled(true);
            tv_yanzheng.setText("获取验证码");
        }
    };

    /**
     * 登录
     *
     * @param phone 电话号
     * @param pwd   验证码
     */
    private void setVerfiyLogin(String phone, String pwd) {
        EanfangHttp.setToken("");
        EanfangHttp.post(UserApi.APP_LOGIN_VERIFY)
                .params("mobile", phone)
                .params("verifycode", pwd)
                .execute(new EanfangCallback<LoginBean>(getActivity(), true, LoginBean.class, (bean) -> {
                    EanfangApplication.get().set(LoginBean.class.getName(), JSONObject.toJSONString(bean, FastjsonConfig.config));
                    EanfangHttp.setToken(bean.getToken());
                    getActivity().runOnUiThread(() -> {
                        goMain();
                    });
                }));

    }


    /**
     * 获取验证码
     *
     * @param phone 电话号
     */
    private void getVerificationCode(String phone) {
        EanfangHttp.post(UserApi.GET_VERIFY_CODE)
                .params("mobile", phone)
                .execute(new EanfangCallback<String>(getActivity(), false, String.class, (bean) -> {
                    showToast(R.string.hint_success_verify);
                }));
    }

    //跳转首页
    synchronized void goMain() {
        startActivity(new Intent(getActivity(), MainActivity.class));
        finishSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

}