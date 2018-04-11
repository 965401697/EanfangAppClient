package net.eanfang.client.ui.adapter;

import com.amap.api.interfaces.IText;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.ClientData;

import net.eanfang.client.R;

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
        if (item.getType() == 1) {//报修
            helper.setText(R.id.tv_title, "当日报修");
        } else if (item.getType() == 2) {//报装
            helper.setText(R.id.tv_title, "当日报装");
        } else if (item.getType() == 3) {//报价
            helper.setText(R.id.tv_title, "当日报价");
        } else {
            helper.setText(R.id.tv_title, "其他");
        }
        helper.setText(R.id.tv_total, item.getTotal()+"");
        helper.setText(R.id.tv_add, item.getAdded()+"");
        helper.setText(R.id.tv_repairOne, item.getStatusOne()+"");
        helper.setText(R.id.tv_repairTwo, item.getStatusTwo()+"");
        helper.setText(R.id.tv_repairThree, item.getStatusThree()+"");
        helper.setText(R.id.tv_repairFour, item.getStatusFour()+"");
    }
}
