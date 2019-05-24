package net.eanfang.worker.ui.activity.im;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.FriendListBean;
import com.eanfang.biz.model.device.User;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.scancode.ScanCodeActivity;
import net.eanfang.worker.ui.adapter.FriendsAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddFriendActivity extends BaseWorkerActivity {

    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.rl_scan_friend)
    RelativeLayout rlScanFriend;
    @BindView(R.id.rl_scan_Group)
    RelativeLayout rlScanGroup;
    @BindView(R.id.ll_input)
    LinearLayout llInput;
    private FriendsAdapter mFriendsAdapter;


    // 首页扫码添加好友
    private String mAccountId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);

        initViews();
        setLeftBack();

        mAccountId = getIntent().getStringExtra("accountId");
        String addFriend = getIntent().getStringExtra("add_friend");
        if (!TextUtils.isEmpty(addFriend)) {
            setTitle("添加好友");
            rlScanGroup.setVisibility(View.INVISIBLE);
        } else {
            setTitle("添加群组");
            rlScanFriend.setVisibility(View.GONE);
            llInput.setVisibility(View.GONE);
        }

        if (!StringUtils.isEmpty(mAccountId)) {
            llInput.setVisibility(View.GONE);
            initIdData(mAccountId);
        }
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 0:我的好友列表没有checkbox  1:创建群组需要checkbox
        mFriendsAdapter = new FriendsAdapter(R.layout.item_friend_list, 0);
        mFriendsAdapter.bindToRecyclerView(recyclerView);

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

                if (friendListBean.getAccId().equals(String.valueOf(WorkerApplication.get().getAccId()))) {
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

    private void initIdData(String id) {
        EanfangHttp.get(UserApi.POST_USER_INFO + id)
                .execute(new EanfangCallback<User>(this, false, User.class, (bean) -> {
                    List<FriendListBean> friendListBeanList = new ArrayList<>();
                    FriendListBean friendListBean = new FriendListBean();

                    friendListBean.setAvatar(bean.getAvatar());
                    friendListBean.setNickName(bean.getNickName());
                    friendListBean.setAccId(bean.getAccId());
                    friendListBean.setMobile(bean.getMobile());
                    friendListBean.setEmail(bean.getEmail());

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

    @Subscribe
    public void onEventId(String id) {
        llInput.setVisibility(View.GONE);
        initIdData(id);
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
                                            .params("senderId", WorkerApplication.get().getAccId())
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

    @OnClick({R.id.rl_scan_friend, R.id.rl_scan_Group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_scan_friend:
                //跳转扫码页面
                Intent intent = new Intent(AddFriendActivity.this, ScanCodeActivity.class);
                intent.putExtra(EanfangConst.QR_ADD_FRIEND, "add_friend");
                intent.putExtra("from", "client");
                startActivity(intent);
                break;
            case R.id.rl_scan_Group:
                //跳转扫码页面
                Intent in = new Intent(AddFriendActivity.this, ScanCodeActivity.class);
                in.putExtra("from", "client");
                startActivity(in);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != CUSTOMIZED_REQUEST_CODE && requestCode != IntentIntegrator.REQUEST_CODE) {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
        if (result.getContents() == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
        }
    }
}
