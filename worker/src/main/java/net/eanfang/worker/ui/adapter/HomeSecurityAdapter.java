package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2019/1/28
 * @description 安防圈adapter
 */

public class HomeSecurityAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HomeSecurityAdapter() {
        super(R.layout.layout_security_item);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item);
    }
}
