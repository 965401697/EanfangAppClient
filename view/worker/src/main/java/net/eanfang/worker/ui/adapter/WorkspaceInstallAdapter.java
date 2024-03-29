package net.eanfang.worker.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.biz.model.bean.WorkspaceInstallBean;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.worker.R;

import static com.eanfang.base.kit.V.v;

/**
 * 工作台-我要报装adapter
 * Created by Administrator on 2017/3/15.
 */

public class WorkspaceInstallAdapter extends BaseQuickAdapter<WorkspaceInstallBean.ListBean, BaseViewHolder> {
    public WorkspaceInstallAdapter() {
        super(R.layout.item_workspace_install_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkspaceInstallBean.ListBean item) {
        // 订单是否 已读 未读 1：新订单 0 已读
        if (item.getNewOrder() == 1) {
            helper.getView(R.id.tv_order_read).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_order_read).setVisibility(View.GONE);
        }
        //报装列表 增加 联系人
        helper.setText(R.id.tv_company_name, v(() -> item.getClientCompanyName() + "  (" + item.getConnector() + ")"))
                .setText(R.id.tv_order_id, "单号：" + v(() -> item.getOrderNo()))
                //下单时间 显示错误 已修改
                .setText(R.id.tv_order, "下单：" + v(() -> item.getCreateTime()))
                .setText(R.id.tv_time, "工期：" + v(() -> Config.get().getConstBean().getData()
                        .getDesignOrderConstant().get(Constant.PREDICTTIME_TYPE).get(item.getPredictTime())))
                .setText(R.id.tv_business, "业务：" + v(() -> Config.get().getBusinessNameByCode(item.getBusinessOneCode(), 1)))
                .setText(R.id.tv_count_money, v(() -> GetConstDataUtils.getBudgetList().get(item.getBudget())));
        helper.setGone(R.id.iv_upload, false);

    }
}
