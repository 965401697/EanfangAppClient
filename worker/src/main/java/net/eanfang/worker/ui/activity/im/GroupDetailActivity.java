package net.eanfang.worker.ui.activity.im;

import android.app.Activity;
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
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.model.GroupDetailBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.GroupsDetailAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;
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
    @BindView(R.id.ll_shut_up)
    LinearLayout ll_shut_up;
    @BindView(R.id.group_transfer)
    LinearLayout group_transfer;
    @BindView(R.id.sw_group_top)
    TextView swGroupTop;
    @BindView(R.id.group_shutup)
    TextView group_shutup;
    @BindView(R.id.group_notice)
    TextView group_notice;
    @BindView(R.id.sw_group_notfaction)
    TextView swGroupNotfaction;

    private GroupsDetailAdapter mGroupsDetailAdapter;
    private ArrayList<FriendListBean> friendListBeanArrayList = new ArrayList<>();
    private ArrayList<FriendListBean> mList = new ArrayList<>();
    private String title;
    private String headPortrait;
    private String id;
    private String groupId;
    private boolean isCheckedTop;//置顶
    private boolean isCheckedNo;//免打扰
    private boolean isCheckedGag;//禁言
    private boolean isOwner;//是不是群主
    private final int HEADER_PIC = 107;

    private final int UPDATA_GROUP_NAME = 101;//更新名字
    private final int UPDATA_GROUP_OWN = 102;//转让群主
    private final int UPDATA_GROUP_NOTICE = 103;//更新名字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        ButterKnife.bind(this);
        setTitle("群组信息");
        setLeftBack();
        groupId = getIntent().getStringExtra(EanfangConst.RONG_YUN_ID);
        id = EanfangApplication.getApplication().get().get(groupId, 0);
        title = getIntent().getStringExtra("title");
        BaseActivity.transactionActivities.add(this);
        initData();
    }

    private void initData() {

        EanfangHttp.post(UserApi.POST_GROUP_DETAIL)
                .params("groupId", id)
                .execute(new EanfangCallback<GroupDetailBean>(this, true, GroupDetailBean.class, (bean) -> {
                    mList = (ArrayList<FriendListBean>) bean.getList();

                    if (String.valueOf(EanfangApplication.getApplication().getAccId()).equals(bean.getGroup().getCreateUser())) {
                        isOwner = true;
                        groupQuit.setText("解散并退出");
                        group_transfer.setVisibility(View.VISIBLE);
                        ll_shut_up.setVisibility(View.VISIBLE);
                    } else {
                        isOwner = false;
                        groupQuit.setText("删除并退出");
                        group_transfer.setVisibility(View.GONE);
                        ll_shut_up.setVisibility(View.GONE);
                    }

                    initViews();

                    if (friendListBeanArrayList.size() > 0) friendListBeanArrayList.clear();
                    if (mGroupsDetailAdapter.getData().size() > 0)
                        mGroupsDetailAdapter.getData().clear();

                    if (bean.getList() != null) {
                        ArrayList<FriendListBean> temp = new ArrayList<>();
                        for (int i = 0; i < bean.getList().size(); i++) {
                            if (bean.getList().get(i).getAccId().equals(bean.getGroup().getCreateUser())) {
                                temp.add(0, bean.getList().get(i));
                            } else {
                                temp.add(bean.getList().get(i));
                                friendListBeanArrayList.add(bean.getList().get(i));
                            }
                        }


                        if (bean.getList().size() > 0) {

                            mGroupsDetailAdapter.setNewData(temp);
                            FriendListBean b = new FriendListBean();//填充数据
                            mGroupsDetailAdapter.addData(b);
                            mGroupsDetailAdapter.addData(b);

                            groupMemberSize.setText("全部群成员（" + bean.getList().size() + "）");


                            if (bean.getList().get(0).getStatus() == 0) {
                                group_shutup.setText("开启禁言");
                                isCheckedGag = false;
                            } else {
                                group_shutup.setText("关闭禁言");
                                isCheckedGag = true;
                            }
                        }
                    }

                    if (bean.getGroup() != null) {
                        groupName.setText(bean.getGroup().getGroupName());
                        groupHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + bean.getGroup().getHeadPortrait()));
                        if (!TextUtils.isEmpty(bean.getGroup().getNotice())) {
                            group_notice.setText(bean.getGroup().getNotice());
                        }
                        title = bean.getGroup().getGroupName();
                        headPortrait = bean.getGroup().getHeadPortrait();


                    }
                }));


        //获取群组的置顶状态
        Conversation conversation = RongIM.getInstance().getConversation(Conversation.ConversationType.GROUP, groupId);
        if (conversation != null) {
            boolean isTop = conversation.isTop();
            if (isTop) {
                isCheckedTop = true;
                swGroupTop.setText("取消置顶");
            } else {
                swGroupTop.setText("置顶群组");
                isCheckedTop = false;
            }

            //免打扰的状态值
//        DO_NOT_DISTURB(0),
//                NOTIFY(1);
            Conversation.ConversationNotificationStatus status = conversation.getNotificationStatus();
            if (status.equals(Conversation.ConversationNotificationStatus.NOTIFY)) {
                isCheckedNo = false;
                swGroupNotfaction.setText("开启免打扰");
            } else {
                isCheckedNo = true;
                swGroupNotfaction.setText("关闭免打扰");
            }
        }
    }

    private void initViews() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mGroupsDetailAdapter = new GroupsDetailAdapter(R.layout.item_group, mList, isOwner);
        mGroupsDetailAdapter.bindToRecyclerView(recyclerView);
        mGroupsDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == adapter.getData().size() - 1) {
                    Intent intent = new Intent(GroupDetailActivity.this, SubtractFriendsActivity.class);
                    intent.putExtra("list", friendListBeanArrayList);
                    intent.putExtra("groupId", id);
                    intent.putExtra("title", title);
                    startActivityForResult(intent, UPDATA_GROUP_OWN);
                } else if (position == adapter.getData().size() - 2) {
                    Intent intent = new Intent(GroupDetailActivity.this, SelectedFriendsActivity.class);
                    intent.putExtra("flag", 2);
                    intent.putExtra("groupId", id);
                    startActivityForResult(intent, UPDATA_GROUP_OWN);
                }
            }
        });
    }


    @OnClick({R.id.ll_group_port, R.id.ll_group_name, R.id.group_announcement, R.id.group_clean, R.id.sw_group_top, R.id.sw_group_notfaction, R.id.group_transfer, R.id.ll_shut_up, R.id.group_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_group_port:
                PermissionUtils.get(this).getCameraPermission(() -> takePhoto(GroupDetailActivity.this, HEADER_PIC));
                break;
            case R.id.ll_group_name:
                Intent intent = new Intent(this, GroupUpdataNameActivity.class);
                startActivityForResult(intent, UPDATA_GROUP_NAME);
                break;
            case R.id.ll_shut_up://全员禁言
                setGagOrNo();
                break;
            case R.id.group_announcement:
                Intent tempIntent = new Intent(this, GroupNoticeActivity.class);
                tempIntent.putExtra("conversationType", Conversation.ConversationType.GROUP.getValue());
                tempIntent.putExtra("targetId", groupId);
                startActivityForResult(tempIntent, UPDATA_GROUP_NOTICE);
                break;
            case R.id.group_clean:
                cleanGroupMsg();
                break;
            case R.id.group_transfer://群主转让
                Intent i = new Intent(GroupDetailActivity.this, TransferOwnActivity.class);
                i.putExtra("groupId", id);
                startActivityForResult(i, UPDATA_GROUP_OWN);
                break;
            case R.id.sw_group_top:
                if (isCheckedTop) {
                    swGroupTop.setText("置顶群组");
                    setConversationTop(Conversation.ConversationType.GROUP, groupId, false);
                    isCheckedTop = false;
                } else {
                    swGroupTop.setText("取消置顶");
                    setConversationTop(Conversation.ConversationType.GROUP, groupId, true);
                    isCheckedTop = true;
                }

                break;
            case R.id.sw_group_notfaction:
                if (isCheckedNo) {
                    swGroupNotfaction.setText("开启免打扰");
                    setConverstionNotif(Conversation.ConversationType.GROUP, groupId, false);
                    isCheckedNo = false;
                } else {
                    setConverstionNotif(Conversation.ConversationType.GROUP, groupId, true);
                    swGroupNotfaction.setText("关闭免打扰");
                    isCheckedNo = true;
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


                                RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, groupId, null);

                                for (Activity activity : BaseActivity.transactionActivities) {

                                    activity.finish();

                                }
                            }));
                } else {
                    EanfangHttp.post(UserApi.POST_GROUP_QUIT)
                            .params("groupId", id)
                            .params("ids", EanfangApplication.getApplication().getAccId())
                            .params("groupName", title)
                            .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                                ToastUtil.get().showToast(GroupDetailActivity.this, "退出成功");

                                RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, groupId, null);

                                for (Activity activity : BaseActivity.transactionActivities) {

                                    activity.finish();

                                }
                            }));
                }
                break;
        }
    }

    private void quitGroup() {
        EanfangHttp.post(UserApi.POST_GROUP_QUIT)
                .params("groupId", id)
                .params("ids", EanfangApplication.get().getAccId())
//                .params("ids", userIdList.toString())
                .params("groupName", title)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                    ToastUtil.get().showToast(GroupDetailActivity.this, "退出成功");
                    RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, groupId, null);
                }));

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
    public void updataGroupInfo(String title, String imgKey, String transfer, String notice) {

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
            if (!TextUtils.isEmpty(notice)) {
                jsonObject.put("notice", notice);
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

    private void setGagOrNo() {
        if (isCheckedGag) {
            EanfangHttp.post(UserApi.POST_GROUP_NOGAG)
                    .params("groupId", id)
                    .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                        ToastUtil.get().showToast(GroupDetailActivity.this, "解禁成功");
                        group_shutup.setText("开启禁言");
                        isCheckedGag = false;
                    }));
        } else {
            EanfangHttp.post(UserApi.POST_GROUP_GAG)
                    .params("groupId", id)
                    .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                        ToastUtil.get().showToast(GroupDetailActivity.this, "禁言成功");
                        group_shutup.setText("关闭禁言");
                        isCheckedGag = true;
                    }));
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);

        if (result == null || result.getImage() == null) {
            return;
        }
        TImage image = result.getImage();

        headPortrait = UuidUtil.getUUID() + ".png";

        groupHeader.setImageURI("file://" + image.getOriginalPath());

        OSSUtils.initOSS(this).asyncPutImage(headPortrait, image.getOriginalPath(), new OSSCallBack(this, true) {
            @Override
            public void onOssSuccess() {
                super.onOssSuccess();
                updataGroupInfo(title, headPortrait, "", "");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == UPDATA_GROUP_NAME) {
                String title = data.getStringExtra("updata_Name").toString();
                updataGroupInfo(title, headPortrait, "", "");
                groupName.setText(title);
                Group groupInfo = new Group(groupId, title, Uri.parse(BuildConfig.OSS_SERVER + headPortrait));
                RongIM.getInstance().refreshGroupInfoCache(groupInfo);
            } else if (requestCode == UPDATA_GROUP_NOTICE) {
                if (!TextUtils.isEmpty(data.getStringExtra("notice"))) {
                    group_notice.setText(data.getStringExtra("notice"));
                    updataGroupInfo(title, headPortrait, "", data.getStringExtra("notice"));
                }
            } else if (requestCode == UPDATA_GROUP_OWN) {
                initData();
            }
        }
    }
}