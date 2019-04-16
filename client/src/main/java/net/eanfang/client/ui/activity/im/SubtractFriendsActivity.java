package net.eanfang.client.ui.activity.im;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GroupDetailBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.UuidUtil;
import com.eanfang.util.compound.CompoundHelper;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;

public class SubtractFriendsActivity extends BaseClientActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private GroupFriendsAdapter mGroupFriendsAdapter;

    private ArrayList<GroupDetailBean.ListBean> mFriendListBeanArrayList;
    private String mGroupId;
    private String mRYGroupId;
    private String mTitle;
    private ArrayList<String> mUserIdList = new ArrayList<String>();
    private ArrayList<GroupDetailBean.ListBean> mSubUserIconList = new ArrayList<GroupDetailBean.ListBean>();

    private boolean isCompound;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String path = (String) msg.obj;

            if (!TextUtils.isEmpty(path)) {
                String inageKey = UuidUtil.getUUID() + ".png";
                OSSUtils.initOSS(SubtractFriendsActivity.this).asyncPutImage(inageKey, path, new OSSCallBack(SubtractFriendsActivity.this, false) {

                    @Override
                    public void onOssSuccess() {
//                        super.onOssSuccess();
                        updataGroupInfo(mTitle, inageKey, "", "");
                    }
                });
                SubtractFriendsActivity.this.setResult(RESULT_OK);
                SubtractFriendsActivity.this.endTransaction(true);
            }

        }
    };
    private ArrayList<GroupDetailBean.ListBean> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtract_friends);
        ButterKnife.bind(this);
        setTitle("群组移除好友");
        setRightTitle("确定");
        setLeftBack();
        mFriendListBeanArrayList = (ArrayList<GroupDetailBean.ListBean>) getIntent().getSerializableExtra("list");
        mGroupId = getIntent().getStringExtra("groupId");
        mRYGroupId = getIntent().getStringExtra("ryGroupId");
        mTitle = getIntent().getStringExtra("title");
        //是否是合成的头像
        isCompound =getIntent().getBooleanExtra("isCompound", true);
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
        mGroupFriendsAdapter = new GroupFriendsAdapter(R.layout.item_friend_list);//标志位 就是为了显示checkbox
        mGroupFriendsAdapter.bindToRecyclerView(recyclerView);
        mList = new ArrayList<>();
        for (GroupDetailBean.ListBean bean : mFriendListBeanArrayList) {
            if (!bean.getAccId().equals(String.valueOf(EanfangApplication.get().getAccId()))) {
                mList.add(bean);//自己不能删除自己
            }
        }
        mGroupFriendsAdapter.setNewData(mList);
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

        List<String> list = new ArrayList<>();

        mFriendListBeanArrayList.removeAll(mSubUserIconList);

        for (GroupDetailBean.ListBean bean : mFriendListBeanArrayList) {
            list.add(bean.getAccountEntity().getAvatar());
        }

//        list.add(EanfangApplication.get().getUser().getAccount().getAvatar());
        if(isCompound) {
            CompoundHelper.getInstance().sendBitmap(this, handler, list);//生成图片
        }

        EanfangHttp.post(UserApi.POST_GROUP_KICKOUT)
                .params("groupId", mGroupId)
                .params("ids", buffer.toString())
                .params("groupName", mTitle)
                .params("senderId", EanfangApplication.get().getAccId())
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                    ToastUtil.get().showToast(SubtractFriendsActivity.this, "移除成功");
                    if(!isCompound){
                        SubtractFriendsActivity.this.setResult(RESULT_OK);
                        SubtractFriendsActivity.this.endTransaction(true);
                    }
                }));
    }

    /**
     * 选择好友
     */
    private void selectFriends() {
        mGroupFriendsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //创建群组选着好友
                if (view.getId() == R.id.cb_checked) {

                    GroupDetailBean.ListBean bean = (GroupDetailBean.ListBean) adapter.getData().get(position);
                    if (bean.getFlag() == 1) {
                        //移除
                        mUserIdList.remove(bean.getAccId());
                        mSubUserIconList.remove(bean);
                        bean.setFlag(0);

                    } else {
                        mUserIdList.add(bean.getAccId());
                        mSubUserIconList.add(bean);
                        bean.setFlag(1);
                    }

                }
            }
        });
    }

    /**
     * 更新群组信息
     */
    public void updataGroupInfo(String title, String imgKey, String transfer, String notice) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("groupId", mGroupId);

            if (!TextUtils.isEmpty(title)) {
                jsonObject.put("groupName", title);
            }
            if (!TextUtils.isEmpty(imgKey)) {
                jsonObject.put("headPortrait", imgKey);
            }
            if (!TextUtils.isEmpty(transfer)) {
                jsonObject.put("create_user", transfer);
            }
            if (!TextUtils.isEmpty(notice)) {
                jsonObject.put("notice", notice);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //创建群组
        EanfangHttp.post(UserApi.POST_UPDATA_GROUP)
                .upJson(jsonObject)
                .execute(new EanfangCallback<JSONObject>(SubtractFriendsActivity.this, false, JSONObject.class, (JSONObject) -> {
                    Group groupInfo = new Group(mRYGroupId, title, Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + imgKey));
                    RongIM.getInstance().refreshGroupInfoCache(groupInfo);

                }));
    }

}
