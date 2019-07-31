package net.eanfang.client.ui.adapter.datastatistics;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.datastatistics.DataStatisticsCompany;

import net.eanfang.client.R;

import java.util.List;


/**
 * 切换公司列表
 */

public class SwitchCompanyDataStatisticsListAdapter extends BaseQuickAdapter<DataStatisticsCompany, BaseViewHolder> {
    public SwitchCompanyDataStatisticsListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataStatisticsCompany item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "." + item.getOrgName());
        helper.addOnClickListener(R.id.tv_delete);
    }
}
