package net.eanfang.worker.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTabHost;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.widget.controltool.ControlToolView;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.AllMessageBean;
import com.eanfang.biz.model.bean.BaseDataBean;
import com.eanfang.biz.model.bean.ConstAllBean;
import com.eanfang.biz.model.GroupDetailBean;
import com.eanfang.biz.model.NoticeEntity;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.model.device.User;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.base.widget.controltool.badgeview.MyBadgeView;
import com.eanfang.sys.activity.LoginActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.BadgeUtil;
import com.eanfang.util.CleanMessageUtil;
import com.eanfang.util.ContactUtil;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.LocationUtil;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.UpdateAppManager;
import com.picker.common.util.ScreenUtils;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.worker.BuildConfig;
import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.im.ConversationActivity;
import net.eanfang.worker.ui.activity.worksapce.SetPasswordActivity;
import net.eanfang.worker.ui.activity.worksapce.WorkDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.notice.MessageListActivity;
import net.eanfang.worker.ui.activity.worksapce.notice.OfficialListActivity;
import net.eanfang.worker.ui.activity.worksapce.notice.SystemNoticeActivity;
import net.eanfang.worker.ui.fragment.ContactListFragment;
import net.eanfang.worker.ui.fragment.ContactsFragment;
import net.eanfang.worker.ui.fragment.HomeFragment;
import net.eanfang.worker.ui.fragment.MyFragment;
import net.eanfang.worker.ui.fragment.WorkspaceFragment;
import net.eanfang.worker.ui.receiver.ReceiverInit;
import net.eanfang.worker.util.PrefUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;

import static com.eanfang.config.EanfangConst.MEIZU_APPID_WORKER;
import static com.eanfang.config.EanfangConst.MEIZU_APPKEY_WORKER;
import static com.eanfang.config.EanfangConst.XIAOMI_APPID_WORKER;
import static com.eanfang.config.EanfangConst.XIAOMI_APPKEY_WORKER;

public class MainActivity extends BaseActivity implements IUnReadMessageObserver {
    private static final String TAG = MainActivity.class.getSimpleName();
    protected FragmentTabHost mTabHost;
    private LoginBean user;
    private long mExitTime;
    //被删除的 群组id 容器
    public static HashMap<String, String> hashMap = new HashMap<>();
    private int mHome = 0;
    private int mContact = 0;
    private int mWork = 0;
    /**
     * 当前聊天服务状态
     */
    private String mStatus = "";
    /**
     * 消息页面回话数量
     */
    private int mContactNum = 0;
    /**
     * 消息总数量
     */
    private int mAllCount = 0;

    /**
     * 所有消息总数记录
     */
    private int mTotalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        user = WorkerApplication.get().getLoginBean();
//        XGPushClickedResult message = XGPushManager.onActivityStarted(this);
//        if (message != null) {
//            // 获取自定义key-value String customContent = message.getCustomContent();
//            //拿到数据自行处理
//            if (isTaskRoot()) {
//                return;
//            }
//            //如果有面板存在则关闭当前的面板
//            finish();
//        }
        setHeaders();
        initXinGe();
        initFragment();
        initUpdate();

        String rongToken = WorkerApplication.get().get(EanfangConst.RONG_YUN_TOKEN, "");
        //融云登录
        if (TextUtils.isEmpty(rongToken)) {
            getRongYToken();
        } else {
            //如果有融云token 就直接登录
            WorkerApplication.connect(rongToken);
        }

        //阻止底部 菜单拦被软键盘顶起
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        getBaseData();
        getConst();
        RxPerm.get(this).storagePerm();
        submitLocation();
        privoderMy();

        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
        RongIM.setConnectionStatusListener(new MyConnectionStatusListener());

        //判断是否完善资料
//        if (TextUtils.isEmpty(WorkerApplication.get().getLoginBean().getAccount().getRealName()) || "待提供".equals(WorkerApplication.get().getLoginBean().getAccount().getRealName())) {
//            startAnimActivity(new Intent(this, LoginHintActivity.class));
//        }
        // 判断是否有密码
        if (WorkerApplication.get().getLoginBean().getAccount().isSimplePwd() == true) {
            startAnimActivity(new Intent(this, SetPasswordActivity.class));
        }
        getEquipmentUnread();//首次

/*
       WorkerApplication.get().setmForwardListener(new WorkerApplication.ForwardListener() {
            @Override
            public void onForwardListener() {
                getEquipmentUnread();
            }
        });
*/

        PrefUtils.setBoolean(getApplicationContext(), PrefUtils.GUIDE, false);//新手引导是否展示

        getPushMessage(getIntent());
        final Conversation.ConversationType[] conversationTypes = {
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE
        };
        ContactUtil.postAccount(MainActivity.this);
        RongIM.getInstance().addUnReadMessageCountChangedObserver(this, conversationTypes);
    }


    /**
     * 技师上报位置专用
     */
    private void submitLocation() {
        new Thread(() -> {
            LocationUtil.location(this, (location) -> {
                LoginBean user = WorkerApplication.get().getLoginBean();
                if (user == null || StringUtils.isEmpty(user.getToken())) {
                    return;
                }
                WorkerEntity workerEntity = new WorkerEntity();
                workerEntity.setAccId(user.getAccount().getAccId());
                workerEntity.setLat(location.getLatitude() + "");
                workerEntity.setLon(location.getLongitude() + "");
                workerEntity.setPlaceCode(Config.get().getAreaCodeByName(location.getCity(), location.getDistrict()));
                //技师上报位置
                EanfangHttp.post(UserApi.POST_WORKER_SUBMIT_LOCATION)
                        .upJson(JSONObject.toJSONString(workerEntity))
                        .execute(new EanfangCallback(this, false, String.class));
            });
        }).start();
    }


    private void initUpdate() {

        if (!WorkerApplication.isUpdated) {
            //app更新
            UpdateAppManager.update(this, BuildConfig.APP_TYPE, false);
            WorkerApplication.isUpdated = true;
        }
    }


    private void initFragment() {
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(R.color.transparent);
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
                //先注释
//                WorkerApplication.get().closeAll();
                WorkerApplication.isUpdated = false;
                //android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(0);//正常退出App
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void initXinGe() {
        registerXinGe();
    }


    private void registerXinGe() {
        // 打开第三方推送
        SDKManager.getXGPush(MainActivity.this).enableOtherPush(true);
        //开启信鸽日志输出
        SDKManager.getXGPush(MainActivity.this).enableDebug(true);
        SDKManager.getXGPush(MainActivity.this).setHuaweiDebug(true);
        SDKManager.getXGPush(MainActivity.this).setMiPush(XIAOMI_APPID_WORKER, XIAOMI_APPKEY_WORKER);
        SDKManager.getXGPush(MainActivity.this).setMzPush(MEIZU_APPID_WORKER, MEIZU_APPKEY_WORKER);
        SDKManager.getXGPush(MainActivity.this).registerPush(user.getAccount().getMobile());

        ReceiverInit.getInstance().inits(MainActivity.this, user.getAccount().getMobile());
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
                .execute(new EanfangCallback<BaseDataBean>(this, false, BaseDataBean.class, (baseDataBean) -> {
                    if (baseDataBean != null) {
                        WorkerApplication.get().set(BaseDataBean.class.getName(), baseDataBean);
                    }
                    saveArea();
                }));
    }

    private void saveArea() {
        //预加载国家区域
        BaseDataEntity areaJson = (BaseDataEntity) WorkerApplication.get().get(Constant.COUNTRY_AREA_LIST, BaseDataEntity.class);
        if (areaJson == null) {
            BaseDataEntity entity = new BaseDataEntity();
            List<BaseDataEntity> areaListBean = Config.get().getRegionList(1);
            //获得全部 地区数据
            List<BaseDataEntity> allAreaList = new ArrayList<>(Config.get().getRegionList());
            for (int i = 0; i < areaListBean.size(); i++) {
                BaseDataEntity provinceEntity = areaListBean.get(i);
                //处理当前省下的所有市
                List<BaseDataEntity> cityList = Stream.of(allAreaList).filter(bean -> bean.getParentId() != null
                        && bean.getParentId().intValue() == provinceEntity.getDataId()).toList();
                //查询出来后，移除，以增加效率
                allAreaList.removeAll(cityList);
                for (int j = 0; j < cityList.size(); j++) {
                    BaseDataEntity cityEntity = cityList.get(j);
                    //处理当前市下所有区县
                    List<BaseDataEntity> countyList = Stream.of(allAreaList).filter(bean -> bean.getParentId() != null
                            && bean.getParentId().intValue() == cityEntity.getDataId()).toList();
                    //查询出来后，移除，以增加效率
                    allAreaList.removeAll(countyList);
                    cityEntity.setChildren(countyList);
                }
                provinceEntity.setChildren(cityList);
            }
            entity.setChildren(areaListBean);
            WorkerApplication.get().sSaveArea = entity;
            WorkerApplication.get().set(Constant.COUNTRY_AREA_LIST, entity);
        } else {
            WorkerApplication.get().sSaveArea = areaJson;
        }
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
                .execute(new EanfangCallback<ConstAllBean>(this, false, ConstAllBean.class, (constAllBean) -> {
                    if (constAllBean != null) {
                        WorkerApplication.get().set(ConstAllBean.class.getName(), constAllBean);
                    }
                }));
    }

    public void setHeaders() {
        if (WorkerApplication.get().getLoginBean() != null) {
            EanfangHttp.setToken(WorkerApplication.get().getLoginBean().getToken());
//            HttpConfig.get().setToken(WorkerApplication.get().getLoginBean().getToken());
        }
        EanfangHttp.setWorker();
    }

    /**
     * 获取融云的token
     */
    private void getRongYToken() {
        EanfangHttp.post(UserApi.POST_RONGY_TOKEN)
                .params("userId", WorkerApplication.get().getAccId())
                .execute(new EanfangCallback<String>(MainActivity.this, false, String.class, (str) -> {
                    if (!TextUtils.isEmpty(str)) {
                        JSONObject json = JSONObject.parseObject(str);
                        String token = json.getString("token");
                        WorkerApplication.get().set(EanfangConst.RONG_YUN_TOKEN, token);
                        WorkerApplication.connect(token);
                    }
                }));
    }

    /**
     * 首页，工作台，我的，通讯录等未查找控件点击事件
     */
    public void noOpen(View v) {
        showToast("暂缓开通");
    }


    /**
     * 向融云提供自己的头像和昵称  兼容老版本
     */
    private void privoderMy() {
        //提供融云的自己头像和昵称
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {

                UserInfo userInfo = new UserInfo(String.valueOf(WorkerApplication.get().getAccId()),
                        WorkerApplication.get().getLoginBean().getAccount().getNickName(), Uri.parse(com.eanfang.BuildConfig.OSS_SERVER +
                        WorkerApplication.get().getLoginBean().getAccount().getAvatar()));
                Log.e("zzw", "userInfo=" + userInfo.toString());

                return userInfo;
            }
        }, true);
//        RongIM.getInstance().setMessageAttachedUserInfo(true);//有具体场景的
    }

    @Override
    public void onCountChanged(int integer) {
        mContactNum = integer;
        int i = mContact + integer;
        int nums = mTotalCount + integer;
        doChange(i, nums);
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

            /**String type = message.getObjectName();
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

             }*/


            if (message.getConversationType().getName().equals(Conversation.ConversationType.SYSTEM.getName())) {
                TextMessage messageContent = (TextMessage) message.getContent();
                if (messageContent.getContent().equals("被删除通知")) {

                    //删除好友的会话记录
                    RongIM.getInstance().clearMessages(Conversation.ConversationType.PRIVATE, message.getTargetId(), null);
                    RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, message.getTargetId(), null);
                    hashMap.put(message.getTargetId(), message.getTargetId());
                    for (Activity activity : transactionActivities) {
                        if (activity instanceof ConversationActivity) {
                            if (message.getTargetId().equals(((ConversationActivity) activity).mId)) {
                                activity.finish();
                            }
                        }
                    }

                } else if (messageContent.getContent().equals("群解散通知")) {
                    String extra = messageContent.getExtra();
                    JSONObject jsonObject = JSON.parseObject(extra);
                    //删除好友的会话记录

                    UserInfo userInfo = new UserInfo(jsonObject.getString("groupId"), jsonObject.getString("groupName"), Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + jsonObject.getString("groupPic")));

                    RongIM.getInstance().refreshUserInfoCache(userInfo);

                    RongIM.getInstance().clearMessages(Conversation.ConversationType.GROUP, message.getTargetId(), null);
                    RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, message.getTargetId(), null);
                    hashMap.put(message.getTargetId(), message.getTargetId());
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

                    // 设置系统消息的内容提供者  要不然第一删除的时候  会话裂变不会显示群名称和图片
                    runOnUiThread(() -> {
                        EanfangHttp.post(UserApi.POST_GROUP_DETAIL_RY)
                                .params("ryGroupId", id)
                                .execute(new EanfangCallback<GroupDetailBean>(MainActivity.this, true, GroupDetailBean.class, (bean) -> {

                                    UserInfo userInfo = new UserInfo(id, bean.getGroup().getGroupName(), Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + bean.getGroup().getHeadPortrait()));
                                    RongIM.getInstance().refreshUserInfoCache(userInfo);

                                }));

                    });
                    //添加被删除的id
                    hashMap.put(message.getTargetId(), message.getTargetId());
                    //删除好友的会话记录
                    RongIM.getInstance().clearMessages(Conversation.ConversationType.GROUP, message.getTargetId(), null);
                    RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, id, null);

                    for (Activity activity : transactionActivities) {
                        if (activity instanceof ConversationActivity) {
                            if (message.getTargetId().equals(((ConversationActivity) activity).mId)) {
                                activity.finish();
                            }
                        }
                    }
                } else if (messageContent.getContent().equals("好友邀请")) {
                    hashMap.put(message.getTargetId(), message.getTargetId());
                } else if (messageContent.getContent().equals("拒绝添加好友通知")) {
                    hashMap.put(message.getTargetId(), message.getTargetId());
                } else {
                    //移除添加被删除的id
                    if (hashMap.get(message.getTargetId()) != null) {
                        hashMap.remove(message.getTargetId());
                    }

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
        /**
         * 获取消息页面回话列表数量
         * */
        RongIM.getInstance().getTotalUnreadCount(new RongIMClient.ResultCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
//                mContactNum = integer;
//                int i = mContact + integer;
//                int nums = mTotalCount + integer;
//                doChange(i, nums);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    private void doChange(int mContactNum, int nums) {
        badgeView(R.id.tab_contact, mContactNum);
        BadgeUtil.setBadgeCount(MainActivity.this, nums, R.drawable.client_logo);
    }

    class MyConnectionStatusListener implements RongIMClient.ConnectionStatusListener {

        @Override
        public void onChanged(ConnectionStatus connectionStatus) {

            switch (connectionStatus) {
                //连接成功。
                case CONNECTED:

                    Log.i("zzw", "--------------------连接成功");
                    getIMUnreadMessageCount();
                    mStatus = "";
                    break;
                //断开连接。
                case DISCONNECTED:

                    Log.i("zzw", "--------------------断开连接");
                    mStatus = "聊天服务器正在连接中...";
                    break;
                //连接中。
                case CONNECTING:

                    Log.i("zzw", "--------------------链接中");
                    mStatus = "聊天服务器正在连接中...";
                    break;
                //网络不可用。
                case NETWORK_UNAVAILABLE:

                    Log.i("zzw", "--------------------网络不可用");
                    mStatus = "";
                    break;
                //用户账户在其他设备登录，本机会被踢掉线
                case KICKED_OFFLINE_BY_OTHER_CLIENT:

                    Log.i("zzw", "--------------------掉线");
                    mStatus = "用户账户在其他设备登录";
                    break;
                default:
                    break;
            }
        }

    }


    @Subscribe
    public void onEvent(Integer integer) {
        if ((System.currentTimeMillis() - mExitTime) > 500) {

            ToastUtil.get().showToast(this, "登录失效，请重新登录！");

            mExitTime = System.currentTimeMillis();

            CleanMessageUtil.clearAllCache(WorkerApplication.get());
            WorkerApplication.get().clear();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            RongIM.getInstance().logout();
            MainActivity.this.finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventAddTroublePic(WorkerEntity workerEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("workEntriy", workerEntity);
        JumpItent.jump(MainActivity.this, WorkDetailActivity.class, bundle);
    }

    private void getEquipmentUnread() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("reciveAccId", String.valueOf(WorkerApplication.get().getAccId()));
        queryEntry.getEquals().put("noticeType", "62");
        queryEntry.getEquals().put("status", "0");

        EanfangHttp.post(NewApiService.GET_UNREAD_MSG)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<NoticeEntity>(this, true, NoticeEntity.class, (bean) -> {
                            if (bean != null) {
                                customDialog(bean.getTitle(), bean.getContent(), bean.getExtInfo(), bean.getId());
                            }

                        })
                );
    }

    /**
     * 自定义对话框
     */
    private void customDialog(String t, String c, Object extInfo, long id) {
        final Dialog dialog = new Dialog(this, R.style.NormalDialogStyle);
        View view = View.inflate(this, R.layout.dialog_normal, null);
        TextView cancel = view.findViewById(R.id.cancel);
        TextView confirm = view.findViewById(R.id.confirm);
        TextView content = view.findViewById(R.id.content);
        TextView title = view.findViewById(R.id.title);

        title.setText(t);
        if (extInfo != null) {
            content.setText(c + "\r\n\t" + "\r\n\t" + extInfo);
        } else {
            content.setText(c);
        }


        dialog.setContentView(view);
        //使得点击对话框外部不消失对话框
        dialog.setCanceledOnTouchOutside(false);
        //设置对话框的大小
        view.setMinimumHeight((int) (ScreenUtils.heightPixels(this) * 0.23f));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenUtils.widthPixels(this) * 0.75f);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read(id);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void read(long id) {
        EanfangHttp.post(NewApiService.GET_PUSH_MSG_INFO + id)
                .execute(new EanfangCallback<NoticeEntity>(MainActivity.this, true, NoticeEntity.class, (bean -> {

                })));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mTabHost.getCurrentTab() == 1) {
            String frgTag = mTabHost.getCurrentTabTag();
            ContactListFragment contactListFragment = (ContactListFragment) getSupportFragmentManager().findFragmentByTag(frgTag);
            contactListFragment.onActivityResult(requestCode, resultCode, data);
        } else if (resultCode == RESULT_OK && requestCode == 49) {
            ContactsFragment contactsFragment = (ContactsFragment) getSupportFragmentManager().getFragments().get(3);
            contactsFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        /**
         * 获取推送
         * */
        getPushMessage(intent);
    }

    private void getPushMessage(Intent intent) {
        Uri uri = intent.getData();
        int mType;
        if (uri != null) {
            if (!StringUtils.isEmpty(uri.getQueryParameter("type"))) {
                mType = Integer.parseInt(uri.getQueryParameter("type"));
                if (mType == 2) {
                    // 打开messagelistactivity
                    JumpItent.jump(MainActivity.this, MessageListActivity.class);
                } else if (mType == 3) {
                    //打开systemnoticeactivity
                    JumpItent.jump(MainActivity.this, SystemNoticeActivity.class);
                } else if (mType == 4) {
                    //打开systemnoticeactivity
                    JumpItent.jump(MainActivity.this, OfficialListActivity.class);
                }
            }

        }
    }

    @Subscribe
    public void onEventBottomRedIcon(AllMessageBean bean) {
        // 首页小红点的显示
        if (bean.getRepair() > 0 || bean.getInstall() > 0 || bean.getDesign() > 0 || bean.getQuote() > 0
                || bean.getMaintain() > 0 || bean.getNoReadCount() > 0 || bean.getCommentNoRead() > 0) {
            mHome = bean.getRepair() + bean.getInstall() + bean.getDesign() + bean.getQuote() +
                    bean.getMaintain() + bean.getNoReadCount() + bean.getCommentNoRead();
        } else {
            mHome = 0;
        }
        //消息页面红点
        if (bean.getBiz() > 0 || bean.getSys() > 0 || bean.getCmp() > 0 || mContactNum > 0) {
            mContact = bean.getBiz() + bean.getSys() + bean.getCmp();
            mAllCount = bean.getBiz() + bean.getSys() + bean.getCmp() + mContactNum;
        } else {
            mAllCount = 0;
        }
        // 工作台消息红点
        if (bean.getReport() > 0 || bean.getTask() > 0 || bean.getInspect() > 0) {
            mWork = bean.getReport() + bean.getTask() + bean.getInspect();
        } else {
            mWork = 0;
        }
        mTotalCount = mHome + mAllCount + mWork;
        // 桌面app红点
        // 首页红点
//            new Handler(getMainLooper()).postDelayed(new Runnable() {
//                @Override
//                public void run() {
        // 桌面气泡赋值
        BadgeUtil.setBadgeCount(MainActivity.this, mTotalCount, R.drawable.client_logo);
//                }
//            }, 3 * 1000);

        badgeView(R.id.tab_home, mHome);
        badgeView(R.id.tab_contact, mAllCount);
        badgeView(R.id.tab_work, mWork);
    }

    private void badgeView(int id, int number) {
        ControlToolView.getBadge(WorkerApplication.get().getApplicationContext())
                .setTargetView(findViewById(id))
                .setBadgeNum(number)
                .badge();

    }

    public String onNoConatac() {
        return mStatus;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RongIM.getInstance().disconnect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int index = Arrays.asList(permissions).indexOf(Manifest.permission.READ_CONTACTS);
        if (grantResults[index] == 0) {
            ContactUtil.postAccount(MainActivity.this);
        }
    }
}

