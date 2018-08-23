package net.eanfang.client.ui.activity.worksapce.equipment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaf.base.entity.CustDeviceParamEntity;

import net.eanfang.client.R;


/**
 * Created by O u r on 2018/8/20.
 */

public class EquipmentParamAdapter extends BaseQuickAdapter<CustDeviceParamEntity, BaseViewHolder> {
    public EquipmentParamAdapter() {
        super(R.layout.item_equipment_param);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustDeviceParamEntity item) {
        helper.setText(R.id.tv_desc, item.getParamName());
        helper.setText(R.id.tv_value, item.getParamValue());
    }
}