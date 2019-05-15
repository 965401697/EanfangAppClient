package net.eanfang.client.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.biz.model.WorkspaceInstallBean;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.V;

import net.eanfang.client.R;

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
        helper.setText(R.id.tv_company_name, item.getClientCompanyName() + "  (" + item.getConnector() + ")")
                .setText(R.id.tv_order_id, "单号：" + item.getOrderNo())
                //下单时间 显示错误 已修改
                .setText(R.id.tv_order, "下单：" + V.v(() -> item.getCreateTime()))
                .setText(R.id.tv_time, "工期：" + Config.get().getConstBean().getData()
                        .getDesignOrderConstant().get(Constant.PREDICTTIME_TYPE).get(V.v(() -> item.getPredictTime())))
                .setText(R.id.tv_count_money, Config.get().getConstBean().getData().getDesignOrderConstant().get(Constant.BUDGET_LIMIT_TYPE).get(V.v(() -> item.getBudget())))
                .setText(R.id.tv_business, "业务：" + Config.get().getBusinessNameByCode(V.v(() -> item.getBusinessOneCode()), 1));
        if (item.getStatus() == 2) {
            if (item.getCreateUserId().equals(EanfangApplication.getApplication().getUserId())) {
                helper.getView(R.id.tv_finish).setVisibility(View.VISIBLE);
            } else {
                ToastUtil.get().showToast(mContext, "只有创建人可操作");
            }

        } else {
            helper.setText(R.id.tv_look, "查看");
        }

        helper.addOnClickListener(R.id.tv_look);
        helper.addOnClickListener(R.id.tv_finish);
        //将业务类型的图片显示到列表
//        ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(item.getPic1());
    }
}
