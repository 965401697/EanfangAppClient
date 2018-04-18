package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
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

        String bugOne = Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 1);
        String bugTwo = Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 2);
        String bugThree = Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 3);
        helper.setText(R.id.tv_detail_name, (helper.getAdapterPosition() + 1) + "." + bugOne + "-" + bugTwo + "-" + bugThree);
        helper.addOnClickListener(R.id.rl_item_detail);
        helper.setVisible(R.id.tv_delete, false);
        helper.addOnClickListener(R.id.tv_delete);
    }
}
