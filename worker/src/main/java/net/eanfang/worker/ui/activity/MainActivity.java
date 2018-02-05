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
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.BaseDataBean;
import com.eanfang.model.ConstAllBean;
import com.eanfang.model.LoginBean;
import com.eanfang.ui.base.BaseActivity;
import com.im.fragment.ContactListFragment;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.fragment.ContactsFragment;
import net.eanfang.worker.ui.fragment.HomeFragment;
import net.eanfang.worker.ui.fragment.MyFragment;
import net.eanfang.worker.ui.fragment.WorkspaceFragment;

import butterknife.ButterKnife;

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
        ButterKnife.bind(this);
        user = EanfangApplication.get().getUser();
        initXinGe();
        initFragment();
        getBaseData();
        getConst();

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

    @Override
    protected void onResume() {
        super.onResume();
//        getInfoBytoken();
    }

    /**
     * 检查token
     */
//    public void getInfoBytoken() {
//        EanfangHttp.get(UserApi.CHECK_TOKEN)
//                .execute(new EanfangCallback<User>(this, false) {
//                    @Override
//                    public void onSuccess(User bean) {
//                        EanfangApplication.get().set(User.class.getName(), bean);
//                        BaseEvent baseEvent = new BaseEvent();
//                        baseEvent.setObject(bean);
//                        baseEvent.setEventId(Local.CHECK_TOKEN_HOME_SUCCESS);
//                        EventBus.getDefault().post(baseEvent);
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                        showToast(message);
//                    }
//                });
//    }

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
        EanfangHttp.get(NewApiService.GET_BASE_DATA)
                .tag(this)
                .execute(new EanfangCallback<BaseDataBean>(this, false, BaseDataBean.class, (bean) -> {
                    EanfangApplication.get().set(BaseDataBean.class.getName(), JSONObject.toJSONString(bean, FastjsonConfig.config));
                }));
    }

    /**
     * 请求静态常量
     */
    private void getConst() {
        EanfangHttp.get(NewApiService.GET_CONST)
                .tag(this)
                .execute(new EanfangCallback<ConstAllBean>(this, false, ConstAllBean.class, (bean) -> {
                    EanfangApplication.get().set(ConstAllBean.class.getName(), JSONObject.toJSONString(bean, FastjsonConfig.config));
                }));
    }
}

