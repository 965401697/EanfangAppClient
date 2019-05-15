package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.WorkCompanyListBean;

import net.eanfang.worker.R;

import java.util.List;


/**
 * 我要报修中的选择技师的adapter
 * Created by Administrator on 2017/3/15.
 */

public class SearchCompanyAdapter extends BaseQuickAdapter<WorkCompanyListBean.ListBean, BaseViewHolder> {
    public SearchCompanyAdapter(List data) {
        super(R.layout.item_search_company, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkCompanyListBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getCompanyEntity().getName());
        helper.addOnClickListener(R.id.tv_set_manager);
    }
}
