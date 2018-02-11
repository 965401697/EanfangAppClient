package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.BughandleDetailEntity;

import net.eanfang.worker.R;

import java.util.List;


/**
 * Created by wen on 2017/4/23.
 */

public class FillTroubleDetailAdapter extends BaseQuickAdapter<BughandleDetailEntity, BaseViewHolder> {
    public FillTroubleDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BughandleDetailEntity item) {
        String bugOne = Config.get().getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 1);
        String bugTwo = Config.get().getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 2);
        String bugThree = Config.get().getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 3);

        helper.setText(R.id.tv_detail_name, (helper.getAdapterPosition() + 1) + "." + bugOne + "-" + bugTwo + "-" + bugThree);
        if (StringUtils.isEmpty(item.getCheckProcess())) {
            helper.setVisible(R.id.tv_detai_status, true);
            helper.setText(R.id.tv_detai_status, "待完善");
        } else {
            helper.setVisible(R.id.tv_detai_status, false);
        }
//        helper.addOnClickListener(R.id.rl_item_detail);
        //2017年7月25日 去掉删除按钮
        helper.setVisible(R.id.tv_delete, false);
        // helper.addOnClickListener(R.id.tv_delete);
    }
}
