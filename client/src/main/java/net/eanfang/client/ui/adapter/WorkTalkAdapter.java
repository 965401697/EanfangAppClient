package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.WorkTalkListBean;

import net.eanfang.client.R;

/**
 * 描述：面谈员工
 *
 * @author Guanluocang
 * @date on 2018/7/26$  17:21$
 */
public class WorkTalkAdapter extends BaseQuickAdapter<WorkTalkListBean.DataBean.ListBean, BaseViewHolder> {


    public WorkTalkAdapter() {
        super(R.layout.layout_item_worktalk);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkTalkListBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_worktalk_name, item.getId());
        if (item.getStatus() == 1) {
            helper.setText(R.id.tv_state, "已读");
        } else {
            helper.setText(R.id.tv_state, "未读");
        }
        helper.setText(R.id.tv_order_id, "编号：" + item.getId());
        helper.setText(R.id.tv_create_time, "创建时间：" + item.getId());
        helper.setText(R.id.tv_receiver_name, "接收人：" + item.getId());
        helper.setText(R.id.tv_telphone, "联系电话：" + item.getId());
    }
}