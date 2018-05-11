package net.eanfang.worker.ui.activity.im;

import android.content.Context;
import android.view.View;


import net.eanfang.worker.R;

import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.model.Conversation;

/**
 * Created by O u r on 2018/5/11.
 */

public class MyConversationListAdapter extends ConversationListAdapter {
    public MyConversationListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindView(View v, int position, UIConversation data) {
        super.bindView(v, position, data);
        if (data.getConversationType() == Conversation.ConversationType.SYSTEM) {

            AsyncImageView img = v.findViewById(io.rong.imkit.R.id.rc_left);

            img.setAvatar(null, R.mipmap.rc_default_system);//设置头像

        }
    }
}