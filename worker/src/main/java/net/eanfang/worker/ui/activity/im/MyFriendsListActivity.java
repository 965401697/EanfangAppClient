package net.eanfang.worker.ui.activity.im;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.FriendsAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

public class MyFriendsListActivity extends BaseWorkerActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private FriendsAdapter mFriendsAdapter;
    private int flag = 0;//显示不显示checkbox的标志位
    private ArrayList<String> userIdList = new ArrayList<String>();
    private ArrayList<FriendListBean> mFriendListBeanArrayList;
    private String groupId;
    private String title;
    private FriendListBean currentBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends_list);
        ButterKnife.bind(this);

        setLeftBack();
        //1:创建选着好友  2：群组删除还有 3：群组添加好友  4,转让群主
        flag = getIntent().getIntExtra("flag", 0);
        rightTitleOnClick(flag);
        initViews();
        initData();
    }


    private void rightTitleOnClick(int flag) {
        if (flag == 1) {
            setTitle("选择好友");
            setRightTitle("下一步");
            startTransaction(true);
        } else if (flag == 2) {
            setTitle("群组移除好友");
            setRightTitle("确定");
            mFriendListBeanArrayList = (ArrayList<FriendListBean>) getIntent().getSerializableExtra("list");
            groupId = getIntent().getStringExtra("groupId");
            title = getIntent().getStringExtra("title");
            startTransaction(true);
        } else if (flag == 3) {
            setTitle("选择好友");
            setRightTitle("确定");
            groupId = getIntent().getStringExtra("groupId");
            startTransaction(true);
        } else if (flag == 4) {
            setTitle("转让群主");
            setRightTitle("确定");
            groupId = getIntent().getStringExtra("groupId");
        } else {
            setTitle("我的好友");
        }

        setRightTitleOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (flag == 1) {
                    if (userIdList.size() == 1) {
                        //说明单聊
                        RongIM.getInstance().startPrivateChat(MyFriendsListActivity.this, userIdList.get(0), currentBean.getNickName());
                    } else {
                        Intent intent = new Intent(MyFriendsListActivity.this, GroupCreatActivity.class);
                        intent.putStringArrayListExtra("userIdList", userIdList);
                        startActivity(intent);
                    }
                } else if (flag == 2) {
                    removeNumber();
                } else if (flag == 3) {
                    AddNumber();
                } else if (flag == 4) {
                    transfer();
                }
            }
        });
    }

    private void initData() {
        if (flag != 2) {
            if (flag == 3) {
                //查找没有在群组的好友
                EanfangHttp.post(UserApi.POST_GROUP_NOJOIN)
                        .params("groupId", groupId)
                        .params("accId", EanfangApplication.get().getAccId())
                        .execute(new EanfangCallback<FriendListBean>(this, true, FriendListBean.class, true, (list) -> {
                            if (list.size() > 0) {
                                mFriendsAdapter.setNewData(list);
                            }
                        }));
            } else if (flag == 4) {
                //查找群内成员
                EanfangHttp.post(UserApi.POST_GROUP_NUM)
                        .params("groupId", groupId)
                        .execute(new EanfangCallback<FriendListBean>(this, true, FriendListBean.class, true, (list) -> {
                            if (list.size() > 0) {
                                ArrayList<FriendListBean> friendListBeanArrayList = new ArrayList<>();
                                for (int i = 0; i < list.size(); i++) {
                                    if (!(String.valueOf(EanfangApplication.getApplication().getAccId()).equals(list.get(i).getAccId()))) {
                                        friendListBeanArrayList.add(list.get(i));
                                    }
                                }
                                mFriendsAdapter.setNewData(friendListBeanArrayList);
                            }
                        }));
            } else {
                EanfangHttp.post(UserApi.POST_FRIENDS_LIST)
                        .params("accId", EanfangApplication.get().getAccId())
                        .execute(new EanfangCallback<FriendListBean>(this, true, FriendListBean.class, true, (list) -> {
                            if (list.size() > 0) {
                                mFriendsAdapter.setNewData(list);
                            }
                        }));
            }
        } else {
            mFriendsAdapter.setNewData(mFriendListBeanArrayList);
        }
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFriendsAdapter = new FriendsAdapter(R.layout.item_friend_list, flag);

        mFriendsAdapter.bindToRecyclerView(recyclerView);
        if (flag == 0) {
            startConv();
        } else {
            selectFriends();
        }

        //删除好友
        mFriendsAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                FriendListBean friendListBean = (FriendListBean) adapter.getData().get(position);
                DialogShow(friendListBean.getAccId(), friendListBean.getNickName(), position);
                return false;
            }
        });
    }

    /**
     * 开始聊天
     */
    private void startConv() {
        mFriendsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                RongIM.getInstance().startConversation(MyFriendsListActivity.this, Conversation.ConversationType.PRIVATE, ((FriendListBean) adapter.getData().get(position)).getAccId(), ((FriendListBean) adapter.getData().get(position)).getNickName());

            }
        });
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

                    if (flag == 4) {

                        if (userIdList.size() == 1) {
                            ToastUtil.get().showToast(MyFriendsListActivity.this, "只能选择一个好友为群主");
                            return;
                        }
                        FriendListBean bean = (FriendListBean) adapter.getData().get(position);
                        if (bean.getFlag() == 1) {
                            //移除
                            userIdList.remove(bean.getAccId());
                            bean.setFlag(0);
                        } else {
                            userIdList.add(bean.getAccId());
                            bean.setFlag(1);
                            currentBean = bean;
                        }

                    } else {
                        FriendListBean bean = (FriendListBean) adapter.getData().get(position);
                        if (bean.getFlag() == 1) {
                            //移除
                            userIdList.remove(bean.getAccId());
                            bean.setFlag(0);
                        } else {
                            userIdList.add(bean.getAccId());
                            bean.setFlag(1);
                            currentBean = bean;
                        }
                    }
                }
            }
        });
    }

    /**
     * 移除组内成员
     */
    private void removeNumber() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < userIdList.size(); i++) {
            if (i == 0) {
                buffer.append(userIdList.get(i));
            } else {
                buffer.append("," + userIdList.get(i));
            }
        }

        EanfangHttp.post(UserApi.POST_GROUP_QUIT)
                .params("groupId", groupId)
                .params("ids", buffer.toString())
//                .params("ids", userIdList.toString())
                .params("groupName", title)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                    ToastUtil.get().showToast(MyFriendsListActivity.this, "移除成功");
                    setResult(RESULT_OK);
                    endTransaction(true);
                }));
    }

    /**
     * 转让群主
     */
    private void transfer() {

        if (userIdList.size() == 0) {
            ToastUtil.get().showToast(MyFriendsListActivity.this, "必须选择一个还有");
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("groupId", groupId);
            jsonObject.put("create_user", userIdList.get(0));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //转让群主
        EanfangHttp.post(UserApi.POST_UPDATA_GROUP)
                .upJson(jsonObject)
                .execute(new EanfangCallback<JSONObject>(MyFriendsListActivity.this, true, JSONObject.class, (JSONObject) -> {
                    ToastUtil.get().showToast(MyFriendsListActivity.this, "转让成功");
                    setResult(RESULT_OK);
                    finish();
                }));

    }

    /**
     * 添加成员
     */
    private void AddNumber() {

        if (userIdList.size() == 0) {
            ToastUtil.get().showToast(MyFriendsListActivity.this, "请选择好友");
            return;
        }

        JSONArray array = new JSONArray();
        JSONObject object = null;
        for (String s : userIdList) {
            object = new JSONObject();
            try {
                object.put("accId", s);
                object.put("groupId", groupId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        array.put(object);
        EanfangHttp.post(UserApi.POST_GROUP_JOIN)
                .upJson(array)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                    ToastUtil.get().showToast(MyFriendsListActivity.this, "添加成功");
                    setResult(RESULT_OK);
                    endTransaction(true);
                }));
    }

    private void DialogShow(String userId, String name, int position) {
        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle("删除好友")//设置对话框的标题
                .setMessage("您确定删除“" + name + "”好友？")//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除好友
                        EanfangHttp.post(UserApi.POST_DELETE_FRIEND)
                                .params("ids", userId)
                                .execute(new EanfangCallback<org.json.JSONObject>(MyFriendsListActivity.this, true, org.json.JSONObject.class, (bean) -> {
                                    mFriendsAdapter.remove(position);

                                    RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, userId, new RongIMClient.ResultCallback<Boolean>() {
                                        @Override
                                        public void onSuccess(Boolean aBoolean) {

                                        }

                                        @Override
                                        public void onError(RongIMClient.ErrorCode errorCode) {

                                        }
                                    });

                                }));
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }
}
