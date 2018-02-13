package net.eanfang.worker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.LoginBean;
import com.eanfang.util.GuideUtil;
import com.eanfang.util.SharePreferenceUtil;
import com.eanfang.util.StringUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.im.model.Model;
import com.im.model.bean.UserInfo;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.util.PrefUtils;


/**
 * Created by MrHou
 *
 * @on 2017/11/9  10:11
 * @email houzhongzhou@yeah.net
 * @desc 引导页
 */

public class SplashActivity extends BaseWorkerActivity implements GuideUtil.OnCallback {
    private static final String TAG = SplashActivity.class.getSimpleName();
    int[] drawables_worker = {R.mipmap.worker0, R.mipmap.worker1, R.mipmap.worker2, R.mipmap.worker3};
    private boolean isFirst;
    //是否加载完成
    private boolean isLoadReady = false;
    //是否登录成功
    private boolean isLoginReady = false;
    //是否校验成功
    private boolean isCheckReady = false;

    private boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        isFirst = SharePreferenceUtil.get().getBoolean("First", false);
        isFirst = PrefUtils.getVBoolean(this, PrefUtils.SHOWGUIDE);
        //3s等待，3s结束isLoadReady 为true
        new Handler().postDelayed(() -> {
            isLoadReady = true;
            go();
        }, 1000);
        isFirst();
    }


    //跳转
    synchronized void go() {
        if (!isFirst) {
            return;
        }
        //登录成功且加载完成
        if (isLoadReady && isLoginReady) {
            startActivity(new Intent(this, MainActivity.class));
            finishSelf();
        }
        if (isLoadReady && isCheckReady) {
            startActivity(new Intent(this, LoginActivity.class));
            finishSelf();
        }
    }


    //加载引导页
    void isFirst() {
        if (!isFirst) {
            new GuideUtil().init(this, (ViewGroup) findViewById(R.id.layout), drawables_worker, this);
            try {
                PrefUtils.setBoolean(getApplicationContext(), PrefUtils.SHOWGUIDE, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            checkToken();
        }
    }


    /**
     * 检查token是否存在  存在 校验token  不存在 跳转到登录页面
     */
    public void checkToken() {
        LoginBean user = EanfangApplication.getApplication().getUser();
        isValid = (user == null) || (!StringUtils.isValid(user.getToken()));
        if (isValid) {
            isCheckReady = true;
            go();
            return;
        }
        EanfangHttp.getHttp().getCommonHeaders().put("Request-From", "WORKER");
        EanfangHttp.getHttp().getCommonHeaders().put("YAF-Token", user.getToken());
        EanfangHttp.get(UserApi.GET_USER_INFO)
                .execute(new EanfangCallback<LoginBean>(this, false, LoginBean.class, (bean) -> {
                    Object object = bean;
                    if (object instanceof LoginBean) {
                        LoginBean loginBean = (LoginBean) object;
                        EanfangApplication.get().set(LoginBean.class.getName(), JSONObject.toJSONString(loginBean, FastjsonConfig.config));
                        isLoginReady = true;
                        loginEase(bean.getAccount().getMobile());

                        go();
                    } else {
                        isCheckReady = true;
                        loginEase(bean.getAccount().getMobile());
                        go();
                    }
                }));

    }


    @Override
    public void goLogin() {
        isFirst = true;
        startActivity(new Intent(this, LoginActivity.class));
        finishSelf();
    }

    private void loginEase(String phone) {

        //3、登录逻辑处理
        Model.getInstance().getGlobalThreadPool().execute(() -> {
            //去环信服务器登录
            EMClient.getInstance().login(phone, "eanfang", new EMCallBack() {
                //登录成功处理
                @Override
                public void onSuccess() {
                    //对模型层数据处理
                    Model.getInstance().loginSuccess(new UserInfo(phone));
                    //保存用户信息到本地数据库
                    Model.getInstance().getUserAccountDao().addAccount(new UserInfo(phone));
                }

                //登录失败处理
                @Override
                public void onError(int i, String s) {
                    //提示登录失败
                    runOnUiThread(() -> Toast.makeText(SplashActivity.this, "登录失败" + s, Toast.LENGTH_SHORT).show());

                }

                //登录中处理
                @Override
                public void onProgress(int i, String s) {

                }
            });
        });
    }

}