package net.eanfang.worker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
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
import com.eanfang.util.LocationUtil;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;
import com.im.fragment.ContactListFragment;
import com.okgo.OkGo;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.fragment.ContactsFragment;
import net.eanfang.worker.ui.fragment.HomeFragment;
import net.eanfang.worker.ui.fragment.MyFragment;
import net.eanfang.worker.ui.fragment.WorkspaceFragment;
import net.eanfang.worker.util.UpdateManager;

import butterknife.ButterKnife;

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
        getBaseData();
        getConst();
        //更新
        UpdateManager manager = new UpdateManager(this);
        manager.checkUpdate();

    }


    /**
     * 技师上报位置专用
     */
    private void submitLocation() {

        PermissionUtils.get(this).getLocationPermission(() -> {
            LocationUtil.location(this, (location) -> {
                runOnUiThread(() -> {
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
        });


    }


    private void initFragment() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        View indicator = getLayoutInflater().inflate(R.layout.indicator_main_home, null);
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator(indicator), HomeFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.indicator_main_contact, null);
        mTabHost.addTab(mTabHost.newTabSpec("contactList").setIndicator(indicator), ContactListFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.indicator_main_work, null);
        mTabHost.addTab(mTabHost.newTabSpec("work").setIndicator(indicator), WorkspaceFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.indicator_org_work, null);
        mTabHost.addTab(mTabHost.newTabSpec("contact").setIndicator(indicator), ContactsFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.indicator_main_config, null);
        mTabHost.addTab(mTabHost.newTabSpec("config").setIndicator(indicator), MyFragment.class, null);
        redPoint = indicator.findViewById(R.id.redPoint);
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
        //开启信鸽日志输出
        XGPushConfig.enableDebug(this, true);
        //信鸽注册代码
        XGPushManager.registerPush(this, user.getAccount().getMobile(), new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                Log.d("TPush", "注册成功，设备token为：" + data);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
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
                    if (!str.contains(Constant.NO_UPDATE)) {
                        BaseDataBean newDate = JSONObject.parseObject(str, BaseDataBean.class);
                        EanfangApplication.get().set(BaseDataBean.class.getName(), JSONObject.toJSONString(newDate, FastjsonConfig.config));
                    }
                    submitLocation();
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
                    if (!str.contains(Constant.NO_UPDATE)) {
                        ConstAllBean newDate = JSONObject.parseObject(str, ConstAllBean.class);
                        EanfangApplication.get().set(ConstAllBean.class.getName(), JSONObject.toJSONString(newDate, FastjsonConfig.config));
                    }
                }));
    }


    public void setHeaders() {
        if (EanfangApplication.get().getUser() != null) {
            EanfangHttp.setToken(EanfangApplication.get().getUser().getToken());
        }
        EanfangHttp.setWorker();
    }

}

