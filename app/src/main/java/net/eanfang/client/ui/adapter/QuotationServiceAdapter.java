package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.QuotationBean;

import java.util.List;

/**
 * Created by wen on 2017/4/23.
 */

public class QuotationServiceAdapter extends BaseQuickAdapter<QuotationBean.QuoteServicesBean, BaseViewHolder> {
    public QuotationServiceAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuotationBean.QuoteServicesBean item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "." + item.getServiceName() + "-小计：￥" + item.getSum());
        helper.addOnClickListener(R.id.tv_delete);
    }
}
