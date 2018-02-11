package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Optional;
import com.bigkoo.pickerview.OptionsPickerView;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleDetailEntity;
import com.yaf.base.entity.BughandleParamEntity;
import com.yaf.base.entity.BughandleUseDeviceEntity;
import com.yaf.base.entity.RepairFailureEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.MaterialAdapter;
import net.eanfang.worker.ui.adapter.ParamAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:24
 * @email houzhongzhou@yeah.net
 * @desc 完善故障处理明细·
 */

public class AddTroubleDetailActivity extends BaseWorkerActivity {
    public static final String TAG = AddTroubleDetailActivity.class.getSimpleName();

    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_2 = 2;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_3 = 3;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_4 = 4;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_5 = 5;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_6 = 6;

    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 101;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_2 = 102;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_3 = 103;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_4 = 104;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_5 = 105;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_6 = 106;

    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_call_service_phone)
    TextView tvCallServicePhone;
    @BindView(R.id.tv_trouble_title)
    TextView tvTroubleTitle;
    @BindView(R.id.tv_trouble_device)
    TextView tvTroubleDevice;
    @BindView(R.id.rl_trouble_device)
    RelativeLayout rlTroubleDevice;
    @BindView(R.id.tv_brand_model)
    TextView tvBrandModel;
    @BindView(R.id.rl_brand_model)
    RelativeLayout rlBrandModel;
    @BindView(R.id.tv_device_no)
    TextView tvDeviceNo;
    @BindView(R.id.rl_device_no)
    RelativeLayout rlDeviceNo;
    @BindView(R.id.tv_device_location)
    TextView tvDeviceLocation;
    @BindView(R.id.rl_device_location)
    RelativeLayout rlDeviceLocation;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.rcy_parameter)
    RecyclerView rcyParameter;
    @BindView(R.id.et_trouble_desc)
    EditText etTroubleDesc;
    @BindView(R.id.ll_trouble_desc)
    LinearLayout llTroubleDesc;
    @BindView(R.id.ll_device_failure_solution)
    LinearLayout llDeviceFailureSolution;
    @BindView(R.id.et_trouble_point)
    EditText etTroublePoint;
    @BindView(R.id.ll_trouble_point)
    LinearLayout llTroublePoint;
    @BindView(R.id.et_trouble_reason)
    EditText etTroubleReason;
    @BindView(R.id.ll_trouble_reason)
    LinearLayout llTroubleReason;
    @BindView(R.id.et_trouble_deal)
    EditText etTroubleDeal;
    @BindView(R.id.ll_trouble_deal)
    LinearLayout llTroubleDeal;
    /**
     * 维修结论
     */
    @BindView(R.id.tv_repair_conclusion)
    TextView tvRepairConclusion;
    @BindView(R.id.ll_repair_conclusion)
    LinearLayout llRepairConclusion;
    /**
     * 故障表象 （3张）
     */
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    /**
     * 工具及蓝布 （3张）
     */
    @BindView(R.id.snpl_monitor_add_photos)
    BGASortableNinePhotoLayout snplMonitorAddPhotos;
    /**
     * 故障点照片 （3张）
     */
    @BindView(R.id.snpl_tools_package_add_photos)
    BGASortableNinePhotoLayout snplToolsPackageAddPhotos;
    /**
     * 处理后现场 （3张）
     */
    @BindView(R.id.snpl_after_processing_locale)
    BGASortableNinePhotoLayout snplAfterProcessingLocale;
    /**
     * 设备回装 （3张）
     */
    @BindView(R.id.snpl_machine_fit_back)
    BGASortableNinePhotoLayout snplMachineFitBack;
    /**
     * 故障恢复后表象 （3张）
     */
    @BindView(R.id.snpl_failure_recover_phenomena)
    BGASortableNinePhotoLayout snplFailureRecoverPhenomena;
    @BindView(R.id.tv_add_consumable)
    TextView tvAddConsumable;
    @BindView(R.id.rcy_consumable)
    RecyclerView rcyConsumable;
    @BindView(R.id.ll_ph_vg)
    LinearLayout llPhVg;
    @BindView(R.id.btn_add_trouble)
    Button btnAddTrouble;
    @BindView(R.id.tv_repair_misinformation)
    TextView tvRepairMisinformation;
    @BindView(R.id.ll_repair_misinformation)
    LinearLayout llRepairMisinformation;
    @BindView(R.id.ll_deviceFailure)
    LinearLayout llDeviceFailure;
    @BindView(R.id.tv_device_failure_solution)
    TextView tvDeviceFailureSolution;

    /**
     * 故障表象 （3张）
     */
    private ArrayList<String> picList1 = new ArrayList<>();
    /**
     * 工具及蓝布 （3张）
     */
    private ArrayList<String> picList2 = new ArrayList<>();
    /**
     * 故障点照片 （3张）
     */
    private ArrayList<String> picList3 = new ArrayList<>();
    //2017年7月21日
    /**
     * 处理后现场 （3张）
     */
    private ArrayList<String> picList4 = new ArrayList<>();
    /**
     * 设备回装 （3张）
     */
    private ArrayList<String> picList5 = new ArrayList<>();
    /**
     * 故障恢复后表象 （3张）
     */
    private ArrayList<String> picList6 = new ArrayList<>();

    private HashMap<String, String> uploadMap = new HashMap<>();

    // private int position;
    private MaterialAdapter materialAdapter;
    private RepairFailureEntity failureEntity;
    private BughandleDetailEntity detailEntity = new BughandleDetailEntity();
    private OptionsPickerView pvOptions;
    private ParamAdapter paramAdapter;

    private Long confirmId;
    private Long failureId;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble_detail);
        ButterKnife.bind(this);

        supprotToolbar();
        setTitle("故障明细");
        initData();
        lookFailureDetail();

        initListener();
    }


    private void initData() {
        // bean = (BughandleDetailEntity) getIntent().getSerializableExtra("bean");
        position = getIntent().getIntExtra("position", 0);
        failureId = getIntent().getLongExtra("failureId", 0);
        confirmId = getIntent().getLongExtra("confirmId", 0);
//        bugBean = (BusinessWorkBean) getIntent().getSerializableExtra("bugBean");
//        bugOneCode = getIntent().getStringExtra("bugOneCode");
    }

    private void initAdapter() {
        if (detailEntity.getParamEntityList() != null) {
            paramAdapter = new ParamAdapter(R.layout.item_parm, detailEntity.getParamEntityList());
            rcyParameter.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL));
            rcyParameter.setLayoutManager(new LinearLayoutManager(this));
            rcyParameter.setAdapter(paramAdapter);
        }
        if (detailEntity.getUseDeviceEntityList() != null) {
            materialAdapter = new MaterialAdapter(R.layout.item_quotation_detail, detailEntity.getUseDeviceEntityList());
            rcyConsumable.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL));
            rcyConsumable.setLayoutManager(new LinearLayoutManager(this));
            rcyConsumable.setAdapter(materialAdapter);
        }
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
        tvTroubleDevice.setText(Optional.ofNullable(failureEntity.getDeviceName()).orElse(""));
        tvBrandModel.setText(Optional.ofNullable(Config.get().getModelNameByCode(failureEntity.getModelCode(), 2)).orElse(""));
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
                        tvRepairMisinformation.setText(GetConstDataUtils.getRepairMisinformationList().get(failureEntity.getIsMisinformation()));
                        if (detailEntity.getStatus() != null) {
                            tvRepairConclusion.setText(Optional.ofNullable(GetConstDataUtils.getBugDetailList().get(detailEntity.getStatus())).orElse(""));
                        }
                        initImgUrlList();
                        initNinePhoto();
                    },
                    () -> {
                        detailEntity.setId(null);
                    }).showDialog();
        } else {
            initNinePhoto();
        }
        if (detailEntity == null || detailEntity.getParamEntityList() == null) {
            detailEntity.setParamEntityList(new ArrayList<>(0));
        }
        if (detailEntity == null || detailEntity.getUseDeviceEntityList() == null) {
            detailEntity.setUseDeviceEntityList(new ArrayList<>(0));
        }
    }

    private void initListener() {
        llDeviceFailure.setOnClickListener((v) -> {
//            if (failureOptionBean == null) {
//                showToast("暂时没有参考内容");
//                return;
//            }
//            List<String> opts = Stream.of(failureOptionBean.getBean()).map(bean -> bean.getTitle()).toList();
//            PickerSelectUtil.singleTextPicker(this, "参考设备故障", opts, (index, item) -> {
//                et_trouble_desc.setText(failureOptionBean.getBean().get(index).getDescription());
//                //修改bean中设备uid
//                bean.setDeviceUid(failureOptionBean.getBean().get(index).getDeviceUid());
//                bean.setDeviceFailureUid(failureOptionBean.getBean().get(index).getUid());
//                doHttpSolution();
//            });
        });

        llDeviceFailureSolution.setOnClickListener((v) -> {
//            if (solutionOptionBean == null) {
//                showToast("暂时没有参考内容");
//                return;
//            }
//            List<String> opts = Stream.of(solutionOptionBean.getBean()).map(bean -> bean.getTitle()).toList();
//            PickerSelectUtil.singleTextPicker(this, "参考解决方案", opts, (index, item) -> {
//                tv_device_failure_solution.setText(item);
//                et_trouble_point.setText(solutionOptionBean.getBean().get(index).getCheckInfo());
//                et_trouble_reason.setText(solutionOptionBean.getBean().get(index).getCauseInfo());
//                et_trouble_deal.setText(solutionOptionBean.getBean().get(index).getDisposeInfo());
//            });
        });

        llRepairConclusion.setOnClickListener(v -> {
            PickerSelectUtil.singleTextPicker(this, "维修结论", tvRepairConclusion, GetConstDataUtils.getBugDetailList());
        });

        llRepairMisinformation.setOnClickListener(v -> {
            PickerSelectUtil.singleTextPicker(this, "是否误报", tvRepairMisinformation, GetConstDataUtils.getRepairMisinformationList());
        });
        tvAdd.setOnClickListener(v -> {
            PickerSelectUtil.singleTextPicker(this, "参数", GetConstDataUtils.getDeviceParamList(), (index, item) -> {
                BughandleParamEntity param = new BughandleParamEntity();
                param.setParamName(item);
                detailEntity.getParamEntityList().add(param);
                paramAdapter.notifyDataSetChanged();
            });
        });

//        btn_add_trouble.setOnClickListener(new MultiClickListener(this, this::checkInfo, this::submit));
        btnAddTrouble.setOnClickListener(v -> submit());

        tvAddConsumable.setOnClickListener(v -> {
            Intent intent = new Intent(AddTroubleDetailActivity.this, AddMaterialActivity.class);
            Bundle bundle = new Bundle();
//                bundle.putSerializable("bugBean", bugBean);
            String bugOne = Config.get().getBusinessNameByCode(failureEntity.getBusinessThreeCode(), 1);
            bundle.putString("bugOneCode", Config.get().getBusinessCodeByName(bugOne, 1));
            intent.putExtras(bundle);
            startActivityForResult(intent, 10009);
        });

//        materialAdapter.setOnItemChildClickListener((adapter, view, position1) -> {
//            switch (view.getId()) {
//                case R.id.rl_item_detail:
        //故障明细耗用材料item添加跳转
//                    Intent intent = new Intent(AddTroubleDetailActivity.this, MaterialInfoActivity.class);
//                    intent.putExtra("type", bean.getBughandledetailmateriallist().get(position1).getEquipmenttype());
//                    intent.putExtra("name", bean.getBughandledetailmateriallist().get(position1).getEquipmentname());
//                    intent.putExtra("model", bean.getBughandledetailmateriallist().get(position1).getEquipmentmodel());
//                    intent.putExtra("number", bean.getBughandledetailmateriallist().get(position1).getNum());
//                    intent.putExtra("mark", bean.getBughandledetailmateriallist().get(position1).getMemo());
//                    startActivity(intent);
//                    break;
//                case R.id.tv_delete:
//                    mDataList_2.remove(position1);
//                    materialAdapter.notifyDataSetChanged();
//                    break;
//                default:
//                    break;
//            }
//        });
    }

    /**
     * 初始化存储图片用的List集合
     */
    private void initImgUrlList() {
        //修改小bug 图片读取问题
        if (!StringUtils.isEmpty(detailEntity.getPresentationPictures())) {
            String[] presentationPic = detailEntity.getPresentationPictures().split(",");
            if (presentationPic.length >= 1) {
                picList1.add(BuildConfig.OSS_SERVER + presentationPic[0]);
            }
            if (presentationPic.length >= 2) {
                picList1.add(BuildConfig.OSS_SERVER + presentationPic[1]);
            }
            if (presentationPic.length >= 3) {
                picList1.add(BuildConfig.OSS_SERVER + presentationPic[2]);
            }
        }

        if (!StringUtils.isEmpty(detailEntity.getToolPictures())) {
            String[] toolPic = detailEntity.getToolPictures().split(",");
            if (toolPic.length >= 1) {
                picList2.add(BuildConfig.OSS_SERVER + toolPic[0]);
            }
            if (toolPic.length >= 2) {
                picList2.add(BuildConfig.OSS_SERVER + toolPic[1]);
            }
            if (toolPic.length >= 3) {
                picList2.add(BuildConfig.OSS_SERVER + toolPic[2]);
            }
        }
        if (!StringUtils.isEmpty(detailEntity.getPointPictures())) {
            String[] pointPic = detailEntity.getPointPictures().split(",");
            if (pointPic.length >= 1) {
                picList3.add(BuildConfig.OSS_SERVER + pointPic[0]);
            }
            if (pointPic.length >= 2) {
                picList3.add(BuildConfig.OSS_SERVER + pointPic[1]);
            }
            if (pointPic.length >= 3) {
                picList3.add(BuildConfig.OSS_SERVER + pointPic[2]);
            }
        }
        if (!StringUtils.isEmpty(detailEntity.getAfterHandlePictures())) {
            String[] afterHandlePic = detailEntity.getAfterHandlePictures().split(",");
            if (afterHandlePic.length >= 1) {
                picList4.add(BuildConfig.OSS_SERVER + afterHandlePic[0]);
            }
            if (afterHandlePic.length >= 2) {
                picList4.add(BuildConfig.OSS_SERVER + afterHandlePic[1]);
            }
            if (afterHandlePic.length >= 3) {
                picList4.add(BuildConfig.OSS_SERVER + afterHandlePic[2]);
            }
        }
        if (!StringUtils.isEmpty(detailEntity.getDeviceReturnInstallPictures())) {
            String[] deviceReturnInstallPic = detailEntity.getDeviceReturnInstallPictures().split(",");
            if (deviceReturnInstallPic.length >= 1) {
                picList5.add(BuildConfig.OSS_SERVER + deviceReturnInstallPic[0]);
            }
            if (deviceReturnInstallPic.length >= 2) {
                picList5.add(BuildConfig.OSS_SERVER + deviceReturnInstallPic[1]);
            }
            if (deviceReturnInstallPic.length >= 3) {
                picList5.add(BuildConfig.OSS_SERVER + deviceReturnInstallPic[2]);
            }
        }
        if (!StringUtils.isEmpty(detailEntity.getRestorePictures())) {
            String[] restorePic = detailEntity.getRestorePictures().split(",");
            if (restorePic.length >= 1) {
                picList6.add(BuildConfig.OSS_SERVER + restorePic[0]);
            }
            if (restorePic.length >= 2) {
                picList6.add(BuildConfig.OSS_SERVER + restorePic[1]);
            }
            if (restorePic.length >= 3) {
                picList6.add(BuildConfig.OSS_SERVER + restorePic[2]);
            }
        }

    }

    private void initNinePhoto() {
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        snplMonitorAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_2, REQUEST_CODE_PHOTO_PREVIEW_2));
        snplToolsPackageAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_3, REQUEST_CODE_PHOTO_PREVIEW_3));
        //2017年7月21日
        snplAfterProcessingLocale.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_4, REQUEST_CODE_PHOTO_PREVIEW_4));
        snplMachineFitBack.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_5, REQUEST_CODE_PHOTO_PREVIEW_5));
        snplFailureRecoverPhenomena.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_6, REQUEST_CODE_PHOTO_PREVIEW_6));

        snplMomentAddPhotos.setData(picList1);
        snplMonitorAddPhotos.setData(picList2);
        snplToolsPackageAddPhotos.setData(picList3);
        //2017年7月21日
        snplAfterProcessingLocale.setData(picList4);
        snplMachineFitBack.setData(picList5);
        snplFailureRecoverPhenomena.setData(picList6);
    }


    private void submit() {

        RepairFailureEntity repairFailureEntity = new RepairFailureEntity();
        repairFailureEntity.setId(failureId);
        repairFailureEntity.setBusinessThreeCode(failureEntity.getBusinessThreeCode());
        repairFailureEntity.setModelCode(failureEntity.getModelCode());
        detailEntity.setBusRepairFailureId(failureId);
        //故障描述
        repairFailureEntity.setBugDescription(etTroubleDesc.getText().toString().trim());
        repairFailureEntity.setIsMisinformation(GetConstDataUtils.getRepairMisinformationList().indexOf(tvRepairMisinformation.getText().toString().trim()));
        detailEntity.setFailureEntity(repairFailureEntity);

        detailEntity.setCause(etTroubleReason.getText().toString().trim());
        detailEntity.setHandle(etTroubleDeal.getText().toString().trim());
        detailEntity.setCheckProcess(etTroublePoint.getText().toString().trim());
        //维修结果
        detailEntity.setStatus(GetConstDataUtils.getBugDetailList().indexOf(tvRepairConclusion.getText().toString().trim()));
//
        //故障表象 （3张）
        String presentationPic = PhotoUtils.getPhotoUrl(snplMomentAddPhotos, uploadMap, true);
        detailEntity.setPresentationPictures(presentationPic);

        //工具及蓝布 （3张）
        String toolPic = PhotoUtils.getPhotoUrl(snplMonitorAddPhotos, uploadMap, false);
        detailEntity.setToolPictures(toolPic);

        //故障点照片 （3张）
        String pointPic = PhotoUtils.getPhotoUrl(snplToolsPackageAddPhotos, uploadMap, false);
        detailEntity.setPointPictures(pointPic);

        //处理后现场 （3张）
        String afterHandlePic = PhotoUtils.getPhotoUrl(snplAfterProcessingLocale, uploadMap, false);
        detailEntity.setAfterHandlePictures(afterHandlePic);

        //设备回装 （3张）
        String deviceReturnInstallPic = PhotoUtils.getPhotoUrl(snplMachineFitBack, uploadMap, false);
        detailEntity.setDeviceReturnInstallPictures(deviceReturnInstallPic);

        //恢复后表象 （3张）
        String restorePic = PhotoUtils.getPhotoUrl(snplFailureRecoverPhenomena, uploadMap, false);

        detailEntity.setBusBughandleConfirmId(confirmId);
        detailEntity.setRestorePictures(restorePic);


        if (uploadMap.size() != 0) {

            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        doHttpSubmit();
                    });

                }
            });
            return;
        }
        doHttpSubmit();

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_PHOTO_1:
                snplMomentAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_2:
                snplMonitorAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_3:
                snplToolsPackageAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            //2017年7月21日
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snplAfterProcessingLocale.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_5:
                snplMachineFitBack.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_6:
                snplFailureRecoverPhenomena.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snplMomentAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_2:
                snplMonitorAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_3:
                snplToolsPackageAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_4:
                snplAfterProcessingLocale.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_5:
                snplMachineFitBack.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_6:
                snplFailureRecoverPhenomena.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case 10009:
                BughandleUseDeviceEntity bugBean = (BughandleUseDeviceEntity) data.getSerializableExtra("bean");
                detailEntity.getUseDeviceEntityList().add(bugBean);
                materialAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

}

