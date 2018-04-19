package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.model.GroupsBean;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.GroupsAdapter;
import net.eanfang.worker.ui.adapter.GroupsDetailAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;

public class GroupDetailActivity extends BaseWorkerActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private GroupsDetailAdapter mGroupsDetailAdapter;
    private ArrayList<FriendListBean> friendListBeanArrayList = new ArrayList<>();
    private String title;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        ButterKnife.bind(this);
        setTitle("群组信息");
        setLeftBack();
        String groupId = getIntent().getStringExtra("rongyun_group_id");
        id = EanfangApplication.getApplication().get().get(groupId, 0);
        title = getIntent().getStringExtra("title");
        initViews();
        initData();
        startTransaction(true);
    }

    private void initData() {


        EanfangHttp.post(UserApi.POST_GROUP_NUM)
                .params("groupId", id)
                .execute(new EanfangCallback<FriendListBean>(this, true, FriendListBean.class, true, (list) -> {
                    if (list.size() > 0) {
                        friendListBeanArrayList.addAll(list);
                        mGroupsDetailAdapter.setNewData(list);
                        FriendListBean bean = new FriendListBean();
                        mGroupsDetailAdapter.addData(bean);
                        mGroupsDetailAdapter.addData(bean);
                    }
                }));
    }

    private void initViews() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mGroupsDetailAdapter = new GroupsDetailAdapter(R.layout.item_group);
        mGroupsDetailAdapter.bindToRecyclerView(recyclerView);
        mGroupsDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == adapter.getData().size() - 2) {
                    ToastUtil.get().showToast(GroupDetailActivity.this, "减人");
                    Intent intent = new Intent(GroupDetailActivity.this, MyFriendsListActivity.class);
                    intent.putExtra("list", friendListBeanArrayList);
                    intent.putExtra("groupId", id);
                    intent.putExtra("title", title);
                    intent.putExtra("flag", 2);
                    startActivity(intent);
                } else if (position == adapter.getData().size() - 1) {
                    ToastUtil.get().showToast(GroupDetailActivity.this, "加人");
                    Intent intent = new Intent(GroupDetailActivity.this, MyFriendsListActivity.class);
                    intent.putExtra("flag", 3);
                    intent.putExtra("groupId", id);
                    intent.putExtra("title", title);
                    startActivity(intent);
                }
            }
        });
    }
}
