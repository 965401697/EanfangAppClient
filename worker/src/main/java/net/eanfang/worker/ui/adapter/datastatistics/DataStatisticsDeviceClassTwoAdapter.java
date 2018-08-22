package net.eanfang.worker.ui.adapter.datastatistics;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.eanfang.model.datastatistics.DataStatisticsBean;

import net.eanfang.worker.R;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/7/18$  15:20$
 */
public class DataStatisticsDeviceClassTwoAdapter extends BaseQuickAdapter<DataStatisticsBean.DeviceBean.BussinessTwoCodeListBean, BaseViewHolder> {
    public DataStatisticsDeviceClassTwoAdapter() {
        super(R.layout.layout_item_datastatistics_device_class_two);
    }


    @Override
    protected void convert(BaseViewHolder helper, DataStatisticsBean.DeviceBean.BussinessTwoCodeListBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_num = helper.getView(R.id.tv_num);
        tv_name.setText(Config.get().getBusinessNameByCode(item.getType(), 2));
        tv_num.setText(item.getCount() + "");
    }
}
