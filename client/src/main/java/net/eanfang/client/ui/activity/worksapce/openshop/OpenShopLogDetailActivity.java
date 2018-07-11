package net.eanfang.client.ui.activity.worksapce.openshop;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.OpenShopLogBean;
import com.eanfang.model.OpenShopLogDetailBean;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.ToastUtil;
import com.yaf.base.entity.OpenShopLogEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenShopLogDetailActivity extends BaseClientActivity {


    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_section_name)
    TextView tvSectionName;
    @BindView(R.id.tv_accept_preson)
    TextView tvAcceptPreson;
    @BindView(R.id.tv_accept_phone)
    TextView tvAcceptPhone;
    @BindView(R.id.tv_staff_time)
    TextView tvStaffTime;
    @BindView(R.id.tv_staff_in_time)
    TextView tvStaffInTime;
    @BindView(R.id.tv_staff_out_time)
    TextView tvStaffOutTime;
    @BindView(R.id.tv_client_time)
    TextView tvClientTime;
    @BindView(R.id.tv_client_in_time)
    TextView tvClientInTime;
    @BindView(R.id.tv_client_out_time)
    TextView tvClientOutTime;
    @BindView(R.id.tv_receiving_time)
    TextView tvReceivingTime;
    @BindView(R.id.tv_open_time)
    TextView tvOpenTime;
    @BindView(R.id.tv_close_time)
    TextView tvCloseTime;
    @BindView(R.id.ev_faultDescripte)
    EditText evFaultDescripte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_shop_log_detail);
        ButterKnife.bind(this);
        setTitle("日志详情");
        setLeftBack();
        initData();
    }

    private void initData() {
        EanfangHttp.post(NewApiService.OA_OPEN_SHOP_DETAIL + "/" + getIntent().getStringExtra("id"))
                .execute(new EanfangCallback<OpenShopLogEntity>(this, true, OpenShopLogEntity.class, (bean) -> {
                    initViews(bean);
                }));
    }

    /**
     * 填充数据
     *
     * @param bean
     */
    private void initViews(OpenShopLogEntity bean) {
        tvCompanyName.setText(bean.getOwnerCompany().getOrgName());
        tvSectionName.setText(bean.getOwnerDepartment().getOrgName());
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
}
