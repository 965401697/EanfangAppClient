package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.WorkAddCheckBean;

import net.eanfang.client.R;

import java.util.List;


/**
 * Created by wen on 2017/4/23.
 */

public class AddCheckDetailAdapter extends BaseQuickAdapter<WorkAddCheckBean.WorkInspectDetailsBean, BaseViewHolder> {
    public AddCheckDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkAddCheckBean.WorkInspectDetailsBean item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "." + item.getTitle());
        helper.addOnClickListener(R.id.tv_delete);
    }
}
