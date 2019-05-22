package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.base.BaseEvent;
import com.eanfang.sdk.selecttime.SelectTimeDialogFragment;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/6/5  14:29
 * @decision 预约上门时间
 */
public class MaintenanceAppointTimeActivity extends BaseActivity implements SelectTimeDialogFragment.SelectTimeListener {

    @BindView(R.id.tv_doorTime)
    TextView tvDoorTime;
    @BindView(R.id.ll_doorTime)
    LinearLayout llDoorTime;
    @BindView(R.id.tv_specificTime)
    TextView tvSpecificTime;
    @BindView(R.id.ll_specificTime)
    LinearLayout llSpecificTime;
    private Long orderId;
    //    private TimePickerView mTimeYearMonthDay, mTimeHourMinSec;
    private long mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_appoint_time);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        orderId = getIntent().getLongExtra("orderId", 0);
        mType = getIntent().getIntExtra("type", 0);
        setTitle("预约上门时间");
        setLeftBack();
        setRightTitle("确认");
//        doSelectYearMonthDay();
//        doSelectMonthMinSec();
    }

    private void initListener() {
        setRightTitleOnClickListener((v) -> {
            String contract_door = tvDoorTime.getText().toString().trim();
            String contract_specific = tvSpecificTime.getText().toString().trim();
            if (TextUtils.isEmpty(contract_door)) {
                showToast("预约时间不能为空");
                return;
            }
            doHttp(0, contract_door + " " + contract_specific);
        });
    }

    /**
     * 带点筛选
     */
    private void doHttp(int solve, String bookTime) {


        JSONObject object = new JSONObject();
        object.put("id", orderId + "");
        object.put("bookTime", bookTime);
        object.put("status", 2);//更新状态 变为带上门

        String url = NewApiService.MAINTENANCE_APPOINT_TIME;
        if (mType != 1) {
            url = NewApiService.MAINTENANCE_UPDATE_APPOINT_TIME;
        }

        String finalUrl = url;
        EanfangHttp.post(url)
                .upJson(JsonUtils.obj2String(object))
                .execute(new EanfangCallback<JSONObject>(MaintenanceAppointTimeActivity.this, true, JSONObject.class, (bean) -> {
                    showToast("预约成功");
                    if (mType == 1) {
                        EventBus.getDefault().post(new BaseEvent());//刷新item

                    }
           Log.d("电话回访，电话预约上门时间b", finalUrl +"\n"+JsonUtils.obj2String(object)+"\n"+bean.toString());

                    finishSelf();
                    EanfangApplication.get().closeActivity(MaintenanceAppointTimeActivity.class.getName());
                }));

    }

    /**
     * 选择年月日
     */
    public void doSelectYearMonthDay() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DATE));
        endDate.set(2040, 11, 31);
//        mTimeYearMonthDay = new TimePickerBuilder(MaintenanceAppointTimeActivity.this, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                tvDoorTime.setText(ETimeUtils.getTimeByYearMonthDay(date));
//            }
//        })
//                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
//                .setCancelText("取消")//取消按钮文字
//                .setSubmitText("确定")//确认按钮文字
//                .setContentTextSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                .setTitleText("上门日期")//标题文字
//                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
//                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(false)//是否显示为对话框样式
//                .build();
    }

    /**
     * 选择时分秒
     */
    public void doSelectMonthMinSec() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(selectedDate.get(Calendar.HOUR), selectedDate.get(Calendar.MINUTE), selectedDate.get(Calendar.SECOND));
        endDate.set(2040, 11, 31);
//        mTimeHourMinSec = new TimePickerBuilder(MaintenanceAppointTimeActivity.this, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                tvSpecificTime.setText(ETimeUtils.getTimeByHourMinSec(date));
//            }
//        })
//                .setType(new boolean[]{false, false, false, true, true, true})// 默认全部显示
//                .setCancelText("取消")//取消按钮文字
//                .setSubmitText("确定")//确认按钮文字
//                .setContentTextSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                .setTitleText("上门日期")//标题文字
//                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
//                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(false)//是否显示为对话框样式
//                .build();
    }

    @OnClick({R.id.ll_doorTime, R.id.ll_specificTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_doorTime:
//                mTimeYearMonthDay.show();
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
                break;
            case R.id.ll_specificTime:
//                mTimeHourMinSec.show();
                break;
        }
    }

    @Override
    public void getData(String time) {
        if (StringUtils.isEmpty(time) || " ".equals(time)) {
            tvDoorTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            tvDoorTime.setText(time);
        }
    }
}
