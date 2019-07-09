package net.eanfang.worker.ui.activity.worksapce;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eanfang.biz.model.Message;
import com.eanfang.util.CallUtils;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify.AuthQualifyFirstActivity;
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

    // 跳转去向
    private String isGoQualify = "";
    // orgid
    private Long mOrgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_event_state);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
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
        isGoQualify = message.getMsgHelp();
        tvInfo.setText(msgContent);
        tvGo.setText(mTip);
        mOrgId = extras.getLong("orgid", 0);
    }

    @OnClick({R.id.tv_go, R.id.tv_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_go:
                if ("go_quaility".equals(isGoQualify)) {// 跳转资质证书
                    Bundle bundle = new Bundle();
                    bundle.putLong("orgid", mOrgId);
                    JumpItent.jump(StateChangeActivity.this, AuthQualifyFirstActivity.class, bundle);
                }
                finishSelf();
                break;
            case R.id.tv_phone:
                CallUtils.call(StateChangeActivity.this, tvPhone.getText().toString());
                break;
        }
    }
}
