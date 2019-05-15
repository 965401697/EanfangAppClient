package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.QuotationBean;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;

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
        helper.setVisible(R.id.tv_delete, false);
        helper.addOnClickListener(R.id.tv_delete);

    }
}
