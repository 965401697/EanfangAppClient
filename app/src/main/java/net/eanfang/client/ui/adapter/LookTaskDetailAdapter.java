package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.WorkTaskInfoBean;

import java.util.List;



/**
 * Created by wen on 2017/4/23.
 */

public class LookTaskDetailAdapter extends BaseQuickAdapter<WorkTaskInfoBean.WorkTaskDetailsBean, BaseViewHolder> {
    public LookTaskDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkTaskInfoBean.WorkTaskDetailsBean item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "." + item.getTitle());
        helper.addOnClickListener(R.id.tv_delete);
    }
}
