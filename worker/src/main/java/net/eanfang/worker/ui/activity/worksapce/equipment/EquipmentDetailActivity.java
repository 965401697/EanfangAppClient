package net.eanfang.worker.ui.activity.worksapce.equipment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JumpItent;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.CustDeviceEntity;
import com.yaf.base.entity.ShopMaintenanceExamDeviceEntity;
import com.yaf.base.entity.ShopMaintenanceOrderEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.maintenance.MaintenanceAddCheckResultActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EquipmentDetailActivity extends BaseWorkerActivity {

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
    SimpleDraweeView ivPicOne;
    @BindView(R.id.iv_pic_two)
    SimpleDraweeView ivPicTwo;
    @BindView(R.id.iv_pic_three)
    SimpleDraweeView ivPicThree;
    @BindView(R.id.iv_pic_four)
    SimpleDraweeView ivPicFour;
    @BindView(R.id.iv_pic_five)
    SimpleDraweeView ivPicFive;
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
    SimpleDraweeView ivLocaleOne;
    @BindView(R.id.iv_locale_two)
    SimpleDraweeView ivLocaleTwo;
    @BindView(R.id.iv_loacle_three)
    SimpleDraweeView ivLoacleThree;
    @BindView(R.id.iv_locale_four)
    SimpleDraweeView ivLocaleFour;
    @BindView(R.id.iv_loacle_five)
    SimpleDraweeView ivLoacleFive;
    @BindView(R.id.tv_notice)
    TextView tvNotice;

    private String id = "";
    private CustDeviceEntity mBean;

    private List<ShopMaintenanceExamDeviceEntity> examDeviceEntityListBeans = new ArrayList<>();
    private Long orderId;

    // 扫描二维码返回 数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_detail);
        ButterKnife.bind(this);
        setTitle("设备详情");
        setLeftBack();
        id = getIntent().getStringExtra("id");
        initData();
        initDeal();
    }


    private void initData() {

        EanfangHttp.post(NewApiService.DEVICE_DETAIL)
                .params("id", id)
                .params("ownerCompanyId", getIntent().getStringExtra("ownerCompanyId"))
                .params("assigneeCompanyId", getIntent().getStringExtra("assigneeCompanyId"))
                .params("businessOneCode", getIntent().getStringExtra("businessOneCode"))
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

        if (bean.getPicture() != null) {
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
        if (locationPictures != null) {
            if (locationPictures != null && locationPictures.length >= 1) {
                ivLocaleOne.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + locationPictures[0]));
            }
            if (locationPictures != null && locationPictures.length >= 2) {
                ivLocaleTwo.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + locationPictures[1]));
            }
            if (locationPictures != null && locationPictures.length >= 3) {
                ivLoacleThree.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + locationPictures[2]));
            }

            if (locationPictures != null && locationPictures.length >= 4) {
                ivLocaleFour.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + locationPictures[3]));
            }
            if (locationPictures != null && locationPictures.length >= 5) {
                ivLoacleFive.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + locationPictures[4]));
            }
        }
    }

    /**
     * 当查看的设备是当前技师所操作的维保单中的一个设备时。右上角出现维保处理按钮。
     */
    private void initDeal() {
        EanfangHttp.post(NewApiService.SCAN_DEVICE_DEAL)
                .params("deviceId", id)
                .execute(new EanfangCallback<ShopMaintenanceOrderEntity>(this, true, ShopMaintenanceOrderEntity.class, bean -> {
                    setRightVisible();
                    setRightTitle("维保处理");
                    examDeviceEntityListBeans = bean.getExamDeviceEntityList();
                    orderId = bean.getId();
                }));

        setRightTitleOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putLong("orderId", orderId);
            bundle.putSerializable("list", (Serializable) examDeviceEntityListBeans);
            bundle.putString("type", "scanDevice");
            JumpItent.jump(EquipmentDetailActivity.this, MaintenanceAddCheckResultActivity.class, bundle);
        });
    }

    @OnClick({R.id.tv_equipment_paramter, R.id.tv_history})
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
        }
    }
}
