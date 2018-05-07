package net.eanfang.client.ui.activity.im;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.model.device.User;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.FriendsAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFriendActivity extends BaseClientActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private FriendsAdapter mFriendsAdapter;
    private String mId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);

        //用户id
        mId = getIntent().getStringExtra("id");

        initViews();
        setTitle("添加好友");
        setLeftBack();
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 0:我的好友列表没有checkbox  1:创建群组需要checkbox
        mFriendsAdapter = new FriendsAdapter(R.layout.item_friend_list, 0);
        mFriendsAdapter.bindToRecyclerView(recyclerView);

        if (!TextUtils.isEmpty(mId)) {
            etPhone.setVisibility(View.GONE);
            initIdData();
        }

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 11) {

                    initPhoneData(etPhone.getText().toString().trim());

                } else {

                }
            }
        });

        mFriendsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FriendListBean friendListBean = (FriendListBean) adapter.getData().get(position);

                if (friendListBean.getAccId().equals(String.valueOf(EanfangApplication.get().getAccId()))) {
                    ToastUtil.get().showToast(AddFriendActivity.this, "亲，自己不能加自己为好友");
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("mobile", friendListBean.getMobile());
                    jsonObject.put("email", friendListBean.getEmail());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                DialogShow(jsonObject, friendListBean, friendListBean.getNickName());

            }
        });
    }

    private void initIdData() {
        EanfangHttp.get(UserApi.POST_USER_INFO + mId)
                .execute(new EanfangCallback<User>(this, false, User.class, (bean) -> {
                    List<FriendListBean> friendListBeanList = new ArrayList<>();
                    FriendListBean friendListBean = new FriendListBean();
                    friendListBean.setAvatar(bean.getAvatar());
                    friendListBean.setNickName(bean.getNickName());
                    friendListBean.setAccId(bean.getAccId());
                    friendListBeanList.add(friendListBean);

                    mFriendsAdapter.setNewData(friendListBeanList);
                }));
    }

    /**
     * 根据手机号查找用户
     *
     * @param id
     */
    private void initPhoneData(String id) {
        EanfangHttp.post(UserApi.POST_FIND_FRIEND)
                .params("mobile", id)
//                            .params("email", etPhone.getText().toString().trim())//邮箱也可以加的
                .execute(new EanfangCallback<FriendListBean>(AddFriendActivity.this, true, FriendListBean.class, true, (list) -> {
                    if (list.size() > 0) {
                        mFriendsAdapter.setNewData(list);
                    } else {
                        mFriendsAdapter.getData().clear();
                        ToastUtil.get().showToast(AddFriendActivity.this, "没有搜索到好友");
                    }
                }));
    }

    private void DialogShow(JSONObject jsonObject, FriendListBean friendListBean, String name) {
        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle("添加好友")//设置对话框的标题
                .setMessage("您确定添加“" + name + "”为好友？")//设置对话框的内容
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
                        EanfangHttp.post(UserApi.POST_ADD_FRIEND)
                                .upJson(jsonObject)
                                .execute(new EanfangCallback<JSONObject>(AddFriendActivity.this, true, JSONObject.class, (bean) -> {


                                    EanfangHttp.post(UserApi.POST_ADD_FRIEND_PUSH)
                                            .params("senderId", EanfangApplication.get().getAccId())
                                            .params("targetIds", friendListBean.getAccId())
                                            .execute(new EanfangCallback<JSONObject>(AddFriendActivity.this, true, JSONObject.class, (json) -> {
                                                AddFriendActivity.this.finish();
                                                ToastUtil.get().showToast(AddFriendActivity.this, "发送成功");
                                            }));

                                }));
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }
}
