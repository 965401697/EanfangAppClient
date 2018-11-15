package net.eanfang.client.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.util.Log;
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
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.BaseDataBean;
import com.eanfang.model.ConstAllBean;
import com.eanfang.model.LoginBean;
import com.eanfang.model.device.User;
import com.eanfang.util.CleanMessageUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.SharePreferenceUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.UpdateAppManager;
import com.eanfang.util.Var;
import com.tencent.android.tpush.XGPushConfig;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
import net.eanfang.client.ui.activity.im.ConversationActivity;
import net.eanfang.client.ui.activity.worksapce.LoginHintActivity;
import net.eanfang.client.ui.activity.worksapce.WorkerDetailActivity;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.base.ClientApplication;
import net.eanfang.client.ui.fragment.ContactListFragment;
import net.eanfang.client.ui.fragment.ContactsFragment;
import net.eanfang.client.ui.fragment.HomeFragment;
import net.eanfang.client.ui.fragment.MyFragment;
import net.eanfang.client.ui.fragment.WorkspaceFragment;
import net.eanfang.client.ui.receiver.ReceiverInit;
import net.eanfang.client.util.PrefUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.TextMessage;

import static com.eanfang.config.EanfangConst.MEIZU_APPID_CLIENT;
import static com.eanfang.config.EanfangConst.MEIZU_APPKEY_CLIENT;
import static com.eanfang.config.EanfangConst.XIAOMI_APPID_CLIENT;
import static com.eanfang.config.EanfangConst.XIAOMI_APPKEY_CLIENT;

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

//        initState();

        ButterKnife.bind(this);
        user = EanfangApplication.get().getUser();
        setHeaders();

        //融云登录
        if (TextUtils.isEmpty(EanfangApplication.get().get(EanfangConst.RONG_YUN_TOKEN, ""))) {
            getRongYToken();
        } else {
            //如果有融云token 就直接登录
            ClientApplication.connect(EanfangApplication.get().get(EanfangConst.RONG_YUN_TOKEN, ""), this);
        }

        initFragment();
        //阻止底部 菜单拦被软键盘顶起
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        initUpdate();
        initXinGe();
        getBaseData();
        getConst();
        PermissionUtils.get(this).getStorageAndLocationPermission(() -> {
        });

        privoderMy();
        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
        RongIM.setConnectionStatusListener(new MyConnectionStatusListener());
        //判断是否完善资料
        if (TextUtils.isEmpty(EanfangApplication.getApplication().getUser().getAccount().getRealName()) || "待提供".equals(EanfangApplication.getApplication().getUser().getAccount().getRealName())) {
            startAnimActivity(new Intent(this, LoginHintActivity.class));
        }
        PrefUtils.setBoolean(getApplicationContext(), PrefUtils.GUIDE, false);//新手引导是否展示
    }

    private void initUpdate() {
        if (!EanfangApplication.isUpdated) {
            //app更新
            UpdateAppManager.update(this, BuildConfig.TYPE, false);
            EanfangApplication.isUpdated = true;
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
        redPoint = indicator.findViewById(R.id.redPoint);
        initMessageCount(indicator);

        indicator = getLayoutInflater().inflate(R.layout.indicator_main_work, null);
        mTabHost.addTab(mTabHost.newTabSpec("work").setIndicator(indicator), WorkspaceFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.indicator_org_work, null);
        mTabHost.addTab(mTabHost.newTabSpec("contact").setIndicator(indicator), ContactsFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.indicator_main_config, null);


        mTabHost.addTab(mTabHost.newTabSpec("config").setIndicator(indicator), MyFragment.class, null);
    }

    private void initMessageCount(View indicator) {
//        Badge qBadgeView = new QBadgeView(this)
//                .bindTarget(indicator.findViewById(R.id.ll_news))
//                .setBadgeNumber(Var.get("MainActivity.initMessageCount").getAllUnreadMessageCount() > 0 ? -1 : 0)
//                .setBadgePadding(5, true)
//                .setBadgeGravity(Gravity.END | Gravity.TOP)
//                .setGravityOffset(0, 0, true)
//                .setBadgeTextSize(18, true)
//                .setOnDragStateChangedListener((dragState, badge, targetView) -> {
//                    //清除成功
//                    if (dragState == Badge.OnDragStateChangedListener.STATE_SUCCEED) {
//                        EanfangHttp.get(NewApiService.GET_PUSH_READ_ALL).execute(new EanfangCallback(this, false, JSONObject.class));
//                        //  showToast("消息被清空了");
//                    }
//                });
        if (Var.get("MainActivity.initMessageCount").getAllUnreadMessageCount() > 0) {
            redPoint.setVisibility(View.VISIBLE);
        } else {
            redPoint.setVisibility(View.GONE);
        }
        //变量监听
        Var.get("MainActivity.initMessageCount").setChangeListener((var) -> {
            runOnUiThread(() -> {
//                qBadgeView.setBadgeNumber(var > 0 ? -1 : 0);
                if (var == 0) {
                    redPoint.setVisibility(View.GONE);
                } else {
                    redPoint.setVisibility(View.VISIBLE);
                }
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
                RongIM.getInstance().logout();//退出融云
                Intent intent = new Intent(getPackageName() + ".ExitListenerReceiver");
                sendBroadcast(intent);
                EanfangApplication.get().closeAll();
                EanfangApplication.isUpdated = false;
//                android.os.Process.killProcess(android.os.Process.myPid());

//                System.exit(0);//正常退出App

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
                            Config.get().cleanBaseDataBean();
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
                            Config.get().cleanConstBean();
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
        // 打开第三方推送
        XGPushConfig.enableOtherPush(MainActivity.this, true);
        //开启信鸽日志输出
        XGPushConfig.enableDebug(MainActivity.this, true);
        XGPushConfig.setHuaweiDebug(true);
        /**
         * 小米
         * */
        XGPushConfig.setMiPushAppId(MainActivity.this, XIAOMI_APPID_CLIENT);
        XGPushConfig.setMiPushAppKey(MainActivity.this, XIAOMI_APPKEY_CLIENT);
        /**
         * 魅族
         * */
        XGPushConfig.setMzPushAppId(MainActivity.this, MEIZU_APPID_CLIENT);
        XGPushConfig.setMzPushAppKey(MainActivity.this, MEIZU_APPKEY_CLIENT);
        ReceiverInit.getInstance().inits(MainActivity.this, user.getAccount().getMobile());
    }

    public void setHeaders() {
        if (EanfangApplication.get().getUser() != null) {
            EanfangHttp.setToken(EanfangApplication.get().getUser().getToken());
        }
        EanfangHttp.setClient();
    }

    /**
     * 获取融云的token
     */
    private void getRongYToken() {
        EanfangHttp.post(UserApi.POST_RONGY_TOKEN)
                .params("userId", EanfangApplication.get().getAccId())
                .execute(new EanfangCallback<String>(MainActivity.this, false, String.class, (str) -> {
                    if (!TextUtils.isEmpty(str)) {
                        Log.e("zzw1", str + "");
                        JSONObject json = JSONObject.parseObject(str);
                        String token = json.getString("token");
                        EanfangApplication.get().set(EanfangConst.RONG_YUN_TOKEN, token);
                        ClientApplication.connect(token, MainActivity.this);
                    }
                    Log.e("zzw2", str + "");
                }));


    }


    /**
     * 向融云提供自己的头像和昵称  兼容老版本
     */
    private void privoderMy() {
        //提供融云的自己头像和昵称
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {

                UserInfo userInfo = new UserInfo(String.valueOf(EanfangApplication.get().getAccId()), EanfangApplication.getApplication().getUser().getAccount().getNickName(), Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + EanfangApplication.getApplication().getUser().getAccount().getAvatar()));

                Log.e("zzw", "userInfo=" + userInfo.toString());


                return userInfo;
            }
        }, true);
//        RongIM.getInstance().setMessageAttachedUserInfo(true);//有具体场景的
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

            getIMUnreadMessageCount();

            //开发者根据自己需求自行处理
            boolean isDelect = false;
            String type = message.getObjectName();
            if (type.equals("RC:InfoNtf")) {
                InformationNotificationMessage msg = (InformationNotificationMessage) message.getContent();
                if (msg.getMessage().equals("解散了")) {
                    isDelect = true;
                    for (Activity activity : transactionActivities) {
                        if (activity instanceof ConversationActivity) {
                            if (message.getTargetId().equals(((ConversationActivity) activity).mId)) {
                                activity.finish();
                            }
                        }
                    }
                    RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, message.getTargetId(), null);
                }

            }


            if (message.getConversationType().getName().equals(Conversation.ConversationType.SYSTEM.getName())) {
                TextMessage messageContent = (TextMessage) message.getContent();
                if (messageContent.getContent().equals("被删除通知")) {

                    RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, message.getTargetId(), null);

                    for (Activity activity : transactionActivities) {
                        if (activity instanceof ConversationActivity) {
                            if (message.getTargetId().equals(((ConversationActivity) activity).mId)) {
                                activity.finish();
                            }
                        }
                    }

                } else if (messageContent.getContent().equals("被移除群组通知")) {

                    TextMessage textMessage = (TextMessage) message.getContent();
                    String extra = textMessage.getExtra();
                    JSONObject object = (JSONObject) JSONObject.parse(extra);
                    String id = (String) object.get("groupId");

                    RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, id, null);

                    for (Activity activity : transactionActivities) {
                        if (activity instanceof ConversationActivity) {
                            if (message.getTargetId().equals(((ConversationActivity) activity).mId)) {
                                activity.finish();
                            }
                        }
                    }
                } else {

                    EanfangHttp.get(UserApi.POST_USER_INFO + message.getTargetId())
                            .execute(new EanfangCallback<User>(MainActivity.this, false, User.class, (bean) -> {
                                UserInfo userInfo = new UserInfo(bean.getAccId(), bean.getNickName(), Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + bean.getAvatar()));

                                RongIM.getInstance().refreshUserInfoCache(userInfo);
                            }));
                }
            }
            Log.i("zzw", "--------------------isDelect=" + isDelect);
            return isDelect;
        }

    }

    public void getIMUnreadMessageCount() {
        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                int mUnreadMessageCount = 0;
                if (conversations != null && conversations.size() > 0) {
                    for (Conversation s : conversations) {
                        mUnreadMessageCount += s.getUnreadMessageCount();
                    }
                }
                Var.get("MainActivity.initMessageCount").setUnreadMessageCount(mUnreadMessageCount);
//                Log.e("zzw0", "IM未读=" + mUnreadMessageCount);
//                Log.e("zzw1", "通知未读=" + Var.get("MainActivity.initMessageCount").getVar());
//                Log.e("zzw2", "总未读=" + Var.get("MainActivity.initMessageCount").getAllUnreadMessageCount());
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        }, Conversation.ConversationType.GROUP, Conversation.ConversationType.PRIVATE);
    }

    class MyConnectionStatusListener implements RongIMClient.ConnectionStatusListener {

        @Override
        public void onChanged(ConnectionStatus connectionStatus) {

            switch (connectionStatus) {

                case CONNECTED://连接成功。

                    Log.i("zzw", "--------------------连接成功");

                    break;

                case DISCONNECTED://断开连接。

                    Log.i("zzw", "--------------------断开连接");

                    break;

                case CONNECTING://连接中。

                    Log.i("zzw", "--------------------链接中");

                    break;

                case NETWORK_UNAVAILABLE://网络不可用。

                    Log.i("zzw", "--------------------网络不可用");

                    break;

                case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线

                    Log.i("zzw", "--------------------掉线");

                    break;
            }
        }

    }

    /**
     * 首页，工作台，我的，通讯录等未查找控件点击事件
     */
    public void noOpen(View v) {
        showToast("暂缓开通");
    }

    @Subscribe
    public void onEvent(Integer integer) {
        if ((System.currentTimeMillis() - mExitTime) > 500) {

            ToastUtil.get().showToast(this, "登录失效，请重新登录！");

            mExitTime = System.currentTimeMillis();

            CleanMessageUtil.clearAllCache(EanfangApplication.get());
            SharePreferenceUtil.get().clear();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            RongIM.getInstance().logout();
            MainActivity.this.finish();
        }
    }

    @Subscribe
    public void onEventWork(WorkerEntity workerEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("workEntriy", workerEntity);
        JumpItent.jump(MainActivity.this, WorkerDetailActivity.class, bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mTabHost.getCurrentTab() == 1) {
            String frgTag = mTabHost.getCurrentTabTag();
            ContactListFragment contactListFragment = (ContactListFragment) getSupportFragmentManager().findFragmentByTag(frgTag);
            contactListFragment.onActivityResult(requestCode, resultCode, data);
        }else if(resultCode == RESULT_OK && requestCode == 49){
            ContactsFragment contactsFragment = (ContactsFragment) getSupportFragmentManager().getFragments().get(3);
            contactsFragment.onActivityResult(requestCode, resultCode, data);
        }
    }


}

