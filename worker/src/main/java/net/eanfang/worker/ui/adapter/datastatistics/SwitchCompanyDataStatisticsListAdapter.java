package net.eanfang.worker.ui.adapter.datastatistics;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.datastatistics.DataStatisticsCompany;

import net.eanfang.worker.R;

import java.util.List;


/**
 * 切换公司列表
 */

public class SwitchCompanyDataStatisticsListAdapter extends BaseQuickAdapter<DataStatisticsCompany.ListBean, BaseViewHolder> {
    public SwitchCompanyDataStatisticsListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataStatisticsCompany.ListBean item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "." + item.getCompanyEntity().getName());
        helper.addOnClickListener(R.id.tv_delete);
    }
}
