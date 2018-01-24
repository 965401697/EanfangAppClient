package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.ExternalListBean;

import java.util.List;

/**
 * 我的-评价的adapter
 * Created by Administrator on 2017/3/15.
 */
public class ExternalAdapter extends BaseQuickAdapter<ExternalListBean, BaseViewHolder> {
    public ExternalAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExternalListBean item) {
        helper.setText(R.id.tv_detail_name, item.getOrgName());

    }
}
