package com.eanfang.sys.viewmodel;

import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.eanfang.R;
import com.eanfang.databinding.FragmentPasswordEanfangBinding;
import com.eanfang.databinding.FragmentVerifyEanfangBinding;
import com.eanfang.kit.rx.RxDialog;
import com.eanfang.model.bean.LoginBean;
import com.eanfang.model.vo.LoginVo;
import com.eanfang.rds.base.BaseViewModel;
import com.eanfang.rds.sys.ds.LoginDs;
import com.eanfang.rds.sys.repo.LoginRepo;

import java.util.concurrent.TimeUnit;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;
import lombok.Setter;

public class LoginViewModel extends BaseViewModel {
    @Getter
    private MutableLiveData<LoginBean> loginLiveData;
    private LoginRepo loginRepo;
    private LoginVo loginVo;
    @Setter
    private FragmentVerifyEanfangBinding verifyBinding;
    @Setter
    private FragmentPasswordEanfangBinding passwordBinding;

    public LoginViewModel() {
        loginLiveData = new MutableLiveData<>();
        loginRepo = new LoginRepo(new LoginDs(this));
        loginVo = new LoginVo();
    }

    /**
     * 密码登录
     *
     * @param userName userName 手机号
     * @param password password 密码
     */
    private void loginPassword(String userName, String password) {
        loginRepo.loginPassword(userName, password).observe(lifecycleOwner, bean -> loginLiveData.setValue(bean));
    }

    /**
     * 验证码登录
     *
     * @param userName userName 手机号
     * @param code     code 验证码
     */
    private void loginVerify(String userName, String code) {
        loginRepo.loginVerify(userName, code).observe(lifecycleOwner, bean -> loginLiveData.setValue(bean));
    }

    /**
     * 获取验证码
     *
     * @param userName 手机号
     */
    private void verifyCode(String userName) {
        loginRepo.verifyCode(userName).observe(lifecycleOwner, this::showToast);
    }

    /**
     * 登录按钮
     *
     * @param type 0验证码登录，1密码登录
     */
    public void loginClick(int type) {
        if (StrUtil.isEmpty(loginVo.getUsername().get())) {
            showToast("手机号不能为空");
            return;
        }
        //电话号码是否符合格式
        if (!Validator.isMobile(loginVo.getUsername().get())) {
            showToast("请输入正确手机号");
            return;
        }

        if (type == 0 && StrUtil.isEmpty(loginVo.getVerifycode().get())) {
            showToast("验证码不能为空");
            return;
        }
        if (type == 1 && StrUtil.isEmpty(loginVo.getPassword().get())) {
            showToast("密码不能为空");
            return;
        }
        if (!loginVo.getLegalCk().get()) {
            showToast("同意易安防会员章程和协议后才可以登陆使用");
            return;
        }
        if (type == 0) {
            loginVerify(loginVo.getUsername().get(), loginVo.getVerifycode().get());
        } else {
            loginPassword(loginVo.getUsername().get(), loginVo.getPassword().get());
        }
    }

    /**
     * 获取验证码
     */
    public void verifyClick() {
        if (StrUtil.isEmpty(loginVo.getUsername().get())) {
            showToast("手机号不能为空");
            return;
        }
        //电话号码是否符合格式
        if (!Validator.isMobile(loginVo.getUsername().get())) {
            showToast("请输入正确手机号");
            return;
        }
        verifyCode(loginVo.getUsername().get());
        verifyBinding.tvYanzheng.setEnabled(false);

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(60)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(count -> 60 - count)
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        verifyBinding.tvYanzheng.setText(aLong + "秒");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        verifyBinding.tvYanzheng.setText("获取验证码");
                        verifyBinding.tvYanzheng.setEnabled(true);
                    }
                });
//        timer.start();
    }


    //验证码倒计时
//    @Getter
//    protected CountDownTimer timer = new CountDownTimer(60000, 1000) {
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            verifyBinding.tvYanzheng.setText(millisUntilFinished / 1000 + "秒");
//        }
//
//        @Override
//        public void onFinish() {
//            verifyBinding.tvYanzheng.setEnabled(true);
//            verifyBinding.tvYanzheng.setText("获取验证码");
//        }
//    };

    /**
     * 阅读协议
     *
     * @param type 0验证码登录，1密码登录
     */
    public void readClick(int type) {
        RxDialog dialog;
        if (type == 0) {
            dialog = new RxDialog(verifyBinding.getRoot().getContext());
        } else {
            dialog = new RxDialog(passwordBinding.getRoot().getContext());
        }
        dialog.setTitle("法律声明")
                .setMessage(loginVo.getLegalText().get())
                .setPositiveText("我同意")
                .setNegativeText("不同意")
                .dialogToObservable()
                .subscribe((code) -> {
                    if (code.equals(RxDialog.POSITIVE)) {
                        loginVo.getLegalCk().set(true);
                    } else if (code.equals(RxDialog.NEGATIVE)) {
                        loginVo.getLegalCk().set(false);
                    }
                });
    }

    /**
     * 显示密码
     */
    public void showClick() {
        if (loginVo.getShowPwd().get()) {
            passwordBinding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            passwordBinding.ivShow.setImageResource(R.mipmap.ic_password_look);
        } else {
            passwordBinding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            passwordBinding.ivShow.setImageResource(R.mipmap.ic_password_hide);
        }
        loginVo.getShowPwd().set(!loginVo.getShowPwd().get());
        passwordBinding.etPassword.postInvalidate();
        //切换后将EditText光标置于末尾
        Spannable charSequence = passwordBinding.etPassword.getText();
        if (charSequence != null) {
            Selection.setSelection(charSequence, charSequence.length());
        }
    }

    public void LegalText(FragmentActivity mActivity) {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext(IoUtil.read(mActivity.getResources().openRawResource(R.raw.legal), "UTF-8"));
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((txt) -> {
                    loginVo.getLegalText().set(txt);
                    verifyBinding.setLoginVo(loginVo);
                    passwordBinding.setLoginVo(loginVo);
                });
    }
}
