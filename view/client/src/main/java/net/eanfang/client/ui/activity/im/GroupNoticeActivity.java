package net.eanfang.client.ui.activity.im;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.MentionedInfo;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

public class GroupNoticeActivity extends BaseClientActivity {

    @BindView(R.id.edit_area)
    EditText mEdit;
    private Conversation.ConversationType mConversationType;
    private String mTargetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_notice);
        ButterKnife.bind(this);
        setTitle("群公告");
        setLeftBack();
        setRightTitle("完成");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAll();
            }
        });
        mConversationType = Conversation.ConversationType.setValue(getIntent().getIntExtra("conversationType", 0));
        mTargetId = getIntent().getStringExtra("targetId");
    }

    /**
     * 发送给全部人员
     */
    private void sendAll() {
        TextMessage textMessage = TextMessage.obtain(RongContext.getInstance().getString(R.string.group_notice_prefix) + mEdit.getText().toString());
        MentionedInfo mentionedInfo = new MentionedInfo(MentionedInfo.MentionedType.ALL, null, null);
        textMessage.setMentionedInfo(mentionedInfo);

        RongIM.getInstance().sendMessage(Message.obtain(mTargetId, mConversationType, textMessage), null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {

            }

            @Override
            public void onSuccess(Message message) {
                Intent intent = new Intent();
                intent.putExtra("notice", mEdit.getText().toString().trim());
                setResult(RESULT_OK, intent);
                GroupNoticeActivity.this.finish();
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {

            }
        });

    }
}
