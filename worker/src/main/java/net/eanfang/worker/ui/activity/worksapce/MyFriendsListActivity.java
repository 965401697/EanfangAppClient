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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends_list);
        ButterKnife.bind(this);

        setLeftBack();
        flag = getIntent().getIntExtra("flag", 0);
        if (flag == 1) {
            setTitle("选择好友");
            setRightTitle("下一步");
            setRightTitleOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyFriendsListActivity.this, GroupCreatActivity.class);
                    intent.putStringArrayListExtra("userIdList", userIdList);
                    startActivity(intent);
                }
            });
        } else {
            setTitle("我的好友");
        }
        initViews();
        initData();
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
                //提供融云的头像和昵称
                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                    @Override
                    public UserInfo getUserInfo(String s) {
                        for (int i = 0; i < adapter.getData().size(); i++) {
                            FriendListBean friendListBean = (FriendListBean) adapter.getData().get(i);
                            UserInfo userInfo = new UserInfo(friendListBean.getAccId(), friendListBean.getNickName(), Uri.parse(BuildConfig.OSS_SERVER + friendListBean.getAvatar()));
                            return userInfo;
                        }
                        return null;
                    }
                }, true);
            }
        });
    }

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
