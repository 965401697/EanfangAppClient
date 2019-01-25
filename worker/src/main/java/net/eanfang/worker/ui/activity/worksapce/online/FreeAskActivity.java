package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.CustDeviceEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.repair.DeviceBrandActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.FaultLibraryActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.SelectDeviceTypeActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 免费提问
 */
public class FreeAskActivity extends BaseWorkerActivity {

    private static final Integer WORKER_ADD_TROUBLE = 10003;
    // 设备信息 RequestCode
    private static final int REQUEST_FAULTDEVICEINFO = 100;
    private static final int RESULT_DATACODE = 200;

    // 故障信息 RequestCode
    private static final int REQUEST_FAULTDESINFO = 1000;
    private static final int RESULT_FAULTDESCODE = 2000;
    // 设备库 RequestCode
    private static final int REQUEST_EQUIPMENT = 3000;
    //设备品牌回调 code
    private final int REQUEST_DEVICE_BRAND_CODE = 1001;
    private final int RESULT_DEVICE_BRAND_CODE = 1002;

    @BindView(R.id.tv_faultDeviceName)
    TextView tvFaultDeviceName;
    @BindView(R.id.tv_deviceBrand)
    TextView tvDeviceBrand;
    @BindView(R.id.tv_faultInfo)
    TextView tvFaultInfo;
    @BindView(R.id.et_input_info)
    EditText etInputInfo;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;

    // 设备code 设备id
    private String dataCode = "";
    private Long dataId;
    //   系统类别
    private String businessOneCode = "";

    private Map<String, String> uploadMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_ask);
        ButterKnife.bind(this);
        setTitle("免费提问");
        setLeftBack();
        initViews();
    }

    private void initViews() {
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));
    }

    @OnClick({R.id.ll_faultDeviceName, R.id.ll_deviceBrand, R.id.ll_faultInfo, R.id.tv_ask})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_faultDeviceName:
                JumpItent.jump(FreeAskActivity.this, SelectDeviceTypeActivity.class, REQUEST_FAULTDEVICEINFO);
                break;
            case R.id.ll_deviceBrand:
                String busOneCode = Config.get().getBaseCodeByName(Config.get().getBusinessNameByCode(dataCode, 1), 1, Constant.MODEL).get(0);
                if (StringUtils.isEmpty(busOneCode)) {
                    showToast("请先选择故障设备");
                    return;
                }
                Bundle bundle_device = new Bundle();
                bundle_device.putString("busOneCode", busOneCode);
                JumpItent.jump(FreeAskActivity.this, DeviceBrandActivity.class, bundle_device, REQUEST_DEVICE_BRAND_CODE);
                break;
            case R.id.ll_faultInfo:
                if (!StringUtils.isEmpty(dataCode)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("businessOneCode", businessOneCode);
                    JumpItent.jump(FreeAskActivity.this, FaultLibraryActivity.class, bundle, REQUEST_FAULTDESINFO);
                } else {
                    showToast("请选择故障设备");
                }
                break;
            case R.id.tv_ask:

                Intent intent = new Intent(FreeAskActivity.this, CommonFaultListActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void sub() {
        String ursStr = PhotoUtils.getPhotoUrl("biz/repair/", snplMomentAddPhotos, uploadMap, true);
    }

    private void subData(List<String> lists) {

        EanfangHttp.post(UserApi.GET_ACC_INFO)
                .upJson(JSON.toJSONString(lists))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class) {

                    @Override
                    public void onSuccess(JSONObject bean) {
                        super.onSuccess(bean);
                    }
                });
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

            tvFaultDeviceName.setText(Config.get().getBusinessNameByCode(dataCode, 1) + " - " + Config.get().getBusinessNameByCode(dataCode, 3));
        } else if (requestCode == REQUEST_FAULTDESINFO && resultCode == RESULT_FAULTDESCODE) {
            tvFaultInfo.setText(data.getStringExtra("sketch"));
            dataId = Long.valueOf(data.getStringExtra("datasId"));
            ArrayList<String> arrayImgList = new ArrayList<String>();
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_EQUIPMENT) {
            CustDeviceEntity custDeviceEntity = (CustDeviceEntity) data.getSerializableExtra("bean");

//            etDeviceNum.setFocusable(false);
//            etDeviceLocationNum.setFocusable(false);
//            etLocation.setFocusable(false);
//            llDeviceBrand.setClickable(false);
//
//            etDeviceNum.setText(custDeviceEntity.getDeviceNo());
//            etDeviceLocationNum.setText(custDeviceEntity.getLocationNumber());
//            etLocation.setText(custDeviceEntity.getLocation());
            tvDeviceBrand.setText(Config.get().getModelNameByCode(custDeviceEntity.getModelCode(), 2));

//            bean.setMaintenanceStatus(custDeviceEntity.getWarrantyStatus());
//            bean.setRepairCount(custDeviceEntity.getDeviceVersion());
        } else if (resultCode == RESULT_DEVICE_BRAND_CODE && requestCode == REQUEST_DEVICE_BRAND_CODE) {// 设备品牌
            tvDeviceBrand.setText(data.getStringExtra("deviceBrandName"));
        }
    }
}
