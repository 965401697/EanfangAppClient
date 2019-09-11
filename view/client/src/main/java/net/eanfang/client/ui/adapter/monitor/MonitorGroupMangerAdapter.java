package net.eanfang.client.ui.adapter.monitor;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.WorkTaskInfoBean;

import java.util.List;

/**
 * @author guanluocang
 * @data 2019/9/9
 * @description  实时监控 F分组管理
 */
public class MonitorGroupMangerAdapter extends BaseMultiItemQuickAdapter<WorkTaskInfoBean.WorkTaskDetailsBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MonitorGroupMangerAdapter(List<WorkTaskInfoBean.WorkTaskDetailsBean> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkTaskInfoBean.WorkTaskDetailsBean item) {

    }
}
