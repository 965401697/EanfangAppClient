package net.eanfang.worker.ui.activity.my.specialist;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseActivity;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.IPictureCallBack;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.rx.RxPerm;
import com.luck.picture.lib.entity.LocalMedia;
import com.eanfang.biz.model.entity.ExpertsCertificationEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.OwnDataHintActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;

/**
 * 已废弃
 *
 * @author jornl
 * @date 2019-07-17 14:44:10
 */
@Deprecated
public class SpecialistIdentityCardCertification extends BaseActivity {
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
        setContentView(R.layout.activity_specialist_identity_card_certification);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("实名认证");
        mExpertsCertificationEntity = (ExpertsCertificationEntity) getIntent().getSerializableExtra("bean");
        initListener();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    private int states = 0;

    private void initListener() {
        ivIdCardFront.setOnClickListener(v -> RxPerm.get(this).cameraPerm((isSuccess) -> {
            states = ID_CARD_FRONT;
            iv();
        }));
        ivIdCardBack.setOnClickListener(v -> RxPerm.get(this).cameraPerm((isSuccess) -> {
            states = ID_CARD_SIDE;
            iv();
        }));
        ivIdCardInHand.setOnClickListener(v -> RxPerm.get(this).cameraPerm((isSuccess) -> {
            states = ID_CARD_HAND;
            iv();
        }));
        tvSave.setOnClickListener((v) -> doSave());

        if (!TextUtils.isEmpty(mExpertsCertificationEntity.getIdCardFront())) {
            fillData();
        }
    }

    private void iv() {
        SDKManager.getPicture().create(this).takePhoto(iPictureCallBack);
    }

    IPictureCallBack iPictureCallBack = new IPictureCallBack() {
        @Override
        public void onSuccess(List<LocalMedia> list) {
            String imgKey = StrUtil.uuid() + ".png";
            switch (states) {
                // 身份证正面
                case ID_CARD_FRONT:
                    mExpertsCertificationEntity.setIdCardFront(imgKey);
                    GlideUtil.intoImageView(SpecialistIdentityCardCertification.this, "file://" + list.get(0).getPath(), ivIdCardFront);
                    break;
                // 反面
                case ID_CARD_SIDE:
                    mExpertsCertificationEntity.setIdCardSide(imgKey);
                    GlideUtil.intoImageView(SpecialistIdentityCardCertification.this, "file://" + list.get(0).getPath(), ivIdCardBack);
                    break;
                // 手持
                case ID_CARD_HAND:
                    mExpertsCertificationEntity.setIdCardHand(imgKey);
                    GlideUtil.intoImageView(SpecialistIdentityCardCertification.this, "file://" + list.get(0).getPath(), ivIdCardInHand);
                    break;
            }
            SDKManager.ossKit(SpecialistIdentityCardCertification.this).asyncPutImage(imgKey, list.get(0).getPath(), (isSuccess) -> {
            });
            states = 0;
        }
    };

    private void fillData() {
        GlideUtil.intoImageView(this, com.eanfang.BuildConfig.OSS_SERVER + mExpertsCertificationEntity.getIdCardFront(), ivIdCardFront);
        GlideUtil.intoImageView(this, com.eanfang.BuildConfig.OSS_SERVER + mExpertsCertificationEntity.getIdCardSide(), ivIdCardBack);
        GlideUtil.intoImageView(this, com.eanfang.BuildConfig.OSS_SERVER + mExpertsCertificationEntity.getIdCardHand(), ivIdCardInHand);
    }

    /**
     * 保存照片
     */
    private void doSave() {
        if (StrUtil.isEmpty(mExpertsCertificationEntity.getIdCardFront())) {
            showToast("请添加身份证正面照");
            return;
        }
        if (StrUtil.isEmpty(mExpertsCertificationEntity.getIdCardHand())) {
            showToast("请添加手持身份证照片");
            return;
        }
        if (StrUtil.isEmpty(mExpertsCertificationEntity.getIdCardSide())) {
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
        finish();
    }

}
