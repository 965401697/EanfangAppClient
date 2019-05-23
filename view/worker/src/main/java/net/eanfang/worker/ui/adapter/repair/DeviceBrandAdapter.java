package net.eanfang.worker.ui.adapter.repair;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;


/**
 * @author guanluocang
 * @data 2018/10/15
 * @description
 * 已提取
 */

public class DeviceBrandAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public DeviceBrandAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (!StringUtils.isEmpty(item)) {
            helper.setText(R.id.tv_item, item);
        }
    }
}
