package net.eanfang.worker.ui.activity.worksapce;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.FriendsAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class MyFriendsListActivity extends BaseWorkerActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private FriendsAdapter mFriendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends_list);
        ButterKnife.bind(this);
        setTitle("我的好友");

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
        mFriendsAdapter = new FriendsAdapter(R.layout.item_friend_list);

        mFriendsAdapter.bindToRecyclerView(recyclerView);
        mFriendsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                    @Override
                    public UserInfo getUserInfo(String s) {
                        for (int i = 0; i < adapter.getData().size(); i++) {
                            if (((FriendListBean) adapter.getData().get(i)).getAccId().equals(s)) {
                                FriendListBean friendListBean = (FriendListBean) adapter.getData().get(i);
                                UserInfo userInfo = new UserInfo(friendListBean.getAccId(), friendListBean.getNickName(), Uri.parse(friendListBean.getAvatar()));
                                return userInfo;
                            }
                        }
                        return null;
                    }
                }, true);
                RongIM.getInstance().startConversation(MyFriendsListActivity.this, Conversation.ConversationType.PRIVATE, "936487014348365825", "赵子武");
            }
        });
    }

}
