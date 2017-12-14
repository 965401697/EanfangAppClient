package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.WorkAddReportBean;

import java.util.List;


/**
 *万万没想到，啦啦啦啦啦啦
 */

public class AddReportDetailAdapter extends BaseQuickAdapter<WorkAddReportBean.WorkReportDetailsBean, BaseViewHolder> {
    public AddReportDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkAddReportBean.WorkReportDetailsBean item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "." + item.getField1());
        helper.addOnClickListener(R.id.tv_delete);
    }
}
