package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eanfang.application.EanfangApplication;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

public class ConversationActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        String title = getIntent().getData().getQueryParameter("title").toString();
        String id = getIntent().getData().getQueryParameter("targetId").toString();
        tvTitle.setText(title);
        setLeftBack();

        String type = getIntent().getData()
                .getLastPathSegment().toUpperCase(Locale.US);
        if (type.equals("GROUP")) {
            setRightTitle("设置");
            setRightTitleOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(ConversationActivity.this, GroupDetailActivity.class);
                    if (!TextUtils.isEmpty(id)) {
                        intent.putExtra("rongyun_group_id", id);
                        intent.putExtra("title",title);
                    }
                    startActivity(intent);
                }
            });
        }
    }

}
