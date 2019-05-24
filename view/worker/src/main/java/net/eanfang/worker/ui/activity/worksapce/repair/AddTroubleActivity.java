package net.eanfang.worker.ui.activity.worksapce.repair;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.takevideo.TakeVdideoMode;
import com.eanfang.takevideo.TakeVideoActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.CustDeviceEntity;
import com.yaf.base.entity.RepairFailureEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.equipment.EquipmentAddActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;

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
    // 设备库 RequestCode
    private static final int REQUEST_EQUIPMENT = 3000;

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
    // 拍摄视频
    @BindView(R.id.tv_addViedeo)
    TextView tvAddViedeo;
    @BindView(R.id.iv_takevideo)
    ImageView ivTakevideo;
    @BindView(R.id.rl_thumbnail)
    RelativeLayout rlThumbnail;

    private Map<String, String> uploadMap = new HashMap<>();
    private Long orderId;

    // 设备code 设备id
    private String dataCode = "";
    private Long dataId;
    //   系统类别
    private String businessOneCode = "";
    private long clientCompanyUid;

    /**
     * 视频上传key
     */
    private String mUploadKey = "";
    /**
     * 视频路径
     */
    private String mVieoPath = "";
    //设备品牌回调 code
    private final int REQUEST_DEVICE_BRAND_CODE = 1001;
    private final int RESULT_DEVICE_BRAND_CODE = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trouble);
        ButterKnife.bind(this);
        initView();
//        initData();
        clientCompanyUid = getIntent().getLongExtra("clientCompanyUid", 0);
        setListener();
    }

    private void initView() {
        orderId = getIntent().getLongExtra("repaid", 0);
        setTitle("新增故障");
        setLeftBack();

        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));

        //个人客户 不显示设备库选择
        if (WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyId() == null) {
//            llDeviceName.setVisibility(View.GONE);
        }
    }


    private void setListener() {
        rlConfirmDevice.setOnClickListener(new MultiClickListener(AddTroubleActivity.this, this::checkInfo, this::onSubmitWorker));
        // 拍摄视频
        tvAddViedeo.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            JumpItent.jump(AddTroubleActivity.this, TakeVideoActivity.class, bundle);
        });
        ivTakevideo.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", mVieoPath);
            JumpItent.jump(AddTroubleActivity.this, PlayVideoActivity.class, bundle);
        });
    }

    private void onSubmitWorker() {
        RepairFailureEntity bean = new RepairFailureEntity();

        bean.setBusinessThreeCode(dataCode);
        bean.setModelCode(Config.get().getBaseCodeByName(tvDeviceBrand.getText().toString().trim(), 2, Constant.MODEL).get(0));// 设备品牌
        bean.setBugPosition(etLocation.getText().toString().trim());
        // 故障位置
        bean.setDeviceNo(etDeviceNum.getText().toString().trim());// 故障编号
        bean.setBugDescription(evFaultDescripte.getText().toString().trim());// 故障详细描述
        bean.setSketch(tvFaultDescripte.getText().toString().trim());// 故障简述
        bean.setLocationNumber(etDeviceLocationNum.getText().toString().trim());//位置编号
        bean.setDeviceName(Config.get().getBusinessNameByCode(bean.getBusinessThreeCode(), 3));// 设备名称
        bean.setMp4_path(mUploadKey);
        String ursStr = PhotoUtils.getPhotoUrl("biz/repair/", snplMomentAddPhotos, uploadMap, true);
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
            dataId = Long.valueOf(data.getStringExtra("deviceFailureId"));
//            String[] imgs = mGetImgs.split(",");
            ArrayList<String> arrayImgList = new ArrayList<String>();
//            arrayImgList.addAll(Stream.of(Arrays.asList(imgs)).map(url -> (BuildConfig.OSS_SERVER + "failure/" + url).toString()).toList());
//            snplMomentAddPhotos.setData(arrayImgList);
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_EQUIPMENT) {
            CustDeviceEntity custDeviceEntity = (CustDeviceEntity) data.getSerializableExtra("bean");

            etDeviceNum.setFocusable(false);
            etDeviceLocationNum.setFocusable(false);
            etLocation.setFocusable(false);
            llDeviceBrand.setClickable(false);

            etDeviceNum.setText(custDeviceEntity.getDeviceNo());
            etDeviceLocationNum.setText(custDeviceEntity.getLocationNumber());
            etLocation.setText(custDeviceEntity.getLocation());
            tvDeviceBrand.setText(Config.get().getModelNameByCode(custDeviceEntity.getModelCode(), 2));

//            bean.setMaintenanceStatus(custDeviceEntity.getWarrantyStatus());
//            bean.setRepairCount(custDeviceEntity.getDeviceVersion());
        }else if (resultCode == RESULT_DEVICE_BRAND_CODE && requestCode == REQUEST_DEVICE_BRAND_CODE) {// 设备品牌
            tvDeviceBrand.setText(data.getStringExtra("deviceBrandName"));
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

//        if (TextUtils.isEmpty(etDeviceNum.getText().toString().trim())) {
//            showToast("请填写设备编号");
//            return false;
//        }
        if (TextUtils.isEmpty(evFaultDescripte.getText().toString().trim())) {
            showToast("请填写故障详细描述");
            return false;
        }
        return true;
    }

    @OnClick({R.id.ll_faultDeviceName, R.id.ll_deviceHouse, R.id.ll_deviceNum, R.id.ll_deviceLocaltion, R.id.ll_deviceBrand, R.id.ll_devicesModel, R.id.rl_confirmDevice, R.id.ll_faultInfo})
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
                b.putString("clientCompanyUid", String.valueOf(clientCompanyUid));
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
                Bundle bundle_device = new Bundle();
                bundle_device.putString("busOneCode", busOneCode);
                JumpItent.jump(AddTroubleActivity.this, DeviceBrandActivity.class, bundle_device, REQUEST_DEVICE_BRAND_CODE);
//                PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getModelList(2)).filter(bus -> bus.getDataCode().startsWith(busOneCode)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
//                    tvDeviceBrand.setText(item);
//                }));
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
            default:
                break;
        }
    }

    @Subscribe()//MAIN代表主线程
    public void receivePath(TakeVdideoMode takeVdideoMode) {
        if (takeVdideoMode != null) {
            rlThumbnail.setVisibility(View.VISIBLE);
            mVieoPath = takeVdideoMode.getMImagePath();
            mUploadKey = takeVdideoMode.getMKey();
            if (!StringUtils.isEmpty(mVieoPath)) {
                ivTakevideo.setImageBitmap(PhotoUtils.getVideoThumbnail(mVieoPath, 100, 100, MINI_KIND));
            }
            tvAddViedeo.setText("重新拍摄");
        }
    }
}
