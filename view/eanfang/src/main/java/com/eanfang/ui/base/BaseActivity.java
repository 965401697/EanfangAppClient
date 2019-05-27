package com.eanfang.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.eanfang.R;
import com.eanfang.base.BaseApplication;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.util.DialogUtil;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.ToastUtil;
import com.jaeger.library.StatusBarUtil;

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

    public Dialog loadingDialog;
    private PermissionUtils.PermissionsCallBack permissionsCallBack;
    private ImageView iv_left;
    private ExitListenerReceiver exitre;

    private boolean hasTransaction = false;
    private boolean containsFirst = false;
    /**
     * 似乎只是在聊天的时候 销毁窗口用的 考虑写成接口形式
     */
    @Deprecated
    public final static ArrayList<Activity> transactionActivities = new ArrayList<>();


    //Android6.0申请权限的回调方法
    @Deprecated
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case PermissionUtils.PermissionsCallBack.callBackCode:
                if (grantResults == null || grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 没有获取到权限，做特殊处理
                    runOnUiThread(() -> {
                        Toast.makeText(getApplicationContext(), "获取权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                    });
                } else {

                    try {
                        // 获取到权限，作相应处理
                        permissionsCallBack.callBack();
                    } catch (NullPointerException e) {

                    }

                }
                break;
            default:
                break;
        }
    }

    @Deprecated
    public void setPermissionsCallBack(PermissionUtils.PermissionsCallBack permissionsCallBack) {
        this.permissionsCallBack = permissionsCallBack;
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
        loadingDialog = DialogUtil.createLoadingDialog(this);
        RegListener();

        // 如果已开启事务将当前activity加入事务队列
        if (hasTransaction) {
            transactionActivities.add(this);
        }

    }

    @Deprecated
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    /**
     * 沉浸式状态栏
     */
    @Deprecated
    private void initState() {
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明导航栏
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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

}
