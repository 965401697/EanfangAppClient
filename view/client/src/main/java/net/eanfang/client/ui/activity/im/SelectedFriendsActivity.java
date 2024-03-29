package net.eanfang.client.ui.activity.im;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.biz.model.bean.GroupDetailBean;
import com.eanfang.biz.model.entity.SysGroupUserEntity;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.FriendListBean;
import com.eanfang.biz.model.bean.TemplateBean;

import com.eanfang.util.ToastUtil;
import com.eanfang.util.compound.CompoundHelper;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.adapter.FriendsAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;

public class SelectedFriendsActivity extends BaseClientActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private FriendsAdapter mFriendsAdapter;
    private ArrayList<String> mUserIdList = new ArrayList<String>();
    private ArrayList<String> mUserIconList = new ArrayList<String>();
    private FriendListBean mCurrentBean;

    private int mFlag;
    private ArrayList<SysGroupUserEntity> mFriendListBeanArrayList;
    private String mGroupId;
    private String mRYGroupId;
    private String mTitle;
    private List<TemplateBean.Preson> presonList = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String path = (String) msg.obj;

            if (!TextUtils.isEmpty(path)) {
                String inageKey = "im/group/" + StrUtil.uuid() + ".png";
                SDKManager.ossKit(SelectedFriendsActivity.this).asyncPutImage(inageKey, path,(isSuccess) -> {
                    updataGroupInfo(mTitle, inageKey, "", "");
                });
                SelectedFriendsActivity.this.setResult(RESULT_OK);
                SelectedFriendsActivity.this.endTransaction(true);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_selected_friends);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("选择好友");
        setLeftBack();

        //1:创建选着好友  2：群组添加好友 3：分享
        mFlag = getIntent().getIntExtra("flag", 0);
        rightTitleOnClick(mFlag);

        initViews();
        initData();

        startTransaction(true);
    }


    private void rightTitleOnClick(int flag) {
        if (flag == 1) {
            setRightTitle("下一步");
        } else if (flag == 2) {
            setRightTitle("确定");
            mGroupId = getIntent().getStringExtra("groupId");
            mFriendListBeanArrayList = (ArrayList<SysGroupUserEntity>) getIntent().getSerializableExtra("list");
            mRYGroupId = getIntent().getStringExtra("ryGroupId");
            mTitle = getIntent().getStringExtra("title");
        } else {
            setRightTitle("确定");
        }

        setRightTitleOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (flag == 1) {
                    if (mUserIdList.size() == 1) {
                        //说明单聊
                        RongIM.getInstance().startPrivateChat(SelectedFriendsActivity.this, mUserIdList.get(0), mCurrentBean.getNickName());

//                        sendCheckedMsg(mUserIdList.get(0));


                    } else {

                        if (mUserIdList.size() <= 1) {
                            ToastUtil.get().showToast(SelectedFriendsActivity.this, "请至少选择一位好友开始聊天");
                            return;
                        }

                        Intent intent = new Intent(SelectedFriendsActivity.this, GroupCreatActivity.class);
                        intent.putStringArrayListExtra("userIdList", mUserIdList);
                        intent.putStringArrayListExtra("userIconList", mUserIconList);
                        startActivity(intent);
                    }
                } else if (flag == 2 || flag == 3) {
//                    AddNumber();
//                } else {
                    EventBus.getDefault().post(presonList);
                    finishSelf();
                }
            }
        });
    }


    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFriendsAdapter = new FriendsAdapter(R.layout.item_friend_list, mFlag);

        mFriendsAdapter.bindToRecyclerView(recyclerView);

        selectFriends();

    }

    private void initData() {

        if (mFlag == 2) {
            //查找没有在群组的好友
            EanfangHttp.post(UserApi.POST_GROUP_NOJOIN)
                    .params("groupId", mGroupId)
                    .params("accId", ClientApplication.get().getAccId())
                    .execute(new EanfangCallback<FriendListBean>(this, true, FriendListBean.class, true, (list) -> {
                        if (list.size() > 0) {
                            mFriendsAdapter.setNewData(list);
                        }
                    }));
        } else {
            EanfangHttp.post(UserApi.POST_FRIENDS_LIST)
                    .params("accId", ClientApplication.get().getAccId())
                    .execute(new EanfangCallback<FriendListBean>(this, true, FriendListBean.class, true, (list) -> {
                        if (list.size() > 0) {
                            mFriendsAdapter.setNewData(list);
                        }
                    }));
        }

    }

    /**
     * 添加成员
     */
    private void AddNumber() {

        if (mUserIdList.size() == 0) {
            ToastUtil.get().showToast(this, "至少选择一个好友");
            return;
        }

        for (SysGroupUserEntity bean : mFriendListBeanArrayList) {
            mUserIconList.add(bean.getAccountEntity().getAvatar());
        }
        mUserIconList.add(ClientApplication.get().getLoginBean().getAccount().getAvatar());
        CompoundHelper.getInstance().sendBitmap(this, handler, mUserIconList);//生成图片


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
                    ToastUtil.get().showToast(SelectedFriendsActivity.this, "添加成功");
                    setResult(RESULT_OK);
                    endTransaction(true);
                }));
    }

    /**
     * 选择好友
     */
    private void selectFriends() {
        mFriendsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //创建群组选着好友
                if (view.getId() == R.id.cb_checked) {

                    FriendListBean bean = (FriendListBean) adapter.getData().get(position);
                    if (bean.getFlag() == 1) {
                        //移除
                        mUserIdList.remove(bean.getAccId());

                        TemplateBean.Preson preson = new TemplateBean.Preson();
                        preson.setName(bean.getNickName());
                        preson.setId(bean.getAccId());
                        preson.setProtraivat(bean.getAvatar());
                        presonList.remove(preson);

                        if (!TextUtils.isEmpty(bean.getAvatar())) {
                            mUserIconList.remove(bean.getAvatar());
                        }
                        bean.setFlag(0);
                    } else {
                        mUserIdList.add(bean.getAccId());

                        TemplateBean.Preson preson = new TemplateBean.Preson();
                        preson.setName(bean.getNickName());
                        preson.setId(bean.getAccId());
                        preson.setProtraivat(bean.getAvatar());

                        presonList.add(preson);

                        if (!TextUtils.isEmpty(bean.getAvatar())) {
                            mUserIconList.add(bean.getAvatar());
                        }
                        bean.setFlag(1);
                        mCurrentBean = bean;
                    }

                }
            }
        });
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
                .execute(new EanfangCallback<JSONObject>(SelectedFriendsActivity.this, false, JSONObject.class, (JSONObject) -> {
                    Group groupInfo = new Group(mRYGroupId, title, Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + imgKey));
                    RongIM.getInstance().refreshGroupInfoCache(groupInfo);

                }));
    }
}
