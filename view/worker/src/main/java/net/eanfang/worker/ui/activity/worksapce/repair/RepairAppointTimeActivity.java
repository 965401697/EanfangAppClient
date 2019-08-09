package net.eanfang.worker.ui.activity.worksapce.repair;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.sdk.selecttime.SelectTimeDialogFragment;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.DateKit;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author Guanluocang
 * @date on 2018/6/5  14:29
 * @decision 预约上门时间
 */
public class RepairAppointTimeActivity extends BaseActivity implements SelectTimeDialogFragment.SelectTimeListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_repair_appoint_time);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {
        orderId = getIntent().getLongExtra("orderId", 0);
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
//        QueryEntry queryEntry = new QueryEntry();
//        queryEntry.getEquals().put("orderId", orderId + "");
//        queryEntry.getEquals().put("solve", solve + "");
//        queryEntry.getEquals().put("bookTime", bookTime);

        EanfangHttp.post(RepairApi.POST_FLOW_SCREENING)
                .params("orderId", orderId + "")
                .params("solve", solve + "")
                .params("bookTime", bookTime)
//                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<JSONObject>(RepairAppointTimeActivity.this, true, JSONObject.class, (bean) -> {

                    Log.d("电话回访，电话预约上门时间a",RepairApi.POST_FLOW_SCREENING+"\n"+"orderId: "+orderId+"\n"+"solve: "+solve+"\n"+"bookTime: "+bookTime+"\n");
                    showToast("预约成功");
                    setResult(RESULT_OK);
                    finishSelf();
                }));

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
        if (StrUtil.isEmpty(time) || " ".equals(time)) {
            tvDoorTime.setText(DateUtil.date().toString());
        } else {
            if (DateUtil.parse(time).getTime() < DateKit.get().offset(DateField.MINUTE, 30).date.getTime()) {
                showToast("预约时间不能小于30分钟");
                return;
            }
            tvDoorTime.setText(time);
        }
    }
}
