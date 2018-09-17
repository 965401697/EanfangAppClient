package net.eanfang.client.ui.activity.im;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class SystemMessageActivity extends BaseClientActivity {

    @BindView(R.id.iv_icon)
    SimpleDraweeView ivIocn;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.tv_accept)
    TextView tvAccept;
    @BindView(R.id.ll_item)
    RelativeLayout llItem;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message);
        ButterKnife.bind(this);
        setTitle("系统消息");
        setLeftBack();
        userInfo = (UserInfo) getIntent().getParcelableExtra("sendUserInfo");

        initViews();
    }

    private void initViews() {
        tvName.setText(userInfo.getName());
        tvMsg.setText("想添加您为好友");
        if (userInfo.getPortraitUri() != null) {
            ivIocn.setImageURI(Uri.parse(userInfo.getPortraitUri().toString()));
        }

        int unreadCount = RongIM.getInstance().getUnreadCount(Conversation.ConversationType.SYSTEM, userInfo.getUserId());
        if (unreadCount == 0) {
            btnAccept.setVisibility(View.GONE);
            tvAccept.setVisibility(View.VISIBLE);
        } else {
            btnAccept.setVisibility(View.VISIBLE);
            tvAccept.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.btn_accept, R.id.ll_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_accept:
                //同意被添加好友
                EanfangHttp.post(UserApi.POST_ACCEPT_FRIEND)
                        .params("ids", userInfo.getUserId())
                        .execute(new EanfangCallback<JSONObject>(SystemMessageActivity.this, true, JSONObject.class, (bean) -> {


                            RongIM.getInstance().clearMessagesUnreadStatus(Conversation.ConversationType.SYSTEM, userInfo.getUserId(),
                                    new RongIMClient.ResultCallback<Boolean>() {
                                        @Override
                                        public void onSuccess(Boolean aBoolean) {
                                        }

                                        @Override
                                        public void onError(RongIMClient.ErrorCode errorCode) {
                                        }
                                    });
                            RongIM.getInstance().startConversation(SystemMessageActivity.this, Conversation.ConversationType.PRIVATE, userInfo.getUserId(), userInfo.getName());

                           endTransaction(true);

                        }));
                break;
            case R.id.ll_item:
                RongIM.getInstance().startConversation(SystemMessageActivity.this, Conversation.ConversationType.PRIVATE, userInfo.getUserId(), userInfo.getName());
                break;
        }
    }
}
