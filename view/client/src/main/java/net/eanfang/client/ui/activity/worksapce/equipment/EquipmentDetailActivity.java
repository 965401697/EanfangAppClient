package net.eanfang.client.ui.activity.worksapce.equipment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.yaf.base.entity.CustDeviceEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.repair.AddTroubleActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.date.DateUtil;

//已提取
public class EquipmentDetailActivity extends BaseClientActivity {

    @BindView(R.id.tv_equipment_classfiy)
    TextView tvEquipmentClassfiy;
    @BindView(R.id.tv_brand_model)
    TextView tvBrandModel;
    @BindView(R.id.tv_equipment_name)
    TextView tvEquipmentName;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.tv_made)
    TextView tvMade;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.tv_equipment_num)
    TextView tvEquipmentNum;
    @BindView(R.id.tv_equipment_price)
    TextView tvEquipmentPrice;
    @BindView(R.id.iv_pic_one)
    ImageView ivPicOne;
    @BindView(R.id.iv_pic_two)
    ImageView ivPicTwo;
    @BindView(R.id.iv_pic_three)
    ImageView ivPicThree;
    @BindView(R.id.iv_pic_four)
    ImageView ivPicFour;
    @BindView(R.id.iv_pic_five)
    ImageView ivPicFive;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_position_num)
    TextView tvPositionNum;
    @BindView(R.id.tv_createtime)
    TextView tvCreatetime;
    @BindView(R.id.tv_equipment_status)
    TextView tvEquipmentStatus;
    @BindView(R.id.tv_section)
    TextView tvSection;
    @BindView(R.id.tv_preson)
    TextView tvPreson;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_service_person)
    TextView tvServicePerson;
    @BindView(R.id.iv_locale_one)
    ImageView ivLocaleOne;
    @BindView(R.id.iv_locale_two)
    ImageView ivLocaleTwo;
    @BindView(R.id.iv_loacle_three)
    ImageView ivLoacleThree;
    @BindView(R.id.iv_locale_four)
    ImageView ivLocaleFour;
    @BindView(R.id.iv_loacle_five)
    ImageView ivLoacleFive;
    @BindView(R.id.tv_notice)
    TextView tvNotice;

    private String id = "";
    private CustDeviceEntity mBean;

    // 扫码 扫描设备进行报修
    private boolean mScanRepair = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_equipment_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("设备详情");
        setLeftBack();
        id = getIntent().getStringExtra("id");
        mScanRepair = getIntent().getBooleanExtra("scan", false);
        if (mScanRepair) {
            setRightTitle("我要报修");
        } else {
            setRightGone();
        }
        initData();
    }

    private void initData() {

        EanfangHttp.post(NewApiService.DEVICE_DETAIL)
                .params("id", id)
                .execute(new EanfangCallback<CustDeviceEntity>(this, true, CustDeviceEntity.class) {

                    @Override
                    public void onSuccess(CustDeviceEntity bean) {
                        mBean = bean;
                        initViews(bean);
                    }
                });
    }

    private void initViews(CustDeviceEntity bean) {
        tvEquipmentClassfiy.setText(Config.get().getBaseNameByCode(bean.getBusinessThreeCode(), 1));
        tvBrandModel.setText(Config.get().getModelNameByCode(bean.getModelCode(), 2));
        tvEquipmentName.setText(bean.getDeviceName());
        tvUnit.setText(bean.getSellUnit());
        tvMade.setText(bean.getProducerName());
        tvPlace.setText(bean.getProducerPlace());
        tvEquipmentNum.setText(bean.getSerialNumber());
        tvEquipmentPrice.setText(String.valueOf(bean.getDevicePrice()));

        String[] picture = bean.getPicture().split(",");
        if (picture != null && picture.length >= 1) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + picture[0]),ivPicOne);
        }
        if (picture != null && picture.length >= 2) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + picture[1]),ivPicTwo);
        }
        if (picture != null && picture.length >= 3) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + picture[2]),ivPicThree);
        }
        if (picture != null && picture.length >= 4) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + picture[3]),ivPicFour);
        }
        if (picture != null && picture.length >= 5) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + picture[4]),ivPicFive);
        }

        tvNotice.setText(bean.getDeviceInfo());

        tvPosition.setText(bean.getLocation());
        tvPositionNum.setText(bean.getLocationNumber());
        tvCreatetime.setText(DateUtil.date(bean.getInstallDate()).toDateStr());
        if (bean.getStatus() == 0) {
            tvEquipmentStatus.setText("出厂");
        } else if (bean.getStatus() == 1) {
            tvEquipmentStatus.setText("仓储运输");
        } else if (bean.getStatus() == 2) {
            tvEquipmentStatus.setText("正常运行");
        } else if (bean.getStatus() == 3) {
            tvEquipmentStatus.setText("故障待修复");
        } else if (bean.getStatus() == 4) {
            tvEquipmentStatus.setText("备用");
        } else if (bean.getStatus() == 5) {
            tvEquipmentStatus.setText("禁用");
        } else {
            tvEquipmentStatus.setText("报废");
        }
        if (bean.getChargeOrgEntity() != null) {
            tvSection.setText(bean.getChargeOrgEntity().getOrgName());
        } else {
            tvSection.setText("无");
        }

        if (bean.getChargeUserEntity() != null && bean.getChargeUserEntity().getAccountEntity() != null) {
            tvPreson.setText(bean.getChargeUserEntity().getAccountEntity().getRealName());
        } else {
            tvPreson.setText("无");
        }

        tvYear.setText(bean.getWarrantyPeriod());

        //1:保外 0：保内
        if (bean.getWarrantyStatus() == 0) {
            tvStatus.setText("保内");
        } else {
            tvStatus.setText("保外");
        }

        tvCompanyName.setText(bean.getRepairCompany());
        tvServicePerson.setText(bean.getRepairUser());
        String[] locationPictures = bean.getLocationPictures().split(",");
        if (locationPictures != null && locationPictures.length >= 1) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + locationPictures[0]),ivLocaleOne);
        }
        if (locationPictures != null && locationPictures.length >= 2) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + locationPictures[1]),ivLocaleTwo);
        }
        if (locationPictures != null && locationPictures.length >= 3) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + locationPictures[2]),ivLoacleThree);
        }

        if (picture != null && locationPictures.length >= 4) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + locationPictures[3]),ivLocaleFour);
        }
        if (picture != null && locationPictures.length >= 5) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + locationPictures[4]),ivLoacleFive);
        }

    }

    @OnClick({R.id.tv_equipment_paramter, R.id.tv_history, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_equipment_paramter:
                Intent intent = new Intent(this, EquipmentParameterActivity.class);
//                ContentValues contentValues = new ContentValues();
                Bundle bundle = new Bundle();
                if (mBean.getParams() != null && mBean.getParams().size() > 0) {
//                    for (CustDeviceParamEntity bean : mBean.getParams()) {
//                        if (bean.getParamName().equals("电压")) {
//                            contentValues.put("0", bean.getParamValue());
//                        } else if (bean.getParamName().equals("电流")) {
//                            contentValues.put("1", bean.getParamValue());
//                        } else if (bean.getParamName().equals("IP地址")) {
//                            contentValues.put("2", bean.getParamValue());
//                        }
//                    }
                    bundle.putSerializable("params", (Serializable) mBean.getParams());
                }
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_history:
                Intent in = new Intent(this, EquipmentChangeListActivity.class);
                in.putExtra("deviceNo", mBean.getDeviceNo());
                startActivity(in);
                break;
            // 我要报修
            case R.id.tv_right:
                Bundle bundle_repair = new Bundle();
                bundle_repair.putSerializable("scan_repair", mBean);
                bundle_repair.putBoolean("isScanRepair", true);
                JumpItent.jump(EquipmentDetailActivity.this, AddTroubleActivity.class, bundle_repair);
                EventBus.getDefault().post(bundle_repair);
                finishSelf();
                break;
            default:
                break;
        }
    }
}
