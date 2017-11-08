/*
 * Copyright (c) 2016. Huangrui All rights reserved.
 */

package net.eanfang.client.ui.base;

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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.base.IBase;
import com.eanfang.util.DialogUtil;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.application.CustomeApplication;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.ui.model.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * BaseAppCompatFragmentActivity
 *
 * @author hr
 *         Created at 2016/1/1 11:33
 * @desc activity基类
 */

public abstract class BaseActivity extends AppCompatActivity implements
        IBase, ActivityCompat.OnRequestPermissionsResultCallback {

    public Dialog loadingDialog;
    private ImageView iv_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Config.get().onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        CustomeApplication.get().push(this);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        EventBus.getDefault().register(this);
        loadingDialog = DialogUtil.createLoadingDialog(this);
        RegListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }

    public void setLeftBack() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(v -> finish());
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

    public void supprotToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onNavigationOnClicked());
    }


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

    public void setRightImageOnClickListener(View.OnClickListener listener) {
        findViewById(R.id.iv_right).setOnClickListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }

/*    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        J_Config.get().onSaveInstanceState();
        super.onSaveInstanceState(outState, outPersistentState);
    }*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        CustomeApplication.get().pull(this);
    }


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

    public User user() {
        Object obj = EanfangApplication.getApplication().getUser();
        if (obj instanceof User)
            return (User) obj;
        return null;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageReceived(BaseEvent baseEvent) {

    }

    /**
     * 退出程序广播
     */
    public void RegListener() {
        ExitListenerReceiver exitre = new ExitListenerReceiver();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction(this.getPackageName() + "."
                + "ExitListenerReceiver");
        this.registerReceiver(exitre, intentfilter);
    }

    class ExitListenerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent i) {
            ((Activity) context).finish();
        }
    }

    @Override
    public void onBackPressed() {
        onNavigationOnClicked();
    }

    public void startAnimActivity(Intent intent) {
        startActivity(intent);
    }





}
