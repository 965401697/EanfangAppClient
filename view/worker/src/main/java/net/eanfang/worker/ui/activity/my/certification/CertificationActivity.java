package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.IPictureCallBack;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;

import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.rx.RxPerm;
import com.luck.picture.lib.entity.LocalMedia;
import com.eanfang.biz.model.entity.TechWorkerVerifyEntity;
import com.eanfang.biz.model.entity.AccountEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.util.StrUtil;

public class CertificationActivity extends BaseWorkeActivity {


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

    private TechWorkerVerifyEntity mTechWorkerVerifyEntity;
    private int mStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_certification);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("实名认证");
        setLeftBack(true);

        mStatus = getIntent().getIntExtra("status", -1);

        initViews();
        setOnClick();
        if (mStatus > 0) {
            // 获取技师信息
            EanfangHttp.get(UserApi.GET_WORKER_INFO)
                    .execute(new EanfangCallback<TechWorkerVerifyEntity>(CertificationActivity.this, true, TechWorkerVerifyEntity.class, (bean) -> {
                        mTechWorkerVerifyEntity = bean;
                        fillData();
                    }));
        } else {
            mTechWorkerVerifyEntity = new TechWorkerVerifyEntity();
        }
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    private void fillData() {
        AccountEntity accountEntity = WorkerApplication.get().getLoginBean().getAccount();
        GlideUtil.intoImageView(this, com.eanfang.BuildConfig.OSS_SERVER + mTechWorkerVerifyEntity.getAvatarPhoto(), ivHeader);
        String contactName = accountEntity.getRealName();
        String mobile = accountEntity.getMobile();

        if (!StrUtil.isEmpty(contactName)) {
            tvContactName.setText(contactName);
        }
        if (!StrUtil.isEmpty(mobile)) {
            tvContactPhone.setText(mobile);
        }
        etCardId.setText(WorkerApplication.get().getLoginBean().getAccount().getIdCard());
        //0女1男
        if (WorkerApplication.get().getLoginBean().getAccount().getGender() == 0) {
            rbWoman.setChecked(true);
        } else {
            rbMan.setChecked(true);
        }
        etIntro.setText(mTechWorkerVerifyEntity.getIntro());
    }

    private void initViews() {
        AccountEntity accountEntity = WorkerApplication.get().getLoginBean().getAccount();
        String contactName = accountEntity.getRealName();
        String mobile = accountEntity.getMobile();
        if (!StrUtil.isEmpty(contactName)) {
            tvContactName.setText(contactName);
        }
        if (!StrUtil.isEmpty(mobile)) {
            tvContactPhone.setText(mobile);
        }
    }

    private void setOnClick() {
        llHeaders.setOnClickListener(v -> RxPerm.get(this).cameraPerm((isSuccess) -> headImage()));
    }

    private void headImage() {
        SDKManager.getPicture().create(this).takePhoto(iPictureCallBack);
    }
    /**
     * 图片选择 回调
     */

    IPictureCallBack iPictureCallBack = new IPictureCallBack() {
        @Override
        public void onSuccess(List<LocalMedia> list) {

            String imgKey = "account/" + StrUtil.uuid() + ".png";
            mTechWorkerVerifyEntity.setAvatarPhoto(imgKey);
            GlideUtil.intoImageView(CertificationActivity.this, "file://" + list.get(0).getPath(), ivHeader);

            SDKManager.ossKit(CertificationActivity.this).asyncPutImage(imgKey, list.get(0).getPath(), (isSuccess) -> {

            });
        }
    };


    private void setData() {


        String cardId = etCardId.getText().toString().trim();
        String info = etIntro.getText().toString().trim();

        if (StrUtil.isEmpty(mTechWorkerVerifyEntity.getAvatarPhoto())) {
            showToast("请选择技师头像");
            return;
        }

        if (StrUtil.isEmpty(cardId)) {
            showToast("请输入身份证号");
            return;
        }
        if (StrUtil.isEmpty(info)) {
            showToast("请输入个人简介");
            return;
        }


        mTechWorkerVerifyEntity.setAccId(WorkerApplication.get().getAccId());
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setRealName(tvContactName.getText().toString().trim());
        accountEntity.setMobile(tvContactPhone.getText().toString().trim());
        accountEntity.setIdCard(cardId);
        accountEntity.setGender(rbMan.isChecked() ? 1 : 0);
        mTechWorkerVerifyEntity.setAccountEntity(accountEntity);
        mTechWorkerVerifyEntity.setIntro(info);

        Intent intent = new Intent(this, IdentityCardCertification.class);
        intent.putExtra("bean", mTechWorkerVerifyEntity);
        startActivity(intent);
    }

    @OnClick(R.id.tv_confim)
    public void onViewClicked() {
        setData();
    }

}
