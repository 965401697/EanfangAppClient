package net.eanfang.worker.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GroupsBean;
import com.eanfang.model.device.User;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.Var;
import com.facebook.common.internal.Sets;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.im.MorePopWindow;
import net.eanfang.worker.ui.activity.im.MyConversationListFragment;
import net.eanfang.worker.ui.activity.my.MessageListActivity;
import net.eanfang.worker.ui.activity.im.SystemMessageActivity;
import net.eanfang.worker.ui.activity.worksapce.notice.SystemNoticeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by MrHou
 *
 * @on 2018/3/2  16:53
 * @email houzhongzhou@yeah.net
 * @desc 消息
 */

public class ContactListFragment extends BaseFragment {

    private boolean isFrist = true;
    private List<String> invalidList = new ArrayList<>();//无效的会话id
    private Set<String> conversationsId = Sets.newHashSet();

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {


        MyConversationListFragment fragment = new MyConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话，该会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话，该会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                .build();
        fragment.setUri(uri);  //设置 ConverssationListFragment 的显示属性

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
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


        findViewById(R.id.ll_msg_list).setOnClickListener(v -> startActivity(new Intent(getActivity(), MessageListActivity.class)));

        findViewById(R.id.ll_system_notice).setOnClickListener(v -> startActivity(new Intent(getActivity(), SystemNoticeActivity.class)));


        if (Var.get("ContactListFragment.messageCount").getVar() > 0) {
            ((TextView) findViewById(R.id.tv_bus_msg_info)).setText("新订单消息");
        } else {
            ((TextView) findViewById(R.id.tv_bus_msg_info)).setText("没有新消息");
        }

        Badge qBadgeView = new QBadgeView(getActivity())
                .bindTarget(findViewById(R.id.tv_bus_msg))
                .setBadgeNumber(Var.get("ContactListFragment.messageCount").getVar())
                .setBadgePadding(2, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(0, 0, true)
                .setBadgeTextSize(11, true)
                .setOnDragStateChangedListener((dragState, badge, targetView) -> {
                    //清除成功
                    if (dragState == Badge.OnDragStateChangedListener.STATE_SUCCEED) {
                        EanfangHttp.get(NewApiService.GET_PUSH_READ_ALL).execute(new EanfangCallback(getActivity(), true, JSONObject.class));
                        showToast("消息被清空了");
//                        Var.get().setVar(0);
                    }
                });
//        变量监听
        Var.get("ContactListFragment.messageCount").setChangeListener((var) -> {
            getActivity().runOnUiThread(() -> {
                qBadgeView.setBadgeNumber(var);
            });
        });

        findViewById(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), SelectedFriendsActivity.class);
//                intent.putExtra("flag", 1);
//                startActivity(intent);

                MorePopWindow morePopWindow = new MorePopWindow(getActivity());
                morePopWindow.showPopupWindow(findViewById(R.id.iv_add));
            }
        });


/**
 * 设置会话列表界面操作的监听器。
 */
        RongIM.setConversationListBehaviorListener(new RongIM.ConversationListBehaviorListener() {

            @Override
            public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
                return true;
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

        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                setUserInfo(s);
                return null;
            }
        }, true);
        /**
         * 获取群组头像和名称
         */
        //提供融云的头像和昵称
        RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
            @Override
            public Group getGroupInfo(String s) {

                setGroupInfo();
                return null;
            }
        }, true);

    }

    /**
     * 设置群组信息提供者
     */
    private void setGroupInfo() {
        EanfangHttp.post(UserApi.POST_GET_GROUP)
                .params("accId", EanfangApplication.get().getAccId())
                .execute(new EanfangCallback<GroupsBean>(getActivity(), false, GroupsBean.class, true, (list) -> {

                    for (GroupsBean b : list) {

                        Group group = new Group(b.getRcloudGroupId(), b.getGroupName(), Uri.parse(BuildConfig.OSS_SERVER + b.getHeadPortrait()));

                        RongIM.getInstance().refreshGroupInfoCache(group);

                        EanfangApplication.get().set(b.getRcloudGroupId(), b.getGroupId());

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
                    UserInfo userInfo = new UserInfo(bean.getAccId(), bean.getNickName(), Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + bean.getAvatar()));

                    RongIM.getInstance().refreshUserInfoCache(userInfo);
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

    @Override
    protected void setListener() {

    }
}
