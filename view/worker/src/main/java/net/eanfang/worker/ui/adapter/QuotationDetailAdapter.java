package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.eanfang.biz.model.QuotationBean;

import net.eanfang.worker.R;

import java.util.List;

/**
 * Created by wen on 2017/4/23.
 */

public class QuotationDetailAdapter extends BaseQuickAdapter<QuotationBean.QuoteDevicesBean, BaseViewHolder> {
    public QuotationDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuotationBean.QuoteDevicesBean item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "."
                + Config.get().getBusinessNameByCode(item.getBusiness_three_code(), 1) + "-小计：￥" +
                item.getSum()/100);
        //        (double)(Math.round(result_value*100)/100.0)

        helper.addOnClickListener(R.id.tv_delete);
    }
}
