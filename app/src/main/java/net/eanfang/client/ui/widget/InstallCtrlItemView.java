package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.base.BaseDialog;
import com.eanfang.util.CallUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.model.InstallDetailRequestBean;
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
    private String ordernum;
    private Activity mContext;

    public InstallCtrlItemView(Activity context, String ordernum) {
        super(context);
        this.mContext = context;
        this.ordernum = ordernum;

    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_install_ctrl_item);
        ButterKnife.bind(this);
        initData();
    }


    private void initData() {

        InstallDetailRequestBean installDetailRequestBean = new InstallDetailRequestBean();
        installDetailRequestBean.setOrdernum(ordernum);
        Gson gson = new Gson();
        String json = gson.toJson(installDetailRequestBean);
        Log.e("json", json);
        EanfangHttp.get(ApiService.WORKSPACE_INSTALL_DETAIL)
                .tag(this)
                .params("json", json.toString())
                .execute(new EanfangCallback<WorkspaceInstallDetailBean>(context, false) {
                    @Override
                    public void onSuccess(WorkspaceInstallDetailBean bean) {
                        super.onSuccess(bean);
                        setData(bean);
                    }
                });
    }

    private void setData(WorkspaceInstallDetailBean bean) {
        WorkspaceInstallDetailBean workspaceInstallDetailBean = bean;
        WorkspaceInstallDetailBean.InstallorderBean installorderBean = workspaceInstallDetailBean.getInstallorder();
        tvCompanyName.setText(installorderBean.getClientcompanyname());
        tvContract.setText(installorderBean.getClientconnector());
        tvContractPhone.setText(installorderBean.getClientphone());
        tvTime.setText(installorderBean.getArrivetime());
        tvAddress.setText(installorderBean.getCity() + installorderBean.getZone() + installorderBean.getDetailplace());
        tvBusiness.setText(installorderBean.getBugonename());
        tvLimit.setText(installorderBean.getPredicttime());
        tvMoney.setText(installorderBean.getBudget());
        tvDesc.setText(installorderBean.getDescription());
        tvNumber.setText(installorderBean.getOrdernum());
        tvFeatureTime.setText(installorderBean.getCreatetime());
        tvWorkerName.setText(workspaceInstallDetailBean.getWorkerCompanyAdminname());
        tvWorkerCompany.setText(workspaceInstallDetailBean.getWorkerCompanyname());
        ivPic.setImageURI(Uri.parse(workspaceInstallDetailBean.getWorkerCompanyPic()));
        tvContractPhone.setTag(workspaceInstallDetailBean.getWorkerPhone());
        ivPhone.setOnClickListener(v -> CallUtils.call(mContext, tvContractPhone.getTag().toString()));
    }

}
