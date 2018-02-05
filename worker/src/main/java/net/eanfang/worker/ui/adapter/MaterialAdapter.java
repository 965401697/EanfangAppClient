package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaf.base.entity.BughandleUseDeviceEntity;

import net.eanfang.worker.R;

import java.util.List;


/**
 * Created by wen on 2017/4/23.
 */

public class MaterialAdapter extends BaseQuickAdapter<BughandleUseDeviceEntity, BaseViewHolder> {
    public MaterialAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BughandleUseDeviceEntity item) {
        helper.setText(R.id.tv_detail_name, helper.getAdapterPosition() + 1 + ".待处理");
//                + item.getModelCode() + "-"
//                + item.getEquipmentname() + "-"
//                + item.getEquipmentmodel());
        helper.addOnClickListener(R.id.rl_item_detail);
        helper.setVisible(R.id.tv_delete, true);
        helper.addOnClickListener(R.id.tv_delete);
    }
}
