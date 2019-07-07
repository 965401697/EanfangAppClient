package com.eanfang.sys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.viewpager.widget.ViewPager;

import com.eanfang.R;
import com.eanfang.base.BaseActivity;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.network.config.HttpConfig;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.databinding.ActivityLoginEanfangBinding;
import com.eanfang.http.EanfangHttp;
import com.eanfang.sys.fragment.PasswordFragment;
import com.eanfang.sys.fragment.VerifyFragment;
import com.eanfang.sys.viewmodel.LoginViewModel;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class LoginActivity extends BaseActivity {

    public static final int LOGIN_BACK_CODE = 1;

    private final String[] mTitles = {
            "短信快捷登录", "账号密码登录"
    };

    private LoginViewModel loginViewModel;

    private ActivityLoginEanfangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_eanfang);
        binding.setApp(getApp());
        super.onCreate(savedInstanceState);

        setTitle("登录");
    }

    @Override
    protected void initView() {
        setLeftBack(false);
        ViewPager vp = binding.vpService;
        MyPagerAdapter mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mTitles);
        mAdapter.getFragments().add(VerifyFragment.getInstance(loginViewModel));
        mAdapter.getFragments().add(PasswordFragment.getInstance(loginViewModel));
        vp.setAdapter(mAdapter);
        vp.setCurrentItem(0);
        binding.slidingtablayout.setViewPager(vp, mTitles, this, mAdapter.getFragments());
    }

    @Override
    protected ViewModel initViewModel() {
        loginViewModel = LViewModelProviders.of(this, LoginViewModel.class);
        loginViewModel.getLoginLiveData().observe(this, this::handlerLogin);
        return loginViewModel;
    }


    @Override
    protected void initStyle() {
        super.initStyle();
        if (isClient()) {
            binding.slidingtablayout.setTextSelectColor(ContextCompat.getColor(this, R.color.color_main_client));
            binding.slidingtablayout.setIndicatorColor(ContextCompat.getColor(this, R.color.color_main_client));
        } else {
            binding.slidingtablayout.setTextSelectColor(ContextCompat.getColor(this, R.color.color_main_worker));
            binding.slidingtablayout.setIndicatorColor(ContextCompat.getColor(this, R.color.color_main_worker));
        }
    }

    //登录成功的回调
    private void handlerLogin(LoginBean loginBean) {
        BaseApplication.get().set(LoginBean.class.getName(), loginBean);
        //老版的 兼容考虑
        EanfangHttp.setToken(loginBean.getToken());
        HttpConfig.get().setToken(loginBean.getToken());
        setResult(LOGIN_BACK_CODE, null);
        finishWithResultOk();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //处理返回按钮被按下
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //退出登录
            Intent intent = new Intent(LoginActivity.this.getPackageName() + ".ExitListenerReceiver");
            LoginActivity.this.sendBroadcast(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
