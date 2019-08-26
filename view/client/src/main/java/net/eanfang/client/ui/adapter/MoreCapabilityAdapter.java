package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.base.widget.customview.SuperTextView;
import com.eanfang.biz.model.bean.SgZzNlBean;

import net.eanfang.client.R;


/**
 * @data 2018/10/23
 * @description
 */

public class MoreCapabilityAdapter extends BaseQuickAdapter<SgZzNlBean.ListBean, BaseViewHolder> {
    private boolean isDelete;

    public MoreCapabilityAdapter(boolean mDelete) {
        super(R.layout.layout_item_more_capability);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, SgZzNlBean.ListBean item) {
        SuperTextView superTextView = helper.getView(R.id.item_stv);
        superTextView.setLeftString(item.getDataName() + "");
        superTextView.setCenterString(item.getRemark() + "");
        superTextView.setRightString(item.getUnits() + "");
    }
}
