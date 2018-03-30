package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.util.GetConstDataUtils;
import com.yaf.base.entity.BughandleDetailEntity;

import net.eanfang.client.R;

import java.util.List;


/**
 * Created by wen on 2017/4/23.
 */

public class TroubleDetailAdapter extends BaseQuickAdapter<BughandleDetailEntity, BaseViewHolder> {

    private Config config = Config.get(EanfangApplication.get().getApplicationContext());
    private GetConstDataUtils constDataUtils = GetConstDataUtils.get(config);

    public TroubleDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BughandleDetailEntity item) {
        String bugOne = config.getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 1);
        String bugTwo = config.getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 2);
        String bugThree = config.getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 3);
        helper.setText(R.id.tv_detail_name, (helper.getAdapterPosition() + 1) + "." + bugOne + "-" + bugTwo + "-" + bugThree);

        helper.setVisible(R.id.tv_detai_status, false);
        helper.setVisible(R.id.tv_delete, false);


    }
}
