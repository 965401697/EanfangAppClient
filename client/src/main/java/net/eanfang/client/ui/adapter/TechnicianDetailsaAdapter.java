package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;


/**
 * @author wq
 * @data 2018/10/23
 */

public class TechnicianDetailsaAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private boolean isDelete;

    public TechnicianDetailsaAdapter(boolean mDelete) {
        super(R.layout.layout_item_jse);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.a_tv, item+"");

    }
}
