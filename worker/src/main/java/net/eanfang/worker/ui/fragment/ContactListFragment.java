package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.eanfang.ui.base.BaseFragment;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.my.MessageListActivity;

/**
 * Created by MrHou
 *
 * @on 2018/3/2  16:53
 * @email houzhongzhou@yeah.net
 * @desc 消息
 */

public class ContactListFragment extends BaseFragment {
    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        findViewById(R.id.ll_msg_list).setOnClickListener(v -> startActivity(new Intent(getActivity(), MessageListActivity.class)));
    }

    @Override
    protected void setListener() {

    }
}