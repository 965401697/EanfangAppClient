package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.datastatistics.HomeDatastisticeBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.V;

import net.eanfang.worker.R;

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
        helper.setText(R.id.tv_repair_num, item.getNum() + "");
        if ("已修复".equals(V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))))) {
            helper.setText(R.id.tv_repair_type, V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))));
            helper.setBackgroundRes(R.id.rl_back_data, R.drawable.bg_home_data_one);
            helper.setTextColor(R.id.tv_repair_num, R.color.color_home_data_text_one);
            helper.setTextColor(R.id.tv_repair_type, R.color.color_home_data_text_one);
        } else if ("维修中".equals(V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))))) {
            helper.setText(R.id.tv_repair_type, V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))));
            helper.setBackgroundRes(R.id.rl_back_data, R.drawable.bg_home_data_two);
            helper.setTextColor(R.id.tv_repair_num, R.color.colorPrimary);
            helper.setTextColor(R.id.tv_repair_type, R.color.colorPrimary);
        } else if ("报废".equals(V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))))) {
            helper.setText(R.id.tv_repair_type, V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))));
            helper.setBackgroundRes(R.id.rl_back_data, R.drawable.bg_home_data_four);
            helper.setTextColor(R.id.tv_repair_num, R.color.roll_content);
            helper.setTextColor(R.id.tv_repair_type, R.color.roll_content);
        } else if ("5".equals(item.getType())) {
            helper.setText(R.id.tv_repair_type, V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))));
            helper.setText(R.id.tv_repair_type, "待处理");
            helper.setBackgroundRes(R.id.rl_back_data, R.drawable.bg_home_data_three);
            helper.setTextColor(R.id.tv_repair_num, R.color.color_home_data_text_three);
            helper.setTextColor(R.id.tv_repair_type, R.color.color_home_data_text_three);
        } else {
            helper.setText(R.id.tv_repair_type, V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))));
            helper.setBackgroundRes(R.id.rl_back_data, R.drawable.bg_home_data_four);
            helper.setTextColor(R.id.tv_repair_num, R.color.roll_content);
            helper.setTextColor(R.id.tv_repair_type, R.color.roll_content);
        }
    }
}