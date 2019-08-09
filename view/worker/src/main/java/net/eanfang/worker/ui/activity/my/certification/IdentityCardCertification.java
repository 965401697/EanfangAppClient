package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.base.kit.SDKManager;

import com.eanfang.base.kit.picture.IPictureCallBack;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.rx.RxPerm;
import com.luck.picture.lib.entity.LocalMedia;
import com.eanfang.biz.model.entity.TechWorkerVerifyEntity;

import net.eanfang.worker.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;

public class IdentityCardCertification extends BaseActivity {
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

        setContentView(R.layout.activity_identity_card_certification);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("身份信息");
        setLeftBack(true);
        mTechWorkerVerifyEntity = (TechWorkerVerifyEntity) getIntent().getSerializableExtra("bean");

        initListener();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    private int state=0;
    private void initListener() {
        ivIdCardFront.setOnClickListener(v -> RxPerm.get(this).cameraPerm((isSuccess)-> {
            state=ID_CARD_FRONT;
            imageV();
        }));
        ivIdCardBack.setOnClickListener(v -> RxPerm.get(this).cameraPerm((isSuccess)-> {
            state=ID_CARD_SIDE;
            imageV();
        }));
        ivIdCardInHand.setOnClickListener(v -> RxPerm.get(this).cameraPerm((isSuccess)-> {
            state=ID_CARD_HAND;
            imageV();
        }));
        tvSave.setOnClickListener((v) -> {
            doSave();
        });

        if (!TextUtils.isEmpty(mTechWorkerVerifyEntity.getIdCardFront())) {
            fillData();
        }


    }
    private void imageV() {
        SDKManager.getPicture().create(this).takePhoto(iPictureCallBack);
    }

    IPictureCallBack iPictureCallBack = new IPictureCallBack() {
        @Override
        public void onSuccess(List<LocalMedia> list) {
            String imgKey = StrUtil.uuid() + ".png";
            switch (state) {
                // 身份证正面
                case ID_CARD_FRONT:
                    mTechWorkerVerifyEntity.setIdCardFront(imgKey);
                    GlideUtil.intoImageView(IdentityCardCertification.this,"file://" + list.get(0).getPath(),ivIdCardFront);
                    break;
                // 反面
                case ID_CARD_SIDE:
                    mTechWorkerVerifyEntity.setIdCardSide(imgKey);
                    GlideUtil.intoImageView(IdentityCardCertification.this,"file://" + list.get(0).getPath(),ivIdCardBack);
                    break;
                // 手持
                case ID_CARD_HAND:
                    mTechWorkerVerifyEntity.setIdCardHand(imgKey);
                    GlideUtil.intoImageView(IdentityCardCertification.this,"file://" + list.get(0).getPath(),ivIdCardInHand);
                    break;
            }
            state=0;
            SDKManager.ossKit(IdentityCardCertification.this).asyncPutImage(imgKey, list.get(0).getPath(),(isSuccess) -> {});
        }
    };

    private void fillData() {
        GlideUtil.intoImageView(this,com.eanfang.BuildConfig.OSS_SERVER + mTechWorkerVerifyEntity.getIdCardFront(),ivIdCardFront);
        GlideUtil.intoImageView(this,com.eanfang.BuildConfig.OSS_SERVER + mTechWorkerVerifyEntity.getIdCardSide(),ivIdCardBack);
        GlideUtil.intoImageView(this,com.eanfang.BuildConfig.OSS_SERVER + mTechWorkerVerifyEntity.getIdCardHand(),ivIdCardInHand);
    }

    /**
     * 保存照片
     */
    private void doSave() {
        if (StrUtil.isEmpty(mTechWorkerVerifyEntity.getIdCardFront())) {
            showToast("请添加身份证正面照");
            return;
        }
        if (StrUtil.isEmpty(mTechWorkerVerifyEntity.getIdCardHand())) {
            showToast("请添加手持身份证照片");
            return;
        }
        if (StrUtil.isEmpty(mTechWorkerVerifyEntity.getIdCardSide())) {
            showToast("请添加身份证反面照");
            return;
        }

        Intent intent = new Intent(this, OtherDataActivity.class);
//        Intent intent = new Intent(this, SubmitSuccessfullyJsActivity.class);
        intent.putExtra("bean", mTechWorkerVerifyEntity);
        startActivity(intent);
    }

}
