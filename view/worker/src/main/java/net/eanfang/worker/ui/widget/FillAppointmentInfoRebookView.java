package net.eanfang.worker.ui.widget;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRadioButton;

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
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  15:35
 * @email houzhongzhou@yeah.net
 * @desc 改约
 */

public class FillAppointmentInfoRebookView extends BaseActivity implements RadioGroup.OnCheckedChangeListener, SelectTimeDialogFragment.SelectTimeListener {
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
    @BindView(R.id.ll_door_time)
    LinearLayout llDoorTime;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    private Long orderId;
    private boolean isReAppointment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fill_appointment);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }


    private void initView() {
        setLeftBack();
        setTitle("电话筛查");
        orderId = getIntent().getLongExtra("itemId", 0);
        isReAppointment = getIntent().getBooleanExtra("isReAppoint", false);
        rgPhone.setOnCheckedChangeListener(this);
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
        llDoorTime.setOnClickListener(v -> SelectTimeDialogFragment.getInstance().show(getSupportFragmentManager(), R.string.app_name + ""));
    }

    /**
     * 带点筛选
     */

    private void doHttp(String bookTime) {
        EanfangHttp.post(RepairApi.POST_FLOW_REBOOK)
                .params("orderId", orderId + "")
                .params("bookTime", bookTime)
                .execute(new EanfangCallback<JSONObject>(FillAppointmentInfoRebookView.this, true, JSONObject.class, (bean) -> {
                    showToast("预约成功");
                    finishSelf();
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

    @Override
    public void getData(String time) {

        if (StrUtil.isEmpty(time) || " ".equals(time)) {
            tvIndoorTime.setText(DateUtil.date().toString());
        } else {
            if (DateUtil.parse(time).getTime() < DateKit.get().offset(DateField.MINUTE, 30).date.getTime()) {
                showToast("预约时间不能小于30分钟");
                return;
            }
            tvIndoorTime.setText(time);
        }
    }
}

