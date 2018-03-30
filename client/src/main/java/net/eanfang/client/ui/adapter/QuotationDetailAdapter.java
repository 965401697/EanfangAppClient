package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.model.QuotationBean;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.client.R;

import java.util.List;

/**
 * Created by wen on 2017/4/23.
 */

public class QuotationDetailAdapter extends BaseQuickAdapter<QuotationBean.QuoteDevicesBean, BaseViewHolder> {

    private Config config = Config.get(EanfangApplication.get().getApplicationContext());
    private GetConstDataUtils constDataUtils = GetConstDataUtils.get(config);

    public QuotationDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuotationBean.QuoteDevicesBean item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "."
                + config.getModelNameByCode(item.getModelCode(), 1) + "-小计：￥" +
                item.getSum() / 100);
        helper.addOnClickListener(R.id.tv_delete);
    }
}
