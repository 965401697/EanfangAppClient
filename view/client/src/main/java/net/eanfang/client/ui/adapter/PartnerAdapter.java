package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.PartnerBean;

import net.eanfang.client.R;

import java.util.List;

/**
 * 我的-评价的adapter
 * Created by Administrator on 2017/3/15.
 */
public class PartnerAdapter extends BaseQuickAdapter<PartnerBean.ListBean, BaseViewHolder> {
    public PartnerAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PartnerBean.ListBean item) {
        helper.setText(R.id.tv_detail_name, item.getOwnerOrg().getOrgName());

    }
}
