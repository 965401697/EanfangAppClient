package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.yaf.base.entity.BughandleDetailEntity;

import net.eanfang.client.R;

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
        String bugOne = Config.get().getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 1);
        String bugTwo = Config.get().getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 2);
        String bugThree = Config.get().getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 3);
        helper.setText(R.id.tv_detail_name, (helper.getAdapterPosition() + 1) + "." + bugOne + "-" + bugTwo + "-" + bugThree);

        helper.setVisible(R.id.tv_detai_status, false);
        helper.setVisible(R.id.tv_delete, false);


    }
}
