package net.eanfang.worker.ui.activity.techniciancertification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
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
        initData();
    }

    private void initData() {
        EanfangHttp.post(UserApi.ZS_SFZ).params("userId", EanfangApplication.get().getUserId()).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
            Log.d("ss56", "initData: " + bean.toString());
            mTechWorkerVerifyEntity = JSONObject.toJavaObject(bean, TechWorkerVerifyEntity.class);
            Log.d("ss566", "initData: " + mTechWorkerVerifyEntity.toString());
            if (mTechWorkerVerifyEntity != null) {
                fillData();
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

    private void doSave() {
        if (StringUtils.isEmpty(mTechWorkerVerifyEntity.getIdCardFront())) {
            showToast("请添加身份证正面照");
            return;
        }
        if (StringUtils.isEmpty(mTechWorkerVerifyEntity.getIdCardHand())) {
            showToast("请添加手持身份证照片");
            return;
        }
        if (StringUtils.isEmpty(mTechWorkerVerifyEntity.getIdCardSide())) {
            showToast("请添加身份证反面照");
            return;
        }
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
            case ID_CARD_FRONT:
                mTechWorkerVerifyEntity.setIdCardFront(imgKey);
                ivIdCardFront.setImageURI("file://" + image.getOriginalPath());
                break;
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

    @OnClick({R.id.iv_idCard_front, R.id.iv_idCard_back, R.id.iv_idCard_inHand, R.id.tv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_idCard_front:
                PermissionUtils.get(this).getCameraPermission(() -> takePhoto(this, ID_CARD_FRONT));
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
}
