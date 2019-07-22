package net.eanfang.client.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.cache.CacheMod;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.sys.activity.LoginActivity;
import com.eanfang.util.GuideUtil;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.worksapce.GuideActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

/**
 * @author jornl
 * @date 2019-07-09
 */
public class SplashActivity extends BaseClientActivity implements GuideUtil.OnCallback {
    public static final String SHOWGUID = "showguid";
    public static final String GUID = "guid";
    int[] drawables_client = {R.mipmap.ic_client_splash_one, R.mipmap.ic_client_splash_two, R.mipmap.ic_client_splash_three, R.mipmap.ic_client_splash_end};

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

    private void init() {
        if (BaseApplication.get().getUser() == null && CacheKit.get().getBool(SHOWGUID, true)) {
            firstUse();
            return;
        }
        //没有登录信息
        if (ClientApplication.get().getLoginBean() == null) {
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
        startActivity(new Intent(this, MainActivity.class));
        finishSelf();
    }

    //加载引导页
    void firstUse() {
        new GuideUtil().init(this, findViewById(R.id.layout), drawables_client, this);
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
                        if (bean != null && !StringUtils.isEmpty(bean.getToken())) {
                            CacheKit.get().put(LoginBean.class.getName(), bean, CacheMod.All);
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
                finishSelf();
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