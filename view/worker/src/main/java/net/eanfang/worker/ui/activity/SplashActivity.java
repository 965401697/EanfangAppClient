package net.eanfang.worker.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.cache.CacheMod;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.LoginDs;
import com.eanfang.biz.rds.sys.repo.LoginRepo;
import com.eanfang.sys.activity.LoginActivity;
import com.eanfang.util.GuideUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.GuideActivity;

import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.StrUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * @author jornl
 * @date 2019-07-09
 */
public class SplashActivity extends BaseActivity {
    public static final String SHOWGUID = "showguid";
    public static final String GUID = "guid";
    int[] drawables_worker = {R.mipmap.ic_work_splash_one, R.mipmap.ic_splash_two, R.mipmap.ic_work_splash_three, R.mipmap.ic_work_splash_end};
    private LoginRepo loginRepo;
    private TextView tv;
    //是否跳过
    private boolean isSkip = false;
    //是否执行了token登录
    private boolean isLogin = false;

    /**
     * 0没有执行完
     * 1跳登录
     * 2跳主页
     */
    private int jumpType = 0;

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
        loginRepo = new LoginRepo(new LoginDs(new BaseViewModel()));
        tv = findViewById(R.id.tv_countDown);
        tv.setOnClickListener((v) -> {
//            isSkip = true;
//            tv.setText("加载中...");
//            loginByToken();
            if (jumpType == 1){
                goLogin();
            }else if (jumpType == 2){
                goMain();
            }else{
                tv.setText("加载中...");
            }
        });
        init();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    private void beginCountDown() {
//        Observable.interval(0, 1, TimeUnit.SECONDS)
//                .take(5)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(count -> 5 - count)
//                .subscribe(new Observer<Long>() {
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        if (!isSkip) {
//                            tv.setText(StrUtil.format("跳过({})", aLong));
////                            if (aLong == 3 && !isLogin) {
////                                loginByToken();
////                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        if (jumpType == 1){
//                            goLogin();
//                        }else if (jumpType == 2){
//                            goMain();
//                        }else{
//                            tv.setText("加载中...");
//                        }
//                    }
//                });
//        loginByToken();
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

        if (!isConnected()) {
            goMain();
            return;
        }
//        beginCountDown();
        loginByToken();
    }

    private void goMain() {
        startActivity(MainActivity.class);
        finish();
    }


    //加载引导页
    void firstUse() {
        new GuideUtil().init(this, findViewById(R.id.layout), drawables_worker, this::goLogin);
        CacheKit.get().put(SHOWGUID, false, CacheMod.All);
    }

    /**
     * token 登陆 验证
     */
    public synchronized void loginByToken() {
        if (isLogin) {
            return;
        }
        isLogin = true;
        loginRepo.loginToken().observe(this, (bean) -> {
            if (bean != null && !StrUtil.isEmpty(bean.getToken())) {
                CacheKit.get().put(LoginBean.class.getName(), bean, CacheMod.All);
                goMain();
                jumpType = 2;
            } else {
                goLogin();
                jumpType = 1;
            }
        });
        loginRepo.onError("loginByToken").observe(this, (bean) -> {
            goLogin();
            jumpType = 1;
        });
    }

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