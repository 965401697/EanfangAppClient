package com.eanfang.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.eanfang.R;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.loading.LoadKit;
import com.eanfang.base.network.config.HttpConfig;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.util.ToastUtil;
import com.jaeger.library.StatusBarUtil;
import com.luck.picture.lib.config.PictureConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by jornl on 2018/1/19.
 */
@Deprecated
public class BaseActivity extends AppCompatActivity implements
        IBase, ActivityCompat.OnRequestPermissionsResultCallback {

    public static Dialog loadingDialog;
    private ImageView iv_left;
    private ExitListenerReceiver exitre;

    private boolean hasTransaction = false;
    private boolean containsFirst = false;
    /**
     * 似乎只是在聊天的时候 销毁窗口用的 考虑写成接口形式
     */
    @Deprecated
    public final static ArrayList<Activity> transactionActivities = new ArrayList<>();

    protected int getApp() {
        return HttpConfig.get().getApp();
    }

    protected boolean isClient() {
        return getApp() == 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Config.get().onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

//        initState();

//        setStatusBar();
        BaseApplication.get().addActivity(this);
//        CustomeApplication.get().push(this);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //确保之前未订阅过，再调用订阅语句，以免报错
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        loadingDialog = LoadKit.dialog(this);
        RegListener();

        // 如果已开启事务将当前activity加入事务队列
        if (hasTransaction) {
            transactionActivities.add(this);
        }

    }

    @Deprecated
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_main_header_bg));
    }


    @Override
    protected void onStart() {
        super.onStart();
        initStyle();
    }

    /**
     * 初始化页面风格样式方法
     */
    protected void initStyle() {
        if (findViewById(R.id.titles_bar) == null) {
            return;
        }
        if (isClient()) {
            findViewById(R.id.titles_bar).setBackgroundColor(ContextCompat.getColor(this, R.color.color_main_header_bg));
        } else {
            findViewById(R.id.titles_bar).setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryW));
        }
    }

    public void setLeftBack() {
        iv_left = findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(v -> finish());
    }

    /**
     * 返回监听
     *
     * @param listener
     */
    public void setLeftBack(View.OnClickListener listener) {
        iv_left = findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(listener);
    }

    /**
     * 返回键被按下执行的操作
     */
    protected void onNavigationOnClicked() {
        finishSelf();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 已过时 页面header 用 header_eanfang 替换
     */
    @Deprecated
    public void supprotToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onNavigationOnClicked());
    }

    public void setRightImageVisible() {
        findViewById(R.id.iv_right).setVisibility(View.VISIBLE);
    }

    public void setRightImageGone() {
        findViewById(R.id.iv_right).setVisibility(View.GONE);
    }

    @Override
    public void setTitle(int id) {
        ((TextView) findViewById(R.id.tv_title)).setText(id);
    }

    public void setTitle(String id) {
        ((TextView) findViewById(R.id.tv_title)).setText(id);
    }

    public void setRightImageResId(int resId) {
        ((ImageView) findViewById(R.id.iv_right)).setImageResource(resId);
    }

    public void setRightTitle(int id) {
        ((TextView) findViewById(R.id.tv_right)).setText(id);
    }

    public void setRightTitle(String id) {
        ((TextView) findViewById(R.id.tv_right)).setText(id);
    }

    public void setRightTitleOnClickListener(View.OnClickListener listener) {
        findViewById(R.id.tv_right).setOnClickListener(listener);
    }

    public void setRightGone() {
        findViewById(R.id.tv_right).setVisibility(View.GONE);
    }

    public void setRightVisible() {
        findViewById(R.id.tv_right).setVisibility(View.VISIBLE);
    }

    public void setLeftGone() {
        findViewById(R.id.iv_left).setVisibility(View.GONE);
    }

    public void setLeftVisible() {
        findViewById(R.id.iv_left).setVisibility(View.VISIBLE);
    }

    @Deprecated
    public void setRightImageOnClickListener(View.OnClickListener listener) {
        findViewById(R.id.iv_right).setOnClickListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    /**
     * @param containThis 完成时是否关闭当前Activity true为关闭
     */

    public void startTransaction(boolean containThis) {
        hasTransaction = true;
        containsFirst = containThis;
        if (containThis && !transactionActivities.contains(this)) {
            transactionActivities.add(this);
        }
    }

    /**
     * @param finishThis 结束事务时是否关闭当前activity
     */

    public void endTransaction(boolean finishThis) {
        if (finishThis) {
            finish();
        }
        for (Activity activity : transactionActivities) {
            if (activity != this) {
                activity.finish();
            }
        }
        containsFirst = false;
        hasTransaction = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消订阅
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
//        CustomeApplication.get().pull(this);
        BaseApplication.get().closeActivity(this);
        this.unregisterReceiver(exitre);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void finishSelf() {
        finish();
    }

    @Override
    public void showToast(int res) {
        ToastUtil.get().showToast(this, res);
    }

    @Override
    public void showToast(String message) {
        ToastUtil.get().showToast(this, message);

    }

    @Deprecated
    public LoginBean user() {
        Object obj = BaseApplication.get().getUser();
        if (obj instanceof LoginBean) {
            return (LoginBean) obj;
        }
        return null;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageReceived(BaseEvent baseEvent) {

    }

    /**
     * 退出程序广播
     */
    public void RegListener() {
        if (exitre == null) {
            exitre = new ExitListenerReceiver();
        }
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction(this.getPackageName() + "."
                + "ExitListenerReceiver");
        this.registerReceiver(exitre, intentfilter);
    }

    @Override
    public void onBackPressed() {
        onNavigationOnClicked();
    }

    @Deprecated
    public void startAnimActivity(Intent intent) {
        startActivity(intent);
    }

    @Deprecated
    class ExitListenerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent i) {
            ((Activity) context).finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pictureSelect(requestCode, resultCode, data);
    }

    /**
     * 图片选择
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void pictureSelect(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                SDKManager.getPicture().create(this).onActivityResult(requestCode, resultCode, data);
            }
        }
    }

}
