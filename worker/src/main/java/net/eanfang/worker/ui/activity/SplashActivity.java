package net.eanfang.worker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;

import com.eanfang.application.EanfangApplication;
import com.eanfang.util.GuideUtil;
import com.eanfang.util.LogUtils;
import com.eanfang.util.SharePreferenceUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.model.LoginBean;

import net.eanfang.worker.BuildConfig;
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
    int[] drawables_client = {R.mipmap.client0, R.mipmap.client1, R.mipmap.client2, R.mipmap.client3};
    int[] drawables_worker = {R.mipmap.worker0, R.mipmap.worker1, R.mipmap.worker2, R.mipmap.worker3};
    private boolean isFirst;
    private boolean isLoadReady = false;  //是否加载完成
    private boolean isLoginReady = false; //是否登录成功
    private boolean isCheckReady = false;//是否校验成功
    private boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        isFirst = SharePreferenceUtil.get().getBoolean("First", false);
        isFirst = PrefUtils.getVBoolean(this, PrefUtils.SHOWGUIDE);
        //3s等待，3s结束isLoadReady 为true
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isLoadReady = true;
                go();
            }
        }, 3000);
        checkVersion();
        isFirst();
    }


    /**
     * 如果后期有需求  加上更新
     */
    private void checkVersion() {

    }


    //跳转
    synchronized void go() {
        if (!isFirst) {
            return;
        }
        //登录成功且加载完成
//        LogUtils.d(TAG, "isLoadReady = " + isLoadReady + "   isLoginReady = " + isLoginReady + "   isCheckReady = " + isCheckReady);
//        if (isLoadReady && isLoginReady) {
//            startActivity(new Intent(this, MainActivity.class));
//            finishSelf();
//        }
//        if (isLoadReady && isCheckReady) {
        startActivity(new Intent(this, LoginActivity.class));
        finishSelf();
//        }
    }


    //加载引导页
    void isFirst() {
        if (!isFirst) {
            if (BuildConfig.APP_TYPE == 0) {
                new GuideUtil().init(this, (ViewGroup) findViewById(R.id.layout), drawables_worker, this);
            } else {
                new GuideUtil().init(this, (ViewGroup) findViewById(R.id.layout), drawables_client, this);
            }


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
        LogUtils.d(TAG, "checkToken();");
        LoginBean user = EanfangApplication.getApplication().getUser();
        LogUtils.d(TAG, "checkToken();" + user);
        isValid = (user == null) || (!StringUtils.isValid(user.getToken()));
        if (isValid) {
            isCheckReady = true;
            go();
            return;
        }
//        EanfangHttp.get(UserApi.CHECK_TOKEN)
//                .execute(new EanfangCallback<User>(SplashActivity.this, false) {
//                    @Override
//                    public void onSuccess(User bean) {
//                        super.onSuccess(bean);
//                        Object object = bean;
//                        EanfangApplication.get().set(User.class.getName(), object instanceof User);
//
//                        if (object instanceof User) {
//                            LogUtils.d(TAG, "user" + object.toString());
//                            User user = (User) object;
//                            EanfangApplication.get().set(User.class.getName(), user);
//                            isLoginReady = true;
//                            go();
//                        } else {
//                            isCheckReady = true;
//                            go();
//                        }
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                        super.onError(message);
//                        isCheckReady = true;
//                        go();
//                    }
//
//                    @Override
//                    public void onFail(Integer code, String message, JSONObject jsonObject) {
//                        super.onFail(code, message, jsonObject);
//                        isCheckReady = true;
//                        go();
//                    }
//                });

    }


    @Override
    public void goLogin() {
        isFirst = true;
        startActivity(new Intent(this, LoginActivity.class));
        finishSelf();
    }
}