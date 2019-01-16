package net.eanfang.worker.ui.activity.my.specialist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
import com.yaf.base.entity.ExpertsCertificationEntity;
import com.yaf.sys.entity.AccountEntity;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SpecialistCertificationActivity extends BaseActivityWithTakePhoto {


    @BindView(R.id.ll_headers)
    LinearLayout llHeaders;
    //联系人姓名
    @BindView(R.id.tv_contact_name)
    TextView tvContactName;
    // 联系人电话
    @BindView(R.id.tv_contact_phone)
    TextView tvContactPhone;
    @BindView(R.id.iv_header)
    SimpleDraweeView ivHeader;
    @BindView(R.id.et_card_id)
    EditText etCardId;
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_woman)
    RadioButton rbWoman;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.et_intro)
    EditText etIntro;

    private final int HEADER_PIC = 107;

    private ExpertsCertificationEntity mExpertsCertificationEntity;
    private int mStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specilalist_certification);
        ButterKnife.bind(this);
        setTitle("实名认证");
        setLeftBack();

        mStatus = getIntent().getIntExtra("status", -1);

        initViews();
        setOnClick();
        if (mStatus > 0) {
            // 获取专家信息
            EanfangHttp.get(UserApi.GET_EXPERT_INFO_BY_ID+"/"+EanfangApplication.get().getAccId())
                    .execute(new EanfangCallback<ExpertsCertificationEntity>(SpecialistCertificationActivity.this, true, ExpertsCertificationEntity.class, (bean) -> {
                        mExpertsCertificationEntity = bean;
                        fillData();
                    }));
        } else {
            mExpertsCertificationEntity = new ExpertsCertificationEntity();
        }
    }

    private void fillData() {
        ivHeader.setImageURI(com.eanfang.BuildConfig.OSS_SERVER + mExpertsCertificationEntity.getAvatarPhoto());
        String contactName = EanfangApplication.get().getUser().getAccount().getRealName();
        String mobile = EanfangApplication.get().getUser().getAccount().getMobile();

        if (!StringUtils.isEmpty(contactName)) {
            tvContactName.setText(contactName);
        }
        if (!StringUtils.isEmpty(mobile)) {
            tvContactPhone.setText(mobile);
        }
        etCardId.setText(EanfangApplication.get().getUser().getAccount().getIdCard());
        //0女1男
        if (EanfangApplication.get().getUser().getAccount().getGender() == 0) {
            rbWoman.setChecked(true);
        } else {
            rbMan.setChecked(true);
        }
        etIntro.setText(mExpertsCertificationEntity.getIntro());
    }

    private void initViews() {
        String contactName = EanfangApplication.get().getUser().getAccount().getRealName();
        String mobile = EanfangApplication.get().getUser().getAccount().getMobile();
        if (!StringUtils.isEmpty(contactName)) {
            tvContactName.setText(contactName);
        }
        if (!StringUtils.isEmpty(mobile)) {
            tvContactPhone.setText(mobile);
        }
    }

    private void setOnClick() {


        llHeaders.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(SpecialistCertificationActivity.this, HEADER_PIC)));
    }


    /**
     * 图片选择 回调
     *
     * @param result
     * @param resultCode
     */
    @Override
    public void takeSuccess(TResult result, int resultCode) {
        super.takeSuccess(result, resultCode);
        if (result == null || result.getImage() == null) {
            return;
        }
        TImage image = result.getImage();
        String imgKey = "account/" + UuidUtil.getUUID() + ".png";
        switch (resultCode) {
            case HEADER_PIC:
                mExpertsCertificationEntity.setAvatarPhoto(imgKey);
                ivHeader.setImageURI("file://" + image.getOriginalPath());
                break;
            default:
                break;
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {


        });

    }

    private void setData() {


        String cardId = etCardId.getText().toString().trim();
        String info = etIntro.getText().toString().trim();

        if (StringUtils.isEmpty(mExpertsCertificationEntity.getAvatarPhoto())) {
            showToast("请选择技师头像");
            return;
        }

        if (StringUtils.isEmpty(cardId)) {
            showToast("请输入身份证号");
            return;
        }
        if (StringUtils.isEmpty(info)) {
            showToast("请输入个人简介");
            return;
        }


        mExpertsCertificationEntity.setAccId(EanfangApplication.get().getAccId());
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setRealName(tvContactName.getText().toString().trim());
        accountEntity.setMobile(tvContactPhone.getText().toString().trim());
        accountEntity.setIdCard(cardId);
        accountEntity.setGender(rbMan.isChecked() ? 1 : 0);
        mExpertsCertificationEntity.setAccountEntity(accountEntity);
        mExpertsCertificationEntity.setIntro(info);

        Intent intent = new Intent(this, SpecialistIdentityCardCertification.class);
        intent.putExtra("bean", mExpertsCertificationEntity);
        startActivity(intent);
    }

    @OnClick(R.id.tv_confim)
    public void onViewClicked() {
        setData();
    }

}
