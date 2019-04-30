package net.eanfang.client.ui.activity.im;

import android.content.Context;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.widget.adapter.ConversationListAdapter;

/**
 * Created by O u r on 2018/5/11.
 */


public class MyConversationListFragment extends ConversationListFragment {




    @Override
    public ConversationListAdapter onResolveAdapter(Context context) {

        return new MyConversationListAdapter(context);
    }
}


