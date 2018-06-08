package net.eanfang.client.ui.activity.im;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.device.User;
import com.eanfang.util.ToastUtil;
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

/**
 * Created by O u r on 2018/6/8.
 */

public class IMCardActivity extends BaseClientActivity {

    @BindView(R.id.iv_header)
    SimpleDraweeView ivHeader;
    @BindView(R.id.tv_nike_name)
    TextView tvNikeName;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_area)
    TextView tvArea;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imcard);
        ButterKnife.bind(this);
        mUser = (User) getIntent().getSerializableExtra("user");
        setTitle("详细信息");
        setLeftBack();
        initView();
    }

    private void initView() {

        ivHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + mUser.getAvatar()));
        tvNikeName.setText(mUser.getNickName());
        if (mUser.getGender() == 1) {
            ivSex.setImageDrawable(getResources().getDrawable(R.mipmap.ic_man));
        } else {
            ivSex.setImageDrawable(getResources().getDrawable(R.mipmap.ic_woman));
        }

        tvName.setText("真实姓名：" + mUser.getRealName());
//        tvBirthday.setText("生日：" + mUser.get());
        tvArea.setText("所在区域：" + Config.get().getAddressByCode(mUser.getAreaCode()));
    }

    @OnClick({R.id.tv_delete, R.id.tv_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_delete:
                delectFriend();
                break;
            case R.id.tv_chat:
                RongIM.getInstance().startConversation(IMCardActivity.this, Conversation.ConversationType.PRIVATE, mUser.getAccId(), mUser.getNickName());

                break;
        }
    }

    private void delectFriend() {
        //删除好友
        EanfangHttp.post(UserApi.POST_DELETE_FRIEND)
                .params("ids", mUser.getAccId())
                .execute(new EanfangCallback<JSONObject>(IMCardActivity.this, true, org.json.JSONObject.class, (bean) -> {

                    EanfangHttp.post(UserApi.POST_DELETE_FRIEND_PUSH)
                            .params("senderId", EanfangApplication.get().getAccId())
                            .params("targetIds", mUser.getAccId())
                            .execute(new EanfangCallback<JSONObject>(IMCardActivity.this, true, JSONObject.class, (json) -> {
                                ToastUtil.get().showToast(IMCardActivity.this, "删除成功");
                                endTransaction(true);
                            }));

                    RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, mUser.getAccId(), new RongIMClient.ResultCallback<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {

                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {

                        }
                    });
                }));

    }

}

