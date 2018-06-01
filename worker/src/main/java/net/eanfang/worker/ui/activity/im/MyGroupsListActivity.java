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
import com.eanfang.util.Cn2Spell;
import com.eanfang.witget.SideBar;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.GroupsAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class MyGroupsListActivity extends BaseWorkerActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private GroupsAdapter mGroupsAdapter;
    private int flag = 0;//显示不显示checkbox的标志位
    @BindView(R.id.side_bar)
    SideBar sideBar;

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

                        for (GroupsBean bean : list) {
                            // 根据姓名获取拼音
                            bean.setPinyin(bean.getGroupName());
                            bean.setFirstLetter(Cn2Spell.getPinYin(bean.getGroupName()).substring(0, 1).toUpperCase()); // 获取拼音首字母并转成大写
                            if (!Cn2Spell.getPinYin(bean.getGroupName()).substring(0, 1).toUpperCase().matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
                                bean.setFirstLetter("#");
                            }
                            EanfangApplication.get().set(bean.getRcloudGroupId(), bean.getGroupId());
                        }

                        Collections.sort(list);
                        mGroupsAdapter.setNewData(list);

                    }
                }));
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mGroupsAdapter = new GroupsAdapter(R.layout.item_friend_list);

        mGroupsAdapter.bindToRecyclerView(recyclerView);

        sideBar.setOnStrSelectCallBack(new SideBar.ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                for (int i = 0; i < mGroupsAdapter.getData().size(); i++) {
                    if (selectStr.equalsIgnoreCase(mGroupsAdapter.getData().get(i).getFirstLetter())) {
                        recyclerView.scrollToPosition(i); // 选择到首字母出现的位置
                        return;
                    }
                }
            }
        });

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
            mGroupsAdapter.notifyDataSetChanged();
        }
        initData();
    }
}
