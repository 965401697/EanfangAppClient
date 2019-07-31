package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.picture.PictureRecycleView;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.contentsafe.ContentDefaultAuditing;
import com.eanfang.util.contentsafe.ContentSecurityAuditUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.eanfang.biz.model.entity.AskQuestionsEntity;
import com.eanfang.biz.model.entity.CustDeviceEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.repair.DeviceBrandActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.FaultLibraryActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.SelectDeviceTypeActivity;
import net.eanfang.worker.ui.base.BaseWorkeActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.util.StrUtil;

/**
 * 免费提问
 */
public class FreeAskActivity extends BaseWorkeActivity {

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
    @BindView(R.id.recycleview)
    PictureRecycleView recycleview;

    // 设备code 设备id
    private String dataCode = "";
    private Long headDeviceId;
    //   系统类别
    private String businessOneCode = "";

    private Map<String, String> uploadMap = new HashMap<>();
    private String mSketch;
    private String mModelCodeT;
    private String deviceFailureId;
    private String failureTypeId;
    private List<LocalMedia> selectList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_free_ask);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("免费提问");
        initViews();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    private void initViews() {
        recycleview.addImagev(listener);
    }
    PictureRecycleView.ImageListener listener = list -> selectList = list;
    @OnClick({R.id.ll_faultDeviceName, R.id.ll_deviceBrand, R.id.ll_faultInfo, R.id.tv_ask})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_faultDeviceName:
                JumpItent.jump(FreeAskActivity.this, SelectDeviceTypeActivity.class, REQUEST_FAULTDEVICEINFO);
                break;
            case R.id.ll_deviceBrand:
                String busOneCode = Config.get().getBaseCodeByName(Config.get().getBusinessNameByCode(dataCode, 1), 1, Constant.MODEL).get(0);
                if (StrUtil.isEmpty(busOneCode)) {
                    showToast("请先选择故障设备");
                    return;
                }
                Bundle bundle_device = new Bundle();
                bundle_device.putString("busOneCode", busOneCode);
                JumpItent.jump(FreeAskActivity.this, DeviceBrandActivity.class, bundle_device, REQUEST_DEVICE_BRAND_CODE);
                break;
            case R.id.ll_faultInfo:
                if (!StrUtil.isEmpty(dataCode)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("businessOneCode", businessOneCode);
                    JumpItent.jump(FreeAskActivity.this, FaultLibraryActivity.class, bundle, REQUEST_FAULTDESINFO);
                } else {
                    showToast("请选择故障设备");
                }
                break;
            case R.id.tv_ask:
                if (TextUtils.isEmpty(tvFaultDeviceName.getText().toString().trim())) {
                    ToastUtil.get().showToast(this, "请选择设备名称");
                    break;
                }
                if (TextUtils.isEmpty(tvDeviceBrand.getText().toString().trim())) {
                    ToastUtil.get().showToast(this, "请选择品牌型号");
                    break;
                }
                if (TextUtils.isEmpty(tvFaultInfo.getText().toString().trim())) {
                    ToastUtil.get().showToast(this, "请选择故障简述");
                    break;
                }
                String input = etInputInfo.getText().toString().trim();
                if (TextUtils.isEmpty(input)) {
                    ToastUtil.get().showToast(this, "请填写故障简述");
                    break;
                }
                ContentSecurityAuditUtil.getInstance().toAuditing(input,
                        new ContentDefaultAuditing(this) {
                            @Override
                            public void auditingSuccess() {
                                fillData();
                            }
                        });
                break;
            default:
                break;
        }
    }


    private boolean fillData() {
        String accidentPic = PhotoUtils.getPhotoUrl("online/", selectList, uploadMap, false);
        // detailsBean.setPictures(accidentPic);
//        if (uploadMap.size() <= 0) {
//            ToastUtil.get().showToast(this, "添加现场照片可能会优先得到回答哦");
//            return false;
//        }

        if (uploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(uploadMap, (isSuccess) -> {
             /*   runOnUiThread(() -> {
                    Intent intent = new Intent();
                    intent.putExtra("resultTwo", accidentPic);
                    setResult(101, intent);
                    finish();
                });*/
            });
        } else {
           /* Intent intent = new Intent();
            intent.putExtra("resultTwo", accidentPic);
            setResult(101, intent);
            finish();*/
        }

//        if (StrUtil.isEmpty(accidentPic)) {
//            showToast("请添加现场照片");
//            return false;
//        }
        AskQuestionsEntity askQuestionsEntity = new AskQuestionsEntity();
        askQuestionsEntity.setQuestionUserId(WorkerApplication.get().getUserId());
        if (WorkerApplication.get().getCompanyId() != 0) {
            askQuestionsEntity.setQuestionCompanyId(WorkerApplication.get().getCompanyId());
            askQuestionsEntity.setQuestionTopCompanyId(WorkerApplication.get().getTopCompanyId());
        }

        askQuestionsEntity.setQuestionCreateDate(new Date());
        askQuestionsEntity.setDataCode(dataCode);
        askQuestionsEntity.setBusinessOneCode(businessOneCode);
        askQuestionsEntity.setModelCode(mModelCodeT);
        askQuestionsEntity.setDeviceFailureId(Long.valueOf(deviceFailureId));
        askQuestionsEntity.setQuestionSketch(mSketch);
        askQuestionsEntity.setFailureTypeId(failureTypeId);
        askQuestionsEntity.setQuestionContent(etInputInfo.getText().toString().trim());
        askQuestionsEntity.setQuestionPics(accidentPic);
        subData(askQuestionsEntity);

        return true;
    }

    private void subData(AskQuestionsEntity askQuestionsEntity) {

        EanfangHttp.post(UserApi.EXPERT_ASK_QUESTION)
                .upJson(JSON.toJSONString(askQuestionsEntity))
                //JSONObject
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class) {

                    @Override
                    public void onSuccess(JSONObject bean) {
                        Toast.makeText(FreeAskActivity.this, "提问成功", Toast.LENGTH_SHORT).show();
                        int questionId = (int) bean.get("questionId");
                        Intent intent = new Intent(FreeAskActivity.this, FaultExplainActivity.class);
                        intent.putExtra("QuestionIdZ", questionId);
                       startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == REQUEST_FAULTDEVICEINFO && resultCode == RESULT_DATACODE) {//设备名称
            dataCode = data.getStringExtra("dataCode");
            businessOneCode = data.getStringExtra("businessOneCode");
            tvFaultDeviceName.setText(Config.get().getBusinessNameByCode(dataCode, 1) + " - " + Config.get().getBusinessNameByCode(dataCode, 3));
        } else if (requestCode == REQUEST_FAULTDESINFO && resultCode == RESULT_FAULTDESCODE) {//故障简述
            mSketch = data.getStringExtra("sketch");
            tvFaultInfo.setText(data.getStringExtra("sketch"));
            deviceFailureId = data.getStringExtra("deviceFailureId");
            failureTypeId = data.getStringExtra("failureTypeId");
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_EQUIPMENT) {
            CustDeviceEntity custDeviceEntity = (CustDeviceEntity) data.getSerializableExtra("bean");
            tvDeviceBrand.setText(Config.get().getModelNameByCode(custDeviceEntity.getModelCode(), 2));
        } else if (resultCode == RESULT_DEVICE_BRAND_CODE && requestCode == REQUEST_DEVICE_BRAND_CODE) {// 设备品牌
            //品牌型号ModelCode值-----bug 必须选择两次，否则值为空
            if (Config.get().getBaseCodeByName(tvDeviceBrand.getText().toString().trim(), 2, Constant.MODEL).get(0) == "") {
                mModelCodeT = "5.1.18";
            } else {
                mModelCodeT = Config.get().getBaseCodeByName(tvDeviceBrand.getText().toString().trim(), 2, Constant.MODEL).get(0);
            }

            Log.i("mModelCodeT", mModelCodeT + "");
            tvDeviceBrand.setText(data.getStringExtra("deviceBrandName"));
        }
    }
}
