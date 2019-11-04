package net.eanfang.worker.ui.adapter.neworder;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2019/10/28
 * @description 首页订单Adapter
 */
public class HomeOrderApdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HomeOrderApdapter() {
        super(R.layout.layout_home_new_order_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
