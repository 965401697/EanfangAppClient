package net.eanfang.worker.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_card_certification);
        ButterKnife.bind(this);
        setTitle("身份信息");
        setLeftBack();
        initListener();
//        workerInfoBean = (WorkerInfoBean) getIntent().getSerializableExtra("workerInfoBean");
//        isAdd = getIntent().getBooleanExtra("isAdd", false);
//        isEdit = getIntent().getBooleanExtra("isEdit", false);
//        mVerifyStatus = getIntent().getIntExtra("verifyStatus", 100);
    }

    private void initListener() {
        ivIdCardFront.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(IdentityCardCertification.this, ID_CARD_FRONT)));
        ivIdCardBack.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(IdentityCardCertification.this, ID_CARD_SIDE)));
        ivIdCardInHand.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(IdentityCardCertification.this, ID_CARD_HAND)));
        tvSave.setOnClickListener((v) -> {
//            doSave();
            Intent intent = new Intent(this, OtherDataActivity.class);
            startActivity(intent);

        });

//        // 已经认证成功/ 已经提交认证，正在认证中 无法点击操作
//        if (mVerifyStatus == 2 || mVerifyStatus == 1) {
//            ivIdCardFront.setEnabled(false);
//            ivIdCardBack.setEnabled(false);
//            ivIdCardInHand.setEnabled(false);
//            snplMomentAccident.setEditable(false);
//            snplMomentCrim.setEditable(false);
//        }
//        // 编辑
//        if (isEdit) {
//            ivIdCardFront.setEnabled(true);
//            ivIdCardBack.setEnabled(true);
//            ivIdCardInHand.setEnabled(true);
//            snplMomentAccident.setEditable(true);
//            snplMomentCrim.setEditable(true);
//        }

    }

    /**
     * 保存照片
     */
//    private void doSave() {
//        if (StringUtils.isEmpty(workerInfoBean.getIdCardFront())) {
//            showToast("请添加身份证正面照");
//            return;
//        }
//        if (StringUtils.isEmpty(workerInfoBean.getIdCardHand())) {
//            showToast("请添加手持身份证照片");
//            return;
//        }
//        if (StringUtils.isEmpty(workerInfoBean.getIdCardSide())) {
//            showToast("请添加身份证反面照");
//            return;
//        }
//        String accidentPic = PhotoUtils.getPhotoUrl("account/verify/", snplMomentAccident, uploadMap, false);
////        if (StringUtils.isEmpty(accidentPic)) {
////            showToast("请添加保险照");
////            return;
////        }
//        workerInfoBean.setAccidentPics(accidentPic);
//
//        String crimePic = PhotoUtils.getPhotoUrl("account/verify/", snplMomentCrim, uploadMap, false);
////        if (StringUtils.isEmpty(crimePic)) {
////            showToast("请添加犯罪照");
////            return;
////        }
//        workerInfoBean.setCrimePic(crimePic);
//
//        /**
//         * 提交照片
//         * */
//        if (uploadMap.size() != 0) {
//            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
//                @Override
//                public void onOssSuccess() {
//                    runOnUiThread(() -> {
//                        Intent intent = new Intent();
//                        intent.putExtra("photos", workerInfoBean);
//                        setResult(RESULT_ADD_PHOTO, intent);
//                        finishSelf();
//                    });
//                }
//            });
//            return;
//        } else if (isAdd) {
//            Intent intent = new Intent();
//            intent.putExtra("photos", workerInfoBean);
//            setResult(RESULT_ADD_PHOTO, intent);
//            finishSelf();
//        }
//
//    }
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
//                workerInfoBean.setIdCardFront(imgKey);
//                ivIdCardFront.setImageURI("file://" + image.getOriginalPath());
                break;
            // 反面
            case ID_CARD_SIDE:
//                workerInfoBean.setIdCardSide(imgKey);
                ivIdCardBack.setImageURI("file://" + image.getOriginalPath());
                break;
            // 手持
            case ID_CARD_HAND:
//                workerInfoBean.setIdCardHand(imgKey);
                ivIdCardInHand.setImageURI("file://" + image.getOriginalPath());
                break;
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
        });

    }

}
