package net.eanfang.worker.ui.activity.worksapce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.bigkoo.pickerview.OptionsPickerView;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.device.GetDeviceFailureOptionBean;
import com.eanfang.model.device.GetDeviceFailureSolutionOptionBean;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:24
 * @email houzhongzhou@yeah.net
 * @desc
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
    //2017年9月29日
    @BindView(R.id.ll_device_failure_solution)
    LinearLayout ll_device_failure_solution;
    @BindView(R.id.tv_device_failure_solution)
    TextView tv_device_failure_solution;
    @BindView(R.id.ll_deviceFailure)
    LinearLayout ll_deviceFailure;
    private Context context = this;
    private TextView tv_trouble_device;
    private RelativeLayout rl_trouble_device;
    private TextView tv_brand_model;
    private RelativeLayout rl_brand_model;
    private TextView tv_device_no;
    private RelativeLayout rl_device_no;
    private TextView tv_device_location;
    private RelativeLayout rl_device_location;
    private TextView tv_add;
    private RecyclerView rcy_parameter;
    private EditText et_trouble_desc;
    private EditText et_trouble_point;
    private EditText et_trouble_reason;
    private EditText et_trouble_deal;
    /**
     * 故障表象 （3张）
     */
    private BGASortableNinePhotoLayout snpl_moment_add_photos;
    /**
     * 工具及蓝布 （3张）
     */
    private BGASortableNinePhotoLayout snpl_monitor_add_photos;
    /**
     * 故障点照片 （3张）
     */
    private BGASortableNinePhotoLayout snpl_tools_package_add_photos;
    /**
     * 处理后现场 （3张）
     */
    private BGASortableNinePhotoLayout snpl_after_processing_locale;
    /**
     * 设备回装 （3张）
     */
    private BGASortableNinePhotoLayout snpl_machine_fit_back;
    /**
     * 故障恢复后表象 （3张）
     */
    private BGASortableNinePhotoLayout snpl_failure_recover_phenomena;

    private TextView tv_add_consumable;
    private RecyclerView rcy_consumable;
    private Button btn_add_trouble;
    private BughandleDetailEntity bean;
    private TextView tv_trouble_title;
    private OptionsPickerView pvOptions;
    private ParamAdapter paramAdapter;
    private HashMap<String, String> uploadMap;

    //2017年7月20日
    //维修结果
    private LinearLayout ll_repair_conclusion;
    private TextView tv_repair_conclusion;
    //选择器
    private OptionsPickerView pvOptions_NoLink4;

    /**
     * 故障表象 （3张）
     */
    private ArrayList<String> picList1;
    /**
     * 工具及蓝布 （3张）
     */
    private ArrayList<String> picList2;
    /**
     * 故障点照片 （3张）
     */
    private ArrayList<String> picList3;
    //2017年7月21日
    /**
     * 处理后现场 （3张）
     */
    private ArrayList<String> picList4;
    /**
     * 设备回装 （3张）
     */
    private ArrayList<String> picList5;
    /**
     * 故障恢复后表象 （3张）
     */
    private ArrayList<String> picList6;
    private int position;
    private String bugOneCode;
    private MaterialAdapter materialAdapter;
    //2017年7月24日
    private String companyName;
    private Long id;

    private GetDeviceFailureSolutionOptionBean solutionOptionBean;
    private GetDeviceFailureOptionBean failureOptionBean;
    private RepairFailureEntity repairFailureEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble_detail);
        ButterKnife.bind(this);
        initView();
        initData();

        initAdapter();

        lookFailureDetail();

        fillData();

        initListener();
        initImgUrlList(bean);
        initNinePhoto();

    }

    private void lookFailureDetail() {

        EanfangHttp.get(RepairApi.GET_FAILURE_DETAIL)
                .params("id", id)
                .execute(new EanfangCallback<RepairFailureEntity>(this, true, RepairFailureEntity.class, (bean) -> {
                    repairFailureEntity = bean;
                }));
    }

    private void initView() {
        tv_trouble_device = (TextView) findViewById(R.id.tv_trouble_device);
        rl_trouble_device = (RelativeLayout) findViewById(R.id.rl_trouble_device);
        tv_brand_model = (TextView) findViewById(R.id.tv_brand_model);
        rl_brand_model = (RelativeLayout) findViewById(R.id.rl_brand_model);
        tv_device_no = (TextView) findViewById(R.id.tv_device_no);
        rl_device_no = (RelativeLayout) findViewById(R.id.rl_device_no);
        tv_device_location = (TextView) findViewById(R.id.tv_device_location);
        rl_device_location = (RelativeLayout) findViewById(R.id.rl_device_location);
        tv_add = (TextView) findViewById(R.id.tv_add);
        rcy_parameter = (RecyclerView) findViewById(R.id.rcy_parameter);
        et_trouble_desc = (EditText) findViewById(R.id.et_trouble_desc);
        et_trouble_point = (EditText) findViewById(R.id.et_trouble_point);
        et_trouble_reason = (EditText) findViewById(R.id.et_trouble_reason);
        et_trouble_deal = (EditText) findViewById(R.id.et_trouble_deal);
        //2017年7月20日
        //维修结论
        ll_repair_conclusion = (LinearLayout) findViewById(R.id.ll_repair_conclusion);
        tv_repair_conclusion = (TextView) findViewById(R.id.tv_repair_conclusion);
        snpl_moment_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_moment_add_photos);
        snpl_monitor_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_monitor_add_photos);
        snpl_tools_package_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_tools_package_add_photos);

        //2017年7月21日
        snpl_after_processing_locale = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_after_processing_locale);
        snpl_machine_fit_back = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_machine_fit_back);
        snpl_failure_recover_phenomena = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_failure_recover_phenomena);


        tv_add_consumable = (TextView) findViewById(R.id.tv_add_consumable);

        rcy_consumable = (RecyclerView) findViewById(R.id.rcy_consumable);


        btn_add_trouble = (Button) findViewById(R.id.btn_add_trouble);


        tv_trouble_title = (TextView) findViewById(R.id.tv_trouble_title);
//        tv_trouble_title.setOnClickListener(this);

        supprotToolbar();
        setTitle("故障明细");
    }

    private void initData() {
        companyName = getIntent().getStringExtra("companyName");
        bean = (BughandleDetailEntity) getIntent().getSerializableExtra("bean");
        position = getIntent().getIntExtra("position", 0);
        id = getIntent().getLongExtra("id", 0);
//        bugBean = (BusinessWorkBean) getIntent().getSerializableExtra("bugBean");
//        bugOneCode = getIntent().getStringExtra("bugOneCode");
        //参数


        if (bean.getParamEntityList() == null) {
            List<BughandleParamEntity> list = new ArrayList<>();
            bean.setParamEntityList(list);
        }
        if (bean.getUseDeviceEntityList() == null) {
            List<BughandleUseDeviceEntity> list = new ArrayList<>();
            bean.setUseDeviceEntityList(list);
        }

    }


    private void initListener() {
        ll_deviceFailure.setOnClickListener((v) -> {
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

        ll_device_failure_solution.setOnClickListener((v) -> {
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

        ll_repair_conclusion.setOnClickListener(v -> {
            showRepairConslusion();
        });
        tv_add.setOnClickListener(v -> {
            showOptions();
        });

//        btn_add_trouble.setOnClickListener(new MultiClickListener(this, this::checkInfo, this::submit));
        btn_add_trouble.setOnClickListener(v -> submit());

        tv_add_consumable.setOnClickListener(v -> {
            Intent intent = new Intent(AddTroubleDetailActivity.this, AddMaterialActivity.class);
            Bundle bundle = new Bundle();
//                bundle.putSerializable("bugBean", bugBean);
            String bugOne = Config.get().getBusinessNameByCode(bean.getFailureEntity().getBusinessThreeCode(), 1);
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

    //

    /**
     * 初始化存储图片用的List集合
     */
    private void initImgUrlList(BughandleDetailEntity bean) {
        uploadMap = new HashMap<>();
        picList1 = new ArrayList<>();
        picList2 = new ArrayList<>();
        picList3 = new ArrayList<>();
        //2017年7月21日
        picList4 = new ArrayList<>();
        picList5 = new ArrayList<>();
        picList6 = new ArrayList<>();
        //修改小bug 图片读取问题
        if (!StringUtils.isEmpty(bean.getPresentationPictures())) {
            String[] presentationPic = bean.getPresentationPictures().split(",");
            if (presentationPic.length >= 1) {
                picList1.add(BuildConfig.OSS_SERVER + presentationPic[0]);
            }
            if (presentationPic.length >= 2) {
                picList1.add(BuildConfig.OSS_SERVER +presentationPic[1]);
            }
            if (presentationPic.length >= 3) {
                picList1.add(BuildConfig.OSS_SERVER +presentationPic[2]);
            }
        }

        if (!StringUtils.isEmpty(bean.getToolPictures())) {
            String[] toolPic = bean.getToolPictures().split(",");
            if (toolPic.length >= 1) {
                picList2.add(BuildConfig.OSS_SERVER+toolPic[0]);
            }
            if (toolPic.length >= 2) {
                picList2.add(BuildConfig.OSS_SERVER+toolPic[1]);
            }
            if (toolPic.length >= 3) {
                picList2.add(BuildConfig.OSS_SERVER+toolPic[2]);
            }
        }
        if (!StringUtils.isEmpty(bean.getPointPictures())) {
            String[] pointPic = bean.getPointPictures().split(",");
            if (pointPic.length >= 1) {
                picList3.add(BuildConfig.OSS_SERVER+pointPic[0]);
            }
            if (pointPic.length >= 2) {
                picList3.add(BuildConfig.OSS_SERVER+pointPic[1]);
            }
            if (pointPic.length >= 3) {
                picList3.add(BuildConfig.OSS_SERVER+pointPic[2]);
            }
        }
        if (!StringUtils.isEmpty(bean.getAfterHandlePictures())) {
            String[] afterHandlePic = bean.getAfterHandlePictures().split(",");
            if (afterHandlePic.length >= 1) {
                picList4.add(BuildConfig.OSS_SERVER+afterHandlePic[0]);
            }
            if (afterHandlePic.length >= 2) {
                picList4.add(BuildConfig.OSS_SERVER+afterHandlePic[1]);
            }
            if (afterHandlePic.length >= 3) {
                picList4.add(BuildConfig.OSS_SERVER+afterHandlePic[2]);
            }
        }
        if (!StringUtils.isEmpty(bean.getDeviceReturnInstallPictures())) {
            String[] deviceReturnInstallPic = bean.getDeviceReturnInstallPictures().split(",");
            if (deviceReturnInstallPic.length >= 1) {
                picList5.add(BuildConfig.OSS_SERVER+deviceReturnInstallPic[0]);
            }
            if (deviceReturnInstallPic.length >= 2) {
                picList5.add(BuildConfig.OSS_SERVER+deviceReturnInstallPic[1]);
            }
            if (deviceReturnInstallPic.length >= 3) {
                picList5.add(BuildConfig.OSS_SERVER+deviceReturnInstallPic[2]);
            }
        }
        if (!StringUtils.isEmpty(bean.getRestorePictures())) {
            String[] restorePic = bean.getRestorePictures().split(",");
            if (restorePic.length >= 1) {
                picList6.add(BuildConfig.OSS_SERVER+restorePic[0]);
            }
            if (restorePic.length >= 2) {
                picList6.add(BuildConfig.OSS_SERVER+restorePic[1]);
            }
            if (restorePic.length >= 3) {
                picList6.add(BuildConfig.OSS_SERVER+restorePic[2]);
            }
        }

    }

    private void initAdapter() {

        paramAdapter = new ParamAdapter(R.layout.item_parm, (ArrayList) bean.getParamEntityList());
        rcy_parameter.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcy_parameter.setLayoutManager(new LinearLayoutManager(this));
        rcy_parameter.setAdapter(paramAdapter);

        materialAdapter = new MaterialAdapter(R.layout.item_quotation_detail, bean.getUseDeviceEntityList());
        rcy_consumable.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcy_consumable.setLayoutManager(new LinearLayoutManager(this));
        rcy_consumable.setAdapter(materialAdapter);


    }

    private void showRepairConslusion() {
        PickerSelectUtil.singleTextPicker(this, "维修结论", tv_repair_conclusion, GetConstDataUtils.getBugDetailList());
    }

    public void fillData() {
        if (bean.getFailureEntity() != null) {
            if (StringUtils.isValid(bean.getFailureEntity().getBusinessThreeCode())) {
                String bugOne = Config.get().getBusinessNameByCode(bean.getFailureEntity().getBusinessThreeCode(), 1);
                String bugTwo = Config.get().getBusinessNameByCode(bean.getFailureEntity().getBusinessThreeCode(), 2);
                String bugThree = Config.get().getBusinessNameByCode(bean.getFailureEntity().getBusinessThreeCode(), 3);
                tv_trouble_title.setText(bugOne + "-" + bugTwo + "-" + bugThree);
            } else {
                tv_trouble_title.setText("");
            }

            tv_trouble_device.setText(Optional.ofNullable(bean.getFailureEntity().getDeviceName()).orElse(""));

            tv_brand_model.setText(Optional.ofNullable(Config.get().getModelNameByCode(bean.getFailureEntity().getModelCode(), 2)).orElse(""));

            tv_device_no.setText(Optional.ofNullable(bean.getFailureEntity().getDeviceNo()).orElse(""));

            tv_device_location.setText(Optional.ofNullable(bean.getFailureEntity().getBugPosition()).orElse(""));

            et_trouble_desc.setText(Optional.ofNullable(bean.getFailureEntity().getBugDescription()).orElse(""));
        }
        et_trouble_point.setText(Optional.ofNullable(bean.getCheckProcess()).orElse(""));
        et_trouble_reason.setText(Optional.ofNullable(bean.getCause()).orElse(""));
        et_trouble_deal.setText(Optional.ofNullable(bean.getHandle()).orElse(""));
        if (bean.getStatus() != null) {
            tv_repair_conclusion.setText(Optional.ofNullable(GetConstDataUtils.getBugDetailList().get(bean.getStatus())).orElse(""));
        }
    }

    private void submit() {

        bean.getFailureEntity().setBugDescription(et_trouble_desc.getText().toString().trim());
//        bean.getFailureEntity().setBusinessThreeCode(Config.get().getBusinessCodeByName(tv_trouble_title.getText().toString().trim(), 3));
//        bean.getFailureEntity().setDeviceNo(tv_device_no.getText().toString().trim());
//        bean.getFailureEntity().setBugPosition(tv_device_location.getText().toString().trim());
//        bean.getFailureEntity().setDeviceName("");
        bean.setCause(et_trouble_reason.getText().toString().trim());
        bean.setHandle(et_trouble_deal.getText().toString().trim());
        bean.setCheckProcess(et_trouble_point.getText().toString().trim());
        //维修结果
        bean.setStatus(GetConstDataUtils.getBugDetailList().indexOf(tv_repair_conclusion.getText().toString().trim()));
//
        //故障表象 （3张）
        String presentationPic = PhotoUtils.getPhotoUrl(snpl_moment_add_photos, uploadMap, true);
        bean.setPresentationPictures(presentationPic);

        //工具及蓝布 （3张）
        String toolPic = PhotoUtils.getPhotoUrl(snpl_monitor_add_photos, uploadMap, false);
        bean.setToolPictures(toolPic);

        //故障点照片 （3张）
        String pointPic = PhotoUtils.getPhotoUrl(snpl_tools_package_add_photos, uploadMap, false);
        bean.setPointPictures(pointPic);

        //处理后现场 （3张）
        String afterHandlePic = PhotoUtils.getPhotoUrl(snpl_after_processing_locale, uploadMap, false);
        bean.setAfterHandlePictures(afterHandlePic);

        //设备回装 （3张）
        String deviceReturnInstallPic = PhotoUtils.getPhotoUrl(snpl_machine_fit_back, uploadMap, false);
        bean.setDeviceReturnInstallPictures(deviceReturnInstallPic);

        //恢复后表象 （3张）
        String restorePic = PhotoUtils.getPhotoUrl(snpl_failure_recover_phenomena, uploadMap, false);
        bean.setRestorePictures(restorePic);

        if (uploadMap.size() != 0) {

            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        getIntent().putExtra("position", position);
                        getIntent().putExtra("bean", bean);
                        setResult(2000, getIntent());
                        finishSelf();
                    });

                }
            });
            return;
        }
        getIntent().putExtra("position", position);
        getIntent().putExtra("bean", bean);
        setResult(2000, getIntent());
        finishSelf();

    }


    private void showOptions() {
        PickerSelectUtil.singleTextPicker(this, "参数", GetConstDataUtils.getDeviceParamList(), (index, item) -> {
            BughandleParamEntity param = new BughandleParamEntity();
            param.setParamName(item);
            bean.getParamEntityList().add(param);
            paramAdapter.notifyDataSetChanged();

        });
    }

    private void initNinePhoto() {
        snpl_moment_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        snpl_monitor_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_2, REQUEST_CODE_PHOTO_PREVIEW_2));
        snpl_tools_package_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_3, REQUEST_CODE_PHOTO_PREVIEW_3));
        //2017年7月21日
        snpl_after_processing_locale.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_4, REQUEST_CODE_PHOTO_PREVIEW_4));
        snpl_machine_fit_back.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_5, REQUEST_CODE_PHOTO_PREVIEW_5));
        snpl_failure_recover_phenomena.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_6, REQUEST_CODE_PHOTO_PREVIEW_6));

        snpl_moment_add_photos.setData(picList1);
        snpl_monitor_add_photos.setData(picList2);
        snpl_tools_package_add_photos.setData(picList3);
        //2017年7月21日
        snpl_after_processing_locale.setData(picList4);
        snpl_machine_fit_back.setData(picList5);
        snpl_failure_recover_phenomena.setData(picList6);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_PHOTO_1:
                snpl_moment_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_2:
                snpl_monitor_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_3:
                snpl_tools_package_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            //2017年7月21日
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snpl_after_processing_locale.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_5:
                snpl_machine_fit_back.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_6:
                snpl_failure_recover_phenomena.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snpl_moment_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_2:
                snpl_monitor_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_3:
                snpl_tools_package_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_4:
                snpl_after_processing_locale.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_5:
                snpl_machine_fit_back.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_6:
                snpl_failure_recover_phenomena.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case 10009:
                BughandleUseDeviceEntity bugBean = (BughandleUseDeviceEntity) data.getSerializableExtra("bean");
                bean.getUseDeviceEntityList().add(bugBean);
                materialAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

}

