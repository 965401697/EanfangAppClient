package net.eanfang.worker.ui.activity.im;

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

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class IMCardActivity extends BaseWorkerActivity {

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
        setContentView(R.layout.activity_imcard);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
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

        if (mUser.getAccId().equals(String.valueOf(WorkerApplication.get().getAccId()))) {
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
                .execute(new EanfangCallback<JSONObject>(IMCardActivity.this, true, JSONObject.class, (bean) -> {

                    EanfangHttp.post(UserApi.POST_DELETE_FRIEND_PUSH)
                            .params("senderId", WorkerApplication.get().getAccId())
                            .params("targetIds", mUser.getAccId())
                            .execute(new EanfangCallback<JSONObject>(IMCardActivity.this, true, JSONObject.class, (json) -> {
                                ToastUtil.get().showToast(IMCardActivity.this, "删除成功");
                                endTransaction(true);
                            }));
                    RongIM.getInstance().clearMessages(Conversation.ConversationType.PRIVATE, mUser.getAccId(), null);
                    RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, mUser.getAccId(), null);
                }));

    }

}
