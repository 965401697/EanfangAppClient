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
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.localcache.CacheUtil;
import com.eanfang.model.BaseDataBean;
import com.eanfang.model.ConstAllBean;
import com.eanfang.model.LoginBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UpdateManager;
import com.eanfang.util.V;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

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

        getBaseData();
        getConst();

    }


    private void registerListener() {
        btn_login.setOnClickListener(v -> {
            if (checkInfo()) {
                setVerfiyLogin();
            }
        });

        tv_yanzheng.setOnClickListener(v -> {
            if (checkInfo()) {
                getVerificationCode();
                v.setEnabled(false);
                timer.start();
            }
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

    private boolean checkInfo() {
        String userPhone = et_phone.getText().toString().trim();
        String userAulth = et_yanzheng.getText().toString().trim();
        if (StringUtils.isEmpty(userPhone)) {
            showToast("手机号不能为空");
            return false;
        }
        if (StringUtils.isEmpty(userAulth)) {
            showToast("验证码不能为空");
            return false;
        }
        if (!cb.isChecked()) {
            showToast("同意易安防会员章程和协议后才可以登陆使用");
            return false;
        }
        return true;
    }

    /**
     * 验证码登录
     */
    private void setVerfiyLogin() {
        EanfangHttp.post(UserApi.APP_LOGIN_VERIFY)
                .params("mobile", et_phone.getText().toString().trim())
                .params("verifycode", et_yanzheng.getText().toString().trim())
                .execute(new EanfangCallback<LoginBean>(LoginActivity.this, false, LoginBean.class, (bean) -> {
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
     */
    private void getVerificationCode() {
        EanfangHttp.post(UserApi.GET_VERIFY_CODE)
                .params("mobile", et_phone.getText().toString().trim())
                .execute(new EanfangCallback<String>(LoginActivity.this, false, String.class, (bean) -> {
                    showToast("验证码获取成功");
                }));
    }

    private void initView() {
        et_phone = findViewById(R.id.et_phone);
        et_yanzheng = findViewById(R.id.et_yanzheng);
        tv_yanzheng = findViewById(R.id.tv_yanzheng);
        cb = findViewById(R.id.cb);
        read = findViewById(R.id.tv_read_agreement);
        btn_login = findViewById(R.id.btn_login);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    private void initData() {
        try {
            InputStream is;
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
    }

    @Override
    public void onValidationSucceeded() {
//        Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
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

    /**
     * 请求基础数据
     */
    private void getBaseData() {
        String url;
        String md5 = V.v(() -> Config.get(this).getBaseDataBean().getMD5());
        if (StringUtils.isEmpty(md5)) {
            url = NewApiService.GET_BASE_DATA_CACHE + "0";
        } else {
            url = NewApiService.GET_BASE_DATA_CACHE + md5;
        }
        EanfangHttp.get(url)
                .tag(this)
                .execute(new EanfangCallback<String>(this, false, String.class, (str) -> {
                    if (!str.contains(Constant.NO_UPDATE)) {
                        CacheUtil.put(this, getPackageName(), BaseDataBean.class.getName(), str);
//                        BaseDataBean newDate = JSONObject.parseObject(str, BaseDataBean.class);
//                        EanfangApplication.get().set(BaseDataBean.class.getName(), JSONObject.toJSONString(newDate, FastjsonConfig.config));
                    }
                }));

    }

    /**
     * 请求静态常量
     */
    private void getConst() {
        String url;
        String md5 = V.v(() -> Config.get(this).getConstBean().getMD5());
        if (StringUtils.isEmpty(md5)) {
            url = NewApiService.GET_CONST_CACHE + "0";
        } else {
            url = NewApiService.GET_CONST_CACHE + md5;
        }
        EanfangHttp.get(url)
                .tag(this)
                .execute(new EanfangCallback<String>(this, false, String.class, (str) -> {
                    if (!str.contains(Constant.NO_UPDATE)) {
                        CacheUtil.put(this, getPackageName(), ConstAllBean.class.getName(), str);
//                        ConstAllBean newDate = JSONObject.parseObject(str, ConstAllBean.class);
//                        EanfangApplication.get().set(ConstAllBean.class.getName(), JSONObject.toJSONString(newDate, FastjsonConfig.config));
                    }

                }));
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
