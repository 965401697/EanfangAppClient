package net.eanfang.client.ui.activity.worksapce.repair;

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
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.config.EanfangConst;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.takevideo.TakeVdideoMode;
import com.eanfang.takevideo.TakeVideoActivity;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.ConnectivityChangeUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.CooperationEntity;
import com.yaf.base.entity.CustDeviceEntity;
import com.yaf.base.entity.RepairBugEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.equipment.EquipmentAddActivity;
import net.eanfang.client.ui.activity.worksapce.scancode.ScanCodeActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    //设备品牌回调 code
    private final int REQUEST_DEVICE_BRAND_CODE = 1001;
    private final int RESULT_DEVICE_BRAND_CODE = 1002;

    /**
     * 扫码报修
     */
    private final int SCAN_REAPIR_ADDTROUBLE = 20190116;
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
    @BindView(R.id.iv_input_voice)
    ImageView ivInputVoice;
    @BindView(R.id.ll_scan)
    LinearLayout ll_scan;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    // 拍摄视频
    @BindView(R.id.tv_addViedeo)
    TextView tvAddViedeo;
    @BindView(R.id.iv_thumbnail)
    ImageView ivThumbnail;
    @BindView(R.id.rl_thumbnail)
    RelativeLayout rlThumbnail;
    @BindView(R.id.et_device)
    EditText etDevice;
    @BindView(R.id.ll_input_device)
    LinearLayout llInputDevice;
    @BindView(R.id.et_brand)
    EditText etBrand;
    @BindView(R.id.ll_input_brand)
    LinearLayout llInputBrand;


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

    // 扫码查看设备 报修
    private CustDeviceEntity mDeviceBean;
    private boolean isScanRepair = false;

    /**
     * 视频上传key
     */
    private String mUploadKey = "";
    /**
     * 视频路径
     */
    private String mVieoPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trouble);
        ButterKnife.bind(this);
        initView();
        initData();
        setListener();
    }

    // 扫码获取数据
    private void initData() {
        if (mDeviceBean != null) {
            //故障设备名称
            tvFaultDeviceName.setText(mDeviceBean.getDeviceName());

            dataCode = V.v(() -> mDeviceBean.getBusinessThreeCode());
            etDeviceNum.setFocusable(false);
            etDeviceLocationNum.setFocusable(false);
            etDeviceLocation.setFocusable(false);
            llDeviceBrand.setClickable(false);
            // 设备编号
            etDeviceNum.setText(V.v(() -> mDeviceBean.getDeviceNo()));
            //位置编号
            etDeviceLocationNum.setText(V.v(() -> mDeviceBean.getLocationNumber()));
            // 故障位置
            etDeviceLocation.setText(V.v(() -> mDeviceBean.getLocation()));
            // 设备品牌
            tvDeviceBrand.setText(Config.get().getModelNameByCode(V.v(() -> mDeviceBean.getModelCode()), 2));
        }
    }

    private String message = "";

    private void setListener() {
        rlConfirmDevice.setOnClickListener(new MultiClickListener(AddTroubleActivity.this, this::checkInfo, this::onSubmitClient));
        ivInputVoice.setOnClickListener((v) -> {
            PermissionUtils.get(this).getVoicePermission(() -> {
                RecognitionManager.getSingleton().startRecognitionWithDialog(AddTroubleActivity.this, evFaultDescripte);
            });
        });
        // 扫一扫设备
        ll_scan.setOnClickListener((v) -> {
            startActivity(new Intent(AddTroubleActivity.this, ScanCodeActivity.class).putExtra("from", EanfangConst.QR_CLIENT));
        });
        // 拍摄视频
        tvAddViedeo.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            JumpItent.jump(AddTroubleActivity.this, TakeVideoActivity.class, bundle);
        });
        //视频展示
        ivThumbnail.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", mVieoPath);
            JumpItent.jump(AddTroubleActivity.this, PlayVideoActivity.class, bundle);
        });
    }

    private void initView() {
        setTitle("新增故障");
        setLeftBack();
//        ivRight.setImageResource(R.mipmap.ic_main_top_qrcode);
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
        if (!ConnectivityChangeUtil.isNetConnected(getApplicationContext())) {
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
        repairBugEntity.setMp4_path(mUploadKey);
        String ursStr = PhotoUtils.getPhotoUrl("biz/repair/", snplMomentAddPhotos, uploadMap, true);
        repairBugEntity.setPictures(ursStr);
        if (llInputDevice.getVisibility() == View.VISIBLE) {
            repairBugEntity.setBusinessThreeName(etDevice.getText().toString().trim());
        }
        if (llInputBrand.getVisibility() == View.VISIBLE) {
            repairBugEntity.setModelName(etBrand.getText().toString().trim());
        }
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

        if (llInputDevice.getVisibility() == View.VISIBLE && TextUtils.isEmpty(etDevice.getText().toString().trim())) {
            showToast("请输入故障设备名称");
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
        if (llInputBrand.getVisibility() == View.VISIBLE && TextUtils.isEmpty(etBrand.getText().toString().trim())) {
            showToast("请输入品牌型号");
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
        } else if (requestCode == REQUEST_FAULTDEVICEINFO && resultCode == RESULT_DATACODE) {// 选择故障设备
            dataCode = data.getStringExtra("dataCode");
            businessOneCode = data.getStringExtra("businessOneCode");

            String text = Config.get().getBusinessNameByCode(dataCode, 3);
            tvFaultDeviceName.setText(text);
            if (text.equals("其他")) {
                llInputDevice.setVisibility(View.VISIBLE);

                //将光标定位
                etDevice.requestFocus();
                etDevice.setFocusable(true);
                etDevice.setFocusableInTouchMode(true);
                StringUtils.showKeyboard(AddTroubleActivity.this, etDevice);

            } else {
                llInputDevice.setVisibility(View.GONE);

                //将光标定位
                etDeviceLocationNum.requestFocus();
                etDeviceLocationNum.setFocusable(true);
                etDeviceLocationNum.setFocusableInTouchMode(true);
                StringUtils.showKeyboard(AddTroubleActivity.this, etDeviceLocationNum);
            }

        } else if (requestCode == REQUEST_FAULTDESINFO && resultCode == RESULT_FAULTDESCODE) {// 故障简述
            tvFaultDescripte.setText(data.getStringExtra("sketch"));
            evFaultDescripte.setText(data.getStringExtra("faultDes"));
//            String mGetImgs = data.getStringExtra("faultImgs");
            dataId = Long.valueOf(data.getStringExtra("datasId"));
//            String[] imgs = mGetImgs.split(",");
//            ArrayList<String> arrayImgList = new ArrayList<String>();
//            arrayImgList.addAll(Stream.of(Arrays.asList(imgs)).map(url -> (BuildConfig.OSS_SERVER + "failure/" + url).toString()).toList());
//            snplMomentAddPhotos.setData(arrayImgList);
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_EQUIPMENT) {// 设备库
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
        } else if (resultCode == RESULT_DEVICE_BRAND_CODE && requestCode == REQUEST_DEVICE_BRAND_CODE) {// 设备品牌
            tvDeviceBrand.setText(data.getStringExtra("deviceBrandName"));
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
                Bundle bundle_device = new Bundle();
                bundle_device.putString("busOneCode", busOneCode);
                JumpItent.jump(AddTroubleActivity.this, DeviceBrandActivity.class, bundle_device, REQUEST_DEVICE_BRAND_CODE);
//                PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getModelList(2)).filter(bus -> bus.getDataCode().startsWith(busOneCode)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
//                    tvDeviceBrand.setText(item);
//                    if (item.equals("其他")) {
//                        llInputBrand.setVisibility(View.VISIBLE);
//
//                    } else {
//                        llInputBrand.setVisibility(View.GONE);
//                    }
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

                    if (isScanRepair) {// 扫码添加故障
                        Intent intent = new Intent();
                        if (bean != null) {
                            intent.putExtra("mOwnerOrgId", bean.getOwnerOrgId());
                        }
                        intent.putExtra("bean", repairBugEntity);
                        intent.putExtra("isScanRepair", isScanRepair);
                        setResult(CLIENT_ADD_TROUBLE, intent);
                        finishSelf();
                    } else {// 正常报修流程
                        Intent intent = new Intent();
                        if (bean != null) {
                            intent.putExtra("mOwnerOrgId", bean.getOwnerOrgId());
                        }
                        intent.putExtra("bean", repairBugEntity);
                        intent.putExtra("isScanRepair", isScanRepair);
                        setResult(CLIENT_ADD_TROUBLE, intent);
                        finish();
                    }

                }));
    }

    @Subscribe()//MAIN代表主线程
    public void receivePath(TakeVdideoMode takeVdideoMode) {
        if (takeVdideoMode != null) {
            rlThumbnail.setVisibility(View.VISIBLE);
            mVieoPath = takeVdideoMode.getMImagePath();
            mUploadKey = takeVdideoMode.getMKey();
            if (!StringUtils.isEmpty(mVieoPath)) {
                ivThumbnail.setImageBitmap(PhotoUtils.getVideoThumbnail(mVieoPath, 100, 100, MINI_KIND));
            }
            tvAddViedeo.setText("重新拍摄");
        }
    }

    /**
     * 扫码报修
     */
    @Subscribe
    public void onEventBScanReapirAddtrouble(Bundle bundle_repair) {
        mDeviceBean = (CustDeviceEntity) bundle_repair.getSerializable("scan_repair");
        isScanRepair = bundle_repair.getBoolean("isScanRepair", false);
        initData();
    }


}
