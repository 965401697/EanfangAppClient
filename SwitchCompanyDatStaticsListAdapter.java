package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaf.sys.entity.OrgEntity;

import net.eanfang.worker.R;

import java.util.List;


/**
 * 切换公司列表
 */

public class SwitchCompanyListAdapter extends BaseQuickAdapter<OrgEntity, BaseViewHolder> {
    public SwitchCompanyListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrgEntity item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "." + item.getOrgName());
        helper.addOnClickListener(R.id.tv_delete);
    }
}
