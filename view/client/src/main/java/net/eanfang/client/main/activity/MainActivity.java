package net.eanfang.client.main.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTabHost;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.cache.CacheMod;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.base.widget.controltool.ControlToolView;
import com.eanfang.biz.model.AllMessageBean;
import com.eanfang.biz.model.bean.BaseDataBean;
import com.eanfang.biz.model.bean.ConstAllBean;
import com.eanfang.biz.model.bean.RongTokenBean;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangHttp;
import com.eanfang.kit.BaseDataKit;
import com.eanfang.kit.ConstDataKit;
import com.eanfang.kit.ToolsKit;
import com.eanfang.rong.MyReceiveMessageListener;
import com.eanfang.util.BadgeUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.UpdateAppManager;
import com.tencent.android.tpush.XGPushConfig;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.ActivityMainBinding;
import net.eanfang.client.main.viewmodel.MainViewModel;
import net.eanfang.client.ui.activity.worksapce.SetPasswordActivity;
import net.eanfang.client.ui.activity.worksapce.WorkerDetailActivity;
import net.eanfang.client.ui.activity.worksapce.notice.MessageListActivity;
import net.eanfang.client.ui.activity.worksapce.notice.OfficialListActivity;
import net.eanfang.client.ui.activity.worksapce.notice.SystemNoticeActivity;
import net.eanfang.client.ui.fragment.ContactListFragment;
import net.eanfang.client.ui.fragment.ContactsFragment;
import net.eanfang.client.ui.fragment.HomeFragment;
import net.eanfang.client.ui.fragment.MyFragment;
import net.eanfang.client.ui.fragment.WorkspaceFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Objects;

import cn.hutool.core.util.StrUtil;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

import static com.eanfang.config.EanfangConst.MEIZU_APPID_CLIENT;
import static com.eanfang.config.EanfangConst.MEIZU_APPKEY_CLIENT;
import static com.eanfang.config.EanfangConst.XIAOMI_APPID_CLIENT;
import static com.eanfang.config.EanfangConst.XIAOMI_APPKEY_CLIENT;

/**
 * MainActivity
 * 暂未启用
 *
 * @author jornl
 */
public class MainActivity extends BaseActivity {
    private static final String IS_UPDATE_TAG = "IS_UPDATE_TAG";
    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;
    private FragmentTabHost mTabHost;

    private int mHome = 0;
    private int mContact = 0;
    private int mWork = 0;

    //按下返回 时间间隔
    private long mExitTime;
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
    //被删除的 群组id 容器
    public static HashMap<String, String> hashMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        setHeaders();
        initUpdate();
        initRongIM();
        registerXinGe();
        initData();

        RxPerm.get(this).getAllPerm();
    }

    @Override
    protected ViewModel initViewModel() {
        mainViewModel = LViewModelProviders.of(this, MainViewModel.class);
        //融云token
        mainViewModel.getTokenLiveData().observe(this, this::handlerValue);
        //基础数据
        mainViewModel.getBaseDataLiveData().observe(this, this::handlerValue);
        //常量数据
        mainViewModel.getConstDataLiveData().observe(this, this::handlerValue);
        return mainViewModel;
    }

    @Override
    protected void initView() {
        super.initView();
        //阻止底部 菜单拦被软键盘顶起
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initFragment();

        // 判断是否有密码
        if (ClientApplication.get().getLoginBean().getAccount().isSimplePwd()) {
            startActivity(SetPasswordActivity.class);
        }
//        PrefUtils.setBoolean(getApplicationContext(), PrefUtils.GUIDE, false);//新手引导是否展示
        getPushMessage(getIntent());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                showToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                RongIM.getInstance().logout();//退出融云
                Intent intent = new Intent(getPackageName() + ".ExitListenerReceiver");

                sendBroadcast(intent);
                BaseApplication.get().closeAllActivity();
//                BaseApplication.get().closeAllActivity();
                //先注释
//                EanfangApplication.get().closeAll();
//                EanfangApplication.isUpdated = false;
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(0);//正常退出App
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getPushMessage(intent);
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

    @Subscribe
    public void onEventWork(WorkerEntity workerEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("workEntriy", workerEntity);
        JumpItent.jump(MainActivity.this, WorkerDetailActivity.class, bundle);
    }

    @Subscribe
    public void onEventBottomRedIcon(AllMessageBean bean) {
        //消息页面红点
        if (bean.getBiz() > 0 || bean.getSys() > 0 || bean.getCmp() > 0 || mContactNum > 0) {
            mContact = bean.getBiz() + bean.getSys() + bean.getCmp();
            mAllCount = bean.getBiz() + bean.getSys() + bean.getCmp() + mContactNum;
        } else {
            mAllCount = 0;
        }
        // 首页小红点的显示
        if (bean.getRepair() > 0 || bean.getInstall() > 0 || bean.getDesign() > 0
                || bean.getNoReadCount() > 0 || bean.getCommentNoRead() > 0 || bean.getAlert() > 0) {
            mHome = bean.getRepair() + bean.getInstall() + bean.getDesign() + bean.getNoReadCount() + bean.getCommentNoRead() + bean.getAlert();

        } else {
            mHome = 0;
        }
        // 工作台消息红点
        if (bean.getReport() > 0 || bean.getTask() > 0 || bean.getInspect() > 0) {
            mWork = bean.getReport() + bean.getTask() + bean.getInspect();
        } else {
            mWork = 0;
        }
        //桌面app红点
        mTotalCount = mHome + mAllCount + mWork;
        badgeView(R.id.tab_home, mHome);
        badgeView(R.id.tab_contact, mAllCount);
        badgeView(R.id.tab_work, mWork);
        // 首页红点
        // 桌面气泡赋值
        BadgeUtil.setBadgeCount(MainActivity.this, mTotalCount, R.drawable.client_logo);

    }

    /**
     * 网络请求回调
     *
     * @param object object
     */
    private void handlerValue(Object object) {
        if (object instanceof RongTokenBean) {
            ClientApplication.get().set(EanfangConst.STR.RONG_YUN_TOKEN, ((RongTokenBean) object).getToken());
            ClientApplication.connect(((RongTokenBean) object).getToken());
            return;
        }
        if (object instanceof BaseDataBean) {
            BaseDataKit.get().put((BaseDataBean) object);
            //兼容老版本
            ClientApplication.get().set(BaseDataBean.class.getName(), object);
            return;
        }

        if (object instanceof ConstAllBean) {
            ConstDataKit.get().put((ConstAllBean) object);
            //兼容老版本
            ClientApplication.get().set(ConstAllBean.class.getName(), object);
            return;
        }
    }


    private void initFragment() {
        // findViewById(android.R.id.tabhost);
        mTabHost = binding.tabhost;

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

    private void initUpdate() {
        if (!CacheKit.get().getBool(IS_UPDATE_TAG, false)) {
            //app更新
            UpdateAppManager.update(this, false);
            CacheKit.get().put(IS_UPDATE_TAG, true, CacheMod.Memory);
        }
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
//        ReceiverInit.getInstance().inits(MainActivity.this, BaseApplication.get().getAccount().getMobile());
//        ReceiverInit.getInstance().inits(MainActivity.this, ClientApplication.get().getLoginBean().getAccount().getMobile());
    }

    private void initData() {
        mainViewModel.getBaseData(BaseDataKit.get().getMd5());
        mainViewModel.getConstData(ConstDataKit.get().getMd5());

    }

    private void initRongIM() {
        //融云登录
        if (StrUtil.isEmpty(ClientApplication.get().get(EanfangConst.STR.RONG_YUN_TOKEN, StrUtil.EMPTY))) {
            mainViewModel.getRongToken(ClientApplication.get().getUserId());
        } else {
            //如果有融云token 就直接登录
            ClientApplication.connect(ClientApplication.get().get(EanfangConst.STR.RONG_YUN_TOKEN, StrUtil.EMPTY));
        }

        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener(this, hashMap, transactionActivities,
                (id) -> mainViewModel.getGroupDetail(id, false),
                (id) -> mainViewModel.getAccountInfo(id)));

        RongIM.setConnectionStatusListener(connectionStatus -> {
            switch (connectionStatus) {
                //连接成功。
                case CONNECTED:
                    // Log.i("zzw", "--------------------连接成功");
                    //getIMUnreadMessageCount();
                    mStatus = "";
                    break;
                //断开连接。
                case DISCONNECTED:
                    //Log.i("zzw", "--------------------断开连接");
                    mStatus = "聊天服务器正在连接中...";
                    break;
                //连接中。
                case CONNECTING:
                    // Log.i("zzw", "--------------------链接中");
                    mStatus = "聊天服务器正在连接中...";
                    break;
                //网络不可用。
                case NETWORK_UNAVAILABLE:
                    // Log.i("zzw", "--------------------网络不可用");
                    mStatus = "";
                    break;
                //用户账户在其他设备登录，本机会被踢掉线
                case KICKED_OFFLINE_BY_OTHER_CLIENT:
                    // Log.i("zzw", "--------------------掉线");
                    mStatus = "用户账户在其他设备登录";
                    break;
                default:
                    break;
            }
        });

        // 向融云提供自己的头像和昵称  兼容老版本
        RongIM.setUserInfoProvider(s -> {
            String id = ClientApplication.get().getAccId().toString();
            AccountEntity account = ClientApplication.get().getLoginBean().getAccount();
            String nickName = account.getNickName();
            Uri avatar = ToolsKit.getOssUri(account.getAvatar());
            return new UserInfo(id, nickName, avatar);
        }, true);

        final Conversation.ConversationType[] conversationTypes = {
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE
        };

        RongIM.getInstance().addUnReadMessageCountChangedObserver((integer) -> {
            mContactNum = integer;
            int i = mContact + integer;
            int nums = mTotalCount + integer;
            badgeView(R.id.tab_contact, i);
            BadgeUtil.setBadgeCount(this, nums, R.drawable.client_logo);
        }, conversationTypes);

    }

    private void badgeView(int id, int number) {
        ControlToolView.getBadge(ClientApplication.get().getApplicationContext())
                .setTargetView(findViewById(id))
                .setBadgeNum(number)
                .badge();

    }

    private void getPushMessage(Intent intent) {
        Uri uri = intent.getData();
        int mType;
        if (uri != null) {
            if (!StrUtil.isEmpty(uri.getQueryParameter("type"))) {
                mType = Integer.parseInt(Objects.requireNonNull(uri.getQueryParameter("type")));
                if (mType == 2) {
                    // 打开messagelistactivity
                    startActivity(MessageListActivity.class);
                } else if (mType == 3) {
                    //打开systemnoticeactivity
                    startActivity(SystemNoticeActivity.class);
                } else if (mType == 4) {
                    //打开systemnoticeactivity
                    startActivity(OfficialListActivity.class);
                }
            }

        }
    }

    /**
     * 老版本兼容
     */
    @Deprecated
    public void setHeaders() {
        if (ClientApplication.get().getLoginBean() != null) {
            EanfangHttp.setToken(ClientApplication.get().getLoginBean().getToken());
        }
        EanfangHttp.setClient();
    }
}
