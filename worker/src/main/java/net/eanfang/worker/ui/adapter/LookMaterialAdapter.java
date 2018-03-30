package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.util.GetConstDataUtils;
import com.yaf.base.entity.BughandleUseDeviceEntity;

import net.eanfang.worker.BuildConfig;
import net.eanfang.worker.R;

import java.util.List;

/**
 * Created by wen on 2017/4/23.
 */

public class LookMaterialAdapter extends BaseQuickAdapter<BughandleUseDeviceEntity, BaseViewHolder> {
    public Config config;
    public GetConstDataUtils constDataUtils;

    public LookMaterialAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        config = Config.get(EanfangApplication.get().getApplicationContext());
        constDataUtils = GetConstDataUtils.get(config);
    }

    @Override
    protected void convert(BaseViewHolder helper, BughandleUseDeviceEntity item) {
        String bugOne = config.getBusinessNameByCode(item.getBusinessThreeCode(), 1);
        String bugTwo = config.getBusinessNameByCode(item.getBusinessThreeCode(), 2);
        String bugThree = config.getBusinessNameByCode(item.getBusinessThreeCode(), 3);
        helper.setText(R.id.tv_detail_name, (helper.getAdapterPosition() + 1) + "." + bugOne + "-" + bugTwo + "-" + bugThree);

        helper.addOnClickListener(R.id.tv_delete);

        //客户端隐藏删除按钮
        if (BuildConfig.APP_TYPE == 0) {
            helper.setVisible(R.id.tv_delete, false);
        }

    }
}
