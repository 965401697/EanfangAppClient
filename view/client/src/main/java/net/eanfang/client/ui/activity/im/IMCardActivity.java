package net.eanfang.client.ui.activity.im;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.config.Config;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.device.User;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.ToastUtil;
import com.picker.common.util.DateUtils;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
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
    CircleImageView ivHeader;
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
    @BindView(R.id.tv_delete)
    TextView tvDelete;
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
        GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + mUser.getAvatar()),ivHeader);
        tvNikeName.setText(mUser.getNickName());
        if (mUser.getGender() == 1) {
            ivSex.setImageDrawable(getResources().getDrawable(R.mipmap.ic_man));
        } else {
            ivSex.setImageDrawable(getResources().getDrawable(R.mipmap.ic_woman));
        }

        tvName.setText("真实姓名：" + mUser.getRealName());
        if (mUser.getBirthday() != null) {
            tvBirthday.setText("生日：" + DateUtils.formatDate(mUser.getBirthday(), "yyyy-MM-dd"));
        } else {
            tvBirthday.setVisibility(View.GONE);
        }
        tvArea.setText("所在区域：" + Config.get().getAddressByCode(mUser.getAreaCode()));
        if (mUser.getAccId().equals(String.valueOf(ClientApplication.get().getAccId()))) {
            tvDelete.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.tv_delete, R.id.tv_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_delete:
                new TrueFalseDialog(this, "系统提示", "您确定删除该好友？", () -> {
                    delectFriend();
                }).showDialog();
                break;
            case R.id.tv_chat:
                RongIM.getInstance().startConversation(IMCardActivity.this, Conversation.ConversationType.PRIVATE, mUser.getAccId(), mUser.getNickName());
                endTransaction(true);
                break;
        }
    }

    private void delectFriend() {
        //删除好友
        EanfangHttp.post(UserApi.POST_DELETE_FRIEND)
                .params("ids", mUser.getAccId())
                .execute(new EanfangCallback<JSONObject>(IMCardActivity.this, true, org.json.JSONObject.class, (bean) -> {

                    EanfangHttp.post(UserApi.POST_DELETE_FRIEND_PUSH)
                            .params("senderId", ClientApplication.get().getAccId())
                            .params("targetIds", mUser.getAccId())
                            .execute(new EanfangCallback<JSONObject>(IMCardActivity.this, true, JSONObject.class, (json) -> {
                                ToastUtil.get().showToast(IMCardActivity.this, "删除成功");
                                endTransaction(true);
                            }));
                    RongIM.getInstance().clearMessages(Conversation.ConversationType.PRIVATE, mUser.getAccId(), null);
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

