package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.customview.SuperTextView;
import com.eanfang.biz.model.SgZzNlBean;

import net.eanfang.worker.R;

/**
 * @author wq
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
        superTextView.setLeftString(item.getBaseDataEntity().getDataName()+"");
        superTextView.setCenterString(item.getRemark()+"");
        superTextView.setRightString(item.getUnits()+"");
    }
}
