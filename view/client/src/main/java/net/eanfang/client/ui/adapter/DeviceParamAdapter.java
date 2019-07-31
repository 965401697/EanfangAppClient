package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.QuotationBean;

import net.eanfang.client.R;

import java.util.ArrayList;

import cn.hutool.core.util.StrUtil;


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
        if (StrUtil.isNotBlank(item.getParamName())) {
            helper.setText(R.id.et_param_name, item.getParamName());
        }

        if (StrUtil.isNotBlank(item.getParamValue())) {
            helper.setText(R.id.et_param_value, item.getParamValue());
        }
        helper.addOnClickListener(R.id.tv_delete);

    }
}
