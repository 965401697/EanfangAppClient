package net.eanfang.client.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.datastatistics.HomeDatastisticeBean;
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
        helper.setText(R.id.tv_repair_num, item.getNum() + "");
        if ("已修复".equals(V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))))) {
            helper.setText(R.id.tv_repair_type, V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))));
            helper.setBackgroundRes(R.id.rl_back_data, R.drawable.bg_home_data_one);
            ((TextView) helper.getView(R.id.tv_repair_num)).setTextColor(helper.getConvertView().getResources().getColor(R.color.color_home_data_text_one));
            ((TextView) helper.getView(R.id.tv_repair_type)).setTextColor(helper.getConvertView().getResources().getColor(R.color.color_home_data_text_one));
        } else if ("维修中".equals(V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))))) {
            helper.setText(R.id.tv_repair_type, V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))));
            helper.setBackgroundRes(R.id.rl_back_data, R.drawable.bg_home_data_two);
            ((TextView) helper.getView(R.id.tv_repair_num)).setTextColor(helper.getConvertView().getResources().getColor(R.color.color_home_company_rz));
            ((TextView) helper.getView(R.id.tv_repair_type)).setTextColor(helper.getConvertView().getResources().getColor(R.color.color_home_company_rz));
        } else if ("报废".equals(V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))))) {
            helper.setText(R.id.tv_repair_type, V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))));
            helper.setBackgroundRes(R.id.rl_back_data, R.drawable.bg_home_data_four);
            ((TextView) helper.getView(R.id.tv_repair_num)).setTextColor(helper.getConvertView().getResources().getColor(R.color.roll_content));
            ((TextView) helper.getView(R.id.tv_repair_type)).setTextColor(helper.getConvertView().getResources().getColor(R.color.roll_content));
        } else if ("5".equals(item.getType())) {
            helper.setText(R.id.tv_repair_type, V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))));
            helper.setText(R.id.tv_repair_type, "待处理");
            helper.setBackgroundRes(R.id.rl_back_data, R.drawable.bg_home_data_three);
            ((TextView) helper.getView(R.id.tv_repair_num)).setTextColor(helper.getConvertView().getResources().getColor(R.color.color_home_data_text_three));
            ((TextView) helper.getView(R.id.tv_repair_type)).setTextColor(helper.getConvertView().getResources().getColor(R.color.color_home_data_text_three));
        } else {
            helper.setText(R.id.tv_repair_type, V.v(() -> GetConstDataUtils.getHomeRepairStatuslList().get(Integer.parseInt(item.getType()))));
            helper.setBackgroundRes(R.id.rl_back_data, R.drawable.bg_home_data_four);
            ((TextView) helper.getView(R.id.tv_repair_num)).setTextColor(helper.getConvertView().getResources().getColor(R.color.roll_content));
            ((TextView) helper.getView(R.id.tv_repair_type)).setTextColor(helper.getConvertView().getResources().getColor(R.color.roll_content));
        }
    }

}
