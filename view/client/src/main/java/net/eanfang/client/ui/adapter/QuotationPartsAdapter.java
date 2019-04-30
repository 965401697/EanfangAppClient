package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.QuotationBean;

import net.eanfang.client.R;

import java.util.List;

/**
 * Created by wen on 2017/4/23.
 */

public class QuotationPartsAdapter extends BaseQuickAdapter<QuotationBean.QuotePartsBean, BaseViewHolder> {
    public QuotationPartsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuotationBean.QuotePartsBean item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "." + item.getPartName() + "-小计：￥" + item.getSum()/100);
        helper.addOnClickListener(R.id.tv_delete);
    }
}
