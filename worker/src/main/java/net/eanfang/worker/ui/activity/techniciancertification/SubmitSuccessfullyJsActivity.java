package net.eanfang.worker.ui.activity.techniciancertification;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
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
    @BindView(R.id.ts_tv)
    TextView tsTv;
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
        SpannableString spannableString = new SpannableString("实名认证➝服务认证➝资质能力➝其他信息");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#A5CAFC")), 4, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 4, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#A5CAFC")), 9, 10, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 9, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 10, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#A5CAFC")), 14, 15, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 14, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 15, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        rzLcTv.setText(spannableString);
    }

    private void initData() {
        mTechWorkerVerifyEntity = (TechWorkerVerifyEntity) getIntent().getSerializableExtra("bean");
        statusB = getIntent().getIntExtra("statusB", -1);
        order = getIntent().getIntExtra("order", 1);

        switch (order) {
            case 1:
                authenticationTv.setText("去服务认证");
                tsTv.setText("尊敬的用户，您的认证资料我们已收到\n您可以继续进行服务认证。");
                break;
            case 2:
                initNet();
                authenticationTv.setText("去完善保障信息");
                tsTv.setText("尊敬的用户，您的认证资料我们已收到\n我们将在24小时内审核，\n您可以完善保障信息");
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

    private void initNet() {
        EanfangHttp.post(UserApi.JS_TJ_RZ).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {

        }));
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
