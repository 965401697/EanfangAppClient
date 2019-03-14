package net.eanfang.client.ui.activity.worksapce.security;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityCreateBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.client.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/2/14
 * @description 创建安防圈
 */

public class SecurityCreateActivity extends BaseActivity {

    private static final int REQUEST_CODE_CHOOSE_CERTIFICATE = 1;
    private static final int REQUEST_CODE_PHOTO_CERTIFICATE = 101;
    @BindView(R.id.snpl_add_photo)
    BGASortableNinePhotoLayout snplAddPhoto;
    @BindView(R.id.et_content)
    EditText etContent;

    /**
     * 上传图片路径
     */
    private String mPic;

    private ArrayList<String> picList_certificate = new ArrayList<>();
    private HashMap<String, String> uploadMap = new HashMap<>();

    private SecurityCreateBean securityCreateBean = new SecurityCreateBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_create);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("发布安防圈");
        setLeftBack();
        snplAddPhoto.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_CERTIFICATE, REQUEST_CODE_PHOTO_CERTIFICATE));
    }

    public void doCommit() {
        String mContent = etContent.getText().toString().trim();
        if (StringUtils.isEmpty(mContent)) {
            showToast("请输入内容");
            return;
        }
        securityCreateBean.setSpcContent(mContent);
        mPic = PhotoUtils.getPhotoUrl("sercurity/", snplAddPhoto, uploadMap, false);
        securityCreateBean.setSpcImg(mPic);
        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        doSubmit();
                    });
                }
            });
        } else {
            doSubmit();
        }

    }

    public void doSubmit() {
        EanfangHttp.post(NewApiService.SERCURITY_CREATE)
                .upJson(JSONObject.toJSONString(securityCreateBean))
                .execute(new EanfangCallback<JSONObject>(SecurityCreateActivity.this, true, JSONObject.class, bean1 -> {
                    setResult(RESULT_OK);
                    finishSelf();
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_CERTIFICATE:
                snplAddPhoto.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_CERTIFICATE:
                snplAddPhoto.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
        }
    }

    @OnClick(R.id.tv_confim)
    public void onViewClicked() {
        doCommit();
    }
}
