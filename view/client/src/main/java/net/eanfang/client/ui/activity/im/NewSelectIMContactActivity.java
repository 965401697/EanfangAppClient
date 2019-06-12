package net.eanfang.client.ui.activity.im;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.GroupCreatBean;
import com.eanfang.biz.model.GroupDetailBean;
import com.eanfang.biz.model.TemplateBean;

import com.eanfang.util.DialogUtil;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.UuidUtil;
import com.eanfang.util.compound.CompoundHelper;
import com.eanfang.biz.model.entity.OrgEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;

public class NewSelectIMContactActivity extends BaseClientActivity {


    @BindView(R.id.rl_selected)
    RelativeLayout rlSelected;
    @BindView(R.id.recycler_view_hori)
    RecyclerView recyclerViewHori;
    @BindView(R.id.rv_company)
    RecyclerView rvCompany;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_no_chat)
    TextView tvNoChat;
    private HeaderIconAdapter mHeaderIconAdapter;
    private Bundle bundle;

    private List<TemplateBean.Preson> newPresonList = new ArrayList<>();
    private Dialog dialog;
    private String imgKey;
    private String path;
    private String groupName;

    private ArrayList<GroupDetailBean.ListBean> mFriendListBeanArrayList;
    private String mGroupId;
    private String mRYGroupId;
    private String mTitle;
    private ArrayList<String> mUserIdList = new ArrayList<String>();
    private ArrayList<String> mUserIconList = new ArrayList<String>();

    //常用联系人的选中容器
    private ArrayList<TemplateBean.Preson> mSeletePrivateChat = new ArrayList<TemplateBean.Preson>();

    private List<TemplateBean.Preson> pirvateChat;

    private OrganizationPersonAdapter mChatAdapter;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object message = msg.obj;
            if (message == null) {
                ToastUtil.get().showToast(NewSelectIMContactActivity.this, "发送成功");
            } else {
                path = (String) message;
                if (!TextUtils.isEmpty(path)) {
                    imgKey = "im/select/" + UuidUtil.getUUID() + ".png";
                    creatGroup();
                }
            }
        }
    };

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String path = (String) msg.obj;

            if (!TextUtils.isEmpty(path)) {
                String inageKey = "im/group/" + UuidUtil.getUUID() + ".png";
                SDKManager.ossKit(NewSelectIMContactActivity.this).asyncPutImage(inageKey, path, (isSuccess) -> {
                    updataGroupInfo(mTitle, inageKey, "", "");
                });

            }
        }
    };


    private int mFlag;
    private List<TemplateBean.Preson> mOldPresonList;//创建群组的已选的老数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_select_imcontact);
        ButterKnife.bind(this);
        supprotToolbar();
        setTitle("选择联系人");
        startTransaction(true);
        dialog = DialogUtil.createLoadingDialog(NewSelectIMContactActivity.this);

        bundle = getIntent().getExtras();

        mFlag = getIntent().getIntExtra("flag", 0);
        setRightTitle("确定");


        Bundle bundle = getIntent().getExtras();

        mOldPresonList = (List<TemplateBean.Preson>) bundle.getSerializable("list");

        if (mFlag == 1) {
            //创建群组
//            if (ClientApplication.get().getCompanyId() != 0) {
//                setRightTitle("创建");
//            } else {
            setRightTitle("下一步");
//            }
            findViewById(R.id.rl_my_group).setVisibility(View.GONE);
        } else if (mFlag == 2) {//创建是分享
            setRightTitle("确定");
        } else if (mFlag == 3) {//创建是分享
            setRightTitle("添加");
            findViewById(R.id.rl_my_group).setVisibility(View.GONE);
            mGroupId = getIntent().getStringExtra("groupId");
            mFriendListBeanArrayList = (ArrayList<GroupDetailBean.ListBean>) getIntent().getSerializableExtra("list");
            mRYGroupId = getIntent().getStringExtra("ryGroupId");
            mTitle = getIntent().getStringExtra("title");

        } else {
            setRightTitle("发送");
        }


        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFlag == 1) {

                    // 私聊好友
                    Set hashSet = new HashSet();
                    hashSet.addAll(mHeaderIconAdapter.getData());
                    hashSet.addAll(mSeletePrivateChat);

                    if (newPresonList.size() > 0) {
                        newPresonList.clear();
                    }
                    newPresonList.addAll(hashSet);

                    if (newPresonList.size() <= 1) {
                        ToastUtil.get().showToast(NewSelectIMContactActivity.this, "最少选两个好友");
                        return;
                    }

                    Intent intent = new Intent(NewSelectIMContactActivity.this, CreateGroupActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) mHeaderIconAdapter.getData());
                    intent.putExtras(bundle);
                    intent.putExtra("groupName", getIntent().getStringExtra("groupName"));
                    intent.putExtra("imgKey", getIntent().getStringExtra("headPortrait"));
                    intent.putExtra("locationPortrait", getIntent().getStringExtra("locationPortrait"));
                    startActivity(intent);
                    endTransaction(true);


                } else if (mFlag == 2) {//创建是分享
                    if (newPresonList.size() > 0) {
                        EventBus.getDefault().post(newPresonList);
                        finishSelf();
                    } else {
                        ToastUtil.get().showToast(NewSelectIMContactActivity.this, "请选择要发送的好友后者群组");
                    }
                } else if (mFlag == 3) {
                    AddNumber();
                } else {
                    //发送分享的群组
                    handler.post(runnable);//立马发送
                }
            }
        });

//        findViewById(R.id.rl_organization).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NewSelectIMContactActivity.this, SelectOrganizationActivity.class);
//                startActivity(intent);
//            }
//        });
        findViewById(R.id.ll_my_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewSelectIMContactActivity.this, SelectedFriendsActivity.class);
                intent.putExtra("flag", 3);
                startActivity(intent);
            }
        });
        findViewById(R.id.rl_my_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewSelectIMContactActivity.this, MyGroupsListActivity.class);
                intent.putExtra("isVisible", true);
                startActivity(intent);
            }
        });

        initViews();
        //区分个人和公司 个人不现实公司
        if (ClientApplication.get().getCompanyId() != 0) {
            getData();
        } else {
            findViewById(R.id.rl_organization).setVisibility(View.GONE);
        }
        //获得常用联系人的会话列表
        getPrivateChat();
    }

    private void getData() {
        EanfangHttp.get(UserApi.GET_BRANCH_OFFICE_ALL_LIST)
                .execute(new EanfangCallback<OrgEntity>(this, true, OrgEntity.class, true, (list) -> {
                    if (list.size() > 0) {
                        List<OrgEntity> data = new ArrayList<>();
                        rvCompany.setLayoutManager(new LinearLayoutManager(NewSelectIMContactActivity.this));

                        CompanyListAdapter adapter = new CompanyListAdapter();
                        adapter.bindToRecyclerView(rvCompany);
                        //排除个人
                        data = Stream.of(list).filter(bean -> bean.getOrgId() != 0).toList();
                        adapter.addData(data);
                        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent = new Intent(NewSelectIMContactActivity.this, CreateGroupOrganizationActivity.class);
                                intent.putExtra("companyId", String.valueOf(((OrgEntity) adapter.getData().get(position)).getCompanyId()));
                                intent.putExtra("companyName", ((OrgEntity) adapter.getData().get(position)).getOrgName());
                                Bundle bundle = new Bundle();

                                // 私聊好友
                                Set hashSet = new HashSet();
                                hashSet.addAll(mHeaderIconAdapter.getData());
                                hashSet.addAll(mSeletePrivateChat);

                                if (newPresonList.size() > 0) {
                                    newPresonList.clear();
                                }
                                newPresonList.addAll(hashSet);


                                bundle.putSerializable("list", (Serializable) newPresonList);
                                intent.putExtras(bundle);

                                intent.putExtra("groupName", getIntent().getStringExtra("groupName"));
                                intent.putExtra("imgKey", getIntent().getStringExtra("headPortrait"));
                                intent.putExtra("locationPortrait", getIntent().getStringExtra("locationPortrait"));

                                startActivity(intent);
                            }
                        });
                    }
                }));
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewHori.setLayoutManager(linearLayoutManager);
        mHeaderIconAdapter = new HeaderIconAdapter(R.layout.item_header_icon);
        mHeaderIconAdapter.bindToRecyclerView(recyclerViewHori);
        //删除已选的人
        mHeaderIconAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                newPresonList.remove(position);
                adapter.notifyDataSetChanged();

                if (newPresonList.size() == 0) {
                    rlSelected.setVisibility(View.GONE);
                }
            }
        });

        rvCompany.setNestedScrollingEnabled(false);

        if (mOldPresonList != null && mOldPresonList.size() > 0) {
            rlSelected.setVisibility(View.VISIBLE);
            newPresonList.addAll(mOldPresonList);//加入创建的老数据
            mHeaderIconAdapter.setNewData(newPresonList);
        }
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

        List<TemplateBean.Preson> presons = new ArrayList<>();

        for (TemplateBean.Preson p : presonList) {
            if (!p.getId().equals(String.valueOf(ClientApplication.get().getAccId()))) {
                presons.add(p);
            }
        }

        if (presons.size() > 0) {

            if (rlSelected.getVisibility() != View.VISIBLE) {
                rlSelected.setVisibility(View.VISIBLE);
            }
            Set hashSet = new HashSet();
            hashSet.addAll(mHeaderIconAdapter.getData());
            hashSet.addAll(presons);

            if (newPresonList.size() > 0) {
                newPresonList.clear();
            }
            newPresonList.addAll(hashSet);
            mHeaderIconAdapter.setNewData(newPresonList);
        }

    }

    /**
     * 循环发送
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情，这里再次调用此Runnable对象，以实现每一秒实现一次的定时器操作
            if (!dialog.isShowing()) {
                dialog.show();
            }
            if (newPresonList.size() > 5) {

                handler.postDelayed(runnable, 1500);
                List<TemplateBean.Preson> newRongIdLists = new ArrayList<>();

                newRongIdLists.addAll(newPresonList);
                List<TemplateBean.Preson> newLists = newPresonList.subList(0, 5);

                for (TemplateBean.Preson preson : newLists) {
                    newRongIdLists.remove(preson);
                    sendCheckedMsg(preson.getId());
                }
                newPresonList = newRongIdLists;

            } else if (newPresonList.size() <= 5) {
                for (TemplateBean.Preson preson : newPresonList) {
                    sendCheckedMsg(preson.getId());
                }
                handler.sendEmptyMessage(1);
                handler.removeCallbacks(runnable);
                dialog.dismiss();
                NewSelectIMContactActivity.this.finishSelf();
            }

        }
    };


    class HeaderIconAdapter extends BaseQuickAdapter<TemplateBean.Preson, BaseViewHolder> {

        public HeaderIconAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, TemplateBean.Preson item) {
            if (item.getProtraivat().startsWith("http")) {
                ((ImageView) helper.getView(R.id.iv_user_header)).setImageURI(Uri.parse(item.getProtraivat()));
            } else {
                ((ImageView) helper.getView(R.id.iv_user_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getProtraivat()));
            }
        }
    }

    private void getPrivateChat() {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
                @Override
                public void onSuccess(List<Conversation> conversations) {

                    if (conversations != null && conversations.size() > 0) {
                        pirvateChat = new ArrayList<>(conversations.size());
                        for (Conversation s : conversations) {
                            //如果名字为空的话 就不现实这天记录
                            if (!TextUtils.isEmpty(s.getConversationTitle())) {
                                TemplateBean.Preson preson = new TemplateBean.Preson();
                                preson.setProtraivat(s.getPortraitUrl());
                                preson.setId(s.getTargetId());
                                preson.setName(s.getConversationTitle());
                                pirvateChat.add(preson);
                            }
                        }


                        runOnUiThread(() -> {
                            if (pirvateChat.size() > 0) {
                                recyclerView.setVisibility(View.VISIBLE);
                                tvNoChat.setVisibility(View.GONE);
                                recyclerView.setLayoutManager(new LinearLayoutManager(NewSelectIMContactActivity.this));
                                mChatAdapter = new OrganizationPersonAdapter(1);
                                mChatAdapter.bindToRecyclerView(recyclerView);
                                mChatAdapter.setNewData(pirvateChat);
                                mChatAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                                        //设置选中不选中
                                        TemplateBean.Preson p = (TemplateBean.Preson) adapter.getData().get(position);
                                        if (p.isChecked()) {
                                            p.setChecked(false);
                                            mSeletePrivateChat.remove(p);
                                        } else {
                                            p.setChecked(true);
                                            mSeletePrivateChat.add(p);
                                        }

                                        adapter.notifyItemChanged(position);
                                    }
                                });
                            }
                        });
                    }
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            }, Conversation.ConversationType.PRIVATE);
        }
    }

    /**
     * 发送分享
     *
     * @param id
     */
    private void sendCheckedMsg(String id) {
        if (bundle != null) {
            //区分消息的类型
            String shareType = bundle.getString("shareType");
            String mPush = null;
            if (shareType.equals("1")) {
                mPush = "报修订单";
            } else if (shareType.equals("2")) {
                mPush = "故障处理";
            } else if (shareType.equals("3")) {
                mPush = "工作汇报";
            } else if (shareType.equals("4")) {
                mPush = "布置任务";
            } else if (shareType.equals("5")) {
                mPush = "设备点检";
            } else if (shareType.equals("6")) {
                mPush = "交接班";
            } else if (shareType.equals("7")) {
                mPush = "面谈员工";
            } else if (shareType.equals("8")) {
                mPush = "开店日志";
            } else if (shareType.equals("9")) {
                mPush = "布防日志";
            } else if (shareType.equals("10")) {
                mPush = "安防圈";
            }
            Conversation.ConversationType conversationType;

            CustomizeMessage customizeMessage = new CustomizeMessage();
            customizeMessage.setPicUrl(bundle.getString("picUrl"));
            customizeMessage.setWorkerName(bundle.getString("workerName"));
            customizeMessage.setCreatTime(bundle.getString("creatTime"));
            customizeMessage.setOrderNum(bundle.getString("orderNum"));
            customizeMessage.setOrderId(bundle.getString("id"));
            customizeMessage.setStatus(bundle.getString("status"));
            customizeMessage.setShareType(bundle.getString("shareType"));
            if (isInteger(id)) {
                conversationType = Conversation.ConversationType.PRIVATE;
            } else {
                conversationType = Conversation.ConversationType.GROUP;
            }

            RongIM.getInstance().sendMessage(conversationType, id, customizeMessage, mPush, mPush, new RongIMClient.SendMessageCallback() {
                @Override
                public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                    Log.e("zzw", "发送失败=" + integer + "=" + errorCode);
                }

                @Override
                public void onSuccess(Integer integer) {
                    Log.e("zzw", "发送成功=" + integer);
                }
            });
        }
    }

    /**
     * 合唱头像
     */
    private void compoundPhoto() {

        if (newPresonList.size() <= 1) {
            ToastUtil.get().showToast(this, "最少选两个好友");
            return;
        }
        dialog.show();
        ArrayList<String> userIconList = new ArrayList<>();

        if (newPresonList.size() > 3) {

            for (int i = 0; i < 3; i++) {
                userIconList.add(newPresonList.get(i).getProtraivat());
            }
            handleNames(3);
        } else {
            for (TemplateBean.Preson preson : newPresonList) {
                userIconList.add(preson.getProtraivat());
            }
            handleNames(newPresonList.size());
        }


        userIconList.add(ClientApplication.get().getLoginBean().getAccount().getAvatar());//添加自己的头像

        //防止创建群组的人员一个头像图片都没有 造成的空指针崩溃
        if (userIconList.size() == 0) {
            ToastUtil.get().showToast(NewSelectIMContactActivity.this, "请先上传群头像");
            return;
        }

        //合成头像
        CompoundHelper.getInstance().sendBitmap(this, handler, userIconList);//生成图片
    }

    private void handleNames(int len) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < len; i++) {
            if (i == 0) {
                stringBuffer.append(newPresonList.get(i).getName());
            } else if (i == len - 1) {
                if (i < 2) {
                    stringBuffer.append("," + newPresonList.get(i).getName());
                } else {
                    stringBuffer.append("," + newPresonList.get(i).getName() + "...等");
                }
            } else {
                stringBuffer.append("," + newPresonList.get(i).getName());
            }
        }
        groupName = ClientApplication.get().getLoginBean().getAccount().getNickName() + "," + stringBuffer.toString();
    }

    /**
     * 创建群组
     */
    private void creatGroup() {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();

        JSONArray array = new JSONArray();
        try {
            for (TemplateBean.Preson preson : newPresonList) {
                JSONObject jsonObject3 = new JSONObject();
                jsonObject3.put("accId", preson.getId());
                array.put(jsonObject3);
            }
            //把自己的id 加进去
            JSONObject jsonObject3 = new JSONObject();
            jsonObject3.put("accId", ClientApplication.get().getAccId());
            array.put(jsonObject3);

            jsonObject1.put("groupName", groupName);
            jsonObject1.put("headPortrait", imgKey);
            jsonObject.put("sysGroup", jsonObject1);
            jsonObject.put("sysGroupUsers", array);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //头像上传成功后  提交数据
        SDKManager.ossKit(NewSelectIMContactActivity.this).asyncPutImage(imgKey, path, (isSuccess) -> {
            //创建群组
            EanfangHttp.post(UserApi.POST_CREAT_GROUP)
                    .upJson(jsonObject)
                    .execute(new EanfangCallback<GroupCreatBean>(NewSelectIMContactActivity.this, false, GroupCreatBean.class) {
                        @Override
                        public void onSuccess(GroupCreatBean bean) {
                            super.onSuccess(bean);
                            ToastUtil.get().showToast(NewSelectIMContactActivity.this, "创建成功");
                            Group groupInfo = new Group(bean.getRcloudGroupId(), bean.getGroupName(), Uri.parse(BuildConfig.OSS_SERVER + imgKey));
                            RongIM.getInstance().refreshGroupInfoCache(groupInfo);

                            ClientApplication.get().set(bean.getRcloudGroupId(), bean.getGroupId());
                            RongIM.getInstance().startGroupChat(NewSelectIMContactActivity.this, bean.getRcloudGroupId(), bean.getGroupName());
                            dialog.dismiss();
                            NewSelectIMContactActivity.this.finish();
                        }

                        @Override
                        public void onFail(Integer code, String message, com.alibaba.fastjson.JSONObject jsonObject) {
                            super.onFail(code, message, jsonObject);
                            ToastUtil.get().showToast(NewSelectIMContactActivity.this, "创建失败");
                            dialog.dismiss();
                        }
                    });
        });

    }

    /**
     * 添加成员
     */
    private void AddNumber() {

        if (newPresonList.size() == 0) {
            ToastUtil.get().showToast(this, "至少选择一个好友");
            return;
        }
        // TODO: 2018/10/9  待优化
        List<String> idList = new ArrayList<>();
        for (GroupDetailBean.ListBean bean : mFriendListBeanArrayList) {
            mUserIconList.add(bean.getAccountEntity().getAvatar());
            idList.add(bean.getAccId());
        }

        for (TemplateBean.Preson p : newPresonList) {
            if (!mUserIconList.contains(p.getProtraivat())) {
                mUserIconList.add(p.getProtraivat());
            }
            mUserIdList.add(p.getId());
        }

        mUserIdList.removeAll(idList);

        mUserIconList.add(ClientApplication.get().getLoginBean().getAccount().getAvatar());
        CompoundHelper.getInstance().sendBitmap(this, mHandler, mUserIconList);//生成图片


        JSONArray array = new JSONArray();
        JSONObject object = null;
        for (String s : mUserIdList) {
            object = new JSONObject();
            try {
                object.put("accId", s);
                object.put("groupId", mGroupId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(object);
        }
        EanfangHttp.post(UserApi.POST_GROUP_JOIN)
                .upJson(array)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                    ToastUtil.get().showToast(NewSelectIMContactActivity.this, "添加成功");
                    setResult(RESULT_OK);
                    endTransaction(true);
                }));
    }

    /**
     * 更新群组信息
     */
    public void updataGroupInfo(String title, String imgKey, String transfer, String notice) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("groupId", mGroupId);

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
                .execute(new EanfangCallback<JSONObject>(NewSelectIMContactActivity.this, false, JSONObject.class, (JSONObject) -> {
                    Group groupInfo = new Group(mRYGroupId, title, Uri.parse(BuildConfig.OSS_SERVER + imgKey));
                    RongIM.getInstance().refreshGroupInfoCache(groupInfo);

                }));
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
}