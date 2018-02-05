package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.SigninBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.PhotoUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/24  20:07
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class SignInCommitActivity extends BaseActivity {
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_commit)
    Button tvCommit;
    @BindView(R.id.et_remark)
    EditText etRemark;
    private SigninBean signinBean;
    private Map<String, String> uploadMap = new HashMap<>();
    private String title;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_commit);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title = getIntent().getStringExtra("title");
        status = getIntent().getIntExtra("status", 0);
        setTitle(title);
        setLeftBack();
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));
        signinBean = (SigninBean) getIntent().getSerializableExtra("bean");
        tvTime.setText(signinBean.getTime());
        tvAddress.setText(signinBean.getDetailPlace());
        tvName.setText(signinBean.getVisitorName());
        tvCompany.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
        tvCommit.setOnClickListener(v -> commit());
    }

    private void commit() {
        signinBean.setRemarkInfo(etRemark.getText().toString().trim());
        String ursStr = PhotoUtils.getPhotoUrl(snplMomentAddPhotos, uploadMap, true);
        signinBean.setPictures(ursStr);
        String json = JSONObject.toJSONString(signinBean);
        EanfangHttp.post(UserApi.GET_SIGN_ADD)
                .upJson(json)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    startAnimActivity(new Intent(SignInCommitActivity.this, SignActivity.class)
                            .putExtra("title", title)
                            .putExtra("status", status));
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            snplMomentAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == BGASortableDelegate.REQUEST_CODE_PHOTO_PREVIEW) {
            snplMomentAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }
}
