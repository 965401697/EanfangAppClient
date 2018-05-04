package net.eanfang.worker.ui.activity.im;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.device.User;
import com.eanfang.util.SoftHideKeyBoardUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class ConversationActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    public String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        String title = getIntent().getData().getQueryParameter("title").toString();//群组名称
        //单聊就是userid 群聊就是groupid
        mId = getIntent().getData().getQueryParameter("targetId").toString();
        tvTitle.setText(title);
        setLeftBack();
        endTransaction(false);
        startTransaction(true);

        String type = getIntent().getData().getLastPathSegment().toUpperCase(Locale.US);

        setRightTitle("设置");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                if (type.equals(Conversation.ConversationType.GROUP.getName().toUpperCase(Locale.US))) {

                    intent.setClass(ConversationActivity.this, GroupDetailActivity.class);

                } else if (type.equals(Conversation.ConversationType.PRIVATE.getName().toUpperCase(Locale.US))) {

                    intent.setClass(ConversationActivity.this, IMPresonInfoActivity.class);

                }


                if (!TextUtils.isEmpty(mId)) {
                    intent.putExtra(EanfangConst.RONG_YUN_ID, mId);
                    intent.putExtra("title", title);
                }
                startActivity(intent);
            }
        });
    }
}
