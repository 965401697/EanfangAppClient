package net.eanfang.worker.ui.activity.my.specialist;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.sys.AccountEntity;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.ExpertsCertificationEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SpecialistCertificationInfoActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_header)
    SimpleDraweeView ivHeader;
    @BindView(R.id.tv_contact_name)
    TextView tvContactName;
    @BindView(R.id.tv_contact_phone)
    TextView tvContactPhone;
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
    @BindView(R.id.iv_idCard_front)
    SimpleDraweeView ivIdCardFront;
    @BindView(R.id.iv_idCard_back)
    SimpleDraweeView ivIdCardBack;
    @BindView(R.id.iv_idCard_inHand)
    SimpleDraweeView ivIdCardInHand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_certification_info);
        ButterKnife.bind(this);
        setTitle("专家认证详情");
        setLeftBack();
        getData();
    }

    private void getData() {

        // 获取技师信息
        EanfangHttp.get(UserApi.GET_EXPERT_INFO_BY_ID)
                .execute(new EanfangCallback<ExpertsCertificationEntity>(SpecialistCertificationInfoActivity.this, true, ExpertsCertificationEntity.class, (bean) -> {
                    initViews(bean);
                }));

    }

    private void initViews(ExpertsCertificationEntity bean) {
        ivHeader.setImageURI(com.eanfang.BuildConfig.OSS_SERVER + bean.getAvatarPhoto());
        AccountEntity accountEntity=WorkerApplication.get().getLoginBean().getAccount();
        String contactName =accountEntity .getRealName();
        String mobile = accountEntity.getMobile();

        if (!StringUtils.isEmpty(contactName)) {
            tvContactName.setText(contactName);
        }
        if (!StringUtils.isEmpty(mobile)) {
            tvContactPhone.setText(mobile);
        }
        AccountEntity accountEntity1=WorkerApplication.get().getAccount();
        etCardId.setFocusable(false);
        etCardId.setText(accountEntity.getIdCard());
        //0女1男
        if (accountEntity1.getGender() == 0) {
            rbWoman.setChecked(true);
        } else {
            rbMan.setChecked(true);
        }
        rbWoman.setClickable(false);
        rbMan.setClickable(false);
        etIntro.setText(bean.getIntro());
        ivIdCardFront.setImageURI(com.eanfang.BuildConfig.OSS_SERVER + bean.getIdCardFront());
        ivIdCardBack.setImageURI(com.eanfang.BuildConfig.OSS_SERVER + bean.getIdCardSide());
        ivIdCardInHand.setImageURI(com.eanfang.BuildConfig.OSS_SERVER + bean.getIdCardHand());
    }
}
