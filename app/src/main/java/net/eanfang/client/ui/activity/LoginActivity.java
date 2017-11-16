package net.eanfang.client.ui.activity;

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

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.util.StringUtils;
import net.eanfang.client.util.UpdateManager;

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

public class LoginActivity extends BaseActivity implements Validator.ValidationListener {
    public static final String TAG = LoginActivity.class.getSimpleName();

    private Context context = this;
    private String legalText;

    @NotEmpty
    private EditText et_phone;
    @NotEmpty
    private EditText et_yanzheng;
    private TextView tv_yanzheng;
    private AppCompatCheckBox cb;
    private TextView read;
    private Button btn_login;
    private Validator validator;


    private Dialog dialog;
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
        setContentView(R.layout.activity_nor_login);
        initView();
//        supprotToolbar();
        registerListener();
        setTitle("登录");
        initData();

    }


    private void registerListener() {
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
            setLogin(userPhone, userAulth);

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
     * 登录
     *
     * @param phone 电话号
     * @param pwd   验证码
     */
    private void setLogin(String phone, String pwd) {
//        JSONObject object = new JSONObject();
//        try {
//            object.put("account", phone);
//            object.put("password", pwd);
//            object.put("type", BuildConfig.TYPE);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        EanfangHttp.post(UserService.APP_LOGIN)
//                .upJson(object)
//                .execute(new EanfangCallback<User>(LoginActivity.this, false) {
//                    @Override
//                    public void onSuccess(User bean) {
//                        super.onSuccess(bean);
//                        goMain();
//                        Object obj = bean;
//                        if (obj instanceof User) {
//                            EanfangApplication.get().set(User.class.getName(), obj);
//                            JPushInterface.setAlias(getApplicationContext(), EanfangApplication.get().getUser().getToken(), new TagAliasCallback() {
//                                @Override
//                                public void gotResult(int i, String s, Set<String> set) {
//                                    Log.d(TAG, "gotResult: " + s);
//                                }
//                            });
//                            //OkGo  head 写入 token
//                            OkGo http = EanfangApplication.get().getHttp();
//                            //清除 headers
//                            http.getCommonHeaders().clear();
//                            HttpHeaders headers = new HttpHeaders();
//                            headers.put("token", EanfangApplication.get().getUser().getToken());
//                            http.addCommonHeaders(headers);
//                        }
//                    }
//
//                    @Override
//                    public void onFail(Integer code, String message, JSONObject jsonObject) {
//                        super.onFail(code, message, jsonObject);
//                        showToast(message);
//                    }
//                });

    }

    /**
     * 获取验证码
     *
     * @param phone 电话号
     */
    private void getVerificationCode(String phone) {
//        EanfangHttp.get(UserService.GET_VERIFY_CODE)
//                .tag(this)
//                .params("account", phone)
//                .params("type", BuildConfig.TYPE)
//                .execute(new EanfangCallback(LoginActivity.this, false) {
//                    @Override
//                    public void onSuccess(Object bean) {
//                        super.onSuccess(bean);
//                        showToast("验证码获取成功");
//                    }
//
//                    @Override
//                    public void onFail(Integer code, String message, JSONObject jsonObject) {
//                        super.onFail(code, message, jsonObject);
//                        showToast(message);
//                    }
//                });
    }

    private void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_yanzheng = (EditText) findViewById(R.id.et_yanzheng);
        tv_yanzheng = (TextView) findViewById(R.id.tv_yanzheng);

        cb = (AppCompatCheckBox) findViewById(R.id.cb);
//        cb.setBackgroundColor(#774473);
        read = (TextView) findViewById(R.id.tv_read_agreement);

        btn_login = (Button) findViewById(R.id.btn_login);

        validator = new Validator(this);
        validator.setValidationListener(this);
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

        //更新
        UpdateManager manager = new UpdateManager(this);
        manager.checkUpdate();

    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }


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
        if (keyCode == KeyEvent.KEYCODE_BACK) {//处理返回按钮被按下
            //退出登录
            Intent intent = new Intent(context.getPackageName() + ".ExitListenerReceiver");
            context.sendBroadcast(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
