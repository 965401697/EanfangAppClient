package net.eanfang.client.ui.adapter.monitor;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.WorkAddReportBean;
import com.eanfang.biz.model.bean.WorkTaskInfoBean;

import net.eanfang.client.R;

import java.util.List;

/**
 * @author guanluocang
 * @data 2019/9/9
 * @description 实时监控 列表 左边的
 */
public class MonitorListLeftAdapter extends BaseMultiItemQuickAdapter<WorkTaskInfoBean.WorkTaskDetailsBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MonitorListLeftAdapter(List<WorkTaskInfoBean.WorkTaskDetailsBean> data) {
        super(data);
        addItemType(WorkAddReportBean.WorkReportDetailsBean.FOLD, R.layout.layout_monitor_left_one);
        addItemType(WorkAddReportBean.WorkReportDetailsBean.EXPAND, R.layout.layout_monitor_left_second);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkTaskInfoBean.WorkTaskDetailsBean item) {

    }
}
