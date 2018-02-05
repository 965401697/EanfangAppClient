package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  15:35
 * @email houzhongzhou@yeah.net
 * @desc 改约
 */

public class FillAppointmentInfoRebookView extends BaseDialog implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.rb_yes)
    AppCompatRadioButton rbYes;
    @BindView(R.id.rb_no)
    AppCompatRadioButton rbNo;
    @BindView(R.id.rg_phone)
    RadioGroup rgPhone;
    @BindView(R.id.tv_indoor_time)
    TextView tvIndoorTime;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.ll_appointment_time)
    LinearLayout llAppointmentTime;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.ll_door_time)
    LinearLayout llDoorTime;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    private Long orderId;
    private boolean isReAppointment = false;
    private Activity mContext;


    public FillAppointmentInfoRebookView(Activity context, boolean isfull, Long orderId, boolean isReAppointment) {
        super(context, isfull);
        this.mContext = context;
        this.orderId = orderId;
        this.isReAppointment = isReAppointment;

    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fill_appointment);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        rgPhone.setOnCheckedChangeListener(this);
        tvTitle.setText("电话筛查");
        if (isReAppointment) {
            rbNo.setChecked(true);
            rbYes.setClickable(false);
            rgPhone.setEnabled(false);
        }
        btnCommit.setOnClickListener((v) -> {

            if (rgPhone.getCheckedRadioButtonId() == R.id.rb_no) {
                //增加预约时间不能为空验证
                String contract = tvIndoorTime.getText().toString().trim();
                if (TextUtils.isEmpty(contract)) {
                    showToast("预约时间不能为空");
                    return;
                }
                doHttp(contract);

            } else {
//                showToast("请到待完工页面继续填写故障处理。");
                doHttp(null);
            }
        });
        llDoorTime.setOnClickListener(v -> PickerSelectUtil.onYearMonthDayTimePicker(mContext, "开始时间", tvIndoorTime));
    }

    /**
     * 带点筛选
     */
    private void doHttp(String bookTime) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("orderId", orderId + "");
        queryEntry.getEquals().put("bookTime", bookTime);
        EanfangHttp.post(RepairApi.POST_FLOW_REBOOK)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<JSONObject>(mContext, true, JSONObject.class, (bean) -> {
                    showToast("预约成功");
                    dismiss();
                }));

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_no:
                llAppointmentTime.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_yes:
                llAppointmentTime.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}

