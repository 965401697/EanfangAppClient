package net.eanfang.client.ui.adapter.repair;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;

import cn.hutool.core.util.StrUtil;

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
        if (!StrUtil.isEmpty(item)) {
            helper.setText(R.id.tv_item, item);
        }
    }
}
