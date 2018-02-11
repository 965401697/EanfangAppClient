package net.eanfang.worker.ui.activity.worksapce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleConfirmEntity;
import com.yaf.base.entity.BughandleDetailEntity;
import com.yaf.base.entity.RepairFailureEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.FillTroubleDetailAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_trouble)
    RecyclerView rvTrouble;
    @BindView(R.id.tv_over_time)
    TextView tvOverTime;
    @BindView(R.id.ll_over_time)
    LinearLayout llOverTime;
    @BindView(R.id.et_repair_time)
    TextView etRepairTime;
    @BindView(R.id.ll_repair_time)
    LinearLayout llRepairTime;
    /**
     * 录像机天数
     */
    @BindView(R.id.tv_store_time)
    TextView tvStoreTime;
    @BindView(R.id.ll_store_time)
    LinearLayout llStoreTime;
    /**
     * 报警打印机
     */
    @BindView(R.id.tv_print_on_alarm)
    TextView tvPrintOnAlarm;
    @BindView(R.id.ll_print_on_alarm)
    LinearLayout llPrintOnAlarm;
    /*
    * 所有设备时间同步
    */
    @BindView(R.id.tv_time_right)
    TextView tvTimeRight;
    @BindView(R.id.ll_time_right)
    LinearLayout llTimeRight;
    /*
    * 各类设备数据远传功能
    */
    @BindView(R.id.tv_machine_data_remote)
    TextView tvMachineDataRemote;
    @BindView(R.id.ll_machine_data_remote)
    LinearLayout llMachineDataRemote;
    /*
  * 遗留问题
  */
    @BindView(R.id.et_remain_question)
    EditText etRemainQuestion;
    @BindView(R.id.ll_remain_question)
    LinearLayout llRemainQuestion;
    /*
    * 协助人员
    */
    @BindView(R.id.et_team_worker)
    EditText etTeamWorker;
    @BindView(R.id.ll_team_worker)
    LinearLayout llTeamWorker;
    /*
    * 电视墙/操作台正面全貌 (3张)
    */
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    @BindView(R.id.ll_tv_bg)
    LinearLayout llTvBg;
    /*
   * 电视墙/操作台背面全照(3张)
   */
    @BindView(R.id.snpl_monitor_add_photos)
    BGASortableNinePhotoLayout snplMonitorAddPhotos;
    @BindView(R.id.ll_tv_bg_b)
    LinearLayout llTvBgB;
    /*
   * 机柜正面/背面 (3张)
   */
    @BindView(R.id.snpl_tools_package_add_photos)
    BGASortableNinePhotoLayout snplToolsPackageAddPhotos;
    @BindView(R.id.ll_gui_bg)
    LinearLayout llGuiBg;
    /*
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

    private List<BughandleDetailEntity> DetailEntityList;
    private FillTroubleDetailAdapter fillTroubleDetailAdapter;
    private HashMap<String, String> uploadMap = new HashMap<>();
    private String companyName;
    private Long companyId;
    private String singInTime;
    private BughandleConfirmEntity bughandleConfirmEntity;
    private Long orderId;
    private List<String> businessIdLis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_repair_info);
        ButterKnife.bind(this);

        initNinePhoto();
        setListener();
        initData();
        supprotToolbar();
        setTitle("填写信息");
        setRightTitle("添加");
    }

    public void setListener() {
        //新增
        setRightTitleOnClickListener(v -> {
            Intent intent = new Intent(FillRepairInfoActivity.this, AddTroubleActivity.class);
            intent.putExtra("repaid", orderId);
            startActivityForResult(intent, 10003);
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
            new TrueFalseDialog(activity, "系统提示", "是否确定挂单？", () -> {
                fillBean();
                putUpOrder();
            }).showDialog();
        }));
        //结束时间
        llOverTime.setOnClickListener(v -> {
            String currDateStr = GetDateUtils.dateToDateTimeString(GetDateUtils.getDateNow());
            Calendar startDate = Calendar.getInstance();
            //startDate.set(2017, 5, 24);
            //修改完成时间选择限制，不能早过当前时间
            startDate.set(GetDateUtils.getYear(currDateStr), GetDateUtils.getMonth(currDateStr), GetDateUtils.getDay(currDateStr), GetDateUtils.getHour(currDateStr), GetDateUtils.getMinute(currDateStr));
            Calendar endDate = Calendar.getInstance();
            endDate.set(2099, 11, 28, 23, 59, 59);

            PickerSelectUtil.onYearMonthDayTimePicker(this, "完工时间", startDate, endDate, (year1, month1, day1, hour1, minute1, startSecond) -> {
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                tvOverTime.setText(year1 + "-" + month1 + "-" + day1 + " " + hour1 + ":" + minute1 + ":" + startSecond);

                //计算维修工时
                if (bughandleConfirmEntity.getSingInTime() == null) {
                    etRepairTime.setText("0小时0分钟");
                    return;
                }

                Date finishDay = GetDateUtils.getDate(year1, month1, day1, hour1, minute1, startSecond);
                long day = GetDateUtils.getTimeDiff(finishDay, bughandleConfirmEntity.getSingInTime(), "day");
                long hours = GetDateUtils.getTimeDiff(finishDay, bughandleConfirmEntity.getSingInTime(), "hours");
                long minutes = GetDateUtils.getTimeDiff(finishDay, bughandleConfirmEntity.getSingInTime(), "minutes");
                if (day < 0) {
                    day = 0;
                }
                if (hours < 0) {
                    hours = 0;
                }
                if (minutes < 0) {
                    minutes = 0;
                }
                etRepairTime.setText((day * 24 + hours) + "小时" + minutes + "分钟");
            });
        });

        //录像机天数
        llStoreTime.setOnClickListener(v -> {
            PickerSelectUtil.singleTextPicker(this, "", tvStoreTime, GetConstDataUtils.getStoreDayList());
        });
        //报警打印机
        llPrintOnAlarm.setOnClickListener(v -> {
            PickerSelectUtil.singleTextPicker(this, "", tvPrintOnAlarm, GetConstDataUtils.getIsNormalList());
        });
        //数据远传
        llMachineDataRemote.setOnClickListener(v -> {
            PickerSelectUtil.singleTextPicker(this, "", tvMachineDataRemote, GetConstDataUtils.getIsNormalList());
        });
        //时间
        llTimeRight.setOnClickListener(v -> {
            PickerSelectUtil.singleTextPicker(this, "", tvTimeRight, GetConstDataUtils.getIsNormalList());
        });

    }

    private void initData() {
        orderId = getIntent().getLongExtra("orderId", 0);
        companyName = getIntent().getStringExtra("companyName");
        companyId = getIntent().getLongExtra("companyUid", 0);
        doHttpOrderDetail(orderId);
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
        fillTroubleDetailAdapter = new FillTroubleDetailAdapter(R.layout.item_quotation_detail, DetailEntityList);
        rvTrouble.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvTrouble.setLayoutManager(new LinearLayoutManager(this));
        rvTrouble.setAdapter(fillTroubleDetailAdapter);
        rvTrouble.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(FillRepairInfoActivity.this, AddTroubleDetailActivity.class);
                intent.putExtra("confirmId", bughandleConfirmEntity.getId());
                intent.putExtra("failureId", DetailEntityList.get(position).getBusRepairFailureId());
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE_UPDATE_TROUBLE);
                ((BaseViewHolder) rvTrouble.getChildViewHolder(rvTrouble.getChildAt(position))).setText(R.id.tv_detai_status, "");
            }
        });
//        fillTroubleDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
//            switch (view.getId()) {
//                case R.id.rl_item_detail:
//                    Intent intent = new Intent(FillRepairInfoActivity.this, QuotationDetailActivity.class);
//                    intent.putExtra("data", .get(position));
//                    startActivity(intent);
//                    break;
////                case R.id.tv_delete:
////                    mDataList.remove(position);
////                    quotationDetailAdapter.notifyDataSetChanged();
////                    break;
//                default:
//                    break;
//            }
//        });
    }

    private void initNinePhoto() {
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        snplMonitorAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_2, REQUEST_CODE_PHOTO_PREVIEW_2));
        snplToolsPackageAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_3, REQUEST_CODE_PHOTO_PREVIEW_3));
        snplFormPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_4, REQUEST_CODE_PHOTO_PREVIEW_4));
    }

    private boolean checkInfo() {
        String overTime = tvOverTime.getText().toString().trim();
        if (StringUtils.isEmpty(overTime)) {
            showToast("请填写完工时间");
            return false;
        }
        String storetime = tvStoreTime.getText().toString().trim();
        if (StringUtils.isEmpty(storetime)) {
            showToast("请检查录像机天数");
            return false;
        }
        String printOnAlarm = tvPrintOnAlarm.getText().toString().trim();
        if (StringUtils.isEmpty(printOnAlarm)) {
            showToast("请检查报警打印机");
            return false;
        }
        String timeRight = tvTimeRight.getText().toString().trim();
        if (StringUtils.isEmpty(timeRight)) {
            showToast("请检查设备时间同步");
            return false;
        }
        String machineDataRemote = tvMachineDataRemote.getText().toString().trim();
        if (StringUtils.isEmpty(machineDataRemote)) {
            showToast("请检查各类设备数据远传功能");
            return false;
        }
        String remainQuestion = etRemainQuestion.getText().toString().trim();
        if (StringUtils.isEmpty(remainQuestion)) {
            showToast("请填写遗留问题");
            return false;
        }
//        if (DetailEntityList != null) {
//            //增加限制，需要先完善故障处理 在提交
//            for (int i = 0; i < DetailEntityList.size(); i++) {
//                if (StringUtils.isEmpty(DetailEntityList.get(i).getCheckProcess())) {
//                    showToast("请完善第" + (i + 1) + "条故障处理明细");
//                    return false;
//                }
//            }
//        }

        return true;
    }

    private BughandleConfirmEntity fillBean() {

        bughandleConfirmEntity.setBusRepairOrderId(orderId);

        bughandleConfirmEntity.setOverTime(GetDateUtils.getDate(tvOverTime.getText().toString().trim()));
        bughandleConfirmEntity.setWorkHour(etRepairTime.getText().toString().trim());
        bughandleConfirmEntity.setStoreDays(GetConstDataUtils.getStoreDayList().indexOf(tvStoreTime.getText().toString().trim()));
        bughandleConfirmEntity.setIsAlarmPrinter(GetConstDataUtils.getIsNormalList().indexOf(tvPrintOnAlarm.getText().toString().trim()));
        bughandleConfirmEntity.setIsTimeRight(GetConstDataUtils.getIsNormalList().indexOf(tvTimeRight.getText().toString().trim()));
        bughandleConfirmEntity.setIsMachineDataRemote(GetConstDataUtils.getIsNormalList().indexOf(tvMachineDataRemote.getText().toString().trim()));
        bughandleConfirmEntity.setLeftoverProblem(etRemainQuestion.getText().toString().trim());
        //协作人员
        bughandleConfirmEntity.setTeamWorker(etTeamWorker.getText().toString().trim());
        //bughandleConfirmEntity.setDetailEntityList(mDataList);
        uploadMap.clear();
        //电视墙/操作台正面全貌 （3张）
        String presentationPic = PhotoUtils.getPhotoUrl(snplMomentAddPhotos, uploadMap, true);
        bughandleConfirmEntity.setFrontPictures(presentationPic);

        //电视墙/操作台背面面全貌 （3张）
        String toolPic = PhotoUtils.getPhotoUrl(snplMonitorAddPhotos, uploadMap, false);
        bughandleConfirmEntity.setReverseSidePictures(toolPic);

        //机柜正面/背面 （3张）
        String pointPic = PhotoUtils.getPhotoUrl(snplToolsPackageAddPhotos, uploadMap, false);
        bughandleConfirmEntity.setEquipmentCabinetPictures(pointPic);

        //单据照片 （3张）
        String afterHandlePic = PhotoUtils.getPhotoUrl(snplFormPhotos, uploadMap, false);
        bughandleConfirmEntity.setInvoicesPictures(afterHandlePic);

        return bughandleConfirmEntity;
    }

    //提交完工
    private void submit() {
        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        //代表不需要挂单Z
                        String requestJson = JSONObject.toJSONString(fillBean());
                        doHttp(requestJson);
                    });
                }
            });
            return;
        }
        //代表不需要挂单Z
        String requestJson = JSONObject.toJSONString(fillBean());
        doHttp(requestJson);
    }

    /**
     * 挂单
     */
    private void putUpOrder() {

        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    Intent intent = new Intent(activity, PutUpOrderActivity.class);
                    intent.putExtra("bean", fillBean());
                    intent.putExtra("companyName", companyName);
                    intent.putExtra("companyId", companyId);
                    intent.putExtra("businessId", (ArrayList<String>) businessIdLis);
                    intent.putExtra("orderId", bughandleConfirmEntity.getId());
                    startActivity(intent);
                }
            });
            return;
        }
        Intent intent = new Intent(this, PutUpOrderActivity.class);
        intent.putExtra("bean", fillBean());
//        intent.putExtra("workerId", workerId);
        intent.putExtra("companyName", companyName);
        intent.putExtra("companyId", companyId);
        intent.putStringArrayListExtra("businessId", (ArrayList<String>) businessIdLis);
        startActivity(intent);
    }

    /**
     * 完工网络请求
     */
    private void doHttp(String jsonString) {
        EanfangHttp.post(RepairApi.POST_REPAIR_FINISH_WORK)
                .upJson(jsonString.toString())
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    showToast("提交成功");
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
                snplMomentAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_2:
                snplMonitorAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_3:
                snplToolsPackageAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snplFormPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
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
                snplFormPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_UPDATE_TROUBLE:
                BughandleDetailEntity resultBean = (BughandleDetailEntity) data.getSerializableExtra("bean");
                if (resultBean == null) {
                    return;
                }
                int position = data.getIntExtra("position", 0);
                DetailEntityList.remove(position);
                DetailEntityList.add(resultBean);
                fillTroubleDetailAdapter.notifyDataSetChanged();
                break;
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
}

