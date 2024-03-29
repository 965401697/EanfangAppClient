package net.eanfang.worker.ui.activity.worksapce.equipment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.CustDeviceParamEntity;

import net.eanfang.worker.R;

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
