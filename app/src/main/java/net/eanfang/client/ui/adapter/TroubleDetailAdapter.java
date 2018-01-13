package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.config.Config;
import net.eanfang.client.ui.model.repair.BughandleDetailEntity;

import java.util.List;



/**
 * Created by wen on 2017/4/23.
 */

public class TroubleDetailAdapter extends BaseQuickAdapter<BughandleDetailEntity, BaseViewHolder> {
    public TroubleDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BughandleDetailEntity item) {
        helper.setText(R.id.tv_detail_name, Config.getConfig().getBusinessName(item.getFailureEntity().getBusinessThreeCode()));

        helper.setVisible(R.id.tv_detai_status, false);
        helper.setVisible(R.id.tv_delete, false);


    }
}
