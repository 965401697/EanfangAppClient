package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2019/1/24
 * @description
 */

public class HomeWaitAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HomeWaitAdapter() {
        super(R.layout.layout_home_wait_item);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item);
    }

}
