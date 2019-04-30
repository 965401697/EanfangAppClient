package net.eanfang.worker.ui.activity.im;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.widget.adapter.ConversationListAdapter;

/**
 * Created by O u r on 2018/5/11.
 */


public class MyConversationListFragment extends ConversationListFragment {


    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);
        return mView;
    }

    public View getmView() {
        return mView;
    }

    @Override
    public ConversationListAdapter onResolveAdapter(Context context) {

        return new MyConversationListAdapter(context);
    }
}


