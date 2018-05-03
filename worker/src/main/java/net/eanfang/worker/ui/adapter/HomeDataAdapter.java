package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.ClientData;


import net.eanfang.worker.R;

import java.util.List;

/**
 * Created by admin on 2018/4/11.
 */

public class HomeDataAdapter extends BaseQuickAdapter<ClientData, BaseViewHolder> {
    public HomeDataAdapter(List data) {
        super(R.layout.layout_home_data, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClientData item) {
        if (item.getType() == 1) {//三级
            helper.setText(R.id.tv_title, "三级");
        } else if (item.getType() == 2) {//二级
            helper.setText(R.id.tv_title, "二级");
        } else {//一级
            helper.setText(R.id.tv_title, "一级");
        }
        helper.setText(R.id.tv_total, item.getTotal() + "");
        helper.setText(R.id.tv_add, item.getAdded() + "");
        helper.setText(R.id.tv_repairOne, item.getStatusOne() + "");
        helper.setText(R.id.tv_repairTwo, item.getStatusTwo() + "");
        helper.setText(R.id.tv_repairThree, item.getStatusThree() + "");
    }
}
