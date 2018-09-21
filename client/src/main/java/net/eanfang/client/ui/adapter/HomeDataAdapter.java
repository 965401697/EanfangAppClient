package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.datastatistics.HomeDatastisticeBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.V;

import net.eanfang.client.R;

/**
 * Created by admin on 2018/4/11.
 */
public class HomeDataAdapter extends BaseQuickAdapter<HomeDatastisticeBean.GroupBean, BaseViewHolder> {

    public HomeDataAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeDatastisticeBean.GroupBean item) {
        // 数量
        helper.setText(R.id.tv_repair_num, item.getCount() + "");

        if ("已修复".equals(V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))))) {
            helper.setBackgroundRes(R.id.tv_repair_num, R.drawable.bg_home_data_one);
        } else if ("维修中".equals(V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))))) {
            helper.setBackgroundRes(R.id.tv_repair_num, R.drawable.bg_home_data_two);
        } else if ("报废".equals(V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))))) {
            helper.setBackgroundRes(R.id.tv_repair_num, R.drawable.bg_home_data_three);
        } else {
            helper.setBackgroundRes(R.id.tv_repair_num, R.drawable.bg_home_data_four);
        }
        // 底下类型
        if ("5".equals(item.getType())) {
            helper.setText(R.id.tv_repair_type, "待处理");
        } else {
            helper.setText(R.id.tv_repair_type, V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))));
        }

    }
}
