package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.model.WorkspaceInstallBean;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.worker.R;

import java.util.List;

import static com.eanfang.util.V.v;

/**
 * 工作台-我要报装adapter
 * Created by Administrator on 2017/3/15.
 */

public class WorkspaceInstallAdapter extends BaseQuickAdapter<WorkspaceInstallBean.ListBean, BaseViewHolder> {
    public Config config;
    public GetConstDataUtils constDataUtils;

    public WorkspaceInstallAdapter(List<WorkspaceInstallBean.ListBean> data) {
        super(R.layout.item_workspace_install_list, data);
        config = Config.get(EanfangApplication.get().getApplicationContext());
        constDataUtils = GetConstDataUtils.get(config);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkspaceInstallBean.ListBean item) {
        //报装列表 增加 联系人
        helper.setText(R.id.tv_company_name, v(() -> item.getClientCompanyName() + "  (" + item.getConnector() + ")"))
                .setText(R.id.tv_order_id, "单号：" + v(() -> item.getOrderNo()))
                //下单时间 显示错误 已修改
                .setText(R.id.tv_order, "下单：" + v(() -> item.getCreateTime()))
                .setText(R.id.tv_time, "工期：" + v(() -> config.getConstBean().getData()
                        .getDesignOrderConstant().get(Constant.PREDICTTIME_TYPE).get(item.getPredictTime())))
                .setText(R.id.tv_business, "业务：" + v(() -> config.getBusinessNameByCode(item.getBusinessOneCode(), 1)))
                .setText(R.id.tv_count_money, v(() -> constDataUtils.getBudgetList().get(item.getBudget())));
        helper.setVisible(R.id.iv_upload, false);

    }
}
