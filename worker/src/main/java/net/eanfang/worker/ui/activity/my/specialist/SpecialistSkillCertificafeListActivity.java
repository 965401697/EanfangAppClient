package net.eanfang.worker.ui.activity.my.specialist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.yaf.base.entity.ZjZgBean;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.my.certification.QualificationAdapter;
import net.eanfang.worker.ui.activity.worksapce.OwnDataHintActivity;
import net.eanfang.worker.ui.activity.worksapce.online.ExpertOnlineActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SpecialistSkillCertificafeListActivity extends BaseActivityWithTakePhoto {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private final int ADD_EDUCATION_CODE = 101;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.iv_upload2)
    SimpleDraweeView ivUpload2;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    private QualificationAdapter adapter;
    private String verifyOrg = "";
    private String verifyPicUrl = "";
    private final int ADPIC_CALLBACK_CODE = 400;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_skill_certificafe_list);
        ButterKnife.bind(this);
        setTitle("专家认证");
        setLeftBack();
        getData();
    }

    private void getData() {
        EanfangHttp.post(UserApi.TECH_WORKER_LIST_QUALIFY_C).execute(new EanfangCallback<ZjZgBean>(this, true, ZjZgBean.class) {
            @Override
            public void onSuccess(ZjZgBean bean) {
                if ((bean == null) || (bean.getVerifyOrg() == null)) {

                } else {
                    verifyOrg = bean.getVerifyOrg();
                    verifyPicUrl=bean.getVerifyPicUrl();
                    etCompany.setText(verifyOrg);
                    ivUpload2.setImageURI(BuildConfig.OSS_SERVER + bean.getVerifyPicUrl());
                    if (bean.getStatus() == 1) {
                        etCompany.setEnabled(false);
                        ivUpload2.setEnabled(false);
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ADD_EDUCATION_CODE) {
            getData();
        }
    }

    @Override
    public void takeSuccess(TResult result, int resultCode) {
        super.takeSuccess(result, resultCode);
        if (result == null || result.getImage() == null) {
            return;
        }
        TImage image = result.getImage();
        String imgKey = "org/" + UuidUtil.getUUID() + "zj.png";
        if (resultCode == ADPIC_CALLBACK_CODE) {
            verifyPicUrl = imgKey;
            ivUpload2.setImageURI("file://" + image.getOriginalPath());
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
        });

    }

    @OnClick({R.id.iv_upload2, R.id.tv_sub})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_upload2:
                PermissionUtils.get(this).getCameraPermission(() -> takePhoto(this, ADPIC_CALLBACK_CODE));
                break;
            case R.id.tv_sub:
                seData();
                break;
            default:
        }
    }

    private void seData() {
        verifyOrg = etCompany.getText().toString().trim();
        if (StringUtils.isEmpty(verifyOrg)) {
            showToast("请输入认定单位");
        } else if (StringUtils.isEmpty(verifyPicUrl)) {
            showToast("请添加培训认定文件");
            return;
        } else {
            Log.d("684587441", "seData: "+UserApi.UP_WORKER_LIST_QUALIFY_C+"\n"+ EanfangApplication.get().getUserId()+"\n"+verifyOrg+"\n"+verifyPicUrl);
            EanfangHttp.post(UserApi.UP_WORKER_LIST_QUALIFY_C).params("userId", EanfangApplication.get().getUserId()).params("verifyOrg", verifyOrg).params("verifyPicUrl", verifyPicUrl).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
                Intent intent = new Intent(SpecialistSkillCertificafeListActivity.this, OwnDataHintActivity.class);
                intent.putExtra("info", "尊敬的用户，您的认证资料我们已收到\n" + "我们将在24小时内审核，\n" + "您可以先去逛逛在线问答哦。");
                intent.putExtra("go", "去看看大家都在问什么");
                intent.putExtra("desc", "如有疑问，请联系客服处理");
                intent.putExtra("service", "客服热线：400-890-9280");
                intent.putExtra("class", ExpertOnlineActivity.class);
                startActivity(intent);
                finish();
            }));
        }
    }
}
