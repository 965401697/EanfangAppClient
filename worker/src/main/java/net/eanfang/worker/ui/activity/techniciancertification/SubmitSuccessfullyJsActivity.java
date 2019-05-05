package net.eanfang.worker.ui.activity.techniciancertification;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eanfang.ui.base.BaseActivity;
import com.yaf.base.entity.TechWorkerVerifyEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.my.certification.OtherDataActivity;
import net.eanfang.worker.ui.activity.my.certification.SkillTypeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author WQ
 */
public class SubmitSuccessfullyJsActivity extends BaseActivity {
    @BindView(R.id.rz_lc_tv)
    TextView rzLcTv;
    @BindView(R.id.authentication_tv)
    TextView authenticationTv;
    @BindView(R.id.call_tv)
    TextView callTv;
    private Long mOrgId;
    private int status = 0;
    private int order = 1;
    private TechWorkerVerifyEntity mTechWorkerVerifyEntity;
    private int statusB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_successfully2);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setLeftBack();
        setTitle("提交成功");
    }

    private void initData() {
        mTechWorkerVerifyEntity = (TechWorkerVerifyEntity) getIntent().getSerializableExtra("bean");
        statusB = getIntent().getIntExtra("statusB", -1);
        order = getIntent().getIntExtra("order", 1);

        switch (order) {
            case 1:
                authenticationTv.setText("去添加服务认证");
                break;
            case 2:
                authenticationTv.setText("去完善保障信息");
                break;
            case 3:
                authenticationTv.setText("去完善资质与能力");
                break;
            case 4:
                authenticationTv.setText("");
                break;
            default:
        }
    }

    @OnClick({R.id.authentication_tv, R.id.call_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.authentication_tv:
                switch (order) {
                    case 1:
                        startActivity(new Intent(this, SkillTypeActivity.class).putExtra("status", statusB));
                        break;
                    case 2:
                        startActivity(new Intent(this, OtherDataActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(this, JsQualificationsAndAbilitiesActivity.class));
                        break;
                    case 4:
                        break;
                    default:
                }
                finish();
                break;
            case R.id.call_tv:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "01058778731"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            default:
        }
    }


}
