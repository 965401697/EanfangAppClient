package net.eanfang.worker.ui.activity.my.certification;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.TechWorkerVerifyEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.techniciancertification.SubmitSuccessfullyJsActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.widget.WQLeftRightClickTextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtherDataActivity extends BaseWorkerActivity {


    //意外保险
    private static final int REQUEST_CODE_CHOOSE_ACCIDENT = 6;
    private static final int REQUEST_CODE_PHOTO_ACCIDENT = 106;
    //有无犯罪记录
    private static final int REQUEST_CODE_CHOOSE_CRIM = 1;
    private static final int REQUEST_CODE_PHOTO_CRIM = 101;

    /**
     * 保险照片
     * （3张）
     */
    @BindView(R.id.snpl_moment_accident)
    BGASortableNinePhotoLayout snplMomentAccident;
    @BindView(R.id.et_urgent_name)
    EditText etUrgentName;
    @BindView(R.id.et_urgent_phone)
    EditText etUrgentPhone;
    @BindView(R.id.ts_lr_tv)
    WQLeftRightClickTextView tsLrTv;
    @BindView(R.id.yx_et)
    EditText yxEt;
    private ArrayList<String> picList_accident = new ArrayList<>();
    private TechWorkerVerifyEntity mTechWorkerVerifyEntity;
    /**
     * 犯罪照片
     * （3张）
     */
    @BindView(R.id.snpl_moment_crim)
    BGASortableNinePhotoLayout snplMomentCrim;
    private ArrayList<String> picList_crim = new ArrayList<>();
    private HashMap<String, String> uploadMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_data);
        ButterKnife.bind(this);
        setTitle("保障信息");
        setLeftBack();
        EanfangHttp.get(UserApi.GET_WORKER_INFO).execute(new EanfangCallback<TechWorkerVerifyEntity>(this, true, TechWorkerVerifyEntity.class, (bean) -> {
            mTechWorkerVerifyEntity = bean;
            if (!TextUtils.isEmpty(mTechWorkerVerifyEntity.getContactName())) {
                fillData();
            }
        }));
        initViews();
    }

    private void fillData() {
        Log.d("5648", "fillData: " + mTechWorkerVerifyEntity.getContactName());
        if (StringUtils.isEmpty(mTechWorkerVerifyEntity.geteMail()) | mTechWorkerVerifyEntity.geteMail() == null) {

        } else {
            yxEt.setText(mTechWorkerVerifyEntity.geteMail());
        }
        if (StringUtils.isEmpty(mTechWorkerVerifyEntity.getContactName()) | mTechWorkerVerifyEntity.getContactName() == null) {

        } else {
            etUrgentName.setText(mTechWorkerVerifyEntity.getContactName());
        }
        etUrgentPhone.setText(mTechWorkerVerifyEntity.getContactPhone());

        String[] crimePic = mTechWorkerVerifyEntity.getCrimePic().split(",");
        if (crimePic.length > 0) {
            for (byte i = 0; i < crimePic.length; i++) {
                picList_crim.add(BuildConfig.OSS_SERVER + crimePic[i]);
            }
            snplMomentCrim.setData(picList_crim);
        }

        String[] accidentPics = mTechWorkerVerifyEntity.getAccidentPics().split(",");
        if (accidentPics.length > 0) {
            for (byte i = 0; i < accidentPics.length; i++) {
                picList_accident.add(BuildConfig.OSS_SERVER + accidentPics[i]);
            }
            snplMomentAccident.setData(picList_accident);
        }
    }

    private void initViews() {
        // 保险照
        snplMomentAccident.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_ACCIDENT, REQUEST_CODE_PHOTO_ACCIDENT));
        snplMomentAccident.setData(picList_accident);
        // 犯罪照
        snplMomentCrim.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_CRIM, REQUEST_CODE_PHOTO_CRIM));
        snplMomentCrim.setData(picList_crim);
    }

    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        doSave();
    }

    private void doSave() {
        String mUrgentName = etUrgentName.getText().toString().trim();
        String mUrgentPhone = etUrgentPhone.getText().toString().trim();
        String yxEtS = yxEt.getText().toString().trim();

        if (StringUtils.isEmpty(yxEtS)) {
            showToast("请输入电子邮箱");
            return;
        }  if (StringUtils.isEmpty(mUrgentName)) {
            showToast("请输入紧急联系人");
            return;
        }
        if (StringUtils.isEmpty(mUrgentPhone)) {
            showToast("请输入紧急联系人电话");
            return;
        }
        mTechWorkerVerifyEntity.setContactName(mUrgentName);
        mTechWorkerVerifyEntity.setContactPhone(mUrgentPhone);
        String accidentPic = PhotoUtils.getPhotoUrl("account/verify/", snplMomentAccident, uploadMap, false);
        mTechWorkerVerifyEntity.setAccidentPics(accidentPic);
        String crimePic = PhotoUtils.getPhotoUrl("account/verify/", snplMomentCrim, uploadMap, false);
        mTechWorkerVerifyEntity.setCrimePic(crimePic);

        /**
         * 提交照片
         * */
        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> EanfangHttp.post(UserApi.GET_TECH_WORKER_ADD_V2).upJson(JSONObject.toJSONString(mTechWorkerVerifyEntity)).execute(new EanfangCallback<JSONObject>(OtherDataActivity.this, true, JSONObject.class, (bean) -> {
                        Intent intent = new Intent(OtherDataActivity.this, SubmitSuccessfullyJsActivity.class);
                        intent.putExtra("order", 3);
                        startAnimActivity(intent);
                        closeActivity();
                    })));
                }
            });
            return;
        } else {
            EanfangHttp.post(UserApi.GET_TECH_WORKER_ADD_V2).upJson(JSONObject.toJSONString(mTechWorkerVerifyEntity)).execute(new EanfangCallback<JSONObject>(OtherDataActivity.this, true, JSONObject.class, (bean) -> {
                Intent intent = new Intent(this, SubmitSuccessfullyJsActivity.class);
                intent.putExtra("order", 3);
                startAnimActivity(intent);
                closeActivity();
            }));

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_ACCIDENT:
                snplMomentAccident.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_CRIM:
                snplMomentCrim.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            default:
        }
    }

    private void closeActivity() {
        EanfangApplication.get().closeActivity(CertificationActivity.class.getName());
        EanfangApplication.get().closeActivity(IdentityCardCertification.class.getName());
        finishSelf();
    }

    @OnClick(R.id.ts_lr_tv)
    public void onClick() {
        View layout = LayoutInflater.from(this).inflate(R.layout.activity_ts_wz, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setCancelable(true);
        builder.create().show();
    }
}
