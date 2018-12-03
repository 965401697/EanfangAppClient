package net.eanfang.client.ui.fragment.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONException;
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
import net.eanfang.client.ui.activity.worksapce.GuideActivity;
import net.eanfang.client.ui.activity.worksapce.SetPasswordActivity;
import net.eanfang.client.util.PrefUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Guanluocang
 * @date on 2018/8/13  13:37
 * @decision 密码输入
 */
public class PasswordFragment extends BaseFragment {

    private String legalText;
    private EditText et_phone;
    private EditText et_password;
    private AppCompatCheckBox cb;
    private TextView read;
    private Button btn_login;
    /**
     * true :密码为空 或者默认密码  false:修改后的密码
     */
    private boolean isUpdataPassword = false;

    public static PasswordFragment getInstance() {
        PasswordFragment passwordFragment = new PasswordFragment();
        return passwordFragment;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_password;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_password);
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
            String userAulth = et_password.getText().toString().trim();
            if (StringUtils.isEmpty(userPhone)) {
                showToast("手机号不能为空");
                return;
            }

            if (StringUtils.isEmpty(userAulth)) {
                showToast("密码不能为空");
                return;
            }
            if (!cb.isChecked()) {
                showToast("同意易安防会员章程和协议后才可以登陆使用");
                return;
            }
            setLogin(userPhone, userAulth);
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

    /**
     * 密码登录
     *
     * @param phone 电话号
     * @param pwd   验证码
     */
    private void setLogin(String phone, String pwd) {
        EanfangHttp.setToken("");
        JSONObject object = new JSONObject();
        try {
            object.put("username", phone);
            object.put("password", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EanfangHttp.post(UserApi.APP_LOGIN)
                .upJson(object.toJSONString())
                .execute(new EanfangCallback<LoginBean>(getActivity(), true, LoginBean.class, (bean) -> {
                    isUpdataPassword = bean.getAccount().isSimplePwd();
                    EanfangApplication.get().set(LoginBean.class.getName(), JSONObject.toJSONString(bean, FastjsonConfig.config));
                    EanfangHttp.setToken(bean.getToken());
                    goMain();
                }));

    }


    //跳转首页
    synchronized void goMain() {
        if (PrefUtils.getVBoolean(getActivity(), PrefUtils.GUIDE)) {
            startActivity(new Intent(getActivity(), GuideActivity.class));
            finishSelf();
        } else if (isUpdataPassword) {
            startActivity(new Intent(getActivity(), SetPasswordActivity.class));
            finishSelf();
        } else {

            startActivity(new Intent(getActivity(), MainActivity.class));
            finishSelf();
        }
    }
}
