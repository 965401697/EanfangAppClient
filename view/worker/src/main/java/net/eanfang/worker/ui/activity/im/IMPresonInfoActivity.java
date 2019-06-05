package net.eanfang.worker.ui.activity.im;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.device.User;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
@Deprecated
public class IMPresonInfoActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.tv_friend)
    TextView tvFriend;
    private String mUserId;
    private String mTitle;
    private User mUser;
    private boolean b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impreson_info);
        ButterKnife.bind(this);
        mUserId = getIntent().getStringExtra(EanfangConst.RONG_YUN_ID);
        mTitle = getIntent().getStringExtra("title");
        setTitle(mTitle);
        setLeftBack();
        initData();
        startTransaction(true);
    }

    private void initData() {
        EanfangHttp.get(UserApi.POST_USER_INFO + mUserId)
                .execute(new EanfangCallback<User>(IMPresonInfoActivity.this, false, User.class, (bean) -> {
                    mUser = bean;
                    GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + bean.getAvatar()),ivHeader);
                    tvName.setText(bean.getNickName());
                    tvPhone.setText(bean.getMobile());
                    setTitle(bean.getNickName());
                    UserInfo userInfo = new UserInfo(bean.getAccId(), bean.getNickName(), Uri.parse(BuildConfig.OSS_SERVER + bean.getAvatar()));
                    RongIM.getInstance().refreshUserInfoCache(userInfo);

                    if (mUser.getAccId().equals(String.valueOf(WorkerApplication.get().getAccId()))) {
                        tvClear.setVisibility(View.VISIBLE);
                        b = true;//如果是自己点击自己的头像
                    } else {
                        checkFriends();
                    }
                }));
    }

    @OnClick({R.id.tv_clear, R.id.rl_info, R.id.tv_friend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clear:

                cleanGroupMsg();
                break;
            case R.id.rl_info:

                if (!b) {
                    ToastUtil.get().showToast(IMPresonInfoActivity.this, "请先添加好友");
                    return;
                }

                Intent intent = new Intent(IMPresonInfoActivity.this, IMCardActivity.class);
                intent.putExtra("user", mUser);
                startActivity(intent);
                break;

            case R.id.tv_friend:

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("mobile", mUser.getMobile());
                    jsonObject.put("email", mUser.getEmail());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DialogShow(jsonObject, mUser.getNickName());
                break;
        }
    }

    /**
     * 清空聊天记录
     */
    private void cleanGroupMsg() {

        if (RongIM.getInstance() != null) {

            RongIM.getInstance().clearMessages(Conversation.ConversationType.PRIVATE, mUserId, new RongIMClient.ResultCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    ToastUtil.get().showToast(IMPresonInfoActivity.this, "清除成功");
                    finish();
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    ToastUtil.get().showToast(IMPresonInfoActivity.this, "清除失败");
                }
            });
            RongIMClient.getInstance().cleanRemoteHistoryMessages(Conversation.ConversationType.PRIVATE, mUserId, System.currentTimeMillis(), null);
        }


    }

    private void checkFriends() {

        EanfangHttp.post(UserApi.POST_CHECK_FRIEND)
                .params("accId", mUser.getAccId())
                .params("inviteeAccId", WorkerApplication.get().getAccId())
                .execute(new EanfangCallback<String>(this, true, String.class, (s) -> {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        b = jsonObject.getBoolean("exists");

                        if (b) {
                            tvClear.setVisibility(View.VISIBLE);
                        } else {
                            tvFriend.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }));
    }

    private void DialogShow(JSONObject jsonObject, String name) {
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
                                .execute(new EanfangCallback<JSONObject>(IMPresonInfoActivity.this, true, JSONObject.class, (bean) -> {


                                    EanfangHttp.post(UserApi.POST_ADD_FRIEND_PUSH)
                                            .params("senderId", WorkerApplication.get().getAccId())
                                            .params("targetIds", mUser.getAccId())
                                            .execute(new EanfangCallback<JSONObject>(IMPresonInfoActivity.this, true, JSONObject.class, (json) -> {
                                                IMPresonInfoActivity.this.finish();
                                                ToastUtil.get().showToast(IMPresonInfoActivity.this, "发送成功");
                                            }));

                                }));
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

}
