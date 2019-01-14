package net.eanfang.client.ui.adapter.datastatistics;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.datastatistics.DataDesignBean;

import net.eanfang.client.R;


/**
 * 描述：报修统计 五家公司
 *
 * @author Guanluocang
 * @date on 2018/7/17$  17:52$
 */
public class DataStatisticsDesignCompanyAdapter extends BaseQuickAdapter<DataDesignBean.FiveBean, BaseViewHolder> {
    public DataStatisticsDesignCompanyAdapter() {
        super(R.layout.layout_item_datastatistics_five_company);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataDesignBean.FiveBean item) {
        ImageView iv_companyLogo = helper.getView(R.id.iv_company_logo);
        TextView tv_companyName = helper.getView(R.id.tv_company_name);
        tv_companyName.setText(item.getUserName() + "");
        if (helper.getAdapterPosition() == 0) {
            iv_companyLogo.setImageResource(R.mipmap.ic_data_repair_one);
        } else if (helper.getAdapterPosition() == 1) {
            iv_companyLogo.setImageResource(R.mipmap.ic_data_repair_two);
        } else if (helper.getAdapterPosition() == 2) {
            iv_companyLogo.setImageResource(R.mipmap.ic_data_repair_three);
        } else if (helper.getAdapterPosition() == 3) {
            iv_companyLogo.setImageResource(R.mipmap.ic_data_repair_four);
        } else if (helper.getAdapterPosition() == 4) {
            iv_companyLogo.setImageResource(R.mipmap.ic_data_repair_five);
        }
    }
}
