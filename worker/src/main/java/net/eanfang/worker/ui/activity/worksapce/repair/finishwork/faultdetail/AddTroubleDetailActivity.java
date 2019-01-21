package net.eanfang.worker.ui.activity.worksapce.repair.finishwork.faultdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
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
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.BughandleDetailEntity;
import com.yaf.base.entity.BughandleParamEntity;
import com.yaf.base.entity.BughandleUseDeviceEntity;
import com.yaf.base.entity.RepairFailureEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.AddMaterialActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.AddTroubleAddPictureActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.DeviceParameterActivity;
import net.eanfang.worker.ui.adapter.MaterialAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:24
 * @email houzhongzhou@yeah.net
 * @desc 电话未解决 完善故障处理明细·
 */

public class AddTroubleDetailActivity extends BaseWorkerActivity implements RadioGroup.OnCheckedChangeListener {
    public static final String TAG = AddTroubleDetailActivity.class.getSimpleName();


    private static final int REQUEST_ADD_MATERIAL = 200;
    private static final int RESULT_ADD_MATERIAL = 2000;

    public static final int ADD_DEVICE_PARAM_REQUEST = 100;
    public static final int ADD_DEVICE_PARAM_RESULT = 1000;

    @BindView(R.id.tv_trouble_title)
    TextView tvTroubleTitle;
    @BindView(R.id.tv_device_no)
    TextView tvDeviceNo;
    @BindView(R.id.tv_device_location)
    TextView tvDeviceLocation;

    //位置编号
    @BindView(R.id.tv_location_num)
    TextView tvLocationNum;

    // 故障简述
    @BindView(R.id.et_trouble_sketch)
    EditText etTroubleSketch;
    // 故障描述
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
    @BindView(R.id.tag_repair_result)
    TagFlowLayout tagRepairResult;
    // 原因判断
    @BindView(R.id.iv_voice_input_trouble_reason)
    ImageView ivVoiceInputTroubleReason;
    @BindView(R.id.tv_num)
    TextView tvNum;
    //过程方法
    @BindView(R.id.iv_voice_input_trouble_point)
    ImageView ivVoiceInputTroublePoint;
    @BindView(R.id.tv_trouble_point_num)
    TextView tvTroublePointNum;
    // 处理措施
    @BindView(R.id.iv_voice_input_trouble_deal)
    ImageView ivVoiceInputTroubleDeal;
    @BindView(R.id.tv_trouble_deal_num)
    TextView tvTroubleDealNum;
    // 使用建议
    @BindView(R.id.iv_voice_input_trouble_useAdvance)
    ImageView ivVoiceInputTroubleUseAdvance;
    @BindView(R.id.tv_use_advance_num)
    TextView tvUseAdvanceNum;

    private TagAdapter<String> mResultAdapter;
    // 修复方式
    @BindView(R.id.tag_repair_result_two)
    TagFlowLayout tagRepairResultTwo;
    private TagAdapter<String> mModeAdapter;


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
//    private List<BughandleUseDeviceEntity> useDeviceEntityList = new ArrayList<>();

    private HashMap<String, String> mUploadMapPicture = new HashMap<>();

    // 设备参数List
    private List<BughandleParamEntity> paramEntityList = new ArrayList<>();

    // 维修结果
    List<String> mRepairResult = GetConstDataUtils.getBugDetailList();

    // 是否加载上次
    private boolean isLoad = false;

    private int maxWordsNum = 200; //输入限制的最大字数

    /**
     * 故障图片
     */
    private String mPicture = "";

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
        mPicture = getIntent().getStringExtra("picture");
        // bean = (BughandleDetailEntity) getIntent().getSerializableExtra("bean");
        // bugBean = (BusinessWorkBean) getIntent().getSerializableExtra("bugBean");
        // bugOneCode = getIntent().getStringExtra("bugOneCode");
        initAdapter();
        lookFailureDetail();
    }

    private void initListener() {

        rgMisreport.setOnCheckedChangeListener(this);
        //添加设备参数
        rlAddDeviceParam.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("paramEntityList", (Serializable) paramEntityList);
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
        // 添加现场照片
        rlAddDevicePicture.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("detailEntity", detailEntity);
            bundle.putBoolean("isLoad", isLoad);
            bundle.putSerializable("mUploadMapPicture", mUploadMapPicture);
            JumpItent.jump(AddTroubleDetailActivity.this, AddTroubleAddPictureActivity.class, bundle);
        });
        // 原因判断
        ivVoiceInputTroubleReason.setOnClickListener((v) -> {
            PermissionUtils.get(this).getVoicePermission(() -> {
                RecognitionManager.getSingleton().startRecognitionWithDialog(AddTroubleDetailActivity.this, etTroubleReason);
            });
        });
        etTroubleReason.addTextChangedListener(new TextWatcher() {
            CharSequence temp;
            int selectionStart;
            int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvNum.setText(s.length() + "/" + maxWordsNum);
                selectionStart = etTroubleReason.getSelectionStart();
                selectionEnd = etTroubleReason.getSelectionEnd();
                if (temp.length() > maxWordsNum) {
//                    toast("不能超过" + maxWordsNum + "个字");
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    etTroubleReason.setText(s);
                    etTroubleReason.setSelection(tempSelection); //设置光标在最后
                }
            }
        });
        //过程方法
        ivVoiceInputTroublePoint.setOnClickListener((v) -> {
            PermissionUtils.get(this).getVoicePermission(() -> {
                RecognitionManager.getSingleton().startRecognitionWithDialog(AddTroubleDetailActivity.this, etTroublePoint);
            });
        });
        etTroublePoint.addTextChangedListener(new TextWatcher() {
            CharSequence temp;
            int selectionStart;
            int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvTroublePointNum.setText(s.length() + "/" + maxWordsNum);
                selectionStart = etTroublePoint.getSelectionStart();
                selectionEnd = etTroublePoint.getSelectionEnd();
                if (temp.length() > maxWordsNum) {
//                    toast("不能超过" + maxWordsNum + "个字");
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    etTroublePoint.setText(s);
                    etTroublePoint.setSelection(tempSelection); //设置光标在最后
                }
            }
        });

        // 处理措施
        ivVoiceInputTroubleDeal.setOnClickListener((v) -> {
            PermissionUtils.get(this).getVoicePermission(() -> {
                RecognitionManager.getSingleton().startRecognitionWithDialog(AddTroubleDetailActivity.this, etTroubleDeal);
            });
        });
        etTroubleDeal.addTextChangedListener(new TextWatcher() {
            CharSequence temp;
            int selectionStart;
            int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvTroubleDealNum.setText(s.length() + "/" + maxWordsNum);
                selectionStart = etTroubleDeal.getSelectionStart();
                selectionEnd = etTroubleDeal.getSelectionEnd();
                if (temp.length() > maxWordsNum) {
//                    toast("不能超过" + maxWordsNum + "个字");
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    etTroubleDeal.setText(s);
                    etTroubleDeal.setSelection(tempSelection); //设置光标在最后
                }
            }
        });
        // 使用建议
        ivVoiceInputTroubleUseAdvance.setOnClickListener((v) -> {
            PermissionUtils.get(this).getVoicePermission(() -> {
                RecognitionManager.getSingleton().startRecognitionWithDialog(AddTroubleDetailActivity.this, etTroubleUseAdvace);
            });
        });
        etTroubleUseAdvace.addTextChangedListener(new TextWatcher() {
            CharSequence temp;
            int selectionStart;
            int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvUseAdvanceNum.setText(s.length() + "/" + maxWordsNum);
                selectionStart = etTroubleUseAdvace.getSelectionStart();
                selectionEnd = etTroubleUseAdvace.getSelectionEnd();
                if (temp.length() > maxWordsNum) {
//                    toast("不能超过" + maxWordsNum + "个字");
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    etTroubleUseAdvace.setText(s);
                    etTroubleUseAdvace.setSelection(tempSelection); //设置光标在最后
                }
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
        tvDeviceNo.setText(Optional.ofNullable(failureEntity.getDeviceNo()).orElse(""));
        tvDeviceLocation.setText(Optional.ofNullable(failureEntity.getBugPosition()).orElse(""));
        tvLocationNum.setText(Optional.ofNullable(failureEntity.getLocationNumber()).orElse(""));
        etTroubleSketch.setText(Optional.ofNullable(failureEntity.getSketch()).orElse(""));
        etTroubleDesc.setText(Optional.ofNullable(failureEntity.getBugDescription()).orElse(""));

        if (detailEntity == null || detailEntity.getParamEntityList() == null) {
            detailEntity.setParamEntityList(new ArrayList<>(0));
        }
        if (detailEntity == null || detailEntity.getUseDeviceEntityList() == null) {
            detailEntity.setUseDeviceEntityList(new ArrayList<>(0));
        }


        //加载上次提交记录
        if (detailEntity.getId() != null) {
            new TrueFalseDialog(this, "系统提醒", "是否加载并修改上次提交的记录？",
                    () -> {
                        isLoad = true;
                        etTroublePoint.setText(Optional.ofNullable(detailEntity.getCheckProcess()).orElse(""));
                        etTroubleReason.setText(Optional.ofNullable(detailEntity.getCause()).orElse(""));
                        etTroubleDeal.setText(Optional.ofNullable(detailEntity.getHandle()).orElse(""));
                        etTroubleUseAdvace.setText(Optional.ofNullable(detailEntity.getUseAdvice()).orElse(""));
                        if (detailEntity.getStatus() != null && detailEntity.getStatusTwo() != null) {
                            mReapirOneStauts = detailEntity.getStatus();
                            mReapirTwoStauts = detailEntity.getStatusTwo();
                        }
                        addRepariResult();
                        addReapirResultMode(mReapirOneStauts);
                        // 是否误报
                        if (failureEntity.getIsMisinformation() == 0) {
                            rgNo.setChecked(true);
                            rgYes.setChecked(false);
                        } else {
                            rgNo.setChecked(false);
                            rgYes.setChecked(true);
                        }
                        if (detailEntity.getUseDeviceEntityList() != null) {
//                            useDeviceEntityList = detailEntity.getUseDeviceEntityList();
                            materialAdapter.setNewData(detailEntity.getUseDeviceEntityList());
                        }
                        if (detailEntity.getParamEntityList() != null) {
                            paramEntityList = detailEntity.getParamEntityList();
                        }
                    },
                    () -> {
                        isLoad = false;
                        detailEntity.setId(null);
                        detailEntity.setPresentationPictures(null);
                        detailEntity.setPointPictures(null);
                        detailEntity.setRestorePictures(null);
                    }).showDialog();
        }
        addRepariResult();
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
        repairFailureEntity.setSketch(etTroubleSketch.getText().toString().trim());
        repairFailureEntity.setIsMisinformation(GetConstDataUtils.getRepairMisinformationList().indexOf(mIsRepairError));
        repairFailureEntity.setBugPosition(tvDeviceLocation.getText().toString().trim());
        if (!TextUtils.isEmpty(mPicture)) {
            repairFailureEntity.setPictures(mPicture);
        }
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
        detailEntity.setUseDeviceEntityList(materialAdapter.getData());
        //设备参数
        detailEntity.setParamEntityList(paramEntityList);

        doHttpSubmit();

    }

    private void initAdapter() {

        materialAdapter = new MaterialAdapter(R.layout.layout_item_add_material);
        materialAdapter.bindToRecyclerView(rcyConsumable);

        materialAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.iv_delete:
                    materialAdapter.remove(position);
                    break;
                default:
                    break;
            }
        });

    }


    private boolean checkData() {
        if (StringUtils.isEmpty(etTroubleDesc.getText().toString().trim())) {
            showToast("请输入故障描述");
            return false;
        }
        if (StringUtils.isEmpty(etTroubleSketch.getText().toString().trim())) {
            showToast("请输入故障简述");
            return false;
        }
        if (StringUtils.isEmpty(etTroubleReason.getText().toString().trim())) {
            showToast("请输入原因");
            return false;
        }
        if (StringUtils.isEmpty(etTroublePoint.getText().toString().trim())) {
            showToast("请输入过程方法");
            return false;
        }
        if (StringUtils.isEmpty(etTroubleDeal.getText().toString().trim())) {
            showToast("请输入处理措施");
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
        if (StringUtils.isEmpty(detailEntity.getPresentationPictures())) {
            showToast("请添加现场照片");
            return false;
        }
        if (StringUtils.isEmpty(detailEntity.getPointPictures())) {
            showToast("请添加现场照片");
            return false;
        }
        if (StringUtils.isEmpty(detailEntity.getRestorePictures())) {
            showToast("请添加现场照片");
            return false;
        }
//        if (paramEntityList.size() == 0) {
//            showToast("请选择设备参数");
//            return false;
//        }
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
        // 耗材
        if (requestCode == REQUEST_ADD_MATERIAL && resultCode == RESULT_ADD_MATERIAL) {
//            useDeviceEntityList.add((BughandleUseDeviceEntity) data.getSerializableExtra("bean"));
            materialAdapter.addData((BughandleUseDeviceEntity) data.getSerializableExtra("bean"));
        } else if (requestCode == ADD_DEVICE_PARAM_REQUEST && resultCode == ADD_DEVICE_PARAM_RESULT) {
            // 设备参数
            paramEntityList = (List<BughandleParamEntity>) data.getSerializableExtra("addParam");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 添加照片回调
     */
    @Subscribe
    public void onEventWork(BughandleDetailEntity mDetailEntity) {
        detailEntity = mDetailEntity;
    }

    @Subscribe
    public void onEventWorkPicture(HashMap<String, String> uploadMap) {
        mUploadMapPicture = uploadMap;
    }

    public void addRepariResult() {

        tagRepairResult.setAdapter(mResultAdapter = new TagAdapter<String>(mRepairResult) {
            @Override
            public View getView(FlowLayout parent, int position, String mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(AddTroubleDetailActivity.this).inflate(R.layout.layout_trouble_result_item, tagRepairResult, false);
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
                    mReapirTwoStauts = 200;
                    addReapirResultMode(position);
                } else {
                    mReapirOneStauts = 100;
                }
            }
        });

    }

    public void addReapirResultMode(int status) {
        List<String> faultModeList = GetConstDataUtils.getBugDetailTwoList(status);
        if (tagRepairResultTwo.getSelectedList().size() > 0) {
            tagRepairResultTwo.getSelectedList().clear();
            tagRepairResultTwo.getAdapter().notifyDataChanged();

        }
        tagRepairResultTwo.setAdapter(mModeAdapter = new TagAdapter<String>(faultModeList) {
            @Override
            public View getView(FlowLayout parent, int position, String mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(AddTroubleDetailActivity.this).inflate(R.layout.layout_trouble_result_item, tagRepairResultTwo, false);
                tv.setText(mrepairResult);
                return tv;
            }
        });
        if (mReapirTwoStauts > faultModeList.size()) {
            mReapirTwoStauts = 200;
        }
        if (mReapirTwoStauts != 200) {
            mModeAdapter.setSelectedList(mReapirTwoStauts);
        }

        tagRepairResultTwo.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (!selectPosSet.isEmpty()) {
                    String str = selectPosSet.toString().substring(1, selectPosSet.toString().length() - 1);
                    int position = Integer.parseInt(str);
                    mReapirTwoStauts = position;
                } else {
                    mReapirTwoStauts = 200;
                }
            }
        });
    }

}

