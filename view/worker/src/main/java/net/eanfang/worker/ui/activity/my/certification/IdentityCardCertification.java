package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.yaf.base.entity.TechWorkerVerifyEntity;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IdentityCardCertification extends BaseActivityWithTakePhoto {
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

    private TechWorkerVerifyEntity mTechWorkerVerifyEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_card_certification);
        ButterKnife.bind(this);
        setTitle("身份信息");
        setLeftBack();
        mTechWorkerVerifyEntity = (TechWorkerVerifyEntity) getIntent().getSerializableExtra("bean");

        initListener();
    }

    private void initListener() {
        ivIdCardFront.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(IdentityCardCertification.this, ID_CARD_FRONT)));
        ivIdCardBack.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(IdentityCardCertification.this, ID_CARD_SIDE)));
        ivIdCardInHand.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(IdentityCardCertification.this, ID_CARD_HAND)));
        tvSave.setOnClickListener((v) -> {
            doSave();
        });

        if (!TextUtils.isEmpty(mTechWorkerVerifyEntity.getIdCardFront())) {
            fillData();
        }


    }


    private void fillData() {
        GlideUtil.intoImageView(this,com.eanfang.BuildConfig.OSS_SERVER + mTechWorkerVerifyEntity.getIdCardFront(),ivIdCardFront);
        GlideUtil.intoImageView(this,com.eanfang.BuildConfig.OSS_SERVER + mTechWorkerVerifyEntity.getIdCardSide(),ivIdCardBack);
        GlideUtil.intoImageView(this,com.eanfang.BuildConfig.OSS_SERVER + mTechWorkerVerifyEntity.getIdCardHand(),ivIdCardInHand);
    }

    /**
     * 保存照片
     */
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

        Intent intent = new Intent(this, OtherDataActivity.class);
//        Intent intent = new Intent(this, SubmitSuccessfullyJsActivity.class);
        intent.putExtra("bean", mTechWorkerVerifyEntity);
        startActivity(intent);
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
                mTechWorkerVerifyEntity.setIdCardFront(imgKey);
                GlideUtil.intoImageView(this,"file://" + image.getOriginalPath(),ivIdCardFront);
                break;
            // 反面
            case ID_CARD_SIDE:
                mTechWorkerVerifyEntity.setIdCardSide(imgKey);
                GlideUtil.intoImageView(this,"file://" + image.getOriginalPath(),ivIdCardBack);
                break;
            // 手持
            case ID_CARD_HAND:
                mTechWorkerVerifyEntity.setIdCardHand(imgKey);
                GlideUtil.intoImageView(this,"file://" + image.getOriginalPath(),ivIdCardInHand);
                break;
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
        });

    }

}
