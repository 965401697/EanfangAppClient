package net.eanfang.worker.ui.activity.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.model.GroupDetailBean;
import com.eanfang.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.GroupsDetailAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class GroupDetailActivity extends BaseWorkerActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.group_member_size)
    TextView groupMemberSize;
    @BindView(R.id.group_header)
    SimpleDraweeView groupHeader;
    @BindView(R.id.group_name)
    TextView groupName;
    @BindView(R.id.group_quit)
    Button groupQuit;
    @BindView(R.id.group_member_size_item)
    RelativeLayout groupMemberSizeItem;
    @BindView(R.id.ll_group_port)
    LinearLayout llGroupPort;
    @BindView(R.id.sw_group_top)
    TextView swGroupTop;
    @BindView(R.id.sw_group_notfaction)
    TextView swGroupNotfaction;

    private GroupsDetailAdapter mGroupsDetailAdapter;
    private ArrayList<FriendListBean> friendListBeanArrayList = new ArrayList<>();
    private String title;
    private String id;
    private String groupId;
    private boolean isChecked;
    private boolean isOwner;//是不是群主

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        ButterKnife.bind(this);
        setTitle("群组信息");
        setLeftBack();
        groupId = getIntent().getStringExtra("rongyun_group_id");
        id = EanfangApplication.getApplication().get().get(groupId, 0);
        title = getIntent().getStringExtra("title");
        initViews();
        initData();
        startTransaction(true);
    }

    private void initData() {

        EanfangHttp.post(UserApi.POST_GROUP_DETAIL)
                .params("groupId", id)
                .execute(new EanfangCallback<GroupDetailBean>(this, true, GroupDetailBean.class, (bean) -> {
                    if (bean.getList() != null) {

                        for (int i = 0; i < bean.getList().size(); i++) {
                            if (bean.getList().get(i).equals(bean.getGroup().getCreateUser())) {
                                bean.getList().set(0, bean.getList().get(i));
                                break;
                            }
                        }


                        if (bean.getList().size() > 0) {
                            friendListBeanArrayList.addAll(bean.getList());
                            mGroupsDetailAdapter.setNewData(bean.getList());
                            FriendListBean b = new FriendListBean();//填充数据
                            mGroupsDetailAdapter.addData(b);
                            mGroupsDetailAdapter.addData(b);

                            groupMemberSize.setText("全部群成员（" + friendListBeanArrayList.size() + "）");
                        }
                    }

                    if (bean.getGroup() != null) {
                        groupName.setText(bean.getGroup().getGroupName());
                        title = bean.getGroup().getGroupName();
//                        groupHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + bean.getGroup().get()));

                        if (String.valueOf(EanfangApplication.getApplication().getAccId()).equals(bean.getGroup().getCreateUser())) {
                            isOwner = true;
                            groupQuit.setText("解散并退出");
                        } else {
                            isOwner = false;
                            groupQuit.setText("删除并退出");
                        }
                    }
                }));
    }

    private void initViews() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mGroupsDetailAdapter = new GroupsDetailAdapter(R.layout.item_group);
        mGroupsDetailAdapter.bindToRecyclerView(recyclerView);
        mGroupsDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == adapter.getData().size() - 2) {
                    Intent intent = new Intent(GroupDetailActivity.this, MyFriendsListActivity.class);
                    intent.putExtra("list", friendListBeanArrayList);
                    intent.putExtra("groupId", id);
                    intent.putExtra("title", title);
                    intent.putExtra("flag", 2);
                    startActivity(intent);
                } else if (position == adapter.getData().size() - 1) {
                    Intent intent = new Intent(GroupDetailActivity.this, MyFriendsListActivity.class);
                    intent.putExtra("flag", 3);
                    intent.putExtra("groupId", id);
                    intent.putExtra("title", title);
                    startActivity(intent);
                }
            }
        });
    }


    @OnClick({R.id.ll_group_port, R.id.ll_group_name, R.id.group_announcement, R.id.group_clean, R.id.sw_group_top, R.id.sw_group_notfaction, R.id.group_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_group_port:
                break;
            case R.id.ll_group_name:
                break;
            case R.id.group_announcement:
                Intent tempIntent = new Intent(this, GroupNoticeActivity.class);
                tempIntent.putExtra("conversationType", Conversation.ConversationType.GROUP.getValue());
                tempIntent.putExtra("targetId", groupId);
                startActivity(tempIntent);
                break;
            case R.id.group_clean:
                cleanGroupMsg();
                break;
            case R.id.sw_group_top:
                if (isChecked) {
                    swGroupTop.setText("置顶群组");
                    setConversationTop(Conversation.ConversationType.GROUP, groupId, true);
                    isChecked = false;
                } else {
                    swGroupTop.setText("取消置顶");
                    setConversationTop(Conversation.ConversationType.GROUP, groupId, false);
                    isChecked = true;
                }

                break;
            case R.id.sw_group_notfaction:
                if (isChecked) {
                    swGroupNotfaction.setText("开启免打扰");
                    setConverstionNotif(Conversation.ConversationType.GROUP, groupId, true);
                    isChecked = false;
                } else {
                    setConverstionNotif(Conversation.ConversationType.GROUP, groupId, false);
                    swGroupNotfaction.setText("关闭免打扰");
                    isChecked = true;
                }

                break;
            case R.id.group_quit:
                if (isOwner) {
                    //我是群主
                    EanfangHttp.post(UserApi.POST_GROUP_DELETE)
                            .params("groupId", id)
                            .params("ids", EanfangApplication.getApplication().getAccId())
                            .params("groupName", title)
                            .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                                ToastUtil.get().showToast(GroupDetailActivity.this, "销毁成功");
                                quitGroup();
                                endTransaction(true);
                            }));
                } else {
                    EanfangHttp.post(UserApi.POST_GROUP_QUIT)
                            .params("groupId", id)
                            .params("ids", EanfangApplication.getApplication().getAccId())
                            .params("groupName", title)
                            .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                                ToastUtil.get().showToast(GroupDetailActivity.this, "退出成功");
                                quitGroup();
                                endTransaction(true);
                            }));
                }
                break;
        }
    }

    private void quitGroup() {
        RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, groupId, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    /**
     * 清空聊天记录
     */
    private void cleanGroupMsg() {

        if (RongIM.getInstance() != null) {

            RongIM.getInstance().clearMessages(Conversation.ConversationType.GROUP, groupId, new RongIMClient.ResultCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    ToastUtil.get().showToast(GroupDetailActivity.this, "清除成功");
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    ToastUtil.get().showToast(GroupDetailActivity.this, "清除失败");
                }
            });
            ;
            RongIMClient.getInstance().cleanRemoteHistoryMessages(Conversation.ConversationType.GROUP, groupId, System.currentTimeMillis(), null);

        }


    }

    /**
     * 会话置顶
     *
     * @param context
     * @param conversationType
     * @param targetId
     * @param state
     */
    public void setConversationTop(Conversation.ConversationType conversationType, String targetId, boolean state) {
        if (!TextUtils.isEmpty(targetId) && RongIM.getInstance() != null) {
            RongIM.getInstance().setConversationToTop(conversationType, targetId, state, new RongIMClient.ResultCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    ToastUtil.get().showToast(GroupDetailActivity.this, "设置成功");
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    ToastUtil.get().showToast(GroupDetailActivity.this, "设置失败");
                }
            });
        }
    }


    /**
     * 消息免打扰
     */
    public void setConverstionNotif(Conversation.ConversationType conversationType, String targetId, boolean state) {
        Conversation.ConversationNotificationStatus cns;
        if (state) {
            cns = Conversation.ConversationNotificationStatus.DO_NOT_DISTURB;
        } else {
            cns = Conversation.ConversationNotificationStatus.NOTIFY;
        }
        RongIM.getInstance().setConversationNotificationStatus(conversationType, targetId, cns, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
            @Override
            public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                if (conversationNotificationStatus == Conversation.ConversationNotificationStatus.DO_NOT_DISTURB) {
                    ToastUtil.get().showToast(GroupDetailActivity.this, "设置免打扰成功");
                } else if (conversationNotificationStatus == Conversation.ConversationNotificationStatus.NOTIFY) {
                    ToastUtil.get().showToast(GroupDetailActivity.this, "取消免打扰成功");
                }

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

}
