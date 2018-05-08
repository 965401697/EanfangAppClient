package net.eanfang.client.ui.activity.im;

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
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.FriendsAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;


import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubtractFriendsActivity extends BaseClientActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private FriendsAdapter mFriendsAdapter;

    private ArrayList<FriendListBean> mFriendListBeanArrayList;
    private String mGroupId;
    private String mTitle;
    private ArrayList<String> mUserIdList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtract_friends);
        ButterKnife.bind(this);
        setTitle("群组移除好友");
        setRightTitle("确定");
        setLeftBack();
        mFriendListBeanArrayList = (ArrayList<FriendListBean>) getIntent().getSerializableExtra("list");
        mGroupId = getIntent().getStringExtra("groupId");
        mTitle = getIntent().getStringExtra("title");
        startTransaction(true);

        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeNumber();
            }
        });
        initViews();
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFriendsAdapter = new FriendsAdapter(R.layout.item_friend_list, 1);//标志位 就是为了显示checkbox
        mFriendsAdapter.bindToRecyclerView(recyclerView);

        mFriendsAdapter.setNewData(mFriendListBeanArrayList);
        selectFriends();
    }

    /**
     * 移除组内成员
     */
    private void removeNumber() {

        if (mUserIdList.size() == 0) {
            ToastUtil.get().showToast(this, "至少选择一个好友");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < mUserIdList.size(); i++) {
            if (i == 0) {
                buffer.append(mUserIdList.get(i));
            } else {
                buffer.append("," + mUserIdList.get(i));
            }
        }

        EanfangHttp.post(UserApi.POST_GROUP_KICKOUT)
                .params("groupId", mGroupId)
                .params("ids", buffer.toString())
                .params("groupName", mTitle)
                .params("senderId", EanfangApplication.get().getAccId())
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                    ToastUtil.get().showToast(SubtractFriendsActivity.this, "移除成功");
                    setResult(RESULT_OK);
                    endTransaction(true);
                }));
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
                        mUserIdList.remove(bean.getAccId());
                        bean.setFlag(0);
                    } else {
                        mUserIdList.add(bean.getAccId());
                        bean.setFlag(1);
                    }

                }
            }
        });
    }

}