package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.WorkTaskBean;

import java.util.List;



/**
 * Created by wen on 2017/4/23.
 */

public class AddTaskDetailAdapter extends BaseQuickAdapter<WorkTaskBean.WorkTaskDetailsBean, BaseViewHolder> {
    public AddTaskDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkTaskBean.WorkTaskDetailsBean item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "." + item.getTitle());
        helper.addOnClickListener(R.id.tv_delete);
    }
}
