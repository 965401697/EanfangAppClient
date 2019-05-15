package net.eanfang.client.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.base.network.config.HttpConfig;
import com.eanfang.util.GuideUtil;
import com.eanfang.util.StringUtils;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.GuideActivity;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.util.PrefUtils;


/**
 * Created by MrHou
 *
 * @on 2017/11/9  10:11
 * @email houzhongzhou@yeah.net
 * @desc 引导页
 */

public class SplashActivity extends BaseClientActivity implements GuideUtil.OnCallback {

    private static final String TAG = SplashActivity.class.getSimpleName();
    int[] drawables_client = {R.mipmap.ic_client_splash_one, R.mipmap.ic_client_splash_two, R.mipmap.ic_client_splash_three,R.mipmap.ic_client_splash_end};
    // 是第一次
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 解决 安装后直接打开 按home键 从新开启闪屏页
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            //结束你的activity
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);
        //bugly初始化
        SDKManager.getBugly().init(this, BuildConfig.BUGLY_CLIENT, BuildConfig.DEBUG);
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(SplashActivity.this);
//        strategy.setAppChannel(ChannelUtil.getChannelName(SplashActivity.this));
//        //App的版本
//        strategy.setAppVersion(ApkUtils.getAppVersionName(SplashActivity.this));
//        strategy.setAppPackageName(BuildConfig.APPLICATION_ID);
//        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY_CLIENT, false, strategy);
        init();
    }

    private void init() {
        isFirst = PrefUtils.getVBoolean(this, PrefUtils.SHOWGUIDE);
        EanfangHttp.setClient();
        //是第一次
        if (isFirst) {
            firstUse();
        } else {
            //不是第一次
            LoginBean user = EanfangApplication.getApplication().getUser();
            //token失效
            if (user == null || StringUtils.isEmpty(user.getToken())) {
                goLogin();
            } else {
                EanfangHttp.setToken(user.getToken());
                HttpConfig.get().setToken(EanfangApplication.get().getUser().getToken());
                if (isConnected()) {
                    loginByToken();
                } else {
                    goMain();
                }
            }
        }
    }

    private void goMain() {
        startActivity(new Intent(this, MainActivity.class));
        finishSelf();
    }

    //加载引导页
    void firstUse() {
        if (isFirst) {
            new GuideUtil().init(this, findViewById(R.id.layout), drawables_client, this);
            try {
                PrefUtils.setBoolean(getApplicationContext(), PrefUtils.SHOWGUIDE, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * token 登陆 验证
     */
    public void loginByToken() {
        EanfangHttp.get(UserApi.GET_USER_INFO)
                .execute(new EanfangCallback<LoginBean>(this, false, LoginBean.class) {
                    @Override
                    public void onSuccess(LoginBean bean) {
                        if (bean != null && !StringUtils.isEmpty(bean.getToken())) {
                            EanfangApplication.get().set(LoginBean.class.getName(), bean);
                            goMain();
                        } else {
                            goLogin();
                        }
                    }

                    @Override
                    public void onFail(Integer code, String message, JSONObject jsonObject) {
//                        super.onFail(code, message, jsonObject);
                        goLogin();
//                        if (code == 50014) {
//                            showToast("token已失效,请重新登录");
//                        }
                    }
                });

    }

    @Override
    public void goLogin() {
        isFirst = true;
        startActivityForResult(new Intent(this, com.eanfang.sys.activity.LoginActivity.class), com.eanfang.sys.activity.LoginActivity.LOGIN_BACK_CODE);
//        finishSelf();
    }

//    @Subscribe
//    public void onEvent(Integer integer) {
//        isFirst = true;
//        ToastUtil.get().showToast(this, "登录失效，请重新登录！");
//        CleanMessageUtil.clearAllCache(EanfangApplication.get());
//        SharePreferenceUtil.get().clear();
//        startActivity(new Intent(this, LoginActivity.class));
//        finishSelf();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == com.eanfang.sys.activity.LoginActivity.LOGIN_BACK_CODE) {
            if (PrefUtils.getVBoolean(this, PrefUtils.GUIDE)) {
                startActivity(new Intent(this, GuideActivity.class));
                finishSelf();
            } else {
                startActivity(new Intent(this, MainActivity.class));
                finishSelf();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}