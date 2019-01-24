package net.eanfang.worker.ui.activity.worksapce.online;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.worker.R;

/**
 * Created by Our on 2019/1/23.
 */

public class ExpertListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ExpertListAdapter() {
        super(R.layout.item_expert_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
      helper.addOnClickListener(R.id.tv_ask);
    }
}
