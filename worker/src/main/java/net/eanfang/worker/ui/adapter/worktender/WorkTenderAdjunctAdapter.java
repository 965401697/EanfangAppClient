package net.eanfang.worker.ui.adapter.worktender;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2019/1/11
 * @description 招标信息 附件
 */

public class WorkTenderAdjunctAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public WorkTenderAdjunctAdapter() {
        super(R.layout.layout_work_tender_adjunct);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, "附件" + helper.getAdapterPosition() + 1 + "：");
        helper.setText(R.id.tv_content, item);

    }
}
