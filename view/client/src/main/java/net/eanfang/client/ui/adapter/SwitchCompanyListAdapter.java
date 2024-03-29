package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.OrgEntity;

import net.eanfang.client.R;


/**
 * 切换公司列表
 */

public class SwitchCompanyListAdapter extends BaseQuickAdapter<OrgEntity, BaseViewHolder> {
    public SwitchCompanyListAdapter() {
        super(R.layout.layout_item_select_company);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrgEntity item) {
        helper.setText(R.id.tv_detail_name, item.getOrgName());
    }
}
