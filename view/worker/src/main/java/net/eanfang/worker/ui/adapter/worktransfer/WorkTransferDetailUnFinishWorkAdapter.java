package net.eanfang.worker.ui.adapter.worktransfer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.WorkTransferDetailBean;

import net.eanfang.worker.R;


/**
 * 描述：交接班详情Adapter
 *
 * @author Guanluocang
 * @date on 2018/8/6$  16:23$
 */
public class WorkTransferDetailUnFinishWorkAdapter extends BaseQuickAdapter<WorkTransferDetailBean.NotDidEntityListBean, BaseViewHolder> {
    private boolean mIsDetail = false;

    public WorkTransferDetailUnFinishWorkAdapter(boolean isDetail) {
        super(R.layout.layout_work_transfer_detail_item);
        this.mIsDetail = isDetail;
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkTransferDetailBean.NotDidEntityListBean item) {
        helper.setText(R.id.tv_title, item.getContent());
        if (mIsDetail) {
            helper.setImageResource(R.id.iv_right_icon, R.mipmap.ic_delete_right);
            helper.addOnClickListener(R.id.iv_right_icon);
        } else {
            helper.setImageResource(R.id.iv_right_icon, R.mipmap.ic_icon_right);
        }
    }
}
