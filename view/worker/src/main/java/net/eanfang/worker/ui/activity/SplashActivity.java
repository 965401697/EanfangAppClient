package net.eanfang.worker.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.LoginBean;
import com.eanfang.sys.activity.LoginActivity;
import com.eanfang.util.ApkUtils;
import com.eanfang.util.ChannelUtil;
import com.eanfang.util.CleanMessageUtil;
import com.eanfang.util.GuideUtil;
import com.eanfang.util.SharePreferenceUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.tencent.bugly.crashreport.CrashReport;

import net.eanfang.worker.BuildConfig;
import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.GuideActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.util.PrefUtils;

import org.greenrobot.eventbus.Subscribe;


/**
 * Created by MrHou
 *
 * @on 2017/11/9  10:11
 * @email houzhongzhou@yeah.net
 * @desc 引导页
 */

public class SplashActivity extends BaseWorkerActivity implements GuideUtil.OnCallback {
    private static final String TAG = SplashActivity.class.getSimpleName();
    int[] drawables_worker = {R.mipmap.ic_work_splash_one, R.mipmap.ic_work_splash_two, R.mipmap.ic_work_splash_three};

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
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(SplashActivity.this);
        strategy.setAppChannel(ChannelUtil.getChannelName(SplashActivity.this));
        //App的版本
        strategy.setAppVersion(ApkUtils.getAppVersionName(SplashActivity.this));
        strategy.setAppPackageName(BuildConfig.APPLICATION_ID);
        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY_WORKER, false, strategy);
        init();
    }

    private void init() {
        isFirst = PrefUtils.getVBoolean(this, PrefUtils.SHOWGUIDE);
        EanfangHttp.setWorker();
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
            new GuideUtil().init(this, findViewById(R.id.layout), drawables_worker, this);
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
                            EanfangApplication.get().set(LoginBean.class.getName(), JSONObject.toJSONString(bean, FastjsonConfig.config));
                            goMain();
                        } else {
                            goLogin();
                        }
                    }

                    @Override
                    public void onFail(Integer code, String message, JSONObject jsonObject) {
                        goLogin();
//
                    }
                });

    }


    @Override
    public void goLogin() {
        isFirst = true;
        startActivityForResult(new Intent(this, com.eanfang.sys.activity.LoginActivity.class), com.eanfang.sys.activity.LoginActivity.LOGIN_BACK_CODE);
//        startActivity(new Intent(this, LoginActivity.class));
//        finishSelf();
    }

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

    @Subscribe
    public void onEvent(Integer integer) {
        isFirst = true;
        ToastUtil.get().showToast(this, "登录失效，请重新登录！");
        CleanMessageUtil.clearAllCache(EanfangApplication.get());
        SharePreferenceUtil.get().clear();
        startActivity(new Intent(this, LoginActivity.class));
        finishSelf();
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