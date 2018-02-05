package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.worker.R;

import java.util.List;


/**
 * 技师详情的adapter
 * Created by Administrator on 2017/3/15.
 */

public class WorkerDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public WorkerDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item1, item);
    }
}
