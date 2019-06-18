package net.eanfang.worker.ui.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.viewholder.NewOrderScreenViewHolder;

/**
 * @author liangkailun
 * Date ：2019-05-30
 * Describe :最新订单筛选adapter
 */
public class NewOrderScreenAdapter extends BaseQuickAdapter<BaseDataEntity, NewOrderScreenViewHolder> {

    public NewOrderScreenAdapter() {
        super(R.layout.item_new_order_screen);
    }

    @Override
    protected void convert(NewOrderScreenViewHolder helper, BaseDataEntity item) {
        helper.getSystemType().setText(item.getDataName());
        helper.addOnClickListener(R.id.btn_system_type);
    }
}
