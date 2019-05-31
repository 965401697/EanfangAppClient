package net.eanfang.worker.ui.activity.my.specialist;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.yaf.base.entity.ExpertsCertificationEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.OwnDataHintActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SpecialistIdentityCardCertification extends BaseActivityWithTakePhoto {
    //身份证正面
    private final int ID_CARD_FRONT = 101;
    // 身份证反面
    private final int ID_CARD_SIDE = 102;
    //身份证手持
    private final int ID_CARD_HAND = 103;

    @BindView(R.id.iv_idCard_front)
    ImageView ivIdCardFront;

    @BindView(R.id.iv_idCard_back)
    ImageView ivIdCardBack;

    @BindView(R.id.iv_idCard_inHand)
    ImageView ivIdCardInHand;

    @BindView(R.id.tv_save)
    TextView tvSave;

    private ExpertsCertificationEntity mExpertsCertificationEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_identity_card_certification);
        ButterKnife.bind(this);
        setTitle("实名认证");
        setLeftBack();
        mExpertsCertificationEntity = (ExpertsCertificationEntity) getIntent().getSerializableExtra("bean");

        initListener();
    }

    private void initListener() {
        ivIdCardFront.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(SpecialistIdentityCardCertification.this, ID_CARD_FRONT)));
        ivIdCardBack.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(SpecialistIdentityCardCertification.this, ID_CARD_SIDE)));
        ivIdCardInHand.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(SpecialistIdentityCardCertification.this, ID_CARD_HAND)));
        tvSave.setOnClickListener((v) -> {
            doSave();
        });

        if (!TextUtils.isEmpty(mExpertsCertificationEntity.getIdCardFront())) {
            fillData();
        }


    }


    private void fillData() {
        GlideUtil.intoImageView(this,com.eanfang.BuildConfig.OSS_SERVER + mExpertsCertificationEntity.getIdCardFront(),ivIdCardFront);
        GlideUtil.intoImageView(this,com.eanfang.BuildConfig.OSS_SERVER + mExpertsCertificationEntity.getIdCardSide(),ivIdCardBack);
        GlideUtil.intoImageView(this,com.eanfang.BuildConfig.OSS_SERVER + mExpertsCertificationEntity.getIdCardHand(),ivIdCardInHand);
    }

    /**
     * 保存照片
     */
    private void doSave() {
        if (StringUtils.isEmpty(mExpertsCertificationEntity.getIdCardFront())) {
            showToast("请添加身份证正面照");
            return;
        }
        if (StringUtils.isEmpty(mExpertsCertificationEntity.getIdCardHand())) {
            showToast("请添加手持身份证照片");
            return;
        }
        if (StringUtils.isEmpty(mExpertsCertificationEntity.getIdCardSide())) {
            showToast("请添加身份证反面照");
            return;
        }

        EanfangHttp.post(UserApi.GET_EXPERT_INFO_CERTIFICATION)
                .upJson(JSONObject.toJSONString(mExpertsCertificationEntity))
                .execute(new EanfangCallback<JSONObject>(SpecialistIdentityCardCertification.this, true, JSONObject.class, (bean) -> {
                    Intent intent = new Intent(SpecialistIdentityCardCertification.this, OwnDataHintActivity.class);
                    intent.putExtra("info", "尊敬的用户，您必须进行资质认证\n" +
                            "才可以使用专家权限，并获得更多关注");
                    intent.putExtra("go", "前往资质认证");
                    intent.putExtra("desc", "如有疑问，请联系客服处理");
                    intent.putExtra("service", "客服热线：" + R.string.text_service_telphone);
                    intent.putExtra("class", SpecialistSkillTypeActivity.class);
                    startActivity(intent);
                    closeActivity();
                }));

    }

    private void closeActivity() {
        WorkerApplication.get().closeActivity(SpecialistCertificationActivity.class);
        WorkerApplication.get().closeActivity(SpecialistIdentityCardCertification.class);
        finishSelf();
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
            // 身份证正面
            case ID_CARD_FRONT:
                mExpertsCertificationEntity.setIdCardFront(imgKey);
                GlideUtil.intoImageView(this,"file://" + image.getOriginalPath(),ivIdCardFront);
                break;
            // 反面
            case ID_CARD_SIDE:
                mExpertsCertificationEntity.setIdCardSide(imgKey);
                GlideUtil.intoImageView(this,"file://" + image.getOriginalPath(),ivIdCardBack);
                break;
            // 手持
            case ID_CARD_HAND:
                mExpertsCertificationEntity.setIdCardHand(imgKey);
                GlideUtil.intoImageView(this,"file://" + image.getOriginalPath(),ivIdCardInHand);
                break;
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
        });

    }

}
