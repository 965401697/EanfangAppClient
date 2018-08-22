package net.eanfang.client.ui.adapter.worktransfer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.WorkTalkListBean;
import com.eanfang.model.WorkTransferListBean;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.client.R;

/**
 * 描述：交接班
 *
 * @author Guanluocang
 * @date on 2018/7/26$  17:21$
 */
public class WorkTransferAdapter extends BaseQuickAdapter<WorkTransferListBean.ListBean, BaseViewHolder> {
    private boolean mIsCreate = false;

    public WorkTransferAdapter(boolean isCreate) {
        super(R.layout.layout_item_worktalk);
        this.mIsCreate = isCreate;
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkTransferListBean.ListBean item) {
        if (!mIsCreate) {// 我创建的
            // 创建人
            helper.setText(R.id.tv_worktalk_name, item.getOwnerDepartmentEntity().getOrgName() + "(" + item.getOwnerUserEntity().getAccountEntity().getRealName() + ")");
            // 接收人
            helper.setText(R.id.tv_receiver_name, "接收人：" + item.getAssigneeUserEntity().getAccountEntity().getRealName());
        } else {// 我接收的
            // 创建人
            helper.setText(R.id.tv_worktalk_name, item.getAssigneeCompanyEntity().getOrgName() + "(" + item.getAssigneeUserEntity().getAccountEntity().getRealName() + ")");
            // 接收人
            helper.setText(R.id.tv_receiver_name, "创建人：" + item.getOwnerUserEntity().getAccountEntity().getRealName());
        }
        helper.setText(R.id.tv_state, GetConstDataUtils.getWorkTransferList().get(item.getStatus()));
        helper.setText(R.id.tv_order_id, "编号：" + item.getOrderNum());
        helper.setText(R.id.tv_create_time, "创建时间：" + item.getCreateTime());
        helper.setText(R.id.tv_telphone, "联系电话：" + item.getAssigneeUserEntity().getAccountEntity().getMobile());
        helper.addOnClickListener(R.id.tv_seedetail);
        helper.addOnClickListener(R.id.tv_contact);
    }
}
