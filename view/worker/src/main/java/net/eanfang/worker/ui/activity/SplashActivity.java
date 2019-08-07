package net.eanfang.worker.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseActivity;
import com.eanfang.base.kit.aop.annotation.BugLog;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.cache.CacheMod;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.sys.activity.LoginActivity;
import com.eanfang.util.GuideUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.GuideActivity;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;


/**
 * @author jornl
 * @date 2019-07-09
 */
public class SplashActivity extends BaseActivity implements GuideUtil.OnCallback {
    public static final String SHOWGUID = "showguid";
    public static final String GUID = "guid";
    int[] drawables_worker = {R.mipmap.ic_work_splash_one, R.mipmap.ic_splash_two, R.mipmap.ic_work_splash_three, R.mipmap.ic_work_splash_end};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);
        // 解决 安装后直接打开 按home键 从新开启闪屏页
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            //结束你的activity
            finish();
            return;
        }
        init();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    private void init() {
        if (WorkerApplication.get().getLoginBean() == null && CacheKit.get().getBool(SHOWGUID, true)) {
            firstUse();
            return;
        }

        //没有登录信息
        if (WorkerApplication.get().getLoginBean() == null) {
            goLogin();
            return;
        }

        //有网 token登录
        if (isConnected()) {
            loginByToken();
        } else {
            //没网 进首页
            goMain();
        }
    }

    private void goMain() {
        startActivity(MainActivity.class);
        finish();
    }


    //加载引导页
    void firstUse() {
        new GuideUtil().init(this, findViewById(R.id.layout), drawables_worker, this);
        CacheKit.get().put(SHOWGUID, false, CacheMod.All);
    }

    /**
     * token 登陆 验证
     */
    public void loginByToken() {

        EanfangHttp.get(UserApi.GET_USER_INFO)
                .execute(new EanfangCallback<LoginBean>(this, false, LoginBean.class) {
                    @Override
                    public void onSuccess(LoginBean bean) {
                        if (bean != null && !StrUtil.isEmpty(bean.getToken())) {
                            ThreadUtil.execAsync(() -> {
                                CacheKit.get().put(LoginBean.class.getName(), bean, CacheMod.All);
                            });
                            goMain();
                        } else {
                            goLogin();
                        }
                    }

                    @Override
                    public void onFail(Integer code, String message, JSONObject jsonObject) {
                        goLogin();
                    }
                });
    }

    @Override
    public void goLogin() {
        startActivityForResult(new Intent(this, LoginActivity.class), LoginActivity.LOGIN_BACK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginActivity.LOGIN_BACK_CODE) {
            //加载引导
            if (CacheKit.get().getBool(GUID, true)) {
                startActivity(new Intent(this, GuideActivity.class));
                CacheKit.get().put(GUID, false, CacheMod.All);
                finish();
            } else {
                goMain();
            }
        }
    }

    /**
     * 判断网络是否连接
     *
     * @return true有网  false无网
     */
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}