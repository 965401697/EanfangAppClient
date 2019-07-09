package com.eanfang.sdk.equipment;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.sdk.equipment.adapter.EquipmentParamAdapter;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.GlideUtil;
import com.yaf.base.entity.CustDeviceChangeLogEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EquipmentChangeDetailActivity extends BaseActivity {

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
    ImageView ivLocaleOne;
    @BindView(R2.id.iv_locale_two)
    ImageView ivLocaleTwo;
    @BindView(R2.id.iv_loacle_three)
    ImageView ivLoacleThree;
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;

    private CustDeviceChangeLogEntity bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_equipment_change_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("变更详情");
        setLeftBack();

        initData();
    }

    private void initData() {

        EanfangHttp.post(NewApiService.DEVICE_CHANGE_DETAIL + getIntent().getLongExtra("id", 0))

                .execute(new EanfangCallback<CustDeviceChangeLogEntity>(this, true, CustDeviceChangeLogEntity.class) {

                    @Override
                    public void onSuccess(CustDeviceChangeLogEntity bean) {
                        EquipmentChangeDetailActivity.this.bean = bean;
                        initViews();
                    }
                });


    }

    private void initViews() {

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
        }else {
            tvSection.setText("无");
        }

        if (bean.getChargeUserEntity() != null && bean.getChargeUserEntity().getAccountEntity() != null) {
            tvPreson.setText(bean.getChargeUserEntity().getAccountEntity().getRealName());
        }else {
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
        String[] pictures = bean.getLocationPictures().split(",");
        if (pictures != null && pictures.length >= 1) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + pictures[0]),ivLocaleOne);
        } else if (pictures != null && pictures.length >= 2) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + pictures[1]),ivLocaleTwo);
        } else if (pictures != null && pictures.length >= 3) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + pictures[2]),ivLoacleThree);
        }

        if (bean.getParams() != null && bean.getParams().size() > 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            EquipmentParamAdapter adapter = new EquipmentParamAdapter();
            adapter.bindToRecyclerView(recyclerView);
            adapter.setNewData(bean.getParams());
        }
    }
}
