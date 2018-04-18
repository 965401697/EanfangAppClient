package net.eanfang.worker.ui.activity.worksapce;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.model.GroupsBean;
import com.eanfang.ui.base.BaseActivity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.FriendsAdapter;
import net.eanfang.worker.ui.adapter.GroupsAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

public class MyGroupsListActivity extends BaseWorkerActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private GroupsAdapter mGroupsAdapter;
    private int flag = 0;//显示不显示checkbox的标志位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups_list);
        ButterKnife.bind(this);

        setTitle("我的群组");
        setLeftBack();
        initViews();
        initData();
    }

    private void initData() {
        EanfangHttp.post(UserApi.POST_GET_GROUP)
                .params("accId", EanfangApplication.get().getAccId())
                .execute(new EanfangCallback<GroupsBean>(this, true, GroupsBean.class, true, (list) -> {
                    if (list.size() > 0) {
                        mGroupsAdapter.setNewData(list);
                        for (int i = 0; i < list.size(); i++) {
                            GroupsBean groupsBean = (GroupsBean) list.get(i);
                            EanfangApplication.get().set(groupsBean.getRcloudGroupId(), groupsBean.getGroupId());
                        }
                        //提供融云的头像和昵称
                        RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
                            @Override
                            public Group getGroupInfo(String s) {
                                for (int i = 0; i < list.size(); i++) {
                                    GroupsBean groupsBean = (GroupsBean) list.get(i);
                                    Group group = new Group(groupsBean.getRcloudGroupId(), groupsBean.getGroupName(), Uri.parse("https://imgcache.cjmx.com/star/201512/20151207142700908.jpg"));
                                    return group;
                                }
                                return null;
                            }
                        }, true);
                    }
                }));
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mGroupsAdapter = new GroupsAdapter(R.layout.item_friend_list);

        mGroupsAdapter.bindToRecyclerView(recyclerView);

        startConv();


        //删除好友
        mGroupsAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                FriendListBean friendListBean = (FriendListBean) adapter.getData().get(position);
//                DialogShow(friendListBean.getAccId(), friendListBean.getNickName(), position);
                return false;
            }
        });
    }

    /**
     * 开始聊天
     */
    private void startConv() {
        mGroupsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                RongIM.getInstance().startConversation(MyGroupsListActivity.this, Conversation.ConversationType.GROUP, ((GroupsBean) adapter.getData().get(position)).getRcloudGroupId(), ((GroupsBean) adapter.getData().get(position)).getGroupName());

            }
        });
    }
}
