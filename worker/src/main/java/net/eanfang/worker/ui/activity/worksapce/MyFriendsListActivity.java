package net.eanfang.worker.ui.activity.worksapce;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import net.eanfang.worker.ui.activity.MainActivity;
import net.eanfang.worker.ui.adapter.FriendsAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends_list);
        ButterKnife.bind(this);

        setLeftBack();
        //1:创建选着好友  2：群组删除还有 3：群组添加好友
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
        } else {
            setTitle("我的好友");
        }

        setRightTitleOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (flag == 1) {
                    Intent intent = new Intent(MyFriendsListActivity.this, GroupCreatActivity.class);
                    intent.putStringArrayListExtra("userIdList", userIdList);
                    startActivity(intent);
                } else if (flag == 2) {
                    removeNumber();
                } else if (flag == 3) {
                    AddNumber();
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


                                //提供融云的头像和昵称
                                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                                    @Override
                                    public UserInfo getUserInfo(String s) {
                                        for (int i = 0; i < list.size(); i++) {
                                            FriendListBean friendListBean = (FriendListBean) list.get(i);
                                            UserInfo userInfo = new UserInfo(friendListBean.getAccId(), friendListBean.getNickName(), Uri.parse(BuildConfig.OSS_SERVER + friendListBean.getAvatar()));
                                            return userInfo;
                                        }
                                        return null;
                                    }
                                }, true);
                            }
                        }));
            } else {
                EanfangHttp.post(UserApi.POST_FRIENDS_LIST)
                        .params("accId", EanfangApplication.get().getAccId())
                        .execute(new EanfangCallback<FriendListBean>(this, true, FriendListBean.class, true, (list) -> {
                            if (list.size() > 0) {
                                mFriendsAdapter.setNewData(list);


//                                //提供融云的头像和昵称
//                                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
//                                    @Override
//                                    public UserInfo getUserInfo(String s) {
//                                        for (int i = 0; i < list.size(); i++) {
//                                            FriendListBean friendListBean = (FriendListBean) list.get(i);
//                                            UserInfo userInfo = new UserInfo(friendListBean.getAccId(), friendListBean.getNickName(), Uri.parse(BuildConfig.OSS_SERVER + friendListBean.getAvatar()));
//                                            return userInfo;
//                                        }
//                                        return null;
//                                    }
//                                }, true);
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
                    FriendListBean bean = (FriendListBean) adapter.getData().get(position);
                    if (bean.getFlag() == 1) {
                        //移除
                        userIdList.remove(bean.getAccId());
                    } else {
                        userIdList.add(bean.getAccId());
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

        EanfangHttp.post(UserApi.POST_GROUP_REMOVE)
                .params("groupId", groupId)
                .params("ids", buffer.toString())
//                .params("ids", userIdList.toString())
                .params("groupName", title)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                    ToastUtil.get().showToast(MyFriendsListActivity.this, "移除成功");
                    endTransaction(true);
                }));
    }

    /**
     * 添加成员
     */
    private void AddNumber() {
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
                                }));
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }
}
