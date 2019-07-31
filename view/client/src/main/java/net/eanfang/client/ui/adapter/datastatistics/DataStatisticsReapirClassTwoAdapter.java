package net.eanfang.client.ui.adapter.datastatistics;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.datastatistics.DataStatisticsBean;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.client.R;


/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/7/17$  15:42$
 */
public class DataStatisticsReapirClassTwoAdapter extends BaseQuickAdapter<DataStatisticsBean.RepairBean.BughandleStatusTwoListBean, BaseViewHolder> {
    public DataStatisticsReapirClassTwoAdapter() {
        super(R.layout.layout_item_datastatistics_repair_class_two);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataStatisticsBean.RepairBean.BughandleStatusTwoListBean item) {

        TextView tv_subName = helper.getView(R.id.tv_repair_class_two_name);
        TextView tv_subNum = helper.getView(R.id.tv_repair_class_two_num);

        if (item.getStatus() == 0) { //修复
            tv_subName.setText(GetConstDataUtils.getBugDetailTwoList(item.getStatus()).get(item.getBughandleStatusTwo()) + ":");
            tv_subNum.setText(item.getCount() + "");
        } else if (item.getStatus() == 1) {//维修中
            tv_subName.setText(GetConstDataUtils.getBugDetailTwoList(item.getStatus()).get(item.getBughandleStatusTwo()) + ":");
            tv_subNum.setText(item.getCount() + "");
        } else if (item.getStatus() == 2) {//报废
            tv_subName.setText(GetConstDataUtils.getBugDetailTwoList(item.getStatus()).get(item.getBughandleStatusTwo()) + ":");
            tv_subNum.setText(item.getCount() + "");
        } else if (item.getStatus() == 3) {//放弃
            tv_subName.setText(GetConstDataUtils.getBugDetailTwoList(item.getStatus()).get(item.getBughandleStatusTwo()) + ":");
            tv_subNum.setText(item.getCount() + "");
        } else if (item.getStatus() == 4) {//关闭
            tv_subName.setText(GetConstDataUtils.getBugDetailTwoList(item.getStatus()).get(item.getBughandleStatusTwo()) + ":");
            tv_subNum.setText(item.getCount() + "");
        }

    }
}
