package net.eanfang.client.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.yaf.model.LoginBean;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.config.Config;
import net.eanfang.client.config.Local;
import net.eanfang.client.network.apiservice.NewApiService;
import net.eanfang.client.network.apiservice.UserApi;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.base.BaseEvent;
import net.eanfang.client.ui.fragment.ContactsFragment;
import net.eanfang.client.ui.fragment.HomeFragment;
import net.eanfang.client.ui.fragment.MyFragment;
import net.eanfang.client.ui.fragment.WorkspaceFragment;
import net.eanfang.client.ui.model.BaseDataBean;
import net.eanfang.client.ui.model.ConstAllBean;
import net.eanfang.client.ui.model.User;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 *
 */
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
        initFragment();
        getBaseData();
        getConst();

//        //更新
//        UpdateManager manager = new UpdateManager(this);
//        manager.checkUpdate();
    }

    private void initFragment() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        View indicator = getLayoutInflater().inflate(R.layout.indicator_main_home, null);
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator(indicator), HomeFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.indicator_main_work, null);
        mTabHost.addTab(mTabHost.newTabSpec("work").setIndicator(indicator), WorkspaceFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.indicator_main_contact, null);
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
    public void getInfoBytoken() {
        EanfangHttp.get(UserApi.CHECK_TOKEN)
                .execute(new EanfangCallback<User>(this, false) {
                    @Override
                    public void onSuccess(User bean) {
                        EanfangApplication.get().set(User.class.getName(), bean);
                        BaseEvent baseEvent = new BaseEvent();
                        baseEvent.setObject(bean);
                        baseEvent.setEventId(Local.CHECK_TOKEN_HOME_SUCCESS);
                        EventBus.getDefault().post(baseEvent);
                    }

                    @Override
                    public void onError(String message) {
                        showToast(message);
                    }
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

    /**
     * 请求基础数据
     */
    private void getBaseData() {
        EanfangHttp.get(NewApiService.GET_BASE_DATA)
                .tag(this)
                .execute(new EanfangCallback<BaseDataBean>(this, false, BaseDataBean.class, true, (list) -> {
//                    EanfangApplication.get().set(BaseDataBean.class.getName(), JSONObject.toJSONString(list, FastjsonConfig.config));
                    Config.getConfig().setBaseDataBean(list);
                }));
    }

    /**
     * 请求静态常量
     */
    private void getConst() {
        EanfangHttp.get(NewApiService.GET_CONST)
                .tag(this)
                .execute(new EanfangCallback<ConstAllBean>(this, false, ConstAllBean.class, (bean) -> {
//                    EanfangApplication.get().set(ConstAllBean.class.getName(), JSONObject.toJSONString(bean, FastjsonConfig.config));
                    Config.getConfig().setConstBean(bean);
                }));
    }
}

