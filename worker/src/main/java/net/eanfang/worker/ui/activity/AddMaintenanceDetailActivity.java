package net.eanfang.worker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.config.Config;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.model.MaintenanceBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.NumberUtil;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加维修保养详情页面
 * Created by wen on 2017/4/4.
 */

public class AddMaintenanceDetailActivity extends BaseWorkerActivity {


    private TextView tv_business_type;
    private MaintenanceBean.MaintainDetailsBean bean;
    private TextView tv_device_type;
    private TextView tv_device_name;
    private RelativeLayout rl_device_name;
    private TextView tv_brand_model;
    private RelativeLayout rl_brand_model;
    private EditText et_amount;
    private EditText et_price;
    private TextView tv_main_leave;
    private RelativeLayout rl_main_leave;
    private TextView tv_main_result;
    private RelativeLayout rl_main_result;
    private EditText et_question;
    private EditText et_maintenance_measures;
    private EditText et_reason_analysis;
    private BGASortableNinePhotoLayout mPhotosSnpl;
    private RelativeLayout rl_device_type, rl_business_type;

    private TextView tv_commit;
    private Map<String, String> uploadMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_detail);

        initView();
        supprotToolbar();
        initData();
        registerListener();

    }

    private void initView() {
        tv_business_type = (TextView) findViewById(R.id.tv_business_type);
        rl_business_type = (RelativeLayout) findViewById(R.id.rl_business_type);
        tv_device_type = (TextView) findViewById(R.id.tv_device_type);
        rl_device_type = (RelativeLayout) findViewById(R.id.rl_device_type);
        tv_device_name = (TextView) findViewById(R.id.tv_device_name);
        rl_device_name = (RelativeLayout) findViewById(R.id.rl_device_name);
        tv_brand_model = (TextView) findViewById(R.id.tv_brand_model);
        rl_brand_model = (RelativeLayout) findViewById(R.id.rl_brand_model);
        et_amount = (EditText) findViewById(R.id.et_amount);
        et_price = (EditText) findViewById(R.id.et_price);
        tv_main_leave = (TextView) findViewById(R.id.tv_main_leave);
        rl_main_leave = (RelativeLayout) findViewById(R.id.rl_main_leave);
        tv_main_result = (TextView) findViewById(R.id.tv_main_result);
        rl_main_result = (RelativeLayout) findViewById(R.id.rl_main_result);
        et_question = (EditText) findViewById(R.id.et_question);
        et_maintenance_measures = (EditText) findViewById(R.id.et_maintenance_measures);
        et_reason_analysis = (EditText) findViewById(R.id.et_reason_analysis);
        mPhotosSnpl = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_monitor_add_photos);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
    }

    private void initData() {
        setTitle("添加明细");
        mPhotosSnpl.setDelegate(new BGASortableDelegate(this));
    }


    private void registerListener() {
        rl_business_type.setOnClickListener(v -> PickerSelectUtil.singleTextPicker(this, "", tv_business_type, Stream.of(Config.get().getBusinessList(1)).map(bus -> bus.getDataName()).toList()))
        ;

//        rl_device_type.setOnClickListener(v -> showBusinessType());
//        rl_device_name.setOnClickListener(v -> {
//            if (StringUtils.isEmpty(tv_device_type.getText().toString().trim())) {
//                showToast("请先选择设备类型");
//            } else {
//                showBusiness2Type();
//            }
//        });
//        rl_brand_model.setOnClickListener(v -> {
//            if (StringUtils.isEmpty(tv_device_type.getText().toString().trim())) {
//                showToast("请先选择设备类型");
//                return;
//            }
//            if (StringUtils.isEmpty(tv_device_name.getText().toString().trim())) {
//                showToast("请先选择设备名称");
//            } else {
//                showBusiness3Type();
//            }
//        });
        rl_main_leave.setOnClickListener(v -> PickerSelectUtil.singleTextPicker(this, "", tv_main_leave, GetConstDataUtils.getMaintainLevelList()));

        rl_main_result.setOnClickListener(v -> PickerSelectUtil.singleTextPicker(this, "", tv_main_result, GetConstDataUtils.getCheckResultList()));
        tv_commit.setOnClickListener(new MultiClickListener(this, this::checkInfo, this::submit));
    }


    private void submit() {
        bean = new MaintenanceBean.MaintainDetailsBean();
        bean.setCount(NumberUtil.parseInt(et_amount.getText().toString().trim(), 0));
        bean.setInstallPosition(et_price.getText().toString().trim());
        bean.setMaintainLevel(GetConstDataUtils.getMaintainLevelList().indexOf(tv_main_leave.getText().toString().trim()));
        bean.setCheckResult(GetConstDataUtils.getCheckResultList().indexOf(tv_main_result.getText().toString().trim()));
        bean.setQuestion(et_question.getText().toString().trim());
        bean.setSolution(et_maintenance_measures.getText().toString().trim());
        bean.setCause(et_reason_analysis.getText().toString().trim());
        bean.setBusinessFourCode(Config.get().getBusinessCodeByName(tv_business_type.getText().toString().trim(), 1));

        bean.setQuestion(et_question.getText().toString().trim());
        String urls = PhotoUtils.getPhotoUrl(mPhotosSnpl, uploadMap, true);
        bean.setPictures(urls);

        if (mPhotosSnpl.getData().size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        Intent intent = new Intent();
                        intent.putExtra("result", bean);
                        setResult(10002, intent);
                        finish();
                    });

                }
            });
        } else {
            Intent intent = new Intent();
            intent.putExtra("result", bean);
            setResult(10002, intent);
            finish();
        }
    }

    private boolean checkInfo() {
//        if (TextUtils.isEmpty(et_amount.getText().toString().trim()) && TextUtils.isDigitsOnly(et_amount.getText().toString().trim())) {
//            Toast.makeText(this, "数量不能为空且必须为数字", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (TextUtils.isEmpty(et_price.getText().toString().trim())) {
//            Toast.makeText(this, "安装位置不能为空", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (TextUtils.isEmpty(tv_main_leave.getText().toString().trim())) {
//            Toast.makeText(this, "维保标准不能为空", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (TextUtils.isEmpty(tv_main_result.getText().toString().trim())) {
//            Toast.makeText(this, "检查结果不能为空", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (TextUtils.isEmpty(et_question.getText().toString().trim())) {
//            Toast.makeText(this, "发现问题不能为空", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (TextUtils.isEmpty(et_reason_analysis.getText().toString().trim())) {
//            Toast.makeText(this, "原因分析不能为空", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (TextUtils.isEmpty(et_maintenance_measures.getText().toString().trim())) {
//            Toast.makeText(this, "维护措施不能为空", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == BGASortableDelegate.REQUEST_CODE_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }

    @Override
    protected void onNavigationOnClicked() {
        finishSelf();
    }
}
