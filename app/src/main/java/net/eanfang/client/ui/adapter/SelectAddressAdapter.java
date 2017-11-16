package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.SelectAddressItem;

import java.util.List;


/**
 * 发送位置的adapter
 * Created by Administrator on 2017/3/15.
 */

public class SelectAddressAdapter extends BaseQuickAdapter<SelectAddressItem, BaseViewHolder> {
    public SelectAddressAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectAddressItem item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_address, item.getAddress());
    }
}
