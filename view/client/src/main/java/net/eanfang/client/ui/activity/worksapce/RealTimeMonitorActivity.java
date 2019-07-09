package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.CallUtils;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2018/10/29
 * @description 实施监控
 */

public class RealTimeMonitorActivity extends BaseActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.rl_consult)
    RelativeLayout rlConsult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_real_time_monitor);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.iv_left, R.id.rl_consult})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finishSelf();
                break;
            case R.id.rl_consult:
                //给客户联系人打电话
                CallUtils.call(RealTimeMonitorActivity.this, "400-890-9280");
                break;
        }
    }
}
