package net.eanfang.worker.ui.activity.im;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;


import net.eanfang.worker.R;

import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.model.Conversation;

/**
 * Created by O u r on 2018/5/11.
 */

public class MyConversationListAdapter extends ConversationListAdapter {
    private Context mContext;
    public MyConversationListAdapter(Context context) {
        super(context);
        this.mContext=context;
    }

    @Override
    protected void bindView(View v, int position, UIConversation data) {
        super.bindView(v, position, data);
        if (data == null) return;//防止 加群 入群的崩溃问题
        if (data.getConversationType() == Conversation.ConversationType.SYSTEM) {

            AsyncImageView img = v.findViewById(io.rong.imkit.R.id.rc_left);

            img.setAvatar(null, R.mipmap.rc_default_system);//设置头像

        }
        FrameLayout leftUnReadView = v.findViewById(io.rong.imkit.R.id.rc_unread_view_left);
        FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) leftUnReadView.getLayoutParams();
        params.leftMargin = (int)this.mContext.getResources().getDimension(io.rong.imkit.R.dimen.rc_dimen_size_40);
        params.topMargin = (int)mContext.getResources().getDimension(io.rong.imkit.R.dimen.rc_dimen_size_7);
        leftUnReadView.setLayoutParams(params);
    }
}