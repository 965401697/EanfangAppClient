package net.eanfang.client.ui.activity.worksapce.repair;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.util.ConnectivityChangeReceiver;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.CooperationEntity;
import com.yaf.base.entity.CustDeviceEntity;
import com.yaf.base.entity.RepairBugEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.equipment.EquipmentAddActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  15:10
 * @email houzhongzhou@yeah.net
 * @desc 添加故障
 */

public class AddTroubleActivity extends BaseClientActivity {

    private static final int CLIENT_ADD_TROUBLE = 2;
    // 设备信息 RequestCode
    private static final int REQUEST_FAULTDEVICEINFO = 100;
    private static final int RESULT_DATACODE = 200;


    // 故障信息 RequestCode
    private static final int REQUEST_FAULTDESINFO = 1000;
    private static final int RESULT_FAULTDESCODE = 2000;

    // 设备库 RequestCode
    private static final int REQUEST_EQUIPMENT = 3000;

    //故障设备名称
    @BindView(R.id.tv_faultDeviceName)
    TextView tvFaultDeviceName;
    @BindView(R.id.ll_faultDeviceName)
    LinearLayout llFaultDeviceName;
    // 故障设备编号
    @BindView(R.id.tv_deviceNumHint)
    TextView tvDeviceNameHint;
    @BindView(R.id.tv_deviceNum)
    TextView tvDeviceNum;
    @BindView(R.id.ll_deviceNum)
    LinearLayout llDeviceNum;
    // 故障设备编号
    @BindView(R.id.tv_deviceLocationHint)
    TextView tvDeviceLocationHint;
    @BindView(R.id.tv_deviceLocation)
    TextView tvDeviceLocation;
    @BindView(R.id.ll_deviceLocaltion)
    LinearLayout llDeviceLocaltion;
    // 故障设备品牌
    @BindView(R.id.tv_deviceBrand)
    TextView tvDeviceBrand;
    @BindView(R.id.ll_deviceBrand)
    LinearLayout llDeviceBrand;
    // 故障设备型号
    @BindView(R.id.tv_deviceModelHint)
    TextView tvDeviceModelHint;
    @BindView(R.id.tv_deviceModel)
    TextView tvDeviceModel;
    @BindView(R.id.ll_devicesModel)
    LinearLayout llDevicesModel;
    // 故障简述
    @BindView(R.id.tv_faultDescripte)
    TextView tvFaultDescripte;
    // 故障编号
    @BindView(R.id.tv_faultNum)
    TextView tvFaultNum;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    // 故障详细描述
    @BindView(R.id.ev_faultDescripte)
    EditText evFaultDescripte;
    // 确定
    @BindView(R.id.rl_confirmDevice)
    RelativeLayout rlConfirmDevice;
    // 设备库
    @BindView(R.id.ll_deviceHouse)
    LinearLayout llDeviceHouse;
    // 设备编号
    @BindView(R.id.et_deviceNum)
    EditText etDeviceNum;
    // 设备位置
    @BindView(R.id.et_deviceLocation)
    EditText etDeviceLocation;
    @BindView(R.id.et_faultNum)
    EditText etFaultNum;
    // 故障信息
    @BindView(R.id.ll_faultInfo)
    LinearLayout llFaultInfo;
    // 位置编号
    @BindView(R.id.et_deviceLocationNum)
    EditText etDeviceLocationNum;
    private Map<String, String> uploadMap = new HashMap<>();

    // 设备code 设备id
    private String dataCode = "";
    private Long dataId;
    //   系统类别
    private String businessOneCode = "";

    // 添加记录
    List<CooperationEntity> cooperationEntities = new ArrayList<>();
    private List<RepairBugEntity> beanList = new ArrayList<>();

    private RepairBugEntity repairBugEntity = new RepairBugEntity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trouble);
        ButterKnife.bind(this);
        initView();
        setListener();
    }

    private void setListener() {
        rlConfirmDevice.setOnClickListener(new MultiClickListener(AddTroubleActivity.this, this::checkInfo, this::onSubmitClient));
    }

    private void initView() {
        setTitle("新增故障");
        setLeftBack();
        beanList = (List<RepairBugEntity>) getIntent().getSerializableExtra("beanList");
        //个人客户 不显示设备库选择
        if (EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyId() == null) {
            llDeviceHouse.setVisibility(View.GONE);
        }
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));
    }

    /**
     * 提交方法
     */
    private void onSubmitClient() {
        if (!ConnectivityChangeReceiver.isNetConnected(getApplicationContext())) {
            showToast("网络异常，请检查网络");
            return;
        }

        repairBugEntity.setBusinessThreeCode(dataCode);
        repairBugEntity.setModelCode(Config.get().getBaseCodeByName(tvDeviceBrand.getText().toString().trim(), 2, Constant.MODEL).get(0));// 设备品牌
        repairBugEntity.setBugPosition(etDeviceLocation.getText().toString().trim());// 故障位置
        repairBugEntity.setDeviceNo(etDeviceNum.getText().toString().trim());// 故障编号
        repairBugEntity.setBugDescription(evFaultDescripte.getText().toString().trim());// 故障详细描述
        repairBugEntity.setDeviceName(Config.get().getBusinessNameByCode(repairBugEntity.getBusinessThreeCode(), 3));// 设备名称
        repairBugEntity.setSketch(tvFaultDescripte.getText().toString().trim());// 故障简述
        repairBugEntity.setHeadDeviceFailureId(dataId);// 故障id
        repairBugEntity.setLocationNumber(etDeviceLocationNum.getText().toString().trim());//位置编号
        String ursStr = PhotoUtils.getPhotoUrl(snplMomentAddPhotos, uploadMap, true);
        repairBugEntity.setPictures(ursStr);

        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        doVerify();
                    });
                }
            });
        } else {
            doVerify();
        }
    }

    public boolean checkInfo() {

        if (TextUtils.isEmpty(tvFaultDeviceName.getText().toString().trim())) {
            showToast("请选择故障设备名称");
            return false;
        }
//        if (TextUtils.isEmpty(etDeviceNum.getText().toString().trim())) {
//            showToast("请填写设备编号");
//            return false;
//        }
        if (TextUtils.isEmpty(etDeviceLocation.getText().toString().trim())) {
            showToast("请填写故障设备位置");
            return false;
        }

        if (TextUtils.isEmpty(tvDeviceBrand.getText().toString().trim())) {
            showToast("请选择品牌型号");
            return false;
        }


        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == RESULT_OK && requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            snplMomentAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            snplMomentAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        } else if (requestCode == REQUEST_FAULTDEVICEINFO && resultCode == RESULT_DATACODE) {
            dataCode = data.getStringExtra("dataCode");
            businessOneCode = data.getStringExtra("businessOneCode");
            tvFaultDeviceName.setText(Config.get().getBusinessNameByCode(dataCode, 3));
        } else if (requestCode == REQUEST_FAULTDESINFO && resultCode == RESULT_FAULTDESCODE) {
            tvFaultDescripte.setText(data.getStringExtra("faultDes"));
//            String mGetImgs = data.getStringExtra("faultImgs");
            dataId = Long.valueOf(data.getStringExtra("datasId"));
//            String[] imgs = mGetImgs.split(",");
//            ArrayList<String> arrayImgList = new ArrayList<String>();
//            arrayImgList.addAll(Stream.of(Arrays.asList(imgs)).map(url -> (BuildConfig.OSS_SERVER + "failure/" + url).toString()).toList());
//            snplMomentAddPhotos.setData(arrayImgList);
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_EQUIPMENT) {
            CustDeviceEntity custDeviceEntity = (CustDeviceEntity) data.getSerializableExtra("bean");

            etDeviceNum.setFocusable(false);
            etDeviceLocationNum.setFocusable(false);
            etDeviceLocation.setFocusable(false);
            llDeviceBrand.setClickable(false);

            etDeviceNum.setText(custDeviceEntity.getDeviceNo());
            etDeviceLocationNum.setText(custDeviceEntity.getLocationNumber());
            etDeviceLocation.setText(custDeviceEntity.getLocation());
            tvDeviceBrand.setText(Config.get().getModelNameByCode(custDeviceEntity.getModelCode(), 2));

            repairBugEntity.setMaintenanceStatus(custDeviceEntity.getWarrantyStatus());
            repairBugEntity.setRepairCount(custDeviceEntity.getDeviceVersion());
        }
    }


    @OnClick({R.id.ll_deviceHouse, R.id.ll_faultDeviceName, R.id.ll_deviceNum, R.id.ll_deviceLocaltion, R.id.ll_deviceBrand, R.id.ll_devicesModel, R.id.ll_faultInfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //故障设备名称
            case R.id.ll_faultDeviceName:
                JumpItent.jump(AddTroubleActivity.this, SelectDeviceTypeActivity.class, REQUEST_FAULTDEVICEINFO);
                break;
            //前往设备库
            case R.id.ll_deviceHouse:

                if (TextUtils.isEmpty(tvFaultDeviceName.getText().toString().trim())) {
                    showToast("请选择故障设备名称");
                    return;
                }

                Bundle b = new Bundle();
                b.putString("businessOneCode", dataCode);

                JumpItent.jump(AddTroubleActivity.this, EquipmentAddActivity.class, b, REQUEST_EQUIPMENT);
                break;
            // 故障设备编号
            case R.id.ll_deviceNum:
                break;
            // 故障设备编号
            case R.id.ll_deviceLocaltion:
                break;
            // 故障设备品牌
            case R.id.ll_deviceBrand:
                String busOneCode = Config.get().getBaseCodeByName(Config.get().getBusinessNameByCode(dataCode, 1), 1, Constant.MODEL).get(0);
                if (StringUtils.isEmpty(busOneCode)) {
                    showToast("请先选择故障设备");
                    return;
                }
                PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getModelList(2)).filter(bus -> bus.getDataCode().startsWith(busOneCode)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
                    tvDeviceBrand.setText(item);
                }));
                break;
            // 故障设备型号
            case R.id.ll_devicesModel:
                break;
            // 故障简述
            case R.id.ll_faultInfo:
                if (!StringUtils.isEmpty(dataCode)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("businessOneCode", businessOneCode);
                    JumpItent.jump(AddTroubleActivity.this, FaultLibraryActivity.class, bundle, REQUEST_FAULTDESINFO);
                } else {
                    showToast("请选择故障设备");
                }
                break;
        }
    }

    public void doVerify() {
        cooperationEntities.clear();
        if (beanList != null && beanList.size() > 0) {
            for (int i = 0; i < beanList.size(); i++) {
                CooperationEntity cooperationEntity = new CooperationEntity();
                cooperationEntity.setAssigneeOrgId(EanfangApplication.getApplication().getCompanyId());
                cooperationEntity.setBusType(0);
                cooperationEntity.setBusinessOneCode(Config.get().getBaseCodeByLevel(beanList.get(i).getBusinessThreeCode(), 1));
                cooperationEntities.add(cooperationEntity);
            }
        }
        CooperationEntity cooperationEntity_now = new CooperationEntity();
        cooperationEntity_now.setAssigneeOrgId(EanfangApplication.getApplication().getCompanyId());
        cooperationEntity_now.setBusType(0);
        cooperationEntity_now.setBusinessOneCode(Config.get().getBaseCodeByLevel(repairBugEntity.getBusinessThreeCode(), 1));
        cooperationEntities.add(cooperationEntity_now);

        EanfangHttp.post(RepairApi.GET_REAPIR_DO_VERIRFY)
                .upJson(JSON.toJSONString(cooperationEntities))
                .execute(new EanfangCallback<CooperationEntity>(this, true, CooperationEntity.class, (bean) -> {
                    Intent intent = new Intent();
                    if (bean != null) {
                        intent.putExtra("mOwnerOrgId", bean.getOwnerOrgId());
                    }
                    intent.putExtra("bean", repairBugEntity);
                    setResult(CLIENT_ADD_TROUBLE, intent);
                    finish();
                }));
    }
}
