package net.eanfang.worker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.picture.PictureRecycleView;
import com.eanfang.biz.model.bean.MaintenanceBean;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.NumberUtil;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.luck.picture.lib.entity.LocalMedia;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;

/**
 * 添加维修保养详情页面
 * Created by wen on 2017/4/4.
 */

public class AddMaintenanceDetailActivity extends BaseWorkerActivity {


    @BindView(R.id.picture_recycler)
    PictureRecycleView pictureRecycler;
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

    private RelativeLayout rl_device_type, rl_business_type;

    private TextView tv_commit;
    private Map<String, String> uploadMap = new HashMap<>();
    private List<LocalMedia> selectList = new ArrayList<>();
    PictureRecycleView.ImageListener listener = list -> selectList = list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_maintenance_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        setTitle("添加明细");
        setLeftBack();
        registerListener();

    }

    private void initView() {
        tv_business_type = findViewById(R.id.tv_business_type);
        rl_business_type = findViewById(R.id.rl_business_type);
        tv_device_type = findViewById(R.id.tv_device_type);
        rl_device_type = findViewById(R.id.rl_device_type);
        tv_device_name = findViewById(R.id.tv_device_name);
        rl_device_name = findViewById(R.id.rl_device_name);
        tv_brand_model = findViewById(R.id.tv_brand_model);
        rl_brand_model = findViewById(R.id.rl_brand_model);
        et_amount = findViewById(R.id.et_amount);
        et_price = findViewById(R.id.et_price);
        tv_main_leave = findViewById(R.id.tv_main_leave);
        rl_main_leave = findViewById(R.id.rl_main_leave);
        tv_main_result = findViewById(R.id.tv_main_result);
        rl_main_result = findViewById(R.id.rl_main_result);
        et_question = findViewById(R.id.et_question);
        et_maintenance_measures = findViewById(R.id.et_maintenance_measures);
        et_reason_analysis = findViewById(R.id.et_reason_analysis);
        tv_commit = findViewById(R.id.tv_commit);
        pictureRecycler.addImagev(listener);
    }

    private void registerListener() {
        rl_business_type.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getBusinessList(1)).map(bus -> bus.getDataName()).toList(), (index, item) -> {
                tv_business_type.setText(item);
                tv_device_type.setText("");
                tv_device_name.setText("");
                tv_brand_model.setText("");
            });
        });
        rl_device_type.setOnClickListener((v) -> {
            String busOneCode = Config.get().getBusinessCodeByName(tv_business_type.getText().toString().trim(), 1);
            if (StrUtil.isEmpty(busOneCode)) {
                showToast("请先选择业务类别");
                return;
            }
            PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getBusinessList(2)).filter(bus -> bus.getDataCode().startsWith(busOneCode)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
                tv_device_type.setText(item);
                tv_device_name.setText("");
                tv_brand_model.setText("");
            }));
        });
        rl_device_name.setOnClickListener((v) -> {
            String busTwoCode = Config.get().getBusinessCodeByName(tv_device_type.getText().toString().trim(), 2);
            if (StrUtil.isEmpty(busTwoCode)) {
                showToast("请先选择设备类别");
                return;
            }
            PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getBusinessList(3)).filter(bus -> bus.getDataCode().startsWith(busTwoCode)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
                tv_device_name.setText(item);
                tv_brand_model.setText("");
            }));
        });

        rl_brand_model.setOnClickListener((v) -> {
            String busOneCode = Config.get().getBaseCodeByName(tv_business_type.getText().toString().trim(), 1, Constant.MODEL).get(0);
            if (StrUtil.isEmpty(busOneCode)) {
                showToast("请先选择业务类别");
                return;
            }
            PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getModelList(2)).filter(bus -> bus.getDataCode().startsWith(busOneCode)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
                tv_brand_model.setText(item);
            }));
        });
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
        bean.setBusinessThreeCode(Config.get().getBusinessCodeByName(tv_device_name.getText().toString().trim(), 3));
        bean.setBusinessFourCode(Config.get().getBaseCodeByName(tv_brand_model.getText().toString().trim(), 2, Constant.MODEL).get(0));

        bean.setQuestion(et_question.getText().toString().trim());
        String urls = PhotoUtils.getPhotoUrl("biz/maintain/", selectList, uploadMap, true);
        bean.setPictures(urls);

        if (selectList.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(uploadMap, (isSuccess) -> {
                runOnUiThread(() -> {
                    Intent intent = new Intent();
                    intent.putExtra("result", bean);
                    setResult(10002, intent);
                    finish();
                });
            });
        } else {
            Intent intent = new Intent();
            intent.putExtra("result", bean);
            setResult(10002, intent);
            finish();
        }
    }

    private boolean checkInfo() {
        if (TextUtils.isEmpty(et_amount.getText().toString().trim()) && TextUtils.isDigitsOnly(et_amount.getText().toString().trim())) {
            showToast("数量不能为空且必须为数字");
            return false;
        }
        if (TextUtils.isEmpty(et_price.getText().toString().trim())) {
            showToast("安装位置不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tv_main_leave.getText().toString().trim())) {
            showToast("维保标准不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tv_main_result.getText().toString().trim())) {
            showToast("检查结果不能为空");
            return false;
        }
        if (TextUtils.isEmpty(et_question.getText().toString().trim())) {
            showToast("发现问题不能为空");
            return false;
        }
        if (TextUtils.isEmpty(et_reason_analysis.getText().toString().trim())) {
            showToast("原因分析不能为空");
            return false;
        }
        if (TextUtils.isEmpty(et_maintenance_measures.getText().toString().trim())) {
            showToast("维护措施不能为空");
            return false;
        }
        return true;
    }

    @Override
    protected void onNavigationOnClicked() {
        finishSelf();
    }
}
