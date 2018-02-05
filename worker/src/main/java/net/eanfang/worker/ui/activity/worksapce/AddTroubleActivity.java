package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
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
    private Long id;

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
        id = getIntent().getLongExtra("repaid", 0);
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
            PickerSelectUtil.singleTextPicker(this, "", tvSystemCategory, Stream.of(Config.get().getBusinessList(1)).map(bus -> bus.getDataName()).toList());
        });

        //故障设备类别
        llEquipmentCategory.setOnClickListener((v) -> {

        });
        //故障设备名称
        llEquipmentName.setOnClickListener((v) -> {

        });
        //品牌型号
        llEquipmentName.setOnClickListener((v) -> {

        });
        setRightTitleOnClickListener(v -> onSubmitWorker());

    }

    private void onSubmitWorker() {
        RepairFailureEntity bean = new RepairFailureEntity();

        bean.setBusinessThreeCode(Config.get().getBusinessCodeByName(tvSystemCategory.getText().toString().trim(), 3));
        bean.setBugPosition(etLocation.getText().toString().trim());
        bean.setDeviceNo(etCode.getText().toString().trim());
        bean.setBugDescription(etDesc.getText().toString().trim());
        bean.setBusRepairOrderId(id);
        String ursStr = PhotoUtils.getPhotoUrl(snplMomentAddPhotos, uploadMap, true);
        bean.setPictures(ursStr);


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
        if (resultCode == RESULT_OK && requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            snplMomentAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            snplMomentAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }


}
