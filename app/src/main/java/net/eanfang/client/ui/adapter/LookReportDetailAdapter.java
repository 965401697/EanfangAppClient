package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.WorkReportInfoBean;

import java.util.List;


/**
 * Created by wen on 2017/4/23.
 */

public class LookReportDetailAdapter extends BaseQuickAdapter<WorkReportInfoBean.WorkReportDetailsBean, BaseViewHolder> {
    public LookReportDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkReportInfoBean.WorkReportDetailsBean item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "." + item.getField1());
        helper.addOnClickListener(R.id.tv_delete);
    }
}
