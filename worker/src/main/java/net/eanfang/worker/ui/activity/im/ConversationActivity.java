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

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class ConversationActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    public String mId;

    private List<UserInfo> userInfoList = new ArrayList<>();

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

        String type = getIntent().getData().getLastPathSegment().toUpperCase(Locale.US);
        Conversation.ConversationType conversationType = Conversation.ConversationType.valueOf(type);
        setRightTitle("设置");

        if (type.equals(Conversation.ConversationType.GROUP.getName().toUpperCase(Locale.US))) {

            initData();


        }


        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                if (type.equals(Conversation.ConversationType.GROUP.getName().toUpperCase(Locale.US))) {

                    intent.setClass(ConversationActivity.this, GroupDetailActivity.class);

                } else if (type.equals(Conversation.ConversationType.PRIVATE.getName().toUpperCase(Locale.US))) {

                    intent.setClass(ConversationActivity.this, IMPresonInfoActivity.class);

                }


                if (!TextUtils.isEmpty(mId)) {
                    intent.putExtra(EanfangConst.RONG_YUN_ID, mId);
                    intent.putExtra("title", title);
                }
                startActivity(intent);
            }
        });

//        RongIMClient.setReadReceiptListener(new RongIMClient.ReadReceiptListener() {
//            @Override
//            public void onReadReceiptReceived(Message message) {
//
//                if (mId.equals(message.getTargetId()) && type.equals(message.getConversationType().getName().toUpperCase())) {
//
//                    ReadReceiptMessage content = (ReadReceiptMessage) message.getContent();
//                    long ntfTime = content.getLastMessageSendTime();    //获取发送时间戳
//                }
//                Log.e("zzw", "onReadReceiptReceived=" + message.getObjectName().toString());
//            }
//
//            @Override
//            public void onMessageReceiptRequest(Conversation.ConversationType conversationType, String s, String s1) {
//                Log.e("zzw", "onMessageReceiptRequest=" + s + "=" + s1);
//            }
//
//            @Override
//            public void onMessageReceiptResponse(Conversation.ConversationType conversationType, String s, String s1, HashMap<String, Long> hashMap) {
//                Log.e("zzw", "onMessageReceiptResponse=" + s + "=" + s1);
//            }
//        });
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
}
