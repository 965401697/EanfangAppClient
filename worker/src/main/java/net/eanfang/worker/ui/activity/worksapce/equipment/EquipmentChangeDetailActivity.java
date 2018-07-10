package net.eanfang.worker.ui.activity.worksapce.equipment;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetDateUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.CustDeviceChangeLogEntity;
import com.yaf.base.entity.CustDeviceParamEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EquipmentChangeDetailActivity extends BaseWorkerActivity {

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
    @BindView(R.id.tv_voltage)
    TextView tvVoltage;
    @BindView(R.id.tv_electricity)
    TextView tvElectricity;
    @BindView(R.id.tv_ip)
    TextView tvIp;

    private CustDeviceChangeLogEntity bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_change_detail);
        ButterKnife.bind(this);
        setTitle("变更详情");
        setLeftBack();

        initData();
    }

    private void initData() {

        EanfangHttp.post(NewApiService.DEVICE_CHANGE_DETAIL + getIntent().getLongExtra("id",0))

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
        tvCreatetime.setText( GetDateUtils.dateToFormatString(bean.getInstallDate(),"yyyy-MM-dd"));
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

        if (bean.getChargeOrgEntity() != null && bean.getChargeOrgEntity().getBelongTopCompany() != null) {
            tvSection.setText(bean.getChargeOrgEntity().getBelongTopCompany().getOrgName());
        }

        if (bean.getChargeUserEntity() != null && bean.getChargeUserEntity().getAccountEntity() != null) {
            tvPreson.setText(bean.getChargeUserEntity().getAccountEntity().getRealName());
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
            ivLocaleOne.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + pictures[0]));
        } else if (pictures != null && pictures.length >= 2) {
            ivLocaleTwo.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + pictures[1]));
        } else if (pictures != null && pictures.length >= 3) {
            ivLoacleThree.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + pictures[2]));
        }

        if (bean.getParams() != null && bean.getParams().size() > 0) {
            for (CustDeviceParamEntity bean : bean.getParams()) {
                if (bean.getParamName().equals("电压")) {
                    tvVoltage.setText(bean.getParamValue());
                } else if (bean.getParamName().equals("电流")) {
                    tvElectricity.setText(bean.getParamValue());
                } else if (bean.getParamName().equals("IP地址")) {
                    tvIp.setText(bean.getParamValue());
                }
            }
        }
    }
}