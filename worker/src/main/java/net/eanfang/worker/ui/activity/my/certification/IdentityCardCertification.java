package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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

public class IdentityCardCertification extends BaseActivityWithTakePhoto {
    //身份证正面
    private final int ID_CARD_FRONT = 101;
    // 身份证反面
    private final int ID_CARD_SIDE = 102;
    //身份证手持
    private final int ID_CARD_HAND = 103;

    @BindView(R.id.iv_idCard_front)
    SimpleDraweeView ivIdCardFront;

    @BindView(R.id.iv_idCard_back)
    SimpleDraweeView ivIdCardBack;

    @BindView(R.id.iv_idCard_inHand)
    SimpleDraweeView ivIdCardInHand;

    @BindView(R.id.tv_save)
    TextView tvSave;

    private TechWorkerVerifyEntity techWorkerVerifyEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_card_certification);
        ButterKnife.bind(this);
        setTitle("身份信息");
        setLeftBack();
        initListener();
        techWorkerVerifyEntity = (TechWorkerVerifyEntity) getIntent().getSerializableExtra("bean");
    }

    private void initListener() {
        ivIdCardFront.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(IdentityCardCertification.this, ID_CARD_FRONT)));
        ivIdCardBack.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(IdentityCardCertification.this, ID_CARD_SIDE)));
        ivIdCardInHand.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(IdentityCardCertification.this, ID_CARD_HAND)));
        tvSave.setOnClickListener((v) -> {
            doSave();
        });


    }

    /**
     * 保存照片
     */
    private void doSave() {
        if (StringUtils.isEmpty(techWorkerVerifyEntity.getIdCardFront())) {
            showToast("请添加身份证正面照");
            return;
        }
        if (StringUtils.isEmpty(techWorkerVerifyEntity.getIdCardHand())) {
            showToast("请添加手持身份证照片");
            return;
        }
        if (StringUtils.isEmpty(techWorkerVerifyEntity.getIdCardSide())) {
            showToast("请添加身份证反面照");
            return;
        }

        Intent intent = new Intent(this, OtherDataActivity.class);
        intent.putExtra("bean", techWorkerVerifyEntity);
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
                techWorkerVerifyEntity.setIdCardFront(imgKey);
                ivIdCardFront.setImageURI("file://" + image.getOriginalPath());
                break;
            // 反面
            case ID_CARD_SIDE:
                techWorkerVerifyEntity.setIdCardSide(imgKey);
                ivIdCardBack.setImageURI("file://" + image.getOriginalPath());
                break;
            // 手持
            case ID_CARD_HAND:
                techWorkerVerifyEntity.setIdCardHand(imgKey);
                ivIdCardInHand.setImageURI("file://" + image.getOriginalPath());
                break;
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
        });

    }

}
