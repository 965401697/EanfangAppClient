package net.eanfang.worker.ui.activity.techniciancertification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
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
import com.yaf.base.entity.TechWorkerVerifyEntity;

import net.eanfang.worker.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author WQ
 */
public class RealNameAuthenticationActivity extends BaseActivityWithTakePhoto {

    @BindView(R.id.iv_idCard_front)
    SimpleDraweeView ivIdCardFront;
    @BindView(R.id.iv_idCard_back)
    SimpleDraweeView ivIdCardBack;
    @BindView(R.id.iv_idCard_inHand)
    SimpleDraweeView ivIdCardInHand;
    @BindView(R.id.tv_save)
    TextView tvSave;
    //身份证正面
    private final int ID_CARD_FRONT = 101;
    // 身份证反面
    private final int ID_CARD_SIDE = 102;
    //身份证手持
    private final int ID_CARD_HAND = 103;
    private TechWorkerVerifyEntity mTechWorkerVerifyEntity = new TechWorkerVerifyEntity();
    private int statusB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name_authentication);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("实名认证");
        statusB = getIntent().getIntExtra("statusB", -1);
        initAccessToken();
        initData();
    }

    private void initData() {
        EanfangHttp.post(UserApi.ZS_SFZ).params("userId", EanfangApplication.get().getUserId()).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
            if (bean != null) {
                Log.d("ss56", "initData: " + bean.toString());
                mTechWorkerVerifyEntity = JSONObject.toJavaObject(bean, TechWorkerVerifyEntity.class);
                Log.d("ss566", "initData: " + mTechWorkerVerifyEntity.toString());
                if (mTechWorkerVerifyEntity != null) {
                    fillData();
                }
            }
        }));
    }

    private void fillData() {
        if (mTechWorkerVerifyEntity.getIdCardFront() != null) {
            ivIdCardFront.setImageURI(com.eanfang.BuildConfig.OSS_SERVER + mTechWorkerVerifyEntity.getIdCardFront());
        }
        if (mTechWorkerVerifyEntity.getIdCardSide() != null) {
            ivIdCardBack.setImageURI(com.eanfang.BuildConfig.OSS_SERVER + mTechWorkerVerifyEntity.getIdCardSide());
        }
        if (mTechWorkerVerifyEntity.getIdCardHand() != null) {
            ivIdCardInHand.setImageURI(com.eanfang.BuildConfig.OSS_SERVER + mTechWorkerVerifyEntity.getIdCardHand());
        }
    }

    private String idCardNum = "";

    private void doSave() {
        if (StringUtils.isEmpty(idCardNum)) {
            showToast("请添加正确的身份证正面照");
            return;
        }
        if (StringUtils.isEmpty(mTechWorkerVerifyEntity.getIdCardSide())) {
            showToast("请添加身份证反面照");
            return;
        }
        if (StringUtils.isEmpty(mTechWorkerVerifyEntity.getIdCardHand())) {
            showToast("请添加手持身份证照片");
            return;
        }

        mTechWorkerVerifyEntity.setIdCardNum(idCardNum);
        mTechWorkerVerifyEntity.setUserId(EanfangApplication.get().getUserId());
        mTechWorkerVerifyEntity.setAccId(EanfangApplication.get().getAccId());

        EanfangHttp.post(UserApi.GET_TECH_WORKER_ADD_V3).upJson(JSONObject.toJSONString(mTechWorkerVerifyEntity)).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
            Intent intent = new Intent(this, SubmitSuccessfullyJsActivity.class);
            intent.putExtra("bean", mTechWorkerVerifyEntity);
            intent.putExtra("statusB", statusB);
            intent.putExtra("order", 1);
            startActivity(intent);
            finish();
        }));
    }

    @Override
    public void takeSuccess(TResult result, int resultCode) {
        super.takeSuccess(result, resultCode);
        if (result == null || result.getImage() == null) {
            return;
        }
        TImage image = result.getImage();
        String imgKey = UuidUtil.getUUID() + ".png";
        switch (resultCode) {
            case ID_CARD_SIDE:
                mTechWorkerVerifyEntity.setIdCardSide(imgKey);
                ivIdCardBack.setImageURI("file://" + image.getOriginalPath());
                break;
            case ID_CARD_HAND:
                mTechWorkerVerifyEntity.setIdCardHand(imgKey);
                ivIdCardInHand.setImageURI("file://" + image.getOriginalPath());
                break;
            default:
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ID_CARD_FRONT && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String imgKey = UuidUtil.getUUID() + "idc.png";
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                Log.d("recIDCard669", "onActivityResult: " + filePath);
                mTechWorkerVerifyEntity.setIdCardFront(imgKey);
                ivIdCardFront.setImageURI("file://" + filePath);
                OSSUtils.initOSS(this).asyncPutImage(imgKey, filePath, new OSSCallBack(this, true) {
                });
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }
        }
    }

    private void initAccessToken() {
        OCR.getInstance(this).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                CameraNativeHelper.init(RealNameAuthenticationActivity.this, OCR.getInstance(RealNameAuthenticationActivity.this).getLicense(),
                        (errorCode, e) -> {
                            String msg;
                            switch (errorCode) {
                                case CameraView.NATIVE_SOLOAD_FAIL:
                                    msg = "加载so失败，请确保apk中存在ui部分的so";
                                    break;
                                case CameraView.NATIVE_AUTH_FAIL:
                                    msg = "授权本地质量控制token获取失败";
                                    break;
                                case CameraView.NATIVE_INIT_FAIL:
                                    msg = "本地质量控制";
                                    break;
                                default:
                                    msg = String.valueOf(errorCode);
                            }
                            showToast("本地质量控制初始化错误，错误原因： " + msg);
                        });
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                showToast("licence方式获取token失败: " + error.getMessage());
            }
        }, getApplicationContext());
    }

    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        param.setIdCardSide(idCardSide);
        param.setDetectDirection(true);
        param.setImageQuality(20);
        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    Log.d("recIDCard66", result.toString());
                    idCardNum = result.getIdNumber().toString();

                }
            }

            @Override
            public void onError(OCRError error) {
                Log.d("recIDCard666", error.getMessage());
            }
        });
    }

    @OnClick({R.id.iv_idCard_front, R.id.iv_idCard_back, R.id.iv_idCard_inHand, R.id.tv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_idCard_front:
                Intent intent = new Intent(this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE, true);
                intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true);
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, ID_CARD_FRONT);
                break;
            case R.id.iv_idCard_back:
                PermissionUtils.get(this).getCameraPermission(() -> takePhoto(this, ID_CARD_SIDE));
                break;
            case R.id.iv_idCard_inHand:
                PermissionUtils.get(this).getCameraPermission(() -> takePhoto(this, ID_CARD_HAND));
                break;
            case R.id.tv_save:
                doSave();
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        CameraNativeHelper.release();
        OCR.getInstance(this).release();
        super.onDestroy();
    }

    public static class FileUtil {
        public static File getSaveFile(Context context) {
            File file = new File(context.getFilesDir(), "idc.png");
            return file;
        }
    }
}