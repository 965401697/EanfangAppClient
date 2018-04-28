package net.eanfang.worker.ui.activity.im;

import android.content.Intent;
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
import io.rong.imkit.RongIM;

public class SelectedFriendsActivity extends BaseWorkerActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private FriendsAdapter mFriendsAdapter;
    private ArrayList<String> mUserIdList = new ArrayList<String>();
    private int mFlag;
    private String mGroupId;
    private FriendListBean mCurrentBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_friends);
        ButterKnife.bind(this);
        setTitle("选择好友");
        setLeftBack();

        //1:创建选着好友  2：群组添加好友
        mFlag = getIntent().getIntExtra("flag", 0);
        rightTitleOnClick(mFlag);

        initViews();
        initData();

        startTransaction(true);
    }


    private void rightTitleOnClick(int flag) {
        if (flag == 1) {
            setRightTitle("下一步");
        } else {
            setRightTitle("确定");
            mGroupId = getIntent().getStringExtra("groupId");
        }

        setRightTitleOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (flag == 1) {
                    if (mUserIdList.size() == 1) {
                        //说明单聊
                        RongIM.getInstance().startPrivateChat(SelectedFriendsActivity.this, mUserIdList.get(0), mCurrentBean.getNickName());
                    } else {

                        if (mUserIdList.size() <= 1) {
                            ToastUtil.get().showToast(SelectedFriendsActivity.this, "请至少选择一位好友开始聊天");
                            return;
                        }

                        Intent intent = new Intent(SelectedFriendsActivity.this, GroupCreatActivity.class);
                        intent.putStringArrayListExtra("userIdList", mUserIdList);
                        startActivity(intent);
                    }
                } else {
                    AddNumber();
                }
            }
        });
    }


    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFriendsAdapter = new FriendsAdapter(R.layout.item_friend_list, mFlag);

        mFriendsAdapter.bindToRecyclerView(recyclerView);

        selectFriends();

    }

    private void initData() {

        if (mFlag == 2) {
            //查找没有在群组的好友
            EanfangHttp.post(UserApi.POST_GROUP_NOJOIN)
                    .params("groupId", mGroupId)
                    .params("accId", EanfangApplication.get().getAccId())
                    .execute(new EanfangCallback<FriendListBean>(this, true, FriendListBean.class, true, (list) -> {
                        if (list.size() > 0) {
                            mFriendsAdapter.setNewData(list);
                        }
                    }));
        } else {
            EanfangHttp.post(UserApi.POST_FRIENDS_LIST)
                    .params("accId", EanfangApplication.get().getAccId())
                    .execute(new EanfangCallback<FriendListBean>(this, true, FriendListBean.class, true, (list) -> {
                        if (list.size() > 0) {
                            mFriendsAdapter.setNewData(list);
                        }
                    }));
        }

    }

    /**
     * 添加成员
     */
    private void AddNumber() {

        if (mUserIdList.size() == 0) {
            ToastUtil.get().showToast(this, "至少选择一个好友");
            return;
        }

        JSONArray array = new JSONArray();
        JSONObject object = null;
        for (String s : mUserIdList) {
            object = new JSONObject();
            try {
                object.put("accId", s);
                object.put("groupId", mGroupId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(object);
        }
        EanfangHttp.post(UserApi.POST_GROUP_JOIN)
                .upJson(array)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                    ToastUtil.get().showToast(SelectedFriendsActivity.this, "添加成功");
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
                        mCurrentBean = bean;
                    }

                }
            }
        });
    }
}
