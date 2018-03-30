package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.model.MaintenanceBean;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.worker.R;

import java.util.List;


/**
 * Created by wen on 2017/4/23.
 */

public class MaintenanceDetailAdapter extends BaseQuickAdapter<MaintenanceBean.MaintainDetailsBean, BaseViewHolder> {

    public Config config;
    public GetConstDataUtils constDataUtils;

    public MaintenanceDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        config = Config.get(EanfangApplication.get().getApplicationContext());
        constDataUtils = GetConstDataUtils.get(config);
    }

    @Override
    protected void convert(BaseViewHolder helper, MaintenanceBean.MaintainDetailsBean item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "." + config.getBusinessNameByCode(item.getBusinessThreeCode(), 1));
        helper.addOnClickListener(R.id.rl_item_detail);
        helper.addOnClickListener(R.id.tv_delete);
    }
}
