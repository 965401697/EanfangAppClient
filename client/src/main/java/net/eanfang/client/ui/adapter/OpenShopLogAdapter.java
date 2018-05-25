package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.application.EanfangApplication;
import com.eanfang.model.OpenShopLogBean;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/5/22.
 */

public class OpenShopLogAdapter extends BaseQuickAdapter<OpenShopLogBean.ListBean, BaseViewHolder> {
    public OpenShopLogAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, OpenShopLogBean.ListBean item) {
        helper.setText(R.id.tv_company_name, item.getCreateOrg().getOrgName() + "(" + item.getCreateUser().getAccountEntity().getRealName() + ")");
        if (item.getStatus() == 0) {
            helper.setText(R.id.tv_state, "未读");
        } else {
            helper.setText(R.id.tv_state, "已读");
        }
        helper.setText(R.id.tv_order_id, "订单编号：" + item.getOrderNumber());
        helper.setText(R.id.tv_create_time, "创建时间：" + item.getCreateTime());
        helper.setText(R.id.tv_person_name, "接收人：" + item.getAssigneeUser().getAccountEntity().getRealName());
        helper.setText(R.id.tv_preson_phone, "联系电话：" + item.getAssigneeUser().getAccountEntity().getMobile());

        helper.addOnClickListener(R.id.tv_do_first);
    }
}
