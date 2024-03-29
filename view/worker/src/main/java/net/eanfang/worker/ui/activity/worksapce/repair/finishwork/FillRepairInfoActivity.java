package net.eanfang.worker.ui.activity.worksapce.repair.finishwork;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.biz.model.QueryEntry;
import com.eanfang.biz.model.bean.TemplateBean;
import com.eanfang.biz.model.entity.BughandleConfirmEntity;
import com.eanfang.biz.model.entity.BughandleDetailEntity;
import com.eanfang.biz.model.entity.RepairFailureEntity;
import com.eanfang.config.Config;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.takevideo.TakeVdideoMode;
import com.eanfang.takevideo.TakeVideoActivity;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.LocationUtil;
import com.eanfang.util.PhotoUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.PutUpOrderActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.AddTroubleActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.finishwork.faultdetail.AddTroubleDetailActivity;
import net.eanfang.worker.ui.adapter.FillTroubleDetailAdapter;
import net.eanfang.worker.ui.adapter.RepairTeamWorkerAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.widget.NewOrderAddTroubleDevicesDialog;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:06
 * @email houzhongzhou@yeah.net
 * @desc 技师完善故障处理
 */

public class FillRepairInfoActivity extends BaseWorkerActivity {

    public static final int REQUEST_CODE_UPDATE_TROUBLE = 10001;
    public static final int REQUEST_CODE_DEAL_TROUBLE = 10002;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_2 = 2;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_3 = 3;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_4 = 4;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 101;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_2 = 102;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_3 = 103;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_4 = 104;
    private static final Integer WORKER_ADD_TROUBLE_CALLBACK = 2;
    private final Activity activity = this;
    @BindView(R.id.rv_trouble)
    RecyclerView rvTrouble;
    /**
     * 遗留问题
     */
    @BindView(R.id.et_remain_question)
    EditText etRemainQuestion;
    /**
     * 协助人员
     */
    /**
     * 电视墙/操作台正面全貌 (3张)
     */
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    @BindView(R.id.tv_addvideo_moment)
    TextView tvAddvideoMoment;
    @BindView(R.id.iv_thumbnail_moment)
    ImageView ivThumbnailMoment;
    @BindView(R.id.rl_thumbnail_moment)
    RelativeLayout rlThumbnailMoment;
    /**
     * 视频上传路径
     */
    private String mUploadKey_moment = "";

    /**
     * 电视墙/操作台背面全照(3张)
     */
    @BindView(R.id.snpl_monitor_add_photos)
    BGASortableNinePhotoLayout snplMonitorAddPhotos;
    @BindView(R.id.tv_addvideo_monitor)
    TextView tvAddvideoMonitor;
    @BindView(R.id.iv_thumbnail_monitor)
    ImageView ivThumbnailMonitor;
    @BindView(R.id.rl_thumbnail_monitor)
    RelativeLayout rlThumbnailMonitor;
    /**
     * 视频上传路径
     */
    private String mUploadKey_monitor = "";
    /**
     * 机柜正面/背面 (3张)
     */
    @BindView(R.id.snpl_tools_package_add_photos)
    BGASortableNinePhotoLayout snplToolsPackageAddPhotos;
    @BindView(R.id.tv_addvideo_package)
    TextView tvAddvideoPackage;
    @BindView(R.id.iv_thumbnail_tools_package)
    ImageView ivThumbnailToolsPackage;
    @BindView(R.id.rl_thumbnail_tools_package)
    RelativeLayout rlThumbnailToolsPackage;
    /**
     * 视频上传路径
     */
    private String mUploadKey_package = "";
    /**
     * 单据照片 (3张)
     */
    @BindView(R.id.snpl_form_photos)
    BGASortableNinePhotoLayout snplFormPhotos;
    @BindView(R.id.ll_form_pic)
    LinearLayout llFormPic;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.tv_up)
    TextView tvUp;
    /**
     * 录像存储权限
     */
    @BindView(R.id.tv_videoStorage)
    TextView tvVideoStorage;
    /**
     * 增加团队成员
     */
    @BindView(R.id.tv_add_group)
    TextView tvAddGroup;
    // 遗留问题
    @BindView(R.id.iv_voice_input_remain_question)
    ImageView ivVoiceInputRemainQuestion;
    @BindView(R.id.tv_num)
    TextView tvNum;


    private boolean isVideoStroage = false;
    /**
     * 所有设备时间同步
     */
    @BindView(R.id.tv_deviceTime)
    TextView tvDeviceTime;
    private boolean isDeviceTime = false;
    /**
     * 报警打印机
     */
    @BindView(R.id.tv_policePriter)
    TextView tvPolicePriter;
    private boolean isPolicePriter = false;
    /**
     * 各类设备数据远传功能
     */
    @BindView(R.id.tv_policeDeliver)
    TextView tvPoliceDeliver;
    private boolean isPoliceDeliver = false;
    //团队成员
    @BindView(R.id.rv_team_worker)
    RecyclerView rvTeamWorker;
    // 添加故障
    @BindView(R.id.tv_add_fault)
    TextView tvAddFault;

    private List<BughandleDetailEntity> DetailEntityList;
    private FillTroubleDetailAdapter fillTroubleDetailAdapter;
    private HashMap<String, String> uploadMap = new HashMap<>();
    private String companyName;
    private Long companyId;
    private BughandleConfirmEntity bughandleConfirmEntity;
    private Long orderId;
    private List<String> businessIdLis;

    private int mInspect = 1;

    // 地址
    private String mAddress = "";
    private double mSignOutLongitude;
    private double mSignOutLatitude;

    private String mAddressCode = "";
    // 团队成员
    List<TemplateBean.Preson> mPresonList = new ArrayList<>();
    private RepairTeamWorkerAdapter repairTeamWorkerAdapter;

    private int maxWordsNum = 50; //输入限制的最大字数e

    private Long clientCompanyUid;


    /**
     * 选择故障设备
     */
    private NewOrderAddTroubleDevicesDialog newOrderAddTroubleDevicesDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_fill_repair_info);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initNinePhoto();
        setListener();
    }

    private void initView() {
        setTitle("填写信息");
        setLeftBack();
        rvTrouble.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvTrouble.setLayoutManager(new LinearLayoutManager(this));
        rvTrouble.setNestedScrollingEnabled(false);
        rvTrouble.setHasFixedSize(false);
        //团队成员
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTeamWorker.setLayoutManager(layoutManager);
    }

    private void initData() {
        orderId = getIntent().getLongExtra("orderId", 0);
        companyName = getIntent().getStringExtra("companyName");
        companyId = getIntent().getLongExtra("companyUid", 0);
        clientCompanyUid = getIntent().getLongExtra("clientCompanyUid", 0);
        // 获取经纬度
        LocationUtil.location(this, (location) -> {
            // mAddress = location.getCity() + location.getDistrict();
            mAddress = location.getAddress().replace(location.getCity(), "").replace(location.getDistrict(), "");
            if (!mAddress.contains("(")) {
                mAddress += " (" + location.getDescription() + ")";
            }
            mSignOutLatitude = location.getLatitude();
            mSignOutLongitude = location.getLongitude();
            mAddressCode = Config.get().getAreaCodeByName(location.getCity(), location.getDistrict());

            runOnUiThread(() -> {
                if (mSignOutLatitude <= 0 || mSignOutLongitude <= 0) {
                    showToast("获取定位信息失败，请检查定位或返回后重试。");
                }
            });
        });

        repairTeamWorkerAdapter = new RepairTeamWorkerAdapter(R.layout.layout_team_worker_item);
        repairTeamWorkerAdapter.bindToRecyclerView(rvTeamWorker);
        repairTeamWorkerAdapter.setNewData(mPresonList);

        doHttpOrderDetail(orderId);
    }

    @SuppressLint("ResourceAsColor")
    public void setListener() {
        //添加故障
        tvAddFault.setOnClickListener(v -> {
            newOrderAddTroubleDevicesDialog = new NewOrderAddTroubleDevicesDialog(FillRepairInfoActivity.this, new NewOrderAddTroubleDevicesDialog.OnSelectListener() {
                @Override
                public void onDeviceScan() {
                    Intent intent = new Intent(FillRepairInfoActivity.this, AddTroubleActivity.class);
                    intent.putExtra("repaid", orderId);
                    intent.putExtra("clientCompanyUid", clientCompanyUid);
                    startActivityForResult(intent, 10003);
                }

                @Override
                public void onDeviceHouse() {
                showToast("设备库");
                }

                @Override
                public void onDeviceInput() {
                    showToast("手动输入");
                }
            });

            newOrderAddTroubleDevicesDialog.show();
        });
        //提交完工
        tvCommit.setOnClickListener(new MultiClickListener(this, this::checkInfo, () -> {
            new TrueFalseDialog(activity, "系统提示", "是否确定提交完工？", () -> {
                fillBean();
                submit();
            }).showDialog();
        }));
        //挂单
        tvUp.setOnClickListener(new MultiClickListener(this, this::checkInfo, () -> {
            new TrueFalseDialog(activity, "系统提示", "是否确定转单？", () -> {
                fillBean();
                putUpOrder();
            }).showDialog();
        }));
        // 录像存储权限
        tvVideoStorage.setOnClickListener(v -> {
            if (!isVideoStroage) {
                isVideoStroage = true;
                tvVideoStorage.setBackgroundResource(R.drawable.bg_trouble_type_pressed);
                tvVideoStorage.setTextColor(ContextCompat.getColor(this, R.color.color_white));
            } else {
                isVideoStroage = false;
                tvVideoStorage.setBackgroundResource(R.drawable.bg_trouble_type);
                tvVideoStorage.setTextColor(ContextCompat.getColor(this, R.color.color_client_neworder));
            }
        });
        // 所有设备时间同步
        tvDeviceTime.setOnClickListener(v -> {
            if (!isDeviceTime) {
                isDeviceTime = true;
                tvDeviceTime.setBackgroundResource(R.drawable.bg_trouble_type_pressed);
                tvDeviceTime.setTextColor(ContextCompat.getColor(this, R.color.color_white));
            } else {
                isDeviceTime = false;
                tvDeviceTime.setBackgroundResource(R.drawable.bg_trouble_type);
                tvDeviceTime.setTextColor(ContextCompat.getColor(this, R.color.color_client_neworder));
            }
        });
        // 报警打印机
        tvPolicePriter.setOnClickListener(v -> {
            if (!isPolicePriter) {
                isPolicePriter = true;
                tvPolicePriter.setBackgroundResource(R.drawable.bg_trouble_type_pressed);
                tvPolicePriter.setTextColor(ContextCompat.getColor(this, R.color.color_white));
            } else {
                isPolicePriter = false;
                tvPolicePriter.setBackgroundResource(R.drawable.bg_trouble_type);
                tvPolicePriter.setTextColor(ContextCompat.getColor(this, R.color.color_client_neworder));
            }
        });
        // 各类设备数据远传功能
        tvPoliceDeliver.setOnClickListener(v -> {
            if (!isPoliceDeliver) {
                isPoliceDeliver = true;
                tvPoliceDeliver.setBackgroundResource(R.drawable.bg_trouble_type_pressed);
                tvPoliceDeliver.setTextColor(ContextCompat.getColor(this, R.color.color_white));
            } else {
                isPoliceDeliver = false;
                tvPoliceDeliver.setBackgroundResource(R.drawable.bg_trouble_type);
                tvPoliceDeliver.setTextColor(ContextCompat.getColor(this, R.color.color_client_neworder));
            }
        });
        // 增加团队成员
        tvAddGroup.setOnClickListener((v) -> {
            Intent intent = new Intent(FillRepairInfoActivity.this, SelectOrganizationActivity.class);
            this.startActivity(intent);
        });
        repairTeamWorkerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TemplateBean.Preson preson = (TemplateBean.Preson) adapter.getData().get(position);
                adapter.getData().remove(preson);
                adapter.notifyDataSetChanged();
            }
        });

        // 遗留问题
        ivVoiceInputRemainQuestion.setOnClickListener((v) -> {
            RxPerm.get(this).voicePerm((isSuccess) -> {
                RecognitionManager.getSingleton().startRecognitionWithDialog(FillRepairInfoActivity.this, etRemainQuestion);
            });
        });
        etRemainQuestion.addTextChangedListener(new TextWatcher() {
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
                selectionStart = etRemainQuestion.getSelectionStart();
                selectionEnd = etRemainQuestion.getSelectionEnd();
                if (temp.length() > maxWordsNum) {
//                    toast("不能超过" + maxWordsNum + "个字");
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    etRemainQuestion.setText(s);
                    etRemainQuestion.setSelection(tempSelection); //设置光标在最后
                }
            }
        });

        /**
         *电视机墙正面照 拍摄视频
         * */
        tvAddvideoMoment.setOnClickListener((v -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            bundle.putString("worker_add", "moment");
            JumpItent.jump(FillRepairInfoActivity.this, TakeVideoActivity.class, bundle);
        }));
        /**
         *电视机墙背面照 拍摄视频
         * */
        tvAddvideoMonitor.setOnClickListener((v -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            bundle.putString("worker_add", "monitor");
            JumpItent.jump(FillRepairInfoActivity.this, TakeVideoActivity.class, bundle);
        }));
        /**
         *机柜照 拍摄视频
         * */
        tvAddvideoPackage.setOnClickListener((v -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            bundle.putString("worker_add", "package");
            JumpItent.jump(FillRepairInfoActivity.this, TakeVideoActivity.class, bundle);
        }));

    }


    private void doHttpOrderDetail(Long orderId) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("repairId", orderId + "");
        EanfangHttp.post(RepairApi.POST_BUGHANDLE_PREPARE)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<BughandleConfirmEntity>(this, true, BughandleConfirmEntity.class, (bean) -> {
                    bughandleConfirmEntity = bean;
                    initAdapter();
                }));
    }

    private void initAdapter() {
        DetailEntityList = bughandleConfirmEntity.getDetailEntityList();

        businessIdLis = Stream.of(DetailEntityList).map(bean -> Config.get().getBusinessIdByCode(bean.getFailureEntity().getBusinessThreeCode(), 3) + "").toList();
        fillTroubleDetailAdapter = new FillTroubleDetailAdapter(R.layout.layout_trouble_detail, DetailEntityList);
        rvTrouble.setAdapter(fillTroubleDetailAdapter);
        fillTroubleDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(FillRepairInfoActivity.this, AddTroubleDetailActivity.class);
                intent.putExtra("confirmId", bughandleConfirmEntity.getId());
                intent.putExtra("failureId", fillTroubleDetailAdapter.getData().get(position).getBusRepairFailureId());
                intent.putExtra("position", position);
                intent.putExtra("picture", fillTroubleDetailAdapter.getData().get(position).getFailureEntity().getPictures());
                startActivityForResult(intent, REQUEST_CODE_UPDATE_TROUBLE);
                //((BaseViewHolder) rvTrouble.getChildViewHolder(rvTrouble.getChildAt(position))).setText(R.id.tv_detai_status, "");
            }
        });
    }

    private void initNinePhoto() {
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        snplMonitorAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_2, REQUEST_CODE_PHOTO_PREVIEW_2));
        snplToolsPackageAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_3, REQUEST_CODE_PHOTO_PREVIEW_3));
        snplFormPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_4, REQUEST_CODE_PHOTO_PREVIEW_4));
    }

    /**
     * 判断是否填写完毕
     */
    private boolean checkInfo() {

        String remainQuestion = etRemainQuestion.getText().toString().trim();
        if (StrUtil.isEmpty(remainQuestion)) {
            showToast("请填写遗留问题");
            return false;
        }
        if (DetailEntityList != null && DetailEntityList.size() > 0) {
            //增加限制，需要先完善故障处理 在提交
            for (int i = 0; i < DetailEntityList.size(); i++) {
                if (StrUtil.isEmpty(DetailEntityList.get(i).getCheckProcess())) {
                    showToast("请完善第" + (i + 1) + "条故障处理明细");
                    return false;
                }
            }
        } else {
            showToast("请至少添加一条故障处理明细");
            return false;
        }

        return true;
    }

    private BughandleConfirmEntity fillBean() {

        bughandleConfirmEntity.setBusRepairOrderId(orderId);

        // 完成时间 维修工时  根据当前时间进行计算 并提交
        bughandleConfirmEntity.setOverTime(DateUtil.date());
        long day = DateUtil.date().between(bughandleConfirmEntity.getSingInTime(), DateUnit.DAY);
        long hours = DateUtil.date().between(bughandleConfirmEntity.getSingInTime(), DateUnit.HOUR);
        long minutes = DateUtil.date().between(bughandleConfirmEntity.getSingInTime(), DateUnit.MINUTE);
        if (day < 0) {
            day = 0;
        }
        if (hours < 0) {
            hours = 0;
        }
        if (minutes < 0) {
            minutes = 0;
        }
//        if (DateUtil.date(bughandleConfirmEntity.getSingInTime()) == null) {
//            bughandleConfirmEntity.setWorkHour("0小时0分钟");
//        } else
        {
            bughandleConfirmEntity.setWorkHour((day * 24 + hours) + "小时" + minutes + "分钟");
        }

        if (isVideoStroage) {
            bughandleConfirmEntity.setStoreDays(mInspect);
        }
        if (isPoliceDeliver) {
            bughandleConfirmEntity.setIsMachineDataRemote(mInspect);
        }
        if (isPolicePriter) {
            bughandleConfirmEntity.setIsAlarmPrinter(mInspect);
        }
        if (isDeviceTime) {
            bughandleConfirmEntity.setIsTimeRight(mInspect);
        }

        bughandleConfirmEntity.setLeftoverProblem(etRemainQuestion.getText().toString().trim());
        //协作人员
//        bughandleConfirmEntity.setTeamWorker(etTeamWorker.getText().toString().trim());
//        uploadMap.clear();
        //电视墙/操作台正面全貌 （3张）
        String presentationPic = PhotoUtils.getPhotoUrl("biz/repair/bughandle/", snplMomentAddPhotos, uploadMap, true);
        bughandleConfirmEntity.setFrontPictures(presentationPic);

        //电视墙/操作台背面面全貌 （3张）
        String toolPic = PhotoUtils.getPhotoUrl("biz/repair/bughandle/", snplMonitorAddPhotos, uploadMap, false);
        bughandleConfirmEntity.setReverseSidePictures(toolPic);

        //机柜正面/背面 （3张）
        String pointPic = PhotoUtils.getPhotoUrl("biz/repair/bughandle/", snplToolsPackageAddPhotos, uploadMap, false);
        bughandleConfirmEntity.setEquipmentCabinetPictures(pointPic);

        //单据照片 （3张）
        String afterHandlePic = PhotoUtils.getPhotoUrl("biz/repair/bughandle/", snplFormPhotos, uploadMap, false);
        bughandleConfirmEntity.setInvoicesPictures(afterHandlePic);

        // 签退时间
        bughandleConfirmEntity.setSignOutTime(new Date(System.currentTimeMillis()));
        //签退地点
        bughandleConfirmEntity.setSignOutAddress(mAddress);
        bughandleConfirmEntity.setSignOutCode(mAddressCode);
        bughandleConfirmEntity.setSignOutLongitude(mSignOutLongitude + "");
        bughandleConfirmEntity.setSignOutLatitude(mSignOutLatitude + "");
        //电视机正面照 短视频
        bughandleConfirmEntity.setFront_mp4_path(mUploadKey_moment);
        //电视机背面照 短视频
        bughandleConfirmEntity.setReverse_side_mp4_path(mUploadKey_monitor);
        // 机柜 短视频
        bughandleConfirmEntity.setEquipment_cabinet_mp4_path(mUploadKey_package);


        //添加合作成员
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < repairTeamWorkerAdapter.getData().size(); i++) {
            TemplateBean.Preson preson = repairTeamWorkerAdapter.getData().get(i);
            if (i == repairTeamWorkerAdapter.getData().size() - 1) {
                stringBuilder.append(preson.getProtraivat() + "-" + preson.getName());
            } else {
                stringBuilder.append(preson.getProtraivat() + "-" + preson.getName() + ",");
            }
        }
        bughandleConfirmEntity.setTeamWorker(String.valueOf(stringBuilder));

        return bughandleConfirmEntity;
    }

    //提交完工
    private void submit() {
        if (uploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(uploadMap, (isSuccess) -> {
                runOnUiThread(() -> {
                    //代表不需要挂单Z
                    String requestJson = JSONObject.toJSONString(bughandleConfirmEntity);
                    doHttp(requestJson);
                });
            });
            return;
        } else {
            //代表不需要挂单Z
            String requestJson = JSONObject.toJSONString(bughandleConfirmEntity);
            doHttp(requestJson);
        }

    }

    /**
     * 挂单
     */
    private void putUpOrder() {

        if (uploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(uploadMap, (isSuccess) -> {
                doJumpValue();
            });
            return;
        }
        doJumpValue();

    }

    public void doJumpValue() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bughandleConfirmEntity);
        bundle.putString("companyName", companyName);
        bundle.putLong("companyId", companyId);
        bundle.putLong("orderId", bughandleConfirmEntity.getBusRepairOrderId());
        bundle.putStringArrayList("businessId", (ArrayList<String>) businessIdLis);
        JumpItent.jump(FillRepairInfoActivity.this, PutUpOrderActivity.class, bundle);

    }

    /**
     * 完工网络请求
     */
    private void doHttp(String jsonString) {
        EanfangHttp.post(RepairApi.POST_REPAIR_FINISH_WORK)
                .upJson(jsonString)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    showToast("提交成功");
                    setResult(RESULT_OK);
                    finishSelf();
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
                snplMomentAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_2:
                snplMonitorAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_3:
                snplToolsPackageAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snplFormPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snplMomentAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_2:
                snplMonitorAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_3:
                snplToolsPackageAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;

            case REQUEST_CODE_PHOTO_PREVIEW_4:
                snplFormPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;
            // 完善故障明细
            case REQUEST_CODE_UPDATE_TROUBLE:
                BughandleDetailEntity resultBean = (BughandleDetailEntity) data.getSerializableExtra("bean");
                if (resultBean == null) {
                    return;
                }
                int position = data.getIntExtra("position", 0);
                DetailEntityList.remove(position);
                fillTroubleDetailAdapter.notifyItemRemoved(position);
                DetailEntityList.add(resultBean);
                fillTroubleDetailAdapter.notifyItemInserted(fillTroubleDetailAdapter.getData().size() - 1);
                fillTroubleDetailAdapter.notifyDataSetChanged();
//                fillTroubleDetailAdapter.notifyItemRangeChanged(position, fillTroubleDetailAdapter.getData().size()-1);
                break;
            // 添加故障明细
            case 10003:
                if (data.getSerializableExtra("bean") == null) {
                    return;
                }
                RepairFailureEntity bugBean = (RepairFailureEntity) data.getSerializableExtra("bean");
                BughandleDetailEntity bughandleDetailEntity = new BughandleDetailEntity();
                bughandleDetailEntity.setBusRepairFailureId(bugBean.getId());
                bughandleDetailEntity.setFailureEntity(bugBean);
                DetailEntityList.add(bughandleDetailEntity);
                fillTroubleDetailAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            new TrueFalseDialog(this, "系统提示", "是否放弃完善故障处理？", () -> {
                finish();
            }).showDialog();
        }
        return false;
    }

    @Subscribe
    public void onEventAddTroublePic(List<TemplateBean.Preson> personList) {
        for (int i = 0; i < personList.size(); i++) {
            int index = 0;
            if (mPresonList.size() <= 0) {
                mPresonList.add(personList.get(i));
            }
            for (int j = 0; j < mPresonList.size(); j++, index++) {
                if (mPresonList.get(j).getId().equals(personList.get(i).getId())) {
                    index = 0;
                    break;
                }
            }
            if (index != 0) {
                mPresonList.add(personList.get(i));
            }
        }
        repairTeamWorkerAdapter.notifyDataSetChanged();
    }

    @Subscribe()//MAIN代表主线程
    public void receivePath(TakeVdideoMode takeVdideoMode) {
        if (takeVdideoMode != null) {
            String image = takeVdideoMode.getMImagePath();
            if (takeVdideoMode.getMType().equals("moment")) {// 电视正面照
                rlThumbnailMoment.setVisibility(View.VISIBLE);
                mUploadKey_moment = takeVdideoMode.getMKey();
                ivThumbnailMoment.setImageBitmap(PhotoUtils.getVideoBitmap(image));
            } else if (takeVdideoMode.getMType().equals("monitor")) {
                rlThumbnailMonitor.setVisibility(View.VISIBLE);
                mUploadKey_monitor = takeVdideoMode.getMKey();
                ivThumbnailMonitor.setImageBitmap(PhotoUtils.getVideoBitmap(image));
            } else if (takeVdideoMode.getMType().equals("package")) {
                rlThumbnailToolsPackage.setVisibility(View.VISIBLE);
                mUploadKey_package = takeVdideoMode.getMKey();
                ivThumbnailToolsPackage.setImageBitmap(PhotoUtils.getVideoBitmap(image));
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (newOrderAddTroubleDevicesDialog != null) {
            newOrderAddTroubleDevicesDialog.dismiss();
        }
    }
}

