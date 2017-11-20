package net.eanfang.client.ui.activity.my;

import android.os.Bundle;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseActivity;

/**
 * Created by MrHou
 *
 * @on 2017/11/16  13:57
 * @email houzhongzhou@yeah.net
 * @desc 推送消息列表
 */

public class MessageListActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
    }

    private void initView() {
        setTitle("通知");
        setLeftBack();
    }


}
