package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.QuotationBean;
import net.eanfang.client.util.StringUtils;

import java.util.ArrayList;


/**
 * 报装公司详情的adapter
 * Created by Administrator on 2017/3/15.
 */

public class DeviceParamAdapter extends BaseQuickAdapter<QuotationBean.QuoteDevicesBean.ParamsBean, BaseViewHolder> {

    public DeviceParamAdapter(int layoutResId, ArrayList data) {
        super(layoutResId, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, final QuotationBean.QuoteDevicesBean.ParamsBean item) {
        if (StringUtils.isValid(item.getParamName())) {
            helper.setText(R.id.et_param_name, item.getParamName());
        }

        if (StringUtils.isValid(item.getParamValue())) {
            helper.setText(R.id.et_param_value, item.getParamValue());
        }
        helper.addOnClickListener(R.id.tv_delete);

    }
}
