package net.eanfang.worker.ui.activity.authentication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.contacts.baseinfo.AuthCompanyFirstBActivity;
import net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify.AuthQualifyFirstActivity;
import net.eanfang.worker.ui.fragment.ContactsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author WQ
 */
public class SubmitSuccessfullyQyActivity extends BaseActivity {

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
    private String orgName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_successfully);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setLeftBack();
        setTitle("提交成功");
        SpannableString spannableString = new SpannableString("工商服务➝服务认证➝资质荣誉➝更多能力");
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
        mOrgId = getIntent().getLongExtra("mOrgId", 0);
        status = getIntent().getIntExtra("status", 0);
        order = getIntent().getIntExtra("order", 1);
        orgName = getIntent().getStringExtra("orgName");

        switch (order) {
            case 1:
                authenticationTv.setText("去添加服务认证");
                tsTv.setText("尊敬的用户，您的认证资料我们已收到\n您需要继续完善服务认证。");
                break;
            case 2:
                authenticationTv.setText("去添加资质与荣誉");
                tsTv.setText("尊敬的用户，您的认证资料我们已收到\n您需要继续填写更多能力。");
                initInt();
                ContactsFragment.isRefresh = true;
                break;
            case 3:
                authenticationTv.setText("去添加更多能力");
                break;
            case 4:
                authenticationTv.setText("服务认证完成");
                break;
            case 5:
                authenticationTv.setText("去完善公司资料");
                tsTv.setText("恭喜您！尊敬的用户，团队已创建成功。\n您可以完善公司资料");
                setTitle("创建成功");
                break;
            case 6:
                authenticationTv.setText("去完善企业认证");
                tsTv.setText("尊敬的用户，您的认证资料我们已收到。\n您可以完善企业认证");
                break;
            default:
        }
    }

    private void initInt() {
        EanfangHttp.get(UserApi.JS_RZ_TJ_INFO+mOrgId).execute(new EanfangCallback<String>(this, true, String.class, (bean) -> {
            Log.d("56483666", "bean: " + bean);
        }));
    }

    @OnClick({R.id.authentication_tv, R.id.call_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.authentication_tv:
                switch (order) {
                    case 1:
                        Bundle bundle_prefect = new Bundle();
                        bundle_prefect.putLong("orgid", mOrgId);
                        if (!PermKit.get().getWorkerCompanyVerifyPerm()) {
                            showToast("您没有权限");
                            return;
                        } else {
                            JumpItent.jump(this, AuthQualifyFirstActivity.class, bundle_prefect);
                            finish();
                        }
                        break;
                    case 2:
                        Intent intent = new Intent(this, QualificationsAndHonoursActivity.class);
                        intent.putExtra("orgid", mOrgId);
                        intent.putExtra("isAuth", status);
                        startActivity(intent);
                        finish();
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        startActivity(new Intent(this, AuthCompanyFirstBActivity.class).putExtra("orgName", orgName).putExtra("orgid", mOrgId));
                        finish();
                        break;
                    case 6:
                        Intent intentf = new Intent(this, EnterpriseCertificationActivity.class);
                        intentf.putExtra("mOrgId", mOrgId);
                        intentf.putExtra("status", 0);
                        intentf.putExtra("orgName", orgName);
                        startActivity(intentf);
                        finish();
                        break;
                    default:
                }
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
