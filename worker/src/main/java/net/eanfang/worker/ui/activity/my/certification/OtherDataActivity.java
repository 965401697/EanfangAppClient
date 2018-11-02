package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
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
import net.eanfang.worker.ui.activity.worksapce.OwnDataHintActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

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
    private ArrayList<String> picList_accident = new ArrayList<>();

    private TechWorkerVerifyEntity techWorkerVerifyEntity;
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
        setTitle("实名认证");
        setLeftBack();

        techWorkerVerifyEntity = (TechWorkerVerifyEntity) getIntent().getSerializableExtra("bean");

        initViews();
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

        if (StringUtils.isEmpty(mUrgentName)) {
            showToast("请输入紧急联系人");
            return;
        }
        if (StringUtils.isEmpty(mUrgentPhone)) {
            showToast("请输入紧急联系人电话");
            return;
        }
        techWorkerVerifyEntity.setContactName(mUrgentName);
        techWorkerVerifyEntity.setContactPhone(mUrgentPhone);
        String accidentPic = PhotoUtils.getPhotoUrl("account/verify/", snplMomentAccident, uploadMap, false);
//        if (StringUtils.isEmpty(accidentPic)) {
//            showToast("请添加保险照");
//            return;
//        }
        techWorkerVerifyEntity.setAccidentPics(accidentPic);

        String crimePic = PhotoUtils.getPhotoUrl("account/verify/", snplMomentCrim, uploadMap, false);
//        if (StringUtils.isEmpty(crimePic)) {
//            showToast("请添加犯罪照");
//            return;
//        }
        techWorkerVerifyEntity.setCrimePic(crimePic);

        /**
         * 提交照片
         * */
        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {

                        EanfangHttp.post(UserApi.GET_TECH_WORKER_ADD_V2)
                                .upJson(JSONObject.toJSONString(techWorkerVerifyEntity))
                                .execute(new EanfangCallback<JSONObject>(OtherDataActivity.this, true, JSONObject.class, (bean) -> {
                                    Intent intent = new Intent(OtherDataActivity.this, OwnDataHintActivity.class);
                                    intent.putExtra("info","尊敬的用户，您必须进行资质认证\n" +
                                            "才可以接单，并获得更多订单");
                                    intent.putExtra("go","前往资质认证");
                                    intent.putExtra("desc","如有疑问，请联系客服处理");
                                    intent.putExtra("service","客服热线：" + R.string.text_service_telphone);
                                    closeActivity();
                                }));


                    });
                }
            });
            return;
        } else {
            EanfangHttp.post(UserApi.GET_TECH_WORKER_ADD_V2)
                    .upJson(JSONObject.toJSONString(techWorkerVerifyEntity))
                    .execute(new EanfangCallback<JSONObject>(OtherDataActivity.this, true, JSONObject.class, (bean) -> {
                        Intent intent = new Intent(OtherDataActivity.this, OwnDataHintActivity.class);
                        intent.putExtra("info","尊敬的用户，您必须进行资质认证\n" +
                                "才可以接单，并获得更多订单");
                        intent.putExtra("go","前往资质认证");
                        intent.putExtra("desc","如有疑问，请联系客服处理");
                        intent.putExtra("service","客服热线：" + R.string.text_service_telphone);
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
        }
    }

    private void closeActivity() {
        EanfangApplication.get().closeActivity(CertificationActivity.class.getName());
        EanfangApplication.get().closeActivity(IdentityCardCertification.class.getName());
        finishSelf();
    }
}
