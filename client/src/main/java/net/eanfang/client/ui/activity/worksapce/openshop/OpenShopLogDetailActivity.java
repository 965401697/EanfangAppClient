package net.eanfang.client.ui.activity.worksapce.openshop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetDateUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.OpenShopLogEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.im.SelectIMContactActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenShopLogDetailActivity extends BaseClientActivity {


    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_accept_preson)
    TextView tvAcceptPreson;
    @BindView(R.id.tv_accept_phone)
    TextView tvAcceptPhone;
    @BindView(R.id.tv_staff_in_time)
    TextView tvStaffInTime;
    @BindView(R.id.tv_staff_out_time)
    TextView tvStaffOutTime;
    @BindView(R.id.tv_client_in_time)
    TextView tvClientInTime;
    @BindView(R.id.tv_client_out_time)
    TextView tvClientOutTime;
    @BindView(R.id.tv_open_time)
    TextView tvOpenTime;
    @BindView(R.id.tv_close_time)
    TextView tvCloseTime;
    @BindView(R.id.ev_faultDescripte)
    EditText evFaultDescripte;
    @BindView(R.id.iv_report_header)
    SimpleDraweeView ivReportHeader;
    @BindView(R.id.tv_report_name)
    TextView tvReportName;
    @BindView(R.id.tv_section)
    TextView tvSection;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.tv_work_time)
    TextView tvWorkTime;
    @BindView(R.id.tv_shopping_time)
    TextView tvShoppingTime;
    @BindView(R.id.tv_reveice_time)
    TextView tvReveiceTime;
    @BindView(R.id.tv_company_phone)
    TextView tvCompanyPhone;

    private boolean isVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_shop_log_detail);
        ButterKnife.bind(this);
        setTitle("日志详情");
        setLeftBack();
        initData();

        isVisible = getIntent().getBooleanExtra("isVisible", false);
    }

    private void initData() {
        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finishSelf();
            }
        });
        EanfangHttp.post(NewApiService.OA_OPEN_SHOP_DETAIL + "/" + getIntent().getStringExtra("id"))
                .execute(new EanfangCallback<OpenShopLogEntity>(this, true, OpenShopLogEntity.class, (bean) -> {
                    initViews(bean);
                    share(bean);
                }));
    }

    /**
     * 填充数据
     *
     * @param bean
     */
    private void initViews(OpenShopLogEntity bean) {
        if (bean.getOwnerUser() != null && bean.getOwnerUser().getAccountEntity() != null) {
            ivReportHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + bean.getOwnerUser().getAccountEntity().getAvatar()));
            tvCompanyPhone.setText(bean.getOwnerUser().getAccountEntity().getMobile());
            tvReportName.setText(bean.getOwnerUser().getAccountEntity().getRealName());
        }
        tvSection.setText(bean.getOwnerDepartment().getOrgName());


        String[] date = GetDateUtils.dateToDateString(bean.getCreateTime()).split("-");
        tvYear.setText(date[0] + "-" + date[1]);
        tvWeek.setText(GetDateUtils.dateToWeek(GetDateUtils.dateToDateString(bean.getCreateTime())));
        tvData.setText(date[2]);
        long workTime = bean.getEmpExitTime().getTime() - bean.getEmpEntryTime().getTime();
        if (!TextUtils.isEmpty(formatTime(workTime))) {
            tvWorkTime.setText("工作时长\r\n" + formatTime(workTime));
        } else {
            tvWorkTime.setText("工作时长\r\n" + "太短了");
        }
        long shoppingTime = bean.getCusExitTime().getTime() - bean.getCusEntryTime().getTime();
        if (!TextUtils.isEmpty(formatTime(shoppingTime))) {
            tvShoppingTime.setText("购物时长\r\n" + formatTime(shoppingTime));
        } else {
            tvShoppingTime.setText("购物时长\r\n" + "太短了");
        }
        long reveiceTime = bean.getRecYardEndTime().getTime() - bean.getRecYardStaTime().getTime();
        if (!TextUtils.isEmpty(formatTime(reveiceTime))) {
            tvReveiceTime.setText("收货时长\r\n" + formatTime(reveiceTime));
        } else {
            tvReveiceTime.setText("收货时长\r\n" + "太短了");
        }


        tvCompanyName.setText(bean.getOwnerCompany().getOrgName());
        tvAcceptPreson.setText(bean.getAssigneeUser().getAccountEntity().getRealName());
        tvAcceptPhone.setText(bean.getAssigneeUser().getAccountEntity().getMobile());
        tvStaffInTime.setText(GetDateUtils.dateToDateTimeString(bean.getEmpEntryTime()));
        tvStaffOutTime.setText(GetDateUtils.dateToDateTimeString(bean.getEmpExitTime()));
        tvClientInTime.setText(GetDateUtils.dateToDateTimeString(bean.getCusEntryTime()));
        tvClientOutTime.setText(GetDateUtils.dateToDateTimeString(bean.getCusExitTime()));
        tvOpenTime.setText(GetDateUtils.dateToDateTimeString(bean.getRecYardStaTime()));
        tvCloseTime.setText(GetDateUtils.dateToDateTimeString(bean.getRecYardEndTime()));

        evFaultDescripte.setText(bean.getRemarkInfo());
    }


    private void share(OpenShopLogEntity bean) {
        if (!isVisible) {
            setRightTitle("分享");
            setRightTitleOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //分享聊天

                    Intent intent = new Intent(OpenShopLogDetailActivity.this, SelectIMContactActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("id", String.valueOf(bean.getId()));
                    bundle.putString("orderNum", bean.getOrderNumber());
                    bundle.putString("creatTime", GetDateUtils.dateToDateTimeStringForChinse(bean.getCreateTime()));
                    bundle.putString("workerName", bean.getAssigneeUser().getAccountEntity().getRealName());
                    bundle.putString("status", String.valueOf(bean.getStatus()));
                    bundle.putString("shareType", "8");

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }

    /*
   * 毫秒转化时分秒毫秒
   */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (milliSecond > 0) {
            sb.append(milliSecond + "秒");
        }

        return sb.toString();
    }

    /**
     * 监听 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setResult(RESULT_OK);
            finishSelf();
        }
        return false;
    }
}
