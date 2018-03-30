package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.RepairFailureEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  15:10
 * @email houzhongzhou@yeah.net
 * @desc 添加故障
 */

public class AddTroubleActivity extends BaseWorkerActivity {
    private static final Integer WORKER_ADD_TROUBLE = 10003;
    @BindView(R.id.tv_deviceName)
    TextView tvDeviceName;
    @BindView(R.id.ll_deviceName)
    LinearLayout llDeviceName;
    @BindView(R.id.tv_System_category)
    TextView tvSystemCategory;
    @BindView(R.id.ll_System_category)
    LinearLayout llSystemCategory;
    @BindView(R.id.tv_Equipment_category)
    TextView tvEquipmentCategory;
    @BindView(R.id.ll_Equipment_category)
    LinearLayout llEquipmentCategory;
    @BindView(R.id.tv_equipment_name)
    TextView tvEquipmentName;
    @BindView(R.id.ll_equipment_name)
    LinearLayout llEquipmentName;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.ll_model)
    LinearLayout llModel;
    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.ll_deviceFailure)
    LinearLayout llDeviceFailure;
    private Map<String, String> uploadMap = new HashMap<>();
    private Long orderId;

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
        setRightTitle("提交");
        setTitle("新增故障");
        setLeftBack();

        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));

        //个人客户 不显示设备库选择
        if (EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyId() == null) {
            llDeviceName.setVisibility(View.GONE);
        }
    }


    private void setListener() {
        //设备库
        llDeviceName.setOnClickListener((v) -> {

        });
        //系统类别
        llSystemCategory.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getBusinessList(1)).map(bus -> bus.getDataName()).toList(), (index, item) -> {
                tvSystemCategory.setText(item);
                tvEquipmentCategory.setText("");
                tvEquipmentName.setText("");
                tvModel.setText("");
            });
        });

        //故障设备类别
        llEquipmentCategory.setOnClickListener((v) -> {
            String busOneCode = Config.get().getBusinessCodeByName(tvSystemCategory.getText().toString().trim(), 1);
            if (StringUtils.isEmpty(busOneCode)) {
                showToast("请先选择系统类别");
                return;
            }
            PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getBusinessList(2)).filter(bus -> bus.getDataCode().startsWith(busOneCode)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
                tvEquipmentCategory.setText(item);
                tvEquipmentName.setText("");
                tvModel.setText("");
            }));
        });
        //故障设备名称
        llEquipmentName.setOnClickListener((v) -> {
            String busTwoCode = Config.get().getBusinessCodeByName(tvEquipmentCategory.getText().toString().trim(), 2);
            if (StringUtils.isEmpty(busTwoCode)) {
                showToast("请先选择设备类别");
                return;
            }
            PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getBusinessList(3)).filter(bus -> bus.getDataCode().startsWith(busTwoCode)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
                tvEquipmentName.setText(item);
                tvModel.setText("");
            }));
        });
        //品牌型号
        llModel.setOnClickListener((v) -> {
            String busOneCode = Config.get().getBaseCodeByName(tvSystemCategory.getText().toString().trim(), 1, Constant.MODEL).get(0);
            if (StringUtils.isEmpty(busOneCode)) {
                showToast("请先选择系统类别");
                return;
            }
            PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getModelList(2)).filter(bus -> bus.getDataCode().startsWith(busOneCode)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
                tvModel.setText(item);
            }));

        });
        setRightTitleOnClickListener(new MultiClickListener(this, this::checkInfo, this::onSubmitWorker));

    }

    private void onSubmitWorker() {
        RepairFailureEntity bean = new RepairFailureEntity();

        bean.setBusinessThreeCode(Config.get().getBusinessCodeByName(tvEquipmentName.getText().toString().trim(), 3));
        bean.setModelCode(Config.get().getBaseCodeByName(tvModel.getText().toString().trim(), 2, Constant.MODEL).get(0));
        bean.setBugPosition(etLocation.getText().toString().trim());
        bean.setDeviceNo(etCode.getText().toString().trim());
        bean.setBugDescription(etDesc.getText().toString().trim());
        bean.setDeviceName(Config.get().getBusinessNameByCode(bean.getBusinessThreeCode(), 3));
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
        }
    }

    // 检查表单是否填写完毕
    public boolean checkInfo() {
        if (TextUtils.isEmpty(tvSystemCategory.getText().toString().trim())) {
            showToast("请选择系统类别");
            return false;
        }

        if (TextUtils.isEmpty(tvEquipmentCategory.getText().toString().trim())) {
            showToast("请选择故障设备类别");
            return false;
        }

        if (TextUtils.isEmpty(tvEquipmentName.getText().toString().trim())) {
            showToast("请选择故障设备名称");
            return false;
        }

        if (TextUtils.isEmpty(tvModel.getText().toString().trim())) {
            showToast("请选择品牌型号");
            return false;
        }

        if (TextUtils.isEmpty(etLocation.getText().toString().trim())) {
            showToast("请填写故障设备位置");
            return false;
        }

        if (TextUtils.isEmpty(etCode.getText().toString().trim())) {
            showToast("请填写设备编号");
            return false;
        }
        if (TextUtils.isEmpty(etDesc.getText().toString().trim())) {
            showToast("请填写故障现象");
            return false;
        }
        return true;
    }

}
