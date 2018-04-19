package net.eanfang.client.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.BaseDataBean;
import com.eanfang.model.ConstAllBean;
import com.eanfang.model.LoginBean;
import com.eanfang.util.ExecuteUtils;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UpdateAppManager;
import com.eanfang.util.Var;
import com.picker.common.util.LogUtils;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.ContactListFragment;
import net.eanfang.client.ui.fragment.ContactsFragment;
import net.eanfang.client.ui.fragment.HomeFragment;
import net.eanfang.client.ui.fragment.MyFragment;
import net.eanfang.client.ui.fragment.WorkspaceFragment;

import butterknife.ButterKnife;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends BaseClientActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    protected FragmentTabHost mTabHost;
    View redPoint;
    private LoginBean user;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initState();
        ButterKnife.bind(this);
        user = EanfangApplication.get().getUser();
        setHeaders();

        initFragment();
        //阻止底部 菜单拦被软键盘顶起
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        initUpdate();
        initXinGe();
        getBaseData();
        getConst();
        PermissionUtils.get(this).getStoragePermission(() -> {
        });
    }

    private void initUpdate() {
        int isUpdate = Var.get("MainActivity.initUpdate").getVar();
        if (isUpdate <= 0) {
            //app更新
            UpdateAppManager.update(this, BuildConfig.TYPE);
        }
        Var.get("MainActivity.initUpdate").setVar(1);
    }

    /**
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明导航栏
        }
    }

    private void initFragment() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(R.color.transparent);
        View indicator = getLayoutInflater().inflate(R.layout.indicator_main_home, null);
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator(indicator), HomeFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.indicator_main_contact, null);
        mTabHost.addTab(mTabHost.newTabSpec("contactList").setIndicator(indicator), ContactListFragment.class, null);
        initMessageCount(indicator);

        indicator = getLayoutInflater().inflate(R.layout.indicator_main_work, null);
        mTabHost.addTab(mTabHost.newTabSpec("work").setIndicator(indicator), WorkspaceFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.indicator_org_work, null);
        mTabHost.addTab(mTabHost.newTabSpec("contact").setIndicator(indicator), ContactsFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.indicator_main_config, null);


        mTabHost.addTab(mTabHost.newTabSpec("config").setIndicator(indicator), MyFragment.class, null);
        redPoint = indicator.findViewById(R.id.redPoint);
    }

    private void initMessageCount(View indicator) {

        Badge qBadgeView = new QBadgeView(this)
                .bindTarget(indicator.findViewById(R.id.tabImg))
                .setBadgeNumber(Var.get("MainActivity.initMessageCount").getVar())
                .setBadgePadding(3, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(-2, -2, true)
                .setBadgeTextSize(10, true)
                .setOnDragStateChangedListener((dragState, badge, targetView) -> {
                    //清除成功
                    if (dragState == Badge.OnDragStateChangedListener.STATE_SUCCEED) {
                        EanfangHttp.get(NewApiService.GET_PUSH_READ_ALL).execute(new EanfangCallback(this, false));
//                        runOnUiThread(() -> {
//                            showToast("消息被清空了");
//                        });
//                        Var.get().setVar(0);
                    }
                });
        //变量监听
        Var.get("MainActivity.initMessageCount").setChangeListener((var) -> {
            runOnUiThread(() -> {
                qBadgeView.setBadgeNumber(var);
            });
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
//        getInfoBytoken();
    }


    /**
     * 点击两次返回键退出
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();

                mExitTime = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(getPackageName() + ".ExitListenerReceiver");
                sendBroadcast(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 请求基础数据
     */
    private void getBaseData() {
        String url;
        BaseDataBean dataBean = Config.get().getBaseDataBean();
        if (dataBean == null || StringUtils.isEmpty(dataBean.getMD5())) {
            url = NewApiService.GET_BASE_DATA_CACHE + "0";
        } else {
            url = NewApiService.GET_BASE_DATA_CACHE + dataBean.getMD5();
        }
        EanfangHttp.get(url)
                .tag(this)
                .execute(new EanfangCallback<JSONObject>(this, false, JSONObject.class, (jsonObject) -> {
                    new Thread(() -> {
                        if (jsonObject != null && !jsonObject.isEmpty() && jsonObject.containsKey("data") && !jsonObject.get("data").equals(Constant.NO_UPDATE)) {
//                            BaseDataBean newDate = jsonObject.toJavaObject(BaseDataBean.class);
                            EanfangApplication.get().set(BaseDataBean.class.getName(), jsonObject.toJSONString());
                        }
                    }).start();
                }));
    }

    /**
     * 请求静态常量
     */
    private void getConst() {
        String url;
        ConstAllBean constBean = Config.get().getConstBean();
        if (constBean == null || StringUtils.isEmpty(constBean.getMD5())) {
            url = NewApiService.GET_CONST_CACHE + "0";
        } else {
            url = NewApiService.GET_CONST_CACHE + constBean.getMD5();
        }
        EanfangHttp.get(url)
                .tag(this)
                .execute(new EanfangCallback<JSONObject>(this, false, JSONObject.class, (jsonObject) -> {
                    new Thread(() -> {
                        if (jsonObject != null && !jsonObject.isEmpty() && jsonObject.containsKey("data") && !jsonObject.get("data").equals(Constant.NO_UPDATE)) {
//                            ConstAllBean newDate = JSONObject.parseObject(str, ConstAllBean.class);
                            EanfangApplication.get().set(ConstAllBean.class.getName(), jsonObject.toJSONString());
                        }
                    }).start();
                }));
    }


    public void initXinGe() {
        registerXinGe();
//        Var.get("MainActivity.initXinGe").setChangeListener((var) -> {
//
//            ExecuteUtils.execute(
//                    () -> Var.get("MainActivity.initXinGe").getVar()
//                    , 1, 0,
//                    () -> Var.remove("MainActivity.initXinGe")
//                    , () -> {
//                        registerXinGe();
//                    });
//
//        });
//        Var.get("MainActivity.initXinGe").setVar(0);
    }


    private void registerXinGe() {
        //开启信鸽日志输出
//        XGPushConfig.enableDebug(this, false);
        //信鸽注册代码
        XGPushManager.registerPush(this, user.getAccount().getMobile(), new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                Log.d("TPush", "注册成功，设备token为：" + data);
//                    Var.get("MainActivity.initXinGe").setVar(1);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
//                    Var.get("MainActivity.initXinGe").setVar(0);
                registerXinGe();
            }
        });
    }

    public void setHeaders() {
        if (EanfangApplication.get().getUser() != null) {
            EanfangHttp.setToken(EanfangApplication.get().getUser().getToken());
        }
        EanfangHttp.setClient();
    }

    /**
     * 首页，工作台，我的，通讯录等未查找控件点击事件
     */
    public void noOpen(View v) {
        showToast("暂缓开通");
    }
}

