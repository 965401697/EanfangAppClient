package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.yaf.base.entity.RepairBugEntity;

import net.eanfang.client.R;

import java.util.List;


/**
 * 我要报修中的adapter
 * Created by Administrator on 2017/3/26.
 */

public class ToRepairAdapter extends BaseQuickAdapter<RepairBugEntity, BaseViewHolder> {
    public ToRepairAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairBugEntity item) {
        // TODO: 2017/12/26 设备类别，设备名称，品牌型号，故障位置

        helper.setText(R.id.tv_name, helper.getPosition() + 1 + "." + Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 1))
                .addOnClickListener(R.id.tv_delete);
    }
}
