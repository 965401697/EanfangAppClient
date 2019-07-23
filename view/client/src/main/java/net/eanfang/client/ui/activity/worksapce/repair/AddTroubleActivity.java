package net.eanfang.client.ui.activity.worksapce.repair;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.V;
import com.eanfang.base.kit.picture.picture.PictureRecycleView;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.config.EanfangConst;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.takevideo.TakeVdideoMode;
import com.eanfang.takevideo.TakeVideoActivity;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.ConnectivityChangeUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.CooperationEntity;
import com.yaf.base.entity.CustDeviceEntity;
import com.yaf.base.entity.RepairBugEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.worksapce.equipment.EquipmentListActivity;
import net.eanfang.client.ui.activity.worksapce.scancode.ScanCodeActivity;
import net.eanfang.client.ui.base.BaseClienActivity;
import net.eanfang.client.ui.widget.RepairSelectDevicesDialog;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/4/17
 * @description 报修改版
 */


public class AddTroubleActivity extends BaseClienActivity {

    private static final int CLIENT_ADD_TROUBLE = 2;
    /**
     * 设备信息 RequestCode
     */
    private static final int REQUEST_FAULTDEVICEINFO = 100;
    private static final int RESULT_DATACODE = 200;

    /**
     * 故障信息 RequestCode
     */
    private static final int REQUEST_FAULTDESINFO = 1000;
    private static final int RESULT_FAULTDESCODE = 2000;

    /**
     * 设备库 RequestCode
     */
    private static final int REQUEST_EQUIPMENT = 3000;
    /**
     * 设备品牌回调 code
     */
    private final int REQUEST_DEVICE_BRAND_CODE = 1001;
    private final int RESULT_DEVICE_BRAND_CODE = 1002;

    /**
     * 扫码报修
     */
    private final int SCAN_REAPIR_ADDTROUBLE = 20190116;
    /**
     * 故障设备名称
     */
    @BindView(R.id.tv_faultDeviceName)
    TextView tvFaultDeviceName;
    /**
     * 故障设备品牌
     */
    @BindView(R.id.tv_deviceBrand)
    TextView tvDeviceBrand;
    @BindView(R.id.ll_deviceBrand)
    LinearLayout llDeviceBrand;
    /**
     * 故障设备型号
     */
    @BindView(R.id.tv_deviceModelHint)
    TextView tvDeviceModelHint;
    @BindView(R.id.tv_deviceModel)
    TextView tvDeviceModel;
    @BindView(R.id.ll_devicesModel)
    LinearLayout llDevicesModel;
    /**
     * 故障简述
     */
    @BindView(R.id.tv_faultDescripte)
    TextView tvFaultDescripte;
    /**
     * 故障编号
     */
    @BindView(R.id.tv_faultNum)
    TextView tvFaultNum;
    /**
     * 故障详细描述
     */
    @BindView(R.id.ev_faultDescripte)
    EditText evFaultDescripte;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.picture_recycler)
    PictureRecycleView pictureRecycler;
    @BindView(R.id.video_recycle)
    PictureRecycleView videoRecycle;
    /**
     * 设备编号
     */
    private String mDeviceNum = "";
    /**
     * 设备位置
     */
    @BindView(R.id.et_deviceLocation)
    EditText etDeviceLocation;
    @BindView(R.id.et_faultNum)
    EditText etFaultNum;
    /**
     * 故障信息
     */
    @BindView(R.id.ll_faultInfo)
    LinearLayout llFaultInfo;
    /**
     * 位置编号
     */
    @BindView(R.id.et_deviceLocationNum)
    EditText etDeviceLocationNum;
    @BindView(R.id.iv_right)
    ImageView ivRight;
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

    /**
     * 设备code 设备id
     */
    private String dataCode = "";
    private Long dataId = null;
    /**
     * 系统类别
     */
    private String businessOneCode = "";

    /**
     * 添加记录
     */
    List<CooperationEntity> cooperationEntities = new ArrayList<>();
    private List<RepairBugEntity> beanList = new ArrayList<>();
    /**
     * 从设备列表页面进行添加
     */
    private boolean fromTroubleList = false;

    private RepairBugEntity repairBugEntity;

    /**
     * 扫码查看设备 报修
     */
    private CustDeviceEntity mDeviceBean;
    private String isScan = "";

    /**
     * 视频上传key
     */
    private String mUploadKey = "";
    /**
     * 视频路径
     */
    private String mVieoPath = "";

    /**
     * 选择故障设备
     */
    private RepairSelectDevicesDialog repairSelectDevicesDialog;
    /**
     * 是否再来一条
     */
    private boolean isAgainAdd = false;
    /**
     * 设备库选择设备
     */
    private boolean isFromDevicesHouse = false;
    private Long mOwnerOrgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_trouble);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();
        setTitle("新增故障");
        fromTroubleList = getIntent().getBooleanExtra("fromTroubleList", false);
        if (fromTroubleList) {
            beanList = (List<RepairBugEntity>) getIntent().getSerializableExtra("beanList");
        }
        //个人客户 不显示设备库选择
        if (ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyId() == null) {
        }
        initRecycle();
        initRecycleVideo();
        initData();
        setListener();
    }

    private List<LocalMedia> selectList = new ArrayList<>();
    private List<LocalMedia> videoList = new ArrayList<>();

    private void initRecycle() {
        pictureRecycler.addImage(listener);
    }

    PictureRecycleView.ImageListener listener = list -> selectList = list;
    PictureRecycleView.ImageListener videoListener = list -> videoList = list;


    private void initRecycleVideo() {
        videoRecycle.addVideo(videoListener);
    }

    /**
     * 扫码获取数据
     */
    private void initData() {
        if (mDeviceBean != null) {
            //故障设备名称a
            tvFaultDeviceName.setText(mDeviceBean.getDeviceName());
            dataCode = V.v(() -> mDeviceBean.getBusinessThreeCode());
            etDeviceLocationNum.setFocusable(false);
            etDeviceLocation.setFocusable(false);
            llDeviceBrand.setClickable(false);
            // 设备编号
            mDeviceNum = V.v(() -> mDeviceBean.getDeviceNo());
            //位置编号
            etDeviceLocationNum.setText(V.v(() -> mDeviceBean.getLocationNumber()));
            // 故障位置
            etDeviceLocation.setText(V.v(() -> mDeviceBean.getLocation()));
            // 设备品牌
            tvDeviceBrand.setText(Config.get().getModelNameByCode(V.v(() -> mDeviceBean.getModelCode()), 2));
        }
    }

    private void setListener() {
        // 保存
        tvSave.setOnClickListener(new MultiClickListener(AddTroubleActivity.this, this::isSave, this::onSubmitClient));
        // 再来一条
        tvAdd.setOnClickListener(new MultiClickListener(AddTroubleActivity.this, this::isAgainAdd, this::onSubmitClient));

        setLeftBack((v) -> {
            if (beanList != null && beanList.size() > 0) {
                doTranValue();
            } else {
                giveUp();
            }
        });
    }

    /**
     * 检查页面输入值
     */
    public boolean checkInfo() {
        if (TextUtils.isEmpty(tvFaultDeviceName.getText().toString().trim())) {
            showToast("请选择故障设备名称");
            return false;
        }

        if (llInputDevice.getVisibility() == View.VISIBLE && TextUtils.isEmpty(etDevice.getText().toString().trim())) {
            showToast("请输入故障设备名称");
            return false;
        }
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

    /**
     * 提交方法
     */
    private void onSubmitClient() {
        if (!ConnectivityChangeUtil.isNetConnected(getApplicationContext())) {
            showToast("网络异常，请检查网络");
            return;
        }
        if (!isFromDevicesHouse) {
            repairBugEntity = new RepairBugEntity();
        }
        repairBugEntity.setBusinessThreeCode(dataCode);
        // 设备品牌
        repairBugEntity.setModelCode(Config.get().getBaseCodeByName(tvDeviceBrand.getText().toString().trim(), 2, Constant.MODEL).get(0));
        // 故障位置
        repairBugEntity.setBugPosition(etDeviceLocation.getText().toString().trim());
        // 故障编号
        repairBugEntity.setDeviceNo(mDeviceNum);
        // 故障详细描述
        repairBugEntity.setBugDescription(evFaultDescripte.getText().toString().trim());
        // 设备名称
        repairBugEntity.setDeviceName(Config.get().getBusinessNameByCode(repairBugEntity.getBusinessThreeCode(), 3));
        // 故障简述
        repairBugEntity.setSketch(tvFaultDescripte.getText().toString().trim());
        // 故障id
        repairBugEntity.setHeadDeviceFailureId(dataId);
        //位置编号
        repairBugEntity.setLocationNumber(etDeviceLocationNum.getText().toString().trim());
        repairBugEntity.setMp4_path(mUploadKey);
        String ursStr = PhotoUtils.getPhotoUrl("biz/repair/", selectList, uploadMap, true);
        repairBugEntity.setPictures(ursStr);
        if (llInputDevice.getVisibility() == View.VISIBLE) {
            repairBugEntity.setBusinessThreeName(etDevice.getText().toString().trim());
        }
        if (llInputBrand.getVisibility() == View.VISIBLE) {
            repairBugEntity.setModelName(etBrand.getText().toString().trim());
        }
        if (uploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(uploadMap, (isSuccess) -> {
                runOnUiThread(() -> {
                    doVerify();
                });
            });
        } else {
            doVerify();
        }
    }


    /**
     * 校验查询技师
     */
    public void doVerify() {
        cooperationEntities.clear();
        if (beanList != null && beanList.size() > 0) {
            for (int i = 0; i < beanList.size(); i++) {
                CooperationEntity cooperationEntity = new CooperationEntity();
                cooperationEntity.setAssigneeOrgId(ClientApplication.get().getCompanyId());
                cooperationEntity.setBusType(0);
                cooperationEntity.setBusinessOneCode(Config.get().getBaseCodeByLevel(beanList.get(i).getBusinessThreeCode(), 1));
                cooperationEntities.add(cooperationEntity);
            }
        }
        CooperationEntity cooperationEntity_now = new CooperationEntity();
        cooperationEntity_now.setAssigneeOrgId(ClientApplication.get().getCompanyId());
        cooperationEntity_now.setBusType(0);
        cooperationEntity_now.setBusinessOneCode(Config.get().getBaseCodeByLevel(repairBugEntity.getBusinessThreeCode(), 1));
        cooperationEntities.add(cooperationEntity_now);
        beanList.add(repairBugEntity);

        EanfangHttp.post(RepairApi.GET_REAPIR_DO_VERIRFY)
                .upJson(JSON.toJSONString(cooperationEntities))
                .execute(new EanfangCallback<CooperationEntity>(this, true, CooperationEntity.class, (bean) -> {
                    if (isAgainAdd) {
                        doCleanAllValue();
                    } else {
                        if (bean != null) {
                            mOwnerOrgId = bean.getOwnerOrgId();
                        }
                        doTranValue();
                    }
                }));
    }

    public void doTranValue() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if (mOwnerOrgId != null) {
            bundle.putLong("mOwnerOrgId", mOwnerOrgId);
        }
        bundle.putSerializable("beanList", (Serializable) beanList);
        intent.putExtras(bundle);
        bundle.putString("qrcode", getIntent().getStringExtra("qrcode"));
        bundle.putSerializable("repairbean", getIntent().getSerializableExtra("repairbean"));
        bundle.putString("headUrl", getIntent().getStringExtra("headUrl"));
        bundle.putString("workName", getIntent().getStringExtra("workName"));
        bundle.putString("companyName", getIntent().getStringExtra("companyName"));
        /**
         * 故障列表页面添加
         * */
        if (fromTroubleList) {
            setResult(CLIENT_ADD_TROUBLE, intent);
        } else {
            JumpItent.jump(AddTroubleActivity.this, TroubleListActivity.class, bundle);
        }
        finish();
    }

    /**
     * 是否再来一条
     */
    private boolean isAgainAdd() {
        /**
         *  从故障列表添加
         * */
        isAgainAdd = true;
        return checkInfo();

    }

    /**
     * 保存
     */
    private boolean isSave() {
        isAgainAdd = false;
        return checkInfo();

    }

    /**
     * 清楚页面的值
     */
    private void doCleanAllValue() {
        mDeviceNum = "";
        uploadMap.clear();
        dataCode = "";
        dataId = null;
        businessOneCode = "";
        mUploadKey = "";
        mVieoPath = "";
        tvFaultDeviceName.setText("");
        tvDeviceBrand.setText("");
        etDeviceLocation.setText("");
        etDeviceLocationNum.setText("");
        tvFaultDescripte.setText("");
        evFaultDescripte.setText("");
        selectList.clear();
        pictureRecycler.setList(selectList);
        videoList.clear();
        videoRecycle.setList(videoList);
        etDeviceLocationNum.setFocusable(true);
        etDeviceLocation.setFocusable(true);
        llDeviceBrand.setClickable(true);

    }

    @OnClick({R.id.ll_faultDeviceName, R.id.ll_deviceBrand, R.id.ll_devicesModel, R.id.ll_faultInfo, R.id.ll_scan, R.id.iv_input_voice,
            R.id.iv_thumbnail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //故障设备
            case R.id.ll_faultDeviceName:
                repairSelectDevicesDialog = new RepairSelectDevicesDialog(this, new RepairSelectDevicesDialog.OnSelectListener() {
                    @Override
                    public void onDeviceType() {
                        //设备类别
                        etDeviceLocationNum.setFocusable(true);
                        etDeviceLocation.setFocusable(true);
                        llDeviceBrand.setClickable(true);
                        etDeviceLocation.setText("");
                        etDeviceLocationNum.setText("");
                        JumpItent.jump(AddTroubleActivity.this, SelectDeviceTypeActivity.class, REQUEST_FAULTDEVICEINFO);
                        repairSelectDevicesDialog.dismiss();
                    }

                    @Override
                    public void onDeviceWareHouse() {
                        // 设备库精选
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("repair", true);
                        JumpItent.jump(AddTroubleActivity.this, EquipmentListActivity.class, bundle, REQUEST_EQUIPMENT);
                        repairSelectDevicesDialog.dismiss();
                    }
                });
                repairSelectDevicesDialog.show();
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
            //扫一扫设备
            case R.id.ll_scan:
                startActivity(new Intent(AddTroubleActivity.this, ScanCodeActivity.class).putExtra("from", EanfangConst.QR_CLIENT));
                break;
            case R.id.iv_input_voice:
                RxPerm.get(this).voicePerm((isSuccess) -> {
                    RecognitionManager.getSingleton().startRecognitionWithDialog(AddTroubleActivity.this, evFaultDescripte);
                });
                break;
            //视频展示
            case R.id.iv_thumbnail:
                Bundle bundle_thumbnail = new Bundle();
                bundle_thumbnail.putString("videoPath", mVieoPath);
                JumpItent.jump(AddTroubleActivity.this, PlayVideoActivity.class, bundle_thumbnail);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == REQUEST_FAULTDEVICEINFO && resultCode == RESULT_DATACODE) {
            // 选择故障设备
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
            }

        } else if (requestCode == REQUEST_FAULTDESINFO && resultCode == RESULT_FAULTDESCODE) {
            // 故障简述
            tvFaultDescripte.setText(data.getStringExtra("sketch"));
            evFaultDescripte.setText(data.getStringExtra("faultDes"));
            dataId = Long.valueOf(data.getStringExtra("datasId"));
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_EQUIPMENT) {
            /**
             *设备库
             * */
            CustDeviceEntity custDeviceEntity = (CustDeviceEntity) data.getSerializableExtra("custDeviceEntity");
            dataCode = custDeviceEntity.getBusinessThreeCode();
            etDeviceLocationNum.setFocusable(false);
            etDeviceLocation.setFocusable(false);
            llDeviceBrand.setClickable(false);

            mDeviceNum = custDeviceEntity.getDeviceNo();
            tvFaultDeviceName.setText(custDeviceEntity.getDeviceName());
            etDeviceLocationNum.setText(custDeviceEntity.getLocationNumber());
            etDeviceLocation.setText(custDeviceEntity.getLocation());
            tvDeviceBrand.setText(Config.get().getModelNameByCode(custDeviceEntity.getModelCode(), 2));
            isFromDevicesHouse = true;
            repairBugEntity = new RepairBugEntity();
            repairBugEntity.setMaintenanceStatus(custDeviceEntity.getWarrantyStatus());
            repairBugEntity.setRepairCount(custDeviceEntity.getDeviceVersion());
        } else if (resultCode == RESULT_DEVICE_BRAND_CODE && requestCode == REQUEST_DEVICE_BRAND_CODE) {
            // 设备品牌
            tvDeviceBrand.setText(data.getStringExtra("deviceBrandName"));
            etDeviceLocationNum.setFocusable(true);
            etDeviceLocationNum.setFocusableInTouchMode(true);
            etDeviceLocationNum.requestFocus();
            etDeviceLocationNum.findFocus();
            //将光标定位
            etDeviceLocation.setFocusable(true);
            etDeviceLocation.setFocusableInTouchMode(true);
            etDeviceLocation.requestFocus();
            etDeviceLocation.findFocus();
            StringUtils.showKeyboard(AddTroubleActivity.this, etDeviceLocation);
        }
    }

    @Subscribe()//MAIN代表主线程
    public void receivePath(TakeVdideoMode takeVdideoMode) {
        if (takeVdideoMode != null) {
            rlThumbnail.setVisibility(View.VISIBLE);
            mVieoPath = takeVdideoMode.getMImagePath();
            mUploadKey = takeVdideoMode.getMKey();
            if (!StringUtils.isEmpty(mVieoPath)) {
                ivThumbnail.setImageBitmap(PhotoUtils.getVideoBitmap(mVieoPath));
            }
        }
    }

    /**
     * 扫码报修
     */
    @Subscribe
    public void onEventBScanReapirAddtrouble(Bundle bundle_repair) {
        mDeviceBean = (CustDeviceEntity) bundle_repair.getSerializable("scan_repair");
//        isScanRepair = bundle_repair.getBoolean("isScanRepair", false);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (repairSelectDevicesDialog != null) {
            repairSelectDevicesDialog.dismiss();
        }
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (beanList != null && beanList.size() > 0) {
                doTranValue();
            } else {
                giveUp();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 放弃报修
     */
    private void giveUp() {
        new TrueFalseDialog(this, "系统提示", "是否放弃报修？", () -> {
            finish();
        }).showDialog();
    }
}
