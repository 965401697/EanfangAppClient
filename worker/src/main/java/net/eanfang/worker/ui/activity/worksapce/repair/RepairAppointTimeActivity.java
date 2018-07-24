package net.eanfang.worker.ui.activity.worksapce.repair;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.ETimeUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;

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
public class RepairAppointTimeActivity extends BaseActivity {

    @BindView(R.id.tv_doorTime)
    TextView tvDoorTime;
    @BindView(R.id.ll_doorTime)
    LinearLayout llDoorTime;
    @BindView(R.id.tv_specificTime)
    TextView tvSpecificTime;
    @BindView(R.id.ll_specificTime)
    LinearLayout llSpecificTime;
    private Long orderId;
    private TimePickerView mTimeYearMonthDay, mTimeHourMinSec;

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
        setTitle("预约上门时间");
        setLeftBack();
        setRightTitle("确认");
        doSelectYearMonthDay();
        doSelectMonthMinSec();
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
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("orderId", orderId + "");
        queryEntry.getEquals().put("solve", solve + "");
        queryEntry.getEquals().put("bookTime", bookTime);
        EanfangHttp.post(RepairApi.POST_FLOW_SCREENING)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<JSONObject>(RepairAppointTimeActivity.this, true, JSONObject.class, (bean) -> {
                    showToast("预约成功");
                    setResult(RESULT_OK);
                    finishSelf();
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
        mTimeYearMonthDay = new TimePickerBuilder(RepairAppointTimeActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvDoorTime.setText(ETimeUtils.getTimeByYearMonthDay(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("上门日期")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
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
        mTimeHourMinSec = new TimePickerBuilder(RepairAppointTimeActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvSpecificTime.setText(ETimeUtils.getTimeByHourMinSec(date));
            }
        })
                .setType(new boolean[]{false, false, false, true, true, true})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("上门日期")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
    }

    @OnClick({R.id.ll_doorTime, R.id.ll_specificTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_doorTime:
                mTimeYearMonthDay.show();
                break;
            case R.id.ll_specificTime:
                mTimeHourMinSec.show();
                break;
        }
    }
}
