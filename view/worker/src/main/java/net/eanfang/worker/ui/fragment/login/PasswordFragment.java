package net.eanfang.worker.ui.fragment.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatCheckBox;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.MainActivity;
import net.eanfang.worker.ui.activity.worksapce.GuideActivity;
import net.eanfang.worker.util.PrefUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Guanluocang
 * @date on 2018/8/13  13:37
 * @decision 密码输入
 */
/**
 * 废弃
 * 2019-04-30 15:26:04
 */
@Deprecated
public class PasswordFragment extends BaseFragment {

    private String legalText;
    private EditText et_phone;
    private EditText et_password;
    private AppCompatCheckBox cb;
    private TextView read;
    private Button btn_login;
    private ImageView mShow;
    /**
     * true :密码为空 或者默认密码  false:修改后的密码
     */
    private boolean isUpdataPassword = false;
    private boolean isHidden = true;

    public static PasswordFragment getInstance() {
        PasswordFragment passwordFragment = new PasswordFragment();
        return passwordFragment;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_password_eanfang;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        cb = findViewById(R.id.cb);
        read = findViewById(R.id.tv_read_agreement);
        btn_login = findViewById(R.id.btn_login);
        mShow = findViewById(R.id.iv_show);

        try {
            InputStream is = null;
            is = getResources().openRawResource(R.raw.legal);
            int size = 0;
            size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            legalText = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        EanfangHttp.setWorker();
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
        mShow.setOnClickListener((v) -> {
            if (isHidden) {
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mShow.setImageResource(R.mipmap.ic_password_look);
            } else {
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mShow.setImageResource(R.mipmap.ic_password_hide);
            }
            isHidden = !isHidden;
            et_password.postInvalidate();
            //切换后将EditText光标置于末尾
            CharSequence charSequence = et_password.getText();
            if (charSequence instanceof Spannable) {
                Spannable spanText = (Spannable) charSequence;
                Selection.setSelection(spanText, charSequence.length());
            }
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
                    WorkerApplication.get().set(LoginBean.class.getName(), bean);
                    EanfangHttp.setToken(bean.getToken());
                    goMain();
                }));

    }


    //跳转首页
    synchronized void goMain() {
        if (PrefUtils.getVBoolean(getActivity(), PrefUtils.GUIDE)) {
            startActivity(new Intent(getActivity(), GuideActivity.class));
            finishSelf();
//        } else if (isUpdataPassword) {
//            startActivity(new Intent(getActivity(), SetPasswordActivity.class));
//            finishSelf();
        } else {

            startActivity(new Intent(getActivity(), MainActivity.class));
            finishSelf();
        }
    }
}
