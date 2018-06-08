package net.eanfang.worker.ui.activity.worksapce.repair;

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
import com.eanfang.BuildConfig;
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
import com.eanfang.util.JumpItent;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.RepairFailureEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

public class AddTroubleActivity extends BaseWorkerActivity {
    private static final Integer WORKER_ADD_TROUBLE = 10003;

    // 设备信息 RequestCode
    private static final int REQUEST_FAULTDEVICEINFO = 100;
    private static final int RESULT_DATACODE = 200;

    // 故障信息 RequestCode
    private static final int REQUEST_FAULTDESINFO = 1000;
    private static final int RESULT_FAULTDESCODE = 2000;

    // 品牌型号
    @BindView(R.id.tv_deviceBrand)
    TextView tvDeviceBrand;
    @BindView(R.id.ll_deviceBrand)
    LinearLayout llDeviceBrand;
    // 设备位置
    @BindView(R.id.et_deviceLocation)
    EditText etLocation;
    // 设备编号（设备库待添加）
    @BindView(R.id.et_deviceNum)
    EditText etDeviceNum;
    // 位置编号
    @BindView(R.id.et_deviceLocationNum)
    EditText etDeviceLocationNum;
    // 故障简述
    @BindView(R.id.tv_faultDescripte)
    TextView tvFaultDescripte;
    // 故障信息
    @BindView(R.id.ll_faultInfo)
    LinearLayout llFaultInfo;
    //故障设备名称
    @BindView(R.id.tv_faultDeviceName)
    TextView tvFaultDeviceName;
    @BindView(R.id.ll_faultDeviceName)
    LinearLayout llFaultDeviceName;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    // 故障详细描述
    @BindView(R.id.ev_faultDescripte)
    EditText evFaultDescripte;
    // 确定
    @BindView(R.id.rl_confirmDevice)
    RelativeLayout rlConfirmDevice;
    private Map<String, String> uploadMap = new HashMap<>();
    private Long orderId;

    // 设备code 设备id
    private String dataCode = "";
    private Long dataId;
    //   系统类别
    private String businessOneCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trouble);
        ButterKnife.bind(this);
        initView();
//        initData();
        setListener();
    }

    private void initView() {
        orderId = getIntent().getLongExtra("repaid", 0);
        setTitle("新增故障");
        setLeftBack();

        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));

        //个人客户 不显示设备库选择
        if (EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyId() == null) {
//            llDeviceName.setVisibility(View.GONE);
        }
    }


    private void setListener() {
        rlConfirmDevice.setOnClickListener(new MultiClickListener(AddTroubleActivity.this, this::checkInfo, this::onSubmitWorker));
    }

    private void onSubmitWorker() {
        RepairFailureEntity bean = new RepairFailureEntity();

        bean.setBusinessThreeCode(dataCode);
        bean.setModelCode(Config.get().getBaseCodeByName(tvDeviceBrand.getText().toString().trim(), 2, Constant.MODEL).get(0));// 设备品牌
        bean.setBugPosition(etLocation.getText().toString().trim());
        ;// 故障位置
        bean.setDeviceNo(etDeviceNum.getText().toString().trim());// 故障编号
        bean.setBugDescription(evFaultDescripte.getText().toString().trim());// 故障详细描述
        bean.setSketch(tvFaultDescripte.getText().toString().trim());// 故障简述
        bean.setLocationNumber(etDeviceLocationNum.getText().toString().trim());//位置编号
        bean.setDeviceName(Config.get().getBusinessNameByCode(bean.getBusinessThreeCode(), 3));// 设备名称
        String ursStr = PhotoUtils.getPhotoUrl(snplMomentAddPhotos, uploadMap, true);
        bean.setPictures(ursStr);
        bean.setBusRepairOrderId(orderId);

        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    commit(JSON.toJSONString(bean));
                }
            });
        } else {
            commit(JSON.toJSONString(bean));
        }

    }

    private void commit(String jsonString) {
        EanfangHttp.post(RepairApi.POST_FAILURE_CREATE)
                .upJson(jsonString)
                .execute(new EanfangCallback<RepairFailureEntity>(this, true, RepairFailureEntity.class, (bean) -> {
                    Intent intent = new Intent();
                    intent.putExtra("bean", bean);
                    setResult(WORKER_ADD_TROUBLE, intent);
                    finish();
                }));
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
            ArrayList<String> arrayImgList = new ArrayList<String>();
//            arrayImgList.addAll(Stream.of(Arrays.asList(imgs)).map(url -> (BuildConfig.OSS_SERVER + "failure/" + url).toString()).toList());
//            snplMomentAddPhotos.setData(arrayImgList);
        }
    }

    // 检查表单是否填写完毕
    public boolean checkInfo() {

        if (TextUtils.isEmpty(tvDeviceBrand.getText().toString().trim())) {
            showToast("请选择品牌型号");
            return false;
        }

        if (TextUtils.isEmpty(etLocation.getText().toString().trim())) {
            showToast("请填写故障设备位置");
            return false;
        }

        if (TextUtils.isEmpty(etDeviceNum.getText().toString().trim())) {
            showToast("请填写设备编号");
            return false;
        }
        if (TextUtils.isEmpty(evFaultDescripte.getText().toString().trim())) {
            showToast("请填写故障现象");
            return false;
        }
        return true;
    }

    @OnClick({R.id.ll_faultDeviceName, R.id.ll_deviceNum, R.id.ll_deviceLocaltion, R.id.ll_deviceBrand, R.id.ll_devicesModel, R.id.rl_confirmDevice, R.id.ll_faultInfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //故障设备名称
            case R.id.ll_faultDeviceName:
                JumpItent.jump(AddTroubleActivity.this, SelectDeviceTypeActivity.class, REQUEST_FAULTDEVICEINFO);
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


}