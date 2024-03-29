package net.eanfang.client.ui.adapter.worktransfer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.WorkTransferDetailBean;

import net.eanfang.client.R;

/**
 * 描述：交接班详情Adapter
 *
 * @author Guanluocang
 * @date on 2018/8/6$  16:23$
 */
public class WorkTransferDetailFollowThingAdapter extends BaseQuickAdapter<WorkTransferDetailBean.FollowUpEntityListBean, BaseViewHolder> {
    private boolean mIsDetail = false;

    public WorkTransferDetailFollowThingAdapter(boolean isDetail) {
        super(R.layout.layout_work_transfer_detail_item);
        this.mIsDetail = isDetail;
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkTransferDetailBean.FollowUpEntityListBean item) {
        helper.setText(R.id.tv_title, item.getContent());
        if (mIsDetail) {
            helper.setImageResource(R.id.iv_right_icon, R.mipmap.ic_delete_right);
            helper.addOnClickListener(R.id.iv_right_icon);
        } else {
            helper.setImageResource(R.id.iv_right_icon, R.mipmap.ic_icon_right);
        }
    }
}
