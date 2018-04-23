package net.eanfang.worker.ui.activity.im;

import android.content.Intent;
import android.net.Uri;
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
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.model.GroupCreatBean;
import com.eanfang.model.GroupDetailBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.GroupsDetailAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URI;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;

public class GroupDetailActivity extends BaseActivityWithTakePhoto {

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
    @BindView(R.id.group_transfer)
    LinearLayout group_transfer;
    @BindView(R.id.sw_group_top)
    TextView swGroupTop;
    @BindView(R.id.sw_group_notfaction)
    TextView swGroupNotfaction;

    private GroupsDetailAdapter mGroupsDetailAdapter;
    private ArrayList<FriendListBean> friendListBeanArrayList = new ArrayList<>();
    private String title;
    private String headPortrait;
    private String id;
    private String groupId;
    private boolean isChecked;
    private boolean isOwner;//是不是群主
    private final int HEADER_PIC = 107;
    private String imgKey;

    private final int UPDATA_NAME_REQEST = 101;
    private final int UPDATA_GROUP_OWN = 102;//转让群主

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

//        startTransaction(true);

        getConversationNotificationStatus(groupId);
    }

    private void initData() {

        EanfangHttp.post(UserApi.POST_GROUP_DETAIL)
                .params("groupId", id)
                .execute(new EanfangCallback<GroupDetailBean>(this, true, GroupDetailBean.class, (bean) -> {
                    if (bean.getList() != null) {
                        ArrayList<FriendListBean> temp = new ArrayList<>();
                        for (int i = 0; i < bean.getList().size(); i++) {
                            if (bean.getList().get(i).getAccId().equals(bean.getGroup().getCreateUser())) {
                                temp.add(0, bean.getList().get(i));
                            } else {
                                temp.add(bean.getList().get(i));
                            }
                        }


                        if (bean.getList().size() > 0) {
                            friendListBeanArrayList.addAll(bean.getList());
                            mGroupsDetailAdapter.setNewData(temp);
                            FriendListBean b = new FriendListBean();//填充数据
                            mGroupsDetailAdapter.addData(b);
                            mGroupsDetailAdapter.addData(b);

                            groupMemberSize.setText("全部群成员（" + friendListBeanArrayList.size() + "）");
                        }
                    }

                    if (bean.getGroup() != null) {
                        groupName.setText(bean.getGroup().getGroupName());
                        groupHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + bean.getGroup().getHeadPortrait()));

                        title = bean.getGroup().getGroupName();
                        headPortrait = bean.getGroup().getHeadPortrait();

                        if (String.valueOf(EanfangApplication.getApplication().getAccId()).equals(bean.getGroup().getCreateUser())) {
                            isOwner = true;
                            groupQuit.setText("解散并退出");
                            group_transfer.setVisibility(View.VISIBLE);
                        } else {
                            isOwner = false;
                            groupQuit.setText("删除并退出");
                            group_transfer.setVisibility(View.GONE);
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


    @OnClick({R.id.ll_group_port, R.id.ll_group_name, R.id.group_announcement, R.id.group_clean, R.id.sw_group_top, R.id.sw_group_notfaction, R.id.group_transfer, R.id.group_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_group_port:
                PermissionUtils.get(this).getCameraPermission(() -> takePhoto(GroupDetailActivity.this, HEADER_PIC));
                break;
            case R.id.ll_group_name:
                Intent intent = new Intent(this, GroupUpdataNameActivity.class);
                startActivityForResult(intent, UPDATA_NAME_REQEST);
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
            case R.id.group_transfer://群主转让
                Intent i = new Intent(GroupDetailActivity.this, MyFriendsListActivity.class);
                i.putExtra("flag", 4);
                i.putExtra("groupId", id);
                startActivityForResult(i, UPDATA_GROUP_OWN);
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
//                                endTransaction(true);
                            }));
                } else {
                    EanfangHttp.post(UserApi.POST_GROUP_QUIT)
                            .params("groupId", id)
                            .params("ids", EanfangApplication.getApplication().getAccId())
                            .params("groupName", title)
                            .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                                ToastUtil.get().showToast(GroupDetailActivity.this, "退出成功");
                                quitGroup();
//                                endTransaction(true);
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

    /**
     * 更新群组信息
     */
    public void updataGroupInfo(String title, String imgKey, String transfer) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("groupId", id);

            if (!TextUtils.isEmpty(title)) {
                jsonObject.put("groupName", title);
            }
            if (!TextUtils.isEmpty(imgKey)) {
                jsonObject.put("headPortrait", imgKey);
            }
            if (!TextUtils.isEmpty(transfer)) {
                jsonObject.put("create_user", transfer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //创建群组
        EanfangHttp.post(UserApi.POST_UPDATA_GROUP)
                .upJson(jsonObject)
                .execute(new EanfangCallback<JSONObject>(GroupDetailActivity.this, true, JSONObject.class, (JSONObject) -> {
                    ToastUtil.get().showToast(GroupDetailActivity.this, "修改成功");

                    Group groupInfo = new Group(groupId, title, Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + imgKey));
                    RongIM.getInstance().refreshGroupInfoCache(groupInfo);

                }));
    }

    /**
     * 群组的状态
     *
     * @param targetId
     */
    public void getConversationNotificationStatus(String targetId) {
        RongIM.getInstance().getConversationNotificationStatus(Conversation.ConversationType.GROUP, groupId, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
            @Override
            public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                conversationNotificationStatus.getValue();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });

    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);

        if (result == null || result.getImage() == null) {
            return;
        }
        TImage image = result.getImage();
        imgKey = UuidUtil.getUUID() + ".png";

        groupHeader.setImageURI("file://" + image.getOriginalPath());

        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
            @Override
            public void onOssSuccess() {
                super.onOssSuccess();
                updataGroupInfo(title, imgKey, "");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == UPDATA_NAME_REQEST) {
                String title = data.getStringExtra("updata_Name").toString();
                updataGroupInfo(title, headPortrait, "");
                groupName.setText(title);
                Group groupInfo = new Group(groupId, title, Uri.parse(BuildConfig.OSS_SERVER + headPortrait));
                RongIM.getInstance().refreshGroupInfoCache(groupInfo);
            } else if (requestCode == UPDATA_GROUP_OWN) {
                initData();
            }
        }
    }
}
