package net.eanfang.worker.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.LoginBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.BuildConfig;
import net.eanfang.worker.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/10  11:56
 * @email houzhongzhou@yeah.net
 * @desc 登录
 */

public class LoginActivity extends BaseActivity {
    public static final String TAG = LoginActivity.class.getSimpleName();

    private Context context = this;
    private String legalText;

    private EditText et_phone;
    private EditText et_yanzheng;
    private TextView tv_yanzheng;
    private AppCompatCheckBox cb;
    private TextView read;
    private Button btn_login;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
//        supprotToolbar();
        registerListener();
        setTitle("登录");
        initData();

        EanfangHttp.setWorker();

    }


    private void registerListener() {
        btn_login.setOnClickListener(v -> {
            String userPhone = et_phone.getText().toString().trim();
            String userAulth = et_yanzheng.getText().toString().trim();
            if (!BuildConfig.LOG_DEBUG) {

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
            }

            //调试阶段
            if (BuildConfig.LOG_DEBUG) {
                if (StringUtils.isEmpty(userPhone)) {
                    userPhone = "13800138020";
                }
                if (StringUtils.isEmpty(userAulth)) {
                    userAulth = "admin";
                }
                if (userAulth.equals("admin")) {
                    setLogin(userPhone, userAulth);
                }
            } else {
                setVerfiyLogin(userPhone, userAulth);
            }

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
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    /**
     * 验证码登录
     *
     * @param phone 电话号
     * @param pwd   验证码
     */
    private void setVerfiyLogin(String phone, String pwd) {
        EanfangHttp.setToken("");
        EanfangHttp.post(UserApi.APP_LOGIN_VERIFY)
                .params("mobile", phone)
                .params("verifycode", pwd)
                .execute(new EanfangCallback<LoginBean>(LoginActivity.this, true, LoginBean.class, (bean) -> {
                    EanfangApplication.get().set(LoginBean.class.getName(), JSONObject.toJSONString(bean, FastjsonConfig.config));
                    EanfangHttp.setToken(bean.getToken());
                    goMain();
                }));

    }

    /**
     * 密码登录
     *
     * @param phone 电话号
     * @param pwd   验证码
     */
    private void setLogin(String phone, String pwd) {
        JSONObject object = new JSONObject();
        try {
            object.put("username", phone);
            object.put("password", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EanfangHttp.post(UserApi.APP_LOGIN)
                .upJson(object.toJSONString())
                .execute(new EanfangCallback<LoginBean>(LoginActivity.this, false, LoginBean.class, (bean) -> {
                    EanfangApplication.get().set(LoginBean.class.getName(), JSONObject.toJSONString(bean, FastjsonConfig.config));
                    EanfangHttp.setToken(bean.getToken());
                    goMain();
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
                .execute(new EanfangCallback<String>(LoginActivity.this, false, String.class, (bean) -> {
                    showToast(R.string.hint_success_verify);
                }));
    }

    private void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_yanzheng = (EditText) findViewById(R.id.et_yanzheng);
        tv_yanzheng = (TextView) findViewById(R.id.tv_yanzheng);

        cb = (AppCompatCheckBox) findViewById(R.id.cb);
//        cb.setBackgroundColor(#774473);
        read = (TextView) findViewById(R.id.tv_read_agreement);

        btn_login = (Button) findViewById(R.id.btn_login);

//        validator = new Validator(this);
//        validator.setValidationListener(this);
    }

    private void initData() {
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

//        PermissionUtils.get(this).getStoragePermission(() -> {
//            //更新
//            UpdateManager manager = new UpdateManager(this, BuildConfig.TYPE);
//            manager.checkUpdate();
//        });

    }

//    @Override
//    public void onValidationSucceeded() {
//        Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onValidationFailed(List<ValidationError> errors) {
//        for (ValidationError error : errors) {
//            View view = error.getView();
//            String message = error.getCollatedErrorMessage(this);
//
//            // Display error messages ;)
//            if (view instanceof EditText) {
//                ((EditText) view).setError(message);
//            } else {
//                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//            }
//        }
//    }


    //跳转首页
    synchronized void goMain() {
        showToast("欢迎使用易安防");
        startActivity(new Intent(this, MainActivity.class));
        finishSelf();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //处理返回按钮被按下
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //退出登录
            Intent intent = new Intent(context.getPackageName() + ".ExitListenerReceiver");
            context.sendBroadcast(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
