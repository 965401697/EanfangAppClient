package net.eanfang.worker.ui.activity.techniciancertification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.IPictureCallBack;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;

import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.util.PhotoUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.eanfang.biz.model.entity.TechWorkerVerifyEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkeActivity;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author WQ
 */
public class RealNameAuthenticationActivity extends BaseWorkeActivity {

    @BindView(R.id.iv_idCard_front)
    ImageView ivIdCardFront;
    @BindView(R.id.iv_idCard_back)
    ImageView ivIdCardBack;
    @BindView(R.id.iv_idCard_inHand)
    ImageView ivIdCardInHand;
    @BindView(R.id.tv_save)
    TextView tvSave;
    //身份证正面
    private final int ID_CARD_FRONT = 101;
    // 身份证反面
    private final int ID_CARD_SIDE = 102;
    //身份证手持
    private final int ID_CARD_HAND = 103;
    @BindView(R.id.sfz_jj_tv)
    TextView sfzJjTv;
    private TechWorkerVerifyEntity mTechWorkerVerifyEntity = new TechWorkerVerifyEntity();
    private int statusB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_real_name_authentication);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("实名认证");
        setLeftBack(true);
        statusB = getIntent().getIntExtra("statusB", -1);
        initAccessToken();
        initData();
    }

    private void initData() {
        EanfangHttp.post(UserApi.ZS_SFZ).params("userId", WorkerApplication.get().getUserId()).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
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
            GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + mTechWorkerVerifyEntity.getIdCardFront(), ivIdCardFront);
        }
        if (mTechWorkerVerifyEntity.getIdCardSide() != null) {
            GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + mTechWorkerVerifyEntity.getIdCardSide(), ivIdCardBack);
        }
        if (mTechWorkerVerifyEntity.getIdCardHand() != null) {
            GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + mTechWorkerVerifyEntity.getIdCardHand(), ivIdCardInHand);
        }
        if (mTechWorkerVerifyEntity.getIdCardNum() != null) {
            idCardNum = mTechWorkerVerifyEntity.getIdCardNum();
            //检验一下 身份证是否正确
            recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, PhotoUtils.downloadImg(mTechWorkerVerifyEntity.getIdCardFront()).getPath(), true);
            recIDCard(IDCardParams.ID_CARD_SIDE_BACK, PhotoUtils.downloadImg(mTechWorkerVerifyEntity.getIdCardSide()).getPath(), true);
            if ((!mTechWorkerVerifyEntity.getIdCardNum().equals("暂无")) | (!mTechWorkerVerifyEntity.getIdCardNum().equals(""))) {
                StringBuilder sbd = new StringBuilder();
                sbd.append("姓名：").append(mTechWorkerVerifyEntity.getIdCardName()).append("\n");
                sbd.append("性别：").append(mTechWorkerVerifyEntity.getIdCardGender()).append("\n");
                sbd.append("出生：").append(DateUtil.parse(mTechWorkerVerifyEntity.getIdCardBirth()).toDateStr()).append("\n");
                sbd.append("身份证号：").append(mTechWorkerVerifyEntity.getIdCardNum()).append("\n");
                sfzJjTv.setText(sbd.toString());
            }
        }
    }

    private String idCardNum = "";

    private void doSave() {
        if (StrUtil.isEmpty(idCardNum)) {
            showToast("请添加正确的身份证正面照");
            return;
        }
        if (StrUtil.isEmpty(mTechWorkerVerifyEntity.getIdCardSide())) {
            showToast("请添加正确的身份证反面照");
            return;
        }
        if (StrUtil.isEmpty(mTechWorkerVerifyEntity.getIdCardHand())) {
            showToast("请添加正确的手持身份证照片");
            return;
        }

        mTechWorkerVerifyEntity.setIdCardNum(idCardNum);
        mTechWorkerVerifyEntity.setUserId(WorkerApplication.get().getUserId());
        mTechWorkerVerifyEntity.setAccId(WorkerApplication.get().getAccId());

        EanfangHttp.post(UserApi.GET_TECH_WORKER_ADD_V3).upJson(JSONObject.toJSONString(mTechWorkerVerifyEntity)).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
            WorkerApplication.get().getLoginBean().getAccount().setNickName(mTechWorkerVerifyEntity.getIdCardName());
            WorkerApplication.get().getLoginBean().getAccount().setIdCard(idCardNum);
            WorkerApplication.get().getLoginBean().getAccount().setGender(mTechWorkerVerifyEntity.getIdCardGender().equals("女") ? 0 : 1);
            Intent intent = new Intent(this, SubmitSuccessfullyJsActivity.class);
            intent.putExtra("bean", mTechWorkerVerifyEntity);
            intent.putExtra("statusB", statusB);
            intent.putExtra("order", 1);
            startActivity(intent);
            finish();
        }));
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

    private void recIDCard(String idCardSide, String filePath, boolean isCheck) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        param.setIdCardSide(idCardSide);
        param.setDetectDirection(true);
        param.setImageQuality(20);
        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResult(IDCardResult result) {
                if (result != null && !result.getImageStatus().equals("non_idcard")) {
                    switch (result.getImageStatus()) {
                        case "normal":
                            String imgKey = null;
                            if (!isCheck) {
                                imgKey = StrUtil.uuid() + "idCl.jpg";
                                SDKManager.ossKit(RealNameAuthenticationActivity.this).asyncPutImage(imgKey, filePath, (isSuccess) -> {
                                });
                            }
                            if (result.getIdCardSide().equals("front")) {
                                idCardNum = result.getIdNumber().toString();
                                if (!isCheck) {
                                    mTechWorkerVerifyEntity.setIdCardFront(imgKey);
                                }
                                mTechWorkerVerifyEntity.setIdCardName(result.getName().toString());
                                mTechWorkerVerifyEntity.setIdCardGender(result.getGender().toString());
                                mTechWorkerVerifyEntity.setIdCardBirth(result.getBirthday().toString());
                                StringBuilder sbd = new StringBuilder();
                                sbd.append("姓名：").append(result.getName()).append("\n");
                                sbd.append("性别：").append(result.getGender()).append("\n");
                                sbd.append("出生：").append(DateUtil.parse(result.getBirthday().toString(), "yyyyMMdd").toDateStr()).append("\n");
                                sbd.append("身份证号：").append(result.getIdNumber()).append("\n");
                                sfzJjTv.setText(sbd.toString());
                            } else if (result.getIdCardSide().equals("back")) {
                                if (!isCheck) {
                                    mTechWorkerVerifyEntity.setIdCardSide(imgKey);
                                }
                            }
                            return;
                        case "reversed_side":
                            showToast("身份证正反面颠倒,请重新上传");
                            break;
                        case "blurred":
                            showToast("身份证模糊,请重新上传");
                            break;
                        case "other_type_card":
                            showToast("请上传正确的身份证");
                            break;
                        default:
                            showToast("请上传正确的身份证");
                            break;
                    }
                }
                if (idCardSide.equals(IDCardParams.ID_CARD_SIDE_FRONT)) {
                    idCardNum = "";
                } else if (idCardSide.equals(IDCardParams.ID_CARD_SIDE_BACK)) {
                    mTechWorkerVerifyEntity.setIdCardSide(null);
                }
            }

            @Override
            public void onError(OCRError error) {
                Log.d("recIDCard666", error.getMessage());
            }
        });
    }

    private int state = 0;

    @OnClick({R.id.iv_idCard_front, R.id.iv_idCard_back, R.id.iv_idCard_inHand, R.id.tv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_idCard_front:
               /* Intent intent = new Intent(this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE, true);
                intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true);
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, ID_CARD_FRONT);*/
                RxPerm.get(this).cameraPerm((isSuccess) -> {
                    state = ID_CARD_FRONT;
                    iv();
                });
                break;
            case R.id.iv_idCard_back:
                RxPerm.get(this).cameraPerm((isSuccess) -> {
                    state = ID_CARD_SIDE;
                    iv();
                });
                break;
            case R.id.iv_idCard_inHand:
                RxPerm.get(this).cameraPerm((isSuccess) -> {
                    state = ID_CARD_HAND;
                    iv();
                });
                break;
            case R.id.tv_save:
                doSave();
                break;
            default:
        }
    }

    private void iv() {
        SDKManager.getPicture().create(this).takePhoto(iPictureCallBack);
    }

    final IPictureCallBack iPictureCallBack = new IPictureCallBack() {
        @Override
        public void onSuccess(List<LocalMedia> list) {

            switch (state) {
                case ID_CARD_FRONT:
                    GlideUtil.intoImageView(RealNameAuthenticationActivity.this, "file://" + list.get(0).getPath(), ivIdCardFront);
                    recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, list.get(0).getPath(), false);
                    break;
                case ID_CARD_SIDE:
                    GlideUtil.intoImageView(RealNameAuthenticationActivity.this, "file://" + list.get(0).getPath(), ivIdCardBack);
                    recIDCard(IDCardParams.ID_CARD_SIDE_BACK, list.get(0).getPath(), false);
                    break;
                case ID_CARD_HAND:
                    String imgKey = StrUtil.uuid() + ".png";
                    GlideUtil.intoImageView(RealNameAuthenticationActivity.this, "file://" + list.get(0).getPath(), ivIdCardInHand);
                    SDKManager.ossKit(RealNameAuthenticationActivity.this).asyncPutImage(imgKey, list.get(0).getPath(), (isSuccess) -> {
                        mTechWorkerVerifyEntity.setIdCardHand(imgKey);
                    });
                    break;
                default:
            }

            state = 0;
        }
    };

    @Override
    protected void onDestroy() {
        CameraNativeHelper.release();
        OCR.getInstance(this).release();
        super.onDestroy();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    private static int nem = 0;

    public static class FileUtil {
        public static File getSaveFile(Context context) {
            File file = new File(context.getFilesDir(), nem + "idCl.jpg");
            return file;
        }
    }
}
