package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.WorkspaceInstallDetailBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetConstDataUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/21  11:23
 * @email houzhongzhou@yeah.net
 * @desc 报装详情
 */

public class InstallCtrlItemView extends BaseDialog {
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
    SimpleDraweeView ivPic;
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
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Long id;
    private Activity mContext;

    public InstallCtrlItemView(Activity context, boolean isFull, Long id) {
        super(context, isFull);
        this.mContext = context;
        this.id = id;

    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_install_ctrl_item);
        ButterKnife.bind(this);
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("报装详情");
        initData();
    }


    private void initData() {
        EanfangHttp.get(NewApiService.GET_WORK_INSTALL_INFO)
                .tag(this)
                .params("id", id)
                .execute(new EanfangCallback<WorkspaceInstallDetailBean>(context, false, WorkspaceInstallDetailBean.class, (bean) -> {
                    setData(bean);
                }));
    }

    private void setData(WorkspaceInstallDetailBean bean) {

        tvCompanyName.setText(bean.getClientCompanyName());
        tvContract.setText(bean.getConnector());
        tvContractPhone.setText(bean.getConnectorPhone());
        tvTime.setText(GetConstDataUtils.getRevertList().get(bean.getRevertTimeLimit()));
        String area = Config.get().getAddressByCode(bean.getZone());
        tvAddress.setText(area + bean.getDetailPlace());
        tvBusiness.setText(Config.get().getBusinessNameByCode(bean.getBusinessOneCode(), 1));
        tvLimit.setText(GetConstDataUtils.getPredictList().get(bean.getPredictTime()));
        tvMoney.setText(GetConstDataUtils.getBudgetList().get(bean.getBudget()));
        tvDesc.setText(bean.getDescription());
        tvNumber.setText(bean.getOrderNo());
        tvFeatureTime.setText(bean.getCreateTime());
        if (bean.getConnector() != null) {
            tvWorkerName.setText(bean.getConnector());
        }
        if (bean.getCompanyEntity() != null) {
            tvWorkerCompany.setText(bean.getCompanyEntity().getName());
        }
        if (bean.getCompanyEntity() != null && bean.getCompanyEntity().getLogoPic() != null) {
            ivPic.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + bean.getCompanyEntity().getLogoPic()));
        }
        ivPhone.setOnClickListener(v -> CallUtils.call(mContext, bean.getConnectorPhone()));
    }

}
