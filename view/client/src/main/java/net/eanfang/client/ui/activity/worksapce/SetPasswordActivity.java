package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2018/12/3
 * @description 设置初次密码
 */

public class SetPasswordActivity extends BaseActivity {

    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_comfirm_password)
    EditText etComfirmPassword;
    @BindView(R.id.rl_confirm)
    RelativeLayout rlConfirm;
    @BindView(R.id.iv_show)
    ImageView ivShow;
    @BindView(R.id.iv_confirm_show)
    ImageView ivConfirmShow;

    //新密码
    private String mPassword = "";
    private boolean isHidden = true;
    // 确认密码
    private String mConfirmPassword = "";
    private boolean isConfirmHidden = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_set_password);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("设置密码");

        rlConfirm.setOnClickListener(new MultiClickListener(this, this::isCheckInfo, this::doSubmit));
    }


    private boolean isCheckInfo() {
        mPassword = etPassword.getText().toString().trim();
        mConfirmPassword = etComfirmPassword.getText().toString().trim();
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

    public void doSubmit() {
        EanfangHttp.post(UserApi.ADD_PASSWORD)
                .params("newkey", mConfirmPassword)
                .execute(new EanfangCallback<JSONObject>(SetPasswordActivity.this, true, JSONObject.class, (bean) -> {
                    showToast("修改成功");
                    finishSelf();
                }));

    }

    @OnClick({R.id.iv_show, R.id.iv_confirm_show, R.id.iv_left, R.id.tv_jumpt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_show:
                if (isHidden) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivShow.setImageResource(R.mipmap.ic_password_look);
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivShow.setImageResource(R.mipmap.ic_password_hide);
                }
                isHidden = !isHidden;
                etPassword.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etPassword.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.iv_confirm_show:
                if (isConfirmHidden) {
                    etComfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivConfirmShow.setImageResource(R.mipmap.ic_password_look);
                } else {
                    etComfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivConfirmShow.setImageResource(R.mipmap.ic_password_hide);
                }
                isConfirmHidden = !isConfirmHidden;
                etComfirmPassword.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence_confirm = etComfirmPassword.getText();
                if (charSequence_confirm instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence_confirm;
                    Selection.setSelection(spanText, charSequence_confirm.length());
                }
                break;
            case R.id.tv_jumpt:
                finishSelf();
                break;
            default:
                break;
        }
    }
}
