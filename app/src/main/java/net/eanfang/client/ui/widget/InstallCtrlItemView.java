package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.base.BaseDialog;
import com.eanfang.util.CallUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.config.Config;
import net.eanfang.client.config.Constant;
import net.eanfang.client.network.apiservice.NewApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.model.WorkspaceInstallDetailBean;

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
    private Long id;
    private Activity mContext;

    public InstallCtrlItemView(Activity context, Long id) {
        super(context);
        this.mContext = context;
        this.id = id;

    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_install_ctrl_item);
        ButterKnife.bind(this);
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
        tvTime.setText(Config.getConfig().getConstBean().getDesignOrderConstant().
                get(Constant.REVERT_TIME_LIMIT_TYPE).get(bean.getRevertTimeLimit()));
        String area = Config.getConfig().getAddress(bean.getZone());
        tvAddress.setText(area + bean.getDetailPlace());
        tvBusiness.setText(Config.getConfig().getBusinessName(bean.getBusinessOneCode()));
        tvLimit.setText(Config.getConfig().getConstBean().getDesignOrderConstant().
                get(Constant.PREDICTTIME_TYPE).get(bean.getPredictTime()));
        tvMoney.setText(Config.getConfig().getConstBean().getDesignOrderConstant().
                get(Constant.BUDGET_LIMIT_TYPE).get(bean.getBudget()));
        tvDesc.setText(bean.getDescription());
        tvNumber.setText(bean.getOrderNo());
        tvFeatureTime.setText(bean.getCreateTime());
        tvWorkerName.setText(bean.getAssignessUser().getAccountEntity().getRealName());
        tvWorkerCompany.setText(bean.getCompanyEntity().getName());
        ivPic.setImageURI(Uri.parse(bean.getCompanyEntity().getLogoPic()));
        tvContractPhone.setTag(bean.getAssignessUser().getAccountEntity().getMobile());
        ivPhone.setOnClickListener(v -> CallUtils.call(mContext, tvContractPhone.getTag().toString()));
    }

}
