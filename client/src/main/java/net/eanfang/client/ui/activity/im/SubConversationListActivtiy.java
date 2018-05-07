package net.eanfang.client.ui.activity.im;

import android.os.Bundle;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

public class SubConversationListActivtiy extends BaseClientActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_conversation_list_activtiy);
        setTitle("系统消息");
        setLeftBack();
        startTransaction(true);
    }
}
