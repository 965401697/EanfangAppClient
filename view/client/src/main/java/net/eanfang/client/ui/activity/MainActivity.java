package net.eanfang.client.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.FragmentTabHost;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.cache.CacheMod;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.biz.model.bean.AllMessageBean;
import com.eanfang.biz.model.bean.BaseDataBean;
import com.eanfang.biz.model.bean.ConstAllBean;
import com.eanfang.biz.model.bean.GroupDetailBean;
import com.eanfang.biz.model.bean.device.User;
import com.eanfang.biz.model.entity.WorkerEntity;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MainDs;
import com.eanfang.biz.rds.sys.repo.MainRepo;
import com.eanfang.config.Config;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.sys.activity.LoginActivity;
import com.eanfang.util.BadgeUtil;
import com.eanfang.util.CleanMessageUtil;
import com.eanfang.util.ContactUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.UpdateAppManager;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.im.ConversationActivity;
import net.eanfang.client.ui.activity.worksapce.WorkerDetailActivity;
import net.eanfang.client.ui.activity.worksapce.notice.MessageListActivity;
import net.eanfang.client.ui.activity.worksapce.notice.OfficialListActivity;
import net.eanfang.client.ui.activity.worksapce.notice.SystemNoticeActivity;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.ContactListFragment;
import net.eanfang.client.ui.fragment.ContactsFragment;
import net.eanfang.client.ui.fragment.HomeFragment;
import net.eanfang.client.ui.fragment.MyFragment;
import net.eanfang.client.ui.fragment.WorkspaceFragment;
import net.eanfang.client.ui.receiver.ReceiverInit;

import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.HashMap;

import butterknife.ButterKnife;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;
import q.rorbin.badgeview.QBadgeView;

import static com.eanfang.config.EanfangConst.MEIZU_APPID_CLIENT;
import static com.eanfang.config.EanfangConst.MEIZU_APPKEY_CLIENT;
import static com.eanfang.config.EanfangConst.XIAOMI_APPID_CLIENT;
import static com.eanfang.config.EanfangConst.XIAOMI_APPKEY_CLIENT;


/**
 * @author jornl
 * @date 2019-07-09
 */
public class MainActivity extends BaseClientActivity implements IUnReadMessageObserver {
    private static final String IS_UPDATE_TAG = "IS_UPDATE_TAG";
    protected FragmentTabHost mTabHost;
    private long mExitTime;

    //被删除的 群组id 容器
    public static HashMap<String, String> hashMap = new HashMap<>();
    /**
     * 底部消息数量
     */
    private QBadgeView qBadgeViewHome = new QBadgeView(ClientApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewContact = new QBadgeView(ClientApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewWork = new QBadgeView(ClientApplication.get().getApplicationContext());
    private int mContact = 0;

    /**
     * 当前聊天服务状态
     */
    private String mStatus = "";
    /**
     * 消息页面回话数量
     */
    private int mContactNum = 0;
    /**
     * 所有消息总数记录
     */
    private int mTotalCount = 0;
    private MainRepo mainRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mainRepo = new MainRepo(new MainDs(new BaseViewModel()));
        init();
    }

    private void init() {
        initXinGe();
        getBaseData();
        getConst();
        RxPerm.get(this).getAllPerm();
        initRongIm();
        initFragment();
        initUpdate();
        initView();
        initYingShiYunData();
//        if (Config.get().getBaseDataBean() == null || Config.get().getConstBean() == null) {
//            Dialog dialog = LoadKit.dialog(this, "正在初始化...");
//            dialog.setCancelable(false);
//            dialog.show();
//            Observable.interval(0, 1, TimeUnit.SECONDS)
//                    .take(10)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .map(count -> 10 - count)
//                    .subscribe(new Observer<Long>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(Long d) {
//                            LoadKit.setText(dialog, StrUtil.format("正在初始化({})", d.toString()));
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            dialog.setCancelable(true);
//                            dialog.dismiss();
//                        }
//                    });
//        }

    }

    /**
     * 存储脱岗监测token
     */
    private void initYingShiYunData() {
        EanfangHttp.post(NewApiService.HOME_SUB_ACCOUNT_INFO_LIST).execute(new EanfangCallback<JSONObject>(this, false, JSONObject.class, bean -> {
            JSONObject jsonObject = bean.getJSONObject("subAccountInfoList");
            CacheKit.get().put("subAccountInfoList", jsonObject);
        }));
    }

    /**
     * 初始化页面相关
     */
    private void initView() {
        //阻止底部 菜单拦被软键盘顶起
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //  判断是否有密码
//        if (ClientApplication.get().getLoginBean().getAccount().isSimplePwd()) {
//            startActivity(new Intent(this, SetPasswordActivity.class));
//            // 判断是否完善资料
//        } else if (TextUtils.isEmpty(ClientApplication.get().getAccount().getRealName()) || "待提供".equals(ClientApplication.get().getAccount().getRealName())) {
//            startActivity(new Intent(this, LoginHintActivity.class));
//        }

    }

    /**
     * 初始化融云
     */
    private void initRongIm() {
        String rongToken = ClientApplication.get().get(EanfangConst.RONG_YUN_TOKEN, "");
        //融云登录
        if (TextUtils.isEmpty(rongToken)) {
            getRongImToken();
        } else {
            //如果有融云token 就直接登录
            ClientApplication.connect(rongToken);
        }

        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
        RongIM.setConnectionStatusListener(new MyConnectionStatusListener());

        //提供融云的自己头像和昵称
        RongIM.setUserInfoProvider(s -> new UserInfo(String.valueOf(ClientApplication.get().getAccId()), ClientApplication.get().getLoginBean().getAccount().getNickName(),
                Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + ClientApplication.get().getLoginBean().getAccount().getAvatar())), true);

        getPushMessage(getIntent());
        final Conversation.ConversationType[] conversationTypes = {
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE
        };

        RongIM.getInstance().addUnReadMessageCountChangedObserver(this, conversationTypes);
    }

    /**
     * 初始化更新检测
     */
    private void initUpdate() {
        if (!CacheKit.get().getBool(IS_UPDATE_TAG, false)) {
            //app更新
            UpdateAppManager.update(this, false);
            CacheKit.get().put(IS_UPDATE_TAG, true, CacheMod.Memory);
        }
    }

    /**
     * 初始化底部五页
     */
    private void initFragment() {
        mTabHost = findViewById(R.id.tabhost);
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


    @Override
    protected void onResume() {
        super.onResume();
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
                BaseApplication.get().closeAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getBaseData() {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            String md5 = Config.get().getBaseDataBean() != null ? Config.get().getBaseDataBean().getMD5() : "0";
            if (md5.equals("0")) {
                byte[] bytes = IoUtil.readBytes(this.getResources().openRawResource(com.eanfang.R.raw.basedata));
                if (bytes != null) {
                    BaseDataBean baseDataBean = ObjectUtil.unserialize(bytes);
                    CacheKit.get().put(BaseDataBean.class.getName(), baseDataBean);
                    md5 = baseDataBean.getMD5();
                }
            }
            emitter.onNext(md5);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(md5 -> {
                    mainRepo.getBaseData(md5).observe(this, (bean) -> {
                        if (bean != null) {
                            CacheKit.get().put(BaseDataBean.class.getName(), bean);
                            Config.get().cleanBaseDataBean();
                            Config.get().getBaseDataBean();
                        }
                    });
                });
//        String md5 = Config.get().getBaseDataBean() != null ? Config.get().getBaseDataBean().getMD5() : "0";
//        mainRepo.getBaseData(md5).observe(this, (bean) -> {
//            if (bean != null) {
//                ClientApplication.get().set(BaseDataBean.class.getName(), bean);
//            }
//        });
    }

    /**
     * 请求静态常量
     */
    private void getConst() {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            String md5 = Config.get().getConstBean() != null ? Config.get().getConstBean().getMD5() : "0";
            if (md5.equals("0")) {
                byte[] bytes = IoUtil.readBytes(this.getResources().openRawResource(com.eanfang.R.raw.constdata));
                if (bytes != null) {
                    ConstAllBean constAllBean = ObjectUtil.unserialize(bytes);
                    CacheKit.get().put(ConstAllBean.class.getName(), constAllBean);
                    md5 = constAllBean.getMD5();
                }
            }
            emitter.onNext(md5);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(md5 -> {
                    mainRepo.getConstData(md5).observe(this, (bean) -> {
                        if (bean != null) {
                            CacheKit.get().put(ConstAllBean.class.getName(), bean);
                            Config.get().cleanConstBean();
                            Config.get().getConstBean();
                        }
                    });
                });

//        mainRepo.getConstData(md5).observe(this, (bean) -> {
//            if (bean != null) {
//                ClientApplication.get().set(ConstAllBean.class.getName(), bean);
//            }
//        });
    }

    /**
     * 初始化信鸽推送
     */
    public void initXinGe() {
        ThreadUtil.execAsync(() -> {
            // 打开第三方推送
            SDKManager.getXGPush(MainActivity.this).enableOtherPush(true);
            //开启信鸽日志输出
            SDKManager.getXGPush(MainActivity.this).enableDebug(true);
            SDKManager.getXGPush(MainActivity.this).setHuaweiDebug(true);
            SDKManager.getXGPush(MainActivity.this).setMiPush(XIAOMI_APPID_CLIENT, XIAOMI_APPKEY_CLIENT);
            SDKManager.getXGPush(MainActivity.this).setMzPush(MEIZU_APPID_CLIENT, MEIZU_APPKEY_CLIENT);

//            SDKManager.getXGPush(MainActivity.this).registerPush(BaseApplication.get().getAccount().getMobile());
            ReceiverInit.getInstance().inits(MainActivity.this, BaseApplication.get().getAccount().getMobile());
        });
    }


    /**
     * 获取融云的token
     */
    /**
     * {@link net.eanfang.client.main.activity.MainActivity}
     */
    @Deprecated
    private void getRongImToken() {
        EanfangHttp.post(UserApi.POST_RONGY_TOKEN)
                .params("userId", ClientApplication.get().getAccId())
                .execute(new EanfangCallback<String>(MainActivity.this, false, String.class, (str) -> {
                    if (!TextUtils.isEmpty(str)) {
                        JSONObject json = JSONObject.parseObject(str);
                        String token = json.getString("token");
                        ClientApplication.get().set(EanfangConst.RONG_YUN_TOKEN, token);
                        ClientApplication.connect(token);
                    }
                }));


    }


    /**
     * {@link com.eanfang.rong.MyReceiveMessageListener}
     */
    @Deprecated
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
//            getIMUnreadMessageCount();
            //开发者根据自己需求自行处理
            if (message.getConversationType().getName().equals(Conversation.ConversationType.SYSTEM.getName())) {
                TextMessage messageContent = (TextMessage) message.getContent();
                switch (messageContent.getContent()) {
                    case "被删除通知":


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

                        break;
                    case "群解散通知": {
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

                        break;
                    }
                    case "被移除群组通知": {

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


                        //删除好友的会话记录
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
                        break;
                    }
                    case "好友邀请":
                        hashMap.put(message.getTargetId(), message.getTargetId());
                        break;
                    case "拒绝添加好友通知":
                        hashMap.put(message.getTargetId(), message.getTargetId());
                        break;
                    default:
                        //移除添加被删除的id
                        if (hashMap.get(message.getTargetId()) != null) {
                            hashMap.remove(message.getTargetId());
                        }

                        EanfangHttp.get(UserApi.POST_USER_INFO + message.getTargetId())
                                .execute(new EanfangCallback<User>(MainActivity.this, false, User.class, (bean) -> {
                                    UserInfo userInfo = new UserInfo(bean.getAccId(), bean.getNickName(), Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + bean.getAvatar()));

                                    RongIM.getInstance().refreshUserInfoCache(userInfo);
                                }));
                        break;
                }
            }
            return false;
        }

    }

    @Override
    public void onCountChanged(int integer) {
        mContactNum = integer;
        int i = mContact + integer;
        int nums = mTotalCount + integer;
        doChange(i, nums);
    }

    private void doChange(int mContactNum, int nums) {
        qBadgeViewContact.setBadgeNumber(mContactNum);
        BadgeUtil.setBadgeCount(MainActivity.this, nums, R.drawable.client_logo);
    }

    class MyConnectionStatusListener implements RongIMClient.ConnectionStatusListener {

        @Override
        public void onChanged(ConnectionStatus connectionStatus) {

            switch (connectionStatus) {
                //连接成功。
                case CONNECTED:

                    Log.i("zzw", "--------------------连接成功");
//                    getIMUnreadMessageCount();
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

            CleanMessageUtil.clearAllCache(ClientApplication.get());
            ClientApplication.get().clear();
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

    @Subscribe
    public void onEventBottomRedIcon(AllMessageBean bean) {
        //消息页面红点
        /**
         * 消息总数量
         */
        int mAllCount = 0;
        if (bean.getBiz() > 0 || bean.getSys() > 0 || bean.getCmp() > 0 || mContactNum > 0) {
            mContact = bean.getBiz() + bean.getSys() + bean.getCmp();
            mAllCount = bean.getBiz() + bean.getSys() + bean.getCmp() + mContactNum;
        } else {
            mAllCount = 0;
        }
        // 首页小红点的显示
        int mHome = 0;
        if (bean.getRepair() > 0 || bean.getInstall() > 0 || bean.getDesign() > 0
                || bean.getNoReadCount() > 0 || bean.getCommentNoRead() > 0 || bean.getAlert() > 0) {
            //首页气泡 暂时去掉安防圈评论未读数量  + bean.getCommentNoRead();
            mHome = bean.getRepair() + bean.getInstall() + bean.getDesign() + bean.getNoReadCount() + bean.getAlert();
        } else {
            mHome = 0;
        }
        // 工作台消息红点
        int mWork = 0;
        if (bean.getReport() > 0 || bean.getTask() > 0 || bean.getInspect() > 0) {
            mWork = bean.getReport() + bean.getTask() + bean.getInspect();
        } else {
            mWork = 0;
        }
        //桌面app红点
        mTotalCount = mHome + mAllCount + mWork;
        BadgeUtil.setBadgeCount(MainActivity.this, mTotalCount, R.drawable.client_logo);

        qBadgeViewHome.bindTarget(findViewById(R.id.tab_home))
                .setBadgeNumber(mHome)
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(2, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(0, 3, true)
                .setBadgeTextSize(11, true);
        qBadgeViewContact.bindTarget(findViewById(R.id.tab_contact))
                .setBadgeNumber(mAllCount)
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(2, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(0, 3, true)
                .setBadgeTextSize(11, true);
        qBadgeViewWork.bindTarget(findViewById(R.id.tab_work))
                .setBadgeNumber(mWork)
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(2, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(0, 3, true)
                .setBadgeTextSize(11, true);
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
            if (!StrUtil.isEmpty(uri.getQueryParameter("type"))) {
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

