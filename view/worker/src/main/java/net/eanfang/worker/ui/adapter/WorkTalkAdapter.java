package net.eanfang.worker.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.WorkTalkListBean;

import net.eanfang.worker.R;

/**
 * 描述：面谈员工
 *
 * @author Guanluocang
 * @date on 2018/7/26$  17:21$
 */
public class WorkTalkAdapter extends BaseQuickAdapter<WorkTalkListBean.ListBean, BaseViewHolder> {
    private boolean mIsCreate = false;

    public WorkTalkAdapter(boolean isCreate) {
        super(R.layout.layout_item_worktalk);
        this.mIsCreate = isCreate;
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkTalkListBean.ListBean item) {
        if (!mIsCreate) {// 我创建的
            // 创建人
            helper.setText(R.id.tv_worktalk_name, item.getOwnerDepartmentEntity().getOrgName() + "(" + item.getOwnerUserEntity().getAccountEntity().getRealName() + ")");
            // 接收人
            helper.setText(R.id.tv_receiver_name, "接收人：" + item.getAssigneeUserEntity().getAccountEntity().getRealName());
            helper.setText(R.id.tv_contact, "联系汇报人");
        } else {// 我接收的
            // 创建人
            helper.setText(R.id.tv_worktalk_name, item.getAssigneeCompanyEntity().getOrgName() + "(" + item.getAssigneeUserEntity().getAccountEntity().getRealName() + ")");
            //接收人
            helper.setText(R.id.tv_receiver_name, "创建人：" + item.getOwnerUserEntity().getAccountEntity().getRealName());
            helper.setText(R.id.tv_contact, "联系接收人");
        }
        // 订单是否 已读 未读 1：新订单 0 已读
        if (item.getNewOrder() == 1) {
            helper.getView(R.id.tv_state).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_state).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_order_id, "编号：" + item.getOrderNum());
        helper.setText(R.id.tv_create_time, "创建时间：" + item.getCreateTime());
        helper.setText(R.id.tv_telphone, "联系电话：" + item.getAssigneeUserEntity().getAccountEntity().getMobile());
        helper.addOnClickListener(R.id.tv_seedetail);
        helper.addOnClickListener(R.id.tv_contact);
    }
}
