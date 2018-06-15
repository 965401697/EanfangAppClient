package net.eanfang.worker.ui.activity.worksapce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Optional;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.BughandleDetailEntity;
import com.yaf.base.entity.BughandleParamEntity;
import com.yaf.base.entity.BughandleUseDeviceEntity;
import com.yaf.base.entity.RepairFailureEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.repair.DeviceParameterActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:37
 * @email houzhongzhou@yeah.net
 * @desc 电话解决 完善故障处理
 */

public class PhoneSolveTroubleDetailActivity extends BaseWorkerActivity implements RadioGroup.OnCheckedChangeListener {

    public static final String TAG = PhoneSolveTroubleDetailActivity.class.getSimpleName();
    public static final int ADD_DEVICE_PARAM_REQUEST = 100;
    public static final int ADD_DEVICE_PARAM_RESULT = 1000;
    // 使用建议
    @BindView(R.id.et_trouble_useAdvace)
    EditText etTroubleUseAdvace;
    // 是否误报
    @BindView(R.id.rg_yes)
    RadioButton rgYes;
    @BindView(R.id.rg_no)
    RadioButton rgNo;
    @BindView(R.id.rg_misreport)
    RadioGroup rgMisreport;
    // 维修结果
    @BindView(R.id.tag_repair_result)
    TagFlowLayout tagRepairResult;
    private TagAdapter<String> mResultAdapter;
    // 修复方式
    @BindView(R.id.tag_repair_result_two)
    TagFlowLayout tagRepairResultTwo;
    private TagAdapter<String> mModeAdapter;
    // 设备编号
    @BindView(R.id.tv_device_no)
    TextView tvDeviceNo;
    // 设备位置
    @BindView(R.id.tv_device_location)
    TextView tvDeviceLocation;
    //故障描述
    @BindView(R.id.et_trouble_desc)
    EditText etTroubleDesc;
    //过程和方法
    @BindView(R.id.et_trouble_point)
    EditText etTroublePoint;
    //原因判断
    @BindView(R.id.et_trouble_reason)
    EditText etTroubleReason;
    // 处理措施
    @BindView(R.id.et_trouble_deal)
    EditText etTroubleDeal;
    // 确定
    @BindView(R.id.btn_add_trouble)
    Button btnAddTrouble;
    //设备名称
    @BindView(R.id.tv_trouble_title)
    TextView tvTroubleTitle;
    // 添加设备参数
    @BindView(R.id.rl_add_device_param)
    RelativeLayout rlAddDeviceParam;
    private BughandleDetailEntity bughandleDetailEntity;
    // 设备参数List
    private List<BughandleParamEntity> paramEntityList = new ArrayList<>();
    //2017年7月20日
    //维修结果
    private int position;
    //2017年7月24日
    private String companyName;
    private Long id;
    private Long confirmId;

    private RepairFailureEntity repairFailureEntity;

    // 维修结果一级
    private int mReapirOneStauts = 100;
    // 维修结果二级
    private int mReapirTwoStauts = 200;
    // 是否误报
    private String mIsRepairError = "否";

    // 维修结果
    List<String> mRepairResult = GetConstDataUtils.getBugDetailList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ps_trouble_detail);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }


    private void initView() {
        setTitle("故障明细");
        setLeftBack();
    }

    private void initData() {
        companyName = getIntent().getStringExtra("companyName");
        bughandleDetailEntity = (BughandleDetailEntity) getIntent().getSerializableExtra("bean");
        position = getIntent().getIntExtra("position", 0);
        id = getIntent().getLongExtra("id", 0);
        confirmId = getIntent().getLongExtra("confirmId", 0);
        lookFailureDetail();
    }

    private void initListener() {
        btnAddTrouble.setOnClickListener(v -> submit());
        rgMisreport.setOnCheckedChangeListener(this);
        rlAddDeviceParam.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("paramEntityList", (Serializable) paramEntityList);
            JumpItent.jump(this, DeviceParameterActivity.class, bundle, ADD_DEVICE_PARAM_REQUEST);
        });
    }

    private void lookFailureDetail() {

        EanfangHttp.get(RepairApi.GET_FAILURE_DETAIL)
                .params("id", id)
                .execute(new EanfangCallback<RepairFailureEntity>(this, true, RepairFailureEntity.class, (bean) -> {
                    repairFailureEntity = bean;
                    if (repairFailureEntity.getBughandleDetailEntityList() != null && repairFailureEntity.getBughandleDetailEntityList().size() > 0) {
                        //取最后一条
                        bughandleDetailEntity = repairFailureEntity.getBughandleDetailEntityList().get(repairFailureEntity.getBughandleDetailEntityList().size() - 1);
                    }
                    fillData();
                }));
    }

    public void fillData() {

        if (bughandleDetailEntity.getParamEntityList() == null) {
            List<BughandleParamEntity> list = new ArrayList<>();
            bughandleDetailEntity.setParamEntityList(list);
        }
        if (bughandleDetailEntity.getUseDeviceEntityList() == null) {
            List<BughandleUseDeviceEntity> list = new ArrayList<>();
            bughandleDetailEntity.setUseDeviceEntityList(list);
        }

        if (StringUtils.isValid(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode())) {
            String bugOne = Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 1);
            String bugTwo = Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 2);
            String bugThree = Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 3);
            tvTroubleTitle.setText(bugThree);
        } else {
            tvTroubleTitle.setText("");
        }
        //品牌型号
//        tv_brand_model.setText(Optional.ofNullable(Config.get().getModelNameByCode(bughandleDetailEntity.getFailureEntity().getModelCode(), 2)).orElse(""));
        //故障设备
//        tv_trouble_device.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getDeviceName()).orElse(""));
        //设备编号
        tvDeviceNo.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getDeviceNo()).orElse(""));
        //故障位置
        tvDeviceLocation.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getBugPosition()).orElse(""));
        //故障描述
        etTroubleDesc.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getBugDescription()).orElse(""));


        //加载上次提交记录
        if (bughandleDetailEntity.getId() != null) {
            new TrueFalseDialog(this, "系统提醒", "是否加载并修改上次提交的记录？",
                    () -> {
                        etTroublePoint.setText(Optional.ofNullable(bughandleDetailEntity.getCheckProcess()).orElse(""));
                        etTroubleReason.setText(Optional.ofNullable(bughandleDetailEntity.getCause()).orElse(""));
                        etTroubleDeal.setText(Optional.ofNullable(bughandleDetailEntity.getHandle()).orElse(""));
                        etTroubleUseAdvace.setText(Optional.ofNullable(bughandleDetailEntity.getUseAdvice()).orElse(""));
                        mReapirOneStauts = bughandleDetailEntity.getStatus();
                        mReapirTwoStauts = bughandleDetailEntity.getStatusTwo();
                        addRepariResult();
                        addReapirResultMode(mReapirOneStauts);
                        // 是否误报
                        if (bughandleDetailEntity.getFailureEntity().getIsMisinformation() == 0) {
                            rgNo.setChecked(true);
                            rgYes.setChecked(false);
                        } else {
                            rgNo.setChecked(false);
                            rgYes.setChecked(true);
                        }
                    },
                    () -> {
                        bughandleDetailEntity.setId(null);

                    }).showDialog();
        }

        //添加维修结论
        addRepariResult();

    }

    private boolean checkData() {
        if (StringUtils.isEmpty(etTroubleDesc.getText().toString().trim())) {
            showToast("请输入故障简述");
            return false;
        }
        if (StringUtils.isEmpty(etTroubleReason.getText().toString().trim())) {
            showToast("请输入原因判断");
            return false;
        }
        if (StringUtils.isEmpty(etTroublePoint.getText().toString().trim())) {
            showToast("请输入 过程和方法");
            return false;
        }
        if (StringUtils.isEmpty(etTroubleDeal.getText().toString().trim())) {
            showToast("请输入处理方法");
            return false;
        }
        if (StringUtils.isEmpty(etTroubleUseAdvace.getText().toString().trim())) {
            showToast("请输入使用建议");
            return false;
        }
        if (StringUtils.isEmpty(etTroubleReason.getText().toString().trim())) {
            showToast("请输入原因");
            return false;
        }
        if (mReapirOneStauts == 100) {
            showToast("请选择维修结果");
            return false;
        }
        if (mReapirTwoStauts == 200) {
            showToast("请选择修复方式");
            return false;
        }
        return true;
    }

    private void submit() {
        if (!checkData()) {
            return;
        }
        // 故障描述
        bughandleDetailEntity.getFailureEntity().setBugDescription(etTroubleDesc.getText().toString().trim());
//        bughandleDetailEntity.getFailureEntity().setBusinessThreeCode(Config.get().getBusinessCodeByName(tv_trouble_device.getText().toString().trim(), 3));
        //设备编号
        bughandleDetailEntity.getFailureEntity().setDeviceNo(tvDeviceNo.getText().toString().trim());
        // 设备位置
        bughandleDetailEntity.getFailureEntity().setBugPosition(tvDeviceLocation.getText().toString().trim());
        // 设备名称
        bughandleDetailEntity.getFailureEntity().setDeviceName("");
        // 原因判断
        bughandleDetailEntity.setCause(etTroubleReason.getText().toString().trim());
        // 处理方法
        bughandleDetailEntity.setHandle(etTroubleDeal.getText().toString().trim());
        // 过程和方法
        bughandleDetailEntity.setCheckProcess(etTroublePoint.getText().toString().trim());

        bughandleDetailEntity.setBusBughandleConfirmId(confirmId);
        //   使用建议
        bughandleDetailEntity.setUseAdvice(etTroubleUseAdvace.getText().toString().trim());
        //是否误报(0：否，1：是)
        bughandleDetailEntity.getFailureEntity().setIsMisinformation(GetConstDataUtils.getRepairMisinformationList().indexOf(mIsRepairError));
        //维修结果
        bughandleDetailEntity.setStatus(mReapirOneStauts);
        //修復方式
        bughandleDetailEntity.setStatusTwo(mReapirTwoStauts);
        //设备参数
        bughandleDetailEntity.setParamEntityList(paramEntityList);

        doHttpSubmit();

    }

    private void doHttpSubmit() {

        EanfangHttp.post(RepairApi.GET_REPAIR_BUGHANDLE_CREATE_DETAIL)
                .upJson(JSONObject.toJSONString(bughandleDetailEntity))
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
                    runOnUiThread(() -> {
                        getIntent().putExtra("beans", bughandleDetailEntity);
                        getIntent().putExtra("position", position);
                        setResult(2000, getIntent());
                        finishSelf();
                    });
                }));


    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.rg_yes:
                mIsRepairError = "是";
                break;
            case R.id.rg_no:
                mIsRepairError = "否";
                break;
            default:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_DEVICE_PARAM_REQUEST && resultCode == ADD_DEVICE_PARAM_RESULT) {
            paramEntityList = (List<BughandleParamEntity>) data.getSerializableExtra("addParam");
        }
    }

    public void addRepariResult() {

        tagRepairResult.setAdapter(mResultAdapter = new TagAdapter<String>(mRepairResult) {
            @Override
            public View getView(FlowLayout parent, int position, String mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(PhoneSolveTroubleDetailActivity.this).inflate(R.layout.layout_trouble_result_item, tagRepairResult, false);
                tv.setText(mrepairResult);
                return tv;
            }
        });
        if (mReapirOneStauts != 100) {
            mResultAdapter.setSelectedList(mReapirOneStauts);
        }
        tagRepairResult.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (!selectPosSet.isEmpty()) {
                    String str = selectPosSet.toString().substring(1, selectPosSet.toString().length() - 1);
                    int position = Integer.parseInt(str);
                    mReapirOneStauts = position;
                    mReapirOneStauts = 100;
                    addReapirResultMode(position);
                }
            }
        });

    }

    public void addReapirResultMode(int status) {
        if (tagRepairResultTwo.getSelectedList().size() > 0) {
            tagRepairResultTwo.getSelectedList().clear();
            tagRepairResultTwo.getAdapter().notifyDataChanged();
        }
        tagRepairResultTwo.setAdapter(mModeAdapter = new TagAdapter<String>(GetConstDataUtils.getBugDetailTwoList(status)) {
            @Override
            public View getView(FlowLayout parent, int position, String mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(PhoneSolveTroubleDetailActivity.this).inflate(R.layout.layout_trouble_result_item, tagRepairResultTwo, false);
                tv.setText(mrepairResult);
                return tv;
            }
        });
        if (mReapirTwoStauts != 100) {
            mModeAdapter.setSelectedList(mReapirTwoStauts);
        }
        tagRepairResultTwo.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (!selectPosSet.isEmpty()) {
                    String str = selectPosSet.toString().substring(1, selectPosSet.toString().length() - 1);
                    int position = Integer.parseInt(str);
                    mReapirTwoStauts = position;
                }
            }
        });
    }

}

