package net.eanfang.worker.ui.activity.im;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.model.device.User;
import com.eanfang.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class IMPresonInfoActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_header)
    SimpleDraweeView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_chat)
    TextView tvChat;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    private String mUserId;
    private String mTitle;

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
    }

    private void initData() {
        EanfangHttp.get(UserApi.POST_USER_INFO + mUserId)
                .execute(new EanfangCallback<User>(IMPresonInfoActivity.this, false, User.class, (bean) -> {

                    ivHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + bean.getAvatar()));
                    tvName.setText(bean.getNickName());
                    tvPhone.setText(bean.getMobile());

                    UserInfo userInfo = new UserInfo(bean.getAccId(), bean.getNickName(), Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + bean.getAvatar()));
                    RongIM.getInstance().refreshUserInfoCache(userInfo);
                }));
    }

    @OnClick({R.id.tv_chat, R.id.tv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_chat:

                finish();

                break;
            case R.id.tv_clear:

                cleanGroupMsg();
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
}