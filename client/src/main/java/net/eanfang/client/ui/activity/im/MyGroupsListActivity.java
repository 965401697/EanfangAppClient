package net.eanfang.client.ui.activity.im;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.model.GroupDetailBean;
import com.eanfang.model.GroupsBean;
import com.eanfang.model.TemplateBean;
import com.eanfang.util.Cn2Spell;
import com.eanfang.util.DialogUtil;
import com.eanfang.util.ToastUtil;
import com.eanfang.witget.SideBar;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.GroupsAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imkit.mention.MemberMentionedActivity;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class MyGroupsListActivity extends BaseClientActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private GroupsAdapter mGroupsAdapter;
    @BindView(R.id.side_bar)
    SideBar sideBar;

    private boolean isVisible;
    private List<TemplateBean.Preson> groupPresonList = new ArrayList<>();//选中群组的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups_list);
        ButterKnife.bind(this);

        setTitle("我的群组");
        setLeftBack();
        isVisible = getIntent().getBooleanExtra("isVisible", false);
        initViews();

        if (isVisible) {
            setRightTitle("确定");
            setRightTitleOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(groupPresonList);
                    endTransaction(true);
                }
            });
        }

    }

    private void initData() {
        EanfangHttp.post(UserApi.POST_GET_GROUP)
                .params("accId", EanfangApplication.get().getAccId())
                .execute(new EanfangCallback<GroupsBean>(this, true, GroupsBean.class, true, (list) -> {
                    if (list.size() > 0) {

                        for (GroupsBean bean : list) {
                            // 根据姓名获取拼音
                            if (!TextUtils.isEmpty(bean.getGroupName())) {//不等于空
                                bean.setPinyin(bean.getGroupName());
                                bean.setFirstLetter(Cn2Spell.getPinYin(bean.getGroupName()).substring(0, 1).toUpperCase()); // 获取拼音首字母并转成大写
                                if (!Cn2Spell.getPinYin(bean.getGroupName()).substring(0, 1).toUpperCase().matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
                                    bean.setFirstLetter("#");
                                }
                                EanfangApplication.get().set(bean.getRcloudGroupId(), bean.getGroupId());
                            } else {
                                //为空赋值默认值
                                bean.setPinyin("");
                                bean.setFirstLetter("#");
                                EanfangApplication.get().set(bean.getRcloudGroupId(), bean.getGroupId());
                            }
                        }


                        List<GroupsBean> groupsBeanList = list;

                        Collections.sort(groupsBeanList, new GroupsBean());

                        mGroupsAdapter.setNewData(groupsBeanList);
                    }
                }));
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mGroupsAdapter = new GroupsAdapter(R.layout.item_friend_list, isVisible);

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

        if (!isVisible) {

            mGroupsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                    RongIM.getInstance().startConversation(MyGroupsListActivity.this, Conversation.ConversationType.GROUP, ((GroupsBean) adapter.getData().get(position)).getRcloudGroupId(), ((GroupsBean) adapter.getData().get(position)).getGroupName());

                }
            });
        } else {
            mGroupsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.cb_checked) {
                        GroupsBean groupsBean = (GroupsBean) adapter.getData().get(position);

                        if (groupsBean.isChecked()) {
                            groupPresonList.remove(position);
                        } else {
                            TemplateBean.Preson preson = new TemplateBean.Preson();

                            preson.setId(groupsBean.getRcloudGroupId());
                            preson.setProtraivat(groupsBean.getHeadPortrait());
                            preson.setName(groupsBean.getGroupName());
                            groupPresonList.add(preson);
                        }
                    }
                }
            });
        }
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
