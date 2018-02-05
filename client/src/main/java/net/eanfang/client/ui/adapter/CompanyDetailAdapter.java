package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;

import java.util.ArrayList;


/**
 * 报装公司详情的adapter
 * Created by Administrator on 2017/3/15.
 */

public class CompanyDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CompanyDetailAdapter(int layoutResId, ArrayList data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item1, item);
    }
}
