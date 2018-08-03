package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.WorkTalkListBean;
import com.eanfang.model.WorkTransferListBean;

import net.eanfang.client.R;

/**
 * 描述：交接班
 *
 * @author Guanluocang
 * @date on 2018/7/26$  17:21$
 */
public class WorkTransferAdapter extends BaseQuickAdapter<WorkTransferListBean.ListBean, BaseViewHolder> {


    public WorkTransferAdapter() {
        super(R.layout.layout_item_worktalk);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkTransferListBean.ListBean item) {
        helper.setText(R.id.tv_worktalk_name, item.getOwnerUserEntity().getDepartmentEntity().getOrgName() + "(" + item.getOwnerUserEntity().getAccountEntity().getNickName() + ")");
        if (item.getStatus() == 1) {
            helper.setText(R.id.tv_state, "已读");
        } else {
            helper.setText(R.id.tv_state, "未读");
        }
        helper.setText(R.id.tv_order_id, "编号：" + item.getOrderNum());
        helper.setText(R.id.tv_create_time, "创建时间：" + item.getCreateTime());
        helper.setText(R.id.tv_receiver_name, "接收人：" + item.getAssigneeUserEntity().getAccountEntity().getNickName());
        helper.setText(R.id.tv_telphone, "联系电话：" + item.getAssigneeUserEntity().getAccountEntity().getMobile());
        helper.addOnClickListener(R.id.tv_seedetail);
        helper.addOnClickListener(R.id.tv_contact);
    }
}
