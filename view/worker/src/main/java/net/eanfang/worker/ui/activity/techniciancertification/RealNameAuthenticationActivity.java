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

import com.eanfang.util.GetDateUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.yaf.base.entity.TechWorkerVerifyEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkeActivity;

import java.io.File;
import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        try {
            if (mTechWorkerVerifyEntity.getIdCardNum() != null) {
                idCardNum = mTechWorkerVerifyEntity.getIdCardNum();
                if ((!mTechWorkerVerifyEntity.getIdCardNum().equals("暂无")) | (!mTechWorkerVerifyEntity.getIdCardNum().equals(""))) {
                    sfzJjTv.setText("姓名：" + mTechWorkerVerifyEntity.getIdCardName() + "\n" + "性别：" + mTechWorkerVerifyEntity.getIdCardGender() + "\n" + "出生：" + GetDateUtils.strToDate(mTechWorkerVerifyEntity.getIdCardBirth()) + "\n" + "身份证号：" + mTechWorkerVerifyEntity.getIdCardNum());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
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

    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        param.setIdCardSide(idCardSide);
        param.setDetectDirection(true);
        param.setImageQuality(20);
        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    Log.d("recIDCard66", result.toString());
                    String imgKey = UuidUtil.getUUID() + "idCl.jpg";
                    if ((result.getIdNumber() != null) && (!result.getIdNumber().toString().equals(""))) {
                        Log.d("recIDCard66888", result.toString());
                        idCardNum = result.getIdNumber().toString();
                        mTechWorkerVerifyEntity.setIdCardFront(imgKey);
                        mTechWorkerVerifyEntity.setIdCardName(result.getName().toString());
                        mTechWorkerVerifyEntity.setIdCardGender(result.getGender().toString());
                        mTechWorkerVerifyEntity.setIdCardBirth(result.getBirthday().toString());
                        mTechWorkerVerifyEntity.setIdCardBirth(result.getBirthday().toString());
                        try {
                            sfzJjTv.setText("姓名：" + result.getName() + "\n" + "性别：" + result.getGender() + "\n" + "出生：" + GetDateUtils.strToDate(result.getBirthday().toString()) + "\n" + "身份证号：" + result.getIdNumber());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SDKManager.ossKit(RealNameAuthenticationActivity.this).asyncPutImage(imgKey, filePath, (isSuccess) -> {
                        });
                    } else {
                        idCardNum = "";
                    }
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
                    state=ID_CARD_SIDE;
                    iv();
                });
                break;
            case R.id.iv_idCard_inHand:
                RxPerm.get(this).cameraPerm((isSuccess) -> {
                    state=ID_CARD_HAND;
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

    IPictureCallBack iPictureCallBack = new IPictureCallBack() {
        @Override
        public void onSuccess(List<LocalMedia> list) {
            String imgKey = UuidUtil.getUUID() + ".png";
            switch (state) {
                case ID_CARD_FRONT:
                    GlideUtil.intoImageView(RealNameAuthenticationActivity.this, "file://" + list.get(0).getPath(), ivIdCardFront);
                    recIDCard(IDCardParams.ID_CARD_SIDE_FRONT,  list.get(0).getPath());
                    break;
                case ID_CARD_SIDE:
                    mTechWorkerVerifyEntity.setIdCardSide(imgKey);
                    GlideUtil.intoImageView(RealNameAuthenticationActivity.this, "file://" + list.get(0).getPath(), ivIdCardBack);
                    break;
                case ID_CARD_HAND:
                    mTechWorkerVerifyEntity.setIdCardHand(imgKey);
                    GlideUtil.intoImageView(RealNameAuthenticationActivity.this, "file://" + list.get(0).getPath(), ivIdCardInHand);
                    break;
                default:
            }
            SDKManager.ossKit(RealNameAuthenticationActivity.this).asyncPutImage(imgKey, list.get(0).getPath(), (isSuccess) -> {
            });
            state=0;
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
