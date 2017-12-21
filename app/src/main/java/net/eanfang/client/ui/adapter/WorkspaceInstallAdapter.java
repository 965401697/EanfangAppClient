package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.WorkspaceInstallBean;

import java.util.List;

/**
 * 工作台-我要报装adapter
 * Created by Administrator on 2017/3/15.
 */

public class WorkspaceInstallAdapter extends BaseQuickAdapter<WorkspaceInstallBean.ListBean, BaseViewHolder> {
    public WorkspaceInstallAdapter(List<WorkspaceInstallBean.ListBean> data) {
        super(R.layout.item_workspace_install_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkspaceInstallBean.ListBean item) {
        //报装列表 增加 联系人
        helper.setText(R.id.tv_company_name, item.getClientCompanyName() + "  (" + item.getConnector() + ")")
                .setText(R.id.tv_order_id, "单号：" + item.getOrderNo())
                //下单时间 显示错误 已修改
                .setText(R.id.tv_order, "下单：" + item.getCreateTime())
                .setText(R.id.tv_time, "工期：" + item.getPredictTime())
                .setText(R.id.tv_business, "业务：" + item.getBusinessOneCode());
//                .setText(R.id.tv_count_money, item.getBudget());

        //将业务类型的图片显示到列表
//        ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(item.getPic1());
    }
}
