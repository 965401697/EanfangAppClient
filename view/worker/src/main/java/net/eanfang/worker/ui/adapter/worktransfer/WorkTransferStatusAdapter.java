package net.eanfang.worker.ui.adapter.worktransfer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.worker.R;


/**
 * 描述：交接班状态选择（班次 状态等）
 *
 * @author Guanluocang
 * @date on 2018/8/7$  17:07$
 */
public class WorkTransferStatusAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public WorkTransferStatusAdapter() {
        super(R.layout.item_quotation_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_detail_name, item);
    }
}
