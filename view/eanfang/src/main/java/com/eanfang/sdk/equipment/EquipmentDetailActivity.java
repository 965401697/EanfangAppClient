package com.eanfang.sdk.equipment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetDateUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.CustDeviceEntity;


import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EquipmentDetailActivity extends BaseActivity {

    @BindView(R2.id.tv_equipment_classfiy)
    TextView tvEquipmentClassfiy;
    @BindView(R2.id.tv_brand_model)
    TextView tvBrandModel;
    @BindView(R2.id.tv_equipment_name)
    TextView tvEquipmentName;
    @BindView(R2.id.tv_unit)
    TextView tvUnit;
    @BindView(R2.id.tv_made)
    TextView tvMade;
    @BindView(R2.id.tv_place)
    TextView tvPlace;
    @BindView(R2.id.tv_equipment_num)
    TextView tvEquipmentNum;
    @BindView(R2.id.tv_equipment_price)
    TextView tvEquipmentPrice;
    @BindView(R2.id.iv_pic_one)
    SimpleDraweeView ivPicOne;
    @BindView(R2.id.iv_pic_two)
    SimpleDraweeView ivPicTwo;
    @BindView(R2.id.iv_pic_three)
    SimpleDraweeView ivPicThree;
    @BindView(R2.id.iv_pic_four)
    SimpleDraweeView ivPicFour;
    @BindView(R2.id.iv_pic_five)
    SimpleDraweeView ivPicFive;
    @BindView(R2.id.tv_position)
    TextView tvPosition;
    @BindView(R2.id.tv_position_num)
    TextView tvPositionNum;
    @BindView(R2.id.tv_createtime)
    TextView tvCreatetime;
    @BindView(R2.id.tv_equipment_status)
    TextView tvEquipmentStatus;
    @BindView(R2.id.tv_section)
    TextView tvSection;
    @BindView(R2.id.tv_preson)
    TextView tvPreson;
    @BindView(R2.id.tv_year)
    TextView tvYear;
    @BindView(R2.id.tv_status)
    TextView tvStatus;
    @BindView(R2.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R2.id.tv_service_person)
    TextView tvServicePerson;
    @BindView(R2.id.iv_locale_one)
    SimpleDraweeView ivLocaleOne;
    @BindView(R2.id.iv_locale_two)
    SimpleDraweeView ivLocaleTwo;
    @BindView(R2.id.iv_loacle_three)
    SimpleDraweeView ivLoacleThree;
    @BindView(R2.id.iv_locale_four)
    SimpleDraweeView ivLocaleFour;
    @BindView(R2.id.iv_loacle_five)
    SimpleDraweeView ivLoacleFive;
    @BindView(R2.id.tv_notice)
    TextView tvNotice;

    private String id = "";
    private CustDeviceEntity mBean;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_detail);
        ButterKnife.bind(this);
        setTitle("设备详情");
        setLeftBack();
        id = getIntent().getStringExtra("id");
            setRightGone();
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
            ivPicOne.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + picture[0]));
        }
        if (picture != null && picture.length >= 2) {
            ivPicTwo.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + picture[1]));
        }
        if (picture != null && picture.length >= 3) {
            ivPicThree.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + picture[2]));
        }
        if (picture != null && picture.length >= 4) {
            ivPicFour.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + picture[3]));
        }
        if (picture != null && picture.length >= 5) {
            ivPicFive.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + picture[4]));
        }

        tvNotice.setText(bean.getDeviceInfo());

        tvPosition.setText(bean.getLocation());
        tvPositionNum.setText(bean.getLocationNumber());
        tvCreatetime.setText(GetDateUtils.dateToFormatString(bean.getInstallDate(), "yyyy-MM-dd"));
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
            ivLocaleOne.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + locationPictures[0]));
        }
        if (locationPictures != null && locationPictures.length >= 2) {
            ivLocaleTwo.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + locationPictures[1]));
        }
        if (locationPictures != null && locationPictures.length >= 3) {
            ivLoacleThree.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + locationPictures[2]));
        }

        if (picture != null && locationPictures.length >= 4) {
            ivLocaleFour.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + locationPictures[3]));
        }
        if (picture != null && locationPictures.length >= 5) {
            ivLoacleFive.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + locationPictures[4]));
        }

    }


    @OnClick({R2.id.tv_equipment_paramter, R2.id.tv_history})
    public void onVie(View view) {
        int i = view.getId();
        if (i == R.id.tv_equipment_paramter) {
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
        } else if (i == R.id.tv_history) {
            Intent in = new Intent(this, EquipmentChangeListActivity.class);
            in.putExtra("deviceNo", mBean.getDeviceNo());
            startActivity(in);
        }
    }
}
