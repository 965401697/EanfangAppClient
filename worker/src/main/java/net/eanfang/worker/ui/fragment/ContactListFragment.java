package net.eanfang.worker.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GroupsBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.Var;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.my.MessageListActivity;
import net.eanfang.worker.ui.activity.im.MyFriendsListActivity;
import net.eanfang.worker.ui.activity.worksapce.SystemMessageActivity;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.model.UIConversation;
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

    private List<GroupsBean> groupsBeanList = new ArrayList<>();

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData(Bundle arguments) {
        initGroupInfo();
    }

    @Override
    protected void initView() {

        ConversationListFragment fragment = new ConversationListFragment();
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

        findViewById(R.id.ll_msg_list).setOnClickListener(v -> startActivity(new Intent(getActivity(), MessageListActivity.class)));

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
                        EanfangHttp.get(NewApiService.GET_PUSH_READ_ALL).execute(new EanfangCallback(getActivity(), false));
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
                Intent intent = new Intent();
                intent.setClass(getActivity(), MyFriendsListActivity.class);
                intent.putExtra("flag", 1);
                startActivity(intent);
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
                return true;
            }

            @Override
            public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
                return false;
            }

            @Override
            public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
                if (uiConversation.getConversationType().equals(Conversation.ConversationType.SYSTEM)) {
                    UserInfo userInfo = new UserInfo(uiConversation.getConversationTargetId(), uiConversation.getUIConversationTitle(), uiConversation.getIconUrl());
                    Intent intent = new Intent(getActivity(), SystemMessageActivity.class);
                    intent.putExtra("sendUserInfo", userInfo);
                    startActivity(intent);
                    return true;
                } else {
                    return false;
                }
            }
        });

        /**
         * 获取群组头像和名称
         */
        //提供融云的头像和昵称
        RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
            @Override
            public Group getGroupInfo(String s) {
                for (GroupsBean b : groupsBeanList) {
                    if (s.equals(b.getRcloudGroupId())) {
                        Group group = new Group(b.getRcloudGroupId(), b.getGroupName(), Uri.parse("https://imgcache.cjmx.com/star/201512/20151207142700908.jpg"));

                        RongIM.getInstance().refreshGroupInfoCache(group);

                        return group;
                    }
                }
                return null;
            }

        }, true);

    }

    private void initGroupInfo() {
        EanfangHttp.post(UserApi.POST_GET_GROUP)
                .params("accId", EanfangApplication.get().getAccId())
                .execute(new EanfangCallback<GroupsBean>(getActivity(), false, GroupsBean.class, true, (list) -> {
                    if (list.size() > 0) {
                        groupsBeanList.addAll(list);
                        for (int i = 0; i < list.size(); i++) {
                            GroupsBean groupsBean = (GroupsBean) list.get(i);
                            EanfangApplication.get().set(groupsBean.getRcloudGroupId(), groupsBean.getGroupId());
                        }
                    }
                }));
    }

    @Override
    protected void setListener() {

    }
}
