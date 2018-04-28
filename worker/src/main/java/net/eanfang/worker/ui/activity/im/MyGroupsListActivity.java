package net.eanfang.worker.ui.activity.im;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GroupsBean;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.GroupsAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

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
//        initData();
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
                    }
                }));
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mGroupsAdapter = new GroupsAdapter(R.layout.item_friend_list);

        mGroupsAdapter.bindToRecyclerView(recyclerView);

        startConv();
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

    @Override
    protected void onResume() {
        super.onResume();
        if (mGroupsAdapter != null && mGroupsAdapter.getData().size() > 0) {
            mGroupsAdapter.getData().clear();
        }
        initData();
    }
}
