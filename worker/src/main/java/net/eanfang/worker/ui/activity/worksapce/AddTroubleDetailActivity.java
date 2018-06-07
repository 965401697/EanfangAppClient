package net.eanfang.worker.ui.activity.worksapce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.eanfang.witget.CustomRadioGroup;
import com.yaf.base.entity.BughandleDetailEntity;
import com.yaf.base.entity.BughandleUseDeviceEntity;
import com.yaf.base.entity.RepairFailureEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.repair.AddTroubleAddPictureActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.DeviceParameterActivity;
import net.eanfang.worker.ui.adapter.MaterialAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:24
 * @email houzhongzhou@yeah.net
 * @desc 完善故障处理明细·
 */

public class AddTroubleDetailActivity extends BaseWorkerActivity implements RadioGroup.OnCheckedChangeListener {
    public static final String TAG = AddTroubleDetailActivity.class.getSimpleName();


    private static final int REQUEST_ADD_MATERIAL = 200;
    private static final int RESULT_ADD_MATERIAL = 2000;

    public static final int ADD_DEVICE_PARAM_REQUEST = 100;
    public static final int ADD_DEVICE_PARAM_RESULT = 1000;

    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_trouble_title)
    TextView tvTroubleTitle;
    @BindView(R.id.tv_device_no)
    TextView tvDeviceNo;
    @BindView(R.id.tv_device_location)
    TextView tvDeviceLocation;
    @BindView(R.id.et_trouble_desc)
    EditText etTroubleDesc;
    // 过程方法
    @BindView(R.id.et_trouble_point)
    EditText etTroublePoint;
    // 原因判断
    @BindView(R.id.et_trouble_reason)
    EditText etTroubleReason;
    // 处理措施
    @BindView(R.id.et_trouble_deal)
    EditText etTroubleDeal;

    // 使用建议
    @BindView(R.id.et_trouble_useAdvace)
    EditText etTroubleUseAdvace;

    @BindView(R.id.tv_add_consumable)
    TextView tvAddConsumable;
    @BindView(R.id.rcy_consumable)
    RecyclerView rcyConsumable;
    @BindView(R.id.btn_add_trouble)
    Button btnAddTrouble;
    // 是否误报
    @BindView(R.id.rg_yes)
    RadioButton rgYes;
    @BindView(R.id.rg_no)
    RadioButton rgNo;
    @BindView(R.id.rg_misreport)
    RadioGroup rgMisreport;
    // 添加照片
    @BindView(R.id.rl_add_device_picture)
    RelativeLayout rlAddDevicePicture;
    // 维修结果
    @BindView(R.id.rg_repairResultOne)
    CustomRadioGroup rgRepairResultOne;
    // 修复方式
    @BindView(R.id.rg_repairResultTwo)
    CustomRadioGroup rgRepairResultTwo;

    // 维修结果一级
    private int mReapirOneStauts = 100;
    // 维修结果二级
    private int mReapirTwoStauts = 200;

    // 添加设备参数
    @BindView(R.id.rl_add_device_param)
    RelativeLayout rlAddDeviceParam;

    private String mPresentationPic = "";


    // private int position;
    private MaterialAdapter materialAdapter;
    private RepairFailureEntity failureEntity;
    private BughandleDetailEntity detailEntity = new BughandleDetailEntity();

    private Long confirmId;
    private Long failureId;
    private int position;

    // 是否误报
    private String mIsRepairError = "否";
    // 耗用材料
    private List<BughandleUseDeviceEntity> useDeviceEntityList = new ArrayList<>();

    private HashMap<String, String> uploadMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble_detail);
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        setTitle("故障明细");
        setLeftBack();

        rcyConsumable.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcyConsumable.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        position = getIntent().getIntExtra("position", 0);
        failureId = getIntent().getLongExtra("failureId", 0);
        confirmId = getIntent().getLongExtra("confirmId", 0);
        // bean = (BughandleDetailEntity) getIntent().getSerializableExtra("bean");
        // bugBean = (BusinessWorkBean) getIntent().getSerializableExtra("bugBean");
        // bugOneCode = getIntent().getStringExtra("bugOneCode");
        lookFailureDetail();
    }

    private void initListener() {

        rgMisreport.setOnCheckedChangeListener(this);
        //添加设备参数
        rlAddDeviceParam.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("bughandleDetailEntity", detailEntity);
            JumpItent.jump(this, DeviceParameterActivity.class, bundle, ADD_DEVICE_PARAM_REQUEST);
        });
        // 确定
        btnAddTrouble.setOnClickListener(v -> submit());
        // 添加耗材
        tvAddConsumable.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            String bugOne = Config.get().getBusinessNameByCode(failureEntity.getBusinessThreeCode(), 1);
            bundle.putString("bugOneCode", Config.get().getBusinessCodeByName(bugOne, 1));
            JumpItent.jump(AddTroubleDetailActivity.this, AddMaterialActivity.class, bundle, REQUEST_ADD_MATERIAL);
        });

        rlAddDevicePicture.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("detailEntity", detailEntity);
            JumpItent.jump(AddTroubleDetailActivity.this, AddTroubleAddPictureActivity.class, bundle);
        });
        // 维修结果一级
        rgRepairResultOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton tempButton = (RadioButton) findViewById(checkedId);
                mReapirOneStauts = (Integer) tempButton.getTag();
                addView(AddTroubleDetailActivity.this, rgRepairResultTwo, GetConstDataUtils.getBugDetailTwoList(mReapirOneStauts));
            }
        });
        rgRepairResultTwo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton tempButton = (RadioButton) findViewById(checkedId);
                mReapirTwoStauts = (Integer) tempButton.getTag();
            }
        });

    }

    private void lookFailureDetail() {
        EanfangHttp.get(RepairApi.GET_FAILURE_DETAIL)
                .params("id", failureId)
                .execute(new EanfangCallback<RepairFailureEntity>(this, true, RepairFailureEntity.class, (bean) -> {
                    failureEntity = bean;
                    if (failureEntity.getBughandleDetailEntityList() != null && failureEntity.getBughandleDetailEntityList().size() > 0) {
                        //取最后一条
                        detailEntity = failureEntity.getBughandleDetailEntityList().get(failureEntity.getBughandleDetailEntityList().size() - 1);
                    }
                    fillData();
                    initAdapter();
                }));
    }

    public void fillData() {

        if (StringUtils.isValid(failureEntity.getBusinessThreeCode())) {
            String bugOne = Config.get().getBusinessNameByCode(failureEntity.getBusinessThreeCode(), 1);
            String bugTwo = Config.get().getBusinessNameByCode(failureEntity.getBusinessThreeCode(), 2);
            String bugThree = Config.get().getBusinessNameByCode(failureEntity.getBusinessThreeCode(), 3);
            tvTroubleTitle.setText(bugOne + "-" + bugTwo + "-" + bugThree);
        } else {
            tvTroubleTitle.setText("");
        }
//        tvTroubleDevice.setText(Optional.ofNullable(failureEntity.getDeviceName()).orElse(""));
//        tvBrandModel.setText(Optional.ofNullable(Config.get().getModelNameByCode(failureEntity.getModelCode(), 2)).orElse(""));
        tvDeviceNo.setText(Optional.ofNullable(failureEntity.getDeviceNo()).orElse(""));
        tvDeviceLocation.setText(Optional.ofNullable(failureEntity.getBugPosition()).orElse(""));
        etTroubleDesc.setText(Optional.ofNullable(failureEntity.getBugDescription()).orElse(""));

        //加载上次提交记录
        if (detailEntity.getId() != null) {
            new TrueFalseDialog(this, "系统提醒", "是否加载并修改上次提交的记录？",
                    () -> {
                        etTroublePoint.setText(Optional.ofNullable(detailEntity.getCheckProcess()).orElse(""));
                        etTroubleReason.setText(Optional.ofNullable(detailEntity.getCause()).orElse(""));
                        etTroubleDeal.setText(Optional.ofNullable(detailEntity.getHandle()).orElse(""));
                        etTroubleUseAdvace.setText(Optional.ofNullable(detailEntity.getUseAdvice()).orElse(""));
                        // 是否误报 TODO
//                        tvRepairMisinformation.setText(GetConstDataUtils.getRepairMisinformationList().get(failureEntity.getIsMisinformation()));
                        if (detailEntity.getStatus() != null) {
                            // 维修结论 TODO
//                            tvRepairConclusion.setText(Optional.ofNullable(GetConstDataUtils.getBugDetailList().get(detailEntity.getStatus())).orElse(""));
                        }
//                        initImgUrlList();
//                        initNinePhoto();
                    },
                    () -> {
                        detailEntity.setId(null);
                    }).showDialog();
        } else {
//            initNinePhoto();
        }
        if (detailEntity == null || detailEntity.getParamEntityList() == null) {
            detailEntity.setParamEntityList(new ArrayList<>(0));
        }
        if (detailEntity == null || detailEntity.getUseDeviceEntityList() == null) {
            detailEntity.setUseDeviceEntityList(new ArrayList<>(0));
        }

        //添加维修结论
        addView(AddTroubleDetailActivity.this, rgRepairResultOne, GetConstDataUtils.getBugDetailList());
    }

    private void initAdapter() {
        if (detailEntity.getUseDeviceEntityList() != null) {
            useDeviceEntityList = detailEntity.getUseDeviceEntityList();
            materialAdapter = new MaterialAdapter(R.layout.layout_item_add_material, useDeviceEntityList);
            rcyConsumable.setAdapter(materialAdapter);
            materialAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                switch (view.getId()) {
                    case R.id.iv_delete:
                        detailEntity.getUseDeviceEntityList().remove(position);
                        materialAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            });
        }
    }

    private void submit() {
        if (!checkData()) {
            return;
        }
        RepairFailureEntity repairFailureEntity = new RepairFailureEntity();
        repairFailureEntity.setId(failureId);
        repairFailureEntity.setBusinessThreeCode(failureEntity.getBusinessThreeCode());
        repairFailureEntity.setModelCode(failureEntity.getModelCode());
        detailEntity.setBusRepairFailureId(failureId);
        //故障描述
        repairFailureEntity.setBugDescription(etTroubleDesc.getText().toString().trim());
        repairFailureEntity.setIsMisinformation(GetConstDataUtils.getRepairMisinformationList().indexOf(mIsRepairError));
        detailEntity.setFailureEntity(repairFailureEntity);
        //   使用建议
        detailEntity.setUseAdvice(etTroubleUseAdvace.getText().toString().trim());

        detailEntity.setCause(etTroubleReason.getText().toString().trim());
        detailEntity.setHandle(etTroubleDeal.getText().toString().trim());
        detailEntity.setCheckProcess(etTroublePoint.getText().toString().trim());
        //维修结果
        detailEntity.setStatus(mReapirOneStauts);
        //修復方式
        detailEntity.setStatusTwo(mReapirTwoStauts);

        //   使用建议
        detailEntity.setUseAdvice(etTroubleUseAdvace.getText().toString().trim());
        detailEntity.setBusBughandleConfirmId(confirmId);
        // 设备耗材
        detailEntity.setUseDeviceEntityList(useDeviceEntityList);
        doHttpSubmit();

    }


    private boolean checkData() {
        if (StringUtils.isEmpty(etTroubleDesc.getText().toString().trim())) {
            showToast("请输入故障描述");
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
        if (StringUtils.isEmpty(etTroubleUseAdvace.getText().toString().trim())) {
            showToast("请输入使用建议");
            return false;
        }
        return true;
    }

    private void doHttpSubmit() {

        EanfangHttp.post(RepairApi.GET_REPAIR_BUGHANDLE_CREATE_DETAIL)
                .upJson(JSONObject.toJSONString(detailEntity))
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
                    runOnUiThread(() -> {
                        getIntent().putExtra("bean", detailEntity);
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
        if (requestCode == REQUEST_ADD_MATERIAL && resultCode == RESULT_ADD_MATERIAL) {
            useDeviceEntityList.add((BughandleUseDeviceEntity) data.getSerializableExtra("bean"));
            materialAdapter.notifyDataSetChanged();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe
    public void onEventWork(BughandleDetailEntity mDetailEntity) {
        detailEntity = mDetailEntity;
    }

    /**
     * 动态添加维修结果
     */
    public static void addView(final Context context, CustomRadioGroup
            parent, List<String> list) {
        parent.removeAllViews();
        RadioGroup.LayoutParams pa = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < list.size(); i++) {
            final RadioButton radioButton = new RadioButton(context);
            pa.setMargins(22, 22, 22, 30);
            radioButton.setLayoutParams(pa);
            radioButton.setText(list.get(i));
            radioButton.setTag(i);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextSize(12);
            radioButton.setPadding(20, 20, 20, 20);
            radioButton.setBackground(null);
            radioButton.setButtonDrawable(null);
            radioButton.setTextColor(R.drawable.select_camera_text_back);
            radioButton.setBackgroundResource(R.drawable.select_camera_back);
            parent.addView(radioButton);
        }
    }
}

