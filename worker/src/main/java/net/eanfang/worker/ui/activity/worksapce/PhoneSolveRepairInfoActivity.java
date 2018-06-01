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
import com.bigkoo.pickerview.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PhotoUtils;
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
import net.eanfang.worker.util.ImagePerviewUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:35
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PhoneSolveRepairInfoActivity extends BaseWorkerActivity {


    public static final int REQUEST_CODE_UPDATE_TROUBLE = 10001;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_4 = 4;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_4 = 104;
    private final Activity activity = this;
    private RecyclerView rv_trouble;
    /*
     * 单据照片 (3张)
     */
    private BGASortableNinePhotoLayout snpl_form_photos;
    private TextView tv_commit;
    private List<BughandleDetailEntity> mDataList;
    private FillTroubleDetailAdapter quotationDetailAdapter;
    private HashMap<String, String> uploadMap = new HashMap<>();
    private String companyName;
    private Long companyId;
    /*
     * 遗留问题
     */
    private EditText et_remain_question;
    private BughandleConfirmEntity bughandleConfirmEntity;
    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            Intent intent = new Intent(PhoneSolveRepairInfoActivity.this, PhoneSolveTroubleDetailActivity.class);
            intent.putExtra("id", bughandleConfirmEntity.getDetailEntityList().get(position).getBusRepairFailureId());
            intent.putExtra("bean", bughandleConfirmEntity.getDetailEntityList().get(position));
            intent.putExtra("confirmId", bughandleConfirmEntity.getId());
            intent.putExtra("position", position);
            intent.putExtra("companyName", companyName);
            startActivityForResult(intent, REQUEST_CODE_UPDATE_TROUBLE);
//            ((BaseViewHolder) rv_trouble.getChildViewHolder(rv_trouble.getChildAt(position))).setText(R.id.tv_detai_status, "");
        }
    };
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ps_fill_repair_info);

        initView();
        initEndTimePicker();
        initNinePhoto();
        setListener();
        initData();
        supprotToolbar();
        setTitle("填写信息");
    }

    private void initView() {
        rv_trouble = (RecyclerView) findViewById(R.id.rv_trouble);
        et_remain_question = (EditText) findViewById(R.id.et_remain_question);
        snpl_form_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_form_photos);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
    }

    public void setListener() {

        tv_commit.setOnClickListener(new MultiClickListener(this, this::checkInfo, () -> {
            new TrueFalseDialog(activity, "系统提示", "是否确定提交完工？", () -> {
                fillBean();
                submit();
            }).showDialog();
        }));
        rv_trouble.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_pic) {
                    ArrayList<String> picList = new ArrayList<String>();
                    String[] urls = mDataList.get(position).getFailureEntity().getPictures().split(",");
                    if (urls.length >= 1) {
                        picList.add(com.eanfang.BuildConfig.OSS_SERVER + urls[0]);
                    }
                    if (urls.length >= 2) {
                        picList.add(com.eanfang.BuildConfig.OSS_SERVER + urls[1]);
                    }
                    if (urls.length >= 3) {
                        picList.add(com.eanfang.BuildConfig.OSS_SERVER + urls[2]);
                    }
                    if (picList.size() == 0) {
//                        showToast("当前没有图片");
                        return;
                    }
                    ImagePerviewUtil.perviewImage(PhoneSolveRepairInfoActivity.this, picList);
                }
            }
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
        quotationDetailAdapter = new FillTroubleDetailAdapter(R.layout.layout_trouble_detail, mDataList);
        rv_trouble.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rv_trouble.setLayoutManager(new LinearLayoutManager(this));
        rv_trouble.setAdapter(quotationDetailAdapter);
        rv_trouble.addOnItemTouchListener(onItemClickListener);
        quotationDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.rl_item_detail:
                    Intent intent = new Intent(PhoneSolveRepairInfoActivity.this, QuotationDetailActivity.class);
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
        snpl_form_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_4, REQUEST_CODE_PHOTO_PREVIEW_4));
    }

    private boolean checkInfo() {
//        String overTime = tv_over_time.getText().toString().trim();
//        if (StringUtils.isEmpty(overTime)) {
//            showToast("请填写完工时间");
//            return false;
//        }

        String remainQuestion = et_remain_question.getText().toString().trim();
        if (StringUtils.isEmpty(remainQuestion)) {
            showToast("请填写遗留问题");
            return false;
        }

        if (bughandleConfirmEntity.getDetailEntityList() != null) {
            //增加限制，需要先完善故障处理 在提交
            for (int i = 0; i < bughandleConfirmEntity.getDetailEntityList().size(); i++) {
                if (StringUtils.isEmpty(bughandleConfirmEntity.getDetailEntityList().get(i).getCheckProcess())) {
                    showToast("请完善第" + (i + 1) + "条故障处理明细");
                    return false;
                }
            }
        }
        return true;
    }

    private BughandleConfirmEntity fillBean() {
        bughandleConfirmEntity.setBusRepairOrderId(id);

//        bughandleConfirmEntity.setOverTime(GetDateUtils.getDate(tv_over_time.getText().toString().trim()));
//        bughandleConfirmEntity.setWorkHour(repairTime);
        bughandleConfirmEntity.setLeftoverProblem(et_remain_question.getText().toString().trim());
//        uploadMap.clear();
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
                        String requestJson = JSONObject.toJSONString(bughandleConfirmEntity);
                        doHttp(requestJson);
                    });
                }
            });
            return;
        }
        String requestJson = JSONObject.toJSONString(bughandleConfirmEntity);
        doHttp(requestJson);
    }

    /**
     * 完工网络请求
     */
    private void doHttp(String jsonString) {
        EanfangHttp.post(RepairApi.POST_REPAIR_FINISH_WORK)
                .upJson(jsonString)
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
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snpl_form_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_4:
                snpl_form_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_UPDATE_TROUBLE:
                BughandleDetailEntity resultBean = (BughandleDetailEntity) data.getSerializableExtra("bean");
                if (resultBean == null) {
                    return;
                }
                int position = data.getIntExtra("position", 0);
                mDataList.remove(position);
                mDataList.add(resultBean);
                quotationDetailAdapter.notifyDataSetChanged();
                break;
            case 10003:
                if (data.getSerializableExtra("beans") == null) {
                    return;
                }
                RepairFailureEntity bugBean = (RepairFailureEntity) data.getSerializableExtra("beans");
                BughandleDetailEntity bughandleDetailEntity = new BughandleDetailEntity();
                bughandleDetailEntity.setBusRepairFailureId(bugBean.getId());
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
//        pvEndTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
//                tv_over_time.setText(GetDateUtils.dateToDateTimeString(date));
//
//                //计算维修工时
//                if (StringUtils.isEmpty(markdowntime)) {
//                    et_repair_time.setText("0小时0分钟");
//                    return;
//                }
//                long day = GetDateUtils.getTimeDiff(date, GetDateUtils.getDate(markdowntime), "day");
//                long hours = GetDateUtils.getTimeDiff(date, GetDateUtils.getDate(markdowntime), "hours");
//                long minutes = GetDateUtils.getTimeDiff(date, GetDateUtils.getDate(markdowntime), "minutes");
//                if (day < 0) {
//                    day = 0;
//                }
//                if (hours < 0) {
//                    hours = 0;
//                }
//                if (minutes < 0) {
//                    minutes = 0;
//                }
//                et_repair_time.setText((day * 24 + hours) + "小时" + minutes + "分钟");
//            }
//        })
//                .setTitleText("结束时间")
//                .setType(TimePickerView.Type.ALL)
//                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
//                .setDividerColor(Color.DKGRAY)
//                .setContentSize(20)
//                .setDate(selectedDate)
//                .setRangDate(startDate, endDate)
//                .build();
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

