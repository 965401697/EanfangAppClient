package net.eanfang.client.ui.activity.worksapce.install;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.WorkspaceInstallDetailBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.V;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/5/10  16:53
 * @decision 报装详情
 */
public class InstallOrderDetailActivity extends BaseActivity {

    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_contract)
    TextView tvContract;
    @BindView(R.id.tv_contract_phone)
    TextView tvContractPhone;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_business)
    TextView tvBusiness;
    @BindView(R.id.tv_limit)
    TextView tvLimit;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.iv_pic)
    CircleImageView ivPic;
    @BindView(R.id.tv_worker_name)
    TextView tvWorkerName;
    @BindView(R.id.tv_worker_company)
    TextView tvWorkerCompany;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_feature_time)
    TextView tvFeatureTime;
    @BindView(R.id.ll_company_info)
    LinearLayout llCompanyInfo;
    private Long orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_install_order_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setLeftBack();
        setTitle("报装详情");
        initData();
    }

    private void initData() {
        orderId = getIntent().getLongExtra("orderId", 0);
        EanfangHttp.get(NewApiService.GET_WORK_INSTALL_INFO)
                .tag(this)
                .params("id", orderId)
                .execute(new EanfangCallback<WorkspaceInstallDetailBean>(this, false, WorkspaceInstallDetailBean.class, (bean) -> {
                    setData(bean);
                }));
    }

    private void setData(WorkspaceInstallDetailBean bean) {

        tvCompanyName.setText(bean.getClientCompanyName());
        tvContract.setText(bean.getConnector());
        tvContractPhone.setText(bean.getConnectorPhone());

        tvTime.setText(V.v(() -> GetConstDataUtils.getRevertList().get(bean.getRevertTimeLimit())));
        String area = Config.get().getAddressByCode(bean.getZone());
        tvAddress.setText(area + bean.getDetailPlace());
        tvBusiness.setText(Config.get().getBusinessNameByCode(bean.getBusinessOneCode(), 1));
        tvLimit.setText(GetConstDataUtils.getPredictList().get(bean.getPredictTime()));
        tvMoney.setText(GetConstDataUtils.getBudgetList().get(bean.getBudget()));
        tvDesc.setText(bean.getDescription());
        tvNumber.setText(bean.getOrderNo());
        tvFeatureTime.setText(bean.getCreateTime());
        if (bean.getAssignessUser() != null) {
            llCompanyInfo.setVisibility(View.VISIBLE);
            tvWorkerName.setText(bean.getAssignessUser().getAccountEntity().getRealName());
            tvContractPhone.setTag(bean.getAssignessUser().getAccountEntity().getMobile());
            ivPhone.setOnClickListener(v -> CallUtils.call(this, tvContractPhone.getTag().toString()));
            tvWorkerCompany.setText(bean.getCompanyEntity().getName());
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + bean.getCompanyEntity().getLogoPic()),ivPic);
        }

    }
}
