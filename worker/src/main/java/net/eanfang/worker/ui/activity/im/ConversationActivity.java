package net.eanfang.worker.ui.activity.im;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GroupDetailBean;
import com.eanfang.takevideo.TakeVdideoMode;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class ConversationActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    public String mId;

    private List<UserInfo> userInfoList = new ArrayList<>();
    private String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        String title = getIntent().getData().getQueryParameter("title").toString();//群组名称
        //单聊就是userid 群聊就是groupid
        mId = getIntent().getData().getQueryParameter("targetId").toString();
        tvTitle.setText(title);
        setLeftBack();
        endTransaction(false);
        startTransaction(true);

        mType = getIntent().getData().getLastPathSegment().toUpperCase(Locale.US);

        setRightTitle("设置");

        if (mType.equals(Conversation.ConversationType.GROUP.getName().toUpperCase(Locale.US))) {

            initData();


        }


        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                if (mType.equals(Conversation.ConversationType.GROUP.getName().toUpperCase(Locale.US))) {

                    intent.setClass(ConversationActivity.this, GroupDetailActivity.class);

                } else if (mType.equals(Conversation.ConversationType.PRIVATE.getName().toUpperCase(Locale.US))) {

                    intent.setClass(ConversationActivity.this, IMPresonInfoActivity.class);

                }


                if (!TextUtils.isEmpty(mId)) {
                    intent.putExtra(EanfangConst.RONG_YUN_ID, mId);
                    intent.putExtra("title", title);
                }
                startActivity(intent);
            }
        });

    }

    private void initData() {

        EanfangHttp.post(UserApi.POST_GROUP_DETAIL_RY)
//                .params("groupId", id)
                .params("ryGroupId", mId)
                .execute(new EanfangCallback<GroupDetailBean>(this, true, GroupDetailBean.class, (bean) -> {

                    if (bean.getList() != null) {

                        for (int i = 0; i < bean.getList().size(); i++) {

                            UserInfo userInfo = new UserInfo(String.valueOf(bean.getList().get(i).getAccId()), bean.getList().get(i).getAccountEntity().getNickName(), Uri.parse(BuildConfig.OSS_SERVER + bean.getList().get(i).getAccountEntity().getAvatar()));

                            userInfoList.add(userInfo);
                        }
                        RongIM.getInstance().setGroupMembersProvider(new RongIM.IGroupMembersProvider() {
                            @Override
                            public void getGroupMembers(String groupId, RongIM.IGroupMemberCallback callback) {

                                callback.onGetGroupMembersResult(userInfoList); // 调用 callback 的 onGetGroupMembersResult 回传群组信息

                            }
                        });
                    }
                }));

    }

    /**
     * 接受拍照小视频的发送event的
     *
     * @param takeVdideoMode
     */
    @Subscribe()//MAIN代表主线程
    public void receivePath(TakeVdideoMode takeVdideoMode) {
        if (takeVdideoMode != null) {
            sendCheckedMsg(takeVdideoMode);
        }
    }


    /**
     * 发送
     *
     * @param takeVdideoMode
     */
    private void sendCheckedMsg(TakeVdideoMode takeVdideoMode) {

        Conversation.ConversationType conversationType = null;

        CustomizeVideoMessage customizeVideoMessage = new CustomizeVideoMessage();

        customizeVideoMessage.setMp4Path(BuildConfig.OSS_SERVER + takeVdideoMode.getMKey() + ".mp4");
        customizeVideoMessage.setImgUrl(takeVdideoMode.getMKey() + ".jpg");


        if (mType.equals(Conversation.ConversationType.GROUP.getName().toUpperCase(Locale.US))) {

            conversationType = Conversation.ConversationType.GROUP;

        } else if (mType.equals(Conversation.ConversationType.PRIVATE.getName().toUpperCase(Locale.US))) {

            conversationType = Conversation.ConversationType.PRIVATE;
        }

        RongIM.getInstance().sendMessage(conversationType, mId, customizeVideoMessage, "小视频", "小视频", new RongIMClient.SendMessageCallback() {
            @Override
            public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {

            }

            @Override
            public void onSuccess(Integer integer) {

            }
        });
    }

}

