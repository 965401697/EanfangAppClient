package net.eanfang.worker.ui.activity.worksapce;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.bigkoo.pickerview.TimePickerView;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:06
 * @email houzhongzhou@yeah.net
 * @desc
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
    private TextView tv_detail_name;
    private RecyclerView rv_trouble;
    private TextView tv_over_time;
    private LinearLayout ll_over_time;
    private TextView et_repair_time;
    /*
     * 电视墙/操作台正面全貌 (3张)
     */
    private BGASortableNinePhotoLayout snpl_moment_add_photos;
    /*
     * 电视墙/操作台背面全照(3张)
     */
    private BGASortableNinePhotoLayout snpl_monitor_add_photos;
    /*
     * 机柜正面/背面 (3张)
     */
    private BGASortableNinePhotoLayout snpl_tools_package_add_photos;
    /*
     * 单据照片 (3张)
     */
    private BGASortableNinePhotoLayout snpl_form_photos;
    private TextView tv_commit;
    private TextView tv_up;
    private List<BughandleDetailEntity> mDataList;
    private FillTroubleDetailAdapter quotationDetailAdapter;
    private HashMap<String, String> uploadMap = new HashMap<>();
    private TimePickerView pvEndTime;
    private String companyName;
    private Long companyId;
    //2017年7月20日 当前订单的签到时间
    private String markdowntime;
    /*
     * 录像机天数
     */
    private TextView tv_store_time;
    /*
     * 录像机天数
     */
    private LinearLayout ll_store_time;
    /*
     * 报警打印机
     */
    private LinearLayout ll_print_on_alarm;
    /*
     * 报警打印机
     */
    private TextView tv_print_on_alarm;
    /*
     * 所有设备时间同步
     */
    private TextView tv_time_right;
    /*
     * 所有设备时间同步
     */
    private LinearLayout ll_time_right;
    /*
     * 各类设备数据远传功能
     */
    private LinearLayout ll_machine_data_remote;
    /*
     * 各类设备数据远传功能
     */
    private TextView tv_machine_data_remote;
    /*
     * 遗留问题
     */
    private EditText et_remain_question;
    /*
     * 遗留问题
     */
    private LinearLayout ll_remain_question;
    //2017年7月21日
    /*
     * 协助人员
     */
    private LinearLayout ll_team_worker;
    /*
     * 协助人员
     */
    private EditText et_team_worker;
    private BughandleConfirmEntity bughandleConfirmEntity;
    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            Intent intent = new Intent(FillRepairInfoActivity.this, AddTroubleDetailActivity.class);
            intent.putExtra("id", bughandleConfirmEntity.getDetailEntityList().get(position).getBusRepairFailureId());
            intent.putExtra("bean", bughandleConfirmEntity.getDetailEntityList().get(position));
//            intent.putExtra("bugOneCode", bugOneCode);
            intent.putExtra("position", position);
            intent.putExtra("companyName", companyName);
            startActivityForResult(intent, REQUEST_CODE_UPDATE_TROUBLE);
            ((BaseViewHolder) rv_trouble.getChildViewHolder(rv_trouble.getChildAt(position))).setText(R.id.tv_detai_status, "");
        }
    };
    private Long id;
    private List<String> businessIdLis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_repair_info);

        initView();
        initEndTimePicker();
        initNinePhoto();
        setListener();
        initData();
        supprotToolbar();
        setTitle("填写信息");
        setRightTitle("添加");
    }

    private void initView() {
        tv_detail_name = (TextView) findViewById(R.id.tv_detail_name);
        rv_trouble = (RecyclerView) findViewById(R.id.rv_trouble);
        // = (TextView) findViewById(R.id.textView15);
        tv_over_time = (TextView) findViewById(R.id.tv_over_time);
        ll_over_time = (LinearLayout) findViewById(R.id.ll_over_time);
        et_repair_time = (TextView) findViewById(R.id.et_repair_time);
        //  ll_repair_time = (LinearLayout) findViewById(R.id.ll_repair_time);
        //报警打印机
        ll_print_on_alarm = (LinearLayout) findViewById(R.id.ll_print_on_alarm);
        tv_print_on_alarm = (TextView) findViewById(R.id.tv_print_on_alarm);
        //各类设备数据远传
        ll_machine_data_remote = (LinearLayout) findViewById(R.id.ll_machine_data_remote);
        tv_machine_data_remote = (TextView) findViewById(R.id.tv_machine_data_remote);
        //协助人员
        ll_team_worker = (LinearLayout) findViewById(R.id.ll_team_worker);
        et_team_worker = (EditText) findViewById(R.id.et_team_worker);

        tv_store_time = (TextView) findViewById(R.id.tv_store_time);
        ll_store_time = (LinearLayout) findViewById(R.id.ll_store_time);
        tv_time_right = (TextView) findViewById(R.id.tv_time_right);
        ll_time_right = (LinearLayout) findViewById(R.id.ll_time_right);
        et_remain_question = (EditText) findViewById(R.id.et_remain_question);
        ll_remain_question = (LinearLayout) findViewById(R.id.ll_remain_question);
        snpl_moment_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_moment_add_photos);
        snpl_monitor_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_monitor_add_photos);
        snpl_tools_package_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_tools_package_add_photos);
        snpl_form_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_form_photos);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_up = (TextView) findViewById(R.id.tv_up);

    }

    public void setListener() {
        setRightTitleOnClickListener(v -> {
            Intent intent = new Intent(FillRepairInfoActivity.this, AddTroubleActivity.class);
            intent.putExtra("repaid", id);
            startActivityForResult(intent, 10003);
        });


        tv_commit.setOnClickListener(new MultiClickListener(this, this::checkInfo, () -> {
            new TrueFalseDialog(activity, "系统提示", "是否确定提交完工？", () -> {
                fillBean();
                submit();
            }).showDialog();
        }));

        tv_up.setOnClickListener(new MultiClickListener(this, this::checkInfo, () -> {
            new TrueFalseDialog(activity, "系统提示", "是否确定挂单？", () -> {
                fillBean();
                putUpOrder();
            }).showDialog();
        }));

        ll_over_time.setOnClickListener(v -> pvEndTime.show());

        //录像机天数
        ll_store_time.setOnClickListener(v -> {
            PickerSelectUtil.singleTextPicker(this, "", tv_store_time, GetConstDataUtils.getStoreDayList());
        });
        //报警打印机
        ll_print_on_alarm.setOnClickListener(v -> {
            PickerSelectUtil.singleTextPicker(this, "", tv_print_on_alarm, GetConstDataUtils.getIsNormalList());
        });
        //数据远传
        ll_machine_data_remote.setOnClickListener(v -> {
            PickerSelectUtil.singleTextPicker(this, "", tv_machine_data_remote, GetConstDataUtils.getIsNormalList());

        });

        ll_time_right.setOnClickListener(v -> {
            PickerSelectUtil.singleTextPicker(this, "", tv_time_right, GetConstDataUtils.getIsNormalList());
        });

    }

    private void initData() {
        id = getIntent().getLongExtra("orderId", 0);
        companyName = getIntent().getStringExtra("companyName");
        companyId = getIntent().getLongExtra("companyUid", 0);
        doHttpOrderDetail(id);
    }

    private void doHttpOrderDetail(Long id) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("repairId", id + "");
        EanfangHttp.post(RepairApi.POST_BUGHANDLE_PREPARE)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<BughandleConfirmEntity>(this, true, BughandleConfirmEntity.class, (bean) -> {
                    bughandleConfirmEntity = bean;
                    initAdapter();
                }));
    }

    private void initAdapter() {
        mDataList = bughandleConfirmEntity.getDetailEntityList();

        businessIdLis = Stream.of(mDataList).map(bean -> Config.get().getBusinessIdByCode(bean.getFailureEntity().getBusinessThreeCode(), 3) + "").toList();
        quotationDetailAdapter = new FillTroubleDetailAdapter(R.layout.item_quotation_detail, mDataList);
        rv_trouble.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rv_trouble.setLayoutManager(new LinearLayoutManager(this));
        rv_trouble.setAdapter(quotationDetailAdapter);
        rv_trouble.addOnItemTouchListener(onItemClickListener);
        quotationDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.rl_item_detail:
                    Intent intent = new Intent(FillRepairInfoActivity.this, QuotationDetailActivity.class);
                    intent.putExtra("data", mDataList.get(position));
                    startActivity(intent);
                    break;
                case R.id.tv_delete:
                    mDataList.remove(position);
                    quotationDetailAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        });
    }

    private void initNinePhoto() {
        snpl_moment_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        snpl_monitor_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_2, REQUEST_CODE_PHOTO_PREVIEW_2));
        snpl_tools_package_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_3, REQUEST_CODE_PHOTO_PREVIEW_3));
        snpl_form_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_4, REQUEST_CODE_PHOTO_PREVIEW_4));
    }

    private boolean checkInfo() {
//        String overTime = tv_over_time.getText().toString().trim();
//        if (StringUtils.isEmpty(overTime)) {
//            showToast("请填写完工时间");
//            return false;
//        }
//        String storetime = tv_store_time.getText().toString().trim();
//        if (StringUtils.isEmpty(storetime)) {
//            showToast("请检查录像机天数");
//            return false;
//        }
//        String printOnAlarm = tv_print_on_alarm.getText().toString().trim();
//        if (StringUtils.isEmpty(printOnAlarm)) {
//            showToast("请检查报警打印机");
//            return false;
//        }
//        String timeRight = tv_time_right.getText().toString().trim();
//        if (StringUtils.isEmpty(timeRight)) {
//            showToast("请检查设备时间同步");
//            return false;
//        }
//        String machineDataRemote = tv_machine_data_remote.getText().toString().trim();
//        if (StringUtils.isEmpty(machineDataRemote)) {
//            showToast("请检查各类设备数据远传功能");
//            return false;
//        }
//        String remainQuestion = et_remain_question.getText().toString().trim();
//        if (StringUtils.isEmpty(remainQuestion)) {
//            showToast("请填写遗留问题");
//            return false;
//        }
//        if (bean.getBughandledetaillist() != null) {
//            //增加限制，需要先完善故障处理 在提交
//            for (int i = 0; i < bean.getBughandledetaillist().size(); i++) {
//                if (StringUtils.isEmpty(bean.getBughandledetaillist().get(i).getCheckprocess())) {
//                    showToast("请完善第" + (i + 1) + "条故障处理明细");
//                    return false;
//                }
//            }
//        }

        return true;
    }

    private BughandleConfirmEntity fillBean() {
        bughandleConfirmEntity.setBusRepairOrderId(id);

        bughandleConfirmEntity.setOverTime(GetDateUtils.getDate(tv_over_time.getText().toString().trim()));
        String repairTime = et_repair_time.getText().toString().trim();

        bughandleConfirmEntity.setWorkHour(repairTime);

        bughandleConfirmEntity.setStoreDays(GetConstDataUtils.getStoreDayList().indexOf(tv_store_time.getText().toString().trim()));


        bughandleConfirmEntity.setIsAlarmPrinter(GetConstDataUtils.getIsNormalList().indexOf(tv_print_on_alarm.getText().toString().trim()));


        bughandleConfirmEntity.setIsTimeRight(GetConstDataUtils.getIsNormalList().indexOf(tv_time_right.getText().toString().trim()));


        bughandleConfirmEntity.setIsMachineDataRemote(GetConstDataUtils.getIsNormalList().indexOf(tv_machine_data_remote.getText().toString().trim()));


        bughandleConfirmEntity.setLeftoverProblem(et_remain_question.getText().toString().trim());
        //协作人员
        bughandleConfirmEntity.setTeamWorker(et_team_worker.getText().toString().trim());
        bughandleConfirmEntity.setDetailEntityList(mDataList);

        uploadMap.clear();
        //电视墙/操作台正面全貌 （3张）
        String presentationPic = PhotoUtils.getPhotoUrl(snpl_moment_add_photos, uploadMap, true);
        bughandleConfirmEntity.setFrontPictures(presentationPic);

        //电视墙/操作台背面面全貌 （3张）
        String toolPic = PhotoUtils.getPhotoUrl(snpl_monitor_add_photos, uploadMap, false);
        bughandleConfirmEntity.setReverseSidePictures(toolPic);

        //机柜正面/背面 （3张）
        String pointPic = PhotoUtils.getPhotoUrl(snpl_tools_package_add_photos, uploadMap, false);
        bughandleConfirmEntity.setEquipmentCabinetPictures(pointPic);

        //单据照片 （3张）
        String afterHandlePic = PhotoUtils.getPhotoUrl(snpl_form_photos, uploadMap, false);
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
                snpl_moment_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_2:
                snpl_monitor_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_3:
                snpl_tools_package_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snpl_form_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
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
                snpl_form_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_UPDATE_TROUBLE:
                Serializable resultBean = data.getSerializableExtra("bean");
                if (resultBean == null) {
                    return;
                }
                int position = data.getIntExtra("position", 0);
                bughandleConfirmEntity.getDetailEntityList().set(position, (BughandleDetailEntity) resultBean);
                quotationDetailAdapter.notifyDataSetChanged();
                break;
            case 10003:
                if (data.getSerializableExtra("bean") == null) {
                    return;
                }
                RepairFailureEntity bugBean = (RepairFailureEntity) data.getSerializableExtra("bean");
                BughandleDetailEntity bughandleDetailEntity = new BughandleDetailEntity();
                bughandleDetailEntity.setFailureEntity(bugBean);
                mDataList.add(bughandleDetailEntity);
                quotationDetailAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void initEndTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        String currDateStr = GetDateUtils.dateToDateTimeString(GetDateUtils.getDateNow());
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2017, 5, 24);
        //修改完成时间选择限制，不能早过当前时间
        startDate.set(GetDateUtils.getYear(currDateStr), GetDateUtils.getMonth(currDateStr), GetDateUtils.getDay(currDateStr), GetDateUtils.getHour(currDateStr), GetDateUtils.getMinute(currDateStr));
        Calendar endDate = Calendar.getInstance();
        endDate.set(2099, 11, 28);
        //修改时间选择器  增加  小时 和 分钟选择
        //时间选择器
        pvEndTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                tv_over_time.setText(GetDateUtils.dateToDateTimeString(date));

                //计算维修工时
                if (StringUtils.isEmpty(markdowntime)) {
                    et_repair_time.setText("0小时0分钟");
                    return;
                }
                long day = GetDateUtils.getTimeDiff(date, GetDateUtils.getDate(markdowntime), "day");
                long hours = GetDateUtils.getTimeDiff(date, GetDateUtils.getDate(markdowntime), "hours");
                long minutes = GetDateUtils.getTimeDiff(date, GetDateUtils.getDate(markdowntime), "minutes");
                if (day < 0) {
                    day = 0;
                }
                if (hours < 0) {
                    hours = 0;
                }
                if (minutes < 0) {
                    minutes = 0;
                }
                et_repair_time.setText((day * 24 + hours) + "小时" + minutes + "分钟");
            }
        })
                .setTitleText("结束时间")
                .setType(TimePickerView.Type.ALL)
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .build();
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

