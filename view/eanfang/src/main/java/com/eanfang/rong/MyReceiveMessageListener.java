package com.eanfang.rong;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.function.Function;
import com.eanfang.biz.model.bean.GroupDetailBean;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.kit.ToolsKit;

import java.util.ArrayList;
import java.util.HashMap;

import cn.hutool.core.bean.BeanUtil;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;

/**
 * 融云消息监听
 */
public class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {
    private Activity activity;
    private HashMap<String, String> hashMap;
    private ArrayList<Activity> transactionActivities;
    private Function<String, MutableLiveData<GroupDetailBean>> groupFunction;
    private Function<String, MutableLiveData<AccountEntity>> accountFunction;


    public MyReceiveMessageListener(Activity activity, HashMap<String, String> hashMap, ArrayList<Activity> transactionActivities,
                                    Function<String, MutableLiveData<GroupDetailBean>> groupFunction, Function<String, MutableLiveData<AccountEntity>> accountFunction) {
        this.activity = activity;
        this.hashMap = hashMap;
        this.transactionActivities = transactionActivities;
        this.groupFunction = groupFunction;
        this.accountFunction = accountFunction;
    }

    /**
     * 收到消息的处理。
     *
     * @param message 收到的消息实体。
     * @param left    剩余未拉取消息数目。
     * @return 收到消息是否处理完成，true 表示自己处理铃声和后台通知，false 走融云默认处理方式。
     */
    @Override
    public boolean onReceived(Message message, int left) {

        /**
         * 获取消息页面回话列表数量   看似好像没用
         * */
//        RongIM.getInstance().getTotalUnreadCount(new RongIMClient.ResultCallback<Integer>() {
//            @Override
//            public void onSuccess(Integer integer) {
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//
//            }
//        });

        //开发者根据自己需求自行处理
        boolean isDelect = false;


        if (message.getConversationType().getName().equals(Conversation.ConversationType.SYSTEM.getName())) {
            TextMessage messageContent = (TextMessage) message.getContent();
            if (messageContent.getContent().equals("被删除通知")) {
                closeActivity(message);
            } else if (messageContent.getContent().equals("群解散通知")) {
                String extra = messageContent.getExtra();
                JSONObject jsonObject = JSON.parseObject(extra);
                //删除好友的会话记录
                UserInfo userInfo = new UserInfo(
                        jsonObject.getString("groupId"),
                        jsonObject.getString("groupName"),
                        ToolsKit.getOssUri(jsonObject.getString("groupPic")));
                RongIM.getInstance().refreshUserInfoCache(userInfo);
                closeActivity(message);

            } else if (messageContent.getContent().equals("被移除群组通知")) {

                TextMessage textMessage = (TextMessage) message.getContent();
                String extra = textMessage.getExtra();
                JSONObject object = (JSONObject) JSONObject.parse(extra);
                String id = (String) object.get("groupId");
                // 设置系统消息的内容提供者  要不然第一删除的时候  会话裂变不会显示群名称和图片
                groupFunction.apply(id).observe((LifecycleOwner) activity, (groupDetailBean -> {
                    UserInfo userInfo = new UserInfo(id, groupDetailBean.getGroup().getGroupName(), ToolsKit.getOssUri(groupDetailBean.getGroup().getHeadPortrait()));
                    RongIM.getInstance().refreshUserInfoCache(userInfo);
                }));

//                runOnUiThread(() -> {
//                    EanfangHttp.post(UserApi.POST_GROUP_DETAIL_RY)
//                            .params("ryGroupId", id)
//                            .execute(new EanfangCallback<GroupDetailBean>(MainActivity.this, true, GroupDetailBean.class, (bean) -> {
//
//
//                            }));
//
//                });
                closeActivity(message);

            } else if (messageContent.getContent().equals("好友邀请")) {
                hashMap.put(message.getTargetId(), message.getTargetId());
            } else if (messageContent.getContent().equals("拒绝添加好友通知")) {
                hashMap.put(message.getTargetId(), message.getTargetId());
            } else {
                //移除添加被删除的id
                if (hashMap.get(message.getTargetId()) != null) {
                    hashMap.remove(message.getTargetId());
                }

                accountFunction.apply(message.getTargetId()).observe((LifecycleOwner) activity, accountEntity -> {
                    UserInfo userInfo = new UserInfo(accountEntity.getAccId().toString(), accountEntity.getNickName(), ToolsKit.getOssUri(accountEntity.getAvatar()));
                    RongIM.getInstance().refreshUserInfoCache(userInfo);
                });
//                EanfangHttp.get(UserApi.POST_USER_INFO + message.getTargetId())
//                        .execute(new EanfangCallback<User>(MainActivity.this, false, User.class, (bean) -> {
//
//
//                        }));
            }
        }
        return isDelect;
    }

    /**
     * 关闭对应的 activity
     *
     * @param message message
     */
    private void closeActivity(Message message) {
        //删除好友的会话记录
        RongIM.getInstance().clearMessages(Conversation.ConversationType.PRIVATE, message.getTargetId(), null);
        RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, message.getTargetId(), null);
        hashMap.put(message.getTargetId(), message.getTargetId());
        for (Activity activity : transactionActivities) {
            if (activity.getClass().getName().contains("ConversationActivity")) {
                String mId = (String) BeanUtil.getFieldValue(activity, "mId");
                if (message.getTargetId().equals(mId)) {
                    activity.finish();
                }
            }
        }
    }
}
