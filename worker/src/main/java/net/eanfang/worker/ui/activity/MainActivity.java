package net.eanfang.worker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.BaseDataBean;
import com.eanfang.model.ConstAllBean;
import com.eanfang.model.LoginBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.ExecuteUtils;
import com.eanfang.util.LocationUtil;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UpdateManager;
import com.eanfang.util.Var;
import com.okgo.OkGo;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.worker.BuildConfig;
import net.eanfang.worker.R;
import net.eanfang.worker.ui.fragment.ContactListFragment;
import net.eanfang.worker.ui.fragment.ContactsFragment;
import net.eanfang.worker.ui.fragment.HomeFragment;
import net.eanfang.worker.ui.fragment.MyFragment;
import net.eanfang.worker.ui.fragment.WorkspaceFragment;

import butterknife.ButterKnife;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    protected FragmentTabHost mTabHost;
    View redPoint;
    private LoginBean user;
    private long mExitTime;
    private OkGo http;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        user = EanfangApplication.get().getUser();
        setHeaders();
        initXinGe();
        initFragment();

        submitLocation();
    }


    /**
     * 技师上报位置专用
     */
    private void submitLocation() {
        new Thread(() -> {
            PermissionUtils.get(this).getLocationPermission(() -> {
                LocationUtil.location(this, (location) -> {
                    WorkerEntity workerEntity = new WorkerEntity();
                    workerEntity.setAccId(user.getAccount().getAccId());
                    workerEntity.setLat(location.getLatitude() + "");
                    workerEntity.setLon(location.getLongitude() + "");
                    workerEntity.setPlaceCode(Config.get(this).getAreaCodeByName(location.getCity(), location.getCountry()));
                    //技师上报位置
                    EanfangHttp.post(UserApi.POST_WORKER_SUBMIT_LOCATION)
                            .upJson(JSONObject.toJSONString(workerEntity))
                            .execute(new EanfangCallback(this, false, String.class));
                });

            });
        }).start();
    }


    private void initFragment() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
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
        Var.get("MainActivity.initXinGe").setChangeListener((var) -> {

            ExecuteUtils.execute(
                    () -> Var.get("MainActivity.initXinGe").getVar()
                    , 1, 0,
                    () -> Var.remove("MainActivity.initXinGe")
                    , () -> {
                        registerXinGe();

                    });
        });
        Var.get("MainActivity.initXinGe").setVar(0);
    }


    private void registerXinGe() {
        //开启信鸽日志输出
//        XGPushConfig.enableDebug(this, false);
        //信鸽注册代码
        XGPushManager.registerPush(this, user.getAccount().getMobile(), new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
//                        Log.d("TPush", "注册成功，设备token为：" + data);
                Var.get("MainActivity.initXinGe").setVar(1);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
//                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
                Var.get("MainActivity.initXinGe").setVar(0);
            }
        });
    }


    public void setHeaders() {
        if (EanfangApplication.get().getUser() != null) {
            EanfangHttp.setToken(EanfangApplication.get().getUser().getToken());
        }
        EanfangHttp.setWorker();
    }
    /**
     * 首页，工作台，我的，通讯录等未查找控件点击事件
     */
    public void noOpen(View v) {
        showToast("暂缓开通");
    }

}

