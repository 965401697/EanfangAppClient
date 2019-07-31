package net.eanfang.worker.ui.activity.my.certification;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.picture.picture.PictureRecycleView;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.biz.model.bean.WorkerInfoBean;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GlideUtil;
import com.luck.picture.lib.entity.LocalMedia;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;

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

    private static final String DEFAULT_CARD_GENDER = "女";
    @BindView(R.id.picture_recycler_accident)
    PictureRecycleView pictureRecyclerAccident;
    @BindView(R.id.picture_recycler_crim)
    PictureRecycleView pictureRecyclerCrim;
    List<LocalMedia> accidentPicList = new ArrayList<>();
    List<LocalMedia> crimePicList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_certification_info);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
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
        GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + bean.getAvatarPhoto(), ivHeader);
        String contactName = WorkerApplication.get().getLoginBean().getAccount().getRealName();
        String mobile = WorkerApplication.get().getLoginBean().getAccount().getMobile();

        if (!StrUtil.isEmpty(contactName)) {
            tvContactName.setText(contactName);
        }
        if (!StrUtil.isEmpty(mobile)) {
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
        GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + bean.getIdCardFront(), ivIdCardFront);
        GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + bean.getIdCardSide(), ivIdCardBack);
        GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + bean.getIdCardHand(), ivIdCardInHand);

        etUrgentName.setText(bean.getContactName());
        etUrgentPhone.setText(bean.getContactPhone());

        crimePicList = pictureRecyclerCrim.setData(bean.getCrimePic());
        pictureRecyclerCrim.showImagev(crimePicList, listener);

        accidentPicList = pictureRecyclerAccident.setData(bean.getAccidentPics());
        pictureRecyclerAccident.showImagev(accidentPicList, listener_accident);
    }

    PictureRecycleView.ImageListener listener = list -> crimePicList = list;
    PictureRecycleView.ImageListener listener_accident = list -> accidentPicList = list;
}
