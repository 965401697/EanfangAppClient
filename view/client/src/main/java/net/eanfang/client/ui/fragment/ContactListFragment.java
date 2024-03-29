package net.eanfang.client.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseFragment;
import com.eanfang.biz.model.bean.AllMessageBean;
import com.eanfang.biz.model.bean.GroupDetailBean;
import com.eanfang.biz.model.bean.GroupsBean;
import com.eanfang.biz.model.bean.device.User;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.NetBroadcastReceiver;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.MainActivity;
import net.eanfang.client.ui.activity.im.MorePopWindow;
import net.eanfang.client.ui.activity.im.MyConversationListFragment;
import net.eanfang.client.ui.activity.im.SystemMessageActivity;
import net.eanfang.client.ui.activity.worksapce.notice.MessageNotificationActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import cn.hutool.core.util.StrUtil;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;
import q.rorbin.badgeview.QBadgeView;

import static android.app.Activity.RESULT_CANCELED;

/**
 * Created by MrHou
 *
 * @on 2018/3/1  16:23
 * @email houzhongzhou@yeah.net
 * @desc 消息
 */

public class ContactListFragment extends BaseFragment {

    private boolean isFrist = true;
    private List<String> invalidList = new ArrayList<>();//无效的会话id
    private Set<String> conversationsId = new HashSet<>();

    private QBadgeView qBadgeViewAllMsg = new QBadgeView(ClientApplication.get().getApplicationContext());

    private MyConversationListFragment myConversationListFragment;
    private Uri uri;

    private final int REQUST_REFRESH_CODE = 101;
    /**
     * 融云链接状态
     */
    private TextView mContactStatus;
    /**
     * 有无网络
     */
    private boolean isNetWork = false;
    private NetBroadcastReceiver netBroadcastReceiver;
    /**
     * 融云状态
     */
    private String mStatus = "";

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_message, container, false);
            initView();
            setListener();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }

        NetBroadcastReceiver.setListener(this::onChangeListener);
        //Android 7.0以上需要动态注册
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //实例化IntentFilter对象
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            if (netBroadcastReceiver == null) {
                netBroadcastReceiver = new NetBroadcastReceiver();
                //注册广播接收
                getActivity().registerReceiver(netBroadcastReceiver, filter);
            }
        }
        return mRootView;
    }

    protected void initView() {
        mContactStatus = findViewById(R.id.tv_contact_status);
        qBadgeViewAllMsg.bindTarget(findViewById(R.id.ll_message))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(3, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(0, 0, true)
                .setBadgeTextSize(10, true);


        myConversationListFragment = new MyConversationListFragment();
        //设置私聊会话，该会话聚合显示
        //设置群组会话，该会话非聚合显示
        //系统
        uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话，该会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话，该会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                .build();
        myConversationListFragment.setUri(uri);  //设置 ConverssationListFragment 的显示属性

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, myConversationListFragment);
        transaction.commit();

        setGroupInfo();//对比数据

        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {

                if (conversations != null && conversations.size() > 0) {

                    for (Conversation s : conversations) {

                        Log.e("zzw", s.getTargetId());

                        conversationsId.add(s.getTargetId());
                    }
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        }, Conversation.ConversationType.GROUP);


        doHttpNoticeCount();

        /**
         * 设置会话列表界面操作的监听器。
         */
        RongIM.setConversationListBehaviorListener(new RongIM.ConversationListBehaviorListener() {
            @Override
            public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @Override
            public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @Override
            public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
                return false;
            }

            @Override
            public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
                if (uiConversation.getConversationType().equals(Conversation.ConversationType.SYSTEM)) {

                    if (uiConversation.getConversationContent().toString().equals("被删除通知")) {
                        RongIM.getInstance().clearMessagesUnreadStatus(Conversation.ConversationType.SYSTEM, uiConversation.getConversationTargetId());
                        return true;
                    } else if (uiConversation.getConversationContent().toString().equals("被移除群组通知")) {
                        RongIM.getInstance().clearMessagesUnreadStatus(Conversation.ConversationType.SYSTEM, uiConversation.getConversationTargetId());
                        return true;
                    } else if (uiConversation.getConversationContent().toString().equals("群解散通知")) {
                        RongIM.getInstance().clearMessagesUnreadStatus(Conversation.ConversationType.SYSTEM, uiConversation.getConversationTargetId());
                        return true;
                    } else if (uiConversation.getConversationContent().toString().equals("拒绝添加好友通知")) {
                        RongIM.getInstance().clearMessagesUnreadStatus(Conversation.ConversationType.SYSTEM, uiConversation.getConversationTargetId());
                        return true;
                    } else {
                        UserInfo userInfo = new UserInfo(uiConversation.getConversationTargetId(), uiConversation.getUIConversationTitle(), uiConversation.getIconUrl());
                        Intent intent = new Intent(getActivity(), SystemMessageActivity.class);
                        intent.putExtra("sendUserInfo", userInfo);
                        startActivity(intent);
                        return true;
                    }
                } else {
                    return false;
                }
            }
        });
        /**
         * 获取用户头像和名称
         */
        RongIM.setUserInfoProvider(s -> {
            //系统消息 也走这个  判断是个人的id 还是融云的群组id  设置系统消息的内容提供者
            if (isInteger(s)) {
                setUserInfo(s);
            } else {
                getActivity().runOnUiThread(() -> {
                    EanfangHttp.post(UserApi.POST_GROUP_DETAIL_RY)
                            .params("ryGroupId", s)
                            .execute(new EanfangCallback<GroupDetailBean>(getActivity(), true, GroupDetailBean.class, (bean) -> {
                                if (bean != null) {
                                    UserInfo userInfo = new UserInfo(s, bean.getGroup().getGroupName(), Uri.parse(BuildConfig.OSS_SERVER + bean.getGroup().getHeadPortrait()));
                                    RongIM.getInstance().refreshUserInfoCache(userInfo);
                                }

                            }));
                });

            }
            return null;
        }, true);
        /**
         * 获取群组头像和名称
         */
        //提供融云的头像和昵称
        RongIM.setGroupInfoProvider(s -> {

            setGroupInfo();
            return null;
        }, true);

    }

    @Override
    public void onResume() {
        super.onResume();
        String mStatus = ((MainActivity) getActivity()).onNoConatac();
        if (StrUtil.isEmpty(mStatus)) {
            findViewById(R.id.rl_no_contact).setVisibility(View.GONE);
        } else {
            findViewById(R.id.rl_no_contact).setVisibility(View.VISIBLE);
            mContactStatus.setText(mStatus);
        }
    }

    private void doHttpNoticeCount() {
        EanfangHttp.get(UserApi.ALL_MESSAGE)
                .execute(new EanfangCallback<AllMessageBean>(getActivity(), true, AllMessageBean.class, bean -> {
                    int messageNum = bean.getSys() + bean.getBiz() + bean.getCmp();
                    if (messageNum > 0) {
                        qBadgeViewAllMsg.setBadgeNumber(messageNum);
                    } else {
                        qBadgeViewAllMsg.setBadgeNumber(0);
                    }
                    /**
                     * 底部红点更新
                     * */
                    EventBus.getDefault().post(bean);

                }));
    }


    /**
     * 设置群组信息提供者
     */
    private void setGroupInfo() {
        EanfangHttp.post(UserApi.POST_GET_GROUP)
                .params("accId", ClientApplication.get().getAccId())
                .execute(new EanfangCallback<GroupsBean>(getActivity(), false, GroupsBean.class, true, (list) -> {

                    for (GroupsBean b : list) {

                        Group group = new Group(b.getRcloudGroupId(), b.getGroupName(), Uri.parse(BuildConfig.OSS_SERVER + b.getHeadPortrait()));

                        RongIM.getInstance().refreshGroupInfoCache(group);

                        ClientApplication.get().set(b.getRcloudGroupId(), b.getGroupId());

                    }

                    if (isFrist) {
                        delectInvalidGroup(list);
                        isFrist = false;
                    }
                }));

    }

    /***
     * 设置个人信息内容提供者
     */
    private void setUserInfo(String s) {

        if (TextUtils.isEmpty(s)) return;


        EanfangHttp.get(UserApi.POST_USER_INFO + s)
                .execute(new EanfangCallback<User>(getActivity(), false, User.class, (bean) -> {
                    if (bean != null) {
                        UserInfo userInfo = new UserInfo(bean.getAccId(), bean.getNickName(), Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + bean.getAvatar()));

                        RongIM.getInstance().refreshUserInfoCache(userInfo);
                    }
                }));

    }

    /**
     * 删除我的群组里没有的会话列表
     */

    private void delectInvalidGroup(List<GroupsBean> list) {

        if (conversationsId.size() == 0) return;


        if (list.size() == 0) {//我的群组为空  删除所有的群组会话
            for (String s : conversationsId) {
                RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, s, null);
            }
            return;
        }


        for (GroupsBean b : list) {

            if (!conversationsId.contains(b.getRcloudGroupId())) {
                invalidList.add(b.getRcloudGroupId());
            }

            if (invalidList.size() > 0) {
                for (String s : invalidList) {
                    RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, s, null);
                }
            }

        }
    }


    protected void setListener() {
        findViewById(R.id.iv_add).setOnClickListener(v -> {
            MorePopWindow morePopWindow = new MorePopWindow(getActivity(), false);
            morePopWindow.showPopupWindow(findViewById(R.id.iv_add));
        });
        findViewById(R.id.iv_message).setOnClickListener(v -> {
            JumpItent.jump(getActivity(), MessageNotificationActivity.class, REQUST_REFRESH_CODE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED && requestCode == REQUST_REFRESH_CODE) {
            doHttpNoticeCount();
        }
    }

    /**
     * 判断字符串是不是数字
     *
     * @param str
     * @return
     */

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public void onChangeListener(boolean status) {
        this.isNetWork = status;
        if (isNetConnect()) {
            if (StrUtil.isEmpty(mStatus)) {
                findViewById(R.id.rl_no_contact).setVisibility(View.GONE);
            } else {
                findViewById(R.id.rl_no_contact).setVisibility(View.VISIBLE);
            }
        } else {
            findViewById(R.id.rl_no_contact).setVisibility(View.VISIBLE);
            mContactStatus.setText("当前网络不可用，请检查网络设置");
        }
    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        return isNetWork;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() != null && getActivity().isRestricted()) {
            getActivity().unregisterReceiver(netBroadcastReceiver);
        }
    }
}
