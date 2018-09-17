package net.eanfang.worker.ui.activity.im;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.eanfang.config.EanfangConst;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

/**
 * Created by O u r on 2018/6/8.
 */

public class MyConversationClickListener implements RongIM.ConversationClickListener {

    /**
     * 当点击用户头像后执行。
     *
     * @param context          上下文。
     * @param conversationType 会话类型。
     * @param user             被点击的用户的信息。
     * @param targetId         会话 id
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo user, String targetId) {

        if (conversationType.equals(Conversation.ConversationType.PRIVATE)) {
            Intent intent = new Intent(context, IMPresonInfoActivity.class);
            intent.putExtra(EanfangConst.RONG_YUN_ID, user.getUserId());
            context.startActivity(intent);

        }

        return true;
    }

    /**
     * 当长按用户头像后执行。
     *
     * @param context          上下文。
     * @param conversationType 会话类型。
     * @param user             被点击的用户的信息。
     * @param targetId         会话 id
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo user, String targetId) {
        return false;
    }

    /**
     * 当点击消息时执行。
     *
     * @param context 上下文。
     * @param view    触发点击的 View。
     * @param message 被点击的消息的实体信息。
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true， 否则返回 false, false 走融云默认处理方式。
     */
    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        return false;
    }

    /**
     * 当点击链接消息时执行。
     *
     * @param context 上下文。
     * @param link    被点击的链接。
     * @param message 被点击的消息的实体信息
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true， 否则返回 false, false 走融云默认处理方式。
     */
    @Override
    public boolean onMessageLinkClick(Context context, String link, Message message) {
        return false;
    }

    /**
     * 当长按消息时执行。
     *
     * @param context 上下文。
     * @param view    触发点击的 View。
     * @param message 被长按的消息的实体信息。
     * @return 如果用户自己处理了长按后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }
}
