package net.eanfang.client.ui.activity.im;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.util.Cn2Spell;
import com.eanfang.util.ToastUtil;
import com.eanfang.witget.SideBar;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.FriendsAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class MyFriendsListActivity extends BaseClientActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.side_bar)
    SideBar sideBar;
    private FriendsAdapter mFriendsAdapter;
    private int flag = 0;//显示不显示checkbox的标志位


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends_list);
        ButterKnife.bind(this);

        setLeftBack();
        setTitle("我的好友");

//        flag=getIntent().getIntExtra("flag",0);

        initViews();
        initData();
        startTransaction(true);

    }


    private void initData() {

        EanfangHttp.post(UserApi.POST_FRIENDS_LIST)
                .params("accId", EanfangApplication.get().getAccId())
                .execute(new EanfangCallback<FriendListBean>(this, true, FriendListBean.class, true, (list) -> {
                    if (list.size() > 0) {

                        for (FriendListBean bean : list) {
                            // 根据姓名获取拼音
                            if (TextUtils.isEmpty(bean.getNickName())) {//名字为空字符串
                                continue;
                            }
                            bean.setPinyin(bean.getNickName());
                            bean.setFirstLetter(Cn2Spell.getPinYin(bean.getNickName()).substring(0, 1).toUpperCase()); // 获取拼音首字母并转成大写
                            if (!Cn2Spell.getPinYin(bean.getNickName()).substring(0, 1).toUpperCase().matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
                                bean.setFirstLetter("#");
                            }
                        }
                        List<FriendListBean> friendListBeanList = list;

                        Collections.sort(friendListBeanList, new FriendListBean());
                        mFriendsAdapter.setNewData(friendListBeanList);
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
        sideBar.setOnStrSelectCallBack(new SideBar.ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                for (int i = 0; i < mFriendsAdapter.getData().size(); i++) {
                    if (selectStr.equalsIgnoreCase(mFriendsAdapter.getData().get(i).getFirstLetter())) {
                        recyclerView.scrollToPosition(i); // 选择到首字母出现的位置
                        return;
                    }
                }
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
                                .execute(new EanfangCallback<JSONObject>(MyFriendsListActivity.this, true, JSONObject.class, (bean) -> {
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
