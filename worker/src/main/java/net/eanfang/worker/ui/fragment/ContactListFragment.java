package net.eanfang.worker.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GroupsBean;
import com.eanfang.model.device.User;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JumpItent;
import com.facebook.common.internal.Sets;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.MainActivity;
import net.eanfang.worker.ui.activity.im.GroupDetailActivity;
import net.eanfang.worker.ui.activity.im.IMPresonInfoActivity;
import net.eanfang.worker.ui.activity.im.MorePopWindow;
import net.eanfang.worker.ui.activity.im.MyConversationListFragment;
import net.eanfang.worker.ui.activity.im.SystemMessageActivity;
import net.eanfang.worker.ui.activity.worksapce.notice.MessageListActivity;
import net.eanfang.worker.ui.activity.worksapce.notice.OfficialListActivity;
import net.eanfang.worker.ui.activity.worksapce.notice.SystemNoticeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;
import q.rorbin.badgeview.QBadgeView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by MrHou
 *
 * @on 2018/3/1  16:23
 * @email houzhongzhou@yeah.net
 * @desc 消息
 */

public class ContactListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private boolean isFrist = true;
    private List<String> invalidList = new ArrayList<>();//无效的会话id
    private Set<String> conversationsId = Sets.newHashSet();

    private QBadgeView qBadgeViewSys = new QBadgeView(EanfangApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewBiz = new QBadgeView(EanfangApplication.get().getApplicationContext());

    // 消息数量
    private int mMessageCount = 0;
    private int mStystemCount = 0;

    private View view;
    private MyConversationListFragment myConversationListFragment;
    private Uri uri;
    private final int REQUST_REFRESH_CODE = 101;
    private FragmentTransaction transaction;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData(Bundle arguments) {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //防止刷新
//        if (view == null) {
        view = inflater.inflate(R.layout.fragment_message, container, false);
        initView();
        setListener();
//            doHttpNoticeCount();
//        } else {
//            initView();
//        }
//        ViewGroup parent = (ViewGroup) view.getParent();
//        if (parent != null) {
//            parent.removeView(view);
//        }

        return view;
    }

    @Override
    protected void initView() {

//        ((android.support.v4.widget.SwipeRefreshLayout) view.findViewById(R.id.swipre_fresh)).setOnRefreshListener(this);

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

        getActivity().getSupportFragmentManager().getFragments();

        transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction = this.getChildFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, myConversationListFragment);
        transaction.commit();

//        RelativeLayout relativeLayout = (RelativeLayout) myConversationListFragment.getmView();
//        ConversationListFragment conversationListFragment = ((ContactListFragment) getActivity().getSupportFragmentManager().getFragments().get(0)).getMyConversationListFragment();
//        RelativeLayout relativeLayout = (RelativeLayout) conversationListFragment.getView();
//        if (relativeLayout != null) {
//            Log.e("zzw", relativeLayout.getChildCount() + "");
//            Log.e("zzw", relativeLayout.getChildAt(2).getId() + "");
//        }

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
                if (conversationType.equals(Conversation.ConversationType.PRIVATE)) {
                    Intent intent = new Intent(getActivity(), IMPresonInfoActivity.class);
                    intent.putExtra(EanfangConst.RONG_YUN_ID, s);
                    startActivity(intent);
                } else if (conversationType.equals(Conversation.ConversationType.GROUP)) {
                    Intent intent = new Intent(getActivity(), GroupDetailActivity.class);
                    intent.putExtra(EanfangConst.RONG_YUN_ID, s);
                    startActivity(intent);
                }
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RelativeLayout relativeLayout = (RelativeLayout) myConversationListFragment.getmView();
    }


    public MyConversationListFragment getMyConversationListFragment() {
        return myConversationListFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
//        doHttpNoticeCount();
//        if (myConversationListFragment != null && uri != null)
//            myConversationListFragment.setUri(uri);
//
        ((MainActivity) getActivity()).getIMUnreadMessageCount();
    }

    private void doHttpNoticeCount() {

//        ((android.support.v4.widget.SwipeRefreshLayout) view.findViewById(R.id.swipre_fresh)).setRefreshing(true);

        EanfangHttp.get(NewApiService.GET_PUSH_COUNT)
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class) {


                    @Override
                    public void onSuccess(JSONObject bean) {
                        super.onSuccess(bean);

//                        ((android.support.v4.widget.SwipeRefreshLayout) view.findViewById(R.id.swipre_fresh)).setRefreshing(false);


                        if (bean.containsKey("sys")) {
                            initSysCount(bean.getInteger("sys"));
                            mStystemCount = bean.getInteger("sys");
                        } else {
                            initSysCount(0);
                            mStystemCount = 0;
                        }
                        if (bean.containsKey("biz")) {
                            initBizCount(bean.getInteger("biz"));
                            mMessageCount = bean.getInteger("biz");
                        } else {
                            initBizCount(0);
                            mMessageCount = 0;
                        }

                    }


                    @Override
                    public void onFail(Integer code, String message, JSONObject jsonObject) {
                        super.onFail(code, message, jsonObject);
//                        ((android.support.v4.widget.SwipeRefreshLayout) view.findViewById(R.id.swipre_fresh)).setRefreshing(false);
                    }
                });
    }

    private void initBizCount(Integer biz) {

        if (biz > 0) {
            ((TextView) view.findViewById(R.id.tv_bus_msg_info)).setText("新消息");
        } else {
            ((TextView) view.findViewById(R.id.tv_bus_msg_info)).setText("没有新消息");
        }


        qBadgeViewBiz.bindTarget(view.findViewById(R.id.tv_bus_msg))
                .setBadgeNumber(biz)
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(2, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(0, 0, true)
                .setBadgeTextSize(11, true);

//        if (biz <= 0) {
//            qBadgeViewSys.setVisibility(View.GONE);
//        }
//                .setOnDragStateChangedListener((dragState, badge, targetView) -> {
//                    //清除成功
//                    if (dragState == Badge.OnDragStateChangedListener.STATE_SUCCEED) {
//                        EanfangHttp.get(NewApiService.GET_PUSH_READ_ALL).execute(new EanfangCallback(getActivity(), false, JSONObject.class));
//                        showToast("消息被清空了");
////                        Var.get().setVar(0);
//                    }
//                });
    }

    private void initSysCount(Integer sys) {
        if (sys > 0) {
            ((TextView) view.findViewById(R.id.tv_sys_msg_info)).setText("新消息");
        } else {
            ((TextView) view.findViewById(R.id.tv_sys_msg_info)).setText("没有新消息");
        }

        qBadgeViewSys
                .bindTarget(view.findViewById(R.id.tv_sys_msg))
                .setBadgeNumber(sys)
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(2, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(0, 0, true)
                .setBadgeTextSize(11, true);


//                .setOnDragStateChangedListener((dragState, badge, targetView) -> {
//                    //清除成功
//                    if (dragState == Badge.OnDragStateChangedListener.STATE_SUCCEED) {
//                        EanfangHttp.get(NewApiService.GET_PUSH_READ_ALL).execute(new EanfangCallback(getActivity(), false, JSONObject.class));
//                        showToast("消息被清空了");
////                        Var.get().setVar(0);
//                    }
//                });
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


    @Override
    protected void setListener() {
        // 官方通知
        view.findViewById(R.id.ll_official).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
//            bundle.putInt("mMessageCount", mMessageCount);
            JumpItent.jump(getActivity(), OfficialListActivity.class, bundle, REQUST_REFRESH_CODE);
        });       // 业务通知
        view.findViewById(R.id.ll_msg_list).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("mMessageCount", mMessageCount);
            JumpItent.jump(getActivity(), MessageListActivity.class, bundle, REQUST_REFRESH_CODE);
        });
        // 系统消息
        view.findViewById(R.id.ll_system_notice).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("mStystemCount", mStystemCount);
            JumpItent.jump(getActivity(), SystemNoticeActivity.class, bundle, REQUST_REFRESH_CODE);
        });
        view.findViewById(R.id.iv_add).setOnClickListener(v -> {
            MorePopWindow morePopWindow = new MorePopWindow(getActivity(), false);
            morePopWindow.showPopupWindow(view.findViewById(R.id.iv_add));
        });
    }

    @Override
    public void onRefresh() {
//        doHttpNoticeCount();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUST_REFRESH_CODE) {
            doHttpNoticeCount();
        }
    }
}
