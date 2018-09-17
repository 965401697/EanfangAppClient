package net.eanfang.worker.ui.activity.worksapce.maintenance;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.yaf.base.entity.ShopMaintenanceDeviceEntity;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/7/16.
 */

public class MaintenanceOrderDetailDeviceListAdapter extends BaseQuickAdapter<ShopMaintenanceDeviceEntity, BaseViewHolder> {
    public MaintenanceOrderDetailDeviceListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopMaintenanceDeviceEntity item) {
        helper.setText(R.id.tv_os_type, (helper.getAdapterPosition() + 1) + ". " + Config.get().getBaseNameByCode(Constant.SYS_TYPE, item.getBusinessThreeCode(), 3));
        helper.setText(R.id.tv_brand, "品牌：" + Config.get().getBaseNameByCode(Constant.MODEL, item.getBusinessFourCode(), 2));
        helper.setText(R.id.tv_mode, "型号：" + item.getSpecification());
        helper.setText(R.id.tv_count, "数量：" + String.valueOf(item.getAmount()));
        helper.setText(R.id.tv_position_num, "位置编号    " + item.getLocationNumber());
        helper.setText(R.id.tv_notice, "备注信息    " + item.getDeviceInfo());
    }
}
