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
import io.rong.imkit.RongContext;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends_list);
        ButterKnife.bind(this);

        setLeftBack();
        setTitle("我的好友");
        initViews();
        initData();
        startTransaction(true);
    }


    private void initData() {

        EanfangHttp.post(UserApi.POST_FRIENDS_LIST)
                .params("accId", EanfangApplication.get().getAccId())
                .execute(new EanfangCallback<FriendListBean>(this, true, FriendListBean.class, true, (list) -> {
                    if (list.size() > 0) {
                        mFriendsAdapter.setNewData(list);
                    }
                }));
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFriendsAdapter = new FriendsAdapter(R.layout.item_friend_list, flag);
        mFriendsAdapter.bindToRecyclerView(recyclerView);
        startConv();


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
                FriendListBean bean = (FriendListBean) adapter.getData().get(position);
                UserInfo userInfo = new UserInfo(bean.getAccId(), bean.getNickName(), Uri.parse(BuildConfig.OSS_SERVER + bean.getAvatar()));
                RongIM.getInstance().refreshUserInfoCache(userInfo);
                RongIM.getInstance().startConversation(MyFriendsListActivity.this, Conversation.ConversationType.PRIVATE, ((FriendListBean) adapter.getData().get(position)).getAccId(), ((FriendListBean) adapter.getData().get(position)).getNickName());

            }
        });
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


                                    EanfangHttp.post(UserApi.POST_DELETE_FRIEND_PUSH)
                                            .params("senderId", EanfangApplication.get().getAccId())
                                            .params("targetIds", userId)
                                            .execute(new EanfangCallback<JSONObject>(MyFriendsListActivity.this, true, JSONObject.class, (json) -> {
                                                MyFriendsListActivity.this.finish();
                                                ToastUtil.get().showToast(MyFriendsListActivity.this, "删除成功");
                                            }));

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
