package net.eanfang.worker.ui.activity.worksapce;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eanfang.model.Message;
import com.eanfang.util.CallUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 状态变化的activity 如发布成功，接单
 * Created by wen on 2017/3/23.
 */

public class StateChangeActivity extends BaseWorkerActivity {


    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_go)
    TextView tvGo;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_state);
        ButterKnife.bind(this);
        initView();
        Bundle extras = getIntent().getExtras();
        showViewType(extras);
    }

    private void initView() {
        setTitle("提交成功");
        setLeftBack();
        tvPhone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
    }

    private void showViewType(Bundle extras) {

        Message message = (Message) extras.getSerializable("message");
        String msgContent = message.getMsgContent();
        String mTip = message.getTip();
        tvInfo.setText(msgContent);
        tvGo.setText(mTip);
    }

    @OnClick({R.id.tv_go, R.id.tv_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_go:
                finishSelf();
                break;
            case R.id.tv_phone:
                CallUtils.call(StateChangeActivity.this, tvPhone.getText().toString());
                break;
        }
    }
}
