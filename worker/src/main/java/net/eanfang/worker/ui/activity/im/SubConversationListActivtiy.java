package net.eanfang.worker.ui.activity.im;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

public class SubConversationListActivtiy extends BaseWorkerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_conversation_list_activtiy);
        setTitle("系统消息");
        setLeftBack();
    }
}
