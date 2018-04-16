package net.eanfang.worker.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.config.EanfangConst;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.BaseDataBean;
import com.eanfang.model.ConstAllBean;
import com.eanfang.model.LoginBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.LocationUtil;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UpdateAppManager;
import com.eanfang.util.Var;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.worker.BuildConfig;
import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.WorkerApplication;
import net.eanfang.worker.ui.fragment.ContactListFragment;
import net.eanfang.worker.ui.fragment.ContactsFragment;
import net.eanfang.worker.ui.fragment.HomeFragment;
import net.eanfang.worker.ui.fragment.MyFragment;
import net.eanfang.worker.ui.fragment.WorkspaceFragment;

import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends BaseActivity {
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
        initXinGe();
        initFragment();
        initUpdate();

        //融云登录
//        if (TextUtils.isEmpty(EanfangApplication.get().get(EanfangConst.RONGY_TOKEN, ""))) {
//            getRongYToken();
//        } else {
        //如果有融云token 就直接登录
        WorkerApplication.connect("8r+X1NYm7DQ/eyyCfExBqKnYAGMEYj7eH80GfOhEd+nNdJZYZAPTY8ftDd0y3YxHe0vITuRR9NnUtyzaI5DrInqIYvXOLkvs");
//            WokerApplication.connect(EanfangApplication.get().get(EanfangConst.RONGY_TOKEN, ""));
//        }

        //阻止底部 菜单拦被软键盘顶起
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //   PermissionUtils.get(this).getStoragePermission(() -> {
        getBaseData();
        getConst();
        //  });
        submitLocation();
//        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
    }

    /**
     * 技师上报位置专用
     */
    private void submitLocation() {
        new Thread(() -> {
            PermissionUtils.get(this).getLocationPermission(() -> {
                LocationUtil.location(this, (location) -> {
                    LoginBean user = EanfangApplication.getApplication().getUser();
                    if (user == null || StringUtils.isEmpty(user.getToken())) {
                        return;
                    }
                    WorkerEntity workerEntity = new WorkerEntity();
                    workerEntity.setAccId(user.getAccount().getAccId());
                    workerEntity.setLat(location.getLatitude() + "");
                    workerEntity.setLon(location.getLongitude() + "");
                    workerEntity.setPlaceCode(Config.get().getAreaCodeByName(location.getCity(), location.getCountry()));
                    //技师上报位置
                    EanfangHttp.post(UserApi.POST_WORKER_SUBMIT_LOCATION)
                            .upJson(JSONObject.toJSONString(workerEntity))
                            .execute(new EanfangCallback(this, false, String.class));
                });

            });
        }).start();
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
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(-2, -2, true)
                .setBadgeTextSize(10, true)
                .setOnDragStateChangedListener((dragState, badge, targetView) -> {
                    //清除成功
                    if (dragState == Badge.OnDragStateChangedListener.STATE_SUCCEED) {
                        EanfangHttp.get(NewApiService.GET_PUSH_READ_ALL).execute(new EanfangCallback(this, false));
                        showToast("消息被清空了");
                    }
                });
        //变量监听
        Var.get("MainActivity.initMessageCount").setChangeListener((var) -> {
            qBadgeView.setBadgeNumber(var);
        });
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

    public void initXinGe() {
        registerXinGe();
//        Var.get("MainActivity.initXinGe").setChangeListener((var) -> {
//
//            ExecuteUtils.execute(
//                    () -> Var.get("MainActivity.initXinGe").getVar()
//                    , 1, 0,
//                    () -> Var.remove("MainActivity.initXinGe")
//                    , () -> {
//
//
//                    });
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
//                        Log.d("TPush", "注册成功，设备token为：" + data);
                // Var.get("MainActivity.initXinGe").setVar(1);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
//                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
                //Var.get("MainActivity.initXinGe").setVar(0);
                registerXinGe();
            }
        });
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
                .execute(new EanfangCallback<String>(this, false, String.class, (str) -> {
                    new Thread(() -> {
                        if (!StringUtils.isEmpty(str) && !str.contains(Constant.NO_UPDATE)) {
                            BaseDataBean newDate = JSONObject.parseObject(str, BaseDataBean.class);
                            EanfangApplication.get().set(BaseDataBean.class.getName(), JSONObject.toJSONString(newDate, FastjsonConfig.config));
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
                .execute(new EanfangCallback<String>(this, false, String.class, (str) -> {
                    new Thread(() -> {
                        if (!StringUtils.isEmpty(str) && !str.contains(Constant.NO_UPDATE)) {
                            ConstAllBean newDate = JSONObject.parseObject(str, ConstAllBean.class);
                            EanfangApplication.get().set(ConstAllBean.class.getName(), JSONObject.toJSONString(newDate, FastjsonConfig.config));
                        }
                    }).start();
                }));
    }


    public void setHeaders() {
        if (EanfangApplication.get().getUser() != null) {
            EanfangHttp.setToken(EanfangApplication.get().getUser().getToken());
        }
        EanfangHttp.setWorker();
    }

    /**
     * 获取融云的token
     */
    private void getRongYToken() {
        EanfangHttp.post(UserApi.POST_RONGY_TOKEN)
                .params("userId", EanfangApplication.get().getAccId())
                .execute(new EanfangCallback<String>(MainActivity.this, false, String.class, (str) -> {
                    if (!TextUtils.isEmpty(str)) {
                        JSONObject json = JSONObject.parseObject(str);
                        String token = json.getString("token");
                        EanfangApplication.get().set(EanfangConst.RONGY_TOKEN, token);
                        WorkerApplication.connect(token);
                    }
                }));
    }


    private class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {

        /**
         * 收到消息的处理。
         *
         * @param message 收到的消息实体。
         * @param left    剩余未拉取消息数目。
         * @return 收到消息是否处理完成，true 表示自己处理铃声和后台通知，false 走融云默认处理方式。
         */
        @Override
        public boolean onReceived(Message message, int left) {
            //开发者根据自己需求自行处理
            Log.e("zzw", message.getContent().toString());
            //根据消息类型判断
            return false;
        }
    }

    /**
     * 首页，工作台，我的，通讯录等未查找控件点击事件
     */
    public void noOpen(View v) {
        showToast("暂缓开通");
    }

}

