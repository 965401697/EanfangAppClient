package net.eanfang.client.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;

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
import com.eanfang.util.ApkUtils;
import com.eanfang.util.GuideUtil;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.SharePreferenceUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
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
    int[] drawables_client = {R.mipmap.client0, R.mipmap.client1, R.mipmap.client2, R.mipmap.client3};
    // 是第一次
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();

        //getBaseData();
        //  getConst();
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
                loginByToken();
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
            new GuideUtil().init(this, (ViewGroup) findViewById(R.id.layout), drawables_client, this);
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
//                        super.onFail(code, message, jsonObject);
                        goLogin();
//                        if (code == 50014) {
//                            showToast("token已失效,请重新登录");
//                        }
                    }
                });

    }


    /**
     * 请求基础数据
     */
    private void getBaseData() {
        String url;
        String md5 = V.v(() -> Config.get().getBaseDataBean().getMD5());
        if (StringUtils.isEmpty(md5)) {
            url = NewApiService.GET_BASE_DATA_CACHE + "0";
        } else {
            url = NewApiService.GET_BASE_DATA_CACHE + md5;
        }
        EanfangHttp.get(url)
                .tag(this)
                .execute(new EanfangCallback<String>(this, false, String.class, (str) -> {
                    if (!str.contains(Constant.NO_UPDATE)) {
                        //CacheUtil.put(this, getPackageName(), BaseDataBean.class.getName(), str);
                        BaseDataBean newDate = JSONObject.parseObject(str, BaseDataBean.class);
                        EanfangApplication.get().set(BaseDataBean.class.getName(), JSONObject.toJSONString(newDate, FastjsonConfig.config));
                    }
                }));

    }

    /**
     * 请求静态常量
     */
    private void getConst() {
        String url;
        String md5 = V.v(() -> Config.get().getConstBean().getMD5());
        if (StringUtils.isEmpty(md5)) {
            url = NewApiService.GET_CONST_CACHE + "0";
        } else {
            url = NewApiService.GET_CONST_CACHE + md5;
        }
        EanfangHttp.get(url)
                .tag(this)
                .execute(new EanfangCallback<String>(this, false, String.class, (str) -> {
                    if (!str.contains(Constant.NO_UPDATE)) {
                        //CacheUtil.put(this, getPackageName(), ConstAllBean.class.getName(), str);
                        ConstAllBean newDate = JSONObject.parseObject(str, ConstAllBean.class);
                        EanfangApplication.get().set(ConstAllBean.class.getName(), JSONObject.toJSONString(newDate, FastjsonConfig.config));
                    }

                }));
    }


    @Override
    public void goLogin() {
        isFirst = true;
        startActivity(new Intent(this, LoginActivity.class));
        finishSelf();
    }


}