package net.eanfang.worker.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eanfang.application.EanfangApplication;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CertificationActivity extends BaseActivityWithTakePhoto {


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        ButterKnife.bind(this);
        setTitle("实名认证");
        setLeftBack();

        initViews();
        setOnClick();
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

        llHeaders.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(CertificationActivity.this, HEADER_PIC)));
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
//                workerInfoBean.setAvatarPhoto(imgKey);
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


//        if (StringUtils.isEmpty(workerInfoBean.getAvatarPhoto())) {
//            showToast("请选择技师头像");
//            return;
//        } else if (StringUtils.isEmpty(workerInfoBean.getAvatarPhoto())) {
//            workerInfoBean.setAvatarPhoto(workerInfoBean.getAvatarPhoto());
//        }

        if (StringUtils.isEmpty(cardId)) {
            showToast("请输入身份证号");
            return;
        }
        if (StringUtils.isEmpty(info)) {
            showToast("请输入个人简介");
            return;
        }


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
//        workerInfoBean.setContactName(mUrgentName);
//        workerInfoBean.setContactPhone(mUrgentPhone);
//        workerInfoBean.setPayAccount(mPayAccount);
//        workerInfoBean.setIntro(mEtIntro);
//        workerInfoBean.setPayType(GetConstDataUtils.getPayTypeList().indexOf(tvPayType.getText().toString().trim()));
//        workerInfoBean.setWorkingLevel(GetConstDataUtils.getWorkingLevelList().indexOf(tvWorkingLevel.getText().toString().trim()));
//        workerInfoBean.setWorkingYear(GetConstDataUtils.getWorkingYearList().indexOf(tvWorkingYear.getText().toString().trim()));
//        workerInfoBean.setAccId(workerInfoBean.getAccId());
//        workerInfoBean.setUserId(EanfangApplication.getApplication().getUser().getAccount().getNullUser());
//        workerInfoBean.setId(workerInfoBean.getId());
//
//        workerInfoBean.setStatus(0);
//        String json = JSONObject.toJSONString(workerInfoBean);
//        if (uploadMap.size() != 0) {
//            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
//                @Override
//                public void onOssSuccess() {
//                    runOnUiThread(() -> submitSuccess(json));
//                }
//            });
//        } else {
//            submitSuccess(json);
//        }
    }

    @OnClick(R.id.tv_confim)
    public void onViewClicked() {
        Intent intent = new Intent(this, IdentityCardCertification.class);
        startActivity(intent);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data == null) {
//            return;
//        }
//        if (requestCode == REQUETST_ADD_PHOTO && resultCode == RESULT_ADD_PHOTO) {
//            workerInfoBean = (WorkerInfoBean) data.getSerializableExtra("photos");
//        }
//
//    }
}
