package net.eanfang.client.ui.adapter.datastatistics;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.datastatistics.DataInstallBean;

import net.eanfang.client.R;


/**
 * 描述：报修统计 五家公司
 *
 * @author Guanluocang
 * @date on 2018/7/17$  17:52$
 */
public class DataStatisticsInstallCompanyAdapter extends BaseQuickAdapter<DataInstallBean.FiveBean, BaseViewHolder> {
    public DataStatisticsInstallCompanyAdapter() {
        super(R.layout.layout_item_datastatistics_five_company);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataInstallBean.FiveBean item) {
        ImageView iv_companyLogo = helper.getView(R.id.iv_company_logo);
        TextView tv_companyName = helper.getView(R.id.tv_company_name);
        tv_companyName.setText(item.getClientCompanyName() + "");
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
