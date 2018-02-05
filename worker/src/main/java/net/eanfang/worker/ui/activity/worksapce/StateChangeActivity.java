package net.eanfang.worker.ui.activity.worksapce;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.model.Message;
import com.eanfang.util.CallUtils;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;


/**
 * 状态变化的activity 如发布成功，接单
 * Created by wen on 2017/3/23.
 */

public class StateChangeActivity extends BaseWorkerActivity {


    private Context mContext = this;
    private LinearLayout ll_message;
    private TextView tv_msg_title;
    private TextView tv_msg_content;
    private TextView tv_msg_help;
    private LinearLayout ll_logo;
    private TextView tv_phone;
    private TextView tv_tip;
    private TextView tv_ok;
    private LinearLayout ll_ok;
    private ImageView iv_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_state);
        Bundle extras = getIntent().getExtras();
        bindViews();
        // supprotToolbar();
        showViewType(extras);
    }


    private void bindViews() {
        ll_message = (LinearLayout) findViewById(R.id.ll_message);
        tv_msg_title = (TextView) findViewById(R.id.tv_msg_title);
        tv_msg_content = (TextView) findViewById(R.id.tv_msg_content);
        tv_msg_help = (TextView) findViewById(R.id.tv_msg_help);
        ll_logo = (LinearLayout) findViewById(R.id.ll_logo);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        ll_ok = (LinearLayout) findViewById(R.id.ll_ok);
        iv_phone = (ImageView) findViewById(R.id.iv_phone);
    }


    private void showViewType(Bundle extras) {

        Message message = (Message) extras.getSerializable("message");


        String title = message.getTitle();
        String msgTitle = message.getMsgTitle();
        String msgContent = message.getMsgContent();
        String msgHelp = message.getMsgHelp();
        boolean isShowLogo = message.isShowLogo();
        boolean isShowOkBtn = message.isShowOkBtn();
        String tip = message.getTip();
        setTitle(title);

        tv_msg_title.setText(msgTitle);
        tv_msg_content.setText(msgContent);
        if (StringUtils.isValid(msgHelp)) {
            tv_msg_help.setVisibility(View.VISIBLE);
            tv_msg_help.setText(msgHelp);
        }
        if (isShowLogo) {
            ll_logo.setVisibility(View.VISIBLE);
        }

        if (isShowOkBtn) {
            ll_ok.setVisibility(View.VISIBLE);
            tv_ok.setOnClickListener(v -> finishSelf());
        } else {
            ll_ok.setVisibility(View.GONE);
        }

        if (StringUtils.isValid(tip)) {
            tv_tip.setVisibility(View.VISIBLE);
            tv_tip.setText(tip);
        }

        iv_phone.setOnClickListener(v -> {
            //增加
            CallUtils.call(mContext, tv_phone.getText().toString());
        });

    }
}
