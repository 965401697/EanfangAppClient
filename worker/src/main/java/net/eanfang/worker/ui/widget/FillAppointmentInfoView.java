package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.repair.SolveModeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  15:35
 * @email houzhongzhou@yeah.net
 * @desc 上门签到
 */

public class FillAppointmentInfoView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    // 上门时间
    @BindView(R.id.tv_doorTime)
    TextView tvDoorTime;
    @BindView(R.id.ll_doorTime)
    LinearLayout llDoorTime;
    // 具体时间
    @BindView(R.id.tv_specificTime)
    TextView tvSpecificTime;
    @BindView(R.id.ll_specificTime)
    LinearLayout llSpecificTime;

    private Long orderId;
    private Activity mContext;

    public FillAppointmentInfoView(Activity context, boolean isfull, Long orderId) {
        super(context, isfull);
        this.orderId = orderId;
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_appoint);
        ButterKnife.bind(this);
        initView();
        initListener();
    }


    private void initView() {
        tvTitle.setText("预约上门时间");
        tvRight.setText("确认");
        //增加预约时间不能为空验证

        llDoorTime.setOnClickListener(v -> PickerSelectUtil.onYearMonthDayTimePicker(mContext, "上门日期", tvDoorTime));
//        llSpecificTime.setOnClickListener(v -> PickerSelectUtil.onHourTimePicker(mContext, "上门日期", tvSpecificTime));
    }

    private void initListener() {
        ivLeft.setOnClickListener((V) -> {
            dismiss();
        });
        tvRight.setOnClickListener((v) -> {
            String contract_door = tvDoorTime.getText().toString().trim();
//            String contract_specific = tvSpecificTime.getText().toString().trim();
            if (TextUtils.isEmpty(contract_door)) {
                showToast("预约时间不能为空");
                return;
            }
            doHttp(0, contract_door);
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
                .execute(new EanfangCallback<JSONObject>(mContext, true, JSONObject.class, (bean) -> {
                    showToast("预约成功");
                    dismiss();
                    EanfangApplication.get().closeActivity(SolveModeActivity.class.getName());
                }));

    }

}

