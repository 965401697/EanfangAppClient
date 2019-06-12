package net.eanfang.worker.ui.activity.my.certification;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.WorkerInfoBean;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.StringUtils;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CertificationInfoActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
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
    ImageView ivIdCardFront;
    @BindView(R.id.iv_idCard_back)
    ImageView ivIdCardBack;
    @BindView(R.id.iv_idCard_inHand)
    ImageView ivIdCardInHand;
    @BindView(R.id.et_urgent_name)
    EditText etUrgentName;
    @BindView(R.id.et_urgent_phone)
    EditText etUrgentPhone;
    @BindView(R.id.snpl_moment_accident)
    BGASortableNinePhotoLayout snplMomentAccident;
    @BindView(R.id.snpl_moment_crim)
    BGASortableNinePhotoLayout snplMomentCrim;
    private static final String DEFAULT_CARD_GENDER = "女";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_info);
        ButterKnife.bind(this);
        setTitle("技师认证详情");
        setLeftBack();
        getData();
    }

    private void getData() {
        // 获取技师信息
        EanfangHttp.get(UserApi.GET_WORKER_INFO)
                .execute(new EanfangCallback<WorkerInfoBean>(CertificationInfoActivity.this, true, WorkerInfoBean.class, (bean) -> {
                    initViews(bean);
                }));

    }

    private void initViews(WorkerInfoBean bean) {
        GlideUtil.intoImageView(this,com.eanfang.BuildConfig.OSS_SERVER + bean.getAvatarPhoto(),ivHeader);
        String contactName = WorkerApplication.get().getLoginBean().getAccount().getRealName();
        String mobile = WorkerApplication.get().getLoginBean().getAccount().getMobile();

        if (!StringUtils.isEmpty(contactName)) {
            tvContactName.setText(contactName);
        }
        if (!StringUtils.isEmpty(mobile)) {
            tvContactPhone.setText(mobile);
        }
        etCardId.setFocusable(false);
        //Log.i("zhangyanran",WorkerApplication.get().getLoginBean().getAccount().getIdCard()+"-----------"+WorkerApplication.get().getLoginBean().getAccount().getRealName());
        etCardId.setText(bean.getIdCardNum());
        if (DEFAULT_CARD_GENDER.equals(bean.getIdCardGender())) {
            rbWoman.setSelected(true);
        } else {
            rbMan.setSelected(true);
        }
        rbWoman.setClickable(false);
        rbMan.setClickable(false);
        etIntro.setText(bean.getIntro());
        GlideUtil.intoImageView(this,com.eanfang.BuildConfig.OSS_SERVER + bean.getIdCardFront(),ivIdCardFront);
        GlideUtil.intoImageView(this,com.eanfang.BuildConfig.OSS_SERVER + bean.getIdCardSide(),ivIdCardBack);
        GlideUtil.intoImageView(this,com.eanfang.BuildConfig.OSS_SERVER + bean.getIdCardHand(),ivIdCardInHand);

        etUrgentName.setText(bean.getContactName());
        etUrgentPhone.setText(bean.getContactPhone());

        String[] crimePic = bean.getCrimePic().split(",");
        if (crimePic.length > 0) {
            List<String> crimePicList = new ArrayList<>();
            for (byte i = 0; i < crimePic.length; i++) {
                crimePicList.add(BuildConfig.OSS_SERVER + crimePic[i]);
            }
            snplMomentCrim.setData(crimePicList);
        }

        String[] accidentPics = bean.getAccidentPics().split(",");
        List<String> accidentPicList = new ArrayList<>();
        if (accidentPics.length > 0) {
            for (byte i = 0; i < accidentPics.length; i++) {
                accidentPicList.add(BuildConfig.OSS_SERVER + accidentPics[i]);
            }
            snplMomentAccident.setData(accidentPicList);
        }
    }
}
