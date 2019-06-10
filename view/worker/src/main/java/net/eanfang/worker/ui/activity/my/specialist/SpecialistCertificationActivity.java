package net.eanfang.worker.ui.activity.my.specialist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;

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
    CircleImageView ivHeader;
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
            EanfangHttp.get(UserApi.GET_EXPERT_INFO_BY_ID)
                    .execute(new EanfangCallback<ExpertsCertificationEntity>(SpecialistCertificationActivity.this, true, ExpertsCertificationEntity.class, (bean) -> {
                        mExpertsCertificationEntity = bean;
                        fillData();
                    }));
        } else {
            mExpertsCertificationEntity = new ExpertsCertificationEntity();
        }
    }

    private void fillData() {
        GlideUtil.intoImageView(this,com.eanfang.BuildConfig.OSS_SERVER + mExpertsCertificationEntity.getAvatarPhoto(),ivHeader);
        String contactName = WorkerApplication.get().getLoginBean().getAccount().getRealName();
        String mobile =WorkerApplication.get().getLoginBean().getAccount().getMobile();

        if (!StringUtils.isEmpty(contactName)) {
            tvContactName.setText(contactName);
        }
        if (!StringUtils.isEmpty(mobile)) {
            tvContactPhone.setText(mobile);
        }
        etCardId.setText(WorkerApplication.get().getLoginBean().getAccount().getIdCard());
        //0女1男
        if (WorkerApplication.get().getLoginBean().getAccount().getGender() == 0) {
            rbWoman.setChecked(true);
        } else {
            rbMan.setChecked(true);
        }
        etIntro.setText(mExpertsCertificationEntity.getIntro());
    }

    private void initViews() {
        String contactName = WorkerApplication.get().getLoginBean().getAccount().getRealName();
        String mobile = WorkerApplication.get().getLoginBean().getAccount().getMobile();
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
                GlideUtil.intoImageView(this,"file://" + image.getOriginalPath(),ivHeader);
                break;
            default:
                break;
        }
        SDKManager.ossKit(this).asyncPutImage(imgKey, image.getOriginalPath(),(isSuccess) -> {});
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


        mExpertsCertificationEntity.setAccId(WorkerApplication.get().getAccId());
        mExpertsCertificationEntity.setExpertName(tvContactName.getText().toString().trim());
        mExpertsCertificationEntity.setPhonenumber(tvContactPhone.getText().toString().trim());
        mExpertsCertificationEntity.setIdCard(cardId);
        mExpertsCertificationEntity.setGender(rbMan.isChecked() ? 1 : 0);
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
